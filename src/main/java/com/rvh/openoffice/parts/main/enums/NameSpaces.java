package com.rvh.openoffice.parts.main.enums;

public enum NameSpaces {

    CONTENT_TYPES("http://schemas.openxmlformats.org/package/2006/content-types"),
    CORE_PROPERTIES("http://schemas.openxmlformats.org/package/2006/metadata/core-properties"),
    EXTENDED_PROPERTIES("http://schemas.openxmlformats.org/officeDocument/2006/extended-properties"),
    DIGITAL_SIGNATURES("http://schemas.openxmlformats.org/package/2006/digital-signature"),
    RELATIONSHIPS("http://schemas.openxmlformats.org/package/2006/relationships"),
    MARKUP_COMPATIBILITY("http://schemas.openxmlformats.org/markup-compatibility/2006"),
    DUBLIN_CORE_ELEMENTS("http://purl.org/dc/elements/1.1/"),
    DUBLIN_CORE_TERMS("http://purl.org/dc/terms/"),
    DUBLIC_CORE_DCMITYPE("http://purl.org/dc/dcmitype/"),
    XSI("http://www.w3.org/2001/XMLSchema-instance"),
    SPREAD_SHEET("http://schemas.openxmlformats.org/spreadsheetml/2006/main"),
    MS_SPREAD_SHEET("http://schemas.microsoft.com/office/spreadsheetml/2009/9/ac"),
    MS_WORKBOOK("http://schemas.microsoft.com/office/spreadsheetml/2010/11/main");

    private final String schema;

    NameSpaces(String schema) {
        this.schema = schema;
    }

    public String getSchema() {
        return schema;
    }
}
