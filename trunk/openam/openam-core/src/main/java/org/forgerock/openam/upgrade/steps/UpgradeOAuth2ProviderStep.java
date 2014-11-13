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
package org.forgerock.openam.upgrade.steps;

import com.iplanet.sso.SSOToken;
import com.sun.identity.sm.SMSUtils;
import com.sun.identity.sm.ServiceConfig;
import com.sun.identity.sm.ServiceConfigManager;
import com.sun.identity.sm.ServiceSchema;
import com.sun.identity.sm.ServiceSchemaManager;
import org.forgerock.openam.sm.datalayer.api.DataLayerConstants;
import org.forgerock.openam.upgrade.UpgradeException;
import org.forgerock.openam.upgrade.UpgradeProgress;
import org.forgerock.openam.upgrade.UpgradeStepInfo;
import org.forgerock.opendj.ldap.ConnectionFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.forgerock.openam.upgrade.UpgradeServices.LF;
import static org.forgerock.openam.upgrade.UpgradeServices.tagSwapReport;

/**
 * This upgrade step will find all the OAuth2 Providers that was created with a subset of the available attributes.
 * It will then add the missing attributes with their default values and persist the provider's attributes.
 * This needs to be done before changes can be made to the OAuth2 Provider Schema to ensure that the existing
 * providers does not automatically inherit the changes made to the schema.
 *
 * @since 12.0.0
 */
@UpgradeStepInfo(dependsOn = "org.forgerock.openam.upgrade.steps.UpgradeServiceSchemaStep")
public class UpgradeOAuth2ProviderStep extends AbstractUpgradeStep {

    private static final String REPORT_DATA = "%REPORT_DATA%";
    private static final String OAUTH2_PROVIDER = "OAuth2Provider";

    private final Map<String, Map<String, Set<String>>> attributesToUpdate =
            new HashMap<String, Map<String, Set<String>>>();
    private ServiceConfigManager scm;
    private ServiceSchemaManager ssm;

    @Inject
    public UpgradeOAuth2ProviderStep(final PrivilegedAction<SSOToken> adminTokenAction, @Named(DataLayerConstants
            .DATA_LAYER_BINDING) final ConnectionFactory factory) {
        super(adminTokenAction, factory);
    }

    @Override
    public boolean isApplicable() {
        return !attributesToUpdate.isEmpty();
    }

    @Override
    public void initialize() throws UpgradeException {
        final SSOToken token = getAdminToken();
        try {
            scm = new ServiceConfigManager(OAUTH2_PROVIDER, token);
            ssm = new ServiceSchemaManager(OAUTH2_PROVIDER, token);
        } catch (Exception e) {
            DEBUG.error("An error occurred while trying to create Service Config and Schema Managers.", e);
            throw new UpgradeException("Unable to create Service Config and Schema Managers.", e);
        }

        findProvidersThatRelyOnDefaults();
    }

    private void findProvidersThatRelyOnDefaults() throws UpgradeException {
        try {
            final ServiceSchema serviceSchema = ssm.getOrganizationSchema();
            for (String realm : getRealmNames()) {
                final ServiceConfig serviceConfig = scm.getOrganizationConfig(realm, null);
                final Map<String, Set<String>> withDefaults = serviceConfig.getAttributesForRead();
                final Map<String, Set<String>> withoutDefaults = serviceConfig.getAttributesWithoutDefaultsForRead();
                final Map<String, Set<String>> withoutValidators = SMSUtils.removeValidators(withDefaults,
                        serviceSchema);

                if (!withoutDefaults.isEmpty() && withoutDefaults.size() < withoutValidators.size()) {
                    attributesToUpdate.put(realm, withoutValidators);
                }
            }
        } catch (Exception e) {
            DEBUG.error("An error occurred while trying to look for upgradable OAuth2 Providers.", e);
            throw new UpgradeException("Unable to retrieve OAuth2 Providers.", e);
        }
    }

    @Override
    public void perform() throws UpgradeException {
        persistDefaultsForProviders();
    }

    private void persistDefaultsForProviders() throws UpgradeException {
        try {
            for (Map.Entry<String, Map<String, Set<String>>> entry : attributesToUpdate.entrySet()) {
                final String realm = entry.getKey();
                UpgradeProgress.reportStart("upgrade.oauth2.provider.start", realm);
                final ServiceConfig serviceConfig = scm.getOrganizationConfig(realm, null);
                serviceConfig.setAttributes(entry.getValue());
                UpgradeProgress.reportEnd("upgrade.success");
            }
        } catch (Exception e) {
            UpgradeProgress.reportEnd("upgrade.failed");
            DEBUG.error("An error occurred while trying to upgrade an OAuth2 Provider", e);
            throw new UpgradeException("Unable to upgrade OAuth2 Providers.", e);
        }

    }

    @Override
    public String getShortReport(String delimiter) {
        return attributesToUpdate.size() == 0 ? "" : BUNDLE.getString("upgrade.oauth2.provider.persisted.short") +
                " (" + attributesToUpdate.size() + ')' + delimiter;
    }

    @Override
    public String getDetailedReport(String delimiter) {
        final Map<String, String> tags = new HashMap<String, String>();
        tags.put(LF, delimiter);
        StringBuilder sb = new StringBuilder();
        sb.append(BUNDLE.getString("upgrade.oauth2.provider.persisted.detail")).append(": ").append(delimiter);
        for (Map.Entry<String, Map<String, Set<String>>> entry : attributesToUpdate.entrySet()) {
            sb.append(INDENT).append(entry.getKey()).append(delimiter);
        }
        tags.put(REPORT_DATA, sb.toString());
        return tagSwapReport(tags, "upgrade.oauth2.provider.report");
    }
}
