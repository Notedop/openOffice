package com.rvh.openoffice.parts.main;

import com.rvh.openoffice.parts.main.config.ConfigCollection;
import com.rvh.openoffice.parts.main.config.RelConfig;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelPartCreator extends PartsCreator<RelConfig> {

    private List<RelConfig> relConfigs;

    public RelPartCreator(ZipArchiveOutputStream zos, ConfigCollection<RelConfig> configCollection) {
        super(zos, configCollection);
    }

    @Override
    public void createPart() throws XMLStreamException, IOException {

        //Relations can be defined per part and thus result in multiple different part files
        Map<String, List<RelConfig>> sortedConfig = new HashMap<>();

        //sort the configurations based on file name
        configCollection.getConfigs().forEach(
                relConfig -> {
                    String fullUri = relConfig.getUri() + relConfig.getName();
                    if (!sortedConfig.containsKey(fullUri)) {
                        List<RelConfig> configs = new ArrayList<>();
                        configs.add(relConfig);
                        sortedConfig.put(fullUri, configs);
                    } else {
                        sortedConfig.get(fullUri).add(relConfig);
                    }
                });

        //for each filename, create the part
        for (Map.Entry<String, List<RelConfig>> entry : sortedConfig.entrySet()) {
            relConfigs = entry.getValue();
            createHeader(entry.getKey());
            createFooter();
        }
    }

    @Override
    public void createHeader(String name) throws XMLStreamException, IOException {
        zos.putArchiveEntry(new ZipArchiveEntry(name));
        xsw.writeStartDocument("UTF-8", "1.0");
        xsw.writeStartElement("Relationships");
        xsw.writeNamespace("xmlns", "http://schemas.openxmlformats.org/package/2006/relationships");

        for (RelConfig relConfig : relConfigs) {
            createRelationShip(relConfig);
        }

    }

    private void createRelationShip(RelConfig relConfig) throws XMLStreamException {
        xsw.writeEmptyElement("Relationship");
        xsw.writeAttribute("Id", relConfig.getId());
        xsw.writeAttribute("Type", relConfig.getRelType().getSchema());
        xsw.writeAttribute("Target", relConfig.getTarget());

    }

    @Override
    public void createFooter() throws XMLStreamException, IOException {
        xsw.writeEndElement();//Relationships
        xsw.flush();
        zos.closeArchiveEntry();

    }

}
