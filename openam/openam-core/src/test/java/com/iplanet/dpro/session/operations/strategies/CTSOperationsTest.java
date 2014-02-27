/**
 * Copyright 2013 ForgeRock AS.
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
package com.iplanet.dpro.session.operations.strategies;

import com.iplanet.dpro.session.Session;
import com.iplanet.dpro.session.SessionException;
import com.iplanet.dpro.session.SessionID;
import com.iplanet.dpro.session.service.InternalSession;
import com.iplanet.dpro.session.service.SessionService;
import com.iplanet.dpro.session.share.SessionInfo;
import com.iplanet.dpro.session.utils.SessionInfoFactory;
import com.sun.identity.shared.debug.Debug;
import org.forgerock.openam.cts.CTSPersistentStore;
import org.forgerock.openam.cts.adapters.SessionAdapter;
import org.forgerock.openam.cts.api.tokens.Token;
import org.forgerock.openam.cts.api.tokens.TokenIdFactory;
import org.forgerock.openam.cts.exceptions.CoreTokenException;
import org.forgerock.openam.cts.exceptions.DeleteFailedException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class CTSOperationsTest {

    private CTSPersistentStore mockCTS;
    private SessionAdapter mockAdapter;
    private TokenIdFactory mockIdFactory;
    private SessionInfoFactory mockInfoFactory;
    private SessionService mockSessionService;
    private CTSOperations ctsOperations;
    private Session mockSession;

    @BeforeMethod
    public void setUp() throws Exception {
        mockSession = mock(Session.class);
        mockCTS = mock(CTSPersistentStore.class);
        mockAdapter = mock(SessionAdapter.class);
        mockIdFactory = mock(TokenIdFactory.class);
        mockInfoFactory = mock(SessionInfoFactory.class);
        mockSessionService = mock(SessionService.class);

        SessionID mockSessionID = mock(SessionID.class);
        given(mockSession.getID()).willReturn(mockSessionID);

        given(mockIdFactory.toSessionTokenId(any(SessionID.class))).willReturn("TEST");

        ctsOperations = new CTSOperations(mockCTS, mockAdapter, mockIdFactory, mockInfoFactory, mockSessionService, mock(Debug.class));

    }

    @Test
    public void shouldReadTokenFromCTS() throws CoreTokenException, SessionException {
        // Given
        Token mockToken = mock(Token.class);
        given(mockCTS.read(anyString())).willReturn(mockToken);

        InternalSession mockInternalSession = mock(InternalSession.class);
        given(mockAdapter.fromToken(eq(mockToken))).willReturn(mockInternalSession);

        SessionInfo mockSessionInfo = mock(SessionInfo.class);
        given(mockInfoFactory.getSessionInfo(eq(mockInternalSession), any(SessionID.class))).willReturn(mockSessionInfo);

        // When
        SessionInfo result = ctsOperations.refresh(mockSession, false);

        // Then
        assertThat(result).isEqualTo(mockSessionInfo);
    }

    @Test
    public void shouldTriggerResetOfLastAccessTime() throws CoreTokenException, SessionException {
        // Given
        Token mockToken = mock(Token.class);
        given(mockCTS.read(anyString())).willReturn(mockToken);

        InternalSession mockInternalSession = mock(InternalSession.class);
        given(mockAdapter.fromToken(eq(mockToken))).willReturn(mockInternalSession);

        // When
        ctsOperations.refresh(mockSession, true);

        // Then
        verify(mockInternalSession).setLatestAccessTime();
    }

    @Test (expectedExceptions = SessionException.class)
    public void shouldThrowExceptionOnReadError() throws CoreTokenException, SessionException {
        // Given
        given(mockCTS.read(anyString())).willThrow(new CoreTokenException(""));

        // When / Then Throw
        ctsOperations.refresh(mockSession, false);
    }

    @Test
    public void shouldDeleteTokenFromCTSDuringLogout() throws SessionException, DeleteFailedException {
        // Given
        SessionID mockSessionID = mock(SessionID.class);
        given(mockSession.getID()).willReturn(mockSessionID);

        // When
        ctsOperations.logout(mockSession);

        // Then
        verify(mockCTS).delete(anyString());
        verify(mockSessionService).logoutInternalSession(eq(mockSessionID));
    }

    @Test
    public void shouldDeleteTokenFromCTSDuringDestory() throws DeleteFailedException, SessionException {
        // Given
        SessionID mockSessionID = mock(SessionID.class);
        given(mockSession.getID()).willReturn(mockSessionID);

        // When
        ctsOperations.destroy(mockSession);

        // Then
        verify(mockCTS).delete(anyString());
        verify(mockSessionService).destroyInternalSession(eq(mockSessionID));
    }

    @Test (expectedExceptions = SessionException.class)
    public void shouldThrowExceptionWhenDeleteFails() throws DeleteFailedException, SessionException {
        // Given
        SessionID mockSessionID = mock(SessionID.class);
        given(mockSession.getID()).willReturn(mockSessionID);

        doThrow(new DeleteFailedException("", mock(Throwable.class))).when(mockCTS).delete(anyString());

        // When / Then Throw
        ctsOperations.logout(mockSession);
    }

    @Test
    public void shouldUpdateTokenDuringSetProperty() throws SessionException, CoreTokenException {
        // Given
        String name = "name";
        String value = "value";

        SessionID mockSessionID = mock(SessionID.class);
        given(mockSession.getID()).willReturn(mockSessionID);

        Token mockToken = mock(Token.class);
        given(mockCTS.read(anyString())).willReturn(mockToken);

        InternalSession mockInternalSession = mock(InternalSession.class);
        given(mockAdapter.fromToken(eq(mockToken))).willReturn(mockInternalSession);

        // When
        ctsOperations.setProperty(mockSession, name, value);

        // Then
        verify(mockCTS).read(anyString());
        verify(mockCTS).update(any(Token.class));
    }

    @Test (expectedExceptions = SessionException.class)
    public void shouldThrownExceptionIfReadFailsDuringSetProperty() throws CoreTokenException, SessionException {
        // Given
        SessionID mockSessionID = mock(SessionID.class);
        given(mockSession.getID()).willReturn(mockSessionID);

        doThrow(new CoreTokenException("")).when(mockCTS).read(anyString());

        // When / Then Throw
        ctsOperations.setProperty(mockSession, "", "");
    }

    @Test (expectedExceptions = SessionException.class)
    public void shouldThrownExceptionIfUpdateFailsDuringSetProperty() throws CoreTokenException, SessionException {
        // Given
        SessionID mockSessionID = mock(SessionID.class);
        given(mockSession.getID()).willReturn(mockSessionID);

        Token mockToken = mock(Token.class);
        given(mockCTS.read(anyString())).willReturn(mockToken);

        InternalSession mockInternalSession = mock(InternalSession.class);
        given(mockAdapter.fromToken(eq(mockToken))).willReturn(mockInternalSession);
        given(mockAdapter.toToken(any(InternalSession.class))).willReturn(mockToken);

        doThrow(new CoreTokenException("")).when(mockCTS).update(any(Token.class));

        // When / Then Throw
        ctsOperations.setProperty(mockSession, "", "");
    }
}
