package com.rvh.openoffice.parts.spreadsheet;

import com.rvh.openoffice.parts.main.PartsCreator;
import com.rvh.openoffice.parts.main.config.ConfigCollection;
import com.rvh.openoffice.parts.main.config.ContentTypeConfig;
import com.rvh.openoffice.parts.spreadsheet.config.WorkBookConfig;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;

import static com.rvh.openoffice.parts.main.enums.ContentTypes.WORKBOOK;
import static com.rvh.openoffice.parts.main.enums.NameSpaces.*;
import static com.rvh.openoffice.parts.main.enums.RelationShipTypes.RELATION_SHIPS;

public class WorkBookPartCreator extends PartsCreator<WorkBookConfig> {

    private final ConfigCollection<ContentTypeConfig> contentTypeConfigs;

    public WorkBookPartCreator(ZipArchiveOutputStream zos, ConfigCollection<WorkBookConfig> config, ConfigCollection<ContentTypeConfig> contentTypeConfigs) {
        super(zos, config);
        this.contentTypeConfigs = contentTypeConfigs;
    }

    @Override
    public void createPart() throws XMLStreamException, IOException {

        createHeader("xl/workbook.xml");
        createFooter();
    }

    @Override
    public void createHeader(String name) throws XMLStreamException, IOException {

        //register part in ContentType
        contentTypeConfigs.addConfig(new ContentTypeConfig("Override", "/xl/workbook.xml",
                WORKBOOK.getPart()));

        zos.putArchiveEntry(new ZipArchiveEntry(name));

        xsw.writeStartDocument();
        xsw.writeStartElement("workbook");
        xsw.writeNamespace("xmlns", SPREAD_SHEET.getSchema());
        xsw.writeNamespace("r", RELATION_SHIPS.getType());
        xsw.writeNamespace("mc", MARKUP_COMPATIBILITY.getSchema());
        xsw.writeAttribute("mc:Ignorable", "x15");
        xsw.writeNamespace("x15", MS_WORKBOOK.getSchema());
        xsw.writeStartElement("sheets");

        List<WorkBookConfig> configs = configCollection.getConfigs();
        configs.forEach(workBookConfig -> {
            try {
                xsw.writeEmptyElement("sheet");
                xsw.writeAttribute("name", workBookConfig.getName());
                xsw.writeAttribute("sheetId", workBookConfig.getSheetId());
                xsw.writeAttribute("r:id", workBookConfig.getId());
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
        });

        xsw.writeEndElement();//sheets
    }

    @Override
    public void createFooter() throws XMLStreamException, IOException {
        xsw.writeEndElement();//workbook
        xsw.flush();
        zos.closeArchiveEntry();
    }
}
