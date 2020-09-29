package com.rvh.openoffice.parts.config;

import java.util.ArrayList;
import java.util.List;

public class SheetConfigCollection extends Config {

    List<SheetConfig> sheetConfigs = new ArrayList<>();

    public SheetConfigCollection() {
        super("SheetConfigCollection", ConfigType.SHEETCOLLECTION);
    }

    public List<SheetConfig> getSheetConfigs() {
        return sheetConfigs;
    }

    public void setSheetConfigs(List<SheetConfig> sheetConfigs) {
        this.sheetConfigs = sheetConfigs;
    }

    public void addSheetConfig(SheetConfig config) {
        sheetConfigs.add(config);
    }

    public void removeSheetConfig (String name) {
        sheetConfigs.removeIf(config -> config.getName().equals(name));
    }
}
