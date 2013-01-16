//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-661 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.01.14 at 08:50:29 AM PST 
//


package com.sun.identity.entitlement.xacml3.core;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;


/**
 * <p>Java class for VariableDefinitionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VariableDefinitionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{urn:oasis:names:tc:xacml:3.0:core:schema:wd-17}Expression"/>
 *       &lt;/sequence>
 *       &lt;attribute name="VariableId" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VariableDefinitionType", propOrder = {
    "expression"
})
public class VariableDefinition {

    @XmlElementRef(name = "Expression", namespace = "urn:oasis:names:tc:xacml:3.0:core:schema:wd-17", type = JAXBElement.class)
    protected JAXBElement<?> expression;
    @XmlAttribute(name = "VariableId", required = true)
    protected String variableId;

    /**
     * Gets the value of the expression property.
     * 
     * @return
     *     possible object is
     *     {@link javax.xml.bind.JAXBElement }{@code <}{@link AttributeSelector }{@code >}
     *     {@link javax.xml.bind.JAXBElement }{@code <}{@link Function }{@code >}
     *     {@link javax.xml.bind.JAXBElement }{@code <}{@link VariableReference }{@code >}
     *     {@link javax.xml.bind.JAXBElement }{@code <}{@link Apply }{@code >}
     *     {@link javax.xml.bind.JAXBElement }{@code <}{@link Expression }{@code >}
     *     {@link javax.xml.bind.JAXBElement }{@code <}{@link AttributeValue }{@code >}
     *     {@link javax.xml.bind.JAXBElement }{@code <}{@link AttributeDesignator }{@code >}
     *
     */
    public JAXBElement<?> getExpression() {
        return expression;
    }

    /**
     * Sets the value of the expression property.
     *
     * @param value
     *     allowed object is
     *     {@link javax.xml.bind.JAXBElement }{@code <}{@link AttributeSelector }{@code >}
     *     {@link javax.xml.bind.JAXBElement }{@code <}{@link Function }{@code >}
     *     {@link javax.xml.bind.JAXBElement }{@code <}{@link VariableReference }{@code >}
     *     {@link javax.xml.bind.JAXBElement }{@code <}{@link Apply }{@code >}
     *     {@link javax.xml.bind.JAXBElement }{@code <}{@link Expression }{@code >}
     *     {@link javax.xml.bind.JAXBElement }{@code <}{@link AttributeValue }{@code >}
     *     {@link javax.xml.bind.JAXBElement }{@code <}{@link AttributeDesignator }{@code >}
     *     
     */
    public void setExpression(JAXBElement<?> value) {
        this.expression = ((JAXBElement<?>) value);
    }

    /**
     * Gets the value of the variableId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVariableId() {
        return variableId;
    }

    /**
     * Sets the value of the variableId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVariableId(String value) {
        this.variableId = value;
    }

}
