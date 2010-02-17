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
 * $Id: DateRangeCondition.java,v 1.2 2009/10/13 16:04:40 farble1670 Exp $
 */
package com.sun.identity.admin.model;

import com.sun.identity.admin.Resources;
import com.sun.identity.entitlement.EntitlementCondition;
import com.sun.identity.entitlement.TimeCondition;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import javax.faces.event.ValueChangeEvent;

public class DateRangeCondition
        extends ViewCondition
        implements Serializable {

    private Date startDate = new Date();
    private Date endDate = new Date();
    private boolean endDateDisabled = false;

    public EntitlementCondition getEntitlementCondition() {
        TimeCondition tc = new TimeCondition();
        tc.setDisplayType(getConditionType().getName());

        String startDateString = getEDateString(startDate);
        tc.setStartDate(startDateString);

        if (endDateDisabled) {
            tc.setEndDate(null);
        } else {
            String endDateString = getEDateString(endDate);
            tc.setEndDate(endDateString);
        }

        return tc;
    }

    private String getEDateString(Date date) {
        StringBuffer b = new StringBuffer();
        Formatter f = new Formatter(b);

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        f.format("%4d:%02d:%02d", c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1,
                c.get(Calendar.DAY_OF_MONTH));

        return b.toString();
    }

    private String getStartDateString() {
        return getEDateString(startDate);
    }

    private String getEndDateString() {
        if (endDateDisabled) {
            Resources r = new Resources();
            String none = r.getString(this, "none");
            return none;
        }
        return getEDateString(endDate);
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void noEndDateChanged(ValueChangeEvent event) {
        boolean checked = ((Boolean) event.getNewValue()).booleanValue();
        setEndDateDisabled(checked);
    }

    @Override
    public String toString() {
        return getTitle() + ":{" + getStartDateString() + " > " + getEndDateString() + "}";
    }

    public boolean isEndDateDisabled() {
        return endDateDisabled;
    }

    public void setEndDateDisabled(boolean endDateDisabled) {
        this.endDateDisabled = endDateDisabled;
    }
}
