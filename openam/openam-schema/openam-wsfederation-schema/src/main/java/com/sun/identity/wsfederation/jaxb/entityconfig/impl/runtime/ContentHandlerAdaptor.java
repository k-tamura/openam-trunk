//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.11 at 10:34:16 AM PDT 
//

package com.sun.identity.wsfederation.jaxb.entityconfig.impl.runtime;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 * Receives SAX2 events and send the equivalent events to
 * {@link com.sun.xml.bind.serializer.XMLSerializer}
 * 
 * @author
 *     Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
 */
public class ContentHandlerAdaptor implements ContentHandler {

    /** Stores newly declared prefix-URI mapping. */
    private final ArrayList prefixMap = new ArrayList();
    
    /** Events will be sent to this object. */
    private final XMLSerializer serializer;
    
    private final StringBuffer text = new StringBuffer();
    
    
    public ContentHandlerAdaptor( XMLSerializer _serializer ) {
        this.serializer = _serializer;
    }
    
    

    public void startDocument() throws SAXException {
        prefixMap.clear();
    }

    public void endDocument() throws SAXException {
    }

    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        prefixMap.add(prefix);
        prefixMap.add(uri);
    }

    public void endPrefixMapping(String prefix) throws SAXException {
    }

    public void startElement(String namespaceURI, String localName, String qName, Attributes atts)
        throws SAXException {
        
        flushText();

        int len = atts.getLength();
        
        serializer.startElement(namespaceURI,localName);
        // declare namespace events
        for( int i=0; i<len; i++ ) {
            String qname = atts.getQName(i);
            int idx = qname.indexOf(':');
            String prefix = (idx==-1)?qname:qname.substring(0,idx);
            
            serializer.getNamespaceContext().declareNamespace(
                atts.getURI(i), prefix, true );
        }
        for( int i=0; i<prefixMap.size(); i+=2 ) {
            String prefix = (String)prefixMap.get(i); 
            serializer.getNamespaceContext().declareNamespace(
                (String)prefixMap.get(i+1),
                prefix,
                prefix.length()!=0 );
        }
        
        serializer.endNamespaceDecls();
        // fire attribute events
        for( int i=0; i<len; i++ ) {
            serializer.startAttribute( atts.getURI(i), atts.getLocalName(i) );
            serializer.text(atts.getValue(i),null);
            serializer.endAttribute();
        }
        prefixMap.clear();
        serializer.endAttributes();
    }

    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        flushText();
        serializer.endElement();
    }
    
    private void flushText() throws SAXException {
        if( text.length()!=0 ) {
            serializer.text(text.toString(),null);
            text.setLength(0);
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        text.append(ch,start,length);
    }

    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        text.append(ch,start,length);
    }



    public void setDocumentLocator(Locator locator) {
    }
    
    public void processingInstruction(String target, String data) throws SAXException {
    }

    public void skippedEntity(String name) throws SAXException {
    }

}
