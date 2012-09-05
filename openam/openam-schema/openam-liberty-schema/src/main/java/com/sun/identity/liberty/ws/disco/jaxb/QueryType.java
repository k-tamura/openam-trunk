//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:33:54 AM PDT 
//


package com.sun.identity.liberty.ws.disco.jaxb;


/**
 * Java content class for QueryType complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/Users/allan/A-SVN/trunk/opensso/products/federation/library/xsd/liberty/lib-arch-disco-svc.xsd line 126)
 * <p>
 * <pre>
 * &lt;complexType name="QueryType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:liberty:disco:2003-08}ResourceIDGroup"/>
 *         &lt;element name="RequestedServiceType" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{urn:liberty:disco:2003-08}ServiceType"/>
 *                   &lt;element ref="{urn:liberty:disco:2003-08}Options" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface QueryType {


    /**
     * Gets the value of the RequestedServiceType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the RequestedServiceType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRequestedServiceType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link com.sun.identity.liberty.ws.disco.jaxb.QueryType.RequestedServiceTypeType}
     * 
     */
    java.util.List getRequestedServiceType();

    /**
     * Gets the value of the resourceID property.
     * 
     * @return
     *     possible object is
     *     {@link com.sun.identity.liberty.ws.disco.jaxb.ResourceIDType}
     *     {@link com.sun.identity.liberty.ws.disco.jaxb.ResourceIDElement}
     */
    com.sun.identity.liberty.ws.disco.jaxb.ResourceIDType getResourceID();

    /**
     * Sets the value of the resourceID property.
     * 
     * @param value
     *     allowed object is
     *     {@link com.sun.identity.liberty.ws.disco.jaxb.ResourceIDType}
     *     {@link com.sun.identity.liberty.ws.disco.jaxb.ResourceIDElement}
     */
    void setResourceID(com.sun.identity.liberty.ws.disco.jaxb.ResourceIDType value);

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
     * Gets the value of the encryptedResourceID property.
     * 
     * @return
     *     possible object is
     *     {@link com.sun.identity.liberty.ws.disco.jaxb.EncryptedResourceIDType}
     *     {@link com.sun.identity.liberty.ws.disco.jaxb.EncryptedResourceIDElement}
     */
    com.sun.identity.liberty.ws.disco.jaxb.EncryptedResourceIDType getEncryptedResourceID();

    /**
     * Sets the value of the encryptedResourceID property.
     * 
     * @param value
     *     allowed object is
     *     {@link com.sun.identity.liberty.ws.disco.jaxb.EncryptedResourceIDType}
     *     {@link com.sun.identity.liberty.ws.disco.jaxb.EncryptedResourceIDElement}
     */
    void setEncryptedResourceID(com.sun.identity.liberty.ws.disco.jaxb.EncryptedResourceIDType value);


    /**
     * Java content class for anonymous complex type.
     * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/Users/allan/A-SVN/trunk/opensso/products/federation/library/xsd/liberty/lib-arch-disco-svc.xsd line 130)
     * <p>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element ref="{urn:liberty:disco:2003-08}ServiceType"/>
     *         &lt;element ref="{urn:liberty:disco:2003-08}Options" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     */
    public interface RequestedServiceTypeType {


        /**
         * Gets the value of the serviceType property.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String}
         */
        java.lang.String getServiceType();

        /**
         * Sets the value of the serviceType property.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String}
         */
        void setServiceType(java.lang.String value);

        /**
         * Gets the value of the options property.
         * 
         * @return
         *     possible object is
         *     {@link com.sun.identity.liberty.ws.disco.jaxb.OptionsElement}
         *     {@link com.sun.identity.liberty.ws.disco.jaxb.OptionsType}
         */
        com.sun.identity.liberty.ws.disco.jaxb.OptionsType getOptions();

        /**
         * Sets the value of the options property.
         * 
         * @param value
         *     allowed object is
         *     {@link com.sun.identity.liberty.ws.disco.jaxb.OptionsElement}
         *     {@link com.sun.identity.liberty.ws.disco.jaxb.OptionsType}
         */
        void setOptions(com.sun.identity.liberty.ws.disco.jaxb.OptionsType value);

    }

}
