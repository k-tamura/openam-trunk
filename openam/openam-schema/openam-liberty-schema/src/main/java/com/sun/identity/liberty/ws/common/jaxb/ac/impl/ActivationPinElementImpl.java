//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:33:54 AM PDT 
//


package com.sun.identity.liberty.ws.common.jaxb.ac.impl;

public class ActivationPinElementImpl
    extends com.sun.identity.liberty.ws.common.jaxb.ac.impl.ActivationPinTypeImpl
    implements com.sun.identity.liberty.ws.common.jaxb.ac.ActivationPinElement, com.sun.xml.bind.RIElement, com.sun.xml.bind.JAXBObject, com.sun.identity.federation.jaxb.entityconfig.impl.runtime.UnmarshallableObject, com.sun.identity.federation.jaxb.entityconfig.impl.runtime.XMLSerializable, com.sun.identity.federation.jaxb.entityconfig.impl.runtime.ValidatableObject
{

    public final static java.lang.Class version = (com.sun.identity.liberty.ws.common.jaxb.ac.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (com.sun.identity.liberty.ws.common.jaxb.ac.ActivationPinElement.class);
    }

    public java.lang.String ____jaxb_ri____getNamespaceURI() {
        return "urn:liberty:ac:2003-08";
    }

    public java.lang.String ____jaxb_ri____getLocalName() {
        return "ActivationPin";
    }

    public com.sun.identity.federation.jaxb.entityconfig.impl.runtime.UnmarshallingEventHandler createUnmarshaller(com.sun.identity.federation.jaxb.entityconfig.impl.runtime.UnmarshallingContext context) {
        return new com.sun.identity.liberty.ws.common.jaxb.ac.impl.ActivationPinElementImpl.Unmarshaller(context);
    }

    public void serializeBody(com.sun.identity.federation.jaxb.entityconfig.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        context.startElement("urn:liberty:ac:2003-08", "ActivationPin");
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
        return (com.sun.identity.liberty.ws.common.jaxb.ac.ActivationPinElement.class);
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
+"q\u0000~\u0000\u0007ppsq\u0000~\u0000\u0007ppsr\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000x"
+"q\u0000~\u0000\bppsq\u0000~\u0000\u000esr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psq\u0000"
+"~\u0000\u0000q\u0000~\u0000\u0012p\u0000sq\u0000~\u0000\u000eppsr\u0000 com.sun.msv.grammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0003x"
+"q\u0000~\u0000\u0004q\u0000~\u0000\u0012psr\u0000 com.sun.msv.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000"
+"\u0003expq\u0000~\u0000\u0003L\u0000\tnameClassq\u0000~\u0000\u0001xq\u0000~\u0000\u0004q\u0000~\u0000\u0012psr\u00002com.sun.msv.gramma"
+"r.Expression$AnyStringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004sq\u0000~\u0000\u0011\u0001q\u0000~\u0000"
+"\u001bsr\u0000 com.sun.msv.grammar.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun."
+"msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Ex"
+"pression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004q\u0000~\u0000\u001cq\u0000~\u0000!sr\u0000#com"
+".sun.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNamet\u0000\u0012Lj"
+"ava/lang/String;L\u0000\fnamespaceURIq\u0000~\u0000#xq\u0000~\u0000\u001et\u00008com.sun.identit"
+"y.liberty.ws.common.jaxb.ac.LengthElementt\u0000+http://java.sun."
+"com/jaxb/xjc/dummy-elementssq\u0000~\u0000\u0000q\u0000~\u0000\u0012p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000"
+"~\u0000\u000eppsq\u0000~\u0000\u0015q\u0000~\u0000\u0012psq\u0000~\u0000\u0018q\u0000~\u0000\u0012pq\u0000~\u0000\u001bq\u0000~\u0000\u001fq\u0000~\u0000!sq\u0000~\u0000\"t\u00005com.sun"
+".identity.liberty.ws.common.jaxb.ac.LengthTypeq\u0000~\u0000&sq\u0000~\u0000\u000epps"
+"q\u0000~\u0000\u0018q\u0000~\u0000\u0012psr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000"
+"\u001fLorg/relaxng/datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0003L\u0000\u0004namet\u0000\u001dLcom"
+"/sun/msv/util/StringPair;xq\u0000~\u0000\u0004q\u0000~\u0000\u0012psr\u0000\"com.sun.msv.datatyp"
+"e.xsd.QnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000*com.sun.msv.datatype.xsd.Built"
+"inAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.Concrete"
+"Type\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd.XSDatatypeImpl\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUriq\u0000~\u0000#L\u0000\btypeNameq\u0000~\u0000#L\u0000\nwhiteSpacet\u0000"
+".Lcom/sun/msv/datatype/xsd/WhiteSpaceProcessor;xpt\u0000 http://w"
+"ww.w3.org/2001/XMLSchemat\u0000\u0005QNamesr\u00005com.sun.msv.datatype.xsd"
+".WhiteSpaceProcessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv.data"
+"type.xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.gra"
+"mmar.Expression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004q\u0000~\u0000\u0012psr\u0000\u001b"
+"com.sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000#L\u0000\fna"
+"mespaceURIq\u0000~\u0000#xpq\u0000~\u0000<q\u0000~\u0000;sq\u0000~\u0000\"t\u0000\u0004typet\u0000)http://www.w3.org"
+"/2001/XMLSchema-instanceq\u0000~\u0000!sq\u0000~\u0000\"t\u0000\u0006Lengtht\u0000\u0016urn:liberty:a"
+"c:2003-08q\u0000~\u0000!sq\u0000~\u0000\u000eppsq\u0000~\u0000\u000eq\u0000~\u0000\u0012psq\u0000~\u0000\u0000q\u0000~\u0000\u0012p\u0000sq\u0000~\u0000\u000eppsq\u0000~\u0000"
+"\u0015q\u0000~\u0000\u0012psq\u0000~\u0000\u0018q\u0000~\u0000\u0012pq\u0000~\u0000\u001bq\u0000~\u0000\u001fq\u0000~\u0000!sq\u0000~\u0000\"t\u0000:com.sun.identity."
+"liberty.ws.common.jaxb.ac.AlphabetElementq\u0000~\u0000&sq\u0000~\u0000\u0000q\u0000~\u0000\u0012p\u0000s"
+"q\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u000eppsq\u0000~\u0000\u0015q\u0000~\u0000\u0012psq\u0000~\u0000\u0018q\u0000~\u0000\u0012pq\u0000~\u0000\u001bq\u0000~\u0000\u001fq\u0000"
+"~\u0000!sq\u0000~\u0000\"t\u00007com.sun.identity.liberty.ws.common.jaxb.ac.Alpha"
+"betTypeq\u0000~\u0000&sq\u0000~\u0000\u000eppsq\u0000~\u0000\u0018q\u0000~\u0000\u0012pq\u0000~\u00004q\u0000~\u0000Dq\u0000~\u0000!sq\u0000~\u0000\"t\u0000\bAlph"
+"abetq\u0000~\u0000Iq\u0000~\u0000!sq\u0000~\u0000\u000eppsq\u0000~\u0000\u000eq\u0000~\u0000\u0012psq\u0000~\u0000\u0000q\u0000~\u0000\u0012p\u0000sq\u0000~\u0000\u000eppsq\u0000~\u0000"
+"\u0015q\u0000~\u0000\u0012psq\u0000~\u0000\u0018q\u0000~\u0000\u0012pq\u0000~\u0000\u001bq\u0000~\u0000\u001fq\u0000~\u0000!sq\u0000~\u0000\"t\u0000<com.sun.identity."
+"liberty.ws.common.jaxb.ac.GenerationElementq\u0000~\u0000&sq\u0000~\u0000\u0000q\u0000~\u0000\u0012p"
+"\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u000eppsq\u0000~\u0000\u0015q\u0000~\u0000\u0012psq\u0000~\u0000\u0018q\u0000~\u0000\u0012pq\u0000~\u0000\u001bq\u0000~\u0000\u001f"
+"q\u0000~\u0000!sq\u0000~\u0000\"t\u00009com.sun.identity.liberty.ws.common.jaxb.ac.Gen"
+"erationTypeq\u0000~\u0000&sq\u0000~\u0000\u000eppsq\u0000~\u0000\u0018q\u0000~\u0000\u0012pq\u0000~\u00004q\u0000~\u0000Dq\u0000~\u0000!sq\u0000~\u0000\"t\u0000\n"
+"Generationq\u0000~\u0000Iq\u0000~\u0000!sq\u0000~\u0000\u000eppsq\u0000~\u0000\u000eq\u0000~\u0000\u0012psq\u0000~\u0000\u0000q\u0000~\u0000\u0012p\u0000sq\u0000~\u0000\u000ep"
+"psq\u0000~\u0000\u0015q\u0000~\u0000\u0012psq\u0000~\u0000\u0018q\u0000~\u0000\u0012pq\u0000~\u0000\u001bq\u0000~\u0000\u001fq\u0000~\u0000!sq\u0000~\u0000\"t\u0000Acom.sun.ide"
+"ntity.liberty.ws.common.jaxb.ac.ActivationLimitElementq\u0000~\u0000&s"
+"q\u0000~\u0000\u0000q\u0000~\u0000\u0012p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u000eppsq\u0000~\u0000\u0015q\u0000~\u0000\u0012psq\u0000~\u0000\u0018q\u0000~\u0000\u0012"
+"pq\u0000~\u0000\u001bq\u0000~\u0000\u001fq\u0000~\u0000!sq\u0000~\u0000\"t\u0000>com.sun.identity.liberty.ws.common."
+"jaxb.ac.ActivationLimitTypeq\u0000~\u0000&sq\u0000~\u0000\u000eppsq\u0000~\u0000\u0018q\u0000~\u0000\u0012pq\u0000~\u00004q\u0000~"
+"\u0000Dq\u0000~\u0000!sq\u0000~\u0000\"t\u0000\u000fActivationLimitq\u0000~\u0000Iq\u0000~\u0000!sq\u0000~\u0000\u000eppsq\u0000~\u0000\u0015q\u0000~\u0000\u0012"
+"psq\u0000~\u0000\u000eq\u0000~\u0000\u0012psq\u0000~\u0000\u0000q\u0000~\u0000\u0012p\u0000sq\u0000~\u0000\u000eppsq\u0000~\u0000\u0015q\u0000~\u0000\u0012psq\u0000~\u0000\u0018q\u0000~\u0000\u0012pq\u0000"
+"~\u0000\u001bq\u0000~\u0000\u001fq\u0000~\u0000!sq\u0000~\u0000\"t\u0000;com.sun.identity.liberty.ws.common.jax"
+"b.ac.ExtensionElementq\u0000~\u0000&sq\u0000~\u0000\u0000q\u0000~\u0000\u0012p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~"
+"\u0000\u000eppsq\u0000~\u0000\u0015q\u0000~\u0000\u0012psq\u0000~\u0000\u0018q\u0000~\u0000\u0012pq\u0000~\u0000\u001bq\u0000~\u0000\u001fq\u0000~\u0000!sq\u0000~\u0000\"t\u00008com.sun."
+"identity.liberty.ws.common.jaxb.ac.ExtensionTypeq\u0000~\u0000&sq\u0000~\u0000\u000ep"
+"psq\u0000~\u0000\u0018q\u0000~\u0000\u0012pq\u0000~\u00004q\u0000~\u0000Dq\u0000~\u0000!sq\u0000~\u0000\"t\u0000\tExtensionq\u0000~\u0000Iq\u0000~\u0000!sq\u0000~"
+"\u0000\u000eppsq\u0000~\u0000\u0018q\u0000~\u0000\u0012pq\u0000~\u00004q\u0000~\u0000Dq\u0000~\u0000!sq\u0000~\u0000\"t\u0000\rActivationPinq\u0000~\u0000Isr"
+"\u0000\"com.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000"
+"/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\u0000-com.su"
+"n.msv.grammar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000"
+"\rstreamVersionL\u0000\u0006parentt\u0000$Lcom/sun/msv/grammar/ExpressionPoo"
+"l;xp\u0000\u0000\u0000/\u0001pq\u0000~\u0000Kq\u0000~\u0000_q\u0000~\u0000sq\u0000~\u0000\u0088q\u0000~\u0000\u0086q\u0000~\u0000\u0087q\u0000~\u0000\u0017q\u0000~\u0000+q\u0000~\u0000Nq\u0000~\u0000V"
+"q\u0000~\u0000bq\u0000~\u0000jq\u0000~\u0000vq\u0000~\u0000~q\u0000~\u0000\u008bq\u0000~\u0000\u0093q\u0000~\u0000\u000bq\u0000~\u0000\rq\u0000~\u0000\fq\u0000~\u0000\u0014q\u0000~\u0000*q\u0000~\u0000M"
+"q\u0000~\u0000Uq\u0000~\u0000aq\u0000~\u0000iq\u0000~\u0000uq\u0000~\u0000}q\u0000~\u0000\u008aq\u0000~\u0000\u0092q\u0000~\u0000\tq\u0000~\u0000(q\u0000~\u0000Sq\u0000~\u0000gq\u0000~\u0000{"
+"q\u0000~\u0000\u0090q\u0000~\u0000\nq\u0000~\u0000\u000fq\u0000~\u0000Jq\u0000~\u0000^q\u0000~\u0000rq\u0000~\u0000/q\u0000~\u0000Zq\u0000~\u0000nq\u0000~\u0000\u0082q\u0000~\u0000\u0097q\u0000~\u0000\u009b"
+"q\u0000~\u0000\u0010x"));
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
            return com.sun.identity.liberty.ws.common.jaxb.ac.impl.ActivationPinElementImpl.this;
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
                        if (("Length" == ___local)&&("urn:liberty:ac:2003-08" == ___uri)) {
                            spawnHandlerFromEnterElement((((com.sun.identity.liberty.ws.common.jaxb.ac.impl.ActivationPinTypeImpl)com.sun.identity.liberty.ws.common.jaxb.ac.impl.ActivationPinElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        if (("Length" == ___local)&&("urn:liberty:ac:2003-08" == ___uri)) {
                            spawnHandlerFromEnterElement((((com.sun.identity.liberty.ws.common.jaxb.ac.impl.ActivationPinTypeImpl)com.sun.identity.liberty.ws.common.jaxb.ac.impl.ActivationPinElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        if (("Alphabet" == ___local)&&("urn:liberty:ac:2003-08" == ___uri)) {
                            spawnHandlerFromEnterElement((((com.sun.identity.liberty.ws.common.jaxb.ac.impl.ActivationPinTypeImpl)com.sun.identity.liberty.ws.common.jaxb.ac.impl.ActivationPinElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        if (("Alphabet" == ___local)&&("urn:liberty:ac:2003-08" == ___uri)) {
                            spawnHandlerFromEnterElement((((com.sun.identity.liberty.ws.common.jaxb.ac.impl.ActivationPinTypeImpl)com.sun.identity.liberty.ws.common.jaxb.ac.impl.ActivationPinElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        if (("Generation" == ___local)&&("urn:liberty:ac:2003-08" == ___uri)) {
                            spawnHandlerFromEnterElement((((com.sun.identity.liberty.ws.common.jaxb.ac.impl.ActivationPinTypeImpl)com.sun.identity.liberty.ws.common.jaxb.ac.impl.ActivationPinElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        if (("Generation" == ___local)&&("urn:liberty:ac:2003-08" == ___uri)) {
                            spawnHandlerFromEnterElement((((com.sun.identity.liberty.ws.common.jaxb.ac.impl.ActivationPinTypeImpl)com.sun.identity.liberty.ws.common.jaxb.ac.impl.ActivationPinElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        if (("ActivationLimit" == ___local)&&("urn:liberty:ac:2003-08" == ___uri)) {
                            spawnHandlerFromEnterElement((((com.sun.identity.liberty.ws.common.jaxb.ac.impl.ActivationPinTypeImpl)com.sun.identity.liberty.ws.common.jaxb.ac.impl.ActivationPinElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        if (("ActivationLimit" == ___local)&&("urn:liberty:ac:2003-08" == ___uri)) {
                            spawnHandlerFromEnterElement((((com.sun.identity.liberty.ws.common.jaxb.ac.impl.ActivationPinTypeImpl)com.sun.identity.liberty.ws.common.jaxb.ac.impl.ActivationPinElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        if (("Extension" == ___local)&&("urn:liberty:ac:2003-08" == ___uri)) {
                            spawnHandlerFromEnterElement((((com.sun.identity.liberty.ws.common.jaxb.ac.impl.ActivationPinTypeImpl)com.sun.identity.liberty.ws.common.jaxb.ac.impl.ActivationPinElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        if (("Extension" == ___local)&&("urn:liberty:ac:2003-08" == ___uri)) {
                            spawnHandlerFromEnterElement((((com.sun.identity.liberty.ws.common.jaxb.ac.impl.ActivationPinTypeImpl)com.sun.identity.liberty.ws.common.jaxb.ac.impl.ActivationPinElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        spawnHandlerFromEnterElement((((com.sun.identity.liberty.ws.common.jaxb.ac.impl.ActivationPinTypeImpl)com.sun.identity.liberty.ws.common.jaxb.ac.impl.ActivationPinElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                        return ;
                    case  0 :
                        if (("ActivationPin" == ___local)&&("urn:liberty:ac:2003-08" == ___uri)) {
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
                        spawnHandlerFromLeaveElement((((com.sun.identity.liberty.ws.common.jaxb.ac.impl.ActivationPinTypeImpl)com.sun.identity.liberty.ws.common.jaxb.ac.impl.ActivationPinElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
                        return ;
                    case  2 :
                        if (("ActivationPin" == ___local)&&("urn:liberty:ac:2003-08" == ___uri)) {
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
                        spawnHandlerFromEnterAttribute((((com.sun.identity.liberty.ws.common.jaxb.ac.impl.ActivationPinTypeImpl)com.sun.identity.liberty.ws.common.jaxb.ac.impl.ActivationPinElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
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
                    case  3 :
                        revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
                        return ;
                    case  1 :
                        spawnHandlerFromLeaveAttribute((((com.sun.identity.liberty.ws.common.jaxb.ac.impl.ActivationPinTypeImpl)com.sun.identity.liberty.ws.common.jaxb.ac.impl.ActivationPinElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
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
                        case  3 :
                            revertToParentFromText(value);
                            return ;
                        case  1 :
                            spawnHandlerFromText((((com.sun.identity.liberty.ws.common.jaxb.ac.impl.ActivationPinTypeImpl)com.sun.identity.liberty.ws.common.jaxb.ac.impl.ActivationPinElementImpl.this).new Unmarshaller(context)), 2, value);
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
