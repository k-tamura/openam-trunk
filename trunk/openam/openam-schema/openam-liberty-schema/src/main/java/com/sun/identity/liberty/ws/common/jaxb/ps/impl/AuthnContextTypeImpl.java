//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:33:54 AM PDT 
//


package com.sun.identity.liberty.ws.common.jaxb.ps.impl;

public class AuthnContextTypeImpl implements com.sun.identity.liberty.ws.common.jaxb.ps.AuthnContextType, com.sun.xml.bind.JAXBObject, com.sun.identity.federation.jaxb.entityconfig.impl.runtime.UnmarshallableObject, com.sun.identity.federation.jaxb.entityconfig.impl.runtime.XMLSerializable, com.sun.identity.federation.jaxb.entityconfig.impl.runtime.ValidatableObject
{

    protected java.lang.String _AuthnContextStatementRef;
    protected com.sun.identity.liberty.ws.common.jaxb.ac.AuthenticationContextStatementType _AuthenticationContextStatement;
    protected java.lang.String _AuthnContextClassRef;
    public final static java.lang.Class version = (com.sun.identity.liberty.ws.common.jaxb.ps.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (com.sun.identity.liberty.ws.common.jaxb.ps.AuthnContextType.class);
    }

    public java.lang.String getAuthnContextStatementRef() {
        return _AuthnContextStatementRef;
    }

    public void setAuthnContextStatementRef(java.lang.String value) {
        _AuthnContextStatementRef = value;
    }

    public com.sun.identity.liberty.ws.common.jaxb.ac.AuthenticationContextStatementType getAuthenticationContextStatement() {
        return _AuthenticationContextStatement;
    }

    public void setAuthenticationContextStatement(com.sun.identity.liberty.ws.common.jaxb.ac.AuthenticationContextStatementType value) {
        _AuthenticationContextStatement = value;
    }

    public java.lang.String getAuthnContextClassRef() {
        return _AuthnContextClassRef;
    }

    public void setAuthnContextClassRef(java.lang.String value) {
        _AuthnContextClassRef = value;
    }

    public com.sun.identity.federation.jaxb.entityconfig.impl.runtime.UnmarshallingEventHandler createUnmarshaller(com.sun.identity.federation.jaxb.entityconfig.impl.runtime.UnmarshallingContext context) {
        return new com.sun.identity.liberty.ws.common.jaxb.ps.impl.AuthnContextTypeImpl.Unmarshaller(context);
    }

    public void serializeBody(com.sun.identity.federation.jaxb.entityconfig.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        if (_AuthnContextClassRef!= null) {
            context.startElement("urn:liberty:iff:2003-08", "AuthnContextClassRef");
            context.endNamespaceDecls();
            context.endAttributes();
            try {
                context.text(((java.lang.String) _AuthnContextClassRef), "AuthnContextClassRef");
            } catch (java.lang.Exception e) {
                com.sun.identity.federation.jaxb.entityconfig.impl.runtime.Util.handlePrintConversionException(this, e, context);
            }
            context.endElement();
        }
        if ((_AuthenticationContextStatement!= null)&&(_AuthnContextStatementRef == null)) {
            if (_AuthenticationContextStatement instanceof javax.xml.bind.Element) {
                context.childAsBody(((com.sun.xml.bind.JAXBObject) _AuthenticationContextStatement), "AuthenticationContextStatement");
            } else {
                context.startElement("urn:liberty:ac:2003-08", "AuthenticationContextStatement");
                context.childAsURIs(((com.sun.xml.bind.JAXBObject) _AuthenticationContextStatement), "AuthenticationContextStatement");
                context.endNamespaceDecls();
                context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _AuthenticationContextStatement), "AuthenticationContextStatement");
                context.endAttributes();
                context.childAsBody(((com.sun.xml.bind.JAXBObject) _AuthenticationContextStatement), "AuthenticationContextStatement");
                context.endElement();
            }
        } else {
            if ((_AuthenticationContextStatement == null)&&(_AuthnContextStatementRef!= null)) {
                context.startElement("urn:liberty:iff:2003-08", "AuthnContextStatementRef");
                context.endNamespaceDecls();
                context.endAttributes();
                try {
                    context.text(((java.lang.String) _AuthnContextStatementRef), "AuthnContextStatementRef");
                } catch (java.lang.Exception e) {
                    com.sun.identity.federation.jaxb.entityconfig.impl.runtime.Util.handlePrintConversionException(this, e, context);
                }
                context.endElement();
            }
        }
    }

    public void serializeAttributes(com.sun.identity.federation.jaxb.entityconfig.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        if ((_AuthenticationContextStatement!= null)&&(_AuthnContextStatementRef == null)) {
            if (_AuthenticationContextStatement instanceof javax.xml.bind.Element) {
                context.childAsAttributes(((com.sun.xml.bind.JAXBObject) _AuthenticationContextStatement), "AuthenticationContextStatement");
            }
        }
    }

    public void serializeURIs(com.sun.identity.federation.jaxb.entityconfig.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        if ((_AuthenticationContextStatement!= null)&&(_AuthnContextStatementRef == null)) {
            if (_AuthenticationContextStatement instanceof javax.xml.bind.Element) {
                context.childAsURIs(((com.sun.xml.bind.JAXBObject) _AuthenticationContextStatement), "AuthenticationContextStatement");
            }
        }
    }

    public java.lang.Class getPrimaryInterface() {
        return (com.sun.identity.liberty.ws.common.jaxb.ps.AuthnContextType.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        if (schemaFragment == null) {
            schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
 "\u00ac\u00ed\u0000\u0005sr\u0000\u001fcom.sun.msv.grammar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.su"
+"n.msv.grammar.BinaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1t\u0000 Lcom/sun/msv/gra"
+"mmar/Expression;L\u0000\u0004exp2q\u0000~\u0000\u0002xr\u0000\u001ecom.sun.msv.grammar.Expressi"
+"on\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Ljava/lang/Boolean;L\u0000\u000b"
+"expandedExpq\u0000~\u0000\u0002xpppsr\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0001ppsr\u0000\'com.sun.msv.grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\tnameClasst\u0000\u001fLcom/sun/msv/grammar/NameClass;xr\u0000\u001ecom."
+"sun.msv.grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttr"
+"ibutesL\u0000\fcontentModelq\u0000~\u0000\u0002xq\u0000~\u0000\u0003sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa"
+"\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000p\u0000sq\u0000~\u0000\u0000ppsr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000\u0000\u0000"
+"\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datatype/Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0002"
+"L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/StringPair;xq\u0000~\u0000\u0003q\u0000~\u0000\rpsr\u0000#com.s"
+"un.msv.datatype.xsd.AnyURIType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000*com.sun.msv.dat"
+"atype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.dataty"
+"pe.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd.X"
+"SDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUrit\u0000\u0012Ljava/lang/String;"
+"L\u0000\btypeNameq\u0000~\u0000\u0017L\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/datatype/xsd/Wh"
+"iteSpaceProcessor;xpt\u0000 http://www.w3.org/2001/XMLSchemat\u0000\u0006an"
+"yURIsr\u00005com.sun.msv.datatype.xsd.WhiteSpaceProcessor$Collaps"
+"e\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd.WhiteSpaceProcessor"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Expression$NullSetExpre"
+"ssion\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003q\u0000~\u0000\rpsr\u0000\u001bcom.sun.msv.util.StringPair\u00d0"
+"t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u0017L\u0000\fnamespaceURIq\u0000~\u0000\u0017xpq\u0000~\u0000\u001bq\u0000~\u0000\u001as"
+"q\u0000~\u0000\u0006ppsr\u0000 com.sun.msv.grammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003exp"
+"q\u0000~\u0000\u0002L\u0000\tnameClassq\u0000~\u0000\txq\u0000~\u0000\u0003q\u0000~\u0000\rpsq\u0000~\u0000\u000fq\u0000~\u0000\rpsr\u0000\"com.sun.ms"
+"v.datatype.xsd.QnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0014q\u0000~\u0000\u001at\u0000\u0005QNameq\u0000~\u0000\u001eq"
+"\u0000~\u0000 sq\u0000~\u0000!q\u0000~\u0000)q\u0000~\u0000\u001asr\u0000#com.sun.msv.grammar.SimpleNameClass\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u0017L\u0000\fnamespaceURIq\u0000~\u0000\u0017xr\u0000\u001dcom.sun.m"
+"sv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpt\u0000\u0004typet\u0000)http://www.w3.org"
+"/2001/XMLSchema-instancesr\u00000com.sun.msv.grammar.Expression$E"
+"psilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003sq\u0000~\u0000\f\u0001q\u0000~\u00001sq\u0000~\u0000+t\u0000\u0014AuthnC"
+"ontextClassReft\u0000\u0017urn:liberty:iff:2003-08q\u0000~\u00001sq\u0000~\u0000\u0006ppsq\u0000~\u0000\u0006p"
+"psq\u0000~\u0000\bpp\u0000sq\u0000~\u0000\u0006ppsr\u0000 com.sun.msv.grammar.OneOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0002x"
+"q\u0000~\u0000\u0003q\u0000~\u0000\rpsq\u0000~\u0000$q\u0000~\u0000\rpsr\u00002com.sun.msv.grammar.Expression$An"
+"yStringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0003q\u0000~\u00002q\u0000~\u0000?sr\u0000 com.sun.msv."
+"grammar.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000,q\u0000~\u00001sq\u0000~\u0000+t\u0000Pcom.sun.i"
+"dentity.liberty.ws.common.jaxb.ac.AuthenticationContextState"
+"mentElementt\u0000+http://java.sun.com/jaxb/xjc/dummy-elementssq\u0000"
+"~\u0000\bpp\u0000sq\u0000~\u0000\u0000ppsq\u0000~\u0000\bpp\u0000sq\u0000~\u0000\u0006ppsq\u0000~\u0000:q\u0000~\u0000\rpsq\u0000~\u0000$q\u0000~\u0000\rpq\u0000~\u0000?"
+"q\u0000~\u0000Aq\u0000~\u00001sq\u0000~\u0000+t\u0000Mcom.sun.identity.liberty.ws.common.jaxb.a"
+"c.AuthenticationContextStatementTypeq\u0000~\u0000Dsq\u0000~\u0000\u0006ppsq\u0000~\u0000$q\u0000~\u0000\r"
+"pq\u0000~\u0000&q\u0000~\u0000-q\u0000~\u00001sq\u0000~\u0000+t\u0000\u001eAuthenticationContextStatementt\u0000\u0016ur"
+"n:liberty:ac:2003-08sq\u0000~\u0000\bpp\u0000sq\u0000~\u0000\u0000ppq\u0000~\u0000\u0012sq\u0000~\u0000\u0006ppsq\u0000~\u0000$q\u0000~\u0000"
+"\rpq\u0000~\u0000&q\u0000~\u0000-q\u0000~\u00001sq\u0000~\u0000+t\u0000\u0018AuthnContextStatementRefq\u0000~\u00005sr\u0000\"c"
+"om.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTablet\u0000/Lc"
+"om/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\u0000-com.sun.m"
+"sv.grammar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rst"
+"reamVersionL\u0000\u0006parentt\u0000$Lcom/sun/msv/grammar/ExpressionPool;x"
+"p\u0000\u0000\u0000\u000e\u0001pq\u0000~\u00007q\u0000~\u0000<q\u0000~\u0000Iq\u0000~\u0000\u000eq\u0000~\u0000Sq\u0000~\u00009q\u0000~\u0000Hq\u0000~\u0000Fq\u0000~\u0000\u0005q\u0000~\u0000#q\u0000~"
+"\u0000Mq\u0000~\u0000Tq\u0000~\u00006q\u0000~\u0000\u0007x"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public class Unmarshaller
        extends com.sun.identity.federation.jaxb.entityconfig.impl.runtime.AbstractUnmarshallingEventHandlerImpl
    {


        public Unmarshaller(com.sun.identity.federation.jaxb.entityconfig.impl.runtime.UnmarshallingContext context) {
            super(context, "---------");
        }

        protected Unmarshaller(com.sun.identity.federation.jaxb.entityconfig.impl.runtime.UnmarshallingContext context, int startState) {
            this(context);
            state = startState;
        }

        public java.lang.Object owner() {
            return com.sun.identity.liberty.ws.common.jaxb.ps.impl.AuthnContextTypeImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  6 :
                        revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
                    case  3 :
                        if (("AuthenticationContextStatement" == ___local)&&("urn:liberty:ac:2003-08" == ___uri)) {
                            _AuthenticationContextStatement = ((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementElementImpl) spawnChildFromEnterElement((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementElementImpl.class), 6, ___uri, ___local, ___qname, __atts));
                            return ;
                        }
                        if (("AuthenticationContextStatement" == ___local)&&("urn:liberty:ac:2003-08" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 4;
                            return ;
                        }
                        if (("AuthnContextStatementRef" == ___local)&&("urn:liberty:iff:2003-08" == ___uri)) {
                            context.pushAttributes(__atts, true);
                            state = 7;
                            return ;
                        }
                        break;
                    case  4 :
                        attIdx = context.getAttribute("", "ID");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        if (("Identification" == ___local)&&("urn:liberty:ac:2003-08" == ___uri)) {
                            _AuthenticationContextStatement = ((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl) spawnChildFromEnterElement((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl.class), 5, ___uri, ___local, ___qname, __atts));
                            return ;
                        }
                        if (("Identification" == ___local)&&("urn:liberty:ac:2003-08" == ___uri)) {
                            _AuthenticationContextStatement = ((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl) spawnChildFromEnterElement((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl.class), 5, ___uri, ___local, ___qname, __atts));
                            return ;
                        }
                        if (("TechnicalProtection" == ___local)&&("urn:liberty:ac:2003-08" == ___uri)) {
                            _AuthenticationContextStatement = ((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl) spawnChildFromEnterElement((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl.class), 5, ___uri, ___local, ___qname, __atts));
                            return ;
                        }
                        if (("TechnicalProtection" == ___local)&&("urn:liberty:ac:2003-08" == ___uri)) {
                            _AuthenticationContextStatement = ((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl) spawnChildFromEnterElement((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl.class), 5, ___uri, ___local, ___qname, __atts));
                            return ;
                        }
                        if (("OperationalProtection" == ___local)&&("urn:liberty:ac:2003-08" == ___uri)) {
                            _AuthenticationContextStatement = ((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl) spawnChildFromEnterElement((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl.class), 5, ___uri, ___local, ___qname, __atts));
                            return ;
                        }
                        if (("OperationalProtection" == ___local)&&("urn:liberty:ac:2003-08" == ___uri)) {
                            _AuthenticationContextStatement = ((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl) spawnChildFromEnterElement((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl.class), 5, ___uri, ___local, ___qname, __atts));
                            return ;
                        }
                        if (("AuthenticationMethod" == ___local)&&("urn:liberty:ac:2003-08" == ___uri)) {
                            _AuthenticationContextStatement = ((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl) spawnChildFromEnterElement((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl.class), 5, ___uri, ___local, ___qname, __atts));
                            return ;
                        }
                        if (("AuthenticationMethod" == ___local)&&("urn:liberty:ac:2003-08" == ___uri)) {
                            _AuthenticationContextStatement = ((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl) spawnChildFromEnterElement((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl.class), 5, ___uri, ___local, ___qname, __atts));
                            return ;
                        }
                        if (("GoverningAgreements" == ___local)&&("urn:liberty:ac:2003-08" == ___uri)) {
                            _AuthenticationContextStatement = ((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl) spawnChildFromEnterElement((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl.class), 5, ___uri, ___local, ___qname, __atts));
                            return ;
                        }
                        if (("GoverningAgreements" == ___local)&&("urn:liberty:ac:2003-08" == ___uri)) {
                            _AuthenticationContextStatement = ((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl) spawnChildFromEnterElement((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl.class), 5, ___uri, ___local, ___qname, __atts));
                            return ;
                        }
                        if (("AuthenticatingAuthority" == ___local)&&("urn:liberty:ac:2003-08" == ___uri)) {
                            _AuthenticationContextStatement = ((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl) spawnChildFromEnterElement((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl.class), 5, ___uri, ___local, ___qname, __atts));
                            return ;
                        }
                        if (("AuthenticatingAuthority" == ___local)&&("urn:liberty:ac:2003-08" == ___uri)) {
                            _AuthenticationContextStatement = ((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl) spawnChildFromEnterElement((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl.class), 5, ___uri, ___local, ___qname, __atts));
                            return ;
                        }
                        if (("Extension" == ___local)&&("urn:liberty:ac:2003-08" == ___uri)) {
                            _AuthenticationContextStatement = ((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl) spawnChildFromEnterElement((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl.class), 5, ___uri, ___local, ___qname, __atts));
                            return ;
                        }
                        if (("Extension" == ___local)&&("urn:liberty:ac:2003-08" == ___uri)) {
                            _AuthenticationContextStatement = ((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl) spawnChildFromEnterElement((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl.class), 5, ___uri, ___local, ___qname, __atts));
                            return ;
                        }
                        _AuthenticationContextStatement = ((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl) spawnChildFromEnterElement((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl.class), 5, ___uri, ___local, ___qname, __atts));
                        return ;
                    case  0 :
                        if (("AuthnContextClassRef" == ___local)&&("urn:liberty:iff:2003-08" == ___uri)) {
                            context.pushAttributes(__atts, true);
                            state = 1;
                            return ;
                        }
                        state = 3;
                        continue outer;
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
                    case  6 :
                        revertToParentFromLeaveElement(___uri, ___local, ___qname);
                        return ;
                    case  4 :
                        attIdx = context.getAttribute("", "ID");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        _AuthenticationContextStatement = ((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl) spawnChildFromLeaveElement((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl.class), 5, ___uri, ___local, ___qname));
                        return ;
                    case  5 :
                        if (("AuthenticationContextStatement" == ___local)&&("urn:liberty:ac:2003-08" == ___uri)) {
                            context.popAttributes();
                            state = 6;
                            return ;
                        }
                        break;
                    case  8 :
                        if (("AuthnContextStatementRef" == ___local)&&("urn:liberty:iff:2003-08" == ___uri)) {
                            context.popAttributes();
                            state = 6;
                            return ;
                        }
                        break;
                    case  2 :
                        if (("AuthnContextClassRef" == ___local)&&("urn:liberty:iff:2003-08" == ___uri)) {
                            context.popAttributes();
                            state = 3;
                            return ;
                        }
                        break;
                    case  0 :
                        state = 3;
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
                        revertToParentFromEnterAttribute(___uri, ___local, ___qname);
                        return ;
                    case  4 :
                        if (("ID" == ___local)&&("" == ___uri)) {
                            _AuthenticationContextStatement = ((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl) spawnChildFromEnterAttribute((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl.class), 5, ___uri, ___local, ___qname));
                            return ;
                        }
                        _AuthenticationContextStatement = ((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl) spawnChildFromEnterAttribute((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl.class), 5, ___uri, ___local, ___qname));
                        return ;
                    case  0 :
                        state = 3;
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
                    case  6 :
                        revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
                        return ;
                    case  4 :
                        attIdx = context.getAttribute("", "ID");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        _AuthenticationContextStatement = ((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl) spawnChildFromLeaveAttribute((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl.class), 5, ___uri, ___local, ___qname));
                        return ;
                    case  0 :
                        state = 3;
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
                        case  7 :
                            state = 8;
                            eatText1(value);
                            return ;
                        case  6 :
                            revertToParentFromText(value);
                            return ;
                        case  4 :
                            attIdx = context.getAttribute("", "ID");
                            if (attIdx >= 0) {
                                context.consumeAttribute(attIdx);
                                context.getCurrentHandler().text(value);
                                return ;
                            }
                            _AuthenticationContextStatement = ((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl) spawnChildFromText((com.sun.identity.liberty.ws.common.jaxb.ac.impl.AuthenticationContextStatementTypeImpl.class), 5, value));
                            return ;
                        case  1 :
                            state = 2;
                            eatText2(value);
                            return ;
                        case  0 :
                            state = 3;
                            continue outer;
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
                _AuthnContextStatementRef = com.sun.xml.bind.WhiteSpaceProcessor.collapse(value);
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

        private void eatText2(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            try {
                _AuthnContextClassRef = com.sun.xml.bind.WhiteSpaceProcessor.collapse(value);
            } catch (java.lang.Exception e) {
                handleParseConversionException(e);
            }
        }

    }

}
