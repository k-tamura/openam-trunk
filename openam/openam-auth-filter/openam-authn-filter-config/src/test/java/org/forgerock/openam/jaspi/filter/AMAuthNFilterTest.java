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
* Copyright 2013-2014 ForgeRock AS.
*/

package org.forgerock.openam.jaspi.filter;

import org.forgerock.jaspi.runtime.JaspiRuntime;
import org.forgerock.jaspi.runtime.config.inject.RuntimeInjector;
import org.forgerock.jaspi.utils.FilterConfiguration;
import org.forgerock.json.resource.NotFoundException;
import org.forgerock.openam.forgerockrest.RestDispatcher;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.security.auth.message.AuthException;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class AMAuthNFilterTest {

    private AMAuthNFilter amAuthNFilter;

    private RuntimeInjector runtimeInjector;
    private JaspiRuntime jaspiRuntime;
    private RestDispatcher restDispatcher;

    @BeforeMethod
    public void setUp() throws AuthException, ServletException {


        FilterConfiguration filterConfiguration = mock(FilterConfiguration.class);
        runtimeInjector = mock(RuntimeInjector.class);
        jaspiRuntime = mock(JaspiRuntime.class);
        restDispatcher = mock(RestDispatcher.class);

        amAuthNFilter = new AMAuthNFilter(restDispatcher, filterConfiguration);

        given(runtimeInjector.getInstance(JaspiRuntime.class)).willReturn(jaspiRuntime);

        FilterConfig filterConfig = mock(FilterConfig.class);
        given(filterConfiguration.get(eq(filterConfig), anyString(), anyString(), anyString()))
                .willReturn(runtimeInjector);
        amAuthNFilter.init(filterConfig);
    }

    @Test
    public void shouldAllowUnauthenticatedRestAuthEndpointWithPOST() throws IOException, ServletException,
            NotFoundException {

        //Given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        given(request.getContextPath()).willReturn("/openam");
        given(request.getRequestURI()).willReturn("/openam/json/authenticate");
        given(request.getMethod()).willReturn("POST");
        given(request.getRequestURL()).willReturn(new StringBuffer("http://example.com:8080/openam"));
        given(request.getContextPath()).willReturn("/openam");

        Map<String, String> details = new HashMap<String, String>();
        details.put("resourceName", "/authenticate");
        given(restDispatcher.getRequestDetails("/authenticate")).willReturn(details);

        //When
        amAuthNFilter.doFilter(request, response, filterChain);

        //Then
        verify(jaspiRuntime, never()).processMessage(request, response, filterChain);
    }

    @Test
    public void shouldAllowUnauthenticatedRestUsersEndpointWithPOSTAndActionRegister() throws IOException,
            ServletException, NotFoundException {

        //Given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        given(request.getContextPath()).willReturn("/openam");
        given(request.getRequestURI()).willReturn("/openam/json/users");
        given(request.getQueryString()).willReturn("other1=valueA&_action=register&other2=valueb");
        given(request.getMethod()).willReturn("POST");

        Map<String, String> details = new HashMap<String, String>();
        details.put("resourceName", "/users");
        given(restDispatcher.getRequestDetails("/users")).willReturn(details);

        //When
        amAuthNFilter.doFilter(request, response, filterChain);

        //Then
        verify(jaspiRuntime, never()).processMessage(request, response, filterChain);
    }

    @Test
    public void shouldAllowUnauthenticatedRestUsersEndpointWithPOSTAndActionConfirm() throws IOException,
            ServletException, NotFoundException {

        //Given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        given(request.getContextPath()).willReturn("/openam");
        given(request.getRequestURI()).willReturn("/openam/json/users");
        given(request.getQueryString()).willReturn("other1=valueA&_action=confirm&other2=valueb");
        given(request.getMethod()).willReturn("POST");

        Map<String, String> details = new HashMap<String, String>();
        details.put("resourceName", "/users");
        given(restDispatcher.getRequestDetails("/users")).willReturn(details);

        //When
        amAuthNFilter.doFilter(request, response, filterChain);

        //Then
        verify(jaspiRuntime, never()).processMessage(request, response, filterChain);
    }

    @Test
    public void shouldAllowUnauthenticatedRestUsersEndpointWithPOSTAndActionForgotPassword() throws IOException,
            ServletException, NotFoundException {

        //Given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        given(request.getContextPath()).willReturn("/openam");
        given(request.getRequestURI()).willReturn("/openam/json/users");
        given(request.getQueryString()).willReturn("other1=valueA&_action=forgotPassword&other2=valueb");
        given(request.getMethod()).willReturn("POST");

        Map<String, String> details = new HashMap<String, String>();
        details.put("resourceName", "/users");
        given(restDispatcher.getRequestDetails("/users")).willReturn(details);

        //When
        amAuthNFilter.doFilter(request, response, filterChain);

        //Then
        verify(jaspiRuntime, never()).processMessage(request, response, filterChain);
    }

    @Test
    public void shouldAllowUnauthenticatedRestUsersEndpointWithPOSTAndActionForgotPasswordReset() throws IOException,
            ServletException, NotFoundException {

        //Given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        given(request.getContextPath()).willReturn("/openam");
        given(request.getRequestURI()).willReturn("/openam/json/users");
        given(request.getQueryString()).willReturn("other1=valueA&_action=forgotPasswordReset&other2=valueb");
        given(request.getMethod()).willReturn("POST");

        Map<String, String> details = new HashMap<String, String>();
        details.put("resourceName", "/users");
        given(restDispatcher.getRequestDetails("/users")).willReturn(details);

        //When
        amAuthNFilter.doFilter(request, response, filterChain);

        //Then
        verify(jaspiRuntime, never()).processMessage(request, response, filterChain);
    }

    @Test
    public void shouldANotllowUnauthenticatedRestUsersEndpointWithPOSTAndActionForgotIdFromSession() throws IOException,
            ServletException, NotFoundException {

        //Given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        given(request.getContextPath()).willReturn("/openam");
        given(request.getRequestURI()).willReturn("/openam/json/users");
        given(request.getQueryString()).willReturn("other1=valueA&_action=idFromSession&other2=valueb");
        given(request.getMethod()).willReturn("POST");
        given(request.getRequestURL()).willReturn(new StringBuffer("http://www.example.com"));

        Map<String, String> details = new HashMap<String, String>();
        details.put("resourceName", "/users");
        given(restDispatcher.getRequestDetails("/users")).willReturn(details);

        //When
        amAuthNFilter.doFilter(request, response, filterChain);

        //Then
        verify(jaspiRuntime).processMessage(request, response, filterChain);
    }

    @Test
    public void shouldNotAllowUnauthenticatedRestUsersEndpointWithGET() throws IOException,
            ServletException, NotFoundException {

        //Given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        given(request.getContextPath()).willReturn("/openam");
        given(request.getRequestURI()).willReturn("/openam/json/users");
        given(request.getMethod()).willReturn("GET");
        given(request.getRequestURL()).willReturn(new StringBuffer("http://www.example.com"));

        Map<String, String> details = new HashMap<String, String>();
        details.put("resourceName", "/users");
        given(restDispatcher.getRequestDetails("/users")).willReturn(details);

        //When
        amAuthNFilter.doFilter(request, response, filterChain);

        //Then
        verify(jaspiRuntime).processMessage(request, response, filterChain);
    }

    @Test
    public void shouldNotAllowUnauthenticatedRestUsersEndpointWithPUT() throws IOException,
            ServletException {

        //Given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        given(request.getContextPath()).willReturn("/openam");
        given(request.getRequestURI()).willReturn("/openam/json/users");
        given(request.getMethod()).willReturn("PUT");
        given(request.getRequestURL()).willReturn(new StringBuffer("http://www.example.com"));

        //When
        amAuthNFilter.doFilter(request, response, filterChain);

        //Then
        verify(jaspiRuntime).processMessage(request, response, filterChain);
    }

    @Test
    public void shouldNotAllowUnauthenticatedRestUsersEndpointWithPATCH() throws IOException,
            ServletException {

        //Given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        given(request.getContextPath()).willReturn("/openam");
        given(request.getRequestURI()).willReturn("/openam/json/users");
        given(request.getMethod()).willReturn("PATCH");
        given(request.getRequestURL()).willReturn(new StringBuffer("http://www.example.com"));

        //When
        amAuthNFilter.doFilter(request, response, filterChain);

        //Then
        verify(jaspiRuntime).processMessage(request, response, filterChain);
    }

    @Test
    public void shouldNotAllowUnauthenticatedRestUsersEndpointWithDELETE() throws IOException,
            ServletException {

        //Given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        given(request.getContextPath()).willReturn("/openam");
        given(request.getRequestURI()).willReturn("/openam/json/users");
        given(request.getMethod()).willReturn("DELETE");
        given(request.getRequestURL()).willReturn(new StringBuffer("http://www.example.com"));

        //When
        amAuthNFilter.doFilter(request, response, filterChain);

        //Then
        verify(jaspiRuntime).processMessage(request, response, filterChain);
    }
}
