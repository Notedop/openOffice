package com.rvh.openoffice.consumer;

import com.rvh.openoffice.parts.SheetPartsCreator;
import org.springframework.jdbc.core.RowCallbackHandler;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorkSheetRowHandler implements RowCallbackHandler {

    private final XMLStreamWriter xmlStreamWriter;
    private final SheetPartsCreator sheetPartsCreator;
    private final int maxRows;
    private int processedSheets;
    private int processedRows;

    private final Logger log = Logger.getLogger(this.getClass().getName());

    public WorkSheetRowHandler(XMLStreamWriter xmlStreamWriter, int maxRows, SheetPartsCreator sheetPartsCreator) {
        this.xmlStreamWriter = xmlStreamWriter;
        this.maxRows = maxRows;
        this.sheetPartsCreator = sheetPartsCreator;
    }

    @Override
    public void processRow(ResultSet rs) throws SQLException {

        if (processedRows < maxRows) {
            processSingleRow(rs);
        } else {
            try {
                processedRows = 0;
                processedSheets++;
                //close current sheet and create new one
                sheetPartsCreator.createFooter();
                sheetPartsCreator.createHeader(sheetPartsCreator.getOriginalSheetName() + processedSheets);
                processSingleRow(rs);
            } catch (XMLStreamException | IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void processSingleRow(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        log.log(Level.FINE,"writng row");
        try {
            xmlStreamWriter.writeStartElement("row");
            for (int i = 1; i <= columnCount; i++) {
                log.log(Level.FINE, "writng cell");
                xmlStreamWriter.writeStartElement("cell");
                //TODO: use columntype to set appropiate cell type
                xmlStreamWriter.writeCharacters(rs.getString(i));
                xmlStreamWriter.writeEndElement();
            }
            xmlStreamWriter.writeEndElement();
            xmlStreamWriter.flush();
            processedRows++;
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    public int getProcessedRows() {
        return processedRows;
    }

    public int getProcessedSheets() {
        return processedSheets;
    }
}

