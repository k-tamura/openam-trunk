/*
 * DO NOT REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2012 ForgeRock Inc. All rights reserved.
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
 * "Portions Copyrighted [year] [name of copyright owner]"
 */
package org.forgerock.restlet.ext.oauth2.consumer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;
import java.util.logging.Level;

import org.forgerock.restlet.ext.oauth2.OAuth2;
import org.restlet.Request;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.engine.header.Header;
import org.restlet.engine.header.HeaderReader;
import org.restlet.engine.security.AuthenticatorHelper;
import org.restlet.engine.util.Base64;
import org.restlet.util.Series;

/**
 * @see <a href="http://tools.ietf.org/html/draft-ietf-oauth-v2-http-mac">HTTP
 *      Authentication: MAC Access Authentication</a>
 */
public class MACAuthenticatorHelper extends AuthenticatorHelper {

    public final static ChallengeScheme HTTP_OAUTH_MAC = new ChallengeScheme("HTTP_MAC", "MAC",
            "MAC Access Authentication");

    /**
     * Constructor.
     */
    public MACAuthenticatorHelper() {
        super(MACAuthenticatorHelper.HTTP_OAUTH_MAC, true, true);
    }

    /**
     * Constructor.
     * 
     * @param clientSide
     *            Indicates if client side authentication is supported.
     * @param serverSide
     *            Indicates if server side authentication is supported.
     */
    public MACAuthenticatorHelper(boolean clientSide, boolean serverSide) {
        super(MACAuthenticatorHelper.HTTP_OAUTH_MAC, clientSide, serverSide);
    }

    @Override
    public ChallengeScheme getChallengeScheme() {
        return MACAuthenticatorHelper.HTTP_OAUTH_MAC;
    }

    public static void saveToken(ChallengeResponse challenge, String token) {
        // challenge.getParameters().set(OAuth2.MAC, token);
    }

    public static String retrieveToken(ChallengeResponse challenge) {
        return challenge.getParameters().getFirstValue(OAuth2.Token.OAUTH_ACCESS_TOKEN);
    }

    /*
     * The header attributes are set as follows:
     * 
     * id REQUIRED. The MAC key identifier. ts REQUIRED. The request timestamp.
     * The value MUST be a positive integer set by the client when making each
     * request to the number of seconds elapsed from a fixed point in time (e.g.
     * January 1, 1970 00:00:00 GMT). The value MUST NOT include leading zeros
     * (e.g. "000273154346"). nonce REQUIRED. A unique string generated by the
     * client. The value MUST be unique across all requests with the same
     * timestamp and MAC key identifier combination. ext OPTIONAL. A string used
     * to include additional information which is covered by the request MAC.
     * The content and format of the string is beyond the scope of this
     * specification. mac REQUIRED. The HTTP request MAC as described in Section
     * 3.2.
     */

    @Override
    public void parseResponse(ChallengeResponse challenge, Request request,
            Series<Header> httpHeaders) {
        try {
            /*
             * GET /resource/1?b=1&a=2 HTTP/1.1 Host: example.com Authorization:
             * MAC id="h480djs93hd8", ts="1336363200", nonce="dj83hs9s",
             * mac="bhCQXTVyfj5cmA9uKkPFx1zeOXM="
             */

            try {
                Header header = HeaderReader.readHeader(challenge.getRawValue());
            } catch (IOException e) {

            }
            StringTokenizer headers = new StringTokenizer(challenge.getRawValue(), ",");

            byte[] credentialsEncoded = Base64.decode(challenge.getRawValue());

            if (credentialsEncoded == null) {
                getLogger().info("Cannot decode credentials: " + challenge.getRawValue());
            }

            String mac = new String(credentialsEncoded, "ISO-8859-1");
            challenge.setIdentifier(mac);

        } catch (UnsupportedEncodingException e) {
            getLogger().log(Level.INFO, "Unsupported MAC Access Authentication encoding error", e);
        } catch (IllegalArgumentException e) {
            getLogger().log(Level.INFO, "Unable to decode the MAC Access Authentication", e);
        }
    }
}
