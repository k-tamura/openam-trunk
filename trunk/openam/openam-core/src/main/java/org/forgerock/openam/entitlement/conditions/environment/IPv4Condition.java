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
 * information: "Portions copyright [year] [name of copyright owner]".
 *
 * Copyright 2006 Sun Microsystems Inc
 */
/*
 * Portions Copyright 2011-2014 ForgeRock AS
 */

package org.forgerock.openam.entitlement.conditions.environment;

import com.sun.identity.entitlement.EntitlementException;
import com.sun.identity.entitlement.PrivilegeManager;
import com.sun.identity.shared.debug.Debug;
import org.forgerock.openam.core.CoreWrapper;
import org.forgerock.openam.utils.ValidateIPaddress;
import java.util.StringTokenizer;

import static com.sun.identity.entitlement.EntitlementException.*;

/**
 * An <code>EntitlementCondition</code> that can be used to enable/disable an authorization policy
 * based on the IP address and DNS name of the originating client requesting access to a resource.
 */
public class IPv4Condition extends IPvXCondition<Long> {

    /**
     * Constructs a new IPv4Condition instance.
     */
    public IPv4Condition() {
        this(PrivilegeManager.debug, new CoreWrapper());
    }

    /**
     * Constructs a new IPv4Condition instance.
     *
     * @param debug A Debug instance.
     * @param coreWrapper An instance of the CoreWrapper.
     */
    IPv4Condition(Debug debug, CoreWrapper coreWrapper) {
        super(debug, coreWrapper, Long.MAX_VALUE, Long.MAX_VALUE, IPVersion.IPV4);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Long stringToIp(String ip) throws EntitlementException {
        StringTokenizer st = new StringTokenizer(ip, ".");
        int tokenCount = st.countTokens();
        if (tokenCount != 4) {
            throw new EntitlementException(INVALID_PROPERTY_VALUE, new String[]{"ip", ip});
        }
        long ipValue = 0L;
        while (st.hasMoreElements()) {
            String s = st.nextToken();
            short ipElement;
            try {
                ipElement = Short.parseShort(s);
            } catch (Exception e) {
                throw new EntitlementException(INVALID_PROPERTY_VALUE, new String[]{"ip", ip});
            }
            if (ipElement < 0 || ipElement > 255) {
                throw new EntitlementException(INVALID_PROPERTY_VALUE, new String[]{"ipElement", s});
            }
            ipValue = ipValue * 256L + ipElement;
        }
        return ipValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean validateIpAddress(String ip) {
        return ValidateIPaddress.isIPv4(ip);
    }

}