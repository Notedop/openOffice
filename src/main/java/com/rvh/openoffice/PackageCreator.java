package com.rvh.openoffice;

import com.rvh.openoffice.parts.SheetPartsCreator;
import com.rvh.openoffice.parts.config.SheetConfig;
import com.rvh.openoffice.parts.config.SheetConfigCollection;
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

    private SheetConfigCollection sheetConfigCollection;

    public PackageCreator(SheetConfigCollection sheetConfigCollection) {
        this.sheetConfigCollection = sheetConfigCollection;
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

                for (SheetConfig config : sheetConfigCollection.getSheetConfigs()) {
                    SheetPartsCreator sheetPartsCreator = new SheetPartsCreator(zos, config);
                    sheetPartsCreator.createPart();
                }
            }
        }
    }

    public void setSheetConfigCollection(SheetConfigCollection sheetConfigCollection) {
        this.sheetConfigCollection = sheetConfigCollection;
    }

    private void copyStream(InputStream in, OutputStream out) throws IOException {
        byte[] chunk = new byte[1024];
        int count;
        while ((count = in.read(chunk)) >= 0) {
            out.write(chunk, 0, count);
        }
    }
}
