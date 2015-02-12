/*
 * Copyright 2013-2015 ForgeRock AS.
 *
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
 */

package org.forgerock.openam.sm.datalayer.api.query;

import java.lang.reflect.ParameterizedType;

import org.forgerock.guice.core.InjectorHolder;

import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Types;

/**
 * Responsible for generating instances of QueryBuilder and QueryFilter to perform queries against
 * the connection.
 * @param <T> The type of connection queries are made for.
 * @param <F> The type of filters that make up the queries.
 */
public interface QueryFactory<T, F> {

    /**
     * Generate an instance of the QueryBuilder.
     *
     * @return A non null instance of the QueryBuilder.
     */
    public QueryBuilder<T, F> createInstance();

    /**
     * Generate an instance of the QueryFilter for use with the QueryBuilder.
     *
     * @return A non null QueryFilter instance.
     */
    public QueryFilter<F> createFilter();
}
