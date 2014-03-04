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

package org.forgerock.openam.authz.filter;

import com.iplanet.dpro.session.service.SessionService;
import com.iplanet.sso.SSOException;
import com.iplanet.sso.SSOToken;
import com.sun.identity.shared.Constants;
import org.apache.commons.lang.StringUtils;
import org.forgerock.authz.AuthorizationContext;
import org.forgerock.authz.AuthorizationModule;
import org.forgerock.json.fluent.JsonValue;
import org.forgerock.openam.auth.shared.AuthUtilsWrapper;
import org.forgerock.openam.auth.shared.AuthnRequestUtils;
import org.forgerock.openam.auth.shared.SSOTokenFactory;
import org.forgerock.openam.utils.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;

/**
 * Responsible for validating that the user performing the request is the Admin user.
 * If this is not the case then the request is not allowed to proceed.
 *
 * @since 10.2.0
 */
@Singleton
public class AdminAuthorizationModule implements AuthorizationModule {

    private final Logger logger = LoggerFactory.getLogger(AdminAuthorizationModule.class);

    private final SSOTokenFactory ssoTokenFactory;
    private final AuthnRequestUtils requestUtils;
    private final Config<SessionService> sessionService;
    private final AuthUtilsWrapper authUtilsWrapper;

    /**
     * Constructs a new instance of the AdminAuthorizationModule.
     *
     * @param ssoTokenFactory An instance of the SSOTokenFactory.
     * @param requestUtils An instance of the AuthnRequestUtils.
     * @param sessionService A Future containing an instance of the SessionService.
     * @param authUtilsWrapper An instance of the AuthUtilWrapper.
     */
    @Inject
    public AdminAuthorizationModule(SSOTokenFactory ssoTokenFactory, AuthnRequestUtils requestUtils,
            Config<SessionService> sessionService, AuthUtilsWrapper authUtilsWrapper) {
        this.ssoTokenFactory = ssoTokenFactory;
        this.requestUtils = requestUtils;
        this.sessionService = sessionService;
        this.authUtilsWrapper = authUtilsWrapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialise(final JsonValue config) {
    }

    /**
     * Filter the request by examining the SSOToken UUID against the Admin user SSOToken.
     *
     * @param servletRequest {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    public boolean authorize(HttpServletRequest servletRequest, AuthorizationContext context) {

        // Request must contain the TokenID of the user.
        String tokenId = requestUtils.getTokenId(servletRequest);
        if (StringUtils.isEmpty(tokenId)) {
            tokenId = servletRequest.getHeader(getCookieHeaderName());
        }
        if (StringUtils.isEmpty(tokenId)) {
            return false;
        }

        // Must generate a valid SSOToken from this TokenID.
        SSOToken token = ssoTokenFactory.getTokenFromId(tokenId);
        if (token == null) {
            return false;
        }

        // Verify that the SSOToken is the super user.
        String userId;
        try {
            userId = token.getProperty(Constants.UNIVERSAL_IDENTIFIER);
        } catch (SSOException e) {
            logger.error("Failed to get userId", e);
            throw new IllegalStateException(e);
        }

        return sessionService.get().isSuperUser(userId);
    }

    /**
     * Gets the AM cookie name, as set by AM.
     *
     * @return The AM cookie name.
     */
    private String getCookieHeaderName() {
        return authUtilsWrapper.getCookieName();
    }

    /**
     * Does nothing in this impl.
     */
    public void destroy() {

    }
}
