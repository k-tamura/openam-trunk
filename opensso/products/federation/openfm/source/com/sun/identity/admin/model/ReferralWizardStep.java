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
 * $Id: ReferralWizardStep.java,v 1.1 2009/08/19 05:40:53 veiming Exp $
 */

package com.sun.identity.admin.model;

import java.util.HashMap;
import java.util.Map;

public enum ReferralWizardStep {

    NAME(0),
    RESOURCES(1),
    SUBJECTS(2),
    SUMMARY(4);

    private final int stepNumber;
    private static final Map<Integer, ReferralWizardStep> intValues = new HashMap<Integer, ReferralWizardStep>() {
        {
            put(NAME.toInt(), NAME);
            put(RESOURCES.toInt(), RESOURCES);
            put(SUBJECTS.toInt(), SUBJECTS);
            put(SUMMARY.toInt(), SUMMARY);
        }
    };

    ReferralWizardStep(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public int toInt() {
        return stepNumber;
    }

    public static ReferralWizardStep valueOf(int i) {
        return intValues.get(Integer.valueOf(i));
    }
}
