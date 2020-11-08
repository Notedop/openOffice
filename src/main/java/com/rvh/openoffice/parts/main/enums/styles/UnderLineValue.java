package com.rvh.openoffice.parts.main.enums.styles;

public enum UnderLineValue {
    SINGLE("single", "Single Underline"),
    DOUBLE("double", "Double Underline"),
    SINGLE_ACCOUNTING("singleAccounting", "Accounting Single Underline"),
    DOUBLE_ACCOUNTING("doubleAccounting", "Accounting Double Underline"),
    NONE("none", "None");


    private final String value;
    private final String description;

    UnderLineValue(String value, String description) {

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
