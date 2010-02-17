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
 * $Id: SamlV2RemoteSpCreateWizardStep.java,v 1.1 2009/08/19 05:40:55 veiming Exp $
 */

package com.sun.identity.admin.model;

import java.util.HashMap;
import java.util.Map;

public enum SamlV2RemoteSpCreateWizardStep {

    REALM(0), 
    METADATA(1),
    COT(2),
    ATTRIBUTEMAPPING(3),
    SUMMARY(4);
    private final int stepNumber;
    private static final Map<Integer, SamlV2RemoteSpCreateWizardStep> intValues = new HashMap<Integer, SamlV2RemoteSpCreateWizardStep>() {

        {
            put(REALM.toInt(), REALM);            
            put(METADATA.toInt(), METADATA);
            put(COT.toInt(), COT);
            put(ATTRIBUTEMAPPING.toInt(), ATTRIBUTEMAPPING);
            put(SUMMARY.toInt(), SUMMARY);
        }
    };

    SamlV2RemoteSpCreateWizardStep(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public int toInt() {
        return stepNumber;
    }

    public static SamlV2RemoteSpCreateWizardStep valueOf(int i) {
        return intValues.get(Integer.valueOf(i));
    }
}
