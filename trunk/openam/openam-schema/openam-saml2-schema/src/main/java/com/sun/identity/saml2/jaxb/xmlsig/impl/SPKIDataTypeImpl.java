//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:34:07 AM PDT 
//


package com.sun.identity.saml2.jaxb.xmlsig.impl;

public class SPKIDataTypeImpl implements com.sun.identity.saml2.jaxb.xmlsig.SPKIDataType, com.sun.xml.bind.JAXBObject, com.sun.identity.saml2.jaxb.assertion.impl.runtime.UnmarshallableObject, com.sun.identity.saml2.jaxb.assertion.impl.runtime.XMLSerializable, com.sun.identity.saml2.jaxb.assertion.impl.runtime.ValidatableObject
{

    protected com.sun.xml.bind.util.ListImpl _SPKISexpAndAny;
    public final static java.lang.Class version = (com.sun.identity.saml2.jaxb.xmlsig.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (com.sun.identity.saml2.jaxb.xmlsig.SPKIDataType.class);
    }

    protected com.sun.xml.bind.util.ListImpl _getSPKISexpAndAny() {
        if (_SPKISexpAndAny == null) {
            _SPKISexpAndAny = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
        }
        return _SPKISexpAndAny;
    }

    public java.util.List getSPKISexpAndAny() {
        return _getSPKISexpAndAny();
    }

    public com.sun.identity.saml2.jaxb.assertion.impl.runtime.UnmarshallingEventHandler createUnmarshaller(com.sun.identity.saml2.jaxb.assertion.impl.runtime.UnmarshallingContext context) {
        return new com.sun.identity.saml2.jaxb.xmlsig.impl.SPKIDataTypeImpl.Unmarshaller(context);
    }

    public void serializeBody(com.sun.identity.saml2.jaxb.assertion.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = ((_SPKISexpAndAny == null)? 0 :_SPKISexpAndAny.size());
        while (idx1 != len1) {
            {
                java.lang.Object o = _SPKISexpAndAny.get(idx1);
                if (o instanceof com.sun.xml.bind.JAXBObject) {
                    context.childAsBody(((com.sun.xml.bind.JAXBObject) _SPKISexpAndAny.get(idx1 ++)), "SPKISexpAndAny");
                } else {
                    if (o instanceof java.lang.Object) {
                        context.childAsBody(((com.sun.xml.bind.JAXBObject) _SPKISexpAndAny.get(idx1 ++)), "SPKISexpAndAny");
                    } else {
                        com.sun.identity.saml2.jaxb.assertion.impl.runtime.Util.handleTypeMismatchError(context, this, "SPKISexpAndAny", o);
                    }
                }
            }
            for (int _0 = 1; ((_0 > 0)&&(idx1 != len1)); _0 --) {
                {
                    java.lang.Object o = _SPKISexpAndAny.get(idx1);
                    if (o instanceof com.sun.xml.bind.JAXBObject) {
                        context.childAsBody(((com.sun.xml.bind.JAXBObject) _SPKISexpAndAny.get(idx1 ++)), "SPKISexpAndAny");
                    } else {
                        if (o instanceof java.lang.Object) {
                            context.childAsBody(((com.sun.xml.bind.JAXBObject) _SPKISexpAndAny.get(idx1 ++)), "SPKISexpAndAny");
                        } else {
                            com.sun.identity.saml2.jaxb.assertion.impl.runtime.Util.handleTypeMismatchError(context, this, "SPKISexpAndAny", o);
                        }
                    }
                }
            }
        }
    }

    public void serializeAttributes(com.sun.identity.saml2.jaxb.assertion.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = ((_SPKISexpAndAny == null)? 0 :_SPKISexpAndAny.size());
        while (idx1 != len1) {
            {
                java.lang.Object o = _SPKISexpAndAny.get(idx1);
                if (o instanceof com.sun.xml.bind.JAXBObject) {
                    context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _SPKISexpAndAny.get(idx1 ++)), "SPKISexpAndAny");
                } else {
                    if (o instanceof java.lang.Object) {
                        idx1 += 1;
                    } else {
                        com.sun.identity.saml2.jaxb.assertion.impl.runtime.Util.handleTypeMismatchError(context, this, "SPKISexpAndAny", o);
                    }
                }
            }
            for (int _0 = 1; ((_0 > 0)&&(idx1 != len1)); _0 --) {
                {
                    java.lang.Object o = _SPKISexpAndAny.get(idx1);
                    if (o instanceof com.sun.xml.bind.JAXBObject) {
                        context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _SPKISexpAndAny.get(idx1 ++)), "SPKISexpAndAny");
                    } else {
                        if (o instanceof java.lang.Object) {
                            idx1 += 1;
                        } else {
                            com.sun.identity.saml2.jaxb.assertion.impl.runtime.Util.handleTypeMismatchError(context, this, "SPKISexpAndAny", o);
                        }
                    }
                }
            }
        }
    }

    public void serializeURIs(com.sun.identity.saml2.jaxb.assertion.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx1 = 0;
        final int len1 = ((_SPKISexpAndAny == null)? 0 :_SPKISexpAndAny.size());
        while (idx1 != len1) {
            {
                java.lang.Object o = _SPKISexpAndAny.get(idx1);
                if (o instanceof com.sun.xml.bind.JAXBObject) {
                    context.childAsURIs(((com.sun.xml.bind.JAXBObject) _SPKISexpAndAny.get(idx1 ++)), "SPKISexpAndAny");
                } else {
                    if (o instanceof java.lang.Object) {
                        idx1 += 1;
                    } else {
                        com.sun.identity.saml2.jaxb.assertion.impl.runtime.Util.handleTypeMismatchError(context, this, "SPKISexpAndAny", o);
                    }
                }
            }
            for (int _0 = 1; ((_0 > 0)&&(idx1 != len1)); _0 --) {
                {
                    java.lang.Object o = _SPKISexpAndAny.get(idx1);
                    if (o instanceof com.sun.xml.bind.JAXBObject) {
                        context.childAsURIs(((com.sun.xml.bind.JAXBObject) _SPKISexpAndAny.get(idx1 ++)), "SPKISexpAndAny");
                    } else {
                        if (o instanceof java.lang.Object) {
                            idx1 += 1;
                        } else {
                            com.sun.identity.saml2.jaxb.assertion.impl.runtime.Util.handleTypeMismatchError(context, this, "SPKISexpAndAny", o);
                        }
                    }
                }
            }
        }
    }

    public java.lang.Class getPrimaryInterface() {
        return (com.sun.identity.saml2.jaxb.xmlsig.SPKIDataType.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        if (schemaFragment == null) {
            schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
 "\u00ac\u00ed\u0000\u0005sr\u0000 com.sun.msv.grammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.s"
+"un.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expt\u0000 Lcom/sun/msv/gram"
+"mar/Expression;xr\u0000\u001ecom.sun.msv.grammar.Expression\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002"
+"L\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000bexpandedExpq\u0000"
+"~\u0000\u0002xpppsr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom"
+".sun.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1q\u0000~\u0000\u0002L\u0000\u0004exp2q\u0000~\u0000"
+"\u0002xq\u0000~\u0000\u0003ppsr\u0000\'com.sun.msv.grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001"
+"\u0002\u0000\u0001L\u0000\tnameClasst\u0000\u001fLcom/sun/msv/grammar/NameClass;xr\u0000\u001ecom.sun"
+".msv.grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttribu"
+"tesL\u0000\fcontentModelq\u0000~\u0000\u0002xq\u0000~\u0000\u0003pp\u0000sr\u0000\u001dcom.sun.msv.grammar.Choi"
+"ceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002"
+"\u0000\u0001Z\u0000\u0005valuexp\u0000psr\u0000 com.sun.msv.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000"
+"\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\tnameClassq\u0000~\u0000\nxq\u0000~\u0000\u0003q\u0000~\u0000\u0011psr\u00002com.sun.msv.gra"
+"mmar.Expression$AnyStringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003sq\u0000~\u0000\u0010\u0001q"
+"\u0000~\u0000\u0015sr\u0000 com.sun.msv.grammar.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.s"
+"un.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar"
+".Expression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003q\u0000~\u0000\u0016q\u0000~\u0000\u001bsr\u0000#"
+"com.sun.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNamet\u0000"
+"\u0012Ljava/lang/String;L\u0000\fnamespaceURIq\u0000~\u0000\u001dxq\u0000~\u0000\u0018t\u00008com.sun.iden"
+"tity.saml2.jaxb.xmlsig.SPKIDataType.SPKISexpt\u0000+http://java.s"
+"un.com/jaxb/xjc/dummy-elementssq\u0000~\u0000\rppsq\u0000~\u0000\tq\u0000~\u0000\u0011p\u0000sq\u0000~\u0000\u0012ppq"
+"\u0000~\u0000\u0015sr\u0000\'com.sun.msv.grammar.DifferenceNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000"
+"\u0003nc1q\u0000~\u0000\nL\u0000\u0003nc2q\u0000~\u0000\nxq\u0000~\u0000\u0018q\u0000~\u0000\u0019sr\u0000#com.sun.msv.grammar.Choic"
+"eNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003nc1q\u0000~\u0000\nL\u0000\u0003nc2q\u0000~\u0000\nxq\u0000~\u0000\u0018sr\u0000&com.sun"
+".msv.grammar.NamespaceNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\fnamespaceURIq\u0000~"
+"\u0000\u001dxq\u0000~\u0000\u0018t\u0000\u0000sq\u0000~\u0000(t\u0000\"http://www.w3.org/2000/09/xmldsig#sq\u0000~\u0000("
+"q\u0000~\u0000 q\u0000~\u0000\u001bsr\u0000\"com.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L"
+"\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;"
+"xpsr\u0000-com.sun.msv.grammar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003"
+"\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$Lcom/sun/msv/grammar/E"
+"xpressionPool;xp\u0000\u0000\u0000\u0005\u0001pq\u0000~\u0000\u000eq\u0000~\u0000\bq\u0000~\u0000\u000fq\u0000~\u0000!q\u0000~\u0000\u0005x"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public static class SPKISexpImpl implements com.sun.identity.saml2.jaxb.xmlsig.SPKIDataType.SPKISexp, com.sun.xml.bind.RIElement, com.sun.xml.bind.JAXBObject, com.sun.identity.saml2.jaxb.assertion.impl.runtime.UnmarshallableObject, com.sun.identity.saml2.jaxb.assertion.impl.runtime.XMLSerializable, com.sun.identity.saml2.jaxb.assertion.impl.runtime.ValidatableObject
    {

        protected byte[] _Value;
        public final static java.lang.Class version = (com.sun.identity.saml2.jaxb.xmlsig.impl.JAXBVersion.class);
        private static com.sun.msv.grammar.Grammar schemaFragment;

        public SPKISexpImpl() {
        }

        public SPKISexpImpl(byte[] value) {
            _Value = value;
        }

        private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
            return (com.sun.identity.saml2.jaxb.xmlsig.SPKIDataType.SPKISexp.class);
        }

        public java.lang.String ____jaxb_ri____getNamespaceURI() {
            return "http://www.w3.org/2000/09/xmldsig#";
        }

        public java.lang.String ____jaxb_ri____getLocalName() {
            return "SPKISexp";
        }

        public byte[] getValue() {
            return _Value;
        }

        public void setValue(byte[] value) {
            _Value = value;
        }

        public com.sun.identity.saml2.jaxb.assertion.impl.runtime.UnmarshallingEventHandler createUnmarshaller(com.sun.identity.saml2.jaxb.assertion.impl.runtime.UnmarshallingContext context) {
            return new com.sun.identity.saml2.jaxb.xmlsig.impl.SPKIDataTypeImpl.SPKISexpImpl.Unmarshaller(context);
        }

        public void serializeBody(com.sun.identity.saml2.jaxb.assertion.impl.runtime.XMLSerializer context)
            throws org.xml.sax.SAXException
        {
            context.startElement("http://www.w3.org/2000/09/xmldsig#", "SPKISexp");
            context.endNamespaceDecls();
            context.endAttributes();
            try {
                context.text(com.sun.msv.datatype.xsd.Base64BinaryType.save(((byte[]) _Value)), "Value");
            } catch (java.lang.Exception e) {
                com.sun.identity.saml2.jaxb.assertion.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
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
            return (com.sun.identity.saml2.jaxb.xmlsig.SPKIDataType.SPKISexp.class);
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
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1q\u0000~\u0000\u0003L\u0000\u0004exp2q\u0000~\u0000\u0003xq\u0000~\u0000\u0004ppsr\u0000\u001bcom.sun.msv.g"
+"rammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datatype/Datat"
+"ype;L\u0000\u0006exceptq\u0000~\u0000\u0003L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/StringPair;xq\u0000"
+"~\u0000\u0004ppsr\u0000)com.sun.msv.datatype.xsd.Base64BinaryType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000"
+"\u0000xr\u0000\'com.sun.msv.datatype.xsd.BinaryBaseType\u00a7\u00ce\u000e\u0097^\u00afW\u0011\u0002\u0000\u0000xr\u0000*c"
+"om.sun.msv.datatype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com."
+"sun.msv.datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv."
+"datatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUrit\u0000\u0012Ljav"
+"a/lang/String;L\u0000\btypeNameq\u0000~\u0000\u0013L\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/d"
+"atatype/xsd/WhiteSpaceProcessor;xpt\u0000 http://www.w3.org/2001/"
+"XMLSchemat\u0000\fbase64Binarysr\u00005com.sun.msv.datatype.xsd.WhiteSp"
+"aceProcessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd"
+".WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Exp"
+"ression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004sr\u0000\u0011java.lang.Bool"
+"ean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psr\u0000\u001bcom.sun.msv.util.StringPair\u00d0t\u001e"
+"jB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u0013L\u0000\fnamespaceURIq\u0000~\u0000\u0013xpq\u0000~\u0000\u0017q\u0000~\u0000\u0016sr\u0000"
+"\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\bppsr\u0000 com.sun"
+".msv.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0003L\u0000\tnameClassq"
+"\u0000~\u0000\u0001xq\u0000~\u0000\u0004q\u0000~\u0000\u001epsq\u0000~\u0000\nppsr\u0000\"com.sun.msv.datatype.xsd.QnameTy"
+"pe\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0010q\u0000~\u0000\u0016t\u0000\u0005QNameq\u0000~\u0000\u001aq\u0000~\u0000\u001csq\u0000~\u0000\u001fq\u0000~\u0000(q\u0000~\u0000\u0016sr"
+"\u0000#com.sun.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalName"
+"q\u0000~\u0000\u0013L\u0000\fnamespaceURIq\u0000~\u0000\u0013xr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpt\u0000\u0004typet\u0000)http://www.w3.org/2001/XMLSchema-instan"
+"cesr\u00000com.sun.msv.grammar.Expression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004sq\u0000~\u0000\u001d\u0001q\u0000~\u00000sq\u0000~\u0000*t\u0000\bSPKISexpt\u0000\"http://www.w3.org"
+"/2000/09/xmldsig#sr\u0000\"com.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/ExpressionPool$Clos"
+"edHash;xpsr\u0000-com.sun.msv.grammar.ExpressionPool$ClosedHash\u00d7j"
+"\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$Lcom/sun/msv/gr"
+"ammar/ExpressionPool;xp\u0000\u0000\u0000\u0002\u0001pq\u0000~\u0000\"q\u0000~\u0000\tx"));
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
                return com.sun.identity.saml2.jaxb.xmlsig.impl.SPKIDataTypeImpl.SPKISexpImpl.this;
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
                        case  0 :
                            if (("SPKISexp" == ___local)&&("http://www.w3.org/2000/09/xmldsig#" == ___uri)) {
                                context.pushAttributes(__atts, true);
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
                            if (("SPKISexp" == ___local)&&("http://www.w3.org/2000/09/xmldsig#" == ___uri)) {
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
                            case  1 :
                                state = 2;
                                eatText1(value);
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
                    _Value = com.sun.msv.datatype.xsd.Base64BinaryType.load(com.sun.xml.bind.WhiteSpaceProcessor.collapse(value));
                } catch (java.lang.Exception e) {
                    handleParseConversionException(e);
                }
            }

        }

    }

    public class Unmarshaller
        extends com.sun.identity.saml2.jaxb.assertion.impl.runtime.AbstractUnmarshallingEventHandlerImpl
    {


        public Unmarshaller(com.sun.identity.saml2.jaxb.assertion.impl.runtime.UnmarshallingContext context) {
            super(context, "---");
        }

        protected Unmarshaller(com.sun.identity.saml2.jaxb.assertion.impl.runtime.UnmarshallingContext context, int startState) {
            this(context);
            state = startState;
        }

        public java.lang.Object owner() {
            return com.sun.identity.saml2.jaxb.xmlsig.impl.SPKIDataTypeImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  1 :
                        if (!(("" == ___uri)||("http://www.w3.org/2000/09/xmldsig#" == ___uri))) {
                            java.lang.Object co = spawnWildcard(2, ___uri, ___local, ___qname, __atts);
                            if (co!= null) {
                                _getSPKISexpAndAny().add(co);
                            }
                            return ;
                        }
                        state = 2;
                        continue outer;
                    case  2 :
                        if (("SPKISexp" == ___local)&&("http://www.w3.org/2000/09/xmldsig#" == ___uri)) {
                            _getSPKISexpAndAny().add(((com.sun.identity.saml2.jaxb.xmlsig.impl.SPKIDataTypeImpl.SPKISexpImpl) spawnChildFromEnterElement((com.sun.identity.saml2.jaxb.xmlsig.impl.SPKIDataTypeImpl.SPKISexpImpl.class), 1, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
                    case  0 :
                        if (("SPKISexp" == ___local)&&("http://www.w3.org/2000/09/xmldsig#" == ___uri)) {
                            _getSPKISexpAndAny().add(((com.sun.identity.saml2.jaxb.xmlsig.impl.SPKIDataTypeImpl.SPKISexpImpl) spawnChildFromEnterElement((com.sun.identity.saml2.jaxb.xmlsig.impl.SPKIDataTypeImpl.SPKISexpImpl.class), 1, ___uri, ___local, ___qname, __atts)));
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
                    case  1 :
                        state = 2;
                        continue outer;
                    case  2 :
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
                        state = 2;
                        continue outer;
                    case  2 :
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
                        state = 2;
                        continue outer;
                    case  2 :
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
                            state = 2;
                            continue outer;
                        case  2 :
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
