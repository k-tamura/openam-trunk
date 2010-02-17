/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2005 Sun Microsystems Inc. All Rights Reserved
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
 * $Id: ValidatorBase.java,v 1.5 2008/06/25 05:42:31 qcheng Exp $
 *
 */

package com.sun.identity.common.validation;

import java.util.Iterator;
import java.util.Set;
import com.sun.identity.shared.validation.ValidationException;

/**
 * Base class for all validator classes.
 *
 * @deprecated As of OpenSSO version 8.0
 *             {@link com.sun.identity.shared.validation.ValidatorBase}
 */
public abstract class ValidatorBase
    implements Validator
{
    protected static final String resourceBundleName =
        "amValidation";

    /**
     * Performs validation on a string.
     *
     * @param strData String to be validated.
     * @throws ValidationException if <code>strData</code> is in incorrect
     *         format.
     */
    public void validate(String strData)
        throws ValidationException
    {
        performValidation(strData);
    }

    /**
     * Performs validation on a set of string.
     *
     * @param setData Set of string to be validated.
     * @throws ValidationException if one or more strings are in incorrect
     *         format.
     */
    public void validate(Set setData)
        throws ValidationException
    {
        for (Iterator iter = setData.iterator(); iter.hasNext(); ) {
            performValidation((String)iter.next());
        }
    }

    /**
     * Performs validation on a string.
     *
     * @param strData String to be validated.
     * @throws ValidationException if <code>strData</code> is in incorrect
     *         format.
     */
    protected abstract void performValidation(String strData)
        throws ValidationException;
}
