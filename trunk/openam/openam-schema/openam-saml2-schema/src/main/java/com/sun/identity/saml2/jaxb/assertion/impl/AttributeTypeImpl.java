//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:34:07 AM PDT 
//


package com.sun.identity.saml2.jaxb.assertion.impl;

public class AttributeTypeImpl implements com.sun.identity.saml2.jaxb.assertion.AttributeType, com.sun.xml.bind.JAXBObject, com.sun.identity.saml2.jaxb.assertion.impl.runtime.UnmarshallableObject, com.sun.identity.saml2.jaxb.assertion.impl.runtime.XMLSerializable, com.sun.identity.saml2.jaxb.assertion.impl.runtime.ValidatableObject
{

    protected java.lang.String _Name;
    protected java.lang.String _NameFormat;
    protected java.lang.String _FriendlyName;
    protected com.sun.xml.bind.util.ListImpl _AttributeValue;
    public final static java.lang.Class version = (com.sun.identity.saml2.jaxb.assertion.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (com.sun.identity.saml2.jaxb.assertion.AttributeType.class);
    }

    public java.lang.String getName() {
        return _Name;
    }

    public void setName(java.lang.String value) {
        _Name = value;
    }

    public java.lang.String getNameFormat() {
        return _NameFormat;
    }

    public void setNameFormat(java.lang.String value) {
        _NameFormat = value;
    }

    public java.lang.String getFriendlyName() {
        return _FriendlyName;
    }

    public void setFriendlyName(java.lang.String value) {
        _FriendlyName = value;
    }

    protected com.sun.xml.bind.util.ListImpl _getAttributeValue() {
        if (_AttributeValue == null) {
            _AttributeValue = new com.sun.xml.bind.util.ListImpl(new java.util.ArrayList());
        }
        return _AttributeValue;
    }

    public java.util.List getAttributeValue() {
        return _getAttributeValue();
    }

    public com.sun.identity.saml2.jaxb.assertion.impl.runtime.UnmarshallingEventHandler createUnmarshaller(com.sun.identity.saml2.jaxb.assertion.impl.runtime.UnmarshallingContext context) {
        return new com.sun.identity.saml2.jaxb.assertion.impl.AttributeTypeImpl.Unmarshaller(context);
    }

    public void serializeBody(com.sun.identity.saml2.jaxb.assertion.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx4 = 0;
        final int len4 = ((_AttributeValue == null)? 0 :_AttributeValue.size());
        while (idx4 != len4) {
            if (_AttributeValue.get(idx4) instanceof com.sun.identity.saml2.jaxb.assertion.AttributeValueElement) {
                context.childAsBody(((com.sun.xml.bind.JAXBObject) _AttributeValue.get(idx4 ++)), "AttributeValue");
            } else {
                if ((_AttributeValue.get(idx4) == null)||(_AttributeValue.get(idx4) instanceof com.sun.identity.saml2.jaxb.schema.AnyType)) {
                    context.startElement("urn:oasis:names:tc:SAML:2.0:assertion", "AttributeValue");
                    int idx_0 = idx4;
                    if (_AttributeValue.get(idx_0) == null) {
                        context.getNamespaceContext().declareNamespace("http://www.w3.org/2001/XMLSchema-instance", null, true);
                        try {
                            idx_0 += 1;
                        } catch (java.lang.Exception e) {
                            com.sun.identity.saml2.jaxb.assertion.impl.runtime.Util.handlePrintConversionException(this, e, context);
                        }
                    } else {
                        if (_AttributeValue.get(idx_0) instanceof com.sun.identity.saml2.jaxb.schema.AnyType) {
                            context.childAsURIs(((com.sun.xml.bind.JAXBObject) _AttributeValue.get(idx_0 ++)), "AttributeValue");
                        } else {
                            com.sun.identity.saml2.jaxb.assertion.impl.runtime.Util.handleTypeMismatchError(context, this, "AttributeValue", _AttributeValue.get(idx_0));
                        }
                    }
                    context.endNamespaceDecls();
                    int idx_1 = idx4;
                    if (_AttributeValue.get(idx_1) == null) {
                        context.startAttribute("http://www.w3.org/2001/XMLSchema-instance", "nil");
                        try {
                            context.text("true", "AttributeValue");
                        } catch (java.lang.Exception e) {
                            com.sun.identity.saml2.jaxb.assertion.impl.runtime.Util.handlePrintConversionException(this, e, context);
                        }
                        context.endAttribute();
                    } else {
                        if (_AttributeValue.get(idx_1) instanceof com.sun.identity.saml2.jaxb.schema.AnyType) {
                            context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _AttributeValue.get(idx_1 ++)), "AttributeValue");
                        } else {
                            com.sun.identity.saml2.jaxb.assertion.impl.runtime.Util.handleTypeMismatchError(context, this, "AttributeValue", _AttributeValue.get(idx_1));
                        }
                    }
                    context.endAttributes();
                    if (!(_AttributeValue.get(idx4) == null)) {
                        if (_AttributeValue.get(idx4) instanceof com.sun.identity.saml2.jaxb.schema.AnyType) {
                            context.childAsBody(((com.sun.xml.bind.JAXBObject) _AttributeValue.get(idx4 ++)), "AttributeValue");
                        } else {
                            com.sun.identity.saml2.jaxb.assertion.impl.runtime.Util.handleTypeMismatchError(context, this, "AttributeValue", _AttributeValue.get(idx4));
                        }
                    }
                    context.endElement();
                } else {
                    com.sun.identity.saml2.jaxb.assertion.impl.runtime.Util.handleTypeMismatchError(context, this, "AttributeValue", _AttributeValue.get(idx4));
                }
            }
        }
    }

    public void serializeAttributes(com.sun.identity.saml2.jaxb.assertion.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx4 = 0;
        final int len4 = ((_AttributeValue == null)? 0 :_AttributeValue.size());
        if (_FriendlyName!= null) {
            context.startAttribute("", "FriendlyName");
            try {
                context.text(((java.lang.String) _FriendlyName), "FriendlyName");
            } catch (java.lang.Exception e) {
                com.sun.identity.saml2.jaxb.assertion.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
            context.endAttribute();
        }
        context.startAttribute("", "Name");
        try {
            context.text(((java.lang.String) _Name), "Name");
        } catch (java.lang.Exception e) {
            com.sun.identity.saml2.jaxb.assertion.impl.runtime.Util.handlePrintConversionException(this, e, context);
        }
        context.endAttribute();
        if (_NameFormat!= null) {
            context.startAttribute("", "NameFormat");
            try {
                context.text(((java.lang.String) _NameFormat), "NameFormat");
            } catch (java.lang.Exception e) {
                com.sun.identity.saml2.jaxb.assertion.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
            context.endAttribute();
        }
        while (idx4 != len4) {
            if (_AttributeValue.get(idx4) instanceof com.sun.identity.saml2.jaxb.assertion.AttributeValueElement) {
                context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _AttributeValue.get(idx4 ++)), "AttributeValue");
            } else {
                if ((_AttributeValue.get(idx4) == null)||(_AttributeValue.get(idx4) instanceof com.sun.identity.saml2.jaxb.schema.AnyType)) {
                    if (_AttributeValue.get(idx4) == null) {
                        try {
                            idx4 += 1;
                        } catch (java.lang.Exception e) {
                            com.sun.identity.saml2.jaxb.assertion.impl.runtime.Util.handlePrintConversionException(this, e, context);
                        }
                    } else {
                        if (_AttributeValue.get(idx4) instanceof com.sun.identity.saml2.jaxb.schema.AnyType) {
                            idx4 += 1;
                        } else {
                            com.sun.identity.saml2.jaxb.assertion.impl.runtime.Util.handleTypeMismatchError(context, this, "AttributeValue", _AttributeValue.get(idx4));
                        }
                    }
                } else {
                    com.sun.identity.saml2.jaxb.assertion.impl.runtime.Util.handleTypeMismatchError(context, this, "AttributeValue", _AttributeValue.get(idx4));
                }
            }
        }
    }

    public void serializeURIs(com.sun.identity.saml2.jaxb.assertion.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        int idx4 = 0;
        final int len4 = ((_AttributeValue == null)? 0 :_AttributeValue.size());
        while (idx4 != len4) {
            if (_AttributeValue.get(idx4) instanceof com.sun.identity.saml2.jaxb.assertion.AttributeValueElement) {
                context.childAsURIs(((com.sun.xml.bind.JAXBObject) _AttributeValue.get(idx4 ++)), "AttributeValue");
            } else {
                if ((_AttributeValue.get(idx4) == null)||(_AttributeValue.get(idx4) instanceof com.sun.identity.saml2.jaxb.schema.AnyType)) {
                    if (_AttributeValue.get(idx4) == null) {
                        try {
                            idx4 += 1;
                        } catch (java.lang.Exception e) {
                            com.sun.identity.saml2.jaxb.assertion.impl.runtime.Util.handlePrintConversionException(this, e, context);
                        }
                    } else {
                        if (_AttributeValue.get(idx4) instanceof com.sun.identity.saml2.jaxb.schema.AnyType) {
                            idx4 += 1;
                        } else {
                            com.sun.identity.saml2.jaxb.assertion.impl.runtime.Util.handleTypeMismatchError(context, this, "AttributeValue", _AttributeValue.get(idx4));
                        }
                    }
                } else {
                    com.sun.identity.saml2.jaxb.assertion.impl.runtime.Util.handleTypeMismatchError(context, this, "AttributeValue", _AttributeValue.get(idx4));
                }
            }
        }
    }

    public java.lang.Class getPrimaryInterface() {
        return (com.sun.identity.saml2.jaxb.assertion.AttributeType.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        if (schemaFragment == null) {
            schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
 "\u00ac\u00ed\u0000\u0005sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.su"
+"n.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/gra"
+"mmar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expressi"
+"on\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000b"
+"expandedExpq\u0000~\u0000\u0002xpppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsr\u0000\u001dcom.sun.msv.grammar."
+"ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0001ppsr\u0000 com.sun.msv.grammar.OneOrMor"
+"eExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000"
+"\u0003expq\u0000~\u0000\u0002xq\u0000~\u0000\u0003sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000ps"
+"q\u0000~\u0000\bq\u0000~\u0000\u000epsr\u0000\'com.sun.msv.grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0001\u0002\u0000\u0001L\u0000\tnameClasst\u0000\u001fLcom/sun/msv/grammar/NameClass;xr\u0000\u001ecom.s"
+"un.msv.grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttri"
+"butesL\u0000\fcontentModelq\u0000~\u0000\u0002xq\u0000~\u0000\u0003q\u0000~\u0000\u000ep\u0000sq\u0000~\u0000\bppsq\u0000~\u0000\nq\u0000~\u0000\u000epsr"
+"\u0000 com.sun.msv.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0002L\u0000\tn"
+"ameClassq\u0000~\u0000\u0011xq\u0000~\u0000\u0003q\u0000~\u0000\u000epsr\u00002com.sun.msv.grammar.Expression$"
+"AnyStringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003sq\u0000~\u0000\r\u0001q\u0000~\u0000\u0019sr\u0000 com.sun."
+"msv.grammar.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.N"
+"ameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Expression$Epsi"
+"lonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003q\u0000~\u0000\u001aq\u0000~\u0000\u001fsr\u0000#com.sun.msv.gram"
+"mar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNamet\u0000\u0012Ljava/lang/Stri"
+"ng;L\u0000\fnamespaceURIq\u0000~\u0000!xq\u0000~\u0000\u001ct\u0000;com.sun.identity.saml2.jaxb."
+"assertion.AttributeValueElementt\u0000+http://java.sun.com/jaxb/x"
+"jc/dummy-elementssq\u0000~\u0000\u0010q\u0000~\u0000\u000ep\u0000sq\u0000~\u0000\bppsq\u0000~\u0000\u0016ppsr\u0000\u001ccom.sun.ms"
+"v.grammar.ValueExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datatype/D"
+"atatype;L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/StringPair;L\u0000\u0005valuet\u0000\u0012Lj"
+"ava/lang/Object;xq\u0000~\u0000\u0003ppsr\u0000$com.sun.msv.datatype.xsd.Boolean"
+"Type\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000*com.sun.msv.datatype.xsd.BuiltinAtomicTyp"
+"e\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000"
+"\fnamespaceUriq\u0000~\u0000!L\u0000\btypeNameq\u0000~\u0000!L\u0000\nwhiteSpacet\u0000.Lcom/sun/m"
+"sv/datatype/xsd/WhiteSpaceProcessor;xpt\u0000 http://www.w3.org/2"
+"001/XMLSchemat\u0000\u0007booleansr\u00005com.sun.msv.datatype.xsd.WhiteSpa"
+"ceProcessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd."
+"WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u0000\u001bcom.sun.msv.util.StringP"
+"air\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000!L\u0000\fnamespaceURIq\u0000~\u0000!xpq\u0000~\u00004t\u0000"
+"\u0000q\u0000~\u0000\u001asq\u0000~\u0000 t\u0000\u0003nilt\u0000)http://www.w3.org/2001/XMLSchema-instan"
+"cesq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0010pp\u0000sq\u0000~\u0000\bppsq\u0000~\u0000\nq\u0000~\u0000\u000epsq\u0000~\u0000\u0016q\u0000~\u0000\u000epq\u0000~\u0000\u0019q\u0000~\u0000"
+"\u001dq\u0000~\u0000\u001fsq\u0000~\u0000 t\u0000*com.sun.identity.saml2.jaxb.schema.AnyTypeq\u0000~"
+"\u0000$sq\u0000~\u0000\bppsq\u0000~\u0000\u0016q\u0000~\u0000\u000epsr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0001\u0002\u0000\u0003L\u0000\u0002dtq\u0000~\u0000)L\u0000\u0006exceptq\u0000~\u0000\u0002L\u0000\u0004nameq\u0000~\u0000*xq\u0000~\u0000\u0003ppsr\u0000\"com.sun."
+"msv.datatype.xsd.QnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000.q\u0000~\u00003t\u0000\u0005QNameq\u0000~\u0000"
+"7sr\u00000com.sun.msv.grammar.Expression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003q\u0000~\u0000\u000epsq\u0000~\u00008q\u0000~\u0000Kq\u0000~\u00003sq\u0000~\u0000 t\u0000\u0004typeq\u0000~\u0000=q\u0000~\u0000\u001fsq\u0000~\u0000"
+" t\u0000\u000eAttributeValuet\u0000%urn:oasis:names:tc:SAML:2.0:assertionq\u0000"
+"~\u0000\u001fsq\u0000~\u0000\bppsq\u0000~\u0000\u0016q\u0000~\u0000\u000epsq\u0000~\u0000Gq\u0000~\u0000\u000epsr\u0000#com.sun.msv.datatype."
+"xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlwaysValidxq\u0000~\u0000.q\u0000~\u00003t\u0000\u0006strin"
+"gsr\u00005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Preserve\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u00006\u0001q\u0000~\u0000Msq\u0000~\u00008q\u0000~\u0000Yq\u0000~\u00003sq\u0000~\u0000 t\u0000\fFriendlyNameq\u0000"
+"~\u0000:q\u0000~\u0000\u001fsq\u0000~\u0000\u0016ppq\u0000~\u0000Vsq\u0000~\u0000 t\u0000\u0004Nameq\u0000~\u0000:sq\u0000~\u0000\bppsq\u0000~\u0000\u0016q\u0000~\u0000\u000eps"
+"q\u0000~\u0000Gq\u0000~\u0000\u000epsr\u0000#com.sun.msv.datatype.xsd.AnyURIType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000"
+"\u0000xq\u0000~\u0000.q\u0000~\u00003t\u0000\u0006anyURIq\u0000~\u00007q\u0000~\u0000Msq\u0000~\u00008q\u0000~\u0000gq\u0000~\u00003sq\u0000~\u0000 t\u0000\nName"
+"Formatq\u0000~\u0000:q\u0000~\u0000\u001fsr\u0000\"com.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/ExpressionPool$Close"
+"dHash;xpsr\u0000-com.sun.msv.grammar.ExpressionPool$ClosedHash\u00d7j\u00d0"
+"N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$Lcom/sun/msv/gra"
+"mmar/ExpressionPool;xp\u0000\u0000\u0000\u000f\u0001pq\u0000~\u0000\u0014q\u0000~\u0000@q\u0000~\u0000Tq\u0000~\u0000>q\u0000~\u0000\u0005q\u0000~\u0000\tq\u0000"
+"~\u0000\u0015q\u0000~\u0000Aq\u0000~\u0000\u000fq\u0000~\u0000\u0007q\u0000~\u0000&q\u0000~\u0000Eq\u0000~\u0000\u0006q\u0000~\u0000\fq\u0000~\u0000bx"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public class Unmarshaller
        extends com.sun.identity.saml2.jaxb.assertion.impl.runtime.AbstractUnmarshallingEventHandlerImpl
    {


        public Unmarshaller(com.sun.identity.saml2.jaxb.assertion.impl.runtime.UnmarshallingContext context) {
            super(context, "---------------");
        }

        protected Unmarshaller(com.sun.identity.saml2.jaxb.assertion.impl.runtime.UnmarshallingContext context, int startState) {
            this(context);
            state = startState;
        }

        public java.lang.Object owner() {
            return com.sun.identity.saml2.jaxb.assertion.impl.AttributeTypeImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  3 :
                        attIdx = context.getAttribute("", "Name");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 6;
                            eatText1(v);
                            continue outer;
                        }
                        break;
                    case  0 :
                        attIdx = context.getAttribute("", "FriendlyName");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 3;
                            eatText2(v);
                            continue outer;
                        }
                        state = 3;
                        continue outer;
                    case  6 :
                        attIdx = context.getAttribute("", "NameFormat");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 9;
                            eatText3(v);
                            continue outer;
                        }
                        state = 9;
                        continue outer;
                    case  9 :
                        if (("AttributeValue" == ___local)&&("urn:oasis:names:tc:SAML:2.0:assertion" == ___uri)) {
                            _getAttributeValue().add(((com.sun.identity.saml2.jaxb.assertion.impl.AttributeValueElementImpl) spawnChildFromEnterElement((com.sun.identity.saml2.jaxb.assertion.impl.AttributeValueElementImpl.class), 12, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("AttributeValue" == ___local)&&("urn:oasis:names:tc:SAML:2.0:assertion" == ___uri)) {
                            context.pushAttributes(__atts, true);
                            state = 10;
                            return ;
                        }
                        state = 12;
                        continue outer;
                    case  12 :
                        if (("AttributeValue" == ___local)&&("urn:oasis:names:tc:SAML:2.0:assertion" == ___uri)) {
                            _getAttributeValue().add(((com.sun.identity.saml2.jaxb.assertion.impl.AttributeValueElementImpl) spawnChildFromEnterElement((com.sun.identity.saml2.jaxb.assertion.impl.AttributeValueElementImpl.class), 12, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        if (("AttributeValue" == ___local)&&("urn:oasis:names:tc:SAML:2.0:assertion" == ___uri)) {
                            context.pushAttributes(__atts, true);
                            state = 10;
                            return ;
                        }
                        revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
                    case  10 :
                        attIdx = context.getAttribute("http://www.w3.org/2001/XMLSchema-instance", "nil");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 11;
                            eatText4(v);
                            continue outer;
                        }
                        if (true) {
                            _getAttributeValue().add(((com.sun.identity.saml2.jaxb.schema.impl.AnyTypeImpl) spawnChildFromEnterElement((com.sun.identity.saml2.jaxb.schema.impl.AnyTypeImpl.class), 11, ___uri, ___local, ___qname, __atts)));
                            return ;
                        }
                        _getAttributeValue().add(((com.sun.identity.saml2.jaxb.schema.impl.AnyTypeImpl) spawnChildFromEnterElement((com.sun.identity.saml2.jaxb.schema.impl.AnyTypeImpl.class), 11, ___uri, ___local, ___qname, __atts)));
                        return ;
                }
                super.enterElement(___uri, ___local, ___qname, __atts);
                break;
            }
        }

        private void eatText1(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _Name = value;
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

        private void eatText2(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _FriendlyName = value;
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

        private void eatText3(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _NameFormat = com.sun.xml.bind.WhiteSpaceProcessor.collapse(value);
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

        private void eatText4(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _getAttributeValue().add(null);
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
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
                        attIdx = context.getAttribute("", "Name");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 6;
                            eatText1(v);
                            continue outer;
                        }
                        break;
                    case  0 :
                        attIdx = context.getAttribute("", "FriendlyName");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 3;
                            eatText2(v);
                            continue outer;
                        }
                        state = 3;
                        continue outer;
                    case  11 :
                        if (("AttributeValue" == ___local)&&("urn:oasis:names:tc:SAML:2.0:assertion" == ___uri)) {
                            context.popAttributes();
                            state = 12;
                            return ;
                        }
                        break;
                    case  6 :
                        attIdx = context.getAttribute("", "NameFormat");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 9;
                            eatText3(v);
                            continue outer;
                        }
                        state = 9;
                        continue outer;
                    case  9 :
                        state = 12;
                        continue outer;
                    case  12 :
                        revertToParentFromLeaveElement(___uri, ___local, ___qname);
                        return ;
                    case  10 :
                        attIdx = context.getAttribute("http://www.w3.org/2001/XMLSchema-instance", "nil");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 11;
                            eatText4(v);
                            continue outer;
                        }
                        _getAttributeValue().add(((com.sun.identity.saml2.jaxb.schema.impl.AnyTypeImpl) spawnChildFromLeaveElement((com.sun.identity.saml2.jaxb.schema.impl.AnyTypeImpl.class), 11, ___uri, ___local, ___qname)));
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
                    case  3 :
                        if (("Name" == ___local)&&("" == ___uri)) {
                            state = 4;
                            return ;
                        }
                        break;
                    case  0 :
                        if (("FriendlyName" == ___local)&&("" == ___uri)) {
                            state = 1;
                            return ;
                        }
                        state = 3;
                        continue outer;
                    case  6 :
                        if (("NameFormat" == ___local)&&("" == ___uri)) {
                            state = 7;
                            return ;
                        }
                        state = 9;
                        continue outer;
                    case  9 :
                        state = 12;
                        continue outer;
                    case  12 :
                        revertToParentFromEnterAttribute(___uri, ___local, ___qname);
                        return ;
                    case  10 :
                        if (("nil" == ___local)&&("http://www.w3.org/2001/XMLSchema-instance" == ___uri)) {
                            state = 13;
                            return ;
                        }
                        _getAttributeValue().add(((com.sun.identity.saml2.jaxb.schema.impl.AnyTypeImpl) spawnChildFromEnterAttribute((com.sun.identity.saml2.jaxb.schema.impl.AnyTypeImpl.class), 11, ___uri, ___local, ___qname)));
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
                        attIdx = context.getAttribute("", "Name");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 6;
                            eatText1(v);
                            continue outer;
                        }
                        break;
                    case  14 :
                        if (("nil" == ___local)&&("http://www.w3.org/2001/XMLSchema-instance" == ___uri)) {
                            state = 11;
                            return ;
                        }
                        break;
                    case  0 :
                        attIdx = context.getAttribute("", "FriendlyName");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 3;
                            eatText2(v);
                            continue outer;
                        }
                        state = 3;
                        continue outer;
                    case  6 :
                        attIdx = context.getAttribute("", "NameFormat");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 9;
                            eatText3(v);
                            continue outer;
                        }
                        state = 9;
                        continue outer;
                    case  2 :
                        if (("FriendlyName" == ___local)&&("" == ___uri)) {
                            state = 3;
                            return ;
                        }
                        break;
                    case  5 :
                        if (("Name" == ___local)&&("" == ___uri)) {
                            state = 6;
                            return ;
                        }
                        break;
                    case  8 :
                        if (("NameFormat" == ___local)&&("" == ___uri)) {
                            state = 9;
                            return ;
                        }
                        break;
                    case  9 :
                        state = 12;
                        continue outer;
                    case  12 :
                        revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
                        return ;
                    case  10 :
                        attIdx = context.getAttribute("http://www.w3.org/2001/XMLSchema-instance", "nil");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 11;
                            eatText4(v);
                            continue outer;
                        }
                        _getAttributeValue().add(((com.sun.identity.saml2.jaxb.schema.impl.AnyTypeImpl) spawnChildFromLeaveAttribute((com.sun.identity.saml2.jaxb.schema.impl.AnyTypeImpl.class), 11, ___uri, ___local, ___qname)));
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
                            attIdx = context.getAttribute("", "Name");
                            if (attIdx >= 0) {
                                final java.lang.String v = context.eatAttribute(attIdx);
                                state = 6;
                                eatText1(v);
                                continue outer;
                            }
                            break;
                        case  0 :
                            attIdx = context.getAttribute("", "FriendlyName");
                            if (attIdx >= 0) {
                                final java.lang.String v = context.eatAttribute(attIdx);
                                state = 3;
                                eatText2(v);
                                continue outer;
                            }
                            state = 3;
                            continue outer;
                        case  1 :
                            state = 2;
                            eatText2(value);
                            return ;
                        case  6 :
                            attIdx = context.getAttribute("", "NameFormat");
                            if (attIdx >= 0) {
                                final java.lang.String v = context.eatAttribute(attIdx);
                                state = 9;
                                eatText3(v);
                                continue outer;
                            }
                            state = 9;
                            continue outer;
                        case  9 :
                            state = 12;
                            continue outer;
                        case  12 :
                            revertToParentFromText(value);
                            return ;
                        case  13 :
                            state = 14;
                            eatText4(value);
                            return ;
                        case  7 :
                            state = 8;
                            eatText3(value);
                            return ;
                        case  10 :
                            attIdx = context.getAttribute("http://www.w3.org/2001/XMLSchema-instance", "nil");
                            if (attIdx >= 0) {
                                final java.lang.String v = context.eatAttribute(attIdx);
                                state = 11;
                                eatText4(v);
                                continue outer;
                            }
                            _getAttributeValue().add(((com.sun.identity.saml2.jaxb.schema.impl.AnyTypeImpl) spawnChildFromText((com.sun.identity.saml2.jaxb.schema.impl.AnyTypeImpl.class), 11, value)));
                            return ;
                        case  4 :
                            state = 5;
                            eatText1(value);
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
