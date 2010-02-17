/* The contents of this file are subject to the terms
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
 * $Id: SetRealmAttributesTest.java,v 1.4 2009/01/26 23:49:39 nithyas Exp $
 *
 * Copyright 2007 Sun Microsystems Inc. All Rights Reserved
 */

package com.sun.identity.qatest.cli;

import com.sun.identity.qatest.common.cli.CLIExitCodes;
import com.sun.identity.qatest.common.cli.FederationManagerCLI;
import com.sun.identity.qatest.common.TestCommon;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.Reporter;

/**
 * <code>SetRealmAttributesTest</code> is used to execute tests involving the 
 * set-realm-attributes sub-command of ssoadm.  This class allows the user to 
 * execute "ssoadm set-realm-attributes" with a variety or arguments (e.g with 
 * short or long options, with a locale argument, with a list of attributes 
 * or a datafile containing attributes, etc.) and a variety of input values.  
 * The properties file <code>SetRealmAttributesTest.properties</code> contains 
 * the input values which are read by this class.
 *
 * This class automates the following test cases:
 * CLI_set-realm-attributes01, CLI_set-realm-attributes02, 
 * CLI_set-realm-attributes03, CLI_set-realm-attributes04,
 * CLI_set-realm-attributes05, CLI_set-realm-attributes06
 */

public class SetRealmAttributesTest extends TestCommon implements CLIExitCodes {
    
    private String locTestName;
    private ResourceBundle rb;
    private String setupRealms;
    private String realm;
    private String serviceName;
    private String attributeValues;
    private boolean useDatafileOption;
    private boolean useVerboseOption;
    private boolean useDebugOption;
    private boolean useLongOptions;
    private String expectedMessage;
    private String expectedExitCode;
    private String description;
    private FederationManagerCLI cli;
    
    /** 
     * Creates a new instance of SetRealmAttributesTest 
     */
    public SetRealmAttributesTest() {
        super("SetRealmAttributesTest");
    }
    
    /**
     * This method is intended to provide initial setup.
     * Creates any realms specified in the setup-realms property, creates
     * any identities specified in the setup-identities property, and adds any 
     * members in the setup-members in the SetRealmAttributesTest.properties 
     * file.
     */
    @Parameters({"testName"})
    @BeforeClass(groups={"ldapv3", "ldapv3_sec", "s1ds", "s1ds_sec", "ad", 
      "ad_sec", "amsdk", "amsdk_sec", "jdbc", "jdbc_sec"})
    public void setup(String testName) 
    throws Exception {
        Object[] params = {testName};
        entering("setup", params);
        try {
            locTestName = testName;
            rb = ResourceBundle.getBundle("cli" + fileseparator + 
                    "SetRealmAttributesTest");
            setupRealms = (String)rb.getString(locTestName + 
                    "-create-setup-realms");
            useVerboseOption = ((String)rb.getString(locTestName + 
                    "-use-verbose-option")).equals("true");
            useDebugOption = ((String)rb.getString(locTestName + 
                    "-use-debug-option")).equals("true");
            useLongOptions = ((String)rb.getString(locTestName + 
                    "-use-long-options")).equals("true");
                
            log(Level.FINEST, "setup", "use-verbose-option: " + 
                    useVerboseOption);
            log(Level.FINEST, "setup", "use-debug-option: " + useDebugOption);
            log(Level.FINEST, "setup", "use-long-options: " + useLongOptions);
            log(Level.FINEST, "setup", "create-setup-realms: " + setupRealms);

            Reporter.log("UseDebugOption: " + useDebugOption);
            Reporter.log("UseVerboseOption: " + useVerboseOption);
            Reporter.log("UseLongOptions: " + useLongOptions);
            Reporter.log("SetupRealms: " + setupRealms);

            cli = new FederationManagerCLI(useDebugOption, useVerboseOption, 
                    useLongOptions);
            
            log(Level.FINE, "setup", "Creating the following realms: " + 
                    setupRealms);
            
            if (!cli.createRealms(setupRealms)) {
                log(Level.SEVERE, "setup", 
                        "All the realms failed to be created.");
                assert false;
            }
 
            exiting("setup");
        } catch (Exception e) {
            log(Level.SEVERE, "setup", e.getMessage(), null);
            e.printStackTrace();
            throw e;
        } 
    }
    
    /**
     * This method is used to execute tests involving 
     * "ssoadm set-realm-attributes" using input data from the 
     * SetRealmAttributesTest.properties file.
     */
    @Test(groups={"ldapv3", "ldapv3_sec", "s1ds", "s1ds_sec", "ad", "ad_sec", 
      "amsdk", "amsdk_sec", "jdbc", "jdbc_sec"})
    public void testRealmSetAttributeValues() 
    throws Exception {
        entering("testRealmSetAttributeValues", null);
        boolean stringsFound = false;
        boolean attributesFound = false;
        boolean errorFound = false;
        int commandStatus = -1;
        
        try {
            description = (String) rb.getString(locTestName + "-description");
            expectedMessage = (String) rb.getString(locTestName + 
                    "-message-to-find");
            expectedExitCode = (String) rb.getString(locTestName + 
                    "-expected-exit-code");
            realm = (String) rb.getString(locTestName + 
                    "-set-realm-attributes-realm");
            serviceName = (String) rb.getString(locTestName + 
                    "-set-realm-attributes-servicename");
            attributeValues = (String) rb.getString(locTestName + 
                    "-set-realm-attributes-attribute-list");
            useDatafileOption = ((String) rb.getString(locTestName + 
                    "-set-realm-attributes-use-datafile-option")).
                    equals("true");

            log(Level.FINEST, "testRealmSetAttributeValues", "description: " + 
                    description);
            log(Level.FINEST, "testRealmSetAttributeValues", 
                    "use-debug-option: " + useDebugOption);
            log(Level.FINEST, "testRealmSetAttributeValues", 
                    "use-verbose-option: " + useVerboseOption);
            log(Level.FINEST, "testRealmSetAttributeValues", 
                    "use-long-options: " + useLongOptions);
            log(Level.FINEST, "testRealmSetAttributeValues", "message-to-find: "
                    + expectedMessage);
            log(Level.FINEST, "testRealmSetAttributeValues", 
                    "expected-exit-code: " + expectedExitCode);
            log(Level.FINEST, "testRealmSetAttributeValues", 
                    "set-realm-attributes-realm: " + realm);
            log(Level.FINEST, "testRealmSetAttributeValues", 
                    "set-realm-attributes-servicename: " + serviceName);
            log(Level.FINEST, "testRealmSetAttributeValues", 
                    "set-realm-attributes-attribute-list: " + attributeValues);
            log(Level.FINEST, "testRealmSetAttributeValues", 
                    "set-realm-attributes-use-datafile-option: " + 
                    useDatafileOption);

            Reporter.log("TestName: " + locTestName);
            Reporter.log("Description: " + description);
            Reporter.log("UseDebugOption: " + useDebugOption);
            Reporter.log("UseVerboseOption: " + useVerboseOption);
            Reporter.log("UseLongOptions: " + useLongOptions);
            Reporter.log("ExpectedMessage: " + expectedMessage);
            Reporter.log("ExpectedExitCode: " + expectedExitCode);
            Reporter.log("Realm: " + realm);
            Reporter.log("ServiceName: " + serviceName);
            Reporter.log("AttributeList: " + attributeValues);
            Reporter.log("UseDatafileOption: " + useDatafileOption);

            commandStatus = cli.setRealmAttributes(realm, serviceName, 
                    attributeValues, useDatafileOption);
            cli.logCommand("testRealmSetAttributeValues");
            cli.resetArgList();
            
            String msg = (String) rb.getString(locTestName + 
                    "-message-to-find");  
            if (expectedExitCode.equals(
                    new Integer(SUCCESS_STATUS).toString())) {
                Object[] params = {realm}; 
                if (msg.equals("")) {           
                    if (!useVerboseOption) {
                        String successString = 
                                (String) rb.getString("success-message");
                        expectedMessage = 
                                MessageFormat.format(successString, params);
                    } else {
                        String verboseSuccessString = 
                                (String) rb.getString(
                                "verbose-success-message");
                        expectedMessage = 
                                MessageFormat.format(verboseSuccessString,
                                params);
                    }
                } else {
                    expectedMessage = 
                            MessageFormat.format(msg, params);
                }
                stringsFound = cli.findStringsInOutput(expectedMessage, ";"); 
                FederationManagerCLI searchCLI = 
                        new FederationManagerCLI(useDebugOption, 
                        useVerboseOption, useLongOptions);
                attributesFound = searchCLI.findRealmAttributes(realm, 
                        serviceName, attributeValues);
                searchCLI.logCommand("testRealmSetAttributeValues");
            } else if (expectedExitCode.equals(
                    new Integer(INVALID_OPTION_STATUS).toString())) {
                expectedMessage = (String) rb.getString("usage");
                String argString = cli.getAllArgs().replaceFirst(
                        cli.getCliPath() + fileseparator + "ssoadm", "ssoadm ");
                Object[] params = {argString};
                String errorMessage = 
                        (String) rb.getString("invalid-usage-message");
                String usageError = MessageFormat.format(errorMessage, params);
                stringsFound = cli.findStringsInOutput(expectedMessage, ";");                
                errorFound = cli.findStringsInError(usageError, ";");
            } else {
                expectedMessage = msg;      
                errorFound = cli.findStringsInError(expectedMessage, ";");
            }
            cli.resetArgList();
                   
            log(Level.FINEST, "testRealmSetAttributeValues", "Exit status: " + 
                    commandStatus);
            if (expectedExitCode.equals(
                    new Integer(SUCCESS_STATUS).toString())) {
                log(Level.FINEST, "testRealmSetAttributeValues", 
                        "Output Messages Found: " + stringsFound);
                log(Level.FINEST, "testRealmSetAttributeValues", 
                        "Attributes Found: " + attributesFound);
                assert (commandStatus == 
                    new Integer(expectedExitCode).intValue()) && stringsFound &&
                        attributesFound;
            } else {
                log(Level.FINEST, "testRealmSetAttributeValues", 
                        "Error Messages Found: " + errorFound);
                if (expectedExitCode.equals(
                        new Integer(INVALID_OPTION_STATUS).toString())) {
                    log(Level.FINEST, "testRealmSetAttributeValues", 
                            "Output Messages Found: " + stringsFound); 
                    assert (commandStatus == 
                            new Integer(expectedExitCode).intValue()) && 
                            stringsFound && errorFound;                    
                } else {
                    assert (commandStatus == 
                            new Integer(expectedExitCode).intValue()) && 
                            errorFound;
                }
            }   
            exiting("testRealmSetAttributeValues");
        } catch (Exception e) {
            log(Level.SEVERE, "testRealmSetAttributeValues", e.getMessage(), 
                    null);
            e.printStackTrace();
            throw e;
        } 
    }
    
    /**
     * This method remove any realms that were created during the setup and 
     * testRealmSetAttributeValues methods using "ssoadm delete-realm".
     */
    @AfterClass(groups={"ldapv3", "ldapv3_sec", "s1ds", "s1ds_sec", "ad", 
      "ad_sec", "amsdk", "amsdk_sec", "jdbc", "jdbc_sec"})
    public void cleanup() 
    throws Exception {
        int exitStatus = -1;

        entering("cleanup", null);
        try {            
            log(Level.FINEST, "cleanup", "useDebugOption: " + useDebugOption);
            log(Level.FINEST, "cleanup", "useVerboseOption: " + 
                    useVerboseOption);
            log(Level.FINEST, "cleanup", "useLongOptions: " + useLongOptions);
            
            Reporter.log("UseDebugOption: " + useDebugOption);
            Reporter.log("UseVerboseOption: " + useVerboseOption);
            Reporter.log("UseLongOptions: " + useLongOptions);
            

            if (!setupRealms.equals("")) {
                String[] realms = setupRealms.split(";");
                for (int i=realms.length-1; i >= 0; i--) {
                    log(Level.FINE, "cleanup", "Removing realm " + 
                        realms[i]);
                    Reporter.log("SetupRealmToDelete: " + realms[i]);
                    exitStatus = cli.deleteRealm(realms[i], true); 
                    cli.logCommand("cleanup");
                    cli.resetArgList();
                    if (exitStatus != SUCCESS_STATUS) {
                        log(Level.SEVERE, "cleanup", "The removal of realm " + 
                                realms[i] + " failed with exit status " + 
                                exitStatus);
                        assert false;
                    }
                } 
            }            
            exiting("cleanup");
        } catch (Exception e) {
            log(Level.SEVERE, "cleanup", e.getMessage(), null);
            e.printStackTrace();
            throw e;
        } 
    }
}
