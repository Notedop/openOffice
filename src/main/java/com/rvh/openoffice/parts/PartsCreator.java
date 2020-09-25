package com.rvh.openoffice.parts;

import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import javax.sql.DataSource;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;

public abstract class PartsCreator {

    protected final DataSource dataSource;
    protected XMLStreamWriter xsw;
    protected final ZipArchiveOutputStream zos;
    private String partName;
    private List<Config> configs;

    public PartsCreator(DataSource dataSource, ZipArchiveOutputStream zos) {
        this.dataSource = dataSource;
        this.zos = zos;
    }

    public abstract void createPart(String name, String sql) throws XMLStreamException, IOException;

    public abstract void createHeader(String name) throws XMLStreamException, IOException;

    public abstract void createFooter() throws XMLStreamException, IOException;

    public String getOriginalPartName() {
        return partName;
    }

}
