package com.rvh.openoffice.parts;

import com.rvh.openoffice.parts.config.Config;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;

//TODO: Make PartsCreator a subject within observer pattern
public abstract class PartsCreator {

    protected XMLStreamWriter xsw;
    protected final ZipArchiveOutputStream zos;
    private String partName;
    protected Config config;

    public PartsCreator(ZipArchiveOutputStream zos, Config config) {
        this.zos = zos;
        this.config = config;
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
