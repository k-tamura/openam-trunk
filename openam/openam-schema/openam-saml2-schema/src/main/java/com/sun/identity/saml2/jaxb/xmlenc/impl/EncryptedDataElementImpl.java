//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:34:07 AM PDT 
//


package com.sun.identity.saml2.jaxb.xmlenc.impl;

public class EncryptedDataElementImpl
    extends com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedDataTypeImpl
    implements com.sun.identity.saml2.jaxb.xmlenc.EncryptedDataElement, com.sun.xml.bind.RIElement, com.sun.xml.bind.JAXBObject, com.sun.identity.saml2.jaxb.assertion.impl.runtime.UnmarshallableObject, com.sun.identity.saml2.jaxb.assertion.impl.runtime.XMLSerializable, com.sun.identity.saml2.jaxb.assertion.impl.runtime.ValidatableObject
{

    public final static java.lang.Class version = (com.sun.identity.saml2.jaxb.xmlenc.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (com.sun.identity.saml2.jaxb.xmlenc.EncryptedDataElement.class);
    }

    public java.lang.String ____jaxb_ri____getNamespaceURI() {
        return "http://www.w3.org/2001/04/xmlenc#";
    }

    public java.lang.String ____jaxb_ri____getLocalName() {
        return "EncryptedData";
    }

    public com.sun.identity.saml2.jaxb.assertion.impl.runtime.UnmarshallingEventHandler createUnmarshaller(com.sun.identity.saml2.jaxb.assertion.impl.runtime.UnmarshallingContext context) {
        return new com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedDataElementImpl.Unmarshaller(context);
    }

    public void serializeBody(com.sun.identity.saml2.jaxb.assertion.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        context.startElement("http://www.w3.org/2001/04/xmlenc#", "EncryptedData");
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
        return (com.sun.identity.saml2.jaxb.xmlenc.EncryptedDataElement.class);
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
+"q\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsr\u0000\u001dcom.sun.msv.gramm"
+"ar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\bppsq\u0000~\u0000\u0000sr\u0000\u0011java.lang.Boolean\u00cd "
+"r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0011ppsr\u0000 com.sun.m"
+"sv.grammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.Un"
+"aryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0003xq\u0000~\u0000\u0004q\u0000~\u0000\u0015psr\u0000 com.sun.msv.gram"
+"mar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0003L\u0000\tnameClassq\u0000~\u0000\u0001xq\u0000~\u0000"
+"\u0004q\u0000~\u0000\u0015psr\u00002com.sun.msv.grammar.Expression$AnyStringExpressio"
+"n\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004sq\u0000~\u0000\u0014\u0001q\u0000~\u0000\u001fsr\u0000 com.sun.msv.grammar.AnyNam"
+"eClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000"
+"\u0000xpsr\u00000com.sun.msv.grammar.Expression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004q\u0000~\u0000 q\u0000~\u0000%sr\u0000#com.sun.msv.grammar.SimpleNameClas"
+"s\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNamet\u0000\u0012Ljava/lang/String;L\u0000\fnamespaceURI"
+"q\u0000~\u0000\'xq\u0000~\u0000\"t\u00007com.sun.identity.saml2.jaxb.xmlenc.EncryptionM"
+"ethodTypet\u0000+http://java.sun.com/jaxb/xjc/dummy-elementssq\u0000~\u0000"
+"\u0011ppsq\u0000~\u0000\u001cq\u0000~\u0000\u0015psr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002"
+"dtt\u0000\u001fLorg/relaxng/datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0003L\u0000\u0004namet\u0000\u001d"
+"Lcom/sun/msv/util/StringPair;xq\u0000~\u0000\u0004ppsr\u0000\"com.sun.msv.datatyp"
+"e.xsd.QnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000*com.sun.msv.datatype.xsd.Built"
+"inAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.Concrete"
+"Type\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd.XSDatatypeImpl\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUriq\u0000~\u0000\'L\u0000\btypeNameq\u0000~\u0000\'L\u0000\nwhiteSpacet\u0000"
+".Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\u0000 http://w"
+"ww.w3.org/2001/XMLSchemat\u0000\u0005QNamesr\u00005com.sun.msv.datatype.xsd"
+".WhiteSpaceProcessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv.data"
+"type.xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.gra"
+"mmar.Expression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004q\u0000~\u0000\u0015psr\u0000\u001b"
+"com.sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\'L\u0000\fna"
+"mespaceURIq\u0000~\u0000\'xpq\u0000~\u00008q\u0000~\u00007sq\u0000~\u0000&t\u0000\u0004typet\u0000)http://www.w3.org"
+"/2001/XMLSchema-instanceq\u0000~\u0000%sq\u0000~\u0000&t\u0000\u0010EncryptionMethodt\u0000!htt"
+"p://www.w3.org/2001/04/xmlenc#q\u0000~\u0000%sq\u0000~\u0000\u0011ppsq\u0000~\u0000\u0011q\u0000~\u0000\u0015psq\u0000~\u0000"
+"\u0000q\u0000~\u0000\u0015p\u0000sq\u0000~\u0000\u0011ppsq\u0000~\u0000\u0019q\u0000~\u0000\u0015psq\u0000~\u0000\u001cq\u0000~\u0000\u0015pq\u0000~\u0000\u001fq\u0000~\u0000#q\u0000~\u0000%sq\u0000~\u0000"
+"&t\u00001com.sun.identity.saml2.jaxb.xmlsig.KeyInfoElementq\u0000~\u0000*sq"
+"\u0000~\u0000\u0000q\u0000~\u0000\u0015p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0011ppsq\u0000~\u0000\u0019q\u0000~\u0000\u0015psq\u0000~\u0000\u001cq\u0000~\u0000\u0015p"
+"q\u0000~\u0000\u001fq\u0000~\u0000#q\u0000~\u0000%sq\u0000~\u0000&t\u0000.com.sun.identity.saml2.jaxb.xmlsig.K"
+"eyInfoTypeq\u0000~\u0000*sq\u0000~\u0000\u0011ppsq\u0000~\u0000\u001cq\u0000~\u0000\u0015pq\u0000~\u00000q\u0000~\u0000@q\u0000~\u0000%sq\u0000~\u0000&t\u0000\u0007K"
+"eyInfot\u0000\"http://www.w3.org/2000/09/xmldsig#q\u0000~\u0000%sq\u0000~\u0000\u0011ppsq\u0000~"
+"\u0000\u0000pp\u0000sq\u0000~\u0000\u0011ppsq\u0000~\u0000\u0019q\u0000~\u0000\u0015psq\u0000~\u0000\u001cq\u0000~\u0000\u0015pq\u0000~\u0000\u001fq\u0000~\u0000#q\u0000~\u0000%sq\u0000~\u0000&t\u0000"
+"4com.sun.identity.saml2.jaxb.xmlenc.CipherDataElementq\u0000~\u0000*sq"
+"\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0011ppsq\u0000~\u0000\u0019q\u0000~\u0000\u0015psq\u0000~\u0000\u001cq\u0000~\u0000\u0015pq\u0000~\u0000"
+"\u001fq\u0000~\u0000#q\u0000~\u0000%sq\u0000~\u0000&t\u00001com.sun.identity.saml2.jaxb.xmlenc.Ciphe"
+"rDataTypeq\u0000~\u0000*sq\u0000~\u0000\u0011ppsq\u0000~\u0000\u001cq\u0000~\u0000\u0015pq\u0000~\u00000q\u0000~\u0000@q\u0000~\u0000%sq\u0000~\u0000&t\u0000\nCi"
+"pherDataq\u0000~\u0000Esq\u0000~\u0000\u0011ppsq\u0000~\u0000\u0011q\u0000~\u0000\u0015psq\u0000~\u0000\u0000q\u0000~\u0000\u0015p\u0000sq\u0000~\u0000\u0011ppsq\u0000~\u0000\u0019"
+"q\u0000~\u0000\u0015psq\u0000~\u0000\u001cq\u0000~\u0000\u0015pq\u0000~\u0000\u001fq\u0000~\u0000#q\u0000~\u0000%sq\u0000~\u0000&t\u0000>com.sun.identity.s"
+"aml2.jaxb.xmlenc.EncryptionPropertiesElementq\u0000~\u0000*sq\u0000~\u0000\u0000q\u0000~\u0000\u0015"
+"p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0011ppsq\u0000~\u0000\u0019q\u0000~\u0000\u0015psq\u0000~\u0000\u001cq\u0000~\u0000\u0015pq\u0000~\u0000\u001fq\u0000~\u0000"
+"#q\u0000~\u0000%sq\u0000~\u0000&t\u0000;com.sun.identity.saml2.jaxb.xmlenc.Encryption"
+"PropertiesTypeq\u0000~\u0000*sq\u0000~\u0000\u0011ppsq\u0000~\u0000\u001cq\u0000~\u0000\u0015pq\u0000~\u00000q\u0000~\u0000@q\u0000~\u0000%sq\u0000~\u0000&"
+"t\u0000\u0014EncryptionPropertiesq\u0000~\u0000Eq\u0000~\u0000%sq\u0000~\u0000\u0011ppsq\u0000~\u0000\u001cq\u0000~\u0000\u0015psq\u0000~\u0000-q"
+"\u0000~\u0000\u0015psr\u0000#com.sun.msv.datatype.xsd.AnyURIType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000"
+"2q\u0000~\u00007t\u0000\u0006anyURIq\u0000~\u0000;q\u0000~\u0000=sq\u0000~\u0000>q\u0000~\u0000\u0087q\u0000~\u00007sq\u0000~\u0000&t\u0000\bEncodingt\u0000"
+"\u0000q\u0000~\u0000%sq\u0000~\u0000\u0011ppsq\u0000~\u0000\u001cq\u0000~\u0000\u0015psq\u0000~\u0000-ppsr\u0000\u001fcom.sun.msv.datatype.x"
+"sd.IDType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000#com.sun.msv.datatype.xsd.NcnameType\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\"com.sun.msv.datatype.xsd.TokenType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000x"
+"r\u0000#com.sun.msv.datatype.xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlways"
+"Validxq\u0000~\u00002q\u0000~\u00007t\u0000\u0002IDq\u0000~\u0000;\u0000q\u0000~\u0000=sq\u0000~\u0000>q\u0000~\u0000\u0094q\u0000~\u00007sq\u0000~\u0000&t\u0000\u0002Idq"
+"\u0000~\u0000\u008bq\u0000~\u0000%sq\u0000~\u0000\u0011ppsq\u0000~\u0000\u001cq\u0000~\u0000\u0015psq\u0000~\u0000-q\u0000~\u0000\u0015psq\u0000~\u0000\u0092q\u0000~\u00007t\u0000\u0006strin"
+"gsr\u00005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Preserve\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000:\u0001q\u0000~\u0000=sq\u0000~\u0000>q\u0000~\u0000\u009cq\u0000~\u00007sq\u0000~\u0000&t\u0000\bMimeTypeq\u0000~\u0000\u008bq"
+"\u0000~\u0000%sq\u0000~\u0000\u0011ppsq\u0000~\u0000\u001cq\u0000~\u0000\u0015pq\u0000~\u0000\u0084sq\u0000~\u0000&t\u0000\u0004Typeq\u0000~\u0000\u008bq\u0000~\u0000%sq\u0000~\u0000\u0011pp"
+"sq\u0000~\u0000\u001cq\u0000~\u0000\u0015pq\u0000~\u00000q\u0000~\u0000@q\u0000~\u0000%sq\u0000~\u0000&t\u0000\rEncryptedDataq\u0000~\u0000Esr\u0000\"co"
+"m.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lco"
+"m/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\u0000-com.sun.ms"
+"v.grammar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstr"
+"eamVersionL\u0000\u0006parentt\u0000$Lcom/sun/msv/grammar/ExpressionPool;xp"
+"\u0000\u0000\u0000)\u0001pq\u0000~\u0000\tq\u0000~\u0000\u0018q\u0000~\u0000Iq\u0000~\u0000Qq\u0000~\u0000]q\u0000~\u0000eq\u0000~\u0000qq\u0000~\u0000yq\u0000~\u0000\u0016q\u0000~\u0000Oq\u0000~\u0000"
+"cq\u0000~\u0000wq\u0000~\u0000\u0082q\u0000~\u0000\u000fq\u0000~\u0000\u0010q\u0000~\u0000\u000eq\u0000~\u0000\u001bq\u0000~\u0000Jq\u0000~\u0000Rq\u0000~\u0000^q\u0000~\u0000fq\u0000~\u0000rq\u0000~\u0000"
+"zq\u0000~\u0000\u0098q\u0000~\u0000\nq\u0000~\u0000\rq\u0000~\u0000Fq\u0000~\u0000nq\u0000~\u0000\u000bq\u0000~\u0000+q\u0000~\u0000Vq\u0000~\u0000jq\u0000~\u0000~q\u0000~\u0000\u00a6q\u0000~\u0000"
+"Gq\u0000~\u0000[q\u0000~\u0000oq\u0000~\u0000\u008cq\u0000~\u0000\u0012q\u0000~\u0000\u00a2q\u0000~\u0000\fx"));
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
            return com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedDataElementImpl.this;
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
                            spawnHandlerFromEnterElement((((com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedDataTypeImpl)com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedDataElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        if (("KeyInfo" == ___local)&&("http://www.w3.org/2000/09/xmldsig#" == ___uri)) {
                            spawnHandlerFromEnterElement((((com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedDataTypeImpl)com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedDataElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        if (("KeyInfo" == ___local)&&("http://www.w3.org/2000/09/xmldsig#" == ___uri)) {
                            spawnHandlerFromEnterElement((((com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedDataTypeImpl)com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedDataElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        if (("CipherData" == ___local)&&("http://www.w3.org/2001/04/xmlenc#" == ___uri)) {
                            spawnHandlerFromEnterElement((((com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedDataTypeImpl)com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedDataElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        if (("CipherData" == ___local)&&("http://www.w3.org/2001/04/xmlenc#" == ___uri)) {
                            spawnHandlerFromEnterElement((((com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedDataTypeImpl)com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedDataElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        break;
                    case  0 :
                        if (("EncryptedData" == ___local)&&("http://www.w3.org/2001/04/xmlenc#" == ___uri)) {
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
                    case  2 :
                        if (("EncryptedData" == ___local)&&("http://www.w3.org/2001/04/xmlenc#" == ___uri)) {
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
                        if (("Encoding" == ___local)&&("" == ___uri)) {
                            spawnHandlerFromEnterAttribute((((com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedDataTypeImpl)com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedDataElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
                            return ;
                        }
                        if (("Id" == ___local)&&("" == ___uri)) {
                            spawnHandlerFromEnterAttribute((((com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedDataTypeImpl)com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedDataElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
                            return ;
                        }
                        if (("MimeType" == ___local)&&("" == ___uri)) {
                            spawnHandlerFromEnterAttribute((((com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedDataTypeImpl)com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedDataElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
                            return ;
                        }
                        if (("Type" == ___local)&&("" == ___uri)) {
                            spawnHandlerFromEnterAttribute((((com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedDataTypeImpl)com.sun.identity.saml2.jaxb.xmlenc.impl.EncryptedDataElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
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
                    }
                } catch (java.lang.RuntimeException e) {
                    handleUnexpectedTextException(value, e);
                }
                break;
            }
        }

    }

}