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
                sheetPartsCreator.createHeader(sheetPartsCreator.getOriginalPartName() + processedSheets);
                processSingleRow(rs);
            } catch (XMLStreamException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void processSingleRow(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        //TODO: Check columncount against any possible configuration that has been passed
        log.log(Level.FINE, "writng row");
        try {
            //TODO: Read row formatting from configuration and apply here
            xmlStreamWriter.writeStartElement("row");
            xmlStreamWriter.writeAttribute("r", String.valueOf(processedRows + 1));
            for (int i = 1; i <= columnCount; i++) {
                log.log(Level.FINE, "writng cell");
                //TODO: Read cell formatting from configuration and apply here
                //TODO: use columntype to set appropiate cell type
                xmlStreamWriter.writeStartElement("c");
                xmlStreamWriter.writeAttribute("r", getColumnName(i) );
                xmlStreamWriter.writeAttribute("t", "inlineStr");
                xmlStreamWriter.writeStartElement("is");
                xmlStreamWriter.writeStartElement("t");
                xmlStreamWriter.writeCharacters(rs.getString(i));
                xmlStreamWriter.writeEndElement();//text
                xmlStreamWriter.writeEndElement();//string inline
                xmlStreamWriter.writeEndElement();//column
                //TODO: Throw CellCreatedEvent
            }
            xmlStreamWriter.writeEndElement();
            //TODO: Throw RowCreatedEvent
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

    public String getColumnName(int columnCount) {

        String columnName = "";
        int aToZ = 26;

        columnCount--;

        //Capital A starts at 65 in ASCI table
        if (columnCount <= aToZ) {
            columnName = Character.toString(columnCount + 65);
        } else {
            while (columnCount >= 0) {
                columnName = columnName.concat(Character.toString((columnCount % aToZ)+ 65) );
                columnCount -= aToZ;
            }
        }

        //column cell name exists out of column name + rownumber. For example A1 or AB3
        columnName =  columnName + String.valueOf(processedRows+1);
        return columnName;
    }
}

