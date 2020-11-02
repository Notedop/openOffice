package com.rvh.openoffice.parts.main.config;

public class ContentTypeConfig extends Config {

    private final String partName;
    private final String contentType;

    public ContentTypeConfig(String name, String partName, String contentType) {
        super(name, null, null);
        this.partName = partName;
        this.contentType = contentType;
    }

    public String getPartName() {
        return partName;
    }

    public String getContentType() {
        return contentType;
    }
}
