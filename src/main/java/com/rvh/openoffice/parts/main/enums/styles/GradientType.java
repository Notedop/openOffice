package com.rvh.openoffice.parts.main.enums.styles;

public enum GradientType {

    LINEAR("linear","Linear Gradient"),
    PATH("path","Path");


    private final String value;
    private final String description;

    GradientType(String value, String description) {
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
