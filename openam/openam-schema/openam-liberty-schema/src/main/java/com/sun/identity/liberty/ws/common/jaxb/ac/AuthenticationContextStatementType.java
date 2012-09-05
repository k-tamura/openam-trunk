//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:33:54 AM PDT 
//


package com.sun.identity.liberty.ws.common.jaxb.ac;


/**
 * Java content class for AuthenticationContextStatementType complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/Users/allan/A-SVN/trunk/opensso/products/federation/library/xsd/liberty/lib-arch-authentication-context.xsd line 543)
 * <p>
 * <pre>
 * &lt;complexType name="AuthenticationContextStatementType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{urn:liberty:ac:2003-08}Identification" minOccurs="0"/>
 *         &lt;element ref="{urn:liberty:ac:2003-08}TechnicalProtection" minOccurs="0"/>
 *         &lt;element ref="{urn:liberty:ac:2003-08}OperationalProtection" minOccurs="0"/>
 *         &lt;element ref="{urn:liberty:ac:2003-08}AuthenticationMethod" minOccurs="0"/>
 *         &lt;element ref="{urn:liberty:ac:2003-08}GoverningAgreements" minOccurs="0"/>
 *         &lt;element ref="{urn:liberty:ac:2003-08}AuthenticatingAuthority" minOccurs="0"/>
 *         &lt;element ref="{urn:liberty:ac:2003-08}Extension" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface AuthenticationContextStatementType {


    /**
     * Gets the value of the Extension property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the Extension property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExtension().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link com.sun.identity.liberty.ws.common.jaxb.ac.ExtensionElement}
     * {@link com.sun.identity.liberty.ws.common.jaxb.ac.ExtensionType}
     * 
     */
    java.util.List getExtension();

    /**
     * Gets the value of the technicalProtection property.
     * 
     * @return
     *     possible object is
     *     {@link com.sun.identity.liberty.ws.common.jaxb.ac.TechnicalProtectionElement}
     *     {@link com.sun.identity.liberty.ws.common.jaxb.ac.TechnicalProtectionType}
     */
    com.sun.identity.liberty.ws.common.jaxb.ac.TechnicalProtectionType getTechnicalProtection();

    /**
     * Sets the value of the technicalProtection property.
     * 
     * @param value
     *     allowed object is
     *     {@link com.sun.identity.liberty.ws.common.jaxb.ac.TechnicalProtectionElement}
     *     {@link com.sun.identity.liberty.ws.common.jaxb.ac.TechnicalProtectionType}
     */
    void setTechnicalProtection(com.sun.identity.liberty.ws.common.jaxb.ac.TechnicalProtectionType value);

    /**
     * Gets the value of the authenticatingAuthority property.
     * 
     * @return
     *     possible object is
     *     {@link com.sun.identity.liberty.ws.common.jaxb.ac.AuthenticatingAuthorityType}
     *     {@link com.sun.identity.liberty.ws.common.jaxb.ac.AuthenticatingAuthorityElement}
     */
    com.sun.identity.liberty.ws.common.jaxb.ac.AuthenticatingAuthorityType getAuthenticatingAuthority();

    /**
     * Sets the value of the authenticatingAuthority property.
     * 
     * @param value
     *     allowed object is
     *     {@link com.sun.identity.liberty.ws.common.jaxb.ac.AuthenticatingAuthorityType}
     *     {@link com.sun.identity.liberty.ws.common.jaxb.ac.AuthenticatingAuthorityElement}
     */
    void setAuthenticatingAuthority(com.sun.identity.liberty.ws.common.jaxb.ac.AuthenticatingAuthorityType value);

    /**
     * Gets the value of the governingAgreements property.
     * 
     * @return
     *     possible object is
     *     {@link com.sun.identity.liberty.ws.common.jaxb.ac.GoverningAgreementsElement}
     *     {@link com.sun.identity.liberty.ws.common.jaxb.ac.GoverningAgreementsType}
     */
    com.sun.identity.liberty.ws.common.jaxb.ac.GoverningAgreementsType getGoverningAgreements();

    /**
     * Sets the value of the governingAgreements property.
     * 
     * @param value
     *     allowed object is
     *     {@link com.sun.identity.liberty.ws.common.jaxb.ac.GoverningAgreementsElement}
     *     {@link com.sun.identity.liberty.ws.common.jaxb.ac.GoverningAgreementsType}
     */
    void setGoverningAgreements(com.sun.identity.liberty.ws.common.jaxb.ac.GoverningAgreementsType value);

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
     * Gets the value of the identification property.
     * 
     * @return
     *     possible object is
     *     {@link com.sun.identity.liberty.ws.common.jaxb.ac.IdentificationElement}
     *     {@link com.sun.identity.liberty.ws.common.jaxb.ac.IdentificationType}
     */
    com.sun.identity.liberty.ws.common.jaxb.ac.IdentificationType getIdentification();

    /**
     * Sets the value of the identification property.
     * 
     * @param value
     *     allowed object is
     *     {@link com.sun.identity.liberty.ws.common.jaxb.ac.IdentificationElement}
     *     {@link com.sun.identity.liberty.ws.common.jaxb.ac.IdentificationType}
     */
    void setIdentification(com.sun.identity.liberty.ws.common.jaxb.ac.IdentificationType value);

    /**
     * Gets the value of the operationalProtection property.
     * 
     * @return
     *     possible object is
     *     {@link com.sun.identity.liberty.ws.common.jaxb.ac.OperationalProtectionElement}
     *     {@link com.sun.identity.liberty.ws.common.jaxb.ac.OperationalProtectionType}
     */
    com.sun.identity.liberty.ws.common.jaxb.ac.OperationalProtectionType getOperationalProtection();

    /**
     * Sets the value of the operationalProtection property.
     * 
     * @param value
     *     allowed object is
     *     {@link com.sun.identity.liberty.ws.common.jaxb.ac.OperationalProtectionElement}
     *     {@link com.sun.identity.liberty.ws.common.jaxb.ac.OperationalProtectionType}
     */
    void setOperationalProtection(com.sun.identity.liberty.ws.common.jaxb.ac.OperationalProtectionType value);

    /**
     * Gets the value of the authenticationMethod property.
     * 
     * @return
     *     possible object is
     *     {@link com.sun.identity.liberty.ws.common.jaxb.ac.AuthenticationMethodType}
     *     {@link com.sun.identity.liberty.ws.common.jaxb.ac.AuthenticationMethodElement}
     */
    com.sun.identity.liberty.ws.common.jaxb.ac.AuthenticationMethodType getAuthenticationMethod();

    /**
     * Sets the value of the authenticationMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link com.sun.identity.liberty.ws.common.jaxb.ac.AuthenticationMethodType}
     *     {@link com.sun.identity.liberty.ws.common.jaxb.ac.AuthenticationMethodElement}
     */
    void setAuthenticationMethod(com.sun.identity.liberty.ws.common.jaxb.ac.AuthenticationMethodType value);

}
