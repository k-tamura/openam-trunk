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
 * $Id: StaticViewAttribute.java,v 1.1 2009/08/19 05:40:55 veiming Exp $
 */

package com.sun.identity.admin.model;

public class StaticViewAttribute extends ViewAttribute {
    private String value;
    private boolean valueEditable = false;

    public StaticViewAttribute() {
        super();
    }

    @Override
    public void setEditable(boolean editable) {
        super.setEditable(editable);
        setValueEditable(editable);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof StaticViewAttribute)) {
            return false;
        }
        StaticViewAttribute sva = (StaticViewAttribute)other;
        return sva.toString().equals(toString());
    }

    @Override
    public String toString() {
        return getTitle() + "=" + value;
    }

    public boolean isValueEditable() {
        if (value == null || value.length() == 0) {
            return true;
        }
        return valueEditable;
    }

    public void setValueEditable(boolean valueEditable) {
        this.valueEditable = valueEditable;
    }
}
