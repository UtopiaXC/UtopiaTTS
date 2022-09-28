package com.utopiaxc.utopiatts.tts.enums;

public enum Driver {
    AZURE_SDK("AZURE_SDK","azure_sdk"),
    WEBSOCKET("WEBSOCKET","websocket");

    private final String mName;
    private final String mId;

    Driver(String name, String id){
        mName=name;
        mId=id;
    }

    public String getName() {
        return mName;
    }

    public String getId() {
        return mId;
    }

    public static Driver getRole(String name) {
        for (Driver role: Driver.values()) {
            if (role.getName().equals(name)) {
                return role;
            }
        }
        return AZURE_SDK;
    }
}
