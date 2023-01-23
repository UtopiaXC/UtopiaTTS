package com.utopiaxc.utopiatts.tts.enums;

import androidx.annotation.NonNull;

public enum Regions {
    SOUTH_AFRICA_NORTH("SOUTH_AFRICA_NORTH", "southafricanorth", false),
    EAST_ASIA("EAST_ASIA", "eastasia", false),
    SOUTH_EAST_ASIA("SOUTH_EAST_ASIA", "southeastasia", true),
    AUSTRALIA_EAST("AUSTRALIA_EAST", "australiaeast", false),
    CENTRAL_INDIA("CENTRAL_INDIA", "centralindia", false),
    JAPAN_EAST("JAPAN_EAST", "japaneast", false),
    JAPAN_WEST("JAPAN_WEST", "japanwest", false),
    KOREA_CENTRAL("KOREA_CENTRAL", "koreacentral", false),
    CANADA_CENTRAL("CANADA_CENTRAL", "canadacentral", false),
    NORTH_EUROPE("NORTH_EUROPE", "northeurope", false),
    WEST_EUROPE("WEST_EUROPE", "westeurope", true),
    FRANCE_CENTRAL("FRANCE_CENTRAL", "francecentral", false),
    GERMANY_WEST_CENTRAL("GERMANY_WEST_CENTRAL", "germanywestcentral", false),
    NORWAY_EAST("NORWAY_EAST", "norwayeast", false),
    SWITZERLAND_NORTH("SWITZERLAND_NORTH", "switzerlandnorth", false),
    SWITZERLAND_WEST("SWITZERLAND_WEST", "switzerlandwest", false),
    UK_SOUTH("UK_SOUTH", "uksouth", false),
    UAE_NORTH("UAE_NORTH", "uaenorth", false),
    BRAZIL_SOUTH("BRAZIL_SOUTH", "brazilsouth", false),
    CENTRAL_US("CENTRAL_US", "centralus", false),
    EAST_US("EAST_US", "eastus", true),
    EAST_US2("EAST_US2", "eastus2", true),
    NORTH_CENTRAL_US("NORTH_CENTRAL_US", "northcentralus", false),
    SOUTH_CENTRAL_US("SOUTH_CENTRAL_US", "southcentralus", false),
    WEST_CENTRAL_US("WEST_CENTRAL_US", "westcentralus",false),
    WEST_US("WEST_US", "westus", false),
    WEST_US2("WEST_US2", "westus2", false),
    WEST_US3("WEST_US3", "westus3", false);

    private final String mName;
    private final String mId;
    private final boolean mIsSupportPre;

    Regions(String name, String id, boolean isSupportPre) {
        mName = name;
        mId = id;
        mIsSupportPre = isSupportPre;
    }

    @NonNull
    @Override
    public String toString() {
        return mName;
    }


    public static Regions getRegion(String name) {
        for (Regions region : Regions.values()) {
            if (region.getName().equals(name)) {
                return region;
            }
        }
        return SOUTH_EAST_ASIA;
    }

    public String getName() {
        return mName;
    }

    public String getId() {
        return mId;
    }

    public boolean isNotSupportPre(){
        return !mIsSupportPre;
    }
}
