package com.utopiaxc.utopiatts.enums;

import com.utopiaxc.utopiatts.tts.enums.Driver;

public enum SettingsEnum {

    THEME("THEME", "theme", "auto"),
    FIRST_BOOT("FIRST_BOOT", "first_boot", true),
    AZURE_REGION("AZURE_REGION", "azure_region", ""),
    AZURE_TOKEN("AZURE_TOKEN", "azure_token", "NULL"),
    OUTPUT_FORMAT("OUTPUT_FORMAT", "output_format", ""),
    ACTOR("ACTOR", "actor", ""),
    ROLE("ROLE", "role", ""),
    STYLE("STYLE", "style", ""),
    STYLE_DEGREE("STYLE_DEGREE", "style_degree", 0),
    TTS_DRIVER("TTS_DRIVER","tts_driver", Driver.AZURE_SDK.getId());
    private final String mName;
    private final String mKey;
    private final Object mDefaultValue;

    SettingsEnum(String name, String key, Object defaultValue) {
        mName = name;
        mKey = key;
        mDefaultValue = defaultValue;
    }

    public String getName() {
        return mName;
    }

    public String getKey() {
        return mKey;
    }

    public Object getDefaultValue() {
        return mDefaultValue;
    }

}
