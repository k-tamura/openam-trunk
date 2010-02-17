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
 * $Id: COSNotFoundException.java,v 1.3 2008/06/25 05:41:46 qcheng Exp $
 *
 */

package com.iplanet.ums.cos;

import com.iplanet.ums.UMSException;

/**
 * The exception thrown by COS Manager if a COS object is not found.
 * @supported.api
 */
public class COSNotFoundException extends UMSException {

    /**
     * Constructs the exception with no message.
     */
    public COSNotFoundException() {
    }

    /**
     * Constructs the exception with a message.
     * 
     * @param msg
     *            the message describing cause of the exception
     */
    public COSNotFoundException(String msg) {
        super(msg);
    }

    /**
     * Constructs the exception with a message and an embedded exception.
     * 
     * @param msg
     *            the message describing the cause of the exception
     * @param rootCause
     *            the exception that led to this exception
     */
    public COSNotFoundException(String msg, Throwable rootCause) {
        super(msg, rootCause);
    }
}
