/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2005 Sun Microsystems Inc. All Rights Reserved
 *
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at
 * https://opensso.dev.java.net/public/CDDLv1.0.html or
 * opensso/legal/CDDLv1.0.txt
 * See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at opensso/legal/CDDLv1.0.txt.
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * $Id: LoginServlet.java,v 1.7 2009/03/14 03:50:43 manish_rustagi Exp $
 *
 */



package com.sun.identity.authentication.distUI;

import com.iplanet.jato.CompleteRequestException;
import com.iplanet.jato.RequestContext;
import com.iplanet.jato.RequestContextImpl;
import com.iplanet.jato.ViewBeanManager;
import com.sun.identity.authentication.client.AuthClientUtils;
import com.sun.identity.common.ISLocaleContext;
import com.sun.identity.common.RequestUtils;
import com.sun.identity.shared.debug.Debug;
import com.sun.identity.shared.locale.L10NMessageImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 *
 *
 */
public class LoginServlet
extends com.sun.identity.authentication.distUI.AuthenticationServletBase {
    /**
     * Creates <code>LoginServlet</code> object.
     */
    public LoginServlet() {
        super();
    }
    
    protected void initializeRequestContext(RequestContext requestContext) {
        super.initializeRequestContext(requestContext);
        
        // Set a view bean manager in the request context.  This must be
        // done at the module level because the view bean manager is
        // module specifc.
        ViewBeanManager viewBeanManager =
        new ViewBeanManager(requestContext,PACKAGE_NAME);
        
        ((RequestContextImpl)requestContext).setViewBeanManager(
        viewBeanManager);
        
        HttpServletRequest request = requestContext.getRequest();
        HttpServletResponse response = requestContext.getResponse();
        
        // Check content length
        try {
            RequestUtils.checkContentLength(request);
        } catch (L10NMessageImpl e) {
            if (debug.messageEnabled()) {
                ISLocaleContext localeContext = new ISLocaleContext();
                localeContext.setLocale(request);
                java.util.Locale locale = localeContext.getLocale();
                if (debug.messageEnabled()) {
                    debug.message("Error in check content lenght : "
                    + e.getL10NMessage(locale));
                }
            }
            AuthExceptionViewBean vb = (AuthExceptionViewBean)
            viewBeanManager.getViewBean(
            com.sun.identity.authentication.distUI.AuthExceptionViewBean.class);
            vb.forwardTo(requestContext);
            throw new CompleteRequestException();
        }
        
        // Check whether this is the correct server to accept the client
        // response.
        String authCookieValue = AuthClientUtils.getAuthCookieValue(request);
        if ((authCookieValue != null) && (authCookieValue.length() != 0) &&
            (!authCookieValue.equalsIgnoreCase("LOGOUT"))) {
            //if cookie server does not match to this local server then
            //send Auth request to cookie (original) server
            if (debug.messageEnabled()) {
                debug.message("authCookieValue : " + authCookieValue);
            }
            
            if ((authCookieValue != null) && (authCookieValue.length() != 0) &&
                    (!AuthClientUtils.isLocalServer(authCookieValue, false))) {

                boolean isRoutingAllowed = 
                    AuthClientUtils.isDistAuthServerTrusted(authCookieValue);
                if(!isRoutingAllowed){
                    if (debug.messageEnabled()) {
                        debug.message("LoginServlet.initializeRequestContext()"
                        + ": Routing the request to distauth server " + 
                        "with Login URL " + authCookieValue +
                        " is not allowed");
                    }
                    
                    try{
                        PrintWriter out = response.getWriter();
                        out.print("<h1>" + authCookieValue +
                       	" is not the trusted server</h1>");
                    }catch(IOException ioe){
                        if (debug.messageEnabled()) {
                            debug.message("initializeRequestContext(): " +
                                ioe.getMessage());
                        }                    	
                    }
                    throw new CompleteRequestException();            		
                }

                debug.message("Routing the request to Original Auth server");
                try {
                    HashMap origRequestData =
                        AuthClientUtils.sendAuthRequestToOrigServer(
                            request,response,authCookieValue);

                    if (debug.messageEnabled()) {
                        debug.message("origRequestData : " + origRequestData);
                    }
                    String redirect_url = null;
                    String clientType = null;
                    String output_data = null;
                    if (origRequestData != null && !origRequestData.isEmpty()) {
                        redirect_url =
                            (String)origRequestData.get("AM_REDIRECT_URL");
                        output_data =
                            (String)origRequestData.get("OUTPUT_DATA");
                        clientType =
                            (String)origRequestData.get("AM_CLIENT_TYPE");
                    } else {
                        Set domainsList = AuthClientUtils.getCookieDomains();

                        if (domainsList != null) {
                            Iterator domains = domainsList.iterator();
                            String domain = null;

                            while (domains.hasNext()) {
                                domain = (String) domains.next();
                                response.addCookie(AuthClientUtils.createCookie(AuthClientUtils.getAuthCookieName(), "LOGOUT", domain));

                                if (debug.messageEnabled()) {
                                    debug.message("LoginServlet reset Auth Cookie in domain: " + domain);
                                }
                            }
                        }
                    }
                    if (((redirect_url != null) && !redirect_url.equals("")) &&
                        AuthClientUtils.isGenericHTMLClient(clientType)) {
                        debug.message("Redirecting the response");
                        response.sendRedirect(redirect_url);
                    }
                    if ((output_data != null) && (!output_data.equals(""))) {
                        debug.message("Printing the forwarded response");
                        java.io.PrintWriter outP = response.getWriter();
                        outP.println(output_data);
                    }
                } catch (Exception e) {
                    if (debug.messageEnabled()) {
                        debug.message("LoginServlet error in Request Routing : "
                            + e.toString());
                    }

                    Set domainsList = AuthClientUtils.getCookieDomains();

                    if (domainsList != null) {
                        Iterator domains = domainsList.iterator();
                        String domain = null;

                        while (domains.hasNext()) {
                            domain = (String) domains.next();
                            response.addCookie(AuthClientUtils.createCookie(AuthClientUtils.getAuthCookieName(), "LOGOUT", domain));

                            if (debug.messageEnabled()) {
                                debug.message("LoginServlet reset Auth Cookie in domain: " + domain);
                            }
                        }
                    }
                }
                throw new CompleteRequestException();
            }
        }
    }    
   

    /**
     * Returns url for auth module.
     * @return url for auth module.
     */
    public String getModuleURL() {
        // The superclass can be configured from init params specified at
        // deployment time.  If the superclass has been configured with
        // a different module URL, it will return a non-null value here.
        // If it has not been configured with a different URL, we use our
        // (hopefully) sensible default.
        String result = super.getModuleURL();
        if (result != null)
            return result;
        else
            return DEFAULT_MODULE_URL;
    }
    
    /**
     *
     *
     */
    protected void onSessionTimeout(RequestContext requestContext)
    throws ServletException {
        // Do nothing
    }
    
    
    ////////////////////////////////////////////////////////////////////////////
    // Class variables
    ////////////////////////////////////////////////////////////////////////////
    
    /** Default module uri. */
    public static final String DEFAULT_MODULE_URL="../UI";
    /** Confiured page name for configured servlet */
    public static String PACKAGE_NAME=
    getPackageName(LoginServlet.class.getName());
    
    private static final String REDIRECT_JSP = "Redirect.jsp";
    
    // the debug file
    private static Debug debug = Debug.getInstance("amLoginServlet");    
    
    ////////////////////////////////////////////////////////////////////////////
    // Instance variables
    ////////////////////////////////////////////////////////////////////////////
}

