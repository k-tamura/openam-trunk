/*
 * DO NOT REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013 ForgeRock Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at
 * http://forgerock.org/license/CDDLv1.0.html
 * See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at http://forgerock.org/license/CDDLv1.0.html
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions copyright [year] [name of copyright owner]"
 */

package org.forgerock.restlet.ext.oauth2.flow.responseTypes;

import com.sun.identity.shared.OAuth2Constants;
import org.forgerock.openam.ext.cts.repo.DefaultOAuthTokenStoreImpl;
import org.forgerock.openam.guice.InjectorHolder;
import org.forgerock.openam.oauth2.model.CoreToken;
import org.forgerock.openam.oauth2.provider.ResponseType;

import java.util.Map;

public class IDTokenResponseType implements ResponseType {

    public CoreToken createToken(Map<String, Object> data){
        DefaultOAuthTokenStoreImpl store = InjectorHolder.getInstance(DefaultOAuthTokenStoreImpl.class);
        return store.createJWT((String)data.get(OAuth2Constants.CoreTokenParams.REALM),
                (String)data.get(OAuth2Constants.CoreTokenParams.USERNAME),
                (String)data.get(OAuth2Constants.CoreTokenParams.CLIENT_ID),
                (String)data.get(OAuth2Constants.CoreTokenParams.CLIENT_ID),
                (String)data.get(OAuth2Constants.Custom.NONCE));
    }

    public String getReturnLocation(){
        return "FRAGMENT";
    }

    public String URIParamValue(){
        return "id_token";
    }
}
