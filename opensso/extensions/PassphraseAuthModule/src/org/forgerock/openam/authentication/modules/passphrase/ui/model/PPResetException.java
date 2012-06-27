/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2007 Sun Microsystems Inc. All Rights Reserved
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
 * $Id: PPResetException.java,v 1.2 2008/06/25 05:43:42 qcheng Exp $
 *
 */

package org.forgerock.openam.authentication.modules.passphrase.ui.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sun.identity.shared.locale.L10NMessageImpl;

/**
 * <code>PPResetException</code> is thrown whenever the model implementor
 * encounters an exception.
 */
public class PPResetException extends L10NMessageImpl {

	private List<String> errList = null;

	/**
	 * Creates a password reset Exception object.
	 * 
	 * @param msg exception message
	 */
	public PPResetException(String msg) {
		super(msg);
		errList = new ArrayList<String>(1);
		errList.add(msg);
	}

	/**
	 * Creates a password reset Exception object with localizable error message.
	 * 
	 * @param bundleName <code>ResourceBundle</code> name to be used for getting
	 *            localized error message
	 * @param errCode key to resource bundle
	 * @param args arguments to message. If it is not present pass as null
	 */
	public PPResetException(String bundleName, String errCode, Object[] args) {
		super(bundleName, errCode, args);
		errList = new ArrayList<String>(1);
		errList.add(getMessage());
	}

	/**
	 * Creates a password reset Exception object.
	 * 
	 * @param errors
	 *            list of error messages
	 */
	public PPResetException(List<String> errors) {
		super(errors.toArray().toString());
		errList = errors;
	}

	/**
	 * Creates a password reset Exception object.
	 * 
	 * @param t <code>Throwable</code> instance
	 */
	public PPResetException(Throwable t) {
		super(t);
		errList = new ArrayList<String>(1);
		errList.add(t.getMessage());
	}

	/**
	 * Returns error list.
	 * 
	 * @return error list
	 */
	public List<?> getErrors() {
		return (errList == null) ? Collections.EMPTY_LIST : errList;
	}
}