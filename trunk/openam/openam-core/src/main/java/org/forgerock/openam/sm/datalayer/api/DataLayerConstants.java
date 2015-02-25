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
package org.forgerock.openam.sm.datalayer.api;

/**
 * Constants for the Data Layer, which represents a pool of connections that
 * can be used by various services to access LDAP and perform queries.
 */
public class DataLayerConstants {
    /**
     * Constants to define the timeout system properties.
     */
    public static final String CORE_TOKEN_ASYNC_TIMEOUT = "org.forgerock.services.datalayer.connection.timeout.cts.async";
    public static final String CORE_TOKEN_REAPER_TIMEOUT = "org.forgerock.services.datalayer.connection.timeout.cts.reaper";
    public static final String DATA_LAYER_TIMEOUT = "org.forgerock.services.datalayer.connection.timeout";
    public static final String RESOURCE_SETS_TIMEOUT = "org.forgerock.services.datalayer.connection.timeout.resourcesets";
    public static final String UMA_AUDIT_ENTRY_TIMEOUT = "org.forgerock.services.datalayer.connection.timeout.umaauditentry";

    /**
     * Guice bindings for ConnectionConfig instances
     */
    public static final String SERVICE_MANAGER_CONFIG = "DataLayerConfig";
    public static final String EXTERNAL_CONFIG = "ExternalConfig";

    /**
     * Debug instance name for all CTS debugging.
     */
    public static final String DATA_LAYER_DEBUG = "tokenDataLayer";

    public static final String ROOT_DN_SUFFIX = "root-dn-suffix";
}
