//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:33:54 AM PDT 
//


package com.sun.identity.liberty.ws.disco.jaxb.impl;

public class ModifyElementImpl
    extends com.sun.identity.liberty.ws.disco.jaxb.impl.ModifyTypeImpl
    implements com.sun.identity.liberty.ws.disco.jaxb.ModifyElement, com.sun.xml.bind.RIElement, com.sun.xml.bind.JAXBObject, com.sun.identity.federation.jaxb.entityconfig.impl.runtime.UnmarshallableObject, com.sun.identity.federation.jaxb.entityconfig.impl.runtime.XMLSerializable, com.sun.identity.federation.jaxb.entityconfig.impl.runtime.ValidatableObject
{

    public final static java.lang.Class version = (com.sun.identity.liberty.ws.disco.jaxb.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (com.sun.identity.liberty.ws.disco.jaxb.ModifyElement.class);
    }

    public java.lang.String ____jaxb_ri____getNamespaceURI() {
        return "urn:liberty:disco:2003-08";
    }

    public java.lang.String ____jaxb_ri____getLocalName() {
        return "Modify";
    }

    public com.sun.identity.federation.jaxb.entityconfig.impl.runtime.UnmarshallingEventHandler createUnmarshaller(com.sun.identity.federation.jaxb.entityconfig.impl.runtime.UnmarshallingContext context) {
        return new com.sun.identity.liberty.ws.disco.jaxb.impl.ModifyElementImpl.Unmarshaller(context);
    }

    public void serializeBody(com.sun.identity.federation.jaxb.entityconfig.impl.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        context.startElement("urn:liberty:disco:2003-08", "Modify");
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
        return (com.sun.identity.liberty.ws.disco.jaxb.ModifyElement.class);
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
+"q\u0000~\u0000\u0007ppsr\u0000\u001dcom.sun.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\bpps"
+"q\u0000~\u0000\rsr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psq\u0000~\u0000\rq\u0000~\u0000\u0011"
+"psq\u0000~\u0000\rq\u0000~\u0000\u0011psq\u0000~\u0000\u0000q\u0000~\u0000\u0011p\u0000sq\u0000~\u0000\rppsr\u0000 com.sun.msv.grammar.On"
+"eOrMoreExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001ccom.sun.msv.grammar.UnaryExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0001\u0002\u0000\u0001L\u0000\u0003expq\u0000~\u0000\u0003xq\u0000~\u0000\u0004q\u0000~\u0000\u0011psr\u0000 com.sun.msv.grammar.Attribute"
+"Exp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0003L\u0000\tnameClassq\u0000~\u0000\u0001xq\u0000~\u0000\u0004q\u0000~\u0000\u0011psr\u00002co"
+"m.sun.msv.grammar.Expression$AnyStringExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000x"
+"q\u0000~\u0000\u0004sq\u0000~\u0000\u0010\u0001q\u0000~\u0000\u001csr\u0000 com.sun.msv.grammar.AnyNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000"
+"\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.NameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.su"
+"n.msv.grammar.Expression$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004q"
+"\u0000~\u0000\u001dq\u0000~\u0000\"sr\u0000#com.sun.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L"
+"\u0000\tlocalNamet\u0000\u0012Ljava/lang/String;L\u0000\fnamespaceURIq\u0000~\u0000$xq\u0000~\u0000\u001ft\u0000"
+"8com.sun.identity.liberty.ws.disco.jaxb.ResourceIDElementt\u0000+"
+"http://java.sun.com/jaxb/xjc/dummy-elementssq\u0000~\u0000\u0000q\u0000~\u0000\u0011p\u0000sq\u0000~"
+"\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\rppsq\u0000~\u0000\u0016q\u0000~\u0000\u0011psq\u0000~\u0000\u0019q\u0000~\u0000\u0011pq\u0000~\u0000\u001cq\u0000~\u0000 q\u0000~\u0000\""
+"sq\u0000~\u0000#t\u00005com.sun.identity.liberty.ws.disco.jaxb.ResourceIDTy"
+"peq\u0000~\u0000\'sq\u0000~\u0000\rppsq\u0000~\u0000\u0019q\u0000~\u0000\u0011psr\u0000\u001bcom.sun.msv.grammar.DataExp\u0000\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datatype/Datatype;L\u0000\u0006exceptq\u0000~"
+"\u0000\u0003L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/StringPair;xq\u0000~\u0000\u0004q\u0000~\u0000\u0011psr\u0000\"com"
+".sun.msv.datatype.xsd.QnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000*com.sun.msv.da"
+"tatype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.sun.msv.datat"
+"ype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.datatype.xsd."
+"XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUriq\u0000~\u0000$L\u0000\btypeNameq\u0000~\u0000"
+"$L\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/datatype/xsd/WhiteSpaceProcess"
+"or;xpt\u0000 http://www.w3.org/2001/XMLSchemat\u0000\u0005QNamesr\u00005com.sun."
+"msv.datatype.xsd.WhiteSpaceProcessor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,"
+"com.sun.msv.datatype.xsd.WhiteSpaceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u0000"
+"0com.sun.msv.grammar.Expression$NullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000"
+"xq\u0000~\u0000\u0004q\u0000~\u0000\u0011psr\u0000\u001bcom.sun.msv.util.StringPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tloc"
+"alNameq\u0000~\u0000$L\u0000\fnamespaceURIq\u0000~\u0000$xpq\u0000~\u0000=q\u0000~\u0000<sq\u0000~\u0000#t\u0000\u0004typet\u0000)h"
+"ttp://www.w3.org/2001/XMLSchema-instanceq\u0000~\u0000\"sq\u0000~\u0000#t\u0000\nResour"
+"ceIDt\u0000\u0019urn:liberty:disco:2003-08sq\u0000~\u0000\u0000q\u0000~\u0000\u0011p\u0000sq\u0000~\u0000\rppsq\u0000~\u0000\u0016q"
+"\u0000~\u0000\u0011psq\u0000~\u0000\u0019q\u0000~\u0000\u0011pq\u0000~\u0000\u001cq\u0000~\u0000 q\u0000~\u0000\"sq\u0000~\u0000#t\u0000Acom.sun.identity.li"
+"berty.ws.disco.jaxb.EncryptedResourceIDElementq\u0000~\u0000\'sq\u0000~\u0000\u0000q\u0000~"
+"\u0000\u0011p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\rppsq\u0000~\u0000\u0016q\u0000~\u0000\u0011psq\u0000~\u0000\u0019q\u0000~\u0000\u0011pq\u0000~\u0000\u001cq\u0000"
+"~\u0000 q\u0000~\u0000\"sq\u0000~\u0000#t\u0000>com.sun.identity.liberty.ws.disco.jaxb.Encr"
+"yptedResourceIDTypeq\u0000~\u0000\'sq\u0000~\u0000\rppsq\u0000~\u0000\u0019q\u0000~\u0000\u0011pq\u0000~\u00005q\u0000~\u0000Eq\u0000~\u0000\"s"
+"q\u0000~\u0000#t\u0000\u0013EncryptedResourceIDq\u0000~\u0000Jq\u0000~\u0000\"sq\u0000~\u0000\rppsq\u0000~\u0000\u0016q\u0000~\u0000\u0011psq\u0000"
+"~\u0000\u0000q\u0000~\u0000\u0011p\u0000sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\rppsq\u0000~\u0000\u0016q\u0000~\u0000\u0011psq\u0000~\u0000\u0019q\u0000~\u0000\u0011pq"
+"\u0000~\u0000\u001cq\u0000~\u0000 q\u0000~\u0000\"sq\u0000~\u0000#t\u00006com.sun.identity.liberty.ws.disco.jax"
+"b.InsertEntryTypeq\u0000~\u0000\'sq\u0000~\u0000\rppsq\u0000~\u0000\u0019q\u0000~\u0000\u0011pq\u0000~\u00005q\u0000~\u0000Eq\u0000~\u0000\"sq\u0000"
+"~\u0000#t\u0000\u000bInsertEntryq\u0000~\u0000Jq\u0000~\u0000\"sq\u0000~\u0000\rppsq\u0000~\u0000\u0016q\u0000~\u0000\u0011psq\u0000~\u0000\u0000q\u0000~\u0000\u0011p\u0000"
+"sq\u0000~\u0000\u0007ppsq\u0000~\u0000\u0000pp\u0000sq\u0000~\u0000\rppsq\u0000~\u0000\u0016q\u0000~\u0000\u0011psq\u0000~\u0000\u0019q\u0000~\u0000\u0011pq\u0000~\u0000\u001cq\u0000~\u0000 q"
+"\u0000~\u0000\"sq\u0000~\u0000#t\u00006com.sun.identity.liberty.ws.disco.jaxb.RemoveEn"
+"tryTypeq\u0000~\u0000\'sq\u0000~\u0000\rppsq\u0000~\u0000\u0019q\u0000~\u0000\u0011pq\u0000~\u00005q\u0000~\u0000Eq\u0000~\u0000\"sq\u0000~\u0000#t\u0000\u000bRemo"
+"veEntryq\u0000~\u0000Jq\u0000~\u0000\"sq\u0000~\u0000\rppsq\u0000~\u0000\u0019q\u0000~\u0000\u0011psq\u0000~\u00002ppsr\u0000\u001fcom.sun.msv"
+".datatype.xsd.IDType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000#com.sun.msv.datatype.xsd."
+"NcnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\"com.sun.msv.datatype.xsd.TokenType\u0000"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000#com.sun.msv.datatype.xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001"
+"Z\u0000\risAlwaysValidxq\u0000~\u00007q\u0000~\u0000<t\u0000\u0002IDq\u0000~\u0000@\u0000q\u0000~\u0000Bsq\u0000~\u0000Cq\u0000~\u0000\u0081q\u0000~\u0000<s"
+"q\u0000~\u0000#t\u0000\u0002idt\u0000\u0000q\u0000~\u0000\"sq\u0000~\u0000\rppsq\u0000~\u0000\u0019q\u0000~\u0000\u0011pq\u0000~\u00005q\u0000~\u0000Eq\u0000~\u0000\"sq\u0000~\u0000#t"
+"\u0000\u0006Modifyq\u0000~\u0000Jsr\u0000\"com.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002"
+"\u0000\u0001L\u0000\bexpTablet\u0000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHa"
+"sh;xpsr\u0000-com.sun.msv.grammar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8"
+"\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005countB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$Lcom/sun/msv/gramma"
+"r/ExpressionPool;xp\u0000\u0000\u0000\"\u0001pq\u0000~\u0000\u0018q\u0000~\u0000,q\u0000~\u0000Mq\u0000~\u0000Uq\u0000~\u0000cq\u0000~\u0000qq\u0000~\u0000\f"
+"q\u0000~\u0000^q\u0000~\u0000lq\u0000~\u0000\u0015q\u0000~\u0000+q\u0000~\u0000Lq\u0000~\u0000Tq\u0000~\u0000bq\u0000~\u0000pq\u0000~\u0000yq\u0000~\u0000)q\u0000~\u0000Rq\u0000~\u0000`"
+"q\u0000~\u0000nq\u0000~\u0000\nq\u0000~\u0000\tq\u0000~\u0000\u0012q\u0000~\u0000\u000eq\u0000~\u0000\u000bq\u0000~\u0000]q\u0000~\u0000kq\u0000~\u0000\u000fq\u0000~\u00000q\u0000~\u0000Yq\u0000~\u0000g"
+"q\u0000~\u0000uq\u0000~\u0000\u0086q\u0000~\u0000\u0013x"));
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
            return com.sun.identity.liberty.ws.disco.jaxb.impl.ModifyElementImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  1 :
                        attIdx = context.getAttribute("", "id");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        if (("ResourceID" == ___local)&&("urn:liberty:disco:2003-08" == ___uri)) {
                            spawnHandlerFromEnterElement((((com.sun.identity.liberty.ws.disco.jaxb.impl.ModifyTypeImpl)com.sun.identity.liberty.ws.disco.jaxb.impl.ModifyElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        if (("ResourceID" == ___local)&&("urn:liberty:disco:2003-08" == ___uri)) {
                            spawnHandlerFromEnterElement((((com.sun.identity.liberty.ws.disco.jaxb.impl.ModifyTypeImpl)com.sun.identity.liberty.ws.disco.jaxb.impl.ModifyElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        if (("EncryptedResourceID" == ___local)&&("urn:liberty:disco:2003-08" == ___uri)) {
                            spawnHandlerFromEnterElement((((com.sun.identity.liberty.ws.disco.jaxb.impl.ModifyTypeImpl)com.sun.identity.liberty.ws.disco.jaxb.impl.ModifyElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        if (("EncryptedResourceID" == ___local)&&("urn:liberty:disco:2003-08" == ___uri)) {
                            spawnHandlerFromEnterElement((((com.sun.identity.liberty.ws.disco.jaxb.impl.ModifyTypeImpl)com.sun.identity.liberty.ws.disco.jaxb.impl.ModifyElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        if (("InsertEntry" == ___local)&&("urn:liberty:disco:2003-08" == ___uri)) {
                            spawnHandlerFromEnterElement((((com.sun.identity.liberty.ws.disco.jaxb.impl.ModifyTypeImpl)com.sun.identity.liberty.ws.disco.jaxb.impl.ModifyElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        if (("RemoveEntry" == ___local)&&("urn:liberty:disco:2003-08" == ___uri)) {
                            spawnHandlerFromEnterElement((((com.sun.identity.liberty.ws.disco.jaxb.impl.ModifyTypeImpl)com.sun.identity.liberty.ws.disco.jaxb.impl.ModifyElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        spawnHandlerFromEnterElement((((com.sun.identity.liberty.ws.disco.jaxb.impl.ModifyTypeImpl)com.sun.identity.liberty.ws.disco.jaxb.impl.ModifyElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname, __atts);
                        return ;
                    case  0 :
                        if (("Modify" == ___local)&&("urn:liberty:disco:2003-08" == ___uri)) {
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
                        attIdx = context.getAttribute("", "id");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        spawnHandlerFromLeaveElement((((com.sun.identity.liberty.ws.disco.jaxb.impl.ModifyTypeImpl)com.sun.identity.liberty.ws.disco.jaxb.impl.ModifyElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
                        return ;
                    case  2 :
                        if (("Modify" == ___local)&&("urn:liberty:disco:2003-08" == ___uri)) {
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
                        if (("id" == ___local)&&("" == ___uri)) {
                            spawnHandlerFromEnterAttribute((((com.sun.identity.liberty.ws.disco.jaxb.impl.ModifyTypeImpl)com.sun.identity.liberty.ws.disco.jaxb.impl.ModifyElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
                            return ;
                        }
                        spawnHandlerFromEnterAttribute((((com.sun.identity.liberty.ws.disco.jaxb.impl.ModifyTypeImpl)com.sun.identity.liberty.ws.disco.jaxb.impl.ModifyElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
                        return ;
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
                        attIdx = context.getAttribute("", "id");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        spawnHandlerFromLeaveAttribute((((com.sun.identity.liberty.ws.disco.jaxb.impl.ModifyTypeImpl)com.sun.identity.liberty.ws.disco.jaxb.impl.ModifyElementImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
                        return ;
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
                            attIdx = context.getAttribute("", "id");
                            if (attIdx >= 0) {
                                context.consumeAttribute(attIdx);
                                context.getCurrentHandler().text(value);
                                return ;
                            }
                            spawnHandlerFromText((((com.sun.identity.liberty.ws.disco.jaxb.impl.ModifyTypeImpl)com.sun.identity.liberty.ws.disco.jaxb.impl.ModifyElementImpl.this).new Unmarshaller(context)), 2, value);
                            return ;
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
