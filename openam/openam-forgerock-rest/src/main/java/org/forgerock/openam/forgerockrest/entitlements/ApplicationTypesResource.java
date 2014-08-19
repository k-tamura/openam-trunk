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
package org.forgerock.openam.forgerockrest.entitlements;

import com.google.inject.name.Named;
import com.sun.identity.entitlement.ApplicationType;
import com.sun.identity.shared.debug.Debug;
import java.io.IOException;
import static java.lang.Math.max;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.security.auth.Subject;
import org.forgerock.json.fluent.JsonPointer;
import org.forgerock.json.fluent.JsonValue;
import org.forgerock.json.resource.ActionRequest;
import org.forgerock.json.resource.CreateRequest;
import org.forgerock.json.resource.DeleteRequest;
import org.forgerock.json.resource.PatchRequest;
import org.forgerock.json.resource.QueryRequest;
import org.forgerock.json.resource.QueryResult;
import org.forgerock.json.resource.QueryResultHandler;
import org.forgerock.json.resource.ReadRequest;
import org.forgerock.json.resource.Resource;
import org.forgerock.json.resource.ResourceException;
import org.forgerock.json.resource.ResultHandler;
import org.forgerock.json.resource.SecurityContext;
import org.forgerock.json.resource.ServerContext;
import org.forgerock.json.resource.UpdateRequest;
import org.forgerock.openam.forgerockrest.RestUtils;
import org.forgerock.openam.forgerockrest.entitlements.wrappers.ApplicationTypeManagerWrapper;
import org.forgerock.openam.forgerockrest.entitlements.wrappers.ApplicationTypeWrapper;

/**
 * Allows for CREST-handling of stored {@link ApplicationType}s.
 *
 * These are unmodfiable - even by an administrator. As such this
 * endpoint only supports the READ and QUERY operations.
 */
public class ApplicationTypesResource extends SubjectAwareResource {

    private final ApplicationTypeManagerWrapper typeManager;
    private final Debug debug;

    /**
     * Guiced-constructor.
     *
     * @param typeManager from which to locate application types
     * @param debug Debugger to use
     */
    @Inject
    public ApplicationTypesResource(final ApplicationTypeManagerWrapper typeManager, @Named("frRest") Debug debug) {
        this.typeManager = typeManager;
        this.debug = debug;
    }


    /**
     * Unsupported by this endpoint.
     */
    @Override
    public void actionCollection(ServerContext context, ActionRequest request, ResultHandler<JsonValue> handler) {
        RestUtils.generateUnsupportedOperation(handler);
    }

    /**
     * Unsupported by this endpoint.
     */
    @Override
    public void actionInstance(ServerContext context, String resourceId, ActionRequest request, ResultHandler<JsonValue> handler) {
        RestUtils.generateUnsupportedOperation(handler);
    }

    /**
     * Unsupported by this endpoint.
     */
    @Override
    public void createInstance(ServerContext context, CreateRequest request, ResultHandler<Resource> handler) {
        RestUtils.generateUnsupportedOperation(handler);
    }

    /**
     * Unsupported by this endpoint.
     */
    @Override
    public void deleteInstance(ServerContext context, String resourceId, DeleteRequest request, ResultHandler<Resource> handler) {
        RestUtils.generateUnsupportedOperation(handler);
    }

    /**
     * Unsupported by this endpoint.
     */
    @Override
    public void patchInstance(ServerContext context, String resourceId, PatchRequest request, ResultHandler<Resource> handler) {
        RestUtils.generateUnsupportedOperation(handler);
    }

    /**
     * Unsupported by this endpoint.
     */
    @Override
    public void updateInstance(ServerContext context, String resourceId, UpdateRequest request, ResultHandler<Resource> handler) {
        RestUtils.generateUnsupportedOperation(handler);
    }

    /**
     * Reads the details of all {@link ApplicationType}s in the system.
     *
     * The user's {@link SecurityContext} must indicate they are a user with administrator-level access.
     *
     * @param context {@inheritDoc}
     * @param request {@inheritDoc}
     * @param handler {@inheritDoc}
     */
    @Override
    public void queryCollection(ServerContext context, QueryRequest request, QueryResultHandler handler) {

        //auth
        final Subject mySubject = getContextSubject(context, handler);

        if (mySubject == null) {
            return;
        }

        //select
        final Set<String> appTypeNames =  typeManager.getApplicationTypeNames(mySubject);
        List<ApplicationTypeWrapper> appTypes = new LinkedList<ApplicationTypeWrapper>();

        for (String appTypeName : appTypeNames) {
            final ApplicationType type = typeManager.getApplicationType(mySubject, appTypeName);
            final ApplicationTypeWrapper wrap = new ApplicationTypeWrapper(type);
            if (type != null) {
                appTypes.add(wrap);
            }
        }

        int totalSize = appTypes.size();
        int pageSize = request.getPageSize();
        int offset = request.getPagedResultsOffset();

        if (pageSize > 0) {
            Collections.sort(appTypes);
            appTypes = appTypes.subList(offset, offset + pageSize);
        }

        final List<JsonValue> jsonifiedAppTypes = jsonify(appTypes);

        final JsonPointer jp = new JsonPointer(ApplicationType.FIELD_NAME);

        for (JsonValue appTypeToReturn : jsonifiedAppTypes) {

            final JsonValue resourceId = appTypeToReturn.get(jp);
            final String id = resourceId != null ? resourceId.toString() : null;

            final Resource resource = new Resource(id, "0", appTypeToReturn);

            handler.handleResource(resource);
        }

        //paginate
        if (pageSize > 0) {
            final String lastIndex = offset + pageSize > totalSize ? String.valueOf(totalSize) : String.valueOf(offset + pageSize);
            handler.handleResult(new QueryResult(lastIndex, max(0, totalSize - (offset + pageSize))));
        } else {
            handler.handleResult(new QueryResult(null, -1));
        }

    }

    /**
     * Takes a set of ApplicationTypes and returns their Json representation in JsonValue.
     *
     * @param types The ApplicationTypes whose values to look up and return in the JsonValue
     * @return a {@link JsonValue} object representing the provided {@link Set}
     */
    protected List<JsonValue> jsonify(List<ApplicationTypeWrapper> types) {

        final List<JsonValue> applicationsList = new ArrayList<JsonValue>();

        for (ApplicationTypeWrapper entry : types) {
            try {
                applicationsList.add(entry.toJsonValue());
            } catch (IOException e) {
                debug.error("Error converting entry to Json value.");
            }
        }

        return applicationsList;
    }

    /**
     * Reads the details of a single instance of an {@link ApplicationType} - the instance
     * referred to by the passed-in resourceId.
     *
     * The user's {@link SecurityContext} must indicate they are a user with administrator-level access.
     *
     * @param context {@inheritDoc}
     * @param resourceId {@inheritDoc}
     * @param request {@inheritDoc}
     * @param handler {@inheritDoc}
     */
    @Override
    public void readInstance(ServerContext context, String resourceId, ReadRequest request, ResultHandler<Resource> handler) {

        //auth
        final Subject mySubject = getContextSubject(context, handler);

        if (mySubject == null) {
            return;
        }

        final ApplicationType applType = typeManager.getApplicationType(mySubject, resourceId);
        final ApplicationTypeWrapper wrap = new ApplicationTypeWrapper(applType);

        if (applType == null) {
            debug.error("Read failed on invalid ApplicationType.");
            handler.handleError(ResourceException.getException(ResourceException.NOT_FOUND));
            return;
        }

        try {
            final Resource resource = new Resource(resourceId, "0",
                    JsonValue.json(wrap.toJsonValue()));
            handler.handleResult(resource);
        } catch (IOException e) {
            debug.message("ApplicationType could not jsonify class associated with defined Type.", e);
            handler.handleError(ResourceException.getException(ResourceException.INTERNAL_ERROR));
        }
    }

}
