/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2006 Sun Microsystems Inc. All Rights Reserved
 *
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at
 * https://opensso.dev.java.net/public/CDDLv1.0.html or
 * opensso/legal/CDDLv1.0.txt
 * See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at opensso/legal/CDDLv1.0.txt.
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * $Id: ResourceImpl.java,v 1.3 2008/06/25 05:48:13 qcheng Exp $
 *
 */

package com.sun.identity.xacml.context.impl;

import com.sun.identity.xacml.common.XACMLSDKUtils;
import com.sun.identity.shared.xml.XMLUtils;
import com.sun.identity.xacml.common.XACMLSDKUtils;
import com.sun.identity.xacml.common.XACMLConstants;
import com.sun.identity.xacml.common.XACMLException;
import com.sun.identity.xacml.context.Attribute;
import com.sun.identity.xacml.context.ContextFactory;
import com.sun.identity.xacml.context.Resource;
import com.sun.identity.xacml.context.ResourceContent;
import java.util.ArrayList;

import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The <code>Resource</code> element specifies information about the
 * resource to which access is requested by listing a 
 * sequence of <code>Attribute</code> elements associated with the
 * resource. it may include <code>ResourceContent</code>
 * <p>
 * <pre>
 * &lt;xs:element name="Resource" type="xacml-context:ResourceType"/>
 *   &lt;xs:complexType name="ResourceType">
 *     &lt;xs:sequence>
 *       &lt;xs:element ref="xacml-context:ResourceContent" minOccurs="0"/>
 *       &lt;xs:element ref="xacml-context:Attribute" minOccurs="0" 
 *          maxOccurs="unbounded"/>
 *    &lt;xs:sequence>
 *  &lt;xs:complexType>
 * </pre>
 *@supported.all.api
 */
public class ResourceImpl implements Resource {
    private List  attributes;
    private Element resourceContent;
    private boolean isMutable = true;

   /** 
    * Default constructor
    */
    public ResourceImpl() {
    }

    /**
     * This constructor is used to build <code>Resource</code> object from a
     * XML string.
     *
     * @param xml A <code>java.lang.String</code> representing
     *        a <code>Resource</code> object
     * @exception XACMLException if it could not process the XML string
     */
    public ResourceImpl(String xml) throws XACMLException {
        Document document = XMLUtils.toDOMDocument(xml, XACMLSDKUtils.debug);
        if (document != null) {
            Element rootElement = document.getDocumentElement();
            processElement(rootElement);
            makeImmutable();
        } else {
            XACMLSDKUtils.debug.error(
                "SubjectImpl.processElement(): invalid XML input");
            throw new XACMLException(
                XACMLSDKUtils.xacmlResourceBundle.getString(
                "errorObtainingElement"));
        }
    }

    /**
     * This constructor is used to build <code>resource</code> object from a
     * block of existing XML that has already been built into a DOM.
     *
     * @param element A <code>org.w3c.dom.Element</code> representing
     *        DOM tree for <code>Resource</code> object
     * @exception XACML2Exception if it could not process the Element
     */
    public ResourceImpl(Element element) throws XACMLException {
        processElement(element);
        makeImmutable();
    }

    private void processElement(Element element) throws XACMLException {
        if (element == null) {
            XACMLSDKUtils.debug.error(
                "ResourceImpl.processElement(): invalid root element");
            throw new XACMLException( 
                XACMLSDKUtils.xacmlResourceBundle.getString(
                "invalid_element"));
        }
        String elemName = element.getLocalName(); 
        if (elemName == null) {
             XACMLSDKUtils.debug.error(
                "ResourceImpl.processElement(): local name missing");
            throw new XACMLException( 
                XACMLSDKUtils.xacmlResourceBundle.getString(
                "missing_local_name"));
        }

        if (!elemName.equals(XACMLConstants.RESOURCE)) {
            XACMLSDKUtils.debug.error(
                "ResourceImpl.processElement(): invalid local name " +
                 elemName);
            throw new XACMLException(
                XACMLSDKUtils.xacmlResourceBundle.getString(
                "invalid_local_name"));
        }

        // starts processing subelements
        NodeList nodes = element.getChildNodes();
        int numOfNodes = nodes.getLength();
        if (numOfNodes > 0) {
            ContextFactory factory = ContextFactory.getInstance();
            for (int i=0; i< numOfNodes; i++) {
                Node child = (Node)nodes.item(i);
                if (child.getNodeType() == Node.ELEMENT_NODE) {
                    String childName = child.getLocalName();
                    // The child nodes should be <Attribute> or 
                    // <ResourceContent>
                    if (childName.equals(XACMLConstants.ATTRIBUTE)) {
                        if (attributes == null) {
                            attributes = new ArrayList();
                        }
                        Attribute attribute = factory.getInstance().
                            createAttribute((Element)child);
                        attributes.add(attribute);
                    } else if (childName.equals(
                            XACMLConstants.RESOURCE_CONTENT)) {
                        resourceContent = (Element)child;
                    }
                }
            }
         } else {
             /* not a schema violation
             XACMLSDKUtils.debug.error(
                "ResourceImpl.processElement(): no attributes or resource "
                +"content");
            throw new XACMLException( 
                XACMLSDKUtils.xacmlResourceBundle.getString(
                "missing_subelements"));
            */
         }
    }

    /**
     * Returns the ResourceConent
     *
     * @return the ResourceContent of the Resource
     */
    public Element getResourceContent() {
        return resourceContent;
    }

    /**
     * Sets the ResourceContent of this Resource
     *
     * @param resourceContent  ResourceContent of this Resource. 
     * ResourceContent  is optional, so could be null.
     *
     * @exception XACMLException if the object is immutable
     * An object is considered <code>immutable</code> if <code>
     * makeImmutable()</code> has been invoked on it. It can
     * be determined by calling <code>isMutable</code> on the object.
     */
    public void setResourceContent(Element resourceContent) 
        throws XACMLException {
          if (!isMutable) {
            throw new XACMLException(
                XACMLSDKUtils.xacmlResourceBundle.getString(
                "objectImmutable"));
        }
        String elemName = resourceContent.getLocalName();
        if (elemName == null 
                || !elemName.equals(XACMLConstants.RESOURCE_CONTENT)) {
            XACMLSDKUtils.debug.error(
                "StatusMessageImpl.processElement():"
                + "local name missing or incorrect");
            throw new XACMLException(
                XACMLSDKUtils.xacmlResourceBundle.getString(
                    "missing_local_name"));
        }
        this.resourceContent = resourceContent;
    }

    /**
     * Returns zero to many <code>Attribute</code> elements of this object
     * If no attributes and present, empty <code>List</code> will be returned.
     * Typically a <code>Resource</code> element will contain an <code>
     * Attribute</code> with an <code>AttributeId</code> of
     * "urn:oasis:names:tc:xacml:1.0:resource:resource-id". Each such
     * <code>Attribute</code> SHALL be an absolute abd fully resolved 
     * representation of the identity of the single resource to which
     * access is requested.
     *
     * @return <code>List</code> containing the <code>Attribute</code> 
     * elements of this object
     */
    public List getAttributes() {
        return attributes;
    }

    /**
     * Sets the <code>Attribute</code> elements of this object
     *
     * @param attributes <code>Attribute</code> elements of this object
     * attributes could be an empty <code>List</code>, if no attributes
     * are present.
     *
     * @exception XACMLException if the object is immutable
     * An object is considered <code>immutable</code> if <code>
     * makeImmutable()</code> has been invoked on it. It can
     * be determined by calling <code>isMutable</code> on the object.
     */
    public void setAttributes(List attributes) throws XACMLException {
         if (!isMutable) {
            throw new XACMLException(
                XACMLSDKUtils.xacmlResourceBundle.getString(
                "objectImmutable"));
        }
        if (attributes != null &&  !attributes.isEmpty()) {
             if (this.attributes == null) {
                 this.attributes = new ArrayList();
            }
            this.attributes.addAll(attributes);
        }
    }

   /**
    * Returns a <code>String</code> representation of this object
    * @param includeNSPrefix Determines whether or not the namespace qualifier
    *        is prepended to the Element when converted
    * @param declareNS Determines whether or not the namespace is declared
    *        within the Element.
    * @return a string representation of this object
    * @exception XACMLException if conversion fails for any reason
     */
    public String toXMLString(boolean includeNSPrefix, boolean declareNS)
            throws XACMLException
    {
        StringBuffer sb = new StringBuffer(2000);
        StringBuffer NS = new StringBuffer(100);
        String appendNS = "";
        if (declareNS) {
            NS.append(XACMLConstants.CONTEXT_NS_DECLARATION)
                    .append(XACMLConstants.SPACE);
            NS.append(XACMLConstants.XSI_NS_URI)
                    .append(XACMLConstants.SPACE)
                    .append(XACMLConstants.CONTEXT_SCHEMA_LOCATION);
        }
        if (includeNSPrefix) {
            appendNS = XACMLConstants.CONTEXT_NS_PREFIX + ":";
        }
        sb.append("<").append(appendNS).append(XACMLConstants.RESOURCE)
                .append(NS);
        sb.append(">");
        int length = 0;
        if (attributes != null) {
            sb.append("\n");
            length = attributes.size();
            for (int i = 0; i < length; i++) {
                Attribute attr = (Attribute)attributes.get(i);
                sb.append(attr.toXMLString(includeNSPrefix, false));
            }
        }
        if (resourceContent != null) {
            sb.append("\n");
            // ignore trailing ":"
            if (includeNSPrefix && (resourceContent.getPrefix() == null)) {
                resourceContent.setPrefix(appendNS.substring(0, appendNS.length()-1));
            }
            if(declareNS) {
                int index = NS.indexOf("=");
                String namespaceName = NS.substring(0, index);
                String namespaceURI = NS.substring(index+1);
                if (resourceContent.getNamespaceURI() == null) {
                    resourceContent.setAttribute(namespaceName, namespaceURI);
                    // does not seem to work to append namespace TODO
                }
            }
            sb.append(XMLUtils.print(resourceContent));
        }
        sb.append("</").append(appendNS).append(XACMLConstants.RESOURCE);
        sb.append(">\n");
        return sb.toString();
    }

   /**
    * Returns a string representation of this object
    *
    * @return a string representation of this object
    * @exception XACMLException if conversion fails for any reason
    */
    public String toXMLString() throws XACMLException {
        return  toXMLString(true, false);
    }

   /*
    * Makes the object immutable
    */
    public void makeImmutable() {// TODO 
    }

   /**
    * Checks if the object is mutable
    *
    * @return <code>true</code> if the object is mutable,
    *         <code>false</code> otherwise
    */
    public boolean isMutable() {
        return isMutable;
    }
}
