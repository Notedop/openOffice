package com.rvh.openoffice.parts.main.config;

import static com.rvh.openoffice.parts.main.enums.ConfigType.CONTENT_TYPE;

public class ContentTypeConfig extends Config {

    private final String partName;
    private final String contentType;

    public ContentTypeConfig(String name, String partName, String ContentType) {
        super(name, CONTENT_TYPE, null, null);
        this.partName = partName;
        contentType = ContentType;
    }

    public String getPartName() {
        return partName;
    }

    public String getContentType() {
        return contentType;
    }
}
