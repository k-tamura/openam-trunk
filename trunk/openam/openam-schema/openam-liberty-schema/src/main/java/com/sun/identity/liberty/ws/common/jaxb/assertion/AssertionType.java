//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:33:54 AM PDT 
//


package com.sun.identity.liberty.ws.common.jaxb.assertion;


/**
 * Java content class for AssertionType complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/Users/allan/A-SVN/trunk/opensso/products/federation/library/xsd/liberty/cs-sstc-schema-assertion-01.xsd line 55)
 * <p>
 * <pre>
 * &lt;complexType name="AssertionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{urn:oasis:names:tc:SAML:1.0:assertion}Conditions" minOccurs="0"/>
 *         &lt;element ref="{urn:oasis:names:tc:SAML:1.0:assertion}Advice" minOccurs="0"/>
 *         &lt;choice maxOccurs="unbounded">
 *           &lt;element ref="{urn:oasis:names:tc:SAML:1.0:assertion}Statement"/>
 *           &lt;element ref="{urn:oasis:names:tc:SAML:1.0:assertion}SubjectStatement"/>
 *           &lt;element ref="{urn:oasis:names:tc:SAML:1.0:assertion}AuthenticationStatement"/>
 *           &lt;element ref="{urn:oasis:names:tc:SAML:1.0:assertion}AuthorizationDecisionStatement"/>
 *           &lt;element ref="{urn:oasis:names:tc:SAML:1.0:assertion}AttributeStatement"/>
 *         &lt;/choice>
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}Signature" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="AssertionID" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="IssueInstant" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="Issuer" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="MajorVersion" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="MinorVersion" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface AssertionType {


    /**
     * Gets the value of the conditions property.
     * 
     * @return
     *     possible object is
     *     {@link com.sun.identity.liberty.ws.common.jaxb.assertion.ConditionsElement}
     *     {@link com.sun.identity.liberty.ws.common.jaxb.assertion.ConditionsType}
     */
    com.sun.identity.liberty.ws.common.jaxb.assertion.ConditionsType getConditions();

    /**
     * Sets the value of the conditions property.
     * 
     * @param value
     *     allowed object is
     *     {@link com.sun.identity.liberty.ws.common.jaxb.assertion.ConditionsElement}
     *     {@link com.sun.identity.liberty.ws.common.jaxb.assertion.ConditionsType}
     */
    void setConditions(com.sun.identity.liberty.ws.common.jaxb.assertion.ConditionsType value);

    /**
     * Gets the value of the issuer property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getIssuer();

    /**
     * Sets the value of the issuer property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setIssuer(java.lang.String value);

    /**
     * Gets the value of the minorVersion property.
     * 
     * @return
     *     possible object is
     *     {@link java.math.BigInteger}
     */
    java.math.BigInteger getMinorVersion();

    /**
     * Sets the value of the minorVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.math.BigInteger}
     */
    void setMinorVersion(java.math.BigInteger value);

    /**
     * Gets the value of the StatementOrSubjectStatementOrAuthenticationStatement property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the StatementOrSubjectStatementOrAuthenticationStatement property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStatementOrSubjectStatementOrAuthenticationStatement().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link com.sun.identity.liberty.ws.common.jaxb.assertion.AttributeStatementElement}
     * {@link com.sun.identity.liberty.ws.common.jaxb.assertion.AuthorizationDecisionStatementElement}
     * {@link com.sun.identity.liberty.ws.common.jaxb.assertion.AuthenticationStatementElement}
     * {@link com.sun.identity.liberty.ws.common.jaxb.security.ResourceAccessStatementElement}
     * {@link com.sun.identity.liberty.ws.common.jaxb.assertion.SubjectStatementElement}
     * {@link com.sun.identity.liberty.ws.common.jaxb.ps.AuthenticationStatementElement}
     * {@link com.sun.identity.liberty.ws.common.jaxb.assertion.StatementElement}
     * {@link com.sun.identity.liberty.ws.common.jaxb.security.SessionContextStatementElement}
     * 
     */
    java.util.List getStatementOrSubjectStatementOrAuthenticationStatement();

    /**
     * Gets the value of the advice property.
     * 
     * @return
     *     possible object is
     *     {@link com.sun.identity.liberty.ws.common.jaxb.assertion.AdviceElement}
     *     {@link com.sun.identity.liberty.ws.common.jaxb.assertion.AdviceType}
     */
    com.sun.identity.liberty.ws.common.jaxb.assertion.AdviceType getAdvice();

    /**
     * Sets the value of the advice property.
     * 
     * @param value
     *     allowed object is
     *     {@link com.sun.identity.liberty.ws.common.jaxb.assertion.AdviceElement}
     *     {@link com.sun.identity.liberty.ws.common.jaxb.assertion.AdviceType}
     */
    void setAdvice(com.sun.identity.liberty.ws.common.jaxb.assertion.AdviceType value);

    /**
     * Gets the value of the issueInstant property.
     * 
     * @return
     *     possible object is
     *     {@link java.util.Calendar}
     */
    java.util.Calendar getIssueInstant();

    /**
     * Sets the value of the issueInstant property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.util.Calendar}
     */
    void setIssueInstant(java.util.Calendar value);

    /**
     * Gets the value of the assertionID property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getAssertionID();

    /**
     * Sets the value of the assertionID property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setAssertionID(java.lang.String value);

    /**
     * Gets the value of the signature property.
     * 
     * @return
     *     possible object is
     *     {@link com.sun.identity.liberty.ws.common.jaxb.xmlsig.SignatureElement}
     *     {@link com.sun.identity.liberty.ws.common.jaxb.xmlsig.SignatureType}
     */
    com.sun.identity.liberty.ws.common.jaxb.xmlsig.SignatureType getSignature();

    /**
     * Sets the value of the signature property.
     * 
     * @param value
     *     allowed object is
     *     {@link com.sun.identity.liberty.ws.common.jaxb.xmlsig.SignatureElement}
     *     {@link com.sun.identity.liberty.ws.common.jaxb.xmlsig.SignatureType}
     */
    void setSignature(com.sun.identity.liberty.ws.common.jaxb.xmlsig.SignatureType value);

    /**
     * Gets the value of the majorVersion property.
     * 
     * @return
     *     possible object is
     *     {@link java.math.BigInteger}
     */
    java.math.BigInteger getMajorVersion();

    /**
     * Sets the value of the majorVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.math.BigInteger}
     */
    void setMajorVersion(java.math.BigInteger value);

}
