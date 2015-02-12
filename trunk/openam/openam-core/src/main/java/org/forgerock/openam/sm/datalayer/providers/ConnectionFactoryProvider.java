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
 * Copyright 2014-2015 ForgeRock AS.
 */
package org.forgerock.openam.sm.datalayer.providers;

import org.forgerock.openam.sm.datalayer.api.ConnectionFactory;
import org.forgerock.openam.sm.exceptions.InvalidConfigurationException;

/**
 * Represents the ability to generate ConnectionFactory instances.
 */
public interface ConnectionFactoryProvider<C> {
    /**
     * Create an instance of a ConnectionFactory.
     *
     * @return Non null ConnectionFactory.
     *
     * @throws InvalidConfigurationException If there was a problem with the configuration that
     * prevented the ConnectionFactory being instantiated.
     */
    ConnectionFactory<C> createFactory() throws InvalidConfigurationException;
}
