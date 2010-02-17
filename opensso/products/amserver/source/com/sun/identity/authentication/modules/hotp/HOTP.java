/*
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
 * $Id: HOTP.java,v 1.1 2009/03/24 23:52:12 pluo Exp $
 *
 */
package com.sun.identity.authentication.modules.hotp;

import com.iplanet.dpro.session.SessionID;
import com.iplanet.dpro.session.service.InternalSession;
import com.sun.identity.shared.debug.Debug;
import com.sun.identity.shared.datastruct.CollectionHelper;
import com.sun.identity.authentication.spi.AMLoginModule;
import com.sun.identity.authentication.spi.AuthLoginException;
import com.sun.identity.authentication.spi.InvalidPasswordException;
import com.sun.identity.authentication.util.ISAuthConstants;
import com.sun.identity.authentication.spi.AMAuthCallBackException;
import com.sun.identity.shared.Constants;
import com.sun.identity.idm.AMIdentityRepository;
import com.sun.identity.idm.IdRepoException;
import com.sun.identity.sm.ServiceConfig;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import java.util.Collections;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.ConfirmationCallback;
import java.io.IOException;
import java.security.Principal;

import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;

import com.sun.identity.idm.AMIdentityRepository;
import com.sun.identity.idm.IdRepoException;
import com.sun.identity.idm.IdSearchControl;
import com.sun.identity.idm.IdSearchResults;
import com.sun.identity.idm.IdType;
import com.sun.identity.idm.AMIdentity;
import com.iplanet.sso.SSOException;
import com.iplanet.sso.SSOToken;
import com.iplanet.sso.SSOTokenID;
import com.iplanet.sso.SSOTokenManager;
import com.iplanet.am.util.AMSendMail;
import javax.mail.MessagingException;

import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public class HOTP extends AMLoginModule {
    // local variables

    ResourceBundle bundle = null;
    private String userName = null;
    private String userUUID = null;
    private ServiceConfig sc;
    private int currentState;
    private String currentConfigName;
    private Map sharedState;
    public Map currentConfig;
    protected Debug debug = null;
    protected String amAuthHOTP;
    protected Principal userPrincipal;

    // TODO : the moving factor should be retrieved from user's profile
    private static int movingFactor = 0;
    String sentHOTPCode = null;
    long sentHOTPCodeTime; // in seconds
    String enteredHOTPCode = null;
    private SecureRandom secureRandom = null;

    // Module specific properties
    private static String AUTHLEVEL = "sunAMAuthHOTPAuthLevel";
    private static String GATEWAYSMSImplCLASS =
            "sunAMAuthHOTPSMSGatewayImplClassName";
    private static String CODEVALIDITYDURATION =
            "sunAMAuthHOTPPasswordValidityDuration";
    private static String CODELENGTH = "sunAMAuthHOTPPasswordLength";
    private static String CODEDELIVERY = "sunAMAuthHOTPasswordDelivery";
    String gatewaySMSImplClass = null;
    String codeValidityDuration = null;
    String codeLength = null;
    String codeDelivery = null;

    public HOTP() {
        amAuthHOTP = "amAuthHOTP";
        debug = Debug.getInstance(amAuthHOTP);

        // Obtain the secureRandom instance
        try {
            secureRandom = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException ex) {
            debug.error("HOTP.HOTP() : " + "HOTP : Initialization Failed", ex);
        }

    }

    public void init(Subject subject, Map sharedState, Map options) {
        sc = (ServiceConfig) options.get("ServiceConfig");
        currentConfig = options;
        currentConfigName =
                (String) options.get(ISAuthConstants.MODULE_INSTANCE_NAME);
        String authLevel = CollectionHelper.getMapAttr(options, AUTHLEVEL);
        if (authLevel != null) {
            try {
                setAuthLevel(Integer.parseInt(authLevel));
            } catch (Exception e) {
                debug.error("HOTP.init() : " + "Unable to set auth level " + authLevel, e);
            }
        }

        gatewaySMSImplClass = CollectionHelper.getMapAttr(options,
                GATEWAYSMSImplCLASS);
        codeValidityDuration = CollectionHelper.getMapAttr(options,
                CODEVALIDITYDURATION);
        codeLength = CollectionHelper.getMapAttr(options, CODELENGTH);
        codeDelivery = CollectionHelper.getMapAttr(options, CODEDELIVERY);

        java.util.Locale locale = getLoginLocale();
        bundle = amCache.getResBundle(amAuthHOTP, locale);
        if (debug.messageEnabled()) {
            debug.message("HOTP.init() : " + "HOTP resouce bundle locale=" + locale);
        }
        try {
            userName = (String) sharedState.get(getUserKey());
        } catch (Exception e) {
            debug.error("HOTP.init() : " + "Unable to set userName : ", e);
        }
        this.sharedState = sharedState;
    }

    public int process(Callback[] callbacks, int state)
            throws AuthLoginException {
        currentState = state;
        int retVal = 0;
        int action = 0;
        try {
            if (userName == null || userName.length() == 0) {
                // session upgrade case. Need to find the user ID from the old
                // session
                SSOTokenManager mgr = SSOTokenManager.getInstance();
                InternalSession isess = getLoginState("HOTP").getOldSession();
                if (isess == null) {
                    throw new AuthLoginException("amAuth", "noInternalSession",
                            null);
                }
                SSOToken token = mgr.createSSOToken(isess.getID().toString());
                userUUID = token.getPrincipal().getName();
                userName = token.getProperty("UserToken");
                if (debug.messageEnabled()) {
                    debug.message("HOTP.process() : " + "UserName in SSOToekn : " + userName);
                }

                if (userName == null || userName.length() == 0) {
                    throw new AuthLoginException("amAuth", "noUserName", null);
                }
            }

            if (currentState == ISAuthConstants.LOGIN_START) {
                // callback[1] is OTP code
                // callback[1] is user selected button index
                // action = 0 is Submit HOTP Code Button
                // action = 1 is Request HOTP COde Button
                if (callbacks != null && callbacks.length == 2) {
                    action =
                            ((ConfirmationCallback)
                            callbacks[1]).getSelectedIndex();
                    if (debug.messageEnabled()) {
                        debug.message("HOTP.process() : " + "LOGIN page button index: " + action);
                    }

                    if (action == 0) { //Submit HOTP Code
                        enteredHOTPCode = String.valueOf(((PasswordCallback)
                                callbacks[0]).getPassword());
                        if (sentHOTPCode == null ||
                                sentHOTPCode.length() == 0 ||
                                enteredHOTPCode == null ||
                                enteredHOTPCode.length() == 0) {
                            if (debug.messageEnabled()) {
                                debug.message("HOTP.process() : " + "invalid HOTP code");
                            }
                            setFailureID(userName); 
                            throw new InvalidPasswordException("amAuth",
                                    "invalidPasswd", null);

                        }
                        // Enfore the code validate time HOTP module config
                        if (sentHOTPCode.equals(enteredHOTPCode)) {
                            long timePassed =
                                    ((System.currentTimeMillis() / 1000) -
                                    sentHOTPCodeTime);
                            if (timePassed <= 
                                    (Long.valueOf(codeValidityDuration).longValue()) * 60){
                                // one time use only
                                sentHOTPCode = null;
                                enteredHOTPCode = null;
                                return ISAuthConstants.LOGIN_SUCCEED;
                            } else {
                                if (debug.messageEnabled()) {
                                    debug.message("HOTP.process() : " + "HOTP code has " +
                                            "expired");
                                }
                                setFailureID(userName);
                                throw new InvalidPasswordException("amAuth",
                                        "invalidPasswd", null);
                            }
                        } else {
                            if (debug.messageEnabled()) {
                                debug.message("HOTP.process() : " + "HOTP code is not " +
                                        "valid");

                            }
                            setFailureID(userName);
                            throw new InvalidPasswordException("amAuth",
                                    "invalidPasswd", null);
                        }

                    } else { // Send HOTP Code
                        sentHOTPCode = sendHOTPCode(callbacks);
                        return ISAuthConstants.LOGIN_START;
                    }
                } else {
                    setFailureID(userName);
                    throw new AuthLoginException(amAuthHOTP, "authFailed", null);
                }

            } else {
                setFailureID(userName);
                throw new AuthLoginException(amAuthHOTP, "authFailed", null);
            }
        } catch (NumberFormatException ex) {
            debug.error("HOTP.process() : " +  "NumberFormatException Exception", ex);
            if (userName != null && userName.length() != 0) {
                setFailureID(userName);
            }
            throw new AuthLoginException(amAuthHOTP, "authFailed", null, ex);

        } catch (SSOException e) {
            debug.error("HOTP.process() : " + "SSOException", e);
            throw new InvalidPasswordException("amAuth",
                    "invalidPasswd", null);
        }
    }

    public java.security.Principal getPrincipal() {
        if (userUUID != null) {
            userPrincipal = new HOTPPrincipal(userUUID);
            return userPrincipal;

        }
        if (userName != null) {
            userPrincipal = new HOTPPrincipal(userName);
            return userPrincipal;
        } else {
            return null;
        }

    }

    // cleanup state fields
    public void destroyModuleState() {
        nullifyUsedVars();
    }

    public void nullifyUsedVars() {
        bundle = null;
        userName = null;
        sc = null;
        sharedState = null;
        currentConfig = null;
        amAuthHOTP = null;
        sentHOTPCode = null;
        enteredHOTPCode = null;
    }

    private String sendHOTPCode(Callback[] callbacks)
            throws AuthLoginException {

        // The HOTP module can not be called alone. It has to be called after
        // one module that has set the userName.

        try {
            int codeDigits = 8;
            if (codeLength.equals("6")) {
                codeDigits = 6;
            }
            String code = HOTPAlgorithm.generateOTP(getSharedSecret(),
                    getMovingFactor(), codeDigits, false, 16);
            sentHOTPCodeTime = System.currentTimeMillis() / 1000;
            sendSMS(code);
            return code;
        } catch (NoSuchAlgorithmException e) {
            debug.error("HOTP.sendHOTPCode() : " + "no such algorithm", e);
            throw new AuthLoginException("amAuth", "noSuchAlgorithm", null);
        } catch (InvalidKeyException e) {
            debug.error("HOTP.sendHOTPCode() : " + "invalid key",e);
            throw new AuthLoginException("amAuth", "invalidKey", null);

        } catch (SSOException e) {
            debug.error("HOTP.sendHOTPCode() : " + "invlaid SSO token", e);
            throw new AuthLoginException("amAuth", "invalidSSOToken", null);
        }

    }

    private byte[] getSharedSecret() {
        String s = Long.toHexString(secureRandom.nextLong());
        return s.getBytes();
    }

    private int getMovingFactor() {
        return movingFactor++;
    }
    
    /**
     * Sends out the SMS message and E-mail with the HOTP code 
     * based on the HOTP module configurtion
     */
    private void sendSMS(String code)
            throws SSOException {
        AMIdentityRepository amIdRepo = getAMIdentityRepository(
                getRequestOrg());

        IdSearchControl idsc = new IdSearchControl();
        idsc.setRecursive(true);
        idsc.setTimeOut(0);
        idsc.setAllReturnAttributes(true);
        // search for the identity
        Set results = Collections.EMPTY_SET;
        try {
            idsc.setMaxResults(0);
            IdSearchResults searchResults =
                    amIdRepo.searchIdentities(IdType.USER, userName, idsc);
            if (searchResults != null) {
                results = searchResults.getSearchResults();
            }

            if (results == null || results.size() != 1) {
                throw new IdRepoException("HTOP:sendSMS : More than one " +
                        "user found");
            }

            Object[] ids = results.toArray();
            AMIdentity id = (AMIdentity) ids[0];
            Set telephoneNumbers = id.getAttribute("telephoneNumber");
            Set emails = id.getAttribute("mail");

            String phone = null;
            Iterator itor = null;
            if (telephoneNumbers != null && telephoneNumbers.size() != 0) {
                itor = telephoneNumbers.iterator();
                phone = (String) itor.next();
                if (debug.messageEnabled()) {
                    debug.message("HOTP.sendSMS() : " + "IdRepoException : phone number found "
                            + phone + " with username : " + userName);
                }
            } else {
                if (debug.messageEnabled()) {
                    debug.message("HOTP.sendSMS() : " + "IdRepoException : no phone number found " +
                            " with username : " + userName);
                }
            }

            String mail = null;
            if (emails != null && emails.size() != 0) {
                itor = emails.iterator();
                mail = (String) itor.next();
                if (debug.messageEnabled()) {
                    debug.message("HOTP.sendSMS() : " + "IdRepoException : email number found "
                            + mail + " with username : " + userName);
                }
            } else {
                if (debug.messageEnabled()) {
                    debug.message("HOTP.sendSMS() : " + "IdRepoException : no email found " +
                            " with username : " + userName);
                }
            }

            if (phone != null || mail != null) {
                String from = bundle.getString("messageFrom");
                String subject = bundle.getString("messageSubject");
                String message = bundle.getString("messageContent");
                SMSGateway gateway = (SMSGateway)
                        Class.forName(gatewaySMSImplClass).newInstance();
                if (codeDelivery.equals("SMS and E-mail")) {
                    gateway.sendSMSMessage(from, phone, subject, message, code, currentConfig);
                    gateway.sendEmail(from, mail, subject, message, code, currentConfig);
                } else if (codeDelivery.equals("SMS")) {
                    gateway.sendSMSMessage(from, phone, subject, message, code, currentConfig);
                } else if (codeDelivery.equals("E-mail")) {
                    gateway.sendEmail(from, mail, subject, message, code, currentConfig);
                }
            } else {
                if (debug.messageEnabled()) {
                    debug.message("HOTP.sendSMS() : " + "IdRepoException : no phone or email found " +
                            " with username : " + userName);
                }
            }

        } catch (ClassNotFoundException ee) {
            debug.error("HOTP.sendSMS() : " + "class not found " +
                        "SMSGateway class", ee);
        } catch (InstantiationException ie) {
            debug.error("HOTP.sendSMS() : " + "can not instantiate " +
                        "SMSGateway class", ie);
        } catch (IdRepoException e) {
            debug.error("HOTP.sendSMS() : " + "error searching " +
                        " Identities with username : " + userName, e);
        } catch (Exception e) {
            debug.error("HOTP.sendSMS() : " +  "HOTP module exception : ", e);
        }

    }
}
