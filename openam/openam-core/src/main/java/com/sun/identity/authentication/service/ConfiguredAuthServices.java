/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2005 Sun Microsystems Inc. All Rights Reserved
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
 * $Id: ConfiguredAuthServices.java,v 1.6 2008/06/25 05:42:04 qcheng Exp $
 *
 */

/*
 * Portions Copyrighted 2014 ForgeRock AS
 */

package com.sun.identity.authentication.service;

import java.security.AccessController;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.iplanet.sso.SSOToken;
import com.sun.identity.authentication.util.ISAuthConstants;
import com.sun.identity.shared.Constants;
import com.sun.identity.security.AdminTokenAction;
import com.sun.identity.sm.ChoiceValues;
import com.sun.identity.sm.SMSEntry;
import com.sun.identity.sm.ServiceConfig;
import com.sun.identity.sm.ServiceConfigManager;

/**
 * The class determines the configured Identity Types for Identity Repository.
 * This list is computed per realm.
 */
public class ConfiguredAuthServices extends ChoiceValues {
    /**
     * Creates <code>ConfiguredAuthServices</code> object.
     * Default constructor that will be used by the SMS
     * to create an instance of this class
     */
    public ConfiguredAuthServices() {
        // do nothing
    }
    
    /**
     * Returns the choice values and their corresponding localization keys.
     *
     * @return the choice values and their corresponding localization keys.
     */
    public Map getChoiceValues() {
        return getChoiceValues(Collections.EMPTY_MAP);
    }

    /**
     * Returns the choice values from configured environment params.
     * @param envParams map for configured parameters
     * @return the choice values from configured environment params.
     */
    public Map getChoiceValues(Map envParams) {
        String org = null;
        SSOToken adminToken = null;
        
        if (envParams != null) {
            org = (String)envParams.get(Constants.ORGANIZATION_NAME);
            adminToken = (SSOToken)envParams.get(Constants.SSO_TOKEN);
        }
        if (org == null || org.length() == 0) {
            org = SMSEntry.getRootSuffix();
        }
        if (adminToken == null) {
            adminToken = (SSOToken)AccessController.doPrivileged(
                AdminTokenAction.getInstance());
        }

        Set namedConfigs = Collections.EMPTY_SET;
        ServiceConfig namedConfig = null;
        Map answer = new HashMap();
        try {
            // Get the named config node
            ServiceConfigManager scm = new ServiceConfigManager(SERVICE_NAME, adminToken);
            ServiceConfig oConfig = scm.getOrganizationConfig(org, null);
            if (oConfig != null) {
                namedConfig = oConfig.getSubConfig(NAMED_CONFIGURATION);
                if (namedConfig != null) {
                    // get all sub config names
                    namedConfigs = namedConfig.getSubConfigNames("*");
                }
            }
        } catch (Exception e) {
            // do nothing as namedConfigs will be empty.
        }

        if (namedConfigs != null && !namedConfigs.isEmpty()) {
            Set<String> configs = filterConfigs(namedConfigs, namedConfig, org, adminToken);
            for (String config : configs) {
                answer.put(config, config);
            }
        }

        answer.put(ISAuthConstants.BLANK, ISAuthConstants.BLANK);

        //return the choice values map
        return (answer);
    }

    /**
     * Extensions will want to restrict the list of auth services. The default implementation just returns all.
     * @param namedConfigs The auth services that have been found.
     * @param parentConfig The ServiceConfig parent of all auth services.
     * @param realm The current realm.
     * @param adminToken The current SSO token.
     * @return The filtered list of auth services.
     */
    protected Set<String> filterConfigs(Set<String> namedConfigs, ServiceConfig parentConfig, String realm,
            SSOToken adminToken) {
        return namedConfigs;
    }

    protected static final String SERVICE_NAME = "iPlanetAMAuthConfiguration";
    protected static final String NAMED_CONFIGURATION = "Configurations";
}
