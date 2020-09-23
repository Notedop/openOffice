package com.rvh.openoffice.consumer;

import com.rvh.openoffice.PartsCreator;
import org.springframework.jdbc.core.RowCallbackHandler;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class WorkSheetRowHandler implements RowCallbackHandler {

    private final XMLStreamWriter xmlStreamWriter;
    private final PartsCreator partsCreator;
    private final int maxRows;
    private int processedSheets;
    private int processedRows;

    public WorkSheetRowHandler(XMLStreamWriter xmlStreamWriter, int maxRows, PartsCreator partsCreator) {
        this.xmlStreamWriter = xmlStreamWriter;
        this.maxRows = maxRows;
        this.partsCreator = partsCreator;
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
                partsCreator.createSheetFooter();
                partsCreator.createSheetHeader(partsCreator.getOriginalSheetName() + processedSheets);
                processSingleRow(rs);
            } catch (XMLStreamException | IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void processSingleRow(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        System.out.println("writng row");
        try {
            xmlStreamWriter.writeStartElement("row");
            for (int i = 1; i <= columnCount; i++) {
                System.out.println("writng cell");
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

