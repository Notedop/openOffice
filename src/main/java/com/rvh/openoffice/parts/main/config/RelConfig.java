package com.rvh.openoffice.parts.main.config;

import com.rvh.openoffice.parts.main.enums.RelationShipTypes;

public class RelConfig extends Config{

    private final RelationShipTypes relType;
    private final String target;

    public RelConfig(String name, String id, RelationShipTypes relType, String target, String uri) {
        super(name, id, uri);
        this.relType = relType;
        this.target = target;
    }

    public RelationShipTypes getRelType() {
        return relType;
    }

    public String getTarget() {
        return target;
    }
}
