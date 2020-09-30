package com.rvh.openoffice.parts.config;

import com.rvh.openoffice.parts.enums.ConfigType;

public class WorkBookConfig extends Config {

    private final String sheetId;


    public WorkBookConfig(String name, String sheetId, String id) {
        super(name, ConfigType.WORKBOOK, id, "xl\\");
        this.sheetId = sheetId;
    }

    public String getSheetId() {
        return sheetId;
    }

}
