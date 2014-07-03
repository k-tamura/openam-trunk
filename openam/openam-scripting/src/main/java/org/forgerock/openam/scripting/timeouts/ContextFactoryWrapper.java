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
package org.forgerock.openam.scripting.timeouts;

import org.mozilla.javascript.ContextFactory;

/**
 * Instantiable wrapper for the {@link ContextFactory} to ease testing.
 */
public class ContextFactoryWrapper {

    /**
     * Passes through to static method call.
     */
    public boolean hasExplicitGlobal() {
        return ContextFactory.hasExplicitGlobal();
    }

    /**
     * Passes through to static method call.
     */
    public void initGlobal(ContextFactory factory) {
        ContextFactory.initGlobal(factory);
    }

}
