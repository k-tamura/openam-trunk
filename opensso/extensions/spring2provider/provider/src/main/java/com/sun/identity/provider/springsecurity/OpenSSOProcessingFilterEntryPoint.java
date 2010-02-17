/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2008 Sun Microsystems Inc. All Rights Reserved
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
 * 
 * "Portions Copyrighted 2008 Robert Dale <robdale@gmail.com>"
 *
 * $Id: OpenSSOProcessingFilterEntryPoint.java,v 1.2 2009/10/08 21:34:54 wstrange Exp $
 *
 */
package com.sun.identity.provider.springsecurity;

import com.sun.identity.shared.debug.Debug;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.AuthenticationException;
import org.springframework.security.ui.AuthenticationEntryPoint;

/**
 *  It is in charge of validating the cookie which says if the user is logged in and
getting its credentials. This class is the basis of Single-Sign On implementation
 */
public class OpenSSOProcessingFilterEntryPoint implements AuthenticationEntryPoint {

    private static Debug debug = Debug.getInstance("amSpring");
    private String loginUrl;
    private String loginParameters;
    private String gotoUrl;
    private String scheme;
    private String serverName;
    private String serverPort;
    private String webContext;
    private String filterProcessesUrl;

    public void commence(ServletRequest request, ServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            request = HttpUtil.unwrapOriginalHttpServletRequest(httpRequest);
            redirectToLoginUrl(httpRequest, httpResponse);
        } else {
            debug.error("Request: " + request.getClass() +" Response: " + response.getClass());
            throw new ServletException("Handles only HttpServletRequest/Response");
        }
    }

    private void redirectToLoginUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuffer redirect = new StringBuffer();
        redirect.append(getLoginUrl());
        boolean hasArguments =  redirect.indexOf("?") > 0;

        if( hasArguments )
            redirect.append("&goto=");
        else
            redirect.append("?goto=");
        redirect.append(buildGotoUrl(request));

        if (getLoginParameters() != null && getLoginParameters().length() > 0) {
            redirect.append("&").append(loginParameters);
        }

        debug.message("Redirecting to " + redirect);
        response.sendRedirect(redirect.toString());
    }

    private String buildGotoUrl(HttpServletRequest request) {
        StringBuffer result = new StringBuffer();
        if (getGotoUrl() != null && getGotoUrl().length() > 0) {
            result.append(getGotoUrl());
        } else {
            result.append(getScheme() == null ? request.getScheme() : getScheme());
            result.append("://");
            result.append(getServerName() == null ? request.getServerName() : getServerName());
            if (getServerPort() != null) {
                result.append(":").append(getServerPort());
            } else if (request.getServerPort() != 80 && request.getServerPort() != 443) {
                result.append(":").append(request.getServerPort());
            }
            result.append(request.getContextPath());
            if (getFilterProcessesUrl() != null) {
                result.append(getFilterProcessesUrl());
            }
        }
        return result.toString();
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getLoginParameters() {
        return loginParameters;
    }

    public void setLoginParameters(String loginParameters) {
        this.loginParameters = loginParameters;
    }

    public String getGotoUrl() {
        return gotoUrl;
    }

    public void setGotoUrl(String backUrl) {
        this.gotoUrl = backUrl;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getWebContext() {
        return webContext;
    }

    public void setWebContext(String webContext) {
        this.webContext = webContext;
    }

    public String getFilterProcessesUrl() {
        return filterProcessesUrl;
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }
}
