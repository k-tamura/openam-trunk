//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:33:54 AM PDT 
//


package com.sun.identity.liberty.ws.meta.jaxb;


/**
 * Java content class for providerDescriptorType complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/Users/allan/A-SVN/trunk/opensso/products/federation/library/xsd/liberty/lib-arch-metadata.xsd line 122)
 * <p>
 * <pre>
 * &lt;complexType name="providerDescriptorType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{urn:liberty:metadata:2003-08}KeyDescriptor" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="SoapEndpoint" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="SingleLogoutServiceURL" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="SingleLogoutServiceReturnURL" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="FederationTerminationServiceURL" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="FederationTerminationServiceReturnURL" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="FederationTerminationNotificationProtocolProfile" type="{http://www.w3.org/2001/XMLSchema}anyURI" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="SingleLogoutProtocolProfile" type="{http://www.w3.org/2001/XMLSchema}anyURI" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="RegisterNameIdentifierProtocolProfile" type="{http://www.w3.org/2001/XMLSchema}anyURI" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="RegisterNameIdentifierServiceURL" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="RegisterNameIdentifierServiceReturnURL" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="NameIdentifierMappingProtocolProfile" type="{urn:oasis:names:tc:SAML:1.0:assertion}AuthorityBindingType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="NameIdentifierMappingEncryptionProfile" type="{http://www.w3.org/2001/XMLSchema}anyURI" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Organization" type="{urn:liberty:metadata:2003-08}organizationType" minOccurs="0"/>
 *         &lt;element name="ContactPerson" type="{urn:liberty:metadata:2003-08}contactType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="AdditionalMetaLocation" type="{urn:liberty:metadata:2003-08}additionalMetadataLocationType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{urn:liberty:metadata:2003-08}Extension" minOccurs="0"/>
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}Signature" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="cacheDuration" type="{http://www.w3.org/2001/XMLSchema}duration" />
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="protocolSupportEnumeration" use="required" type="{http://www.w3.org/2001/XMLSchema}NMTOKENS" />
 *       &lt;attribute name="validUntil" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface ProviderDescriptorType {


    /**
     * Gets the value of the RegisterNameIdentifierProtocolProfile property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the RegisterNameIdentifierProtocolProfile property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRegisterNameIdentifierProtocolProfile().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link java.lang.String}
     * 
     */
    java.util.List getRegisterNameIdentifierProtocolProfile();

    /**
     * Gets the value of the NameIdentifierMappingProtocolProfile property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the NameIdentifierMappingProtocolProfile property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNameIdentifierMappingProtocolProfile().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link com.sun.identity.liberty.ws.common.jaxb.assertion.AuthorityBindingType}
     * 
     */
    java.util.List getNameIdentifierMappingProtocolProfile();

    /**
     * Gets the value of the registerNameIdentifierServiceReturnURL property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getRegisterNameIdentifierServiceReturnURL();

    /**
     * Sets the value of the registerNameIdentifierServiceReturnURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setRegisterNameIdentifierServiceReturnURL(java.lang.String value);

    /**
     * Gets the value of the ProtocolSupportEnumeration property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ProtocolSupportEnumeration property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProtocolSupportEnumeration().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link java.lang.String}
     * 
     */
    java.util.List getProtocolSupportEnumeration();

    /**
     * Gets the value of the ContactPerson property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ContactPerson property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContactPerson().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link com.sun.identity.liberty.ws.meta.jaxb.ContactType}
     * 
     */
    java.util.List getContactPerson();

    /**
     * Gets the value of the AdditionalMetaLocation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the AdditionalMetaLocation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdditionalMetaLocation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link com.sun.identity.liberty.ws.meta.jaxb.AdditionalMetadataLocationType}
     * 
     */
    java.util.List getAdditionalMetaLocation();

    /**
     * Gets the value of the organization property.
     * 
     * @return
     *     possible object is
     *     {@link com.sun.identity.liberty.ws.meta.jaxb.OrganizationType}
     */
    com.sun.identity.liberty.ws.meta.jaxb.OrganizationType getOrganization();

    /**
     * Sets the value of the organization property.
     * 
     * @param value
     *     allowed object is
     *     {@link com.sun.identity.liberty.ws.meta.jaxb.OrganizationType}
     */
    void setOrganization(com.sun.identity.liberty.ws.meta.jaxb.OrganizationType value);

    /**
     * Gets the value of the NameIdentifierMappingEncryptionProfile property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the NameIdentifierMappingEncryptionProfile property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNameIdentifierMappingEncryptionProfile().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link java.lang.String}
     * 
     */
    java.util.List getNameIdentifierMappingEncryptionProfile();

    /**
     * Gets the value of the singleLogoutServiceURL property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getSingleLogoutServiceURL();

    /**
     * Sets the value of the singleLogoutServiceURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setSingleLogoutServiceURL(java.lang.String value);

    /**
     * Gets the value of the signature property.
     * 
     * @return
     *     possible object is
     *     {@link com.sun.identity.liberty.ws.common.jaxb.xmlsig.SignatureElement}
     *     {@link com.sun.identity.liberty.ws.common.jaxb.xmlsig.SignatureType}
     */
    com.sun.identity.liberty.ws.common.jaxb.xmlsig.SignatureType getSignature();

    /**
     * Sets the value of the signature property.
     * 
     * @param value
     *     allowed object is
     *     {@link com.sun.identity.liberty.ws.common.jaxb.xmlsig.SignatureElement}
     *     {@link com.sun.identity.liberty.ws.common.jaxb.xmlsig.SignatureType}
     */
    void setSignature(com.sun.identity.liberty.ws.common.jaxb.xmlsig.SignatureType value);

    /**
     * Gets the value of the singleLogoutServiceReturnURL property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getSingleLogoutServiceReturnURL();

    /**
     * Sets the value of the singleLogoutServiceReturnURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setSingleLogoutServiceReturnURL(java.lang.String value);

    /**
     * Gets the value of the FederationTerminationNotificationProtocolProfile property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the FederationTerminationNotificationProtocolProfile property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFederationTerminationNotificationProtocolProfile().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link java.lang.String}
     * 
     */
    java.util.List getFederationTerminationNotificationProtocolProfile();

    /**
     * Gets the value of the extension property.
     * 
     * @return
     *     possible object is
     *     {@link com.sun.identity.liberty.ws.meta.jaxb.ExtensionElement}
     *     {@link com.sun.identity.liberty.ws.meta.jaxb.ExtensionType}
     */
    com.sun.identity.liberty.ws.meta.jaxb.ExtensionType getExtension();

    /**
     * Sets the value of the extension property.
     * 
     * @param value
     *     allowed object is
     *     {@link com.sun.identity.liberty.ws.meta.jaxb.ExtensionElement}
     *     {@link com.sun.identity.liberty.ws.meta.jaxb.ExtensionType}
     */
    void setExtension(com.sun.identity.liberty.ws.meta.jaxb.ExtensionType value);

    /**
     * Gets the value of the KeyDescriptor property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the KeyDescriptor property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKeyDescriptor().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link com.sun.identity.liberty.ws.meta.jaxb.KeyDescriptorElement}
     * {@link com.sun.identity.liberty.ws.meta.jaxb.KeyDescriptorType}
     * 
     */
    java.util.List getKeyDescriptor();

    /**
     * Gets the value of the registerNameIdentifierServiceURL property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getRegisterNameIdentifierServiceURL();

    /**
     * Sets the value of the registerNameIdentifierServiceURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setRegisterNameIdentifierServiceURL(java.lang.String value);

    /**
     * Gets the value of the federationTerminationServiceURL property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getFederationTerminationServiceURL();

    /**
     * Sets the value of the federationTerminationServiceURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setFederationTerminationServiceURL(java.lang.String value);

    /**
     * Gets the value of the validUntil property.
     * 
     * @return
     *     possible object is
     *     {@link java.util.Calendar}
     */
    java.util.Calendar getValidUntil();

    /**
     * Sets the value of the validUntil property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.util.Calendar}
     */
    void setValidUntil(java.util.Calendar value);

    /**
     * Gets the value of the federationTerminationServiceReturnURL property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getFederationTerminationServiceReturnURL();

    /**
     * Sets the value of the federationTerminationServiceReturnURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setFederationTerminationServiceReturnURL(java.lang.String value);

    /**
     * Gets the value of the cacheDuration property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getCacheDuration();

    /**
     * Sets the value of the cacheDuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setCacheDuration(java.lang.String value);

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getId();

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setId(java.lang.String value);

    /**
     * Gets the value of the soapEndpoint property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getSoapEndpoint();

    /**
     * Sets the value of the soapEndpoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setSoapEndpoint(java.lang.String value);

    /**
     * Gets the value of the SingleLogoutProtocolProfile property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the SingleLogoutProtocolProfile property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSingleLogoutProtocolProfile().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link java.lang.String}
     * 
     */
    java.util.List getSingleLogoutProtocolProfile();

}
