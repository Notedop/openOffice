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

        zos.putArchiveEntry(new ZipArchiveEntry(name));

        xsw.writeStartDocument();
        xsw.writeStartElement("workbook");
        xsw.writeNamespace("xmlns", "http://schemas.openxmlformats.org/spreadsheetml/2006/main");
        xsw.writeNamespace("r", "http://schemas.openxmlformats.org/officeDocument/2006/relationships");
        xsw.writeNamespace("mc", "http://schemas.openxmlformats.org/markup-compatibility/2006");
        xsw.writeAttribute("mc:Ignorable", "x15");
        xsw.writeNamespace("x15", "http://schemas.microsoft.com/office/spreadsheetml/2010/11/main");
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
