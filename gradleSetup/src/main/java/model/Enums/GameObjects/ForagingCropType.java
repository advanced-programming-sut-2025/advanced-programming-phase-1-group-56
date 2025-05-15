package model.Enums.GameObjects;

import model.Enums.Items.FoodType;
import model.Enums.Items.FruitType;
import model.Enums.Items.ItemType;
import model.Enums.WeatherAndTime.Seasons;

public enum ForagingCropType {
    COMMON_MUSHROOM(FoodType.COMMON_MUSHROOM, new Seasons[]{Seasons.Summer, Seasons.Fall, Seasons.Spring, Seasons.Winter}, 40, 38),
    DAFFODIL(FoodType.DAFFODIL, new Seasons[]{Seasons.Spring}, 30, 0),
    DANDELION(FoodType.DANDELION, new Seasons[]{Seasons.Spring}, 40, 25),
    LEEK(FoodType.LEEK, new Seasons[]{Seasons.Spring}, 60, 40),
    MOREL(FoodType.MOREL, new Seasons[]{Seasons.Spring}, 150, 20),
    SALMONBERRY(FoodType.SALMON_BERRY, new Seasons[]{Seasons.Spring}, 5, 25),
    SPRING_ONION(FoodType.SPRING_ONION, new Seasons[]{Seasons.Spring}, 8, 13),
    WILD_HORSERADISH(FoodType.WILD_HORSERADISH, new Seasons[]{Seasons.Spring}, 50, 13),
    FIDDLEHEAD_FERN(FoodType.FIDDLE_HEAD_FERN, new Seasons[]{Seasons.Summer}, 90, 25),
    GRAPE(FoodType.GRAPE, new Seasons[]{Seasons.Summer}, 80, 38),
    RED_MUSHROOM(FoodType.RED_MUSHROOM, new Seasons[]{Seasons.Summer}, 75, -50),
    SPICE_BERRY(FoodType.SPICE_BERRY, new Seasons[]{Seasons.Summer}, 80, 25),
    SWEET_PEA(FoodType.SWEET_PEA, new Seasons[]{Seasons.Summer}, 50, 0),
    BLACKBERRY(FoodType.BLACKBERRY, new Seasons[]{Seasons.Fall}, 25, 25),
    CHANTERELLE(FoodType.CHANTERELLE, new Seasons[]{Seasons.Fall}, 160, 75),
    HAZELNUT(FoodType.HAZELNUT, new Seasons[]{Seasons.Fall}, 40, 38),
    PURPLE_MUSHROOM(FoodType.PURPLE_MUSHROOM, new Seasons[]{Seasons.Fall}, 90, 30),
    WILD_PLUM(FoodType.WILD_PLUM, new Seasons[]{Seasons.Fall}, 80, 25),
    CROCUS(FoodType.CROCUS, new Seasons[]{Seasons.Winter}, 60, 0),
    CRYSTAL_FRUIT(FoodType.CRYSTAL_FRUIT, new Seasons[]{Seasons.Winter}, 150, 63),
    HOLLY(FoodType.HOLLY, new Seasons[]{Seasons.Winter}, 80, -37),
    SNOW_YAM(FoodType.SNOW_YAM, new Seasons[]{Seasons.Winter}, 100, 30),
    WINTER_ROOT(FoodType.WINTER_ROOT, new Seasons[]{Seasons.Winter}, 70, 25);

    public final FoodType cropItem;
    public final Seasons[] season;
    public final int baseSellPrice;
    public final int energy;

    ForagingCropType(FoodType name, Seasons[] season, int baseSellPrice, int energy) {
        this.cropItem = name;
        this.season = season;
        this.baseSellPrice = baseSellPrice;
        this.energy = energy;
    }

}
