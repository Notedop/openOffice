package com.rvh.openoffice.parts.main.config;

public abstract class Config {

    private final String name;
    private final String id;
    private final String uri;

    public Config(String name, String id, String uri) {
        this.name = name;
        this.id = id;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getUri() {
        return uri;
    }
}
