package com.rvh.openoffice.parts;

import com.rvh.openoffice.parts.config.ConfigCollection;
import com.rvh.openoffice.parts.config.WorkBookConfig;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class WorkBookPartCreator extends PartsCreator<WorkBookConfig> {

    public WorkBookPartCreator(ZipArchiveOutputStream zos, ConfigCollection<WorkBookConfig> config) {
        super(zos, config);
    }

    @Override
    public void createPart() throws XMLStreamException, IOException {
        xsw = xof.createXMLStreamWriter(new OutputStreamWriter(zos));
        createHeader("xl\\workbook.xml");
        createFooter();
    }

    @Override
    public void createHeader(String name) throws XMLStreamException, IOException {
        zos.putArchiveEntry(new ZipArchiveEntry(name));
        xsw.writeStartElement("workbook");
        xsw.writeStartElement("sheets");

        List<WorkBookConfig> configs = configCollection.getConfigs();
        for (int i = 0; i < configs.size(); i++) {
            WorkBookConfig workBookConfig = configs.get(i);
            xsw.writeEmptyElement("sheet");
            xsw.writeAttribute("name", workBookConfig.getName());
            xsw.writeAttribute("sheetId", String.valueOf(i));
            xsw.writeAttribute("r:id", "rId" + i);
        }
        xsw.writeEndElement();//sheets
    }

    @Override
    public void createFooter() throws XMLStreamException, IOException {
        xsw.writeEndElement();//workbook
        xsw.flush();
        zos.closeArchiveEntry();
    }
}
