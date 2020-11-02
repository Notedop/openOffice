package com.rvh.openoffice.parts.main;

import com.rvh.openoffice.parts.main.config.*;
import com.rvh.openoffice.parts.main.enums.ContentTypes;
import com.rvh.openoffice.parts.main.enums.RelationShipTypes;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static com.rvh.openoffice.parts.main.enums.NameSpaces.*;

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
                ContentTypes.CORE_PROPERTIES.getPart()));

        //create relation in RelationPart
        String relId = "rId" + (relConfigs.countConfigByName(".rels") + 1);
        relConfigs.addConfig(new RelConfig(".rels", relId, RelationShipTypes.CORE,"docProps/core.xml", "_rels/" ));

        zos.putArchiveEntry(new ZipArchiveEntry(name));
        xsw.writeStartDocument();

        writeNameSpace();

        writeBasicElement(CORE_PROPERTIES.getSchema(),"category", coreConfig.getCategory());
        writeBasicElement(CORE_PROPERTIES.getSchema(),"contentStatus", coreConfig.getContentStatus());
        writeCreatedDate();
        writeBasicElement(DUBLIN_CORE_ELEMENTS.getSchema(), "creator", coreConfig.getCreator());
        writeBasicElement(DUBLIN_CORE_ELEMENTS.getSchema(),"description", coreConfig.getDescription());
        writeBasicElement(CORE_PROPERTIES.getSchema(), "lastModifiedBy", coreConfig.getLastModifiedBy());
        writeBasicElement(DUBLIN_CORE_ELEMENTS.getSchema(),"language", coreConfig.getLanguage());
        writeModifiedDate();
        writeBasicElement(DUBLIN_CORE_ELEMENTS.getSchema(),"title", coreConfig.getTitle());
        writeBasicElement(DUBLIN_CORE_ELEMENTS.getSchema(),"subject", coreConfig.getSubject());
        writeBasicElement(CORE_PROPERTIES.getSchema(),"version", coreConfig.getVersion());

    }


    private void writeNameSpace() throws XMLStreamException {
        xsw.writeStartElement( "cp:coreProperties" );
        xsw.writeNamespace("cp", CORE_PROPERTIES.getSchema());
        xsw.writeNamespace("dc", DUBLIN_CORE_ELEMENTS.getSchema());
        xsw.writeNamespace("dcterms", DUBLIN_CORE_TERMS.getSchema());
        xsw.writeNamespace("dcmitype", DUBLIC_CORE_DCMITYPE.getSchema());
        xsw.writeNamespace("xsi", XSI.getSchema());
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

        xsw.writeStartElement(DUBLIN_CORE_TERMS.getSchema(), "modified");
        xsw.writeAttribute(XSI.getSchema(), "type", "dcterms:W3CDTF");
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
