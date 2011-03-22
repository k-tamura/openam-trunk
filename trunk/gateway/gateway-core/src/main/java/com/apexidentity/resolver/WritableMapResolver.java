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
 * information: "Portions Copyrighted [year] [name of copyright owner]".
 *
 * Copyright © 2010–2011 ApexIdentity Inc. All rights reserved.
 */

package com.apexidentity.resolver;

// Java Standard Edition
import java.util.Collections;
import java.util.Set;

// ApexIdentity Core Library
import com.apexidentity.util.WritableMap;

/**
 * Resolves {@link WritableMap} objects. This implementation uses duck-typing to attempt
 * access to the object. It will successfully resolve the object if it exposes the methods
 * defined in the {@code WritableMap} interface.
 *
 * @author Paul C. Bryan
 */
public class WritableMapResolver implements Resolver {

    @Override
    public Class getKey() {
        return WritableMap.class;
    }

    @Override
    public Object get(Object object, Object element) {
        return Resolver.UNRESOLVED;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object put(Object object, Object element, Object value) {
        try {
            return WritableMap.DUCK.cast(object).put(element, value);
        }
        catch (ClassCastException cce) {
            return Resolver.UNRESOLVED;
        }
    }

    @Override
    public Object remove(Object object, Object element) {
        try {
            return WritableMap.DUCK.cast(object).remove(element);
        }
        catch (ClassCastException cce) {
            return Resolver.UNRESOLVED;
        }
    }

    @Override
    public boolean containsKey(Object object, Object element) {
        return false;
    }

    @Override
    public Set<? extends Object> keySet(Object object) {
        return Collections.emptySet();
    }
}
