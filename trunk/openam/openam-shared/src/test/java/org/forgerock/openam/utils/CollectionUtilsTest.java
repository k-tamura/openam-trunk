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

package org.forgerock.openam.utils;

import org.forgerock.util.promise.Function;
import org.forgerock.util.promise.NeverThrowsException;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Unit test for {@link CollectionUtils}.
 */
public class CollectionUtilsTest {

    @Test
    public void mapListToSetWithinMap() {
        // Given...
        Map<String, List<String>> mapOfList = new HashMap<String, List<String>>();
        List<String> strings = Arrays.asList("abc", "def", "ghi", "jkl", "ghi");
        mapOfList.put("myListOfStrings", strings);

        // When...
        Map<String, Set<String>> mapOfSet = CollectionUtils.transformMap(mapOfList, new MapListToSet());

        // Then...
        assertThat(mapOfSet.size()).isEqualTo(1);
        Set<String> stringSet = mapOfSet.get("myListOfStrings");
        assertThat(stringSet).isNotNull().containsOnly("abc", "def", "ghi", "jkl");
    }

    @Test
    public void mapStringToIntWithinMap() {
        // Given...
        Map<String, String> mapOfString = new HashMap<String, String>();
        mapOfString.put("value1", "12");
        mapOfString.put("value2", "34");
        mapOfString.put("value3", "56");
        mapOfString.put("value4", "78");
        mapOfString.put("value5", "90");

        // When...
        Map<String, Integer> mapOfInt = CollectionUtils.transformMap(mapOfString, new MapStringToInt());

        // Then...
        assertThat(mapOfInt.size()).isEqualTo(5);
        assertThat(mapOfInt.get("value1")).isNotNull().isEqualTo(12);
        assertThat(mapOfInt.get("value2")).isNotNull().isEqualTo(34);
        assertThat(mapOfInt.get("value3")).isNotNull().isEqualTo(56);
        assertThat(mapOfInt.get("value4")).isNotNull().isEqualTo(78);
        assertThat(mapOfInt.get("value5")).isNotNull().isEqualTo(90);
    }

    @Test(expectedExceptions = SomeCheckedException.class)
    public void mappingFailsWithinMap() throws SomeCheckedException {
        // Given...
        Map<String, String> someMap = new HashMap<String, String>();
        someMap.put("test", "data");

        // When...
        CollectionUtils.transformMap(someMap, new FailMapping());
    }

    @Test
    public void mapStringToIntWithinList() {
        // Given...
        List<String> listOfStrings = new ArrayList<String>();
        listOfStrings.add("12");
        listOfStrings.add("34");
        listOfStrings.add("56");
        listOfStrings.add("78");
        listOfStrings.add("90");

        // When...
        List<Integer> listOfInt = CollectionUtils.transformList(listOfStrings, new MapStringToInt());

        // Then...
        assertThat(listOfInt.size()).isEqualTo(5);
        assertThat(listOfInt.contains(12)).isTrue();
        assertThat(listOfInt.contains(34)).isTrue();
        assertThat(listOfInt.contains(56)).isTrue();
        assertThat(listOfInt.contains(78)).isTrue();
        assertThat(listOfInt.contains(90)).isTrue();
    }

    @Test(expectedExceptions = SomeCheckedException.class)
    public void mappingFailsWithinList() throws SomeCheckedException {
        // Given...
        List<String> someList = new ArrayList<String>();
        someList.add("abc");

        // When...
        CollectionUtils.transformList(someList, new FailMapping());
    }

    /**
     * Mapper that maps a list of strings to a set of strings.
     */
    private static final class MapListToSet implements Function<List<String>, Set<String>, NeverThrowsException> {

        public Set<String> apply(List<String> value) throws NeverThrowsException {
            return new HashSet<String>(value);
        }

    }

    /**
     * Mapper that maps a string to an integer.
     */
    private static final class MapStringToInt implements Function<String, Integer, NumberFormatException> {

        public Integer apply(String value) throws NumberFormatException {
            return Integer.valueOf(value);
        }

    }

    /**
     * Mapper that deliberately throws an exception.
     */
    private static final class FailMapping implements Function<String, String, SomeCheckedException> {

        public String apply(String value) throws SomeCheckedException {
            throw new SomeCheckedException();
        }

    }

    /**
     * Some arbitrary exception.
     */
    private static final class SomeCheckedException extends Exception {
    }

}
