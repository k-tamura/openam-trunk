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
 * $Id: OrCondition.java,v 1.2 2009/09/05 00:24:04 veiming Exp $
 */
package com.sun.identity.entitlement;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.security.auth.Subject;

/**
 * <code>EntitlementCondition</code> wrapper on a set of
 * <code>EntitlementCondition</code>(s) to provide boolean OR logic
 * Membership is of OrCondition is satisfied if the user is a member of any
 * of the wrapped EntitlementCondition
 */
public class OrCondition extends LogicalCondition {
    /**
     * Constructor.
     */
    public OrCondition() {
        super();
    }

    /**
     * Constructor.
     *
     * @param eConditions wrapped EntitlementCondition(s)
     */
    public OrCondition(Set<EntitlementCondition> eConditions) {
        super(eConditions);
    }

    /**
     * Constructor.
     *
     * @param eConditions wrapped EntitlementCondition(s)
     * @param pConditionName subject name as used in OpenSSO policy,
     * this is releavant only when UserECondition was created from
     * OpenSSO policy Condition
     */
    public OrCondition(
        Set<EntitlementCondition> eConditions,
        String pConditionName
    ) {
        super(eConditions, pConditionName);
    }


    /**
     * Returns <code>ConditionDecision</code> of
     * <code>EntitlementCondition</code> evaluation
     *
     * @param realm Realm name.
     * @param subject EntitlementCondition who is under evaluation.
     * @param resourceName Resource name.
     * @param environment Environment parameters.
     * @return <code>ConditionDecision</code> of
     * <code>EntitlementCondition</code> evaluation
     * @throws com.sun.identity.entitlement,  EntitlementException in case
     * of any error
     */
    public ConditionDecision evaluate(
        String realm,
        Subject subject,
        String resourceName,
        Map<String, Set<String>> environment
    ) throws EntitlementException {
        Set<EntitlementCondition> eConditions = getEConditions();
        if ((eConditions == null) || eConditions.isEmpty()) {
            return new ConditionDecision(true, Collections.EMPTY_MAP);
        }

        ConditionDecision decision = new ConditionDecision(false,
            Collections.EMPTY_MAP);
        for (EntitlementCondition ec : eConditions) {
            ConditionDecision d = ec.evaluate(realm, subject, resourceName,
                environment);
            decision.addAdvices(d);

            if (d.isSatisfied()) {
                decision.setSatisfied(true);
                return decision;
            }
        }

        return decision;
    }
}
