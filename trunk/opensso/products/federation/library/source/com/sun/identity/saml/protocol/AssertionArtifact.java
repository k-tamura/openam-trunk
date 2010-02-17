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
 * $Id: AssertionArtifact.java,v 1.2 2008/06/25 05:47:36 qcheng Exp $
 *
 */


package com.sun.identity.saml.protocol;

import com.sun.identity.saml.common.SAMLConstants;
import com.sun.identity.saml.common.SAMLException;
import com.sun.identity.saml.common.SAMLRequesterException;
import com.sun.identity.saml.common.SAMLUtils;
import com.sun.identity.shared.encode.Base64;

/**
 * This class represents the <code>AssertionArtifact</code> element in
 * SAML protocol schema. Current implementation supports TYPE 1 artifact only.
 * Other type of artifact can be supported by extending this class.
 *
 * @supported.all.api
 */
public class AssertionArtifact {

    /**
     * This value specifies the assertion artifact as a string. 
     */	
    protected String artifact;

    protected String assertionHandle = null;
    protected String sourceID = null;
    protected byte[] typeCode = null;

    final static int ARTIFACT_1_LENGTH = 42;
    final static byte ARTIFACT_1_TYPE_CODE_0 = 0;
    final static byte ARTIFACT_1_TYPE_CODE_1 = 1;
    final static byte[] ARTIFACT_1_TYPE_CODE = {0, 1};
            
    /**
     * This is the default constructor of assertion artifact.
     */
    protected AssertionArtifact() {
    }
  
    /**
     * This constructor is used to construct an assertion artifact.      
     * @param theArtifact is the string that is generated by a provider.
     * @exception SAMLException if an error occurs.
     */
    public AssertionArtifact(String theArtifact) throws SAMLException {
	// check if the input is empty
	if ((theArtifact == null) || (theArtifact.length() == 0)) {
	    SAMLUtils.debug.message("AssertionArtifact: empty input.");
	    throw new SAMLRequesterException(
		SAMLUtils.bundle.getString("nullInput"));
	}

	// decode the artifact
	byte raw[] = null;
	try {
	    raw = Base64.decode(theArtifact);
	} catch (Exception e) {
	    if (SAMLUtils.debug.messageEnabled()) {
		SAMLUtils.debug.message("AssertionArtifact: exception decode"
			+ " input:", e);
	    }
	    throw new SAMLRequesterException(
		SAMLUtils.bundle.getString("wrongInput"));
	}

	// check if the length is 42bytes
	if (raw.length != ARTIFACT_1_LENGTH) {
	    if (SAMLUtils.debug.messageEnabled()) {
		SAMLUtils.debug.message("AssertionArtifact: the length is"
			+ " not 42:" + raw.length);
	    }
	    throw new SAMLRequesterException(
		SAMLUtils.bundle.getString("wrongInput"));
	}

	// check if the typecode is correct
	if ((raw[0] != ARTIFACT_1_TYPE_CODE_0) ||
	    (raw[1] != ARTIFACT_1_TYPE_CODE_1)) {
	    SAMLUtils.debug.message("AssertionArtifact: wrong typecode.");
	    throw new SAMLRequesterException(
		SAMLUtils.bundle.getString("wrongInput"));
	}
	typeCode = ARTIFACT_1_TYPE_CODE;
	
	artifact = theArtifact;

	// get the sourceID and assertionHandle
	byte sBytes[] = new byte[SAMLConstants.ID_LENGTH];
	byte aBytes[] = new byte[SAMLConstants.ID_LENGTH];
	System.arraycopy(raw, 2, sBytes, 0, SAMLConstants.ID_LENGTH);
	System.arraycopy(raw, 22, aBytes, 0, SAMLConstants.ID_LENGTH);

	try {
	    sourceID = SAMLUtils.byteArrayToString(sBytes);
	    assertionHandle = SAMLUtils.byteArrayToString(aBytes);
	} catch (Exception e) {
	    SAMLUtils.debug.error("AssertionArtifact: encoding exception: ", e);
	    sourceID = new String(sBytes);
	    assertionHandle = new String(aBytes);
	}
    }

    /**
     * This constructor will be used at the sender side to create a new
     * <code>AssertionArtifact</code>.
     *
     * @param id A string that represents the <code>sourceID</code>.
     * @param handle A string that represents the <code>assertionHandle</code>.
     * @exception SAMLException if wrong input or could not encode the artifact.
     */
    public AssertionArtifact(String id, String handle) throws SAMLException {
	if ((id == null) || (handle == null)) {
	    SAMLUtils.debug.message("AssertionArtifact: null input.");
	    throw new SAMLRequesterException(
		SAMLUtils.bundle.getString("nullInput"));
	}
	
	byte idBytes[] = null;
	byte handleBytes[] = null;
	try {
	    idBytes = SAMLUtils.stringToByteArray(id);
	    handleBytes = SAMLUtils.stringToByteArray(handle);
	} catch (Exception e) {
	    SAMLUtils.debug.error("AssertionArtifact: encoding exception: ",e);
	    idBytes = id.getBytes();
	    handleBytes = handle.getBytes();
	}

	if ((idBytes.length != SAMLConstants.ID_LENGTH) ||
	    (handleBytes.length != SAMLConstants.ID_LENGTH)) {
	    SAMLUtils.debug.message("AssertionArtifact: wrong input length.");
	    throw new SAMLRequesterException(
		SAMLUtils.bundle.getString("wrongInput"));
	}

	sourceID = id;
	assertionHandle = handle;

	byte raw[] = new byte[42];
	raw[0] = ARTIFACT_1_TYPE_CODE_0;
	raw[1] = ARTIFACT_1_TYPE_CODE_1;
	for (int i = 0; i < SAMLConstants.ID_LENGTH; i++) {
	    raw[2+i] = idBytes[i];
	    raw[22+i] = handleBytes[i];
	}
	try {
	    artifact = Base64.encode(raw).trim();
	} catch (Exception e) {
	    if (SAMLUtils.debug.messageEnabled()) {
		SAMLUtils.debug.message("AssertionArtifact: exception encode"
			+ " input:", e);
	    }
	    throw new SAMLRequesterException(
		SAMLUtils.bundle.getString("errorCreateArtifact"));
	}
	typeCode = ARTIFACT_1_TYPE_CODE;
    }

    /**
     * Gets the artifact.
     * @return the string format of the artifact. It's base64 encoded.
     */
    public String getAssertionArtifact() {
	return artifact;
    }

    /**
     * Returns the <code>SourceID</code> of the artifact.
     *
     * @return The <code>SourceID</code> of the artifact.
     */
    public String getSourceID() {
	return sourceID;
    }

    /**
     * Gets the <code>AssertionHandle</code> of the artifact. The result will be
     * decoded.
     *
     * @return The <code>AssertionHandle</code> of the artifact.
     */
    public String getAssertionHandle() {
	return assertionHandle;
    }

    /**
     * Gets the <code>typeCode</code> of the artifact.
     * @return The byte array of the <code>TypeCode</code> for the artifact.
     */
    public byte[] getTypeCode() {
	return typeCode;
    }

    /**
     * Translates the <code>AssertionArtifact</code> to an XML document String
     * based on the SAML schema.
     * @return An XML String representing the <code>AssertionArtifact</code>.
     */
    public String toString() {
	return toString(true, false);
    }

    /**
     * Creates a String representation of the
     * <code>&lt;samlp:AssertionArtifact&gt;</code> element.
     *
     * @param includeNS Determines whether or not the namespace qualifier
     *        is prepended to the Element when converted
     * @param declareNS Determines whether or not the namespace is declared
     *        within the Element.
     * @return A string containing the valid XML for this element
     */   
    public String toString(boolean includeNS, boolean declareNS) {
	StringBuffer xml = new StringBuffer(100);
	String prefix = "";
	if (includeNS) {
	    prefix = SAMLConstants.PROTOCOL_PREFIX;
	}

	String uri = "";
	if (declareNS) {
	    uri = SAMLConstants.PROTOCOL_NAMESPACE_STRING;
	}

	xml.append("<").append(prefix).append("AssertionArtifact").append(uri).
		append(">").append(artifact).append("</").append(prefix).
		append("AssertionArtifact>\n");
	return xml.toString();
    }
}
