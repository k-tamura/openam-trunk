//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:34:07 AM PDT 
//


package com.sun.identity.saml2.jaxb.xmlenc;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sun.identity.saml2.jaxb.xmlenc package. 
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
    extends com.sun.identity.saml2.jaxb.assertion.impl.runtime.DefaultJAXBContextImpl
{

    private static java.util.HashMap defaultImplementations = new java.util.HashMap(37, 0.75F);
    private static java.util.HashMap rootTagMap = new java.util.HashMap();
    public final static com.sun.identity.saml2.jaxb.assertion.impl.runtime.GrammarInfo grammarInfo = new com.sun.identity.saml2.jaxb.assertion.impl.runtime.GrammarInfoImpl(rootTagMap, defaultImplementations, (com.sun.identity.saml2.jaxb.xmlenc.ObjectFactory.class));
    public final static java.lang.Class version = (com.sun.identity.saml2.jaxb.xmlenc.impl.JAXBVersion.class);

    static {
        defaultImplementations.put((com.sun.identity.saml2.jaxb.xmlenc.ReferenceListType.KeyReference.class), "com.sun.identity.saml2.jaxb.xmlenc.impl.ReferenceListTypeImpl$KeyReferenceImpl");
        defaultImplementations.put((com.sun.identity.saml2.jaxb.xmlenc.CipherReferenceType.class), "com.sun.identity.saml2.jaxb.xmlenc.impl.CipherReferenceTypeImpl");
        defaultImplementations.put((com.sun.identity.saml2.jaxb.xmlenc.CipherDataType.class), "com.sun.identity.saml2.jaxb.xmlenc.impl.CipherDataTypeImpl");
        defaultImplementations.put((com.sun.identity.saml2.jaxb.xmlenc.ReferenceListType.DataReference.class), "com.sun.identity.saml2.jaxb.xmlenc.impl.ReferenceListTypeImpl$DataReferenceImpl");
        defaultImplementations.put((com.sun.identity.saml2.jaxb.xmlenc.AgreementMethodType.KANonce.class), "com.sun.identity.saml2.jaxb.xmlenc.impl.AgreementMethodTypeImpl$KANonceImpl");
        defaultImplementations.put((com.sun.identity.saml2.jaxb.xmlenc.CipherDataElement.class), "com.sun.identity.saml2.jaxb.xmlenc.impl.CipherDataElementImpl");
        defaultImplementations.put((com.sun.identity.saml2.jaxb.xmlenc.EncryptedType.class), "com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedTypeImpl");
        defaultImplementations.put((com.sun.identity.saml2.jaxb.xmlenc.EncryptionMethodType.KeySize.class), "com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptionMethodTypeImpl$KeySizeImpl");
        defaultImplementations.put((com.sun.identity.saml2.jaxb.xmlenc.ReferenceListType.class), "com.sun.identity.saml2.jaxb.xmlenc.impl.ReferenceListTypeImpl");
        defaultImplementations.put((com.sun.identity.saml2.jaxb.xmlenc.ReferenceType.class), "com.sun.identity.saml2.jaxb.xmlenc.impl.ReferenceTypeImpl");
        defaultImplementations.put((com.sun.identity.saml2.jaxb.xmlenc.EncryptionPropertiesElement.class), "com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptionPropertiesElementImpl");
        defaultImplementations.put((com.sun.identity.saml2.jaxb.xmlenc.EncryptionMethodType.class), "com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptionMethodTypeImpl");
        defaultImplementations.put((com.sun.identity.saml2.jaxb.xmlenc.EncryptedKeyElement.class), "com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedKeyElementImpl");
        defaultImplementations.put((com.sun.identity.saml2.jaxb.xmlenc.CipherReferenceElement.class), "com.sun.identity.saml2.jaxb.xmlenc.impl.CipherReferenceElementImpl");
        defaultImplementations.put((com.sun.identity.saml2.jaxb.xmlenc.EncryptedKeyType.class), "com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedKeyTypeImpl");
        defaultImplementations.put((com.sun.identity.saml2.jaxb.xmlenc.TransformsType.class), "com.sun.identity.saml2.jaxb.xmlenc.impl.TransformsTypeImpl");
        defaultImplementations.put((com.sun.identity.saml2.jaxb.xmlenc.EncryptedDataType.class), "com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedDataTypeImpl");
        defaultImplementations.put((com.sun.identity.saml2.jaxb.xmlenc.ReferenceListElement.class), "com.sun.identity.saml2.jaxb.xmlenc.impl.ReferenceListElementImpl");
        defaultImplementations.put((com.sun.identity.saml2.jaxb.xmlenc.EncryptionMethodType.OAEPparams.class), "com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptionMethodTypeImpl$OAEPparamsImpl");
        defaultImplementations.put((com.sun.identity.saml2.jaxb.xmlenc.EncryptionPropertyType.class), "com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptionPropertyTypeImpl");
        defaultImplementations.put((com.sun.identity.saml2.jaxb.xmlenc.EncryptedDataElement.class), "com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedDataElementImpl");
        defaultImplementations.put((com.sun.identity.saml2.jaxb.xmlenc.EncryptionPropertiesType.class), "com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptionPropertiesTypeImpl");
        defaultImplementations.put((com.sun.identity.saml2.jaxb.xmlenc.EncryptionPropertyElement.class), "com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptionPropertyElementImpl");
        defaultImplementations.put((com.sun.identity.saml2.jaxb.xmlenc.AgreementMethodElement.class), "com.sun.identity.saml2.jaxb.xmlenc.impl.AgreementMethodElementImpl");
        defaultImplementations.put((com.sun.identity.saml2.jaxb.xmlenc.AgreementMethodType.RecipientKeyInfo.class), "com.sun.identity.saml2.jaxb.xmlenc.impl.AgreementMethodTypeImpl$RecipientKeyInfoImpl");
        defaultImplementations.put((com.sun.identity.saml2.jaxb.xmlenc.AgreementMethodType.OriginatorKeyInfo.class), "com.sun.identity.saml2.jaxb.xmlenc.impl.AgreementMethodTypeImpl$OriginatorKeyInfoImpl");
        defaultImplementations.put((com.sun.identity.saml2.jaxb.xmlenc.AgreementMethodType.class), "com.sun.identity.saml2.jaxb.xmlenc.impl.AgreementMethodTypeImpl");
        rootTagMap.put(new javax.xml.namespace.QName("http://www.w3.org/2001/04/xmlenc#", "EncryptionProperties"), (com.sun.identity.saml2.jaxb.xmlenc.EncryptionPropertiesElement.class));
        rootTagMap.put(new javax.xml.namespace.QName("http://www.w3.org/2001/04/xmlenc#", "EncryptedData"), (com.sun.identity.saml2.jaxb.xmlenc.EncryptedDataElement.class));
        rootTagMap.put(new javax.xml.namespace.QName("http://www.w3.org/2001/04/xmlenc#", "EncryptionProperty"), (com.sun.identity.saml2.jaxb.xmlenc.EncryptionPropertyElement.class));
        rootTagMap.put(new javax.xml.namespace.QName("http://www.w3.org/2001/04/xmlenc#", "EncryptedKey"), (com.sun.identity.saml2.jaxb.xmlenc.EncryptedKeyElement.class));
        rootTagMap.put(new javax.xml.namespace.QName("http://www.w3.org/2001/04/xmlenc#", "CipherData"), (com.sun.identity.saml2.jaxb.xmlenc.CipherDataElement.class));
        rootTagMap.put(new javax.xml.namespace.QName("http://www.w3.org/2001/04/xmlenc#", "CipherReference"), (com.sun.identity.saml2.jaxb.xmlenc.CipherReferenceElement.class));
        rootTagMap.put(new javax.xml.namespace.QName("http://www.w3.org/2001/04/xmlenc#", "AgreementMethod"), (com.sun.identity.saml2.jaxb.xmlenc.AgreementMethodElement.class));
        rootTagMap.put(new javax.xml.namespace.QName("http://www.w3.org/2001/04/xmlenc#", "ReferenceList"), (com.sun.identity.saml2.jaxb.xmlenc.ReferenceListElement.class));
    }

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sun.identity.saml2.jaxb.xmlenc
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
     * Create an instance of ReferenceListTypeKeyReference
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.saml2.jaxb.xmlenc.ReferenceListType.KeyReference createReferenceListTypeKeyReference()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.saml2.jaxb.xmlenc.impl.ReferenceListTypeImpl.KeyReferenceImpl();
    }

    /**
     * Create an instance of CipherReferenceType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.saml2.jaxb.xmlenc.CipherReferenceType createCipherReferenceType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.saml2.jaxb.xmlenc.impl.CipherReferenceTypeImpl();
    }

    /**
     * Create an instance of CipherDataType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.saml2.jaxb.xmlenc.CipherDataType createCipherDataType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.saml2.jaxb.xmlenc.impl.CipherDataTypeImpl();
    }

    /**
     * Create an instance of ReferenceListTypeDataReference
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.saml2.jaxb.xmlenc.ReferenceListType.DataReference createReferenceListTypeDataReference()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.saml2.jaxb.xmlenc.impl.ReferenceListTypeImpl.DataReferenceImpl();
    }

    /**
     * Create an instance of AgreementMethodTypeKANonce
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.saml2.jaxb.xmlenc.AgreementMethodType.KANonce createAgreementMethodTypeKANonce()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.saml2.jaxb.xmlenc.impl.AgreementMethodTypeImpl.KANonceImpl();
    }

    /**
     * Create an instance of AgreementMethodTypeKANonce
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.saml2.jaxb.xmlenc.AgreementMethodType.KANonce createAgreementMethodTypeKANonce(byte[] value)
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.saml2.jaxb.xmlenc.impl.AgreementMethodTypeImpl.KANonceImpl(value);
    }

    /**
     * Create an instance of CipherDataElement
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.saml2.jaxb.xmlenc.CipherDataElement createCipherDataElement()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.saml2.jaxb.xmlenc.impl.CipherDataElementImpl();
    }

    /**
     * Create an instance of EncryptedType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.saml2.jaxb.xmlenc.EncryptedType createEncryptedType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedTypeImpl();
    }

    /**
     * Create an instance of EncryptionMethodTypeKeySize
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.saml2.jaxb.xmlenc.EncryptionMethodType.KeySize createEncryptionMethodTypeKeySize()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptionMethodTypeImpl.KeySizeImpl();
    }

    /**
     * Create an instance of EncryptionMethodTypeKeySize
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.saml2.jaxb.xmlenc.EncryptionMethodType.KeySize createEncryptionMethodTypeKeySize(java.math.BigInteger value)
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptionMethodTypeImpl.KeySizeImpl(value);
    }

    /**
     * Create an instance of ReferenceListType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.saml2.jaxb.xmlenc.ReferenceListType createReferenceListType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.saml2.jaxb.xmlenc.impl.ReferenceListTypeImpl();
    }

    /**
     * Create an instance of ReferenceType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.saml2.jaxb.xmlenc.ReferenceType createReferenceType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.saml2.jaxb.xmlenc.impl.ReferenceTypeImpl();
    }

    /**
     * Create an instance of EncryptionPropertiesElement
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.saml2.jaxb.xmlenc.EncryptionPropertiesElement createEncryptionPropertiesElement()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptionPropertiesElementImpl();
    }

    /**
     * Create an instance of EncryptionMethodType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.saml2.jaxb.xmlenc.EncryptionMethodType createEncryptionMethodType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptionMethodTypeImpl();
    }

    /**
     * Create an instance of EncryptedKeyElement
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.saml2.jaxb.xmlenc.EncryptedKeyElement createEncryptedKeyElement()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedKeyElementImpl();
    }

    /**
     * Create an instance of CipherReferenceElement
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.saml2.jaxb.xmlenc.CipherReferenceElement createCipherReferenceElement()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.saml2.jaxb.xmlenc.impl.CipherReferenceElementImpl();
    }

    /**
     * Create an instance of EncryptedKeyType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.saml2.jaxb.xmlenc.EncryptedKeyType createEncryptedKeyType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedKeyTypeImpl();
    }

    /**
     * Create an instance of TransformsType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.saml2.jaxb.xmlenc.TransformsType createTransformsType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.saml2.jaxb.xmlenc.impl.TransformsTypeImpl();
    }

    /**
     * Create an instance of EncryptedDataType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.saml2.jaxb.xmlenc.EncryptedDataType createEncryptedDataType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedDataTypeImpl();
    }

    /**
     * Create an instance of ReferenceListElement
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.saml2.jaxb.xmlenc.ReferenceListElement createReferenceListElement()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.saml2.jaxb.xmlenc.impl.ReferenceListElementImpl();
    }

    /**
     * Create an instance of EncryptionMethodTypeOAEPparams
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.saml2.jaxb.xmlenc.EncryptionMethodType.OAEPparams createEncryptionMethodTypeOAEPparams()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptionMethodTypeImpl.OAEPparamsImpl();
    }

    /**
     * Create an instance of EncryptionMethodTypeOAEPparams
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.saml2.jaxb.xmlenc.EncryptionMethodType.OAEPparams createEncryptionMethodTypeOAEPparams(byte[] value)
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptionMethodTypeImpl.OAEPparamsImpl(value);
    }

    /**
     * Create an instance of EncryptionPropertyType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.saml2.jaxb.xmlenc.EncryptionPropertyType createEncryptionPropertyType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptionPropertyTypeImpl();
    }

    /**
     * Create an instance of EncryptedDataElement
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.saml2.jaxb.xmlenc.EncryptedDataElement createEncryptedDataElement()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedDataElementImpl();
    }

    /**
     * Create an instance of EncryptionPropertiesType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.saml2.jaxb.xmlenc.EncryptionPropertiesType createEncryptionPropertiesType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptionPropertiesTypeImpl();
    }

    /**
     * Create an instance of EncryptionPropertyElement
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.saml2.jaxb.xmlenc.EncryptionPropertyElement createEncryptionPropertyElement()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptionPropertyElementImpl();
    }

    /**
     * Create an instance of AgreementMethodElement
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.saml2.jaxb.xmlenc.AgreementMethodElement createAgreementMethodElement()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.saml2.jaxb.xmlenc.impl.AgreementMethodElementImpl();
    }

    /**
     * Create an instance of AgreementMethodTypeRecipientKeyInfo
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.saml2.jaxb.xmlenc.AgreementMethodType.RecipientKeyInfo createAgreementMethodTypeRecipientKeyInfo()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.saml2.jaxb.xmlenc.impl.AgreementMethodTypeImpl.RecipientKeyInfoImpl();
    }

    /**
     * Create an instance of AgreementMethodTypeOriginatorKeyInfo
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.saml2.jaxb.xmlenc.AgreementMethodType.OriginatorKeyInfo createAgreementMethodTypeOriginatorKeyInfo()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.saml2.jaxb.xmlenc.impl.AgreementMethodTypeImpl.OriginatorKeyInfoImpl();
    }

    /**
     * Create an instance of AgreementMethodType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.sun.identity.saml2.jaxb.xmlenc.AgreementMethodType createAgreementMethodType()
        throws javax.xml.bind.JAXBException
    {
        return new com.sun.identity.saml2.jaxb.xmlenc.impl.AgreementMethodTypeImpl();
    }

}
