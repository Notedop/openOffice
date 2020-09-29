package com.rvh.openoffice;

import com.rvh.openoffice.parts.*;
import com.rvh.openoffice.parts.config.Config;
import com.rvh.openoffice.parts.config.SheetConfig;
import com.rvh.openoffice.parts.config.TableConfig;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.poi.openxml4j.opc.internal.ZipHelper;
import org.apache.poi.openxml4j.util.ZipSecureFile;

import javax.sql.DataSource;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

public class PackageCreator {

    private final DataSource dataSource;
    private Map<String, Config> packageConfiguration;

    public PackageCreator(DataSource dataSource) {
        this.dataSource = dataSource;
        }

    public void generate(File template, String entry, OutputStream out) throws IOException, XMLStreamException {

        try (ZipSecureFile zip = ZipHelper.openZipFile(template)) {
            try (ZipArchiveOutputStream zos = new ZipArchiveOutputStream(out)) {
                Enumeration<? extends ZipArchiveEntry> en = zip.getEntries();
                while (en.hasMoreElements()) {
                    ZipArchiveEntry ze = en.nextElement();
                    if (!ze.getName().equals("xl/worksheets/"+entry+".xml")) {
                        zos.putArchiveEntry(new ZipArchiveEntry(ze.getName()));
                        try (InputStream is = zip.getInputStream(ze)) {
                            copyStream(is, zos);
                        }
                        zos.closeArchiveEntry();
                    }
                }

                List<Config> configs = new ArrayList<>();
                TableConfig tableConfig = new TableConfig("main");
                //do something with tableConfig.
                configs.add(new SheetConfig("sheet1", dataSource,"select * from excel_test_data", 1000, tableConfig));
                PartsCreator partsCreator = new SheetPartsCreator( zos, configs);
                partsCreator.createPart();

            }
        }
    }

    public void addSheetConfig(SheetConfig config) {
        packageConfiguration.put("SheetConfig", config);
    }

    private void copyStream(InputStream in, OutputStream out) throws IOException {
        byte[] chunk = new byte[1024];
        int count;
        while ((count = in.read(chunk)) >= 0) {
            out.write(chunk, 0, count);
        }
    }
}
