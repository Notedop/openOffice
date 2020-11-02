package com.rvh.openoffice.test;

import com.rvh.openoffice.PackageCreator;
import com.rvh.openoffice.parts.main.config.ConfigCollection;
import com.rvh.openoffice.parts.main.config.CoreConfig;
import com.rvh.openoffice.parts.spreadsheet.config.SheetConfig;
import com.rvh.openoffice.parts.spreadsheet.config.TableConfig;
import com.rvh.openoffice.test.basetestcase.h2DatabaseTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

class testXml extends h2DatabaseTests {

    Logger log = LoggerFactory.getLogger(h2DatabaseTests.class);

    @BeforeEach
    void prepareData() throws SQLException {
        int columns = 26;
        int numberOfRows = 100;

        DataSource ds = getDataSource();
        Connection conn = ds.getConnection();
        Statement st = conn.createStatement();

        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE excel_test_data (");

        for (int i = 1; i <= columns; i++) {
            sb.append("ID_");
            sb.append(i);
            sb.append(" VARCHAR(10) NOT NULL");
            if (i != columns) {
                sb.append(",");
            }
        }
        sb.append(");");

        st.executeUpdate(sb.toString());
        conn.commit();

        sb = new StringBuilder();
        sb.append("insert into excel_test_data ");
        sb.append("values ");
        for (int j = 1; j <= numberOfRows; j++) {
            sb.append("(");
            for (int i = 1; i <= columns; i++) {
                sb.append("'");
                sb.append(j);
                sb.append('-');
                sb.append(i);
                if (i!=columns) {
                    sb.append("',");
                } else {
                    sb.append("'");
                }


            }
            if (j!=numberOfRows) {
                sb.append("),");
            } else {
                sb.append(")");
            }

        }

        log.debug("Records inserted: {}", st.executeUpdate(sb.toString()));

    }

    @Test
    void testConnection() throws IOException, XMLStreamException {

        File outputFile = new File("test.xlsx");
        OutputStream fos = new FileOutputStream(outputFile);

        ConfigCollection<SheetConfig> sheetConfigs = new ConfigCollection<>();

        CoreConfig coreConfig = new CoreConfig();
        coreConfig.setCreator("Raoul van Hal");
        coreConfig.setVersion("2.0");

        TableConfig tableConfig = new TableConfig("main", "1");
        sheetConfigs.addConfig(new SheetConfig("sheet1", getDataSource(),"select * from excel_test_data", 1000, tableConfig, "1"));
        sheetConfigs.addConfig(new SheetConfig("sheet2", getDataSource(),"select * from excel_test_data", 50, tableConfig, "1"));
        PackageCreator creator = new PackageCreator(coreConfig, sheetConfigs);

        creator.generate(fos);
        fos.close();
    }
}
