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
 * Copyright 2013 ForgeRock Inc.
 */

package org.forgerock.openam.guice;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * Temporary fix to get around issues with Runtime classpath scanning.
 *
 * Simply returns all of the GuiceModules in AM that are used to configure the Guice Injector.
 *
 * @author Phill Cunnington
 */
public class GuiceConfiguration {

    /**
     * Returns the set of GuiceModules that will be used to configure the Guice Injector.
     *
     * @param moduleAnnotation The annotation the GuiceModules are annotated with. Not required in this case.
     * @return The Set of Guice Modules.
     */
    public Set<Class<?>> getGuiceModuleClasses(Class<? extends Annotation> moduleAnnotation) {
        Set<Class<?>> moduleClasses = new HashSet<Class<?>>();

        try {
            moduleClasses.add(Class.forName("org.forgerock.openam.auth.shared.AuthFilterGuiceModule"));
            moduleClasses.add(Class.forName("org.forgerock.openam.core.guice.CoreGuiceModule"));
            moduleClasses.add(Class.forName("org.forgerock.openam.forgerockrest.guice.ForgerockRestGuiceModule"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found!", e);
        }

        return moduleClasses;
    }
}
