//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:33:54 AM PDT 
//


package com.sun.identity.liberty.ws.authnsvc.jaxb;


/**
 * Java content class for SASLResponse element declaration.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/Users/allan/A-SVN/trunk/opensso/products/federation/library/xsd/liberty/lib-idwsf-authn-svc.xsd line 119)
 * <p>
 * <pre>
 * &lt;element name="SASLResponse">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element ref="{urn:liberty:sa:2004-04}Status"/>
 *           &lt;element ref="{urn:liberty:sa:2004-04}PasswordTransforms" minOccurs="0"/>
 *           &lt;element name="Data" minOccurs="0">
 *             &lt;complexType>
 *               &lt;simpleContent>
 *                 &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>base64Binary">
 *                 &lt;/extension>
 *               &lt;/simpleContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element ref="{urn:liberty:disco:2003-08}ResourceOffering" maxOccurs="unbounded" minOccurs="0"/>
 *           &lt;element name="Credentials" minOccurs="0">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;any/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/sequence>
 *         &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *         &lt;attribute name="serverMechanism" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 * 
 */
public interface SASLResponseElement
    extends javax.xml.bind.Element, com.sun.identity.liberty.ws.authnsvc.jaxb.SASLResponseType
{


}
