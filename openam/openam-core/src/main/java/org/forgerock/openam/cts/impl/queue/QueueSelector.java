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
 * Copyright 2014 ForgeRock AS.
 */
package org.forgerock.openam.cts.impl.queue;

import org.forgerock.util.Reject;

import java.lang.IllegalArgumentException;

/**
 * QueueSelector is responsible for selecting an appropriate asynchronous queue for
 * a task to be performed on.
 *
 * This selection is based on the Token ID to ensure that requests to the same Token ID
 * are processed by the same queue. By enforcing this invariant, we can be certain that
 * we can avoid concurrent modification exceptions due to operations being performed on
 * the same Token by different threads.
 *
 * This algorithm is prone to modulus bias towards zero for numbers that are not a power of
 * two. Therefore this algorithm should only be used if this is the case.
 */
public final class QueueSelector {
    /**
     * Static utility class
     */
    private QueueSelector() {}

    public static int select(String tokenId, int queues) {
        Reject.ifTrue(tokenId == null, "Token ID cannot be null");
        Reject.ifTrue(queues <= 0, "queues must be positive");

        int value = Math.abs(tokenId.hashCode());
        return value % queues;
    }

    /**
     * Not every even number is a power of two.
     * @see <a href="http://en.wikipedia.org/wiki/Power_of_two#Fast_algorithm_to_check_if_a_positive_number_is_a_power_of_two">Wikipedia</a>
     *
     * @return true if the integer is a power of two.
     */
    public static boolean powerOfTwo(int value) {
        return (value & (value - 1)) == 0;
    }

    /**
     * Locate a power of two that is less than or equal to the given number.
     *
     * @see #select(String, int)
     *
     * @param value Starting value, must be positive.
     * @return A valid power of two less than or equal to the given value. Not negative.
     * @throws IllegalArgumentException If no power of two was found.
     */
    public static int findPowerOfTwo(int value) {
        for (int ii = value; ii > 0; ii--) {
            if (powerOfTwo(ii)) {
                return ii;
            }
        }
        throw new IllegalArgumentException("No power of two found.");
    }
}
