//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-661 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.01.14 at 08:50:29 AM PST 
//


package com.sun.identity.entitlement.xacml3.core;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for PolicySetCombinerParametersType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PolicySetCombinerParametersType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:oasis:names:tc:xacml:3.0:core:schema:wd-17}CombinerParametersType">
 *       &lt;attribute name="PolicySetIdRef" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PolicySetCombinerParametersType")
public class PolicySetCombinerParameters
    extends CombinerParameters
{

    @XmlAttribute(name = "PolicySetIdRef", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String policySetIdRef;

    /**
     * Gets the value of the policySetIdRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPolicySetIdRef() {
        return policySetIdRef;
    }

    /**
     * Sets the value of the policySetIdRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPolicySetIdRef(String value) {
        this.policySetIdRef = value;
    }

}
