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
 * $Id: SsoServerFedCOTMemberEntryImpl.java,v 1.2 2009/08/07 20:07:23 bigfatrat Exp $
 *
 */

package com.sun.identity.monitoring;

import com.sun.identity.shared.debug.Debug;
import com.sun.management.snmp.agent.SnmpMib;
import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * This class extends the "SsoServerFedCOTMemberEntry" class.
 */
public class SsoServerFedCOTMemberEntryImpl extends SsoServerFedCOTMemberEntry
{
    private static Debug debug = null;
    private static String myMibName;

    /**
     * Constructor
     */
    public SsoServerFedCOTMemberEntryImpl (SnmpMib myMib) {
        super(myMib);
        myMibName = myMib.getMibName();
        init();
    }

    private void init() {
        if (debug == null) {
            debug = Debug.getInstance("amMonitoring");
        }
    }

    public ObjectName
        createSsoServerFedCOTMemberEntryObjectName (MBeanServer server)
    {
        String classModule = "SsoServerFedCOTMemberEntryImpl." +
            "createSsoServerFedCOTMemberEntryObjectName: ";
        String prfx = "ssoServerFedCOTMemberEntry.";

        if (debug.messageEnabled()) {
            debug.message(classModule +
                "\n    SsoServerRealmIndex = " +
                SsoServerRealmIndex +
                "\n    FedCOTMemberName = " +
                FedCOTMemberName +
                "\n    FedCOTMemberType = " +
                FedCOTMemberType +
                "\n    FedCOTMemberIndex = " +
                FedCOTMemberIndex);
        }

        String realm = Agent.getEscRealmNameFromIndex(SsoServerRealmIndex);
        String objname = myMibName +
            "/ssoServerFedCOTMemberTable:" +
            prfx + "ssoServerRealmName=" + realm + "," +
            prfx + "FedCOTMemberType=" + FedCOTMemberType + "," +
            prfx + "FedCOTMemberName=" + FedCOTMemberName;

        try {
            if (server == null) {
                return null;
            } else {
                // is the object name sufficiently unique?
                return
                    new ObjectName(objname);
            }
        } catch (Exception ex) {
            debug.error(classModule + objname, ex);
            return null;
        }
    }
}
