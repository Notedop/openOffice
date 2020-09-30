package com.rvh.openoffice.parts.main.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

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

    public long countConfigByName(String name) {
        Predicate<t> byName = config -> config.getName().equals(name);
        return configs.stream().filter(byName).count();
    }

    public t getConfigByNameAndId(String name, String relId){
        Predicate<t> byNameAndId = config -> config.getName().equals(name) && config.getId().equals(relId);
        Optional<t> item = configs.stream().findFirst().filter(byNameAndId);
        return item.orElse(null);
    }
}
