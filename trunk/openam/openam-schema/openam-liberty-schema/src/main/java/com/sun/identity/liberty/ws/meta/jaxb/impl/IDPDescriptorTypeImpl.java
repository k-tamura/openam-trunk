//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:33:54 AM PDT 
//


package com.sun.identity.liberty.ws.meta.jaxb.impl;

public class IDPDescriptorTypeImpl
    extends com.sun.identity.liberty.ws.meta.jaxb.impl.ProviderDescriptorTypeImpl
    implements com.sun.identity.liberty.ws.meta.jaxb.IDPDescriptorType, com.sun.xml.bind.JAXBObject, com.sun.identity.federation.jaxb.entityconfig.impl.runtime.UnmarshallableObject, com.sun.identity.federation.jaxb.entityconfig.impl.runtime.XMLSerializable, com.sun.identity.federation.jaxb.entityconfig.impl.runtime.ValidatableObject
{

    protected java.lang.String _SingleSignOnServiceURL;
    protected com.sun.xml.bind.util.ListImpl _SingleSignOnProtocolProfile;
    protected java.lang.String _AuthnServiceURL;
    public final static java.lang.Class version = (com.sun.identity.liberty.ws.meta.jaxb.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (com.sun.identity.liberty.ws.meta.jaxb.IDPDescriptorType.class);
    }

    public java.lang.String getSingleSignOnServiceURL() {
        return _SingleSignOnServiceURL;
    }

    public void setSingleSignOnServiceURL(java.lang.String value) {
        _SingleSignOnServiceURL = value;
    }

    protected com.sun.xml.bind.util.ListImpl _getSingleSignOnProtocolProfile() {
        if (_SingleSignOnProtocolProfile == null) {
            _SingleSignOnProtocolProfile = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
        }
        return _SingleSignOnProtocolProfile;
    }

    public java.util.List getSingleSignOnProtocolProfile() {
        return _getSingleSignOnProtocolProfile();
    }

    public java.lang.String getAuthnServiceURL() {
        return _AuthnServiceURL;
    }

    public void setAuthnServiceURL(java.lang.String value) {
        _AuthnServiceURL = value;
    }

    public com.sun.identity.federation.jaxb.entityconfig.impl.runtime.UnmarshallingEventHandler createUnmarshaller(com.sun.identity.federation.jaxb.entityconfig.impl.runtime.UnmarshallingContext context) {
        return new com.sun.identity.liberty.ws.meta.jaxb.impl.IDPDescriptorTypeImpl.Unmarshaller(context);
    }

    public void serializeBody(com.sun.identity.federation.jaxb.entityconfig.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx2 = 0;
        final int len2 = ((_SingleSignOnProtocolProfile == null)? 0 :_SingleSignOnProtocolProfile.size());
        super.serializeBody(context);
        context.startElement("urn:liberty:metadata:2003-08", "SingleSignOnServiceURL");
        context.endNamespaceDecls();
        context.endAttributes();
        try {
            context.text(((java.lang.String) _SingleSignOnServiceURL), "SingleSignOnServiceURL");
        } catch (java.lang.Exception e) {
            com.sun.identity.federation.jaxb.entityconfig.impl.runtime.Util.handlePrintConversionException(this, e, context);
        }
        context.endElement();
        while (idx2 != len2) {
            context.startElement("urn:liberty:metadata:2003-08", "SingleSignOnProtocolProfile");
            int idx_2 = idx2;
            try {
                idx_2 += 1;
            } catch (java.lang.Exception e) {
                com.sun.identity.federation.jaxb.entityconfig.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
            context.endNamespaceDecls();
            int idx_3 = idx2;
            try {
                idx_3 += 1;
            } catch (java.lang.Exception e) {
                com.sun.identity.federation.jaxb.entityconfig.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
            context.endAttributes();
            try {
                context.text(((java.lang.String) _SingleSignOnProtocolProfile.get(idx2 ++)), "SingleSignOnProtocolProfile");
            } catch (java.lang.Exception e) {
                com.sun.identity.federation.jaxb.entityconfig.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
            context.endElement();
        }
        if (_AuthnServiceURL!= null) {
            context.startElement("urn:liberty:metadata:2003-08", "AuthnServiceURL");
            context.endNamespaceDecls();
            context.endAttributes();
            try {
                context.text(((java.lang.String) _AuthnServiceURL), "AuthnServiceURL");
            } catch (java.lang.Exception e) {
                com.sun.identity.federation.jaxb.entityconfig.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
            context.endElement();
        }
    }

    public void serializeAttributes(com.sun.identity.federation.jaxb.entityconfig.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx2 = 0;
        final int len2 = ((_SingleSignOnProtocolProfile == null)? 0 :_SingleSignOnProtocolProfile.size());
        super.serializeAttributes(context);
        while (idx2 != len2) {
            try {
                idx2 += 1;
            } catch (java.lang.Exception e) {
                com.sun.identity.federation.jaxb.entityconfig.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
        }
    }

    public void serializeURIs(com.sun.identity.federation.jaxb.entityconfig.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx2 = 0;
        final int len2 = ((_SingleSignOnProtocolProfile == null)? 0 :_SingleSignOnProtocolProfile.size());
        super.serializeURIs(context);
        while (idx2 != len2) {
            try {
                idx2 += 1;
            } catch (java.lang.Exception e) {
                com.sun.identity.federation.jaxb.entityconfig.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
        }
    }

    public java.lang.Class getPrimaryInterface() {
        return (com.sun.identity.liberty.ws.meta.jaxb.IDPDescriptorType.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        if (schemaFragment == null) {
            schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
 "\u00ac\u00ed\u0000\u0005sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.su"
+"n.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/gra"
+"mmar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expressi"
+"on\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000b"
+"expandedExpq\u0000~\u0000\u0002xpppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000pp"
+"sq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsq\u0000~"
+"\u0000\u0000ppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000pp"
+"sq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsr\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0001ppsr\u0000 com.sun.msv.grammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001"
+"\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0002xq\u0000"
+"~\u0000\u0003sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psq\u0000~\u0000\u001dq\u0000~\u0000#ps"
+"r\u0000\'com.sun.msv.grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\tname"
+"Classt\u0000\u001fLcom/sun/msv/grammar/NameClass;xr\u0000\u001ecom.sun.msv.gramm"
+"ar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttributesL\u0000\fcont"
+"entModelq\u0000~\u0000\u0002xq\u0000~\u0000\u0003q\u0000~\u0000#p\u0000sq\u0000~\u0000\u001dppsq\u0000~\u0000\u001fq\u0000~\u0000#psr\u0000 com.sun.ms"
+"v.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\tnameClassq\u0000~\u0000"
+"&xq\u0000~\u0000\u0003q\u0000~\u0000#psr\u00002com.sun.msv.grammar.Expression$AnyStringExp"
+"ression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003sq\u0000~\u0000\"\u0001q\u0000~\u0000.sr\u0000 com.sun.msv.grammar."
+"AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000"
+"\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Expression$EpsilonExpressio"
+"n\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003q\u0000~\u0000/q\u0000~\u00004sr\u0000#com.sun.msv.grammar.SimpleNa"
+"meClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNamet\u0000\u0012Ljava/lang/String;L\u0000\fnamesp"
+"aceURIq\u0000~\u00006xq\u0000~\u00001t\u0000:com.sun.identity.liberty.ws.meta.jaxb.Ke"
+"yDescriptorElementt\u0000+http://java.sun.com/jaxb/xjc/dummy-elem"
+"entssq\u0000~\u0000%q\u0000~\u0000#p\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000%pp\u0000sq\u0000~\u0000\u001dppsq\u0000~\u0000\u001fq\u0000~\u0000#psq\u0000~\u0000+"
+"q\u0000~\u0000#pq\u0000~\u0000.q\u0000~\u00002q\u0000~\u00004sq\u0000~\u00005t\u00007com.sun.identity.liberty.ws.me"
+"ta.jaxb.KeyDescriptorTypeq\u0000~\u00009sq\u0000~\u0000\u001dppsq\u0000~\u0000+q\u0000~\u0000#psr\u0000\u001bcom.su"
+"n.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datatyp"
+"e/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0002L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/StringP"
+"air;xq\u0000~\u0000\u0003q\u0000~\u0000#psr\u0000\"com.sun.msv.datatype.xsd.QnameType\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0001\u0002\u0000\u0000xr\u0000*com.sun.msv.datatype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002"
+"\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'co"
+"m.sun.msv.datatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespace"
+"Uriq\u0000~\u00006L\u0000\btypeNameq\u0000~\u00006L\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/datatyp"
+"e/xsd/WhiteSpaceProcessor;xpt\u0000 http://www.w3.org/2001/XMLSch"
+"emat\u0000\u0005QNamesr\u00005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$"
+"Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd.WhiteSpacePr"
+"ocessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Expression$NullS"
+"etExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003q\u0000~\u0000#psr\u0000\u001bcom.sun.msv.util.Stri"
+"ngPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u00006L\u0000\fnamespaceURIq\u0000~\u00006xpq\u0000~\u0000"
+"Oq\u0000~\u0000Nsq\u0000~\u00005t\u0000\u0004typet\u0000)http://www.w3.org/2001/XMLSchema-insta"
+"nceq\u0000~\u00004sq\u0000~\u00005t\u0000\rKeyDescriptort\u0000\u001curn:liberty:metadata:2003-0"
+"8q\u0000~\u00004sq\u0000~\u0000\u001dppsq\u0000~\u0000%q\u0000~\u0000#p\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000Dq\u0000~\u0000#psr\u0000#com.sun.m"
+"sv.datatype.xsd.AnyURIType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000Iq\u0000~\u0000Nt\u0000\u0006anyURIq\u0000~"
+"\u0000Rq\u0000~\u0000Tsq\u0000~\u0000Uq\u0000~\u0000cq\u0000~\u0000Nsq\u0000~\u0000\u001dppsq\u0000~\u0000+q\u0000~\u0000#pq\u0000~\u0000Gq\u0000~\u0000Wq\u0000~\u00004sq"
+"\u0000~\u00005t\u0000\fSoapEndpointq\u0000~\u0000\\q\u0000~\u00004sq\u0000~\u0000\u001dppsq\u0000~\u0000%q\u0000~\u0000#p\u0000sq\u0000~\u0000\u0000ppq\u0000"
+"~\u0000`sq\u0000~\u0000\u001dppsq\u0000~\u0000+q\u0000~\u0000#pq\u0000~\u0000Gq\u0000~\u0000Wq\u0000~\u00004sq\u0000~\u00005t\u0000\u0016SingleLogoutS"
+"erviceURLq\u0000~\u0000\\q\u0000~\u00004sq\u0000~\u0000\u001dppsq\u0000~\u0000%q\u0000~\u0000#p\u0000sq\u0000~\u0000\u0000ppq\u0000~\u0000`sq\u0000~\u0000\u001dp"
+"psq\u0000~\u0000+q\u0000~\u0000#pq\u0000~\u0000Gq\u0000~\u0000Wq\u0000~\u00004sq\u0000~\u00005t\u0000\u001cSingleLogoutServiceRetu"
+"rnURLq\u0000~\u0000\\q\u0000~\u00004sq\u0000~\u0000\u001dppsq\u0000~\u0000%q\u0000~\u0000#p\u0000sq\u0000~\u0000\u0000ppq\u0000~\u0000`sq\u0000~\u0000\u001dppsq\u0000"
+"~\u0000+q\u0000~\u0000#pq\u0000~\u0000Gq\u0000~\u0000Wq\u0000~\u00004sq\u0000~\u00005t\u0000\u001fFederationTerminationServic"
+"eURLq\u0000~\u0000\\q\u0000~\u00004sq\u0000~\u0000\u001dppsq\u0000~\u0000%q\u0000~\u0000#p\u0000sq\u0000~\u0000\u0000ppq\u0000~\u0000`sq\u0000~\u0000\u001dppsq\u0000~"
+"\u0000+q\u0000~\u0000#pq\u0000~\u0000Gq\u0000~\u0000Wq\u0000~\u00004sq\u0000~\u00005t\u0000%FederationTerminationService"
+"ReturnURLq\u0000~\u0000\\q\u0000~\u00004sq\u0000~\u0000\u001dppsq\u0000~\u0000\u001fq\u0000~\u0000#psq\u0000~\u0000%q\u0000~\u0000#p\u0000sq\u0000~\u0000\u0000pp"
+"q\u0000~\u0000`sq\u0000~\u0000\u001dppsq\u0000~\u0000+q\u0000~\u0000#pq\u0000~\u0000Gq\u0000~\u0000Wq\u0000~\u00004sq\u0000~\u00005t\u00000FederationT"
+"erminationNotificationProtocolProfileq\u0000~\u0000\\q\u0000~\u00004sq\u0000~\u0000\u001dppsq\u0000~\u0000"
+"\u001fq\u0000~\u0000#psq\u0000~\u0000%q\u0000~\u0000#p\u0000sq\u0000~\u0000\u0000ppq\u0000~\u0000`sq\u0000~\u0000\u001dppsq\u0000~\u0000+q\u0000~\u0000#pq\u0000~\u0000Gq\u0000"
+"~\u0000Wq\u0000~\u00004sq\u0000~\u00005t\u0000\u001bSingleLogoutProtocolProfileq\u0000~\u0000\\q\u0000~\u00004sq\u0000~\u0000\u001d"
+"ppsq\u0000~\u0000\u001fq\u0000~\u0000#psq\u0000~\u0000%q\u0000~\u0000#p\u0000sq\u0000~\u0000\u0000ppq\u0000~\u0000`sq\u0000~\u0000\u001dppsq\u0000~\u0000+q\u0000~\u0000#p"
+"q\u0000~\u0000Gq\u0000~\u0000Wq\u0000~\u00004sq\u0000~\u00005t\u0000%RegisterNameIdentifierProtocolProfil"
+"eq\u0000~\u0000\\q\u0000~\u00004sq\u0000~\u0000\u001dppsq\u0000~\u0000%q\u0000~\u0000#p\u0000sq\u0000~\u0000\u0000ppq\u0000~\u0000`sq\u0000~\u0000\u001dppsq\u0000~\u0000+q"
+"\u0000~\u0000#pq\u0000~\u0000Gq\u0000~\u0000Wq\u0000~\u00004sq\u0000~\u00005t\u0000 RegisterNameIdentifierServiceUR"
+"Lq\u0000~\u0000\\q\u0000~\u00004sq\u0000~\u0000\u001dppsq\u0000~\u0000%q\u0000~\u0000#p\u0000sq\u0000~\u0000\u0000ppq\u0000~\u0000`sq\u0000~\u0000\u001dppsq\u0000~\u0000+q"
+"\u0000~\u0000#pq\u0000~\u0000Gq\u0000~\u0000Wq\u0000~\u00004sq\u0000~\u00005t\u0000&RegisterNameIdentifierServiceRe"
+"turnURLq\u0000~\u0000\\q\u0000~\u00004sq\u0000~\u0000\u001dppsq\u0000~\u0000\u001fq\u0000~\u0000#psq\u0000~\u0000%q\u0000~\u0000#p\u0000sq\u0000~\u0000\u0000ppsq"
+"\u0000~\u0000%pp\u0000sq\u0000~\u0000\u001dppsq\u0000~\u0000\u001fq\u0000~\u0000#psq\u0000~\u0000+q\u0000~\u0000#pq\u0000~\u0000.q\u0000~\u00002q\u0000~\u00004sq\u0000~\u00005"
+"t\u0000Fcom.sun.identity.liberty.ws.common.jaxb.assertion.Authori"
+"tyBindingTypeq\u0000~\u00009sq\u0000~\u0000\u001dppsq\u0000~\u0000+q\u0000~\u0000#pq\u0000~\u0000Gq\u0000~\u0000Wq\u0000~\u00004sq\u0000~\u00005t"
+"\u0000$NameIdentifierMappingProtocolProfileq\u0000~\u0000\\q\u0000~\u00004sq\u0000~\u0000\u001dppsq\u0000~"
+"\u0000\u001fq\u0000~\u0000#psq\u0000~\u0000%q\u0000~\u0000#p\u0000sq\u0000~\u0000\u0000ppq\u0000~\u0000`sq\u0000~\u0000\u001dppsq\u0000~\u0000+q\u0000~\u0000#pq\u0000~\u0000Gq"
+"\u0000~\u0000Wq\u0000~\u00004sq\u0000~\u00005t\u0000&NameIdentifierMappingEncryptionProfileq\u0000~\u0000"
+"\\q\u0000~\u00004sq\u0000~\u0000\u001dppsq\u0000~\u0000%q\u0000~\u0000#p\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000%pp\u0000sq\u0000~\u0000\u001dppsq\u0000~\u0000\u001fq\u0000"
+"~\u0000#psq\u0000~\u0000+q\u0000~\u0000#pq\u0000~\u0000.q\u0000~\u00002q\u0000~\u00004sq\u0000~\u00005t\u00006com.sun.identity.lib"
+"erty.ws.meta.jaxb.OrganizationTypeq\u0000~\u00009sq\u0000~\u0000\u001dppsq\u0000~\u0000+q\u0000~\u0000#pq"
+"\u0000~\u0000Gq\u0000~\u0000Wq\u0000~\u00004sq\u0000~\u00005t\u0000\fOrganizationq\u0000~\u0000\\q\u0000~\u00004sq\u0000~\u0000\u001dppsq\u0000~\u0000\u001fq"
+"\u0000~\u0000#psq\u0000~\u0000%q\u0000~\u0000#p\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000%pp\u0000sq\u0000~\u0000\u001dppsq\u0000~\u0000\u001fq\u0000~\u0000#psq\u0000~\u0000"
+"+q\u0000~\u0000#pq\u0000~\u0000.q\u0000~\u00002q\u0000~\u00004sq\u0000~\u00005t\u00001com.sun.identity.liberty.ws.m"
+"eta.jaxb.ContactTypeq\u0000~\u00009sq\u0000~\u0000\u001dppsq\u0000~\u0000+q\u0000~\u0000#pq\u0000~\u0000Gq\u0000~\u0000Wq\u0000~\u00004"
+"sq\u0000~\u00005t\u0000\rContactPersonq\u0000~\u0000\\q\u0000~\u00004sq\u0000~\u0000\u001dppsq\u0000~\u0000\u001fq\u0000~\u0000#psq\u0000~\u0000%q\u0000"
+"~\u0000#p\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000%pp\u0000sq\u0000~\u0000\u001dppsq\u0000~\u0000\u001fq\u0000~\u0000#psq\u0000~\u0000+q\u0000~\u0000#pq\u0000~\u0000.q"
+"\u0000~\u00002q\u0000~\u00004sq\u0000~\u00005t\u0000Dcom.sun.identity.liberty.ws.meta.jaxb.Addi"
+"tionalMetadataLocationTypeq\u0000~\u00009sq\u0000~\u0000\u001dppsq\u0000~\u0000+q\u0000~\u0000#pq\u0000~\u0000Gq\u0000~\u0000"
+"Wq\u0000~\u00004sq\u0000~\u00005t\u0000\u0016AdditionalMetaLocationq\u0000~\u0000\\q\u0000~\u00004sq\u0000~\u0000\u001dppsq\u0000~\u0000"
+"\u001dq\u0000~\u0000#psq\u0000~\u0000%q\u0000~\u0000#p\u0000sq\u0000~\u0000\u001dppsq\u0000~\u0000\u001fq\u0000~\u0000#psq\u0000~\u0000+q\u0000~\u0000#pq\u0000~\u0000.q\u0000~"
+"\u00002q\u0000~\u00004sq\u0000~\u00005t\u00006com.sun.identity.liberty.ws.meta.jaxb.Extens"
+"ionElementq\u0000~\u00009sq\u0000~\u0000%q\u0000~\u0000#p\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000%pp\u0000sq\u0000~\u0000\u001dppsq\u0000~\u0000\u001fq"
+"\u0000~\u0000#psq\u0000~\u0000+q\u0000~\u0000#pq\u0000~\u0000.q\u0000~\u00002q\u0000~\u00004sq\u0000~\u00005t\u00003com.sun.identity.li"
+"berty.ws.meta.jaxb.ExtensionTypeq\u0000~\u00009sq\u0000~\u0000\u001dppsq\u0000~\u0000+q\u0000~\u0000#pq\u0000~"
+"\u0000Gq\u0000~\u0000Wq\u0000~\u00004sq\u0000~\u00005t\u0000\tExtensionq\u0000~\u0000\\q\u0000~\u00004sq\u0000~\u0000\u001dppsq\u0000~\u0000\u001dq\u0000~\u0000#p"
+"sq\u0000~\u0000%q\u0000~\u0000#p\u0000sq\u0000~\u0000\u001dppsq\u0000~\u0000\u001fq\u0000~\u0000#psq\u0000~\u0000+q\u0000~\u0000#pq\u0000~\u0000.q\u0000~\u00002q\u0000~\u00004"
+"sq\u0000~\u00005t\u0000?com.sun.identity.liberty.ws.common.jaxb.xmlsig.Sign"
+"atureElementq\u0000~\u00009sq\u0000~\u0000%q\u0000~\u0000#p\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000%pp\u0000sq\u0000~\u0000\u001dppsq\u0000~\u0000"
+"\u001fq\u0000~\u0000#psq\u0000~\u0000+q\u0000~\u0000#pq\u0000~\u0000.q\u0000~\u00002q\u0000~\u00004sq\u0000~\u00005t\u0000<com.sun.identity."
+"liberty.ws.common.jaxb.xmlsig.SignatureTypeq\u0000~\u00009sq\u0000~\u0000\u001dppsq\u0000~"
+"\u0000+q\u0000~\u0000#pq\u0000~\u0000Gq\u0000~\u0000Wq\u0000~\u00004sq\u0000~\u00005t\u0000\tSignaturet\u0000\"http://www.w3.or"
+"g/2000/09/xmldsig#q\u0000~\u00004sq\u0000~\u0000%pp\u0000sq\u0000~\u0000\u0000ppq\u0000~\u0000`sq\u0000~\u0000\u001dppsq\u0000~\u0000+q"
+"\u0000~\u0000#pq\u0000~\u0000Gq\u0000~\u0000Wq\u0000~\u00004sq\u0000~\u00005t\u0000\u0016SingleSignOnServiceURLq\u0000~\u0000\\sq\u0000~"
+"\u0000\u001fppsq\u0000~\u0000%pp\u0000sq\u0000~\u0000\u0000ppq\u0000~\u0000`sq\u0000~\u0000\u001dppsq\u0000~\u0000+q\u0000~\u0000#pq\u0000~\u0000Gq\u0000~\u0000Wq\u0000~\u0000"
+"4sq\u0000~\u00005t\u0000\u001bSingleSignOnProtocolProfileq\u0000~\u0000\\sq\u0000~\u0000\u001dppsq\u0000~\u0000%q\u0000~\u0000"
+"#p\u0000sq\u0000~\u0000\u0000ppq\u0000~\u0000`sq\u0000~\u0000\u001dppsq\u0000~\u0000+q\u0000~\u0000#pq\u0000~\u0000Gq\u0000~\u0000Wq\u0000~\u00004sq\u0000~\u00005t\u0000\u000f"
+"AuthnServiceURLq\u0000~\u0000\\q\u0000~\u00004sq\u0000~\u0000\u001dppsq\u0000~\u0000+q\u0000~\u0000#psq\u0000~\u0000Dppsr\u0000%com"
+".sun.msv.datatype.xsd.DurationType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000Iq\u0000~\u0000Nt\u0000\bd"
+"urationq\u0000~\u0000Rq\u0000~\u0000Tsq\u0000~\u0000Uq\u0000~\u0001,q\u0000~\u0000Nsq\u0000~\u00005t\u0000\rcacheDurationt\u0000\u0000q\u0000"
+"~\u00004sq\u0000~\u0000\u001dppsq\u0000~\u0000+q\u0000~\u0000#psq\u0000~\u0000Dppsr\u0000\u001fcom.sun.msv.datatype.xsd."
+"IDType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000#com.sun.msv.datatype.xsd.NcnameType\u0000\u0000\u0000\u0000"
+"\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\"com.sun.msv.datatype.xsd.TokenType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000#"
+"com.sun.msv.datatype.xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlwaysVal"
+"idxq\u0000~\u0000Iq\u0000~\u0000Nt\u0000\u0002IDq\u0000~\u0000R\u0000q\u0000~\u0000Tsq\u0000~\u0000Uq\u0000~\u00019q\u0000~\u0000Nsq\u0000~\u00005t\u0000\u0002idq\u0000~\u0001"
+"0q\u0000~\u00004sq\u0000~\u0000+ppsq\u0000~\u0000Dppsr\u0000*com.sun.msv.datatype.xsd.DatatypeF"
+"actory$1\u00a1\u00f3\u000b\u00e3`rj\u000e\u0002\u0000\u0000xr\u0000\u001ecom.sun.msv.datatype.xsd.Proxy\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0001\u0002\u0000\u0001L\u0000\bbaseTypet\u0000)Lcom/sun/msv/datatype/xsd/XSDatatypeImpl;x"
+"q\u0000~\u0000Kq\u0000~\u0000Nt\u0000\bNMTOKENSq\u0000~\u0000Rsr\u0000\'com.sun.msv.datatype.xsd.MinLe"
+"ngthFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001I\u0000\tminLengthxr\u00009com.sun.msv.datatype.xsd"
+".DataTypeWithValueConstraintFacet\"\u00a7Ro\u00ca\u00c7\u008aT\u0002\u0000\u0000xr\u0000*com.sun.msv."
+"datatype.xsd.DataTypeWithFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0005Z\u0000\fisFacetFixedZ\u0000\u0012n"
+"eedValueCheckFlagL\u0000\bbaseTypeq\u0000~\u0001AL\u0000\fconcreteTypet\u0000\'Lcom/sun/"
+"msv/datatype/xsd/ConcreteType;L\u0000\tfacetNameq\u0000~\u00006xq\u0000~\u0000Kppq\u0000~\u0000R"
+"\u0000\u0000sr\u0000!com.sun.msv.datatype.xsd.ListType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bitemTyp"
+"eq\u0000~\u0001Axq\u0000~\u0000Jppq\u0000~\u0000Rsr\u0000$com.sun.msv.datatype.xsd.NmtokenType\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u00016q\u0000~\u0000Nt\u0000\u0007NMTOKENq\u0000~\u0000R\u0000q\u0000~\u0001Jt\u0000\tminLength\u0000\u0000\u0000\u0001q\u0000"
+"~\u0000Tpsq\u0000~\u00005t\u0000\u001aprotocolSupportEnumerationq\u0000~\u00010sq\u0000~\u0000\u001dppsq\u0000~\u0000+q\u0000"
+"~\u0000#psq\u0000~\u0000Dppsr\u0000%com.sun.msv.datatype.xsd.DateTimeType\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0001\u0002\u0000\u0000xr\u0000)com.sun.msv.datatype.xsd.DateTimeBaseType\u0014W\u001a@3\u00a5\u00b4\u00e5\u0002\u0000\u0000"
+"xq\u0000~\u0000Iq\u0000~\u0000Nt\u0000\bdateTimeq\u0000~\u0000Rq\u0000~\u0000Tsq\u0000~\u0000Uq\u0000~\u0001Wq\u0000~\u0000Nsq\u0000~\u00005t\u0000\nval"
+"idUntilq\u0000~\u00010q\u0000~\u00004sr\u0000\"com.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/ExpressionPool$Clos"
+"edHash;xpsr\u0000-com.sun.msv.grammar.ExpressionPool$ClosedHash\u00d7j"
+"\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$Lcom/sun/msv/gr"
+"ammar/ExpressionPool;xp\u0000\u0000\u0000x\u0001pq\u0000~\u00011q\u0000~\u0000\u0018q\u0000~\u0000\u0007q\u0000~\u0000!q\u0000~\u0001\u0019q\u0000~\u0000\u00baq"
+"\u0000~\u0000\u0086q\u0000~\u0000\u008eq\u0000~\u0000\u0096q\u0000~\u0001\u0007q\u0000~\u0000\u00f3q\u0000~\u0000\u00dfq\u0000~\u0000\u00d1q\u0000~\u0000\u00c3q\u0000~\u0000;q\u0000~\u0000\u00aeq\u0000~\u0000\rq\u0000~\u0000\u00feq"
+"\u0000~\u0000\u00eaq\u0000~\u0000\u001cq\u0000~\u0000\u000eq\u0000~\u0000\u000fq\u0000~\u0000\fq\u0000~\u0000\u0011q\u0000~\u0000\u0013q\u0000~\u0000\tq\u0000~\u0000\u001eq\u0000~\u0001Qq\u0000~\u0000\u0005q\u0000~\u0000\u00ffq"
+"\u0000~\u0000\u00ebq\u0000~\u0000$q\u0000~\u0001\'q\u0000~\u0000\u00dcq\u0000~\u0000\u00ceq\u0000~\u0000\u00abq\u0000~\u0000\u0014q\u0000~\u0001\tq\u0000~\u0001\u0001q\u0000~\u0000\u00f5q\u0000~\u0000\u00edq\u0000~\u0000\u00e1q"
+"\u0000~\u0000\u00d3q\u0000~\u0000\u00c5q\u0000~\u0000)q\u0000~\u0000=q\u0000~\u0000\u00b0q\u0000~\u0000\u00c1q\u0000~\u0000\nq\u0000~\u0000\u0016q\u0000~\u0000\u000bq\u0000~\u0000\u0015q\u0000~\u0000\u0006q\u0000~\u0001\nq"
+"\u0000~\u0001\u0002q\u0000~\u0000\u00f6q\u0000~\u0000\u00eeq\u0000~\u0000\u00e2q\u0000~\u0000\u00d4q\u0000~\u0000\u00c6q\u0000~\u0000*q\u0000~\u0000>q\u0000~\u0000\u00b1q\u0000~\u0001\"q\u0000~\u0001\u001bq\u0000~\u0001\u0014q"
+"\u0000~\u0000\u00bcq\u0000~\u0000_q\u0000~\u0000kq\u0000~\u0000rq\u0000~\u0000yq\u0000~\u0000\u0080q\u0000~\u0000\u0088q\u0000~\u0000\u0090q\u0000~\u0000\u0098q\u0000~\u0000\u009fq\u0000~\u0000\u00a6q\u0000~\u0001#q"
+"\u0000~\u0001\u001cq\u0000~\u0001\u0015q\u0000~\u0001\u000eq\u0000~\u0000\u00faq\u0000~\u0000\u00e6q\u0000~\u0000\u00d8q\u0000~\u0000\u00caq\u0000~\u0000\u00bdq\u0000~\u0000Bq\u0000~\u0000eq\u0000~\u0000lq\u0000~\u0000sq"
+"\u0000~\u0000zq\u0000~\u0000\u0081q\u0000~\u0000\u0089q\u0000~\u0000\u0017q\u0000~\u0000\u0091q\u0000~\u0000\u0099q\u0000~\u0000\u00a0q\u0000~\u0000\u00a7q\u0000~\u0000\u00b5q\u0000~\u0000\u0019q\u0000~\u0000\u001bq\u0000~\u0000\u00b9q"
+"\u0000~\u0000\u0085q\u0000~\u0000\u008dq\u0000~\u0000\u0095q\u0000~\u0000\u001aq\u0000~\u0000\u0010q\u0000~\u0000\bq\u0000~\u0001 q\u0000~\u0000]q\u0000~\u0000iq\u0000~\u0000pq\u0000~\u0000wq\u0000~\u0000\u00ddq"
+"\u0000~\u0000\u00cfq\u0000~\u0000\u00acq\u0000~\u0000\u0012q\u0000~\u0000~q\u0000~\u0000\u009dq\u0000~\u0000\u00a4x"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public class Unmarshaller
        extends com.sun.identity.federation.jaxb.entityconfig.impl.runtime.AbstractUnmarshallingEventHandlerImpl
    {


        public Unmarshaller(com.sun.identity.federation.jaxb.entityconfig.impl.runtime.UnmarshallingContext context) {
            super(context, "-----------");
        }

        protected Unmarshaller(com.sun.identity.federation.jaxb.entityconfig.impl.runtime.UnmarshallingContext context, int startState) {
            this(context);
            state = startState;
        }

        public java.lang.Object owner() {
            return com.sun.identity.liberty.ws.meta.jaxb.impl.IDPDescriptorTypeImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  4 :
                        if (("SingleSignOnProtocolProfile" == ___local)&&("urn:liberty:metadata:2003-08" == ___uri)) {
                            context.pushAttributes(__atts, true);
                            state = 5;
                            return ;
                        }
                        break;
                    case  7 :
                        if (("SingleSignOnProtocolProfile" == ___local)&&("urn:liberty:metadata:2003-08" == ___uri)) {
                            context.pushAttributes(__atts, true);
                            state = 5;
                            return ;
                        }
                        if (("AuthnServiceURL" == ___local)&&("urn:liberty:metadata:2003-08" == ___uri)) {
                            context.pushAttributes(__atts, true);
                            state = 8;
                            return ;
                        }
                        state = 10;
                        continue outer;
                    case  0 :
                        attIdx = context.getAttribute("", "cacheDuration");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        attIdx = context.getAttribute("", "id");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        attIdx = context.getAttribute("", "protocolSupportEnumeration");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        break;
                    case  1 :
                        if (("SingleSignOnServiceURL" == ___local)&&("urn:liberty:metadata:2003-08" == ___uri)) {
                            context.pushAttributes(__atts, true);
                            state = 2;
                            return ;
                        }
                        break;
                    case  10 :
                        revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
                }
                super.enterElement(___uri, ___local, ___qname, __atts);
                break;
            }
        }

        public void leaveElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  3 :
                        if (("SingleSignOnServiceURL" == ___local)&&("urn:liberty:metadata:2003-08" == ___uri)) {
                            context.popAttributes();
                            state = 4;
                            return ;
                        }
                        break;
                    case  7 :
                        state = 10;
                        continue outer;
                    case  0 :
                        attIdx = context.getAttribute("", "cacheDuration");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = context.getAttribute("", "id");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = context.getAttribute("", "protocolSupportEnumeration");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        break;
                    case  6 :
                        if (("SingleSignOnProtocolProfile" == ___local)&&("urn:liberty:metadata:2003-08" == ___uri)) {
                            context.popAttributes();
                            state = 7;
                            return ;
                        }
                        break;
                    case  9 :
                        if (("AuthnServiceURL" == ___local)&&("urn:liberty:metadata:2003-08" == ___uri)) {
                            context.popAttributes();
                            state = 10;
                            return ;
                        }
                        break;
                    case  10 :
                        revertToParentFromLeaveElement(___uri, ___local, ___qname);
                        return ;
                }
                super.leaveElement(___uri, ___local, ___qname);
                break;
            }
        }

        public void enterAttribute(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  7 :
                        state = 10;
                        continue outer;
                    case  0 :
                        if (("cacheDuration" == ___local)&&("" == ___uri)) {
                            spawnHandlerFromEnterAttribute((((com.sun.identity.liberty.ws.meta.jaxb.impl.ProviderDescriptorTypeImpl)com.sun.identity.liberty.ws.meta.jaxb.impl.IDPDescriptorTypeImpl.this).new Unmarshaller(context)), 1, ___uri, ___local, ___qname);
                            return ;
                        }
                        if (("id" == ___local)&&("" == ___uri)) {
                            spawnHandlerFromEnterAttribute((((com.sun.identity.liberty.ws.meta.jaxb.impl.ProviderDescriptorTypeImpl)com.sun.identity.liberty.ws.meta.jaxb.impl.IDPDescriptorTypeImpl.this).new Unmarshaller(context)), 1, ___uri, ___local, ___qname);
                            return ;
                        }
                        if (("protocolSupportEnumeration" == ___local)&&("" == ___uri)) {
                            spawnHandlerFromEnterAttribute((((com.sun.identity.liberty.ws.meta.jaxb.impl.ProviderDescriptorTypeImpl)com.sun.identity.liberty.ws.meta.jaxb.impl.IDPDescriptorTypeImpl.this).new Unmarshaller(context)), 1, ___uri, ___local, ___qname);
                            return ;
                        }
                        break;
                    case  10 :
                        revertToParentFromEnterAttribute(___uri, ___local, ___qname);
                        return ;
                }
                super.enterAttribute(___uri, ___local, ___qname);
                break;
            }
        }

        public void leaveAttribute(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  7 :
                        state = 10;
                        continue outer;
                    case  0 :
                        attIdx = context.getAttribute("", "cacheDuration");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = context.getAttribute("", "id");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = context.getAttribute("", "protocolSupportEnumeration");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        break;
                    case  10 :
                        revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
                        return ;
                }
                super.leaveAttribute(___uri, ___local, ___qname);
                break;
            }
        }

        public void handleText(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                try {
                    switch (state) {
                        case  2 :
                            state = 3;
                            eatText1(value);
                            return ;
                        case  7 :
                            state = 10;
                            continue outer;
                        case  0 :
                            attIdx = context.getAttribute("", "cacheDuration");
                            if (attIdx >= 0) {
                                context.consumeAttribute(attIdx);
                                context.getCurrentHandler().text(value);
                                return ;
                            }
                            attIdx = context.getAttribute("", "id");
                            if (attIdx >= 0) {
                                context.consumeAttribute(attIdx);
                                context.getCurrentHandler().text(value);
                                return ;
                            }
                            attIdx = context.getAttribute("", "protocolSupportEnumeration");
                            if (attIdx >= 0) {
                                context.consumeAttribute(attIdx);
                                context.getCurrentHandler().text(value);
                                return ;
                            }
                            break;
                        case  8 :
                            state = 9;
                            eatText2(value);
                            return ;
                        case  5 :
                            state = 6;
                            eatText3(value);
                            return ;
                        case  10 :
                            revertToParentFromText(value);
                            return ;
                    }
                } catch (java.lang.RuntimeException e) {
                    handleUnexpectedTextException(value, e);
                }
                break;
            }
        }

        private void eatText1(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _SingleSignOnServiceURL = com.sun.xml.bind.WhiteSpaceProcessor.collapse(value);
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

        private void eatText2(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _AuthnServiceURL = com.sun.xml.bind.WhiteSpaceProcessor.collapse(value);
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

        private void eatText3(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _getSingleSignOnProtocolProfile().add(com.sun.xml.bind.WhiteSpaceProcessor.collapse(value));
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

    }

}
