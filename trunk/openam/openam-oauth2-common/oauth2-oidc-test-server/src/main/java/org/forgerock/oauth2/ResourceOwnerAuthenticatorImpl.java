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

package org.forgerock.oauth2;

import org.forgerock.common.UserStore;
import org.forgerock.oauth2.core.OAuth2Request;
import org.forgerock.oauth2.core.ResourceOwner;
import org.forgerock.oauth2.core.ResourceOwnerAuthenticator;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @since 12.0.0
 */
@Singleton
public class ResourceOwnerAuthenticatorImpl implements ResourceOwnerAuthenticator {

    private final UserStore userStore;

    @Inject
    public ResourceOwnerAuthenticatorImpl(final UserStore userStore) {
        this.userStore = userStore;
    }

    public ResourceOwner authenticate(OAuth2Request request) {

        final String username = request.getParameter("username");
        final char[] password = request.getParameter("password") == null ? null :
                request.<String>getParameter("password").toCharArray();

        final ResourceOwnerImpl user = userStore.get(username);

        if (!user.getPassword().equals(new String(password))) {
            return null;
        }

        return user;
    }
}
