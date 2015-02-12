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
 * Copyright 2015 ForgeRock AS.
 */

package org.forgerock.openam.oauth2.resources;

import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.forgerock.openam.utils.CollectionUtils.asSet;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.forgerock.oauth2.core.OAuth2ProviderSettings;
import org.forgerock.oauth2.core.OAuth2ProviderSettingsFactory;
import org.forgerock.oauth2.core.OAuth2Request;
import org.forgerock.oauth2.core.exceptions.BadRequestException;
import org.forgerock.oauth2.core.exceptions.NotFoundException;
import org.forgerock.oauth2.core.exceptions.ServerException;
import org.forgerock.oauth2.resources.ResourceSetDescription;
import org.forgerock.oauth2.resources.ResourceSetStore;
import org.forgerock.openam.cts.CTSPersistentStore;
import org.forgerock.openam.cts.adapters.JavaBeanAdapter;
import org.forgerock.openam.cts.api.fields.ResourceSetTokenField;
import org.forgerock.openam.cts.api.filter.TokenFilter;
import org.forgerock.openam.cts.api.tokens.Token;
import org.forgerock.openam.cts.api.tokens.TokenIdGenerator;
import org.forgerock.openam.sm.datalayer.store.TokenDataStore;
import org.forgerock.openam.tokens.CoreTokenField;
import org.forgerock.openam.tokens.TokenType;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class OpenAMResourceSetStoreTest {

    private OpenAMResourceSetStore store;

    private TokenDataStore<ResourceSetDescription> dataStore;
    private TokenIdGenerator idGenerator;

    @BeforeMethod
    @SuppressWarnings("unchecked")
    public void setup() throws NotFoundException {

        dataStore = mock(TokenDataStore.class);
        OAuth2ProviderSettingsFactory providerSettingsFactory = mock(OAuth2ProviderSettingsFactory.class);
        OAuth2ProviderSettings providerSettings = mock(OAuth2ProviderSettings.class);
        this.idGenerator = mock(TokenIdGenerator.class);

        store = new OpenAMResourceSetStore("REALM", providerSettingsFactory, idGenerator, dataStore);

        given(providerSettingsFactory.get(Matchers.<OAuth2Request>anyObject())).willReturn(providerSettings);
        given(providerSettings.getResourceSetRegistrationPolicyEndpoint(anyString())).willReturn("POLICY_URI");
    }

    @Test(expectedExceptions = BadRequestException.class)
    public void shouldNotCreateDuplicateResourceSetWithSameId() throws Exception {

        //Given
        OAuth2Request request = mock(OAuth2Request.class);
        ResourceSetDescription resourceSetDescription =
                new ResourceSetDescription(null, "RESOURCE_SET_ID", "CLIENT_ID", "RESOURCE_OWNER_ID",
                        Collections.<String, Object>singletonMap("name", "RESOURCE_SET_NAME"));

        resourceSetDescription.setRealm("REALM");
        given(dataStore.query(Matchers.<Map<String, Object>>anyObject(), eq(TokenDataStore.FilterType.AND)))
                .willReturn(Collections.singleton(resourceSetDescription));

        //When
        try {
            store.create(request, resourceSetDescription);
        } catch (BadRequestException e) {
            //Then
            assertThat(resourceSetDescription.getPolicyUri()).isNull();
            verify(dataStore, never()).create(any(ResourceSetDescription.class));
            throw e;
        }
    }

    @Test
    public void shouldCreateResourceSetToken() throws Exception {

        //Given
        OAuth2Request request = mock(OAuth2Request.class);
        ResourceSetDescription resourceSetDescription =
                new ResourceSetDescription(null, "RESOURCE_SET_ID", "CLIENT_ID", "RESOURCE_OWNER_ID",
                        Collections.<String, Object>singletonMap("name", "RESOURCE_SET_NAME"));

        given(dataStore.query(Matchers.<Map<String, Object>>anyObject(), eq(TokenDataStore.FilterType.AND)))
                .willReturn(Collections.<ResourceSetDescription>emptySet());

        //When
        store.create(request, resourceSetDescription);

        //Then
        assertThat(resourceSetDescription.getPolicyUri()).isEqualTo("POLICY_URI");
        verify(dataStore).create(resourceSetDescription);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void readShouldThrowNotFoundExceptionWhenNoResourceSetFound() throws Exception {
        //Given
        given(dataStore.query(Matchers.<Map<String, Object>>anyObject(), eq(TokenDataStore.FilterType.AND)))
                .willReturn(Collections.<ResourceSetDescription>emptySet());

        //When
        store.read("RESOURCE_SET_ID", "CLIENT_ID");

        //Then
        //Excepted NotFoundException
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void readShouldThrowNotFoundExceptionWhenMultipleResourceSetsFound() throws Exception {

        //Given
        ResourceSetDescription resourceSet1 = mock(ResourceSetDescription.class);
        ResourceSetDescription resourceSet2 = mock(ResourceSetDescription.class);
        Set<ResourceSetDescription> resourceSets = new HashSet<ResourceSetDescription>();
        resourceSets.add(resourceSet1);
        resourceSets.add(resourceSet2);

        given(dataStore.query(Matchers.<Map<String, Object>>anyObject(), eq(TokenDataStore.FilterType.AND)))
                .willReturn(resourceSets);

        //When
        store.read("RESOURCE_SET_ID", "CLIENT_ID");

        //Then
        //Excepted NotFoundException
    }

    @Test
    public void shouldReadResourceSetToken() throws Exception {

        //Given
        ResourceSetDescription resourceSetDescription =
                new ResourceSetDescription("123", "RESOURCE_SET_ID", "CLIENT_ID", "RESOURCE_OWNER_ID",
                        Collections.<String, Object>emptyMap());

        given(dataStore.query(Matchers.<Map<String, Object>>anyObject(), eq(TokenDataStore.FilterType.AND)))
                .willReturn(asSet(resourceSetDescription));
        resourceSetDescription.setRealm("REALM");

        //When
        ResourceSetDescription readResourceSetDescription = store.read("RESOURCE_SET_ID", "CLIENT_ID");

        //Then
        ArgumentCaptor<Map> queryCaptor = ArgumentCaptor.forClass(Map.class);
        verify(dataStore).query(queryCaptor.capture(), eq(TokenDataStore.FilterType.AND));

        assertThat(queryCaptor.getValue()).containsOnly(
                entry(ResourceSetTokenField.RESOURCE_SET_ID, "RESOURCE_SET_ID"),
                entry(ResourceSetTokenField.CLIENT_ID, "CLIENT_ID"));
        assertThat(readResourceSetDescription).isEqualTo(readResourceSetDescription);
    }

    @Test
    public void shouldReadResourceSetTokenWithResourceSetUid() throws Exception {

        //Given
        ResourceSetDescription resourceSetDescription =
                new ResourceSetDescription("123", "RESOURCE_SET_ID", "CLIENT_ID", "RESOURCE_OWNER_ID",
                        Collections.<String, Object>emptyMap());

        given(dataStore.read("123")).willReturn(resourceSetDescription);
        resourceSetDescription.setRealm("REALM");

        //When
        ResourceSetDescription readResourceSetDescription = store.read("123");

        //Then
        verify(dataStore).read("123");
        assertThat(readResourceSetDescription).isEqualTo(readResourceSetDescription);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void readWithResourceSetUidShouldThrowNotFoundExceptionWhenMultipleResourceSetsFound() throws Exception {

        //Given
        given(dataStore.read("123")).willThrow(new org.forgerock.openam.sm.datalayer.store.NotFoundException("not found"));

        //When
        store.read("123");

        //Then
        //Excepted NotFoundException
    }

    @Test
    public void shouldUpdateResourceSetToken() throws Exception {

        //Given
        ResourceSetDescription resourceSetDescription =
                new ResourceSetDescription("123", "RESOURCE_SET_ID", "CLIENT_ID", "RESOURCE_OWNER_ID",
                        Collections.<String, Object>emptyMap());

        resourceSetDescription.setRealm("REALM");

        //When
        store.update(resourceSetDescription);

        //Then
        verify(dataStore).update(resourceSetDescription);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void shouldNotDeleteResourceSetTokenIfResourceSetNotFound() throws Exception {

        //Given
        given(dataStore.query(Matchers.<Map<String, Object>>anyObject(), eq(TokenDataStore.FilterType.AND)))
                .willReturn(Collections.<ResourceSetDescription>emptySet());

        //When
        store.delete("RESOURCE_SET_ID", "CLIENT_ID");

        //Then
        //Excepted NotFoundException
    }

    @Test
    public void shouldDeleteResourceSetToken() throws Exception {

        //Given
        Token token = mock(Token.class);
        ResourceSetDescription resourceSetDescription = new ResourceSetDescription();

        resourceSetDescription.setId("ID");
        resourceSetDescription.setRealm("REALM");
        given(dataStore.query(Matchers.<Map<String, Object>>anyObject(), eq(TokenDataStore.FilterType.AND)))
                .willReturn(Collections.singleton(resourceSetDescription));

        //When
        store.delete("RESOURCE_SET_ID", "CLIENT_ID");

        //Then
        verify(dataStore).delete("ID");
    }

    @Test
    public void shouldQueryResourceSetToken() throws Exception {

        //Given
        Map<String, Object> queryParameters = new HashMap<String, Object>();
        queryParameters.put(ResourceSetTokenField.CLIENT_ID, "CLIENT_ID");
        ResourceSetStore.FilterType filterType = ResourceSetStore.FilterType.AND;
        ResourceSetDescription resourceSet1 =
                new ResourceSetDescription("123", "RESOURCE_SET_ID_1", "CLIENT_ID", "RESOURCE_OWNER_ID",
                        Collections.<String, Object>emptyMap());
        ResourceSetDescription resourceSet2 =
                new ResourceSetDescription("456", "RESOURCE_SET_ID_2", "CLIENT_ID", "RESOURCE_OWNER_ID",
                        Collections.<String, Object>emptyMap());

        given(dataStore.query(Matchers.<Map<String, Object>>anyObject(), any(TokenDataStore.FilterType.class)))
                .willReturn(asSet(resourceSet1, resourceSet2));
        resourceSet1.setRealm("REALM");
        resourceSet2.setRealm("REALM");

        //When
        Set<ResourceSetDescription> resourceSetDescriptions = store.query(queryParameters, filterType);

        //Then
        assertThat(resourceSetDescriptions).contains(resourceSet1, resourceSet2);
        ArgumentCaptor<Map> tokenFilterCaptor = ArgumentCaptor.forClass(Map.class);
        verify(dataStore).query(tokenFilterCaptor.capture(), eq(TokenDataStore.FilterType.AND));
        assertThat(tokenFilterCaptor.getValue()).containsOnly(
                entry(ResourceSetTokenField.CLIENT_ID, "CLIENT_ID"));
    }
}
