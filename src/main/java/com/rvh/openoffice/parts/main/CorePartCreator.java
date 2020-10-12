package com.rvh.openoffice.parts.main;

import com.rvh.openoffice.parts.main.config.*;
import com.rvh.openoffice.parts.main.enums.RelationTypes;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class CorePartCreator extends PartsCreator<CoreConfig>{

    private final CoreConfig coreConfig;
    private final ConfigCollection<RelConfig> relConfigs;
    private final ConfigCollection<ContentTypeConfig> contentTypeConfigs;

    public CorePartCreator(ZipArchiveOutputStream zos, CoreConfig coreConfig, ConfigCollection<RelConfig> relConfigs, ConfigCollection<ContentTypeConfig> contentTypeConfigs) {
        super(zos, null);
        this.coreConfig = coreConfig;
        this.relConfigs = relConfigs;
        this.contentTypeConfigs = contentTypeConfigs;
    }

    @Override
    public void createPart() throws XMLStreamException, IOException {

        createHeader("docProps/core.xml");
        createFooter();

    }

    @Override
    public void createHeader(String name) throws XMLStreamException, IOException {

        //register part in ContentType
        contentTypeConfigs.addConfig(new ContentTypeConfig("Override", "/docProps/core.xml",
                "application/vnd.openxmlformats-package.core-properties+xml"));

        //create relation in RelationPart
        String relId = "rId" + (relConfigs.countConfigByName(".rels") + 1);
        relConfigs.addConfig(new RelConfig(".rels", relId, RelationTypes.CORE,"docProps/core.xml", "_rels/" ));

        zos.putArchiveEntry(new ZipArchiveEntry(name));
        xsw.writeStartDocument();

        writeNameSpace();

        writeBasic("http://schemas.openxmlformats.org/package/2006/metadata/core-properties","category", coreConfig.getCategory());
        writeBasic("http://schemas.openxmlformats.org/package/2006/metadata/core-properties","contentStatus", coreConfig.getContentStatus());
        writeCreatedDate();
        writeBasic("http://purl.org/dc/elements/1.1/", "creator", coreConfig.getCreator());
        writeBasic("http://purl.org/dc/elements/1.1/","description", coreConfig.getDescription());
        writeBasic("http://schemas.openxmlformats.org/package/2006/metadata/core-properties", "lastModifiedBy", coreConfig.getLastModifiedBy());
        writeBasic("http://purl.org/dc/elements/1.1/","language", coreConfig.getLanguage());
        writeModifiedDate();
        writeBasic("http://purl.org/dc/elements/1.1/","title", coreConfig.getTitle());
        writeBasic("http://purl.org/dc/elements/1.1/","subject", coreConfig.getSubject());
        writeBasic("http://schemas.openxmlformats.org/package/2006/metadata/core-properties","version", coreConfig.getVersion());

    }

    private void writeBasic(String nameSpace, String element, String data) throws XMLStreamException {
        xsw.writeStartElement(nameSpace, element);
        xsw.writeCharacters(data);
        xsw.writeEndElement();
    }

    private void writeNameSpace() throws XMLStreamException {
        xsw.writeStartElement( "cp:coreProperties" );
        xsw.writeNamespace("cp", "http://schemas.openxmlformats.org/package/2006/metadata/core-properties");
        xsw.writeNamespace("dc", "http://purl.org/dc/elements/1.1/");
        xsw.writeNamespace("dcterms", "http://purl.org/dc/terms/");
        xsw.writeNamespace("dcmitype", "http://purl.org/dc/dcmitype/");
        xsw.writeNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
    }

    private void writeCreatedDate() throws XMLStreamException {
        xsw.writeStartElement("http://purl.org/dc/terms/", "created");
        xsw.writeAttribute("http://www.w3.org/2001/XMLSchema-instance", "type", "dcterms:W3CDTF");

        String createdDateValue = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
                .withZone(ZoneId.of("UTC"))
                .format(coreConfig.getCreated());

        xsw.writeCharacters(createdDateValue);
        xsw.writeEndElement();
    }

    private void writeModifiedDate() throws XMLStreamException {
        String modifiedDateValue = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
                .withZone(ZoneId.of("UTC"))
                .format(coreConfig.getModified());

        xsw.writeStartElement("http://purl.org/dc/terms/", "modified");
        xsw.writeAttribute("http://www.w3.org/2001/XMLSchema-instance", "type", "dcterms:W3CDTF");
        xsw.writeCharacters(modifiedDateValue);
        xsw.writeEndElement();
    }

    @Override
    public void createFooter() throws XMLStreamException, IOException {

        xsw.writeEndElement();//end Core
        xsw.flush();

        zos.closeArchiveEntry();

    }

    public CoreConfig getCoreConfig() {
        return coreConfig;
    }
}
