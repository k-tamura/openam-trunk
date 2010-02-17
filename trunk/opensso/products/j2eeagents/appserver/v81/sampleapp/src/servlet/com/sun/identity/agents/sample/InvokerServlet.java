/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2006 Sun Microsystems Inc. All Rights Reserved
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
 * $Id: InvokerServlet.java,v 1.2 2008/06/25 05:52:10 qcheng Exp $
 *
 */

package com.sun.identity.agents.sample;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import javax.ejb.CreateException;
import javax.naming.NamingException;
import javax.servlet.ServletException;

public class InvokerServlet extends SampleServletBase {

    public void processRequest(HttpServletRequest request, 
        HttpServletResponse response) throws ServletException, IOException
    {
        request.setAttribute("RESULT", "OK");
        String ejbMessage = null;
        boolean ejbAccess = false;
        try {
            ejbMessage = invokeSecurityAwareEJB();
            ejbAccess  = true;
        } catch (Exception ex) {
            ejbMessage = ex.getMessage();
        }
        if (ejbAccess) {
            request.setAttribute("EJB-ACCESS", "OK");
        }
        if (ejbMessage != null) {
            request.setAttribute("EJB-MESSAGE", ejbMessage);
        }
        response.setContentType("text/html");
        RequestDispatcher dispatcher = 
	    request.getRequestDispatcher("/jsp/invokerservletresult.jsp");
        dispatcher.forward(request, response);
    }

    private String invokeSecurityAwareEJB() throws Exception {
        InitialContext ctx = new InitialContext(null);
        SecurityAwareEJBHome ejbHome = 
            (SecurityAwareEJBHome) ctx.lookup("SecurityAwareEJB");
            
        SecurityAwareEJB ejb = (SecurityAwareEJB) ejbHome.create();
        return ejb.getDetails();
    }
}
