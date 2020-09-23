//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.09.14 at 08:56:38 PM CEST 
//


package com.rvh.openoffice.model.officepackage.contentTypes;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.rvh.collector.excel.model package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Types_QNAME = new QName("http://schemas.openxmlformats.org/package/2006/content-types", "Types");
    private final static QName _Default_QNAME = new QName("http://schemas.openxmlformats.org/package/2006/content-types", "Default");
    private final static QName _Override_QNAME = new QName("http://schemas.openxmlformats.org/package/2006/content-types", "Override");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.rvh.collector.excel.model
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CTTypes }
     * 
     */
    public CTTypes createCTTypes() {
        return new CTTypes();
    }

    /**
     * Create an instance of {@link CTDefault }
     * 
     */
    public CTDefault createCTDefault() {
        return new CTDefault();
    }

    /**
     * Create an instance of {@link CTOverride }
     * 
     */
    public CTOverride createCTOverride() {
        return new CTOverride();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CTTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.openxmlformats.org/package/2006/content-types", name = "Types")
    public JAXBElement<CTTypes> createTypes(
            CTTypes value) {
        return new JAXBElement<CTTypes>(_Types_QNAME, CTTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CTDefault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.openxmlformats.org/package/2006/content-types", name = "Default")
    public JAXBElement<CTDefault> createDefault(
            CTDefault value) {
        return new JAXBElement<CTDefault>(_Default_QNAME, CTDefault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CTOverride }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://schemas.openxmlformats.org/package/2006/content-types", name = "Override")
    public JAXBElement<CTOverride> createOverride(
            CTOverride value) {
        return new JAXBElement<CTOverride>(_Override_QNAME, CTOverride.class, null, value);
    }

}
