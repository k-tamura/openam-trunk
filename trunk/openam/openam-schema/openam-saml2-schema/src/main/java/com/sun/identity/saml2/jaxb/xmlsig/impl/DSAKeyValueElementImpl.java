//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:34:07 AM PDT 
//


package com.sun.identity.saml2.jaxb.xmlsig.impl;

public class DSAKeyValueElementImpl
    extends com.sun.identity.saml2.jaxb.xmlsig.impl.DSAKeyValueTypeImpl
    implements com.sun.identity.saml2.jaxb.xmlsig.DSAKeyValueElement, com.sun.xml.bind.RIElement, com.sun.xml.bind.JAXBObject, com.sun.identity.saml2.jaxb.assertion.impl.runtime.UnmarshallableObject, com.sun.identity.saml2.jaxb.assertion.impl.runtime.XMLSerializable, com.sun.identity.saml2.jaxb.assertion.impl.runtime.ValidatableObject
{

    public final static java.lang.Class version = (com.sun.identity.saml2.jaxb.xmlsig.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (com.sun.identity.saml2.jaxb.xmlsig.DSAKeyValueElement.class);
    }

    public java.lang.String ____jaxb_ri____getNamespaceURI() {
        return "http://www.w3.org/2000/09/xmldsig#";
    }

    public java.lang.String ____jaxb_ri____getLocalName() {
        return "DSAKeyValue";
    }

    public com.sun.identity.saml2.jaxb.assertion.impl.runtime.UnmarshallingEventHandler createUnmarshaller(com.sun.identity.saml2.jaxb.assertion.impl.runtime.UnmarshallingContext context) {
        return new com.sun.identity.saml2.jaxb.xmlsig.impl.DSAKeyValueElementImpl.Unmarshaller(context);
    }

    public void serializeBody(com.sun.identity.saml2.jaxb.assertion.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        context.startElement("http://www.w3.org/2000/09/xmldsig#", "DSAKeyValue");
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
        return (com.sun.identity.saml2.jaxb.xmlsig.DSAKeyValueElement.class);
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
+"q\u0000~\u0000\bppsq\u0000~\u0000\u0007sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psq\u0000"
+"~\u0000\u0000q\u0000~\u0000\u0012p\u0000sq\u0000~\u0000\u0007ppsr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003"
+"L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0003L\u0000\u0004name"
+"t\u0000\u001dLcom/sun/msv/util/StringPair;xq\u0000~\u0000\u0004ppsr\u0000\'com.sun.msv.data"
+"type.xsd.FinalComponent\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001I\u0000\nfinalValuexr\u0000\u001ecom.sun.m"
+"sv.datatype.xsd.Proxy\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bbaseTypet\u0000)Lcom/sun/msv/d"
+"atatype/xsd/XSDatatypeImpl;xr\u0000\'com.sun.msv.datatype.xsd.XSDa"
+"tatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUrit\u0000\u0012Ljava/lang/String;L\u0000\b"
+"typeNameq\u0000~\u0000\u001dL\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/datatype/xsd/White"
+"SpaceProcessor;xpt\u0000\"http://www.w3.org/2000/09/xmldsig#t\u0000\fCry"
+"ptoBinarysr\u00005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Co"
+"llapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd.WhiteSpaceProc"
+"essor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u0000)com.sun.msv.datatype.xsd.Base64BinaryT"
+"ype\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd.BinaryBaseType\u00a7\u00ce\u000e"
+"\u0097^\u00afW\u0011\u0002\u0000\u0000xr\u0000*com.sun.msv.datatype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000"
+"~\u0000\u001ct\u0000 http://www.w3.org/2001/XMLSchemat\u0000\fbase64Binaryq\u0000~\u0000$\u0000\u0000"
+"\u0000\u0000sr\u00000com.sun.msv.grammar.Expression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004q\u0000~\u0000\u0012psr\u0000\u001bcom.sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L"
+"\u0000\tlocalNameq\u0000~\u0000\u001dL\u0000\fnamespaceURIq\u0000~\u0000\u001dxpq\u0000~\u0000+q\u0000~\u0000 sq\u0000~\u0000\u000eppsr\u0000 "
+"com.sun.msv.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0003L\u0000\tnam"
+"eClassq\u0000~\u0000\u0001xq\u0000~\u0000\u0004q\u0000~\u0000\u0012psq\u0000~\u0000\u0015ppsr\u0000\"com.sun.msv.datatype.xsd."
+"QnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\'q\u0000~\u0000*t\u0000\u0005QNameq\u0000~\u0000$q\u0000~\u0000-sq\u0000~\u0000.q\u0000~\u00006"
+"q\u0000~\u0000*sr\u0000#com.sun.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlo"
+"calNameq\u0000~\u0000\u001dL\u0000\fnamespaceURIq\u0000~\u0000\u001dxr\u0000\u001dcom.sun.msv.grammar.Name"
+"Class\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpt\u0000\u0004typet\u0000)http://www.w3.org/2001/XMLSchema"
+"-instancesr\u00000com.sun.msv.grammar.Expression$EpsilonExpressio"
+"n\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004sq\u0000~\u0000\u0011\u0001q\u0000~\u0000>sq\u0000~\u00008t\u0000\u0001Pq\u0000~\u0000 sq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0007"
+"ppq\u0000~\u0000\u0018sq\u0000~\u0000\u000eppsq\u0000~\u00001q\u0000~\u0000\u0012pq\u0000~\u00003q\u0000~\u0000:q\u0000~\u0000>sq\u0000~\u00008t\u0000\u0001Qq\u0000~\u0000 q\u0000~"
+"\u0000>sq\u0000~\u0000\u000eppsq\u0000~\u0000\u0000q\u0000~\u0000\u0012p\u0000sq\u0000~\u0000\u0007ppq\u0000~\u0000\u0018sq\u0000~\u0000\u000eppsq\u0000~\u00001q\u0000~\u0000\u0012pq\u0000~\u0000"
+"3q\u0000~\u0000:q\u0000~\u0000>sq\u0000~\u00008t\u0000\u0001Gq\u0000~\u0000 q\u0000~\u0000>sq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\u0007ppq\u0000~\u0000\u0018sq\u0000~\u0000\u000ep"
+"psq\u0000~\u00001q\u0000~\u0000\u0012pq\u0000~\u00003q\u0000~\u0000:q\u0000~\u0000>sq\u0000~\u00008t\u0000\u0001Yq\u0000~\u0000 sq\u0000~\u0000\u000eppsq\u0000~\u0000\u0000q\u0000~"
+"\u0000\u0012p\u0000sq\u0000~\u0000\u0007ppq\u0000~\u0000\u0018sq\u0000~\u0000\u000eppsq\u0000~\u00001q\u0000~\u0000\u0012pq\u0000~\u00003q\u0000~\u0000:q\u0000~\u0000>sq\u0000~\u00008t\u0000"
+"\u0001Jq\u0000~\u0000 q\u0000~\u0000>sq\u0000~\u0000\u000eppsq\u0000~\u0000\u0007q\u0000~\u0000\u0012psq\u0000~\u0000\u0000q\u0000~\u0000\u0012p\u0000sq\u0000~\u0000\u0007ppq\u0000~\u0000\u0018sq"
+"\u0000~\u0000\u000eppsq\u0000~\u00001q\u0000~\u0000\u0012pq\u0000~\u00003q\u0000~\u0000:q\u0000~\u0000>sq\u0000~\u00008t\u0000\u0004Seedq\u0000~\u0000 sq\u0000~\u0000\u0000pp\u0000"
+"sq\u0000~\u0000\u0007ppq\u0000~\u0000\u0018sq\u0000~\u0000\u000eppsq\u0000~\u00001q\u0000~\u0000\u0012pq\u0000~\u00003q\u0000~\u0000:q\u0000~\u0000>sq\u0000~\u00008t\u0000\u000bPge"
+"nCounterq\u0000~\u0000 q\u0000~\u0000>sq\u0000~\u0000\u000eppsq\u0000~\u00001q\u0000~\u0000\u0012pq\u0000~\u00003q\u0000~\u0000:q\u0000~\u0000>sq\u0000~\u00008t"
+"\u0000\u000bDSAKeyValueq\u0000~\u0000 sr\u0000\"com.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000"
+"\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/ExpressionPool$Clo"
+"sedHash;xpsr\u0000-com.sun.msv.grammar.ExpressionPool$ClosedHash\u00d7"
+"j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$Lcom/sun/msv/g"
+"rammar/ExpressionPool;xp\u0000\u0000\u0000\u001a\u0001pq\u0000~\u0000\fq\u0000~\u0000\tq\u0000~\u0000Hq\u0000~\u0000Uq\u0000~\u0000\nq\u0000~\u0000\u0014"
+"q\u0000~\u0000Cq\u0000~\u0000Jq\u0000~\u0000Pq\u0000~\u0000Wq\u0000~\u0000_q\u0000~\u0000eq\u0000~\u0000\u000bq\u0000~\u0000\u0010q\u0000~\u0000]q\u0000~\u00000q\u0000~\u0000Dq\u0000~\u0000K"
+"q\u0000~\u0000Qq\u0000~\u0000Xq\u0000~\u0000`q\u0000~\u0000fq\u0000~\u0000jq\u0000~\u0000\u000fq\u0000~\u0000\\q\u0000~\u0000\rx"));
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
            return com.sun.identity.saml2.jaxb.xmlsig.impl.DSAKeyValueElementImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  1 :
                        if (("P" == ___local)&&("http://www.w3.org/2000/09/xmldsig#" == ___uri)) {
                            spawnHandlerFromEnterElement((((com.sun.identity.saml2.jaxb.xmlsig.impl.DSAKeyValueTypeImpl)com.sun.identity.saml2.jaxb.xmlsig.impl.DSAKeyValueElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        if (("G" == ___local)&&("http://www.w3.org/2000/09/xmldsig#" == ___uri)) {
                            spawnHandlerFromEnterElement((((com.sun.identity.saml2.jaxb.xmlsig.impl.DSAKeyValueTypeImpl)com.sun.identity.saml2.jaxb.xmlsig.impl.DSAKeyValueElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        if (("Y" == ___local)&&("http://www.w3.org/2000/09/xmldsig#" == ___uri)) {
                            spawnHandlerFromEnterElement((((com.sun.identity.saml2.jaxb.xmlsig.impl.DSAKeyValueTypeImpl)com.sun.identity.saml2.jaxb.xmlsig.impl.DSAKeyValueElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        break;
                    case  3 :
                        revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
                    case  0 :
                        if (("DSAKeyValue" == ___local)&&("http://www.w3.org/2000/09/xmldsig#" == ___uri)) {
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
                    case  2 :
                        if (("DSAKeyValue" == ___local)&&("http://www.w3.org/2000/09/xmldsig#" == ___uri)) {
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
                    }
                } catch (java.lang.RuntimeException e) {
                    handleUnexpectedTextException(value, e);
                }
                break;
            }
        }

    }

}
