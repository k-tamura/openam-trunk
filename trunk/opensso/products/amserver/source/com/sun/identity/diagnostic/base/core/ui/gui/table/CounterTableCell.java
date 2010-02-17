/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2008 Sun Microsystems Inc. All Rights Reserved
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
 * $Id: CounterTableCell.java,v 1.1 2008/11/22 02:19:57 ak138937 Exp $
 *
 */

package com.sun.identity.diagnostic.base.core.ui.gui.table;

public class CounterTableCell extends LabelTableCell {
    
    int elapsedTime;
    
    /** Creates a new instance of CounterTableCell */
    public CounterTableCell() {
        super();
        elapsedTime = 0;
        setText(parseToString());
    }
    
    public void increase() {
        elapsedTime++;
        setText(parseToString());
    }
    
    private String parseToString() {
        int hour = elapsedTime / 3600;
        int min = (elapsedTime - (hour * 3600)) / 60;
        int sec = elapsedTime % 60;
        String hourString = String.valueOf(hour);
        String minString = String.valueOf(min);
        String secString = String.valueOf(sec);
        return hourString + ":" + (minString.length() < 2 ? "0" +
            minString : minString) + ":" + (secString.length() < 2  ? "0" + 
            secString : secString);
    }
}
