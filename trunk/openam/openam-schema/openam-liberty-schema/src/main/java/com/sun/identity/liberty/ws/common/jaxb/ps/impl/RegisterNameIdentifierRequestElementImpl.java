//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:33:54 AM PDT 
//


package com.sun.identity.liberty.ws.common.jaxb.ps.impl;

public class RegisterNameIdentifierRequestElementImpl
    extends com.sun.identity.liberty.ws.common.jaxb.ps.impl.RegisterNameIdentifierRequestTypeImpl
    implements com.sun.identity.liberty.ws.common.jaxb.ps.RegisterNameIdentifierRequestElement, com.sun.xml.bind.RIElement, com.sun.xml.bind.JAXBObject, com.sun.identity.federation.jaxb.entityconfig.impl.runtime.UnmarshallableObject, com.sun.identity.federation.jaxb.entityconfig.impl.runtime.XMLSerializable, com.sun.identity.federation.jaxb.entityconfig.impl.runtime.ValidatableObject
{

    public final static java.lang.Class version = (com.sun.identity.liberty.ws.common.jaxb.ps.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (com.sun.identity.liberty.ws.common.jaxb.ps.RegisterNameIdentifierRequestElement.class);
    }

    public java.lang.String ____jaxb_ri____getNamespaceURI() {
        return "urn:liberty:iff:2003-08";
    }

    public java.lang.String ____jaxb_ri____getLocalName() {
        return "RegisterNameIdentifierRequest";
    }

    public com.sun.identity.federation.jaxb.entityconfig.impl.runtime.UnmarshallingEventHandler createUnmarshaller(com.sun.identity.federation.jaxb.entityconfig.impl.runtime.UnmarshallingContext context) {
        return new com.sun.identity.liberty.ws.common.jaxb.ps.impl.RegisterNameIdentifierRequestElementImpl.Unmarshaller(context);
    }

    public void serializeBody(com.sun.identity.federation.jaxb.entityconfig.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        context.startElement("urn:liberty:iff:2003-08", "RegisterNameIdentifierRequest");
        super.serializeURIs(context);
        context.endNamespaceDecls();
        super.serializeAttributes(context);
        context.endAttributes();
        super.serializeBody(context);
        context.endElement();
    }

    public void serializeAttributes(com.sun.identity.federation.jaxb.entityconfig.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
    }

    public void serializeURIs(com.sun.identity.federation.jaxb.entityconfig.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
    }

    public java.lang.Class getPrimaryInterface() {
        return (com.sun.identity.liberty.ws.common.jaxb.ps.RegisterNameIdentifierRequestElement.class);
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
+"\u0007ppsq\u0000~\u0000\u0007ppsr\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000"
+"\bppsr\u0000 com.sun.msv.grammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.su"
+"n.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0003xq\u0000~\u0000\u0004sr\u0000\u0011java.l"
+"ang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psq\u0000~\u0000\u0000q\u0000~\u0000\u001bp\u0000sq\u0000~\u0000\u0007ppsr\u0000\u001bc"
+"om.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/da"
+"tatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0003L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/St"
+"ringPair;xq\u0000~\u0000\u0004q\u0000~\u0000\u001bpsr\u0000\"com.sun.msv.datatype.xsd.QnameType\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000*com.sun.msv.datatype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000"
+"\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000x"
+"r\u0000\'com.sun.msv.datatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fname"
+"spaceUrit\u0000\u0012Ljava/lang/String;L\u0000\btypeNameq\u0000~\u0000&L\u0000\nwhiteSpacet\u0000"
+".Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\u0000 http://w"
+"ww.w3.org/2001/XMLSchemat\u0000\u0005QNamesr\u00005com.sun.msv.datatype.xsd"
+".WhiteSpaceProcessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv.data"
+"type.xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.gra"
+"mmar.Expression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004q\u0000~\u0000\u001bpsr\u0000\u001b"
+"com.sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000&L\u0000\fna"
+"mespaceURIq\u0000~\u0000&xpq\u0000~\u0000*q\u0000~\u0000)sq\u0000~\u0000\u0015ppsr\u0000 com.sun.msv.grammar.A"
+"ttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0003L\u0000\tnameClassq\u0000~\u0000\u0001xq\u0000~\u0000\u0004q\u0000~\u0000"
+"\u001bpq\u0000~\u0000!sr\u0000#com.sun.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\t"
+"localNameq\u0000~\u0000&L\u0000\fnamespaceURIq\u0000~\u0000&xr\u0000\u001dcom.sun.msv.grammar.Na"
+"meClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpt\u0000\u0004typet\u0000)http://www.w3.org/2001/XMLSche"
+"ma-instancesr\u00000com.sun.msv.grammar.Expression$EpsilonExpress"
+"ion\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004sq\u0000~\u0000\u001a\u0001q\u0000~\u0000;sq\u0000~\u00005t\u0000\u000bRespondWitht\u0000$urn:o"
+"asis:names:tc:SAML:1.0:protocolq\u0000~\u0000;sq\u0000~\u0000\u0015ppsq\u0000~\u0000\u0015q\u0000~\u0000\u001bpsq\u0000~"
+"\u0000\u0000q\u0000~\u0000\u001bp\u0000sq\u0000~\u0000\u0015ppsq\u0000~\u0000\u0017q\u0000~\u0000\u001bpsq\u0000~\u00003q\u0000~\u0000\u001bpsr\u00002com.sun.msv.gra"
+"mmar.Expression$AnyStringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004q\u0000~\u0000<q\u0000~"
+"\u0000Gsr\u0000 com.sun.msv.grammar.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u00006q\u0000~\u0000;"
+"sq\u0000~\u00005t\u0000?com.sun.identity.liberty.ws.common.jaxb.xmlsig.Sign"
+"atureElementt\u0000+http://java.sun.com/jaxb/xjc/dummy-elementssq"
+"\u0000~\u0000\u0000q\u0000~\u0000\u001bp\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0015ppsq\u0000~\u0000\u0017q\u0000~\u0000\u001bpsq\u0000~\u00003q\u0000~\u0000\u001bp"
+"q\u0000~\u0000Gq\u0000~\u0000Iq\u0000~\u0000;sq\u0000~\u00005t\u0000<com.sun.identity.liberty.ws.common.j"
+"axb.xmlsig.SignatureTypeq\u0000~\u0000Lsq\u0000~\u0000\u0015ppsq\u0000~\u00003q\u0000~\u0000\u001bpq\u0000~\u0000!q\u0000~\u00007q"
+"\u0000~\u0000;sq\u0000~\u00005t\u0000\tSignaturet\u0000\"http://www.w3.org/2000/09/xmldsig#q"
+"\u0000~\u0000;sq\u0000~\u0000\u0015ppsq\u0000~\u0000\u0017q\u0000~\u0000\u001bpsq\u0000~\u0000\u0015q\u0000~\u0000\u001bpsq\u0000~\u0000\u0000q\u0000~\u0000\u001bp\u0000sq\u0000~\u0000\u0015ppsq\u0000"
+"~\u0000\u0017q\u0000~\u0000\u001bpsq\u0000~\u00003q\u0000~\u0000\u001bpq\u0000~\u0000Gq\u0000~\u0000Iq\u0000~\u0000;sq\u0000~\u00005t\u0000;com.sun.identit"
+"y.liberty.ws.common.jaxb.ps.ExtensionElementq\u0000~\u0000Lsq\u0000~\u0000\u0000q\u0000~\u0000\u001b"
+"p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0015ppsq\u0000~\u0000\u0017q\u0000~\u0000\u001bpsq\u0000~\u00003q\u0000~\u0000\u001bpq\u0000~\u0000Gq\u0000~\u0000"
+"Iq\u0000~\u0000;sq\u0000~\u00005t\u00008com.sun.identity.liberty.ws.common.jaxb.ps.Ex"
+"tensionTypeq\u0000~\u0000Lsq\u0000~\u0000\u0015ppsq\u0000~\u00003q\u0000~\u0000\u001bpq\u0000~\u0000!q\u0000~\u00007q\u0000~\u0000;sq\u0000~\u00005t\u0000\t"
+"Extensiont\u0000\u0017urn:liberty:iff:2003-08q\u0000~\u0000;sq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0007ppsq\u0000"
+"~\u0000\u001eppsr\u0000\'com.sun.msv.datatype.xsd.MaxLengthFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001I"
+"\u0000\tmaxLengthxr\u00009com.sun.msv.datatype.xsd.DataTypeWithValueCon"
+"straintFacet\"\u00a7Ro\u00ca\u00c7\u008aT\u0002\u0000\u0000xr\u0000*com.sun.msv.datatype.xsd.DataType"
+"WithFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0005Z\u0000\fisFacetFixedZ\u0000\u0012needValueCheckFlagL\u0000\bb"
+"aseTypet\u0000)Lcom/sun/msv/datatype/xsd/XSDatatypeImpl;L\u0000\fconcre"
+"teTypet\u0000\'Lcom/sun/msv/datatype/xsd/ConcreteType;L\u0000\tfacetName"
+"q\u0000~\u0000&xq\u0000~\u0000%t\u0000\u001curn:liberty:metadata:2003-08t\u0000\fentityIDTypeq\u0000~"
+"\u0000-\u0000\u0000sr\u0000#com.sun.msv.datatype.xsd.AnyURIType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000#"
+"q\u0000~\u0000)t\u0000\u0006anyURIq\u0000~\u0000-q\u0000~\u0000|t\u0000\tmaxLength\u0000\u0000\u0004\u0000q\u0000~\u0000/sq\u0000~\u00000q\u0000~\u0000zq\u0000~\u0000"
+"ysq\u0000~\u0000\u0015ppsq\u0000~\u00003q\u0000~\u0000\u001bpq\u0000~\u0000!q\u0000~\u00007q\u0000~\u0000;sq\u0000~\u00005t\u0000\nProviderIDq\u0000~\u0000o"
+"sq\u0000~\u0000\u0015ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0015ppsq\u0000~\u0000\u0017q\u0000~\u0000\u001bpsq\u0000~\u00003q\u0000~\u0000\u001bpq\u0000~\u0000Gq\u0000~\u0000Iq"
+"\u0000~\u0000;sq\u0000~\u00005t\u0000Kcom.sun.identity.liberty.ws.common.jaxb.ps.IDPP"
+"rovidedNameIdentifierElementq\u0000~\u0000Lsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000s"
+"q\u0000~\u0000\u0015ppsq\u0000~\u0000\u0017q\u0000~\u0000\u001bpsq\u0000~\u00003q\u0000~\u0000\u001bpq\u0000~\u0000Gq\u0000~\u0000Iq\u0000~\u0000;sq\u0000~\u00005t\u0000Dcom.s"
+"un.identity.liberty.ws.common.jaxb.assertion.NameIdentifierT"
+"ypeq\u0000~\u0000Lsq\u0000~\u0000\u0015ppsq\u0000~\u00003q\u0000~\u0000\u001bpq\u0000~\u0000!q\u0000~\u00007q\u0000~\u0000;sq\u0000~\u00005t\u0000\u0019IDPProvi"
+"dedNameIdentifierq\u0000~\u0000osq\u0000~\u0000\u0015ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0015ppsq\u0000~\u0000\u0017q\u0000~\u0000\u001bps"
+"q\u0000~\u00003q\u0000~\u0000\u001bpq\u0000~\u0000Gq\u0000~\u0000Iq\u0000~\u0000;sq\u0000~\u00005t\u0000Jcom.sun.identity.liberty."
+"ws.common.jaxb.ps.SPProvidedNameIdentifierElementq\u0000~\u0000Lsq\u0000~\u0000\u0000"
+"pp\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0015ppsq\u0000~\u0000\u0017q\u0000~\u0000\u001bpsq\u0000~\u00003q\u0000~\u0000\u001bpq\u0000~\u0000Gq\u0000~"
+"\u0000Iq\u0000~\u0000;sq\u0000~\u00005q\u0000~\u0000\u0092q\u0000~\u0000Lsq\u0000~\u0000\u0015ppsq\u0000~\u00003q\u0000~\u0000\u001bpq\u0000~\u0000!q\u0000~\u00007q\u0000~\u0000;sq"
+"\u0000~\u00005t\u0000\u0018SPProvidedNameIdentifierq\u0000~\u0000osq\u0000~\u0000\u0015ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0015p"
+"psq\u0000~\u0000\u0017q\u0000~\u0000\u001bpsq\u0000~\u00003q\u0000~\u0000\u001bpq\u0000~\u0000Gq\u0000~\u0000Iq\u0000~\u0000;sq\u0000~\u00005t\u0000Kcom.sun.ide"
+"ntity.liberty.ws.common.jaxb.ps.OldProvidedNameIdentifierEle"
+"mentq\u0000~\u0000Lsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0015ppsq\u0000~\u0000\u0017q\u0000~\u0000\u001bpsq\u0000~\u0000"
+"3q\u0000~\u0000\u001bpq\u0000~\u0000Gq\u0000~\u0000Iq\u0000~\u0000;sq\u0000~\u00005q\u0000~\u0000\u0092q\u0000~\u0000Lsq\u0000~\u0000\u0015ppsq\u0000~\u00003q\u0000~\u0000\u001bpq\u0000"
+"~\u0000!q\u0000~\u00007q\u0000~\u0000;sq\u0000~\u00005t\u0000\u0019OldProvidedNameIdentifierq\u0000~\u0000osq\u0000~\u0000\u0015pp"
+"sq\u0000~\u0000\u0000q\u0000~\u0000\u001bp\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u001eq\u0000~\u0000\u001bpsr\u0000#com.sun.msv.datatype.xs"
+"d.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlwaysValidxq\u0000~\u0000#q\u0000~\u0000)t\u0000\u0006strings"
+"r\u00005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Preserve\u0000\u0000\u0000\u0000"
+"\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000,\u0001q\u0000~\u0000/sq\u0000~\u00000q\u0000~\u0000\u00c1q\u0000~\u0000)sq\u0000~\u0000\u0015ppsq\u0000~\u00003q\u0000~\u0000\u001bpq\u0000~\u0000!"
+"q\u0000~\u00007q\u0000~\u0000;sq\u0000~\u00005t\u0000\nRelayStateq\u0000~\u0000oq\u0000~\u0000;sq\u0000~\u00003ppsq\u0000~\u0000\u001eppsr\u0000%c"
+"om.sun.msv.datatype.xsd.DateTimeType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000)com.sun.m"
+"sv.datatype.xsd.DateTimeBaseType\u0014W\u001a@3\u00a5\u00b4\u00e5\u0002\u0000\u0000xq\u0000~\u0000#q\u0000~\u0000)t\u0000\bdat"
+"eTimeq\u0000~\u0000-q\u0000~\u0000/sq\u0000~\u00000q\u0000~\u0000\u00ceq\u0000~\u0000)sq\u0000~\u00005t\u0000\fIssueInstantt\u0000\u0000sq\u0000~\u0000"
+"3ppsq\u0000~\u0000\u001eppsr\u0000$com.sun.msv.datatype.xsd.IntegerType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002"
+"\u0000\u0000xr\u0000+com.sun.msv.datatype.xsd.IntegerDerivedType\u0099\u00f1]\u0090&6k\u00be\u0002\u0000\u0001"
+"L\u0000\nbaseFacetsq\u0000~\u0000vxq\u0000~\u0000#q\u0000~\u0000)t\u0000\u0007integerq\u0000~\u0000-sr\u0000,com.sun.msv."
+"datatype.xsd.FractionDigitsFacet\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001I\u0000\u0005scalexr\u0000;com.s"
+"un.msv.datatype.xsd.DataTypeWithLexicalConstraintFacetT\u0090\u001c>\u001az"
+"b\u00ea\u0002\u0000\u0000xq\u0000~\u0000uppq\u0000~\u0000-\u0001\u0000sr\u0000#com.sun.msv.datatype.xsd.NumberType\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000#q\u0000~\u0000)t\u0000\u0007decimalq\u0000~\u0000-q\u0000~\u0000\u00ddt\u0000\u000efractionDigits\u0000\u0000"
+"\u0000\u0000q\u0000~\u0000/sq\u0000~\u00000q\u0000~\u0000\u00d8q\u0000~\u0000)sq\u0000~\u00005t\u0000\fMajorVersionq\u0000~\u0000\u00d2sq\u0000~\u00003ppq\u0000~"
+"\u0000\u00d4sq\u0000~\u00005t\u0000\fMinorVersionq\u0000~\u0000\u00d2sq\u0000~\u00003ppsq\u0000~\u0000\u001eppsr\u0000\u001fcom.sun.msv."
+"datatype.xsd.IDType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000#com.sun.msv.datatype.xsd.N"
+"cnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\"com.sun.msv.datatype.xsd.TokenType\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u00bfq\u0000~\u0000)t\u0000\u0002IDq\u0000~\u0000-\u0000q\u0000~\u0000/sq\u0000~\u00000q\u0000~\u0000\u00ecq\u0000~\u0000)sq\u0000~\u00005t\u0000"
+"\tRequestIDq\u0000~\u0000\u00d2sq\u0000~\u0000\u0015ppsq\u0000~\u00003q\u0000~\u0000\u001bpq\u0000~\u0000!q\u0000~\u00007q\u0000~\u0000;sq\u0000~\u00005t\u0000\u001dR"
+"egisterNameIdentifierRequestq\u0000~\u0000osr\u0000\"com.sun.msv.grammar.Exp"
+"ressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/Exp"
+"ressionPool$ClosedHash;xpsr\u0000-com.sun.msv.grammar.ExpressionP"
+"ool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parentt\u0000"
+"$Lcom/sun/msv/grammar/ExpressionPool;xp\u0000\u0000\u0000<\u0001pq\u0000~\u0000\u00bdq\u0000~\u0000\u00bbq\u0000~\u0000\u000f"
+"q\u0000~\u0000\u0014q\u0000~\u0000\u000eq\u0000~\u0000\u0010q\u0000~\u0000\u0012q\u0000~\u0000\u001dq\u0000~\u0000\tq\u0000~\u0000@q\u0000~\u0000\u0011q\u0000~\u0000Zq\u0000~\u0000\u00b3q\u0000~\u0000\u00abq\u0000~\u0000\u00a1"
+"q\u0000~\u0000\u0099q\u0000~\u0000\u008eq\u0000~\u0000\u0086q\u0000~\u0000fq\u0000~\u0000^q\u0000~\u0000Pq\u0000~\u0000Cq\u0000~\u0000Aq\u0000~\u0000\u00a9q\u0000~\u0000\u0097q\u0000~\u0000\u0084q\u0000~\u0000\\"
+"q\u0000~\u0000\u00c5q\u0000~\u0000\u00b7q\u0000~\u0000\u00a5q\u0000~\u0000\u0093q\u0000~\u0000\u0080q\u0000~\u0000kq\u0000~\u0000Uq\u0000~\u00002q\u0000~\u0000\u000bq\u0000~\u0000\u00f0q\u0000~\u0000\u0019q\u0000~\u0000\u0013"
+"q\u0000~\u0000qq\u0000~\u0000\u0016q\u0000~\u0000\nq\u0000~\u0000\rq\u0000~\u0000\u00b1q\u0000~\u0000\u009fq\u0000~\u0000\u008cq\u0000~\u0000dq\u0000~\u0000Nq\u0000~\u0000\fq\u0000~\u0000[q\u0000~\u0000\u00b4"
+"q\u0000~\u0000\u00acq\u0000~\u0000\u00a2q\u0000~\u0000\u009aq\u0000~\u0000\u008fq\u0000~\u0000\u0087q\u0000~\u0000gq\u0000~\u0000_q\u0000~\u0000Qq\u0000~\u0000Dx"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public class Unmarshaller
        extends com.sun.identity.federation.jaxb.entityconfig.impl.runtime.AbstractUnmarshallingEventHandlerImpl
    {


        public Unmarshaller(com.sun.identity.federation.jaxb.entityconfig.impl.runtime.UnmarshallingContext context) {
            super(context, "----");
        }

        protected Unmarshaller(com.sun.identity.federation.jaxb.entityconfig.impl.runtime.UnmarshallingContext context, int startState) {
            this(context);
            state = startState;
        }

        public java.lang.Object owner() {
            return com.sun.identity.liberty.ws.common.jaxb.ps.impl.RegisterNameIdentifierRequestElementImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  1 :
                        attIdx = context.getAttribute("", "IssueInstant");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        break;
                    case  0 :
                        if (("RegisterNameIdentifierRequest" == ___local)&&("urn:liberty:iff:2003-08" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 1;
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
                    case  1 :
                        attIdx = context.getAttribute("", "IssueInstant");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        break;
                    case  2 :
                        if (("RegisterNameIdentifierRequest" == ___local)&&("urn:liberty:iff:2003-08" == ___uri)) {
                            context.popAttributes();
                            state = 3;
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
                        if (("IssueInstant" == ___local)&&("" == ___uri)) {
                            spawnHandlerFromEnterAttribute((((com.sun.identity.liberty.ws.common.jaxb.ps.impl.RegisterNameIdentifierRequestTypeImpl)com.sun.identity.liberty.ws.common.jaxb.ps.impl.RegisterNameIdentifierRequestElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
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
                        attIdx = context.getAttribute("", "IssueInstant");
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
                            attIdx = context.getAttribute("", "IssueInstant");
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
