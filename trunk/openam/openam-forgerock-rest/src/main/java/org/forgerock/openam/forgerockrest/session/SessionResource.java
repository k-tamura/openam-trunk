/*
 * Copyright 2013-2014 ForgeRock AS.
 *
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
 */

package org.forgerock.openam.forgerockrest.session;

import com.iplanet.dpro.session.service.SessionConstants;
import com.iplanet.dpro.session.share.SessionInfo;
import com.iplanet.services.naming.WebtopNaming;
import com.iplanet.sso.SSOException;
import com.iplanet.sso.SSOToken;
import com.iplanet.sso.SSOTokenManager;
import com.sun.identity.authentication.service.AuthUtils;
import com.sun.identity.shared.debug.Debug;
import org.forgerock.json.fluent.JsonValue;
import org.forgerock.json.resource.ActionRequest;
import org.forgerock.json.resource.BadRequestException;
import org.forgerock.json.resource.CollectionResourceProvider;
import org.forgerock.json.resource.CreateRequest;
import org.forgerock.json.resource.DeleteRequest;
import org.forgerock.json.resource.InternalServerErrorException;
import org.forgerock.json.resource.NotSupportedException;
import org.forgerock.json.resource.PatchRequest;
import org.forgerock.json.resource.QueryRequest;
import org.forgerock.json.resource.QueryResult;
import org.forgerock.json.resource.QueryResultHandler;
import org.forgerock.json.resource.ReadRequest;
import org.forgerock.json.resource.Resource;
import org.forgerock.json.resource.ResourceException;
import org.forgerock.json.resource.ResultHandler;
import org.forgerock.json.resource.ServerContext;
import org.forgerock.json.resource.UpdateRequest;
import org.forgerock.openam.forgerockrest.RestUtils;
import org.forgerock.openam.forgerockrest.session.query.SessionQueryManager;
import org.forgerock.openam.rest.resource.SSOTokenContext;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.forgerock.json.fluent.JsonValue.field;
import static org.forgerock.json.fluent.JsonValue.json;
import static org.forgerock.json.fluent.JsonValue.object;

/**
 * Represents Sessions that can queried via a REST interface.
 *
 * Currently describe three different entrypoints for this Resource, useful when querying
 * Session Information:
 *
 * <ul>
 *     <li>All - All sessions across all servers known to OpenAM.</li>
 *     <li>Servers - Lists all servers that are known to OpenAM.</li>
 *     <li>[server-id] - Lists the servers for that server instance.</li>
 * </ul>
 *
 * This resources acts as a read only resource for the moment.
 *
 * @author robert.wapshott@forgerock.com
 */
public class SessionResource implements CollectionResourceProvider {

    private static final Debug DEBUG = Debug.getInstance(SessionConstants.SESSION_DEBUG);

    public static final String KEYWORD_ALL = "all";
    public static final String KEYWORD_LIST = "list";

    public static final String HEADER_USER_ID = "userid";
    public static final String HEADER_TIME_REMAINING = "timeleft";

    private final SSOTokenManager ssoTokenManager;

    private SessionQueryManager queryManager;

    /**
     * Dependency Injection constructor allowing the SessionResource dependency to be provided.
     *
     * @param sessionQueryManager An instance of the SessionQueryManager. Must not null.
     * @param ssoTokenManager An instance of the SSOTokenManager.
     */
    @Inject
    public SessionResource(final SessionQueryManager sessionQueryManager, final SSOTokenManager ssoTokenManager) {
        this.queryManager = sessionQueryManager;
        this.ssoTokenManager = ssoTokenManager;
    }

    /**
     * Returns a collection of all Server ID that are known to the OpenAM instance.
     *
     *  @return A non null, possibly empty collection of server ids.
     */
    public Collection<String> getAllServerIds() {
        try {
            return WebtopNaming.getAllServerIDs();
        } catch (Exception e) {
            throw new IllegalStateException("Cannot recover from this error", e);
        }
    }

    /**
     * Actions supported are:
     * <ul>
     * <li>logout - invalidates the token on the request</li>
     * <li>validate - validate if the token on the request is still valid</li>
     * </ul>
     *
     * @param context {@inheritDoc}
     * @param request {@inheritDoc}
     * @param handler {@inheritDoc}
     */
    public void actionCollection(ServerContext context, ActionRequest request, ResultHandler<JsonValue> handler) {

        final String action = request.getAction();
        try {
            final SSOToken ssoToken = context.asContext(SSOTokenContext.class).getCallerSSOToken(ssoTokenManager);

            if ("logout".equalsIgnoreCase(action)) {

                if (ssoToken == null) {
                    final BadRequestException e = new BadRequestException("iPlanetDirectoryCookie not set on request");
                    DEBUG.error("iPlanetDirectoryCookie not set on request", e);
                    handler.handleError(e);
                    return;
                }

                try {
                    final JsonValue jsonValue = logout(ssoToken.getTokenID().toString());
                    handler.handleResult(jsonValue);
                } catch (InternalServerErrorException e) {
                    DEBUG.error("Exception handling logout", e);
                    handler.handleError(e);
                }
                return;
            }

            if ("validate".equalsIgnoreCase(action)) {

                if (ssoToken == null) {
                    final BadRequestException e = new BadRequestException("SSO Token Id Cookie not set on request");
                    DEBUG.error("SSO Token Id Cookie not set on request", e);
                    handler.handleError(e);
                    return;
                }

                handler.handleResult(validateSession(ssoToken));
                return;
            }

            final NotSupportedException e =
                    new NotSupportedException("Action, " + action + ", Not implemented for this Resource");
            DEBUG.error("Action, " + action + ", Not implemented for this Resource", e);
            handler.handleError(e);
        } catch (SSOException e) {
            DEBUG.error(e.getMessage());
            handler.handleError(ResourceException.getException(ResourceException.INTERNAL_ERROR, e.getMessage()));
        }
    }

    /**
     * Actions supported are:
     * <ul>
     * <li>logout - invalidates the token</li>
     * <li>validate - validate if the token is still valid</li>
     * </ul>
     *
     * @param context {@inheritDoc}
     * @param request {@inheritDoc}
     * @param handler {@inheritDoc}
     */
    public void actionInstance(ServerContext context, String resourceId, ActionRequest request,
            ResultHandler<JsonValue> handler) {

        final String action = request.getAction();

        if ("logout".equalsIgnoreCase(action)) {
            try {
                JsonValue jsonValue = logout(resourceId);
                handler.handleResult(jsonValue);
            } catch (InternalServerErrorException e) {
                DEBUG.error("Exception handling logout", e);
                handler.handleError(e);
            }
            return;
        }

        if ("validate".equalsIgnoreCase(action)) {
            handler.handleResult(validateSession(resourceId));
            return;
        }

        NotSupportedException e = new NotSupportedException("Action, " + action + ", Not implemented for this Resource");
        DEBUG.error("Action, " + action + ", Not implemented for this Resource", e);
        handler.handleError(e);
    }

    /**
     * Will validate that the specified SSO Token Id is valid or not.
     * <br/>
     * Example response:
     * { "valid": true }
     * <br/>
     * If there is any problem getting or validating the token which causes an exception the json response will be
     * false. In addition if the token is expired then the json response will be set to true. Otherwise it will be
     * set to true.
     *
     * @param tokenId The SSO Token Id.
     * @return The json response of the validation.
     */
    private JsonValue validateSession(final String tokenId) {

        try {
            final SSOToken ssoToken = ssoTokenManager.createSSOToken(tokenId);
            return validateSession(ssoToken);
        } catch (SSOException e) {
            DEBUG.error("Session validation for token, " + tokenId + ", failed.", e);
            return json(object(field("valid", false)));
        }
    }

    private JsonValue validateSession(final SSOToken ssoToken) {
        try {
            if (!ssoTokenManager.isValidToken(ssoToken)) {
                DEBUG.message("Session validation for token, " + ssoToken.getTokenID() + ", returning false.");
                return json(object(field("valid", false)));
            }

            DEBUG.message("Session validation for token, " + ssoToken.getTokenID() + ", returning true.");
            return json(object(field("valid", true), field("prn", ssoToken.getPrincipal().getName())));
        } catch (SSOException e) {
            DEBUG.error("Session validation for token, " + ssoToken.getTokenID() + ", failed.", e);
            return json(object(field("valid", false)));
        }
    }

    /**
     * Logs out a user.
     *
     * @param tokenId The id of the Token to invalidate
     * @throws InternalServerErrorException If the tokenId is invalid or could not be used to logout.
     */
    private JsonValue logout(String tokenId) throws InternalServerErrorException {

        SSOToken ssoToken;
        try {
            if (tokenId == null) {
                DEBUG.error("Invalid Token Id");
                throw new InternalServerErrorException("Invalid Token Id");
            }
            SSOTokenManager mgr = SSOTokenManager.getInstance();
            ssoToken = mgr.createSSOToken(tokenId);
        } catch (SSOException ex) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("result", "Token has expired");
            DEBUG.error("Token has expired");
            return new JsonValue(map);
        }

        if (ssoToken != null) {
            try {
                AuthUtils.logout(ssoToken.getTokenID().toString(), null, null);
            } catch (SSOException e) {
                DEBUG.error("Error logging out", e);
                throw new InternalServerErrorException("Error logging out", e);
            }
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", "Successfully logged out");
        DEBUG.message("Successfully logged out");
        return new JsonValue(map);
    }

    /**
     * Queries the session resources using one of the predefined query filters.
     *
     * all - (default) will query all Sessions across all servers.
     * list - will list the available servers which is useful for the next query
     * [server-id] - will list the available Sessions on the named server.
     *
     * @param context {@inheritDoc}
     * @param request {@inheritDoc}
     * @param handler {@inheritDoc}
     */
    public void queryCollection(ServerContext context, QueryRequest request, QueryResultHandler handler) {
        String id = request.getQueryId();

        if (KEYWORD_LIST.equals(id)) {
            Collection<String> servers = generateListServers();
            handler.handleResource(new Resource(KEYWORD_LIST, "0", new JsonValue(servers)));
        } else {
            Collection<SessionInfo> sessions;

            if (KEYWORD_ALL.equals(id)) {
                sessions = generateAllSessions();
            } else {
                sessions = generateNamedServerSession(id);
            }

            for (SessionInfo session : sessions) {

                int timeleft = convertTimeLeft(session.timeleft);
                String username = (String) session.properties.get("UserId");

                Map<String, Object> map = new HashMap<String, Object>();
                map.put(HEADER_USER_ID, username);
                map.put(HEADER_TIME_REMAINING, timeleft);

                handler.handleResource(new Resource("Sessions", "0", new JsonValue(map)));
            }
        }

        handler.handleResult(new QueryResult());
    }

    /**
     * Perform a read operation against a named session.
     *
     * {@inheritDoc}
     */
    public void readInstance(ServerContext context, String id, ReadRequest request, ResultHandler<Resource> handler) {
        handler.handleError(new NotSupportedException("Not implemented for this Resource"));
    }

    /**
     * @param serverId Server to query.
     * @return A non null collection of SessionInfos from the named server.
     */
    private Collection<SessionInfo> generateNamedServerSession(String serverId) {
        List<String> serverList = Arrays.asList(new String[]{serverId});
        Collection<SessionInfo> sessions = queryManager.getAllSessions(serverList);
        return sessions;
    }


    /**
     * @return A non null collection of SessionInfo instances queried across all servers.
     */
    private Collection<SessionInfo> generateAllSessions() {
        Collection<SessionInfo> sessions = queryManager.getAllSessions(getAllServerIds());
        return sessions;
    }


    /**
     * @return Returns a JSON Resource which defines the available servers.
     */
    private Collection<String> generateListServers() {
        return getAllServerIds();
    }

    /**
     * Internal function for converting time in seconds to minutes.
     *
     * @param timeleft Non null string value of time in seconds.
     * @return The parsed time.
     */
    private static int convertTimeLeft(String timeleft) {
        float seconds = Long.parseLong(timeleft);
        float mins = seconds / 60;
        return Math.round(mins);
    }

    /**
     * {@inheritDoc}
     */
    public void createInstance(ServerContext ctx, CreateRequest request, ResultHandler<Resource> handler) {
        RestUtils.generateUnsupportedOperation(handler);
    }

    /**
     * {@inheritDoc}
     */
    public void deleteInstance(ServerContext ctx, String resId, DeleteRequest request,
            ResultHandler<Resource> handler) {
        RestUtils.generateUnsupportedOperation(handler);
    }

    /**
     * {@inheritDoc}
     */
    public void patchInstance(ServerContext ctx, String resId, PatchRequest request,
            ResultHandler<Resource> handler) {
        RestUtils.generateUnsupportedOperation(handler);
    }

    /**
     * {@inheritDoc}
     */
    public void updateInstance(ServerContext ctx, String resId, UpdateRequest request,
            ResultHandler<Resource> handler) {
        RestUtils.generateUnsupportedOperation(handler);
    }
}
