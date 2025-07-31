package io.src.model.Enums;

public enum GameLocationType {
    Town("Town4.tmx"),
    Farm1("Farm1.tmx"),
    Farm2("Farm2.tmx"),
    //TODO handle indoors
    Home_Indoor("Home.png"),
    GreenHouse_Indoor("GreenHouse.png"),
    JojaMart_Indoor("Jojamart.png"),
    StardropSallon_Indoor("StardropSallon.png"),
    Blacksmith_Indoor("Blacksmith.png"),
    Fishshop_Indoor("Fishshop.png"),
    PierreGeneralStore_Indoor("PierreGeneralStore.png"),
    CarpenterShop_Indoor("CarpenterShop.png"),
    MarniesRanch_Indoor("MarniesRanch.png");
    ;

    private final String assetName;


    GameLocationType(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetName() {
        return assetName;
    }
}
