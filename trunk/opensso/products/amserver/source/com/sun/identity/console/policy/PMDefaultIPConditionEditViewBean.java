/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2007 Sun Microsystems Inc. All Rights Reserved
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
 * $Id: PMDefaultIPConditionEditViewBean.java,v 1.2 2008/06/25 05:43:03 qcheng Exp $
 *
 */

package com.sun.identity.console.policy;

import com.sun.identity.console.base.model.AMModel;
import com.sun.identity.console.policy.model.PolicyModel;
import java.util.Map;

public class PMDefaultIPConditionEditViewBean
    extends ConditionEditViewBean {
    private static IPConditionHelper helper = IPConditionHelper.getInstance();

    public static final String DEFAULT_DISPLAY_URL =
        "/console/policy/PMDefaultIPConditionEdit.jsp";

    public PMDefaultIPConditionEditViewBean() {
        super("PMDefaultIPConditionEdit", DEFAULT_DISPLAY_URL);
    }

    protected String getConditionXML(
        String curRealm,
        String condType,
        boolean readonly
    ) {
        return helper.getConditionXML(false, readonly);
    }

    protected String getMissingValuesMessage() {
        return helper.getMissingValuesMessage();
    }

    protected void setPropertiesValues(Map values) {
        helper.setPropertiesValues(values, propertySheetModel, getModel());
    }

    protected Map getConditionValues(
        PolicyModel model,
        String realmName,
        String conditionType
    ) {
        return helper.getConditionValues(model, propertySheetModel);
    }
}
