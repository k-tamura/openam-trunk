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
 * $Id: HttpServletRequestHelper.java,v 1.3 2009/01/21 19:00:04 kanduls Exp $
 *
 */

package com.sun.identity.agents.common;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletInputStream;

import com.sun.identity.agents.arch.Module;
import com.sun.identity.agents.arch.SurrogateBase;
import com.sun.identity.agents.util.EnumerationAdapter;


/**
 * The class handles HttpServletRequest
 */
public class HttpServletRequestHelper extends SurrogateBase 
        implements IHttpServletRequestHelper 
{

    public HttpServletRequestHelper(Module module) {
        super(module);
    }
    
    public void initialize(String dateFormatString, Map attributes, 
            ServletInputStream inputStream) 
    {
        setUserAttributes(attributes);
        setDateFormatString(dateFormatString);
        setServletInputStream(inputStream);
    }

    public String toString() {
        return "HELPER: " + getUserAttributes() + ", DATE-FMT: "
               + getDateFormatString();
    }

    public Enumeration getHeaders(String name, Enumeration innerHeaders) {
        ArrayList headerValues = new ArrayList();
        if(getUserAttributes().containsKey(name)) {
            Set ldapAttrValues = (Set) getUserAttributes().get(name);
            if((ldapAttrValues != null) && (ldapAttrValues.size() > 0)) {
                headerValues.addAll(ldapAttrValues);
            }
        } else {
            while(innerHeaders.hasMoreElements()) {
                headerValues.add(innerHeaders.nextElement());
            }            
        }

        EnumerationAdapter result =
            new EnumerationAdapter(headerValues.iterator());

        if(isLogMessageEnabled()) {
            logMessage("HttpServletRequestHelper.getHeaders(" + name
                       + ") => " + headerValues);
        }

        return result;
    }

    public Enumeration getHeaderNames(Enumeration innerHeaderNames) {
        ArrayList headerNameList = new ArrayList();
        headerNameList.addAll(getUserAttributes().keySet());

        while(innerHeaderNames.hasMoreElements()) {
            String nextInnerHeader = (String) innerHeaderNames.nextElement();
            if (!headerNameList.contains(nextInnerHeader)) {
                headerNameList.add(nextInnerHeader);
            }
        }

        EnumerationAdapter result =
            new EnumerationAdapter(headerNameList.iterator());

        if(isLogMessageEnabled()) {
            logMessage("HttpServletRequestHelper.getHeaderNames() => "
                       + headerNameList);
        }

        return result;
    }

    public String getHeader(String name, String innerValue) {
        String result = null;
        if(getUserAttributes().containsKey(name)) {
            Set values = (Set) getUserAttributes().get(name);
            if((values != null) && (values.size() > 0)) {
                result = (String) values.iterator().next();
            }
        } else {
            result = innerValue;
        }

        if(isLogMessageEnabled()) {
            logMessage("HttpServletRequestHelper.getHeader(" + name + ") => "
                       + result);
        }
        return result;
    }

    public long getDateHeader(String name, long innerValue) {
        long result = -1L;
        String headerValue = getHeader(name, String.valueOf(innerValue));
        if (headerValue != null && headerValue.trim().length() > 0) {
            int index = headerValue.indexOf(";");
            String dateString = null;
            if(index == -1) {
                dateString = headerValue;
            } else {
                dateString = headerValue.substring(0, index);
            }
            
            try {
                SimpleDateFormat sdf =
                    new SimpleDateFormat(getDateFormatString());

                result = sdf.parse(dateString).getTime();
            } catch(Exception ex) {
                throw new IllegalArgumentException("Invalid date header: "
                                                   + dateString);
            }
        } else {
            result = innerValue;
        }
        
        if(isLogMessageEnabled()) {
            logMessage("HttpServletRequestHelper.getDateHeader(" + name
                       + ") => " + result);
        }

        return result;
    }

    public int getIntHeader(String name, int innerValue) {
        int result = -1;
        String headerValue = getHeader(name, String.valueOf(innerValue));

        if((headerValue != null) && (headerValue.trim().length() > 0)) {
            try {
                result = Integer.parseInt(headerValue);
            } catch(Exception ex) {
                throw new IllegalArgumentException("Invalid int header: "
                                                   + headerValue);
            }
        }
        if(isLogMessageEnabled()) {
            logMessage("HttpServletRequestHelper.getIntHeader(" + name
                       + ") => " + result);
        }
        return result;
    }
    
    public Map getUserAttributes() {
        return _userAttributes;
    }
    
    public void addUserAttributes(Map newAttributes) {
        Map oldAttributes = getUserAttributes();
        Map userAttributes = new HashMap();
        if (oldAttributes != null) {
            userAttributes.putAll(oldAttributes);
        }
        if (newAttributes != null) {
            userAttributes.putAll(newAttributes);
        }
        setUserAttributes(userAttributes);
    }
    
    public ServletInputStream getInputStream(ServletInputStream inputStream) {
        ServletInputStream result = inputStream;
        if (_servletInputStream != null) {
            result = _servletInputStream;
        }
        return result;
    }

    private void setUserAttributes(Map map) {
        _userAttributes = map;
    }
    
    /**
     * Method setDateFormatString
     *
     *
     * @param dateFormat
     *
     */
    private void setDateFormatString(String dateFormat) {
        _dateFormatString = dateFormat;
    }

    /**
     * Method getDateFormatString
     *
     *
     * @return
     *
     */
    protected String getDateFormatString() {
        return _dateFormatString;
    }
    
    private void setServletInputStream(ServletInputStream inputStream) {
        _servletInputStream = inputStream;
    }    

    private Map    _userAttributes;
    private String _dateFormatString;
    private ServletInputStream _servletInputStream;
}
