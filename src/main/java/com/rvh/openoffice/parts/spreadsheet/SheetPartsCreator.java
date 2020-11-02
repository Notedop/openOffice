package com.rvh.openoffice.parts.spreadsheet;

import com.rvh.openoffice.consumer.SpringRowCallBackHandler;
import com.rvh.openoffice.parts.main.PartsCreator;
import com.rvh.openoffice.parts.main.config.ConfigCollection;
import com.rvh.openoffice.parts.main.config.ContentTypeConfig;
import com.rvh.openoffice.parts.main.config.RelConfig;
import com.rvh.openoffice.parts.main.enums.NameSpaces;
import com.rvh.openoffice.parts.main.enums.RelationShipTypes;
import com.rvh.openoffice.parts.spreadsheet.config.SheetConfig;
import com.rvh.openoffice.parts.spreadsheet.config.WorkBookConfig;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static com.rvh.openoffice.parts.main.enums.ContentTypes.SPREAD_SHEET;


/**
 * PartsCreater creates the OpenOffice XML parts required to build a OpenOfficeDocument.
 */
public class SheetPartsCreator extends PartsCreator<SheetConfig> {

    ConfigCollection<WorkBookConfig> workBookConfigs;
    private final ConfigCollection<RelConfig> relConfigs;
    private final ConfigCollection<ContentTypeConfig> contentTypeConfigs;

    public SheetPartsCreator(ZipArchiveOutputStream zos, ConfigCollection<SheetConfig> config,
                             ConfigCollection<WorkBookConfig> workBookConfigs,
                             ConfigCollection<RelConfig> relConfigs,
                             ConfigCollection<ContentTypeConfig> contentTypeConfigs) {

        super(zos, config);
        this.workBookConfigs = workBookConfigs;
        this.relConfigs = relConfigs;
        this.contentTypeConfigs = contentTypeConfigs;
    }

    @Override
    public void createPart() throws XMLStreamException, IOException {

        for (SheetConfig config : configCollection.getConfigs()) {
            if (config != null) {

                setOriginalPartName(config.getName());
                xsw = xof.createXMLStreamWriter(new OutputStreamWriter(zos));

                SpringRowCallBackHandler handler = new SpringRowCallBackHandler(xsw, config.getMaxRows(), this);

                createHeader(config.getName());

                //the handler will directly write from result set to the writer
                new JdbcTemplate(config.getDataSource()).query(config.getSql(), handler);

                createFooter();
            } else {
                //log error, throw exception
            }
        }
    }

    @Override
    public void createHeader(String name) throws XMLStreamException, IOException {

        String location = "xl/worksheets/" + name + ".xml";
        zos.putArchiveEntry(new ZipArchiveEntry(location));

        //creating a new sheet; this needs to be referenced in both the workbook.xml.rels and workbook.xml
        //Create relation between sheet and workbook
        String relId = "rId" + (relConfigs.countConfigByName("workbook.xml.rels") + 1);
        String target = "worksheets/" + name + ".xml";
        relConfigs.addConfig(new RelConfig("workbook.xml.rels", relId, RelationShipTypes.WORKSHEET, target, "xl/_rels/"));

        //use relation in workbook config so that it can be used in the workbook part.
        String sheetId = String.valueOf(workBookConfigs.getConfigs().size() + 1);
        workBookConfigs.addConfig(new WorkBookConfig(name, sheetId, relId));

        //register part in ContentType
        contentTypeConfigs.addConfig(new ContentTypeConfig("Override", "/" + location,
                SPREAD_SHEET.getPart()));


        xsw.writeStartDocument();
        xsw.writeStartElement("worksheet");
        xsw.writeDefaultNamespace(NameSpaces.SPREAD_SHEET.getSchema());
        xsw.writeNamespace("r", NameSpaces.RELATIONSHIPS.getSchema());
        xsw.writeNamespace("mc", NameSpaces.MARKUP_COMPATIBILITY.getSchema());
        xsw.writeAttribute("mc:Ignorable", "x14ac");
        xsw.writeNamespace("x14ac", NameSpaces.MS_SPREAD_SHEET.getSchema());
        xsw.writeEmptyElement("dimension");
        xsw.writeAttribute("ref", "A1");
        xsw.writeStartElement("sheetViews");
        xsw.writeStartElement("sheetView");
        xsw.writeAttribute("tabSelected", "1");
        xsw.writeAttribute("workbookViewId", "0");
        xsw.writeEmptyElement("selection");
        xsw.writeAttribute("activeCell", "A1");
        xsw.writeAttribute("sqref", "A1");
        xsw.writeEndElement();
        xsw.writeEndElement();
        xsw.writeEmptyElement("sheetFormatPr");
        xsw.writeAttribute("defaultRowHeight", "15");
        xsw.writeAttribute("x14ac:dyDescent", "0.25");
        xsw.writeStartElement("sheetData");

    }

    @Override
    public void createFooter() throws XMLStreamException, IOException {
        xsw.writeEndElement();//end worksheet
        xsw.writeEmptyElement("pageMargins");
        xsw.writeAttribute("left", "0.7");
        xsw.writeAttribute("right", "0.7");
        xsw.writeAttribute("top", "0.75");
        xsw.writeAttribute("bottom", "0.75");
        xsw.writeAttribute("header", "0.3");
        xsw.writeAttribute("footer", "0.3");
        xsw.writeEndElement();//end workbook
        xsw.flush();

        zos.closeArchiveEntry();

    }
}
