package io.src.model.Enums.GameObjects;

import io.src.model.Enums.Items.FoodType;
import io.src.model.Enums.Items.FruitType;
import io.src.model.Enums.WeatherAndTime.Seasons;
import org.jetbrains.annotations.Nullable;

public enum ForagingCropType {
    COMMON_MUSHROOM(FoodType.COMMON_MUSHROOM, new Seasons[]{Seasons.Summer, Seasons.Fall, Seasons.Spring, Seasons.Winter}, 40, 38,""),
    DAFFODIL(FoodType.DAFFODIL, new Seasons[]{Seasons.Spring}, 30, 0,"Daffodil1"),
    DANDELION(FoodType.DANDELION, new Seasons[]{Seasons.Spring}, 40, 25,"Dandelion1"),
    LEEK(FoodType.LEEK, new Seasons[]{Seasons.Spring}, 60, 40,"Leek1"),
    MOREL(FoodType.MOREL, new Seasons[]{Seasons.Spring}, 150, 20,"Morel1"),
    SALMONBERRY(FoodType.SALMON_BERRY, new Seasons[]{Seasons.Spring}, 5, 25,"Salmonberry"),
    SPRING_ONION(FoodType.SPRING_ONION, new Seasons[]{Seasons.Spring}, 8, 13,"Vanity_Narrow_Short_Bush_Spring"),
    WILD_HORSERADISH(FoodType.WILD_HORSERADISH, new Seasons[]{Seasons.Spring}, 50, 13,"Wild_Horseradish1"),
    FIDDLEHEAD_FERN(FoodType.FIDDLE_HEAD_FERN, new Seasons[]{Seasons.Summer}, 90, 25,"Fiddlehead_Fern"),
    GRAPE(FoodType.GRAPE, new Seasons[]{Seasons.Summer}, 80, 38,"Grape2"),
    RED_MUSHROOM(FoodType.RED_MUSHROOM, new Seasons[]{Seasons.Summer}, 75, -50,"Red_Mushroom1"),
    SPICE_BERRY(FoodType.SPICE_BERRY, new Seasons[]{Seasons.Summer}, 80, 25,"Spice_Berry1"),
    SWEET_PEA(FoodType.SWEET_PEA, new Seasons[]{Seasons.Summer}, 50, 0,"Sweet_Pea1"),
    BLACKBERRY(FoodType.BLACKBERRY, new Seasons[]{Seasons.Fall}, 25, 25,"Blackberry"),
    CHANTERELLE(FoodType.CHANTERELLE, new Seasons[]{Seasons.Fall}, 160, 75,"Chanterelle1"),
    HAZELNUT(FoodType.HAZELNUT, new Seasons[]{Seasons.Fall}, 40, 38,"Hazelnut1"),
    PURPLE_MUSHROOM(FoodType.PURPLE_MUSHROOM, new Seasons[]{Seasons.Fall}, 90, 30,"Purple_Mushroom1"),
    WILD_PLUM(FoodType.WILD_PLUM, new Seasons[]{Seasons.Fall}, 80, 25,"Wild_Plum1"),
    CROCUS(FoodType.CROCUS, new Seasons[]{Seasons.Winter}, 60, 0,"Crocus1"),
    CRYSTAL_FRUIT(FoodType.CRYSTAL_FRUIT, new Seasons[]{Seasons.Winter}, 150, 63,"Crystal_Fruit1"),
    HOLLY(FoodType.HOLLY, new Seasons[]{Seasons.Winter}, 80, -37,"Holly"),
    SNOW_YAM(FoodType.SNOW_YAM, new Seasons[]{Seasons.Winter}, 100, 30,"Snow_Yam1"),
    WINTER_ROOT(FoodType.WINTER_ROOT, new Seasons[]{Seasons.Winter}, 70, 25,"Winter_Root1");

    public final FoodType cropItem;
    public final Seasons[] season;
    public final int baseSellPrice;
    public final int energy;
    private final String assetName;

    ForagingCropType(FoodType name, Seasons[] season, int baseSellPrice, int energy,String assetName) {
        this.cropItem = name;
        this.season = season;
        this.baseSellPrice = baseSellPrice;
        this.energy = energy;
        this.assetName = assetName;
    }




    public static ForagingCropType fromName(String name) {
        for (ForagingCropType type : ForagingCropType.values()) {
            if (type.cropItem.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

    @Nullable
    public String getAssetName() {
        return switch (assetName) {
            case "null" -> null;
            case "" -> cropItem.getAssetName().replace(" ", "_");
            default -> assetName;
        };
    }



}
