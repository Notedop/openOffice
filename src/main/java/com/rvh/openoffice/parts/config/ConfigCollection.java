package com.rvh.openoffice.parts.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigCollection<t extends Config> {

    List<t> configs = new ArrayList<>();


    public List<t> getConfigs() {
        return configs;
    }

    public void setConfigs(List<t> configs) {
        this.configs = configs;
    }

    public void addConfig(t config) {
        configs.add(config);
    }

    public void removeConfig (String name) {
        configs.removeIf(config -> config.getName().equals(name));
    }
}
