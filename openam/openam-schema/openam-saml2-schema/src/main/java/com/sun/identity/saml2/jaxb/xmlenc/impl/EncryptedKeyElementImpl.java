//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:34:07 AM PDT 
//


package com.sun.identity.saml2.jaxb.xmlenc.impl;

public class EncryptedKeyElementImpl
    extends com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedKeyTypeImpl
    implements com.sun.identity.saml2.jaxb.xmlenc.EncryptedKeyElement, com.sun.xml.bind.RIElement, com.sun.xml.bind.JAXBObject, com.sun.identity.saml2.jaxb.assertion.impl.runtime.UnmarshallableObject, com.sun.identity.saml2.jaxb.assertion.impl.runtime.XMLSerializable, com.sun.identity.saml2.jaxb.assertion.impl.runtime.ValidatableObject
{

    public final static java.lang.Class version = (com.sun.identity.saml2.jaxb.xmlenc.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (com.sun.identity.saml2.jaxb.xmlenc.EncryptedKeyElement.class);
    }

    public java.lang.String ____jaxb_ri____getNamespaceURI() {
        return "http://www.w3.org/2001/04/xmlenc#";
    }

    public java.lang.String ____jaxb_ri____getLocalName() {
        return "EncryptedKey";
    }

    public com.sun.identity.saml2.jaxb.assertion.impl.runtime.UnmarshallingEventHandler createUnmarshaller(com.sun.identity.saml2.jaxb.assertion.impl.runtime.UnmarshallingContext context) {
        return new com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedKeyElementImpl.Unmarshaller(context);
    }

    public void serializeBody(com.sun.identity.saml2.jaxb.assertion.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        context.startElement("http://www.w3.org/2001/04/xmlenc#", "EncryptedKey");
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
        return (com.sun.identity.saml2.jaxb.xmlenc.EncryptedKeyElement.class);
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
+"\u0007ppsr\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\bppsq\u0000~\u0000"
+"\u0000sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000"
+"pp\u0000sq\u0000~\u0000\u0014ppsr\u0000 com.sun.msv.grammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr"
+"\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0003xq\u0000~\u0000\u0004q\u0000"
+"~\u0000\u0018psr\u0000 com.sun.msv.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~"
+"\u0000\u0003L\u0000\tnameClassq\u0000~\u0000\u0001xq\u0000~\u0000\u0004q\u0000~\u0000\u0018psr\u00002com.sun.msv.grammar.Expre"
+"ssion$AnyStringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004sq\u0000~\u0000\u0017\u0001q\u0000~\u0000\"sr\u0000 co"
+"m.sun.msv.grammar.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.gra"
+"mmar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Expressio"
+"n$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004q\u0000~\u0000#q\u0000~\u0000(sr\u0000#com.sun.ms"
+"v.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNamet\u0000\u0012Ljava/lan"
+"g/String;L\u0000\fnamespaceURIq\u0000~\u0000*xq\u0000~\u0000%t\u00007com.sun.identity.saml2"
+".jaxb.xmlenc.EncryptionMethodTypet\u0000+http://java.sun.com/jaxb"
+"/xjc/dummy-elementssq\u0000~\u0000\u0014ppsq\u0000~\u0000\u001fq\u0000~\u0000\u0018psr\u0000\u001bcom.sun.msv.gramm"
+"ar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datatype/Datatype;"
+"L\u0000\u0006exceptq\u0000~\u0000\u0003L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/StringPair;xq\u0000~\u0000\u0004p"
+"psr\u0000\"com.sun.msv.datatype.xsd.QnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000*com.su"
+"n.msv.datatype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.m"
+"sv.datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datat"
+"ype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUriq\u0000~\u0000*L\u0000\btype"
+"Nameq\u0000~\u0000*L\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/datatype/xsd/WhiteSpac"
+"eProcessor;xpt\u0000 http://www.w3.org/2001/XMLSchemat\u0000\u0005QNamesr\u00005"
+"com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001"
+"\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Expression$NullSetExpression\u0000\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004q\u0000~\u0000\u0018psr\u0000\u001bcom.sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002"
+"\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000*L\u0000\fnamespaceURIq\u0000~\u0000*xpq\u0000~\u0000;q\u0000~\u0000:sq\u0000~\u0000)t\u0000\u0004"
+"typet\u0000)http://www.w3.org/2001/XMLSchema-instanceq\u0000~\u0000(sq\u0000~\u0000)t"
+"\u0000\u0010EncryptionMethodt\u0000!http://www.w3.org/2001/04/xmlenc#q\u0000~\u0000(s"
+"q\u0000~\u0000\u0014ppsq\u0000~\u0000\u0014q\u0000~\u0000\u0018psq\u0000~\u0000\u0000q\u0000~\u0000\u0018p\u0000sq\u0000~\u0000\u0014ppsq\u0000~\u0000\u001cq\u0000~\u0000\u0018psq\u0000~\u0000\u001fq\u0000"
+"~\u0000\u0018pq\u0000~\u0000\"q\u0000~\u0000&q\u0000~\u0000(sq\u0000~\u0000)t\u00001com.sun.identity.saml2.jaxb.xmls"
+"ig.KeyInfoElementq\u0000~\u0000-sq\u0000~\u0000\u0000q\u0000~\u0000\u0018p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0014pp"
+"sq\u0000~\u0000\u001cq\u0000~\u0000\u0018psq\u0000~\u0000\u001fq\u0000~\u0000\u0018pq\u0000~\u0000\"q\u0000~\u0000&q\u0000~\u0000(sq\u0000~\u0000)t\u0000.com.sun.iden"
+"tity.saml2.jaxb.xmlsig.KeyInfoTypeq\u0000~\u0000-sq\u0000~\u0000\u0014ppsq\u0000~\u0000\u001fq\u0000~\u0000\u0018pq"
+"\u0000~\u00003q\u0000~\u0000Cq\u0000~\u0000(sq\u0000~\u0000)t\u0000\u0007KeyInfot\u0000\"http://www.w3.org/2000/09/x"
+"mldsig#q\u0000~\u0000(sq\u0000~\u0000\u0014ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0014ppsq\u0000~\u0000\u001cq\u0000~\u0000\u0018psq\u0000~\u0000\u001fq\u0000~\u0000\u0018"
+"pq\u0000~\u0000\"q\u0000~\u0000&q\u0000~\u0000(sq\u0000~\u0000)t\u00004com.sun.identity.saml2.jaxb.xmlenc."
+"CipherDataElementq\u0000~\u0000-sq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0014ppsq\u0000~"
+"\u0000\u001cq\u0000~\u0000\u0018psq\u0000~\u0000\u001fq\u0000~\u0000\u0018pq\u0000~\u0000\"q\u0000~\u0000&q\u0000~\u0000(sq\u0000~\u0000)t\u00001com.sun.identity"
+".saml2.jaxb.xmlenc.CipherDataTypeq\u0000~\u0000-sq\u0000~\u0000\u0014ppsq\u0000~\u0000\u001fq\u0000~\u0000\u0018pq\u0000"
+"~\u00003q\u0000~\u0000Cq\u0000~\u0000(sq\u0000~\u0000)t\u0000\nCipherDataq\u0000~\u0000Hsq\u0000~\u0000\u0014ppsq\u0000~\u0000\u0014q\u0000~\u0000\u0018psq\u0000"
+"~\u0000\u0000q\u0000~\u0000\u0018p\u0000sq\u0000~\u0000\u0014ppsq\u0000~\u0000\u001cq\u0000~\u0000\u0018psq\u0000~\u0000\u001fq\u0000~\u0000\u0018pq\u0000~\u0000\"q\u0000~\u0000&q\u0000~\u0000(sq\u0000"
+"~\u0000)t\u0000>com.sun.identity.saml2.jaxb.xmlenc.EncryptionPropertie"
+"sElementq\u0000~\u0000-sq\u0000~\u0000\u0000q\u0000~\u0000\u0018p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0014ppsq\u0000~\u0000\u001cq\u0000~"
+"\u0000\u0018psq\u0000~\u0000\u001fq\u0000~\u0000\u0018pq\u0000~\u0000\"q\u0000~\u0000&q\u0000~\u0000(sq\u0000~\u0000)t\u0000;com.sun.identity.saml"
+"2.jaxb.xmlenc.EncryptionPropertiesTypeq\u0000~\u0000-sq\u0000~\u0000\u0014ppsq\u0000~\u0000\u001fq\u0000~"
+"\u0000\u0018pq\u0000~\u00003q\u0000~\u0000Cq\u0000~\u0000(sq\u0000~\u0000)t\u0000\u0014EncryptionPropertiesq\u0000~\u0000Hq\u0000~\u0000(sq\u0000"
+"~\u0000\u0014ppsq\u0000~\u0000\u0014q\u0000~\u0000\u0018psq\u0000~\u0000\u0000q\u0000~\u0000\u0018p\u0000sq\u0000~\u0000\u0014ppsq\u0000~\u0000\u001cq\u0000~\u0000\u0018psq\u0000~\u0000\u001fq\u0000~\u0000"
+"\u0018pq\u0000~\u0000\"q\u0000~\u0000&q\u0000~\u0000(sq\u0000~\u0000)t\u00007com.sun.identity.saml2.jaxb.xmlenc"
+".ReferenceListElementq\u0000~\u0000-sq\u0000~\u0000\u0000q\u0000~\u0000\u0018p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~"
+"\u0000\u0014ppsq\u0000~\u0000\u001cq\u0000~\u0000\u0018psq\u0000~\u0000\u001fq\u0000~\u0000\u0018pq\u0000~\u0000\"q\u0000~\u0000&q\u0000~\u0000(sq\u0000~\u0000)t\u00004com.sun."
+"identity.saml2.jaxb.xmlenc.ReferenceListTypeq\u0000~\u0000-sq\u0000~\u0000\u0014ppsq\u0000"
+"~\u0000\u001fq\u0000~\u0000\u0018pq\u0000~\u00003q\u0000~\u0000Cq\u0000~\u0000(sq\u0000~\u0000)t\u0000\rReferenceListq\u0000~\u0000Hq\u0000~\u0000(sq\u0000~"
+"\u0000\u0014ppsq\u0000~\u0000\u0000q\u0000~\u0000\u0018p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u00000q\u0000~\u0000\u0018psr\u0000#com.sun.msv.datatyp"
+"e.xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlwaysValidxq\u0000~\u00005q\u0000~\u0000:t\u0000\u0006str"
+"ingsr\u00005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Preserve"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000=\u0001q\u0000~\u0000@sq\u0000~\u0000Aq\u0000~\u0000\u009fq\u0000~\u0000:sq\u0000~\u0000\u0014ppsq\u0000~\u0000\u001fq\u0000~\u0000\u0018pq"
+"\u0000~\u00003q\u0000~\u0000Cq\u0000~\u0000(sq\u0000~\u0000)t\u0000\u000eCarriedKeyNameq\u0000~\u0000Hq\u0000~\u0000(sq\u0000~\u0000\u0014ppsq\u0000~\u0000"
+"\u001fq\u0000~\u0000\u0018psq\u0000~\u00000q\u0000~\u0000\u0018psr\u0000#com.sun.msv.datatype.xsd.AnyURIType\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u00005q\u0000~\u0000:t\u0000\u0006anyURIq\u0000~\u0000>q\u0000~\u0000@sq\u0000~\u0000Aq\u0000~\u0000\u00acq\u0000~\u0000:sq\u0000~\u0000"
+")t\u0000\bEncodingt\u0000\u0000q\u0000~\u0000(sq\u0000~\u0000\u0014ppsq\u0000~\u0000\u001fq\u0000~\u0000\u0018psq\u0000~\u00000ppsr\u0000\u001fcom.sun."
+"msv.datatype.xsd.IDType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000#com.sun.msv.datatype.x"
+"sd.NcnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\"com.sun.msv.datatype.xsd.TokenTy"
+"pe\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u009dq\u0000~\u0000:t\u0000\u0002IDq\u0000~\u0000>\u0000q\u0000~\u0000@sq\u0000~\u0000Aq\u0000~\u0000\u00b8q\u0000~\u0000:sq\u0000~"
+"\u0000)t\u0000\u0002Idq\u0000~\u0000\u00b0q\u0000~\u0000(sq\u0000~\u0000\u0014ppsq\u0000~\u0000\u001fq\u0000~\u0000\u0018pq\u0000~\u0000\u009csq\u0000~\u0000)t\u0000\bMimeTypeq"
+"\u0000~\u0000\u00b0q\u0000~\u0000(sq\u0000~\u0000\u0014ppsq\u0000~\u0000\u001fq\u0000~\u0000\u0018pq\u0000~\u0000\u00a9sq\u0000~\u0000)t\u0000\u0004Typeq\u0000~\u0000\u00b0q\u0000~\u0000(sq\u0000"
+"~\u0000\u0014ppsq\u0000~\u0000\u001fq\u0000~\u0000\u0018pq\u0000~\u0000\u009csq\u0000~\u0000)t\u0000\tRecipientq\u0000~\u0000\u00b0q\u0000~\u0000(sq\u0000~\u0000\u0014ppsq"
+"\u0000~\u0000\u001fq\u0000~\u0000\u0018pq\u0000~\u00003q\u0000~\u0000Cq\u0000~\u0000(sq\u0000~\u0000)t\u0000\fEncryptedKeyq\u0000~\u0000Hsr\u0000\"com.s"
+"un.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/s"
+"un/msv/grammar/ExpressionPool$ClosedHash;xpsr\u0000-com.sun.msv.g"
+"rammar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstream"
+"VersionL\u0000\u0006parentt\u0000$Lcom/sun/msv/grammar/ExpressionPool;xp\u0000\u0000\u0000"
+"8\u0001pq\u0000~\u0000\u009bq\u0000~\u0000\u001bq\u0000~\u0000Lq\u0000~\u0000Tq\u0000~\u0000`q\u0000~\u0000hq\u0000~\u0000tq\u0000~\u0000|q\u0000~\u0000\u0088q\u0000~\u0000\u0090q\u0000~\u0000\u00c0q\u0000"
+"~\u0000\u000bq\u0000~\u0000\u0019q\u0000~\u0000Rq\u0000~\u0000fq\u0000~\u0000zq\u0000~\u0000\u008eq\u0000~\u0000\u0012q\u0000~\u0000\u0010q\u0000~\u0000\u00c4q\u0000~\u0000\rq\u0000~\u0000\u0013q\u0000~\u0000\tq\u0000"
+"~\u0000\u0011q\u0000~\u0000\u00a7q\u0000~\u0000\u001eq\u0000~\u0000Mq\u0000~\u0000Uq\u0000~\u0000aq\u0000~\u0000iq\u0000~\u0000uq\u0000~\u0000}q\u0000~\u0000\u0089q\u0000~\u0000\u0091q\u0000~\u0000Iq\u0000"
+"~\u0000qq\u0000~\u0000\u0085q\u0000~\u0000\u00bcq\u0000~\u0000\u000fq\u0000~\u0000\fq\u0000~\u0000\u0099q\u0000~\u0000.q\u0000~\u0000Yq\u0000~\u0000mq\u0000~\u0000\u0081q\u0000~\u0000\u0095q\u0000~\u0000Jq\u0000"
+"~\u0000^q\u0000~\u0000rq\u0000~\u0000\u0086q\u0000~\u0000\u00a3q\u0000~\u0000\u00c8q\u0000~\u0000\u00b1q\u0000~\u0000\u0015q\u0000~\u0000\nq\u0000~\u0000\u000ex"));
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
            return com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedKeyElementImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  0 :
                        if (("EncryptedKey" == ___local)&&("http://www.w3.org/2001/04/xmlenc#" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 1;
                            return ;
                        }
                        break;
                    case  1 :
                        attIdx = context.getAttribute("", "Recipient");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        attIdx = context.getAttribute("", "Encoding");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        attIdx = context.getAttribute("", "Id");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        attIdx = context.getAttribute("", "MimeType");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        attIdx = context.getAttribute("", "Type");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        if (("EncryptionMethod" == ___local)&&("http://www.w3.org/2001/04/xmlenc#" == ___uri)) {
                            spawnHandlerFromEnterElement((((com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedKeyTypeImpl)com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedKeyElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        if (("KeyInfo" == ___local)&&("http://www.w3.org/2000/09/xmldsig#" == ___uri)) {
                            spawnHandlerFromEnterElement((((com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedKeyTypeImpl)com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedKeyElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        if (("KeyInfo" == ___local)&&("http://www.w3.org/2000/09/xmldsig#" == ___uri)) {
                            spawnHandlerFromEnterElement((((com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedKeyTypeImpl)com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedKeyElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        if (("CipherData" == ___local)&&("http://www.w3.org/2001/04/xmlenc#" == ___uri)) {
                            spawnHandlerFromEnterElement((((com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedKeyTypeImpl)com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedKeyElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        if (("CipherData" == ___local)&&("http://www.w3.org/2001/04/xmlenc#" == ___uri)) {
                            spawnHandlerFromEnterElement((((com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedKeyTypeImpl)com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedKeyElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        break;
                    case  3 :
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
                    case  2 :
                        if (("EncryptedKey" == ___local)&&("http://www.w3.org/2001/04/xmlenc#" == ___uri)) {
                            context.popAttributes();
                            state = 3;
                            return ;
                        }
                        break;
                    case  1 :
                        attIdx = context.getAttribute("", "Recipient");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = context.getAttribute("", "Encoding");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = context.getAttribute("", "Id");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = context.getAttribute("", "MimeType");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = context.getAttribute("", "Type");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        break;
                    case  3 :
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
                    case  1 :
                        if (("Recipient" == ___local)&&("" == ___uri)) {
                            spawnHandlerFromEnterAttribute((((com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedKeyTypeImpl)com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedKeyElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
                            return ;
                        }
                        if (("Encoding" == ___local)&&("" == ___uri)) {
                            spawnHandlerFromEnterAttribute((((com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedKeyTypeImpl)com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedKeyElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
                            return ;
                        }
                        if (("Id" == ___local)&&("" == ___uri)) {
                            spawnHandlerFromEnterAttribute((((com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedKeyTypeImpl)com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedKeyElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
                            return ;
                        }
                        if (("MimeType" == ___local)&&("" == ___uri)) {
                            spawnHandlerFromEnterAttribute((((com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedKeyTypeImpl)com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedKeyElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
                            return ;
                        }
                        if (("Type" == ___local)&&("" == ___uri)) {
                            spawnHandlerFromEnterAttribute((((com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedKeyTypeImpl)com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedKeyElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
                            return ;
                        }
                        break;
                    case  3 :
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
                    case  1 :
                        attIdx = context.getAttribute("", "Recipient");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = context.getAttribute("", "Encoding");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = context.getAttribute("", "Id");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = context.getAttribute("", "MimeType");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        attIdx = context.getAttribute("", "Type");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        break;
                    case  3 :
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
                        case  1 :
                            attIdx = context.getAttribute("", "Recipient");
                            if (attIdx >= 0) {
                                context.consumeAttribute(attIdx);
                                context.getCurrentHandler().text(value);
                                return ;
                            }
                            attIdx = context.getAttribute("", "Encoding");
                            if (attIdx >= 0) {
                                context.consumeAttribute(attIdx);
                                context.getCurrentHandler().text(value);
                                return ;
                            }
                            attIdx = context.getAttribute("", "Id");
                            if (attIdx >= 0) {
                                context.consumeAttribute(attIdx);
                                context.getCurrentHandler().text(value);
                                return ;
                            }
                            attIdx = context.getAttribute("", "MimeType");
                            if (attIdx >= 0) {
                                context.consumeAttribute(attIdx);
                                context.getCurrentHandler().text(value);
                                return ;
                            }
                            attIdx = context.getAttribute("", "Type");
                            if (attIdx >= 0) {
                                context.consumeAttribute(attIdx);
                                context.getCurrentHandler().text(value);
                                return ;
                            }
                            break;
                        case  3 :
                            revertToParentFromText(value);
                            return ;
                    }
                } catch (java.lang.RuntimeException e) {
                    handleUnexpectedTextException(value, e);
                }
                break;
            }
        }

    }

}
