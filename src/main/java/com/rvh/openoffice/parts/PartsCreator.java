package com.rvh.openoffice.parts;

import com.rvh.openoffice.parts.config.Config;
import com.rvh.openoffice.parts.config.ConfigCollection;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public abstract class PartsCreator<t extends Config> {

    protected final ZipArchiveOutputStream zos;
    protected XMLStreamWriter xsw;
    protected ConfigCollection<t> configCollection;
    protected Config config;
    protected XMLOutputFactory xof = XMLOutputFactory.newFactory();
    private String partName;

    public PartsCreator(ZipArchiveOutputStream zos, ConfigCollection<t> configCollection) {
        this.zos = zos;
        this.configCollection = configCollection;
        try {
            xsw = xof.createXMLStreamWriter(new OutputStreamWriter(zos));
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
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
