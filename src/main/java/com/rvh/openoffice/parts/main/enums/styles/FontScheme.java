package com.rvh.openoffice.parts.main.enums.styles;

public enum FontScheme {

    NONE("none", "None"),
    MAJOR("major", "Major font"),
    MINOR("minor", "Minor font");


    private final String value;
    private final String description;

    FontScheme(String value, String description) {

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
