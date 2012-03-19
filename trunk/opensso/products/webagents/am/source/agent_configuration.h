/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2007 Sun Microsystems Inc. All Rights Reserved
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
 * $Id: agent_configuration.h,v 1.17 2010/03/10 05:09:36 dknab Exp $
 *
 */

/*
 * Portions Copyrighted [2010] [ForgeRock AS]
 */


#ifndef _AGENT_CONFIGURATION_H_
#define _AGENT_CONFIGURATION_H_
#include "am_web.h"
#include "am_properties.h"
#include "ref_cnt_ptr.h"
#include "utils.h"
#include "url.h"
#include <string>
#include <set>
#include <stdio.h>
#include "fqdn_handler.h"
#include "p_cache.h"

#if	defined(WINNT)
#define _X86_
#include <windef.h>
#include <winbase.h>
#include <winuser.h>
#include <winnls.h>
#include <windows.h>
#if	!defined(strncasecmp)
#define	strncasecmp	strnicmp
#define	strcasecmp	stricmp
#endif

#if     !defined(snprintf)
#define snprintf        _snprintf
#endif
#else /* WINNT */
#include <unistd.h>
#endif /* WINNT */
BEGIN_PRIVATE_NAMESPACE
#define AGENT_CONFIGURATION "AgentConfiguration"

typedef std::list<std::string> slist_t;
typedef std::map<std::string, slist_t> smap_t;
typedef std::pair<std::string, slist_t> spair_t;

template <class ContainerT >
void tokenize(const std::string& str, ContainerT& tokens, const std::string& delimiters = " ", const bool trimEmpty = false) {
    std::string::size_type pos, lastPos = 0;
    while (true) {
        pos = str.find_first_of(delimiters, lastPos);
        if (pos == std::string::npos) {
            pos = str.length();
            if (pos != lastPos || !trimEmpty)
                tokens.push_back(typename ContainerT::value_type(str.data() + lastPos,
                    (typename ContainerT::value_type::size_type)pos - lastPos));
            break;
        } else {
            if (pos != lastPos || !trimEmpty)
                tokens.push_back(typename ContainerT::value_type(str.data() + lastPos,
                    (typename ContainerT::value_type::size_type)pos - lastPos));
        }
        lastPos = pos + 1;
    }
};
        
/**
* Data Structure to hold Cookie information passed to set_cookie function.
*/

class AgentConfiguration:public RefCntObj {
public:
    am_log_module_id_t log_module;
    am_log_module_id_t remote_LogID;
    am_properties_t properties;
    Utils::url_info_list_t not_enforced_list;
    std::set<std::string> *not_enforce_IPAddr;
    PRBool reverse_the_meaning_of_not_enforced_list;
    PRBool notenforced_url_attributes_enable;
    PRBool do_sso_only;
    const char *instance_name;
    const char *cookie_name;
    size_t cookie_name_len;
    PRBool is_cookie_secure;
    PRBool is_cookie_httponly;
    const char *access_denied_url;
    const char *logout_redirect_url;
    PRLock *lock;
    Utils::url_info_list_t login_url_list;
    Utils::url_info_list_t logout_url_list;
    Utils::url_info_list_t agent_logout_url_list;
    Utils::url_info_list_t cdsso_server_url_list;
    PRBool notification_enable;
    const char *notification_url;
    PRBool url_comparison_ignore_case;
    PostCache *postcache_handle;
    unsigned long postcacheentry_life;
    PRBool postdatapreserve_enable;
    const char *postdatapreserve_sticky_session_mode;
    const char *postdatapreserve_sticky_session_value;
    const char *url_redirect_param;
    const char *user_id_param;
    const char *authLogType_param;
#if defined(WINNT)
    HINSTANCE hInst;
#endif
    const char *locale;
    const char *unauthenticated_user;
    PRBool anon_remote_user_enable;
    PRBool check_client_ip;
    
    PRBool fqdn_check_enable;
    FqdnHandler *fqdn_handler;
    const char *fqdn_default;
    size_t fqdn_default_len;   
    PRBool cookie_reset_enable;
    std::set<std::string> *cookie_domain_list;
    PRBool cdsso_enable;
    Utils::cookie_info_list_t cookie_list;
    const char *cookie_reset_default_domain;
    Utils::url_info_t agent_server_url;
    Utils::cookie_info_list_t logout_cookie_reset_list;
    PRBool getClientHostname;
    unsigned log_access_type;
    PRBool convert_mbyte;
    PRBool encode_url_special_chars;
    PRBool override_protocol;	// whether to override protocol in request url
    PRBool override_host;	// whether to override host in request url
    PRBool override_port;	// whether to override port in request url
    PRBool override_notification_url;	// whether to override the notification
    // url the same way as other rq urls
    PRBool ignore_path_info;
    PRBool ignore_path_info_for_not_enforced_list;
    PRBool use_basic_auth;
    unsigned long connection_timeout;   //connection timeout in sec to check if active login server alive
    PRBool ignore_server_check;	// ignore server check before redirection
    const char *authtype;   //value of authtype in IIS6 agent
    PRBool override_host_port;	// used by Proxy agent
    const char *iis6_replaypasswd_key; // IIS6 replay passwd key
    const char *filter_priority; //IIS 5 filter priority
    PRBool owa_enable;	// OWA enable in IIS6
    PRBool owa_enable_change_protocol;	// OWA enable change protocol in IIS6
    const char *owa_enable_session_timeout_url; // OWA enable session timeout url 
    PRBool check_name_database;    // IBM Lotus DOMINO check name database
    PRBool ltpa_enable;            // IBM Lotus DOMINO enable ltpa token
    const char * ltpa_config_name; // IBM Lotus DOMINO ltpa config name
    const char * ltpa_org_name;    // IBM Lotus DOMINO ltpa organization name
    const char * ltpa_cookie_name; // IBM Lotus DOMINO ltpa cookie name
    const char * attrCookiePrefix;
    const char * attrCookieMaxAge;
    const char * profileMode;
    const char * sessionMode;
    const char * responseMode;
    const char * sessionAttributes;
    const char * responseAttributes;
    set_user_attrs_mode_t setUserProfileAttrsMode;
    set_user_attrs_mode_t setUserSessionAttrsMode;
    set_user_attrs_mode_t setUserResponseAttrsMode;
    std::list<std::string> attrList;
    const char * attrMultiValueSeparator;
    unsigned long policy_clock_skew; // Policy Clock Skew
    PRBool configChangeNotificationEnable;
    const char * auditLogDisposition;
    unsigned long localAuditLogFileSize;
    PRBool localAuditLogFileRotate;
    unsigned long debugFileSize;
    PRBool debugFileRotate;
    PRBool doRemoteLog; //utility member to determine whether to remote log or not
    const char * clientIPHeader;
    const char * clientHostnameHeader;
    PRBool encodeCookieSpecialChars;

    const char *notenforcedIPmode;
    PRBool use_redirect_for_advice;	// use redirect instead of POST for advice
    PRBool cdsso_cookie_urlencode;	// encode cookie value extracted from LARES response
    PRBool cdsso_disable_redirect_on_post;	// disable extra-302-redirect on (after) LARES post
    PRBool cache_control_header_enable;	// enable cache-control/pragma/expires = no-cache headers for unauthenticated sessions
    am_status_t error;
    
    smap_t cond_login_url;
    
    const char *dummyPostPrefixUri;
    
    AgentConfiguration();
    AgentConfiguration(am_properties_t properties);
    ~AgentConfiguration();
    
    /*
     * Initialize the Agent configuration object
     */
    void initAgentConfiguration() {
        this->log_module = AM_LOG_ALL_MODULES;
        this->remote_LogID =  AM_LOG_ALL_MODULES;
        this->properties =  ((am_properties_t) 0);
        this->not_enforce_IPAddr =  NULL; 
        this->reverse_the_meaning_of_not_enforced_list = AM_FALSE;
        this->notenforced_url_attributes_enable = AM_FALSE;
        this->do_sso_only = AM_FALSE;
        this->instance_name = NULL;
        this->cookie_name = NULL;
        this->cookie_name_len = 0;
        this->is_cookie_secure = AM_FALSE;
        this->is_cookie_httponly = AM_FALSE;
        this->access_denied_url = NULL;
        this->logout_redirect_url = NULL;
        this->lock = (PRLock *) NULL;
        this->notification_enable = AM_FALSE;
        this->notification_url = NULL;
        this->url_comparison_ignore_case = false;
        this->postcacheentry_life = 0;
        this->postcache_handle = NULL;
        this->postdatapreserve_enable = AM_TRUE;
        this->postdatapreserve_sticky_session_mode = NULL;
        this->postdatapreserve_sticky_session_value = NULL;
        this->url_redirect_param= NULL;
        this->user_id_param = NULL;
        this->authLogType_param = NULL;
#if defined(WINNT)
        this->hInst = NULL;
#endif
        this->locale = NULL;
        this->unauthenticated_user = NULL;
        this->anon_remote_user_enable = AM_FALSE;
        this->check_client_ip = AM_FALSE;
        this->fqdn_check_enable = AM_TRUE;
        this->fqdn_handler = NULL;
        this->fqdn_default = NULL;
        this->fqdn_default_len =  0;
        this->cookie_reset_enable = AM_FALSE;
        this->cookie_domain_list = NULL;
        this->cdsso_enable = AM_FALSE;
        this->cookie_reset_default_domain  = NULL;
        this->getClientHostname = AM_FALSE;
        this->log_access_type = (unsigned int)-1;
        this->convert_mbyte = AM_FALSE;
        this->encode_url_special_chars = AM_FALSE;
        this->override_notification_url =  AM_FALSE;
        this->ignore_path_info = AM_FALSE;
        this->ignore_path_info_for_not_enforced_list = AM_FALSE;
        this->use_basic_auth = AM_FALSE;
        this->connection_timeout = 0;
        this->ignore_server_check = AM_FALSE;
        this->authtype = NULL;
        this->override_host_port = AM_FALSE;
        this->iis6_replaypasswd_key = NULL;
        this->filter_priority = IIS_FILTER_PRIORITY;
        this->owa_enable = AM_FALSE;
        this->owa_enable_change_protocol = NULL;        
        this->check_name_database = AM_FALSE;
        this->ltpa_enable = AM_FALSE;
        this->ltpa_config_name = LTPA_DEFAULT_CONFIG_NAME;
        this->ltpa_org_name = LTPA_DEFAULT_ORG_NAME;
        this->ltpa_cookie_name = LTPA_DEFAULT_TOKEN_NAME;
        this->attrCookiePrefix = COOKIE_ATTRIBUTE_PREFIX;
        this->attrCookieMaxAge = COOKIE_ATTRIBUTE_MAX_AGE;
        this->profileMode = POLICY_ATTRIBUTES_MODE_NONE;
        this->sessionMode = POLICY_ATTRIBUTES_MODE_NONE;
        this->responseMode = POLICY_ATTRIBUTES_MODE_NONE;
        this->sessionAttributes = POLICY_SESSION_ATTRIBUTES;
        this->responseAttributes = POLICY_RESPONSE_ATTRIBUTES;
        this->setUserProfileAttrsMode = SET_ATTRS_NONE;
        this->setUserSessionAttrsMode = SET_ATTRS_NONE;
        this->setUserResponseAttrsMode = SET_ATTRS_NONE;
        this->attrMultiValueSeparator = ATTR_MULTI_VALUE_SEPARATOR;
        this->policy_clock_skew = 0;
        this->configChangeNotificationEnable = AM_FALSE;
        this->auditLogDisposition = NULL;
        this->localAuditLogFileSize = 0;
        this->localAuditLogFileRotate = AM_FALSE;
        this->debugFileSize = 0;
        this->debugFileRotate = AM_TRUE;
        this->doRemoteLog = AM_FALSE;
        this->clientIPHeader = NULL;
        this->clientHostnameHeader = NULL;
        this->encodeCookieSpecialChars = AM_FALSE;
        this->notenforcedIPmode = NULL;
        this->use_redirect_for_advice = AM_FALSE;
        this->cdsso_cookie_urlencode = AM_FALSE;
        this->cdsso_disable_redirect_on_post = AM_FALSE;
        this->dummyPostPrefixUri = NULL;
        this->error = AM_SUCCESS;

    }
    
    void initLoginURLList() {
        this->login_url_list.size = 0;   
        this->login_url_list.list = ((Utils::url_info_t *) NULL);
    }

    void initLogoutURLList() {
        this->logout_url_list.size = 0;
        this->logout_url_list.list = ((Utils::url_info_t *) NULL);
    }

    void initAgentLogoutURLList() {
        this->agent_logout_url_list.size = 0;
        this->agent_logout_url_list.list = ((Utils::url_info_t *) NULL);
    }
    
    void initNotEnforcedURLList() {
        this->not_enforced_list.size = 0;
         this->not_enforced_list.list = ((Utils::url_info_t *) NULL);
    }
    
    void initCDSSOURLList() {
        this->cdsso_server_url_list.size = 0;
        this->cdsso_server_url_list.list = ((Utils::url_info_t *) NULL);
    }

    void initCookieList() {
        this->cookie_list.size = 0;
        this->cookie_list.list = ((Utils::cookie_info_t *) NULL);
    }

    void initLogoutCookieList() {
        this->logout_cookie_reset_list.size = 0;
        this->logout_cookie_reset_list.list = ((Utils::cookie_info_t *) NULL);
    }

    void initAgentServerURLList() {
        this->agent_server_url.url = NULL;
        this->agent_server_url.url_len = 0;
        this->agent_server_url.protocol = NULL;
        this->agent_server_url.port = 0;
        this->agent_server_url.has_parameters = AM_FALSE;
        this->agent_server_url.has_patterns = AM_FALSE;
    }
    
    void setProperties(am_properties_t props) {
        this->properties = props;
    }

    am_properties_t getProperties() {
        return this->properties;
    }
    
    
private:
    am_status_t populateAgentProperties();
    void populate_am_resource_traits(am_resource_traits_t &rsrcTraits);
    am_status_t load_fqdn_handler(bool ignore_case);
    am_status_t unload_fqdn_handler();
    void cleanup_properties();

};

typedef RefCntPtr<AgentConfiguration> AgentConfigurationRefCntPtr;

END_PRIVATE_NAMESPACE

#endif	

