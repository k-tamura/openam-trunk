/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2005 Sun Microsystems Inc. All Rights Reserved
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
 * $Id: AMStaticGroupImpl.java,v 1.4 2008/06/25 05:41:23 qcheng Exp $
 *
 */

package com.iplanet.am.sdk;

import java.util.Map;
import java.util.Set;

import com.iplanet.sso.SSOException;
import com.iplanet.sso.SSOToken;
import com.iplanet.sso.SSOTokenManager;

/**
 * The <code>AMStaticGroupImpl</code> class implements interface AMStaticGroup
 *
 * @deprecated  As of Sun Java System Access Manager 7.1.
 */

class AMStaticGroupImpl extends AMGroupImpl implements AMStaticGroup {
    public AMStaticGroupImpl(SSOToken ssoToken, String DN) {
        super(ssoToken, DN, GROUP);
    }

    /**
     * Adds users to the static group.
     * 
     * @param users
     *            The set of user DN's to be added to the static group.
     */
    public void addUsers(Set users) throws AMException, SSOException {
        SSOTokenManager.getInstance().validateToken(super.token);
        dsServices.modifyMemberShip(super.token, users, super.entryDN, GROUP,
                ADD_MEMBER);
    }

    /**
     * Removes users from the static group.
     * 
     * @param users
     *            The set of user DN's to be removed from the static group.
     */
    public void removeUsers(Set users) throws AMException, SSOException {
        SSOTokenManager.getInstance().validateToken(super.token);
        dsServices.modifyMemberShip(super.token, users, super.entryDN, GROUP,
                REMOVE_MEMBER);
    }

    /**
     * Returns true if the group is subscribable.
     * 
     * @return true if the group is subscribable.
     */
    public boolean isSubscribable() throws AMException, SSOException {
        return getBooleanAttribute(SUBSCRIBABLE_ATTRIBUTE);
    }

    /**
     * Sets subscribability of the group.
     * 
     * @param subscribable
     *            true if the group is subscribable.
     */
    public void setSubscribable(boolean subscribable) throws AMException,
            SSOException {
        setBooleanAttribute(SUBSCRIBABLE_ATTRIBUTE, subscribable);
        store();
    }

    public void assignServices(String serviceName, Map attrMap)
            throws AMException, SSOException {
    }

}
