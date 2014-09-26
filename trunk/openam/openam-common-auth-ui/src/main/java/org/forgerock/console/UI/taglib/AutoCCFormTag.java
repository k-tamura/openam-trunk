/*
 * The contents of this file are subject to the terms of the Common Development and
 * Distribution License (the License). You may not use this file except in compliance with the
 * License.
 *
 * You can obtain a copy of the License at legal/CDDLv1.0.txt. See the License for the
 * specific language governing permission and limitations under the License.
 *
 * When distributing Covered Software, include this CDDL Header Notice in each file and include
 * the License file at legal/CDDLv1.0.txt. If applicable, add the following below the CDDL
 * Header, with the fields enclosed by brackets [] replaced by your own identifying
 * information: "Portions copyright [year] [name of copyright owner]".
 *
 * Copyright 2014 ForgeRock AS.
 */

package org.forgerock.console.UI.taglib;

import com.iplanet.am.util.SystemProperties;
import com.iplanet.jato.util.NonSyncStringBuffer;
import com.sun.identity.shared.Constants;
import com.sun.web.ui.taglib.html.CCFormTag;


public class AutoCCFormTag extends CCFormTag {

    private boolean autoCompleteEnabled = true;

    public AutoCCFormTag() {
        super();
        autoCompleteEnabled = SystemProperties.getAsBoolean(Constants.AUTOCOMPLETE_ENABLED, true);
    }

    @Override
    public void appendStyleAttributes(NonSyncStringBuffer buffer) {

        if (!autoCompleteEnabled) {
            appendAttribute(buffer, "autocomplete", "off");
        }

        super.appendStyleAttributes(buffer);
    }
}
