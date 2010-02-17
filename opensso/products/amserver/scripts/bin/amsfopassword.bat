@echo off
:
: DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
:  
: Copyright (c) 2006 Sun Microsystems Inc. All Rights Reserved
:  
: The contents of this file are subject to the terms
: of the Common Development and Distribution License
: (the License). You may not use this file except in
: compliance with the License.
:
: You can obtain a copy of the License at
: https://opensso.dev.java.net/public/CDDLv1.0.html or
: opensso/legal/CDDLv1.0.txt
: See the License for the specific language governing
: permission and limitations under the License.
:
: When distributing Covered Code, include this CDDL
: Header Notice in each file and include the License file
: at opensso/legal/CDDLv1.0.txt.
: If applicable, add the following below the CDDL Header,
: with the fields enclosed by brackets [] replaced by
: your own identifying information:
: "Portions Copyrighted [year] [name of copyright owner]"
:
: $Id: amsfopassword.bat,v 1.4 2008/06/30 16:52:52 qcheng Exp $
:

setlocal
set JAVA_HOME=JDK_PATH
set AM_HOME=BASEDIR\PRODUCT_DIR
set CLASSPATH=%AM_HOME%\locale;%AM_HOME%\lib\am_sessiondb.jar

%JAVA_HOME%/bin/java.exe -classpath "%CLASSPATH%" com.sun.identity.ha.jmqdb.client.FAMSFOPassword %*
endlocal
