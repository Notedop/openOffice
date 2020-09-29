package com.rvh.openoffice.test;

import com.rvh.openoffice.PackageCreator;
import com.rvh.openoffice.parts.config.ConfigCollection;
import com.rvh.openoffice.parts.config.SheetConfig;
import com.rvh.openoffice.parts.config.TableConfig;
import com.rvh.openoffice.test.basetestcase.h2DatabaseTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
            sb.append(String.valueOf(i));
            sb.append(" VARCHAR(10) NOT NULL");
            if (i != columns) {
                sb.append(",");
            } else {
                sb.append("");
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
                sb.append("\'");
                sb.append(String.valueOf(j));
                sb.append(String.valueOf('-'));
                sb.append(String.valueOf(i));
                if (i!=columns) {
                    sb.append("\',");
                } else {
                    sb.append("\'");
                }


            }
            if (j!=numberOfRows) {
                sb.append("),");
            } else {
                sb.append(")");
            }

        }

        System.out.println(st.executeUpdate(sb.toString()));

    }

    @Test
    void testConnection() throws IOException, XMLStreamException {

        File outputFile = new File("test.xlsx");
        OutputStream fos = new FileOutputStream(outputFile);
        File template = new File(this.getClass().getClassLoader().getResource("template.xlsx").getFile());

        ConfigCollection<SheetConfig> configs = new ConfigCollection<>();
        TableConfig tableConfig = new TableConfig("main");
        configs.addConfig(new SheetConfig("sheet1", getDataSource(),"select * from excel_test_data", 1000, tableConfig));
        configs.addConfig(new SheetConfig("sheet2", getDataSource(),"select * from excel_test_data", 50, tableConfig));

        PackageCreator creator = new PackageCreator(configs);

        creator.generate(template, "sheet1", fos);
        fos.close();

    }

}
