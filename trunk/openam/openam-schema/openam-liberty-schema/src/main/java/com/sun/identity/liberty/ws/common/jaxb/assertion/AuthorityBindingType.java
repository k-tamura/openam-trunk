//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:33:54 AM PDT 
//


package com.sun.identity.liberty.ws.common.jaxb.assertion;


/**
 * Java content class for AuthorityBindingType complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/Users/allan/A-SVN/trunk/opensso/products/federation/library/xsd/liberty/cs-sstc-schema-assertion-01.xsd line 171)
 * <p>
 * <pre>
 * &lt;complexType name="AuthorityBindingType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="AuthorityKind" use="required" type="{http://www.w3.org/2001/XMLSchema}QName" />
 *       &lt;attribute name="Binding" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;attribute name="Location" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface AuthorityBindingType {


    /**
     * Gets the value of the authorityKind property.
     * 
     * @return
     *     possible object is
     *     {@link javax.xml.namespace.QName}
     */
    javax.xml.namespace.QName getAuthorityKind();

    /**
     * Sets the value of the authorityKind property.
     * 
     * @param value
     *     allowed object is
     *     {@link javax.xml.namespace.QName}
     */
    void setAuthorityKind(javax.xml.namespace.QName value);

    /**
     * Gets the value of the location property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getLocation();

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setLocation(java.lang.String value);

    /**
     * Gets the value of the binding property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getBinding();

    /**
     * Sets the value of the binding property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setBinding(java.lang.String value);

}
