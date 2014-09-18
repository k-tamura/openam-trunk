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
package org.forgerock.openam.rest.authz;

import com.iplanet.dpro.session.service.SessionService;
import com.sun.identity.shared.debug.Debug;
import javax.inject.Inject;
import javax.inject.Named;
import org.forgerock.authz.filter.api.AuthorizationResult;
import org.forgerock.json.resource.ActionRequest;
import org.forgerock.json.resource.CreateRequest;
import org.forgerock.json.resource.DeleteRequest;
import org.forgerock.json.resource.PatchRequest;
import org.forgerock.json.resource.QueryRequest;
import org.forgerock.json.resource.ReadRequest;
import org.forgerock.json.resource.Request;
import org.forgerock.json.resource.ResourceException;
import org.forgerock.json.resource.ServerContext;
import org.forgerock.json.resource.UpdateRequest;
import org.forgerock.openam.forgerockrest.session.SessionResource;
import org.forgerock.openam.utils.Config;
import org.forgerock.util.promise.Promise;
import org.forgerock.util.promise.Promises;

/**
 * Authorization module specifically designed for the Sessions Resource endpoint. This allows anonymous access
 * to the Sessions Endpoint for the ACTIONS of 'logout' and 'validate'. All other endpoint requests are
 * pushed up to the {@link AdminOnlyAuthzModule}.
 */
public class SessionResourceAuthzModule extends AdminOnlyAuthzModule {

    public final static String NAME = "SessionResourceFilter";

    @Inject
    public SessionResourceAuthzModule(Config<SessionService> sessionService, @Named("frRest") Debug debug) {
        super(sessionService, debug);
    }

    @Override
    public Promise<AuthorizationResult, ResourceException> authorizeCreate(ServerContext context, CreateRequest request) {
        return authorize(context);
    }

    @Override
    public Promise<AuthorizationResult, ResourceException> authorizeRead(ServerContext context, ReadRequest request) {
        return authorize(context);
    }

    @Override
    public Promise<AuthorizationResult, ResourceException> authorizeUpdate(ServerContext context, UpdateRequest request) {
        return authorize(context);
    }

    @Override
    public Promise<AuthorizationResult, ResourceException> authorizeDelete(ServerContext context, DeleteRequest request) {
        return authorize(context);
    }

    @Override
    public Promise<AuthorizationResult, ResourceException> authorizePatch(ServerContext context, PatchRequest request) {
        return authorize(context);
    }

    @Override
    public Promise<AuthorizationResult, ResourceException> authorizeAction(ServerContext context, ActionRequest request) {
        return authorize(context);
    }

    @Override
    public Promise<AuthorizationResult, ResourceException> authorizeQuery(ServerContext context, QueryRequest request) {
        return authorize(context);
    }

    /**
     * Lets through requests to ?_action=logout and ?_action=validate, otherwise defers to
     * {@link AdminOnlyAuthzModule}.
     */
    Promise<AuthorizationResult, ResourceException> authorize(Request request, ServerContext context) {

        if ((request instanceof ActionRequest)) {
            ActionRequest actionRequest = (ActionRequest) request;
            if (SessionResource.LOGOUT.equals(actionRequest.getAction()) ||
                    SessionResource.VALIDATE.equals(actionRequest.getAction())) {
                if (debug.messageEnabled()) {
                    debug.message("SessionResourceAuthzModule :: " + actionRequest.getAction() +
                            " action request authorized by module.");
                }
                return Promises.newSuccessfulPromise(AuthorizationResult.success());
            }
        }

        if (debug.messageEnabled()) {
            debug.message("SessionResourceAuthzModule :: Request forwarded to AdminOnlyAuthzModule.");
        }
        return super.authorize(context);
    }
}