package io.src.model.Enums.Buildings;

import io.src.model.items.Saleable;
import org.jetbrains.annotations.Nullable;

public enum BuildingType implements Saleable {
    COOP("Coop", 4, 300, 100, 6, 3, true, "Coop"),
    BIG_COOP("Big Coop", 8, 400, 150, 6, 3, true, "Big_Coop"),
    DELUXE_COOP("Deluxe Coop", 12, 500, 200, 6, 3, true, "Deluxe_Coop"),
    BARN("Barn", 4, 350, 150, 7, 4, true, "Barn"),
    BIG_BARN("Big Barn", 8, 450, 200, 7, 4, true, "Big_Barn"),
    DELUXE_BARN("Deluxe Barn", 12, 350, 300, 7, 4, true, "Deluxe_Barn"),
    WELL("Well", Integer.MAX_VALUE, 0, 75, 3, 3, true, "well"),
    SHIPPING_BIN("Shipping Bin", 100, 150, 0, 1, 1, true, "Shipping_Bin"),
    JOJA_MART("Joja Mart", 100, 150, 0, 1, 1, false, "Jojamart"),
    MARNIES_RANCH("Marnies Ranch", 100, 150, 0, 1, 1, false, "Marnies_Ranch"),
    FISH_SHOP("Fish Shop", 100, 150, 0, 1, 1, false, "Fish_Shop"),
    PIERRES_STORE("Pierres Store", 100, 150, 0, 1, 1, false, "Pierre_General_Store"),
    BLACKSMITH("Blacksmith", 100, 150, 0, 1, 1, false, "Blacksmith"),
    THE_SALOON_STARDROP("The Saloon Stardrop", 100, 150, 0, 1, 1, false, "The_Sallon_Stardrop"),
    CARPENTER_SHOP("Carpenter Shop", 100, 150, 0, 1, 1, false, "Carpenter_Shop"),
    STORE("Store", -1, 0, -1, 3, 3, false, "Store_Indoor"),
    HOME("Home", -1, -1, -1, -1, -1, false, "Croped_Player_House"),
    GREEN_HOUSE("Green House", -1, -1, -1, -1, -1, false, "Green_House"),
    ;
    //TODO handle atlases
    // handle indoor
    // handle icon
    // handle ....

    private final String name;
    private final int capacity;
    private final int woodCount;
    private final int stoneCount;
    private final int width;
    private final int height;
    private final boolean canBuild;
    private final String assetName;

    BuildingType(String name, int capacity, int woodCount, int stoneCount, int width, int height, boolean canBuild, String assetName) {
        this.name = name;
        this.capacity = capacity;
        this.woodCount = woodCount;
        this.stoneCount = stoneCount;
        this.width = width;
        this.height = height;
        this.canBuild = canBuild;
        this.assetName = assetName;
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

    public boolean isIndoor() {
        return assetName.toLowerCase().contains("indoor");
    }

    public boolean isAtlas() {
        return assetName.toLowerCase().contains("atlas");
    }

    public static BuildingType getTypeByName(String name) {
        for (BuildingType type : BuildingType.values()) {
            if (type.name().equals(name)) {
                return type;
            }
        }
        return null;
    }

    public boolean isBuildable() {
        return canBuild;
    }

    @Nullable
    public String getAssetName() {
        return switch (assetName) {
            case "null" -> null;
            case "" -> name.replace(" ", "_");
            default -> assetName;
        };
    }
}
