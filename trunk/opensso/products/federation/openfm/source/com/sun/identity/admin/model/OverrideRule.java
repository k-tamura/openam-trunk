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
 * $Id: OverrideRule.java,v 1.1 2009/08/19 05:40:52 veiming Exp $
 */

package com.sun.identity.admin.model;

import com.sun.identity.admin.Resources;
import com.sun.identity.entitlement.DenyOverride;
import java.util.HashMap;
import java.util.Map;

public enum OverrideRule {
    DENY_OVERRIDE(DenyOverride.class);

    private static final Map<Class,OverrideRule> classValues = new HashMap<Class,OverrideRule>() {
        {
            put(DENY_OVERRIDE.getEntitlementCombinerClass(), DENY_OVERRIDE);
        }
    };

    private Class entitlementCombinerClass;

    private OverrideRule(Class entitlementCombinerClass) {
        this.entitlementCombinerClass = entitlementCombinerClass;
    }

    public Class getEntitlementCombinerClass() {
        return entitlementCombinerClass;
    }

    public String getTitle() {
        Resources r = new Resources();
        String t = r.getString(this, "title." + toString());
        if (t == null) {
            t = toString();
        }

        return t;
    }

    public static OverrideRule valueOf(Class ecClass) {
        return classValues.get(ecClass);
    }
}
