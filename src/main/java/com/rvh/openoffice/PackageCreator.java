package com.rvh.openoffice;

import com.rvh.openoffice.parts.main.AppPartCreator;
import com.rvh.openoffice.parts.main.ContentTypePartCreator;
import com.rvh.openoffice.parts.main.CorePartCreator;
import com.rvh.openoffice.parts.main.RelPartCreator;
import com.rvh.openoffice.parts.main.config.ConfigCollection;
import com.rvh.openoffice.parts.main.config.ContentTypeConfig;
import com.rvh.openoffice.parts.main.config.CoreConfig;
import com.rvh.openoffice.parts.main.config.RelConfig;
import com.rvh.openoffice.parts.main.enums.RelationShipTypes;
import com.rvh.openoffice.parts.spreadsheet.SheetPartsCreator;
import com.rvh.openoffice.parts.spreadsheet.WorkBookPartCreator;
import com.rvh.openoffice.parts.spreadsheet.config.SheetConfig;
import com.rvh.openoffice.parts.spreadsheet.config.WorkBookConfig;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class PackageCreator {

    private final CoreConfig coreConfig;
    //will be populated during processing
    private final ConfigCollection<RelConfig> relConfigs = new ConfigCollection<>();
    private final ConfigCollection<ContentTypeConfig> contentTypeConfigs = new ConfigCollection<>();
    private final ConfigCollection<WorkBookConfig> workBookConfigs = new ConfigCollection<>();
    //must be provided via constructor
    private ConfigCollection<SheetConfig> sheetConfigs;

    public PackageCreator(CoreConfig coreConfig, ConfigCollection<SheetConfig> sheetConfigs) {
        this.coreConfig = coreConfig;
        this.sheetConfigs = sheetConfigs;
    }

    public void generate(File template, String entry, OutputStream out) throws IOException, XMLStreamException {

        try (ZipArchiveOutputStream zos = new ZipArchiveOutputStream(out)) {

            //create main relation configurations
            String relId = "rId" + (relConfigs.countConfigByName(".rels") + 1);
            relConfigs.addConfig(new RelConfig(".rels", relId, RelationShipTypes.EXTENDED, "docProps/app.xml", "_rels/"));

            relId = "rId" + (relConfigs.countConfigByName(".rels") + 1);
            relConfigs.addConfig(new RelConfig(".rels", relId, RelationShipTypes.OFFICE_DOC, "xl/workbook.xml", "_rels/"));

            AppPartCreator appPartCreator = new AppPartCreator(zos, null);
            appPartCreator.createPart();

            CorePartCreator corePartCreator = new CorePartCreator(zos, coreConfig, relConfigs, contentTypeConfigs);
            corePartCreator.createPart();

            SheetPartsCreator sheetPartsCreator = new SheetPartsCreator(zos, sheetConfigs, workBookConfigs, relConfigs, contentTypeConfigs);
            sheetPartsCreator.createPart();

            WorkBookPartCreator wbCreator = new WorkBookPartCreator(zos, workBookConfigs, contentTypeConfigs);
            wbCreator.createPart();

            RelPartCreator relPartCreator = new RelPartCreator(zos, relConfigs, contentTypeConfigs);
            relPartCreator.createPart();

            ContentTypePartCreator contentTypePartCreator = new ContentTypePartCreator(zos, contentTypeConfigs);
            contentTypePartCreator.createPart();

        }
    }

    public void setSheetConfigs(ConfigCollection<SheetConfig> sheetConfigCollection) {
        this.sheetConfigs = sheetConfigCollection;
    }

}
