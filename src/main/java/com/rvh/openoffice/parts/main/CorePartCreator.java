package com.rvh.openoffice.parts.main;

import com.rvh.openoffice.parts.main.config.Config;
import com.rvh.openoffice.parts.main.config.ConfigCollection;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class CorePartCreator extends PartsCreator<Config>{

    public CorePartCreator(ZipArchiveOutputStream zos, ConfigCollection<Config> configCollection) {
        super(zos, configCollection);
    }

    @Override
    public void createPart() throws XMLStreamException, IOException {

        createHeader("docProps/core.xml");
        createFooter();

    }

    @Override
    public void createHeader(String name) throws XMLStreamException, IOException {

        zos.putArchiveEntry(new ZipArchiveEntry(name));
        xsw.writeStartDocument();
        xsw.writeStartElement( "cp:coreProperties" );
        xsw.writeNamespace("cp", "http://schemas.openxmlformats.org/package/2006/metadata/core-properties");
        xsw.writeNamespace("dc", "http://purl.org/dc/elements/1.1/");
        xsw.writeNamespace("dcterms", "http://purl.org/dc/terms/");
        xsw.writeNamespace("dcmitype", "http://purl.org/dc/dcmitype/");
        xsw.writeNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");

        xsw.writeStartElement("http://purl.org/dc/elements/1.1/", "creator");
        xsw.writeCharacters("Raoul");
        xsw.writeEndElement();

        xsw.writeStartElement("http://schemas.openxmlformats.org/package/2006/metadata/core-properties", "lastModifiedBy");
        xsw.writeCharacters("Raoul");
        xsw.writeEndElement();

        xsw.writeStartElement("http://purl.org/dc/terms/", "created");
        xsw.writeAttribute("http://www.w3.org/2001/XMLSchema-instance", "type", "dcterms:W3CDTF");
        //Instant instant = Instant.now();

        ZonedDateTime zdt = ZonedDateTime.now();
        String dateValue = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withZone(ZoneId.of("UTC")).format(zdt);
        xsw.writeCharacters(dateValue);
        xsw.writeEndElement();

        xsw.writeStartElement("http://purl.org/dc/terms/", "modified");
        xsw.writeAttribute("http://www.w3.org/2001/XMLSchema-instance", "type", "dcterms:W3CDTF");
        xsw.writeCharacters(dateValue);
        xsw.writeEndElement();

    }

    @Override
    public void createFooter() throws XMLStreamException, IOException {

        xsw.writeEndElement();//end Core
        xsw.flush();

        zos.closeArchiveEntry();

    }
}
