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
 * $Id: TimezoneConditionType.java,v 1.1 2009/08/19 05:40:56 veiming Exp $
 */

package com.sun.identity.admin.model;

import com.sun.identity.entitlement.TimeCondition;
import java.io.Serializable;
import java.util.TimeZone;

public class TimezoneConditionType
    extends TimeConditionType
    implements Serializable {
    public ViewCondition newViewCondition() {
        ViewCondition vc = new TimezoneCondition();
        vc.setConditionType(this);

        return vc;
    }

    public ViewCondition newViewCondition(TimeCondition tc) {
        TimezoneCondition tzc = (TimezoneCondition)newViewCondition();
        tzc.setTimezoneId(tc.getEnforcementTimeZone());

        return tzc;
    }
}
