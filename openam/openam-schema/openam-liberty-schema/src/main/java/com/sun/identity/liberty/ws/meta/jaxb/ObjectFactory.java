//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:33:54 AM PDT 
//


package com.sun.identity.liberty.ws.meta.jaxb;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sun.identity.liberty.ws.meta.jaxb package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
public class ObjectFactory
    extends com.sun.identity.federation.jaxb.entityconfig.impl.runtime.DefaultJAXBContextImpl
{

    private static java.util.HashMap defaultImplementations = new java.util.HashMap(33, 0.75F);
    private static java.util.HashMap rootTagMap = new java.util.HashMap();
    public final static com.sun.identity.federation.jaxb.entityconfig.impl.runtime.GrammarInfo grammarInfo = new com.sun.identity.federation.jaxb.entityconfig.impl.runtime.GrammarInfoImpl(rootTagMap, defaultImplementations, (com.sun.identity.liberty.ws.meta.jaxb.ObjectFactory.class));
    public final static java.lang.Class version = (com.sun.identity.liberty.ws.meta.jaxb.impl.JAXBVersion.class);

    static {
        defaultImplementations.put((com.sun.identity.liberty.ws.meta.jaxb.SPDescriptorType.class), "com.sun.identity.liberty.ws.meta.jaxb.impl.SPDescriptorTypeImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.meta.jaxb.AffiliationDescriptorType.class), "com.sun.identity.liberty.ws.meta.jaxb.impl.AffiliationDescriptorTypeImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.meta.jaxb.OrganizationDisplayNameType.class), "com.sun.identity.liberty.ws.meta.jaxb.impl.OrganizationDisplayNameTypeImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.meta.jaxb.LocalizedURIType.class), "com.sun.identity.liberty.ws.meta.jaxb.impl.LocalizedURITypeImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.meta.jaxb.AdditionalMetadataLocationType.class), "com.sun.identity.liberty.ws.meta.jaxb.impl.AdditionalMetadataLocationTypeImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.meta.jaxb.EntityDescriptorElement.class), "com.sun.identity.liberty.ws.meta.jaxb.impl.EntityDescriptorElementImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.meta.jaxb.OrganizationType.class), "com.sun.identity.liberty.ws.meta.jaxb.impl.OrganizationTypeImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.meta.jaxb.EntityDescriptorType.class), "com.sun.identity.liberty.ws.meta.jaxb.impl.EntityDescriptorTypeImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.meta.jaxb.KeyDescriptorElement.class), "com.sun.identity.liberty.ws.meta.jaxb.impl.KeyDescriptorElementImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.meta.jaxb.OrganizationNameType.class), "com.sun.identity.liberty.ws.meta.jaxb.impl.OrganizationNameTypeImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.meta.jaxb.StatusType.class), "com.sun.identity.liberty.ws.meta.jaxb.impl.StatusTypeImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.meta.jaxb.EmptyType.class), "com.sun.identity.liberty.ws.meta.jaxb.impl.EmptyTypeImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.meta.jaxb.ExtensionElement.class), "com.sun.identity.liberty.ws.meta.jaxb.impl.ExtensionElementImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.meta.jaxb.KeyDescriptorType.class), "com.sun.identity.liberty.ws.meta.jaxb.impl.KeyDescriptorTypeImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.meta.jaxb.EntitiesDescriptorType.class), "com.sun.identity.liberty.ws.meta.jaxb.impl.EntitiesDescriptorTypeImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.meta.jaxb.StatusElement.class), "com.sun.identity.liberty.ws.meta.jaxb.impl.StatusElementImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.meta.jaxb.ContactType.class), "com.sun.identity.liberty.ws.meta.jaxb.impl.ContactTypeImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.meta.jaxb.IDPDescriptorType.class), "com.sun.identity.liberty.ws.meta.jaxb.impl.IDPDescriptorTypeImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.meta.jaxb.IDPDescriptorElement.class), "com.sun.identity.liberty.ws.meta.jaxb.impl.IDPDescriptorElementImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.meta.jaxb.ProviderDescriptorType.class), "com.sun.identity.liberty.ws.meta.jaxb.impl.ProviderDescriptorTypeImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.meta.jaxb.SPDescriptorElement.class), "com.sun.identity.liberty.ws.meta.jaxb.impl.SPDescriptorElementImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.meta.jaxb.ExtensionType.class), "com.sun.identity.liberty.ws.meta.jaxb.impl.ExtensionTypeImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.meta.jaxb.SPDescriptorType.AssertionConsumerServiceURLType.class), "com.sun.identity.liberty.ws.meta.jaxb.impl.SPDescriptorTypeImpl$AssertionConsumerServiceURLTypeImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.meta.jaxb.EntitiesDescriptorElement.class), "com.sun.identity.liberty.ws.meta.jaxb.impl.EntitiesDescriptorElementImpl");
        rootTagMap.put(new javax.xml.namespace.QName("urn:liberty:metadata:2003-08", "IDPDescriptor"), (com.sun.identity.liberty.ws.meta.jaxb.IDPDescriptorElement.class));
        rootTagMap.put(new javax.xml.namespace.QName("urn:liberty:metadata:2003-08", "EntityDescriptor"), (com.sun.identity.liberty.ws.meta.jaxb.EntityDescriptorElement.class));
        rootTagMap.put(new javax.xml.namespace.QName("urn:liberty:metadata:2003-08", "EntitiesDescriptor"), (com.sun.identity.liberty.ws.meta.jaxb.EntitiesDescriptorElement.class));
        rootTagMap.put(new javax.xml.namespace.QName("urn:liberty:metadata:2003-08", "Status"), (com.sun.identity.liberty.ws.meta.jaxb.StatusElement.class));
        rootTagMap.put(new javax.xml.namespace.QName("urn:liberty:metadata:2003-08", "KeyDescriptor"), (com.sun.identity.liberty.ws.meta.jaxb.KeyDescriptorElement.class));
        rootTagMap.put(new javax.xml.namespace.QName("urn:liberty:metadata:2003-08", "Extension"), (com.sun.identity.liberty.ws.meta.jaxb.ExtensionElement.class));
        rootTagMap.put(new javax.xml.namespace.QName("urn:liberty:metadata:2003-08", "SPDescriptor"), (com.sun.identity.liberty.ws.meta.jaxb.SPDescriptorElement.class));
    }

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sun.identity.liberty.ws.meta.jaxb
     * 
     */
    public ObjectFactory() {
        super(grammarInfo);
    }

    /**
     * Create an instance of the specified Java content interface.
     * 
     * @param javaContentInterface
     *     the Class object of the javacontent interface to instantiate
     * @return
     *     a new instance
     * @throws JAXBException
     *     if an error occurs
     */
    public java.lang.Object newInstance(java.lang.Class javaContentInterface)
        throws javax.xml.bind.JAXBException
    {
        return super.newInstance(javaContentInterface);
    }

    /**
     * Get the specified property. This method can only be
     * used to get provider specific properties.
     * Attempting to get an undefined property will result
     * in a PropertyException being thrown.
     * 
     * @param name
     *     the name of the property to retrieve
     * @return
     *     the value of the requested property
     * @throws PropertyException
     *     when there is an error retrieving the given property or value
     */
    public java.lang.Object getProperty(java.lang.String name)
        throws javax.xml.bind.PropertyException
    {
        return super.getProperty(name);
    }

    /**
     * Set the specified property. This method can only be
     * used to set provider specific properties.
     * Attempting to set an undefined property will result
     * in a PropertyException being thrown.
     * 
     * @param name
     *     the name of the property to retrieve
     * @param value
     *     the value of the property to be set
     * @throws PropertyException
     *     when there is an error processing the given property or value
     */
    public void setProperty(java.lang.String name, java.lang.Object value)
        throws javax.xml.bind.PropertyException
    {
        super.setProperty(name, value);
    }

    /**
     * Create an instance of SPDescriptorType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.meta.jaxb.SPDescriptorType createSPDescriptorType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.meta.jaxb.impl.SPDescriptorTypeImpl();
    }

    /**
     * Create an instance of AffiliationDescriptorType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.meta.jaxb.AffiliationDescriptorType createAffiliationDescriptorType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.meta.jaxb.impl.AffiliationDescriptorTypeImpl();
    }

    /**
     * Create an instance of OrganizationDisplayNameType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.meta.jaxb.OrganizationDisplayNameType createOrganizationDisplayNameType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.meta.jaxb.impl.OrganizationDisplayNameTypeImpl();
    }

    /**
     * Create an instance of LocalizedURIType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.meta.jaxb.LocalizedURIType createLocalizedURIType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.meta.jaxb.impl.LocalizedURITypeImpl();
    }

    /**
     * Create an instance of AdditionalMetadataLocationType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.meta.jaxb.AdditionalMetadataLocationType createAdditionalMetadataLocationType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.meta.jaxb.impl.AdditionalMetadataLocationTypeImpl();
    }

    /**
     * Create an instance of EntityDescriptorElement
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.meta.jaxb.EntityDescriptorElement createEntityDescriptorElement()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.meta.jaxb.impl.EntityDescriptorElementImpl();
    }

    /**
     * Create an instance of OrganizationType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.meta.jaxb.OrganizationType createOrganizationType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.meta.jaxb.impl.OrganizationTypeImpl();
    }

    /**
     * Create an instance of EntityDescriptorType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.meta.jaxb.EntityDescriptorType createEntityDescriptorType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.meta.jaxb.impl.EntityDescriptorTypeImpl();
    }

    /**
     * Create an instance of KeyDescriptorElement
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.meta.jaxb.KeyDescriptorElement createKeyDescriptorElement()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.meta.jaxb.impl.KeyDescriptorElementImpl();
    }

    /**
     * Create an instance of OrganizationNameType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.meta.jaxb.OrganizationNameType createOrganizationNameType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.meta.jaxb.impl.OrganizationNameTypeImpl();
    }

    /**
     * Create an instance of StatusType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.meta.jaxb.StatusType createStatusType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.meta.jaxb.impl.StatusTypeImpl();
    }

    /**
     * Create an instance of EmptyType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.meta.jaxb.EmptyType createEmptyType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.meta.jaxb.impl.EmptyTypeImpl();
    }

    /**
     * Create an instance of ExtensionElement
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.meta.jaxb.ExtensionElement createExtensionElement()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.meta.jaxb.impl.ExtensionElementImpl();
    }

    /**
     * Create an instance of KeyDescriptorType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.meta.jaxb.KeyDescriptorType createKeyDescriptorType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.meta.jaxb.impl.KeyDescriptorTypeImpl();
    }

    /**
     * Create an instance of EntitiesDescriptorType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.meta.jaxb.EntitiesDescriptorType createEntitiesDescriptorType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.meta.jaxb.impl.EntitiesDescriptorTypeImpl();
    }

    /**
     * Create an instance of StatusElement
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.meta.jaxb.StatusElement createStatusElement()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.meta.jaxb.impl.StatusElementImpl();
    }

    /**
     * Create an instance of ContactType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.meta.jaxb.ContactType createContactType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.meta.jaxb.impl.ContactTypeImpl();
    }

    /**
     * Create an instance of IDPDescriptorType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.meta.jaxb.IDPDescriptorType createIDPDescriptorType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.meta.jaxb.impl.IDPDescriptorTypeImpl();
    }

    /**
     * Create an instance of IDPDescriptorElement
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.meta.jaxb.IDPDescriptorElement createIDPDescriptorElement()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.meta.jaxb.impl.IDPDescriptorElementImpl();
    }

    /**
     * Create an instance of ProviderDescriptorType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.meta.jaxb.ProviderDescriptorType createProviderDescriptorType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.meta.jaxb.impl.ProviderDescriptorTypeImpl();
    }

    /**
     * Create an instance of SPDescriptorElement
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.meta.jaxb.SPDescriptorElement createSPDescriptorElement()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.meta.jaxb.impl.SPDescriptorElementImpl();
    }

    /**
     * Create an instance of ExtensionType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.meta.jaxb.ExtensionType createExtensionType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.meta.jaxb.impl.ExtensionTypeImpl();
    }

    /**
     * Create an instance of SPDescriptorTypeAssertionConsumerServiceURLType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.meta.jaxb.SPDescriptorType.AssertionConsumerServiceURLType createSPDescriptorTypeAssertionConsumerServiceURLType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.meta.jaxb.impl.SPDescriptorTypeImpl.AssertionConsumerServiceURLTypeImpl();
    }

    /**
     * Create an instance of EntitiesDescriptorElement
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.meta.jaxb.EntitiesDescriptorElement createEntitiesDescriptorElement()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.meta.jaxb.impl.EntitiesDescriptorElementImpl();
    }

}
