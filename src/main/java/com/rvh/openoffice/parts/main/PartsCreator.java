package com.rvh.openoffice.parts.main;

import com.rvh.openoffice.parts.main.config.Config;
import com.rvh.openoffice.parts.main.config.ConfigCollection;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public abstract class PartsCreator<T extends Config> {

    protected final ZipArchiveOutputStream zos;
    protected XMLStreamWriter xsw;
    protected ConfigCollection<T> configCollection;
    protected Config config;
    protected XMLOutputFactory xof = XMLOutputFactory.newFactory();
    private String partName;

    public PartsCreator(ZipArchiveOutputStream zos, ConfigCollection<T> configCollection) {
        this.zos = zos;
        this.configCollection = configCollection;
        try {
            xsw = xof.createXMLStreamWriter(new OutputStreamWriter(zos));
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    public void writeBasicElement(String element, String data) throws XMLStreamException {
        writeBasicElement(null, element, data);
    }
    public void writeBasicElement(String nameSpace, String element, String data) throws XMLStreamException {
        if (nameSpace != null && !nameSpace.isEmpty()) {
            xsw.writeStartElement(nameSpace, element);
        } else {
            xsw.writeStartElement(element);
        }
        xsw.writeCharacters(data);
        xsw.writeEndElement();
    }

    public abstract void createPart() throws XMLStreamException, IOException;

    public abstract void createHeader(String name) throws XMLStreamException, IOException;

    public abstract void createFooter() throws XMLStreamException, IOException;

    public String getOriginalPartName() {
        return partName;
    }

    public void setOriginalPartName(String partName) {
        this.partName = partName;
    }
}
