/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright © 2011 ForgeRock AS. All rights reserved.
 * Copyright © 2011 Cybernetica AS.
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
 *
 */

package org.forgerock.openam.authentication.modules.oauth2;

import javax.servlet.http.HttpServletRequest;
import com.sun.identity.shared.debug.Debug;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javax.servlet.http.Cookie;
import static org.forgerock.openam.authentication.modules.oauth2.OAuthParam.*;

public class OAuthUtil  {

    private static Debug debug = Debug.getInstance("amAuth");

    public static String findCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        Cookie returnCookie = null;
        String value = "";
        if (cookies != null) {
            for(Cookie cookie : cookies) {
                if (cookieName.equalsIgnoreCase(cookie.getName())) {
                    returnCookie = cookie;
                    value = returnCookie.getValue();
                    debugMessage("OAuth.findCookie()" + "Cookie "
                                + cookieName
                                + " found. "
                                + "Content is: " + value);
                    break;
                }
            }
        }
        return value;
    }
    
    
    public static Cookie deleteCookie(String strCookieName, String serverName, String strPath) {
        Cookie cookie = new Cookie(strCookieName, "");
        cookie.setMaxAge(0);
        cookie.setDomain(serverName);
        cookie.setPath(strPath);
        return cookie;
    }    
    
    
    public static String getParamValue(String query, String param) {

        String paramValue = "";
        if (query != null && query.length() != 0) {
            String[] paramsArray = query.split("\\&");
            for (String parameter : paramsArray) {
                if (parameter.startsWith(param)) {
                    paramValue = parameter.substring(parameter.indexOf("=") + 1);
                    break;
                }
            }
        }
        return paramValue;
    }
    
    public static Cookie setCookie(String cookieName, String cookieValue, 
            String domainName, String path) {
                Cookie returnURLCookie = new Cookie(cookieName, cookieValue);
                returnURLCookie.setDomain(domainName);
                returnURLCookie.setPath(path);
                debugMessage("OAuthUtil.setCookie: Cookie " + cookieName + " set " +
                        "in the domain " + domainName + " and path " + path);
                return returnURLCookie;
    }
    
    static boolean isEmpty(String value) {
        return value == null || "".equals(value);
    }
    
    public static Set addToSet(Set<String> set, String attribute) {
	set.add(attribute);
	return set;
    }
    
    public static void sendEmail(String from, String emailAddress, String activCode,
              Map<String, String> SMTPConfig, ResourceBundle bundle, String linkURL)
    throws NoEmailSentException {
        try {
            String gatewayEmailImplClass = SMTPConfig.get(KEY_EMAIL_GWY_IMPL);
            if (from != null || emailAddress != null) {
                // String from = bundle.getString(MESSAGE_FROM);
                String subject = bundle.getString(MESSAGE_SUBJECT);
                String message = bundle.getString(MESSAGE_BODY);
                message = message.replace("#ACTIVATION_CODE#", activCode);
                
                String link = "";
                try {
                     link = linkURL + "?" + PARAM_ACTIVATION + "=" +
                     URLEncoder.encode(activCode, "UTF-8");
                } catch (UnsupportedEncodingException ex) {
                   debugError("OAuthUtil.sendEmail(): Error while encoding", ex);
                }

                message = message.replace("#ACTIVATION_LINK#", link.toString());
                EmailGateway gateway =  Class.forName(gatewayEmailImplClass).
                        asSubclass(EmailGateway.class).newInstance();
                gateway.sendEmail(from, emailAddress, subject, message, SMTPConfig);
                debugMessage("OAuthUtil.sendEmail(): sent email to " +
                            emailAddress);
            } else {
                  debugMessage("OAuthUtil.sendEmail(): unable to send email");

            }
        } catch (ClassNotFoundException cnfe) {
            debugError("OAuthUtil.sendEmail(): " + "class not found " +
                        "EmailGateway class", cnfe);
        } catch (InstantiationException ie) {
            debugError("OAuthUtil.sendEmail(): " + "can not instantiate " +
                        "EmailGateway class", ie);
        } catch (IllegalAccessException iae) {
            debugError("OAuthUtil.sendEmail(): " + "can not access " +
                        "EmailGateway class", iae);
        }
    }
    
    public static void debugMessage(String message) {
        if (debug.messageEnabled()) {
            debug.message(message);
        }
    }

    public static void debugWarning(String message) {
        if (debug.warningEnabled()) {
            debug.warning(message);
        }
    }
    
    public static void debugError(String message, Throwable t) {
        if (debug.errorEnabled()) {
            debug.error(message, t);
        }
    }
    
    public static void debugError(String message) {
        if (debug.errorEnabled()) {
            debug.error(message);
        }
    }
    
    public static String oAuthEncode(String toEncode) throws UnsupportedEncodingException {
        if (toEncode != null && !toEncode.isEmpty()) {
            return URLEncoder.encode(toEncode, "UTF-8").
                    replace("+", "%20").
                    replace("*", "%2A").
                    replace("%7E", "~");

        } else {
            return "";
        }
    }
            
}
