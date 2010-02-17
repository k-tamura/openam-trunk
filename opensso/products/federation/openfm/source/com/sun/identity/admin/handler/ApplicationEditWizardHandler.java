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
 * $Id: ApplicationEditWizardHandler.java,v 1.1 2009/09/09 19:19:13 farble1670 Exp $
 */

package com.sun.identity.admin.handler;

import com.sun.identity.admin.Resources;
import com.sun.identity.admin.model.ApplicationWizardStep;
import com.sun.identity.admin.model.LinkBean;
import com.sun.identity.admin.model.NameApplicationCreateWizardStepValidator;
import com.sun.identity.admin.model.NextPopupBean;
import com.sun.identity.admin.model.ResourcesApplicationWizardStepValidator;
import com.sun.identity.admin.model.SubjectsApplicationWizardStepValidator;
import java.util.ArrayList;
import java.util.List;

public class ApplicationEditWizardHandler extends ApplicationWizardHandler {
    @Override
    public void initWizardStepValidators() {
        getWizardStepValidators()[ApplicationWizardStep.RESOURCES.toInt()] = new ResourcesApplicationWizardStepValidator(getWizardBean());
        getWizardStepValidators()[ApplicationWizardStep.SUBJECTS.toInt()] = new SubjectsApplicationWizardStepValidator(getWizardBean());
    }


    public void doFinishNext() {
        NextPopupBean npb = NextPopupBean.getInstance();
        npb.setVisible(true);
        Resources r = new Resources();
        npb.setTitle(r.getString(this, "finishTitle"));
        npb.setMessage(r.getString(this, "finishMessage"));
        npb.setLinkBeans(getFinishLinkBeans());

    }

    public void doCancelNext() {
        NextPopupBean npb = NextPopupBean.getInstance();
        npb.setVisible(true);
        Resources r = new Resources();
        npb.setTitle(r.getString(this, "cancelTitle"));
        npb.setMessage(r.getString(this, "cancelMessage"));
        npb.setLinkBeans(getCancelLinkBeans());

    }

    private List<LinkBean> getFinishLinkBeans() {
        List<LinkBean> lbs = new ArrayList<LinkBean>();
        lbs.add(LinkBean.HOME);
        lbs.add(LinkBean.APPLICATION_MANAGE);

        return lbs;
    }

    private List<LinkBean> getCancelLinkBeans() {
        List<LinkBean> lbs = new ArrayList<LinkBean>();
        lbs.add(LinkBean.HOME);
        lbs.add(LinkBean.APPLICATION_MANAGE);

        return lbs;
    }

}
