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
 * information: "Portions Copyrighted [year] [name of copyright owner]".
 *
 * Copyright 2013-2015 ForgeRock AS.
 */

package org.forgerock.openam.sts.soap.config.user;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.forgerock.json.fluent.JsonValue;
import org.forgerock.openam.sts.AMSTSConstants;
import org.forgerock.openam.sts.TokenType;
import org.forgerock.openam.sts.config.user.AuthTargetMapping;
import org.forgerock.openam.sts.config.user.SAML2Config;
import org.forgerock.openam.utils.IOUtils;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

public class SoapSTSInstanceConfigTest {
    private static final boolean WITH_KEYSTORE_CONFIG = true;
    private static final boolean WITH_VALIDATE_TRANSFORM = true;
    private static final boolean WITH_ISSUE_OPERATION = true;
    private static final boolean DELEGATION_VALIDATORS_SPECIFIED = true;
    private static final boolean CUSTOM_DELEGATION_HANDLER = true;

    @Test
    public void testEquals() throws UnsupportedEncodingException {
        SoapSTSInstanceConfig ric1 = createInstanceConfig("/bob", "http://localhost:8080/openam", WITH_KEYSTORE_CONFIG,
                WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, DELEGATION_VALIDATORS_SPECIFIED, CUSTOM_DELEGATION_HANDLER);
        SoapSTSInstanceConfig ric2 = createInstanceConfig("/bob", "http://localhost:8080/openam", WITH_KEYSTORE_CONFIG,
                WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, DELEGATION_VALIDATORS_SPECIFIED, CUSTOM_DELEGATION_HANDLER);
        assertEquals(ric1, ric2);
        assertEquals(ric1.hashCode(), ric2.hashCode());

        ric1 = createInstanceConfig("/bob", "http://localhost:8080/openam", !WITH_KEYSTORE_CONFIG,
                WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, !DELEGATION_VALIDATORS_SPECIFIED, !CUSTOM_DELEGATION_HANDLER);
        ric2 = createInstanceConfig("/bob", "http://localhost:8080/openam", !WITH_KEYSTORE_CONFIG,
                WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, !DELEGATION_VALIDATORS_SPECIFIED, !CUSTOM_DELEGATION_HANDLER);
        assertEquals(ric1, ric2);
        assertEquals(ric1.hashCode(), ric2.hashCode());

        ric1 = createInstanceConfig("/bob", "http://localhost:8080/openam", !WITH_KEYSTORE_CONFIG,
                !WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, DELEGATION_VALIDATORS_SPECIFIED, CUSTOM_DELEGATION_HANDLER);
        ric2 = createInstanceConfig("/bob", "http://localhost:8080/openam", !WITH_KEYSTORE_CONFIG,
                !WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, DELEGATION_VALIDATORS_SPECIFIED, CUSTOM_DELEGATION_HANDLER);
        assertEquals(ric1, ric2);
        assertEquals(ric1.hashCode(), ric2.hashCode());

        ric1 = createInstanceConfig("/bob", "http://localhost:8080/openam", WITH_KEYSTORE_CONFIG,
                WITH_VALIDATE_TRANSFORM, !WITH_ISSUE_OPERATION, !DELEGATION_VALIDATORS_SPECIFIED, !CUSTOM_DELEGATION_HANDLER);
        ric2 = createInstanceConfig("/bob", "http://localhost:8080/openam", WITH_KEYSTORE_CONFIG,
                WITH_VALIDATE_TRANSFORM, !WITH_ISSUE_OPERATION, !DELEGATION_VALIDATORS_SPECIFIED, !CUSTOM_DELEGATION_HANDLER);
        assertEquals(ric1, ric2);
        assertEquals(ric1.hashCode(), ric2.hashCode());

        ric1 = createInstanceConfig("/bob", "http://localhost:8080/openam", !WITH_KEYSTORE_CONFIG,
                WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, DELEGATION_VALIDATORS_SPECIFIED, !CUSTOM_DELEGATION_HANDLER);
        ric2 = createInstanceConfig("/bob", "http://localhost:8080/openam", !WITH_KEYSTORE_CONFIG,
                WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, DELEGATION_VALIDATORS_SPECIFIED, !CUSTOM_DELEGATION_HANDLER);
        assertEquals(ric1, ric2);
        assertEquals(ric1.hashCode(), ric2.hashCode());

        ric1 = createInstanceConfig("/bob", "http://localhost:8080/openam", WITH_KEYSTORE_CONFIG,
                !WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, DELEGATION_VALIDATORS_SPECIFIED, !CUSTOM_DELEGATION_HANDLER);
        ric2 = createInstanceConfig("/bob", "http://localhost:8080/openam", WITH_KEYSTORE_CONFIG,
                !WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, DELEGATION_VALIDATORS_SPECIFIED, !CUSTOM_DELEGATION_HANDLER);
        assertEquals(ric1, ric2);
        assertEquals(ric1.hashCode(), ric2.hashCode());
    }

    @Test
    public void testNotEquals() throws UnsupportedEncodingException {
        SoapSTSInstanceConfig ric1 = createInstanceConfig("/bob", "http://localhost:8080/openam", WITH_KEYSTORE_CONFIG,
                WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, DELEGATION_VALIDATORS_SPECIFIED, CUSTOM_DELEGATION_HANDLER);
        SoapSTSInstanceConfig ric2 = createInstanceConfig("/bobo", "http://localhost:8080/openam", WITH_KEYSTORE_CONFIG,
                WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, DELEGATION_VALIDATORS_SPECIFIED, !CUSTOM_DELEGATION_HANDLER);
        assertNotEquals(ric1, ric2);
        assertNotEquals(ric1.hashCode(), ric2.hashCode());

        ric1 = createInstanceConfig("/bob", "http://localhost:8080/openam", WITH_KEYSTORE_CONFIG,
                WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, DELEGATION_VALIDATORS_SPECIFIED, !CUSTOM_DELEGATION_HANDLER);
        ric2 = createInstanceConfig("/bob", "http://localhost:8080/", WITH_KEYSTORE_CONFIG,
                WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, DELEGATION_VALIDATORS_SPECIFIED, CUSTOM_DELEGATION_HANDLER);
        assertNotEquals(ric1, ric2);
        assertNotEquals(ric1.hashCode(), ric2.hashCode());

        ric1 = createInstanceConfig("/bob", "http://localhost:8080/", WITH_KEYSTORE_CONFIG,
                WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, DELEGATION_VALIDATORS_SPECIFIED, CUSTOM_DELEGATION_HANDLER);
        ric2 = createInstanceConfig("/bob", "http://localhost:8080/", !WITH_KEYSTORE_CONFIG,
                WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, DELEGATION_VALIDATORS_SPECIFIED, !CUSTOM_DELEGATION_HANDLER);
        assertNotEquals(ric1, ric2);
        assertNotEquals(ric1.hashCode(), ric2.hashCode());

        ric1 = createInstanceConfig("/bob", "http://localhost:8080/", !WITH_KEYSTORE_CONFIG,
                !WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, DELEGATION_VALIDATORS_SPECIFIED, !CUSTOM_DELEGATION_HANDLER);
        ric2 = createInstanceConfig("/bob", "http://localhost:8080/", !WITH_KEYSTORE_CONFIG,
                WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, DELEGATION_VALIDATORS_SPECIFIED, CUSTOM_DELEGATION_HANDLER);
        assertNotEquals(ric1, ric2);
        assertNotEquals(ric1.hashCode(), ric2.hashCode());

        ric1 = createInstanceConfig("/bob", "http://localhost:8080/", WITH_KEYSTORE_CONFIG,
                !WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, !DELEGATION_VALIDATORS_SPECIFIED, !CUSTOM_DELEGATION_HANDLER);
        ric2 = createInstanceConfig("/bob", "http://localhost:8080/", WITH_KEYSTORE_CONFIG,
                WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, !DELEGATION_VALIDATORS_SPECIFIED, !CUSTOM_DELEGATION_HANDLER);
        assertNotEquals(ric1, ric2);
        assertNotEquals(ric1.hashCode(), ric2.hashCode());

        ric1 = createInstanceConfig("/bob", "http://localhost:8080/", WITH_KEYSTORE_CONFIG,
                !WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, !DELEGATION_VALIDATORS_SPECIFIED, !CUSTOM_DELEGATION_HANDLER);
        ric2 = createInstanceConfig("/bob", "http://localhost:8080/", WITH_KEYSTORE_CONFIG,
                WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, !DELEGATION_VALIDATORS_SPECIFIED, !CUSTOM_DELEGATION_HANDLER);
        assertNotEquals(ric1, ric2);
        assertNotEquals(ric1.hashCode(), ric2.hashCode());

        ric1 = createInstanceConfig("/bob", "http://localhost:8080/", WITH_KEYSTORE_CONFIG,
                WITH_VALIDATE_TRANSFORM, !WITH_ISSUE_OPERATION, !DELEGATION_VALIDATORS_SPECIFIED, !CUSTOM_DELEGATION_HANDLER);
        ric2 = createInstanceConfig("/bob", "http://localhost:8080/", WITH_KEYSTORE_CONFIG,
                WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, !DELEGATION_VALIDATORS_SPECIFIED, !CUSTOM_DELEGATION_HANDLER);
        assertNotEquals(ric1, ric2);
        assertNotEquals(ric1.hashCode(), ric2.hashCode());

        ric1 = createInstanceConfig("/bob", "http://localhost:8080/", WITH_KEYSTORE_CONFIG,
                WITH_VALIDATE_TRANSFORM, !WITH_ISSUE_OPERATION, DELEGATION_VALIDATORS_SPECIFIED, !CUSTOM_DELEGATION_HANDLER);
        ric2 = createInstanceConfig("/bob", "http://localhost:8080/", WITH_KEYSTORE_CONFIG,
                WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, !DELEGATION_VALIDATORS_SPECIFIED, !CUSTOM_DELEGATION_HANDLER);
        assertNotEquals(ric1, ric2);
        assertNotEquals(ric1.hashCode(), ric2.hashCode());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testRejectIfNull() throws UnsupportedEncodingException {
        createIncompleteInstanceConfig();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testRejectIfNeitherIssueNorValidateTransform() throws UnsupportedEncodingException {
        createInstanceConfig("/bob", "http://localhost:8080/", WITH_KEYSTORE_CONFIG,
                !WITH_VALIDATE_TRANSFORM, !WITH_ISSUE_OPERATION, DELEGATION_VALIDATORS_SPECIFIED, !CUSTOM_DELEGATION_HANDLER);
    }

    @Test
    public void testJsonRoundTrip() throws UnsupportedEncodingException {
        SoapSTSInstanceConfig instanceConfig = createInstanceConfig("/bobo/instance1", "http://host.com:8080/am",
                WITH_KEYSTORE_CONFIG, WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, DELEGATION_VALIDATORS_SPECIFIED, CUSTOM_DELEGATION_HANDLER);
        assertEquals(instanceConfig, SoapSTSInstanceConfig.fromJson(instanceConfig.toJson()));

        instanceConfig = createInstanceConfig("/bobo/instance1", "http://host.com:8080/am", !WITH_KEYSTORE_CONFIG,
                WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, !DELEGATION_VALIDATORS_SPECIFIED, !CUSTOM_DELEGATION_HANDLER);
        assertEquals(instanceConfig, SoapSTSInstanceConfig.fromJson(instanceConfig.toJson()));

        instanceConfig = createInstanceConfig("/bobo/instance1", "http://host.com:8080/am", WITH_KEYSTORE_CONFIG,
                WITH_VALIDATE_TRANSFORM, !WITH_ISSUE_OPERATION, DELEGATION_VALIDATORS_SPECIFIED, !CUSTOM_DELEGATION_HANDLER);
        assertEquals(instanceConfig, SoapSTSInstanceConfig.fromJson(instanceConfig.toJson()));

        instanceConfig = createInstanceConfig("/bobo/instance1", "http://host.com:8080/am", !WITH_KEYSTORE_CONFIG,
                !WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, DELEGATION_VALIDATORS_SPECIFIED, CUSTOM_DELEGATION_HANDLER);
        assertEquals(instanceConfig, SoapSTSInstanceConfig.fromJson(instanceConfig.toJson()));

        instanceConfig = createInstanceConfig("/bobo/instance1", "http://host.com:8080/am", WITH_KEYSTORE_CONFIG,
                !WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, !DELEGATION_VALIDATORS_SPECIFIED, !CUSTOM_DELEGATION_HANDLER);
        assertEquals(instanceConfig, SoapSTSInstanceConfig.fromJson(instanceConfig.toJson()));

        instanceConfig = createInstanceConfig("/bobo/instance1", "http://host.com:8080/am", WITH_KEYSTORE_CONFIG,
                WITH_VALIDATE_TRANSFORM, !WITH_ISSUE_OPERATION, DELEGATION_VALIDATORS_SPECIFIED, !CUSTOM_DELEGATION_HANDLER);
        assertEquals(instanceConfig, SoapSTSInstanceConfig.fromJson(instanceConfig.toJson()));

        instanceConfig = createInstanceConfig("/bobo/instance1", "http://host.com:8080/am", WITH_KEYSTORE_CONFIG,
                !WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, !DELEGATION_VALIDATORS_SPECIFIED, !CUSTOM_DELEGATION_HANDLER);
        assertEquals(instanceConfig, SoapSTSInstanceConfig.fromJson(instanceConfig.toJson()));

        instanceConfig = createInstanceConfig("/bobo/instance1", "http://host.com:8080/am", !WITH_KEYSTORE_CONFIG,
                !WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, DELEGATION_VALIDATORS_SPECIFIED, !CUSTOM_DELEGATION_HANDLER);
        assertEquals(instanceConfig, SoapSTSInstanceConfig.fromJson(instanceConfig.toJson()));
    }

    @Test
    public void testMapMarshalRoundTrip() throws UnsupportedEncodingException {
        SoapSTSInstanceConfig instanceConfig = createInstanceConfig("/bobo/instance1", "http://host.com:8080/am",
                WITH_KEYSTORE_CONFIG, WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, DELEGATION_VALIDATORS_SPECIFIED,
                !CUSTOM_DELEGATION_HANDLER);
        assertEquals(instanceConfig, SoapSTSInstanceConfig.marshalFromAttributeMap(instanceConfig.marshalToAttributeMap()));

        instanceConfig = createInstanceConfig("/bobo/instance1", "http://host.com:8080/am", !WITH_KEYSTORE_CONFIG,
                WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, !DELEGATION_VALIDATORS_SPECIFIED, !CUSTOM_DELEGATION_HANDLER);
        assertEquals(instanceConfig, SoapSTSInstanceConfig.marshalFromAttributeMap(instanceConfig.marshalToAttributeMap()));

        instanceConfig = createInstanceConfig("/bobo/instance1", "http://host.com:8080/am", WITH_KEYSTORE_CONFIG,
                WITH_VALIDATE_TRANSFORM, !WITH_ISSUE_OPERATION, DELEGATION_VALIDATORS_SPECIFIED, !CUSTOM_DELEGATION_HANDLER);
        assertEquals(instanceConfig, SoapSTSInstanceConfig.marshalFromAttributeMap(instanceConfig.marshalToAttributeMap()));

        instanceConfig = createInstanceConfig("/bobo/instance1", "http://host.com:8080/am", !WITH_KEYSTORE_CONFIG,
                !WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, !DELEGATION_VALIDATORS_SPECIFIED, !CUSTOM_DELEGATION_HANDLER);
        assertEquals(instanceConfig, SoapSTSInstanceConfig.marshalFromAttributeMap(instanceConfig.marshalToAttributeMap()));

        instanceConfig = createInstanceConfig("/bobo/instance1", "http://host.com:8080/am", WITH_KEYSTORE_CONFIG,
                !WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, !DELEGATION_VALIDATORS_SPECIFIED, CUSTOM_DELEGATION_HANDLER);
        assertEquals(instanceConfig, SoapSTSInstanceConfig.marshalFromAttributeMap(instanceConfig.marshalToAttributeMap()));

        instanceConfig = createInstanceConfig("/bobo/instance1", "http://host.com:8080/am", WITH_KEYSTORE_CONFIG,
                WITH_VALIDATE_TRANSFORM, !WITH_ISSUE_OPERATION, !DELEGATION_VALIDATORS_SPECIFIED, !CUSTOM_DELEGATION_HANDLER);
        assertEquals(instanceConfig, SoapSTSInstanceConfig.marshalFromAttributeMap(instanceConfig.marshalToAttributeMap()));

        instanceConfig = createInstanceConfig("/bobo/instance1", "http://host.com:8080/am", WITH_KEYSTORE_CONFIG,
                !WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, DELEGATION_VALIDATORS_SPECIFIED, CUSTOM_DELEGATION_HANDLER);
        assertEquals(instanceConfig, SoapSTSInstanceConfig.marshalFromAttributeMap(instanceConfig.marshalToAttributeMap()));

        instanceConfig = createInstanceConfig("/bobo/instance1", "http://host.com:8080/am", !WITH_KEYSTORE_CONFIG,
                !WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, !DELEGATION_VALIDATORS_SPECIFIED, !CUSTOM_DELEGATION_HANDLER);
        assertEquals(instanceConfig, SoapSTSInstanceConfig.marshalFromAttributeMap(instanceConfig.marshalToAttributeMap()));
    }

    @Test
    public void testOldJacksonJsonStringMarhalling() throws IOException {
        SoapSTSInstanceConfig origConfig = createInstanceConfig("/bobo/instance1", "http://host.com:8080/am", WITH_KEYSTORE_CONFIG,
                WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, DELEGATION_VALIDATORS_SPECIFIED, !CUSTOM_DELEGATION_HANDLER);
        /*
        This is how the Crest HttpServletAdapter ultimately constitutes a JsonValue from a json string. See the
        org.forgerock.json.resource.servlet.HttpUtils.parseJsonBody (called from HttpServletAdapter.getJsonContent)
        for details. Covering old and new Jackson versions for completeness.
         */
        org.codehaus.jackson.JsonParser parser =
                new org.codehaus.jackson.map.ObjectMapper().getJsonFactory().createJsonParser(origConfig.toJson().toString());
        final Object content = parser.readValueAs(Object.class);

        assertEquals(origConfig, SoapSTSInstanceConfig.fromJson(new JsonValue(content)));
    }

    @Test
    public void testJsonStringMarshalling() throws IOException {
        SoapSTSInstanceConfig origConfig = createInstanceConfig("/bobo/instance1", "http://host.com:8080/am", WITH_KEYSTORE_CONFIG,
                WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, DELEGATION_VALIDATORS_SPECIFIED, CUSTOM_DELEGATION_HANDLER);

        /*
        This is how the Crest HttpServletAdapter ultimately constitutes a JsonValue from a json string. See the
        org.forgerock.json.resource.servlet.HttpUtils.parseJsonBody (called from HttpServletAdapter.getJsonContent)
        for details. Covering old and new Jackson versions for completeness.
         */
        JsonParser parser = new ObjectMapper().getJsonFactory().createJsonParser(origConfig.toJson().toString());
        final Object content = parser.readValueAs(Object.class);

        assertEquals(origConfig, SoapSTSInstanceConfig.fromJson(new JsonValue(content)));
    }

    @Test
    public void testJsonMapMarshalRoundTrip() throws IOException {
        SoapSTSInstanceConfig config = createInstanceConfig("/bobo/instance1", "http://host.com:8080/am", WITH_KEYSTORE_CONFIG,
                WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, !DELEGATION_VALIDATORS_SPECIFIED, !CUSTOM_DELEGATION_HANDLER);
        Map<String, Set<String>> attributeMap = config.marshalToAttributeMap();
        JsonValue jsonMap = new JsonValue(marshalSetValuesToListValues(attributeMap));
        assertEquals(config, SoapSTSInstanceConfig.marshalFromJsonAttributeMap(jsonMap));

        config = createInstanceConfig("/bobo/instance1", "http://host.com:8080/am", !WITH_KEYSTORE_CONFIG,
                WITH_VALIDATE_TRANSFORM, WITH_ISSUE_OPERATION, DELEGATION_VALIDATORS_SPECIFIED, CUSTOM_DELEGATION_HANDLER);
        attributeMap = config.marshalToAttributeMap();
        jsonMap = new JsonValue(marshalSetValuesToListValues(attributeMap));
        assertEquals(config, SoapSTSInstanceConfig.marshalFromJsonAttributeMap(jsonMap));
    }

    /*
    Because SoapSTSInstanceConfig and encapsulated instances must be marshaled to a Map<String, Set<String>> for SMS persistence,
    SoapSTSInstanceConfig and encapsulated classes define statics that define the keys which must correspond to the String
    keys in the SMS-persisted-map, values which must correspond to the entries defined in soapSTS.xml. This unit test
    tests this correspondence.
     */
    @Test
    public void testServicePropertyFileCorrespondence() throws IOException {
        String fileContent =
                IOUtils.getFileContent("../../openam-server-only/src/main/resources/services/soapSTS.xml");
        assertTrue(fileContent.contains(SoapSTSKeystoreConfig.KEYSTORE_FILENAME));
        assertTrue(fileContent.contains(SoapSTSKeystoreConfig.KEYSTORE_PASSWORD));
        assertTrue(fileContent.contains(SoapSTSKeystoreConfig.SIGNATURE_KEY_ALIAS));
        assertTrue(fileContent.contains(SoapSTSKeystoreConfig.ENCRYPTION_KEY_ALIAS));
        assertTrue(fileContent.contains(SoapSTSKeystoreConfig.SIGNATURE_KEY_PASSWORD));
        assertTrue(fileContent.contains(SoapSTSKeystoreConfig.ENCRYPTION_KEY_ALIAS));
        assertTrue(fileContent.contains(SoapSTSKeystoreConfig.ENCRYPTION_KEY_PASSWORD));

        assertTrue(fileContent.contains(SoapDeploymentConfig.SERVICE_QNAME));
        assertTrue(fileContent.contains(SoapDeploymentConfig.SERVICE_PORT));
        assertTrue(fileContent.contains(SoapDeploymentConfig.WSDL_LOCATION));
        assertTrue(fileContent.contains(SoapDeploymentConfig.AM_DEPLOYMENT_URL));

        assertTrue(fileContent.contains(SoapDelegationConfig.DELEGATION_TOKEN_VALIDATORS));
        assertTrue(fileContent.contains(SoapDelegationConfig.CUSTOM_DELEGATION_TOKEN_HANDLERS));

        assertTrue(fileContent.contains(SoapSTSInstanceConfig.ISSUE_TOKEN_TYPES));
        assertTrue(fileContent.contains(SoapSTSInstanceConfig.TRANSFORMED_TOKEN_TYPES));
        assertTrue(fileContent.contains(SoapSTSInstanceConfig.DELEGATION_RELATIONSHIP_SUPPORTED));
    }

    private SoapSTSInstanceConfig createInstanceConfig(String uriElement, String amDeploymentUrl,
                                                       boolean withKeystoreConfig, boolean withValidateTransform,
                                                       boolean withIssueOperation,
                                                       boolean delegationValidatorsSpecified,
                                                       boolean customDelegationHandler) throws UnsupportedEncodingException {
        AuthTargetMapping mapping = AuthTargetMapping.builder()
                .addMapping(TokenType.USERNAME, "service", "ldap")
                .build();

        SoapDeploymentConfig deploymentConfig =
                SoapDeploymentConfig.builder()
                        .portQName(AMSTSConstants.UNPROTECTED_STS_SERVICE_PORT)
                        .serviceQName(AMSTSConstants.UNPROTECTED_STS_SERVICE)
                        .wsdlLocation("wsdl_loc")
                        .realm("realm")
                        .amDeploymentUrl(amDeploymentUrl)
                        .uriElement(uriElement)
                        .authTargetMapping(mapping)
                        .build();

        SoapSTSKeystoreConfig keystoreConfig = null;
        if (withKeystoreConfig) {
            keystoreConfig =
                    SoapSTSKeystoreConfig.builder()
                    .keystoreFileName("stsstore.jks")
                    .keystorePassword("stsspass".getBytes(AMSTSConstants.UTF_8_CHARSET_ID))
                    .encryptionKeyAlias("mystskey")
                    .signatureKeyAlias("mystskey")
                    .encryptionKeyPassword("stskpass".getBytes(AMSTSConstants.UTF_8_CHARSET_ID))
                    .signatureKeyPassword("stskpass".getBytes(AMSTSConstants.UTF_8_CHARSET_ID))
                    .build();
        }

        SoapSTSInstanceConfig.SoapSTSInstanceConfigBuilderBase<?> builder = SoapSTSInstanceConfig.builder();
        if (withValidateTransform) {
            builder.addValidateTokenTranslation(TokenType.OPENAM, TokenType.SAML2, false);
        }
        if (withIssueOperation) {
            builder.addIssueTokenType(TokenType.SAML2);
        }
        Map<String,String> attributeMap = new HashMap<String, String>();
        attributeMap.put("mail", "email");
        attributeMap.put("uid", "id");
        SAML2Config saml2Config =
                SAML2Config.builder()
                        .nameIdFormat("transient")
                        .tokenLifetimeInSeconds(500000)
                        .spEntityId("http://host.com/saml2/sp/entity/id")
                        .encryptAssertion(true)
                        .signAssertion(true)
                        .encryptionAlgorithm("http://www.w3.org/2001/04/xmlenc#aes128-cbc")
                        .encryptionKeyAlias("test")
                        .signatureKeyAlias("test")
                        .signatureKeyPassword("super.secret".getBytes())
                        .encryptionAlgorithmStrength(128)
                        .keystoreFile("da/directory/file")
                        .keystorePassword("super.secret".getBytes())
                        .attributeMap(attributeMap)
                        .build();
        boolean delegationRelationshipsSupported = customDelegationHandler || delegationValidatorsSpecified;
        if (delegationRelationshipsSupported) {
            SoapDelegationConfig.SoapDelegationConfigBuilder delegationConfigBuilder = SoapDelegationConfig.builder();
            if (delegationValidatorsSpecified) {
                delegationConfigBuilder
                        .addValidatedDelegationTokenType(TokenType.USERNAME)
                        .addValidatedDelegationTokenType(TokenType.OPENAM);
            }
            if (customDelegationHandler) {
                delegationConfigBuilder.addCustomDelegationTokenHandler("com.org.TokenDelegationHandlerImpl");
            }
            builder.soapDelegationConfig(delegationConfigBuilder.build());
        }
        return  builder
                .deploymentConfig(deploymentConfig)
                .soapSTSKeystoreConfig(keystoreConfig)
                .issuerName("Cornholio")
                .saml2Config(saml2Config)
                .delegationRelationshipsSupported(delegationRelationshipsSupported)
                .build();
    }

    private SoapSTSInstanceConfig createIncompleteInstanceConfig() throws UnsupportedEncodingException {
        //leave out the AuthTargetMapping and SAML2Config

        SoapDeploymentConfig deploymentConfig =
                SoapDeploymentConfig.builder()
                        .uriElement("whatever")
                        .amDeploymentUrl("whatever")
                        .build();

        SoapSTSKeystoreConfig keystoreConfig =
                SoapSTSKeystoreConfig.builder()
                        .keystoreFileName("stsstore.jks")
                        .keystorePassword("stsspass".getBytes(AMSTSConstants.UTF_8_CHARSET_ID))
                        .encryptionKeyAlias("mystskey")
                        .signatureKeyAlias("mystskey")
                        .encryptionKeyPassword("stskpass".getBytes(AMSTSConstants.UTF_8_CHARSET_ID))
                        .signatureKeyPassword("stskpass".getBytes(AMSTSConstants.UTF_8_CHARSET_ID))
                        .build();

        return SoapSTSInstanceConfig.builder()
                .deploymentConfig(deploymentConfig)
                .soapSTSKeystoreConfig(keystoreConfig)
                .issuerName("Cornholio")
                .build();
    }

    //TODO: investigate if this is still necessary - does CREST turn Set values into json arrays?
    private Map<String, List<String>> marshalSetValuesToListValues(Map<String, Set<String>> smsMap) {
        Map<String, List<String>> listMap = new HashMap<String, List<String>>();
        for (Map.Entry<String, Set<String>> entry : smsMap.entrySet()) {
            ArrayList<String> list = new ArrayList<String>(entry.getValue().size());
            list.addAll(entry.getValue());
            listMap.put(entry.getKey(), list);
        }
        return listMap;
    }
}
