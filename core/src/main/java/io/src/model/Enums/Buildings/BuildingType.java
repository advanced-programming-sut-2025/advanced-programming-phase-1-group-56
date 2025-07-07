package io.src.model.Enums.Buildings;

import io.src.model.MapModule.Buildings.JojaMart;
import io.src.model.items.Saleable;

public enum BuildingType implements Saleable {
    COOP("Coop", 4, 300, 100, 6, 3 , true , "\uD83C\uDFE6"),
    BIG_COOP("Big Coop", 8, 400, 150, 6, 3 , true , "\uD83C\uDFE6"),
    DELUXE_COOP("Deluxe Coop", 12, 500, 200, 6, 3 , true , "\uD83C\uDFE6"),
    BARN("Barn", 4, 350, 150, 7, 4 , true , "\uD83C\uDFEB"),
    BIG_BARN("Big Barn", 8, 450, 200, 7, 4 , true , "\uD83C\uDFEB"),
    DELUXE_BARN("Deluxe Barn", 12, 350, 300, 7, 4 , true , "\uD83C\uDFEB"),
    WELL("Well", Integer.MAX_VALUE, 0, 75, 3, 3 , true , "\uD83C\uDFEB"),
    SHIPPING_BIN("Shipping Bin", 100, 150, 0, 1, 1 , true , "\uD83E\uDDFA"),

//    JOJA_MART("Joja Mart", 100, 150, 0, 1, 1 , false),
//    MARNIES_RANCH("Marnies Ranch", 100, 150, 0, 1, 1 , false),
//    FiSH_SHOP("Fish Shop", 100, 150, 0, 1, 1 , false),
//    PIERRES_STORE("Pierres Store", 100, 150, 0, 1, 1 , false),
//    BLACKSMITH("Blacksmith", 100, 150, 0, 1, 1 , false),
//    THE_SALOON_STARDROP("The Saloon Stardrop", 100, 150, 0, 1, 1 , false),
//    CARPENTER_SHOP("Carpenter Shop", 100, 150, 0, 1, 1 , false),
    STORE("Store", -1, 0, -1, 3, 3 , false , "\uD83C\uDFEA"),
    HOME("Home", -1, -1, -1, -1, -1 , false , "\uD83C\uDFE0"),
    GREEN_HOUSE("Green House", -1, -1, -1, -1, -1 , false , "\uD83C\uDFE1");
    //TODO

    private final String name;
    private final int capacity;
    private final int woodCount;
    private final int stoneCount;
    private final int width;
    private final int height;
    private final boolean canBuild;
    private final String icon;

    BuildingType(String name, int capacity, int woodCount, int stoneCount, int width, int height , boolean canBuild, String icon) {
        this.name = name;
        this.capacity = capacity;
        this.woodCount = woodCount;
        this.stoneCount = stoneCount;
        this.width = width;
        this.height = height;
        this.canBuild = canBuild;
        this.icon = icon;
    }

    public int getCapacity() {
        return capacity;
    }


    @Override
    public String getName() {
        return this.name;
    }

    public int getWoodCount() {
        return woodCount;
    }

    public int getStoneCount() {
        return stoneCount;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public static BuildingType getTypeByName(String name) {
        for (BuildingType type : BuildingType.values()) {
            if (type.name().equals(name)) {
                return type;
            }
        }
        return null;
    }

    public boolean isCanBuild() {
        return canBuild;
    }

    public String getIcon() {
        return "BB";
    }
}
