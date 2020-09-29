package com.rvh.openoffice;

import com.rvh.openoffice.parts.SheetPartsCreator;
import com.rvh.openoffice.parts.WorkBookPartCreator;
import com.rvh.openoffice.parts.config.ConfigCollection;
import com.rvh.openoffice.parts.config.SheetConfig;
import com.rvh.openoffice.parts.config.WorkBookConfig;
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

    private ConfigCollection<SheetConfig> configCollection;

    public PackageCreator(ConfigCollection<SheetConfig> configCollection) {
        this.configCollection = configCollection;
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

                ConfigCollection<WorkBookConfig> workBookConfigs = new ConfigCollection<>();
                SheetPartsCreator sheetPartsCreator = new SheetPartsCreator(zos, configCollection);
                sheetPartsCreator.createPart();
                WorkBookPartCreator wbCreator = new WorkBookPartCreator(zos, workBookConfigs);
                wbCreator.createPart();
            }
        }
    }

    public void setConfigCollection(ConfigCollection<SheetConfig> sheetConfigCollection) {
        this.configCollection = sheetConfigCollection;
    }

    private void copyStream(InputStream in, OutputStream out) throws IOException {
        byte[] chunk = new byte[1024];
        int count;
        while ((count = in.read(chunk)) >= 0) {
            out.write(chunk, 0, count);
        }
    }
}
