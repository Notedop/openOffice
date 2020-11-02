package com.rvh.openoffice.parts.main.enums;

public enum RelationShipTypes {

    STYLES("http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles"),
    THEME("http://schemas.openxmlformats.org/officeDocument/2006/relationships/theme"),
    WORKSHEET("http://schemas.openxmlformats.org/officeDocument/2006/relationships/worksheet"),
    EXTENDED( "http://schemas.openxmlformats.org/officeDocument/2006/relationships/extended-properties"),
    CORE("http://schemas.openxmlformats.org/package/2006/relationships/metadata/core-properties"),
    OFFICE_DOC("http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument"),
    RELATION_SHIPS("http://schemas.openxmlformats.org/officeDocument/2006/relationships");

    private final String type;

    RelationShipTypes(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
