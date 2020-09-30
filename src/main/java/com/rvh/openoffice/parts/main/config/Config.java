package com.rvh.openoffice.parts.main.config;

import com.rvh.openoffice.parts.main.enums.ConfigType;

public abstract class Config {

    private final String name;
    private final ConfigType type;
    private final String id;
    private final String uri;

    public Config(String name, ConfigType type, String id, String uri) {
        this.name = name;
        this.type = type;
        this.id = id;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public ConfigType getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getUri() {
        return uri;
    }
}
