/*
 * DO NOT REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2012 ForgeRock Inc. All rights reserved.
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
 * "Portions Copyrighted [2012] [ForgeRock Inc]"
 */

package org.forgerock.openam.oauth2.internal;

import java.util.ArrayList;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;

import org.forgerock.openam.oauth2.utils.OAuth2Utils;
import org.forgerock.restlet.ext.openam.OpenAMParameters;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import org.restlet.security.SecretVerifier;
import org.restlet.security.User;

import com.sun.identity.authentication.AuthContext;
import com.sun.identity.authentication.spi.AuthLoginException;

/**
 * Verifies user credentials
 * @param <T> the type of object being verified
 */
public abstract class AbstractIdentityVerifier<T extends User> extends SecretVerifier {

    /**
     * Constructor.
     * <p/>
     *
     */
    public AbstractIdentityVerifier() {
    }

    @Override
    public int verify(final String identifier, char[] secret) {
        T user = authenticate(identifier, secret, "/");
        if (null != user) {
            return RESULT_VALID;
        }
        return RESULT_INVALID;
    }

    /**
     * Returns the user identifier.
     * 
     * @param request
     *            The request to inspect.
     * @param response
     *            The response to inspect.
     * @return The user identifier.
     */
    protected String getIdentifier(Request request, Response response) {
        return null != request.getChallengeResponse() ? request.getChallengeResponse()
                .getIdentifier() : null;
    }

    /**
     * Returns the secret provided by the user.
     * 
     * @param request
     *            The request to inspect.
     * @param response
     *            The response to inspect.
     * @return The secret provided by the user.
     */
    protected char[] getSecret(Request request, Response response) {
        return null != request.getChallengeResponse() ? request.getChallengeResponse().getSecret()
                : null;
    }

    /**
     * {@inheritDoc}
     */
    public int verify(Request request, Response response) {
        int result = RESULT_INVALID;
        String identifier = getIdentifier(request, response);
        char[] secret = getSecret(request, response);
        if (null == identifier || null == secret) {
            result = RESULT_MISSING;
        } else {
            // result = verify(identifier, secret);
            T user = authenticate(identifier, secret, OAuth2Utils.getRealm(request));
            if (null != user) {
                result = RESULT_VALID;
                request.getClientInfo().setUser(user);
            }
        }

        return result;
    }

    /**
     * Attempt to authenticate using simple user/password credentials.
     * 
     * @param username
     *            Subject's user name.
     * @param password
     *            Subject's password
     * @param realm
     *            Realm to search for subject
     * @return Subject's token if authenticated.
     */
    public T authenticate(String username, char[] password, String realm) {

        T ret = null;
        try {
            AuthContext lc = new AuthContext(realm);
            lc.login();
            while (lc.hasMoreRequirements()) {
                Callback[] callbacks = lc.getRequirements();
                ArrayList missing = new ArrayList();
                // loop through the requires setting the needs..
                for (int i = 0; i < callbacks.length; i++) {
                    if (callbacks[i] instanceof NameCallback) {
                        NameCallback nc = (NameCallback) callbacks[i];
                        nc.setName(username);
                    } else if (callbacks[i] instanceof PasswordCallback) {
                        PasswordCallback pc = (PasswordCallback) callbacks[i];
                        pc.setPassword(password);
                    } else {
                        missing.add(callbacks[i]);
                    }
                }
                // there's missing requirements not filled by this
                if (missing.size() > 0) {
                    throw new ResourceException(Status.SERVER_ERROR_INTERNAL,
                            "Missing requirements");
                }
                lc.submitRequirements(callbacks);
            }

            // validate the password..
            if (lc.getStatus() == AuthContext.Status.SUCCESS) {
                try {
                    // package up the token for transport..
                    ret = createUser(lc);
                } catch (Exception e) {
                    OAuth2Utils.DEBUG.error( "AbstractIdentityVerifier::authContext: "
                            + "Unable to get SSOToken", e);
                    // we're going to throw a generic error
                    // because the system is likely down..
                    throw new ResourceException(Status.SERVER_ERROR_INTERNAL, e);
                }
            }
        } catch (AuthLoginException le) {
            OAuth2Utils.DEBUG.error("AbstractIdentityVerifier::authContext AuthException", le);
            throw new ResourceException(Status.SERVER_ERROR_INTERNAL, le);
        }
        return ret;
    }

    /**
     * Creates new User object.
     * <p/>
     * 
     * @param authContext
     *            context
     * @return new User
     * @throws Exception
     *             when something unexpected happens
     */
    protected abstract T createUser(AuthContext authContext) throws Exception;
}
