<%--
   DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
  
   Copyright (c) 2006 Sun Microsystems Inc. All Rights Reserved
  
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

   $Id: MAPCreateDeviceTwo.jsp,v 1.2 2008/06/25 05:44:52 qcheng Exp $

--%>




<%@ page info="MAPCreateDeviceTwo" language="java" %>
<%@taglib uri="/WEB-INF/jato.tld" prefix="jato" %>
<%@taglib uri="/WEB-INF/cc.tld" prefix="cc" %>

<jato:useViewBean
    className="com.sun.identity.console.service.MAPCreateDeviceTwoViewBean"
    fireChildDisplayEvents="true" >

<cc:i18nbundle baseName="amConsole" id="amConsole"
    locale="<%=((com.sun.identity.console.base.AMViewBeanBase)viewBean).getUserLocale()%>"/>

<cc:header name="hdrCommon" 
    pageTitle="webconsole.title" 
    bundleID="amConsole" 
    copyrightYear="2004" 
    fireDisplayEvents="true">

<cc:form name="MAPCreateDeviceTwo" method="post" defaultCommandChild="/btnCreate">

<cc:secondarymasthead name="secMhCommon" />

<%-- PAGE CONTENT --------------------------------------------------------- --%>
<table border="0" cellpadding="10" cellspacing="0" width="100%">
    <tr>
	<td>
	<cc:alertinline name="ialertCommon" bundleID="amConsole" />
	</td>
    </tr>
</table>

<cc:pagetitle name="pgtitle" bundleID="amConsole" pageTitleText="map.client.manager.createDevice.window.title" showPageTitleSeparator="true" viewMenuLabel="" pageTitleHelpMessage="" showPageButtonsTop="true" showPageButtonsBottom="false" />

<cc:hidden name="tfParentId" />
<cc:hidden name="tfClientType" />
<cc:propertysheet name="propertyAttributes" bundleID="amConsole" showJumpLinks="true" />

<%-- PAGE CONTENT --------------------------------------------------------- --%>

</cc:form>
</cc:header>
</jato:useViewBean>
