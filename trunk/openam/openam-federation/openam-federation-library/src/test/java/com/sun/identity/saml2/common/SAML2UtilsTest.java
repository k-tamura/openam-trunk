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

package com.sun.identity.saml2.common;

import com.sun.identity.shared.encode.URLEncDec;
import org.apache.commons.lang.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Random;

public class SAML2UtilsTest {

    @Test
    public void encodeDecodeTest() {

        // the max length of each random String to encode
        int maxStringLength = 300;
        String[] randomStrings = new String[2000];
        Random R = new Random();

        // generate the random Strings
        for (int i = 0; i < randomStrings.length; i++) {
            int size = R.nextInt(maxStringLength);
            // We don't want any 0 length arrays
            while (size == 0) {
                size = R.nextInt(maxStringLength);
            }
            randomStrings[i] = RandomStringUtils.randomAlphanumeric(size);
        }

        // Exercise the SAML2Utils encode/decode routines
        for (int i = 0; i < randomStrings.length; i++) {
            String encoded = SAML2Utils.encodeForRedirect(randomStrings[i]);
            String decoded = SAML2Utils.decodeFromRedirect(URLEncDec.decode(encoded));
            Assert.assertEquals(decoded, randomStrings[i]);
        }
    }
}
