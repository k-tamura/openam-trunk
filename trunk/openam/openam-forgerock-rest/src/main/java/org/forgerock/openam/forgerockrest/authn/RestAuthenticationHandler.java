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

package org.forgerock.openam.forgerockrest.authn;

import com.sun.identity.authentication.spi.AuthLoginException;
import com.sun.identity.authentication.spi.PagePropertiesCallback;
import com.sun.identity.shared.debug.Debug;
import com.sun.identity.shared.locale.L10NMessageImpl;
import org.forgerock.json.fluent.JsonException;
import org.forgerock.json.fluent.JsonValue;
import org.forgerock.json.jwt.JwtBuilder;
import org.forgerock.json.jwt.SignedJwt;
import org.forgerock.openam.forgerockrest.authn.callbackhandlers.RestAuthCallbackHandlerResponseException;
import org.forgerock.openam.forgerockrest.authn.core.AuthIndexType;
import org.forgerock.openam.forgerockrest.authn.core.AuthenticationContext;
import org.forgerock.openam.forgerockrest.authn.core.HttpMethod;
import org.forgerock.openam.forgerockrest.authn.core.LoginAuthenticator;
import org.forgerock.openam.forgerockrest.authn.core.LoginConfiguration;
import org.forgerock.openam.forgerockrest.authn.core.LoginProcess;
import org.forgerock.openam.forgerockrest.authn.exceptions.RestAuthErrorCodeException;
import org.forgerock.openam.forgerockrest.authn.exceptions.RestAuthException;
import org.forgerock.openam.utils.JsonObject;
import org.forgerock.openam.utils.JsonValueBuilder;

import javax.inject.Inject;
import javax.security.auth.callback.Callback;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.SignatureException;

/**
 * Handles the initial authenticate and subsequent callback submit RESTful calls.
 */
public class RestAuthenticationHandler {

    private static final Debug DEBUG = Debug.getInstance("amIdentityServices");

    private final LoginAuthenticator loginAuthenticator;
    private final RestAuthCallbackHandlerManager restAuthCallbackHandlerManager;
    private final JwtBuilder jwtBuilder;
    private final AMAuthErrorCodeResponseStatusMapping amAuthErrorCodeResponseStatusMapping;
    private final AuthIdHelper authIdHelper;

    /**
     * Constructs an instance of the RestAuthenticationHandler.
     *
     * @param loginAuthenticator An instance of the LoginAuthenticator.
     * @param restAuthCallbackHandlerManager An instance of the RestAuthCallbackHandlerManager.
     * @param jwtBuilder An instance of the JwtBuilder.
     * @param amAuthErrorCodeResponseStatusMapping An instance of the AMAuthErrorCodeResponseStatusMapping.
     * @param authIdHelper An instance of the AuthIdHelper.
     */
    @Inject
    public RestAuthenticationHandler(LoginAuthenticator loginAuthenticator,
                                     RestAuthCallbackHandlerManager restAuthCallbackHandlerManager, JwtBuilder jwtBuilder,
                                     AMAuthErrorCodeResponseStatusMapping amAuthErrorCodeResponseStatusMapping,
                                     AuthIdHelper authIdHelper) {
        this.loginAuthenticator = loginAuthenticator;
        this.restAuthCallbackHandlerManager = restAuthCallbackHandlerManager;
        this.jwtBuilder = jwtBuilder;
        this.amAuthErrorCodeResponseStatusMapping = amAuthErrorCodeResponseStatusMapping;
        this.authIdHelper = authIdHelper;
    }

    /**
     * Handles authentication requests from HTTP both GET and POST. Will then either create or retrieve the Login
     * Process, dependent on if the request is a new authentication request or a continuation of one.
     *
     * @param headers The HttpHeaders of the request.
     * @param request The HttpServletRequest.
     * @param response The HttpServletResponse.
     * @param authIndexType The authentication index type.
     * @param indexValue The authentication index value.
     * @param sessionUpgradeSSOTokenId The SSO Token Id of the user's current session, null if not performing a session
     *                                 upgrade.
     * @param httpMethod The HTTP method used to make the request.
     * @return The Response of the authentication request.
     */
    public Response authenticate(HttpHeaders headers, HttpServletRequest request, HttpServletResponse response,
                                 String authIndexType, String indexValue, String sessionUpgradeSSOTokenId, HttpMethod httpMethod) {
        return authenticate(headers, request, response, null, authIndexType, indexValue, sessionUpgradeSSOTokenId,
                httpMethod);
    }

    /**
     * Handles authentication requests from HTTP POST. Will then either create or retrieve the Login Process,
     * dependent on if the request is a new authentication request or a continuation of one.
     *
     * @param headers The HttpHeaders of the request.
     * @param request The HttpServletRequest.
     * @param response The HttpServletResponse.
     * @param postBody The post body of the request.
     * @param sessionUpgradeSSOTokenId The SSO Token Id of the user's current session, null if not performing a session
     *                                 upgrade.
     * @return The Response of the authentication request.
     */
    public Response authenticate(HttpHeaders headers, HttpServletRequest request, HttpServletResponse response,
                                 String postBody, String sessionUpgradeSSOTokenId) {
        return authenticate(headers, request, response, JsonValueBuilder.toJsonValue(postBody), null, null,
                sessionUpgradeSSOTokenId, HttpMethod.POST);
    }

    /**
     * Handles either the creation or retrieval of the Login Process, dependent on if the request is a new
     * authentication request or a continuation of one.
     *
     * @param headers The HttpHeaders of the request.
     * @param request The HttpServletRequest.
     * @param response The HttpServletResponse.
     * @param postBody The post body of the request.
     * @param authIndexType The authentication index type.
     * @param indexValue The authentication index value.
     * @param sessionUpgradeSSOTokenId The SSO Token Id of the user's current session, null if not performing a session
     *                                 upgrade.
     * @param httpMethod The HTTP method used to make the request.
     * @return The Response of the authentication request.
     */
    private Response authenticate(HttpHeaders headers, HttpServletRequest request, HttpServletResponse response,
                                  JsonValue postBody, String authIndexType, String indexValue, String sessionUpgradeSSOTokenId,
                                  HttpMethod httpMethod) {

        Response.ResponseBuilder responseBuilder;
        try {
            AuthIndexType indexType = getAuthIndexType(authIndexType);

            String authId;
            String sessionId = null;
            if (postBody != null && (authId = postBody.get("authId").asString()) != null) {
                SignedJwt jwt = (SignedJwt) jwtBuilder.recontructJwt(authId);
                sessionId = jwt.getJwt().getContent("sessionId", String.class);
                String authIndexTypeString = jwt.getJwt().getContent("authIndexType", String.class);
                indexType = AuthIndexType.getAuthIndexType(authIndexTypeString);
                indexValue = jwt.getJwt().getContent("authIndexValue", String.class);
            }

            LoginConfiguration loginConfiguration = new LoginConfiguration()
                    .httpRequest(request)
                    .indexType(indexType)
                    .indexValue(indexValue)
                    .sessionId(sessionId)
                    .sessionUpgrade(sessionUpgradeSSOTokenId);

            LoginProcess loginProcess = loginAuthenticator.getLoginProcess(loginConfiguration);


            if (postBody != null && (authId = postBody.get("authId").asString()) != null) {
                authIdHelper.verifyAuthId(loginProcess, authId);
            }

            responseBuilder = processAuthentication(headers, request, response, postBody, httpMethod, loginProcess,
                    loginConfiguration);

        } catch (RestAuthException e) {
            DEBUG.error(e.getMessage());
            return e.getResponse();
        } catch (L10NMessageImpl e) {
            DEBUG.error(e.getMessage(), e);
            return new RestAuthException(Response.Status.UNAUTHORIZED, e).getResponse();
        } catch (JsonException e)  {
            DEBUG.error(e.getMessage(), e);
            return new RestAuthException(Response.Status.INTERNAL_SERVER_ERROR, e).getResponse();
        } catch (SignatureException e) {
            DEBUG.error(e.getMessage(), e);
            return new RestAuthException(Response.Status.INTERNAL_SERVER_ERROR, e).getResponse();
        } catch (AuthLoginException e) {
            DEBUG.error(e.getMessage(), e);
            return new RestAuthException(amAuthErrorCodeResponseStatusMapping.getAuthLoginExceptionResponseStatus(
                    e.getErrorCode()), e).getResponse();
        } catch (RestAuthCallbackHandlerResponseException e) {
            // Construct a Response object from the exception.
            responseBuilder = Response.status(e.getResponseStatus());
            for (String key : e.getResponseHeaders().keySet()) {
                responseBuilder.header(key, e.getResponseHeaders().get(key));
            }
            responseBuilder.entity(e.getJsonResponse().toString());
        }

        responseBuilder.header("Cache-control", "no-cache");
        responseBuilder.type(MediaType.APPLICATION_JSON_TYPE);
        return responseBuilder.build();
    }

    /**
     * Using the given LoginProcess will process the authentication by getting the required callbacks and either
     * completing and submitting them or sending the requirements back to the client as JSON. If the authentication
     * process has completed it will then check the completion status and will either return an error or the SSO Token
     * Id to the client.
     *
     * @param headers The HttpHeaders of the request.
     * @param request The HttpServletRequest.
     * @param response The HttpServletResponse.
     * @param postBody The post body of the request.
     * @param httpMethod The HTTP method used to make the request.
     * @param loginProcess The LoginProcess used to track the login.
     * @param loginConfiguration The LoginConfiguration used to configure the login process.
     * @return A ResponseBuilder which contains the contents of the response to return to the client.
     * @throws RestAuthCallbackHandlerResponseException If there is a problem handling the callbacks.
     * @throws AuthLoginException If there is a problem submitting the callbacks.
     * @throws SignatureException If there is a problem creating the JWT to use in the response to the client.
     */
    private Response.ResponseBuilder processAuthentication(HttpHeaders headers, HttpServletRequest request,
                                                           HttpServletResponse response, JsonValue postBody, HttpMethod httpMethod, LoginProcess loginProcess,
                                                           LoginConfiguration loginConfiguration) throws RestAuthCallbackHandlerResponseException,
            AuthLoginException, SignatureException {

        Response.ResponseBuilder responseBuilder;

        switch (loginProcess.getLoginStage()) {
            case REQUIREMENTS_WAITING: {
                Callback[] callbacks = loginProcess.getCallbacks();
                PagePropertiesCallback pagePropertiesCallback = loginProcess.getPagePropertiesCallback();

                JsonValue jsonCallbacks = null;
                if (postBody == null) {
                    jsonCallbacks = restAuthCallbackHandlerManager.handleCallbacks(headers, request, response,
                            postBody, callbacks, httpMethod);
                } else if (!postBody.get("callbacks").isNull()) {
                    JsonValue jCallbacks = postBody.get("callbacks");

                    callbacks = restAuthCallbackHandlerManager.handleJsonCallbacks(callbacks, jCallbacks);
                } else {
                    callbacks = restAuthCallbackHandlerManager.handleResponseCallbacks(headers, request, response,
                            callbacks, postBody);
                }


                if (jsonCallbacks != null && jsonCallbacks.size() > 0) {

                    JsonObject jsonResponseObject = JsonValueBuilder.jsonValue();

                    String authId;
                    if (postBody == null || (authId = postBody.get("authId").asString()) == null) {
                        authId = authIdHelper.createAuthId(loginConfiguration, loginProcess.getAuthContext());
                    }
                    jsonResponseObject.put("authId", authId);
                    if (pagePropertiesCallback != null) {
                        jsonResponseObject.put("template", pagePropertiesCallback.getTemplateName());
                        String moduleName = pagePropertiesCallback.getModuleName();
                        String state = pagePropertiesCallback.getPageState();
                        jsonResponseObject.put("stage", moduleName + state);
                    }
                    jsonResponseObject.put("callbacks", jsonCallbacks);

                    JsonValue jsonValue = jsonResponseObject.build();

                    responseBuilder = Response.status(Response.Status.OK);
                    responseBuilder.entity(jsonValue.toString());

                } else {
                    loginProcess = loginProcess.next(callbacks);
                    responseBuilder = processAuthentication(headers, request, response, null, httpMethod, loginProcess,
                            loginConfiguration);
                }
                break;
            }
            case COMPLETE: {
                if (loginProcess.isSuccessful()) {
                    // send token to client
                    JsonObject jsonResponseObject = JsonValueBuilder.jsonValue();

                    String tokenId = loginProcess.getAuthContext().getSSOToken().getTokenID().toString();
                    jsonResponseObject.put("tokenId", tokenId);

                    JsonValue jsonValue = jsonResponseObject.build();

                    responseBuilder = Response.status(Response.Status.OK);
                    responseBuilder.entity(jsonValue.toString());

                } else {
                    // send Error to client
                    AuthenticationContext authContext = loginProcess.getAuthContext();
                    String errorCode = authContext.getErrorCode();
                    String errorMessage = authContext.getErrorMessage();

                    throw new RestAuthErrorCodeException(errorCode, errorMessage);
                }
                break;
            }
            default: {
                responseBuilder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
                JsonValue jsonValue = JsonValueBuilder.jsonValue().put("errorMessage", "Invalid Login Stage").build();
                responseBuilder.entity(jsonValue.toString());
            }
        }

        return responseBuilder;
    }

    /**
     * Gets the AuthIndexType for the given authentication index type string.
     *
     * @param authIndexType The authentication index string.
     * @return The AuthIndexType enum.
     */
    private AuthIndexType getAuthIndexType(String authIndexType) {

        try {
            return AuthIndexType.getAuthIndexType(authIndexType);
        } catch (IllegalArgumentException e) {
            DEBUG.message("Unknown Authentication Index Type, " + authIndexType);
            throw new RestAuthException(Response.Status.INTERNAL_SERVER_ERROR, "Unknown Authentication Index Type, "
                    + authIndexType);
        }
    }
}
