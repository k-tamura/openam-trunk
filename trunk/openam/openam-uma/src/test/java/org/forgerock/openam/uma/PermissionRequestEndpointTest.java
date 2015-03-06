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

package org.forgerock.openam.uma;

import static org.assertj.core.api.Assertions.assertThat;
import static org.forgerock.json.fluent.JsonValue.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.eq;

import java.util.Collections;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.forgerock.json.fluent.JsonValue;
import org.forgerock.oauth2.core.AccessToken;
import org.forgerock.oauth2.core.OAuth2ProviderSettings;
import org.forgerock.oauth2.core.OAuth2ProviderSettingsFactory;
import org.forgerock.oauth2.core.OAuth2Request;
import org.forgerock.oauth2.core.OAuth2RequestFactory;
import org.forgerock.oauth2.core.exceptions.InvalidGrantException;
import org.forgerock.oauth2.core.exceptions.NotFoundException;
import org.forgerock.oauth2.core.exceptions.ServerException;
import org.forgerock.oauth2.resources.ResourceSetDescription;
import org.forgerock.oauth2.resources.ResourceSetStore;
import org.forgerock.openam.cts.api.fields.ResourceSetTokenField;
import org.forgerock.util.query.QueryFilter;
import org.json.JSONObject;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PermissionRequestEndpointTest {

    private PermissionRequestEndpoint endpoint;

    private ResourceSetStore resourceSetStore;
    private Response response;
    private UmaTokenStore umaTokenStore;

    @BeforeMethod
    @SuppressWarnings("unchecked")
    public void setup() throws ServerException, InvalidGrantException, NotFoundException {
        resourceSetStore = mock(ResourceSetStore.class);
        OAuth2RequestFactory<Request> requestFactory = mock(OAuth2RequestFactory.class);
        umaTokenStore = mock(UmaTokenStore.class);

        OAuth2ProviderSettingsFactory providerSettingFactory = mock(OAuth2ProviderSettingsFactory.class);
        OAuth2ProviderSettings providerSettings = mock(OAuth2ProviderSettings.class);

        given(providerSettingFactory.get(Matchers.<OAuth2Request>anyObject())).willReturn(providerSettings);
        given(providerSettings.getResourceSetStore()).willReturn(resourceSetStore);

        UmaProviderSettingsFactory umaProviderSettingsFactory = mock(UmaProviderSettingsFactory.class);
        UmaProviderSettings umaProviderSettings = mock(UmaProviderSettings.class);
        given(umaProviderSettingsFactory.get(any(Request.class))).willReturn(umaProviderSettings);
        given(umaProviderSettings.getUmaTokenStore()).willReturn(umaTokenStore);

        endpoint = spy(new PermissionRequestEndpoint(providerSettingFactory, requestFactory,
                umaProviderSettingsFactory));

        response = mock(Response.class);
        endpoint.setResponse(response);

        Request request = mock(Request.class);
        given(endpoint.getRequest()).willReturn(request);

        AccessToken accessToken = mock(AccessToken.class);
        given(accessToken.getClientId()).willReturn("CLIENT_ID");
        given(accessToken.getResourceOwnerId()).willReturn("RESOURCE_OWNER_ID");

        OAuth2Request oAuth2Request = mock(OAuth2Request.class);
        given(requestFactory.create(request)).willReturn(oAuth2Request);
        given(oAuth2Request.getToken(AccessToken.class)).willReturn(accessToken);
    }

    private void setupResourceSetStore() throws NotFoundException, ServerException {
        JsonValue description = json(object(field("scopes", array("SCOPE_A", "SCOPE_B"))));
        ResourceSetDescription resourceSetDescription = new ResourceSetDescription("RESOURCE_SET_ID",
                "CLIENT_ID", "RESOURCE_OWNER_ID", description.asMap());

        given(resourceSetStore.read("RESOURCE_SET_ID", "RESOURCE_OWNER_ID")).willReturn(resourceSetDescription);
    }

    @Test(expectedExceptions = UmaException.class)
    public void shouldThrowInvalidResourceSetIdExceptionWhenEntityIsNull() throws Exception {

        //Given

        //When
        try {
            endpoint.registerPermissionRequest(null);
        } catch (UmaException e) {
            //Then
            assertThat(e.getStatusCode()).isEqualTo(400);
            assertThat(e.getError()).isEqualTo("invalid_resource_set_id");
            assertThat(e.getMessage()).contains("Missing required attribute", "'resource_set_id'");
            throw e;
        }
    }

    @Test(expectedExceptions = UmaException.class)
    public void shouldThrowInvalidResourceSetIdExceptionWhenNoResourceSetId() throws Exception {

        //Given
        JsonRepresentation entity = mock(JsonRepresentation.class);
        JSONObject requestBody = mock(JSONObject.class);

        given(entity.getJsonObject()).willReturn(requestBody);
        given(requestBody.toString()).willReturn("");

        //When
        try {
            endpoint.registerPermissionRequest(entity);
        } catch (UmaException e) {
            //Then
            assertThat(e.getStatusCode()).isEqualTo(400);
            assertThat(e.getError()).isEqualTo("invalid_resource_set_id");
            assertThat(e.getMessage()).contains("Missing required attribute", "'resource_set_id'");
            throw e;
        }
    }

    @Test(expectedExceptions = UmaException.class)
    public void shouldThrowInvalidResourceSetIdExceptionWhenResourceSetIdIsNotAString() throws Exception {

        //Given
        JsonRepresentation entity = mock(JsonRepresentation.class);
        JSONObject requestBody = mock(JSONObject.class);

        given(entity.getJsonObject()).willReturn(requestBody);
        given(requestBody.toString()).willReturn("{\"resource_set_id\":[]}");

        //When
        try {
            endpoint.registerPermissionRequest(entity);
        } catch (UmaException e) {
            //Then
            assertThat(e.getStatusCode()).isEqualTo(400);
            assertThat(e.getError()).isEqualTo("invalid_resource_set_id");
            assertThat(e.getMessage()).contains("Required attribute", "'resource_set_id'", "must be a String");
            throw e;
        }
    }

    @Test(expectedExceptions = UmaException.class)
    public void shouldThrowInvalidScopeExceptionWhenNoScope() throws Exception {

        //Given
        JsonRepresentation entity = mock(JsonRepresentation.class);
        JSONObject requestBody = mock(JSONObject.class);
        ResourceSetDescription resourceSetDescription = new ResourceSetDescription("RESOURCE_SET_ID",
                "CLIENT_ID", "RESOURCE_OWNER_ID", Collections.<String, Object>emptyMap());

        given(entity.getJsonObject()).willReturn(requestBody);
        given(requestBody.toString()).willReturn("{\"resource_set_id\":\"RESOURCE_SET_ID\"}");
        given(resourceSetStore.read("RESOURCE_SET_ID", "RESOURCE_OWNER_ID")).willReturn(resourceSetDescription);

        //When
        try {
            endpoint.registerPermissionRequest(entity);
        } catch (UmaException e) {
            //Then
            assertThat(e.getStatusCode()).isEqualTo(400);
            assertThat(e.getError()).isEqualTo("invalid_scope");
            assertThat(e.getMessage()).contains("Missing required attribute", "'scopes'");
            throw e;
        }
    }

    @Test(expectedExceptions = UmaException.class)
    public void shouldThrowInvalidScopeExceptionWhenScopeIsNotASetOfStrings() throws Exception {

        //Given
        JsonRepresentation entity = mock(JsonRepresentation.class);
        JSONObject requestBody = mock(JSONObject.class);
        ResourceSetDescription resourceSetDescription = new ResourceSetDescription("RESOURCE_SET_ID",
                "CLIENT_ID", "RESOURCE_OWNER_ID", Collections.<String, Object>emptyMap());

        given(entity.getJsonObject()).willReturn(requestBody);
        given(requestBody.toString()).willReturn("{\"resource_set_id\":\"RESOURCE_SET_ID\", \"scopes\":\"SCOPE\"}");
        given(resourceSetStore.read("RESOURCE_SET_ID", "RESOURCE_OWNER_ID")).willReturn(resourceSetDescription);

        //When
        try {
            endpoint.registerPermissionRequest(entity);
        } catch (UmaException e) {
            //Then
            assertThat(e.getStatusCode()).isEqualTo(400);
            assertThat(e.getError()).isEqualTo("invalid_scope");
            assertThat(e.getMessage()).contains("Required attribute", "'scopes'", "must be an array of Strings");
            throw e;
        }
    }

    @Test(expectedExceptions = UmaException.class)
    public void shouldThrowInvalidResourceSetIdExceptionWhenResourceSetNotFound() throws Exception {

        //Given
        JsonRepresentation entity = mock(JsonRepresentation.class);
        JSONObject requestBody = mock(JSONObject.class);

        given(entity.getJsonObject()).willReturn(requestBody);
        given(requestBody.toString()).willReturn("{\"resource_set_id\":\"RESOURCE_SET_ID\", "
                + "\"scopes\":[\"SCOPE_A\", \"SCOPE_C\"]}");

        doThrow(NotFoundException.class).when(resourceSetStore).read("RESOURCE_SET_ID", "RESOURCE_OWNER_ID");

        //When
        try {
            endpoint.registerPermissionRequest(entity);
        } catch (UmaException e) {
            //Then
            assertThat(e.getStatusCode()).isEqualTo(400);
            assertThat(e.getError()).isEqualTo("invalid_resource_set_id");
            throw e;
        }
    }

    @Test(expectedExceptions = UmaException.class)
    public void shouldThrowInvalidResourceSetIdExceptionWhenResourceSetStoreThrowsServerException() throws Exception {

        //Given
        JsonRepresentation entity = mock(JsonRepresentation.class);
        JSONObject requestBody = mock(JSONObject.class);

        given(entity.getJsonObject()).willReturn(requestBody);
        given(requestBody.toString()).willReturn("{\"resource_set_id\":\"RESOURCE_SET_ID\", "
                + "\"scopes\":[\"SCOPE_A\", \"SCOPE_C\"]}");

        doThrow(ServerException.class).when(resourceSetStore).read("RESOURCE_SET_ID", "RESOURCE_OWNER_ID");

        //When
        try {
            endpoint.registerPermissionRequest(entity);
        } catch (UmaException e) {
            //Then
            assertThat(e.getStatusCode()).isEqualTo(400);
            assertThat(e.getError()).isEqualTo("invalid_resource_set_id");
            throw e;
        }
    }

    @Test(expectedExceptions = UmaException.class)
    public void shouldThrowInvalidScopeExceptionWhenRequestedScopeNotInResourceScope() throws Exception {

        //Given
        JsonRepresentation entity = mock(JsonRepresentation.class);
        JSONObject requestBody = mock(JSONObject.class);

        given(entity.getJsonObject()).willReturn(requestBody);
        given(requestBody.toString()).willReturn("{\"resource_set_id\":\"RESOURCE_SET_ID\", "
                + "\"scopes\":[\"SCOPE_A\", \"SCOPE_C\"]}");

        setupResourceSetStore();

        //When
        try {
            endpoint.registerPermissionRequest(entity);
        } catch (UmaException e) {
            //Then
            assertThat(e.getStatusCode()).isEqualTo(400);
            assertThat(e.getError()).isEqualTo("invalid_scope");
            assertThat(e.getMessage()).contains("Requested scopes are not in allowed scopes");
            throw e;
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void shouldReturnPermissionTicket() throws Exception {

        //Given
        JsonRepresentation entity = mock(JsonRepresentation.class);
        JSONObject requestBody = mock(JSONObject.class);

        given(entity.getJsonObject()).willReturn(requestBody);
        given(requestBody.toString()).willReturn("{\"resource_set_id\":\"RESOURCE_SET_ID\", "
                + "\"scopes\":[\"SCOPE_A\", \"SCOPE_B\"]}");

        setupResourceSetStore();

        PermissionTicket ticket = new PermissionTicket("abc", null, null, null);
        given(umaTokenStore.createPermissionTicket(eq("RESOURCE_SET_ID"), anySetOf(String.class), eq("CLIENT_ID"))).willReturn(ticket);

        //When
        Representation responseBody = endpoint.registerPermissionRequest(entity);

        //Then
        Map<String, String> permissionTicket = (Map<String, String>) new ObjectMapper()
                .readValue(responseBody.getText(), Map.class);
        assertThat(permissionTicket).containsEntry("ticket", "abc");

        ArgumentCaptor<Status> statusCaptor = ArgumentCaptor.forClass(Status.class);
        verify(response).setStatus(statusCaptor.capture());
        assertThat(statusCaptor.getValue().getCode()).isEqualTo(201);
    }
}
