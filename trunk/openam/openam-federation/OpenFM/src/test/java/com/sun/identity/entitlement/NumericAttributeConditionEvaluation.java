/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2009 Sun Microsystems Inc. All Rights Reserved
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
 * $Id: NumericAttributeConditionEvaluation.java,v 1.1 2009/08/19 05:41:00 veiming Exp $
 *
 * Portions Copyrighted 2014 ForgeRock AS
 */

package com.sun.identity.entitlement;

import com.iplanet.sso.SSOToken;
import com.sun.identity.entitlement.opensso.SubjectUtils;
import com.sun.identity.security.AdminTokenAction;
import java.security.AccessController;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.security.auth.Subject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class NumericAttributeConditionEvaluation {
    private static final String PRIVILEGE_NAME =
        "NumericAttributeConditionEvaluationPrivilege";
    private static final String URL = 
        "http://www.NumericAttributeConditionEvaluation.com";
    private SSOToken adminToken = (SSOToken) AccessController.doPrivileged(
            AdminTokenAction.getInstance());
    private Subject adminSubject = SubjectUtils.createSubject(adminToken);
    private boolean migrated = EntitlementConfiguration.getInstance(
        adminSubject, "/").migratedToEntitlementService();

    @BeforeClass
    public void setup() throws Exception {
        if (!migrated) {
            return;
        }

        PrivilegeManager pm = PrivilegeManager.getInstance("/",
            adminSubject);
        Map<String, Boolean> actions = new HashMap<String, Boolean>();
        actions.put("GET", Boolean.TRUE);
        Entitlement ent = new Entitlement(
            ApplicationTypeManager.URL_APPLICATION_TYPE_NAME, URL, actions);

        Privilege privilege = Privilege.getNewInstance();
        privilege.setName(PRIVILEGE_NAME);
        privilege.setEntitlement(ent);
        privilege.setSubject(new AnyUserSubject());

        NumericAttributeCondition tc = new NumericAttributeCondition();
        tc.setAttributeName("attributeN");
        tc.setValue(10f);
        tc.setOperator(NumericAttributeCondition.Operator.GREATER_THAN);
        privilege.setCondition(tc);
        pm.add(privilege);
        Thread.sleep(1000);
    }

    @AfterClass
    public void cleanup() throws Exception {
        if (!migrated) {
            return;
        }
        PrivilegeManager pm = PrivilegeManager.getInstance("/",
            adminSubject);
        pm.remove(PRIVILEGE_NAME);
    }

    @Test
    public void postiveTest()
        throws Exception {
        Set actions = new HashSet();
        actions.add("GET");
        Evaluator evaluator = new Evaluator(
            SubjectUtils.createSubject(adminToken),
            ApplicationTypeManager.URL_APPLICATION_TYPE_NAME);
        Map<String, Set<String>> env = new HashMap<String, Set<String>>();
        Set<String> setValues = new HashSet<String>();
        setValues.add("20.1");
        env.put("attributeN", setValues);
        Boolean result = evaluator.hasEntitlement("/", null,
            new Entitlement(URL, actions), env);
        if (!result) {
            throw new Exception(
                "NumericAttributeConditionEvaluation.positiveTest fails");
        }
    }

    @Test
    public void negativeTest()
        throws Exception {
        Set actions = new HashSet();
        actions.add("GET");
        Evaluator evaluator = new Evaluator(
            SubjectUtils.createSubject(adminToken),
            ApplicationTypeManager.URL_APPLICATION_TYPE_NAME);
        Map<String, Set<String>> env = new HashMap<String, Set<String>>();
        Set<String> setValues = new HashSet<String>();
        setValues.add("9");
        env.put("attributeN", setValues);
        Boolean result = evaluator.hasEntitlement("/", null,
            new Entitlement(URL, actions), env);
        if (result) {
            throw new Exception(
                "NumericAttributeConditionEvaluation.negativeTest fails");
        }
    }
}

