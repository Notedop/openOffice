package com.rvh.openoffice.parts.main.enums;

/**
 * Content types define the type of part
 */
public enum ContentTypes {

    XML("application/xml"),
    CORE_PROPERTIES("application/vnd.openxmlformats-package.core-properties+xml"),
    DIGITAL_SIGNATURE_CERTIFICATE("application/vnd.openxmlformats-package.digital-signature-certificate"),
    DIGITAL_SIGNATURE_ORIGIN("application/vnd.openxmlformats-package.digital-signature-origin"),
    DIGITAL_SIGNATURE_XML_SIGNATURE("application/vnd.openxmlformats-package.digital-signature-xmlsignature+xml"),
    RELATIONSHIPS("application/vnd.openxmlformats-package.relationships+xml"),
    EXTENDED_PROPERTIES("application/vnd.openxmlformats-officedocument.extended-properties+xml"),
    SPREAD_SHEET("application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml"),
    WORKBOOK("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml");

    private final String part;

    ContentTypes(String part) {

        this.part = part;
    }

    public String getPart() {
        return part;
    }
}
