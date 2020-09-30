package com.rvh.openoffice.parts.spreadsheet.config;

import com.rvh.openoffice.parts.main.config.Config;
import com.rvh.openoffice.parts.main.enums.ConfigType;

public class WorkBookConfig extends Config {

    private final String sheetId;


    public WorkBookConfig(String name, String sheetId, String id) {
        super(name, ConfigType.WORKBOOK, id, "xl/");
        this.sheetId = sheetId;
    }

    public String getSheetId() {
        return sheetId;
    }

}
