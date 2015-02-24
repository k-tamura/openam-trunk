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
 * Copyright 2014-2015 ForgeRock AS.
 */
package org.forgerock.openam.cts.api.filter;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TokenFilterTest {

    private TokenFilter filter;

    @BeforeMethod
    public void setup() {
        filter = new TokenFilter();
    }

    @Test (expectedExceptions = UnsupportedOperationException.class)
    public void shouldPreventModificationToReturnAttributes() {
        filter.getReturnFields().clear();
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void shouldPreventNullReturnAttribute() {
        filter.addReturnAttribute(null);
    }
}