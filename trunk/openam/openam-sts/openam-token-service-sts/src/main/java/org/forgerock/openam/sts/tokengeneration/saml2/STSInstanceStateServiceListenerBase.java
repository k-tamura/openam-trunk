package org.forgerock.openam.sts.tokengeneration.saml2;

import com.sun.identity.sm.ServiceListener;
import org.forgerock.openam.sts.AMSTSConstants;

/**
 * This class implements the ServiceListener interface and leverages the STSInstanceStateProvider interface to
 * invalidate cache entries in the RestSTSInstanceStateProvider when the organizational config of a rest sts instance
 * is updated.
 */
public abstract class STSInstanceStateServiceListenerBase<T extends STSInstanceState> implements ServiceListener {
    private final STSInstanceStateProvider<T> instanceStateProvider;
    private final String serviceName;
    private final String serviceVersion;

    STSInstanceStateServiceListenerBase(STSInstanceStateProvider<T> instanceStateProvider, String serviceName, String serviceVersion) {
        this.instanceStateProvider = instanceStateProvider;
        this.serviceName = serviceName;
        this.serviceVersion = serviceVersion;
    }

    public void schemaChanged(String serviceName, String version) {
        //do nothing - these updates not relevant
    }

    public void globalConfigChanged(String serviceName, String version, String groupName, String serviceComponent, int type) {
        //do nothing - these updates not relevant
    }

    /*
    Called when the OrganizationConfig is modified. In this method, the serviceName is RestSecurityTokenService (as defined
    in restSTS.xml), and the serviceComponent corresponds to the actual name of the rest-sts instance, albeit in all
    lower-case. This is the value I want to use to invalidate the cache entry.
     */
    public void organizationConfigChanged(String serviceName, String version, String orgName, String groupName, String serviceComponent, int type) {
        if (instanceTargetedByDelete(serviceName, version, type)) {
            /*
            It seems the serviceComponent is the full realm path, and always includes a '/' at the beginning, to represent
            the root realm. This value needs to be stripped-off, as the cache uses the rest-sts id, which is normalized to
            remove any leading/trailing '/' characters.
             */
            if (serviceComponent.startsWith(AMSTSConstants.FORWARD_SLASH)) {
                serviceComponent = serviceComponent.substring(1);
            }
            instanceStateProvider.invalidateCachedEntry(serviceComponent);
        }
    }

    /*
    When a rest-sts instance is 'updated', it is really a delete followed by an add. So I will simply listen for the delete,
    as that will invalidate cache entries as part of the update cycle, and have the added benefit of preventing my cache
    from growing too large in case many rest-sts instances are updated, but very few actually invoked after the update.
     */
    private boolean instanceTargetedByDelete(String serviceName, String version, int type) {
        return this.serviceName.equals(serviceName) &&
                this.serviceVersion.equals(version) &&
                (ServiceListener.REMOVED == type);
    }
}
