package com.rvh.openoffice.parts.main.enums.styles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum PatternType {

    NONE("none", "None"),
    SOLID("solid", "Solid"),
    MEDIUM_GRAY("mediumGray", "Medium Gray"),
    DARK_GRAY("darkGray", "Dary Gray"),
    LIGHT_GRAY("lightGray", "Light Gray"),
    DARK_HORIZONTAL("darkHorizontal", "Dark Horizontal"),
    DARK_VERTICAL("darkVertical", "Dark Vertical"),
    DARK_DOWN("darkDown", "Dark Down"),
    DARK_UP("darkUp", "Dark Up"),
    DARK_GRID("darkGrid", "Dark Grid"),
    DARK_TRELLIS("darkTrellis", "Dark Trellis"),
    LIGHT_HORIZONTAL("lightHorizontal", "Light Horizontal"),
    LIGHT_VERTICAL("lightVertical", "Light Vertical"),
    LIGHT_DOWN("lightDown", "Light Down"),
    LIGHT_UP("lightUp", "Light Up"),
    LIGHT_GRID("lightGrid", "Light Grid"),
    LIGHT_TRELLIS("lightTrellis", "Light Trellis"),
    GRAY_125("gray125", "Gray 0.125"),
    GRAY_0625("gray0625", "Gray 0.0625");

    private final String value;
    private final String description;
    private final List<String> allDescriptions = new ArrayList<>();

    PatternType(String value, String description) {
        this.value = value;
        this.description = description;

        Arrays.stream(PatternType.values())
                .map(PatternType::getDescription)
                .forEachOrdered(allDescriptions::add);

    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getAllDescriptions() {
        return allDescriptions;
    }

    public PatternType getEnumByDescription(String description) {

        return Arrays.stream(PatternType.values())
                .filter(patternType -> patternType.description.equals(description))
                .findFirst()
                .orElse(null);
    }
}
