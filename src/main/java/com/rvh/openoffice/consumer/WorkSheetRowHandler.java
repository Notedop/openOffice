package com.rvh.openoffice.consumer;

import com.rvh.openoffice.parts.spreadsheet.SheetPartsCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class WorkSheetRowHandler {

    private final XMLStreamWriter xmlStreamWriter;
    private final SheetPartsCreator sheetPartsCreator;
    private final int maxRows;
    private int processedSheets;
    private int processedRows;

    private final Logger log = LoggerFactory.getLogger(WorkSheetRowHandler.class);

    public WorkSheetRowHandler(XMLStreamWriter xmlStreamWriter, int maxRows, SheetPartsCreator sheetPartsCreator) {
        this.xmlStreamWriter = xmlStreamWriter;
        this.maxRows = maxRows;
        this.sheetPartsCreator = sheetPartsCreator;
    }

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
        //TODO: Check columncount against any possible configuration that has been passed
        log.debug("writing row");
        try {
            //TODO: Read row formatting from configuration and apply here
            //write headers
            switch (processedRows) {
                case 0:
                    writeRow(rs, true);
                    break;
            }

            writeRow(rs, false);

        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    private void writeRow(ResultSet rs, boolean isHeaderRow) throws XMLStreamException, SQLException {

        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        xmlStreamWriter.writeStartElement("row");
        xmlStreamWriter.writeAttribute("r", String.valueOf(processedRows + 1));
        for (int i = 1; i <= columnCount; i++) {
            if (isHeaderRow) {
                writeCell(metaData.getColumnName(i), getColumnPosition(i));
            } else {
                writeCell(rs.getString(i), getColumnPosition(i));
            }
        }

        xmlStreamWriter.writeEndElement();
        xmlStreamWriter.flush();
        processedRows++;
    }

    private void writeCell(String cellValue, String cellPosition) throws XMLStreamException {
        log.debug("writing cell");
        //TODO: Read cell formatting from configuration and apply here
        //TODO: use columntype to set appropiate cell type
        xmlStreamWriter.writeStartElement("c");
        xmlStreamWriter.writeAttribute("r", cellPosition );
        xmlStreamWriter.writeAttribute("t", "inlineStr");
        xmlStreamWriter.writeStartElement("is");
        xmlStreamWriter.writeStartElement("t");
        xmlStreamWriter.writeCharacters(cellValue);
        xmlStreamWriter.writeEndElement();//text
        xmlStreamWriter.writeEndElement();//string inline
        xmlStreamWriter.writeEndElement();//column
    }

    public int getProcessedRows() {
        return processedRows;
    }

    public int getProcessedSheets() {
        return processedSheets;
    }

    public String getColumnPosition(int columnCount) {

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
        columnName =  columnName + (processedRows + 1);
        return columnName;
    }
}

