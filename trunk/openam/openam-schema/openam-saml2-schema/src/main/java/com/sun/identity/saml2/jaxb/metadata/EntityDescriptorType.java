//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:34:07 AM PDT 
//


package com.sun.identity.saml2.jaxb.metadata;


/**
 * Java content class for EntityDescriptorType complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/Users/allan/A-SVN/trunk/opensso/products/federation/library/xsd/saml2/saml-schema-metadata-2.0.xsd line 129)
 * <p>
 * <pre>
 * &lt;complexType name="EntityDescriptorType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}Signature" minOccurs="0"/>
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:metadata}Extensions" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;choice maxOccurs="unbounded">
 *             &lt;element ref="{urn:oasis:names:tc:SAML:2.0:metadata}RoleDescriptor"/>
 *             &lt;element ref="{urn:oasis:names:tc:SAML:2.0:metadata}IDPSSODescriptor"/>
 *             &lt;element ref="{urn:oasis:names:tc:SAML:2.0:metadata}SPSSODescriptor"/>
 *             &lt;element ref="{urn:oasis:names:tc:SAML:2.0:metadata}AuthnAuthorityDescriptor"/>
 *             &lt;element ref="{urn:oasis:names:tc:SAML:2.0:metadata}AttributeAuthorityDescriptor"/>
 *             &lt;element ref="{urn:oasis:names:tc:SAML:2.0:metadata}PDPDescriptor"/>
 *             &lt;element ref="{urn:oasis:names:tc:SAML:2.0:metadata}XACMLPDPDescriptor"/>
 *             &lt;element ref="{urn:oasis:names:tc:SAML:2.0:metadata}QueryDescriptor"/>
 *             &lt;element ref="{urn:oasis:names:tc:SAML:2.0:metadata}XACMLAuthzDecisionQueryDescriptor"/>
 *             &lt;element ref="{urn:oasis:names:tc:SAML:metadata:ext:query}AttributeQueryDescriptor"/>
 *           &lt;/choice>
 *           &lt;element ref="{urn:oasis:names:tc:SAML:2.0:metadata}AffiliationDescriptor"/>
 *         &lt;/choice>
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:metadata}Organization" minOccurs="0"/>
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:metadata}ContactPerson" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:metadata}AdditionalMetadataLocation" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="cacheDuration" type="{http://www.w3.org/2001/XMLSchema}duration" />
 *       &lt;attribute name="entityID" use="required" type="{urn:oasis:names:tc:SAML:2.0:metadata}entityIDType" />
 *       &lt;attribute name="validUntil" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface EntityDescriptorType {


    /**
     * Gets the value of the RoleDescriptorOrIDPSSODescriptorOrSPSSODescriptor property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the RoleDescriptorOrIDPSSODescriptorOrSPSSODescriptor property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRoleDescriptorOrIDPSSODescriptorOrSPSSODescriptor().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link com.sun.identity.saml2.jaxb.metadataextquery.AttributeQueryDescriptorElement}
     * {@link com.sun.identity.saml2.jaxb.metadata.IDPSSODescriptorElement}
     * {@link com.sun.identity.saml2.jaxb.metadata.AuthnAuthorityDescriptorElement}
     * {@link com.sun.identity.saml2.jaxb.metadata.QueryDescriptorElement}
     * {@link com.sun.identity.saml2.jaxb.metadata.RoleDescriptorElement}
     * {@link com.sun.identity.saml2.jaxb.metadata.XACMLPDPDescriptorElement}
     * {@link com.sun.identity.saml2.jaxb.metadata.SPSSODescriptorElement}
     * {@link com.sun.identity.saml2.jaxb.metadata.AttributeAuthorityDescriptorElement}
     * {@link com.sun.identity.saml2.jaxb.metadata.XACMLAuthzDecisionQueryDescriptorElement}
     * {@link com.sun.identity.saml2.jaxb.metadata.PDPDescriptorElement}
     * 
     */
    java.util.List getRoleDescriptorOrIDPSSODescriptorOrSPSSODescriptor();

    /**
     * Gets the value of the extensions property.
     * 
     * @return
     *     possible object is
     *     {@link com.sun.identity.saml2.jaxb.metadata.ExtensionsType}
     *     {@link com.sun.identity.saml2.jaxb.metadata.ExtensionsElement}
     */
    com.sun.identity.saml2.jaxb.metadata.ExtensionsType getExtensions();

    /**
     * Sets the value of the extensions property.
     * 
     * @param value
     *     allowed object is
     *     {@link com.sun.identity.saml2.jaxb.metadata.ExtensionsType}
     *     {@link com.sun.identity.saml2.jaxb.metadata.ExtensionsElement}
     */
    void setExtensions(com.sun.identity.saml2.jaxb.metadata.ExtensionsType value);

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
     * {@link com.sun.identity.saml2.jaxb.metadata.ContactType}
     * {@link com.sun.identity.saml2.jaxb.metadata.ContactPersonElement}
     * 
     */
    java.util.List getContactPerson();

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getID();

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setID(java.lang.String value);

    /**
     * Gets the value of the organization property.
     * 
     * @return
     *     possible object is
     *     {@link com.sun.identity.saml2.jaxb.metadata.OrganizationType}
     *     {@link com.sun.identity.saml2.jaxb.metadata.OrganizationElement}
     */
    com.sun.identity.saml2.jaxb.metadata.OrganizationType getOrganization();

    /**
     * Sets the value of the organization property.
     * 
     * @param value
     *     allowed object is
     *     {@link com.sun.identity.saml2.jaxb.metadata.OrganizationType}
     *     {@link com.sun.identity.saml2.jaxb.metadata.OrganizationElement}
     */
    void setOrganization(com.sun.identity.saml2.jaxb.metadata.OrganizationType value);

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
     * Gets the value of the AdditionalMetadataLocation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the AdditionalMetadataLocation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdditionalMetadataLocation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link com.sun.identity.saml2.jaxb.metadata.AdditionalMetadataLocationType}
     * {@link com.sun.identity.saml2.jaxb.metadata.AdditionalMetadataLocationElement}
     * 
     */
    java.util.List getAdditionalMetadataLocation();

    /**
     * Gets the value of the entityID property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getEntityID();

    /**
     * Sets the value of the entityID property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setEntityID(java.lang.String value);

    /**
     * Gets the value of the affiliationDescriptor property.
     * 
     * @return
     *     possible object is
     *     {@link com.sun.identity.saml2.jaxb.metadata.AffiliationDescriptorElement}
     *     {@link com.sun.identity.saml2.jaxb.metadata.AffiliationDescriptorType}
     */
    com.sun.identity.saml2.jaxb.metadata.AffiliationDescriptorType getAffiliationDescriptor();

    /**
     * Sets the value of the affiliationDescriptor property.
     * 
     * @param value
     *     allowed object is
     *     {@link com.sun.identity.saml2.jaxb.metadata.AffiliationDescriptorElement}
     *     {@link com.sun.identity.saml2.jaxb.metadata.AffiliationDescriptorType}
     */
    void setAffiliationDescriptor(com.sun.identity.saml2.jaxb.metadata.AffiliationDescriptorType value);

    /**
     * Gets the value of the signature property.
     * 
     * @return
     *     possible object is
     *     {@link com.sun.identity.saml2.jaxb.xmlsig.SignatureType}
     *     {@link com.sun.identity.saml2.jaxb.xmlsig.SignatureElement}
     */
    com.sun.identity.saml2.jaxb.xmlsig.SignatureType getSignature();

    /**
     * Sets the value of the signature property.
     * 
     * @param value
     *     allowed object is
     *     {@link com.sun.identity.saml2.jaxb.xmlsig.SignatureType}
     *     {@link com.sun.identity.saml2.jaxb.xmlsig.SignatureElement}
     */
    void setSignature(com.sun.identity.saml2.jaxb.xmlsig.SignatureType value);

}
