package com.rvh.openoffice.parts.config;

public abstract class Config {

    private String name;
    private ConfigType type;

    public Config(String name, ConfigType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public ConfigType getType() {
        return type;
    }
}
