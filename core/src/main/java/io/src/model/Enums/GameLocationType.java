package io.src.model.Enums;

import io.src.model.MapModule.Buildings.*;

public enum GameLocationType {
    Town("gameLocations\\Town4.tmx", false, null, ""),
    Farm1("gameLocations\\Farm1.tmx", false, null, ""),
    Farm2("gameLocations\\Farm2.tmx", false, null, ""),
    //TODO handle indoors
    Home_Indoor("gameLocations\\Player_House_Indoor.tmx", true, null, ""),
    GreenHouse_Indoor("gameLocations\\Green_House_Indoor.tmx", true, null, ""),
    JojaMart_Indoor("gameLocations\\JojaMart_Indoor.tmx", true, JojaMart.class, ""),
    StardropSallon_Indoor("gameLocations\\The_Stardrop_Saloon_Indoor.tmx", true, TheSaloonStardrop.class, "Saloon"),
    Blacksmith_Indoor("gameLocations\\Blacksmith_Indoor.tmx", true, Blacksmith.class, "BlackSmith"),
    Fishshop_Indoor("gameLocations\\Fish_Shop_Indoor.tmx", true, FishShop.class, "FishShop"),
    PierreGeneralStore_Indoor("gameLocations\\Pierres_General_Store_Indoor.tmx", true, PierresGeneralStore.class, "Pierre's General Store"),
    CarpenterShop_Indoor("gameLocations\\Carpenter_Shop_Indoor.tmx", true, CarpentersShop.class, "CarpenterShop"),
    MarniesRanch_Indoor("gameLocations\\Marnies_Ranch_Indoor.tmx", true, MarniesRanch.class, "Marnie's Ranch"),
    Barn_Indoor("gameLocations\\Barn.tmx", true, null, ""),
    Coop_Indoor("gameLocations\\Coop_Indoor.tmx", true, null, "");
    private final String assetName;
    private final boolean indoor;
    private final Class<? extends Store> relatedClazz;
    private final String storeAvatarName;

    public static GameLocationType getGameLocationTypeByName(String assetName) {
        assetName = assetName.substring(assetName.lastIndexOf("\\") + 1);
        for (GameLocationType gameLocationType : GameLocationType.values()) {
            if (gameLocationType.getAssetName().contains(assetName)) {
                return gameLocationType;
            }
        }
        return null;
    }


    GameLocationType(String assetName, boolean indoor, Class<? extends Store> relatedClazz, String storeAvatarName) {
        this.assetName = assetName;
        this.indoor = indoor;
        this.relatedClazz = relatedClazz;
        this.storeAvatarName = storeAvatarName;
    }

    public String getAssetName() {
        return assetName;
    }

    public Class<? extends Store> getRelatedClazz() {
        return relatedClazz;
    }

    public boolean isIndoor() {
        return indoor;
    }

    public String getStoreAvatarName() {
        return storeAvatarName;
    }
}
