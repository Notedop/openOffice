package com.rvh.openoffice.test;

import com.rvh.openoffice.PackageCreator;
import com.rvh.openoffice.test.basetestcase.h2DatabaseTests;
import org.junit.jupiter.api.Test;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

class testXml extends h2DatabaseTests {

    @Test
    void testConnection() throws IOException, XMLStreamException {

        PackageCreator creator = new PackageCreator(getDataSource());

        File outputFile = new File("test.xlsx");
        OutputStream fos = new FileOutputStream(outputFile);
        File template = new File(this.getClass().getClassLoader().getResource("template.xlsx").getFile());
        creator.generate(template, "test",fos);
        fos.close();

    }

}
