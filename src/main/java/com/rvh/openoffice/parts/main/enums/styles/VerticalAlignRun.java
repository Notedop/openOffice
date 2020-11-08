package com.rvh.openoffice.parts.main.enums.styles;

public enum VerticalAlignRun {

    BASELINE("baseline", "Base line"),
    SUPERSCRIPT("superscript", "Super script"),
    SUBSCRIPT("subscript", "Sub script");

    private final String value;
    private final String description;

    VerticalAlignRun(String value, String description) {

        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
