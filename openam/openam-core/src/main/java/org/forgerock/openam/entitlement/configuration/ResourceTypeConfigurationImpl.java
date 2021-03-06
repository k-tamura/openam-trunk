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
 * Copyright 2015 ForgeRock AS.
 */
package org.forgerock.openam.entitlement.configuration;

import static com.sun.identity.entitlement.EntitlementException.MODIFY_RESOURCE_TYPE_FAIL;
import static com.sun.identity.entitlement.EntitlementException.REMOVE_RESOURCE_TYPE_FAIL;
import static com.sun.identity.entitlement.EntitlementException.RESOURCE_TYPE_RETRIEVAL_ERROR;
import static com.sun.identity.entitlement.opensso.OpenSSOLogger.LogLevel.ERROR;
import static com.sun.identity.entitlement.opensso.OpenSSOLogger.LogLevel.MESSAGE;
import static com.sun.identity.entitlement.opensso.OpenSSOLogger.Message.RESOURCE_TYPE_ATTEMPT_REMOVE;
import static com.sun.identity.entitlement.opensso.OpenSSOLogger.Message.RESOURCE_TYPE_ATTEMPT_SAVE;
import static com.sun.identity.entitlement.opensso.OpenSSOLogger.Message.RESOURCE_TYPE_FAILED_REMOVE;
import static com.sun.identity.entitlement.opensso.OpenSSOLogger.Message.RESOURCE_TYPE_FAILED_SAVE;
import static com.sun.identity.entitlement.opensso.OpenSSOLogger.Message.RESOURCE_TYPE_SUCCEEDED_REMOVE;
import static com.sun.identity.entitlement.opensso.OpenSSOLogger.Message.RESOURCE_TYPE_SUCCEEDED_SAVE;
import static org.forgerock.openam.entitlement.utils.EntitlementUtils.CONFIG_ACTIONS;
import static org.forgerock.openam.entitlement.utils.EntitlementUtils.CONFIG_CREATED_BY;
import static org.forgerock.openam.entitlement.utils.EntitlementUtils.CONFIG_CREATION_DATE;
import static org.forgerock.openam.entitlement.utils.EntitlementUtils.CONFIG_DESCRIPTION;
import static org.forgerock.openam.entitlement.utils.EntitlementUtils.CONFIG_LAST_MODIFIED_BY;
import static org.forgerock.openam.entitlement.utils.EntitlementUtils.CONFIG_LAST_MODIFIED_DATE;
import static org.forgerock.openam.entitlement.utils.EntitlementUtils.CONFIG_NAME;
import static org.forgerock.openam.entitlement.utils.EntitlementUtils.CONFIG_PATTERNS;
import static org.forgerock.openam.entitlement.utils.EntitlementUtils.CONFIG_RESOURCE_TYPES;
import static org.forgerock.openam.entitlement.utils.EntitlementUtils.REALM_DN_TEMPLATE;
import static org.forgerock.openam.entitlement.utils.EntitlementUtils.RESOURCE_TYPE;
import static org.forgerock.openam.entitlement.utils.EntitlementUtils.SCHEMA_RESOURCE_TYPES;
import static org.forgerock.openam.entitlement.utils.EntitlementUtils.getActionSet;
import static org.forgerock.openam.entitlement.utils.EntitlementUtils.getActions;
import static org.forgerock.openam.entitlement.utils.EntitlementUtils.getAttribute;
import static org.forgerock.openam.entitlement.utils.EntitlementUtils.resourceTypeFromMap;

import com.iplanet.sso.SSOException;
import com.iplanet.sso.SSOToken;
import com.sun.identity.entitlement.EntitlementException;
import com.sun.identity.entitlement.PrivilegeManager;
import com.sun.identity.entitlement.opensso.OpenSSOLogger;
import com.sun.identity.entitlement.opensso.SubjectUtils;
import com.sun.identity.sm.DNMapper;
import com.sun.identity.sm.SMSDataEntry;
import com.sun.identity.sm.SMSEntry;
import com.sun.identity.sm.SMSException;
import com.sun.identity.sm.ServiceConfig;
import org.forgerock.openam.entitlement.ResourceType;
import org.forgerock.openam.ldap.LDAPUtils;
import org.forgerock.opendj.ldap.DN;
import org.forgerock.opendj.ldap.Filter;
import org.forgerock.util.query.QueryFilter;

import javax.security.auth.Subject;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;


/**
 * The implementation for <code>ResourceTypeConfiguration</code> that is responsible for the persistence
 * of the resource type entitlement configuration.
 */
public class ResourceTypeConfigurationImpl extends AbstractConfiguration implements ResourceTypeConfiguration {

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, ResourceType> getResourceTypes(Subject subject, String realm) throws EntitlementException {
        final Map<String, ResourceType> resourceTypeMap = new HashMap<String, ResourceType>();
        try {
            final ServiceConfig subOrgConfig = getOrgConfig(subject, realm).getSubConfig(CONFIG_RESOURCE_TYPES);
            if (subOrgConfig == null) {
                return resourceTypeMap;
            }
            final Set<String> uuids = subOrgConfig.getSubConfigNames();

            for (String uuid : uuids) {
                final Map<String, Set<String>> data = subOrgConfig.getSubConfig(uuid).getAttributes();
                final ResourceType resourceType = resourceTypeFromMap(realm, uuid, data);
                resourceTypeMap.put(uuid, resourceType);
            }
        } catch (SMSException ex) {
            PrivilegeManager.debug.error("ResourceTypeConfiguration.getResourceTypes", ex);
            throw new EntitlementException(RESOURCE_TYPE_RETRIEVAL_ERROR, ex, realm);
        } catch (SSOException ex) {
            PrivilegeManager.debug.error("ResourceTypeConfiguration.getResourceTypes", ex);
            throw new EntitlementException(RESOURCE_TYPE_RETRIEVAL_ERROR, ex, realm);
        }
        return resourceTypeMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsUUID(Subject subject, String realm, String uuid) throws EntitlementException {
        final ServiceConfig resourceTypeConf;
        try {
            final ServiceConfig subOrgConfig = getOrgConfig(subject, realm).getSubConfig(CONFIG_RESOURCE_TYPES);
            if (subOrgConfig == null) {
                return false;
            }
            resourceTypeConf = subOrgConfig.getSubConfig(uuid);
        } catch (SMSException ex) {
            PrivilegeManager.debug.error("ResourceTypeConfiguration.containsUUID", ex);
            throw new EntitlementException(RESOURCE_TYPE_RETRIEVAL_ERROR, ex, realm);
        } catch (SSOException ex) {
            PrivilegeManager.debug.error("ResourceTypeConfiguration.containsUUID", ex);
            throw new EntitlementException(RESOURCE_TYPE_RETRIEVAL_ERROR, ex, realm);
        }
        return resourceTypeConf != null && resourceTypeConf.exists();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsName(Subject subject, String realm, String name) throws EntitlementException {
        try {
            final ServiceConfig subOrgConfig = getOrgConfig(subject, realm).getSubConfig(CONFIG_RESOURCE_TYPES);
            if (subOrgConfig == null) {
                return false;
            }
            final Set<String> configNames = subOrgConfig.getSubConfigNames();
            for (String configName : configNames) {
                if (name.equals(getAttribute(subOrgConfig.getSubConfig(configName).getAttributes(), CONFIG_NAME))) {
                    return true;
                }
            }
        } catch (SMSException ex) {
            PrivilegeManager.debug.error("ResourceTypeConfiguration.containsName", ex);
            throw new EntitlementException(RESOURCE_TYPE_RETRIEVAL_ERROR, ex, realm);
        } catch (SSOException ex) {
            PrivilegeManager.debug.error("ResourceTypeConfiguration.containsName", ex);
            throw new EntitlementException(RESOURCE_TYPE_RETRIEVAL_ERROR, ex, realm);
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeResourceType(Subject subject, String realm, String uuid) throws EntitlementException {
        try {
            final String[] logParams = {realm, uuid};
            OpenSSOLogger.log(MESSAGE, Level.INFO, RESOURCE_TYPE_ATTEMPT_REMOVE, logParams, subject);
            getSubOrgConfig(subject, realm, CONFIG_RESOURCE_TYPES).removeSubConfig(uuid);
            OpenSSOLogger.log(MESSAGE, Level.INFO, RESOURCE_TYPE_SUCCEEDED_REMOVE, logParams, subject);
        } catch (SMSException e) {
            handleRemoveException(subject, realm, uuid, e);
        } catch (SSOException e) {
            handleRemoveException(subject, realm, uuid, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void storeResourceType(Subject subject, ResourceType resourceType) throws EntitlementException {
        final String realm = resourceType.getRealm();
        final String uuid = resourceType.getUUID();
        createResourceTypeCollectionConfig(subject, realm, uuid);

        final SSOToken token = SubjectUtils.getSSOToken(subject);
        try {
            final SMSEntry entry = new SMSEntry(token, getResourceTypeDN(realm, uuid));
            final String[] logParams = {realm, uuid};

            entry.setAttributes(getResourceTypeData(resourceType));
            OpenSSOLogger.log(MESSAGE, Level.INFO, RESOURCE_TYPE_ATTEMPT_SAVE, logParams, subject);
            entry.save();
            OpenSSOLogger.log(MESSAGE, Level.INFO, RESOURCE_TYPE_SUCCEEDED_SAVE, logParams, subject);
        } catch (SMSException ex) {
            handleSaveException(subject, realm, uuid, ex);
        } catch (SSOException ex) {
            handleSaveException(subject, realm, uuid, ex);
        }
    }

    @Override
    public Set<ResourceType> getResourceTypes(final QueryFilter<SmsAttribute> queryFilter,
                                              final Subject subject, final String realm) throws EntitlementException {

        final SSOToken token = SubjectUtils.getSSOToken(subject);
        final String dn = getResourceTypeBaseDN(realm);
        final Filter filter = queryFilter.accept(new SmsQueryFilterVisitor(), null);
        final Set<ResourceType> resourceTypes = new HashSet<ResourceType>();

        try {
            @SuppressWarnings("unchecked") // Interaction with legacy service.
            final Iterator<SMSDataEntry> iterator = (Iterator<SMSDataEntry>)SMSEntry
                    .search(token, dn, filter.toString(), 0, 0, false, false, Collections.emptySet());

            while (iterator.hasNext()) {
                final SMSDataEntry entry = iterator.next();
                final String name = entry.getAttributeValue(CONFIG_NAME);
                // Extract the resource types UUID from the LDAP DN representation.
                final String uuid = LDAPUtils.getName(DN.valueOf(entry.getDN()));

                @SuppressWarnings("unchecked") // Interaction with legacy service.
                final Set<String> actionSet = entry.getAttributeValues(CONFIG_ACTIONS);
                final Map<String, Boolean> actions = getActions(actionSet);

                @SuppressWarnings("unchecked") // Interaction with legacy service.
                final Set<String> resources = entry.getAttributeValues(CONFIG_PATTERNS);

                final String description = entry.getAttributeValue(CONFIG_DESCRIPTION);
                final String createdBy = entry.getAttributeValue(CONFIG_CREATED_BY);
                final String creationDate = entry.getAttributeValue(CONFIG_CREATION_DATE);
                final String modifiedBy = entry.getAttributeValue(CONFIG_LAST_MODIFIED_BY);
                final String modifiedDate = entry.getAttributeValue(CONFIG_LAST_MODIFIED_DATE);

                final ResourceType resourceType = ResourceType
                        .builder(name, realm)
                        .setUUID(uuid)
                        .setActions(actions)
                        .setPatterns(resources)
                        .setDescription(description)
                        .setCreatedBy(createdBy)
                        .setCreationDate(Long.parseLong(creationDate))
                        .setLastModifiedBy(modifiedBy)
                        .setLastModifiedDate(Long.parseLong(modifiedDate))
                        .build();

                resourceTypes.add(resourceType);
            }
        } catch (SMSException smsE) {
            throw new EntitlementException(RESOURCE_TYPE_RETRIEVAL_ERROR, realm, smsE);
        }

        return resourceTypes;
    }

    /**
     * Create the config instance in the data store where new resource types can be added to.
     * @param realm The realm in which to create the config instance.
     * @param uuid The unique identifier for the resource type.
     * @throws EntitlementException
     */
    private void createResourceTypeCollectionConfig(Subject subject, String realm, String uuid)
            throws EntitlementException {

        try {
            final ServiceConfig orgConfig = getOrgConfig(subject, realm);
            if (orgConfig.getSubConfig(CONFIG_RESOURCE_TYPES) == null) {
                orgConfig.addSubConfig(CONFIG_RESOURCE_TYPES, SCHEMA_RESOURCE_TYPES, 0, Collections.EMPTY_MAP);
            }
        } catch (SMSException ex) {
            handleSaveException(subject, realm, uuid, ex);
        } catch (SSOException ex) {
            handleSaveException(subject, realm, uuid, ex);
        }
    }

    /**
     * Get the DN for the resource type.
     * @param realm The realm in which the resource type will be stored.
     * @param uuid The unique identifier for the resource type.
     * @return the resource type DN.
     */
    private String getResourceTypeDN(String realm, String uuid) {
        return "ou=" + uuid + "," + getResourceTypeBaseDN(realm);
    }

    /**
     * Get the base DN for the resource type.
     * @param realm The realm in which the resource type will be stored.
     * @return the resource type base DN.
     */
    private String getResourceTypeBaseDN(String realm) {
        return MessageFormat.format(REALM_DN_TEMPLATE, CONFIG_RESOURCE_TYPES, DNMapper.orgNameToDN(realm));
    }

    /**
     * Add all the resource type attributes to a map that can be stored in the data store.
     * @param resourceType The resource type to add to the map.
     * @return The map containing the resource type attributes.
     */
    private Map<String, Set<String>> getResourceTypeData(ResourceType resourceType) {
        final Map<String, Set<String>> map = new HashMap<String, Set<String>>();
        prepareAttributeMap(map, RESOURCE_TYPE);

        Set<String> data = new HashSet<String>();
        map.put(SMSEntry.ATTR_KEYVAL, data);

        data.add(CONFIG_NAME + "=" + resourceType.getName());

        if (resourceType.getDescription() != null) {
            data.add(CONFIG_DESCRIPTION + "=" + resourceType.getDescription());
        } else {
            data.add(CONFIG_DESCRIPTION + "=");
        }

        for (String pattern : resourceType.getPatterns()) {
            data.add(CONFIG_PATTERNS + "=" + pattern);
        }

        for (String actionPair : getActionSet(resourceType.getActions())) {
            data.add(CONFIG_ACTIONS + "=" + actionPair);
        }

        if (resourceType.getCreatedBy() != null) {
            data.add(CONFIG_CREATED_BY + "=" + resourceType.getCreatedBy());
        } else {
            data.add(CONFIG_CREATED_BY + "=");
        }

        data.add(CONFIG_CREATION_DATE + "=" + resourceType.getCreationDate());

        if (resourceType.getLastModifiedBy() != null) {
            data.add(CONFIG_LAST_MODIFIED_BY + "=" + resourceType.getLastModifiedBy());
        } else {
            data.add(CONFIG_LAST_MODIFIED_BY + "=");
        }

        data.add(CONFIG_LAST_MODIFIED_DATE + "=" + resourceType.getLastModifiedDate());

        return map;
    }

    /**
     * Log the exception and throw the appropriate <code>EntitlementException</code>.
     * @param subject The subject for which the problem occurred.
     * @param realm The realm in which the entry was to be saved.
     * @param uuid The unique identifier for the resource type.
     *@param e The exception to be handled.  @throws EntitlementException will always be thrown.
     */
    private void handleSaveException(Subject subject, String realm, String uuid, Exception e)
            throws EntitlementException {

        OpenSSOLogger.log(ERROR, Level.INFO, RESOURCE_TYPE_FAILED_SAVE, new String[]{realm, uuid, e.getMessage()},
                subject);
        throw new EntitlementException(MODIFY_RESOURCE_TYPE_FAIL, e, uuid);
    }

    /**
     * Log the exception and throw the appropriate <code>EntitlementException</code>.
     * @param subject The subject for which the problem occurred.
     * @param realm The realm from which the entry was to be removed.
     * @param uuid The unique identifier for the resource type.
     *@param e The exception to be handled.  @throws EntitlementException will always be thrown.
     */
    private void handleRemoveException(Subject subject, String realm, String uuid, Exception e)
            throws EntitlementException {

        OpenSSOLogger.log(ERROR, Level.INFO, RESOURCE_TYPE_FAILED_REMOVE, new String[]{realm, uuid, e.getMessage()},
                subject);
        throw new EntitlementException(REMOVE_RESOURCE_TYPE_FAIL, e, uuid);
    }
}
