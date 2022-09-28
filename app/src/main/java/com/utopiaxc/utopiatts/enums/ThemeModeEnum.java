package com.utopiaxc.utopiatts.enums;

public enum ThemeModeEnum {
    AUTO_MODE("auto", "Auto Mode"),
    DAY_MODE("day", "Day Mode"),
    NIGHT_MODE("night", "Night Mode");

    private final String mode;
    private final String description;
    ThemeModeEnum(String mode, String description) {
        this.mode = mode;
        this.description = description;
    }

    public String getMode() {
        return mode;
    }
    public String getDescription() {
        return description;
    }
}
