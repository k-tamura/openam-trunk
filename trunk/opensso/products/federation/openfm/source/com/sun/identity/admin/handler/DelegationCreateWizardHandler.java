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
 * $Id: DelegationCreateWizardHandler.java,v 1.5.2.1 2009/12/23 00:53:53 farble1670 Exp $
 */

package com.sun.identity.admin.handler;

import com.sun.identity.admin.Resources;
import com.sun.identity.admin.model.DelegationWizardStep;
import com.sun.identity.admin.model.LinkBean;
import com.sun.identity.admin.model.MessageBean;
import com.sun.identity.admin.model.NameDelegationWizardStepValidator;
import com.sun.identity.admin.model.NextPopupBean;
import com.sun.identity.admin.model.ResourcesDelegationWizardStepValidator;
import com.sun.identity.admin.model.SubjectsDelegationWizardStepValidator;
import com.sun.identity.admin.model.ViewApplicationsBean;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;

public class DelegationCreateWizardHandler extends DelegationWizardHandler {
    @Override
    public void initWizardStepValidators() {
        getWizardStepValidators()[DelegationWizardStep.NAME.toInt()] = new NameDelegationWizardStepValidator(getWizardBean());
        getWizardStepValidators()[DelegationWizardStep.RESOURCES.toInt()] = new ResourcesDelegationWizardStepValidator(getWizardBean());
        getWizardStepValidators()[DelegationWizardStep.SUBJECTS.toInt()] = new SubjectsDelegationWizardStepValidator(getWizardBean());
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
        lbs.add(LinkBean.DELEGATION_MANAGE);

        return lbs;
    }

    private List<LinkBean> getCancelLinkBeans() {
        List<LinkBean> lbs = new ArrayList<LinkBean>();
        lbs.add(LinkBean.HOME);
        lbs.add(LinkBean.DELEGATION_CREATE);
        lbs.add(LinkBean.DELEGATION_MANAGE);

        return lbs;
    }

    public String createAction() {
        ViewApplicationsBean vasb = ViewApplicationsBean.getInstance();
        if (vasb.getViewApplications() == null || vasb.getViewApplications().size() == 0) {
            MessageBean mb = new MessageBean();
            Resources r = new Resources();
            mb.setSummary(r.getString(this, "noApplicationsSummary"));
            mb.setDetail(r.getString(this, "noApplicationsDetail"));
            mb.setSeverity(FacesMessage.SEVERITY_ERROR);
            getMessagesBean().addMessageBean(mb);

            return null;
        } else {
            return "delegation-create";
        }
    }
}
