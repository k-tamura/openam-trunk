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

package org.forgerock.openam.sts.tokengeneration.service;

import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import org.forgerock.json.resource.ConnectionFactory;
import org.forgerock.json.resource.Resources;
import org.forgerock.json.resource.Router;
import org.forgerock.openam.sts.tokengeneration.config.TokenGenerationServiceInjectorHolder;
import org.forgerock.openam.sts.tokengeneration.saml2.RestSTSInstanceState;
import org.forgerock.openam.sts.tokengeneration.saml2.SAML2TokenGeneration;
import org.forgerock.openam.sts.tokengeneration.saml2.STSInstanceStateProvider;
import org.slf4j.Logger;

/**
 * CREST servlet connection factory provider for the token-generation-service. References the TokenGenerationServiceInjectorHolder
 * which initializes the guice injector to create the bindings defined in the TokenGenerationModule.
 */
public class TokenGenerationServiceConnectionFactoryProvider {
    public static ConnectionFactory getConnectionFactory() {
        final Router router = new Router();
        //TODO: this route must reflect the OpenAM rest naming conventions.
        router.addRoute("/issue/",
                new TokenGenerationService(TokenGenerationServiceInjectorHolder.getInstance(Key.get(SAML2TokenGeneration.class)),
                        TokenGenerationServiceInjectorHolder.getInstance(Key.get(new TypeLiteral<STSInstanceStateProvider<RestSTSInstanceState>>(){})),
                        TokenGenerationServiceInjectorHolder.getInstance(Key.get(Logger.class))));
        return Resources.newInternalConnectionFactory(router);
    }

}