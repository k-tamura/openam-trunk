/*
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
 */

/*global define, require*/

define(["org/forgerock/libwrappers/JQueryWrapper"], function($) {
    var obj = {};
    
    obj.getLoginForm = function() {
        return $("[name=Login]");
    };
   
    obj.getDevicePrintInfoInput = function() {
	    if(!obj.getLoginForm().find("[name=devicePrintInfo]").length) {
		    obj.getLoginForm().append("<input name='devicePrintInfo' type='hidden' />");		  
	    }
	  
	    return obj.getLoginForm().find("[name=devicePrintInfo]");	   
    };
        
    return obj; 
});
/*global define, require*/

/*
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
 */

define(["org/forgerock/libwrappers/JQueryWrapper"], function($) {
    var obj = {};
    
    obj.getLoginForm = function() {
        return $("[name=Login]");
    };
   
    obj.getDevicePrintInfoInput = function() {
	    if(!obj.getLoginForm().find("[name=devicePrintInfo]").length) {
		    obj.getLoginForm().append("<input name='devicePrintInfo' type='hidden' />");		  
	    }
	  
	    return obj.getLoginForm().find("[name=devicePrintInfo]");	   
    };
        
    return obj; 
});
