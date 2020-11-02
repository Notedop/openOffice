package com.rvh.openoffice.test.basetestcase;

import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;
import org.h2.tools.Server;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;


import static org.junit.jupiter.api.Assertions.fail;

public class h2DatabaseTests {

    private static Connection conn;
    private static JdbcDataSource dataSource;
    private static Server server = null;
    private static Server webServer = null;

    private static final Logger log = LoggerFactory.getLogger(h2DatabaseTests.class);

    @BeforeAll
    static void initializeDatabase() throws Exception {

        String scriptLoc = null;

        Properties dbProperties = new Properties();
        try (InputStream inputStream = h2DatabaseTests.class.getResourceAsStream("/database.properties")) {
            dbProperties.load(inputStream);
            checkProperties(dbProperties);
        } catch (IOException | NullPointerException ex) {
            log.error("Unable to load database.properties!");
            throw new Exception(ex);
        }

        String startUpScript = dbProperties.getProperty("database.startUpScript");
        try {
            if (startUpScript != null && !startUpScript.equals(""))
                scriptLoc = h2DatabaseTests.class.getResource(startUpScript).getFile();
            else
                log.info("No startup script defined");
        } catch (NullPointerException ex) {
            log.error("Unable to load specified startup script: {}", startUpScript);
        }

        try {
            //create local server
            server = Server.createTcpServer(dbProperties.getProperty("database.loadTcpServerParameters")).start();
            //create web server
            String loadWebServer = dbProperties.getProperty("database.loadWebServer");
            if (loadWebServer!=null && loadWebServer.equalsIgnoreCase("true"))
               webServer = Server.createWebServer().start();

            //check if driver is loaded
            Class.forName(dbProperties.getProperty("database.driver"));

            dataSource = new JdbcDataSource();
            dataSource.setURL(dbProperties.getProperty("database.url"));
            dataSource.setUser(dbProperties.getProperty("database.user"));
            dataSource.setPassword(dbProperties.getProperty("database.password"));

            conn = dataSource.getConnection();

            if (scriptLoc != null) {
                log.info("Running script");
                File script = new File(scriptLoc);
                RunScript.execute(conn, new FileReader(script));
            }

            log.info("Connection Established: "
                    + conn.getMetaData().getDatabaseProductName() + "/" + conn.getCatalog());

//
//            while (true) {
//                //keep looping infinitly so user can connect to database via web console
//                //requires web to be started obviously.
//            }


        } catch (Exception e) {
            log.error("Error occurred: ", e.getCause());
        }

    }

    public static JdbcDataSource getDataSource() {
        return dataSource;
    }

    @AfterAll
    static void killDb() {
        log.info("shutting down");
        try {
            conn.close();
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
        server.shutdown();
        if (webServer != null)
            webServer.shutdown();
    }

    static void checkProperties(Properties properties) {

        if (properties.getProperty("database.driver").equals("") || properties.getProperty("database.driver") == null)
            fail("Unable to initialize tests: No database driver specified in database.properties");
        if (properties.getProperty("database.url").equals("") || properties.getProperty("database.url") == null)
            fail("Unable to initialize tests: No database url specified in database.properties");
        if (properties.getProperty("database.url").equals("") || properties.getProperty("database.user") == null)
            fail("Unable to initialize tests: No database user specified in database.properties");
        if (properties.getProperty("database.url").equals("") || properties.getProperty("database.password") == null)
            fail("Unable to initialize tests: No database password specified in database.properties");

    }

}
