package com.rvh.openoffice.parts.spreadsheet.config;

import com.rvh.openoffice.parts.main.config.Config;
import com.rvh.openoffice.parts.main.enums.ConfigType;

public class TableConfig extends Config {

    public TableConfig(String partName, String id) {
        super(partName, ConfigType.TABLE, id, "");
    }

}
