//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:34:16 AM PDT 
//


package com.sun.identity.wsfederation.jaxb.entityconfig;


/**
 * Java content class for FederationConfigType complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/Users/allan/A-SVN/trunk/opensso/products/federation/library/xsd/wsfederation/entity-config-schema.xsd line 42)
 * <p>
 * <pre>
 * &lt;complexType name="FederationConfigType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}Signature" minOccurs="0"/>
 *         &lt;element ref="{urn:sun:fm:wsfederation:1.0:federationconfig}Attribute" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;choice maxOccurs="unbounded">
 *             &lt;element ref="{urn:sun:fm:wsfederation:1.0:federationconfig}IDPSSOConfig"/>
 *             &lt;element ref="{urn:sun:fm:wsfederation:1.0:federationconfig}SPSSOConfig"/>
 *           &lt;/choice>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="FederationID" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="hosted" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface FederationConfigType {


    /**
     * Gets the value of the IDPSSOConfigOrSPSSOConfig property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the IDPSSOConfigOrSPSSOConfig property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIDPSSOConfigOrSPSSOConfig().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link com.sun.identity.wsfederation.jaxb.entityconfig.IDPSSOConfigElement}
     * {@link com.sun.identity.wsfederation.jaxb.entityconfig.SPSSOConfigElement}
     * 
     */
    java.util.List getIDPSSOConfigOrSPSSOConfig();

    /**
     * Gets the value of the federationID property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getFederationID();

    /**
     * Sets the value of the federationID property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setFederationID(java.lang.String value);

    /**
     * Gets the value of the hosted property.
     * 
     */
    boolean isHosted();

    /**
     * Sets the value of the hosted property.
     * 
     */
    void setHosted(boolean value);

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
     * Gets the value of the Attribute property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the Attribute property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttribute().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link com.sun.identity.wsfederation.jaxb.entityconfig.AttributeElement}
     * {@link com.sun.identity.wsfederation.jaxb.entityconfig.AttributeType}
     * 
     */
    java.util.List getAttribute();

    /**
     * Gets the value of the signature property.
     * 
     * @return
     *     possible object is
     *     {@link com.sun.identity.wsfederation.jaxb.xmlsig.SignatureType}
     *     {@link com.sun.identity.wsfederation.jaxb.xmlsig.SignatureElement}
     */
    com.sun.identity.wsfederation.jaxb.xmlsig.SignatureType getSignature();

    /**
     * Sets the value of the signature property.
     * 
     * @param value
     *     allowed object is
     *     {@link com.sun.identity.wsfederation.jaxb.xmlsig.SignatureType}
     *     {@link com.sun.identity.wsfederation.jaxb.xmlsig.SignatureElement}
     */
    void setSignature(com.sun.identity.wsfederation.jaxb.xmlsig.SignatureType value);

}
