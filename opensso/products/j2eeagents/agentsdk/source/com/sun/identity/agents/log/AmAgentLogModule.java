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
 * $Id: AmAgentLogModule.java,v 1.2 2008/06/25 05:51:53 qcheng Exp $
 *
 */

package com.sun.identity.agents.log;

import com.sun.identity.agents.arch.Module;
import com.sun.identity.agents.arch.ModuleList;

/**
 * The class represents an agent logging module
 */
public class AmAgentLogModule implements IAmLogModuleConstants {

    public static Module getModule() {
        return _module;
    }

    public static byte getModuleCode() {
        return AM_LOG_MODULE_CODE;
    }

    public static void init() {
        ModuleList.addRegisteredModule(getModule());
    }

    private static Module _module = new Module(
            getModuleCode(), STR_AM_LOG_MODULE, AM_LOG_RESOURCE);
}
