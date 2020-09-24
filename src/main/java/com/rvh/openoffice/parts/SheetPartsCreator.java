package com.rvh.openoffice.parts;

import com.rvh.openoffice.consumer.WorkSheetRowHandler;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.OutputStreamWriter;


/**
 * PartsCreater creates the OpenOffice XML parts required to build a OpenOfficeDocument.
 */
public class SheetPartsCreator extends PartsCreator {


    public SheetPartsCreator(DataSource dataSource, ZipArchiveOutputStream zos) {
        super(dataSource,zos);
    }

    @Override
    public void createPart(String name, String sql) throws XMLStreamException, IOException {

        originalSheetName = name;

        XMLOutputFactory xof = XMLOutputFactory.newFactory();
        xsw = xof.createXMLStreamWriter(new OutputStreamWriter(zos));

        WorkSheetRowHandler handler = new WorkSheetRowHandler(xsw, 1, this);
        createHeader(name);

        //the handler will directly write from result set to the writer
        new JdbcTemplate(dataSource).query(sql, handler);

        createFooter();

    }

    @Override
    public void createHeader(String name) throws XMLStreamException, IOException {

        //TODO: everytime we create a sheet, we need make sure the sheet is registered in the workbook
        zos.putArchiveEntry(new ZipArchiveEntry("xl\\worksheets\\" + name));

        xsw.writeStartDocument();
        xsw.writeStartElement("Workbook");
        xsw.writeNamespace("xmlns", "urn:schemas-microsoft-com:office:spreadsheet");
        xsw.writeNamespace("xmlns:o", "urn:schemas-microsoft-com:office:office");
        xsw.writeStartElement("Worksheet");

    }

    @Override
    public void createFooter() throws XMLStreamException, IOException {
        xsw.writeEndElement();//end worksheet
        xsw.writeEndElement();//end workbook

        zos.closeArchiveEntry();

    }

    public String getOriginalSheetName() {
        return originalSheetName;
    }
}
