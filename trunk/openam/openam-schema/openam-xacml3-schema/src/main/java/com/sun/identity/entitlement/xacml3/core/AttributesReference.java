//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-661 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.01.14 at 08:50:29 AM PST 
//


package com.sun.identity.entitlement.xacml3.core;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for AttributesReferenceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AttributesReferenceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="ReferenceId" use="required" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AttributesReferenceType")
public class AttributesReference {

    @XmlAttribute(name = "ReferenceId", required = true)
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    protected Object referenceId;

    /**
     * Gets the value of the referenceId property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getReferenceId() {
        return referenceId;
    }

    /**
     * Sets the value of the referenceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setReferenceId(Object value) {
        this.referenceId = value;
    }

}
