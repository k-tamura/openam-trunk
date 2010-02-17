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
 * $Id: Membership.java,v 1.7 2009/11/18 20:50:24 qcheng Exp $
 *
 */


package com.sun.identity.authentication.modules.membership;

import com.sun.identity.shared.debug.Debug;
import com.sun.identity.shared.datastruct.CollectionHelper;
import com.iplanet.sso.SSOException;
import com.sun.identity.authentication.spi.AMLoginModule;
import com.sun.identity.authentication.spi.AuthLoginException;
import com.sun.identity.authentication.spi.InvalidPasswordException;
import com.sun.identity.authentication.util.ISAuthConstants;
import com.sun.identity.idm.AMIdentityRepository;
import com.sun.identity.idm.IdRepoException;
import com.sun.identity.idm.IdSearchControl;
import com.sun.identity.idm.IdSearchResults;
import com.sun.identity.idm.IdType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.ChoiceCallback;
import javax.security.auth.callback.ConfirmationCallback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;

public class Membership extends AMLoginModule {
    private static Debug debug;
    private ResourceBundle bundle;
    private Map sharedState;
    
    // state IDs
    // state 2 through 15 are error message states
    private static final int PASSWORD_CHANGE = 2;
    private static final int WRONG_PASSWORD_ERROR = 3;
    private static final int NO_USER_PROFILE_ERROR = 4;
    private static final int NO_USER_NAME_ERROR = 5;
    private static final int NO_PASSWORD_ERROR = 6;
    private static final int NO_CONFIRMATION_ERROR = 7;
    private static final int PASSWORD_MISMATCH_ERROR = 8;
    private static final int CONFIGURATION_ERROR = 9;
    private static final int USER_EXISTS_ERROR = 10;
    private static final int PROFILE_ERROR = 11;
    private static final int MISSING_REQ_FIELD_ERROR = 12;
    private static final int USER_PASSWORD_SAME_ERROR = 13;
    private static final int INVALID_PASSWORD = 14;
    private static final int PASSWORD_EXPIRED = 15;
    
    // having the registration and disclaimer at the end of
    // the properties file allows the disclaimer page to
    // become optional. If the user does not want to display
    // the disclaimer they just need to remove its state definition
    // from the properties file. Following a successful completion
    // of the registration page the next state will be
    // the disclaimer page if it exists. If it is not there
    // the registration page by default becomes the last page and
    // the verification process ends
    private static final int REGISTRATION = 16;
    
    // if the mode is set to turn on user name generator, then
    // this displays some possible choices of user names
    // generated by the pluggable user name generator
    private static final int CHOOSE_USERNAMES = 17;
    
    // if disclaimer page exists the user is created after
    // the user agrees to disclaimer
    private static final int DISCLAIMER = 18;
    
    // previousScreen is used when an error occurs. After
    // the user acknowledges the error they will be returned
    // the the state stored in previousScreen
    private int previousScreen;
    
    // user's valid ID and principal
    private String validatedUserID;
    private MembershipPrincipal userPrincipal;
    
    // configurations
    private Map options;
    
    private String serviceStatus;
    private boolean isDisclaimerExist = true;
    private Set defaultRoles;
    private int requiredPasswordLength;
    // private String peopleContainerDN; TODO - BACKWARD COMPATIBLITY IDRepo?
    private PasswordCallback pwdCallback;
    private String createMyOwn;
    private String userID;
    private String userName;
    private Map userAttrs;
    private static final String amAuthMembership = "amAuthMembership";
    private String regEx;
    private static final String INVALID_CHARS =
    "iplanet-am-auth-membership-invalid-chars";
    private boolean isReset;
    private boolean getCredentialsFromSharedState;
    private Callback[] callbacks;
    
    static {
        if (debug == null) {
            debug = Debug.getInstance(amAuthMembership);
        }       
    }
    
    /**
     * Initializes this <code>LoginModule</code>.
     *
     * @param subject the <code>Subject</code> to be authenticated.
     * @param sharedState shared <code>LoginModule</code> state.
     * @param options options specified in the login.
     *        <code>Configuration</code> for this particular
     *        <code>LoginModule</code>.
     */
    public void init(Subject subject, Map sharedState, Map options) {    
        java.util.Locale locale = getLoginLocale();
        bundle = amCache.getResBundle(amAuthMembership, locale);
        if (debug.messageEnabled()) {
            debug.message("Membership getting resource bundle for locale: " +
            locale);
        }
        
        this.options = options;
        this.sharedState = sharedState;
    }
    
    /**
     * Takes an array of submitted <code>Callback</code>,
     * process them and decide the order of next state to go.
     * Return STATE_SUCCEED if the login is successful, return STATE_FAILED
     * if the LoginModule should be ignored.
     *
     * @param callbacks an array of <code>Callback</cdoe> for this Login state
     * @param state order of state. State order starts with 1.
     * @return int order of next state. Return STATE_SUCCEED if authentication
     *         is successful, return STATE_FAILED if the
     *         LoginModule should be ignored.
     * @throws AuthLoginException
     */
    public int process(Callback[] callbacks, int state)
            throws AuthLoginException {
        if (debug.messageEnabled()) {
            debug.message("in process(), login state is " + state);
        }
        this.callbacks = callbacks;
        switch (state) {
            case ISAuthConstants.LOGIN_START:
                previousScreen = ISAuthConstants.LOGIN_START;
                int action = 0;
                
                // callback[2] is user selected button index
                // action == 0 is a Submit Button
                if (callbacks !=null && callbacks.length != 0) {
                    action =
                    ((ConfirmationCallback)callbacks[2]).getSelectedIndex();
                    if (debug.messageEnabled()) {
                        debug.message("LOGIN page button index: " + action);
                    }
                }
                
                if (action == 0) {
                    // loginUser will attempt to validate the user and return
                    // the next state to display, either an error state or
                    // SUCCEEDED
                    
                    // checks whether the superadmin and the username supplied
                    // is same . This check is to allow Super Admin to log in
                    // even though ldap parameters are messed up due to
                    // misconfiguration
                    return loginUser(callbacks);
                    
                } else {
                    // new user registration
                    initAuthConfig();
                    return REGISTRATION;
                }
                
            case PASSWORD_CHANGE:
                // change the user current password to a new one; the
                // next state to display will be returned, either an
                // error state or SUCCEEDED
                // callbacks[3] is user selected button index
                // pwdAction == 0 is a Submit button
                int pwdAction =
                ((ConfirmationCallback)callbacks[3]).getSelectedIndex();
                if (pwdAction == 0) {
                    return changeToNewPassword(callbacks);
                } else {
                    if (isReset) {
                        isReset = false;
                        return ISAuthConstants.LOGIN_START;
                    }
                    return ISAuthConstants.LOGIN_SUCCEED;
                }
                
            case WRONG_PASSWORD_ERROR:
            case NO_USER_PROFILE_ERROR:
            case NO_USER_NAME_ERROR:
            case NO_PASSWORD_ERROR:
            case NO_CONFIRMATION_ERROR:
            case PASSWORD_MISMATCH_ERROR:
            case CONFIGURATION_ERROR:
            case USER_EXISTS_ERROR:
            case PROFILE_ERROR:
            case MISSING_REQ_FIELD_ERROR:
            case USER_PASSWORD_SAME_ERROR:
            case INVALID_PASSWORD:
                // all error states are handled here;
                // following an error condition we want to go back to
                // the screen we were working on when the error occurred
                return previousScreen;
                
            case CHOOSE_USERNAMES:
                // user name entered already exists, generate
                // a set of user names for user to choose
                return chooseUserID(callbacks);
                
            case DISCLAIMER:
                // when disclaimer page exists the user is created
                // after the user agrees to disclaimer
                // callbacks[0] is user selected button index
                int agree =
                ((ConfirmationCallback)callbacks[0]).getSelectedIndex();
                if (debug.messageEnabled()) {
                    debug.message("DISCLAIMER page button index: " + agree);
                }
                if (agree == 0) {
                    return registerNewUser();
                    
                } else if (agree == 1) {
                    return ISAuthConstants.LOGIN_START;
                } else {
                    throw new AuthLoginException(amAuthMembership,
                    "loginException", null);
                }
                
            default:
                // this is REGISTRATION state, registration will attempt to
                // create a new user profile
                
                previousScreen = REGISTRATION;
                
                // callbacks[len-1] is a user selected button index
                // next == 0 is a Submit button
                // next == 1 is a Cancel button
                int next = ((ConfirmationCallback)
                callbacks[callbacks.length-1]).getSelectedIndex();
                if (debug.messageEnabled()) {
                    debug.message("REGISTRATION page button index: " + next);
                }
                if (next == 0) {
                    int tempState = getAndCheckRegistrationFields(callbacks);
                    
                    if (tempState == REGISTRATION) {
                        if (isDisclaimerExist) {
                            return DISCLAIMER;
                        } else {
                            // no Disclaimer, just register the user
                            return registerNewUser();
                        }
                    } else {
                        // go to error state
                        return tempState;
                    }
                    
                } else if (next == 1) {
                    clearCallbacks(callbacks);
                    return ISAuthConstants.LOGIN_START;
                    
                } else {
                    return ISAuthConstants.LOGIN_IGNORE;
                }
        }
    }
    
    /**
     * User input value will be store in the callbacks[].
     * When user click cancel button, these input field should be reset
     * to blank.
     */
    private void clearCallbacks(Callback[] callbacks) {
        for(int i=0; i<callbacks.length; i++) {
            if (callbacks[i] instanceof NameCallback) {
                NameCallback nc = (NameCallback) callbacks[i];
                nc.setName("");
            }
        }
    }
    
    
    /**
     * Returns <code>java.security.Principal</code>.
     *
     * @return <code>java.security.Principal</code>
     */
    public java.security.Principal getPrincipal() {
        if (userPrincipal != null) {
            return userPrincipal;
            
        } else if (validatedUserID != null) {
            userPrincipal = new MembershipPrincipal(validatedUserID);
            return userPrincipal;
            
        } else {
            return null;
        }
    }

    /**
     * Destroy the module state
     */
    public void destroyModuleState() {
        validatedUserID = null;
    }
    
    /**
     * Set all the used variables to null
     */
    public void nullifyUsedVars() {
        bundle = null;
        sharedState = null;
        options = null;
        serviceStatus = null;
        defaultRoles = null;
        // peopleContainerDN = null;  TODO- Backward Compatiblity
        pwdCallback = null;
        createMyOwn = null;
        userID = null;
        userName = null ;
        userAttrs = null;
        regEx = null;
        callbacks = null;
    }
    

    /**
     * Initializes auth configurations.
     */
    private void initAuthConfig() throws AuthLoginException {
        if (options == null || options.isEmpty()) {
            debug.error("options is null or empty");
            throw new AuthLoginException(amAuthMembership,
            "unable-to-initialize-options", null);
        }
        
        try {
            String authLevel = CollectionHelper.getMapAttr(options,
                "iplanet-am-auth-membership-auth-level");
            if (authLevel != null) {
                try {
                    int tmp = Integer.parseInt(authLevel);
                    setAuthLevel(tmp);
                } catch (NumberFormatException e) {
                    // invalid auth level
                    debug.error("invalid auth level " + authLevel, e);
                }
            }    
            regEx = CollectionHelper.getMapAttr(options, INVALID_CHARS);        
            serviceStatus = CollectionHelper.getMapAttr(options,
                "iplanet-am-auth-membership-default-user-status", "Active");
	            
            if (getNumberOfStates() >= DISCLAIMER) {
                isDisclaimerExist = true;
            } else {
                isDisclaimerExist = false;
            }
 
            defaultRoles  = (Set)options.get(
                "iplanet-am-auth-membership-default-roles");        
            if (debug.messageEnabled()) {
                debug.message("defaultRoles is : " + defaultRoles);
            }
     
            String tmp = CollectionHelper.getMapAttr(options,
                "iplanet-am-auth-membership-min-password-length");
            if (tmp != null) {
                requiredPasswordLength = Integer.parseInt(tmp);
            }
   
            if (callbacks !=null && callbacks.length != 0) {
                Callback[] callbacks = getCallback(PASSWORD_CHANGE);
                pwdCallback = ((PasswordCallback)callbacks[0]);
            }        
        }catch(Exception e){
            debug.error("unable to initialize in initAuthConfig(): ", e);
            throw new AuthLoginException(amAuthMembership,
                "Membershipex", null, e);        	
        }
    }    
   
    
    private int loginUser(Callback[] callbacks) throws AuthLoginException {
    	String password = null;
        Callback[] idCallbacks = new Callback[2];
        try {
            if (callbacks !=null && callbacks.length == 0) {
                userName = (String) sharedState.get(getUserKey());
                password = (String) sharedState.get(getPwdKey());
                if (userName == null || password == null) {
                    return ISAuthConstants.LOGIN_START;
                }
                getCredentialsFromSharedState = true;
                NameCallback nameCallback = new NameCallback("dummy");
                nameCallback.setName(userName);
                idCallbacks[0] = nameCallback;
                PasswordCallback passwordCallback = new PasswordCallback
                    ("dummy",false);
                passwordCallback.setPassword(password.toCharArray());
                idCallbacks[1] = passwordCallback;
            } else {
                idCallbacks = callbacks;
                //callbacks is not null
                userName = ( (NameCallback) callbacks[0]).getName();
                password = String.valueOf(((PasswordCallback)
                    callbacks[1]).getPassword());
            }

            if (password == null || password.length() == 0) {
                if (debug.messageEnabled()) {
                    debug.message("Membership.loginUser: Password is null/empty");
                } 
                throw new InvalidPasswordException("amAuth",
                        "invalidPasswd", null); 
            }

            //store username password both in success and failure case
            storeUsernamePasswd(userName, password);
            initAuthConfig();
                 
            AMIdentityRepository idrepo = getAMIdentityRepository(
                getRequestOrg());
            boolean success = idrepo.authenticate(idCallbacks);
            if (success) {
                validatedUserID = userName;
                return ISAuthConstants.LOGIN_SUCCEED;
            } else {
                throw new AuthLoginException(amAuthMembership, "authFailed",
                null);
            }
        } catch (IdRepoException ex) {
            if (getCredentialsFromSharedState && !isUseFirstPassEnabled()) {
                getCredentialsFromSharedState = false;
                return ISAuthConstants.LOGIN_START;
            }			
            debug.message("idRepo Exception");
            setFailureID(userName);
            throw new AuthLoginException(amAuthMembership, "authFailed",
                null, ex);
        }
    }   

    
    /**
     * Returns the current password, new password, and
     * confirm password from user input. It will then try to change
     * the password of the user.
     */
    private int changeToNewPassword(Callback[] callbacks)
            throws AuthLoginException {
        // callback[0] is for old password
        // callback[1] is for new password
        // callback[2] is for confirm password
        debug.message("trying to change user password");
        
        String oldPassword = getPassword((PasswordCallback)callbacks[0]);
        String newPassword = getPassword((PasswordCallback)callbacks[1]);
        String confirmPassword = getPassword((PasswordCallback)callbacks[2]);
        validatePassword(newPassword);
        
        //Change password functionality needs to be implemented  
        
        return ISAuthConstants.LOGIN_SUCCEED; 
    
        /*
        try {
            ldapUtil.changePassword(oldPassword, newPassword, confirmPassword);
            int ldapState = ldapUtil.getState();
            return processMembershipPasswordState(ldapState);
            
        } catch(LDAPUtilException ex) {
            setFailureID(ldapUtil.getUserId(userName));
            switch( ex.getLDAPResultCode() ) {
                case LDAPException.NO_SUCH_OBJECT:
                    debug.message("The specified user does not exist");
                    return REGISTRATION;
                    
                case LDAPException.INVALID_CREDENTIALS:
                    debug.message("Invalid password");
                    throw new AuthLoginException(amAuthMembership,
                    "InvalidUP", null);
                    
                default:
                    throw new AuthLoginException(amAuthMembership,
                    "LDAPex", null, ex);
            }
        }*/
    }    

    
    /**
     * Creates user profile and sets the membership profile attributes.
     * This registration should be done after getting and checking all
     * registration fields.
     */
    private int registerNewUser() throws AuthLoginException {
        if (debug.messageEnabled()) {
            debug.message("trying to register(create) a new user: " + userID);
        }
        try {
            if (userExists(userID)) {
                if (debug.messageEnabled()) {
                    debug.message("unable to register, user " +
                    userID + " already exists");
                }
                return USER_EXISTS_ERROR;
            }
            
            Set vals = new HashSet();
            // set user status
            vals.add(serviceStatus);
            userAttrs.put("inetuserstatus", vals);
            
            createIdentity(userID,userAttrs,defaultRoles);
            
        } catch (SSOException pe) {
            debug.error("profile exception occured: ", pe);
            return PROFILE_ERROR;
            
        } catch (IdRepoException pe) {
            // log constraint violation message
            getLoginState("Membership").logFailed(pe.getMessage(),
                "CREATE_USER_PROFILE_FAILED", false, null);
            debug.error("profile exception occured: ", pe);
            return PROFILE_ERROR;
            
        }
        
        // TODO: does it need to be a DN?
        validatedUserID = userID;
        
        if (debug.messageEnabled()) {
            debug.message("registration is completed, created user: " +
            validatedUserID);
        }
        
        return ISAuthConstants.LOGIN_SUCCEED;
    }
    
    /**
     * Returns and checks registration fields. Returns error state or
     * REGISTRATION if no error.
     */
    private int getAndCheckRegistrationFields(Callback[] callbacks)
            throws AuthLoginException {
        // callback[0] is for user name
        // callback[1] is for new password
        // callback[2] is for confirm password
        
        Map attrs = new HashMap();
        
        // get the value of the user name from the input form
        userID = getCallbackFieldValue(callbacks[0]);
        // check user name
        if ((userID == null) || userID.length() == 0) {
            // no user name was entered, this is required to
            // create the user's profile
            return NO_USER_NAME_ERROR;
        }
        //validate username using plugin if any
        validateUserName(userID, regEx);
        
        // get the passwords from the input form
        String password = getPassword((PasswordCallback)callbacks[1]);
        String confirmPassword = getPassword((PasswordCallback)callbacks[2]);
        // check passwords
        int tempState = checkPassword(password, confirmPassword);
        if (debug.messageEnabled()) {
            debug.message("state returned from checkPassword(): " +
            tempState);
        }
        if (tempState != ISAuthConstants.LOGIN_SUCCEED) {
            // the next state to display is returned from checkPassword
            return tempState;
        }
        // validate password using validation plugin if any
        validatePassword(confirmPassword);
        
        if (password.equals(userID)) {
            // the user name and password are the same. these fields
            // must be different
            return USER_PASSWORD_SAME_ERROR;
        }
        
        // get the registration fields, also check required fields
        for (int i = 0; i < callbacks.length; i++) {
            String attrName = getAttribute(REGISTRATION, i);
            Set values = getCallbackFieldValues(callbacks[i]);
            if (isRequired(REGISTRATION, i)) {
                if (values.isEmpty()) {
                    if (debug.messageEnabled()) {
                        debug.message("Empty value for required field :" +
                        attrName);
                    }
                    return MISSING_REQ_FIELD_ERROR;
                }
            }
            
            if (attrName != null && attrName.length() != 0) {
                attrs.put(attrName, values);
            }
        }
        
        userAttrs = attrs;
        
        // check user ID uniqueness
        try {
            if (userExists(userID)) {
                if (debug.messageEnabled()) {
                    debug.message("user ID " + userID + " already exists");
                }
                
                // get a list of user IDs from the generator
                Set generatedUserIDs = getNewUserIDs(attrs, 0);
                if (generatedUserIDs == null) {
                    // user name generator is disable
                    return USER_EXISTS_ERROR;
                }
                
                // get a list of user IDs that are not yet being used
                ArrayList nonExistingUserIDs =
                getNonExistingUserIDs(generatedUserIDs);
                
                resetCallback(CHOOSE_USERNAMES, 0);
                Callback[] origCallbacks = getCallback(CHOOSE_USERNAMES);
                ChoiceCallback origCallback =
                (ChoiceCallback) origCallbacks[0];
                String prompt = origCallback.getPrompt();
                createMyOwn = origCallback.getChoices()[0];
                
                nonExistingUserIDs.add(createMyOwn);
                
                String[] choices =
                ((String[]) nonExistingUserIDs.toArray(new String[0]));
                
                ChoiceCallback callback = new ChoiceCallback(
                prompt, choices, 0, false);
                callback.setSelectedIndex(0);
                replaceCallback(CHOOSE_USERNAMES, 0, callback);
                
                return CHOOSE_USERNAMES;
            }
            
        } catch (SSOException pe) {
            debug.error("profile exception occured: ", pe);
            return PROFILE_ERROR;
            
        } catch (IdRepoException pe) {
            debug.error("profile exception occured: ", pe);
            return PROFILE_ERROR;
            
        }
        
        /**if (debug.messageEnabled()) {
         *   debug.message("the user attributes to be created:\n" + userAttrs);
         * }
         */
        
        return REGISTRATION;
    }
    
    /**
     * Checks the passwords and returned error state or SUCCEEDED if
     * the passwords are valid.
     */
    private int checkPassword(String password, String confirmPassword) {
        if ((password == null) || password.length() == 0) {
            // missing the password field
            debug.message("password was missing from the form");
            return NO_PASSWORD_ERROR;
            
        } else {
            // compare the length of the user entered password with
            // the length required
            if (password.length() < requiredPasswordLength) {
                debug.message("password was not long enough");
                return INVALID_PASSWORD;
            }
            
            // length OK, now make sure the user entered a confirmation
            // password
            if ((confirmPassword == null) || confirmPassword.length() == 0) {
                // no confirm password field entered
                debug.message("no confirmation password");
                return NO_CONFIRMATION_ERROR;
                
            } else {
                // does the confirmation password match the actual password
                if (!password.equals(confirmPassword)) {
                    // the password and the confirmation password don't match
                    return PASSWORD_MISMATCH_ERROR;
                }
            }
        }
        
        return ISAuthConstants.LOGIN_SUCCEED;
    }
    
    /**
     * Returns the user choice user ID and proceed to the next state.
     */
    private int chooseUserID(Callback[] callbacks) throws AuthLoginException {
        // callbacks[0] is the choice of the user ID
        String userChoiceID = getCallbackFieldValue(callbacks[0]);
        
        if (userChoiceID.equals(createMyOwn)) {
            return REGISTRATION;
            
        } else {
            String attrName = getAttribute(REGISTRATION, 0);
            userID = userChoiceID;
            Set values = new HashSet();
            values.add(userID);
            userAttrs.put(attrName, values);
            
            if (isDisclaimerExist) {
                return DISCLAIMER;
            } else {
                // no Disclaimer, just register the user
                return registerNewUser();
            }
        }
    }
    
    /**
     * Returns the password from the PasswordCallback.
     */
    private String getPassword(PasswordCallback callback) {
        char[] tmpPassword = callback.getPassword();
        if (tmpPassword == null) {
            // treat a NULL password as an empty password
            tmpPassword = new char[0];
        }
        char[] pwd = new char[tmpPassword.length];
        System.arraycopy(tmpPassword, 0, pwd, 0, tmpPassword.length);
        
        return (new String(pwd));
    }
    
    
    /**
     * Returns the input values as a Set for different types of Callback.
     * An empty Set will be returned if there is no value for the
     * Callback, or the Callback is not supported.
     */
    private Set getCallbackFieldValues(Callback callback) {
        Set values = new HashSet();
        if (callback instanceof NameCallback) {
            String value = ((NameCallback)callback).getName();
            if (value != null && value.length() != 0) {
                values.add(value);
            }
            
        } else if (callback instanceof PasswordCallback) {
            String value = getPassword((PasswordCallback)callback);
            if (value != null && value.length() != 0) {
                values.add(value);
            }
            
        } else if (callback instanceof ChoiceCallback) {
            String[] vals = ((ChoiceCallback)callback).getChoices();
            int[] selectedIndexes =
            ((ChoiceCallback)callback).getSelectedIndexes();
            for (int i = 0; i < selectedIndexes.length; i++) {
                values.add(vals[selectedIndexes[i]]);
            }
        }
        return values;
    }
    
    /**
     * Returns the first input value for the given Callback.
     * Returns null if there is no value for the Callback.
     */
    private String getCallbackFieldValue(Callback callback) {
        Set values = getCallbackFieldValues(callback);
        Iterator it = values.iterator();
        if (it.hasNext()) {
            return ((String) it.next());
        }
        return null;
    }
    
    /**
     * Returns a list of user IDs from the specified set of user IDs that
     * are not exist under the specified people container.
     */
    private ArrayList getNonExistingUserIDs(Set userIDs)
            throws IdRepoException, SSOException {
        ArrayList validUserIDs = new ArrayList();
        Iterator it = userIDs.iterator();
        
        while (it.hasNext()) {
            String userID = (String)it.next();
            // check if user already exists with the same user ID
            if (!userExists(userID)) {
                validUserIDs.add(userID);
            }
        }
        
        return validUserIDs;
    }
    
    
    /** check if user exists */
    private boolean userExists(String userID)
        throws IdRepoException, SSOException {
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
            amIdRepo.searchIdentities(IdType.USER, userID, idsc);
            if (searchResults != null) {
                results = searchResults.getSearchResults();
            }
        } catch (IdRepoException e) {
            if (debug.messageEnabled()) {
                debug.message("IdRepoException : Error searching "
                + " Identities with username : "
                + e.getMessage());
            }
        }

        return !results.isEmpty();
    }
}
