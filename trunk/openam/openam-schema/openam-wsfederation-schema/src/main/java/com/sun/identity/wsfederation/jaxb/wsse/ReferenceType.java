//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:34:16 AM PDT 
//


package com.sun.identity.wsfederation.jaxb.wsse;


/**
 * This type represents a reference to an external security token.
 * Java content class for ReferenceType complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/Users/allan/A-SVN/trunk/opensso/products/federation/library/xsd/wsfederation/oasis-200401-wss-wssecurity-secext-1.0.xsd line 87)
 * <p>
 * <pre>
 * &lt;complexType name="ReferenceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="URI" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;attribute name="ValueType" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface ReferenceType {


    /**
     * Gets the value of the valueType property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getValueType();

    /**
     * Sets the value of the valueType property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setValueType(java.lang.String value);

    /**
     * Gets the value of the uri property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getURI();

    /**
     * Sets the value of the uri property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setURI(java.lang.String value);

}
