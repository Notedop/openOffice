package com.rvh.openoffice.parts.main.config;

import com.rvh.openoffice.parts.main.enums.RelationTypes;

public class RelConfig extends Config{

    private final RelationTypes relType;
    private final String target;

    public RelConfig(String name, String id, RelationTypes relType, String target, String uri) {
        super(name, id, uri);
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
