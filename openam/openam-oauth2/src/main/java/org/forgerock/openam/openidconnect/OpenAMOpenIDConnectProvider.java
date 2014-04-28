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

package org.forgerock.openam.openidconnect;

import com.iplanet.sso.SSOToken;
import com.iplanet.sso.SSOTokenManager;
import org.forgerock.oauth2.core.OAuth2Request;
import org.forgerock.oauth2.core.exceptions.ServerException;
import org.forgerock.openam.oauth2.IdentityManager;
import org.forgerock.openidconnect.OpenIDConnectProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Provider for OpenId Connect for managing OpenId Connect sessions.
 *
 * @since 12.0.0
 */
@Singleton
public class OpenAMOpenIDConnectProvider implements OpenIDConnectProvider {

    private final Logger logger = LoggerFactory.getLogger("OAuth2Provider");
    private final SSOTokenManager tokenManager;
    private final IdentityManager identityManager;

    /**
     * Constructs a new OpenAMOpenIDConnectProvider.
     *
     * @param tokenManager An instance of the SSOTokenManager.
     * @param identityManager An instance of the IdentityManager.
     */
    @Inject
    public OpenAMOpenIDConnectProvider(SSOTokenManager tokenManager, IdentityManager identityManager) {
        this.tokenManager = tokenManager;
        this.identityManager = identityManager;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isUserValid(String userId, OAuth2Request request) {
        try {
            identityManager.getResourceOwnerIdentity(userId, request.<String>getParameter("realm"));
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    public void destroySession(String sessionId) throws ServerException {
        try {
            final SSOToken token = tokenManager.createSSOToken(sessionId);
            tokenManager.destroyToken(token);
        } catch (Exception e) {
            logger.error("Unable to get SsoTokenManager", e);
            throw new ServerException("Unable to get SsoTokenManager");
        }
    }
}
