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

import org.forgerock.oauth2.core.exceptions.InvalidRequestException;
import org.forgerock.oauth2.core.exceptions.RedirectUriMismatchException;

import java.net.URI;

import static org.forgerock.oauth2.core.Utils.isEmpty;

/**
 * Validates OAuth2 Redirect URIs.
 *
 * @since 12.0.0
 */
public class RedirectUriValidator {

    /**
     * Validates a OAuth2 redirect URI against pre-registered redirect URIs for the client.
     *
     * @param clientRegistration The client's registration.
     * @param redirectUri The redirect URI to validate.
     * @throws InvalidRequestException If the redirect URI is empty and the client does not have just a single redirect
     * URI registered.
     * @throws RedirectUriMismatchException If the redirect URI does not match, exactly, on of the client's registered
     * redirect URIs or the redirect URI is not absoulte or contains a fragment.
     */
    public void validate(final ClientRegistration clientRegistration, final String redirectUri)
            throws InvalidRequestException, RedirectUriMismatchException {

        if (isEmpty(redirectUri)) {
            if (clientRegistration.getRedirectUris().size() == 1) {
                return;
            }

            throw new InvalidRequestException("Missing parameter: redirect_uri");
        }

        final URI request = URI.create(redirectUri);

        if (request.getFragment() != null) {
            throw new RedirectUriMismatchException();
        }
        if (!request.isAbsolute()) {
            throw new RedirectUriMismatchException();
        }
        for (URI uri : clientRegistration.getRedirectUris()) {
            if (uri.equals(request)) {
                //GOOD CASE
                return;
            }
        }
        throw new RedirectUriMismatchException();
    }
}
