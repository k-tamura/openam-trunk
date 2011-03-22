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

package com.apexidentity.io;

import java.io.OutputStream;

/**
 * An output stream that discards all data written to it. GNDN.
 *
 * @author Paul C. Bryan
 */
public class NullOutputStream extends OutputStream {

    /**
     * Has no effect.
     */
    @Override
    public void write(int b) {
        // goes nowhere, does nothing
    }

    /**
     * Has no effect.
     */
    @Override
    public void write(byte[] b) {
        // goes nowhere, does nothing
    }

    /**
     * Has no effect.
     */
    @Override
    public void write(byte[] b, int off, int len) {
        // goes nowhere, does nothing
    }
}
