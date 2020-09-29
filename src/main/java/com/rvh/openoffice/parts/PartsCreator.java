package com.rvh.openoffice.parts;

import com.rvh.openoffice.parts.config.Config;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import javax.sql.DataSource;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;
import java.util.List;

//TODO: Make PartsCreator a subject within observer pattern
public abstract class PartsCreator {

    protected final DataSource dataSource;
    protected XMLStreamWriter xsw;
    protected final ZipArchiveOutputStream zos;
    private String partName;
    protected List<Config> configs;

    public PartsCreator(DataSource dataSource, ZipArchiveOutputStream zos, List<Config> configs) {
        this.dataSource = dataSource;
        this.zos = zos;
        this.configs = configs;
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
