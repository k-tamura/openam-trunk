//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:33:54 AM PDT 
//


package com.sun.identity.liberty.ws.idpp.jaxb;


/**
 * Java content class for CommonNameType complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/Users/allan/A-SVN/trunk/opensso/products/federation/library/xsd/liberty/lib-id-sis-pp.xsd line 54)
 * <p>
 * <pre>
 * &lt;complexType name="CommonNameType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{urn:liberty:id-sis-pp:2003-08}CN" minOccurs="0"/>
 *         &lt;element ref="{urn:liberty:id-sis-pp:2003-08}LCN" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{urn:liberty:id-sis-pp:2003-08}AltCN" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{urn:liberty:id-sis-pp:2003-08}LAltCN" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{urn:liberty:id-sis-pp:2003-08}AnalyzedName" minOccurs="0"/>
 *         &lt;element ref="{urn:liberty:id-sis-pp:2003-08}Extension" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:liberty:id-sis-pp:2003-08}commonAttributes"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface CommonNameType {


    /**
     * Gets the value of the extension property.
     * 
     * @return
     *     possible object is
     *     {@link com.sun.identity.liberty.ws.idpp.jaxb.ExtensionElement}
     *     {@link com.sun.identity.liberty.ws.idpp.jaxb.ExtensionType}
     */
    com.sun.identity.liberty.ws.idpp.jaxb.ExtensionType getExtension();

    /**
     * Sets the value of the extension property.
     * 
     * @param value
     *     allowed object is
     *     {@link com.sun.identity.liberty.ws.idpp.jaxb.ExtensionElement}
     *     {@link com.sun.identity.liberty.ws.idpp.jaxb.ExtensionType}
     */
    void setExtension(com.sun.identity.liberty.ws.idpp.jaxb.ExtensionType value);

    /**
     * Gets the value of the LCN property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the LCN property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLCN().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link com.sun.identity.liberty.ws.idpp.jaxb.LCNElement}
     * {@link com.sun.identity.liberty.ws.idpp.jaxb.DSTLocalizedString}
     * 
     */
    java.util.List getLCN();

    /**
     * Gets the value of the AltCN property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the AltCN property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAltCN().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link com.sun.identity.liberty.ws.idpp.jaxb.AltCNElement}
     * {@link com.sun.identity.liberty.ws.idpp.jaxb.DSTString}
     * 
     */
    java.util.List getAltCN();

    /**
     * Gets the value of the modificationTime property.
     * 
     * @return
     *     possible object is
     *     {@link java.util.Calendar}
     */
    java.util.Calendar getModificationTime();

    /**
     * Sets the value of the modificationTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.util.Calendar}
     */
    void setModificationTime(java.util.Calendar value);

    /**
     * Gets the value of the LAltCN property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the LAltCN property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLAltCN().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link com.sun.identity.liberty.ws.idpp.jaxb.LAltCNElement}
     * {@link com.sun.identity.liberty.ws.idpp.jaxb.DSTLocalizedString}
     * 
     */
    java.util.List getLAltCN();

    /**
     * Gets the value of the analyzedName property.
     * 
     * @return
     *     possible object is
     *     {@link com.sun.identity.liberty.ws.idpp.jaxb.AnalyzedNameType}
     *     {@link com.sun.identity.liberty.ws.idpp.jaxb.AnalyzedNameElement}
     */
    com.sun.identity.liberty.ws.idpp.jaxb.AnalyzedNameType getAnalyzedName();

    /**
     * Sets the value of the analyzedName property.
     * 
     * @param value
     *     allowed object is
     *     {@link com.sun.identity.liberty.ws.idpp.jaxb.AnalyzedNameType}
     *     {@link com.sun.identity.liberty.ws.idpp.jaxb.AnalyzedNameElement}
     */
    void setAnalyzedName(com.sun.identity.liberty.ws.idpp.jaxb.AnalyzedNameType value);

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
     * Gets the value of the cn property.
     * 
     * @return
     *     possible object is
     *     {@link com.sun.identity.liberty.ws.idpp.jaxb.CNElement}
     *     {@link com.sun.identity.liberty.ws.idpp.jaxb.DSTString}
     */
    com.sun.identity.liberty.ws.idpp.jaxb.DSTString getCN();

    /**
     * Sets the value of the cn property.
     * 
     * @param value
     *     allowed object is
     *     {@link com.sun.identity.liberty.ws.idpp.jaxb.CNElement}
     *     {@link com.sun.identity.liberty.ws.idpp.jaxb.DSTString}
     */
    void setCN(com.sun.identity.liberty.ws.idpp.jaxb.DSTString value);

}
