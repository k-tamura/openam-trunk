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
 * Copyright 2013-2015 ForgeRock AS.
 */
package org.forgerock.openam.utils;

import org.forgerock.util.Reject;
import org.forgerock.util.promise.Function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.sun.identity.common.CaseInsensitiveHashSet;

/**
 * A simple utility class to simplify interactions with collections.
 *
 * @author Peter Major
 */
public class CollectionUtils {

    private CollectionUtils() {
    }

    /**
     * Collects the passed in objects into a List.
     *
     * @param <T> The type of the passed in objects.
     * @param values An unbounded amount of objects that needs to be collected to a List.
     * @return Either an empty list (if there was no value passed in), or a List that holds all the values passed in.
     */
    public static <T> List<T> asList(T... values) {
        if (values == null) {
            return new ArrayList<T>(0);
        } else {
            return new ArrayList<T>(Arrays.asList(values));
        }
    }

    /**
     * Collects the passed in objects into a case insensitive hash set.  Unfortunately case insensitive hash sets
     * are not generic.
     *
     * @param values An unbounded amount of objects that needs to be converted to a case insensitive hash set.
     * @return Either an empty set (if there was no value passed in), or a Set that holds all the values passed in.
     */
    public static Set asCaseInsensitiveHashSet(Object... values) {
        if (values == null) {
            return new CaseInsensitiveHashSet(0);
        } else {
            return new CaseInsensitiveHashSet(Arrays.asList(values));
        }
    }

    /**
     * Collects the passed in objects into an unordered Set.
     *
     * @param <T> The type of the passed in objects.
     * @param values An unbounded amount of objects that needs to be converted to a Set.
     * @return Either an empty set (if there was no value passed in), or a Set that holds all the values passed in.
     */
    public static <T> Set<T> asSet(T... values) {
        if (values == null) {
            return new HashSet<T>(0);
        } else {
            return new HashSet<T>(Arrays.asList(values));
        }
    }

    /**
     * Collects the passed in objects to a LinkedHashSet.
     *
     * @param <T> The type of the passed in objects.
     * @param values An unbounded amount of objects that needs to be converted to a Set.
     * @return Either an empty set (if there was no value passed in), or a Set that holds all the values in the exact
     * same order as passed in.
     */
    public static <T> LinkedHashSet<T> asOrderedSet(T... values) {
        if (values == null) {
            return new LinkedHashSet<T>(0);
        } else {
            return new LinkedHashSet<T>(Arrays.asList(values));
        }
    }

    /**
     * Creates a new Map which contains the entries of the provided map but with keys and values exchanged.
     *
     * @param map
     *          the non-null original map.
     * @param <K>
     *         the type of keys in the provided map; the type of the values in the returned map.
     * @param <V>
     *         the type of values in the provided map; the type of the keys in the returned map.
     *
     * @return a new map with inverted key-value mapping.
     */
    public static <K, V> Map<V, K> invertMap(final Map<K, V> map) {

        Reject.ifNull(map);
        final Map<V, K> newMap = new HashMap<V, K>(map.size());

        for (Map.Entry<K, V> entry : map.entrySet()) {
            newMap.put(entry.getValue(), entry.getKey());
        }

        return newMap;
    }

    /**
     * Maps the values of a non-null map from one type to another type using a non-null mapper function.
     *
     * @param map
     *         the non-null original map
     * @param mapper
     *         the non-null mapping function
     * @param <K>
     *         the type of key
     * @param <I>
     *         the type of the initial value
     * @param <M>
     *         the type of the mapped value
     * @param <E>
     *         the type of exception that the function may throw
     *
     * @return a new map with the mapped values
     *
     * @throws E
     *         should an exception occur during the mapping process
     */
    public static <K, I, M, E extends Exception> Map<K, M> transformMap(final Map<K, I> map,
                                                                        final Function<I, M, E> mapper) throws E {
        Reject.ifNull(map, mapper);
        final Map<K, M> newMap = new HashMap<K, M>(map.size());

        for (Map.Entry<K, I> entry : map.entrySet()) {
            newMap.put(entry.getKey(), mapper.apply(entry.getValue()));
        }

        return newMap;
    }

    /**
     * Maps the values of a non-null list from one type to another type using a non-null mapper function.
     *
     * @param list
     *         the non-null original list
     * @param mapper
     *         the non-null mapping function
     * @param <I>
     *         the type of the initial value
     * @param <M>
     *         the type of the mapped value
     * @param <E>
     *         the type of exception that the function may throw
     *
     * @return a new list with the mapped values
     *
     * @throws E
     *         should an exception occur during the mapping process
     */
    public static <I, M, E extends Exception> List<M> transformList(final List<I> list,
                                                                    final Function<I, M, E> mapper) throws E {
        Reject.ifNull(list, mapper);
        final List<M> newList = new ArrayList<M>(list.size());

        for (I value : list) {
            newList.add(mapper.apply(value));
        }

        return newList;
    }

    /**
     * Maps the values of a non-null set from one type to another type using a non-null mapper function.
     *
     * @param set
     *         the non-null original set
     * @param mapper
     *         the non-null mapping function
     * @param <I>
     *         the type of the initial value
     * @param <M>
     *         the type of the mapped value
     * @param <E>
     *         the type of exception that the function may throw
     *
     * @return a new list with the mapped values
     *
     * @throws E
     *         should an exception occur during the mapping process
     */
    public static <I, M, E extends Exception> Set<M> transformSet(final Set<I> set,
                                                                  final Function<I, M, E> mapper) throws E {
        Reject.ifNull(set, mapper);
        final Set<M> newSet = new HashSet<M>(set.size());

        for (I value : set) {
            newSet.add(mapper.apply(value));
        }

        return newSet;
    }


    /**
     * Retrieves the first item from a collections.
     *
     * @param collection
     *         the collection
     * @param defaultValue
     *         the default value should not initial value exist
     * @param <T>
     *         the type of the collection
     *
     * @return the first instance of the collection else null if not present
     */
    public static <T> T getFirstItem(final Collection<T> collection, final T defaultValue) {
        if (collection == null) {
            return defaultValue;
        }

        final Iterator<T> iterator = collection.iterator();
        return iterator.hasNext() ? iterator.next() : defaultValue;
    }

    /**
     * Compares any two arbitrary objects - including collections, for equality. It also handles either of the objects
     * being null without issue.
     *
     * @param valA - the first object to be compared.
     * @param valB - the second object to tbe compared.
     * @return true if the parameter values are the same, false if different.
     */
    public static <T> boolean genericCompare(T valA, T valB) {
        return valA == null ? valB == null : valA.equals(valB);
    }

    /**
     * Compares two map collections containing sets of strings in a case insensitive manner.
     *
     * @param valA - the first map of set of strings to be compared.
     * @param valB - the second map of set of strings to be compared.
     * @return true if the parameter values are the same, false if different.
     */
    public static boolean compareCaseInsensitiveMapOfSetOfStrings(Map<String, Set<String>> valA, Map<String, Set<String>> valB) {
        if (valA == valB) {
            return true;
        }
        if ((valA == null) || (valB == null)) {
            return false;
        }
        if (valA.size() != valB.size()) {
            return false;
        }
        if (valA.size() == 0) {
            return true;
        }

        for (Map.Entry<String, Set<String>> valAEntry : valA.entrySet()) {
            Set<String> valBValues = valB.get(valAEntry.getKey());
            Set<String> valAValues = valAEntry.getValue();
            if (valBValues == null) {
                if (valAValues != null) {
                    return false;
                }
                continue;
            }

            if (valAValues.size() != valBValues.size()) {
                return false;
            }
            if (valAValues.size() == 0) {
                continue;
            }

            for (String valAValueString : valAValues)
            {
                if (valAValueString == null) {
                    boolean bFoundNull = false;
                    for (String nullTestInValBString : valBValues) {
                        // This works as sets can only have a maximum of 1 null entry
                        bFoundNull = nullTestInValBString == null;
                        if (bFoundNull) {
                            break;
                        }
                    }

                    if (!bFoundNull) {
                        return false;
                    }
                    continue;
                }

                boolean bFound=false;
                for (String valBValueString : valBValues) {
                    bFound = StringUtils.compareCaseInsensitiveString(valAValueString, valBValueString);
                    if (bFound) {
                        break;
                    }
                }

                if (!bFound) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Creates a hash code for a map of set of strings in a case insensitive manner. This is needed so that
     * if a case insensitive equality is applied to the map, the hash is generated in a similar and consisent
     * manner.
     *
     * @param map - the map of set of strings for which the hash code is to be generated.
     * @return the generated hash code.
     */
    public static int createHashForCaseInsensitiveMapOfSetOfStrings(Map<String, Set<String>> map) {
        int hc = 0;
        for (Map.Entry<String, Set<String>> currentEntry : map.entrySet()) {
            if (currentEntry == null) {
                continue;
            }
            Set<String> currentValue = currentEntry.getValue();
            if (currentValue == null) {
                continue;
            }
            for (String currentString : currentValue) {
                if (currentString == null) {
                    continue;
                }
                // case insensitive mode so force lower case on strings
                hc = hc * 31 + currentString.toLowerCase().hashCode();
            }
        }

        return hc;
    }

    /**
     * Given a collection determines whether it contains some values.
     *
     * @param collection
     *         collection in question
     *
     * @return whether the collection contains any values
     */
    public static boolean isNotEmpty(final Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }

    /**
     * Given a map determines whether it contains some values.
     *
     * @param map map in question
     *
     * @return whether the map contains any values
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return map != null && !map.isEmpty();
    }

    /**
     * See if any of the containers have entries and if so return true.  Any argument which is not a {@code Collection}
     * or a {@code Map} is ignored.
     * @param containers a varied list of maps and collections
     * @return true if any one of the maps and/or collections have entries
     */
    public static boolean anyHaveEntries(Object... containers) {
        for (Object o : containers) {
            if (o instanceof Collection<?> && isNotEmpty((Collection<?>) o)) {
                return true;
            } else if (o instanceof Map<?, ?> && isNotEmpty((Map<?, ?>) o)) {
                return true;
            }
        }
        return false;
    }

}
