//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:34:07 AM PDT 
//


package com.sun.identity.saml2.jaxb.assertion.impl;

public class NameIDTypeImpl implements com.sun.identity.saml2.jaxb.assertion.NameIDType, com.sun.xml.bind.JAXBObject, com.sun.identity.saml2.jaxb.assertion.impl.runtime.UnmarshallableObject, com.sun.identity.saml2.jaxb.assertion.impl.runtime.XMLSerializable, com.sun.identity.saml2.jaxb.assertion.impl.runtime.ValidatableObject
{

    protected java.lang.String _Value;
    protected java.lang.String _Format;
    protected java.lang.String _NameQualifier;
    protected java.lang.String _SPProvidedID;
    protected java.lang.String _SPNameQualifier;
    public final static java.lang.Class version = (com.sun.identity.saml2.jaxb.assertion.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (com.sun.identity.saml2.jaxb.assertion.NameIDType.class);
    }

    public java.lang.String getValue() {
        return _Value;
    }

    public void setValue(java.lang.String value) {
        _Value = value;
    }

    public java.lang.String getFormat() {
        return _Format;
    }

    public void setFormat(java.lang.String value) {
        _Format = value;
    }

    public java.lang.String getNameQualifier() {
        return _NameQualifier;
    }

    public void setNameQualifier(java.lang.String value) {
        _NameQualifier = value;
    }

    public java.lang.String getSPProvidedID() {
        return _SPProvidedID;
    }

    public void setSPProvidedID(java.lang.String value) {
        _SPProvidedID = value;
    }

    public java.lang.String getSPNameQualifier() {
        return _SPNameQualifier;
    }

    public void setSPNameQualifier(java.lang.String value) {
        _SPNameQualifier = value;
    }

    public com.sun.identity.saml2.jaxb.assertion.impl.runtime.UnmarshallingEventHandler createUnmarshaller(com.sun.identity.saml2.jaxb.assertion.impl.runtime.UnmarshallingContext context) {
        return new com.sun.identity.saml2.jaxb.assertion.impl.NameIDTypeImpl.Unmarshaller(context);
    }

    public void serializeBody(com.sun.identity.saml2.jaxb.assertion.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        try {
            context.text(((java.lang.String) _Value), "Value");
        } catch (java.lang.Exception e) {
            com.sun.identity.saml2.jaxb.assertion.impl.runtime.Util.handlePrintConversionException(this, e, context);
        }
    }

    public void serializeAttributes(com.sun.identity.saml2.jaxb.assertion.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        if (_NameQualifier!= null) {
            context.startAttribute("", "NameQualifier");
            try {
                context.text(((java.lang.String) _NameQualifier), "NameQualifier");
            } catch (java.lang.Exception e) {
                com.sun.identity.saml2.jaxb.assertion.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
            context.endAttribute();
        }
        if (_SPNameQualifier!= null) {
            context.startAttribute("", "SPNameQualifier");
            try {
                context.text(((java.lang.String) _SPNameQualifier), "SPNameQualifier");
            } catch (java.lang.Exception e) {
                com.sun.identity.saml2.jaxb.assertion.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
            context.endAttribute();
        }
        if (_Format!= null) {
            context.startAttribute("", "Format");
            try {
                context.text(((java.lang.String) _Format), "Format");
            } catch (java.lang.Exception e) {
                com.sun.identity.saml2.jaxb.assertion.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
            context.endAttribute();
        }
        if (_SPProvidedID!= null) {
            context.startAttribute("", "SPProvidedID");
            try {
                context.text(((java.lang.String) _SPProvidedID), "SPProvidedID");
            } catch (java.lang.Exception e) {
                com.sun.identity.saml2.jaxb.assertion.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
            context.endAttribute();
        }
    }

    public void serializeURIs(com.sun.identity.saml2.jaxb.assertion.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
    }

    public java.lang.Class getPrimaryInterface() {
        return (com.sun.identity.saml2.jaxb.assertion.NameIDType.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        if (schemaFragment == null) {
            schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
 "\u00ac\u00ed\u0000\u0005sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.su"
+"n.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/gra"
+"mmar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expressi"
+"on\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000b"
+"expandedExpq\u0000~\u0000\u0002xpppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsq\u0000~\u0000\u0000ppsr\u0000\u001bcom.sun.msv."
+"grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datatype/Data"
+"type;L\u0000\u0006exceptq\u0000~\u0000\u0002L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/StringPair;xq"
+"\u0000~\u0000\u0003sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psr\u0000#com.sun."
+"msv.datatype.xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\risAlwaysValidxr\u0000*co"
+"m.sun.msv.datatype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.s"
+"un.msv.datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.d"
+"atatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUrit\u0000\u0012Ljava"
+"/lang/String;L\u0000\btypeNameq\u0000~\u0000\u0013L\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/da"
+"tatype/xsd/WhiteSpaceProcessor;xpt\u0000 http://www.w3.org/2001/X"
+"MLSchemat\u0000\u0006stringsr\u00005com.sun.msv.datatype.xsd.WhiteSpaceProc"
+"essor$Preserve\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd.WhiteS"
+"paceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xp\u0001sr\u00000com.sun.msv.grammar.Expressio"
+"n$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003q\u0000~\u0000\u000epsr\u0000\u001bcom.sun.msv.ut"
+"il.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u0013L\u0000\fnamespaceURIq\u0000~\u0000"
+"\u0013xpq\u0000~\u0000\u0017q\u0000~\u0000\u0016sr\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000"
+"~\u0000\u0001ppsr\u0000 com.sun.msv.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000"
+"~\u0000\u0002L\u0000\tnameClasst\u0000\u001fLcom/sun/msv/grammar/NameClass;xq\u0000~\u0000\u0003q\u0000~\u0000\u000e"
+"psq\u0000~\u0000\tq\u0000~\u0000\u000epsr\u0000#com.sun.msv.datatype.xsd.AnyURIType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001"
+"\u0002\u0000\u0000xq\u0000~\u0000\u0010q\u0000~\u0000\u0016t\u0000\u0006anyURIsr\u00005com.sun.msv.datatype.xsd.WhiteSpa"
+"ceProcessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0019q\u0000~\u0000\u001csq\u0000~\u0000\u001dq\u0000~\u0000\'q\u0000~\u0000\u0016sr"
+"\u0000#com.sun.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalName"
+"q\u0000~\u0000\u0013L\u0000\fnamespaceURIq\u0000~\u0000\u0013xr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpt\u0000\u0006Formatt\u0000\u0000sr\u00000com.sun.msv.grammar.Expression$Ep"
+"silonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003sq\u0000~\u0000\r\u0001q\u0000~\u00001sq\u0000~\u0000\u001fppsq\u0000~\u0000!q\u0000"
+"~\u0000\u000epq\u0000~\u0000\fsq\u0000~\u0000+t\u0000\fSPProvidedIDq\u0000~\u0000/q\u0000~\u00001sq\u0000~\u0000\u001fppsq\u0000~\u0000!q\u0000~\u0000\u000ep"
+"q\u0000~\u0000\fsq\u0000~\u0000+t\u0000\rNameQualifierq\u0000~\u0000/q\u0000~\u00001sq\u0000~\u0000\u001fppsq\u0000~\u0000!q\u0000~\u0000\u000epq\u0000~"
+"\u0000\fsq\u0000~\u0000+t\u0000\u000fSPNameQualifierq\u0000~\u0000/q\u0000~\u00001sr\u0000\"com.sun.msv.grammar."
+"ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/"
+"ExpressionPool$ClosedHash;xpsr\u0000-com.sun.msv.grammar.Expressi"
+"onPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006paren"
+"tt\u0000$Lcom/sun/msv/grammar/ExpressionPool;xp\u0000\u0000\u0000\b\u0001pq\u0000~\u00003q\u0000~\u0000;q\u0000"
+"~\u0000\u0005q\u0000~\u0000 q\u0000~\u0000\u0006q\u0000~\u0000\u0007q\u0000~\u0000\bq\u0000~\u00007x"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public class Unmarshaller
        extends com.sun.identity.saml2.jaxb.assertion.impl.runtime.AbstractUnmarshallingEventHandlerImpl
    {


        public Unmarshaller(com.sun.identity.saml2.jaxb.assertion.impl.runtime.UnmarshallingContext context) {
            super(context, "--------------");
        }

        protected Unmarshaller(com.sun.identity.saml2.jaxb.assertion.impl.runtime.UnmarshallingContext context, int startState) {
            this(context);
            state = startState;
        }

        public java.lang.Object owner() {
            return com.sun.identity.saml2.jaxb.assertion.impl.NameIDTypeImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  6 :
                        attIdx = context.getAttribute("", "Format");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 9;
                            eatText1(v);
                            continue outer;
                        }
                        state = 9;
                        continue outer;
                    case  13 :
                        revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
                    case  0 :
                        attIdx = context.getAttribute("", "NameQualifier");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 3;
                            eatText2(v);
                            continue outer;
                        }
                        state = 3;
                        continue outer;
                    case  3 :
                        attIdx = context.getAttribute("", "SPNameQualifier");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 6;
                            eatText3(v);
                            continue outer;
                        }
                        state = 6;
                        continue outer;
                    case  9 :
                        attIdx = context.getAttribute("", "SPProvidedID");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 12;
                            eatText4(v);
                            continue outer;
                        }
                        state = 12;
                        continue outer;
                }
                super.enterElement(___uri, ___local, ___qname, __atts);
                break;
            }
        }

        private void eatText1(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _Format = com.sun.xml.bind.WhiteSpaceProcessor.collapse(value);
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

        private void eatText2(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _NameQualifier = value;
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

        private void eatText3(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _SPNameQualifier = value;
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

        private void eatText4(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _SPProvidedID = value;
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
                    case  6 :
                        attIdx = context.getAttribute("", "Format");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 9;
                            eatText1(v);
                            continue outer;
                        }
                        state = 9;
                        continue outer;
                    case  13 :
                        revertToParentFromLeaveElement(___uri, ___local, ___qname);
                        return ;
                    case  0 :
                        attIdx = context.getAttribute("", "NameQualifier");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 3;
                            eatText2(v);
                            continue outer;
                        }
                        state = 3;
                        continue outer;
                    case  3 :
                        attIdx = context.getAttribute("", "SPNameQualifier");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 6;
                            eatText3(v);
                            continue outer;
                        }
                        state = 6;
                        continue outer;
                    case  9 :
                        attIdx = context.getAttribute("", "SPProvidedID");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 12;
                            eatText4(v);
                            continue outer;
                        }
                        state = 12;
                        continue outer;
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
                    case  6 :
                        if (("Format" == ___local)&&("" == ___uri)) {
                            state = 7;
                            return ;
                        }
                        state = 9;
                        continue outer;
                    case  13 :
                        revertToParentFromEnterAttribute(___uri, ___local, ___qname);
                        return ;
                    case  0 :
                        if (("NameQualifier" == ___local)&&("" == ___uri)) {
                            state = 1;
                            return ;
                        }
                        state = 3;
                        continue outer;
                    case  3 :
                        if (("SPNameQualifier" == ___local)&&("" == ___uri)) {
                            state = 4;
                            return ;
                        }
                        state = 6;
                        continue outer;
                    case  9 :
                        if (("SPProvidedID" == ___local)&&("" == ___uri)) {
                            state = 10;
                            return ;
                        }
                        state = 12;
                        continue outer;
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
                    case  2 :
                        if (("NameQualifier" == ___local)&&("" == ___uri)) {
                            state = 3;
                            return ;
                        }
                        break;
                    case  6 :
                        attIdx = context.getAttribute("", "Format");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 9;
                            eatText1(v);
                            continue outer;
                        }
                        state = 9;
                        continue outer;
                    case  13 :
                        revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
                        return ;
                    case  8 :
                        if (("Format" == ___local)&&("" == ___uri)) {
                            state = 9;
                            return ;
                        }
                        break;
                    case  11 :
                        if (("SPProvidedID" == ___local)&&("" == ___uri)) {
                            state = 12;
                            return ;
                        }
                        break;
                    case  5 :
                        if (("SPNameQualifier" == ___local)&&("" == ___uri)) {
                            state = 6;
                            return ;
                        }
                        break;
                    case  0 :
                        attIdx = context.getAttribute("", "NameQualifier");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 3;
                            eatText2(v);
                            continue outer;
                        }
                        state = 3;
                        continue outer;
                    case  3 :
                        attIdx = context.getAttribute("", "SPNameQualifier");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 6;
                            eatText3(v);
                            continue outer;
                        }
                        state = 6;
                        continue outer;
                    case  9 :
                        attIdx = context.getAttribute("", "SPProvidedID");
                        if (attIdx >= 0) {
                            final java.lang.String v = context.eatAttribute(attIdx);
                            state = 12;
                            eatText4(v);
                            continue outer;
                        }
                        state = 12;
                        continue outer;
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
                            eatText2(value);
                            return ;
                        case  6 :
                            attIdx = context.getAttribute("", "Format");
                            if (attIdx >= 0) {
                                final java.lang.String v = context.eatAttribute(attIdx);
                                state = 9;
                                eatText1(v);
                                continue outer;
                            }
                            state = 9;
                            continue outer;
                        case  7 :
                            state = 8;
                            eatText1(value);
                            return ;
                        case  4 :
                            state = 5;
                            eatText3(value);
                            return ;
                        case  13 :
                            revertToParentFromText(value);
                            return ;
                        case  0 :
                            attIdx = context.getAttribute("", "NameQualifier");
                            if (attIdx >= 0) {
                                final java.lang.String v = context.eatAttribute(attIdx);
                                state = 3;
                                eatText2(v);
                                continue outer;
                            }
                            state = 3;
                            continue outer;
                        case  10 :
                            state = 11;
                            eatText4(value);
                            return ;
                        case  3 :
                            attIdx = context.getAttribute("", "SPNameQualifier");
                            if (attIdx >= 0) {
                                final java.lang.String v = context.eatAttribute(attIdx);
                                state = 6;
                                eatText3(v);
                                continue outer;
                            }
                            state = 6;
                            continue outer;
                        case  9 :
                            attIdx = context.getAttribute("", "SPProvidedID");
                            if (attIdx >= 0) {
                                final java.lang.String v = context.eatAttribute(attIdx);
                                state = 12;
                                eatText4(v);
                                continue outer;
                            }
                            state = 12;
                            continue outer;
                        case  12 :
                            state = 13;
                            eatText5(value);
                            return ;
                    }
                } catch (java.lang.RuntimeException e) {
                    handleUnexpectedTextException(value, e);
                }
                break;
            }
        }

        private void eatText5(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _Value = value;
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

    }

}
