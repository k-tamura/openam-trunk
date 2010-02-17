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
 * $Id: EntityNameSamlV2HostedSpCreateSummary.java,v 1.4 2009/06/27 01:52:14 asyhuang Exp $
 */

package com.sun.identity.admin.model;

import com.sun.identity.admin.Resources;

public class EntityNameSamlV2HostedSpCreateSummary extends SamlV2HostedSpCreateSummary {

    public EntityNameSamlV2HostedSpCreateSummary(SamlV2HostedSpCreateWizardBean samlV2HostedSpCreateWizardBean) {
        super(samlV2HostedSpCreateWizardBean);
    }

    public String getLabel() {
        Resources r = new Resources();
        String label = r.getString(this, "label");
        return label;
    }

    public String getValue() {
        String entityName;
        boolean hasMeta = getSamlV2HostedSpCreateWizardBean().isMeta();
        if(!hasMeta){
            entityName = getSamlV2HostedSpCreateWizardBean().getNewEntityName();
        } else {
            entityName = new String();
        }

        return entityName;

    }

    public boolean isExpandable() {
        return false;
    }

    public String getIcon() {
        return "../image/file.png";
    }

    public String getTemplate() {
        return null;
    }

    public int getGotoStep() {
        return SamlV2HostedSpCreateWizardStep.METADATA.toInt();
    }
}