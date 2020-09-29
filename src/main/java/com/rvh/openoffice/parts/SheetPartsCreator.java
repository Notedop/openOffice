package com.rvh.openoffice.parts;

import com.rvh.openoffice.consumer.WorkSheetRowHandler;
import com.rvh.openoffice.parts.config.ConfigCollection;
import com.rvh.openoffice.parts.config.ConfigType;
import com.rvh.openoffice.parts.config.SheetConfig;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.OutputStreamWriter;


/**
 * PartsCreater creates the OpenOffice XML parts required to build a OpenOfficeDocument.
 */
public class SheetPartsCreator extends PartsCreator<SheetConfig> {

    public SheetPartsCreator(ZipArchiveOutputStream zos, ConfigCollection<SheetConfig> config) {
        super(zos, config);
    }

    @Override
    public void createPart() throws XMLStreamException, IOException {

        for (SheetConfig config : configCollection.getConfigs()) {
            if (config != null) {
                if (config.getType().equals(ConfigType.SHEET)) {
                    SheetConfig sheetConfig = (SheetConfig) config;

                    setOriginalPartName(config.getName());
                    xsw = xof.createXMLStreamWriter(new OutputStreamWriter(zos));

                    WorkSheetRowHandler handler = new WorkSheetRowHandler(xsw, sheetConfig.getMaxRows(), this);

                    createHeader(sheetConfig.getName());

                    //the handler will directly write from result set to the writer
                    new JdbcTemplate(sheetConfig.getDataSource()).query(sheetConfig.getSql(), handler);

                    createFooter();
                } else {
                    //log error, throw exception
                }
            } else {
                //log error, throw exception
            }
        }


    }

    @Override
    public void createHeader(String name) throws XMLStreamException, IOException {

        //TODO: everytime we create a sheet, we need make sure the sheet is registered in the workbook
        zos.putArchiveEntry(new ZipArchiveEntry("xl\\worksheets\\" + name + ".xml"));

        xsw.writeStartDocument();
        xsw.writeStartElement("worksheet");
        xsw.writeNamespace("xmlns", "http://schemas.openxmlformats.org/spreadsheetml/2006/main");
        xsw.writeNamespace("r", "http://schemas.openxmlformats.org/officeDocument/2006/relationships");
        xsw.writeNamespace("mc", "http://schemas.openxmlformats.org/markup-compatibility/2006");
        xsw.writeAttribute("mc:Ignorable", "x14ac");
        xsw.writeNamespace("x14ac", "http://schemas.microsoft.com/office/spreadsheetml/2009/9/ac");
        xsw.writeEmptyElement("dimension");
        xsw.writeAttribute("ref", "A1");
        xsw.writeStartElement("sheetViews");
        xsw.writeStartElement("sheetView");
        xsw.writeAttribute("tabSelected", "1");
        xsw.writeAttribute("workbookViewId", "0");
        xsw.writeEmptyElement("selection");
        xsw.writeAttribute("activeCell", "H11");
        xsw.writeAttribute("sqref", "H11");
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
        //TODO: Throw SheetCreatedEvent
        zos.closeArchiveEntry();

    }
}
