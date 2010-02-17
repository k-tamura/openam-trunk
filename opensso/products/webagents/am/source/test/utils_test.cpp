/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2006 Sun Microsystems Inc. All Rights Reserved
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
 *
 * Test for AM utilities:  
 *     am_status_to_name, am_status_to_string
 *     more to come.
 * 
 * 
 */

#include <iostream>

#include "am_types.h"

using std::cout;
using std::endl;

void status_test() {
    for (int i = -1; i <= AM_NUM_ERROR_CODES; i++) {
        cout << am_status_to_name((am_status_t)i) 
	     << " '" << am_status_to_string((am_status_t)i) << "'"
	     << endl;
    }
}


int main(int argc, char *argv[])
{
    status_test();
}
