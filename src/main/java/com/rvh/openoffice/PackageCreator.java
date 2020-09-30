package com.rvh.openoffice;

import com.rvh.openoffice.parts.main.RelPartCreator;
import com.rvh.openoffice.parts.spreadsheet.SheetPartsCreator;
import com.rvh.openoffice.parts.spreadsheet.WorkBookPartCreator;
import com.rvh.openoffice.parts.main.config.*;
import com.rvh.openoffice.parts.spreadsheet.config.SheetConfig;
import com.rvh.openoffice.parts.spreadsheet.config.WorkBookConfig;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.poi.openxml4j.opc.internal.ZipHelper;
import org.apache.poi.openxml4j.util.ZipSecureFile;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

public class PackageCreator {

    //must be provided via constructor
    private ConfigCollection<SheetConfig> sheetConfigs;

    //will be populated during processing
    private ConfigCollection<RelConfig> relConfigs = new ConfigCollection<>();
    ;
    private ConfigCollection<ContentTypeConfig> contentTypeConfigs = new ConfigCollection<>();
    ;
    private ConfigCollection<WorkBookConfig> workBookConfigs = new ConfigCollection<>();

    public PackageCreator(ConfigCollection<SheetConfig> sheetConfigs) {
        this.sheetConfigs = sheetConfigs;
    }

    public void generate(File template, String entry, OutputStream out) throws IOException, XMLStreamException {

        try (ZipSecureFile zip = ZipHelper.openZipFile(template)) {
            try (ZipArchiveOutputStream zos = new ZipArchiveOutputStream(out)) {
                Enumeration<? extends ZipArchiveEntry> en = zip.getEntries();
                while (en.hasMoreElements()) {
                    ZipArchiveEntry ze = en.nextElement();
                    if (!ze.getName().equals("xl/worksheets/" + entry + ".xml")) {
                        zos.putArchiveEntry(new ZipArchiveEntry(ze.getName()));
                        try (InputStream is = zip.getInputStream(ze)) {
                            copyStream(is, zos);
                        }
                        zos.closeArchiveEntry();
                    }
                }


                SheetPartsCreator sheetPartsCreator = new SheetPartsCreator(zos, sheetConfigs, workBookConfigs, relConfigs, contentTypeConfigs);
                sheetPartsCreator.createPart();
                WorkBookPartCreator wbCreator = new WorkBookPartCreator(zos, workBookConfigs);
                wbCreator.createPart();
                RelPartCreator relPartCreator = new RelPartCreator(zos, relConfigs);
                relPartCreator.createPart();
            }
        }
    }

    public void setSheetConfigs(ConfigCollection<SheetConfig> sheetConfigCollection) {
        this.sheetConfigs = sheetConfigCollection;
    }

    private void copyStream(InputStream in, OutputStream out) throws IOException {
        byte[] chunk = new byte[1024];
        int count;
        while ((count = in.read(chunk)) >= 0) {
            out.write(chunk, 0, count);
        }
    }
}
