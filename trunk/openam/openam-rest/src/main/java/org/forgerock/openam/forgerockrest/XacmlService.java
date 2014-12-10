/*
 * The contents of this file are subject to the terms of the Common Development and
 * Distribution License (the License). You may not use this file except in compliance with the
 * License.
 *
 * You can obtain a copy of the License at legal/CDDLv1.0.txt. See the License for the
 * specific language governing permission and limitations under the License.
 *
 * When distributing Covered Software, include this CDDL Header Notice in each file and include
 * the License file at legal/CDDLv1.0.txt. If applicable, add the following below the CDDL
 * Header, with the fields enclosed by brackets [] replaced by your own identifying
 * information: "Portions copyright [year] [name of copyright owner]".
 *
 * Copyright 2014 ForgeRock AS.
 */

package org.forgerock.openam.forgerockrest;

import com.sun.identity.entitlement.EntitlementException;
import com.sun.identity.entitlement.opensso.SubjectUtils;
import com.sun.identity.entitlement.xacml3.SearchFilterFactory;
import com.sun.identity.entitlement.xacml3.XACMLExportImport;
import com.sun.identity.entitlement.xacml3.XACMLExportImport.ImportStep;
import com.sun.identity.entitlement.xacml3.XACMLPrivilegeUtils;
import com.sun.identity.entitlement.xacml3.core.PolicySet;
import com.sun.identity.security.AdminTokenAction;
import com.sun.identity.shared.debug.Debug;
import org.forgerock.openam.rest.service.RestletRealmRouter;
import org.forgerock.openam.rest.service.XACMLServiceEndpointApplication;
import org.forgerock.util.annotations.VisibleForTesting;
import org.restlet.data.Disposition;
import org.restlet.data.Status;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.ext.servlet.ServletUtils;
import org.restlet.representation.OutputRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import javax.inject.Inject;
import javax.inject.Named;
import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.forgerock.json.resource.ResourceException.BAD_REQUEST;
import static org.forgerock.json.resource.ResourceException.INTERNAL_ERROR;

/**
 * Provides XACML based services
 */
public class XacmlService extends ServerResource {

    public static final String QUERY_PARAM_STRING = "filter";

    private static final String ROOT_REALM = "/";

    private final XACMLExportImport importExport;
    private final AdminTokenAction admin;
    private final Debug debug;

    /**
     * Constructor with dependencies exposed for unit testing.
     *
     * @param importExport Non null utility functions.
     * @param adminTokenAction Non null admin action function.
     * @param debug The debug instance for logging.
     */
    @Inject
    public XacmlService(XACMLExportImport importExport, AdminTokenAction adminTokenAction,
            @Named("frRest") Debug debug) {
        this.importExport = importExport;
        this.admin = adminTokenAction;
        this.debug = debug;
    }

    /**
     * Returns a Subject for the valid admin SSOToken.
     *
     * @return A subject for the valid admin SSOToken.
     */
    private final Subject getAdminToken() {
        return SubjectUtils.createSubject(AccessController.doPrivileged(admin));
    }

    /**
     * Expects to receive XACML formatted XML which will be read and imported.
     */
    @Post
    public Representation importXACML(Representation entity) {
        String realm = RestletRealmRouter.getRealmFromRequest(getRequest());
        boolean dryRun = "true".equalsIgnoreCase(getQuery().getFirstValue("dryrun"));
        List<ImportStep> steps;

        try {
            steps = importExport.importXacml(realm, entity.getStream(), getAdminToken(), dryRun);
        } catch (EntitlementException e) {
            debug.warning("Importing XACML to policies failed", e);
            throw new ResourceException(new Status(BAD_REQUEST, e, e
                    .getLocalizedMessage(getRequestLocale()), null, null));
        } catch (IOException e) {
            debug.warning("Reading XACML import failed", e);
            throw new ResourceException(new Status(BAD_REQUEST, e, e
                    .getLocalizedMessage(), null, null));
        }

        if (steps.isEmpty()) {
            throw new ResourceException(new Status(BAD_REQUEST,
                    "No policies found in XACML document", null, null));
        }

        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        for (XACMLExportImport.ImportStep step : steps) {
            Map<String, String> stepResult = new HashMap<String, String>();
            stepResult.put("status", String.valueOf(step.getDiffStatus().getCode()));
            stepResult.put("name", step.getPrivilege().getName());
            result.add(stepResult);
        }
        getResponse().setStatus(Status.SUCCESS_OK);
        return new JacksonRepresentation<List<Map<String, String>>>(result);
    }

    /**
     * Get the client's preferred locale, or the server default if not specified.
     */
    private Locale getRequestLocale() {
        final HttpServletRequest httpRequest = ServletUtils.getRequest(getRequest());
        return httpRequest == null ? Locale.getDefault() : httpRequest.getLocale();
    }

    /**
     * Export all Policies defined within the Realm used to access this end point.
     *
     * The end point supports the query parameter "filter" which can be used multiple
     * times to define the Search Filters which will restrict the output to only those
     * Privileges which match the Search Filters.
     *
     * See {@link SearchFilterFactory} for more details on the format of these
     * Search Filters.
     *
     * @return XACML of the matching Privileges.
     */
    @Get
    public Representation exportXACML() {
        String realm = RestletRealmRouter.getRealmFromRequest(getRequest());
        return exportXACML(realm);
    }

    /**
     * This version of exportXACML here for testing - it saves trying to mock the static getRealmFromRequest
     * @param realm The realm
     * @return Representation object wrapping the converted XACML
     */
    @VisibleForTesting
    Representation exportXACML(String realm) {

        List<String> filters = new ArrayList<String>(
                Arrays.asList(getQuery().getValuesArray(QUERY_PARAM_STRING)));

        final PolicySet policySet;
        try {
            policySet = importExport.exportXACML(realm, getAdminToken(), filters);
        } catch (EntitlementException e) {
            debug.warning("Reading Policies failed", e);
            throw new ResourceException(new Status(INTERNAL_ERROR, e.getLocalizedMessage(getRequestLocale()), null,
                    null));
        }

        getResponse().setStatus(Status.SUCCESS_OK);

        Representation result = new OutputRepresentation(XACMLServiceEndpointApplication.APPLICATION_XML_XACML3) {
            @Override
            public void write(OutputStream outputStream) throws IOException {
                try {
                    XACMLPrivilegeUtils.writeXMLToStream(policySet, outputStream);
                } catch (EntitlementException e) {
                    throw new IOException(e);
                }
            }
        };
        // OPENAM-4974
        Disposition disposition = new Disposition();
        disposition.setType(Disposition.TYPE_ATTACHMENT);
        disposition.setFilename(getPolicyAttachmentFileName(realm));
        result.setDisposition(disposition);

        return result;
    }

    /**
     * Figure the name of the attachment file that will be created to contain the policies.  See OPENAM-4974.
     * File naming agreed with Andy H.
     *
     * @param realm The realm
     * @return A suitable file name, involving the realm in a meaningful way.
     */
    private String getPolicyAttachmentFileName(String realm) {
        String result;
        if (ROOT_REALM.equals(realm)) {
            result = "realm-policies";
        } else {
            result = realm.substring(1).replace('/', '-') + "-realm-policies";
        }
        return result + ".xml";
    }
}
