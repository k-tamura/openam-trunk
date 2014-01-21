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

package org.forgerock.openam.rest.router;

import org.forgerock.json.resource.CollectionResourceProvider;
import org.forgerock.json.resource.SingletonResourceProvider;
import org.forgerock.openam.rest.RestEndpointServlet;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.forgerock.openam.forgerockrest.guice.RestEndpointGuiceProvider.ServiceProviderClass;

/**
* Implementation of the Rest Endpoint Manager.
*
* @since 12.0.0
*/
@Singleton
public class RestEndpointManagerImpl implements RestEndpointManager {

    private final Map<String, CollectionResourceProvider> collectionResourceEndpoints;
    private final Map<String, SingletonResourceProvider> singletonResourceEndpoints;
    private final Map<String, ServiceProviderClass> serviceEndpoints;

    private final Map<EndpointTemplate, String> endpointTemplateMap = new HashMap<EndpointTemplate, String>();

    /**
     * Constructs an instance of the RestEndpointManagerImpl.
     *
     * @param collectionResourceEndpoints A map of the collection resource endpoints and their handlers.
     * @param singletonResourceEndpoints A map of the singleton resource endpoints and their handlers.
     * @param serviceEndpoints A map of the service endpoints and their handler classes.
     */
    @Inject
    public RestEndpointManagerImpl(final Map<String, CollectionResourceProvider> collectionResourceEndpoints,
            final Map<String, SingletonResourceProvider> singletonResourceEndpoints,
            final Map<String, ServiceProviderClass> serviceEndpoints) {
        this.collectionResourceEndpoints = collectionResourceEndpoints;
        this.singletonResourceEndpoints = singletonResourceEndpoints;
        this.serviceEndpoints = serviceEndpoints;
        createEndpointTemplates(collectionResourceEndpoints.keySet(), EndpointType.RESOURCE);
        createEndpointTemplates(singletonResourceEndpoints.keySet(), EndpointType.RESOURCE);
        createEndpointTemplates(serviceEndpoints.keySet(), EndpointType.SERVICE);
    }

    /**
     * Creates templates for each endpoint that will be used to match requests to the endpoint type so the request
     * can be handled by the correct router for the endpoint type.
     *
     * @param endpoints The endpoints.
     * @param endpointType The type of the endpoints.
     */
    private void createEndpointTemplates(final Set<String> endpoints, final EndpointType endpointType) {
        for (String endpoint : endpoints) {
            EndpointTemplate template = EndpointTemplate.createTemplate(endpoint);
            endpointTemplateMap.put(template, endpoint);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEndpoint(String token) {
        return collectionResourceEndpoints.containsKey(token) || singletonResourceEndpoints.containsKey(token)
                || serviceEndpoints.containsKey(token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EndpointType getEndpointType(String endpoint) {
        if (collectionResourceEndpoints.containsKey(endpoint) || singletonResourceEndpoints.containsKey(endpoint) ) {
            return EndpointType.RESOURCE;
        }
        if (serviceEndpoints.containsKey(endpoint)) {
            return EndpointType.SERVICE;
        }
        return null;
    }

    @Override
    public String findEndpoint(final String request) {

        EndpointMatch bestMatch = null;

        for (final EndpointTemplate template : endpointTemplateMap.keySet()) {

            final Matcher matcher = template.regex.matcher(RestEndpointServlet.normalizeResourceName(request));

            if (!matcher.matches()) {
                continue;
            }

            String matched = matcher.group(1);

            EndpointMatch match = new EndpointMatch(template, matched);
            if (match.isBetterMatchThan(bestMatch)) {
                bestMatch = match;
            }
        }

        if (bestMatch != null) {
            return endpointTemplateMap.get(bestMatch.template);
        }

        return null;
    }

    /**
     * Models an endpoint matched with a request.
     *
     * @since 12.0.0
     */
    private static class EndpointMatch {

        private final EndpointTemplate template;
        private final String match;

        /**
         * Constructs a new EndpointMatch.
         *
         * @param template The template that matched the request.
         * @param match The portion of the request that matched the template.
         */
        private EndpointMatch(final EndpointTemplate template, final String match) {
            this.template = template;
            this.match = match;
        }

        /**
         * Determines if this Endpoint Match is a better match than the given match.
         * <br/>
         * <ul>
         * <li>If the given match is <code>null</code>, this one is better.</li>
         * <li>The Endpoint Match which has matched the most of the request is the better.</li>
         * <li>The Endpoint Match which has less variables is the better.</li>
         * </ul>
         *
         * @param match The Endpoint Match to compare against.
         * @return <code>true</code> if this match is the better.
         */
        boolean isBetterMatchThan(final EndpointMatch match) {
            if (match == null) {
                return true;
            } else if (!this.match.equals(match.match)) {
                // One template matched a greater proportion of the resource
                // name than the other. Use the template which matched the most.
                return this.match.length() > match.match.length();
            } else {
                // Prefer a match with less variables.
                return this.template.variables.size() < match.template.variables.size();
            }
        }
    }

    /**
     * Models a regex template for an Endpoint.
     *
     * @since 12.0.0
     */
    private static class EndpointTemplate {

        private final Pattern regex;
        private final List<String> variables;

        /**
         * Constructs a new Endpoint Template.
         *
         * @param regex The regex for the endpoint.
         * @param variables The variable in the endpoint.
         */
        private EndpointTemplate(final Pattern regex, final List<String> variables) {
            this.regex = regex;
            this.variables = variables;
        }

        /**
         * Creates a new Endpoint Template for the given endpoint String.
         *
         * @param endpoint The endpoint string.
         * @return The template for the endpoint.
         */
        private static EndpointTemplate createTemplate(final String endpoint) {
            final String t = RestEndpointServlet.normalizeResourceName(endpoint);

            final StringBuilder builder = new StringBuilder(t.length() + 8);
            final List<String> variables = new ArrayList<String>();

            boolean isInVariable = false;
            int elementStart = 0;
            builder.append(".*(");
            for (int i = 0; i < t.length(); i++) {
                final char c = t.charAt(i);
                if (isInVariable) {
                    if (c == '}') {
                        if (elementStart == i) {
                            throw new IllegalArgumentException("URI template " + t
                                    + " contains zero-length template variable");
                        }
                        variables.add(t.substring(elementStart, i));
                        builder.append("([^/]+)");
                        isInVariable = false;
                        elementStart = i + 1;
                    } else if (!isValidVariableCharacter(c)) {
                        throw new IllegalArgumentException("URI template " + t
                                + " contains an illegal character " + c + " in a template variable");
                    } else {
                        // Continue counting characters in variable.
                    }
                } else if (c == '{') {
                    // Escape and add literal substring.
                    builder.append(Pattern.quote(t.substring(elementStart, i)));
                    isInVariable = true;
                    elementStart = i + 1;
                }
            }

            if (isInVariable) {
                throw new IllegalArgumentException("URI template " + t
                        + " contains a trailing unclosed variable");
            }

            // Escape and add remaining literal substring.
            builder.append(Pattern.quote(t.substring(elementStart)));
            builder.append(").*");

            return new EndpointTemplate(Pattern.compile(builder.toString()), variables);
        }

        // As per RFC.
        private static boolean isValidVariableCharacter(final char c) {
            return ((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z'))
                    || ((c >= '0') && (c <= '9')) || (c == '_');
        }
    }
}
