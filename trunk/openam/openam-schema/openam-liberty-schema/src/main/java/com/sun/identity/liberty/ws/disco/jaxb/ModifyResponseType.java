//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:33:54 AM PDT 
//


package com.sun.identity.liberty.ws.disco.jaxb;


/**
 * Java content class for ModifyResponseType complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/Users/allan/A-SVN/trunk/opensso/products/federation/library/xsd/liberty/lib-arch-disco-svc.xsd line 183)
 * <p>
 * <pre>
 * &lt;complexType name="ModifyResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{urn:liberty:disco:2003-08}Status"/>
 *         &lt;element ref="{urn:liberty:disco:2003-08}Extension" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="newEntryIDs">
 *         &lt;simpleType>
 *           &lt;list itemType="{urn:liberty:disco:2003-08}IDReferenceType" />
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface ModifyResponseType {


    /**
     * Gets the value of the extension property.
     * 
     * @return
     *     possible object is
     *     {@link com.sun.identity.liberty.ws.disco.jaxb.ExtensionType}
     *     {@link com.sun.identity.liberty.ws.disco.jaxb.ExtensionElement}
     */
    com.sun.identity.liberty.ws.disco.jaxb.ExtensionType getExtension();

    /**
     * Sets the value of the extension property.
     * 
     * @param value
     *     allowed object is
     *     {@link com.sun.identity.liberty.ws.disco.jaxb.ExtensionType}
     *     {@link com.sun.identity.liberty.ws.disco.jaxb.ExtensionElement}
     */
    void setExtension(com.sun.identity.liberty.ws.disco.jaxb.ExtensionType value);

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link com.sun.identity.liberty.ws.disco.jaxb.StatusElement}
     *     {@link com.sun.identity.liberty.ws.disco.jaxb.StatusType}
     */
    com.sun.identity.liberty.ws.disco.jaxb.StatusType getStatus();

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link com.sun.identity.liberty.ws.disco.jaxb.StatusElement}
     *     {@link com.sun.identity.liberty.ws.disco.jaxb.StatusType}
     */
    void setStatus(com.sun.identity.liberty.ws.disco.jaxb.StatusType value);

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
     * Gets the value of the NewEntryIDs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the NewEntryIDs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNewEntryIDs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link java.lang.String}
     * 
     */
    java.util.List getNewEntryIDs();

}
