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
 * $Id: VersionCheck.java,v 1.9 2008/12/11 18:26:37 veiming Exp $
 *
 */

/*
 * Portions Copyrighted 2010-2011 ForgeRock AS
 */

package com.sun.identity.tools.bundles;

import com.iplanet.am.util.SystemProperties;
import com.sun.identity.setup.Bootstrap;
import com.sun.identity.setup.ConfiguratorException;
import com.sun.identity.shared.debug.Debug;
import java.util.Locale;
import java.util.ResourceBundle;

public class VersionCheck implements SetupConstants {
    private static ResourceBundle bundle = ResourceBundle.getBundle(
        System.getProperty(SETUP_PROPERTIES_FILE, DEFAULT_PROPERTIES_FILE));
    private final static String MISMATCH_MSG_TO_DEBUGLOG =
            "openam.mismatch.message.to.debuglog";
    private final static String IGNORE_VERSION_CHECK =
            "openam.ignore.version.check";
    private final static Debug debug = Debug.getInstance("amCLI");
    
    /**
     * Check whether the version AM is valid.
     */
    public static int isValid() {
        try {
            Bootstrap.load();
        } catch (ConfiguratorException ex) {
            System.err.println(ex.getL10NMessage(Locale.getDefault()));
            System.exit(1);
        } catch (Exception ex) {
            System.out.println(bundle.getString("message.error.amconfig") + " "
                + System.getProperty(Bootstrap.JVM_OPT_BOOTSTRAP));
            return 1;
        }
        return isVersionValid();
    }

    public static int isVersionValid() {
        String javaExpectedVersion = System.getProperty(JAVA_VERSION_EXPECTED);
        String amExpectedVersion = System.getProperty(AM_VERSION_EXPECTED);
        String configVersion = SystemProperties.get(System.getProperty(
            AM_VERSION_CURRENT)).trim();
       
        if (!versionCompatible(System.getProperty(JAVA_VERSION_CURRENT),
            javaExpectedVersion)) {
            System.out.println(bundle.getString("message.error.version.jvm") +
                " " + javaExpectedVersion + " .");
            return 1;
        }

        Boolean writeToDebugLog = Boolean.valueOf(
                SystemProperties.get(MISMATCH_MSG_TO_DEBUGLOG, "true"))
                && Boolean.valueOf(
                      SystemProperties.get(IGNORE_VERSION_CHECK, "true"));

        // checking case like this, server version is 
        // OpenSSO Express Build 6a(2008-December-9 02:22) but
        // ssoadm version is (2008-December-10 01:19)
        if (configVersion.length() != amExpectedVersion.length()) {
            if (writeToDebugLog) {
                debug.warning("VersionCheck.isVersionValid: "
                        + bundle.getString("message.error.version.am")
                        + " " + amExpectedVersion + " .");
            } else {
                System.out.println(bundle.getString("message.error.version.am")
                        + " " + amExpectedVersion + " .");
            }
            return 1;
        }
        
        if (!versionCompatible(configVersion, amExpectedVersion)) {
            if (writeToDebugLog) {
                debug.warning("VersionCheck.isVersionValid: "
                        + bundle.getString("message.error.version.am")
                        + " " + amExpectedVersion + " .");
            } else {
                System.out.println(bundle.getString("message.error.version.am")
                        + " " + amExpectedVersion + " .");
            }
            return 1;
        }
        return 0;
    }

    /**
     * Check whether the version String is compatible with expected.
     * 
     * @param currentVersion The string of current version.
     * @param expectedVersion The string of expected version.
     * @return A boolean value to indicate whether the version is compatible.
     */
    protected static boolean versionCompatible(String currentVersion,
        String expectedVersion) {
        if (Character.isDigit(expectedVersion.charAt(expectedVersion.length()
            - 1))) {
            if (!currentVersion.startsWith(expectedVersion)) {
                return false;
            }
        } else {
            boolean backwardCom = false;
            int compareLength = Math.min(expectedVersion.length() - 1,
                currentVersion.length());
            if (expectedVersion.endsWith("-")) {
                backwardCom = true;
            }
            for (int i = 0; i < compareLength; i++) {
                if (backwardCom) {
                    if (expectedVersion.charAt(i) < currentVersion.charAt(i)) {
                        return false;
                    }
                } else {
                    if (currentVersion.charAt(i) > expectedVersion.charAt(i)) {
                        break;
                    }
                    if (currentVersion.charAt(i) < expectedVersion.charAt(i)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
