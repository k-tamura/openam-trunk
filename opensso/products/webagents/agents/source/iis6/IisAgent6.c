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
 *
 */
/*
 * Portions Copyrighted 2012 ForgeRock AS
 */

#include <windows.h>
#include <httpext.h>
#include <string.h>
#include <stdio.h>
#include <stdarg.h>
#include "am_web.h"
#include <nspr.h>
#include "IisAgent6.h"

const char REDIRECT_TEMPLATE[] = {
    "Location: %s\r\n"
    "Content-Length: 0\r\n"
    "Content-Type: text/html\r\n"        
    "\r\n"
};

const char REDIRECT_COOKIE_TEMPLATE[] = {
    "Location: %s\r\n"
    "%s"
    "Content-Length: 0\r\n"
    "Content-Type: text/html\r\n"
    "\r\n"
};


/* Comment for both FORBIDDEN_MSG and INTERNAL_SERVER_ERROR_MSG:
 * Both these have string messages only because Netscape browsers
 * can be happy.  Otherwise, they throw up a dialog box saying
 * document contains no data.  Netscape 7 does not say anything
 * at all.
 */
const char FORBIDDEN_MSG[] = {
    "403 Forbidden"
};

const char NOTFOUND_MSG[] = {
    "404 Not Found"
};

const char INTERNAL_SERVER_ERROR_MSG[] = {
    "500 Internal Server Error"
};

#define AGENT_DESCRIPTION   "ForgeRock OpenAM Policy Agent 3.0 for Microsoft IIS 6.0"
const CHAR agentDescription[]   = { AGENT_DESCRIPTION };
#define	MAGIC_STR		"sunpostpreserve"
#define	POST_PRESERVE_URI	"/dummypost/"MAGIC_STR
#define   EMPTY_STRING	""

// actually const. But API prototypes don't alow.
CHAR httpOk[]                   = "200 OK";
CHAR httpRedirect[]             = "302 Found";
CHAR httpBadRequest[]           = "400 Bad Request";
CHAR httpForbidden[]            = "403 Forbidden";
CHAR httpNotFound[]             = "404 Not Found";
CHAR httpServerError[]          = "500 Internal Server Error";

DWORD http200                   = 200;
DWORD http302                   = 302;
DWORD http500                   = 500;
DWORD http403                   = 403;
DWORD http404                   = 404;

const CHAR httpProtocol[]       = "http";
const CHAR httpVersion1_1[]     = "HTTP/1.1";
const CHAR httpsProtocol[]      = "https";
const CHAR httpProtocolDelimiter[]  = "://";
const CHAR pszLocalHost[]       = "localhost";
// Do not change. Used to see if port number needed to reconstructing URL.
const CHAR httpPortDefault[]        = "80";
const CHAR httpsPortDefault[]       = "443";
const CHAR httpPortDelimiter[]      = ":";
const CHAR pszLocation[]        = "Location: ";
const CHAR pszContentLengthNoBody[] = "Content-length: 0\r\n";
const CHAR pszCrlf[]            = "\r\n";
const CHAR pszEntityDelimiter[]     = "\r\n\r\n";
// Response to cache invalidation notification request.
//   I.e. UpdateAgentCacheServlet
const CHAR HTTP_RESPONSE_OK[]     = {
    "HTTP/1.1 200 OK\r\n"
    "Content-length: 2\r\n"
    "Content-type: text/plain\r\n\r\n"
    "OK" // case etc. CRITICAL, Exact match in Access Manager.
};
const CHAR contentLength[]      = "Content-Length:";
const DWORD cbCrlfLen           = 2; // strlen("\r\n")
      CHAR  pszHttpAuthHeaderName[] = "Authorization:"; // Really const.
const CHAR refererServlet[]     = "refererservlet";

// Responses the agent uses to requests.
typedef enum {aaDeny, aaAllow, aaLogin} tAgentAction;

tAgentConfig agentConfig;

BOOL readAgentConfigFile = FALSE;
CRITICAL_SECTION initLock;

typedef struct OphResources {
    CHAR* cookies;      // cookies in the request
    DWORD cbCookies;
    am_policy_result_t result;
} tOphResources;

#define RESOURCE_INITIALIZER \
    { NULL, 0, AM_POLICY_RESULT_INITIALIZER }

BOOL WINAPI GetExtensionVersion(HSE_VERSION_INFO * pVer) {
    pVer->dwExtensionVersion = MAKELONG(0, 1); // Version 1.0
    // A brief one line description of the ISAPI extension
    strncpy(pVer->lpszExtensionDesc, agentDescription, HSE_MAX_EXT_DLL_NAME_LEN);
    InitializeCriticalSection(&initLock);
    return TRUE;
}

BOOL loadAgentPropertyFile(EXTENSION_CONTROL_BLOCK *pECB)
{
    BOOL  gotInstanceId = FALSE;
    CHAR  *instanceId =  NULL;
    DWORD instanceIdSize = 0;
    CHAR* propertiesFileFullPath  = NULL;
    am_status_t status = AM_SUCCESS;
    am_status_t polsPolicyStatus = AM_SUCCESS;
    BOOL         statusContinue      = FALSE;
    CHAR         debugMsg[2048]   = "";
    CHAR* agent_bootstrap_file  = NULL;
    CHAR* agent_config_file = NULL;    
    boolean_t agentInitialized = B_FALSE;

    // Init to NULL values until we read properties file.
    agentConfig.bAgentInitSuccess = FALSE; // assume Failure until success

    if ( pECB->GetServerVariable(pECB->ConnID, "INSTANCE_ID", NULL,
                                 &instanceIdSize) == FALSE ) {
       instanceId = malloc(instanceIdSize);
       if (instanceId != NULL) {
           gotInstanceId = pECB->GetServerVariable(pECB->ConnID,
                                   "INSTANCE_ID",
                               instanceId,
                               &instanceIdSize);
           if ((gotInstanceId == FALSE) || (instanceIdSize <= 0)) {
               sprintf(debugMsg,
                       "%d: Invalid Instance Id received",
                       instanceIdSize);
           status = AM_FAILURE;
           }
       } else {
            sprintf(debugMsg,
                    "%d: Invalid Instance Id received",
                    instanceIdSize);
            status = AM_NO_MEMORY;
       }
    }

    if (status == AM_SUCCESS) {
       if (iisaPropertiesFilePathGet(&agent_bootstrap_file, instanceId, TRUE)
                                     == FALSE){ 
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

       // Initialize the OpenSSO Policy API
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

    status = am_agent_init(&agentInitialized);
       if (AM_SUCCESS != polsPolicyStatus) {
         sprintf(debugMsg, "%s: Initialization of the agent(am_agent_init) failed: status = %s (%d)",
                 agentDescription, am_status_to_string(polsPolicyStatus),
         polsPolicyStatus);
         logPrimitive(debugMsg);
         SetLastError(IISA_ERROR_INIT_POLICY);
         return FALSE;
       }

    if (instanceId != NULL) {
       free(instanceId);
    }

    // Record success initializing agent.
    agentConfig.bAgentInitSuccess = TRUE;
    return TRUE;
}

// Method to register POST data in agent cache
static am_status_t register_post_data(EXTENSION_CONTROL_BLOCK *pECB, 
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

// Method to check and create post page
static am_status_t check_for_post_data(EXTENSION_CONTROL_BLOCK *pECB,
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
                stickySessionPos = strstr(post_data_query, stickySessionValue);
                if (stickySessionPos != NULL) {
                    size_t len = strlen(post_data_query) - 
                                 strlen(stickySessionPos)-1;
                    temp_uri = malloc(len+1);
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
            status = AM_SUCCESS;
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

am_status_t get_post_data(EXTENSION_CONTROL_BLOCK *pECB, char **body)
{
    const char *thisfunc = "get_post_data()";
    am_status_t status = AM_SUCCESS;
    
    int totalBytesRecvd = pECB->cbTotalBytes;
    if (totalBytesRecvd > 0) {
        *body = (char *) malloc(totalBytesRecvd + 1);
        if (*body != NULL) {
            memset(*body, 0, sizeof(char) * totalBytesRecvd + 1);
            strncpy(*body, pECB->lpbData, totalBytesRecvd);
        } else {
            am_web_log_error("%s: Not enough memory 0x%x bytes.",
                              thisfunc, pECB->cbTotalBytes);
            status = AM_NO_MEMORY;
        }
    } else {
        am_web_log_warning("%s: This POST request has no post data.", thisfunc);
    }
    
    return status;
}

DWORD send_error(EXTENSION_CONTROL_BLOCK *pECB) {
    const char *thisfunc = "send_error()";
    const char *data = INTERNAL_SERVER_ERROR_MSG;
    size_t data_len = sizeof (INTERNAL_SERVER_ERROR_MSG) - 1;
    pECB->dwHttpStatusCode = http500;
    pECB->ServerSupportFunction(pECB->ConnID,
            HSE_REQ_SEND_RESPONSE_HEADER,
            "500 Internal Server Error",
            (LPDWORD) NULL,
            (LPDWORD) "Content-Type: text/html\r\n\r\n");
    if ((pECB->WriteClient(pECB->ConnID, (LPVOID) data,
            (LPDWORD) & data_len, (DWORD) 0)) == FALSE) {
        am_web_log_error("%s: WriteClient did not succeed: "
                "Attempted message = %s ", thisfunc, data);
    }
    return HSE_STATUS_SUCCESS_AND_KEEP_CONN;
}

DWORD send_notfound(EXTENSION_CONTROL_BLOCK *pECB) {
    const char *thisfunc = "send_notfound()";
    const char *data = NOTFOUND_MSG;
    size_t data_len = sizeof (NOTFOUND_MSG) - 1;
    pECB->dwHttpStatusCode = http404;
    pECB->ServerSupportFunction(pECB->ConnID,
            HSE_REQ_SEND_RESPONSE_HEADER,
            "404 Not Found",
            (LPDWORD) NULL,
            (LPDWORD) "Content-Type: text/html\r\n\r\n");
    if ((pECB->WriteClient(pECB->ConnID, (LPVOID) data,
            (LPDWORD) & data_len, (DWORD) 0)) == FALSE) {
        am_web_log_error("%s: WriteClient did not succeed: "
                "Attempted message = %s ", thisfunc, data);
    }
    return HSE_STATUS_SUCCESS_AND_KEEP_CONN;
}

DWORD do_deny(EXTENSION_CONTROL_BLOCK *pECB) {
    const char *thisfunc = "do_deny()";
    const char *data = FORBIDDEN_MSG;
    size_t data_len = sizeof (FORBIDDEN_MSG) - 1;
    pECB->dwHttpStatusCode = http403;
    pECB->ServerSupportFunction(pECB->ConnID,
            HSE_REQ_SEND_RESPONSE_HEADER,
            "403 Forbidden",
            (LPDWORD) NULL,
            (LPDWORD) "Content-Type: text/html\r\n\r\n");
    if ((pECB->WriteClient(pECB->ConnID, (LPVOID) data,
            (LPDWORD) & data_len, (DWORD) 0)) == FALSE) {
        am_web_log_error("%s: WriteClient did not succeed: "
                "Attempted message = %s ", thisfunc, data);
    }
    return HSE_STATUS_SUCCESS_AND_KEEP_CONN;
}

// This function is called when the preserve post data feature is enabled
// to send the original request with the original post data after the 
// iPlanetDirectoryPro value has been obtained from the CDC servlet
DWORD send_post_data(EXTENSION_CONTROL_BLOCK *pECB, char *page, 
                     char *set_cookies_list)
{
    const char *thisfunc = "send_post_data()";
    size_t page_len = 0;
    size_t headers_len = 0;
    char *headers = NULL;
    HSE_SEND_HEADER_EX_INFO sendHdr;
    DWORD returnValue = HSE_STATUS_SUCCESS;
    const char post_headers_template[] = {
             "Content-Length: %d\r\n"
             "Content-Type: text/html\r\n"
             "\r\n"
    };
    const char post_headers_template_with_cookie[] = {
             "Content-Length: %d\r\n"
             "Content-Type: text/html\r\n"
             "%s"
             "\r\n"
    };

    // Prepare the headers 
    headers_len = strlen(post_headers_template) + 3;
    if (set_cookies_list != NULL && strlen(set_cookies_list) > 0) {
        headers_len += strlen(set_cookies_list);
    }
    headers = (char *) malloc(headers_len);
    if (headers == NULL) {
        //Send internal error message
        am_web_log_error("%s: Not enough memory to allocate headers.",
                         thisfunc);
        returnValue = send_error(pECB);
    } else {
        page_len = strlen(page);
        memset(headers, 0, headers_len);
        if (set_cookies_list != NULL && strlen(set_cookies_list) > 0) {
            sprintf(headers, post_headers_template_with_cookie, 
                    page_len, set_cookies_list);
        } else {
            sprintf(headers, post_headers_template, page_len);
        }
        am_web_log_debug("%s: Headers sent with post form:\n%s",
                          thisfunc, headers);
        am_web_log_debug("%s: Post form:\n%s", thisfunc, page);
        sendHdr.pszStatus = httpOk;
        sendHdr.pszHeader = headers;
        sendHdr.cchStatus = (DWORD) strlen(httpOk);
        sendHdr.cchHeader = (DWORD) strlen(headers);
        sendHdr.fKeepConn = TRUE;
        //Send the headers
        pECB->dwHttpStatusCode = http200;
        pECB->ServerSupportFunction(pECB->ConnID,
                           HSE_REQ_SEND_RESPONSE_HEADER_EX,
                           &sendHdr,
                           NULL,
                           NULL);
        //Send the post page
        if ((pECB->WriteClient(pECB->ConnID, (LPVOID)page,
                               (LPDWORD)&page_len, (DWORD)0))==FALSE) {
            am_web_log_error("%s: WriteClient did not succeed: "
                             "Attempted message = %s ", thisfunc, page);
        }
    }
    if (headers != NULL) { 
        free(headers);
        headers = NULL;
    }
    return returnValue;
}

/*
 * This function retrieves the value of a HTTP header.
 * header_name is the name of the http header.
 * The value is assigned in header_value.
 * This value must be freed by the caller.
 */
am_status_t get_header_value(EXTENSION_CONTROL_BLOCK *pECB,
                             const char *original_header_name,
                             char **header_value,
                             BOOL isRequired,
                             BOOL addHTTPPrefix)
{
    const char *thisfunc = "get_header_value()";
    am_status_t status = AM_SUCCESS;
    DWORD header_size = 0;
    BOOL got_header = FALSE;
    char *header_name = NULL;

    if (original_header_name == NULL) {
        am_web_log_debug("%s: Header name is null.", thisfunc);
        status = AM_INVALID_ARGUMENT;
    }
    // Add HTTP in front of the header name if requested
    if (addHTTPPrefix == TRUE) {
        header_name = malloc(strlen("HTTP_") + 
                             strlen(original_header_name) + 1);
        if (header_name != NULL) {
            strcpy(header_name,"HTTP_");
            strcat(header_name,original_header_name);
            strcat(header_name,"\0");
        } else {
            am_web_log_error("%s: Not enough memory to allocate header_name",
                             thisfunc);
            status = AM_NO_MEMORY;
        }
    } else {
        header_name = (char*) original_header_name;
    }
    // Get the header value
    if (status == AM_SUCCESS) {
        if (pECB->GetServerVariable(pECB->ConnID, header_name,
                                    NULL, &header_size) == FALSE)
        {
            *header_value = malloc(header_size);
            if (*header_value != NULL) {
                got_header = pECB->GetServerVariable(pECB->ConnID, header_name,
                                                     *header_value, &header_size);
                if (got_header == TRUE) {
                    // header_size includes the null-terminating byte
                    // so the size needs to be > 1 to not be empty
                    if ((*header_value != NULL) && (header_size > 1)) {
                        am_web_log_debug("%s: %s = %s",
                                         thisfunc, header_name, *header_value);
                    } else {
                        *header_value = NULL;
                        if (isRequired == TRUE) {
                            am_web_log_error("%s: Could not get a value for "
                                             "header %s.", 
                                             thisfunc, header_name);
                            status = AM_FAILURE;
                        } else {
                            am_web_log_debug("%s: %s = ", thisfunc,
                                             header_name);
                        }
                    }
                } else {
                    *header_value = NULL;
                    if (isRequired == TRUE) {
                        am_web_log_error("%s: Header %s not found.", thisfunc,
                                         header_name);
                        status = AM_FAILURE;
                    } else {
                        am_web_log_debug("%s: %s: not found.", thisfunc,
                                         header_name);
                    }
                }
            } else {
                am_web_log_error("%s: Header value alloc failed ", thisfunc);
                status = AM_NO_MEMORY;
            }
        }
    }
    if((addHTTPPrefix == TRUE) && (header_name != NULL)) {
        free(header_name);
        header_name = NULL;
    }
    return status;
}

// Set attributes as HTTP headers
static am_status_t set_header(const char *key, const char *values, void **args)
{
     am_status_t status = AM_SUCCESS;
     CHAR** ptr = NULL;
     CHAR* set_headers_list = NULL;

     if (key != NULL && args != NULL ) {
        EXTENSION_CONTROL_BLOCK *pECB = (EXTENSION_CONTROL_BLOCK *) args[0];
        int cookie_length = 0;
        char* httpHeaderName = NULL;
        char* tmpHeader = NULL;
        size_t header_length = 0;

        ptr = (CHAR **) args[1];
        set_headers_list = *ptr;

        if (pECB != NULL) {
          header_length = strlen(key) + strlen("\r\n") + 1;
          if (values != NULL) {
             header_length += strlen(values);
          }
          httpHeaderName = (char *) malloc(header_length + 1);
        } else {
          am_web_log_error("set_header(): Invalid EXTENSION_CONTROL_BLOCK");
          status = AM_INVALID_ARGUMENT;
        }

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

// Append set-cookie header in set_cookies_list variable
static am_status_t set_cookie(const char *header, void **args)
{
     const char *thisfunc = "set_cookie()";
     am_status_t status = AM_SUCCESS;
     CHAR** ptr = NULL;
     CHAR* set_cookies_list = NULL;

     if (header != NULL && args != NULL ) {
        EXTENSION_CONTROL_BLOCK *pECB = (EXTENSION_CONTROL_BLOCK *) args[0];
        size_t cookie_length = 0;
        char* cookieValue = NULL;
        char* tmpStr = NULL;

        ptr = (CHAR **) args[2];
        set_cookies_list = *ptr;

        if (pECB != NULL) {
            cookie_length = strlen("Set-Cookie:") + strlen(header)
                                            + strlen("\r\n");
            cookieValue = (char *) malloc(cookie_length + 1);
        } else {
          am_web_log_error("set_cookie(): Invalid EXTENSION_CONTROL_BLOCK");
          status = AM_INVALID_ARGUMENT;
        }

       if (status == AM_SUCCESS) {
          if (cookieValue != NULL) {
             sprintf(cookieValue, "Set-Cookie:%s\r\n", header);

             if (set_cookies_list == NULL) {
                set_cookies_list = (char *) malloc(cookie_length + 1);
                if (set_cookies_list != NULL) {
                    memset(set_cookies_list, 0, sizeof(char) *
                                        cookie_length + 1);
                    strcpy(set_cookies_list, cookieValue);
                } else {
                    am_web_log_error("%s:Not enough memory 0x%x "
                                   "bytes.",thisfunc, cookie_length + 1);
                    status = AM_NO_MEMORY;
                }
             } else {
                  tmpStr = set_cookies_list;
                  set_cookies_list = (char *) malloc(strlen(tmpStr) +
                                                     cookie_length + 1);
                  if (set_cookies_list == NULL) {
                    am_web_log_error("%s: Not enough memory 0x%x "
                                      "bytes.", thisfunc, cookie_length + 1);
                    status = AM_NO_MEMORY;
                  } else {
                     memset(set_cookies_list,0,sizeof(set_cookies_list));
                     strcpy(set_cookies_list,tmpStr);
                     strcat(set_cookies_list,cookieValue);
                  }
            }
            am_web_log_info("%s: Following header added to "
                            "set_cookies_list:\n%s", 
                            thisfunc, cookieValue);
            free(cookieValue);

            if (tmpStr) {
                free(tmpStr);
                tmpStr = NULL;
            }
          } else {
            am_web_log_error("%s: Not enough memory 0x%x bytes.",
                                  thisfunc, cookie_length + 1);
             status = AM_NO_MEMORY;
          }
       }
     } else {
            am_web_log_error("%s: Invalid arguments obtained", thisfunc);
          status = AM_INVALID_ARGUMENT;
     }

    if (set_cookies_list && set_cookies_list[0] != '\0') {
        am_web_log_info("%s:set_cookies_list = %s", thisfunc, set_cookies_list);
        *ptr = set_cookies_list;
    }

     return status;
}

static am_status_t set_header_attr_as_cookie(const char *header, void **args)
{
  return AM_SUCCESS;
}

static am_status_t get_cookie_sync(const char *cookieName,
                                   char** dpro_cookie,
                                   void **args)
{
   am_status_t status = AM_SUCCESS;
   return status;
}

static void set_method(void ** args, char * orig_req)
{
}

VOID WINAPI execute_orig_request(EXTENSION_CONTROL_BLOCK *pECB,
                      PVOID pContext,
                      DWORD cbIO,
                      DWORD dwError)
{
    HSE_EXEC_URL_STATUS execUrlStatus;
    CHAR        szStatus[32] = "";
    CHAR        szWin32Error[32] = "";
    BOOL                result;
    HSE_EXEC_URL_INFO   *pExecUrlInfo;

    pExecUrlInfo = (HSE_EXEC_URL_INFO *)pContext;

    //
    // Get the results of the child request and report it.
    //
    result = pECB->ServerSupportFunction(pECB->ConnID,
                                HSE_REQ_GET_EXEC_URL_STATUS,
                                &execUrlStatus,
                                NULL,
                                NULL );
    if ( result )
    {
        if ( execUrlStatus.uHttpSubStatus != 0 )
        {
            _snprintf( szStatus,
                   32,
                       "Child Status=%d.%d",
                       execUrlStatus.uHttpStatusCode,
                       execUrlStatus.uHttpSubStatus );
        }
        else
        {
            _snprintf( szStatus,
                   32,
                       "%d",
                       execUrlStatus.uHttpStatusCode );
        }

        szStatus[31] = '\0';

        if ( execUrlStatus.dwWin32Error != ERROR_SUCCESS )
        {
            _snprintf( szWin32Error,
                       16,
                       "ErrorCode=%d, ",
                       execUrlStatus.dwWin32Error );

            szWin32Error[31] = '\0';
        }
    }

    //
    // Clean up the context pointer
    //

    if ( pExecUrlInfo != NULL )
    {
        if (pExecUrlInfo->pUserInfo != NULL) {
            free(pExecUrlInfo->pUserInfo);
            pExecUrlInfo->pUserInfo = NULL;
        }
        free(pExecUrlInfo);
        pExecUrlInfo = NULL;
    }


    //
    // Notify IIS that we are done with this request
    //

    pECB->ServerSupportFunction(
        pECB->ConnID,
        HSE_REQ_DONE_WITH_SESSION,
        NULL,
        NULL,
        NULL
        );
}

DWORD process_original_url(EXTENSION_CONTROL_BLOCK *pECB,
               CHAR* requestURL,
               CHAR* orig_req_method,
               CHAR* request_hdrs,
               tOphResources* pOphResources,
               void* agent_config)
{
    const char *thisfunc = "process_original_url()";
    const char *authtype = NULL;
    HSE_EXEC_URL_INFO  *execUrlInfo  = NULL;
    execUrlInfo = (HSE_EXEC_URL_INFO *) malloc(sizeof(HSE_EXEC_URL_INFO));

    if (execUrlInfo == NULL) {
        am_web_log_error("%s: Error %d occurred during "
                         "creating HSE_EXEC_URL_INFO context. \r\n",
                         thisfunc, GetLastError());
        return HSE_STATUS_ERROR;
    } else {
        memset(execUrlInfo, 0, sizeof(execUrlInfo));
        execUrlInfo->pszUrl = NULL;          // Use original request URL
        if (orig_req_method != NULL) {
            //CDSSO mode(restore orig method)
            execUrlInfo->pszMethod = orig_req_method;
            //Remove the entity-body sent by the CDC servlet
            execUrlInfo->pEntity->cbAvailable = 0;
            execUrlInfo->pEntity->lpbData = NULL;
            am_web_log_debug("%s: CDSSO Mode - method set back to "
                             "original method (%s), "
                             "CDC servlet content deleted",
                             thisfunc, orig_req_method);
        } else {
            execUrlInfo->pszMethod = NULL;       // Use original request method
            execUrlInfo->pEntity = NULL;         // Use original request entity
        }
        if (request_hdrs != NULL) {
           am_web_log_debug("%s: request_hdrs = %s", thisfunc, request_hdrs);
           execUrlInfo->pszChildHeaders = request_hdrs; // Original and custom
                                                   // headers
        } else {
            execUrlInfo->pszChildHeaders = NULL;// Use original request headers
        }
        execUrlInfo->pUserInfo = NULL;       // Use original request user info
        if (pOphResources->result.remote_user != NULL) {
           // Set the remote user
           execUrlInfo->pUserInfo = malloc(sizeof(HSE_EXEC_URL_USER_INFO));
           if (execUrlInfo->pUserInfo != NULL) {
               memset(execUrlInfo->pUserInfo,0,sizeof(execUrlInfo->pUserInfo));
               execUrlInfo->pUserInfo->hImpersonationToken = NULL;
               execUrlInfo->pUserInfo->pszCustomUserName =
                                 (LPSTR)pOphResources->result.remote_user;
               authtype = am_web_get_authType(agent_config);
               if (authtype != NULL)
                   execUrlInfo->pUserInfo->pszCustomAuthType = (LPSTR) authtype;
               else
                   execUrlInfo->pUserInfo->pszCustomAuthType = "dsame";
               am_web_log_debug("%s: Auth-Type set to %s", thisfunc,
                        execUrlInfo->pUserInfo->pszCustomAuthType);
           }
        }
    }
    //
    // Need to set the below flag to avoid recursion
    //
    execUrlInfo->dwExecUrlFlags = HSE_EXEC_URL_IGNORE_CURRENT_INTERCEPTOR;
    //
    // Associate the completion routine and the current URL with
    // this request.
    //

    if ( pECB->ServerSupportFunction( pECB->ConnID,
                                      HSE_REQ_IO_COMPLETION,
                                      execute_orig_request,
                                      0,
                                      (LPDWORD)execUrlInfo) == FALSE )
    {
        am_web_log_error("%s: Error %d occurred setting "
             "I/O completion.\r\n", thisfunc, GetLastError());
        if (execUrlInfo->pUserInfo != NULL) {
            free(execUrlInfo->pUserInfo);
            execUrlInfo->pUserInfo = NULL;
        }
        return HSE_STATUS_ERROR;
    }

    //
    // Execute child request
    //

    if ( pECB->ServerSupportFunction( pECB->ConnID,
                                      HSE_REQ_EXEC_URL,
                                      execUrlInfo,
                                      NULL,
                                      NULL ) == FALSE )
    {
        am_web_log_error("%s: Error %d occurred calling "
                     "HSE_REQ_EXEC_URL.\r\n", thisfunc, GetLastError() );

        if (execUrlInfo->pUserInfo != NULL) {
            free(execUrlInfo->pUserInfo);
            execUrlInfo->pUserInfo = NULL;
        }
        return HSE_STATUS_ERROR;
    }

    //
    // Return pending and let the completion clean up.
    //
    return HSE_STATUS_PENDING;
}

static DWORD do_redirect(EXTENSION_CONTROL_BLOCK *pECB,
        am_status_t status,
        am_policy_result_t *policy_result,
        const char *original_url,
        const char *method,
        void** args,
        void* agent_config) {
    const char *thisfunc = "do_redirect()";
    char *redirect_header = NULL;
    size_t redirect_hdr_len = 0;
    char *redirect_status = httpServerError;
    char *redirect_url = NULL;
    DWORD redirect_url_len = 0;
    HSE_SEND_HEADER_EX_INFO sendHdr;
    size_t advice_headers_len = 0;
    char *advice_headers = NULL;
    const char advice_headers_template[] = {
        "Content-Length: %d\r\n"
        "Content-Type: text/html\r\n"
        "\r\n"
    };
    const char advice_headers_cookie_template[] = {
        "%s"
        "Content-Length: %d\r\n"
        "Content-Type: text/html\r\n"
        "\r\n"
    };

    am_status_t ret = AM_SUCCESS;
    const am_map_t advice_map = policy_result->advice_map;

    ret = am_web_get_url_to_redirect(status, advice_map, original_url,
            method, AM_RESERVED, &redirect_url, agent_config);

    // Compute the length of the redirect response.  Using the size of
    // the format string overallocates by a couple of bytes, but that is
    // not a significant issue given the short life span of the allocation.
    switch (status) {
        case AM_ACCESS_DENIED:
        case AM_INVALID_SESSION:
        case AM_INVALID_FQDN_ACCESS:

            //Check whether policy advices exist. If exists send
            //the advice back to client
            if ((ret == AM_SUCCESS) && (redirect_url != NULL) &&
                    (B_FALSE == am_web_use_redirect_for_advice(agent_config)) &&
                    (policy_result->advice_string != NULL)) {

                // Composite advice is sent as a POST
                char *advice_txt = NULL;
                ret = am_web_build_advice_response(policy_result, redirect_url,
                        &advice_txt);
                am_web_log_debug("%s: policy status=%s, response[%s]",
                        thisfunc, am_status_to_string(status), advice_txt);

                if (ret == AM_SUCCESS) {
                    size_t data_length = (advice_txt != NULL) ? strlen(advice_txt) : 0;
                    if (data_length > 0) {
                        //Send the headers
                        CHAR* set_cookies_list = *((CHAR**) args[2]);
                        if (set_cookies_list == NULL) {
                            advice_headers_len = strlen(advice_headers_template) + 3;
                        } else {
                            advice_headers_len = strlen(advice_headers_cookie_template) + strlen(set_cookies_list) + 3;
                        }
                        advice_headers = (char *) malloc(advice_headers_len);
                        if (advice_headers != NULL) {
                            memset(advice_headers, 0, advice_headers_len);
                            if (set_cookies_list == NULL) {
                                sprintf(advice_headers, advice_headers_template, data_length);
                            } else {
                                sprintf(advice_headers, advice_headers_cookie_template, set_cookies_list, data_length);
                                free(set_cookies_list);
                                set_cookies_list = NULL;
                            }
                            sendHdr.pszStatus = httpOk;
                            sendHdr.pszHeader = advice_headers;
                            sendHdr.cchStatus = (DWORD) strlen(httpOk);
                            sendHdr.cchHeader = (DWORD) strlen(advice_headers);
                            sendHdr.fKeepConn = FALSE;
                            pECB->dwHttpStatusCode = http200;
                            pECB->ServerSupportFunction(pECB->ConnID,
                                    HSE_REQ_SEND_RESPONSE_HEADER_EX,
                                    &sendHdr,
                                    NULL,
                                    NULL);
                            //Send the advice
                            if ((pECB->WriteClient(pECB->ConnID, (LPVOID) advice_txt,
                                    (LPDWORD) & data_length, (DWORD) 0)) == FALSE) {
                                am_web_log_error("%s: WriteClient did not "
                                        "succeed sending policy advice: "
                                        "Attempted message = %s ", thisfunc, advice_txt);
                            }
                        } else {
                            am_web_log_error("%s: Not enough memory 0x%x bytes.",
                                    thisfunc, advice_headers_len);
                        }
                    }

                } else {
                    am_web_log_error("%s: Error while building "
                            "advice response body:%s",
                            thisfunc, am_status_to_string(ret));
                }
            } else {
                // No composite advice or composite advice is redirected.
                // If there is a composite advice 
                // we need to modify the redirect_url with the policy advice
                if ((B_TRUE == am_web_use_redirect_for_advice(agent_config)) && 
                          (policy_result->advice_string != NULL)) {
                    char *redirect_url_with_advice = NULL;
                    ret = am_web_build_advice_redirect_url(policy_result, 
                            redirect_url, &redirect_url_with_advice);
                    am_web_log_debug("%s: policy status=%s, "
                                     "redirect url with advice [%s]", 
                                     thisfunc, am_status_to_string(status),
                                     redirect_url_with_advice);
                    if(ret == AM_SUCCESS) {
                        redirect_url = redirect_url_with_advice;
                    } else {
                        am_web_log_error("%s: Error while building "
                                        "advice response body:%s",
                                        thisfunc, am_status_to_string(ret));
                    }
                }
                if (ret == AM_SUCCESS && redirect_url != NULL) {
                    CHAR* set_cookies_list = *((CHAR**) args[2]);
                    am_web_log_debug("%s: policy status = %s, "
                            "redirection URL is %s",
                            thisfunc, am_status_to_string(status),
                            redirect_url);
                    if (set_cookies_list == NULL) {
                        redirect_hdr_len = sizeof (REDIRECT_TEMPLATE) +
                                strlen(redirect_url);
                    } else {
                        redirect_hdr_len = sizeof (REDIRECT_COOKIE_TEMPLATE) +
                                strlen(redirect_url) +
                                strlen(set_cookies_list);
                    }

                    redirect_header = malloc(redirect_hdr_len + 1);
                    if (redirect_header != NULL) {
                        redirect_status = httpRedirect;
                        if (set_cookies_list == NULL) {
                            _snprintf(redirect_header, redirect_hdr_len,
                                    REDIRECT_TEMPLATE, redirect_url);
                        } else {
                            _snprintf(redirect_header, redirect_hdr_len,
                                    REDIRECT_COOKIE_TEMPLATE, redirect_url,
                                    set_cookies_list);
                            free(set_cookies_list);
                            set_cookies_list = NULL;
                        }
                        am_web_log_info("%s: redirect_header = %s",
                                thisfunc, redirect_header);
                    } else {
                        am_web_log_error("%s: unable to allocate "
                                "%u bytes", thisfunc, redirect_hdr_len);
                    }
                } else {
                    if (status == AM_ACCESS_DENIED) {
                        // Only reason why we should be sending 403 forbidden.
                        // All other cases are non-deterministic.
                        redirect_status = httpForbidden;
                    }
                    am_web_log_error("%s: Error while calling "
                            "am_web_get_redirect_url(): status = %s",
                            thisfunc, am_status_to_string(ret));
                }

                if (redirect_status == httpRedirect) {
                    sendHdr.pszStatus = httpRedirect;
                    sendHdr.pszHeader = redirect_header;
                    sendHdr.cchStatus = (DWORD) strlen(httpRedirect);
                    sendHdr.cchHeader = (DWORD) strlen(redirect_header);
                    sendHdr.fKeepConn = FALSE;
                    pECB->dwHttpStatusCode = http302;
                    pECB->ServerSupportFunction(pECB->ConnID,
                            HSE_REQ_SEND_RESPONSE_HEADER_EX,
                            &sendHdr,
                            NULL,
                            NULL);
                } else {
                    size_t data_len = sizeof (FORBIDDEN_MSG) - 1;
                    const char *data = FORBIDDEN_MSG;
                    if (redirect_status == httpServerError) {
                        data = INTERNAL_SERVER_ERROR_MSG;
                        data_len = sizeof (INTERNAL_SERVER_ERROR_MSG) - 1;
                        pECB->dwHttpStatusCode = http500;
                        pECB->ServerSupportFunction(pECB->ConnID,
                                HSE_REQ_SEND_RESPONSE_HEADER,
                                "500 Internal Server Error",
                                (LPDWORD) NULL,
                                (LPDWORD) "Content-Type: text/html\r\n\r\n");
                    } else {
                        pECB->dwHttpStatusCode = http403;
                        pECB->ServerSupportFunction(pECB->ConnID,
                                HSE_REQ_SEND_RESPONSE_HEADER,
                                "403 Forbidden",
                                (LPDWORD) NULL,
                                (LPDWORD) "Content-Type: text/html\r\n\r\n");
                    }
                    if ((pECB->WriteClient(pECB->ConnID, (LPVOID) data,
                            (LPDWORD) & data_len, (DWORD) 0)) == FALSE) {
                        am_web_log_error("do_redirect() WriteClient did not "
                                "succeed: Attempted message = %s ", data);
                    }
                }
                free(redirect_header);
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
    return HSE_STATUS_SUCCESS;
}


am_status_t get_request_url(EXTENSION_CONTROL_BLOCK *pECB,
                      CHAR** requestURL, CHAR** pathInfo,
                      CHAR** origRequestURL,
                      void* agent_config)
{
    const char *thisfunc = "get_request_url()";
    CHAR *requestHostHeader = NULL;
    const CHAR* requestProtocol = NULL;
    CHAR *requestProtocolType  = NULL;
    CHAR defaultPort[TCP_PORT_ASCII_SIZE_MAX + 1] = "";
    CHAR *requestPort = NULL;
    size_t portNumber = 0; 
    CHAR *queryString = NULL;
    CHAR *baseUrl = NULL;
    CHAR *fullBaseUrl = NULL;
    size_t fullBaseUrlLength = 0;
    DWORD baseUrlLength = 0;
    CHAR *path_info = NULL;
    CHAR *newPathInfo = NULL;
    CHAR *tmpPath = NULL;
    CHAR *scriptName = NULL;
    am_status_t status = AM_SUCCESS;

    // Check whether the request is http or https
    status = get_header_value(pECB, "HTTPS", &requestProtocolType,
                              TRUE, FALSE);
    if (status == AM_SUCCESS) {
        if(strncmp(requestProtocolType,"on", 2) == 0) {
            requestProtocol = httpsProtocol;
            strcpy(defaultPort, httpsPortDefault);
        } else if(strncmp(requestProtocolType,"off", 3) == 0) {
            requestProtocol = httpProtocol;
            strcpy(defaultPort, httpPortDefault);
        } else {
            am_web_log_error("%s: Unable to get the protocol type.", thisfunc);
            status = AM_FAILURE;
        }
    }
    if (status == AM_SUCCESS) {
        am_web_log_debug("%s: requestProtocolType = %s",
                        thisfunc, requestProtocolType);
        // Get the host name
        status = get_header_value(pECB, "HEADER_Host",
                                  &requestHostHeader, TRUE, FALSE);
    }
    // Get the port number
    if (status == AM_SUCCESS) {
        CHAR *colon_ptr = strchr(requestHostHeader, ':');
        // Check if the port number is included in the host name
        if (colon_ptr != NULL) {
            requestPort = malloc(TCP_PORT_ASCII_SIZE_MAX + 1);
            if (requestPort != NULL) {
                strncpy(requestPort, colon_ptr + 1, strlen(colon_ptr)-1);
            } else {
                am_web_log_error("%s: Not enough memory to allocate "
                                 "requestPort.", thisfunc);
                status = AM_NO_MEMORY;
            }
        } else {
            // Get the port number from Server variable
            status = get_header_value(pECB, "SERVER_PORT", 
                                      &requestPort, TRUE, FALSE);
        }
    }
    //Get the base url
    if (status == AM_SUCCESS) {
        status = get_header_value(pECB, "URL", &baseUrl, TRUE, FALSE);
        baseUrlLength = (DWORD) strlen(baseUrl);
    }
    // Get the path info .
    if (status == AM_SUCCESS) {
        status = get_header_value(pECB, "PATH_INFO", &path_info, FALSE, FALSE);
    }
    // Get the script name
    if (status == AM_SUCCESS) {
        status = get_header_value(pECB, "SCRIPT_NAME", &scriptName,
                                  FALSE, FALSE);
    }
    //Remove the script name from path_info to get the real path info
    if (status == AM_SUCCESS) {
        if (path_info != NULL && scriptName != NULL) {
            tmpPath = path_info + strlen(scriptName);
            newPathInfo = strdup(tmpPath);
            if (newPathInfo != NULL) {
                *pathInfo = newPathInfo;
                am_web_log_debug("%s: Reconstructed path info = %s",
                                 thisfunc, *pathInfo );
            } else {
               am_web_log_error("%s: Unable to allocate newPathInfo.",
                                   thisfunc);
               status = AM_NO_MEMORY;
            }
        }
    }
    // Get the query string
    if (status == AM_SUCCESS) {
        status = get_header_value(pECB, "QUERY_STRING", &queryString,
                                  FALSE, FALSE);
    }
    // Check if we have to add path info to the base url
    if (status == AM_SUCCESS) {
        portNumber = atoi(requestPort);
        if ((newPathInfo != NULL) && (strlen(newPathInfo) > 0)) {
            fullBaseUrlLength = baseUrlLength + strlen(newPathInfo);
            fullBaseUrl = (char *) malloc(fullBaseUrlLength + 1);
            if(fullBaseUrl != NULL) {
               memset(fullBaseUrl, 0, sizeof(char) * (fullBaseUrlLength + 1));
               strcpy(fullBaseUrl, baseUrl);
               strcat(fullBaseUrl, newPathInfo); 
               status = am_web_get_all_request_urls(requestHostHeader,
                               requestProtocol,NULL, portNumber,
                               fullBaseUrl, queryString, agent_config,
                               requestURL, origRequestURL);
            } else {
               am_web_log_error("%s: Unable to allocate memory for "
                                "fullBaseUrl.", thisfunc);
               status = AM_NO_MEMORY;
            }
        } else {
            status = am_web_get_all_request_urls(requestHostHeader,
                                requestProtocol, NULL, portNumber, baseUrl,
                                queryString, agent_config,
                                requestURL, origRequestURL);
        }
        if(status == AM_SUCCESS) {
            am_web_log_debug("%s: Constructed request url: %s",
                             thisfunc, *requestURL);
        } else {
            am_web_log_error("%s: Failed with error: %s.",
                        thisfunc, am_status_to_string(status)); 
        }
    }
    if (requestProtocolType != NULL) {
        free(requestProtocolType);
        requestProtocolType = NULL;
    }
    if (requestHostHeader != NULL) {
        free(requestHostHeader);
        requestHostHeader = NULL;
    }
    if (requestPort != NULL) {
        free(requestPort);
        requestPort = NULL;
    }
    if (baseUrl != NULL) {
        free(baseUrl);
        baseUrl = NULL;
    }
    if (path_info != NULL) {
        free(path_info);
        path_info = NULL;
    }
    if (scriptName != NULL) {
        free(scriptName);
        scriptName = NULL;
    }
    if (queryString != NULL) {
        free(queryString);
        queryString = NULL;
    }
    if (fullBaseUrl != NULL) {
        free(fullBaseUrl);
        fullBaseUrl = NULL;
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
    
    pStartHdr = string_case_insensitive_search(*httpHeaders,key);
    if (pStartHdr != NULL) {
        tmpHdr = malloc(strlen(*httpHeaders) + 1);
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

int test_attr_exists(char *key, char* set_headers_list) {
    char *a, *ab, *hlCopy;
    hlCopy = strdup(set_headers_list);
    for ((a = strtok_s(hlCopy, ":\r\n", &ab)); a; (a = strtok_s(NULL, ":\r\n", &ab))) {
        if (strcmp(key, a) == 0) {
            free(hlCopy);
            return 1;
        }
    }
    free(hlCopy);
    return 0;
}

int vasprintf(char **buffer, const char *fmt, va_list argv) {
    int size = _vsnprintf(*buffer = NULL, 0, fmt, argv);
    if ((size > 0) && ((*buffer = malloc(size + 1)) != NULL)) {
        return vsprintf(*buffer, fmt, argv);
    }
    return size;
}

int am_sprintf(char **buffer, const char *fmt, ...) {
    int size;
    char *tmp = NULL;
    va_list ap;
    va_start(ap, fmt);
    tmp = *buffer;
    size = vasprintf(buffer, fmt, ap);
    free(tmp);
    va_end(ap);
    return size;
}

void rem_attr_headers(char** rawHeaders, char* set_headers_list) {
    int hlen = 0, nlen = 0;
    char *a, *ab, *rawCopy = NULL, *tmpHdr = NULL;
    if (*rawHeaders != NULL && set_headers_list != NULL) {
        rawCopy = strdup(*rawHeaders);
        hlen = strlen(*rawHeaders);
        for ((a = strtok_s(rawCopy, "\r\n", &ab)); a; (a = strtok_s(NULL, "\r\n", &ab))) {
            if (test_attr_exists(a, set_headers_list) == 0) {
                if (tmpHdr == NULL) {
                    nlen = am_sprintf(&tmpHdr, "%s\r\n", a);
                } else {
                    nlen = am_sprintf(&tmpHdr, "%s%s\r\n", tmpHdr, a);
                }
            }
        }
        if (nlen > hlen) {
            *rawHeaders = (char *) realloc(*rawHeaders, nlen + 1);
            memset(*rawHeaders, 0, nlen);
        } else {
            memset(*rawHeaders, 0, hlen);
        }
        strcpy(*rawHeaders, tmpHdr);
        free(rawCopy);
        free(tmpHdr);
    }
}

am_status_t set_request_headers(EXTENSION_CONTROL_BLOCK *pECB,
                                void** args, BOOL addOriginalHeaders)
{
    const char *thisfunc = "set_request_headers()";
    am_status_t status = AM_SUCCESS;
    am_status_t status1 = AM_SUCCESS;
    am_status_t status2 = AM_SUCCESS;
    CHAR* httpHeaders = NULL;
    BOOL gotHttpHeaders = FALSE;
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
    char *temp = NULL;

    if (pECB != NULL) {
        CHAR* set_headers_list = *((CHAR**) args[1]);
        CHAR* set_cookies_list = *((CHAR**) args[2]);
        CHAR** ptr = (CHAR **) args[3];
        CHAR* request_hdrs = *ptr;

        if (addOriginalHeaders == TRUE) {
            //Get the original headers from the request
            status = get_header_value(pECB, "ALL_RAW", &httpHeaders, TRUE, FALSE);
        //Remove profile attributes from original request headers, if any,
        //to avoid tampering
            if ((status == AM_SUCCESS) && (set_headers_list != NULL)) {
                rem_attr_headers(&httpHeaders, set_headers_list);
            }
            //Remove empty values from set_headers_list 
            if ((status == AM_SUCCESS) && (set_headers_list != NULL)) {
                tmpAttributeList = malloc(strlen(set_headers_list)+1);
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
        }

        //Add custom headers and/or set_cookie header to original headers
        if (status == AM_SUCCESS) {
            if (addOriginalHeaders == TRUE) {
                http_headers_length = strlen(httpHeaders);
            }
            if (set_headers_list != NULL) {
                http_headers_length = http_headers_length + 
                                strlen(set_headers_list);
            }
            if (set_cookies_list != NULL) {
                http_headers_length = http_headers_length +
                               strlen(set_cookies_list);
            }
            if (http_headers_length > 0) {
                request_hdrs = (char *)malloc(http_headers_length + 1);
                if (request_hdrs != NULL) {
                    memset(request_hdrs,0, http_headers_length + 1);
                    if (httpHeaders != NULL) {
                        strcpy(request_hdrs, httpHeaders);
                    }
                    if (set_headers_list != NULL) {
                        strcat(request_hdrs,set_headers_list);
                    }
                    if (set_cookies_list != NULL) {
                        strcat(request_hdrs,set_cookies_list);
                    }
                    *ptr = request_hdrs;
                    am_web_log_debug("%s: Final headers: %s",
                                     thisfunc, request_hdrs);
                } else {
                    am_web_log_error("%s: Not enough memory to allocate "
                                   "request_hdrs", thisfunc);
                    status = AM_NO_MEMORY;
                }
            }
        }
        if (httpHeaders != NULL) {
            free(httpHeaders);
            httpHeaders = NULL;
        }
        if (set_headers_list != NULL) {
            free(set_headers_list);
            set_headers_list = NULL;
        }
    }
    
    return status;
}

void OphResourcesFree(tOphResources* pOphResources)
{
    if (pOphResources->cookies != NULL) {
        free(pOphResources->cookies);
        pOphResources->cookies   = NULL;
        pOphResources->cbCookies    = 0;
    }
    am_web_clear_attributes_map(&pOphResources->result);
    am_policy_result_destroy(&pOphResources->result);
    return;
}

DWORD process_request_with_post_data_preservation(EXTENSION_CONTROL_BLOCK *pECB,
                                    am_status_t request_status,
                                    am_policy_result_t *policy_result,
                                    char *requestURL,
                                    void **args,
                                    char **resp,
                                    void* agent_config)
{
    const char *thisfunc = "process_request_with_post_data_preservation()";
    am_status_t status = AM_SUCCESS;
    DWORD returnValue = HSE_STATUS_SUCCESS;
    post_urls_t *post_urls = NULL;
    char *response = NULL;

    if (*resp != NULL) {
        response = *resp;
    }
    status = am_web_create_post_preserve_urls(requestURL, &post_urls,
                                              agent_config);
    if (status != AM_SUCCESS) {
        returnValue = send_error(pECB);
    }
    // In CDSSO mode, for a POST request, the post data have
    // already been saved in the response variable, so we need
    // to get them here only if response is NULL.
    if (status == AM_SUCCESS) {
        if (response == NULL) {
            status = get_post_data(pECB, &response);
            if (status != AM_SUCCESS) {
                returnValue = send_error(pECB);
            }
        }
    }
    if (status == AM_SUCCESS) {
        if (response != NULL && strlen(response) > 0) {
            if (AM_SUCCESS == register_post_data(pECB,post_urls->action_url,
                                       post_urls->post_time_key, response, 
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
                    returnValue = send_error(pECB);
                } else {
                    if (status == AM_SUCCESS) {
                        am_web_log_debug("%s: Setting LB cookie "
                                         "for post data preservation.",
                                         thisfunc);
                        set_cookie(lbCookieHeader, args);
                    }
                    returnValue = do_redirect(pECB, request_status, 
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
                returnValue = send_error(pECB);
            }
        } else {
            am_web_log_debug("%s: This is a POST request with no post data. "
                             "Redirecting as a GET request.", thisfunc);
            returnValue = do_redirect(pECB, request_status,
                                      policy_result,
                                      requestURL, 
                                      REQUEST_METHOD_GET, args,
                                      agent_config);
        }
    } 
    if (post_urls != NULL) {
        am_web_clean_post_urls(post_urls);
        post_urls = NULL;
    }

    return returnValue;
}

static DWORD redirect_to_request_url(EXTENSION_CONTROL_BLOCK *pECB,
                                  const char *redirect_url, 
                                  const char *set_cookies_list)
{
    const char *thisfunc = "redirect_to_request_url()";
    char *redirect_header = NULL;
    size_t redirect_hdr_len = 0;
    char *redirect_status = httpServerError;
    HSE_SEND_HEADER_EX_INFO sendHdr;
    DWORD returnValue = HSE_STATUS_SUCCESS;
    
    // Build the redirect header
    if (redirect_url == NULL) {
        am_web_log_error("%s: redirect_url is NULL", thisfunc);
    } else {
        if (set_cookies_list == NULL) {
            redirect_hdr_len = sizeof(REDIRECT_TEMPLATE) +
                                      strlen(redirect_url);
        } else {
            redirect_hdr_len = sizeof(REDIRECT_COOKIE_TEMPLATE) +
                                strlen(redirect_url) +
                                strlen(set_cookies_list);
        }
        redirect_header = malloc(redirect_hdr_len + 1);
        if (redirect_header == NULL) {
            am_web_log_error("%s: Not enough memory for redirect_header",
                              thisfunc);
        } else {
            redirect_status = httpRedirect;
            if (set_cookies_list == NULL) {
                _snprintf(redirect_header, redirect_hdr_len,
                          REDIRECT_TEMPLATE, redirect_url);
            } else {
                _snprintf(redirect_header, redirect_hdr_len,
                        REDIRECT_COOKIE_TEMPLATE, redirect_url,
                        set_cookies_list);
            }
        }
    }

    // Send the request
    if (redirect_status == httpRedirect) {
        am_web_log_info("%s: redirect_header = %s", 
                             thisfunc, redirect_header);
        sendHdr.pszStatus = httpRedirect;
        sendHdr.pszHeader = redirect_header;
        sendHdr.cchStatus = (DWORD) strlen(httpRedirect);
        sendHdr.cchHeader = (DWORD) strlen(redirect_header);
        sendHdr.fKeepConn = FALSE;
        pECB->dwHttpStatusCode = http302;
        pECB->ServerSupportFunction(pECB->ConnID,
                        HSE_REQ_SEND_RESPONSE_HEADER_EX,
                        &sendHdr,
                        NULL,
                        NULL);
    } else {
        returnValue = send_error(pECB);
    }

    if (redirect_header != NULL) {
        free(redirect_header);
        redirect_header = NULL;
    }
    
    return returnValue;
}

DWORD send_ok(EXTENSION_CONTROL_BLOCK *pECB) 
{
    const char *thisfunc = "send_ok()";
    const char *data = HTTP_RESPONSE_OK;
    size_t data_len = sizeof(HTTP_RESPONSE_OK) - 1;
    pECB->dwHttpStatusCode = http200;
    if ((pECB->WriteClient(pECB->ConnID, (LPVOID)data,
                     (LPDWORD)&data_len, (DWORD) 0))==FALSE)
    {
        am_web_log_error("%s: WriteClient did not succeed: "
                     "Attempted message = %s ", thisfunc, data);
    }
    return HSE_STATUS_SUCCESS_AND_KEEP_CONN;
}

DWORD getHttpStatusCode(EXTENSION_CONTROL_BLOCK *pECB) {
    const char *thisfunc = "getHttpStatusCode()";
    DWORD status = http200;
    WIN32_FIND_DATA fi;
    if (GetFileAttributes(pECB->lpszPathTranslated) == INVALID_FILE_ATTRIBUTES) {
        am_web_log_debug("%s: File/directory \"%s\" doesn't exist, setting HTTP status code to 404.", thisfunc, pECB->lpszPathTranslated);
        status = http404;
    } else {
        am_web_log_debug("%s: Requested \"%s\" resource is mapped to an existing file/directory \"%s\", setting HTTP status code to 200.",
                thisfunc, pECB->lpszPathInfo, pECB->lpszPathTranslated);
    }
    return status;
}

DWORD WINAPI HttpExtensionProc(EXTENSION_CONTROL_BLOCK *pECB)
{
    const char *thisfunc = "HttpExtensionProc()";
    CHAR* requestURL = NULL;
    CHAR* origRequestURL = NULL;
    CHAR* pathInfo = NULL;
    
    CHAR* dpro_cookie = NULL;
    am_status_t status = AM_SUCCESS;
    am_status_t status_tmp = AM_SUCCESS;
    DWORD returnValue = HSE_STATUS_SUCCESS;
    CHAR *set_cookies_list = NULL;
    CHAR *set_headers_list = NULL;
    CHAR *request_hdrs = NULL;
    CHAR *requestMethod = NULL;
    void *args[] = {(void *) pECB, (void *) &set_headers_list,
                    (void *) &set_cookies_list, (void *) &request_hdrs };
    am_map_t env_parameter_map = NULL;
    tOphResources OphResources = RESOURCE_INITIALIZER;
    tOphResources* pOphResources = &OphResources;
    CHAR* orig_req_method = NULL;
    CHAR* query = NULL;
    CHAR* response = NULL;
    BOOL fCookie = FALSE;
    DWORD cbCookiesLength = 0;
    CHAR* cookieValue = NULL;
    CHAR* post_page = NULL;
    int length = 0;
    int i = 0;
    BOOL isLocalAlloc = FALSE;
    BOOL redirectRequest = FALSE;
    const char *clientIP_hdr_name = NULL;
    char *clientIP_hdr = NULL;
    char *clientIP = NULL;
    const char *clientHostname_hdr_name = NULL;
    char *clientHostname_hdr = NULL;
    char *clientHostname = NULL;
    void *agent_config=NULL;
    char* logout_url = NULL;
    am_status_t cdStatus = AM_FAILURE; 

    // Load Agent Propeties file only once
    if (readAgentConfigFile == FALSE) {
        EnterCriticalSection(&initLock);
        if (readAgentConfigFile == FALSE) {
            if (loadAgentPropertyFile(pECB) == FALSE) {
                LeaveCriticalSection(&initLock);
                am_web_log_error("%s: Agent init failed.", thisfunc);
                return do_deny(pECB);
            }
            readAgentConfigFile = TRUE;
        }
        LeaveCriticalSection(&initLock);
    }

    agent_config = am_web_get_agent_configuration();

    // Get ther request url and the path info
    status = get_request_url(pECB, &requestURL, &pathInfo, 
                             &origRequestURL, agent_config);

    // Check whether the url is a notification url
    if ((status == AM_SUCCESS) &&
         (B_TRUE == am_web_is_notification(origRequestURL, agent_config))) {
          const char* data = NULL;
          if (pECB->cbTotalBytes > 0) {
             data =  pECB->lpbData;
             am_web_handle_notification(data, pECB->cbTotalBytes, agent_config);
             OphResourcesFree(pOphResources);
             am_web_free_memory(requestURL);
             am_web_free_memory(origRequestURL);
             if (pathInfo != NULL) {
                 free(pathInfo);
                 pathInfo = NULL;
             }
             send_ok(pECB);
             return HSE_STATUS_SUCCESS_AND_KEEP_CONN;
          }
    }
    // Set the correct HTTP status. By default the status is always 200,
    // even if the file doesn't exist. Leave status to 200 for notification
    // and dummy urls as those cases are handled by the agent.
    if (status == AM_SUCCESS) {
        char *dummyPtr = strstr(requestURL, POST_PRESERVE_URI);
        if ((am_web_is_notification(origRequestURL, agent_config) != B_TRUE) &&
            ( dummyPtr == NULL))
        {
            pECB->dwHttpStatusCode = getHttpStatusCode(pECB);
        }
    }
    // Get the request method
    if (status == AM_SUCCESS) {
        status = get_header_value(pECB, "REQUEST_METHOD", &requestMethod,
                                  TRUE, FALSE);
    }
    
    //Check if the SSO token is in the HTTP_COOKIE header
    if (status == AM_SUCCESS) {
        am_web_log_debug("%s: requestMethod = %s",thisfunc, requestMethod);
        
        // Get the HTTP_COOKIE header
        pOphResources->cbCookies  = COOKIES_SIZE_MAX + 1;
        pOphResources->cookies = malloc(pOphResources->cbCookies);
        if (pOphResources->cookies != NULL) {
            memset(pOphResources->cookies,0,pOphResources->cbCookies);
            cbCookiesLength = pOphResources->cbCookies;
            fCookie = pECB->GetServerVariable(pECB->ConnID,
                                       "HTTP_COOKIE",
                                       pOphResources->cookies,
                                       &cbCookiesLength);
            if (fCookie  &&  cbCookiesLength > 0) {
                const char *cookieName = am_web_get_cookie_name(agent_config);
                
                // Look for the iPlanetDirectoryPro cookie
                if (cookieName != NULL) {
                    cookieValue = strstr(pOphResources->cookies, cookieName);
                    while (cookieValue) {
                        char *marker = strstr(cookieValue+1, cookieName);
                        if (marker) {
                            cookieValue = marker;
                        } else {
                            break;
                        }
                    }
                    if (cookieValue != NULL && (cookieValue = strchr(cookieValue ,'=')) != NULL) {
                        cookieValue = &cookieValue[1]; // 1 vs 0 skips over '='
                        // find the end of the cookie
                        length = 0;
                        for (i=0;(cookieValue[i] != ';') &&
                              (cookieValue[i] != '\0'); i++) {
                            length++;
                        }
                        cookieValue[length]='\0';
                        if (length < URL_SIZE_MAX-1) {
                            if (length > 0) {
                                dpro_cookie = malloc(length+1);
                                if (dpro_cookie != NULL) {
                                    strncpy(dpro_cookie, cookieValue, length);
                                    dpro_cookie[length] = '\0';
                                    isLocalAlloc = TRUE;
                                    am_web_log_debug("%s: SSO token found in "
                                          " cookie header.",
                                           thisfunc);
                                } else {
                                    am_web_log_error("%s: Unable to allocate "
                                            "memory for cookie, size = %u",
                                            thisfunc, length);
                                    status = AM_NO_MEMORY;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    // If post preserve data is enabled, check if there is post data
    // in the post data cache
    if (status == AM_SUCCESS) {
        if (B_TRUE == am_web_is_postpreserve_enabled(agent_config)) {
            status = check_for_post_data(pECB, requestURL, &post_page,
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
        clientIP_hdr_name = am_web_get_client_ip_header_name(agent_config);
        if (clientIP_hdr_name != NULL) {
            status = get_header_value(pECB, clientIP_hdr_name,
                                      &clientIP_hdr,
                                      FALSE, TRUE);
        }
    }
    // Get the client host name header set by the proxy, if there is one
    if (status == AM_SUCCESS) {
        clientHostname_hdr_name = 
               am_web_get_client_hostname_header_name(agent_config);
        if (clientHostname_hdr_name != NULL) {
            status = get_header_value(pECB, clientHostname_hdr_name, 
                                      &clientHostname_hdr, FALSE, TRUE);
        }
    }
    // If the client IP and host name headers contain more than one
    // value, take the first value.
    if (status == AM_SUCCESS) {
        if ((clientIP_hdr != NULL) || (clientHostname_hdr != NULL)) {
            status = am_web_get_client_ip_host(clientIP_hdr,
                                               clientHostname_hdr,
                                               &clientIP, &clientHostname);
        }
    }
    // Set the IP address and host name in the environment map
    if ((status == AM_SUCCESS) && (clientIP_hdr != NULL)) {
        status = am_web_set_host_ip_in_env_map(clientIP, clientHostname,
                                      env_parameter_map, agent_config);
    }
    // If the client IP was not obtained previously,
    // get it from the REMOTE_ADDR header.
    if ((status == AM_SUCCESS) && (clientIP == NULL)) {
        status = get_header_value(pECB, "REMOTE_ADDR", &clientIP,
                                  FALSE, FALSE);
    }
    
    //In CDSSO mode, check if the sso token is in the post data
    if (status == AM_SUCCESS) {
        if ((am_web_is_cdsso_enabled(agent_config) == B_TRUE) &&
                  (strcmp(requestMethod, REQUEST_METHOD_POST) == 0))
        {
            if (dpro_cookie == NULL &&
                 ((post_page != NULL) || 
                   am_web_is_url_enforced(requestURL, pathInfo, 
                   clientIP, agent_config) == B_TRUE))
            {
                status = get_post_data(pECB, &response);
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
                        if(dpro_cookie != NULL) {
                            free(dpro_cookie);
                            dpro_cookie = NULL;
                        }
                        status = am_web_check_cookie_in_post(args, &dpro_cookie,
                                                &requestURL,
                                                &orig_req_method,
                                                requestMethod, response,
                                                B_FALSE, set_cookie,
                                                set_method, agent_config);
                        if (status == AM_SUCCESS) {
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
                    }
                }
            }
        }
    }

    // Check if the user is authorized to access the resource.
    if (status == AM_SUCCESS) {
        if (dpro_cookie != NULL) {
            am_web_log_debug("%s: SSO token = %s", thisfunc, dpro_cookie);
        } else {
            am_web_log_debug("%s: SSO token not found.", thisfunc);
        }
        status = am_web_is_access_allowed(dpro_cookie,
                                          requestURL,
                                          pathInfo,
                                          requestMethod,
                                          (char *)clientIP,
                                          env_parameter_map,
                                          &OphResources.result,
                                          agent_config);
        am_web_log_debug("%s: Status after "
                "am_web_is_access_allowed = %s (%d)",thisfunc,
                am_status_to_string(status), status);
        am_map_destroy(env_parameter_map);
    }

    switch(status) {
        case AM_SUCCESS:
              if (am_web_is_logout_url(requestURL,agent_config) == B_TRUE) {
                 (void)am_web_logout_cookies_reset(set_cookie, args, agent_config);
            }
            // set user attributes to http header/cookies
            status_tmp = am_web_result_attr_map_set(&OphResources.result,
                                        set_header, set_cookie,
                                        set_header_attr_as_cookie,
                                        get_cookie_sync, args, agent_config);
            if (status_tmp == AM_SUCCESS) {
                // Set request headers
                if ((set_headers_list != NULL) || (set_cookies_list != NULL)) {
                    BOOL addOriginalHeaders;
                    if (redirectRequest == TRUE) {
                        addOriginalHeaders = FALSE;
                    } else {
                        addOriginalHeaders = TRUE;
                    }
                    status_tmp = set_request_headers(pECB, args, 
                                    addOriginalHeaders);
                }
            }
            if (status_tmp == AM_SUCCESS) {
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
                        returnValue = send_error(pECB);
                    } else {
                        if (status_tmp == AM_SUCCESS) {
                            am_web_log_debug("%s: Setting LB cookie for "
                                             "post data preservation to null.",
                                             thisfunc);
                            set_cookie(lbCookieHeader, args);
                        }
                        returnValue = send_post_data(pECB, post_page, 
                                                 set_cookies_list);
                    }
                    if (lbCookieHeader != NULL) {
                        am_web_free_memory(lbCookieHeader);
                        lbCookieHeader = NULL;
                    }
                } else if (redirectRequest == TRUE) {
                    //If the property use.sunwmethod is set to false,
                    //the request method after authentication is GET
                    //The request can then be redirected to the original
                    //url instead of being handled by process_original_url().
                    //This avoids problems with ASP and perl.
                    am_web_log_debug("%s: Request redirected to orignal url after return "
                                     " from CDC servlet",thisfunc);
                    returnValue = redirect_to_request_url(pECB, 
                                  requestURL, request_hdrs);
                } else {
                    // If set_cookies_list is not empty, set the cookies 
                    // in the current response
                    if (set_cookies_list != NULL && strlen(set_cookies_list) > 0) {
                        HSE_SEND_HEADER_EX_INFO cookieResponseHdr;
                        cookieResponseHdr.pszStatus = NULL;
                        cookieResponseHdr.pszHeader = set_cookies_list;
                        cookieResponseHdr.cchStatus = 0;
                        cookieResponseHdr.cchHeader = (DWORD) strlen(set_cookies_list);
                        cookieResponseHdr.fKeepConn = TRUE;
                        pECB->ServerSupportFunction(pECB->ConnID,
                                          HSE_REQ_SEND_RESPONSE_HEADER_EX,
                                          &cookieResponseHdr,
                                          NULL,
                                          NULL);
                    }
                    returnValue = process_original_url(pECB, requestURL,
                                orig_req_method,
                                request_hdrs,
                                pOphResources,
                                agent_config);
                }
            }
            if (set_cookies_list != NULL) {
                free(set_cookies_list);
                set_cookies_list = NULL;
            }
            break;

        case AM_INVALID_SESSION:
            am_web_log_info("%s: Invalid session.",thisfunc);
            // reset ldap cookies on invalid session.
            am_web_do_cookies_reset(set_cookie, args, agent_config);
            // reset the CDSSO cookie 
            if (am_web_is_cdsso_enabled(agent_config) == B_TRUE) {
                if(am_web_do_cookie_domain_set(set_cookie, args, EMPTY_STRING, agent_config) != AM_SUCCESS) {
                    am_web_log_error("%s : CDSSO reset cookie failed. ", thisfunc);
                }
            }
            // If the post data preservation feature is enabled
            // save the post data in the cache for post requests.
            if (strcmp(requestMethod, REQUEST_METHOD_POST) == 0 
                && B_TRUE == am_web_is_postpreserve_enabled(agent_config))
            {
                returnValue = process_request_with_post_data_preservation
                                  (pECB, status, &pOphResources->result,
                                   requestURL, args, &response, agent_config);
            } else {
                returnValue = do_redirect(pECB, status,
                                          &OphResources.result,
                                          requestURL, requestMethod,
                                          args, agent_config);
            }
            break;

        case AM_ACCESS_DENIED:
            am_web_log_info("%s: Access denied to %s",thisfunc,
                            OphResources.result.remote_user ?
                            OphResources.result.remote_user : "unknown user");
            if (am_web_is_cdsso_enabled(agent_config) == B_TRUE) {
                am_web_log_debug("%s: Resetting cookie to avoid double assertion post.", thisfunc);
                am_web_do_cookies_reset(set_cookie, args, agent_config);
                if(am_web_do_cookie_domain_set(set_cookie, args, EMPTY_STRING, agent_config) != AM_SUCCESS) {
                    am_web_log_error("%s : CDSSO reset cookie failed. ", thisfunc);
                }
            }
            // If the post data preservation feature is enabled
            // save the post data in the cache for post requests.
            // This needs to be done when the access has been denied 
            // in case there is a composite advice.
            if (strcmp(requestMethod, REQUEST_METHOD_POST) == 0
                && B_TRUE == am_web_is_postpreserve_enabled(agent_config))
            {
                returnValue = process_request_with_post_data_preservation
                                  (pECB, status, &pOphResources->result,
                                   requestURL, args, &response, agent_config);
            } else {
                returnValue = do_redirect(pECB, status,
                                       &OphResources.result,
                                       requestURL, requestMethod,
                                       args, agent_config);
            }
            break;

        case AM_INVALID_FQDN_ACCESS:
            am_web_log_info("%s: Invalid FQDN access",thisfunc);
            returnValue = do_redirect(pECB, status,
                                        &OphResources.result,
                                        requestURL, requestMethod, args,
                                        agent_config);
           break;

        case AM_INVALID_ARGUMENT:
        case AM_NO_MEMORY:
        case AM_REDIRECT_LOGOUT:
            status = am_web_get_logout_url(&logout_url, agent_config);
            if(status == AM_SUCCESS) {
                returnValue = redirect_to_request_url(pECB, 
                                  logout_url, NULL);
            }
            else {
                returnValue = send_error(pECB);
                am_web_log_debug("validate_session_policy(): "
                    "am_web_get_logout_url failed. ");
            }
    break;
        case AM_FAILURE:
        default:
            am_web_log_error("%s: status: %s (%d)",thisfunc,
                              am_status_to_string(status), status);
            returnValue = send_error(pECB);
            break;
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
    if (pathInfo != NULL) {
        free(pathInfo);
        pathInfo = NULL;
    }
    if (requestMethod != NULL) {
        free(requestMethod);
        requestMethod = NULL;
    }
    if (response != NULL) {
        free(response);
        response = NULL;
    }
    if (request_hdrs != NULL) {
        free(request_hdrs);
        request_hdrs = NULL;
    }
    if (post_page != NULL) {
        free(post_page);
        post_page = NULL;
    }
    if (requestURL != NULL) {
        am_web_free_memory(requestURL);
    }
    if (origRequestURL != NULL) {
        am_web_free_memory(origRequestURL);
    }
    if(clientIP_hdr !=NULL){
        free(clientIP_hdr);
        clientIP_hdr = NULL;
    }
    if (clientIP != NULL) {
        am_web_free_memory(clientIP);
    }
    if(clientHostname_hdr != NULL){
        free(clientHostname_hdr);
        clientHostname_hdr = NULL;
    }
    if (clientHostname != NULL) {
        am_web_free_memory(clientHostname);
    }
    if (logout_url != NULL) {
        am_web_free_memory(logout_url);
    }
    OphResourcesFree(pOphResources);
    am_web_delete_agent_configuration(agent_config);

    return returnValue;
}


BOOL iisaPropertiesFilePathGet(CHAR** propertiesFileFullPath, char* instanceId,
        BOOL isBootStrapFile)
{
    // Max WINAPI path
    const DWORD dwPropertiesFileFullPathSize = MAX_PATH + 1;
    CHAR  szPropertiesFileName[512]          = "";
    CHAR agentApplicationSubKey[1000]        = "";
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

    strcpy(agentApplicationSubKey,
        "Software\\Sun Microsystems\\OpenSSO IIS6 Agent\\Identifier_");
    if (instanceId != NULL) {
       strcat(agentApplicationSubKey,instanceId);
    }
    ///////////////////////////////////////////////////////////////////
    //  get the location of the properties file from the registry
    lRet = RegOpenKeyEx(HKEY_LOCAL_MACHINE, agentApplicationSubKey,
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
                           *propertiesFileFullPath,
                           &dwPropertiesFileFullPathLen);
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
    return TRUE;
}

// Primitive error logger here that works before before policy_error() is
// initialized.
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

BOOL WINAPI TerminateExtension(DWORD dwFlags)
{
    am_status_t status = am_web_cleanup();
    DeleteCriticalSection(&initLock);
    return TRUE;
}

char* string_case_insensitive_search(char *HTTPHeaders, char *KeY)
{
    char *h, *n;
    if(!*KeY) {
        return HTTPHeaders;
    }
    for(; *HTTPHeaders; ++HTTPHeaders) {
        if(toupper(*HTTPHeaders) == toupper(*KeY)) {
            for(h=HTTPHeaders, n=KeY; *h && *n; ++h,++n) {
                if(toupper(*h)!=toupper(*n)) {
		    break;
		}
	    }
            if(!*n) {
	        return HTTPHeaders;
	    }
        }
    }
    return NULL;
}
