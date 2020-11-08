package com.rvh.openoffice.test;

import com.rvh.openoffice.parts.main.config.CellConfig;
import com.rvh.openoffice.parts.main.config.ConfigCollection;
import com.rvh.openoffice.parts.main.config.StylePartCreator;
import com.rvh.openoffice.parts.main.config.styles.Border;
import com.rvh.openoffice.parts.main.config.styles.CellBorders;
import com.rvh.openoffice.parts.main.config.styles.Color;
import com.rvh.openoffice.parts.main.enums.styles.BorderStyle;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.junit.jupiter.api.Test;

import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.util.zip.ZipOutputStream;

public class stylePartCreatorTest {


    @Test
    void testStylePartCreator()  {

        ConfigCollection<CellConfig> configuration = new ConfigCollection<>();

        defineConfiguration(configuration);

        File outputFile = new File("styles.zip");

        try (OutputStream fos = new FileOutputStream(outputFile);
             ZipArchiveOutputStream os = new ZipArchiveOutputStream(fos)) {
            StylePartCreator partCreator = new StylePartCreator(os, configuration);
            partCreator.createPart();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
        }





    }

    private void defineConfiguration(ConfigCollection<CellConfig> configuration) {

        CellConfig monthConfig = new CellConfig("month", true);
        CellConfig yearConfig = new CellConfig("year", true);
        CellConfig priceConfig = new CellConfig("price", true);

        CellBorders cellBorders1 = new CellBorders("cellborders1", null, null);
        CellBorders cellBorders2 = new CellBorders("cellborders2", null, null);

        Border dashBorder = new Border();
        dashBorder.setStyle(BorderStyle.DASH_DOT);

        Border thickBorder = new Border();
        dashBorder.setStyle(BorderStyle.THICK);

        cellBorders1.setLeft(dashBorder);
        cellBorders1.setTop(dashBorder);
        cellBorders1.setBottom(dashBorder);
        cellBorders1.setRight(dashBorder);

        cellBorders2.setLeft(thickBorder);
        cellBorders2.setTop(thickBorder);
        cellBorders2.setBottom(thickBorder);
        cellBorders2.setRight(thickBorder);

        monthConfig.setCellBorders(cellBorders1);
        yearConfig.setCellBorders(cellBorders2);
        priceConfig.setCellBorders(cellBorders1);

        configuration.addConfig(monthConfig);
        configuration.addConfig(yearConfig);
        configuration.addConfig(priceConfig);
    }
}
