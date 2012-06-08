/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2012 ForgeRock AS. All Rights Reserved
 *
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at
 * http://forgerock.org/license/CDDLv1.0.html
 * See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at http://forgerock.org/license/CDDLv1.0.html
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 */

#include <stdlib.h>
#include <pthread.h>
#include <stdio.h>
#include <sys/socket.h>
#include <netdb.h>

#include <vrt.h>
#include <bin/varnishd/cache.h>
#include <vct.h>

#include <apr_general.h>
#include <apr_strings.h>
#include <apr_hash.h>
#include <apr_tables.h>

#include <am_web.h>

typedef struct sess sess;

typedef struct resp_data {
    int status;
    apr_table_t* headers_out;
    char* body;
} resp_data;

typedef struct sess_record {
    sess* s;
    apr_pool_t* pool;
    resp_data response;
} sess_record;

typedef struct itd {
    sess_record* r;
    enum gethdr_e hdr;
} itd;

typedef enum {
    OK = 0,
    DONE = 1,
} ret_status;

#define	LOG(...)                fprintf(stderr, __VA_ARGS__);
#define POOL_KEY                "AM_VARNISH_PA_PK"
#define	MAGIC_STR		"sunpostpreserve"
#define	POST_PRESERVE_URI	"/dummypost/"MAGIC_STR

static pthread_mutex_t s_module_mutex = PTHREAD_MUTEX_INITIALIZER;
static pthread_mutex_t init_mutex = PTHREAD_MUTEX_INITIALIZER;
static boolean_t agentInitialized = B_FALSE;
static apr_pool_t* s_module_pool = NULL;
static apr_hash_t* s_module_storage = NULL;

static int iterate_func(void *v, const char *key, const char *value) {
    itd *d = (itd *) v;
    if (key == NULL || value == NULL || value[0] == '\0') return 1;
    VRT_SetHdr(d->r->s, d->hdr, apr_psprintf(d->r->pool, "%c%s:", (int) strlen(key) + 1, key), value, vrt_magic_string_end);
    return 1;
}

static void fill_http(sess_record *sp, int done) {
    itd d;
    d.r = sp;
    d.hdr = (done == 1 ? HDR_OBJ : HDR_RESP);
    apr_table_do(iterate_func, &d, sp->response.headers_out, NULL);
    if (done == 1 && sp->response.status != 0) {
        http_PutStatus(sp->s->obj->http, sp->response.status);
    }
    if (done == 1 && sp->response.body != NULL && sp->response.body[0] != '\0') {
        VRT_synth_page(sp->s, 0, sp->response.body, vrt_magic_string_end);
    }
}

static void ap_custom_response(sess_record* rec, int status, char* data) {
    rec->response.status = status;
    rec->response.body = apr_pstrdup(rec->pool, data);
}

sess_record* get_sess_rec(const sess* s) {
    if (B_FALSE == agentInitialized) return NULL;
    apr_pool_t* pool = NULL;
    sess_record* rec = NULL;
    pthread_mutex_lock(&s_module_mutex);
    pool = (apr_pool_t*) apr_hash_get(s_module_storage, &s, sizeof (sess*));
    pthread_mutex_unlock(&s_module_mutex);
    if (NULL != pool)
        apr_pool_userdata_get((void**) &rec, POOL_KEY, pool);
    return rec;
}

sess_record* on_request_init(const sess* s) {
    void* key = 0;
    apr_pool_t* pool = NULL;
    sess_record* rec = NULL;
    apr_status_t status = APR_EINIT;
    pthread_mutex_lock(&s_module_mutex);
    status = apr_pool_create(&pool, s_module_pool);
    if (status == APR_SUCCESS) {
        rec = (sess_record*) apr_palloc(pool, sizeof (sess_record));
        if (rec != NULL) {
            rec->response.body = NULL;
            rec->response.status = 0;
            rec->response.headers_out = apr_table_make(pool, 0);
            if (rec->response.headers_out != NULL) {
                rec->pool = pool;
                rec->s = (sess*) s;
                status = apr_pool_userdata_setn(rec, POOL_KEY, NULL, pool);
                if (status == APR_SUCCESS) {
                    key = apr_palloc(pool, sizeof (s));
                    if (key != NULL) {
                        memcpy(key, &s, sizeof (s));
                        apr_hash_set(s_module_storage, key, sizeof (s), pool);
                        status = APR_SUCCESS;
                    }
                }
            } else status = APR_EINIT;
        }
        if (status != APR_SUCCESS && pool != NULL) apr_pool_destroy(pool);
    }
    pthread_mutex_unlock(&s_module_mutex);
    return rec;
}

static void on_request_cleanup(const sess* s) {
    apr_pool_t* pool = NULL;
    pthread_mutex_lock(&s_module_mutex);
    pool = (apr_pool_t*) apr_hash_get(s_module_storage, &s, sizeof (s));
    if (pool != NULL) {
        apr_hash_set(s_module_storage, &s, sizeof (s), NULL);
        apr_pool_destroy(pool);
    }
    pthread_mutex_unlock(&s_module_mutex);
}

static am_status_t content_read(void **args, char **rbuf) {
    char thisfunc[] = "content_read()";
    const char* cl = "\017Content-Length:";

    sess_record *r;
    char *ptr, *endp;
    unsigned long content_length;
    am_status_t sts = AM_SUCCESS;

    int total_read = 0;
    int bytes_read;
    int bytes_to_read;

    const int buf_length = 8192;
    char buf[buf_length];

    if (args == NULL || (r = args[0]) == NULL) {
        am_web_log_error("%s: invalid arguments passed.", thisfunc);
        sts = AM_INVALID_ARGUMENT;
    } else if (ptr = VRT_GetHdr(r->s, HDR_REQ, cl)) {
        content_length = strtoul(ptr, &endp, 10);
        ptr = 0;
        while (content_length > 0) {
            bytes_to_read = content_length > buf_length ? buf_length : content_length;
            bytes_read = HTC_Read(r->s->htc, buf, bytes_to_read);
            if (bytes_read <= 0) {
                sts = AM_FAILURE;
                break;
            }
            *rbuf = apr_pcalloc(r->pool, total_read + bytes_read);
            if (*rbuf == 0) {
                sts = AM_FAILURE;
                break;
            }
            if (ptr) {
                memcpy(*rbuf, ptr, total_read);
            }
            memcpy(*rbuf + total_read, buf, bytes_read);
            total_read += bytes_read;
            ptr = *rbuf;
            content_length -= bytes_read;
        }
    } else
        sts = AM_FAILURE;

    if (AM_SUCCESS == sts)
        VRT_SetHdr(r->s, HDR_REQ, cl, NULL, vrt_magic_string_end);

    am_web_log_debug("%s: %d bytes read", thisfunc, total_read);
    am_web_log_max_debug("\n%s\n", *rbuf);
    return sts;
}

static am_status_t set_header_in_request(void **args, const char *key, const char *value) {
    sess_record * rec = (sess_record*) args[0];
    sess* sp = rec->s;
    const char *thisfunc = "set_header_in_request()";
    am_status_t sts = AM_SUCCESS;
    if (rec == NULL || key == NULL) {
        am_web_log_error("%s: invalid argument passed.", thisfunc);
        sts = AM_INVALID_ARGUMENT;
    } else {
        if (value != NULL && *value != '\0') {
            VRT_SetHdr(sp, HDR_REQ, apr_psprintf(rec->pool, "%c%s:", (int) strlen(key) + 1, key), value, vrt_magic_string_end);
        }
        sts = AM_SUCCESS;
    }
    return sts;
}

static am_status_t add_header_in_response(void **args, const char *key, const char *values) {
    const char *thisfunc = "add_header_in_response()";
    sess_record* r = NULL;
    am_status_t sts = AM_SUCCESS;
    if (args == NULL || (r = (sess_record *) args[0]) == NULL || key == NULL) {
        am_web_log_error("%s: invalid argument passed", thisfunc);
        sts = AM_INVALID_ARGUMENT;
    } else {
        apr_table_add(r->response.headers_out, key, values);
        sts = AM_SUCCESS;
    }
    return sts;
}

static am_status_t set_user(void **args, const char *user) {
    const char *thisfunc = "set_user()";
    sess_record* r = NULL;
    am_status_t sts = AM_SUCCESS;
    if (args == NULL || (r = (sess_record *) args[0]) == NULL) {
        am_web_log_error("%s: invalid argument passed", thisfunc);
        sts = AM_INVALID_ARGUMENT;
    } else {
        if (user != NULL)
            apr_table_add(r->response.headers_out, "REMOTE_USER", user);
        sts = AM_SUCCESS;
    }
    return sts;
}

static am_status_t render_result(void **args, am_web_result_t http_result, char *data) {
    const char *thisfunc = "render_result()";
    sess_record* rec = NULL;
    am_status_t sts = AM_SUCCESS;
    int *ret = NULL;
    int len = 0;

    if (args == NULL || (rec = (sess_record *) args[0]) == NULL,
            (ret = (int *) args[1]) == NULL ||
            ((http_result == AM_WEB_RESULT_OK_DONE ||
            http_result == AM_WEB_RESULT_REDIRECT) &&
            (data == NULL || *data == '\0'))) {
        am_web_log_error("%s: invalid arguments received", thisfunc);
        sts = AM_INVALID_ARGUMENT;
    } else {
        switch (http_result) {
            case AM_WEB_RESULT_OK:
                *ret = OK;
                break;
            case AM_WEB_RESULT_OK_DONE:
                if (data && ((len = strlen(data)) > 0)) {
                    rec->response.status = 200;
                    apr_table_addn(rec->response.headers_out, "Content-Type", "text/html");
                    apr_table_addn(rec->response.headers_out, "Content-Length", apr_itoa(rec->pool, len));
                    rec->response.body = apr_pstrdup(rec->pool, data);
                    *ret = DONE;
                } else {
                    *ret = OK;
                }
                break;
            case AM_WEB_RESULT_REDIRECT:
                apr_table_add(rec->response.headers_out, "Location", data);
                ap_custom_response(rec, 302, apr_psprintf(rec->pool, "<head><title>Document Moved</title></head>\n"
                        "<body><h1>Object Moved</h1>This document may be found "
                        "<a HREF=\"%s\">here</a></body>", data));
                *ret = DONE;
                break;
            case AM_WEB_RESULT_FORBIDDEN:
                rec->response.status = 403;
                *ret = DONE;
                break;
            case AM_WEB_RESULT_ERROR:
                rec->response.status = 500;
                *ret = DONE;
                break;
            default:
                am_web_log_error("%s: Unrecognized process result %d", thisfunc, http_result);
                rec->response.status = 500;
                *ret = DONE;
                break;
        }
        sts = AM_SUCCESS;
    }
    return sts;
}

static am_status_t set_method(void **args, am_web_req_method_t method) {
    const char *thisfunc = "set_method()";
    struct sess_record * rec = (struct sess_record*) args[0];
    struct sess* sp = rec->s;
    am_status_t sts = AM_SUCCESS;
    if (args == NULL || rec == NULL || sp->http == NULL) {
        am_web_log_error("%s: invalid argument passed.", thisfunc);
        sts = AM_INVALID_ARGUMENT;
    } else {
        http_SetH(sp->http, HTTP_HDR_REQ, apr_pstrdup(rec->pool, am_web_method_num_to_str(method)));
        sts = AM_SUCCESS;
    }
    return sts;
}

static am_web_req_method_t get_method_num(sess_record *sp) {
    const char *thisfunc = "get_method_num()";
    am_web_req_method_t method_num = AM_WEB_REQUEST_UNKNOWN;
    if (sp == NULL || sp->s->http == NULL) {
        am_web_log_error("%s: invalid argument passed.", thisfunc);
    } else {
        method_num = am_web_method_str_to_num(http_GetReq(sp->s->http));
        am_web_log_debug("%s: Method string is %s", thisfunc, http_GetReq(sp->s->http));
        am_web_log_debug("%s: Varnish method number corresponds to %s method",
                thisfunc, am_web_method_num_to_str(method_num));
    }
    return method_num;
}

static char* get_query_string(const char* url, apr_pool_t *pool) {
    char thisfunc[] = "get_query_string()";
    char *ptr;
    if (url == 0 || pool == 0) {
        am_web_log_error("%s: invalid arguments passed.", thisfunc);
        return "";
    }
    ptr = strstr(url, "?");
    if (ptr == 0) {
        return "";
    }
    return apr_pstrdup(pool, ptr + 1);
}

static const char* get_req_header(sess_record* r, const char* key) {
    char thisfunc[] = "get_req_header()";
    if (r == NULL || r->s == NULL) {
        am_web_log_error("%s: invalid arguments passed.", thisfunc);
        return NULL;
    }
    return VRT_GetHdr(r->s, HDR_REQ, apr_psprintf(r->pool, "%c%s:", (int) strlen(key) + 1, key));
}

static am_status_t update_post_data_for_request(void **args, const char *key, const char *acturl, const char *value, const unsigned long postcacheentry_life) {
    const char *thisfunc = "update_post_data_for_request()";
    am_web_postcache_data_t post_data;
    void *agent_config = NULL;
    am_status_t status = AM_SUCCESS;
    agent_config = am_web_get_agent_configuration();
    if (agent_config == NULL || key == NULL || acturl == NULL) {
        am_web_log_error("%s: invalid argument passed.", thisfunc);
        status = AM_INVALID_ARGUMENT;
    } else {
        post_data.value = (char *) value;
        post_data.url = (char *) acturl;
        am_web_log_debug("%s: Register POST data key :%s", thisfunc, key);
        if (am_web_postcache_insert(key, &post_data, agent_config) == B_FALSE) {
            am_web_log_error("Register POST data insert into hash table failed: %s", key);
            status = AM_FAILURE;
        }
    }
    return status;
}

static am_status_t check_for_post_data(void **args, const char *requestURL, char **page, const unsigned long postcacheentry_life) {
    const char *thisfunc = "check_for_post_data()";
    const char *post_data_query = NULL;
    am_web_postcache_data_t get_data = {NULL, NULL};
    const char *actionurl = NULL;
    const char *postdata_cache = NULL;
    am_status_t status = AM_SUCCESS;
    am_status_t status_tmp = AM_SUCCESS;
    void *agent_config = NULL;
    char *buffer_page = NULL;
    char *stickySessionValue = NULL;
    char *stickySessionPos = NULL;
    char *temp_uri = NULL;
    *page = NULL;

    agent_config = am_web_get_agent_configuration();

    if (agent_config == NULL) {
        am_web_log_error("%s: unable to get agent configuration", thisfunc);
        status = AM_FAILURE;
    }

    if (requestURL == NULL) {
        status = AM_INVALID_ARGUMENT;
    }
    // Check if magic URI is present in the URL
    if (status == AM_SUCCESS) {
        post_data_query = strstr(requestURL, POST_PRESERVE_URI);
        if (post_data_query != NULL) {
            post_data_query += strlen(POST_PRESERVE_URI);
            // Check if a query parameter for the  sticky session has been
            // added to the dummy URL. Remove it if it is the case.
            status_tmp = am_web_get_postdata_preserve_URL_parameter(&stickySessionValue, agent_config);
            if (status_tmp == AM_SUCCESS) {
                stickySessionPos = strstr((char *) post_data_query, stickySessionValue);
                if (stickySessionPos != NULL) {
                    size_t len = strlen(post_data_query) - strlen(stickySessionPos) - 1;
                    temp_uri = (char *) malloc(len + 1);
                    memset(temp_uri, 0, len + 1);
                    strncpy(temp_uri, post_data_query, len);
                    post_data_query = temp_uri;
                }
            }
        }
    }
    // If magic uri present search for corresponding value in hashtable
    if ((status == AM_SUCCESS) && (post_data_query != NULL) && (strlen(post_data_query) > 0)) {
        am_web_log_debug("%s: POST Magic Query Value: %s", thisfunc, post_data_query);
        if (am_web_postcache_lookup(post_data_query, &get_data,
                agent_config) == B_TRUE) {
            postdata_cache = get_data.value;
            actionurl = get_data.url;
            am_web_log_debug("%s: POST hashtable actionurl: %s", thisfunc, actionurl);
            // Create the post page
            buffer_page = am_web_create_post_page(post_data_query,
                    postdata_cache, actionurl, agent_config);
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
            am_web_log_error("%s: Found magic URI (%s) but entry is not in POST hash table", thisfunc, post_data_query);
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

void vmod_init(struct sess* s, const char *agent_bootstrap_file, const char *agent_config_file) {
    am_status_t amstatus = AM_SUCCESS;
    apr_status_t status = apr_initialize();
    if (APR_SUCCESS != status) {
        LOG("am_vmod_init apr_initialize failed\n");
        return;
    }
    status = apr_pool_create(&s_module_pool, NULL);
    if (APR_SUCCESS != status) {
        LOG("am_vmod_init apr_pool_create failed\n");
        apr_terminate();
        return;
    }
    s_module_storage = apr_hash_make(s_module_pool);
    if (s_module_storage == NULL) {
        LOG("am_vmod_init apr_hash_make failed\n");
        apr_pool_destroy(s_module_pool);
        apr_terminate();
        return;
    }

    pthread_mutex_lock(&init_mutex);
    if ((amstatus = am_web_init(agent_bootstrap_file, agent_config_file)) != AM_SUCCESS) {
        LOG("am_vmod_init am_web_init failed: %s (%d)\n", am_status_to_string(amstatus), amstatus);
        status = APR_EINIT;
    }
    if (APR_SUCCESS == status) {
        if ((amstatus = am_agent_init(&agentInitialized)) != AM_SUCCESS) {
            LOG("am_vmod_init am_agent_init failed: %s (%d)\n", am_status_to_string(amstatus), amstatus);
            status = APR_EINIT;
        }
    }
    pthread_mutex_unlock(&init_mutex);

    if (APR_SUCCESS != status) {
        apr_pool_destroy(s_module_pool);
        apr_terminate();
    }
}

void vmod_cleanup(struct sess * s __attribute__((unused))) {
    if (B_TRUE == agentInitialized) {
        am_web_cleanup();
        am_shutdown_nss();
        apr_pool_destroy(s_module_pool);
        apr_terminate();
    }
}

static void send_deny(sess_record * rec) {
    if (NULL == rec) return;
    apr_table_addn(rec->response.headers_out, "Content-Type", "text/plain");
    ap_custom_response(rec, 403, "403 Forbidden");
}

static void send_ok(sess_record * rec) {
    if (NULL == rec) return;
    apr_table_addn(rec->response.headers_out, "Content-Type", "text/plain");
    ap_custom_response(rec, 200, "OK");
}

static void send_error(sess_record * rec) {
    if (NULL == rec) return;
    apr_table_addn(rec->response.headers_out, "Content-Type", "text/plain");
    ap_custom_response(rec, 500, "500 Internal Server Error");
}

unsigned vmod_authenticate(struct sess *s, const char *req_method, const char *proto, const char *host, int port, const char *uri, struct sockaddr_storage * cip) {
    char thisfunc[] = "vmod_authenticate()";
    void *agent_config = NULL;
    am_status_t status = AM_FAILURE;
    char *url = NULL;
    sess_record* r = NULL;
    int ret = OK;
    void *args[] = {NULL, (void*) &ret};
    socklen_t addr_len = sizeof (*cip);
    char client_ip[INET6_ADDRSTRLEN];
    char client_host[NI_MAXHOST];
    am_web_req_method_t method;
    am_web_request_params_t req_params;
    am_web_request_func_t req_func;

    memset((void *) & req_params, 0, sizeof (req_params));
    memset((void *) & req_func, 0, sizeof (req_func));

    if (agentInitialized != B_TRUE || (r = on_request_init(s)) == NULL) {
        send_deny(r);
        return 0;
    }

    args[0] = r;
    agent_config = am_web_get_agent_configuration();

    if (agent_config == NULL) {
        send_deny(r);
        return 0;
    }

    am_web_log_debug("Begin process %s request, proto: %s, host: %s, port: %d, uri: %s", req_method, proto, host, port, uri);

    if (proto == NULL) {
        url = apr_psprintf(r->pool, "http://%s%s", host, uri);
    } else {
        url = apr_psprintf(r->pool, "%s://%s%s", proto, host, uri);
    }

    method = get_method_num(r);
    if (method == AM_WEB_REQUEST_UNKNOWN) {
        am_web_log_error("%s: Request method is unknown.", thisfunc);
        status = AM_FAILURE;
    } else {
        status = AM_SUCCESS;
    }

    if (status == AM_SUCCESS) {
        if (B_TRUE == am_web_is_notification(url, agent_config)) {
            char* data = NULL;
            status = content_read(args, &data);
            if (status == AM_SUCCESS && data != NULL && strlen(data) > 0) {
                am_web_handle_notification(data, strlen(data), agent_config);
                am_web_delete_agent_configuration(agent_config);
                send_ok(r);
                return 0;
            } else {
                am_web_log_error("%s: content_read for notification failed, %s", thisfunc, am_status_to_string(status));
            }
        }
    }

    if (status == AM_SUCCESS) {
        int err = getnameinfo((struct sockaddr*) cip, addr_len, client_ip, sizeof (client_ip), 0, 0, NI_NUMERICHOST);
        if (err == 0) {
            am_web_log_debug("%s: client ip: %s", thisfunc, client_ip);
            req_params.client_ip = client_ip;
        }
        err = getnameinfo((struct sockaddr*) cip, addr_len, client_host, sizeof (client_host), NULL, 0, NI_NAMEREQD);
        if (err == 0) {
            am_web_log_debug("%s: client host: %s", thisfunc, client_host);
            req_params.client_hostname = client_host;
        }
        if ((req_params.client_ip == NULL) || (strlen(req_params.client_ip) == 0)
                || (req_params.client_hostname == NULL) || (strlen(req_params.client_hostname) == 0)) {
            am_web_log_error("%s: Could not get the remote host/ip (error: %d)", thisfunc, errno);
            status = AM_FAILURE;
        }
    }

    if (status == AM_SUCCESS) {
        req_params.url = url;
        req_params.query = get_query_string(url, r->pool);
        req_params.method = method;
        req_params.path_info = ""; // N/A in Varnish
        req_params.cookie_header_val = (char *) get_req_header(r, "Cookie");
        req_func.get_post_data.func = content_read;
        req_func.get_post_data.args = args;
        req_func.set_user.func = set_user;
        req_func.set_user.args = args;
        req_func.set_method.func = set_method;
        req_func.set_method.args = args;
        req_func.set_header_in_request.func = set_header_in_request;
        req_func.set_header_in_request.args = args;
        req_func.add_header_in_response.func = add_header_in_response;
        req_func.add_header_in_response.args = args;
        //TODO:
        //req_func.set_notes_in_request.func = set_notes_in_request;
        //req_func.set_notes_in_request.args = args;
        req_func.render_result.func = render_result;
        req_func.render_result.args = args;
        req_func.reg_postdata.func = update_post_data_for_request;
        req_func.reg_postdata.args = args;
        req_func.check_postdata.func = check_for_post_data;
        req_func.check_postdata.args = args;

        (void) am_web_process_request(&req_params, &req_func, &status, agent_config);

        if (status != AM_SUCCESS) {
            am_web_log_error("%s: error from am_web_process_request: %s", thisfunc, am_status_to_string(status));
        }
    }

    am_web_delete_agent_configuration(agent_config);

    if (status != AM_SUCCESS) {
        am_web_log_error("%s: error encountered rendering result: %s", thisfunc, am_status_to_string(status));
        send_error(r);
        return 0;
    }
    return (0 == ret);
}

void vmod_done(struct sess * s) {
    char thisfunc[] = "vmod_done()";
    sess_record* r = get_sess_rec(s);
    if (r != NULL) {
        fill_http(r, 1);
    } else {
        am_web_log_error("%s: invalid argument", thisfunc);
        http_PutStatus(s->obj->http, 403);
    }
    on_request_cleanup(s);
}

void vmod_ok(struct sess * s) {
    sess_record* r = get_sess_rec(s);
    if (r != NULL) {
        fill_http(r, 0);
    }
    on_request_cleanup(s);
}