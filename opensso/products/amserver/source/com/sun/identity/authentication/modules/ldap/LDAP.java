/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2005 Sun Microsystems Inc. All Rights Reserved
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
 * $Id: LDAP.java,v 1.17 2010/01/25 22:09:16 qcheng Exp $
 *
 */

/*
 * Portions Copyrighted 2010-2012 ForgeRock AS
 */

package com.sun.identity.authentication.modules.ldap;

import com.iplanet.am.util.SystemProperties;
import com.sun.identity.authentication.service.AuthD;
import com.sun.identity.authentication.spi.AMAuthCallBackImpl;
import com.sun.identity.authentication.spi.AMAuthCallBackException;
import com.sun.identity.authentication.spi.AMLoginModule;
import com.sun.identity.authentication.spi.AuthLoginException;
import com.sun.identity.authentication.spi.InvalidPasswordException;
import com.sun.identity.authentication.spi.UserNamePasswordValidationException;
import com.sun.identity.authentication.util.ISAuthConstants;
import com.sun.identity.common.GeneralTaskRunnable;
import com.sun.identity.common.SystemTimer;
import com.sun.identity.shared.Constants;
import com.sun.identity.shared.datastruct.CollectionHelper;
import com.sun.identity.shared.debug.Debug;
import com.sun.identity.sm.ServiceConfig;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;
import javax.net.ssl.SSLContext;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.ConfirmationCallback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import org.forgerock.opendj.ldap.Connection;
import org.forgerock.opendj.ldap.ErrorResultException;
import org.forgerock.opendj.ldap.LDAPConnectionFactory;
import org.forgerock.opendj.ldap.LDAPOptions;
import org.forgerock.opendj.ldap.ResultCode;
import org.forgerock.opendj.ldap.SSLContextBuilder;
import org.forgerock.opendj.ldap.SearchScope;
import org.forgerock.opendj.ldap.TrustManagers;

public class LDAP extends AMLoginModule {
    // static variables
    private static HashMap orgMap = new HashMap();
    private static final long DEFAULT_SERVER_CHECK_INTERVAL = 15;
    private static final String USER_CREATION_ATTR =
        "iplanet-am-ldap-user-creation-attr-list";
    private static final String INVALID_CHARS =
        "iplanet-am-auth-ldap-invalid-chars";
    private static final String PIPE_SEPARATOR="|";
    private static boolean ldapSSL = false;
    private boolean sslTrustAll = false;
    private boolean sslEnabled = false;
    
    // local variables
    ResourceBundle bundle = null;
    protected String validatedUserID;
    private String userName;
    private String userPassword;
    private int requiredPasswordLength = 0;
    private String regEx;
    private String currentConfigName;
    private String bindDN;
    private Iterator subConfigNamesIter = null;
    private ServiceConfig sc;
    private boolean firstTry = true;
    private int currentState;
    private boolean primary = true;
    private int previousScreen;
    protected LDAPAuthUtils ldapUtil;
    private static volatile FailbackManager fMgr;
    private boolean isReset;
    private int primaryServerPort;
    private String primaryServerHost;
    private boolean isProfileCreationEnabled;
    private boolean getCredentialsFromSharedState;

    private AMAuthCallBackImpl callbackImpl = null;
    
    private long interval = DEFAULT_SERVER_CHECK_INTERVAL;
    
    private Set userCreationAttrs = new HashSet();
    private HashMap userAttrMap = new HashMap();
    private Map sharedState;
    private String serverHost;
    private int serverPort;
    public Map currentConfig;
    
    protected Debug debug = null;
    protected String amAuthLDAP;
    protected Principal userPrincipal;
    
    static {
        // initializing the login parameters by getting the values from
        // ConfigProperties
        ldapSSL = Boolean.valueOf(SystemProperties.get(
        Constants.AM_DIRECTORY_SSL_ENABLED, "false")).booleanValue();
    }
    
    enum LoginScreen {
        LOGIN_START(1, "loginState"),
        PASSWORD_CHANGE(2, "passwordChange"),    
        PASSWORD_EXPIRED_SCREEN(3, "passwordExpired"),
        USER_INACTIVE(4, "userInactive"),
        ACCOUNT_LOCKED(5, "accountLocked");
        
        private static final Map<Integer,LoginScreen> lookup = 
                new HashMap<Integer,LoginScreen>();

        static {
            for(LoginScreen ls : EnumSet.allOf(LoginScreen.class)) {
                lookup.put(ls.intValue(), ls);
            }
        }
        
        private final int state;
        private final String name;

        private LoginScreen(final int state, final String name) {
            this.state = state;
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
        
        public static LoginScreen get(int screen) {
            return lookup.get(screen);
        }
        
        int intValue() {
            return state;
        }  
    }

    /**
     * TODO-JAVADOC
     */
    public LDAP() {
        amAuthLDAP = "amAuthLDAP";
        debug = Debug.getInstance(amAuthLDAP);
    }

    /**
     * TODO-JAVADOC
     */
    public void init(Subject subject, Map sharedState, Map options) {
        sc = (ServiceConfig) options.get("ServiceConfig");
        currentConfig = options;
        currentConfigName = 
            (String)options.get(ISAuthConstants.MODULE_INSTANCE_NAME);
        primary = getPrimaryFlag(currentConfigName);
        java.util.Locale locale = getLoginLocale();
        bundle = amCache.getResBundle(amAuthLDAP, locale);
        if (debug.messageEnabled()) {
            debug.message("LDAP resbundle locale=" + locale);
        }
        this.sharedState = sharedState;
        if (debug.messageEnabled()) {
            debug.message("Host: " + AuthD.directoryHostName +
            "\nPORT : " + AuthD.directoryPort);
        }
    }

    /**
     * TODO-JAVADOC
     */
    public boolean initializeLDAP() throws AuthLoginException {
        debug.message("LDAP initialize()");
        serverHost = null;
        
        try {
            if (currentConfig != null) {
                try {
                    String checkAttr = "iplanet-am-auth-ldap-server-check";
                    interval = Long.parseLong(CollectionHelper.getServerMapAttr(
                        currentConfig, checkAttr));
                } catch (NumberFormatException nfe) {
                    if (debug.messageEnabled()) {
                        debug.message("Server Check Interval is not set.\n"+
                        "Setting it to default value 15 min");
                    }
                    interval = DEFAULT_SERVER_CHECK_INTERVAL;
                }
                setInterval(interval);
                if (primary) {
                    serverHost = CollectionHelper.getServerMapAttr(
                        currentConfig, "iplanet-am-auth-ldap-server");
                    if (serverHost == null) {
                        if (debug.messageEnabled()) {
                            debug.message("No primary server for confing " +
                            currentConfigName);
                        }
                        return false;
                    }
                } else {
                    serverHost = CollectionHelper.getServerMapAttr(
                        currentConfig, "iplanet-am-auth-ldap-server2");
                    
                    if (serverHost == null) {
                        if (debug.messageEnabled()) {
                            debug.message("No secondary server for confing " +
                            currentConfigName);
                        }
                        return false;
                    }
                }
                
                String baseDN = CollectionHelper.getServerMapAttr(
                    currentConfig, "iplanet-am-auth-ldap-base-dn");
                if (baseDN == null) {
                    debug.error("BaseDN for search is invalid: " + baseDN);
                }
                
                String pLen = CollectionHelper.getMapAttr(currentConfig,
                    "iplanet-am-auth-ldap-min-password-length"); 
                if (pLen != null) {
                    try {
                        requiredPasswordLength = Integer.parseInt(pLen);
                    } catch (NumberFormatException ex) {
                        debug.error("LDAP.initializeLDAP : " + pLen, ex);
                    }
                } 
                bindDN = CollectionHelper.getMapAttr(currentConfig,
                    "iplanet-am-auth-ldap-bind-dn", "");
                String bindPassword = CollectionHelper.getMapAttr(
                    currentConfig, "iplanet-am-auth-ldap-bind-passwd", "");
                String userNamingAttr = CollectionHelper.getMapAttr(
                    currentConfig,
                    "iplanet-am-auth-ldap-user-naming-attribute", "uid");
                Set userSearchAttrs = (Set)currentConfig.get(
                    "iplanet-am-auth-ldap-user-search-attributes");
                String searchFilter = CollectionHelper.getMapAttr(
                    currentConfig, "iplanet-am-auth-ldap-search-filter", "");
                sslEnabled = Boolean.valueOf(CollectionHelper.getMapAttr(
                    currentConfig, "iplanet-am-auth-ldap-ssl-enabled", "false")
                    ).booleanValue();
                getUserCreationAttrs(currentConfig);
                String tmp = CollectionHelper.getMapAttr(currentConfig,
                    "iplanet-am-auth-ldap-search-scope", "SUBTREE");
                
                String authLevel = CollectionHelper.getMapAttr(currentConfig,
                    "iplanet-am-auth-ldap-auth-level");
                if (authLevel != null) {
                    try {
                        setAuthLevel(Integer.parseInt(authLevel));
                    } catch (Exception e) {
                        debug.error("Unable to set auth level " + authLevel);
                    }
                }
                
                SearchScope searchScope = SearchScope.WHOLE_SUBTREE;
                
                if (tmp.equalsIgnoreCase("OBJECT")) {
                    searchScope = SearchScope.BASE_OBJECT;
                } else if (tmp.equalsIgnoreCase("ONELEVEL")) {
                    searchScope = SearchScope.SINGLE_LEVEL;
                }
                
                String returnUserDN = CollectionHelper.getMapAttr(
                    currentConfig, ISAuthConstants.LDAP_RETURNUSERDN, "true");
                regEx = CollectionHelper.getMapAttr(
                    currentConfig, INVALID_CHARS);
                
                // set LDAP Parameters
                int index = serverHost.indexOf(':');
                serverPort = 389;
                
                if (index != -1) {
                    serverPort = Integer.parseInt(
                    serverHost.substring(index + 1));
                    serverHost = serverHost.substring(0, index);
                }
                if (!primary) {
                    primaryServerHost = CollectionHelper.getServerMapAttr(
                        currentConfig, "iplanet-am-auth-ldap-server");
                    primaryServerPort = 389;
                    int colonIndex = primaryServerHost.indexOf(':');
                    if (colonIndex != -1) {
                        primaryServerPort = Integer.parseInt(
                        primaryServerHost.substring(colonIndex + 1));
                        primaryServerHost = primaryServerHost.substring(0,
                            colonIndex);
                    }
                    if (LDAPAuthUtils.connectionPoolsStatus != null) {
                        String poolKey = primaryServerHost + ":" +
                            primaryServerPort + ":" + bindDN;
                        LDAPAuthUtils.ServerStatus adminPoolStatus = 
                                LDAPAuthUtils.connectionPoolsStatus.get(poolKey);
                        if ( (adminPoolStatus == null) ||
                            (adminPoolStatus.equals(LDAPAuthUtils.ServerStatus.STATUS_UP))) {
                             setPrimaryFlag(currentConfigName, true);
                             primary = true;
                             serverHost = primaryServerHost;
                             serverPort = primaryServerPort;
                        }
                    }
                }
                
                boolean beheraEnabled = Boolean.valueOf(CollectionHelper.getMapAttr(
                    currentConfig, "iplanet-am-auth-ldap-behera-password-policy-enabled", "false")
                    ).booleanValue();
                
                sslTrustAll = Boolean.valueOf(CollectionHelper.getMapAttr(
                    currentConfig, "iplanet-am-auth-ldap-ssl-trust-all", "false")
                    ).booleanValue();
                
                isProfileCreationEnabled = isDynamicProfileCreationEnabled();
                // set the optional attributes here
                ldapUtil = new LDAPAuthUtils(serverHost, serverPort, sslEnabled, 
                    bundle, baseDN, debug);
                ldapUtil.setScope(searchScope);
                ldapUtil.setFilter(searchFilter);
                ldapUtil.setUserNamingAttribute(userNamingAttr);
                ldapUtil.setUserSearchAttribute(userSearchAttrs);
                ldapUtil.setAuthPassword(bindPassword);
                ldapUtil.setAuthDN(bindDN);
                ldapUtil.setReturnUserDN(returnUserDN);
                ldapUtil.setUserAttributes(userCreationAttrs);
                ldapUtil.setTrustAll(sslTrustAll);
                ldapUtil.setDynamicProfileCreationEnabled(
                    isProfileCreationEnabled);
                ldapUtil.setBeheraEnabled(beheraEnabled);
                
                if (debug.messageEnabled()) {
                    debug.message("bindDN-> " + bindDN +
                    "\nrequiredPasswordLength-> " + requiredPasswordLength +
                    "\nbaseDN-> " + baseDN +
                    "\nuserNamingAttr-> " + userNamingAttr +
                    "\nuserSearchAttr(s)-> " + userSearchAttrs +
                    "\nuserCreationAttrs-> "+userCreationAttrs+
                    "\nsearchFilter-> " + searchFilter +
                    "\nsearchScope-> " + searchScope +
                    "\nssl-> " + sslEnabled +
                    "\ntrustAll-> " + sslTrustAll +        
                    "\nauthLevel: " + authLevel + 
                    "\nbeheraEnabled->" + beheraEnabled +
                    "\nHost: " + serverHost +
                    "\nPORT : " + serverPort +
                    "\nPattern : " + regEx);
                }
                return true;
            }
        } catch (Exception ex) {
            debug.error("Init Exception", ex);
            throw new AuthLoginException(amAuthLDAP, "LDAPex", null, ex);
        }
        return false;
    }
    
    private boolean getPrimaryFlag( String configName) {
        synchronized (orgMap) {
            String reqOrg = getRequestOrg();
            Map flags = (Map) orgMap.get(reqOrg);
            if (flags == null) {
                flags = new HashMap();
                flags.put(configName, "true");
                orgMap.put(reqOrg, flags);
                return true;
            }
            
            String flag = (String) flags.get(configName);
            if (flag == null) {
                flags.put(configName, "true");
                return true;
            }
            
            return (flag.equals("true"));
        }
    }
    
    private void setPrimaryFlag( String configName, boolean flag) {
        synchronized (orgMap) {
            String reqOrg = getRequestOrg();
            Map flags = (Map) orgMap.get(reqOrg);
            if (flags == null) {
                flags = new HashMap();
                orgMap.put(reqOrg, flags);
            }
            
            if (flag) {
                flags.put(configName, "true");
            } else {
                flags.put(configName, "false");
            }
        }
    }
    
    private boolean getSubConfig() {
        firstTry = true;
        try {
            if (subConfigNamesIter == null) {
                if (sc != null) {
                    Set subConfigNames = sc.getSubConfigNames();
                    if (subConfigNames == null || subConfigNames.isEmpty()) {
                        return false;
                    }
                    subConfigNamesIter = subConfigNames.iterator();
                }
            }
            
            if (subConfigNamesIter != null) {
                while (subConfigNamesIter.hasNext()) {
                    String subConfigName = (String) subConfigNamesIter.next();
                    ServiceConfig ssc = sc.getSubConfig(subConfigName);
                    if (ssc != null) {
                        if (debug.messageEnabled()) {
                            debug.message("LDAP.getSubConfig subConfigName = " +
                            subConfigName);
                        }
                        currentConfig = ssc.getAttributes();
                        currentConfigName = subConfigName;
                        primary = getPrimaryFlag(subConfigName);
                        return true;
                    }
                }
            }
        } catch (Exception ex) {
            if (debug.warningEnabled()) {
                debug.warning("LDAP.getSubConfig unable to get sub config", ex);
            }
        }
        return false;
    }
    
    public int process(Callback[] callbacks, int state)
            throws AuthLoginException {
        currentState = state;
        ModuleState newState;
        LoginScreen loginScreen = LoginScreen.get(state);
        
        try {
            
            if (loginScreen.equals(LoginScreen.LOGIN_START)) {
                if (callbacks == null || callbacks.length == 0) {
                    userName = (String) sharedState.get(getUserKey());
                    userPassword = (String) sharedState.get(getPwdKey());
                    
                    if (userName == null || userPassword == null) {
                        return LoginScreen.LOGIN_START.intValue();
                    }
                    
                    getCredentialsFromSharedState = true;
                } else {
                    //callbacks is not null
                    userName = ( (NameCallback) callbacks[0]).getName();
                    userPassword = charToString(((PasswordCallback)
                    callbacks[1]).getPassword(), callbacks[1]);
                }
                
                if (userPassword == null || userPassword.length() == 0) {
                    if (debug.messageEnabled()) {
                        debug.message("LDAP.process: Password is null/empty");
                    } 
                    
                    throw new InvalidPasswordException("amAuth",
                            "invalidPasswd", null); 
                }
                
                //store username password both in success and failure case
                storeUsernamePasswd(userName, userPassword);
                
                /*
                 * checks whether the superadmin and the username supplied
                 * is same . This check is to allow Super Admin to log in
                 * even though ldap parameters are messed up due to
                 * misconfiguration
                 */
                if (isSuperAdmin(userName)) {
                    ldapUtil = new LDAPAuthUtils(AuthD.directoryHostName,
                    AuthD.directoryPort, ldapSSL, bundle, debug);
                    ldapUtil.authenticateSuperAdmin(userName, userPassword);
                    
                    if (ldapUtil.getState().equals(ModuleState.SUCCESS)) {
                        validatedUserID = userName;
                        return ISAuthConstants.LOGIN_SUCCEED;
                    } else {
                        if (debug.messageEnabled()) {
                            debug.message("Invalid adminID or admin Password");
                        }
                        
                        setFailureID(ldapUtil.getUserId(userName));
                        throw new AuthLoginException(
                            amAuthLDAP, "InvalidUP", null);
                    }
                } else {
                    if (initializeLDAP()) {
                        //validate username
                        validateUserName(userName, regEx);
                        ldapUtil.authenticateUser(userName, userPassword);
                        newState = ldapUtil.getState();
                    } else {
                        newState = ModuleState.SERVER_DOWN;
                    }
                    
                    boolean passwordValidationSuccessFlag = true;
                    // Validating Password only if authentication
                    // information entered is correct
                    if (newState == ModuleState.SUCCESS) {
                        try {
                            validatePassword(userPassword);
                        } catch (UserNamePasswordValidationException upve) {
                            if (debug.messageEnabled()) {
                                debug.message("Password does not satisfy " +
                                              "password policy rules specified"
                                              + " in OpenAM");
                            }
                            isReset = true;
                            String invalidMsg = bundle.getString(
                                                   "PasswordInvalid");
                            replaceHeader(LoginScreen.PASSWORD_CHANGE.intValue(), invalidMsg);
                            currentState = LoginScreen.PASSWORD_CHANGE.intValue();
                            passwordValidationSuccessFlag = false;
                        }
                    }
                    
                    if (passwordValidationSuccessFlag) {
                        processLoginScreen(newState);
                    }
                    
                    return currentState;
                }
            } else if (loginScreen.equals(LoginScreen.PASSWORD_CHANGE)) {
                if (debug.messageEnabled()) {
                    debug.message("you are in Password Screen:" + currentState);
                }
                // callbacks[3] is a user selected button index
                // PwdAction == 0 is a Submit button
                int pwdAction =
                    ((ConfirmationCallback)callbacks[3]).getSelectedIndex();
                if (pwdAction == 0) {
                    String oldPassword = charToString(((PasswordCallback)
                    callbacks[0]).getPassword(), callbacks[0]);
                    String newPassword = charToString(((PasswordCallback)
                    callbacks[1]).getPassword(), callbacks[1]);
                    String confirmPassword = charToString(((PasswordCallback)
                    callbacks[2]).getPassword(), callbacks[2]);
                    
                    try {
                        validatePassword(newPassword);
                        // check minimal password length requirement
                        int newPasswordLength = 0;
                        if (newPassword != null) {
                            newPasswordLength = newPassword.length();
                        }
                        if (newPasswordLength < requiredPasswordLength) {
                            if (debug.messageEnabled()) {
                                debug.message("LDAP.process: new password less"
                                    + " than the minimal length of " 
                                    + requiredPasswordLength);
                            }
                            newState = ModuleState.PASSWORD_MIN_CHARACTERS;
                            // add log
                            getLoginState("LDAP").logFailed(newState.name(),
                                "CHANGE_USER_PASSWORD_FAILED", false, null);
                        } else {
                            ldapUtil.changePassword(oldPassword, newPassword,
                                confirmPassword);
                            newState = ldapUtil.getState();
                            
                            if (newState == 
                                ModuleState.PASSWORD_UPDATED_SUCCESSFULLY){
                                // log change password success
                                getLoginState("LDAP").logSuccess(
                                    "changePasswdSucceeded",
                                    "CHANGE_USER_PASSWORD_SUCCEEDED");
                            } else {
                                // add log
                                getLoginState("LDAP").logFailed(newState.name(),
                                    "CHANGE_USER_PASSWORD_FAILED", false, null);
                            }
                        }
                        processPasswordScreen(newState);
                        
                        if (debug.messageEnabled()) {
                            debug.message("Password change state :" + newState);
                        }
                    } catch(UserNamePasswordValidationException upve) {
                        if (debug.messageEnabled()) {
                            debug.message("Password could not be validated, " +
                                          "need a different password");
                        }
                        String invalidMsg = bundle.getString(
                                                "NewPasswordInvalid");
                        replaceHeader(LoginScreen.PASSWORD_CHANGE.intValue(), invalidMsg);
                        currentState = LoginScreen.PASSWORD_CHANGE.intValue();                    	
                    }
                    
                    return currentState;
                } else  {
                    if (isReset) {
                        isReset = false;
                        return LoginScreen.LOGIN_START.intValue();
                    }
                    validatedUserID = ldapUtil.getUserId();
                    return ISAuthConstants.LOGIN_SUCCEED;
                }
            } else {
                setFailureID(ldapUtil.getUserId(userName));
                throw new AuthLoginException(amAuthLDAP, "LDAPex", null);
            }
        } catch (LDAPUtilException ex) {
            if (getCredentialsFromSharedState && !isUseFirstPassEnabled()) {
                getCredentialsFromSharedState = false;
                return LoginScreen.LOGIN_START.intValue();
            }
            setFailureID((ldapUtil != null) ? 
                ldapUtil.getUserId(userName) : userName);
            
            if (ex.getResultCode().equals(ResultCode.NO_SUCH_OBJECT)) {
                if (debug.messageEnabled()) {
                    debug.message("The specified user does not exist.");
                }
                    
                throw new AuthLoginException(amAuthLDAP, "NoUser", null);
            } else if (ex.getResultCode().equals(ResultCode.INVALID_CREDENTIALS)) {
                if (debug.messageEnabled()) {
                    debug.message("Invalid password.");
                }
                
                String failureUserID = ldapUtil.getUserId();
                throw new InvalidPasswordException(amAuthLDAP, "InvalidUP",
                    null, failureUserID, null);
            } else if (ex.getResultCode().equals(ResultCode.UNWILLING_TO_PERFORM)) {
                if (debug.messageEnabled()) {
                    debug.message("Unwilling to perform. Account inactivated.");
                }
                
                currentState = LoginScreen.USER_INACTIVE.intValue();
                return currentState;
            } else if (ex.getResultCode().equals(ResultCode.INAPPROPRIATE_AUTHENTICATION)) {
                if (debug.messageEnabled()) {
                    debug.message("Inappropriate authentication.");
                }
                    
                throw new AuthLoginException(amAuthLDAP, "InappAuth", null);
            } else if (ex.getResultCode().equals(ResultCode.CONSTRAINT_VIOLATION)) {
                if (debug.messageEnabled()) {
                    debug.message("Exceed password retry limit.");
                }
                
                throw new AuthLoginException(amAuthLDAP,
                        ISAuthConstants.EXCEED_RETRY_LIMIT, null);
            } else {
                throw new AuthLoginException(amAuthLDAP, "LDAPex", null);
            }
        } catch (UserNamePasswordValidationException upve) {
            // Note: Do not set failure Id for this exception
            if (debug.messageEnabled()) {
                debug.message("Invalid Characters detected");
            }
            
            throw new AuthLoginException(upve);
        }
    }
   
    /**
     * Returns principal.
     *
     * @return principal.
     */
    public Principal getPrincipal() {
        if (userPrincipal != null) {
            return userPrincipal;
        } else if (validatedUserID != null) {
            userPrincipal = new LDAPPrincipal(validatedUserID);
            return userPrincipal;
        } else {
            return null;
        }
    }
    
    /**
     * Cleans up state fields.
     */
    public void destroyModuleState() {
        validatedUserID = null;
        userPrincipal = null;
    }

    /**
     * TODO-JAVADOC
     */
    public void nullifyUsedVars() {
        bundle = null;
        userName = null ;
        userPassword = null;
        regEx = null;
        subConfigNamesIter = null;
        sc = null;
        userCreationAttrs = null;
        userAttrMap = null;
        sharedState = null;
        currentConfig = null;
        
        amAuthLDAP = null;
    }
    
    private void processLoginScreen(ModuleState newState) throws AuthLoginException {
        try {
            switch (newState) {
                case SUCCESS:
                    validatedUserID = ldapUtil.getUserId();
                    createProfile();
                    currentState = ISAuthConstants.LOGIN_SUCCEED;
                    setForceCallbacksRead(false);
                    break;
                case PASSWORD_EXPIRING:
                {
                    String fmtMsg = bundle.getString("PasswordExp");
                    String msg = com.sun.identity.shared.locale.Locale.
                        formatMessage(fmtMsg, ldapUtil.getExpTime());
                    /**
                     * In case of sharedstate if the chain breaks in ldap
                     * because of abnormal condition like pwd expiring
                     * then the callbacks has to be read fresh so that new
                     * screen appears for the user.
                     */
                    setForceCallbacksRead(true);
                    forceCallbacksInit();
                    replaceHeader(LoginScreen.PASSWORD_CHANGE.intValue(), msg);
                }
                    currentState = LoginScreen.PASSWORD_CHANGE.intValue();
                    break;
                case PASSWORD_RESET_STATE:
                case CHANGE_AFTER_RESET:
                    isReset = true;
                    String resetMsg = bundle.getString("PasswordReset");
                    /**
                     * In case of sharedstate if the chain breaks in ldap
                     * because of abnormal condition like pwd reset
                     * then the callbacks has to be read fresh so that new
                     * screen appears for the user.
                     */
                    setForceCallbacksRead(true);
                    forceCallbacksInit();
                    replaceHeader(LoginScreen.PASSWORD_CHANGE.intValue(), resetMsg);
                    currentState = LoginScreen.PASSWORD_CHANGE.intValue();
                    break;
                case PASSWORD_EXPIRED_STATE:
                    currentState = LoginScreen.PASSWORD_EXPIRED_SCREEN.intValue();
                    break;
                case ACCOUNT_LOCKED:
                    currentState = LoginScreen.ACCOUNT_LOCKED.intValue();
                    break;
                case GRACE_LOGINS:
                {
                    String fmtMsg = bundle.getString("GraceLogins");
                    String msg = com.sun.identity.shared.locale.Locale.
                        formatMessage(fmtMsg, ldapUtil.getGraceLogins());

                    setForceCallbacksRead(true);
                    forceCallbacksInit();
                    if (ldapUtil.getGraceLogins() == 1) {
                        Callback[] callback = getCallback(LoginScreen.PASSWORD_CHANGE.intValue());
                        for (int i = 0; i < callback.length; i++) {
                            Callback cbk = callback[i];
                            if (cbk instanceof ConfirmationCallback) {
                                ConfirmationCallback confirm = (ConfirmationCallback) cbk;
                                String[] options = confirm.getOptions();
                                String[] newOptions = new String[1];
                                System.arraycopy(options, 0, newOptions, 0, 1);
                                ConfirmationCallback newConfirm =
                                        new ConfirmationCallback(confirm.getMessageType(), newOptions, confirm.getDefaultOption());
                                replaceCallback(LoginScreen.PASSWORD_CHANGE.intValue(), i, newConfirm);
                            }
                        }
                    }

                    replaceHeader(LoginScreen.PASSWORD_CHANGE.intValue(), msg);
                }
                    currentState = LoginScreen.PASSWORD_CHANGE.intValue();
                    break;
                case TIME_BEFORE_EXPIRATION:
                {
                    String fmtMsg = bundle.getString("TimeBeforeExpiration");
                    String msg = com.sun.identity.shared.locale.Locale.
                        formatMessage(fmtMsg, ldapUtil.getExpTime());

                    setForceCallbacksRead(true);
                    forceCallbacksInit();
                    replaceHeader(LoginScreen.PASSWORD_CHANGE.intValue(), msg);
                }                    
                    currentState = LoginScreen.PASSWORD_CHANGE.intValue();
                case USER_NOT_FOUND:
                    if (!getSubConfig()) {
                        throw new LDAPUtilException("noUserMatchFound",
                        (Object[])null);
                    }
                    if (initializeLDAP()) {
                        ldapUtil.authenticateUser(userName, userPassword);
                        newState = ldapUtil.getState();
                    } else {
                        newState = ModuleState.SERVER_DOWN;
                    }
                    processLoginScreen(newState);
                    break;
                case SERVER_DOWN:
                    if (firstTry) {
                        String key = serverHost + ":" + serverPort + ":" + bindDN;
                        synchronized(LDAPAuthUtils.connectionPoolsStatus) {
                            LDAPAuthUtils.connectionPoolsStatus.put(key,
                                LDAPAuthUtils.ServerStatus.STATUS_DOWN);
                        }
                        synchronized(LDAPAuthUtils.connectionPools) {
                            LDAPAuthUtils.connectionPools.remove(key);
                        }
                        synchronized(LDAPAuthUtils.adminConnectionPools) {
                            LDAPAuthUtils.adminConnectionPools.remove(key);
                        }
                        firstTry = false;
                        primary = !primary;
                        setPrimaryFlag(currentConfigName, primary);
                        if ((fMgr == null) ||
                            (fMgr.scheduledExecutionTime() == -1)) {
                            fMgr = new FailbackManager();
                            SystemTimer.getTimer().schedule(fMgr, new Date(((
                                System.currentTimeMillis()) / 1000) * 1000));
                        } else {
                            if (interval < fMgr.runPeriod) {
                                fMgr.runPeriod = interval;
                            }
                        }
                        if (initializeLDAP()) {
                            ldapUtil.authenticateUser(userName, userPassword);
                            newState = ldapUtil.getState();
                            processLoginScreen(newState);
                            break;
                        }
                    }
                    
                    if (!getSubConfig()) {
                        throw new AuthLoginException(
                            amAuthLDAP, "LDAPex", null);
                    }
                    if (initializeLDAP()) {
                        ldapUtil.authenticateUser(userName, userPassword);
                        newState = ldapUtil.getState();
                    } else {
                        newState = ModuleState.SERVER_DOWN;
                    }
                    processLoginScreen(newState);
                    break;
                default:
            }
        } catch (LDAPUtilException ex) {
            if (getCredentialsFromSharedState && !isUseFirstPassEnabled()) {
                getCredentialsFromSharedState = false;
                currentState = LoginScreen.LOGIN_START.intValue();
                return;
            }
            if (newState != ModuleState.USER_NOT_FOUND) {
                debug.error("Unknown Login State:", ex);
            }
            throw new AuthLoginException(amAuthLDAP, "LDAPex", null, ex);
        }
    }
    
    private void processPasswordScreen(ModuleState newState)
            throws AuthLoginException {
        switch (newState) {
            case PASSWORD_UPDATED_SUCCESSFULLY:
                validatedUserID = ldapUtil.getUserId();
                createProfile();
                currentState = ISAuthConstants.LOGIN_SUCCEED;
                // Instantiating the callback implementation variable. This
                // will be used to notify the plug-in classes that a
                // successful password change was performed.
                try {
                    callbackImpl =
                        AMAuthCallBackImpl.getInstance(this.getRequestOrg());
                    // We need the current system time since this is required
                    // as part of the callback method parameter.
                    Long now = new Long(System.currentTimeMillis());
                    // We now notify the plug-in that a successful
                    // password change was performed.
                    callbackImpl.processedPasswordChange(now, validatedUserID);
                } catch (AMAuthCallBackException acbe) {
                    if (debug.errorEnabled()) {
                        debug.error("process : unable to get " +
                        "AMAuthCallBackImpl instance or callback module " +
                        "raised an exception.", acbe);
                    }
                }
                break;
            case PASSWORD_NOT_UPDATE:
                replaceHeader(LoginScreen.PASSWORD_CHANGE.intValue(), bundle.getString("PInvalid"));
                currentState = LoginScreen.PASSWORD_CHANGE.intValue();
                break;
            case PASSWORD_MISMATCH:
                replaceHeader(LoginScreen.PASSWORD_CHANGE.intValue(), bundle.getString("PasswdMismatch"));
                currentState = LoginScreen.PASSWORD_CHANGE.intValue();
                break;
            case WRONG_PASSWORD_ENTERED:
                replaceHeader(LoginScreen.PASSWORD_CHANGE.intValue(), bundle.getString("PasswdSame"));
                currentState = LoginScreen.PASSWORD_CHANGE.intValue();
                break;                
            case PASSWORD_MIN_CHARACTERS:
                replaceHeader(LoginScreen.PASSWORD_CHANGE.intValue(), bundle.getString("PasswdMinChars"));
                currentState = LoginScreen.PASSWORD_CHANGE.intValue();
                break;
            case USER_PASSWORD_SAME:
                replaceHeader(LoginScreen.PASSWORD_CHANGE.intValue(), bundle.getString("UPsame"));
                currentState = LoginScreen.PASSWORD_CHANGE.intValue();
                break;
            case INSUFFICIENT_PASSWORD_QUALITY:
                replaceHeader(LoginScreen.PASSWORD_CHANGE.intValue(), bundle.getString("inPwdQual"));
                currentState = LoginScreen.PASSWORD_CHANGE.intValue();
                break;
            case PASSWORD_IN_HISTORY:
                replaceHeader(LoginScreen.PASSWORD_CHANGE.intValue(), bundle.getString("pwdInHist"));
                currentState = LoginScreen.PASSWORD_CHANGE.intValue();
                break;            
            case PASSWORD_TOO_SHORT:
                replaceHeader(LoginScreen.PASSWORD_CHANGE.intValue(), bundle.getString("pwdToShort"));
                currentState = LoginScreen.PASSWORD_CHANGE.intValue();
                break;
            case PASSWORD_TOO_YOUNG:
                replaceHeader(LoginScreen.PASSWORD_CHANGE.intValue(), bundle.getString("pwdToYoung"));
                currentState = LoginScreen.PASSWORD_CHANGE.intValue();
                break;                
            default:
                
        }
    }

    private void createProfile() {
        if (isProfileCreationEnabled && userCreationAttrs.size() > 0) {
            Map userAttributeValues = ldapUtil.getUserAttributeValues();
            
            if (debug.messageEnabled()) {
                debug.message("user creation attributes: " +
                        userAttributeValues);
            }
            
            Map userValues= getAttributeMap(userAttributeValues);
            setUserAttributes(userValues);
        }
    }
    
    private String charToString(char[] tmpPassword, Callback cbk) {
        if (tmpPassword == null) {
            // treat a NULL password as an empty password
            tmpPassword = new char[0];
        }
        char[] pwd = new char[tmpPassword.length];
        System.arraycopy(tmpPassword, 0, pwd, 0, tmpPassword.length);
        ((PasswordCallback) cbk).clearPassword();
        return new String(pwd);
    }
    
    // reset replace text method should be provided by AMLoginModule
    // untill then this method cannot be used for multiple replacement
    private void setReplaceText(int state, int index, String msg)
            throws AuthLoginException {
        // set #REPLACE# in password callback
        if (debug.messageEnabled()) {
            debug.message("Entered in setReplaceText");
        }
        
        Callback[] callbacks2 = getCallback(LoginScreen.PASSWORD_CHANGE.intValue());
        // reset first callback at PASSWORD_CHANGE state
        resetCallback(state, 0);
        String origMsg = ( (PasswordCallback) callbacks2[0]).getPrompt();
        int idx = origMsg.indexOf("#REPLACE#");
        String setMsg = msg + origMsg.substring(idx + 9);
        replaceCallback(state, 0, new PasswordCallback(setMsg,
            ((PasswordCallback) callbacks2[0]).isEchoOn()));
        
        if (debug.messageEnabled()) {
            debug.message("origmessage:" + origMsg + ":::+setMsg" + setMsg);
        }
    }
    
    
    // sets the time interval to check whether primary is up
    private void setInterval(long iVal) {
        interval = (iVal*60000);
    }
    
    // This class checks in regular intervals whether the connection is open
    // if connection is open it sets primary to true.
    class FailbackManager extends GeneralTaskRunnable {
        public long runPeriod = interval;
        
        public FailbackManager() {
            super();
        }
        
        public long getRunPeriod() {
            return runPeriod;
        }
        
        public boolean isEmpty() {
            return true;
        }
        
        public boolean addElement(Object obj) {
            return false;
        }
        
        public boolean removeElement(Object obj) {
            return false;
        }
        
        public void run() {
            boolean foundDown = true;
            
            try {
                foundDown = false;

                for (Map.Entry<String, LDAPAuthUtils.ServerStatus> server : 
                        LDAPAuthUtils.connectionPoolsStatus.entrySet()) {
                    String key = server.getKey();
                    LDAPAuthUtils.ServerStatus status  = server.getValue();;
                    if (status.equals(LDAPAuthUtils.ServerStatus.STATUS_DOWN)) {
                        foundDown = true;
                        
                        if (debug.messageEnabled()) {
                            debug.message("Checking for server " + key);
                        }
                        
                        StringTokenizer st = new StringTokenizer(key,":");
                        String downHost = (String)st.nextToken();
                        String downPort = (String)st.nextToken();
                        
                        if ((downHost != null) && (downHost.length() != 0)
                            && (downPort != null) && (downPort.length() != 0)) {
                            int intPort = (Integer.valueOf(downPort)).
                                intValue();
                            Connection conn = null;
                            LDAPOptions options = new LDAPOptions();
                            LDAPConnectionFactory factory = null;

                            try {
                                if (ldapSSL) {
                                    SSLContextBuilder builder = new SSLContextBuilder();

                                    if (sslTrustAll) {
                                        builder.setTrustManager(TrustManagers.trustAll());
                                    }

                                    SSLContext sslContext = builder.getSSLContext();
                                    options.setSSLContext(sslContext);
                                }

                                factory = new LDAPConnectionFactory(downHost, intPort, options);
                                conn = factory.getConnection();
                                
                                if (conn.isValid()) {
                                    LDAPAuthUtils.connectionPoolsStatus.
                                        put(key,LDAPAuthUtils.ServerStatus.STATUS_UP);  
                                    
                                    if (debug.messageEnabled()) {
                                        debug.message("Server is running:" + key);
                                    }
                                }
                            } catch (ErrorResultException ere) {
                                if (debug.messageEnabled()) {
                                    debug.message("Server is not running: " + key);
                                }
                            } catch (InterruptedException ie) {
                                if (debug.messageEnabled()) {
                                    debug.message("Server is not running: " + key);
                                }
                            } catch (GeneralSecurityException gse) {
                                if (debug.messageEnabled()) {
                                    debug.message("Server is not running: " + key);
                                }            
                            } finally {
                                if (conn != null) {
                                    conn.close();
                                }
                            }
                        }
                    } 
                }
            } catch (Exception exp) {
                debug.error("Error in Fallback Manager Thread", exp);
            }
            
            if (!foundDown) {
                runPeriod = -1;
            }
        }
    }
    
    /**
     * Retrieves the user creation attribute list from the
     * ldap configuration. The format of each line in the attribute
     * list is localAttribute:externalAttribute , this indicates the
     * the mapping of the local attribute in local iDS to the external
     * attribute in remote iDS.
     * This method parses each line in the list to separate the local
     * attribute and external attribute and creates a set of
     * external attributes and a Map with key as the internal
     * attribute and value the external attribute.
     */
    private void getUserCreationAttrs(Map currentConfig) {
        Set attrs = (Set)currentConfig.get(USER_CREATION_ATTR);
        
        if (debug.messageEnabled()) {
            debug.message("attrs is : " + attrs);
        }
        
        if ((attrs != null) && (!attrs.isEmpty())) {
            Iterator attrIterator = attrs.iterator();
            
            while (attrIterator.hasNext()) {
                String userAttr = (String) attrIterator.next();
                int i = userAttr.indexOf(PIPE_SEPARATOR);
                
                if (i != -1) {
                    String localAttr =  userAttr.substring(0,i);
                    String extAttr = userAttr.substring(i+1,userAttr.length());
                    
                    if ( (extAttr == null)  || (extAttr.length() == 0)) {
                        userCreationAttrs.add(localAttr);
                        userAttrMap.put(localAttr,localAttr);
                    } else {
                        userCreationAttrs.add(extAttr);
                        userAttrMap.put(localAttr,extAttr);
                    }
                } else {
                    userCreationAttrs.add(userAttr);
                    userAttrMap.put(userAttr,userAttr);
                }
            }
        }
        
        return;
    }
    
    /**
     * this method retrieves the key which is the external
     * attribute from the attributeValues and maps it
     * to the local attribute. A new map with localAttribute
     * as the key and value is the value of the attribute in
     * external iDS is created.
     */
    private Map getAttributeMap(Map attributeValues) {
        if (debug.messageEnabled()) {
            debug.message("In getAttribute Map: " + attributeValues);
        }
        
        Map newAttrMap = new HashMap();
        Iterator userIterator = userAttrMap.keySet().iterator();
        
        while (userIterator.hasNext()) {
            String key = (String) userIterator.next();
            String value = (String) userAttrMap.get(key);
            Set newValue = (Set) attributeValues.get(value);
            
            if (debug.messageEnabled()) {
                debug.message("key is : " + key);
                debug.message("value is : " + newValue);
            }
            
            if (newValue != null) {
                newAttrMap.put(key,newValue);
            }
        }
        
        if (debug.messageEnabled()) {
            debug.message("New attr map is : " + newAttrMap);
        }
        
        return newAttrMap;
    }
}
