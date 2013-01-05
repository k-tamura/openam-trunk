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

package org.forgerock.identity.authentication.modules.adaptivedeviceprint.configuration;

import org.forgerock.identity.authentication.modules.common.config.AttributeNameMapping;

/**
 * Configuration of the device print module. Put here all variables which can't
 * be assigned to any other variables group.
 * 
 * @author ljaromin
 * 
 */
public class DevicePrintLoginModuleCommonConfig {

	/**
	 * Age of the persistent cookie. When age is exceeded, cookie is replaced
	 * with the new one
	 */
	@AttributeNameMapping("iplanet-am-auth-adaptive-device-print-persistence-cookie-expire-days")
	private Integer persistenceCookieAge;

	/**
	 * Name of the field in user record where profile information is stored
	 */
	@AttributeNameMapping("iplanet-am-auth-adaptive-device-print-ldap-field-name")
	private String adaptiveProfilesFieldName;

	/**
	 * Name of the profile expiration days configuration attribute
	 */
	@AttributeNameMapping("iplanet-am-auth-adaptive-device-print-profile-expiration-days")
	private Integer profileExpirationDays;

	public Integer getProfileExpirationDays() {
		return profileExpirationDays;
	}

	public void setProfileExpirationDays(Integer profileExpirationDays) {
		this.profileExpirationDays = profileExpirationDays;
	}

	public String getAdaptiveProfilesFieldName() {
		return adaptiveProfilesFieldName;
	}

	public void setAdaptiveProfilesFieldName(String adaptiveProfilesFieldName) {
		this.adaptiveProfilesFieldName = adaptiveProfilesFieldName;
	}

	public Integer getPersistenceCookieAge() {
		return persistenceCookieAge;
	}

	public void setPersistenceCookieAge(Integer persistenceCookieAge) {
		this.persistenceCookieAge = persistenceCookieAge;
	}

	@Override
	public String toString() {
		return "DevicePrintLoginModuleCommonConfig [persistenceCookieAge=" 
				+ persistenceCookieAge + ", profileExpirationDays=" 
				+ profileExpirationDays + ", adaptiveProfilesFieldName="
				+ adaptiveProfilesFieldName + "]";
	}

}
