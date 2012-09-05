//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:26:55 AM PDT 
//


package com.sun.identity.entitlement.xacml3.core;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *         &lt;element ref="{urn:oasis:names:tc:xacml:3.0:core:schema:cd-1}RequestDefaults" minOccurs="0"/>
 *         &lt;element ref="{urn:oasis:names:tc:xacml:3.0:core:schema:cd-1}Attributes" maxOccurs="unbounded"/>
 *         &lt;element ref="{urn:oasis:names:tc:xacml:3.0:core:schema:cd-1}MultiRequests" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="ReturnPolicyIdList" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestType", propOrder = {
    "requestDefaultsAndAttributesAndMultiRequests"
})
public class Request {

    @XmlElements({
        @XmlElement(name = "Attributes", type = Attributes.class),
        @XmlElement(name = "MultiRequests", type = MultiRequests.class),
        @XmlElement(name = "RequestDefaults", type = RequestDefaults.class)
    })
    protected List<Object> requestDefaultsAndAttributesAndMultiRequests;
    @XmlAttribute(name = "ReturnPolicyIdList", required = true)
    protected boolean returnPolicyIdList;

    /**
     * Gets the value of the requestDefaultsAndAttributesAndMultiRequests property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the requestDefaultsAndAttributesAndMultiRequests property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRequestDefaultsAndAttributesAndMultiRequests().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Attributes }
     * {@link MultiRequests }
     * {@link RequestDefaults }
     * 
     * 
     */
    public List<Object> getRequestDefaultsAndAttributesAndMultiRequests() {
        if (requestDefaultsAndAttributesAndMultiRequests == null) {
            requestDefaultsAndAttributesAndMultiRequests = new ArrayList<Object>();
        }
        return this.requestDefaultsAndAttributesAndMultiRequests;
    }

    /**
     * Gets the value of the returnPolicyIdList property.
     * 
     */
    public boolean isReturnPolicyIdList() {
        return returnPolicyIdList;
    }

    /**
     * Sets the value of the returnPolicyIdList property.
     * 
     */
    public void setReturnPolicyIdList(boolean value) {
        this.returnPolicyIdList = value;
    }

}
