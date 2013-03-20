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
 * Copyright 2013 ForgeRock Inc.
 */

package org.forgerock.openam.forgerockrest.authn.callbackhandlers;

import com.sun.identity.authentication.spi.HttpCallback;
import com.sun.identity.shared.debug.Debug;
import org.forgerock.openam.forgerockrest.authn.HttpMethod;
import org.forgerock.openam.forgerockrest.authn.exceptions.RestAuthException;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Defines methods to update a HttpCallback from the headers and request of a Rest call and methods to convert a
 * Callback to and from a JSON representation.
 */
public class RestAuthHttpCallbackHandler extends AbstractRestAuthCallbackHandler<HttpCallback>
        implements RestAuthCallbackHandler<HttpCallback> {

    private static final Debug DEBUG = Debug.getInstance("amIdentityServices");

    private static final String CALLBACK_NAME = "HttpCallback";

    private static final String IWA_FAILED = "iwa-failed";

    /**
     * Checks the request for the presence of a header with the Authorization Header as define in the HttpCallBack,
     * if present and not an empty string then sets this on the Callback and returns true. Otherwise does nothing and
     * returns false.
     *
     * {@inheritDoc}
     */
    public boolean updateCallbackFromRequest(HttpHeaders headers, HttpServletRequest request,
            HttpServletResponse response, JSONObject postBody, HttpCallback callback, HttpMethod httpMethod) throws
            RestAuthCallbackHandlerResponseException {

        String httpAuthorization = request.getHeader(callback.getAuthorizationHeader());

        if (httpAuthorization == null || "".equals(httpAuthorization)) {
            DEBUG.message("Authorization Header not set in request.");

            try {
                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("failure", true);
                jsonResponse.put("reason", IWA_FAILED);
                Map<String, String> responseHeaders = new HashMap<String, String>();
                responseHeaders.put("WWW-Authenticate", "Negotiate");
                throw new RestAuthCallbackHandlerResponseException(Response.Status.UNAUTHORIZED,
                        responseHeaders, jsonResponse);
            } catch (JSONException e) {
                throw new RestAuthException(Response.Status.INTERNAL_SERVER_ERROR,
                        "Could not generate IWA response");
            }
        }

        callback.setAuthorization(httpAuthorization);
        return true;
    }

    /**
     * This method will never be called as the <code>updateCallbackFromRequest</code> method from
     * <code>AbstractRestAuthCallbackHandler</code> has been overridden.
     *
     * {@inheritDoc}
     */
    boolean doUpdateCallbackFromRequest(HttpHeaders headers, HttpServletRequest request, HttpServletResponse response,
            JSONObject postBody, HttpCallback callback) throws RestAuthCallbackHandlerResponseException {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public HttpCallback handle(HttpHeaders headers, HttpServletRequest request, HttpServletResponse response,
            JSONObject postBody, HttpCallback originalCallback) throws JSONException {
        if (isJsonAttributePresent(postBody, "reason") && postBody.getString("reason").equals(IWA_FAILED)) {
            request.setAttribute(IWA_FAILED, true);
        }
        return originalCallback;
    }

    /**
     * {@inheritDoc}
     */
    public String getCallbackClassName() {
        return CALLBACK_NAME;
    }

    /**
     * {@inheritDoc}
     */
    public JSONObject convertToJson(HttpCallback callback, int index) {
        throw new RestAuthException(Response.Status.BAD_REQUEST, new UnsupportedOperationException(
                "HttpCallback Authorization Header must be specified in the initial request. Cannot be converted into"
                        + " a JSON representation."));
    }

    /**
     * {@inheritDoc}
     */
    public HttpCallback convertFromJson(HttpCallback callback, JSONObject jsonCallback) {
        throw new RestAuthException(Response.Status.BAD_REQUEST, new UnsupportedOperationException(
                "HttpCallback Authorization Header must be specified in the initial request. Cannot be converted from"
                        + " a JSON representation."));
    }
}
