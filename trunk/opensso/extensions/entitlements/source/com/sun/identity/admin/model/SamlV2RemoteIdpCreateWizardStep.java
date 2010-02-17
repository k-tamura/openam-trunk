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
 * $Id: SamlV2RemoteIdpCreateWizardStep.java,v 1.1 2009/06/23 06:36:21 babysunil Exp $
 */

package com.sun.identity.admin.model;

import java.util.HashMap;
import java.util.Map;

public enum SamlV2RemoteIdpCreateWizardStep {

    REALM(0),
    METADATA(1),
    COT(2),
    SUMMARY(3);
    private final int stepNumber;
    private static final Map<Integer, SamlV2RemoteIdpCreateWizardStep>
            intValues = new HashMap<Integer, SamlV2RemoteIdpCreateWizardStep>() {

        {
            put(REALM.toInt(), REALM);
            put(METADATA.toInt(), METADATA);
            put(COT.toInt(), COT);
            put(SUMMARY.toInt(), SUMMARY);
        }
    };

    SamlV2RemoteIdpCreateWizardStep(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public int toInt() {
        return stepNumber;
    }

    public static SamlV2RemoteIdpCreateWizardStep valueOf(int i) {
        return intValues.get(Integer.valueOf(i));
    }
}
