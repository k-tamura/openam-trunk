/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2008 Sun Microsystems, Inc. All Rights Reserved.
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
 * $Id: Tuning.java,v 1.1 2008/07/02 18:56:23 kanduls Exp $
 */

package com.sun.identity.tune.intr;

import com.sun.identity.tune.common.AMTuneException;
import com.sun.identity.tune.config.AMTuneConfigInfo;

/**
 * <code>Tuning><\code> is main interface it declares methods for tuning
 * components.
 *  
 */
public interface Tuning {
    
    /**
     * This method initializes the Performance tuning configuration information.
     * 
     * @param configInfo Instance of AMTuneConfigInfo class
     * @throws com.sun.identity.tune.common.AMTuneException
     */
    public void initialize(AMTuneConfigInfo configInfo) 
    throws AMTuneException;
    
    /**
     * Start tuning by calling the required methods sequentially.
     * 
     * @throws com.sun.identity.tune.common.AMTuneException
     */
    public void startTuning() throws AMTuneException;

}
