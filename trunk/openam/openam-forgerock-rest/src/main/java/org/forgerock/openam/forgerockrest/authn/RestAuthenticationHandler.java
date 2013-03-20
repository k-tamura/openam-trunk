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

import com.sun.identity.authentication.AuthContext;
import com.sun.identity.authentication.spi.AuthLoginException;
import com.sun.identity.authentication.spi.PagePropertiesCallback;
import com.sun.identity.shared.debug.Debug;
import com.sun.identity.shared.locale.L10NMessageImpl;
import org.forgerock.openam.forgerockrest.authn.callbackhandlers.RestAuthCallbackHandlerResponseException;
import org.forgerock.openam.forgerockrest.authn.exceptions.RestAuthErrorCodeException;
import org.forgerock.openam.forgerockrest.authn.exceptions.RestAuthException;
import org.forgerock.openam.forgerockrest.jwt.JwsAlgorithm;
import org.forgerock.openam.forgerockrest.jwt.JwtBuilder;
import org.forgerock.openam.forgerockrest.jwt.SignedJwt;
import org.forgerock.openam.utils.AMKeyProvider;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.security.auth.callback.Callback;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles the initial authenticate and subsequent callback submit RESTful calls.
 */
public class RestAuthenticationHandler {

    private static final Debug DEBUG = Debug.getInstance("amIdentityServices");

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String KEYSTORE_ALIAS = "org.forgerock.keystore.alias";

    private final AuthContextStateMap authContextStateMap;
    private final AMKeyProvider amKeyProvider;
    private final RestAuthCallbackHandlerManager restAuthCallbackHandlerManager;
    private final JwtBuilder jwtBuilder;
    private final SystemPropertiesManagerWrapper systemPropertiesManager;

    /**
     * Constructs an instance of the RestAuthenticationHandler.
     *
     * @param authContextStateMap An instance of the AuthContextStateMap.
     * @param amKeyProvider An instance of the AMKeyProvider.
     * @param restAuthCallbackHandlerManager An instance of the RestAuthCallbackHandlerManager.
     * @param jwtBuilder An instance of the JwtBuilder.
     */
    @Inject
    public RestAuthenticationHandler(AuthContextStateMap authContextStateMap, AMKeyProvider amKeyProvider,
            RestAuthCallbackHandlerManager restAuthCallbackHandlerManager, JwtBuilder jwtBuilder,
            SystemPropertiesManagerWrapper systemPropertiesManager) {
        this.authContextStateMap = authContextStateMap;
        this.amKeyProvider = amKeyProvider;
        this.restAuthCallbackHandlerManager = restAuthCallbackHandlerManager;
        this.jwtBuilder = jwtBuilder;
        this.systemPropertiesManager = systemPropertiesManager;
    }

    /**
     * Handles the initial RESTful call from clients to authenticate. Starts the login process and either returns an
     * SSOToken on successful authentication or a number of Callbacks needing to be completed before authentication
     * can proceed or an exception if any problems occurred whilst trying to authenticate.
     *
     * @param headers The HttpHeaders of the RESTful call.
     * @param request The HttpServletRequest of the RESTful call.
     * @param realm The realm to authenticate in from the url parameters.
     * @param authIndexType The authentication index type from the url parameters.
     * @param authIndexValue The authentication index value from the url parameters.
     * @param httpMethod The Http Method used to initiate this request.
     * @return A response to be sent back to the client. The response will contain either a JSON object containing the
     * SSOToken id from a successful authentication, a JSON object containing a number of Callbacks for the client to
     * complete and return or a JSON object containing an exception message.
     */
    public Response authenticate(HttpHeaders headers, HttpServletRequest request, HttpServletResponse response,
            String realm, String authIndexType, String authIndexValue, HttpMethod httpMethod) {

        AuthContext.IndexType indexType = getAuthIndexType(authIndexType);

        if (realm == null) {
            realm = "/";
        }

        Response.ResponseBuilder responseBuilder;
        try {
            AuthContext authContext = startAuthenticationProcess(realm, indexType, authIndexValue, request, response);

            JSONObject jsonResponseObject = processAuthContextRequirements(headers, request, response, authContext,
                    indexType, authIndexValue, httpMethod);

            responseBuilder = Response.status(Response.Status.OK);
            responseBuilder.header("Cache-control", "no-cache");
            responseBuilder.type(MediaType.APPLICATION_JSON_TYPE);
            responseBuilder.entity(jsonResponseObject.toString());

        } catch (RestAuthException e) {
            DEBUG.error(e.getMessage());
            return e.getResponse();
        } catch (L10NMessageImpl e) {
            DEBUG.error(e.getMessage(), e);
            return new RestAuthException(Response.Status.UNAUTHORIZED, e).getResponse();
        } catch (JSONException e) {
            DEBUG.error(e.getMessage(), e);
            return new RestAuthException(Response.Status.BAD_REQUEST, e).getResponse();
        } catch (SignatureException e) {
            DEBUG.error(e.getMessage(), e);
            return new RestAuthException(Response.Status.INTERNAL_SERVER_ERROR, e).getResponse();
        } catch (AuthLoginException e) {
            DEBUG.error(e.getMessage(), e);
            return new RestAuthException(Response.Status.UNAUTHORIZED, e).getResponse();
        } catch (RestAuthCallbackHandlerResponseException e) {
            // Construct a Response object from the exception.
            responseBuilder = Response.status(e.getResponseStatus());
            responseBuilder.type(MediaType.APPLICATION_JSON_TYPE);
            for (String key : e.getResponseHeaders().keySet()) {
                responseBuilder.header(key, e.getResponseHeaders().get(key));
            }
            responseBuilder.entity(e.getJsonResponse().toString());
        }

        return responseBuilder.build();
    }

    /**
     * Starts the authentication process by creating the AuthContext and calling login() on it.
     *
     * @param realm The realm.
     * @param indexType The IndexType.
     * @param authIndexValue The IndexType value.
     * @param request The HttpServletRequest.
     * @param response The HttpServletResponse.
     * @return The AuthContext.
     * @throws AuthLoginException If a problem occurred when creating the AuthContext or calling the login() method.
     */
    private AuthContext startAuthenticationProcess(String realm, AuthContext.IndexType indexType,
            String authIndexValue, HttpServletRequest request, HttpServletResponse response) throws
            AuthLoginException {

        AuthContext authContext = createAuthContext(realm);

        if (indexType != null) {
            authContext.login(indexType, authIndexValue, null, request, response);
        } else {
            authContext.login(request, response);
        }
        return authContext;
    }

    /**
     * Determines the AuthIndexType based from a String representation of the index type.
     *
     * @param authIndexType A String representing one of the possible AuthIndexTypes.
     * @return The corresponding AuthIndexType.
     */
    private AuthContext.IndexType getAuthIndexType(String authIndexType) {

        AuthContext.IndexType indexType = null;

        if (authIndexType == null) {
            return indexType;
        }

        try {
            AuthIndexType authIndex = AuthIndexType.valueOf(authIndexType.toUpperCase());
            return authIndex.getIndexType();

        } catch (IllegalArgumentException e) {
            DEBUG.message("Unknown Authentication Index Type, " + authIndexType);
            throw new RestAuthException(Response.Status.INTERNAL_SERVER_ERROR, "Unknown Authentication Index Type, "
                    + authIndexType);
        }
    }

    /**
     * Calls <code>processAuthContextRequirements(HttpHeaders headers, HttpServletRequest request,
     * AuthContext authContext, String authId)</code> with authId set to null,
     * as an authId JWT has not been generated yet.
     *
     * @param headers The HttpHeaders of the RESTful call.
     * @param request The HttpServletRequest of the RESTful call.
     * @param response The HttpServletResponse of the RESTful call.
     * @param authContext The AuthContext for the authentication process.
     * @param indexType The authentication index type from the url parameters.
     * @param authIndexValue The authentication index value from the url parameters.
     * @param httpMethod The Http Method used to initiate this request.
     * @return A JSON object of either an array of Callbacks to be returned to the client or the SSOToken id.
     * @throws SignatureException If there is a problem signing the authId JWT.
     * @throws JSONException If there is a syntax problem when creating the JSON object.
     * @throws L10NMessageImpl If there is a problem getting the SSOToken from the AuthContext.
     * @throws RestAuthCallbackHandlerResponseException If one of the CallbackHandlers has its own response to be sent.
     */
    private JSONObject processAuthContextRequirements(HttpHeaders headers, HttpServletRequest request,
            HttpServletResponse response, AuthContext authContext, AuthContext.IndexType indexType,
            String authIndexValue, HttpMethod httpMethod) throws SignatureException, JSONException, L10NMessageImpl,
            RestAuthCallbackHandlerResponseException {
        return processAuthContextRequirements(headers, request, response, null, authContext, null, indexType,
                authIndexValue, httpMethod);
    }

    /**
     * Processes the AuthContext's requirements (Callbacks). If any Callbacks need to be sent back to the client to
     * be completed, the method will return a JSONObject containing the array of Callbacks and the authId JWT to be
     * returned to continue the authentication process.
     * </p>
     * <code>
     * {
     *     authId : "XXXXXX",
     *     callbacks : [
     *         {
     *             type : "NameCallback",
     *             output : [
     *                 {
     *                     name : "prompt",
     *                     value : "Enter User Name:"
     *                 }
     *             ],
     *             input : [
     *                 {
     *                     key : "name",
     *                     value : ""
     *                 }
     *             ]
     *         },{
     *             type : "PasswordCallback",
     *             output : [
     *                 {
     *                     name : "prompt",
     *                     value : "Enter Password:"
     *                 }
     *             ],
     *             input : [
     *                 {
     *                     key : "password",
     *                     value : ""
     *                 }
     *             ]
     *         }
     *     ]
     * }</p>
     * </code>
     *
     * If all of the AuthContext's requirements are met then either
     * a JSON object with the SSOToken id will be returned or an exception will be thrown if the authentication failed.
     *
     * @param headers The HttpHeaders of the RESTful call.
     * @param request The HttpServletRequest of the RESTful call.
     * @param response The HttpServletResponse of the RESTful call.
     * @param authContext The AuthContext for the authentication process.
     * @param authId The authId JWT to store the AuthContext. Null if the the AuthContext has not been stored before.
     * @param indexType The authentication index type from the url parameters.
     * @param authIndexValue The authentication index value from the url parameters.
     * @param httpMethod The Http Method used to initiate this request.
     * @return A JSON object of either an array of Callbacks to be returned to the client or the SSOToken id.
     * @throws SignatureException If there is a problem signing the authId JWT.
     * @throws JSONException If there is a syntax problem when creating the JSON object.
     * @throws L10NMessageImpl If there is a problem getting the SSOToken from the AuthContext.
     * @throws RestAuthCallbackHandlerResponseException If one of the CallbackHandlers has its own response to be sent.
     */
    private JSONObject processAuthContextRequirements(HttpHeaders headers, HttpServletRequest request,
            HttpServletResponse response, JSONObject postBody, AuthContext authContext, String authId, AuthContext.IndexType indexType,
            String authIndexValue, HttpMethod httpMethod) throws SignatureException, JSONException, L10NMessageImpl,
            RestAuthCallbackHandlerResponseException {

        JSONObject jsonResponseObject = new JSONObject();

        if (authContext.hasMoreRequirements()) {

            Callback[] callbacks = authContext.getRequirements();

            PagePropertiesCallback pagePropertiesCallback = getPagePropertiesCallback(authContext);

            JSONArray jsonCallbacks = null;
            try {
                jsonCallbacks = restAuthCallbackHandlerManager.handleCallbacks(headers, request, response,
                        postBody, callbacks, httpMethod);
            } catch (RestAuthCallbackHandlerResponseException e) {
                // Include the authId in the JSON response.
                if (authId == null) {
                    authId = createAuthId(indexType, authIndexValue, authContext);
                }
                e.getJsonResponse().put("authId", authId);
                e.getResponseHeaders().put("Cache-control", "no-cache");
                throw e;
            }

            if (jsonCallbacks.length() > 0) {
                //callbacks to send back
                if (authId == null) {
                    authId = createAuthId(indexType, authIndexValue, authContext);
                }
                jsonResponseObject.put("authId", authId);
                if (pagePropertiesCallback != null) {
                    jsonResponseObject.put("template", pagePropertiesCallback.getTemplateName());
                    String moduleName = pagePropertiesCallback.getModuleName();
                    String state = pagePropertiesCallback.getPageState();
                    jsonResponseObject.put("stage", moduleName + state);
                }
                jsonResponseObject.put("callbacks", jsonCallbacks);

                return jsonResponseObject;

            } else {
                // handled callbacks internally
                authContext.submitRequirements(callbacks);
                return processAuthContextRequirements(headers, request, response, postBody, authContext, authId,
                        indexType, authIndexValue, httpMethod);
            }

        } else {
            // no more reqs so check auth status
            return handleAuthenticationComplete(authContext);
        }
    }

    /**
     * Creates the authId JWT.
     *
     * @param indexType The IndexType which will be included in the payload of the JWT.
     * @param authIndexValue The IndexType value which wil be included in the payload of the JWT.
     * @param authContext The AuthContext.
     * @return The authId JWT.
     * @throws SignatureException If there is a problem signing the JWT.
     */
    private String createAuthId(AuthContext.IndexType indexType, String authIndexValue, AuthContext authContext)
            throws SignatureException {
        Map<String, Object> jwtValues = new HashMap<String, Object>();
        jwtValues.put("authIndexType", indexType);
        jwtValues.put("authIndexValue", authIndexValue);
        String authId = generateAuthId(jwtValues);
        authContextStateMap.addAuthContext(authId, authContext);
        return authId;
    }

    /**
     * Gets the PagePropertiesCallback from the Authentication Modules Callback array.
     *
     * @param authContext The AuthContext for this Authentication request.
     * @return The PagePropertiesCallback or null if none exists.
     */
    private PagePropertiesCallback getPagePropertiesCallback(AuthContext authContext) {

        PagePropertiesCallback pagePropertiesCallback = null;

        for (Callback callback : authContext.getRequirements(true)) {
            if (callback instanceof PagePropertiesCallback) {
                pagePropertiesCallback = (PagePropertiesCallback) callback;
                break;
            }
        }

        return pagePropertiesCallback;
    }

    /**
     * Creates an AuthContext with the given realm.
     *
     * Separated into separate protected method so can be overridden when unit testing to avoid initialising other
     * aspects of the system.
     *
     * @param realm The realm for the authentication process.
     * @return An AuthContext instance.
     * @throws AuthLoginException If there is a problem creating the AuthContext instance.
     */
    protected AuthContext createAuthContext(String realm) throws AuthLoginException {
        return new AuthContext(realm);
    }

    /**
     * Generates an JWT as an authentication id, which is unique and will never be generated again,
     * to be used to store the AuthContext instance against so that the login process can continue asynchronously.
     *
     * i.e.
     * <ol>
     *     <li>Client send authentication request</li>
     *     <li>OpenAM stores AuthContext and responds with Callbacks and authId</li>
     *     <li>Client sends Callbacks and authId</li>
     *     <li>OpenAM retrieves AuthContext using authId, submits Callbacks</li>
     *     <li>OpenAM sends SSOToken to client</li>
     * </ol>
     *
     * @param jwtValues A Map of key value pairs to be included in the JWT payload.
     * @return A JWT as an unique id that will never be generated again, to be used to store AuthContexts between
     *          requests.
     * @throws SignatureException If there is a problem signing the JWT.
     */
    private String generateAuthId(Map<String, Object> jwtValues) throws SignatureException {

        if (jwtValues == null) {
            jwtValues = new HashMap<String, Object>();
        }

        String keyStoreAlias = systemPropertiesManager.get(KEYSTORE_ALIAS);

        if (keyStoreAlias == null) {
            throw new RestAuthException(Response.Status.INTERNAL_SERVER_ERROR,
                    "Could not find Key Store with alias, " + keyStoreAlias);
        }

        PrivateKey privateKey = amKeyProvider.getPrivateKey(keyStoreAlias);

        String otk = new BigInteger(130, RANDOM).toString(32);

        String jwt = jwtBuilder.jwt()
                .header("alg", "HS256")
                .content("otk", otk)
                .content(jwtValues)
                .sign(JwsAlgorithm.HS256, privateKey)
                .build();

        return jwt;
    }

    /**
     * Verifies that the authId JWT's signature is valid.
     *
     * @param authId The JWT used to store AuthContexts between requests.
     * @throws SignatureException If there is a problem verifying the signature of the JWT or the signature is not
     * valid.
     */
    private void verifyAuthId(String authId) throws SignatureException {

        PrivateKey privateKey = amKeyProvider.getPrivateKey(systemPropertiesManager.get(KEYSTORE_ALIAS));
        X509Certificate certificate = amKeyProvider.getX509Certificate(systemPropertiesManager.get(KEYSTORE_ALIAS));

        boolean verified = ((SignedJwt) jwtBuilder.recontructJwt(authId)).verify(privateKey, certificate);
        if (!verified) {
            throw new SignatureException("AuthId JWT Signature not valid");
        }
    }

    /**
     * When there are no more Callbacks in the authentication process this method checks the status of the
     * AuthContext and if the status is "SUCCESS" then creates a JSON object with the SSOToken,
     * otherwise throws a <code>RestAuthErrorCodeException</code> with the AuthContext error message and error code.
     *
     * @param authContext The AuthContext for the authentication process.
     * @return A JSON object with the SSOToken id.
     * @throws L10NMessageImpl If there is a problem getting the SSOToken from the AuthContext.
     * @throws JSONException If there is a syntax error when setting the SSOToken id in the JSON object.
     */
    private JSONObject handleAuthenticationComplete(AuthContext authContext) throws L10NMessageImpl, JSONException {

        JSONObject jsonResponseObject = new JSONObject();

        AuthContext.Status authStatus = authContext.getStatus();

        if (AuthContext.Status.SUCCESS.equals(authStatus)) {
            DEBUG.message("Authentication succeeded");
            String tokenId = authContext.getSSOToken().getTokenID().toString();
            jsonResponseObject.put("tokenId", tokenId);
        } else {
            DEBUG.message("Authentication failed");
            String errorCode = authContext.getErrorCode();
            String errorMessage = authContext.getErrorMessage();

            throw new RestAuthErrorCodeException(errorCode, errorMessage);
        }

        return jsonResponseObject;
    }

    /**
     * Handles subsequent RESTful calls from clients submitting Callbacks for the authentication process to continue.
     * Using the message body the method continues the login process, submitting the given Callbacks and
     * then either returns an SSOToken on successful authentication or a number of additional Callbacks needing to be
     * completed before authentication can proceed or an exception if any problems occurred whilst trying to
     * authenticate.
     *
     * @param headers The HttpHeaders of the RESTful call.
     * @param request The HttpServletRequest of the RESTful call.
     * @param response The HttpServletResponse of the RESTful call.
     * @param postBody The POST body of the RESTful call.
     * @param httpMethod The Http Method used to initiate this request.
     * @return A response to be sent back to the client. The response will contain either a JSON object containing the
     * SSOToken id from a successful authentication, a JSON object containing a number of Callbacks for the client to
     * complete and return or a JSON object containing an exception message.
     */
    public Response processAuthenticationRequirements(HttpHeaders headers, HttpServletRequest request,
            HttpServletResponse response, String postBody, HttpMethod httpMethod) {

        Response.ResponseBuilder responseBuilder;
        try {
            JSONObject jsonRequestObject = new JSONObject(postBody);

            String authId = jsonRequestObject.getString("authId");
            verifyAuthId(authId);

            AuthContext authContext = authContextStateMap.getAuthContext(authId);

            if (authContext == null) {
                //try to re-create authcontext - need to be careful as could fail if have already submitted callbacks
                SignedJwt jwt = (SignedJwt) jwtBuilder.recontructJwt(authId);
                String realm = jwt.getJwt().getContent("realm", String.class);
                String authIndexType = jwt.getJwt().getContent("authIndexType", String.class);
                AuthContext.IndexType indexType = getAuthIndexType(authIndexType);
                String authIndexValue = jwt.getJwt().getContent("authIndexValue", String.class);
                authContext = startAuthenticationProcess(realm, indexType, authIndexValue, request, response);
            }

            // Check that the AuthContext is still in progress
            if (!AuthContext.Status.IN_PROGRESS.equals(authContext.getStatus())) {
                throw new RestAuthException(Response.Status.UNAUTHORIZED, "Authentication Process not valid");
            }

            Callback[] originalCallbacks = authContext.getRequirements();

            Callback[] responseCallbacks = handleCallbacks(headers, request, response, originalCallbacks,
                    jsonRequestObject);

            authContext.submitRequirements(responseCallbacks);

            JSONObject jsonResponseObject = processAuthContextRequirements(headers, request, response,
                    jsonRequestObject, authContext, authId, null, null, httpMethod);

            responseBuilder = Response.status(Response.Status.OK);
            responseBuilder.header("Cache-control", "no-cache");
            responseBuilder.type(MediaType.APPLICATION_JSON_TYPE);
            responseBuilder.entity(jsonResponseObject.toString());

        } catch (RestAuthException e) {
            DEBUG.error(e.getMessage());
            return e.getResponse();
        } catch (L10NMessageImpl e) {
            DEBUG.error(e.getMessage(), e);
            return new RestAuthException(Response.Status.UNAUTHORIZED, e).getResponse();
        } catch (JSONException e) {
            DEBUG.error(e.getMessage(), e);
            return new RestAuthException(Response.Status.BAD_REQUEST, e).getResponse();
        } catch (SignatureException e) {
            DEBUG.error(e.getMessage(), e);
            return new RestAuthException(Response.Status.INTERNAL_SERVER_ERROR, e).getResponse();
        } catch (AuthLoginException e) {
            DEBUG.error(e.getMessage(), e);
            return new RestAuthException(Response.Status.UNAUTHORIZED, e).getResponse();
        } catch (RestAuthCallbackHandlerResponseException e) {
            // Construct a Response object from the exception.
            responseBuilder = Response.status(e.getResponseStatus());
            responseBuilder.type(MediaType.APPLICATION_JSON_TYPE);
            for (String key : e.getResponseHeaders().keySet()) {
                responseBuilder.header(key, e.getResponseHeaders().get(key));
            }
            responseBuilder.entity(e.getJsonResponse().toString());
        }

        return responseBuilder.build();
    }

    /**
     * Will update the Callbacks from the request and JSON object from the POST body.
     *
     * @param headers The Headers from the request.
     * @param request The HttpServletRequest.
     * @param response The HttpSerlvetResponse.
     * @param originalCallbacks The orignal callbacks from the AuthContext.
     * @param jsonRequestObject The JSON object from the request body.
     * @return The updated original callbacks.
     * @throws JSONException If there is a problem parsing the JSON object.
     */
    private Callback[] handleCallbacks(HttpHeaders headers, HttpServletRequest request,
            HttpServletResponse response, Callback[] originalCallbacks, JSONObject jsonRequestObject)
            throws JSONException {

        Callback[] responseCallbacks;
        if (isJsonAttributePresent(jsonRequestObject, "callbacks")) {
            JSONArray jsonCallbacks = jsonRequestObject.getJSONArray("callbacks");

            responseCallbacks = restAuthCallbackHandlerManager.handleJsonCallbacks(originalCallbacks, jsonCallbacks);
        } else {
            responseCallbacks = restAuthCallbackHandlerManager.handleResponseCallbacks(headers, request, response,
                    originalCallbacks, jsonRequestObject);
        }

        return responseCallbacks;
    }

    /**
     * Checks to see if the given JSON object has the specified attribute name.
     *
     * @param jsonObject The JSON object.
     * @param attributeName The attribute name to check the presence of.
     * @return If the JSON object contains the attribute name.
     */
    private boolean isJsonAttributePresent(JSONObject jsonObject, String attributeName) {
        try {
            jsonObject.get(attributeName);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }
}
