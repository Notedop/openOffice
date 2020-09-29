package com.rvh.openoffice.parts;

import com.rvh.openoffice.parts.config.Config;
import com.rvh.openoffice.parts.config.ContentTypeConfig;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public class ContentTypePartCreator extends PartsCreator<ContentTypeConfig> {

    public ContentTypePartCreator(ZipArchiveOutputStream zos, Config config) {
        super(zos, config);
    }

    @Override
    public void createPart() throws XMLStreamException, IOException {

    }

    @Override
    public void createHeader(String name) throws XMLStreamException, IOException {
        zos.putArchiveEntry(new ZipArchiveEntry("[Content_Types].xml"));
    }

    @Override
    public void createFooter() throws XMLStreamException, IOException {
        zos.closeArchiveEntry();
    }
}
