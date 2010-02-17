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
 * $Id: PolicyCache.java,v 1.5 2009/06/30 17:47:38 veiming Exp $
 */

package com.sun.identity.entitlement.opensso;

import com.sun.identity.entitlement.Privilege;
import java.util.HashMap;
import com.sun.identity.entitlement.ReferralPrivilege;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Policy Cache
 */
class PolicyCache {
    private Cache cache;
    static HashMap<String, Integer> countByRealm;
    private ReadWriteLock rwlock = new ReentrantReadWriteLock();

    PolicyCache(int size) {
        cache = new Cache(size);
        countByRealm = new HashMap<String, Integer>();
    }

    /**
     * Caches a privilege.
     *
     * @param dn DN of the privilege object.
     * @param p Privilege.
     */
    public void cache(String dn, Privilege p, String realm) {
        rwlock.writeLock().lock();
        try {
            Object e = cache.put(dn, p);
            if (e == null) {
                // Update count only if added, not if replaced
                Integer i = countByRealm.get(realm);
                int count = (i == null) ? 1 : i.intValue() + 1;
                countByRealm.put(realm, count);
            }
        } finally {
            rwlock.writeLock().unlock();
        }
    }

    /**
     * Caches a referral privilege.
     *
     * @param dn DN of the referral privilege object.
     * @param p Referral privilege.
     */
    public void cache(String dn, ReferralPrivilege p, String realm) {
        rwlock.writeLock().lock();
        try {
            cache.put(dn, p);
        } finally {
            rwlock.writeLock().unlock();
        }
    }

    public void cache(Map<String, Privilege> privileges, boolean force) {
        rwlock.writeLock().lock();
        try {
            for (String dn : privileges.keySet()) {
                if (force) {
                    cache.put(dn, privileges.get(dn));
                } else {
                    Privilege p = (Privilege)privileges.get(dn);
                    if (p == null) {
                        cache.put(dn, privileges.get(dn));
                    }
                }
            }
        } finally {
            rwlock.writeLock().unlock();
        }
    }

    public void decache(String dn, String realm) {
        rwlock.writeLock().lock();
        try {
            Object p = cache.remove(dn);
            if (p != null) {
                // Update cache only if entry removed from cache
                Integer i = countByRealm.get(realm);
                if (i != null) {
                    countByRealm.put(realm, i.intValue() - 1);
                }
            }
        } finally {
            rwlock.writeLock().unlock();
        }
    }

    public Privilege getPolicy(String dn) {
        rwlock.readLock().lock();
        try {
            return (Privilege)cache.get(dn);
        } finally {
            rwlock.readLock().unlock();
        }
    }
    
    /**
     * Returns the number of cached policies in the given realm
     * 
     * @param realm
     *            realm name
     * @return cached policies for the realm
     */
    int getCount(String realm) {
        Integer integer = countByRealm.get(realm);
        if (integer != null) {
            return (integer.intValue());
        }
        return 0;
    }

    public ReferralPrivilege getReferral(String dn) {
        rwlock.readLock().lock();
        try {
            return (ReferralPrivilege)cache.get(dn);
        } finally {
            rwlock.readLock().unlock();
        }
    }
}
