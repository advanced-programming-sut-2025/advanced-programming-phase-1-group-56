package model.Enums.Buildings;

import model.items.Saleable;

public enum BuildingType implements Saleable {
    COOP("Coop", 4, 300, 100, 6, 3),
    BIG_COOP("Big Coop", 8, 400, 150, 6, 3),
    DELUXE_COOP("Deluxe Coop", 12, 500, 200, 6, 3),
    BARN("Barn", 4, 350, 150, 7, 4),
    BIG_BARN("Big Barn", 8, 450, 200, 7, 4),
    DELUXE_BARN("Deluxe Barn", 12, 350, 300, 7, 4),
    WELL("Well", Integer.MAX_VALUE, 0, 75, 3, 3),
    SHIPPING_BIN("Shipping Bin", 100, 150, 0, 1, 1);

    private final String name;
    private final int capacity;
    private final int woodCount;
    private final int stoneCount;
    private final int width;
    private final int height;

    BuildingType(String name, int capacity, int woodCount, int stoneCount, int width, int height) {
        this.name = name;
        this.capacity = capacity;
        this.woodCount = woodCount;
        this.stoneCount = stoneCount;
        this.width = width;
        this.height = height;
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
}
