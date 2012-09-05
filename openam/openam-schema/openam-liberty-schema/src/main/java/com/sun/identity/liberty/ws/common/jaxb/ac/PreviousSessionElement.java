//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:33:54 AM PDT 
//


package com.sun.identity.liberty.ws.common.jaxb.ac;


/**
 * Indicates that the Principal has been strongly authenticated in a previous session during which  the IdP has set a cookie in the UA.  During the present session the Principal has only been authenticated by the UA returning the cookie to the IdP.
 * 
 * Java content class for PreviousSession element declaration.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/Users/allan/A-SVN/trunk/opensso/products/federation/library/xsd/liberty/lib-arch-authentication-context.xsd line 245)
 * <p>
 * <pre>
 * &lt;element name="PreviousSession">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element ref="{urn:liberty:ac:2003-08}Extension" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/sequence>
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 * 
 */
public interface PreviousSessionElement
    extends javax.xml.bind.Element, com.sun.identity.liberty.ws.common.jaxb.ac.PreviousSessionType
{


}
