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
 * Copyright 2012 ForgeRock Inc.
 */
package org.forgerock.openam.forgerockrest;


import com.iplanet.am.util.SystemProperties;

import com.iplanet.sso.SSOException;
import com.iplanet.sso.SSOToken;
import com.iplanet.sso.SSOTokenManager;
import com.sun.identity.idm.AMIdentity;
import com.sun.identity.idm.IdRepoException;
import com.sun.identity.idm.IdType;
import com.sun.identity.security.AdminTokenAction;
import com.sun.identity.shared.Constants;
import org.forgerock.json.fluent.JsonValue;
import org.forgerock.json.resource.*;
import org.forgerock.json.resource.servlet.HttpContext;


import java.lang.Exception;
import java.lang.Object;
import java.lang.String;
import java.security.AccessController;
import java.util.LinkedHashMap;
import java.util.List;


import com.sun.identity.idsvcs.*;
import org.forgerock.json.fluent.JsonValueException;

import com.sun.identity.idsvcs.opensso.IdentityServicesImpl;



/**
 * forgerock-rest utility methods and variables
 */
final public class  RestUtils {

    private static SSOToken token = (SSOToken) AccessController.doPrivileged(AdminTokenAction.getInstance());
    private static String adminUser = SystemProperties.get(Constants.AUTHENTICATION_SUPER_USER);
    private static AMIdentity adminUserId = null;
    static {
        if (adminUser != null) {
            adminUserId = new AMIdentity(token,
                    adminUser, IdType.USER, "/", null);
        }
    }
    /**
     * Returns TokenID from headers
     *
     * @param context ServerContext which contains the headers.
     * @return String with TokenID
     */
    static public String getCookieFromServerContext(ServerContext context) {
        List<String> cookies = null;
        String cookieName = null;
        HttpContext header = null;
        try {
            cookieName = SystemProperties.get("com.iplanet.am.cookie.name");
            if (cookieName == null || cookieName.isEmpty()) {
                RestDispatcher.debug.error("IdentityResource.getCookieFromServerContext() :: " +
                        "Cannot retrieve SystemProperty : com.iplanet.am.cookie.name");
                return null;
            }
            header = context.asContext(HttpContext.class);
            if (header == null) {
                RestDispatcher.debug.error("IdentityResource.getCookieFromServerContext() :: " +
                        "Cannot retrieve ServerContext as HttpContext");
                return null;
            }
            //get the cookie from header directly   as the name of com.iplanet.am.cookie.am
            cookies = header.getHeaders().get(cookieName.toLowerCase());
            if (cookies != null && !cookies.isEmpty()) {
                for (String s : cookies) {
                    if (s == null || s.isEmpty()) {
                        return null;
                    } else {
                        return s;
                    }
                }
            } else {  //get cookie from header parameter called cookie
                cookies = header.getHeaders().get("cookie");
                if (cookies != null && !cookies.isEmpty()) {
                    for (String cookie : cookies) {
                        String cookieNames[] = cookie.split(";"); //Split parameter up
                        for (String c : cookieNames) {
                            if (c.contains(cookieName)) { //if com.iplanet.am.cookie.name exists in cookie param
                                String amCookie = c.replace(cookieName + "=", "").trim();
                                return amCookie; //return com.iplanet.am.cookie.name value
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            RestDispatcher.debug.error("IdentityResource.getCookieFromServerContext() :: " +
                    "Cannot get cookie from ServerContext!" + e);
        }
        return null;
    }

    static public boolean hasPermission(final ServerContext context){
        //Checks to see if User is amadmin, currently only amAdmin can access realms
        JsonValue result = new JsonValue(new LinkedHashMap<String, Object>(1));

        Token admin = new Token();
        admin.setId(getCookieFromServerContext(context));
        SSOToken ssotok = null;
        AMIdentity amIdentity = null;

        try {
            SSOTokenManager mgr = SSOTokenManager.getInstance();
            ssotok = mgr.createSSOToken(getCookieFromServerContext(context));
            amIdentity = new AMIdentity(ssotok);

            if (!(amIdentity.equals(adminUserId))){
                RestDispatcher.debug.error("Unauthorized user.");
                return false;
            }
            return true;
        } catch (SSOException e) {
            RestDispatcher.debug.error("IdentityResource.idFromSession() :: Cannot retrieve SSO Token: " + e);
        } catch (IdRepoException ex) {
            RestDispatcher.debug.error("IdentityResource.idFromSession() :: Cannot retrieve user from IdRepo" + ex);
        }
        return false;
    }



}
