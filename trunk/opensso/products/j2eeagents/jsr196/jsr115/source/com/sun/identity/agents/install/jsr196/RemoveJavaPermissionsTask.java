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
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * $Id: RemoveJavaPermissionsTask.java,v 1.1 2009/01/30 12:09:39 kalpanakm Exp $
 *
 */

package com.sun.identity.agents.install.jsr196;

import java.util.Map;

import com.sun.identity.install.tools.configurator.IStateAccess;
import com.sun.identity.install.tools.configurator.ITask;
import com.sun.identity.install.tools.configurator.InstallConstants;
import com.sun.identity.install.tools.configurator.InstallException;
import com.sun.identity.install.tools.util.LocalizedMessage;


/**
 * The class removes the Java Permissions granted to agent
 */
public class RemoveJavaPermissionsTask extends JavaPermissionsBase 
    implements ITask, InstallConstants
{
    
    public RemoveJavaPermissionsTask() {
        super();
    }
    
    public boolean execute(String name, IStateAccess stateAccess, 
        Map properties) throws InstallException 
    {
        return removeFromServerPolicy(stateAccess);
    }

    public LocalizedMessage getExecutionMessage(IStateAccess stateAccess,
        Map properties) {
        String serverPolicyFile = getServerPolicyFile(stateAccess); 
        Object[] args = { serverPolicyFile };
        LocalizedMessage message = LocalizedMessage.get(
            LOC_TSK_MSG_REMOVE_JAVA_PERMS_EXECUTE, STR_AS_GROUP, args);
        return message;
    }
    
    public LocalizedMessage getRollBackMessage(IStateAccess stateAccess,
        Map properties) {
        // Nothing to do during uninstall
        return null;
    }
    
    public boolean rollBack(String name, IStateAccess state, Map properties)
        throws InstallException {
        // No Roll Back during uninstall
        return true;
    }
    
    public static final String LOC_TSK_MSG_REMOVE_JAVA_PERMS_EXECUTE=
        "TSK_MSG_REMOVE_JAVA_PERMS_EXECUTE";
    
    private static final String STR_AS_GROUP = "asTools";
}
