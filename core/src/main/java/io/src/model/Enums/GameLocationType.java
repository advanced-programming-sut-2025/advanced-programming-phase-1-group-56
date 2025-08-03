package io.src.model.Enums;

public enum GameLocationType {
    Town("gameLocations\\Town4.tmx"),
    Farm1("gameLocations\\Farm1.tmx"),
    Farm2("gameLocations\\Farm2.tmx"),
    //TODO handle indoors
    Home_Indoor("gameLocations\\Player_House_Indoor.tmx"),
    GreenHouse_Indoor("gameLocations\\Green_House_Indoor.tmx"),
    JojaMart_Indoor("gameLocations\\JojaMart_Indoor.tmx"),
    StardropSallon_Indoor("gameLocations\\The_Stardrop_Saloon_Indoor.tmx"),
    Blacksmith_Indoor("gameLocations\\Blacksmith_Indoor.tmx"),
    Fishshop_Indoor("gameLocations\\Fish_Shop_Indoor.tmx"),
    PierreGeneralStore_Indoor("gameLocations\\Pierres_General_Store_Indoor.tmx"),
    CarpenterShop_Indoor("gameLocations\\Carpenter_Shop_Indoor.tmx"),
    MarniesRanch_Indoor("gameLocations\\Marnies_Ranch_Indoor.tmx");

    private final String assetName;


    GameLocationType(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetName() {
        return assetName;
    }
}
