package org.forgerock.openam.sts.soap.token.config;

import com.google.inject.*;
import org.apache.cxf.sts.token.provider.SAMLTokenProvider;
import org.apache.cxf.sts.token.renewer.SAMLTokenRenewer;
import org.apache.ws.security.message.token.UsernameToken;
import org.forgerock.openam.sts.AMSTSConstants;
import org.forgerock.openam.sts.AuthTargetMapping;
import org.forgerock.openam.sts.STSInitializationException;
import org.forgerock.openam.sts.TokenType;
import org.forgerock.openam.sts.token.AMTokenParser;
import org.forgerock.openam.sts.token.AMTokenParserImpl;
import org.forgerock.openam.sts.token.ThreadLocalAMTokenCache;
import org.forgerock.openam.sts.token.ThreadLocalAMTokenCacheImpl;
import org.forgerock.openam.sts.token.provider.AMTokenProvider;
import org.forgerock.openam.sts.token.provider.OpenAMSessionIdElementBuilder;
import org.forgerock.openam.sts.token.provider.OpenAMSessionIdElementBuilderImpl;
import org.forgerock.openam.sts.token.validator.wss.AuthenticationHandler;
import org.forgerock.openam.sts.token.validator.wss.AuthenticationHandlerImpl;
import org.forgerock.openam.sts.token.validator.wss.UsernameTokenValidator;
import org.forgerock.openam.sts.token.validator.wss.disp.TokenAuthenticationRequestDispatcher;
import org.forgerock.openam.sts.token.validator.wss.disp.UsernameTokenAuthenticationRequestDispatcher;
import org.forgerock.openam.sts.token.validator.wss.uri.AuthenticationUriProvider;
import org.forgerock.openam.sts.token.validator.wss.uri.AuthenticationUriProviderImpl;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;

import static org.testng.Assert.assertTrue;

/**
 */
public class TokenOperationFactoryImplTest {
    TokenOperationFactory operationFactory;
    static class MyModule extends AbstractModule {

        @Override
        protected void configure() {
            bind(ThreadLocalAMTokenCache.class).to(ThreadLocalAMTokenCacheImpl.class);
            bind(OpenAMSessionIdElementBuilder.class).to(OpenAMSessionIdElementBuilderImpl.class);
            bind(AuthenticationUriProvider.class)
                    .to(AuthenticationUriProviderImpl.class);

            bind(new TypeLiteral<TokenAuthenticationRequestDispatcher<UsernameToken>>(){})
                    .to(UsernameTokenAuthenticationRequestDispatcher.class);

            bind(new TypeLiteral<AuthenticationHandler<UsernameToken>>(){})
                    .to(new TypeLiteral<AuthenticationHandlerImpl<UsernameToken>>() {
                    });
            bind(AMTokenParser.class).to(AMTokenParserImpl.class);
            bind(TokenOperationFactory.class).to(TokenOperationFactoryImpl.class);
        }

        @Provides
        @Named(AMSTSConstants.AM_DEPLOYMENT_URL)
        public String getAMDeploymentUrl() {
            return "am_deployment_url";
        }

        @Provides
        @Named(AMSTSConstants.REST_LOGOUT_URI_ELEMENT)
        public String getAMRestLogoutUriElement() {
            return "am_rest_logout";
        }

        @Provides
        @Named(AMSTSConstants.REST_ID_FROM_SESSION_URI_ELEMENT)
        public String getIdFromSessionUriElement() {
            return "id_from_session";
        }

        @Provides
        @Named(AMSTSConstants.AM_SESSION_COOKIE_NAME)
        public String getAMSessionCookieName() {
            return "cornholio";
        }

        @Provides
        @Named (AMSTSConstants.REST_AUTHN_URI_ELEMENT)
        String restAuthnUriElement() {
            return "rest_authn";
        }

        @Provides
        AuthTargetMapping authTargetMapping() {
            return AuthTargetMapping.builder()
                    .addMapping(UsernameToken.class, "index_type", "index_value")
                    .build();
        }

        @Provides
        @Inject
        public AMTokenProvider getAMTokenProvider(ThreadLocalAMTokenCache tokenCache, OpenAMSessionIdElementBuilder builder, org.slf4j.Logger logger) {
             return new AMTokenProvider(tokenCache, builder, logger);
        }

        @Provides
        @Inject
        UsernameTokenValidator getWssUsernameTokenValidator(
                AuthenticationHandler<UsernameToken> authenticationHandler,
                Logger logger) {
            return new UsernameTokenValidator(logger, authenticationHandler);
        }

        @Provides
        @Named (AMSTSConstants.REALM)
        String realm() {
            return "realm";
        }

        @Provides
        @Named(AMSTSConstants.AM_REST_AUTHN_JSON_ROOT)
        String getJsonRoot() {
            return "json";
        }

        @Provides
        Logger getSlf4jLogger() {
            return LoggerFactory.getLogger(AMSTSConstants.REST_STS_DEBUG_ID);
        }


    }
    @BeforeTest
    public void getTokenOperationFactory() {
        operationFactory = Guice.createInjector(new MyModule()).getInstance(TokenOperationFactory.class);
    }

    @Test
    public void testGetUsernameTokenValidator() throws STSInitializationException {
        assertTrue(operationFactory.getTokenStatusValidatorForType(TokenType.USERNAME) instanceof org.apache.cxf.sts.token.validator.UsernameTokenValidator);
    }

    @Test
    public void testTokenRenewer() throws STSInitializationException {
        assertTrue(operationFactory.getTokenRenewerForType(TokenType.SAML2) instanceof SAMLTokenRenewer);
    }

    @Test(expectedExceptions = STSInitializationException.class)
    public void testNonExistentTokenRenewer() throws STSInitializationException {
        operationFactory.getTokenRenewerForType(TokenType.USERNAME);
    }

    @Test
    public void testTokenTransform() throws STSInitializationException {
        assertTrue(operationFactory.getTokenProviderForTransformOperation(TokenType.USERNAME, TokenType.OPENAM) instanceof AMTokenProvider);
    }

    @Test(expectedExceptions = STSInitializationException.class)
    public void testNonExistentTransform() throws STSInitializationException {
        operationFactory.getTokenProviderForTransformOperation(TokenType.OPENAM, TokenType.USERNAME);
    }

    @Test
    public void testProvider() throws STSInitializationException {
        assertTrue(operationFactory.getTokenProviderForType(TokenType.SAML2) instanceof SAMLTokenProvider);
    }

    @Test(expectedExceptions = STSInitializationException.class)
    public void testNonExistentProvider() throws STSInitializationException {
        operationFactory.getTokenProviderForType(TokenType.USERNAME);
    }
}
