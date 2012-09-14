//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.6-b27-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.09.08 at 08:59:44 DU CEST 
//

package com.sun.identity.diagnostic.base.core.jaxbgen.impl.runtime;

import javax.xml.namespace.NamespaceContext;

/**
 * Maintains namespace&lt;->prefix bindings.
 * 
 * <p>
 * This interface extends {@link NamespaceContext} and provides
 * an additional functionality, which is necessary to declare
 * namespaced attributes on elements. The added method is for
 * self-consumption by the marshaller.
 * 
 * This object is composed into a Serializer.
 */
public interface NamespaceContext2 extends NamespaceContext
{
    /**
     * Declares a new namespace binding within the current context.
     * 
     * <p>
     * The prefix is automatically assigned by MarshallingContext. If
     * a given namespace URI is already declared, nothing happens.
     * 
     * <p>
     * It is <b>NOT</b> an error to declare the same namespace URI
     * more than once.
     * 
     * <p>
     * For marshalling to work correctly, all namespace bindings
     * for an element must be declared between its startElement method and
     * its endAttributes event. Calling the same method with the same
     * parameter between the endAttributes and the endElement returns
     * the same prefix.
     * 
     * @param   requirePrefix
     *      If this parameter is true, this method must assign a prefix
     *      to this namespace, even if it's already bound to the default
     *      namespace. IOW, this method will never return null if this
     *      flag is true. This functionality is necessary to declare
     *      namespace URI used for attribute names.
     * @param   preferedPrefix
     *      If the caller has any particular preference to the
     *      prefix, pass that as a parameter. The callee will try
     *      to honor it. Set null if there's no particular preference.
     * 
     * @return
     *      returns the assigned prefix. If the namespace is bound to
     *      the default namespace, null is returned.
     */
    String declareNamespace( String namespaceUri, String preferedPrefix, boolean requirePrefix );
}
