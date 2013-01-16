//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-661 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.01.14 at 08:50:29 AM PST 
//


package com.sun.identity.entitlement.xacml3.core;

import javax.xml.bind.annotation.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for PolicyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PolicyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{urn:oasis:names:tc:xacml:3.0:core:schema:wd-17}Description" minOccurs="0"/>
 *         &lt;element ref="{urn:oasis:names:tc:xacml:3.0:core:schema:wd-17}PolicyIssuer" minOccurs="0"/>
 *         &lt;element ref="{urn:oasis:names:tc:xacml:3.0:core:schema:wd-17}PolicyDefaults" minOccurs="0"/>
 *         &lt;element ref="{urn:oasis:names:tc:xacml:3.0:core:schema:wd-17}Target"/>
 *         &lt;choice maxOccurs="unbounded">
 *           &lt;element ref="{urn:oasis:names:tc:xacml:3.0:core:schema:wd-17}CombinerParameters" minOccurs="0"/>
 *           &lt;element ref="{urn:oasis:names:tc:xacml:3.0:core:schema:wd-17}RuleCombinerParameters" minOccurs="0"/>
 *           &lt;element ref="{urn:oasis:names:tc:xacml:3.0:core:schema:wd-17}VariableDefinition"/>
 *           &lt;element ref="{urn:oasis:names:tc:xacml:3.0:core:schema:wd-17}Rule"/>
 *         &lt;/choice>
 *         &lt;element ref="{urn:oasis:names:tc:xacml:3.0:core:schema:wd-17}ObligationExpressions" minOccurs="0"/>
 *         &lt;element ref="{urn:oasis:names:tc:xacml:3.0:core:schema:wd-17}AdviceExpressions" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="PolicyId" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;attribute name="Version" use="required" type="{urn:oasis:names:tc:xacml:3.0:core:schema:wd-17}VersionType" />
 *       &lt;attribute name="RuleCombiningAlgId" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;attribute name="MaxDelegationDepth" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PolicyType", propOrder = {
    "description",
    "policyIssuer",
    "policyDefaults",
    "target",
    "combinerParametersOrRuleCombinerParametersOrVariableDefinition",
    "obligationExpressions",
    "adviceExpressions"
})
public class Policy {

    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "PolicyIssuer")
    protected PolicyIssuer policyIssuer;
    @XmlElement(name = "PolicyDefaults")
    protected Defaults policyDefaults;
    @XmlElement(name = "Target", required = true)
    protected Target target;
    @XmlElements({
        @XmlElement(name = "Rule", type = Rule.class),
        @XmlElement(name = "CombinerParameters", type = CombinerParameters.class),
        @XmlElement(name = "VariableDefinition", type = VariableDefinition.class),
        @XmlElement(name = "RuleCombinerParameters", type = RuleCombinerParameters.class)
    })
    protected List<Object> combinerParametersOrRuleCombinerParametersOrVariableDefinition;
    @XmlElement(name = "ObligationExpressions")
    protected ObligationExpressions obligationExpressions;
    @XmlElement(name = "AdviceExpressions")
    protected AdviceExpressions adviceExpressions;
    @XmlAttribute(name = "PolicyId", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String policyId;
    @XmlAttribute(name = "Version", required = true)
    protected Version version;
    @XmlAttribute(name = "RuleCombiningAlgId", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String ruleCombiningAlgId;
    @XmlAttribute(name = "MaxDelegationDepth")
    protected BigInteger maxDelegationDepth;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the policyIssuer property.
     * 
     * @return
     *     possible object is
     *     {@link PolicyIssuer }
     *
     */
    public PolicyIssuer getPolicyIssuer() {
        return policyIssuer;
    }

    /**
     * Sets the value of the policyIssuer property.
     *
     * @param value
     *     allowed object is
     *     {@link PolicyIssuer }
     *
     */
    public void setPolicyIssuer(PolicyIssuer value) {
        this.policyIssuer = value;
    }

    /**
     * Gets the value of the policyDefaults property.
     *
     * @return
     *     possible object is
     *     {@link Defaults }
     *
     */
    public Defaults getPolicyDefaults() {
        return policyDefaults;
    }

    /**
     * Sets the value of the policyDefaults property.
     *
     * @param value
     *     allowed object is
     *     {@link Defaults }
     *
     */
    public void setPolicyDefaults(Defaults value) {
        this.policyDefaults = value;
    }

    /**
     * Gets the value of the target property.
     *
     * @return
     *     possible object is
     *     {@link Target }
     *
     */
    public Target getTarget() {
        return target;
    }

    /**
     * Sets the value of the target property.
     *
     * @param value
     *     allowed object is
     *     {@link Target }
     *
     */
    public void setTarget(Target value) {
        this.target = value;
    }

    /**
     * Gets the value of the combinerParametersOrRuleCombinerParametersOrVariableDefinition property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the combinerParametersOrRuleCombinerParametersOrVariableDefinition property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCombinerParametersOrRuleCombinerParametersOrVariableDefinition().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Rule }
     * {@link CombinerParameters }
     * {@link VariableDefinition }
     * {@link RuleCombinerParameters }
     *
     *
     */
    public List<Object> getCombinerParametersOrRuleCombinerParametersOrVariableDefinition() {
        if (combinerParametersOrRuleCombinerParametersOrVariableDefinition == null) {
            combinerParametersOrRuleCombinerParametersOrVariableDefinition = new ArrayList<Object>();
        }
        return this.combinerParametersOrRuleCombinerParametersOrVariableDefinition;
    }

    /**
     * Gets the value of the obligationExpressions property.
     *
     * @return
     *     possible object is
     *     {@link ObligationExpressions }
     *
     */
    public ObligationExpressions getObligationExpressions() {
        return obligationExpressions;
    }

    /**
     * Sets the value of the obligationExpressions property.
     *
     * @param value
     *     allowed object is
     *     {@link ObligationExpressions }
     *
     */
    public void setObligationExpressions(ObligationExpressions value) {
        this.obligationExpressions = value;
    }

    /**
     * Gets the value of the adviceExpressions property.
     *
     * @return
     *     possible object is
     *     {@link AdviceExpressions }
     *
     */
    public AdviceExpressions getAdviceExpressions() {
        return adviceExpressions;
    }

    /**
     * Sets the value of the adviceExpressions property.
     *
     * @param value
     *     allowed object is
     *     {@link AdviceExpressions }
     *
     */
    public void setAdviceExpressions(AdviceExpressions value) {
        this.adviceExpressions = value;
    }

    /**
     * Gets the value of the policyId property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPolicyId() {
        return policyId;
    }

    /**
     * Sets the value of the policyId property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPolicyId(String value) {
        this.policyId = value;
    }

    /**
     * Gets the value of the version property.
     *
     * @return
     *     possible object is
     *     {@link Version }
     *
     */
    public Version getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     *
     * @param value
     *     allowed object is
     *     {@link Version }
     *
     */
    public void setVersion(Version value) {
        this.version = value;
    }

    /**
     * Gets the value of the ruleCombiningAlgId property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getRuleCombiningAlgId() {
        return ruleCombiningAlgId;
    }

    /**
     * Sets the value of the ruleCombiningAlgId property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setRuleCombiningAlgId(String value) {
        this.ruleCombiningAlgId = value;
    }

    /**
     * Gets the value of the maxDelegationDepth property.
     *
     * @return
     *     possible object is
     *     {@link java.math.BigInteger }
     *
     */
    public BigInteger getMaxDelegationDepth() {
        return maxDelegationDepth;
    }

    /**
     * Sets the value of the maxDelegationDepth property.
     *
     * @param value
     *     allowed object is
     *     {@link java.math.BigInteger }
     *     
     */
    public void setMaxDelegationDepth(BigInteger value) {
        this.maxDelegationDepth = value;
    }

}
