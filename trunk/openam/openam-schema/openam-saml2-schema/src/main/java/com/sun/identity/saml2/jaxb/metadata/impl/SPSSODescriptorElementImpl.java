//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:34:07 AM PDT 
//


package com.sun.identity.saml2.jaxb.metadata.impl;

public class SPSSODescriptorElementImpl
    extends com.sun.identity.saml2.jaxb.metadata.impl.SPSSODescriptorTypeImpl
    implements com.sun.identity.saml2.jaxb.metadata.SPSSODescriptorElement, com.sun.xml.bind.RIElement, com.sun.xml.bind.JAXBObject, com.sun.identity.saml2.jaxb.assertion.impl.runtime.UnmarshallableObject, com.sun.identity.saml2.jaxb.assertion.impl.runtime.XMLSerializable, com.sun.identity.saml2.jaxb.assertion.impl.runtime.ValidatableObject
{

    public final static java.lang.Class version = (com.sun.identity.saml2.jaxb.metadata.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (com.sun.identity.saml2.jaxb.metadata.SPSSODescriptorElement.class);
    }

    public java.lang.String ____jaxb_ri____getNamespaceURI() {
        return "urn:oasis:names:tc:SAML:2.0:metadata";
    }

    public java.lang.String ____jaxb_ri____getLocalName() {
        return "SPSSODescriptor";
    }

    public com.sun.identity.saml2.jaxb.assertion.impl.runtime.UnmarshallingEventHandler createUnmarshaller(com.sun.identity.saml2.jaxb.assertion.impl.runtime.UnmarshallingContext context) {
        return new com.sun.identity.saml2.jaxb.metadata.impl.SPSSODescriptorElementImpl.Unmarshaller(context);
    }

    public void serializeBody(com.sun.identity.saml2.jaxb.assertion.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        context.startElement("urn:oasis:names:tc:SAML:2.0:metadata", "SPSSODescriptor");
        super.serializeURIs(context);
        context.endNamespaceDecls();
        super.serializeAttributes(context);
        context.endAttributes();
        super.serializeBody(context);
        context.endElement();
    }

    public void serializeAttributes(com.sun.identity.saml2.jaxb.assertion.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
    }

    public void serializeURIs(com.sun.identity.saml2.jaxb.assertion.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
    }

    public java.lang.Class getPrimaryInterface() {
        return (com.sun.identity.saml2.jaxb.metadata.SPSSODescriptorElement.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        if (schemaFragment == null) {
            schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
 "\u00ac\u00ed\u0000\u0005sr\u0000\'com.sun.msv.grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000"
+"\tnameClasst\u0000\u001fLcom/sun/msv/grammar/NameClass;xr\u0000\u001ecom.sun.msv."
+"grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttributesL\u0000"
+"\fcontentModelt\u0000 Lcom/sun/msv/grammar/Expression;xr\u0000\u001ecom.sun."
+"msv.grammar.Expression\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Lj"
+"ava/lang/Boolean;L\u0000\u000bexpandedExpq\u0000~\u0000\u0003xppp\u0000sr\u0000\u001fcom.sun.msv.gra"
+"mmar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.BinaryExp"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1q\u0000~\u0000\u0003L\u0000\u0004exp2q\u0000~\u0000\u0003xq\u0000~\u0000\u0004ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007pps"
+"q\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000"
+"\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007pps"
+"r\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\bppsq\u0000~\u0000\u001bsr\u0000"
+"\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psq\u0000~\u0000\u0000q\u0000~\u0000\u001fp\u0000sq\u0000~\u0000\u001b"
+"ppsr\u0000 com.sun.msv.grammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun"
+".msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0003xq\u0000~\u0000\u0004q\u0000~\u0000\u001fpsr\u0000 c"
+"om.sun.msv.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0003L\u0000\tname"
+"Classq\u0000~\u0000\u0001xq\u0000~\u0000\u0004q\u0000~\u0000\u001fpsr\u00002com.sun.msv.grammar.Expression$Any"
+"StringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004sq\u0000~\u0000\u001e\u0001q\u0000~\u0000(sr\u0000 com.sun.msv"
+".grammar.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.Name"
+"Class\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Expression$Epsilon"
+"Expression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004q\u0000~\u0000)q\u0000~\u0000.sr\u0000#com.sun.msv.grammar"
+".SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNamet\u0000\u0012Ljava/lang/String;"
+"L\u0000\fnamespaceURIq\u0000~\u00000xq\u0000~\u0000+t\u00003com.sun.identity.saml2.jaxb.xml"
+"sig.SignatureElementt\u0000+http://java.sun.com/jaxb/xjc/dummy-el"
+"ementssq\u0000~\u0000\u0000q\u0000~\u0000\u001fp\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u001bppsq\u0000~\u0000\"q\u0000~\u0000\u001fpsq\u0000~"
+"\u0000%q\u0000~\u0000\u001fpq\u0000~\u0000(q\u0000~\u0000,q\u0000~\u0000.sq\u0000~\u0000/t\u00000com.sun.identity.saml2.jaxb."
+"xmlsig.SignatureTypeq\u0000~\u00003sq\u0000~\u0000\u001bppsq\u0000~\u0000%q\u0000~\u0000\u001fpsr\u0000\u001bcom.sun.msv"
+".grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datatype/Dat"
+"atype;L\u0000\u0006exceptq\u0000~\u0000\u0003L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/StringPair;x"
+"q\u0000~\u0000\u0004ppsr\u0000\"com.sun.msv.datatype.xsd.QnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000*"
+"com.sun.msv.datatype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com"
+".sun.msv.datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv"
+".datatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUriq\u0000~\u00000L"
+"\u0000\btypeNameq\u0000~\u00000L\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/datatype/xsd/Whi"
+"teSpaceProcessor;xpt\u0000 http://www.w3.org/2001/XMLSchemat\u0000\u0005QNa"
+"mesr\u00005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Expression$NullSetExpress"
+"ion\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004q\u0000~\u0000\u001fpsr\u0000\u001bcom.sun.msv.util.StringPair\u00d0t\u001e"
+"jB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u00000L\u0000\fnamespaceURIq\u0000~\u00000xpq\u0000~\u0000Iq\u0000~\u0000Hsq\u0000"
+"~\u0000/t\u0000\u0004typet\u0000)http://www.w3.org/2001/XMLSchema-instanceq\u0000~\u0000.s"
+"q\u0000~\u0000/t\u0000\tSignaturet\u0000\"http://www.w3.org/2000/09/xmldsig#q\u0000~\u0000.s"
+"q\u0000~\u0000\u001bppsq\u0000~\u0000\u001bq\u0000~\u0000\u001fpsq\u0000~\u0000\u0000q\u0000~\u0000\u001fp\u0000sq\u0000~\u0000\u001bppsq\u0000~\u0000\"q\u0000~\u0000\u001fpsq\u0000~\u0000%q\u0000"
+"~\u0000\u001fpq\u0000~\u0000(q\u0000~\u0000,q\u0000~\u0000.sq\u0000~\u0000/t\u00006com.sun.identity.saml2.jaxb.meta"
+"data.ExtensionsElementq\u0000~\u00003sq\u0000~\u0000\u0000q\u0000~\u0000\u001fp\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000"
+"~\u0000\u001bppsq\u0000~\u0000\"q\u0000~\u0000\u001fpsq\u0000~\u0000%q\u0000~\u0000\u001fpq\u0000~\u0000(q\u0000~\u0000,q\u0000~\u0000.sq\u0000~\u0000/t\u00003com.sun"
+".identity.saml2.jaxb.metadata.ExtensionsTypeq\u0000~\u00003sq\u0000~\u0000\u001bppsq\u0000"
+"~\u0000%q\u0000~\u0000\u001fpq\u0000~\u0000Aq\u0000~\u0000Qq\u0000~\u0000.sq\u0000~\u0000/t\u0000\nExtensionst\u0000$urn:oasis:name"
+"s:tc:SAML:2.0:metadataq\u0000~\u0000.sq\u0000~\u0000\u001bppsq\u0000~\u0000\"q\u0000~\u0000\u001fpsq\u0000~\u0000\u001bq\u0000~\u0000\u001fps"
+"q\u0000~\u0000\u0000q\u0000~\u0000\u001fp\u0000sq\u0000~\u0000\u001bppsq\u0000~\u0000\"q\u0000~\u0000\u001fpsq\u0000~\u0000%q\u0000~\u0000\u001fpq\u0000~\u0000(q\u0000~\u0000,q\u0000~\u0000.s"
+"q\u0000~\u0000/t\u00009com.sun.identity.saml2.jaxb.metadata.KeyDescriptorEl"
+"ementq\u0000~\u00003sq\u0000~\u0000\u0000q\u0000~\u0000\u001fp\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u001bppsq\u0000~\u0000\"q\u0000~\u0000\u001fp"
+"sq\u0000~\u0000%q\u0000~\u0000\u001fpq\u0000~\u0000(q\u0000~\u0000,q\u0000~\u0000.sq\u0000~\u0000/t\u00006com.sun.identity.saml2.j"
+"axb.metadata.KeyDescriptorTypeq\u0000~\u00003sq\u0000~\u0000\u001bppsq\u0000~\u0000%q\u0000~\u0000\u001fpq\u0000~\u0000A"
+"q\u0000~\u0000Qq\u0000~\u0000.sq\u0000~\u0000/t\u0000\rKeyDescriptorq\u0000~\u0000kq\u0000~\u0000.sq\u0000~\u0000\u001bppsq\u0000~\u0000\u001bq\u0000~\u0000"
+"\u001fpsq\u0000~\u0000\u0000q\u0000~\u0000\u001fp\u0000sq\u0000~\u0000\u001bppsq\u0000~\u0000\"q\u0000~\u0000\u001fpsq\u0000~\u0000%q\u0000~\u0000\u001fpq\u0000~\u0000(q\u0000~\u0000,q\u0000~"
+"\u0000.sq\u0000~\u0000/t\u00008com.sun.identity.saml2.jaxb.metadata.Organization"
+"Elementq\u0000~\u00003sq\u0000~\u0000\u0000q\u0000~\u0000\u001fp\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u001bppsq\u0000~\u0000\"q\u0000~\u0000"
+"\u001fpsq\u0000~\u0000%q\u0000~\u0000\u001fpq\u0000~\u0000(q\u0000~\u0000,q\u0000~\u0000.sq\u0000~\u0000/t\u00005com.sun.identity.saml2"
+".jaxb.metadata.OrganizationTypeq\u0000~\u00003sq\u0000~\u0000\u001bppsq\u0000~\u0000%q\u0000~\u0000\u001fpq\u0000~\u0000"
+"Aq\u0000~\u0000Qq\u0000~\u0000.sq\u0000~\u0000/t\u0000\fOrganizationq\u0000~\u0000kq\u0000~\u0000.sq\u0000~\u0000\u001bppsq\u0000~\u0000\"q\u0000~\u0000"
+"\u001fpsq\u0000~\u0000\u001bq\u0000~\u0000\u001fpsq\u0000~\u0000\u0000q\u0000~\u0000\u001fp\u0000sq\u0000~\u0000\u001bppsq\u0000~\u0000\"q\u0000~\u0000\u001fpsq\u0000~\u0000%q\u0000~\u0000\u001fpq"
+"\u0000~\u0000(q\u0000~\u0000,q\u0000~\u0000.sq\u0000~\u0000/t\u00009com.sun.identity.saml2.jaxb.metadata."
+"ContactPersonElementq\u0000~\u00003sq\u0000~\u0000\u0000q\u0000~\u0000\u001fp\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000"
+"\u001bppsq\u0000~\u0000\"q\u0000~\u0000\u001fpsq\u0000~\u0000%q\u0000~\u0000\u001fpq\u0000~\u0000(q\u0000~\u0000,q\u0000~\u0000.sq\u0000~\u0000/t\u00000com.sun.i"
+"dentity.saml2.jaxb.metadata.ContactTypeq\u0000~\u00003sq\u0000~\u0000\u001bppsq\u0000~\u0000%q\u0000"
+"~\u0000\u001fpq\u0000~\u0000Aq\u0000~\u0000Qq\u0000~\u0000.sq\u0000~\u0000/t\u0000\rContactPersonq\u0000~\u0000kq\u0000~\u0000.sq\u0000~\u0000\u001bpps"
+"q\u0000~\u0000\"q\u0000~\u0000\u001fpsq\u0000~\u0000\u001bq\u0000~\u0000\u001fpsq\u0000~\u0000\u0000q\u0000~\u0000\u001fp\u0000sq\u0000~\u0000\u001bppsq\u0000~\u0000\"q\u0000~\u0000\u001fpsq\u0000~"
+"\u0000%q\u0000~\u0000\u001fpq\u0000~\u0000(q\u0000~\u0000,q\u0000~\u0000.sq\u0000~\u0000/t\u0000Ecom.sun.identity.saml2.jaxb."
+"metadata.ArtifactResolutionServiceElementq\u0000~\u00003sq\u0000~\u0000\u0000q\u0000~\u0000\u001fp\u0000s"
+"q\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u001bppsq\u0000~\u0000\"q\u0000~\u0000\u001fpsq\u0000~\u0000%q\u0000~\u0000\u001fpq\u0000~\u0000(q\u0000~\u0000,q\u0000"
+"~\u0000.sq\u0000~\u0000/t\u00008com.sun.identity.saml2.jaxb.metadata.IndexedEndp"
+"ointTypeq\u0000~\u00003sq\u0000~\u0000\u001bppsq\u0000~\u0000%q\u0000~\u0000\u001fpq\u0000~\u0000Aq\u0000~\u0000Qq\u0000~\u0000.sq\u0000~\u0000/t\u0000\u0019Art"
+"ifactResolutionServiceq\u0000~\u0000kq\u0000~\u0000.sq\u0000~\u0000\u001bppsq\u0000~\u0000\"q\u0000~\u0000\u001fpsq\u0000~\u0000\u001bq\u0000"
+"~\u0000\u001fpsq\u0000~\u0000\u0000q\u0000~\u0000\u001fp\u0000sq\u0000~\u0000\u001bppsq\u0000~\u0000\"q\u0000~\u0000\u001fpsq\u0000~\u0000%q\u0000~\u0000\u001fpq\u0000~\u0000(q\u0000~\u0000,q"
+"\u0000~\u0000.sq\u0000~\u0000/t\u0000?com.sun.identity.saml2.jaxb.metadata.SingleLogo"
+"utServiceElementq\u0000~\u00003sq\u0000~\u0000\u0000q\u0000~\u0000\u001fp\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u001bpps"
+"q\u0000~\u0000\"q\u0000~\u0000\u001fpsq\u0000~\u0000%q\u0000~\u0000\u001fpq\u0000~\u0000(q\u0000~\u0000,q\u0000~\u0000.sq\u0000~\u0000/t\u00001com.sun.ident"
+"ity.saml2.jaxb.metadata.EndpointTypeq\u0000~\u00003sq\u0000~\u0000\u001bppsq\u0000~\u0000%q\u0000~\u0000\u001f"
+"pq\u0000~\u0000Aq\u0000~\u0000Qq\u0000~\u0000.sq\u0000~\u0000/t\u0000\u0013SingleLogoutServiceq\u0000~\u0000kq\u0000~\u0000.sq\u0000~\u0000\u001b"
+"ppsq\u0000~\u0000\"q\u0000~\u0000\u001fpsq\u0000~\u0000\u001bq\u0000~\u0000\u001fpsq\u0000~\u0000\u0000q\u0000~\u0000\u001fp\u0000sq\u0000~\u0000\u001bppsq\u0000~\u0000\"q\u0000~\u0000\u001fps"
+"q\u0000~\u0000%q\u0000~\u0000\u001fpq\u0000~\u0000(q\u0000~\u0000,q\u0000~\u0000.sq\u0000~\u0000/t\u0000?com.sun.identity.saml2.ja"
+"xb.metadata.ManageNameIDServiceElementq\u0000~\u00003sq\u0000~\u0000\u0000q\u0000~\u0000\u001fp\u0000sq\u0000~"
+"\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u001bppsq\u0000~\u0000\"q\u0000~\u0000\u001fpsq\u0000~\u0000%q\u0000~\u0000\u001fpq\u0000~\u0000(q\u0000~\u0000,q\u0000~\u0000."
+"sq\u0000~\u0000/q\u0000~\u0000\u00cfq\u0000~\u00003sq\u0000~\u0000\u001bppsq\u0000~\u0000%q\u0000~\u0000\u001fpq\u0000~\u0000Aq\u0000~\u0000Qq\u0000~\u0000.sq\u0000~\u0000/t\u0000\u0013"
+"ManageNameIDServiceq\u0000~\u0000kq\u0000~\u0000.sq\u0000~\u0000\u001bppsq\u0000~\u0000\"q\u0000~\u0000\u001fpsq\u0000~\u0000\u0000q\u0000~\u0000\u001f"
+"p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000>q\u0000~\u0000\u001fpsr\u0000#com.sun.msv.datatype.xsd.AnyURITyp"
+"e\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000Cq\u0000~\u0000Ht\u0000\u0006anyURIq\u0000~\u0000Lq\u0000~\u0000Nsq\u0000~\u0000Oq\u0000~\u0000\u00efq\u0000~\u0000Hsq"
+"\u0000~\u0000\u001bppsq\u0000~\u0000%q\u0000~\u0000\u001fpq\u0000~\u0000Aq\u0000~\u0000Qq\u0000~\u0000.sq\u0000~\u0000/t\u0000\fNameIDFormatq\u0000~\u0000kq"
+"\u0000~\u0000.sq\u0000~\u0000\"ppsq\u0000~\u0000\u001bppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u001bppsq\u0000~\u0000\"q\u0000~\u0000\u001fpsq\u0000~\u0000%q\u0000~\u0000\u001f"
+"pq\u0000~\u0000(q\u0000~\u0000,q\u0000~\u0000.sq\u0000~\u0000/t\u0000Dcom.sun.identity.saml2.jaxb.metadat"
+"a.AssertionConsumerServiceElementq\u0000~\u00003sq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000"
+"\u0000pp\u0000sq\u0000~\u0000\u001bppsq\u0000~\u0000\"q\u0000~\u0000\u001fpsq\u0000~\u0000%q\u0000~\u0000\u001fpq\u0000~\u0000(q\u0000~\u0000,q\u0000~\u0000.sq\u0000~\u0000/q\u0000~"
+"\u0000\u00baq\u0000~\u00003sq\u0000~\u0000\u001bppsq\u0000~\u0000%q\u0000~\u0000\u001fpq\u0000~\u0000Aq\u0000~\u0000Qq\u0000~\u0000.sq\u0000~\u0000/t\u0000\u0018Assertion"
+"ConsumerServiceq\u0000~\u0000ksq\u0000~\u0000\u001bppsq\u0000~\u0000\"q\u0000~\u0000\u001fpsq\u0000~\u0000\u001bq\u0000~\u0000\u001fpsq\u0000~\u0000\u0000q\u0000"
+"~\u0000\u001fp\u0000sq\u0000~\u0000\u001bppsq\u0000~\u0000\"q\u0000~\u0000\u001fpsq\u0000~\u0000%q\u0000~\u0000\u001fpq\u0000~\u0000(q\u0000~\u0000,q\u0000~\u0000.sq\u0000~\u0000/t\u0000"
+"Ecom.sun.identity.saml2.jaxb.metadata.AttributeConsumingServ"
+"iceElementq\u0000~\u00003sq\u0000~\u0000\u0000q\u0000~\u0000\u001fp\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u001bppsq\u0000~\u0000\"q"
+"\u0000~\u0000\u001fpsq\u0000~\u0000%q\u0000~\u0000\u001fpq\u0000~\u0000(q\u0000~\u0000,q\u0000~\u0000.sq\u0000~\u0000/t\u0000Bcom.sun.identity.sa"
+"ml2.jaxb.metadata.AttributeConsumingServiceTypeq\u0000~\u00003sq\u0000~\u0000\u001bpp"
+"sq\u0000~\u0000%q\u0000~\u0000\u001fpq\u0000~\u0000Aq\u0000~\u0000Qq\u0000~\u0000.sq\u0000~\u0000/t\u0000\u0019AttributeConsumingServic"
+"eq\u0000~\u0000kq\u0000~\u0000.sq\u0000~\u0000\u001bppsq\u0000~\u0000%q\u0000~\u0000\u001fpsq\u0000~\u0000>ppsr\u0000\u001fcom.sun.msv.datat"
+"ype.xsd.IDType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000#com.sun.msv.datatype.xsd.Ncname"
+"Type\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\"com.sun.msv.datatype.xsd.TokenType\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0001\u0002\u0000\u0000xr\u0000#com.sun.msv.datatype.xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risA"
+"lwaysValidxq\u0000~\u0000Cq\u0000~\u0000Ht\u0000\u0002IDq\u0000~\u0000L\u0000q\u0000~\u0000Nsq\u0000~\u0000Oq\u0000~\u0001%q\u0000~\u0000Hsq\u0000~\u0000/t"
+"\u0000\u0002IDt\u0000\u0000q\u0000~\u0000.sq\u0000~\u0000\u001bppsq\u0000~\u0000%q\u0000~\u0000\u001fpsq\u0000~\u0000>ppsr\u0000%com.sun.msv.data"
+"type.xsd.DurationType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000Cq\u0000~\u0000Ht\u0000\bdurationq\u0000~\u0000Lq"
+"\u0000~\u0000Nsq\u0000~\u0000Oq\u0000~\u0001/q\u0000~\u0000Hsq\u0000~\u0000/t\u0000\rcacheDurationq\u0000~\u0001)q\u0000~\u0000.sq\u0000~\u0000\u001bpp"
+"sq\u0000~\u0000%q\u0000~\u0000\u001fpq\u0000~\u0000\u00ecsq\u0000~\u0000/t\u0000\berrorURLq\u0000~\u0001)q\u0000~\u0000.sq\u0000~\u0000%ppsq\u0000~\u0000>pp"
+"sr\u0000!com.sun.msv.datatype.xsd.ListType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bitemTypet"
+"\u0000)Lcom/sun/msv/datatype/xsd/XSDatatypeImpl;xq\u0000~\u0000Dq\u0000~\u0000kt\u0000\u000eany"
+"URIListTypeq\u0000~\u0000Lq\u0000~\u0000\u00eeq\u0000~\u0000Npsq\u0000~\u0000/t\u0000\u001aprotocolSupportEnumerati"
+"onq\u0000~\u0001)sq\u0000~\u0000\u001bppsq\u0000~\u0000%q\u0000~\u0000\u001fpsq\u0000~\u0000>ppsr\u0000%com.sun.msv.datatype."
+"xsd.DateTimeType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000)com.sun.msv.datatype.xsd.Date"
+"TimeBaseType\u0014W\u001a@3\u00a5\u00b4\u00e5\u0002\u0000\u0000xq\u0000~\u0000Cq\u0000~\u0000Ht\u0000\bdateTimeq\u0000~\u0000Lq\u0000~\u0000Nsq\u0000~\u0000"
+"Oq\u0000~\u0001Eq\u0000~\u0000Hsq\u0000~\u0000/t\u0000\nvalidUntilq\u0000~\u0001)q\u0000~\u0000.sq\u0000~\u0000\u001bppsq\u0000~\u0000%q\u0000~\u0000\u001fp"
+"sq\u0000~\u0000>ppsr\u0000$com.sun.msv.datatype.xsd.BooleanType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000x"
+"q\u0000~\u0000Cq\u0000~\u0000Ht\u0000\u0007booleanq\u0000~\u0000Lq\u0000~\u0000Nsq\u0000~\u0000Oq\u0000~\u0001Nq\u0000~\u0000Hsq\u0000~\u0000/t\u0000\u0013Authn"
+"RequestsSignedq\u0000~\u0001)q\u0000~\u0000.sq\u0000~\u0000\u001bppsq\u0000~\u0000%q\u0000~\u0000\u001fpq\u0000~\u0001Ksq\u0000~\u0000/t\u0000\u0014Wa"
+"ntAssertionsSignedq\u0000~\u0001)q\u0000~\u0000.sq\u0000~\u0000\u001bppsq\u0000~\u0000%q\u0000~\u0000\u001fpq\u0000~\u0000Aq\u0000~\u0000Qq\u0000"
+"~\u0000.sq\u0000~\u0000/t\u0000\u000fSPSSODescriptorq\u0000~\u0000ksr\u0000\"com.sun.msv.grammar.Expr"
+"essionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/Expr"
+"essionPool$ClosedHash;xpsr\u0000-com.sun.msv.grammar.ExpressionPo"
+"ol$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$"
+"Lcom/sun/msv/grammar/ExpressionPool;xp\u0000\u0000\u0000s\u0001pq\u0000~\u0000\u00afq\u0000~\u0000\u00b7q\u0000~\u0000\u00c4q"
+"\u0000~\u0000\u0081q\u0000~\u0000\u00f8q\u0000~\u0000\u00e0q\u0000~\u0000\u00d8q\u0000~\u0000\u00cbq\u0000~\u0000\u00c3q\u0000~\u0000!q\u0000~\u00007q\u0000~\u0000Zq\u0000~\u0000bq\u0000~\u0000pq\u0000~\u0000xq"
+"\u0000~\u0000\u0084q\u0000~\u0000\u008cq\u0000~\u0000\u0099q\u0000~\u0000\u00a1q\u0000~\u0000\u00aeq\u0000~\u0000\u00b6q\u0000~\u0001\u0015q\u0000~\u0001\rq\u0000~\u0001\u0001q\u0000~\u0000\u00f9q\u0000~\u0000\u00e1q\u0000~\u0000\u00d9q"
+"\u0000~\u0000\u0017q\u0000~\u0000\u00ccq\u0000~\u0000$q\u0000~\u00008q\u0000~\u0000[q\u0000~\u0000cq\u0000~\u0000qq\u0000~\u0000yq\u0000~\u0000\u0085q\u0000~\u00013q\u0000~\u0000\u0018q\u0000~\u0000\u00e8q"
+"\u0000~\u0000\rq\u0000~\u0000\u0012q\u0000~\u0001Rq\u0000~\u0000\u0010q\u0000~\u0000\u000fq\u0000~\u0000\u000eq\u0000~\u0001*q\u0000~\u0000\u0019q\u0000~\u0001Iq\u0000~\u0000\u0011q\u0000~\u0000\fq\u0000~\u0000\u00ebq"
+"\u0000~\u0000\nq\u0000~\u0001?q\u0000~\u0000\u00e9q\u0000~\u0000\u001aq\u0000~\u0001\tq\u0000~\u0000\u00f5q\u0000~\u0001\u0012q\u0000~\u0000\u00feq\u0000~\u0000\u00deq\u0000~\u0000\u00c9q\u0000~\u00005q\u0000~\u0000`q"
+"\u0000~\u0001Vq\u0000~\u0001\u0019q\u0000~\u0001\u0004q\u0000~\u0000\u00f1q\u0000~\u0000\u00e4q\u0000~\u0000\u00d0q\u0000~\u0000<q\u0000~\u0000gq\u0000~\u0000}q\u0000~\u0000\u0091q\u0000~\u0000\u00a6q\u0000~\u0000\u00bbq"
+"\u0000~\u0000vq\u0000~\u0000\u008aq\u0000~\u0000\u009fq\u0000~\u0000\u00b4q\u0000~\u0000\u00d5q\u0000~\u0000\u00c0q\u0000~\u0000mq\u0000~\u0000\u0096q\u0000~\u0000\u00abq\u0000~\u0000\u0016q\u0000~\u0001\u001dq\u0000~\u0000\u0013q"
+"\u0000~\u0001\nq\u0000~\u0000\u00f6q\u0000~\u0000\u00d6q\u0000~\u0000\u00c1q\u0000~\u0000\u001dq\u0000~\u0000Xq\u0000~\u0000nq\u0000~\u0000\u0082q\u0000~\u0000\u0097q\u0000~\u0000\u00acq\u0000~\u0000\tq\u0000~\u0000\u0015q"
+"\u0000~\u0001\bq\u0000~\u0000\u00d4q\u0000~\u0000\u00bfq\u0000~\u0000lq\u0000~\u0000\u0095q\u0000~\u0000\u00aaq\u0000~\u0000\u000bq\u0000~\u0000\u0014q\u0000~\u0001\u0014q\u0000~\u0001\fq\u0000~\u0001\u0000q\u0000~\u0000\u001cq"
+"\u0000~\u0000Wq\u0000~\u0000\u008dq\u0000~\u0000\u009aq\u0000~\u0000\u00a2x"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public class Unmarshaller
        extends com.sun.identity.saml2.jaxb.assertion.impl.runtime.AbstractUnmarshallingEventHandlerImpl
    {


        public Unmarshaller(com.sun.identity.saml2.jaxb.assertion.impl.runtime.UnmarshallingContext context) {
            super(context, "----");
        }

        protected Unmarshaller(com.sun.identity.saml2.jaxb.assertion.impl.runtime.UnmarshallingContext context, int startState) {
            this(context);
            state = startState;
        }

        public java.lang.Object owner() {
            return com.sun.identity.saml2.jaxb.metadata.impl.SPSSODescriptorElementImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  3 :
                        revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
                    case  1 :
                        attIdx = context.getAttribute("", "AuthnRequestsSigned");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        attIdx = context.getAttribute("", "WantAssertionsSigned");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        attIdx = context.getAttribute("", "ID");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        attIdx = context.getAttribute("", "cacheDuration");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        attIdx = context.getAttribute("", "errorURL");
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
                    case  0 :
                        if (("SPSSODescriptor" == ___local)&&("urn:oasis:names:tc:SAML:2.0:metadata" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 1;
                            return ;
                        }
                        break;
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
                        revertToParentFromLeaveElement(___uri, ___local, ___qname);
                        return ;
                    case  1 :
                        attIdx = context.getAttribute("", "AuthnRequestsSigned");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = context.getAttribute("", "WantAssertionsSigned");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = context.getAttribute("", "ID");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = context.getAttribute("", "cacheDuration");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = context.getAttribute("", "errorURL");
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
                    case  2 :
                        if (("SPSSODescriptor" == ___local)&&("urn:oasis:names:tc:SAML:2.0:metadata" == ___uri)) {
                            context.popAttributes();
                            state = 3;
                            return ;
                        }
                        break;
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
                    case  3 :
                        revertToParentFromEnterAttribute(___uri, ___local, ___qname);
                        return ;
                    case  1 :
                        if (("AuthnRequestsSigned" == ___local)&&("" == ___uri)) {
                            spawnHandlerFromEnterAttribute((((com.sun.identity.saml2.jaxb.metadata.impl.SPSSODescriptorTypeImpl)com.sun.identity.saml2.jaxb.metadata.impl.SPSSODescriptorElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
                            return ;
                        }
                        if (("WantAssertionsSigned" == ___local)&&("" == ___uri)) {
                            spawnHandlerFromEnterAttribute((((com.sun.identity.saml2.jaxb.metadata.impl.SPSSODescriptorTypeImpl)com.sun.identity.saml2.jaxb.metadata.impl.SPSSODescriptorElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
                            return ;
                        }
                        if (("ID" == ___local)&&("" == ___uri)) {
                            spawnHandlerFromEnterAttribute((((com.sun.identity.saml2.jaxb.metadata.impl.SPSSODescriptorTypeImpl)com.sun.identity.saml2.jaxb.metadata.impl.SPSSODescriptorElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
                            return ;
                        }
                        if (("cacheDuration" == ___local)&&("" == ___uri)) {
                            spawnHandlerFromEnterAttribute((((com.sun.identity.saml2.jaxb.metadata.impl.SPSSODescriptorTypeImpl)com.sun.identity.saml2.jaxb.metadata.impl.SPSSODescriptorElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
                            return ;
                        }
                        if (("errorURL" == ___local)&&("" == ___uri)) {
                            spawnHandlerFromEnterAttribute((((com.sun.identity.saml2.jaxb.metadata.impl.SPSSODescriptorTypeImpl)com.sun.identity.saml2.jaxb.metadata.impl.SPSSODescriptorElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
                            return ;
                        }
                        if (("protocolSupportEnumeration" == ___local)&&("" == ___uri)) {
                            spawnHandlerFromEnterAttribute((((com.sun.identity.saml2.jaxb.metadata.impl.SPSSODescriptorTypeImpl)com.sun.identity.saml2.jaxb.metadata.impl.SPSSODescriptorElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
                            return ;
                        }
                        break;
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
                    case  3 :
                        revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
                        return ;
                    case  1 :
                        attIdx = context.getAttribute("", "AuthnRequestsSigned");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = context.getAttribute("", "WantAssertionsSigned");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = context.getAttribute("", "ID");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = context.getAttribute("", "cacheDuration");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = context.getAttribute("", "errorURL");
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
                        case  3 :
                            revertToParentFromText(value);
                            return ;
                        case  1 :
                            attIdx = context.getAttribute("", "AuthnRequestsSigned");
                            if (attIdx >= 0) {
                                context.consumeAttribute(attIdx);
                                context.getCurrentHandler().text(value);
                                return ;
                            }
                            attIdx = context.getAttribute("", "WantAssertionsSigned");
                            if (attIdx >= 0) {
                                context.consumeAttribute(attIdx);
                                context.getCurrentHandler().text(value);
                                return ;
                            }
                            attIdx = context.getAttribute("", "ID");
                            if (attIdx >= 0) {
                                context.consumeAttribute(attIdx);
                                context.getCurrentHandler().text(value);
                                return ;
                            }
                            attIdx = context.getAttribute("", "cacheDuration");
                            if (attIdx >= 0) {
                                context.consumeAttribute(attIdx);
                                context.getCurrentHandler().text(value);
                                return ;
                            }
                            attIdx = context.getAttribute("", "errorURL");
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
                    }
                } catch (java.lang.RuntimeException e) {
                    handleUnexpectedTextException(value, e);
                }
                break;
            }
        }

    }

}
