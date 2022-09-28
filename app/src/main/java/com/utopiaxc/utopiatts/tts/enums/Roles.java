package com.utopiaxc.utopiatts.tts.enums;

public enum Roles {
    NONE("NONE",""),
    YOUNG_ADULT_FEMALE("YOUNG_ADULT_FEMALE","YoungAdultFemale"),
    YOUNG_ADULT_MALE("YOUNG_ADULT_MALE","YoungAdultMale"),
    OLDER_ADULT_FEMALE("OLDER_ADULT_FEMALE","OlderAdultFemale"),
    OLDER_ADULT_MALE("OLDER_ADULT_MALE","OlderAdultMale"),
    SENIOR_FEMALE("SENIOR_FEMALE","SeniorFemale"),
    SENIOR_MALE("SENIOR_MALE","SeniorMale"),
    GIRL("GIRL","Girl"),
    BOY("BOY","Boy"),
    NARRATOR("NARRATOR","Narrator");

    private final String mName;
    private final String mId;

    Roles(String name, String id){
        mName=name;
        mId=id;
    }

    public String getName() {
        return mName;
    }

    public String getId() {
        return mId;
    }

    public static Roles getRole(String name) {
        for (Roles role: Roles.values()) {
            if (role.getName().equals(name)) {
                return role;
            }
        }
        return NONE;
    }
}
