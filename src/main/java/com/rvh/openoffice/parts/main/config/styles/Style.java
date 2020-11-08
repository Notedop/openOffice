package com.rvh.openoffice.parts.main.config.styles;

import com.rvh.openoffice.parts.main.config.Config;

import java.util.Objects;

/**
 * defines the cellXfs section of a style sheet.
 * if availableToUser is set to true
 */
public class Style extends Config {

    private String numberFormatId;
    private String fontId;
    private String fillId;
    private String cellBordersId;
    private boolean availableToUser = false;

    public Style(String name, String id) {
        super(name, id, "xl/styles.xml");
    }

    public String getNumberFormatId() {
        return numberFormatId;
    }

    public void setNumberFormatId(String numberFormatId) {
        this.numberFormatId = numberFormatId;
    }

    public String getFontId() {
        return fontId;
    }

    public void setFontId(String fontId) {
        this.fontId = fontId;
    }

    public String getFillId() {
        return fillId;
    }

    public void setFillId(String fillId) {
        this.fillId = fillId;
    }

    public String getCellBordersId() {
        return cellBordersId;
    }

    public void setCellBordersId(String cellBordersId) {
        this.cellBordersId = cellBordersId;
    }

    /**
     * Provides information if this style is available to the user in
     * the application opening the document.
     *
     * @return
     */
    public boolean isAvailableToUser() {
        return availableToUser;
    }

    /**
     * define if the style is available to the user in the application
     * opening the document. If set to true this will result cellStyles
     * element added to the styles.xml
     *
     * @param availableToUser
     */
    public void setAvailableToUser(boolean availableToUser) {
        this.availableToUser = availableToUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Style)) return false;
        Style style = (Style) o;
        return Objects.equals(numberFormatId, style.numberFormatId) &&
                Objects.equals(fontId, style.fontId) &&
                Objects.equals(fillId, style.fillId) &&
                Objects.equals(cellBordersId, style.cellBordersId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberFormatId, fontId, fillId, cellBordersId);
    }
}



