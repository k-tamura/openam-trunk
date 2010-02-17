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
 * $Id: RemoveServiceTemplateAttrValuesReq.java,v 1.2 2008/06/25 05:52:34 qcheng Exp $
 *
 */

package com.iplanet.am.admin.cli;

import com.iplanet.am.sdk.AMException;
import com.iplanet.am.sdk.AMTemplate;
import com.iplanet.sso.SSOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

abstract class RemoveServiceTemplateAttrValuesReq
    extends ServiceTemplateAttrValuesReq
{
    /**
     * Constructs a new instance.
     *
     * @param targetDN distinguished name of target. 
     */        
    RemoveServiceTemplateAttrValuesReq(String targetDN) {
        super(targetDN);
    }

    void removeAttributeValues(AMTemplate tmpl)
        throws AdminException
    {
        if ((mapAttrValues != null) && !mapAttrValues.isEmpty()) {
            try {
                Map attrValues = tmpl.getAttributes();
                Map modified = new HashMap(mapAttrValues.size() *2);

                for (Iterator iter = mapAttrValues.keySet().iterator();
                    iter.hasNext();
                ) {
                    String attrName = (String)iter.next();
                    Set values = (Set)attrValues.get(attrName);

                    if ((values != null) && !values.isEmpty()) {
                        values.removeAll((Set)mapAttrValues.get(attrName));
                        modified.put(attrName, values);
                    }
                }

                if (!modified.isEmpty()) {
                    doLog(tmpl, AdminUtils.REMOVE_SERVTEMPLATE_ATTEMPT);
                    tmpl.setAttributes(modified);
                    tmpl.store();
//                    doLog(tmpl, "modify-servtemplate");
                    doLog(tmpl, AdminUtils.REMOVE_SERVTEMPLATE);
                }
            } catch (AMException ame) {
                throw new AdminException(ame);
            } catch (SSOException ssoe) {
                throw new AdminException(ssoe);
            }
        }
    }
}
