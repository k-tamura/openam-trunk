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

package org.forgerock.oauth2.core;

import org.forgerock.oauth2.core.exceptions.ServerException;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of the ResponseTypeHandler for handling authorization code response types.
 *
 * @since 12.0.0
 */
@Singleton
public class AuthorizationCodeResponseTypeHandler implements ResponseTypeHandler {

    private final TokenStore tokenStore;

    /**
     * Constructs a new AuthorizationCodeResponseTypeHandler.
     *
     * @param tokenStore An instance of the TokenStore.
     */
    @Inject
    public AuthorizationCodeResponseTypeHandler(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    /**
     * {@inheritDoc}
     */
    public Map.Entry<String, Token> handle(String tokenType, Set<String> scope, String resourceOwnerId, String clientId,
            String redirectUri, String nonce, OAuth2Request request) throws ServerException {

        final AuthorizationCode authorizationCode = tokenStore.createAuthorizationCode(scope, resourceOwnerId, clientId,
                redirectUri, nonce, request);
        return new AbstractMap.SimpleEntry<String, Token>("code", authorizationCode);
    }

    /**
     * {@inheritDoc}
     */
    public ReturnLocation getReturnLocation() {
        return ReturnLocation.QUERY;
    }
}
