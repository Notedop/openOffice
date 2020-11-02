package com.rvh.openoffice.parts.spreadsheet.config;

import com.rvh.openoffice.parts.main.config.Config;

public class WorkBookConfig extends Config {

    private final String sheetId;


    public WorkBookConfig(String name, String sheetId, String id) {
        super(name, id, "xl/");
        this.sheetId = sheetId;
    }

    public String getSheetId() {
        return sheetId;
    }

}
