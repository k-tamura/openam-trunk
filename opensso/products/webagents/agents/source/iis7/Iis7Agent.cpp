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
* $Id: Iis7Agent.cpp,v 1.7 2010/03/10 05:08:52 dknab Exp $
*
*
*/

#include "Iis7Agent.h"

#define WIN32_LEAN_AND_MEAN
#include <windows.h>
#include <httpserv.h>
#include <string.h>
#include <stdio.h>
#include <stdarg.h>
#include <nspr.h>

typedef struct agent_props {
	am_properties_t agent_bootstrap_props;
	am_properties_t agent_config_props;
} agent_props_t;

static agent_props_t agent_props = {
	AM_PROPERTIES_NULL
};

boolean_t agentInitialized = B_FALSE;

#define EMPTY_STRING        ""
#define AGENT_DESCRIPTION   "Sun OpenSSO Policy Agent 3.0 for Microsoft IIS 7.0"
#define	MAGIC_STR		"sunpostpreserve"
#define	POST_PRESERVE_URI	"/dummypost/"MAGIC_STR

const CHAR agentDescription[]       = { AGENT_DESCRIPTION };
const CHAR httpProtocol[]           = "http";
const CHAR httpsProtocol[]          = "https";
const CHAR httpProtocolDelimiter[]  = "://";
// Do not change. Used to see if port number needed to reconstructing URL.
const CHAR httpPortDefault[]        = "80";
const CHAR httpsPortDefault[]       = "443";
const CHAR httpPortDelimiter[]      = ":";
const CHAR pszCrlf[]                = "\r\n";

// Responses the agent uses to requests.
typedef enum {aaDeny, aaAllow, aaLogin} tAgentAction;
tAgentConfig agentConfig;

BOOL readAgentConfigFile = FALSE;
CRITICAL_SECTION initLock;

BOOL isCdssoEnabled = FALSE;

#define RESOURCE_INITIALIZER \
{ NULL, 0, AM_POLICY_RESULT_INITIALIZER }

/*
* This is the function invoked by RegisterModule
* when the agent module DLL is loaded at startup.
* */
BOOL RegisterAgentModule()
{
	HMODULE nsprHandle = NULL;
	PR_Init(PR_SYSTEM_THREAD, PR_PRIORITY_NORMAL, 0);
	nsprHandle = LoadLibrary("libnspr4.dll");
	InitializeCriticalSection(&initLock);
	return TRUE;
}

static am_status_t register_post_data(IHttpContext* pHttpContext,
									  char *url, const char *key, char* response,
									  void* agent_config)
{
	const char *thisfunc = "register_post_data()";
	am_web_postcache_data_t post_data;
	am_status_t status = AM_SUCCESS;

	post_data.value = response;
	post_data.url = url;
	am_web_log_debug("%s: Register POST data key :%s", thisfunc, key);
	if (am_web_postcache_insert(key, &post_data, agent_config) == B_FALSE) {
		am_web_log_error("Register POST data insert into"
			" hash table failed:%s",key);
		status = AM_FAILURE;
	}

	return status;
}

am_status_t process_request_with_post_data_preservation(IHttpContext* pHttpContext,
														am_status_t request_status,
														am_policy_result_t *policy_result,
														char *requestURL,
														void **args,
														char *resp,
														void* agent_config)
{
	const char *thisfunc = "process_request_with_post_data_preservation()";
	am_status_t status = AM_SUCCESS;
	DWORD returnValue = AM_FAILURE;
	post_urls_t *post_urls = NULL;
	string response = "";

	if (resp != NULL) {
		response = resp;
	}
	status = am_web_create_post_preserve_urls(requestURL, &post_urls,
		agent_config);
	if (status != AM_SUCCESS) {
		returnValue = AM_FAILURE;
	}
	// In CDSSO mode, for a POST request, the post data have
	// already been saved in the response variable, so we need
	// to get them here only if response is NULL.
	if ((status == AM_SUCCESS) && (response.size() == 0)) {
		GetEntity(pHttpContext, response);
	}
	if ((status == AM_SUCCESS) && (response.size()) > 0) {
		if (AM_SUCCESS == register_post_data(pHttpContext,post_urls->action_url,
			post_urls->post_time_key, (char *)response.c_str(),
			agent_config))
		{
			char *lbCookieHeader = NULL;
			// If using a LB in front of the agent and if the sticky
			// session mode is COOKIE, the lb cookie needs to be set there.
			// If am_web_get_postdata_preserve_lbcookie()
			// returns AM_INVALID_ARGUMENT, it means that the
			// sticky session feature is disabled (ie no LB) or
			// that the sticky session mode is set to URL.
			status = am_web_get_postdata_preserve_lbcookie(
				&lbCookieHeader, B_FALSE, agent_config);
			if (status == AM_NO_MEMORY) {
				returnValue = AM_FAILURE;
			} else {
				if (status == AM_SUCCESS) {
					am_web_log_debug("%s: Setting LB cookie "
						"for post data preservation.",
						thisfunc);
					set_cookie(lbCookieHeader, args);
				}
				returnValue = do_redirect(pHttpContext, request_status,
					policy_result,
					post_urls->dummy_url,
					REQUEST_METHOD_POST, args,
					agent_config);
			}
			if (lbCookieHeader != NULL) {
				am_web_free_memory(lbCookieHeader);
				lbCookieHeader = NULL;
			}
		} else {
			am_web_log_error("%s: register_post_data() "
				"failed.", thisfunc);
			returnValue = AM_FAILURE;
		}
	} else {
		am_web_log_debug("%s: This is a POST request with no post data. "
			"Redirecting as a GET request.", thisfunc);
		returnValue = do_redirect(pHttpContext, request_status,
			policy_result,
			requestURL,
			REQUEST_METHOD_GET, args,
			agent_config);
	}
	if (post_urls != NULL) {
		am_web_clean_post_urls(post_urls);
		post_urls = NULL;
	}

	return (am_status_t)returnValue;
}


// Method to check and create post page
static am_status_t check_for_post_data(IHttpContext* pHttpContext,
									   char *requestURL, char **page,
									   void *agent_config)
{
	const char *thisfunc = "check_for_post_data()";
	const char *post_data_query = NULL;
	am_web_postcache_data_t get_data = {NULL, NULL};
	const char *actionurl = NULL;
	const char *postdata_cache = NULL;
	am_status_t status = AM_SUCCESS;
	am_status_t status_tmp = AM_SUCCESS;
	CHAR* buffer_page = NULL;
	char *stickySessionValue = NULL;
	char *stickySessionPos = NULL;
	char *temp_uri = NULL;
	*page = NULL;

	if(requestURL == NULL) {
		status = AM_INVALID_ARGUMENT;
	}
	// Check if magic URI is present in the URL
	if(status == AM_SUCCESS) {
		post_data_query = strstr(requestURL, POST_PRESERVE_URI);
		if (post_data_query != NULL) {
			post_data_query += strlen(POST_PRESERVE_URI);
			// Check if a query parameter for the  sticky session has been
			// added to the dummy URL. Remove it if it is the case.
			status_tmp = am_web_get_postdata_preserve_URL_parameter
				(&stickySessionValue, agent_config);
			if (status_tmp == AM_SUCCESS) {
				stickySessionPos = strstr((char *)post_data_query, stickySessionValue);
				if (stickySessionPos != NULL) {
					size_t len = strlen(post_data_query) -
						strlen(stickySessionPos)-1;
					temp_uri = (char *)malloc(len+1);
					memset(temp_uri,0,len+1);
					strncpy(temp_uri, post_data_query, len);
					post_data_query = temp_uri;
				}
			}
		}
	}
	// If magic uri present search for corresponding value in hashtable
	if((status == AM_SUCCESS) && (post_data_query != NULL) &&
		(strlen(post_data_query) > 0))
	{
		am_web_log_debug("%s: POST Magic Query Value: %s",
			thisfunc, post_data_query);
		if(am_web_postcache_lookup(post_data_query, &get_data,
			agent_config) == B_TRUE) {
				postdata_cache = get_data.value;
				actionurl = get_data.url;
				am_web_log_debug("%s: POST hashtable actionurl: %s",
					thisfunc, actionurl);
				// Create the post page
				buffer_page = am_web_create_post_page(post_data_query,
					postdata_cache,actionurl, agent_config);
				*page = strdup(buffer_page);
				if (*page == NULL) {
					am_web_log_error("%s: Not enough memory to allocate page");
					status = AM_NO_MEMORY;
				}
				am_web_postcache_data_cleanup(&get_data);
				if (buffer_page != NULL) {
					am_web_free_memory(buffer_page);
				}
		} else {
			am_web_log_error("%s: Found magic URI (%s) but entry is not in POST"
				" hash table",thisfunc, post_data_query);
			status = AM_FAILURE;
		}
	}
	if (temp_uri != NULL) {
		free(temp_uri);
		temp_uri = NULL;
	}
	if (stickySessionValue != NULL) {
		am_web_free_memory(stickySessionValue);
		stickySessionValue = NULL;
	}
	return status;
}

// This function is called when the preserve post data feature is enabled
// to send the original request with the original post data after the
// iPlanetDirectoryPro value has been obtained from the CDC servlet
REQUEST_NOTIFICATION_STATUS send_post_data(IHttpContext* pHttpContext, char *page,
										   char *set_cookies_list)
{
	const char *thisfunc = "send_post_data()";
	size_t page_len = 0;
	HRESULT hr;
	REQUEST_NOTIFICATION_STATUS retStatus = RQ_NOTIFICATION_CONTINUE;
	USHORT content_len = 0;

	IHttpResponse* res = pHttpContext->GetResponse();

	page_len = strlen(page);

	am_web_log_debug("%s: Cookies sent with post form:\n%s",
		thisfunc, set_cookies_list);
	am_web_log_debug("%s: Post form:\n%s", thisfunc, page);

	hr = res->SetStatus(200,"Status OK",0, S_OK);
	hr = res->SetHeader("Content-Type","text/html",
		(USHORT)strlen("text/html"),TRUE);
	content_len = (USHORT)page_len;
	if (set_cookies_list != NULL)
		content_len += 0;    // strlen(set_cookies_list);

	char buff[256];
	itoa(content_len,buff,10);
	hr = res->SetHeader("Content-Length",buff,
		(USHORT)strlen(buff),TRUE);

	if (set_cookies_list != NULL) 
		set_headers_in_context(pHttpContext, set_cookies_list, FALSE);

	//Send the post page
	void * pvBuffer = pHttpContext->AllocateRequestMemory(page_len);

	if (pvBuffer == NULL) {
		am_web_log_error("%s: AllocRequestMemory Failed.", thisfunc);
		return RQ_NOTIFICATION_FINISH_REQUEST;
	}
	DWORD cbSent;
	HTTP_DATA_CHUNK dataChunk;
	strcpy((char *)pvBuffer,page);
	dataChunk.DataChunkType = HttpDataChunkFromMemory;
	dataChunk.FromMemory.pBuffer = (PVOID) pvBuffer;
	dataChunk.FromMemory.BufferLength = (USHORT) page_len;
	hr = res->WriteEntityChunks(&dataChunk,1,FALSE,FALSE,&cbSent);

	BOOL fCompletionExpected = false;
	hr = res->Flush(false,false,&cbSent,&fCompletionExpected);
	return RQ_NOTIFICATION_FINISH_REQUEST;

	if (FAILED(hr)) {
		am_web_log_error("%s: WriteClient did not succeed: "
			"Attempted message = %s ", thisfunc, page);
		return RQ_NOTIFICATION_FINISH_REQUEST;
	}

	return retStatus;
}

static void send_ok(IHttpContext* pHttpContext) 
{
	am_web_log_debug("send_ok(): sending http response ok");
       HRESULT hr;
	IHttpResponse* pHttpResponse = pHttpContext->GetResponse();
	pHttpResponse->Clear();
       PCSTR pszBuffer;
	pszBuffer = "OK";
	hr = pHttpResponse->SetStatus(200,"Status OK",0, S_OK);
	hr = pHttpResponse->SetHeader("Content-Type", "text/plain", (USHORT)strlen("text/plain"), TRUE);
	hr = pHttpResponse->SetHeader("Content-Length", "2", (USHORT)strlen("2"),TRUE);
	if (FAILED(hr)) {
		am_web_log_error("send_ok(): SetHeader failed.");
	}
       HTTP_DATA_CHUNK dataChunk;
	dataChunk.DataChunkType = HttpDataChunkFromMemory;
	DWORD cbSent;
	dataChunk.FromMemory.pBuffer = (PVOID) pszBuffer;
	dataChunk.FromMemory.BufferLength = (USHORT) strlen(pszBuffer);
	hr = pHttpResponse->WriteEntityChunks(&dataChunk,1,FALSE,TRUE,&cbSent);
	if (FAILED(hr)) {
		am_web_log_error("send_ok(): WriteEntityChunks failed.");
	}
}

/*
*This function gets invoked at every request by OnBeginRequest.
*
* */
REQUEST_NOTIFICATION_STATUS ProcessRequest(IHttpContext* pHttpContext, 
										   IHttpEventProvider* pProvider)
{
	const char* thisfunc = "ProcessRequest";
	am_status_t status = AM_SUCCESS;
	am_status_t status_tmp = AM_SUCCESS;
	REQUEST_NOTIFICATION_STATUS retStatus = RQ_NOTIFICATION_CONTINUE;
	string requestURL;
	string origRequestURL;
	string pathInfo;
	PCSTR reqMethod = NULL;
	char* requestMethod = NULL;
	DWORD requestMethodSize = 0;
	CHAR* orig_req_method = NULL;
	CHAR* dpro_cookie = NULL;
	BOOL isLocalAlloc = FALSE;    
	BOOL redirectRequest = FALSE;
	CHAR* post_page = NULL;
	CHAR *set_cookies_list = NULL;
	CHAR *set_headers_list = NULL;
	CHAR *request_hdrs = NULL;
	const char *clientIP_hdr_name = NULL;
	const char *clientHostname_hdr_name = NULL;
	PCSTR clientIP_hdr = NULL;
	PCSTR clientHostname_hdr = NULL;
	char *clientIP = NULL;
	BOOL isClientIPLocalAlloc = TRUE;
	char *clientHostname = NULL;
	char* logout_url = NULL;
	CHAR *tmpPecb = NULL;
	am_map_t env_parameter_map = NULL;
	tOphResources OphResources = RESOURCE_INITIALIZER;
	tOphResources* pOphResources = &OphResources;
	void *args[] = {(void *) tmpPecb, (void *) &set_headers_list,
		(void *) &set_cookies_list, (void *) &request_hdrs };
	void *agent_config=NULL;
	string response = "";


	IHttpRequest* req = pHttpContext->GetRequest();
	IHttpResponse* res = pHttpContext->GetResponse();

	am_web_log_debug("ProcessRequest -- Starting");

	if (readAgentConfigFile == FALSE) {
		EnterCriticalSection(&initLock);
		if (readAgentConfigFile == FALSE) {
			loadAgentPropertyFile(pHttpContext);
			readAgentConfigFile = TRUE;
		}
		LeaveCriticalSection(&initLock);
	}
	// Initialize agent
	if(agentInitialized != B_TRUE){
		EnterCriticalSection(&initLock);
		if(agentInitialized != B_TRUE){
			am_web_log_debug("%s: Will call init", thisfunc);
			init_at_request(); 
			if(agentInitialized != B_TRUE){
				am_web_log_error("%s: Agent intialization failed.", thisfunc);
				do_deny(pHttpContext);
				retStatus = RQ_NOTIFICATION_FINISH_REQUEST;
				LeaveCriticalSection(&initLock);
				return retStatus;
			}  else {
				am_web_log_debug("ProcessRequest: Agent intialized");
			}
		}
		LeaveCriticalSection(&initLock);
	}
	agent_config = am_web_get_agent_configuration();
	if ((am_web_is_cdsso_enabled(agent_config) == B_TRUE)){
		isCdssoEnabled = TRUE;
	}
	req->SetHeader("Cache-Control","no-cache",(USHORT)strlen("no-cache"),TRUE);
	res->SetHeader("Cache-Control","no-store",(USHORT)strlen("no-store"),TRUE);
	res->DisableKernelCache(9);

	// Get the request url
	status = get_request_url(pHttpContext, requestURL, origRequestURL,
		pathInfo, agent_config);
	// Handle notification
	if ((status == AM_SUCCESS) &&
		(B_TRUE == am_web_is_notification(origRequestURL.c_str(), 
		agent_config)))
	{ 
		string data="";
		GetEntity(pHttpContext, data);
		am_web_handle_notification(data.c_str(), data.size(), agent_config);
		OphResourcesFree(pOphResources);
		send_ok(pHttpContext);
		retStatus = RQ_NOTIFICATION_FINISH_REQUEST;
		am_web_delete_agent_configuration(agent_config);
		return retStatus;
	}
	// Get the request method
	if (status == AM_SUCCESS) {
		status = GetVariable(pHttpContext,"REQUEST_METHOD", &reqMethod,
			&requestMethodSize, TRUE);
	}
	if (status == AM_SUCCESS) {
		if(requestMethodSize > 0) {
			requestMethod = (char*)malloc(requestMethodSize + 1);
			if (requestMethod != NULL) {
				memset(requestMethod, 0, requestMethodSize+1);
				strncpy(requestMethod, (char*)reqMethod, requestMethodSize);
				am_web_log_debug("%s: requestMethod = %s", 
					thisfunc, requestMethod);
			} else {
				am_web_log_error("%s: Not enough memory to ", 
					"allocate orig_req_method.", thisfunc);
				status = AM_NO_MEMORY;
			}
		}
	}
	// Get the HTTP_COOKIE header
	if (status == AM_SUCCESS) {
		status = GetVariable(pHttpContext,"HTTP_COOKIE", 
			&pOphResources->cookies, &pOphResources->cbCookies, FALSE);
	}
	// Check for SSO Token in Http Cookie
	if (status == AM_SUCCESS) {
		if (pOphResources->cbCookies > 0) {
			char *cookieValue = NULL;
			int length = 0;
			int i = 0;
			const char *cookieName = am_web_get_cookie_name(agent_config);
			// Look for the iPlanetDirectoryPro cookie
			if (cookieName != NULL) {
				cookieValue = strstr((char *)(pOphResources->cookies), cookieName);
				while (cookieValue) {
					char *marker = strstr(cookieValue+1, cookieName);
					if (marker) {
						cookieValue = marker;
					} else {
						break;
					}
				}
				if (cookieValue != NULL) {
					cookieValue = strchr(cookieValue ,'=');
				}
				if (cookieValue != NULL) {
					cookieValue = &cookieValue[1]; // 1 vs 0 skips over '='
					// find the end of the cookie
					length = 0;
					for (i=0;(cookieValue[i] != ';') &&
						(cookieValue[i] != '\0'); i++) {
							length++;
					}
					if (length < URL_SIZE_MAX-1) {
						if (length > 0) {
							dpro_cookie = (CHAR *) malloc(length+1);
							if (dpro_cookie != NULL) {
								strncpy(dpro_cookie, cookieValue, length);
								dpro_cookie[length] = '\0';
								isLocalAlloc = TRUE;
								am_web_log_debug("%s: SSO token found in "
									" cookie header.", thisfunc);
							} 
							else {
								am_web_log_error("%s: Unable to allocate memory"
									" for cookie, size = %u", thisfunc, length);
								status = AM_NO_MEMORY;
							}
						}
					}
				}
			}
		}
	}

	if (status == AM_SUCCESS) {
		if (B_TRUE == am_web_is_postpreserve_enabled(agent_config)) {
			status = check_for_post_data(pHttpContext, (char *)requestURL.c_str(), &post_page,
				agent_config);
		}
	}

	// Create the environment map
	if (status == AM_SUCCESS) {
		status = am_map_create(&env_parameter_map);
	}
	// If there is a proxy in front of the agent, the user can set in the
	// properties file the name of the headers that the proxy uses to set
	// the real client IP and host name. In that case the agent needs
	// to use the value of these headers to process the request
	//
	// Get the client IP address header set by the proxy, if there is one

	if (status == AM_SUCCESS) {
		clientIP_hdr_name = (PCSTR) am_web_get_client_ip_header_name(agent_config);
		if (clientIP_hdr_name != NULL) {
			status = GetVariable(pHttpContext,clientIP_hdr_name, 
				&clientIP_hdr, NULL, FALSE);
		}
	}
	// Get the client host name header set by the proxy, if there is one
	if (status == AM_SUCCESS) {
		clientHostname_hdr_name = 
			(PCSTR) am_web_get_client_hostname_header_name(agent_config);
		if (clientHostname_hdr_name != NULL) {
			status = GetVariable(pHttpContext,clientHostname_hdr_name, 
				&clientHostname_hdr, NULL, FALSE);

		}
	}
	// If the client IP and host name headers contain more than one
	// value, take the first value.
	if (status == AM_SUCCESS) {
		if ((clientIP_hdr != NULL) || (clientHostname_hdr != NULL)) {
			status = am_web_get_client_ip_host((char *) clientIP_hdr,
				(char *) clientHostname_hdr,
				&clientIP, &clientHostname);
		}
	}
	// Set the IP address and host name in the environment map
	if ((status == AM_SUCCESS) && (clientIP != NULL)) {
		isClientIPLocalAlloc = FALSE;
		status = am_web_set_host_ip_in_env_map(clientIP, clientHostname,
			env_parameter_map, agent_config);
	}
	// If the client IP was not obtained previously,
	// get it from the REMOTE_ADDR header.
	if ((status == AM_SUCCESS) && (clientIP == NULL)) {
		PCSTR tmpClientIP = NULL;
		DWORD tmpClientIPLength = 0;
		status = GetVariable(pHttpContext,"REMOTE_ADDR", 
			&tmpClientIP, &tmpClientIPLength, FALSE);
		isClientIPLocalAlloc = TRUE;
		clientIP = (char*)malloc(tmpClientIPLength + 1);
		memset(clientIP, 0, tmpClientIPLength + 1);
		strncpy(clientIP, (char*)tmpClientIP, tmpClientIPLength);
	}

	//  process post data in CDSSO
	if (status == AM_SUCCESS) 
	{
		//In CDSSO mode, check if the sso token is in the post data
		if ((am_web_is_cdsso_enabled(agent_config) == B_TRUE) && 
			(strcmp(requestMethod, REQUEST_METHOD_POST) == 0)) 
		{
			if (dpro_cookie == NULL && (post_page != NULL ||
				am_web_is_url_enforced(requestURL.c_str(), pathInfo.c_str(), 
				clientIP, agent_config) == B_TRUE))
			{
				GetEntity(pHttpContext, response);
				if (status == AM_SUCCESS) {
					//Set original method to GET
					orig_req_method = strdup(REQUEST_METHOD_GET);
					if (orig_req_method != NULL) {
						am_web_log_debug("%s: Request method set to GET.", 
							thisfunc);
					} else {
						am_web_log_error("%s: Not enough memory to ", 
							"allocate orig_req_method.", thisfunc);
						status = AM_NO_MEMORY;
					}
					if (status == AM_SUCCESS) {
						pHttpContext->GetRequest()->SetHttpMethod(orig_req_method);
						if(dpro_cookie != NULL) {
							free(dpro_cookie);
							dpro_cookie = NULL;
						}
						char* req_url= new char [requestURL.size()+1];
						strcpy(req_url,requestURL.c_str());
						status = am_web_check_cookie_in_post(args, &dpro_cookie, 
							&req_url, &orig_req_method, requestMethod,
							(char*)response.c_str(), B_FALSE, set_cookie, 
							set_method, agent_config);
						if (status == AM_SUCCESS) {
							requestURL = req_url;
							isLocalAlloc = FALSE;
							am_web_log_debug("%s: SSO token found in "
								"assertion.",thisfunc);
							redirectRequest = TRUE;
						} else {
							am_web_log_debug("%s: SSO token not found in "
								"assertion. Redirecting to login page.",
								thisfunc);
							status = AM_INVALID_SESSION;
						}
						if(req_url != NULL) {
							delete [] req_url;
							req_url = NULL;
						}
					}
				}
			}
		}
	}
	// Check if the user is authorized to access the resource.
	if (status == AM_SUCCESS) {
		status = am_web_is_access_allowed(dpro_cookie, requestURL.c_str(),
			pathInfo.c_str(), requestMethod,
			clientIP,
			env_parameter_map,
			&OphResources.result,
			agent_config);
		am_web_log_debug("%s: status after "
			"am_web_is_access_allowed = %s (%d)",thisfunc,
			am_status_to_string(status), status);
		am_map_destroy(env_parameter_map);
	}

	//  Check for status and proceed accordingly
	switch(status) {
		case AM_SUCCESS:
			if (am_web_is_logout_url(requestURL.c_str(), agent_config) 
				== B_TRUE)
			{
				(void)am_web_logout_cookies_reset(reset_cookie, args, 
					agent_config);
			}
			status = am_web_result_attr_map_set(&OphResources.result,
				set_header, set_cookie_in_response,
				set_header_attr_as_cookie,
				get_cookie_sync, args, agent_config);
			if (status == AM_SUCCESS) {
				if ((set_headers_list != NULL) || (set_cookies_list != NULL) 
					|| (redirectRequest == TRUE)) {
						status = set_request_headers(pHttpContext, args);
				}
			}
			if (status == AM_SUCCESS) {
				if (post_page != NULL) {
					char *lbCookieHeader = NULL;
					// If post_ page is not null it means that the request 
					// contains the "/dummypost/sunpostpreserve" string and
					// that the post data of the original request need to be
					// posted.
					// If using a LB cookie, it needs to be set to NULL there.
					// If am_web_get_postdata_preserve_lbcookie() returns
					// AM_INVALID_ARGUMENT, it means that the sticky session
					// feature is disabled (ie no LB) or that the sticky
					// session mode is URL.
					status_tmp = am_web_get_postdata_preserve_lbcookie(
						&lbCookieHeader, B_TRUE,
						agent_config);
					if (status_tmp == AM_NO_MEMORY) {
						retStatus = RQ_NOTIFICATION_FINISH_REQUEST;
					} else {
						if (status_tmp == AM_SUCCESS) {
							am_web_log_debug("%s: Setting LB cookie for "
								"post data preservation to null.",
								thisfunc);
							set_cookie(lbCookieHeader, args);
						}
						retStatus = send_post_data(pHttpContext, post_page,
							set_cookies_list);
					}
					if (lbCookieHeader != NULL) {
						am_web_free_memory(lbCookieHeader);
						lbCookieHeader = NULL;
					}
				} else { 
					if (set_cookies_list != NULL && strlen(set_cookies_list) > 0) {
						//this call sets only cookies
						set_headers_in_context(pHttpContext, set_cookies_list, FALSE);
					}
					//now set remote user
					if (pOphResources->result.remote_user != NULL) {
						const char * ruser = pOphResources->result.remote_user;
						wchar_t *remoteUser = (wchar_t *)pHttpContext->
							AllocateRequestMemory((DWORD) (strlen(ruser)+1)
							* sizeof(wchar_t));
						mbstowcs( remoteUser, ruser, strlen(ruser) + 1);
						pHttpContext->SetServerVariable("REMOTE_USER", remoteUser);
					}
					if (redirectRequest == TRUE) {
						am_web_log_debug("%s: Request redirected to orignal url "
							"after return from CDC servlet",thisfunc);

						retStatus = redirect_to_request_url(pHttpContext,
							requestURL.c_str(), request_hdrs);
					} else {
						retStatus = RQ_NOTIFICATION_CONTINUE;
					}
					if (set_cookies_list != NULL) {
						free(set_cookies_list);
						set_cookies_list = NULL;
					}
				}
			}
			break;

		case AM_INVALID_SESSION:
			am_web_log_info("%s: Invalid session.",thisfunc);
                        // reset ldap cookies on invalid session.
			am_web_do_cookies_reset(reset_cookie, args, agent_config);
			// reset the CDSSO cookie 
			if (am_web_is_cdsso_enabled(agent_config) == B_TRUE) {
				am_status_t cdStatus = am_web_do_cookie_domain_set(set_cookie, args, EMPTY_STRING, agent_config);
				if(cdStatus != AM_SUCCESS) {
					am_web_log_error("%s: CDSSO reset cookie failed",thisfunc);
				}
			}
			// If the post data preservation feature is enabled
			// save the post data in the cache for post requests.
			if (strcmp(requestMethod, REQUEST_METHOD_POST) == 0
				&& B_TRUE == am_web_is_postpreserve_enabled(agent_config))
			{
				status = process_request_with_post_data_preservation
					(pHttpContext, status, &pOphResources->result,
					(char *)requestURL.c_str(), args, (char *)response.c_str(), agent_config);
			} else {
				status = do_redirect(pHttpContext, status, &OphResources.result,
					requestURL.c_str(), requestMethod, args, agent_config);
                                retStatus = RQ_NOTIFICATION_FINISH_REQUEST;
			}
			{
				HRESULT hr;
				// Buffer to store the byte count.
				DWORD cbSent = 0;
				// Buffer to store if asyncronous completion is pending.
				BOOL fCompletionExpected = false;
				hr = res->Flush(false,false,&cbSent,&fCompletionExpected);

			}
			break;

		case AM_ACCESS_DENIED:
			am_web_log_info("%s: Access denied to %s",thisfunc,
				OphResources.result.remote_user ?
				OphResources.result.remote_user : "unknown user");
			// reset ldap and CDSSO cookies
			if (am_web_is_cdsso_enabled(agent_config) == B_TRUE) {
                            am_web_do_cookies_reset(reset_cookie, args, agent_config);
                            am_status_t cdStatus = am_web_do_cookie_domain_set(set_cookie, args, EMPTY_STRING, agent_config);
                            if(cdStatus != AM_SUCCESS) {
                                am_web_log_error("%s: CDSSO reset cookie failed",thisfunc);
                            }
			}
			// If the post data preservation feature is enabled
			// save the post data in the cache for post requests.
			// This needs to be done when the access has been denied
			// in case there is a composite advice.
			if (strcmp(requestMethod, REQUEST_METHOD_POST) == 0
				&& B_TRUE == am_web_is_postpreserve_enabled(agent_config))
			{
				status = process_request_with_post_data_preservation
					(pHttpContext, status, &pOphResources->result,
					(char *)requestURL.c_str(), args, (char *)response.c_str(), agent_config);
			} else {
				status = do_redirect(pHttpContext, status, &OphResources.result,
					requestURL.c_str(), requestMethod, 
					args, agent_config);
                                retStatus = RQ_NOTIFICATION_FINISH_REQUEST;
			}
                        {
				HRESULT hr;
				// Buffer to store the byte count.
				DWORD cbSent = 0;
				// Buffer to store if asynchronous completion is pending.
				BOOL fCompletionExpected = false;
				hr = res->Flush(false,false,&cbSent,&fCompletionExpected);
			}
			break;

		case AM_INVALID_FQDN_ACCESS:
			am_web_log_info("%s: Invalid FQDN access",thisfunc);
			status = do_redirect(pHttpContext, status, &OphResources.result,
				requestURL.c_str(), requestMethod, 
				args, agent_config);
                        retStatus = RQ_NOTIFICATION_FINISH_REQUEST;
			break;

		case AM_REDIRECT_LOGOUT:
			status = am_web_get_logout_url(&logout_url, agent_config);
			if(status == AM_SUCCESS) {
				if (am_web_is_cdsso_enabled(agent_config) == B_TRUE) {
					am_status_t cdStatus = 
						am_web_do_cookie_domain_set(set_cookie, args, 
						EMPTY_STRING, agent_config);
					if (set_cookies_list != NULL &&
						strlen(set_cookies_list) > 0) 
					{
						set_headers_in_context(pHttpContext, set_cookies_list, FALSE);
					}
					if(cdStatus != AM_SUCCESS) {
					}
				}
				res->Redirect(logout_url, true, false);
                                retStatus = RQ_NOTIFICATION_FINISH_REQUEST;
			} else {
				am_web_log_debug("%s: am_web_get_logout_url failed. ");
				retStatus = RQ_NOTIFICATION_FINISH_REQUEST;
			}
			if (set_cookies_list != NULL) {
				free(set_cookies_list);
				set_cookies_list = NULL;
			}
			am_web_free_memory(logout_url);
			break;

		case AM_INVALID_ARGUMENT:
		case AM_NO_MEMORY:
		case AM_FAILURE:
		default:
			am_web_log_error("%s: status: %s (%d)",thisfunc,
				am_status_to_string(status), status);
			HRESULT hr = res->SetStatus(500,"Internal Server Error", 0, S_OK);
			if (FAILED(hr)) {
				am_web_log_error("%s: Cannot set status to 500 .",thisfunc);
			}
			break;
	}

	if (requestMethod != NULL) {
		free(requestMethod);
		requestMethod = NULL;
	}

	if (request_hdrs != NULL) {
		free(request_hdrs);
		request_hdrs = NULL;
	}

	if(dpro_cookie != NULL) {
		if(isLocalAlloc) {
			free(dpro_cookie);
		} else {
			am_web_free_memory(dpro_cookie);
		}
		dpro_cookie = NULL;
	}

	if (orig_req_method != NULL) {
		free(orig_req_method);
		orig_req_method = NULL;
	}
	if(clientIP != NULL) {
		if(isClientIPLocalAlloc) {
			free(clientIP);
		} else {
			am_web_free_memory(clientIP);
		}
		clientIP = NULL;
	}
	if (clientHostname != NULL) {
		am_web_free_memory(clientHostname);
		clientHostname = NULL;
	}

	OphResourcesFree(pOphResources);
	am_web_delete_agent_configuration(agent_config);

	return retStatus;
}

/*
* This function loads the bootstrap and the configuration files and 
* invokes am_web_init.
*
* */
BOOL loadAgentPropertyFile(IHttpContext* pHttpContext)
{
	BOOL gotInstanceId = FALSE;
	PCSTR instanceId = NULL;
	DWORD instanceIdSize = 0;
	CHAR* agent_bootstrap_file  = NULL;
	am_status_t status = AM_SUCCESS;
	am_status_t polsPolicyStatus = AM_SUCCESS;
	BOOL statusContinue      = FALSE;
	CHAR debugMsg[2048]   = "";
	char *agent_config_file = NULL;    

	agentConfig.bAgentInitSuccess = FALSE; 

	if(S_OK == (pHttpContext->GetServerVariable("INSTANCE_ID", &instanceId, 
		&instanceIdSize)))
	{
		instanceId = (PCSTR)pHttpContext->AllocateRequestMemory(instanceIdSize+1);
		if(instanceId == NULL)
		{
			sprintf(debugMsg, "%d: Could not allocate memory", instanceIdSize);
			status = AM_NO_MEMORY;
		}
		else
		{
			if(S_OK != (pHttpContext->GetServerVariable("INSTANCE_ID", &instanceId, 
				&instanceIdSize)))
			{
				sprintf(debugMsg, "%d: Invalid Instance Id ", instanceIdSize);
				status = AM_FAILURE;
			}
		}
	}
	else
	{
		sprintf(debugMsg, "GetServerVariable failed.");
		status = AM_FAILURE;
	}


	if (status == AM_SUCCESS) {
		string instanceIdStr = instanceId;


		BOOL getPath = iisaPropertiesFilePathGet(&agent_bootstrap_file, 
			instanceIdStr, TRUE);

		if (getPath == FALSE) {
			sprintf(debugMsg,"%s: iisaPropertiesFilePathGet() failed.", 
				agentDescription);
			logPrimitive(debugMsg);
			free(agent_bootstrap_file);
			agent_bootstrap_file = NULL;
			SetLastError(IISA_ERROR_PROPERTIES_FILE_PATH_GET);
			return FALSE;
		}


	}

	if (iisaPropertiesFilePathGet(&agent_config_file, instanceId, FALSE)
		== FALSE) {
			sprintf(debugMsg, "%s: iisaPropertiesFilePathGet() returned failure",
				agentDescription);
			logPrimitive(debugMsg);
			free(agent_config_file);
			agent_config_file = NULL;
			SetLastError(IISA_ERROR_PROPERTIES_FILE_PATH_GET);
			return FALSE;
	}

	polsPolicyStatus = am_web_init(agent_bootstrap_file, agent_config_file);
	free(agent_bootstrap_file);
	agent_bootstrap_file = NULL;
	free(agent_config_file);
	agent_config_file = NULL;

	if (AM_SUCCESS != polsPolicyStatus) {
		// Use logPrimitive() AND am_web_log_error() here since a policy_init()
		//   failure could mean am_web_log_error() isn't initialized.
		sprintf(debugMsg,
			"%s: Initialization of the agent failed: status = %s (%d)",
			agentDescription, am_status_to_string(polsPolicyStatus),
			polsPolicyStatus);
		logPrimitive(debugMsg);
		SetLastError(IISA_ERROR_INIT_POLICY);
		return FALSE;
	}

	// Record success initializing agent.
	agentConfig.bAgentInitSuccess = TRUE;
	return TRUE;
}

/*
* This function retirieves the location of config files from the registry and 
* returns the complete path of the files.
*
* */
BOOL iisaPropertiesFilePathGet(CHAR** propertiesFileFullPath, string instanceId,
							   BOOL isBootStrapFile)
{
	// Max WINAPI path
	const DWORD dwPropertiesFileFullPathSize = MAX_PATH + 1;
	CHAR szPropertiesFileName[1000];
	string agentApplicationSubKey = "Software\\Sun Microsystems\\OpenSSO IIS7 Agent\\Identifier_";
	const CHAR agentDirectoryKeyName[]       = "Path";
	DWORD dwPropertiesFileFullPathLen        = dwPropertiesFileFullPathSize;
	HKEY hKey                                = NULL;
	LONG lRet                                = ERROR_SUCCESS;
	CHAR debugMsg[2048]                      = "";


	if(isBootStrapFile) {
		strcpy(szPropertiesFileName,"OpenSSOAgentBootstrap.properties");
	}
	else {
		strcpy(szPropertiesFileName,"OpenSSOAgentConfiguration.properties");
	}

	if(!instanceId.empty()) {
		agentApplicationSubKey.append(instanceId);
	}
	///////////////////////////////////////////////////////////////////
	//  get the location of the properties file from the registry
	lRet = RegOpenKeyEx(HKEY_LOCAL_MACHINE, agentApplicationSubKey.c_str(),
		0, KEY_READ, &hKey);


	if(lRet != ERROR_SUCCESS) {
		sprintf(debugMsg,
			"%s(%d) Opening registry key %s%s failed with error code %d",
			__FILE__, __LINE__, "HKEY_LOCAL_MACHINE\\",
			agentApplicationSubKey, lRet);
		logPrimitive(debugMsg);
		return FALSE;
	}

	// free'd by caller, even when there's an error.
	*propertiesFileFullPath = (CHAR*) malloc(dwPropertiesFileFullPathLen);
	if (*propertiesFileFullPath == NULL) {
		sprintf(debugMsg,
			"%s(%d) Insufficient memory for propertiesFileFullPath %d bytes",
			__FILE__, __LINE__, dwPropertiesFileFullPathLen);
		logPrimitive(debugMsg);
		return FALSE;
	}
	lRet = RegQueryValueEx(hKey, agentDirectoryKeyName, NULL, NULL,
		(LPBYTE)*propertiesFileFullPath,
		&dwPropertiesFileFullPathLen);
	string filePath = *propertiesFileFullPath;
	if (lRet != ERROR_SUCCESS || *propertiesFileFullPath == NULL ||
		(*propertiesFileFullPath)[0] == '\0') {
			sprintf(debugMsg,
				"%s(%d) Reading registry value %s\\%s\\%s failed with error code %d",
				__FILE__, __LINE__,
				"HKEY_LOCAL_MACHINE\\", agentApplicationSubKey,
				agentDirectoryKeyName, lRet);
			logPrimitive(debugMsg);
			return FALSE;
	}
	if (*propertiesFileFullPath &&
		(**propertiesFileFullPath == '\0')) {
			sprintf(debugMsg,
				"%s(%d) Properties file directory path is NULL.",
				__FILE__, __LINE__);
			logPrimitive(debugMsg);
			return FALSE;
	}
	if (*(*propertiesFileFullPath + dwPropertiesFileFullPathLen - 1) !=
		'\0') {
			sprintf(debugMsg,
				"%s(%d) Properties file directory path missing NULL termination.",
				__FILE__, __LINE__);
			logPrimitive(debugMsg);
			return FALSE;
	}
	// closes system registry
	RegCloseKey(hKey);
	if ((strlen(*propertiesFileFullPath) + 2 /* size of \\ */ +
		strlen(szPropertiesFileName) + 1) > dwPropertiesFileFullPathSize) {
			sprintf(debugMsg,
				"%s(%d) Properties file directory path exceeds Max WINAPI path.",
				__FILE__, __LINE__);
			logPrimitive(debugMsg);
			return FALSE;
	}
	strcat(*propertiesFileFullPath, "\\");
	strcat(*propertiesFileFullPath, szPropertiesFileName);
	filePath = *propertiesFileFullPath;
	return TRUE;
}


/*
* Logs into the event viewer logs as Application Error. 
* This is used when the policy agent logging is not initialized yet.
*
* */
void logPrimitive(CHAR *message)
{
	HANDLE hes        = NULL;
	const CHAR* rsz[] = {message};

	if (message == NULL) {
		return;
	}

	hes = RegisterEventSource(0, agentDescription);
	if (hes) {
		ReportEvent(hes, EVENTLOG_ERROR_TYPE, 0, 0, 0, 1, 0, rsz, 0);
		DeregisterEventSource(hes);
	}
}

/*
*Invoked when the agent module DLL is unloaded at shutdown.
*
* */
void TerminateAgent()
{
	am_status_t status = am_web_cleanup();
	DeleteCriticalSection(&initLock);
}

/*
* Retrieves the complete request URL in the context.
*
* */
am_status_t get_request_url(IHttpContext* pHttpContext,
							string& requestURLStr,
							string& origRequestURLStr,
							string& pathInfo,
							void* agent_config)
{
	const char *thisfunc = "get_request_url()";
	PCSTR requestHostHeader = NULL;
	const char* requestProtocol = NULL;
	PCSTR requestProtocolType  = NULL;
	CHAR  defaultPort[TCP_PORT_ASCII_SIZE_MAX + 1] = "";
	PCSTR requestPort = NULL;
	PCSTR queryString = NULL;
	PCSTR baseUrl = NULL;
	PCSTR tmpPathInfo = NULL;
	PCSTR scriptName = NULL;
	char *requestURL = NULL;
	char *origRequestURL = NULL;

	am_status_t status = AM_SUCCESS;

	// Get the protocol type (HTTP / HTTPS)
	status = GetVariable(pHttpContext,"HTTPS", &requestProtocolType, 
		NULL, TRUE);
	if (status == AM_SUCCESS) 
	{
		if(strncmp(requestProtocolType,"on", 2) == 0) {
			requestProtocol = httpsProtocol;
			strcpy(defaultPort, httpsPortDefault);
		} else if(strncmp(requestProtocolType,"off", 3) == 0) {
			requestProtocol = httpProtocol;
			strcpy(defaultPort, httpPortDefault);
		}
		// Get the host name
		status = GetVariable(pHttpContext,"HEADER_Host", 
			&requestHostHeader, NULL, TRUE);
	}
	// Get the port
	if (status == AM_SUCCESS) {
		const char* colon_ptr = strchr(requestHostHeader, ':');
		if (colon_ptr != NULL) {
			requestPort = (PCSTR)pHttpContext->
				AllocateRequestMemory((DWORD) (strlen(colon_ptr))
				+ 1 );
			if (requestPort != NULL) {
				strncpy((char *)requestPort, colon_ptr + 1, 
					strlen(colon_ptr)-1);
				am_web_log_debug("%s: port = %s", thisfunc, requestPort);
			} else {
				am_web_log_error("%s: Unable to allocate requestPort.",
					thisfunc);
				status = AM_NO_MEMORY;
			}
		} else {
			status = GetVariable(pHttpContext,"SERVER_PORT", &requestPort, 
				NULL, TRUE);
		}
	}
	//Get the base url
	if (status == AM_SUCCESS) {
		status = GetVariable(pHttpContext,"URL", &baseUrl, 
			NULL, TRUE);
	}
	// Get the path info
	if (status == AM_SUCCESS) {
		status = GetVariable(pHttpContext,"PATH_INFO", &tmpPathInfo, 
			NULL, FALSE);
	}
	// Get the script name
	if (status == AM_SUCCESS) {
		status = GetVariable(pHttpContext,"SCRIPT_NAME", &scriptName, 
			NULL, FALSE);
	}
	//Remove the script name from path_info to get the real path info
	if (status == AM_SUCCESS) {
		if ((tmpPathInfo != NULL) && (scriptName != NULL)) {
			string pathInfoStr(tmpPathInfo);
			size_t scriptPos = pathInfoStr.find(scriptName);
			if (scriptPos != string::npos) {
				pathInfo = pathInfoStr.substr(strlen(scriptName));
				am_web_log_debug("%s: Reconstructed path info = %s",
					thisfunc, pathInfo.c_str());
			} else {
				am_web_log_warning("%s: Script name %s not found in path info"
					" (%s). Could not get the path info.",
					thisfunc, scriptName, tmpPathInfo);
			}
		}
	}
	// Get the query string
	if (status == AM_SUCCESS) {
		status = GetVariable(pHttpContext,"QUERY_STRING", &queryString, 
			NULL, FALSE);
	}
	// Construct the URL
	if (status == AM_SUCCESS) {
		size_t portNumber = atoi(requestPort);
		if (!pathInfo.empty()) {
			string fullBaseUrl(baseUrl);
			fullBaseUrl.append(pathInfo);
			status = am_web_get_all_request_urls(requestHostHeader,
				requestProtocol,NULL, portNumber,
				fullBaseUrl.c_str(), queryString,
				agent_config, 
				&requestURL, &origRequestURL);
		} else {
			status = am_web_get_all_request_urls(requestHostHeader,
				requestProtocol, NULL, portNumber,
				baseUrl, queryString,
				agent_config,
				&requestURL, &origRequestURL);
		}
		if(status == AM_SUCCESS) {
			if (requestURL != NULL) {
				requestURLStr.assign(requestURL);
				am_web_log_debug("%s: Constructed request url: %s",
					thisfunc, requestURLStr.c_str());
			}
			if (origRequestURL != NULL) {
				origRequestURLStr.assign(origRequestURL);
			}
		}
	}
	return status;
}

/*
* Retrives the server variables using GetServerVariable.
*
* */
am_status_t GetVariable(IHttpContext* pHttpContext, PCSTR varName, 
						PCSTR* pVarVal, DWORD* pVarValSize,
						BOOL isRequired)
{
	const char* thisfunc = "GetVariable()";
	am_status_t status = AM_SUCCESS;
	DWORD VarValSize = 0;

	if (pVarValSize == NULL) {
		pVarValSize = &VarValSize;
	}
	if(S_OK == (pHttpContext->GetServerVariable(varName, pVarVal, 
		pVarValSize)))
	{
		*pVarVal = (PCSTR)pHttpContext->AllocateRequestMemory((*pVarValSize)+1);
		if(*pVarVal == NULL) {
			am_web_log_error("%s: Could not allocate memory", thisfunc);
			status = AM_NO_MEMORY;
		} else {
			if(S_OK != (pHttpContext->GetServerVariable(varName, pVarVal,
				pVarValSize)))
			{
				am_web_log_error("%s: %s is not a valid server variable.", 
					thisfunc, pVarVal);
				status = AM_FAILURE;
			} else {
				if (*pVarVal != NULL && strlen(*pVarVal) > 0) {
					am_web_log_debug("%s: %s = %s", thisfunc, 
						varName, *pVarVal);
				} else {
					am_web_log_debug("%s: %s = ", thisfunc, varName);
					if (*pVarVal != NULL && strlen(*pVarVal) == 0) {
						*pVarVal = NULL;
					}
				}
			}
		}
	} else {
		if (isRequired) {
			am_web_log_error("%s: Server variable %s is not found in "
				"HttpContext.", thisfunc, varName);
			status = AM_FAILURE;
		} else {
			am_web_log_debug("%s: Server variable %s not found in HttpContext.",
				thisfunc, varName);
		}
	}
	return status;
}

void OphResourcesFree(tOphResources* pOphResources)
{
	//cookies are not freed because they are allocated
	//by httpContext.
	am_web_clear_attributes_map(&pOphResources->result);
	am_policy_result_destroy(&pOphResources->result);
	return;
}


/*
* Retrieves entity data from the request.
*
* */
void GetEntity(IHttpContext* pHttpContext, string& data)
{
	HRESULT hr;
	IHttpRequest* pHttpRequest = pHttpContext->GetRequest();
	DWORD cbBytesReceived = pHttpRequest->GetRemainingEntityBytes();
	int cb = (int)cbBytesReceived;
	void * pvRequestBody = pHttpContext->AllocateRequestMemory(cbBytesReceived);
	void * entityBody;
	data.clear();

	if (cbBytesReceived > 0)
	{
		while (pHttpRequest->GetRemainingEntityBytes() != 0)
		{
			hr = pHttpRequest->ReadEntityBody(pvRequestBody,cbBytesReceived,false,
				&cbBytesReceived,NULL);
			if (FAILED(hr)) {
				return;
			}
			data.append((char*)pvRequestBody,(int)cbBytesReceived);
		}
	}

	//set it back in the request
	entityBody = pHttpContext->AllocateRequestMemory((DWORD) data.length());
	strcpy((char*)entityBody,data.c_str());
	pHttpRequest->InsertEntityBody(entityBody, 
		(DWORD) strlen((char*)entityBody));

}


/*
* This function is invoked in CDSSO when the cookie is set in the agent's domain.
* The one set by the browser in the server domain is meaningless.
* Invoked by am_web_check_cookie_in_post. It constructs the cookie data 
* from the LARES post data and set it as a cookie here.
* */
static am_status_t set_cookie(const char *header, void **args)
{
	am_status_t status = AM_SUCCESS;
	CHAR** ptr = NULL;
	CHAR* set_cookies_list = NULL;

	if (header != NULL && args != NULL ) {
		size_t cookie_length = 0;
		char* cdssoCookie = NULL;
		char* tmpStr = NULL;

		ptr = (CHAR **) args[2];
		set_cookies_list = *ptr;

		cookie_length = strlen("Set-Cookie:") + strlen(header)
			+ strlen("\r\n");
		cdssoCookie = (char *) malloc(cookie_length + 1);

		if (status == AM_SUCCESS) {
			if (cdssoCookie != NULL) {
				sprintf(cdssoCookie, "Set-Cookie:%s\r\n", header);

				if (set_cookies_list == NULL) {
					set_cookies_list = (char *) malloc(cookie_length + 1);
					if (set_cookies_list != NULL) {
						memset(set_cookies_list, 0, sizeof(char) *
							cookie_length + 1);
						strcpy(set_cookies_list, cdssoCookie);
					} else {
						am_web_log_error("set_cookie():Not enough memory 0x%x "
							"bytes.",cookie_length + 1);
						status = AM_NO_MEMORY;
					}
				} else {
					tmpStr = set_cookies_list;
					set_cookies_list = (char *) malloc(strlen(tmpStr) +
						cookie_length + 1);
					if (set_cookies_list == NULL) {
						am_web_log_error("set_cookie():Not enough memory 0x%x "
							"bytes.",cookie_length + 1);
						status = AM_NO_MEMORY;
					} else {
						memset(set_cookies_list,0,sizeof(set_cookies_list));
						strcpy(set_cookies_list,tmpStr);
						strcat(set_cookies_list,cdssoCookie);
					}
				}
				free(cdssoCookie);

				if (tmpStr) {
					free(tmpStr);
					tmpStr = NULL;
				}
			} else {
				am_web_log_error("set_cookie():Not enough memory 0x%x bytes.",
					cookie_length + 1);
				status = AM_NO_MEMORY;
			}
		}
	} else {
		am_web_log_error("set_cookie(): Invalid arguments obtained");
		status = AM_INVALID_ARGUMENT;
	}

	if (set_cookies_list && set_cookies_list[0] != '\0') {
		am_web_log_info("set_cookie():set_cookies_list = %s", set_cookies_list);
		*ptr = set_cookies_list;
	}

	return status;
}

/*
* Not implemented.
*
* */
static void set_method(void ** args, char * orig_req)
{
}

// Function to reset all the cookies before redirecting to AM
static am_status_t reset_cookie(const char *header, void **args)
{
	am_status_t status = AM_SUCCESS;

	if (header != NULL && args != NULL) {

		size_t reset_cookie_length = 0;
		char *resetCookie = NULL;
		char *tmpStr = NULL;
		CHAR* set_cookies_list = NULL;
		CHAR** ptr = NULL;

		ptr = (CHAR **) args[2];
		set_cookies_list = *ptr;

		reset_cookie_length = strlen("Set-Cookie:") + strlen(header)
			+ strlen("\r\n");
		resetCookie = (char *) malloc(reset_cookie_length + 1);


		if (status == AM_SUCCESS) {
			if (resetCookie != NULL) {
				memset(resetCookie, 0, sizeof(char) * reset_cookie_length + 1);
				sprintf(resetCookie, "Set-Cookie:%s\r\n", header);

				if (set_cookies_list == NULL) {
					set_cookies_list = (char *) malloc(reset_cookie_length + 1);
					if (set_cookies_list != NULL) {
						memset(set_cookies_list, 0, sizeof(char) *
							reset_cookie_length + 1);
						strcpy(set_cookies_list, resetCookie);
					} else {
						am_web_log_error("reset_cookie():Not enough memory 0x%x bytes.",
							reset_cookie_length + 1);
						status = AM_NO_MEMORY;
					}
				} else {
					tmpStr = set_cookies_list;
					set_cookies_list = (char *) malloc(strlen(tmpStr) +
						reset_cookie_length + 1);
					if (set_cookies_list == NULL) {
						am_web_log_error("reset_cookie():Not enough memory 0x%x "
							"bytes.", reset_cookie_length + 1);
						status = AM_NO_MEMORY;
					} else {
						memset(set_cookies_list, 0, sizeof(set_cookies_list));
						strcpy(set_cookies_list, tmpStr);
						strcat(set_cookies_list, resetCookie);
					}
				}
				am_web_log_debug("reset_cookie(): set_cookies_list ==> %s",
					set_cookies_list);
				free(resetCookie);

				if (tmpStr != NULL) {
					free(tmpStr);
				}
				if (status != AM_NO_MEMORY) {
					*ptr = set_cookies_list;
				}

			} else {
				am_web_log_error("reset_cookie():Not enough memory 0x%x bytes.",
					reset_cookie_length + 1);
				status = AM_NO_MEMORY;
			}
		}
	} else {
		am_web_log_error("reset_cookie(): Invalid arguments obtained");
		status = AM_INVALID_ARGUMENT;
	}
	return status;
}

// Set attributes as HTTP headers
static am_status_t set_header(const char *key, const char *values, void **args)
{
	am_status_t status = AM_SUCCESS;
	CHAR** ptr = NULL;
	CHAR* set_headers_list = NULL;

	if(key != NULL && values !=NULL ){
		string skey = key;
		string svalues = values;
	}

	if (key != NULL && args != NULL ) {
		int cookie_length = 0;
		char* httpHeaderName = NULL;
		char* tmpHeader = NULL;
		size_t header_length = 0;

		ptr = (CHAR **) args[1];
		set_headers_list = *ptr;

		header_length = strlen(key) + strlen("\r\n") + 1;
		if (values != NULL) {
			header_length += strlen(values);
		}
		httpHeaderName = (char *) malloc(header_length + 1);


		if (status == AM_SUCCESS) {
			if (httpHeaderName != NULL) {
				memset(httpHeaderName, 0, sizeof(char) * (header_length + 1));
				strcpy(httpHeaderName, key);
				strcat(httpHeaderName, ":");
				if (values != NULL) {
					strcat(httpHeaderName, values);
				}
				strcat(httpHeaderName, "\r\n");

				if (set_headers_list == NULL) {
					set_headers_list = (char *) malloc(header_length + 1);
					if (set_headers_list != NULL) {
						memset(set_headers_list, 0, sizeof(char) *
							header_length + 1);
						strcpy(set_headers_list, httpHeaderName);
					} else {
						am_web_log_error("set_header():Not enough memory 0x%x "
							"bytes.",header_length + 1);
						status = AM_NO_MEMORY;
					}
				} else {
					tmpHeader = set_headers_list;
					set_headers_list = (char *) malloc(strlen(tmpHeader) +
						header_length + 1);
					if (set_headers_list == NULL) {
						am_web_log_error("set_header():Not enough memory 0x%x "
							"bytes.",header_length + 1);
						status = AM_NO_MEMORY;
					} else {
						memset(set_headers_list, 0, sizeof(set_headers_list));
						strcpy(set_headers_list, tmpHeader);
						strcat(set_headers_list, httpHeaderName);
					}
				}
				free(httpHeaderName);
				if (tmpHeader) {
					free(tmpHeader);
					tmpHeader = NULL;
				}
			} else {
				am_web_log_error("set_header():Not enough memory 0x%x bytes.",
					cookie_length + 1);
				status = AM_NO_MEMORY;
			}
		}
	} else {
		am_web_log_error("set_header(): Invalid arguments obtained");
		status = AM_INVALID_ARGUMENT;
	}

	if (set_headers_list && set_headers_list[0] != '\0') {
		am_web_log_info("set_header():set_headers_list = %s", set_headers_list);
		*ptr = set_headers_list;
	}

	return status;
}

// Set attributes as cookies
static am_status_t set_cookie_in_response(const char *header, void **args)
{
	am_status_t status = AM_SUCCESS;

	if (header != NULL && args != NULL ) {
		size_t header_length = 0;

		CHAR* httpHeader = NULL;
		CHAR* new_cookie_str = NULL;
		CHAR* tmpHeader = NULL;
		CHAR** ptr = NULL;
		CHAR* set_cookies_list = NULL;

		ptr = (CHAR **) args[2];
		set_cookies_list = *ptr;

		header_length = strlen("Set-Cookie:") + strlen("\r\n")
			+ strlen(header) + 1;
		httpHeader = (char *)malloc(header_length);
		if (httpHeader != NULL) {
			sprintf(httpHeader, "Set-Cookie:%s\r\n", header);
			if (set_cookies_list == NULL) {
				set_cookies_list = (char *) malloc(header_length + 1);
				if (set_cookies_list != NULL) {
					memset(set_cookies_list, 0, sizeof(char) *
						header_length + 1);
					strcpy(set_cookies_list, httpHeader);
				} else {
					am_web_log_error("set_cookie_in_response(): Not "
						"enough memory 0x%x bytes.",
						header_length + 1);
					status = AM_NO_MEMORY;
				}
			} else {
				tmpHeader = set_cookies_list;
				set_cookies_list = (char *)malloc(strlen(tmpHeader) +
					header_length + 1);
				if (set_cookies_list == NULL) {
					am_web_log_error("set_cookie_in_response():Not "
						"enough memory 0x%x bytes.",
						header_length + 1);
					status = AM_NO_MEMORY;
				} else {
					memset(set_cookies_list,0,sizeof(set_cookies_list));
					strcpy(set_cookies_list,tmpHeader);
					strcat(set_cookies_list, httpHeader);
				}
			}
			if (new_cookie_str) {
				am_web_free_memory(new_cookie_str);
			}
		} else {
			am_web_log_error("set_cookie_in_response(): Not enough "
				"memory 0x%x bytes.", header_length + 1);
		}
		free(httpHeader);

		if (tmpHeader != NULL) {
			free(tmpHeader);
		}

		if (status != AM_NO_MEMORY) {
			*ptr = set_cookies_list;
		}

	} else {
		am_web_log_error("set_cookie_in_response():Invalid arguments obtained");
		status = AM_INVALID_ARGUMENT;
	}
	return status;
}

/*
* Not implemented here. Similar fucntionality is implemented inside 
* set_headers_in_context.
* */
static am_status_t set_header_attr_as_cookie(const char *header, void **args)
{
	return AM_SUCCESS;
}


// Not implemented.
static am_status_t get_cookie_sync(const char *cookieName, char** dpro_cookie, void **args)
{
	am_status_t status = AM_SUCCESS;
	return status;
}

/*
* Utility function used when setting the attributes as cookies in the first request itself
* */
void ConstructReqCookieValue(string& completeString,string value)
{
	size_t c1 =0, c2=0;

	c1=value.find_first_of('=');
	c2=value.find_first_of(';');
	size_t diffr=c2-c1;
	if(diffr>1)
	{
		string newKey = value.substr(0,c1);
		string newValue = value.substr(0,c2);
		if(completeString.find(newKey) == string::npos)
		{
			completeString.append(";"+newValue);
		}
	}
}


//Sets the headers in httpContext. Used when setting attributes as headers.
am_status_t set_headers_in_context(IHttpContext *pHttpContext, 
								   string headersList, BOOL isRequest)
{
	const char *thisfunc = "set_headers_in_context()";
	am_status_t status = AM_SUCCESS;
	string st = headersList;
	size_t cl =0, cr=0;
	int h1=0; 
	string header="", value="";
	PCSTR pcHeader, pcValue;
	string tmpCookieString="";

	if (isRequest)  am_web_log_debug("%s: inRequest: TRUE",thisfunc);
	else        am_web_log_debug("%s: inRequest: FALSE",thisfunc);

	IHttpRequest* pHttpRequest = pHttpContext->GetRequest();
	IHttpResponse* pHttpResponse = pHttpContext->GetResponse();

	do
	{
		cl=st.find_first_of(':');
		cr=st.find_first_of("\r\n");

		if(cl!=string::npos && cr!=string::npos)
		{
			header = st.substr(h1,cl);
			value = st.substr(cl+1,cr-cl-1);
			st= st.substr(cr+2);

			ConstructReqCookieValue(tmpCookieString, value);

                        if (header.compare("Set-Cookie") == 0 && value[value.length()-1] != ';') {
                            /*append ; only to 'Set-Cookie' header value*/
                            value.append(";");
                        }
                        
			pcHeader = (PCSTR)pHttpContext->AllocateRequestMemory(
				(DWORD) (header.length()) + 1);
			pcValue = (PCSTR)pHttpContext->AllocateRequestMemory(
				(DWORD) (value.length()) + 1);
			memset((void*)pcHeader,0,header.length() + 1);
			memset((void*)pcValue,0,value.length() + 1);
			strcpy((char*)pcHeader, header.c_str());
			strcpy((char*)pcValue, value.c_str());

			am_web_log_debug("%s: Setting Head: %s = %s",thisfunc,pcHeader,pcValue);

			if(isRequest)
			{
				pHttpContext->GetRequest()->SetHeader(pcHeader,pcValue,
					(USHORT)strlen(pcValue),TRUE);
			}
			else
			{
				//this is for the browser to set the cookies
				pHttpContext->GetResponse()->SetHeader(pcHeader,pcValue,
					(USHORT)strlen(pcValue),FALSE);
			}

		}

	}
	while(cl!=string::npos);


	//Set the cookie in the request header
	//similar to what set_header_attr_as_cookie is supposed to do
	if(!isCdssoEnabled && !isRequest && tmpCookieString.length()>0)
	{
		PCSTR pszCookie;
		PCSTR newCookie;
		USHORT cchCookie;

		pszCookie = pHttpRequest->GetHeader("Cookie",&cchCookie);
		pszCookie = (PCSTR) pHttpContext->AllocateRequestMemory( cchCookie + 1 );

		if (pszCookie==NULL)
		{
			return AM_FAILURE;
		}
                
                if (tmpCookieString[tmpCookieString.length()-1] != ';') {
                    tmpCookieString.append(";");
                }

		pszCookie = pHttpRequest->GetHeader("Cookie",&cchCookie);
		newCookie = (PCSTR) pHttpContext->
			AllocateRequestMemory(cchCookie + 
			(DWORD) (tmpCookieString.length()) + 1 );
		strcpy((char*)newCookie, (char*)pszCookie);
		strcat((char*)newCookie, tmpCookieString.c_str());
		strcat((char*)newCookie,"\0");

		pHttpRequest->SetHeader("Cookie",newCookie,(USHORT)strlen(newCookie),TRUE);
	}

	return status;
}


am_status_t set_request_headers(IHttpContext *pHttpContext, void** args)
{
	const char *thisfunc = "set_request_headers()";
	am_status_t status = AM_SUCCESS;
	PCSTR httpHeaders = NULL;
	CHAR* httpHeadersC = NULL;
	DWORD httpHeadersSize = 0;
	size_t http_headers_length = 0;
	CHAR* key = NULL;
	CHAR* pkeyStart = NULL;
	CHAR* tmpAttributeList = NULL;
	CHAR* pTemp = NULL;
	int i, j;
	int iKeyStart, keyLength;
	int iValueStart, iValueEnd, iHdrStart;
	BOOL isEmptyValue = FALSE;

	CHAR* set_headers_list = *((CHAR**) args[1]);
	CHAR* set_cookies_list = *((CHAR**) args[2]);
	CHAR** ptr = (CHAR **) args[3];
	CHAR* request_hdrs = *ptr;

	//Get the original headers from the request
	status = GetVariable(pHttpContext,"ALL_RAW", &httpHeaders, &httpHeadersSize, TRUE);

	httpHeadersC = (CHAR*) malloc(strlen(httpHeaders) + 1); 
	strcpy(httpHeadersC, httpHeaders);

	//Remove profile attributes from original request headers, if any,
	//to avoid tampering
	if ((status == AM_SUCCESS) && (set_headers_list != NULL)) {
		pkeyStart = set_headers_list;
		iKeyStart=0;
		for (i = 0; i < strlen(set_headers_list); ++i) {
			if (set_headers_list[i] == ':') {
				keyLength = i + 1 - iKeyStart;
				key = (char *)malloc(keyLength + 1);
				if (key != NULL) {
					memset(key,0,keyLength + 1);
					strncpy (key,pkeyStart,keyLength);
					if (strlen(key) > 0) {
						status = remove_key_in_headers(key, &httpHeadersC);
					}
					free(key);
					key = NULL;
				} else {
					am_web_log_error("%s:Not enough memory "
						"to allocate key variable", thisfunc);
					status = AM_NO_MEMORY;
					break;
				}
				pkeyStart = set_headers_list;
			}
			if ((set_headers_list[i] == '\r') && (set_headers_list[i+1] == '\n'))
			{
				iKeyStart = i+2;
				pkeyStart = pkeyStart + i + 2;
			}
		}
	}

	//Remove empty values from set_headers_list 
	//also set these non empty headers in pHttpContext
	if ((status == AM_SUCCESS) && (set_headers_list != NULL)) {
		tmpAttributeList = (char*)malloc(strlen(set_headers_list)+1);
		if (tmpAttributeList != NULL) {
			memset(tmpAttributeList,0,strlen(set_headers_list)+1);
			strcpy(tmpAttributeList,set_headers_list);
			memset(set_headers_list,0,strlen(tmpAttributeList)+1);
			iValueStart = 0;
			iValueEnd = 0;
			for (i = 0; i < strlen(tmpAttributeList); ++i) {
				if (tmpAttributeList[i] == ':') {
					iValueStart = i + 1;
				}
				if ((tmpAttributeList[i] == '\r') &&
					(tmpAttributeList[i+1] == '\n')) {
						iHdrStart = iValueEnd;
						iValueEnd = i;
						isEmptyValue = TRUE;
						if ((iValueStart > 0 ) && (iValueEnd > iValueStart)) {
							for (j=iValueStart ; j < iValueEnd ; j++) {
								if (tmpAttributeList[j] != ' ') {
									isEmptyValue = FALSE;
									break;
								}
							}
						}
						if (isEmptyValue == FALSE) {
							for (j=iHdrStart ; j<iValueEnd ; j++) {
								if ((tmpAttributeList[j] != '\r') &&
									(tmpAttributeList[j] != '\n')) {
										pTemp = tmpAttributeList + j;
										strncat(set_headers_list, pTemp, 1);
								}
							}
							strcat(set_headers_list,pszCrlf);
						}
				}
			}
		} else {
			am_web_log_error("%s:Not enough memory to allocate "
				"tmpAttributeList.", thisfunc);
			status = AM_NO_MEMORY;
		}
		if (tmpAttributeList != NULL) {
			free(tmpAttributeList);
			tmpAttributeList = NULL;
		}
	}



	//set the non-empty headers in pHttpContext
	string headersList ="";
	if(set_headers_list)
		headersList = set_headers_list;
	set_headers_in_context(pHttpContext, headersList, TRUE);


	//Add custom headers and/or set_cookie header to original headers
	if (status == AM_SUCCESS) {
		http_headers_length = strlen(httpHeadersC);
		if (set_headers_list != NULL) {
			http_headers_length = http_headers_length + 
				strlen(set_headers_list);
		}
		if (set_cookies_list != NULL) {
			http_headers_length = http_headers_length +
				strlen(set_cookies_list);
		}
		request_hdrs = (char *)malloc(http_headers_length + 1);
		if (request_hdrs != NULL) {
			memset(request_hdrs,0, http_headers_length + 1);
			strcpy(request_hdrs, httpHeadersC);
			if (set_headers_list != NULL) {
				strcat(request_hdrs,set_headers_list);
			}
			if (set_cookies_list != NULL) {
				strcat(request_hdrs,set_cookies_list);
			}
			*ptr = request_hdrs;
			am_web_log_debug("set_request_headers(): Final headers: %s \n",
				request_hdrs);
		} else {
			am_web_log_error("set_request_headers():Not enough memory "
				"to allocate request_hdrs");
			status = AM_NO_MEMORY;
		}
	}

	if (httpHeadersC != NULL) {
		free(httpHeadersC);
		httpHeadersC = NULL;
	}
	if (set_headers_list != NULL) {
		free(set_headers_list);
		set_headers_list = NULL;
	}

	return status;
}

// Method to register POST data in agent cache

/*
* Invoked during CDSSO. It jsut returns RQ_NOTIFICATION_CONTINUE now to let the 
* IIS handle the request. If more processing is required, it can be implemented
* here.
*
* */
REQUEST_NOTIFICATION_STATUS redirect_to_request_url(IHttpContext* pHttpContext,
													const char *redirect_url, 
													const char *set_cookies_list)
{
	am_web_log_debug("redirect_to_request_url:  "
		"redirection URL is %s", 
		redirect_url);

	am_web_log_debug("redirect_to_request_url: Generated Redirect");

	IHttpResponse * pHttpResponse = pHttpContext->GetResponse();
	if(pHttpResponse == NULL) {
		am_web_log_error("redirect_to_request_url(): pHttpResponse is NULL.");
	}
	/* Delete content-length since this is a redirect. */
	pHttpResponse->DeleteHeader("Content-Length");

	pHttpResponse->Redirect(redirect_url, true, false);
	return RQ_NOTIFICATION_FINISH_REQUEST;
}

/*
 * Invoked when redireting the response to either server login page
 * or 403, 500 responses.
 *
 * */
static am_status_t do_redirect(IHttpContext* pHttpContext,
        am_status_t policy_status,
        am_policy_result_t *policy_result,
        const char *original_url,
        const char *method,
        void** args,
        void* agent_config) {
    const char *thisfunc = "do_redirect()";
    am_status_t status = AM_SUCCESS;
    size_t redirect_hdr_len = 0;
    char *redirect_url = NULL;
    DWORD redirect_url_len = 0;
    size_t advice_headers_len = 0;
    char *advice_headers = NULL;
    const char advice_headers_template[] = {
        "Content-Length: %d\r\n"
        "Content-Type: text/html\r\n"
        "\r\n"
    };
    const am_map_t advice_map = policy_result->advice_map;
    HRESULT hr;

    IHttpResponse * pHttpResponse = pHttpContext->GetResponse();
    if (pHttpResponse == NULL) {
        am_web_log_error("%s: pHttpResponse is NULL.", thisfunc);
        return AM_FAILURE;
    }
    status = am_web_get_url_to_redirect(policy_status, advice_map, original_url,
            method, AM_RESERVED, &redirect_url, agent_config);

    // Compute the length of the redirect response.  Using the size of
    // the format string overallocates by a couple of bytes, but that is
    // not a significant issue given the short life span of the allocation.


    switch (policy_status) {
        case AM_ACCESS_DENIED:
        case AM_INVALID_SESSION:
        case AM_INVALID_FQDN_ACCESS:

            //Check whether policy advices exist. If exists send
            //the advice back to client
            if ((status == AM_SUCCESS) && (redirect_url != NULL) &&
                    (B_FALSE == am_web_use_redirect_for_advice(agent_config)) &&
                    (policy_result->advice_string != NULL)) {
                char *advice_txt = NULL;
                status = am_web_build_advice_response(policy_result, redirect_url,
                        &advice_txt);
                am_web_log_debug("%s: policy status=%s, response[%s]",
                        thisfunc, am_status_to_string(policy_status),
                        advice_txt);
                if (status == AM_SUCCESS) {
                    size_t data_length = (advice_txt != NULL) ? strlen(advice_txt) : 0;
                    if (data_length > 0) {
                        char buff[256];
                        itoa(data_length, buff, 10);
                        advice_headers_len = strlen(advice_headers_template) + 3;
                        advice_headers = (char *) malloc(advice_headers_len);
                        pHttpResponse->Clear();
                        hr = pHttpResponse->SetStatus(200, "Status OK", 0, S_OK);
                        hr = pHttpResponse->SetHeader("Content-Type", "text/html",
                                (USHORT) strlen("text/html"), TRUE);
                        hr = pHttpResponse->SetHeader("Content-Length", buff,
                                (USHORT) strlen(buff), TRUE);
                        CHAR* set_cookies_list = *((CHAR**) args[2]);
                        if (set_cookies_list != NULL) {
                            set_headers_in_context(pHttpContext, set_cookies_list, FALSE);
                        }
                        if (FAILED(hr)) {
                            am_web_log_error("%s: SetHeader failed.", thisfunc);
                            status = AM_FAILURE;
                        }
                        DWORD cbSent;
                        PCSTR pszBuffer = advice_txt;
                        HTTP_DATA_CHUNK dataChunk;
                        dataChunk.DataChunkType = HttpDataChunkFromMemory;
                        dataChunk.FromMemory.pBuffer = (PVOID) pszBuffer;
                        dataChunk.FromMemory.BufferLength = (USHORT) data_length;
                        hr = pHttpResponse->WriteEntityChunks(&dataChunk, 1,
                                FALSE, TRUE, &cbSent);
                    }
                } else {
                    am_web_log_error("%s: Error while building "
                            "advice response body:%s",
                            thisfunc, am_status_to_string(status));
                }
            } else {
                // No composite advice or composite advice is redirected.
                // If there is a composite advice 
                // we need to modify the redirect_url with the policy advice
                if ((B_TRUE == am_web_use_redirect_for_advice(agent_config)) &&
                        (policy_result->advice_string != NULL)) {
                    char *redirect_url_with_advice = NULL;
                    status = am_web_build_advice_redirect_url(policy_result,
                            redirect_url, &redirect_url_with_advice);
                    am_web_log_debug("%s: policy status=%s, "
                            "redirect url with advice [%s]",
                            thisfunc, am_status_to_string(status),
                            redirect_url_with_advice);
                    if (status == AM_SUCCESS) {
                        redirect_url = redirect_url_with_advice;
                    } else {
                        am_web_log_error("%s: Error while building "
                                "advice response body:%s",
                                thisfunc, am_status_to_string(status));
                    }
                }
                if (status == AM_SUCCESS && redirect_url != NULL) {
                    CHAR* set_cookies_list = *((CHAR**) args[2]);
                    am_web_log_debug("%s: policy status = %s, "
                            "redirection URL is %s", thisfunc,
                            am_status_to_string(policy_status),
                            redirect_url);
                    if (set_cookies_list != NULL) {
                        set_headers_in_context(pHttpContext, set_cookies_list,
                                FALSE);
                    }
                    am_web_log_debug("Generated Redirect");
                    pHttpResponse->Redirect(redirect_url, true, false);
                    if (FAILED(hr)) {
                        am_web_log_error("%s: SetHeader failed.", thisfunc);
                        status = AM_FAILURE;
                    }
                } else {
                    //redirect url might be null or status is not success
                    //redirect to 403 Forbidden or 500 Internal Server error page.
                    pHttpResponse->Clear();
                    PCSTR pszBuffer;
                    //if status is access denied, send 403.
                    //for every other error, send 500.
                    if (policy_status == AM_ACCESS_DENIED) {
                        pszBuffer = "403 Forbidden";
                        hr = pHttpResponse->SetStatus(403, "Forbidden", 0, S_OK, NULL);
                        hr = pHttpResponse->SetHeader("Content-Length", "13",
                                (USHORT) strlen("13"), TRUE);
                        hr = pHttpResponse->SetHeader("Content-Type", "text/plain",
                                (USHORT) strlen("text/plain"), TRUE);
                    } else {
                        pszBuffer = "500 Internal Server Error";
                        hr = pHttpResponse->SetStatus(500, "Internal Server Error",
                                0, S_OK);
                        hr = pHttpResponse->SetHeader("Content-Length", "25",
                                (USHORT) strlen("25"), TRUE);
                        hr = pHttpResponse->SetHeader("Content-Type", "text/html",
                                (USHORT) strlen("text/html"), TRUE);
                    }
                    HTTP_DATA_CHUNK dataChunk;
                    dataChunk.DataChunkType = HttpDataChunkFromMemory;
                    DWORD cbSent;
                    dataChunk.FromMemory.pBuffer = (PVOID) pszBuffer;
                    dataChunk.FromMemory.BufferLength = (USHORT) strlen(pszBuffer);
                    hr = pHttpResponse->WriteEntityChunks(&dataChunk, 1, FALSE,
                            TRUE, &cbSent);
                    if (FAILED(hr)) {
                        am_web_log_error("%s: Error while calling "
                                "am_web_get_redirect_url(): status = %s",
                                thisfunc, am_status_to_string(status));
                        status = AM_FAILURE;
                    }
                }
            }
            if (redirect_url) {
                am_web_free_memory(redirect_url);
            }
            if (advice_headers) {
                free(advice_headers);
            }
            break;

        default:
            // All the default values are set to send 500 code.
            break;
    }
    return status;
}


/* 
* This function checks if the profile attribute key is in the original 
* request headers. If it is remove it in order to avoid tampering.
*/
am_status_t remove_key_in_headers(char* key, char** httpHeaders)
{
	const char *thisfunc = "remove_custom_attribute_in_header()";
	am_status_t status = AM_SUCCESS;
	CHAR* pStartHdr =NULL;
	CHAR* pEndHdr =NULL;    
	CHAR* tmpHdr=NULL;  
	size_t len;

	pStartHdr = strstr(*httpHeaders,key);
	if (pStartHdr != NULL) {
		tmpHdr = (char*)malloc(strlen(*httpHeaders) + 1);
		if (tmpHdr != NULL) {
			memset(tmpHdr,0,strlen(*httpHeaders) + 1);
			len = strlen(*httpHeaders) - strlen(pStartHdr);
			strncpy(tmpHdr,*httpHeaders,len);
			pEndHdr = strstr(pStartHdr,pszCrlf);
			if (pEndHdr != NULL) {
				pEndHdr = pEndHdr + 2;
				strcat(tmpHdr,pEndHdr);
			}
			am_web_log_info("%s: Attribute %s was found and removed from "
				"the original request headers.",thisfunc, key);
		} else {
			am_web_log_error("%s: Not enough memory to allocate tmpHdr.",
				thisfunc);
			status = AM_NO_MEMORY;
		}
	}
	if (tmpHdr != NULL) {
		memset(*httpHeaders,0,strlen(*httpHeaders) + 1);
		strcpy(*httpHeaders,tmpHdr);
		free(tmpHdr);
		tmpHdr = NULL;
	}

	return status;
}

/*
* Gets invoked at the first request. It intializes the agent toolkit.
*
* */
void init_at_request()
{
	am_status_t status;
	char debugMsg[2048]="";
	status = am_agent_init(&agentInitialized);
	if (status != AM_SUCCESS) {
		sprintf(debugMsg, "%s: am_agent_init() returned failure. ",
			am_status_to_string(status));
		logPrimitive(debugMsg);
	} 
} 

/*
* Invoked when denying requests when the OpenSSO is not responding
* properly.
* */
void do_deny(IHttpContext* pHttpContext)
{
	am_status_t status=AM_SUCCESS;
	HRESULT hr;
	IHttpResponse* pHttpResponse = pHttpContext->GetResponse();
	pHttpResponse->Clear();
	PCSTR pszBuffer;
	pszBuffer = "403 Forbidden";
	hr = pHttpResponse->SetStatus(403,"Forbidden",0, S_OK, NULL);
	hr = pHttpResponse->SetHeader("Content-Length","13",(USHORT)strlen("13"),
		TRUE);
	hr = pHttpResponse->SetHeader("Content-Type","text/plain",
		(USHORT)strlen("text/plain"), TRUE);
	HTTP_DATA_CHUNK dataChunk;
	dataChunk.DataChunkType = HttpDataChunkFromMemory;
	DWORD cbSent;
	dataChunk.FromMemory.pBuffer = (PVOID) pszBuffer;
	dataChunk.FromMemory.BufferLength = (USHORT) strlen(pszBuffer);
	hr = pHttpResponse->WriteEntityChunks(&dataChunk,1,FALSE,TRUE,&cbSent);
	if (FAILED(hr)) {
		status=AM_FAILURE;
	}
}

