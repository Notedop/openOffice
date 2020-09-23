package com.rvh.openoffice;

import com.rvh.openoffice.consumer.WorkSheetRowHandler;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;


/**
 * PartsCreater creates the OpenOffice XML parts required to build a OpenOfficeDocument.
 */
public class PartsCreator {

    private final DataSource dataSource;
    private XMLStreamWriter xsw;
    private final ZipArchiveOutputStream zos;
    private String originalSheetName;

    public PartsCreator(DataSource dataSource, ZipArchiveOutputStream zos) {
        this.dataSource = dataSource;
        this.zos = zos;
    }

    public void createSheetPart(String sheetName, String sql) throws XMLStreamException, IOException {

        this.originalSheetName = sheetName;

        XMLOutputFactory xof = XMLOutputFactory.newFactory();
        xsw = xof.createXMLStreamWriter(new OutputStreamWriter(zos));

        WorkSheetRowHandler handler = new WorkSheetRowHandler(xsw, 1, this);
        createSheetHeader(sheetName);

        //the handler will directly write from result set to the writer
        new JdbcTemplate(dataSource).query(sql, handler);

        createSheetFooter();

    }

    public void createSheetHeader(String name) throws XMLStreamException, IOException {

        //TODO: everytime we create a sheet, we need make sure the sheet is registered in the workbook
        zos.putArchiveEntry(new ZipArchiveEntry("xl\\worksheets\\" + name));

        xsw.writeStartDocument();
        xsw.writeStartElement("Workbook");
        xsw.writeNamespace("xmlns", "urn:schemas-microsoft-com:office:spreadsheet");
        xsw.writeNamespace("xmlns:o", "urn:schemas-microsoft-com:office:office");
        xsw.writeStartElement("Worksheet");

    }

    public void createSheetFooter() throws XMLStreamException, IOException {
        xsw.writeEndElement();//end worksheet
        xsw.writeEndElement();//end workbook

        zos.closeArchiveEntry();

    }

    public String getOriginalSheetName() {
        return originalSheetName;
    }
}
