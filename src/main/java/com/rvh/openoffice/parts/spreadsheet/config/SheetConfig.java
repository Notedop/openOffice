package com.rvh.openoffice.parts.spreadsheet.config;

import com.rvh.openoffice.parts.main.config.Config;

import javax.sql.DataSource;

public class SheetConfig extends Config {

    private final DataSource dataSource;
    private final String sql;
    private final int maxRows;
    private final TableConfig tableConfig ;

    public SheetConfig(String name, DataSource dataSource, String sql, int maxRows, TableConfig tableConfig, String id) {

        super(name, id, "xl/worksheets/");
        this.dataSource = dataSource;
        this.sql = sql;
        this.maxRows = maxRows;
        this.tableConfig = tableConfig;

    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public String getSql() {
        return sql;
    }

    public int getMaxRows() {
        return maxRows;
    }

    public TableConfig getTableConfig() {
        return tableConfig;
    }

}
