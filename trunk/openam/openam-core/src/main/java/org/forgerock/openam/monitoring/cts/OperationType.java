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
 *
 */

package org.forgerock.openam.monitoring.cts;

/**
 * Defines the available CRUDL operations which can be performed on {@link org.forgerock.openam.tokens.TokenType}
 * tokens.
 *
 * If new operations are added, this enum must be updated via APPENDING to the end of the enum list.
 *
 * Existing operations MUST STAY in the order they are defined. This is validated by OperationTypeTest.
 */
public enum OperationType {

    CREATE(),
    READ(),
    UPDATE(),
    DELETE(),
    LIST()

}
