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
