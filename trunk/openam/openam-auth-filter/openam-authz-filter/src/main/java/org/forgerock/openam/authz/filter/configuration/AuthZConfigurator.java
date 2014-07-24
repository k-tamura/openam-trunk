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
 * Copyright 2013-2014 ForgeRock AS.
 */

package org.forgerock.openam.authz.filter.configuration;

import org.forgerock.auth.common.AuditLogger;
import org.forgerock.authz.AuthorizationLoggingConfigurator;
import org.forgerock.guice.core.InjectorHolder;

/**
 * Common implementation of the Authorization Configurator that provides the correct AM debug and audit logs for
 * authorization.
 *
 * @since 10.2.0
 */
public class AuthZConfigurator implements AuthorizationLoggingConfigurator {

    /**
     * @return An instance of the AuthZConfigurator.
     */
    public static AuthorizationLoggingConfigurator getLoggingConfigurator() {
        return new AuthZConfigurator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuditLogger getAuditLogger() {
        return InjectorHolder.getInstance(AuthZAuditLogger.class);
    }
}