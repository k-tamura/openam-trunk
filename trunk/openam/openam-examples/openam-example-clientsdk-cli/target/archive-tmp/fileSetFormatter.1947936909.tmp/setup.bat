@echo off
:
: DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
:  
: Copyright (c) 2007 Sun Microsystems Inc. All Rights Reserved
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
: $Id: setup.bat,v 1.3 2008/07/07 22:58:30 dillidorai Exp $
:
: Portions Copyrighted 2013 ForgeRock, Inc.
:

java -cp resources;lib/openam-clientsdk-10.2.0-SNAPSHOT.jar;openam-example-clientsdk-cli-10.2.0-SNAPSHOT.jar com.sun.identity.setup.SetupClientSDKSamples
