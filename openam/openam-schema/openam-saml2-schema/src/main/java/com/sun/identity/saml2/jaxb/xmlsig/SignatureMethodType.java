//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:34:07 AM PDT 
//


package com.sun.identity.saml2.jaxb.xmlsig;


/**
 * Java content class for SignatureMethodType complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/Users/allan/A-SVN/trunk/opensso/products/federation/library/xsd/saml2/xmldsig-core-schema.xsd line 87)
 * <p>
 * <pre>
 * &lt;complexType name="SignatureMethodType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HMACOutputLength" type="{http://www.w3.org/2000/09/xmldsig#}HMACOutputLengthType" minOccurs="0"/>
 *         &lt;any/>
 *       &lt;/sequence>
 *       &lt;attribute name="Algorithm" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface SignatureMethodType {


    /**
     * Gets the value of the algorithm property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getAlgorithm();

    /**
     * Sets the value of the algorithm property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setAlgorithm(java.lang.String value);

    /**
     * Gets the value of the Content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the Content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link java.lang.String}
     * {@link java.lang.Object}
     * {@link com.sun.identity.saml2.jaxb.xmlsig.SignatureMethodType.HMACOutputLength}
     * 
     */
    java.util.List getContent();


    /**
     * Java content class for HMACOutputLength element declaration.
     * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/Users/allan/A-SVN/trunk/opensso/products/federation/library/xsd/saml2/xmldsig-core-schema.xsd line 89)
     * <p>
     * <pre>
     * &lt;element name="HMACOutputLength" type="{http://www.w3.org/2000/09/xmldsig#}HMACOutputLengthType"/>
     * </pre>
     * 
     */
    public interface HMACOutputLength
        extends javax.xml.bind.Element
    {


        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link java.math.BigInteger}
         */
        java.math.BigInteger getValue();

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link java.math.BigInteger}
         */
        void setValue(java.math.BigInteger value);

    }

}
