//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:33:54 AM PDT 
//


package com.sun.identity.liberty.ws.common.jaxb.ps;


/**
 * Java content class for AssertionType complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/Users/allan/A-SVN/trunk/opensso/products/federation/library/xsd/liberty/lib-arch-protocols-schema.xsd line 140)
 * <p>
 * <pre>
 * &lt;complexType name="AssertionType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:oasis:names:tc:SAML:1.0:assertion}AssertionType">
 *       &lt;attribute name="InResponseTo" type="{http://www.w3.org/2001/XMLSchema}NCName" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface AssertionType
    extends com.sun.identity.liberty.ws.common.jaxb.assertion.AssertionType
{


    /**
     * Gets the value of the inResponseTo property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getInResponseTo();

    /**
     * Sets the value of the inResponseTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setInResponseTo(java.lang.String value);

}
