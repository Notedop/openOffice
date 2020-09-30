package com.rvh.openoffice.parts.main.enums;

public enum RelationTypes {

    STYLES("http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles"),
    THEME("http://schemas.openxmlformats.org/officeDocument/2006/relationships/theme"),
    WORKSHEET("http://schemas.openxmlformats.org/officeDocument/2006/relationships/worksheet");

    private final String schema;

    RelationTypes(String schema) {
        this.schema = schema;
    }

    public String getSchema() {
        return schema;
    }
}
