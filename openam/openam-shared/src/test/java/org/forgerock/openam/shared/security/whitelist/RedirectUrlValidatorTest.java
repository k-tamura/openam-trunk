/*
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
 *
 * Copyright 2014 ForgeRock AS.
 */
package org.forgerock.openam.shared.security.whitelist;

import java.util.Collection;
import java.util.Set;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.*;
import static org.forgerock.openam.utils.CollectionUtils.*;

public class RedirectUrlValidatorTest {

    @DataProvider(name = "wildcardInProto")
    public Object[][] getWildcardInProtoCases() {
        return new Object[][]{
            {"http://example.com:80/", true},
            {"https://example.com:443/", true},
            {"http://example.com/a", false},
            {"https://example.com/a", false}
        };
    }

    @Test(dataProvider = "wildcardInProto")
    public void testWildcardInProtocol(String url, boolean result) {
        RedirectUrlValidator<String> validator = getValidator(asSet("http*://example.com/"));
        assertThat(validator.isRedirectUrlValid(url, null)).isEqualTo(result);
    }

    @DataProvider(name = "wildcardInHost")
    public Object[][] getWildcardInHostCases() {
        return new Object[][]{
            {"http://example.com/", true},
            {"http://exbmple.com/", true},
            {"http://exmple.com/", true},
            {"http://exbcdefghijklmple.com/", true},
            {"https://example.com", false},
            {"http://example.com/a", false}
        };
    }

    @Test(dataProvider = "wildcardInHost")
    public void testWildcardInHost(String url, boolean result) {
        RedirectUrlValidator<String> validator = getValidator(asSet("http://ex*mple.com/"));
        assertThat(validator.isRedirectUrlValid(url, null)).isEqualTo(result);
    }

    @DataProvider(name = "wildcardInPort")
    public Object[][] getWildcardInPortCases() {
        return new Object[][]{
            {"http://example.com:80/", true},
            {"http://example.com:1/", true},
            {"http://example.com:443/", true},
            {"http://example.com/a", false},
            {"https://example.com:443/", false},
            {"https://example.com/a", false}
        };
    }

    @Test(dataProvider = "wildcardInPort")
    public void testWildcardInPort(String url, boolean result) {
        RedirectUrlValidator<String> validator = getValidator(asSet("http://example.com:*/"));
        assertThat(validator.isRedirectUrlValid(url, null)).isEqualTo(result);
    }

    @DataProvider(name = "wildcardInPath")
    public Object[][] getWildcardInPathCases() {
        return new Object[][]{
            {"http://example.com", true},
            {"http://example.com/hello.html", true},
            {"http://example.com/a", true},
            {"http://example.com/a/b", true},
            {"http://example.com/a/b/c", true},
            {"http://example.com/a/b/c/world.php", true},
            {"http://example.com/a/b.html/c", true},
            {"http://example.com/?", false},
            {"http://example.com/?d", false},
            {"http://example.com/?d=e", false},
            {"http://example.com/a?d=e", false},
            {"http://example.com/a/b?d=e", false},
            {"http://example.com/a/b/?d=e", false},
            {"http://example.com/a/b/?d=e;f=g", false},
            {"http://example.com/a/b/c?d", false}
        };
    }

    @Test(dataProvider = "wildcardInPath")
    public void testWildcardInPath(String url, boolean result) {
        RedirectUrlValidator<String> validator = getValidator(asSet("http://example.com/*"));
        assertThat(validator.isRedirectUrlValid(url, null)).isEqualTo(result);
    }

    @DataProvider(name = "wildcardInQuery")
    public Object[][] getWildcardInQueryCases() {
        return new Object[][]{
            {"http://example.com", false},
            {"http://example.com/hello.html", false},
            {"http://example.com/a", false},
            {"http://example.com/a/b", false},
            {"http://example.com/a/b/c", false},
            {"http://example.com/a/b/c/world.php", false},
            {"http://example.com/a/b.html/c", false},
//            {"http://example.com/?", true}, see OPENAM-1974
            {"http://example.com/?d", true},
            {"http://example.com/?d=e", true},
            {"http://example.com/a?d=e", true},
            {"http://example.com/a/b?d=e", true},
            {"http://example.com/a/b/?d=e", true},
            {"http://example.com/a/b/?d=e;f=g", true},
            {"http://example.com/a/b/c?d", true}
        };
    }

    @Test(dataProvider = "wildcardInQuery")
    public void testWildcardInQuery(String url, boolean result) {
        RedirectUrlValidator<String> validator = getValidator(asSet("http://example.com/*?*"));
        assertThat(validator.isRedirectUrlValid(url, null)).isEqualTo(result);
    }

    private RedirectUrlValidator<String> getValidator(final Set<String> domains) {
        return new RedirectUrlValidator<String>(new ValidDomainExtractor<String>() {

            public Collection<String> extractValidDomains(String configInfo) {
                return domains;
            }
        });
    }
}
