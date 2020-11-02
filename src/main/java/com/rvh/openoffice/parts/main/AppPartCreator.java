package com.rvh.openoffice.parts.main;

import com.rvh.openoffice.parts.main.config.AppConfig;
import com.rvh.openoffice.parts.main.config.ConfigCollection;
import com.rvh.openoffice.parts.main.config.ContentTypeConfig;
import com.rvh.openoffice.parts.main.enums.ContentTypes;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

import static com.rvh.openoffice.parts.main.enums.NameSpaces.EXTENDED_PROPERTIES;

public class AppPartCreator extends PartsCreator<AppConfig> {

    private final ConfigCollection<ContentTypeConfig> contentTypeConfigs;

    public AppPartCreator(ZipArchiveOutputStream zos, ConfigCollection<AppConfig> configCollection,
                          ConfigCollection<ContentTypeConfig> contentTypeConfigs) {
        super(zos, configCollection);
        this.contentTypeConfigs = contentTypeConfigs;
    }

    @Override
    public void createPart() throws XMLStreamException, IOException {

        createHeader("docProps/app.xml");
        createFooter();

    }

    @Override
    public void createHeader(String name) throws XMLStreamException, IOException {

        //register part in ContentType
        contentTypeConfigs.addConfig(new ContentTypeConfig("Override", "/docProps/app.xml",
                ContentTypes.EXTENDED_PROPERTIES.getPart()));

        zos.putArchiveEntry(new ZipArchiveEntry(name));
        xsw.writeStartDocument("UTF-8", "1.0");

        xsw.writeStartElement("Properties");
        xsw.writeNamespace("xmlns", EXTENDED_PROPERTIES.getSchema());

        writeBasicElement("Application", "Microsoft Excel");
        writeBasicElement("DocSecurity", "0");
        writeBasicElement("ScaleCrop", "false");
        writeBasicElement("Company", "UPS");
        writeBasicElement("LinksUpToDate", "false");
        writeBasicElement("SharedDoc", "false");
        writeBasicElement("HyperlinksChanged", "false");
        writeBasicElement("AppVersion", "15.0300");

    }

    @Override
    public void createFooter() throws XMLStreamException, IOException {

        xsw.writeEndElement();//end properties
        xsw.flush();

        zos.closeArchiveEntry();

    }

}
