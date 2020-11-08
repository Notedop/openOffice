package com.rvh.openoffice.parts.main.config;

import com.rvh.openoffice.parts.main.config.styles.*;

public class CellConfig extends Config {

    private CellBorders cellBorders;
    private Fill fill;
    private Font font;
    private NumberFormat numberFormat;

    private int columnIndex;
    private boolean isHeader;
    private String xfId;

    public CellConfig(String name, boolean isHeader) {
        super(name, null, null);
        this.isHeader = isHeader;
    }

    public CellConfig(String name, boolean isHeader, int columnIndex) {
        super(name, null, null);
        this.isHeader = isHeader;
        this.columnIndex = columnIndex;
    }

    public CellBorders getCellBorders() {
        return cellBorders;
    }

    public void setCellBorders(CellBorders cellBorders) {
        this.cellBorders = cellBorders;
    }

    public Fill getFill() {
        return fill;
    }

    public void setFill(Fill fill) {
        this.fill = fill;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public NumberFormat getNumberFormat() {
        return numberFormat;
    }

    public void setNumberFormat(NumberFormat numberFormat) {
        this.numberFormat = numberFormat;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public void setXfId(String xfId) {
        this.xfId = xfId;
    }

    public String getXfId() {
        return xfId;
    }
}
