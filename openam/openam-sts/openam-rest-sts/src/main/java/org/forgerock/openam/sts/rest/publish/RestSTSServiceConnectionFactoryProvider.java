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
 * information: "Portions Copyrighted [year] [name of copyright owner]".
 *
 * Copyright 2014 ForgeRock AS. All rights reserved.
 */

package org.forgerock.openam.sts.rest.publish;

import com.google.inject.Key;
import com.google.inject.name.Names;
import org.forgerock.json.resource.ConnectionFactory;
import org.forgerock.openam.sts.AMSTSConstants;
import org.forgerock.openam.sts.rest.config.RestSTSInjectorHolder;

/**
 * Provides the ConnectionFactory for all Rest STS instances. This method will be called when the
 * crest servlet is constructed by the web-container. As such it is an appropriate entry point for initializing rest sts
 * infrastructure state - i.e. the state related to the publication of rest sts instances.
 */
public class RestSTSServiceConnectionFactoryProvider {
    public static ConnectionFactory getConnectionFactory() {
        return RestSTSInjectorHolder.getInstance(Key.get(ConnectionFactory.class,
                Names.named(AMSTSConstants.REST_STS_CONNECTION_FACTORY_NAME)));
    }
}
