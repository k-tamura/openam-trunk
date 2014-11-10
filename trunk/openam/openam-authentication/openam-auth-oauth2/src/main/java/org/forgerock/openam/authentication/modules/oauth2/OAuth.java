/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2011-2014 ForgeRock AS. All rights reserved.
 * Copyright (c) 2011 Cybernetica AS.
 * 
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at
 * http://forgerock.org/license/CDDLv1.0.html
 * See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at http://forgerock.org/license/CDDLv1.0.html
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 */

package org.forgerock.openam.authentication.modules.oauth2;

import static org.forgerock.openam.authentication.modules.oauth2.OAuthParam.*;

import com.iplanet.sso.SSOException;
import com.sun.identity.authentication.client.AuthClientUtils;
import com.sun.identity.authentication.service.AuthUtils;
import com.sun.identity.authentication.spi.AMLoginModule;
import com.sun.identity.authentication.spi.AuthLoginException;
import com.sun.identity.authentication.spi.RedirectCallback;
import com.sun.identity.authentication.util.ISAuthConstants;
import com.sun.identity.common.HttpURLConnectionManager;
import com.sun.identity.idm.AMIdentity;
import com.sun.identity.idm.IdRepoException;
import com.sun.identity.shared.debug.Debug;
import com.sun.identity.shared.encode.Base64;
import com.sun.identity.shared.encode.CookieUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.forgerock.guice.core.InjectorHolder;
import org.forgerock.json.jose.jwt.JwtClaimsSet;
import org.forgerock.openam.authentication.modules.common.mapping.AccountProvider;
import org.forgerock.openam.authentication.modules.common.mapping.AttributeMapper;
import org.forgerock.openam.authentication.modules.oidc.JwtHandler;
import org.forgerock.openam.authentication.modules.oidc.JwtHandlerConfig;
import org.forgerock.openam.cts.CTSPersistentStore;
import org.forgerock.openam.cts.api.TokenType;
import org.forgerock.openam.cts.api.fields.CoreTokenField;
import org.forgerock.openam.cts.api.tokens.Token;
import org.forgerock.openam.cts.exceptions.CoreTokenException;
import org.forgerock.openam.utils.CollectionUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.owasp.esapi.ESAPI;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;

public class OAuth extends AMLoginModule {

    public static final String PROFILE_SERVICE_RESPONSE = "ATTRIBUTES";
    public static final String OPENID_TOKEN = "OPENID_TOKEN";
    private static Debug debug = Debug.getInstance("amLoginModule");
    private String authenticatedUser = null;
    private Map sharedState;
    private OAuthConf config;
    private JwtHandlerConfig jwtHandlerConfig;
    String serverName = "";
    private ResourceBundle bundle = null;
    private static final SecureRandom random = new SecureRandom();    
    String data = "";
    String userPassword = "";
    String proxyURL = "";
    private final CTSPersistentStore ctsStore;

    public OAuth() {
        OAuthUtil.debugMessage("OAuth()");
        ctsStore = InjectorHolder.getInstance(CTSPersistentStore.class);
    }

    public void init(Subject subject, Map sharedState, Map config) {
        this.sharedState = sharedState;
        this.config = new OAuthConf(config);
        this.jwtHandlerConfig = new JwtHandlerConfig(config);
        bundle = amCache.getResBundle(BUNDLE_NAME, getLoginLocale());
        setAuthLevel(this.config.getAuthnLevel());    
    }

    
    public int process(Callback[] callbacks, int state) throws LoginException {

        OAuthUtil.debugMessage("process: state = " + state);
        HttpServletRequest request = getHttpServletRequest();
        HttpServletResponse response = getHttpServletResponse();

        if (request == null) {
            OAuthUtil.debugError("OAuth.process(): The request was null, this is "
                    + "an interactive module");
            return ISAuthConstants.LOGIN_IGNORE;
        }

        // We are being redirected back from an OAuth 2 Identity Provider
        String code = request.getParameter(PARAM_CODE);
        if (code != null) {
            OAuthUtil.debugMessage("OAuth.process(): GOT CODE: " + code);
            state = GET_OAUTH_TOKEN_STATE;
        }

        // The Proxy is used to return with a POST to the module
        proxyURL = config.getProxyURL();

        switch (state) {
            case ISAuthConstants.LOGIN_START: {
                config.validateConfiguration();
                serverName = request.getServerName();
                String requestedURI = request.getRequestURI();
                String requestedQuery = request.getQueryString();

                String authCookieName = AuthUtils.getAuthCookieName();

                if (requestedQuery != null) {
                    if (requestedQuery.endsWith(authCookieName + "=")) {
                        requestedQuery = requestedQuery.substring(0,
                                requestedQuery.length() - authCookieName.length() - 1);
                    }
                    requestedURI += "?" + requestedQuery;
                }

                // Bit of a hack but required for when the XUI is using the OAuth module so the redirect goes back to
                // the XUI to handle the next authentication stage and not straight to the authenticate REST endpoint
                if (requestedURI.contains("/json/authenticate")) {
                    requestedURI = requestedURI.replace("/json/authenticate", "");
                }

                // Find the domains for which we are configured
                Set<String> domains = AuthClientUtils.getCookieDomains();
                
                String ProviderLogoutURL = config.getLogoutServiceUrl();

                String csrfStateTokenId = RandomStringUtils.randomAlphanumeric(32);
                String csrfState = createAuthorizationState();
                Token csrfStateToken = new Token(csrfStateTokenId, TokenType.GENERIC);
                csrfStateToken.setAttribute(CoreTokenField.STRING_ONE, csrfState);
                try {
                    ctsStore.create(csrfStateToken);
                } catch (CoreTokenException e) {
                    OAuthUtil.debugError("OAuth.process(): Authorization redirect failed to be sent because the state "
                            + "could not be stored");
                    throw new AuthLoginException("OAuth.process(): Authorization redirect failed to be sent because "
                            + "the state could not be stored", e);
                }

                // Set the return URL Cookie
                // Note: The return URL cookie from the RedirectCallback can not
                // be used because the framework changes the order of the 
                // parameters in the query. OAuth2 requires an identical URL 
                // when retrieving the token
                for (String domain : domains) {
                    CookieUtils.addCookieToResponse(response,
                            CookieUtils.newCookie(COOKIE_PROXY_URL, proxyURL, "/", domain));
                    CookieUtils.addCookieToResponse(response,
                            CookieUtils.newCookie(COOKIE_ORIG_URL, requestedURI, "/", domain));
                    CookieUtils.addCookieToResponse(response,
                            CookieUtils.newCookie(NONCE_TOKEN_ID, csrfStateTokenId, "/", domain));
                    if (ProviderLogoutURL != null && !ProviderLogoutURL.isEmpty()) {
                        CookieUtils.addCookieToResponse(response,
                                CookieUtils.newCookie(COOKIE_LOGOUT_URL, ProviderLogoutURL, "/", domain));
                    }
                }

                // The Proxy is used to return with a POST to the module
                setUserSessionProperty(ISAuthConstants.FULL_LOGIN_URL,
                        requestedURI);

                setUserSessionProperty(SESSION_LOGOUT_BEHAVIOUR,
                        config.getLogoutBhaviour());

                String authServiceUrl = config.getAuthServiceUrl(proxyURL, csrfState);
                OAuthUtil.debugMessage("OAuth.process(): New RedirectURL=" + authServiceUrl);

                Callback[] callbacks1 = getCallback(2);
                RedirectCallback rc = (RedirectCallback) callbacks1[0];
                RedirectCallback rcNew = new RedirectCallback(authServiceUrl,
                        null,
                        "GET",
                        rc.getStatusParameter(),
                        rc.getRedirectBackUrlCookieName());
                replaceCallback(2, 0, rcNew);
                return GET_OAUTH_TOKEN_STATE;
            }

            case GET_OAUTH_TOKEN_STATE: {
                final String csrfState = request.getParameter("state");
                if (csrfState == null) {
                    OAuthUtil.debugError("OAuth.process(): Authorization call-back failed because there was no state "
                            + "parameter");
                    throw new AuthLoginException(BUNDLE_NAME, "noState", null);
                }

                try {
                    Token csrfStateToken = ctsStore.read(OAuthUtil.findCookie(request, NONCE_TOKEN_ID));
                    ctsStore.delete(csrfStateToken);
                    String expectedCsrfState = csrfStateToken.getValue(CoreTokenField.STRING_ONE);
                    if (!expectedCsrfState.equals(csrfState)) {
                        OAuthUtil.debugError("OAuth.process(): Authorization call-back failed because the state parameter "
                                + "contained an unexpected value");
                        throw new AuthLoginException(BUNDLE_NAME, "incorrectState", null);
                    }

                    // We are being redirected back from an OAuth 2 Identity Provider
                    code = request.getParameter(PARAM_CODE);
                    if (code == null || code.isEmpty()) {
                            OAuthUtil.debugMessage("OAuth.process(): LOGIN_IGNORE");
                            return ISAuthConstants.LOGIN_START;
                    }

                    validateInput("code", code, "HTTPParameterValue", 512, false);

                    OAuthUtil.debugMessage("OAuth.process(): code parameter: " + code);

                    String tokenSvcResponse = getContent(
                            config.getTokenServiceUrl(code, proxyURL), null);
                    OAuthUtil.debugMessage("OAuth.process(): token=" + tokenSvcResponse);

                    JwtClaimsSet jwtClaims = null;
                    String idToken = null;
                    if (config.isOpenIDConnect()) {
                        idToken = extractToken(ID_TOKEN, tokenSvcResponse);
                        JwtHandler jwtHandler = new JwtHandler(jwtHandlerConfig);
                        jwtClaims = jwtHandler.validateJwt(idToken);
                        if (!JwtHandler.isIntendedForAudience(config.getClientId(), jwtClaims)) {
                            OAuthUtil.debugError("OAuth.process(): ID token is not for this client as audience.");
                            throw new AuthLoginException(BUNDLE_NAME, "audience", null);
                        }
                    }

                    String token = extractToken(PARAM_ACCESS_TOKEN, tokenSvcResponse);

                    setUserSessionProperty(SESSION_OAUTH_TOKEN, token);

                    String profileSvcResponse = null;
                    if (StringUtils.isNotEmpty(config.getProfileServiceUrl())) {
                        profileSvcResponse = getContent(config.getProfileServiceUrl(), "Bearer " + token);
                        OAuthUtil.debugMessage("OAuth.process(): Profile Svc response: " + profileSvcResponse);
                    }

                    String realm = getRequestOrg();

                    if (realm == null) {
                        realm = "/";
                    }

                    AccountProvider accountProvider = instantiateAccountProvider();
                    AttributeMapper accountAttributeMapper = instantiateAccountMapper();
                    Map<String, Set<String>> userNames = getAttributes(profileSvcResponse,
                            config.getAccountMapperConfig(), accountAttributeMapper, jwtClaims);
                    
                    String user = null;
                    if (!userNames.isEmpty()) {
                      user = getUser(realm, accountProvider, userNames);
                    }

                    if (user == null && !config.getCreateAccountFlag()) {
                        authenticatedUser = getDynamicUser(userNames);

                        if (authenticatedUser != null) {
                            if (config.getSaveAttributesToSessionFlag()) {
                                Map <String, Set<String>> attributes = 
                                        getAttributesMap(profileSvcResponse, null);
                                saveAttributes(attributes);
                            }
                            OAuthUtil.debugMessage("OAuth.process(): LOGIN_SUCCEED "
                                    + "with user " + authenticatedUser);
                            return ISAuthConstants.LOGIN_SUCCEED;
                        } else {
                            throw new AuthLoginException("No user mapped!");
                        }

                    }

                    if (user == null && config.getCreateAccountFlag()) {
                        if (config.getPromptPasswordFlag()) {
                            setUserSessionProperty(PROFILE_SERVICE_RESPONSE, profileSvcResponse);
                            setUserSessionProperty(OPENID_TOKEN, idToken);
                            return SET_PASSWORD_STATE;
                        } else {
                            authenticatedUser = provisionAccountNow(accountProvider, realm, profileSvcResponse,
                                    getRandomData(), jwtClaims);
                            if (authenticatedUser != null) {
                                OAuthUtil.debugMessage("User created: " + authenticatedUser);
                                return ISAuthConstants.LOGIN_SUCCEED;
                            } else {
                                return ISAuthConstants.LOGIN_IGNORE;
                            }
                        }
                    }

                    if (user != null) {
                        authenticatedUser = user;
                        OAuthUtil.debugMessage("OAuth.process(): LOGIN_SUCCEED "
                                + "with user " + authenticatedUser);
                        if (config.getSaveAttributesToSessionFlag()) {
                            Map<String, Set<String>> attributes = getAttributesMap(
                                    profileSvcResponse, null);
                            saveAttributes(attributes);
                        }
                        return ISAuthConstants.LOGIN_SUCCEED;
                    }

                } catch (JSONException je) {
                    OAuthUtil.debugError("OAuth.process(): JSONException: "
                            + je.getMessage());
                    throw new AuthLoginException(BUNDLE_NAME, "json", null, je);
                } catch (SSOException ssoe) {
                    OAuthUtil.debugError("OAuth.process(): SSOException: "
                            + ssoe.getMessage());
                    throw new AuthLoginException(BUNDLE_NAME, "ssoe", null, ssoe);
                } catch (IdRepoException ire) {
                    OAuthUtil.debugError("OAuth.process(): IdRepoException: "
                            + ire.getMessage());
                    throw new AuthLoginException(BUNDLE_NAME, "ire", null, ire);
                } catch (CoreTokenException e) {
                    OAuthUtil.debugError("OAuth.process(): Authorization call-back failed because the state parameter "
                            + "contained an unexpected value");
                    throw new AuthLoginException(BUNDLE_NAME, "incorrectState", null, e);
                }
                break;
            }

            case SET_PASSWORD_STATE: {
                if (!config.getCreateAccountFlag()) {
                    return ISAuthConstants.LOGIN_IGNORE;
                }
                userPassword = request.getParameter(PARAM_TOKEN1);
                validateInput(PARAM_TOKEN1, userPassword, "HTTPParameterValue", 
                        512, false);
                String userPassword2 = request.getParameter(PARAM_TOKEN2);
                validateInput(PARAM_TOKEN2, userPassword2, "HTTPParameterValue", 
                        512, false);               
                
                if (!userPassword.equals(userPassword2)) {
                    OAuthUtil.debugWarning("OAuth.process(): Passwords did not match!");
                    return SET_PASSWORD_STATE;
                }
                
                String terms = request.getParameter("terms");
                if (!terms.equalsIgnoreCase("accept")) {
                    return SET_PASSWORD_STATE;
                }
                
                String profileSvcResponse = getUserSessionProperty("ATTRIBUTES");
                data = getRandomData();
                String mail = getMail(profileSvcResponse, config.getMailAttribute());
                OAuthUtil.debugMessage("Mail found = " + mail);
                try {
                    OAuthUtil.sendEmail(config.getEmailFrom(), mail, data, 
                            config.getSMTPConfig(), bundle, proxyURL);
                } catch (NoEmailSentException ex) {
                    OAuthUtil.debugError("No mail sent due to error", ex);
                    throw new AuthLoginException("Aborting authentication, because "
                            + "the mail could not be sent due to a mail sending error");
                }
                OAuthUtil.debugMessage("User to be created, we need to activate: " + data);
                return CREATE_USER_STATE;
            }

            case CREATE_USER_STATE: {
                String activation = request.getParameter(PARAM_ACTIVATION);
                validateInput(PARAM_ACTIVATION, activation, "HTTPParameterValue", 
                        512, false);
                OAuthUtil.debugMessage("code entered by the user: " + activation);

                if (activation == null || activation.isEmpty()
                        || !activation.trim().equals(data.trim())) {
                    return CREATE_USER_STATE;
                }

                String profileSvcResponse = getUserSessionProperty(PROFILE_SERVICE_RESPONSE);
                String idToken = getUserSessionProperty(ID_TOKEN);
                String realm = getRequestOrg();
                if (realm == null) {
                    realm = "/";
                }

                OAuthUtil.debugMessage("Got Attributes: " + profileSvcResponse);
                AccountProvider accountProvider = instantiateAccountProvider();
                JwtClaimsSet jwtClaims = null;
                if (idToken != null) {
                    jwtClaims = new JwtHandler(jwtHandlerConfig).getJwtClaims(idToken);
                }
                authenticatedUser = provisionAccountNow(accountProvider, realm, profileSvcResponse, userPassword,
                        jwtClaims);
                if (authenticatedUser != null) {
                    OAuthUtil.debugMessage("User created: " + authenticatedUser);
                    return ISAuthConstants.LOGIN_SUCCEED;
                } else {
                    return ISAuthConstants.LOGIN_IGNORE;
                }
            }

            default: {
                OAuthUtil.debugError("OAuth.process(): Illegal State");
                return ISAuthConstants.LOGIN_IGNORE;
            }
        }
        
        throw new AuthLoginException(BUNDLE_NAME, "unknownState", null);
    }

    private String createAuthorizationState() {
        return new BigInteger(160, new SecureRandom()).toString(Character.MAX_RADIX);
    }

    // Search for the user in the realm, using the instantiated account mapper
    private String getUser(String realm, AccountProvider accountProvider,
            Map<String, Set<String>> userNames)
            throws AuthLoginException, JSONException, SSOException, IdRepoException {

        String user = null;
        if ((userNames != null) && !userNames.isEmpty()) {
            AMIdentity userIdentity = accountProvider.searchUser(
                    getAMIdentityRepository(realm), userNames);
            if (userIdentity != null) {
                user = userIdentity.getName();
            }
        }
        
        return user;
    }

    // Generate random data
    private String getRandomData() {
	        byte[] pass = new byte[20];
	        random.nextBytes(pass);
	       return Base64.encode(pass);
     }

    // Create an instance of the pluggable account mapper
    private AttributeMapper<?> instantiateAccountMapper ()
            throws AuthLoginException {

        try {
            return getConfiguredType(AttributeMapper.class, config.getAccountMapper());
        } catch (ClassCastException ex) {
            debug.error("Account Mapper is not an implementation of AttributeMapper.", ex);
            throw new AuthLoginException("Problem when trying to instantiate the account provider", ex);
        } catch (Exception ex) {
            throw new AuthLoginException("Problem when trying to instantiate the account mapper", ex);
        }
    }

    // Create an instance of the pluggable account mapper
    private AccountProvider instantiateAccountProvider()
            throws AuthLoginException {
        try {
            return getConfiguredType(AccountProvider.class, config.getAccountProvider());
        } catch (ClassCastException ex) {
            debug.error("Account Provider is not actually an implementation of AccountProvider.", ex);
            throw new AuthLoginException("Problem when trying to instantiate the account provider", ex);
        } catch (Exception ex) {
            throw new AuthLoginException("Problem when trying to instantiate the account provider", ex);
        }
    }

    // Obtain the attributes configured for the module, by using the pluggable
    // Attribute mapper
    private Map<String, Set<String>> getAttributesMap(String svcProfileResponse, JwtClaimsSet jwtClaims) {
        
        Map<String, Set<String>> attributes = new HashMap<String, Set<String>>();
        Map<String, String> attributeMapperConfig = config.getAttributeMapperConfig();

        for (String attributeMapperClassname : config.getAttributeMappers()) {
            try {
                AttributeMapper attributeMapper = getConfiguredType(AttributeMapper.class, attributeMapperClassname);
                attributeMapper.init(OAuthParam.BUNDLE_NAME);
                attributes.putAll(getAttributes(svcProfileResponse, attributeMapperConfig, attributeMapper, jwtClaims));
            } catch (ClassCastException ex) {
                debug.error("Attribute Mapper is not actually an implementation of AttributeMapper.", ex);
            } catch (Exception ex) {
                OAuthUtil.debugError("OAuth.getUser: Problem when trying to get the Attribute Mapper", ex);
            }
        }
        OAuthUtil.debugMessage("OAuth.getUser: creating new user; attributes = " + attributes);
        return attributes;
    }

    private <T> T getConfiguredType(Class<T> type, String config) throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        String[] parameters = new String[0];
        int delimiter = config.indexOf('|');
        if (delimiter > -1) {
            parameters = config.substring(delimiter + 1).split("\\|");
            config = config.substring(0, delimiter);
        }

        Class<? extends T> clazz = Class.forName(config).asSubclass(type);
        Class<?>[] parameterTypes = new Class<?>[parameters.length];
        Arrays.fill(parameterTypes, String.class);
        return clazz.getConstructor(parameterTypes).newInstance(parameters);
    }

    private Map getAttributes(String svcProfileResponse, Map<String, String> attributeMapperConfig,
            AttributeMapper attributeMapper, JwtClaimsSet jwtClaims) throws AuthLoginException {
        try {
            attributeMapper.getClass().getDeclaredMethod("getAttributes", Map.class, String.class);
            return attributeMapper.getAttributes(attributeMapperConfig, svcProfileResponse);
        } catch (NoSuchMethodException e) {
            return attributeMapper.getAttributes(attributeMapperConfig, jwtClaims);
        }
    }


    // Save the attributes configured for the attribute mapper as session attributes
    public void saveAttributes(Map<String, Set<String>> attributes) throws AuthLoginException {

        if (attributes != null && !attributes.isEmpty()) {
            for (String attributeName : attributes.keySet()) {
                String attributeValue = attributes.get(attributeName).
                        iterator().next().toString();
                setUserSessionProperty(attributeName, attributeValue);
                OAuthUtil.debugMessage("OAuth.saveAttributes: "
                        + attributeName + "=" + attributeValue);
            }
        } else {
            OAuthUtil.debugMessage("OAuth.saveAttributes: NO attributes to set");
        }
    }
    
    // Generate a user name, either using the anonymous user if configured or by
    // extracting the user from the userName map.
    // Return null, if nothing was found
    private String getDynamicUser(Map<String, Set<String>> userNames)
            throws AuthLoginException {

        String dynamicUser = null;
        if (config.getUseAnonymousUserFlag()) {
            String anonUser = config.getAnonymousUser();
            if (anonUser != null && !anonUser.isEmpty()) {
                dynamicUser = anonUser;
            }
        } else { // Do not use anonymous
            if (userNames != null && !userNames.isEmpty()) {
                Iterator<Set<String>> usersIt = userNames.values().iterator();
                dynamicUser = usersIt.next().iterator().next();
            }

        }
        return dynamicUser;
    }


    // Obtain the user profile information from the OAuth 2.0 Identity Provider
    // Profile service configured for this module, either using first GET and
    // POST as a fall back
    private String getContent(String serviceUrl, String authorizationHeader) throws LoginException {

        BufferedReader in = new BufferedReader(new InputStreamReader(
                getContentStreamByGET(serviceUrl, authorizationHeader)));
        StringBuilder buf = new StringBuilder();
        try {
            String str;
            while ((str = in.readLine()) != null) {
                buf.append(str);
            }
        } catch (IOException ioe) {
            OAuthUtil.debugError("OAuth.getContent: IOException: " + ioe.getMessage());
            throw new AuthLoginException(BUNDLE_NAME, "ioe", null, ioe);
        } finally {
            try {
                in.close();
            } catch (IOException ioe) {
                OAuthUtil.debugError("OAuth.getContent: IOException: " + ioe.getMessage());
                throw new AuthLoginException(BUNDLE_NAME, "ioe", null, ioe);
            }
        }
        return buf.toString();
    }

    // Create the account in the realm, by using the pluggable account mapper and
    // the attributes configured in the attribute mapper
    public String provisionAccountNow(AccountProvider accountProvider, String realm, String profileSvcResponse,
            String userPassword, JwtClaimsSet jwtClaims)
            throws AuthLoginException {

            Map<String, Set<String>> attributes = getAttributesMap(profileSvcResponse, jwtClaims);
            if (config.getSaveAttributesToSessionFlag()) {
                saveAttributes(attributes);
            }
            attributes.put("userPassword", CollectionUtils.asSet(userPassword));
            attributes.put("inetuserstatus", CollectionUtils.asSet("Active"));
            AMIdentity userIdentity =
                    accountProvider.provisionUser(getAMIdentityRepository(realm),
                    attributes);
            if (userIdentity != null) {
                return userIdentity.getName().trim();
            } else {
                return null;      
            }     
    }

    public InputStream getContentStreamByGET(String serviceUrl, String authorizationHeader)
            throws LoginException {

        OAuthUtil.debugMessage("service url: " + serviceUrl);
        try {
            InputStream is;
            URL urlC = new URL(serviceUrl);

            HttpURLConnection connection = HttpURLConnectionManager.getConnection(urlC);
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            if (authorizationHeader != null) {
                connection.setRequestProperty("Authorization", authorizationHeader);
            }
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                OAuthUtil.debugMessage("OAuth.getContentStreamByGET: HTTP Conn OK");
                is = connection.getInputStream();
            } else {
                // Server returned HTTP error code.
                String errorStream = getErrorStream(connection);
                if (OAuthUtil.debugMessageEnabled()) {
                  OAuthUtil.debugMessage("OAuth.getContentStreamByGET: HTTP Conn Error:\n" +
                        " Response code: " + connection.getResponseCode() + "\n " +
                        " Response message: " + connection.getResponseMessage() + "\n" +
                        " Error stream: " + errorStream + "\n");
                }
                is = getContentStreamByPOST(serviceUrl, authorizationHeader);
            }

            return is;

        } catch (MalformedURLException mfe) {
            throw new AuthLoginException(BUNDLE_NAME,"malformedURL", null, mfe);
        } catch (IOException ioe) {
            throw new AuthLoginException(BUNDLE_NAME,"ioe", null, ioe);
        }
    }
    
    private String getErrorStream(HttpURLConnection connection) {
        InputStream errStream = connection.getErrorStream();
        if (errStream == null) {
            return "Empty error stream";
        } else {
            BufferedReader in = new BufferedReader(new InputStreamReader(errStream));
            StringBuilder buf = new StringBuilder();
            try {
                String str;
                while ((str = in.readLine()) != null) {
                    buf.append(str);
                }
            }
            catch (IOException ioe) {
                OAuthUtil.debugError("OAuth.getErrorStream: IOException: " + ioe.getMessage());
            } finally {
                try {
                    in.close();
                } catch (IOException ioe) {
                    OAuthUtil.debugError("OAuth.getContent: IOException: " + ioe.getMessage());
                }
            }
            return buf.toString();
        }
    }

    public InputStream getContentStreamByPOST(String serviceUrl, String authorizationHeader)
            throws LoginException {

        InputStream is = null;

        try {
            OAuthUtil.debugMessage("OAuth.getContentStreamByPOST: URL = " + serviceUrl);

            URL url = new URL(serviceUrl);

            String query = url.getQuery();
            OAuthUtil.debugMessage("OAuth.getContentStreamByPOST: Query: " + query);

            HttpURLConnection connection = HttpURLConnectionManager.getConnection(url);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            if (authorizationHeader != null) {
                connection.setRequestProperty("Authorization", authorizationHeader);
            }
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(query);
            writer.close();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                OAuthUtil.debugMessage("OAuth.getContentStreamByPOST: HTTP Conn OK");
                is = connection.getInputStream();
            } else { // Error Code
                String data2[] = {String.valueOf(connection.getResponseCode())};
                OAuthUtil.debugError("OAuth.getContentStreamByPOST: HTTP Conn Error:\n" +
                        " Response code: " + connection.getResponseCode() + "\n" +
                        " Response message: " + connection.getResponseMessage() + "\n" +
                        " Error stream: " + getErrorStream(connection) + "\n");
                throw new AuthLoginException(BUNDLE_NAME, "httpErrorCode", data2);
            }
        } catch (MalformedURLException e) {
            throw new AuthLoginException(BUNDLE_NAME,"malformedURL", null, e);
        } catch (IOException e) {
            throw new AuthLoginException(BUNDLE_NAME,"ioe", null, e);
        }

        return is;
    }

    // Extract the Token from the OAuth 2.0 response
    // Todo: Maybe this should be pluggable
    public String extractToken(String tokenName, String response) {

        String token = "";
        try {
            JSONObject jsonToken = new JSONObject(response);
            if (jsonToken != null
                    && !jsonToken.isNull(tokenName)) {
                token = jsonToken.getString(tokenName);
                OAuthUtil.debugMessage(tokenName + ": " + token);
            }
        } catch (JSONException je) {
            OAuthUtil.debugMessage("OAuth.extractToken: Not in JSON format" + je);
            token = OAuthUtil.getParamValue(response, tokenName);
        }

        return token;
    }

    // Obtain the email address field from the response provided by the
    // OAuth 2.0 Profile service.
    public String getMail(String svcResponse, String mailAttribute) {
        String mail = "";
        OAuthUtil.debugMessage("mailAttribute: " + mailAttribute);
        try {
            JSONObject jsonData = new JSONObject(svcResponse);

            if (mailAttribute != null && mailAttribute.indexOf(".") != -1) {
                StringTokenizer parts = new StringTokenizer(mailAttribute, ".");
                mail = jsonData.getJSONObject(parts.nextToken()).getString(parts.nextToken());
            } else {
                mail = jsonData.getString(mailAttribute);
            }
            OAuthUtil.debugMessage("mail: " + mail);

        } catch (JSONException je) {
            OAuthUtil.debugMessage("OAuth.getMail: Not in JSON format" + je);
        }

        return mail;
    }
    
    // Validate the field provided as input
    public void validateInput(String tag, String inputField,
            String rule, int maxLength, boolean allowNull)
            throws AuthLoginException {
        if (!ESAPI.validator().isValidInput(tag, inputField, rule, maxLength, allowNull)) {
            OAuthUtil.debugError("OAuth.validateInput(): OAuth 2.0 Not valid input !");
            String msgdata[] = {tag, inputField};
            throw new AuthLoginException(BUNDLE_NAME, "invalidField", msgdata);
        };
    }
    
    
    public Principal getPrincipal() {
        if (authenticatedUser != null) {
            return new OAuthPrincipal(authenticatedUser);
        }
        return null;
    }

    public void destroyModuleState() {
        authenticatedUser = null;
    }

    public void nullifyUsedVars() {
        config = null;
        sharedState = null;
    }
    
}
