package com.rvh.openoffice.consumer;

import com.rvh.openoffice.parts.spreadsheet.SheetPartsCreator;
import org.springframework.jdbc.core.RowCallbackHandler;

import javax.xml.stream.XMLStreamWriter;

public class SpringRowCallBackHandler extends WorkSheetRowHandler implements RowCallbackHandler {
    public SpringRowCallBackHandler(XMLStreamWriter xmlStreamWriter, int maxRows, SheetPartsCreator sheetPartsCreator) {
        super(xmlStreamWriter, maxRows, sheetPartsCreator);
    }
}
