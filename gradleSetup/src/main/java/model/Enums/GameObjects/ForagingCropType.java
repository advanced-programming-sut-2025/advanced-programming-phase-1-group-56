package model.Enums.GameObjects;

import model.Enums.Items.FoodType;
import model.Enums.Items.FruitType;
import model.Enums.Items.ItemType;

public enum ForagingCropType {
    COMMON_MUSHROOM(FoodType.COMMON_MUSHROOM, "Special", 40, 38),
    DAFFODIL(FoodType.DAFFODIL, "Spring", 30, 0),
    DANDELION(FoodType.DANDELION, "Spring", 40, 25),
    LEEK(FoodType.LEEK, "Spring", 60, 40),
    MOREL(FoodType.MOREL, "Spring", 150, 20),
    SALMONBERRY(FoodType.SALMON_BERRY, "Spring", 5, 25),
    SPRING_ONION(FoodType.SPRING_ONION, "Spring", 8, 13),
    WILD_HORSERADISH(FoodType.WILD_HORSERADISH, "Spring", 50, 13),
    FIDDLEHEAD_FERN(FoodType.FIDDLE_HEAD_FERN, "Summer", 90, 25),
    GRAPE(FoodType.GRAPE, "Summer", 80, 38),
    RED_MUSHROOM(FoodType.RED_MUSHROOM, "Summer", 75, -50),
    SPICE_BERRY(FoodType.SPICE_BERRY, "Summer", 80, 25),
    SWEET_PEA(FoodType.SWEET_PEA, "Summer", 50, 0),
    BLACKBERRY(FoodType.BLACKBERRY, "Fall", 25, 25),
    CHANTERELLE(FoodType.CHANTERELLE, "Fall", 160, 75),
    HAZELNUT(FoodType.HAZELNUT, "Fall", 40, 38),
    PURPLE_MUSHROOM(FoodType.PURPLE_MUSHROOM, "Fall", 90, 30),
    WILD_PLUM(FoodType.WILD_PLUM, "Fall", 80, 25),
    CROCUS(FoodType.CROCUS, "Winter", 60, 0),
    CRYSTAL_FRUIT(FoodType.CRYSTAL_FRUIT, "Winter", 150, 63),
    HOLLY(FoodType.HOLLY, "Winter", 80, -37),
    SNOW_YAM(FoodType.SNOW_YAM, "Winter", 100, 30),
    WINTER_ROOT(FoodType.WINTER_ROOT, "Winter", 70, 25);

    public final FoodType cropItem;
    public final String season;
    public final int baseSellPrice;
    public final int energy;

    ForagingCropType(FoodType name, String season, int baseSellPrice, int energy) {
        this.cropItem = name;
        this.season = season;
        this.baseSellPrice = baseSellPrice;
        this.energy = energy;
    }
}
