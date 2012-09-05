//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:34:07 AM PDT 
//


package com.sun.identity.saml2.jaxb.assertion;


/**
 * Java content class for AuthzDecisionStatementType complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/Users/allan/A-SVN/trunk/opensso/products/federation/library/xsd/saml2/saml-schema-assertion-2.0.xsd line 253)
 * <p>
 * <pre>
 * &lt;complexType name="AuthzDecisionStatementType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:oasis:names:tc:SAML:2.0:assertion}StatementAbstractType">
 *       &lt;sequence>
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:assertion}Action" maxOccurs="unbounded"/>
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:assertion}Evidence" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Decision" use="required" type="{urn:oasis:names:tc:SAML:2.0:assertion}DecisionType" />
 *       &lt;attribute name="Resource" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface AuthzDecisionStatementType
    extends com.sun.identity.saml2.jaxb.assertion.StatementAbstractType
{


    /**
     * Gets the value of the resource property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getResource();

    /**
     * Sets the value of the resource property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setResource(java.lang.String value);

    /**
     * Gets the value of the Action property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the Action property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAction().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link com.sun.identity.saml2.jaxb.assertion.ActionElement}
     * {@link com.sun.identity.saml2.jaxb.assertion.ActionType}
     * 
     */
    java.util.List getAction();

    /**
     * Gets the value of the decision property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getDecision();

    /**
     * Sets the value of the decision property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setDecision(java.lang.String value);

    /**
     * Gets the value of the evidence property.
     * 
     * @return
     *     possible object is
     *     {@link com.sun.identity.saml2.jaxb.assertion.EvidenceType}
     *     {@link com.sun.identity.saml2.jaxb.assertion.EvidenceElement}
     */
    com.sun.identity.saml2.jaxb.assertion.EvidenceType getEvidence();

    /**
     * Sets the value of the evidence property.
     * 
     * @param value
     *     allowed object is
     *     {@link com.sun.identity.saml2.jaxb.assertion.EvidenceType}
     *     {@link com.sun.identity.saml2.jaxb.assertion.EvidenceElement}
     */
    void setEvidence(com.sun.identity.saml2.jaxb.assertion.EvidenceType value);

}
