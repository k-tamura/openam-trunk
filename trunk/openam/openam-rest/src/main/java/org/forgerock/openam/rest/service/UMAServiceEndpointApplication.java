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

package org.forgerock.openam.rest.service;

import org.forgerock.openam.rest.RestEndpoints;
import org.restlet.data.MediaType;
import org.restlet.routing.Router;

/**
 * A {@code ServiceEndpointApplication} for the /uma services.
 */
public class UMAServiceEndpointApplication extends ServiceEndpointApplication {

    /**
     * {@inheritDoc}
     * Sets the default media type as "application/json".
     */
    public UMAServiceEndpointApplication() {
        super(new XMLRestStatusService());
        getMetadataService().setDefaultMediaType(MediaType.APPLICATION_JSON);
    }

    /**
     * Returns the UMA router.
     *
     * @param restEndpoints Registry of routers.
     * @return The UMA router.
     */
    protected Router getRouter(RestEndpoints restEndpoints) {
        return restEndpoints.getUMAServiceRouter();
    }
}
