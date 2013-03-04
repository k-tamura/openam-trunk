/**
 * Copyright 2013 ForgeRock, Inc.
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

import com.iplanet.dpro.session.share.SessionInfo;
import com.iplanet.services.naming.WebtopNaming;
import com.sun.identity.sm.OrganizationConfigManager;
import edu.emory.mathcs.backport.java.util.Arrays;
import org.forgerock.json.fluent.JsonValue;
import org.forgerock.json.resource.ActionRequest;
import org.forgerock.json.resource.CollectionResourceProvider;
import org.forgerock.json.resource.CreateRequest;
import org.forgerock.json.resource.DeleteRequest;
import org.forgerock.json.resource.NotSupportedException;
import org.forgerock.json.resource.PatchRequest;
import org.forgerock.json.resource.QueryRequest;
import org.forgerock.json.resource.QueryResult;
import org.forgerock.json.resource.QueryResultHandler;
import org.forgerock.json.resource.ReadRequest;
import org.forgerock.json.resource.Resource;
import org.forgerock.json.resource.ResultHandler;
import org.forgerock.json.resource.Router;
import org.forgerock.json.resource.RoutingMode;
import org.forgerock.json.resource.ServerContext;
import org.forgerock.json.resource.UpdateRequest;
import org.forgerock.openam.forgerockrest.session.query.SessionQueryFactory;
import org.forgerock.openam.forgerockrest.session.query.SessionQueryManager;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static final String KEYWORD_ALL = "all";
    public static final String KEYWORD_LIST = "list";

    public static final String HEADER_USER_ID = "userid";
    public static final String HEADER_TIME_REMAINING = "timeleft";

    private SessionQueryManager queryManager;

    public SessionResource(SessionQueryManager queryManager) {
        this.queryManager = queryManager;
    }

    /**
     * Applies the routing to the Router that this class supports.
     *
     * @param ocm Configuration required for organisation name.
     * @param router Router to apply changes to.
     */
    public static void applyRouting(OrganizationConfigManager ocm, Router router) {
        String orgName = ocm.getOrganizationName();
        if (!orgName.endsWith("/")) {
            orgName += "/";
        }

        SessionQueryManager sessionQueryManager = new SessionQueryManager(new SessionQueryFactory());
        router.addRoute(RoutingMode.STARTS_WITH, orgName + "sessions", new SessionResource(sessionQueryManager));
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
     * Currently unimplemented.
     *
     * @param context {@inheritDoc}
     * @param request {@inheritDoc}
     * @param handler {@inheritDoc}
     */
    public void actionCollection(ServerContext context, ActionRequest request, ResultHandler<JsonValue> handler) {
        handler.handleError(new NotSupportedException("Not implemented for this Resource"));
    }


    /**
     * Currently unimplemented.
     *
     * @param context {@inheritDoc}
     * @param request {@inheritDoc}
     * @param handler {@inheritDoc}
     */
    public void actionInstance(ServerContext context, String resourceId, ActionRequest request, ResultHandler<JsonValue> handler) {
        handler.handleError(new NotSupportedException("Not implemented for this Resource"));
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

    private NotSupportedException generateException(String type) {
        return new NotSupportedException(type + " are not supported for this Resource");
    }

    /**
     * {@inheritDoc}
     */
    public void createInstance(ServerContext ctx, CreateRequest request, ResultHandler<Resource> handler) {
        handler.handleError(generateException("Creates"));
    }

    /**
     * {@inheritDoc}
     */
    public void deleteInstance(ServerContext ctx, String resId, DeleteRequest request,
            ResultHandler<Resource> handler) {
        handler.handleError(generateException("Deletes"));
    }

    /**
     * {@inheritDoc}
     */
    public void patchInstance(ServerContext ctx, String resId, PatchRequest request,
            ResultHandler<Resource> handler) {
        handler.handleError(generateException("Patches"));
    }

    /**
     * {@inheritDoc}
     */
    public void updateInstance(ServerContext ctx, String resId, UpdateRequest request,
            ResultHandler<Resource> handler) {
        handler.handleError(generateException("Updates"));
    }
}
