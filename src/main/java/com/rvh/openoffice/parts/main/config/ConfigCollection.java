package com.rvh.openoffice.parts.main.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class ConfigCollection<T extends Config> {

    List<T> configs = new ArrayList<>();


    public List<T> getConfigs() {
        return configs;
    }

    public void setConfigs(List<T> configs) {
        this.configs = configs;
    }

    public void addConfig(T config) {
        configs.add(config);
    }

    public void removeConfig (String name) {
        configs.removeIf(config -> config.getName().equals(name));
    }

    public long countConfigByName(String name) {
        Predicate<T> byName = config -> config.getName().equals(name);
        return configs.stream().filter(byName).count();
    }

    public T getConfigByNameAndId(String name, String relId){
        Predicate<T> byNameAndId = config -> config.getName().equals(name) && config.getId().equals(relId);
        Optional<T> item = configs.stream().findFirst().filter(byNameAndId);
        return item.orElse(null);
    }
}
