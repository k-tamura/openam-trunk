//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:33:54 AM PDT 
//


package com.sun.identity.liberty.ws.common.jaxb.protocol;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sun.identity.liberty.ws.common.jaxb.protocol package. 
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

    private static java.util.HashMap defaultImplementations = new java.util.HashMap(35, 0.75F);
    private static java.util.HashMap rootTagMap = new java.util.HashMap();
    public final static com.sun.identity.federation.jaxb.entityconfig.impl.runtime.GrammarInfo grammarInfo = new com.sun.identity.federation.jaxb.entityconfig.impl.runtime.GrammarInfoImpl(rootTagMap, defaultImplementations, (com.sun.identity.liberty.ws.common.jaxb.protocol.ObjectFactory.class));
    public final static java.lang.Class version = (com.sun.identity.liberty.ws.common.jaxb.protocol.impl.JAXBVersion.class);

    static {
        defaultImplementations.put((com.sun.identity.liberty.ws.common.jaxb.protocol.StatusCodeElement.class), "com.sun.identity.liberty.ws.common.jaxb.protocol.impl.StatusCodeElementImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.common.jaxb.protocol.SubjectQueryAbstractType.class), "com.sun.identity.liberty.ws.common.jaxb.protocol.impl.SubjectQueryAbstractTypeImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.common.jaxb.protocol.AssertionArtifactElement.class), "com.sun.identity.liberty.ws.common.jaxb.protocol.impl.AssertionArtifactElementImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.common.jaxb.protocol.RequestElement.class), "com.sun.identity.liberty.ws.common.jaxb.protocol.impl.RequestElementImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.common.jaxb.protocol.QueryAbstractType.class), "com.sun.identity.liberty.ws.common.jaxb.protocol.impl.QueryAbstractTypeImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.common.jaxb.protocol.ResponseType.class), "com.sun.identity.liberty.ws.common.jaxb.protocol.impl.ResponseTypeImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.common.jaxb.protocol.RequestType.class), "com.sun.identity.liberty.ws.common.jaxb.protocol.impl.RequestTypeImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.common.jaxb.protocol.StatusDetailType.class), "com.sun.identity.liberty.ws.common.jaxb.protocol.impl.StatusDetailTypeImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.common.jaxb.protocol.AttributeQueryElement.class), "com.sun.identity.liberty.ws.common.jaxb.protocol.impl.AttributeQueryElementImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.common.jaxb.protocol.AuthorizationDecisionQueryElement.class), "com.sun.identity.liberty.ws.common.jaxb.protocol.impl.AuthorizationDecisionQueryElementImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.common.jaxb.protocol.AuthenticationQueryType.class), "com.sun.identity.liberty.ws.common.jaxb.protocol.impl.AuthenticationQueryTypeImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.common.jaxb.protocol.StatusElement.class), "com.sun.identity.liberty.ws.common.jaxb.protocol.impl.StatusElementImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.common.jaxb.protocol.ResponseAbstractType.class), "com.sun.identity.liberty.ws.common.jaxb.protocol.impl.ResponseAbstractTypeImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.common.jaxb.protocol.AttributeQueryType.class), "com.sun.identity.liberty.ws.common.jaxb.protocol.impl.AttributeQueryTypeImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.common.jaxb.protocol.ResponseElement.class), "com.sun.identity.liberty.ws.common.jaxb.protocol.impl.ResponseElementImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.common.jaxb.protocol.StatusMessageElement.class), "com.sun.identity.liberty.ws.common.jaxb.protocol.impl.StatusMessageElementImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.common.jaxb.protocol.RespondWithElement.class), "com.sun.identity.liberty.ws.common.jaxb.protocol.impl.RespondWithElementImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.common.jaxb.protocol.AuthorizationDecisionQueryType.class), "com.sun.identity.liberty.ws.common.jaxb.protocol.impl.AuthorizationDecisionQueryTypeImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.common.jaxb.protocol.StatusDetailElement.class), "com.sun.identity.liberty.ws.common.jaxb.protocol.impl.StatusDetailElementImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.common.jaxb.protocol.RequestAbstractType.class), "com.sun.identity.liberty.ws.common.jaxb.protocol.impl.RequestAbstractTypeImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.common.jaxb.protocol.StatusCodeType.class), "com.sun.identity.liberty.ws.common.jaxb.protocol.impl.StatusCodeTypeImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.common.jaxb.protocol.StatusType.class), "com.sun.identity.liberty.ws.common.jaxb.protocol.impl.StatusTypeImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.common.jaxb.protocol.QueryElement.class), "com.sun.identity.liberty.ws.common.jaxb.protocol.impl.QueryElementImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.common.jaxb.protocol.AuthenticationQueryElement.class), "com.sun.identity.liberty.ws.common.jaxb.protocol.impl.AuthenticationQueryElementImpl");
        defaultImplementations.put((com.sun.identity.liberty.ws.common.jaxb.protocol.SubjectQueryElement.class), "com.sun.identity.liberty.ws.common.jaxb.protocol.impl.SubjectQueryElementImpl");
        rootTagMap.put(new javax.xml.namespace.QName("urn:oasis:names:tc:SAML:1.0:protocol", "Status"), (com.sun.identity.liberty.ws.common.jaxb.protocol.StatusElement.class));
        rootTagMap.put(new javax.xml.namespace.QName("urn:oasis:names:tc:SAML:1.0:protocol", "AssertionArtifact"), (com.sun.identity.liberty.ws.common.jaxb.protocol.AssertionArtifactElement.class));
        rootTagMap.put(new javax.xml.namespace.QName("urn:oasis:names:tc:SAML:1.0:protocol", "SubjectQuery"), (com.sun.identity.liberty.ws.common.jaxb.protocol.SubjectQueryElement.class));
        rootTagMap.put(new javax.xml.namespace.QName("urn:oasis:names:tc:SAML:1.0:protocol", "StatusMessage"), (com.sun.identity.liberty.ws.common.jaxb.protocol.StatusMessageElement.class));
        rootTagMap.put(new javax.xml.namespace.QName("urn:oasis:names:tc:SAML:1.0:protocol", "StatusDetail"), (com.sun.identity.liberty.ws.common.jaxb.protocol.StatusDetailElement.class));
        rootTagMap.put(new javax.xml.namespace.QName("urn:oasis:names:tc:SAML:1.0:protocol", "AttributeQuery"), (com.sun.identity.liberty.ws.common.jaxb.protocol.AttributeQueryElement.class));
        rootTagMap.put(new javax.xml.namespace.QName("urn:oasis:names:tc:SAML:1.0:protocol", "AuthorizationDecisionQuery"), (com.sun.identity.liberty.ws.common.jaxb.protocol.AuthorizationDecisionQueryElement.class));
        rootTagMap.put(new javax.xml.namespace.QName("urn:oasis:names:tc:SAML:1.0:protocol", "StatusCode"), (com.sun.identity.liberty.ws.common.jaxb.protocol.StatusCodeElement.class));
        rootTagMap.put(new javax.xml.namespace.QName("urn:oasis:names:tc:SAML:1.0:protocol", "Query"), (com.sun.identity.liberty.ws.common.jaxb.protocol.QueryElement.class));
        rootTagMap.put(new javax.xml.namespace.QName("urn:oasis:names:tc:SAML:1.0:protocol", "Response"), (com.sun.identity.liberty.ws.common.jaxb.protocol.ResponseElement.class));
        rootTagMap.put(new javax.xml.namespace.QName("urn:oasis:names:tc:SAML:1.0:protocol", "Request"), (com.sun.identity.liberty.ws.common.jaxb.protocol.RequestElement.class));
        rootTagMap.put(new javax.xml.namespace.QName("urn:oasis:names:tc:SAML:1.0:protocol", "RespondWith"), (com.sun.identity.liberty.ws.common.jaxb.protocol.RespondWithElement.class));
        rootTagMap.put(new javax.xml.namespace.QName("urn:oasis:names:tc:SAML:1.0:protocol", "AuthenticationQuery"), (com.sun.identity.liberty.ws.common.jaxb.protocol.AuthenticationQueryElement.class));
    }

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sun.identity.liberty.ws.common.jaxb.protocol
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
     * Create an instance of StatusCodeElement
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.common.jaxb.protocol.StatusCodeElement createStatusCodeElement()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.common.jaxb.protocol.impl.StatusCodeElementImpl();
    }

    /**
     * Create an instance of SubjectQueryAbstractType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.common.jaxb.protocol.SubjectQueryAbstractType createSubjectQueryAbstractType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.common.jaxb.protocol.impl.SubjectQueryAbstractTypeImpl();
    }

    /**
     * Create an instance of AssertionArtifactElement
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.common.jaxb.protocol.AssertionArtifactElement createAssertionArtifactElement()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.common.jaxb.protocol.impl.AssertionArtifactElementImpl();
    }

    /**
     * Create an instance of AssertionArtifactElement
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.common.jaxb.protocol.AssertionArtifactElement createAssertionArtifactElement(java.lang.String value)
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.common.jaxb.protocol.impl.AssertionArtifactElementImpl(value);
    }

    /**
     * Create an instance of RequestElement
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.common.jaxb.protocol.RequestElement createRequestElement()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.common.jaxb.protocol.impl.RequestElementImpl();
    }

    /**
     * Create an instance of QueryAbstractType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.common.jaxb.protocol.QueryAbstractType createQueryAbstractType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.common.jaxb.protocol.impl.QueryAbstractTypeImpl();
    }

    /**
     * Create an instance of ResponseType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.common.jaxb.protocol.ResponseType createResponseType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.common.jaxb.protocol.impl.ResponseTypeImpl();
    }

    /**
     * Create an instance of RequestType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.common.jaxb.protocol.RequestType createRequestType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.common.jaxb.protocol.impl.RequestTypeImpl();
    }

    /**
     * Create an instance of StatusDetailType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.common.jaxb.protocol.StatusDetailType createStatusDetailType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.common.jaxb.protocol.impl.StatusDetailTypeImpl();
    }

    /**
     * Create an instance of AttributeQueryElement
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.common.jaxb.protocol.AttributeQueryElement createAttributeQueryElement()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.common.jaxb.protocol.impl.AttributeQueryElementImpl();
    }

    /**
     * Create an instance of AuthorizationDecisionQueryElement
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.common.jaxb.protocol.AuthorizationDecisionQueryElement createAuthorizationDecisionQueryElement()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.common.jaxb.protocol.impl.AuthorizationDecisionQueryElementImpl();
    }

    /**
     * Create an instance of AuthenticationQueryType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.common.jaxb.protocol.AuthenticationQueryType createAuthenticationQueryType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.common.jaxb.protocol.impl.AuthenticationQueryTypeImpl();
    }

    /**
     * Create an instance of StatusElement
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.common.jaxb.protocol.StatusElement createStatusElement()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.common.jaxb.protocol.impl.StatusElementImpl();
    }

    /**
     * Create an instance of ResponseAbstractType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.common.jaxb.protocol.ResponseAbstractType createResponseAbstractType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.common.jaxb.protocol.impl.ResponseAbstractTypeImpl();
    }

    /**
     * Create an instance of AttributeQueryType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.common.jaxb.protocol.AttributeQueryType createAttributeQueryType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.common.jaxb.protocol.impl.AttributeQueryTypeImpl();
    }

    /**
     * Create an instance of ResponseElement
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.common.jaxb.protocol.ResponseElement createResponseElement()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.common.jaxb.protocol.impl.ResponseElementImpl();
    }

    /**
     * Create an instance of StatusMessageElement
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.common.jaxb.protocol.StatusMessageElement createStatusMessageElement()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.common.jaxb.protocol.impl.StatusMessageElementImpl();
    }

    /**
     * Create an instance of StatusMessageElement
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.common.jaxb.protocol.StatusMessageElement createStatusMessageElement(java.lang.String value)
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.common.jaxb.protocol.impl.StatusMessageElementImpl(value);
    }

    /**
     * Create an instance of RespondWithElement
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.common.jaxb.protocol.RespondWithElement createRespondWithElement()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.common.jaxb.protocol.impl.RespondWithElementImpl();
    }

    /**
     * Create an instance of RespondWithElement
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.common.jaxb.protocol.RespondWithElement createRespondWithElement(javax.xml.namespace.QName value)
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.common.jaxb.protocol.impl.RespondWithElementImpl(value);
    }

    /**
     * Create an instance of AuthorizationDecisionQueryType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.common.jaxb.protocol.AuthorizationDecisionQueryType createAuthorizationDecisionQueryType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.common.jaxb.protocol.impl.AuthorizationDecisionQueryTypeImpl();
    }

    /**
     * Create an instance of StatusDetailElement
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.common.jaxb.protocol.StatusDetailElement createStatusDetailElement()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.common.jaxb.protocol.impl.StatusDetailElementImpl();
    }

    /**
     * Create an instance of RequestAbstractType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.common.jaxb.protocol.RequestAbstractType createRequestAbstractType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.common.jaxb.protocol.impl.RequestAbstractTypeImpl();
    }

    /**
     * Create an instance of StatusCodeType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.common.jaxb.protocol.StatusCodeType createStatusCodeType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.common.jaxb.protocol.impl.StatusCodeTypeImpl();
    }

    /**
     * Create an instance of StatusType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.common.jaxb.protocol.StatusType createStatusType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.common.jaxb.protocol.impl.StatusTypeImpl();
    }

    /**
     * Create an instance of QueryElement
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.common.jaxb.protocol.QueryElement createQueryElement()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.common.jaxb.protocol.impl.QueryElementImpl();
    }

    /**
     * Create an instance of AuthenticationQueryElement
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.common.jaxb.protocol.AuthenticationQueryElement createAuthenticationQueryElement()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.common.jaxb.protocol.impl.AuthenticationQueryElementImpl();
    }

    /**
     * Create an instance of SubjectQueryElement
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.liberty.ws.common.jaxb.protocol.SubjectQueryElement createSubjectQueryElement()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.liberty.ws.common.jaxb.protocol.impl.SubjectQueryElementImpl();
    }

}
