/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2011 ForgeRock AS. All Rights Reserved
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

/*
 * Portions Copyrighted 2011 TOOLS.LV SIA
 */

#include "sdk.hpp"
#include <assert.h>
#include <vector>
#include <stdio.h>
#include <string.h>
#include <time.h>
#include <stdarg.h>

#ifdef _MSC_VER
#include <windows.h>
#include <winsock.h>
#include <intrin.h>
#include <iterator> 
#else
#include <sys/time.h>
#include <netdb.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <ctype.h>
#endif

#include <algorithm>
#include <cctype>
#include <functional>

#include <nspr.h>
#include <nss.h> 
#include <pk11pub.h>
#include <ssl.h> 
#include <sslproto.h>
#include <key.h>

const char* INTERNAL_TOKEN_NAME = "internal                         ";

extern "C" SECStatus acceptAnyCertificate(void *, PRFileDesc *, PRBool, PRBool) {
    return SECSuccess;
}

static char toLowerCase(char c) {
    return char(::tolower((unsigned char) (c)));
}

void sdk::utils::url::parse(const std::string& url_s) {
    const std::string prot_end("://");
    std::string::const_iterator prot_i = std::search(url_s.begin(), url_s.end(),
            prot_end.begin(), prot_end.end());
    if (prot_i == url_s.end())
        return;
    protocol_.reserve(std::distance(url_s.begin(), prot_i));
    std::transform(url_s.begin(), prot_i,
            std::back_inserter(protocol_),
            std::ptr_fun<char>(toLowerCase));
    std::advance(prot_i, prot_end.length());
    std::string::const_iterator path_i = std::find(prot_i, url_s.end(), ':');
    if (path_i != url_s.end()) {
        host_.reserve(std::distance(prot_i, path_i));
        std::transform(prot_i, path_i,
                std::back_inserter(host_),
                std::ptr_fun<char>(toLowerCase));
        ++path_i; //skip over :
        std::string::const_iterator port_i = std::find(path_i, url_s.end(), '/');
        port_.assign(path_i, port_i);
        std::advance(path_i, port_.length());
    } else {
        //port is not available
        path_i = std::find(prot_i, url_s.end(), '/');
        host_.reserve(std::distance(prot_i, path_i));
        std::transform(prot_i, path_i,
                std::back_inserter(host_),
                std::ptr_fun<char>(toLowerCase));
    }

    //validate host value
#ifdef _MSC_VER
    WSADATA wsaData;
    WSAStartup(MAKEWORD(2, 2), &wsaData);
#endif
    if (inet_addr(host_.c_str()) == INADDR_NONE && gethostbyname(host_.c_str()) == NULL) {
        host_.clear();
        return;
    }

    std::string::const_iterator query_i = std::find(path_i, url_s.end(), '?');
    path_.assign(path_i, query_i);
    if (query_i != url_s.end())
        ++query_i;
    query_.assign(query_i, url_s.end());
}

std::string sdk::utils::format(const char *fmt, ...) {
    va_list ap;
    int size = 1024;
    std::vector<char> buf(size);
    va_start(ap, fmt);
    int needed = vsnprintf(&buf[0], buf.size(), fmt, ap);
    va_end(ap);
    if (needed >= size) {
        buf.resize(needed + 1);
        va_start(ap, fmt);
        needed = vsnprintf(&buf[0], buf.size(), fmt, ap);
        va_end(ap);
        assert(needed < buf.size());
    }
    return std::string(&buf[0]);
}

std::string sdk::utils::urldecode(std::string &src) {
    std::string ret;
    char ch;
    int i, ii;
    for (i = 0; i < src.length(); i++) {
        if (int(src[i]) == 37) {
            sscanf(src.substr(i + 1, 2).c_str(), "%x", &ii);
            ch = static_cast<char> (ii);
            ret += ch;
            i = i + 2;
        } else {
            ret += src[i];
        }
    }
    return (ret);
}

inline std::string sdk::utils::trim(std::string &str) {
    str.erase(0, str.find_first_not_of(' '));
    str.erase(str.find_last_not_of(' ') + 1);
    return str;
}

void sdk::utils::stringtokenize(std::string &str, std::string separator, std::list<std::string>* results) {
    int found;
    found = str.find_first_of(separator);
    while (found != std::string::npos) {
        if (found > 0) {
            std::string strt(str.substr(0, found));
            results->push_back(trim(strt));
        }
        str = str.substr(found + 1);
        found = str.find_first_of(separator);
    }
    if (str.length() > 0) {
        std::string strt(str);
        results->push_back(trim(strt));
    }
}

std::string sdk::utils::timestamp() {
    int offset;
    char time_string[20];
    char time_string_tz[30];
#ifdef _MSC_VER
    struct tm ptmw;
    time_t rawtime;
    time(&rawtime);
    localtime_s(&ptmw, &rawtime);
    offset = (-(int) timezone);
    if (ptmw.tm_isdst)
        offset += 3600;
    strftime(time_string, sizeof (time_string), "%Y-%m-%d %H:%M:%S", &ptmw);
#else
    struct tm *ptm;
    struct timeval tv;
    gettimeofday(&tv, NULL);
    ptm = localtime(&tv.tv_sec);
    offset = (-(int) timezone); //ptm->tm_gmtoff;
    if (ptm->tm_isdst)
        offset += 3600;
    strftime(time_string, sizeof (time_string), "%Y-%m-%d %H:%M:%S", ptm);
#endif
    snprintf(time_string_tz, sizeof (time_string_tz), "%s %+03d%02d", time_string, (int) (offset / 3600),
            (int) ((abs((int) offset) / 60) % 60));
    return std::string(time_string_tz);
}

int sdk::utils::validate_agent_credentials(url *u, const char *aname, const char *apwd, const char *arealm,
        const char *ssldb, const char *sslpwd, int init) {
    SECStatus secStatus;
    PRStatus prStatus;
    PRInt32 rv;
    PRNetAddr addr;
    PRHostEnt hostEntry;
    PRFileDesc *sslSocket;
    PRFileDesc *tcpSocket;
    PRSocketOptionData socketOption;
    char buffer[8192];
    char buffer_out[8192];
    char *tptr = NULL;
    int ret = 0;
    std::string dpath;

    size_t dui = u->path().find("/namingservice");
    if (dui != std::string::npos) {
        dpath = u->path().substr(1, dui);
    }
    std::string postData = sdk::utils::format("username=%s&password=%s&uri=realm=%s", aname, apwd, arealm);
    std::string postBuffer = sdk::utils::format("POST /%s/identity/authenticate HTTP/1.0\r\nContent-Type: %s\r\nContent-length: %d\r\nHost: %s\r\nConnection: close\r\n\r\n%s\r\n",
            dpath.c_str(),
            "application/x-www-form-urlencoded",
            postData.length(),
            u->host().c_str(),
            postData.c_str());

    if (init == 1) {
        PR_Init(PR_USER_THREAD, PR_PRIORITY_NORMAL, 0);

        if (ssldb == NULL) {
            NSS_NoDB_Init(NULL);
        } else {
            if ((secStatus = NSS_Init(ssldb)) != SECSuccess) {
                PRErrorCode prError = PR_GetError();
                return prError;
            }
        }

        if ((secStatus = NSS_SetDomesticPolicy()) != SECSuccess) {
            PRErrorCode prError = PR_GetError();
            return prError;
        }

        PK11_ConfigurePKCS11(NULL, NULL, NULL, INTERNAL_TOKEN_NAME, NULL, NULL, NULL, NULL, 8, 1);
        SSL_ShutdownServerSessionIDCache();

        SSL_ConfigMPServerSIDCache(NULL, 100, 86400L, NULL);
        SSL_ConfigServerSessionIDCache(NULL, 100, 86400L, NULL);
    }
    SSL_ClearSessionCache();

    if ((tcpSocket = PR_NewTCPSocket()) == NULL) {
        PRErrorCode prError = PR_GetError();
        return prError;
    }

    socketOption.option = PR_SockOpt_Nonblocking;
    socketOption.value.non_blocking = PR_FALSE;

    if ((prStatus = PR_SetSocketOption(tcpSocket, &socketOption)) != PR_SUCCESS) {
        PRErrorCode prError = PR_GetError();
        PR_Close(tcpSocket);
        return prError;
    }

    if (u->protocol() == "https") {
        if ((sslSocket = SSL_ImportFD(NULL, tcpSocket)) == NULL) {
            ret = PR_GetError();
            goto exit;
        }
        tcpSocket = NULL;
        if ((secStatus = SSL_OptionSet(sslSocket, SSL_HANDSHAKE_AS_CLIENT, PR_TRUE)) != SECSuccess) {
            ret = PR_GetError();
            goto exit;
        }
        if ((secStatus = SSL_AuthCertificateHook(sslSocket, acceptAnyCertificate, NULL)) != SECSuccess) {
            ret = PR_GetError();
            goto exit;
        }
        if ((secStatus = SSL_OptionSet(sslSocket, SSL_ENABLE_FDX, PR_TRUE)) != SECSuccess) {
            ret = PR_GetError();
            goto exit;
        }
        if ((secStatus = SSL_SetPKCS11PinArg(sslSocket, (void *) sslpwd)) != SECSuccess) {
            ret = PR_GetError();
            goto exit;
        }
        if ((secStatus = SSL_SetURL(sslSocket, u->host().c_str())) != SECSuccess) {
            ret = PR_GetError();
            goto exit;
        }
    } else {
        sslSocket = tcpSocket;
    }

    if ((prStatus = PR_GetHostByName(u->host().c_str(), buffer, sizeof (buffer), &hostEntry)) != PR_SUCCESS) {
        ret = PR_GetError();
        goto exit;
    }

    if ((rv = PR_EnumerateHostEnt(0, &hostEntry, u->port(), &addr)) < 0) {
        ret = PR_GetError();
        goto exit;
    }

    if ((prStatus = PR_Connect(sslSocket, &addr, PR_INTERVAL_NO_TIMEOUT)) != PR_SUCCESS) {
        ret = PR_GetError();
        goto exit;
    }

    if (u->protocol() == "https") {
        if ((secStatus = SSL_ResetHandshake(sslSocket, PR_FALSE)) != SECSuccess) {
            ret = PR_GetError();
            goto exit;
        }
    }

    if ((PR_Write(sslSocket, postBuffer.c_str(), postBuffer.length())) <= 0) {
        ret = PR_GetError();
        goto exit;
    }

    if ((rv = PR_Read(sslSocket, buffer, sizeof (buffer))) <= 0) {
        ret = PR_GetError();
        goto exit;
    }

    buffer[rv] = 0;
    if (((tptr = strstr(buffer, "token.id=")) != NULL &&
            sscanf(tptr, "token.id=%s", buffer_out) == 1)) {
        ret = 1;
    } else {
        ret = -1;
    }

    if (ret == 1) {
        std::string logoutPostData = sdk::utils::format("subjectid=%s", buffer_out);
        std::string logoutPostBuffer = sdk::utils::format("POST /%s/identity/logout HTTP/1.0\r\nContent-Type: %s\r\nContent-length: %d\r\nHost: %s\r\nConnection: close\r\n\r\n%s\r\n",
                dpath.c_str(),
                "application/x-www-form-urlencoded",
                logoutPostData.length(),
                u->host().c_str(),
                logoutPostData.c_str());
        PR_Write(sslSocket, logoutPostBuffer.c_str(), logoutPostBuffer.length());
    }

exit:
    PR_Shutdown(sslSocket, PR_SHUTDOWN_BOTH);
    PR_Close(sslSocket);
    if (init == 1) {
        SSL_ShutdownServerSessionIDCache();
        SSL_ClearSessionCache();
        NSS_Shutdown();
        PR_Cleanup();
    }
    return ret;
}
