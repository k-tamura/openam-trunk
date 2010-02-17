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
 * $Id: SetGroupHandler.java,v 1.2 2008/11/21 22:21:44 leiming Exp $
 *
 */

package com.sun.identity.agents.tools.websphere;

import java.util.ArrayList;
import java.io.File;
import java.util.Random;

import com.sun.identity.install.tools.admin.IToolsOptionHandler;
import com.sun.identity.install.tools.configurator.InstallConstants;
import com.sun.identity.install.tools.util.Console;
import com.sun.identity.install.tools.util.Debug;
import com.sun.identity.install.tools.util.LocalizedMessage;
import com.sun.identity.install.tools.util.xml.XMLDocument;
import com.sun.identity.install.tools.util.xml.XMLElement;

/**
 * SetGroupHandler will set the OpenSSO Enterprise group name in admin-authz.xml file
 * for WAS roles.
 *
 */
public class SetGroupHandler extends GroupUpdateHandlerBase
        implements IToolsOptionHandler, InstallConstants,
        IConstants {
    
    public SetGroupHandler() {
        super();
    }
    
    public boolean checkArguments(ArrayList arguments) {
        return super.checkArguments(arguments);
    }
    
    public void handleRequest(ArrayList arguments) {
        
        Debug.log("SetGroupHandler: handle request");
        String amGroupName = (String)arguments.get(1);
        File file = new File((String)arguments.get(2) +
                STR_FILE_SEP + STR_ADMIN_AUTHZ_XML_FILE);
        
        try {
            XMLDocument adminauthFile = new XMLDocument(file);
            adminauthFile.setIndentDepth(2);
            XMLElement authElem = findAuthElement(adminauthFile,arguments);
            
            if (authElem != null) {
                // First see if the element with name=amGroupName is already
                // present so that we do not add it twice
                XMLElement groupElem = getElement(authElem,"groups", "name",
                        amGroupName);
                if(groupElem == null) {
                    boolean stat =
                            addNewGroupsElement(adminauthFile,authElem,
                            amGroupName);
                    if(stat) {
                        Console.println();
                        Console.println(LocalizedMessage.get(
                                LOC_HR_MSG_UPDATED_ADMIN_AUTH_FILE,
                                STR_WAS_GROUP));
                        Console.println();
                    } else {
                        Console.println();
                        Console.println(LocalizedMessage.get(
                                LOC_HR_ERROR_FAILED_UPDATE_ADMIN_AUTH_FILE,
                                STR_WAS_GROUP));
                        Console.println();
                    }
                } else { // element with name=amGroupName already present
                    Console.println();
                    Console.println(LocalizedMessage.get(
                            LOC_HR_ERROR_FAILED_UPDATE_GROUP_PRESENT,
                            STR_WAS_GROUP), new Object[] {amGroupName});
                    Console.println();
                }
            }
            
            adminauthFile.store();
            
        } catch (Exception ex) {
            Debug.log("SetGroupHandler: failed to update admin-authz.xml", ex);
            Console.println();
            Console.println(LocalizedMessage.get(
                    LOC_HR_ERR_INVALID_ADMIN_AUTH_FILE,STR_WAS_GROUP),
                    new Object[] { arguments.get(2) });
            Console.println();
        }
    }
    
     /*
      *  Generic function to add group element
      *  <groups xmi:id="GroupExt_32443331" 
      *     name="id=manager,ou=role,dc=iplanet,dc=com"/>
      */
    public boolean addNewGroupsElement(XMLDocument doc,
            XMLElement authElem, String amGroup) {
        
        boolean status = true;
        try {
            StringBuffer sb = new StringBuffer(256);
            Random rand = new Random();
            int number = rand.nextInt();
            if(number < 0) {
                number = -number;
            }
            sb.append("<groups xmi:id=\"").append("GroupExt_").
                    append(String.valueOf(number)).append("\"").
                    append("  ").append("name=\"").append(amGroup).
                    append("\"").append("/>");
            XMLElement groupsElem = doc.newElementFromXMLFragment(
                    sb.toString());
            Debug.log("SetGroupsHandler.addNewGroupsElement() -  "
                    + groupsElem.toXMLString());
            authElem.addChildElement(groupsElem,true);
        } catch (Exception ex){
            Debug.log(
                    "SetGroupHandler.addNewGroupsElement() - exception caught ",
                    ex);
            status = false;
        }
        
        return status;
    }
    
    public void displayHelp() {
        Console.println();
        Console.println(LocalizedMessage.get(LOC_HR_MSG_SETGROUP_USAGE_DESC,
                STR_WAS_GROUP));
        Console.println();
        Console.println();
        Console.println(LocalizedMessage.get(LOC_HR_MSG_SETGROUP_USAGE_HELP,
                STR_WAS_GROUP));
        Console.println();
    }
    
    
    public static final String LOC_HR_MSG_SETGROUP_USAGE_DESC =
            "HR_MSG_SETGROUP_USAGE_DESC";
    public static final String LOC_HR_MSG_SETGROUP_USAGE_HELP =
            "HR_MSG_SETGROUP_USAGE_HELP";
    public static final String LOC_HR_ERROR_FAILED_UPDATE_GROUP_PRESENT =
            "HR_ERROR_FAILED_UPDATE_GROUP_PRESENT";
    
}
