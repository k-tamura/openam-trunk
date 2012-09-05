/**
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
 * $Id: ISearchIndex.java,v 1.2 2009/04/09 13:15:02 veiming Exp $
 */

package com.sun.identity.entitlement.interfaces;

import com.sun.identity.entitlement.ResourceSearchIndexes;

/**
 * Search index interface defines the method required for generating indexes
 * for search datastore for privileges.
 */
public interface ISearchIndex {
    /**
     * Returns search indexes for a given resource.
     *
     * @param resource Resource for generating the indexes.
     * @return search indexes for a given resource.
     */
    ResourceSearchIndexes getIndexes(String resource);
}
