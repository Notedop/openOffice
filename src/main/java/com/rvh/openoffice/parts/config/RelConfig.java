package com.rvh.openoffice.parts.config;

import com.rvh.openoffice.parts.enums.ConfigType;
import com.rvh.openoffice.parts.enums.RelationTypes;

public class RelConfig extends Config{

    private final RelationTypes relType;
    private final String target;

    public RelConfig(String name, String id, RelationTypes relType, String target, String uri) {
        super(name, ConfigType.RELATION, id, uri);
        this.relType = relType;
        this.target = target;
    }

    public RelationTypes getRelType() {
        return relType;
    }

    public String getTarget() {
        return target;
    }
}
