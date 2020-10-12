package com.rvh.openoffice.parts.main;

import com.rvh.openoffice.parts.main.config.ConfigCollection;
import com.rvh.openoffice.parts.main.config.ContentTypeConfig;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public class ContentTypePartCreator extends PartsCreator<ContentTypeConfig> {

    private static final String CONTENT_TYPE = "[Content_Types].xml";

    public ContentTypePartCreator(ZipArchiveOutputStream zos, ConfigCollection<ContentTypeConfig> configCollection) {
        super(zos, configCollection);
    }

    @Override
    public void createPart() throws XMLStreamException, IOException {

        createHeader(CONTENT_TYPE);

        for (ContentTypeConfig config : configCollection.getConfigs()) {
            xsw.writeEmptyElement(config.getName());
            xsw.writeAttribute("PartName", config.getPartName());
            xsw.writeAttribute("ContentType", config.getContentType());

        }

        //TODO: move to AppPartCreator once implemented
        xsw.writeEmptyElement("Override");
        xsw.writeAttribute("PartName", "/docProps/app.xml");
        xsw.writeAttribute("ContentType", "application/vnd.openxmlformats-officedocument.extended-properties+xml");

        createFooter();

    }

    @Override
    public void createHeader(String name) throws XMLStreamException, IOException {
        zos.putArchiveEntry(new ZipArchiveEntry(name));
        xsw.writeStartDocument("UTF-8", "1.0");
        xsw.writeStartElement("Types");
        xsw.writeDefaultNamespace("http://schemas.openxmlformats.org/package/2006/content-types");
        writeDefaultElement("rels", "application/vnd.openxmlformats-package.relationships+xml");
        writeDefaultElement("xml", "application/xml");

    }

    private void writeDefaultElement(String extension, String contentType) throws XMLStreamException {
        xsw.writeEmptyElement("Default");
        xsw.writeAttribute("Extension", extension);
        xsw.writeAttribute("ContentType", contentType);
    }

    @Override
    public void createFooter() throws XMLStreamException, IOException {
        xsw.writeEndElement();//Types
        xsw.flush();
        zos.closeArchiveEntry();
    }
}
