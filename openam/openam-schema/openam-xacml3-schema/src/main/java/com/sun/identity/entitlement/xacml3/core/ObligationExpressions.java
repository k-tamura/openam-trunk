/**
 *
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2011-2013 ForgeRock AS. All Rights Reserved
 *
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at
 * http://forgerock.org/license/CDDLv1.0.html
 * See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at http://forgerock.org/license/CDDLv1.0.html
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 */

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-661 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.01.21 at 10:40:04 AM PST 
//


package com.sun.identity.entitlement.xacml3.core;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ObligationExpressionsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ObligationExpressionsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{urn:oasis:names:tc:xacml:3.0:core:schema:wd-17}ObligationExpression" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ObligationExpressionsType", propOrder = {
    "obligationExpression"
})
public class ObligationExpressions implements XACMLRootElement {

    @XmlElement(name = "ObligationExpression", required = true)
    protected List<ObligationExpression> obligationExpression;

    /**
     * Gets the value of the obligationExpression property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the obligationExpression property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getObligationExpression().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ObligationExpression }
     * 
     * 
     */
    public List<ObligationExpression> getObligationExpression() {
        if (obligationExpression == null) {
            obligationExpression = new ArrayList<ObligationExpression>();
        }
        return this.obligationExpression;
    }

    /**
     * Default toXML Method to Marshal Object into XML.
     * @return String - Marshaled Results into XML String.
     */
    public String toXML() {
        StringBuilder stringBuilder = new StringBuilder();



        // Return Marshaled Data.
        return stringBuilder.toString();
    }

}
