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

import org.forgerock.oauth2.core.exceptions.InvalidClientException;
import org.forgerock.oauth2.core.exceptions.ServerException;
import org.forgerock.oauth2.core.exceptions.UnsupportedResponseTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.forgerock.oauth2.core.Utils.splitResponseType;

/**
 * Issues Authorization Tokens requested by OAuth2 authorize requests.
 *
 * @since 12.0.0
 */
@Singleton
public class AuthorizationTokenIssuer {

    private final Logger logger = LoggerFactory.getLogger("OAuth2Provider");

    /**
     * Issues tokens for the OAuth2 authorize request.
     *
     * @param request The OAuth2 request.
     * @param clientRegistration The client's registration.
     * @param resourceOwner The resource owner.
     * @param authorizationScope The authorization scope.
     * @param providerSettings An instance of the OAuth2ProviderSettings.
     * @return An AuthorizationToken.
     * @throws InvalidClientException If either the request does not contain the client's id or the client fails to be
     *          authenticated.
     * @throws UnsupportedResponseTypeException If the requested response type is not supported by either the client
     *          or the OAuth2 provider.
     * @throws ServerException If any internal server error occurs.
     */
    public AuthorizationToken issueTokens(OAuth2Request request, ClientRegistration clientRegistration,
            ResourceOwner resourceOwner, Set<String> authorizationScope, OAuth2ProviderSettings providerSettings)
            throws InvalidClientException, UnsupportedResponseTypeException, ServerException {

        //issue tokens
        final Set<String> requestedResponseTypes = splitResponseType(request.<String>getParameter("response_type"));
        if (Utils.isEmpty(requestedResponseTypes)) {
            logger.debug("Response type is not supported");
            throw new UnsupportedResponseTypeException("Response type is not supported");
        }

        final Set<String> validatedScope = providerSettings.validateAccessTokenScope(clientRegistration,
                authorizationScope, request);

        final Map<String, ResponseTypeHandler> allowedResponseTypes = providerSettings.getAllowedResponseTypes();

        final String tokenType = clientRegistration.getAccessTokenType();
        final String resourceOwnerId = resourceOwner.getId();
        final String clientId = clientRegistration.getClientId();
        final String redirectUri = request.getParameter("redirect_uri");
        final String nonce = request.getParameter("nonce");

        final Map<String, Token> tokens = new HashMap<String, Token>();
        boolean returnAsFragment = false;
        for (final String responseType : requestedResponseTypes) {

            if (Utils.isEmpty(responseType)) {
                throw new UnsupportedResponseTypeException("Response type is not supported");
            }

            final ResponseTypeHandler responseTypeHandler = allowedResponseTypes.get(responseType);

            final Map.Entry<String, Token> token = responseTypeHandler.handle(tokenType, validatedScope,
                    resourceOwnerId, clientId, redirectUri, nonce, request);

            if (token != null) {
                if (tokens.containsKey(token.getKey())) {
                    logger.debug("Returning multiple response types with the same url value");
                    throw new UnsupportedResponseTypeException("Returning multiple response types with the same url "
                            + "value");
                }

                tokens.put(token.getKey(), token.getValue());

                if (!returnAsFragment) {
                    final ResponseTypeHandler.ReturnLocation returnLocation = responseTypeHandler.getReturnLocation();
                    returnAsFragment = ResponseTypeHandler.ReturnLocation.FRAGMENT.equals(returnLocation);
                }
            }
        }

        final Map<String, String> tokenMap = flattenTokens(tokens);

        //plugin point for provider to add additional entries to tokenMap
        final Map<String, String> additionalData = providerSettings.additionalDataToReturnFromAuthorizeEndpoint(
                Collections.unmodifiableMap(tokens), request);
        if (!Utils.isEmpty(additionalData)) {
            final String returnLoc = additionalData.remove("returnLocation");
            if (!Utils.isEmpty(returnLoc)) {
                final ResponseTypeHandler.ReturnLocation returnLocation =
                        ResponseTypeHandler.ReturnLocation.valueOf(returnLoc.toUpperCase());
                if (!returnAsFragment && ResponseTypeHandler.ReturnLocation.FRAGMENT.equals(returnLocation)) {
                    returnAsFragment = true;
                }
            }
            tokenMap.putAll(additionalData);
        }

        final Set<String> scopeBefore = Utils.splitScope(request.<String>getParameter("scope"));
        if (!scopeBefore.containsAll(authorizationScope)) {
            final Set<String> checkedScope = providerSettings.validateAccessTokenScope(clientRegistration, scopeBefore,
                    request);
            if (!Utils.isEmpty(checkedScope)) {
                tokenMap.put("scope", Utils.joinScope(checkedScope));
            }
        }

        if (request.getParameter("state") != null) {
            tokenMap.put("state", request.<String>getParameter("state"));
        }

        return new AuthorizationToken(tokenMap, returnAsFragment);
    }

    /**
     * Flattens a {@code Map} of token name and Token into a single {@code Map} of the token name and any additional
     * information from an access token.
     *
     * @param tokens The {@code Map} of tokens.
     * @return A {@code Map} of the flattened tokens.
     * @throws ServerException If any internal server error occurs.
     */
    private Map<String, String> flattenTokens(Map<String, Token> tokens) throws ServerException {

        final Map<String, String> tokenMap = new HashMap<String, String>();

        for (final Map.Entry<String, Token> entry : tokens.entrySet()) {
            final Map<String, Object> token = entry.getValue().toMap();
            if (!tokenMap.containsKey(entry.getKey())) {
                tokenMap.put(entry.getKey(), entry.getValue().getTokenId());
            }
            //if access token add extra fields
            if (entry.getValue().getTokenName().equalsIgnoreCase("access_token")) {
                for (final Map.Entry<String, Object> entryInMap : token.entrySet()) {
                    if (!tokenMap.containsKey(entryInMap.getKey())) {
                        tokenMap.put(entryInMap.getKey(), entryInMap.getValue().toString());
                    }
                }
            }
        }
        return tokenMap;
    }
}