<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta content="text/html; charset=ISO-8859-1" http-equiv="content-type" />
<title>Sample Application</title>
<!--
   DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
  
   Copyright (c) 2008 Sun Microsystems Inc. All Rights Reserved
  
   The contents of this file are subject to the terms
   of the Common Development and Distribution License
   (the License). You may not use this file except in
   compliance with the License.

   You can obtain a copy of the License at
   https://opensso.dev.java.net/public/CDDLv1.0.html or
   opensso/legal/CDDLv1.0.txt
   See the License for the specific language governing
   permission and limitations under the License.

   When distributing Covered Code, include this CDDL
   Header Notice in each file and include the License file
   at opensso/legal/CDDLv1.0.txt.
   If applicable, add the following below the CDDL Header,
   with the fields enclosed by brackets [] replaced by
   your own identifying information:
   "Portions Copyrighted [year] [name of copyright owner]"

   $Id: securityawareservletresult.jsp,v 1.1 2009/01/30 12:12:21 kalpanakm Exp $

-->
<%
    String result = (String) request.getAttribute("RESULT");
    boolean success = false;
    if (result != null && result.equals("OK")) {
        success = true;
    }
    String details = null;
    if (success) {
        details = (String) request.getAttribute("DETAILS");
    }
%>
  <style type="text/css">
<!-- 

body,td,p,div,span,a,input,big,small{font-family:arial,helvetica,sans-serif}
body,td,p,div,span,a,input{font-size:10pt}
small,.small,small span,.small span,.small a,small a,div.sitelinks,div.sitelinks a,div.footlinks,div.footlinks a{font-size:9pt}
big,.big,big span,.big span,.big a,big a{font-size:11pt}
body,td,p,div,div.sitelinks a#homelink{color:#333}
input.buttonred{background:#acacac;cursor:hand;color:#FFF;height:1.4em;font-weight:bold;padding:0px;margin:0px;border:0px none #000}
input.medium{width:120px;height:18px}
a{text-decoration:none}
a:visited{color:#96C}
a:link,a.named:visited,div.breadcrumb a:visited,div.sitelinks a:visited,div.footlinks a:visited{color:#594FBF}
a:hover{text-decoration:underline}
.footlinks{padding:7px 0px}
.toolbar{padding:7px 0px 3px 0px}
.homenav{padding:7px 0px 0px 0px}
.homeftr{padding:0px}
.htitle div{padding:11px 0px 0px 0px}
.hitemtop div{padding:6px 0px 2px 0px}
.hitem div{padding:3px 0px 2px 0px}
.hitemverybottom div{padding:3px 0px 0px 0px}
.htitle div{font-weight:bold}
.spot div{padding:6px 0px 6px 0px}
.spottop div{padding:0px 0px 6px 0px}
-->
  </style>
</head>
<body>
<table style="width: 800px; text-align: left;" border="0" cellpadding="2" cellspacing="2">
  <tbody>
    <tr>
      <td style="vertical-align: top;"><a href="http://www.sun.com/" id="homelink">sun.com</a> </td>
      <td style="vertical-align: top; text-align: right;">
      <a href="http://www.sun.com/software/products/access_mgr/index.html" id="homelink">OpenSSO<br>
      </a></td>
      <td style="vertical-align: top;">&nbsp;<br>
      </td>
    </tr>
    <tr>
      <td style="background-color: rgb(89, 79, 191); vertical-align: top; width: 150px; text-align: left;">
      <img alt="Sun Microsystems, Inc." src="/agentsample/images/sun_logo.gif" style="width: 107px; height: 54px;" /><br>
      <br>
      </td>
      <td style="text-align: left; background-color: rgb(251, 226, 73); vertical-align: middle; font-family: andale sans;">
      <span style="font-weight: bold;">J2EE Policy Agent Sample Application</span><br>
      </td>
      <td style="vertical-align: top; width: 50px;">&nbsp;<br>
      </td>
    </tr>
    <tr>
      <td style="vertical-align: top; width: 150px;">
      <table style="text-align: left; width: 100%;" border="0" cellpadding="0" cellspacing="0">
        <tbody>
          <tr valign="top">
            <td class="htitle" style="vertical-align: top; text-align: left;">
            <hr class="menutop" size="1">
            <div>Sample Application</div>
            <hr class="light"> </td>
          </tr>
          <tr>
            <td style="vertical-align: top;"> <a href="/agentsample/public/welcome.html">Welcome </a>
            <hr class="faint"><a href="/agentsample/public/declarativesecurity.html">J2EE Declarative Security</a>
            <hr class="faint"><a href="/agentsample/public/programmaticsecurity.html">J2EE Security API</a>
            <hr class="faint"><a href="/agentsample/public/urlpolicy.html">URL Policy Enforcement</a>
            <hr class="faint"><a href="/agentsample/jsp/showHttpHeaders.jsp">Show HTTP Headers</a>
            <hr class="faint">
            </td>
          </tr>
          <tr>
            <td class="htitle" style="vertical-align: top; text-align: left;">
            <hr class="menutop" size="1">
            <div>Other Resources<br>
            </div>
            <hr class="light"> </td>
          </tr>
          <tr>
            <td style="vertical-align: top;">
	    <a href="http://openam.forgerock.org/docs.html">J2EE Agents Guide</a><br>
            <hr class="faint">
            <a href="http://java.sun.com/j2ee">J2EE Documentation<br></a>
            <hr class="faint"> 
            </td>
          </tr>
        </tbody>
      </table>
      <br>
      </td>
      <td style="vertical-align: top;">
      <table style="text-align: left; width: 572px; height: 394px;" border="0" cellpadding="2" cellspacing="2">
        <tbody>
          <tr>
            <td style="vertical-align: top; width: 75px; height: 75px;">&nbsp;<br>
            </td>
            <td style="vertical-align: top; height: 75px;">
		<% if (success) { %>
		    <br><b>Successful Invocation: Please verify</b><br>
		<% } else { %>
		    <br><b>Configuration Error</b><br>
		<% } %>
            </td>
            <td style="vertical-align: top; width: 75px; height: 75px;">&nbsp;<br>
            </td>
          </tr>
          <tr>
            <td style="vertical-align: top; width: 75px;">&nbsp;<br>
            </td>
            <td style="vertical-align: top;"> 
		<% if (success) { %>
			<br>This page was dispatched by the Security Aware Servlet. The following details were 
			evaluated by this servlet:<br><br> <code><%=details%></code>.<br>
		<% } else { %>
			<br> The dispatched page was not routed via the Security Aware Servlet. This
			condition can happen if the page was accessed directly instead of being routed via
			the Security Aware Servlet.<br>
		<% } %>
	        <br>
		You can return to 
		<a href="/agentsample/public/programmaticsecurity.html">J2EE Security API</a> page to try again.<br>
            </td>
            <td style="vertical-align: top; width: 75px;">&nbsp;<br>
            </td>
          </tr>
        </tbody>
      </table>
      <br>
      </td>
      <td style="vertical-align: top; width: 50px;">&nbsp;<br>
      </td>
    </tr>
    <tr>
      <td style="vertical-align: top; width: 150px;">
      <hr class="menutop"></td>
      <td style="vertical-align: top;">
      <hr class="menutop">
      <div style="text-align: right;"></div>
      </td>
      <td style="vertical-align: top; width: 50px;">&nbsp;<br>
      </td>
    </tr>
  </tbody>
</table>
<br>
<br>
</body>
</html>
