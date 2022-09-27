package com.utopiaxc.utopiatts.tts;

public enum Regions {
    SOUTH_AFRICA_NORTH("SOUTH_AFRICA_NORTH", "southafricanorth"),
    EAST_ASIA("EAST_ASIA", "eastasia"),
    SOUTH_EAST_ASIA("SOUTH_EAST_ASIA", "southeastasia"),
    AUSTRALIA_EAST("AUSTRALIA_EAST", "australiaeast"),
    CENTRAL_INDIA("CENTRAL_INDIA", "centralindia"),
    JAPAN_EAST("JAPAN_EAST", "japaneast"),
    JAPAN_WEST("JAPAN_WEST", "japanwest"),
    KOREA_CENTRAL("KOREA_CENTRAL", "koreacentral"),
    CANADA_CENTRAL("CANADA_CENTRAL", "canadacentral"),
    NORTH_EUROPE("NORTH_EUROPE", "northeurope"),
    WEST_EUROPE("WEST_EUROPE", "westeurope"),
    FRANCE_CENTRAL("FRANCE_CENTRAL", "francecentral"),
    GERMANY_WEST_CENTRAL("GERMANY_WEST_CENTRAL", "germanywestcentral"),
    NORWAY_EAST("NORWAY_EAST", "norwayeast"),
    SWITZERLAND_NORTH("SWITZERLAND_NORTH", "switzerlandnorth"),
    SWITZERLAND_WEST("SWITZERLAND_WEST", "switzerlandwest"),
    UK_SOUTH("UK_SOUTH", "uksouth"),
    UAE_NORTH("UAE_NORTH", "uaenorth"),
    BRAZIL_SOUTH("BRAZIL_SOUTH", "brazilsouth"),
    CENTRAL_US("CENTRAL_US", "centralus"),
    EAST_US("EAST_US", "eastus"),
    EAST_US2("EAST_US2", "eastus2"),
    NORTH_CENTRAL_US("NORTH_CENTRAL_US", "northcentralus"),
    SOUTH_CENTRAL_US("SOUTH_CENTRAL_US", "southcentralus"),
    WEST_CENTRAL_US("WEST_CENTRAL_US", "westcentralus"),
    WEST_US("WEST_US", "westus"),
    WEST_US2("WEST_US2", "westus2"),
    WEST_US3("WEST_US3", "westus3");

    private final String mName;
    private final String mId;

    Regions(String name, String id) {
        mName = name;
        mId = id;
    }

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
        return null;
    }

    public String getName() {
        return mName;
    }

    public String getId() {
        return mId;
    }
}
