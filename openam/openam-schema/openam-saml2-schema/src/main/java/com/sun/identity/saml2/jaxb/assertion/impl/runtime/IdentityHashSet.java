//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:34:07 AM PDT 
//

package com.sun.identity.saml2.jaxb.assertion.impl.runtime;

/**
 * A set of {@link Object}s that uses the == (instead of equals)
 * for the comparison.
 * 
 * @author
 *     Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
 */
final class IdentityHashSet {
    /** The hash table data. */
    private Object table[];

    /** The total number of mappings in the hash table. */
    private int count;

    /**
     * The table is rehashed when its size exceeds this threshold.  (The
     * value of this field is (int)(capacity * loadFactor).)
     */
    private int threshold;

    /** The load factor for the hashtable. */
    private static final float loadFactor = 0.3f;
    private static final int initialCapacity = 191;


    public IdentityHashSet() {
        table = new Object[initialCapacity];
        threshold = (int) (initialCapacity * loadFactor);
    }

    public boolean contains(Object key) {
        Object tab[] = table;
        int index = (System.identityHashCode(key) & 0x7FFFFFFF) % tab.length;

        while (true) {
            final Object e = tab[index];
            if (e == null)
                return false;
            if (e==key)
                return true;
            index = (index + 1) % tab.length;
        }
    }

    /**
     * rehash.
     * 
     * It is possible for one thread to call get method
     * while another thread is performing rehash.
     * Keep this in mind.
     */
    private void rehash() {
        // create a new table first.
        // meanwhile, other threads can safely access get method.
        int oldCapacity = table.length;
        Object oldMap[] = table;

        int newCapacity = oldCapacity * 2 + 1;
        Object newMap[] = new Object[newCapacity];

        for (int i = oldCapacity; i-- > 0;)
            if (oldMap[i] != null) {
                int index = (System.identityHashCode(oldMap[i]) & 0x7FFFFFFF) % newMap.length;
                while (newMap[index] != null)
                    index = (index + 1) % newMap.length;
                newMap[index] = oldMap[i];
            }

        // threshold is not accessed by get method.
        threshold = (int) (newCapacity * loadFactor);
        // switch!
        table = newMap;
    }

    public boolean add(Object newObj) {
        if (count >= threshold)
            rehash();

        Object tab[] = table;
        int index = (System.identityHashCode(newObj) & 0x7FFFFFFF) % tab.length;

        Object existing;
        
        while ((existing=tab[index]) != null) {
            if(existing==newObj)    return false;
            index = (index + 1) % tab.length;
        }
        tab[index] = newObj;

        count++;
        
        return true;
    }
}
