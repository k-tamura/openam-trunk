/*
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
 */
/*
 * Portions Copyrighted 2013 Syntegrity.
 * Portions Copyrighted 2013 ForgeRock Inc.
 */

package org.forgerock.openam.authentication.modules.deviceprint;

import com.iplanet.sso.SSOException;
import com.sun.identity.authentication.modules.hotp.HOTPService;
import com.sun.identity.authentication.spi.AMLoginModule;
import com.sun.identity.idm.AMIdentity;
import com.sun.identity.idm.AMIdentityRepository;
import com.sun.identity.idm.IdRepoException;
import com.sun.identity.idm.IdSearchControl;
import com.sun.identity.idm.IdSearchResults;
import com.sun.identity.idm.IdType;
import com.sun.identity.shared.debug.Debug;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.login.LoginException;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * The OpenAM Authentication module for providing additional authentication using client Device Print information.
 */
public class DevicePrintModule extends AMLoginModule {

    private static final String AUTH_MODULE_NAME = "amAuthDevicePrint";
    private static final Debug DEBUG = Debug.getInstance(AUTH_MODULE_NAME);

    private final DevicePrintModuleInitialiser moduleInitialiser;
    private DevicePrintAuthenticationService devicePrintAuthenticationService;

    private String userName;

    /**
     * Constructs an instance of the DevicePrintModule.
     */
    public DevicePrintModule() {
        moduleInitialiser = new DevicePrintModuleInitialiser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(Subject subject, Map sharedState, Map options) {

        userName = (String) sharedState.get(getUserKey());

        AMIdentityRepository amIdentityRepository = getAMIdentityRepository(getRequestOrg());
        AMIdentity amIdentity = getIdentity(userName);

        HOTPService hotpService = moduleInitialiser.getHOTPService(getLoginLocale(), amCache, userName,
                amIdentityRepository, options);
        devicePrintAuthenticationService = moduleInitialiser.getDevicePrintAuthenticationService(amIdentity,
                getHttpServletRequest(), hotpService, options);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int process(Callback[] callbacks, int state) throws LoginException {
        return devicePrintAuthenticationService.process(callbacks, state);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Principal getPrincipal() {
        return new DevicePrintModulePrinciple(userName);
    }

    /**
     * Gets the user's AMIdentity from LDAP.
     *
     * @param userName The user's name.
     * @return The AMIdentity for the user.
     */
    public AMIdentity getIdentity(String userName) {
        AMIdentity amIdentity = null;
        AMIdentityRepository amIdRepo = getAMIdentityRepository(getRequestOrg());

        IdSearchControl idsc = new IdSearchControl();
        idsc.setAllReturnAttributes(true);
        Set<AMIdentity> results = Collections.EMPTY_SET;

        try {
            idsc.setMaxResults(0);
            IdSearchResults searchResults = amIdRepo.searchIdentities(IdType.USER, userName, idsc);
            if (searchResults != null) {
                results = searchResults.getSearchResults();
            }

            if (results == null || results.size() != 1) {
                throw new IdRepoException("getIdentity : More than one user found");
            }

            amIdentity = results.iterator().next();
        } catch (IdRepoException e) {
            DEBUG.error("Error searching Identities with username : " + userName, e);
        } catch (SSOException e) {
            DEBUG.error("Module exception : ", e);
        }

        return amIdentity;
    }
}
