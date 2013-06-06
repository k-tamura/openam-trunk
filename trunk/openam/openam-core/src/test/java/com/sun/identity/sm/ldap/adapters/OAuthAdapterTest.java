/**
 * Copyright 2013 ForgeRock, Inc.
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
 */
package com.sun.identity.sm.ldap.adapters;

import com.sun.identity.shared.debug.Debug;
import com.sun.identity.sm.ldap.api.TokenType;
import com.sun.identity.sm.ldap.api.fields.OAuthTokenField;
import com.sun.identity.sm.ldap.api.tokens.Token;
import com.sun.identity.sm.ldap.api.tokens.TokenIdFactory;
import com.sun.identity.sm.ldap.utils.JSONSerialisation;
import com.sun.identity.sm.ldap.utils.KeyConversion;
import edu.emory.mathcs.backport.java.util.Arrays;
import org.forgerock.json.fluent.JsonValue;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.mock;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

/**
 * @author robert.wapshott@forgerock.com
 */
public class OAuthAdapterTest {
    @Test
    public void shouldSerialiseSimpleString() {
        // Given
        OAuthAdapter adapter = generateOAuthAdapter();

        String text = "badger";
        OAuthTokenField field = OAuthTokenField.PARENT;

        Map<String, Object> values = new HashMap<String, Object>();
        values.put(field.getOAuthField(), text);
        JsonValue jsonValue = makeDefaultJsonValue(values);

        // When
        Token result = adapter.toToken(jsonValue);

        // Then
        assertEquals(text, result.getValue(field.getField()));
    }

    @Test
    public void shouldSerialiseCollectionOfStrings() {
        // Given
        OAuthAdapter adapter = generateOAuthAdapter();

        String one = "badger";
        String two = "weasel";
        String three = "ferret";

        OAuthTokenField field = OAuthTokenField.SCOPE;

        Map<String, Object> values = new HashMap<String, Object>();
        values.put(field.getOAuthField(), Arrays.asList(new String[]{one, two, three}));
        JsonValue jsonValue = makeDefaultJsonValue(values);

        // When
        String result = adapter.toToken(jsonValue).getValue(field.getField());

        // Then
        assertTrue(result.contains(one));
        assertTrue(result.contains(two));
        assertTrue(result.contains(three));
    }

    @Test (expectedExceptions = IllegalStateException.class)
    public void shouldNotAllowASingleDate() {
        // Given
        OAuthAdapter adapter = generateOAuthAdapter();

        String text = "1234567890";
        OAuthTokenField field = OAuthTokenField.EXPIRY_TIME;

        Map<String, Object> values = new HashMap<String, Object>();
        values.put(field.getOAuthField(), text);
        JsonValue jsonValue = makeDefaultJsonValue(values);

        // When
        adapter.toToken(jsonValue);
    }

    @Test
    public void shouldSerialiseACollectionOfTimestamps() {
        // Given
        OAuthAdapter adapter = generateOAuthAdapter();

        String text = "1370425721197";
        OAuthTokenField field = OAuthTokenField.EXPIRY_TIME;

        Map<String, Object> values = new HashMap<String, Object>();
        values.put(field.getOAuthField(), Arrays.asList(new String[]{text}));
        JsonValue jsonValue = makeDefaultJsonValue(values);

        // When
        Calendar result = adapter.toToken(jsonValue).getExpiryTimestamp();

        // Then
        // Wed, 05 Jun 2013 10:48:41 BST
        assertEquals(result.get(Calendar.MONTH), 5);
        assertEquals(result.get(Calendar.YEAR), 2013);
        assertEquals(result.get(Calendar.HOUR_OF_DAY), 10);
        assertEquals(result.get(Calendar.MINUTE), 48);
    }

    @Test
    public void shouldDeserialiseSerialisedToken() {
        // Given
        String id = "badger";
        OAuthTokenField field = OAuthTokenField.ID;

        JSONSerialisation serialisation = new JSONSerialisation(mock(Debug.class));
        OAuthAdapter adapter = generateOAuthAdapter();

        // Populate a map for serialisation.
        Map<String, String> values = new HashMap<String, String>();
        values.put(field.getOAuthField(), id);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(OAuthAdapter.VALUE, values);
        String serialisedObject = serialisation.serialise(map);

        Token token = new Token(id, TokenType.OAUTH);
        // Set the serialised binary data
        token.setBlob(serialisedObject.getBytes());

        // When
        JsonValue result = adapter.fromToken(token);

        // Then
        assertNotNull(result);
        JsonValue value = result.get(OAuthAdapter.VALUE);
        assertEquals(value.asMap().get(field.getOAuthField()), id);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void shouldVerifyThatObjectIsAMap() {
        // Given
        OAuthAdapter adapter = generateOAuthAdapter();

        JsonValue mockValue = mock(JsonValue.class);
        given(mockValue.getObject()).willReturn("badger");

        // When/Then
        adapter.toToken(mockValue);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void shouldNotDeserialiseATokenWhichDoesntContainAMap() {
        // Given
        JSONSerialisation serialisation = new JSONSerialisation(mock(Debug.class));
        OAuthAdapter adapter = generateOAuthAdapter();

        Token token = new Token("", TokenType.OAUTH);
        token.setBlob(serialisation.serialise("badger").getBytes());

        // When/Then
        adapter.fromToken(token);
    }


    /**
     * @return Makes a standard OAuthAdapter with real dependencies.
     */
    private OAuthAdapter generateOAuthAdapter() {
        JSONSerialisation serialisation = new JSONSerialisation(mock(Debug.class));
        KeyConversion keyConversion = new KeyConversion();
        OAuthValues oAuthValues = new OAuthValues();
        return new OAuthAdapter(new TokenIdFactory(keyConversion), serialisation, oAuthValues);
    }

    /**
     * @param values Generate a JsonValue based on the values in this map.
     * @return A non null JsonValue.
     */
    private JsonValue makeDefaultJsonValue(Map<String, Object> values) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(OAuthAdapter.VALUE, values);
        return new JsonValue(map);
    }
}
