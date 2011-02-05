/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2007 Sun Microsystems Inc. All Rights Reserved
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
 * $Id: AMDelimitAttrTokenizer.java,v 1.2 2008/06/25 05:42:47 qcheng Exp $
 *
 */

/*
 * Portions Copyrighted [2011] [ForgeRock AS]
 */
package com.sun.identity.console.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class AMDelimitAttrTokenizer {
    private static AMDelimitAttrTokenizer instance =
        new AMDelimitAttrTokenizer();

    private AMDelimitAttrTokenizer() {
    }

    public static AMDelimitAttrTokenizer getInstance() {
        return instance;
    }

    public List tokenizes(String token) {
        return tokenizes(token, ",");
    }

    public List tokenizes(String token, String delimit) {
        List list = new ArrayList();
        StringTokenizer st = new StringTokenizer(token, delimit);

        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }

        return list;
    }

    public String deTokenizes(List list) {
        return deTokenizes(list, ",");
    }

    public String deTokenizes(List list, String delimit) {
        StringBuilder buff = new StringBuilder(200);
        boolean firstElement = true;

        if ((list != null) && !list.isEmpty()) {
            for (Iterator iter = list.iterator(); iter.hasNext(); ) {
                if (!firstElement) {
                    buff.append(delimit);
                }
                firstElement = false;
                buff.append((String)iter.next());
            }
        }

        return buff.toString();
    }
}
