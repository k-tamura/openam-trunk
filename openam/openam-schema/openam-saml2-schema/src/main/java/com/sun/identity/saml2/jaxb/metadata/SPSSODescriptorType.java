//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:34:07 AM PDT 
//


package com.sun.identity.saml2.jaxb.metadata;


/**
 * Java content class for SPSSODescriptorType complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/Users/allan/A-SVN/trunk/opensso/products/federation/library/xsd/saml2/saml-schema-metadata-2.0.xsd line 283)
 * <p>
 * <pre>
 * &lt;complexType name="SPSSODescriptorType">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:oasis:names:tc:SAML:2.0:metadata}SSODescriptorType">
 *       &lt;sequence>
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:metadata}AssertionConsumerService" maxOccurs="unbounded"/>
 *         &lt;element ref="{urn:oasis:names:tc:SAML:2.0:metadata}AttributeConsumingService" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="AuthnRequestsSigned" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="WantAssertionsSigned" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface SPSSODescriptorType
    extends com.sun.identity.saml2.jaxb.metadata.SSODescriptorType
{


    /**
     * Gets the value of the authnRequestsSigned property.
     * 
     */
    boolean isAuthnRequestsSigned();

    /**
     * Sets the value of the authnRequestsSigned property.
     * 
     */
    void setAuthnRequestsSigned(boolean value);

    /**
     * Gets the value of the AssertionConsumerService property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the AssertionConsumerService property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAssertionConsumerService().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link com.sun.identity.saml2.jaxb.metadata.AssertionConsumerServiceElement}
     * {@link com.sun.identity.saml2.jaxb.metadata.IndexedEndpointType}
     * 
     */
    java.util.List getAssertionConsumerService();

    /**
     * Gets the value of the AttributeConsumingService property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the AttributeConsumingService property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttributeConsumingService().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link com.sun.identity.saml2.jaxb.metadata.AttributeConsumingServiceType}
     * {@link com.sun.identity.saml2.jaxb.metadata.AttributeConsumingServiceElement}
     * 
     */
    java.util.List getAttributeConsumingService();

    /**
     * Gets the value of the wantAssertionsSigned property.
     * 
     */
    boolean isWantAssertionsSigned();

    /**
     * Sets the value of the wantAssertionsSigned property.
     * 
     */
    void setWantAssertionsSigned(boolean value);

}
