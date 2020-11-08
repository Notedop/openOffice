package com.rvh.openoffice.parts.main.enums.styles;

public enum BorderStyle {

    NONE( "none", 	"None"),
    THIN( "thin","Thin Border"),
    MEDIUM( "medium","Medium Border"),
    DASHED("dashed", "Dashed"),
    DOTTED("dotted", "Dotted"),
    THICK ("thick", "Thick Line Border"),
    DOUBLE ("double","Double Line"),
    HAIR ("hair", "Hairline Border"),
    MEDIUM_DASHED("mediumDashed","Medium Dashed"),
    DASH_DOT( "dashDot", "Dash Dot"),
    MEDIUM_DASH_DOT ("mediumDashDot","Medium Dash Dot"),
    DASH_DOT_DOT ("dashDotDot",	"Dash Dot Dot"),
    MEDIUM_DASH_DOT_DOT ("mediumDashDotDot", "Medium Dash Dot Dot"),
    SLANT_DASH_DOT ("slantDashDot", "Slant Dash Dot");

    private final String value;
    private final String description;

    BorderStyle(String value, String description) {
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
