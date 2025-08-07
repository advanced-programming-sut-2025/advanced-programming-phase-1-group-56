package io.src.model.Enums;

import io.src.model.MapModule.Buildings.*;
import io.src.model.MapModule.GameLocations.Farm;
import io.src.model.MapModule.GameLocations.Town;

public enum GameLocationType {
    Town("gameLocations\\Town4.tmx", false, io.src.model.MapModule.GameLocations.Town.class),
    Farm1("gameLocations\\Farm1.tmx", false, Farm.class),
    Farm2("gameLocations\\Farm2.tmx", false, Farm.class),
    //TODO handle indoors
    Home_Indoor("gameLocations\\Player_House_Indoor.tmx", true, Home.class),
    GreenHouse_Indoor("gameLocations\\Green_House_Indoor.tmx", true, GreenHouse.class),
    JojaMart_Indoor("gameLocations\\JojaMart_Indoor.tmx", true, JojaMart.class),
    StardropSallon_Indoor("gameLocations\\The_Stardrop_Saloon_Indoor.tmx", true, TheSaloonStardrop.class),
    Blacksmith_Indoor("gameLocations\\Blacksmith_Indoor.tmx", true, Blacksmith.class),
    Fishshop_Indoor("gameLocations\\Fish_Shop_Indoor.tmx", true, FishShop.class),
    PierreGeneralStore_Indoor("gameLocations\\Pierres_General_Store_Indoor.tmx", true, PierresGeneralStore.class),
    CarpenterShop_Indoor("gameLocations\\Carpenter_Shop_Indoor.tmx", true, CarpentersShop.class),
    MarniesRanch_Indoor("gameLocations\\Marnies_Ranch_Indoor.tmx", true, MarniesRanch.class)
    ;

    private final String assetName;
    private final boolean indoor;
    private final Class<?> relatedClazz;

    public static GameLocationType getGameLocationTypeByName(String assetName) {
        assetName = assetName.substring(assetName.lastIndexOf("\\") + 1);
        for (GameLocationType gameLocationType : GameLocationType.values()) {
            if (gameLocationType.getAssetName().contains(assetName)) {
                return gameLocationType;
            }
        }
        return null;
    }


    GameLocationType(String assetName, boolean indoor, Class<?> relatedClazz) {
        this.assetName = assetName;
        this.indoor = indoor;
        this.relatedClazz = relatedClazz;
    }

    public String getAssetName() {
        return assetName;
    }

    public Class<?> getRelatedClazz() {
        return relatedClazz;
    }

    public boolean isIndoor() {
        return indoor;
    }
}
