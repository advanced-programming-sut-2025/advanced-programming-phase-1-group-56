package model.Enums.GameObjects;


import model.Enums.Items.FoodType;
import model.Enums.WeatherAndTime.Seasons;

public enum CropType implements initialPlant {
    BLUE_JAZZ(FoodType.BLUE_JAZZ, new int[]{1,2,2,2}, 7, true, -1, 50, true, 45, 20, new Seasons[]{Seasons.Spring}, false),
    CARROT(FoodType.CARROT, new int[]{1,1,1}, 3, true, -1, 35, true, 75, 33, new Seasons[]{Seasons.Spring}, false),
    CAULIFLOWER(FoodType.CAULIFLOWER, new int[]{1,2,4,4,1}, 12, true, -1, 175, true, 75, 33, new Seasons[]{Seasons.Spring}, true),
    COFFEE_BEAN(FoodType.COFFEE_BEAN, new int[]{1,2,2,3,2}, 10, false, 2, 15, false, -1, -1, new Seasons[]{Seasons.Spring, Seasons.Summer}, false),
    GARLIC(FoodType.GARLIC, new int[]{1,1,1,1}, 4, true, -1, 60, true, 20, 9, new Seasons[]{Seasons.Spring}, false),
    GREEN_BEAN(FoodType.GREEN_BEAN, new int[]{1,1,1,3,4}, 10, false, 3, 40, true, 25, 11, new Seasons[]{Seasons.Spring}, false),
    KALE(FoodType.KALE, new int[]{1,2,2,1}, 6, true, -1, 110, true, 50, 22, new Seasons[]{Seasons.Spring}, false),
    PARSNIP(FoodType.PARSNIP, new int[]{1,1,1,1}, 4, true, -1, 35, true, 25, 11, new Seasons[]{Seasons.Spring}, false),
    POTATO(FoodType.POTATO, new int[]{1,1,1,2,1}, 6, true, -1, 80, true, 25, 11, new Seasons[]{Seasons.Spring}, false),
    RHUBARB(FoodType.RHUBARB, new int[]{2,2,2,3,4}, 13, true, -1, 220, false, -1, -1, new Seasons[]{Seasons.Spring}, false),
    STRAWBERRY(FoodType.STRAWBERRY, new int[]{1,1,2,2,2}, 8, false, 4, 120, true, 50, 22, new Seasons[]{Seasons.Spring}, false),
    TULIP(FoodType.TULIP, new int[]{1,1,2,2}, 6, true, -1, 30, true, 45, 20, new Seasons[]{Seasons.Spring}, false),
    UNMILLED_RICE(FoodType.UNMILLED_RICE, new int[]{1,2,2,3}, 8, true, -1, 30, true, 3, 1, new Seasons[]{Seasons.Spring}, false),
    BLUEBERRY(FoodType.BLUEBERRY, new int[]{1,3,3,4,2}, 13, false, 4, 50, true, 25, 11, new Seasons[]{Seasons.Summer}, false),
    CORN(FoodType.CORN, new int[]{2,3,3,3,3}, 14, false, 4, 50, true, 25, 11, new Seasons[]{Seasons.Summer, Seasons.Fall}, false),
    HOPS(FoodType.HOPS, new int[]{1,1,2,3,4}, 11, false, 1, 25, true, 45, 20, new Seasons[]{Seasons.Summer}, false),
    HOT_PEPPER(FoodType.HOT_PEPPER, new int[]{1,1,1,1,1}, 5, false, 3, 40, true, 13, 5, new Seasons[]{Seasons.Summer}, false),
    MELON(FoodType.MELON, new int[]{1,2,3,3,3}, 12, true, -1, 250, true, 113, 50, new Seasons[]{Seasons.Summer}, true),
    POPPY(FoodType.POPPY, new int[]{1,2,2,2}, 7, true, -1, 140, true, 45, 20, new Seasons[]{Seasons.Summer}, false),
    RADISH(FoodType.RADISH, new int[]{2,1,2,1}, 6, true, -1, 90, true, 45, 20, new Seasons[]{Seasons.Summer}, false),
    RED_CABBAGE(FoodType.RED_CABBAGE, new int[]{2,1,2,2,2}, 9, true, -1, 260, true, 75, 33, new Seasons[]{Seasons.Summer}, false),
    STARFRUIT(FoodType.STARFRUIT, new int[]{2,3,2,3,3}, 13, true, -1, 750, true, 125, 56, new Seasons[]{Seasons.Summer}, false),
    SUMMER_SPANGLE(FoodType.SUMMER_SPANGLE, new int[]{1,2,3,1}, 8, true, -1, 90, true, 45, 20, new Seasons[]{Seasons.Summer}, false),
    SUMMER_SQUASH(FoodType.SUMMER_SQUASH, new int[]{1,1,1,2,1}, 6, false, 3, 45, true, 63, 28, new Seasons[]{Seasons.Summer}, false),
    SUNFLOWER(FoodType.SUNFLOWER, new int[]{1,2,3,2}, 8, true, -1, 80, true, 45, 20, new Seasons[]{Seasons.Summer, Seasons.Fall}, false),
    TOMATO(FoodType.TOMATO, new int[]{2,2,2,2,3}, 11, false, 4, 60, true, 20, 9, new Seasons[]{Seasons.Summer}, false),
    WHEAT(FoodType.WHEAT, new int[]{1,1,1,1}, 4, true, -1, 25, false, -1, -1, new Seasons[]{Seasons.Summer, Seasons.Fall}, false),
    AMARANTH(FoodType.AMARANTH, new int[]{1,2,2,2}, 7, true, -1, 150, true, 50, 22, new Seasons[]{Seasons.Fall}, false),
    ARTICHOKE(FoodType.ARTICHOKE, new int[]{2,2,1,2,1}, 8, true, -1, 160, true, 30, 13, new Seasons[]{Seasons.Fall}, false),
    BEET(FoodType.BEET, new int[]{1,1,2,2}, 6, true, -1, 100, true, 30, 13, new Seasons[]{Seasons.Fall}, false),
    BOK_CHOY(FoodType.BOK_CHOY, new int[]{1,1,1,1}, 4, true, -1, 80, true, 25, 11, new Seasons[]{Seasons.Fall}, false),
    BROCCOLI(FoodType.BROCCOLI, new int[]{2,2,2,2}, 8, false, 4, 70, true, 63, 28, new Seasons[]{Seasons.Fall}, false),
    CRANBERRIES(FoodType.CRANBERRIES, new int[]{1,2,1,1,2}, 7, false, 5, 75, true, 38, 17, new Seasons[]{Seasons.Fall}, false),
    EGGPLANT(FoodType.EGGPLANT, new int[]{1,1,1,1}, 5, false, 5, 60, true, 20, 9, new Seasons[]{Seasons.Fall}, false),
    FAIRY_ROSE(FoodType.FAIRY_ROSE, new int[]{1,4,4,3}, 12, true, -1, 290, true, 45, 20, new Seasons[]{Seasons.Fall}, false),
    GRAPE(FoodType.GRAPE, new int[]{1,1,2,3,3}, 10, false, 3, 80, true, 38, 17, new Seasons[]{Seasons.Fall}, false),
    PUMPKIN(FoodType.PUMPKIN, new int[]{1,2,3,4,3}, 13, true, -1, 320, false, -1, -1, new Seasons[]{Seasons.Fall}, true),
    YAM(FoodType.YAM, new int[]{1,3,3,3}, 10, true, -1, 160, true, 45, 20, new Seasons[]{Seasons.Fall}, false),
    SWEET_GEM_BERRY(FoodType.SWEET_GEM_BERRY, new int[]{2,4,6,6,6}, 24, true, -1, 3000, false, -1, -1, new Seasons[]{Seasons.Fall}, false),
    POWDERMELON(FoodType.POWDER_MELON, new int[]{1,2,1,2,1}, 7, true, -1, 60, true, 63, 28, new Seasons[]{Seasons.Winter}, true),
    ANCIENT_FRUIT(FoodType.ANCIENT_FRUIT, new int[]{2,7,7,7,5}, 28, false, 7, 550, false, -1, -1, new Seasons[]{Seasons.Summer, Seasons.Fall, Seasons.Spring}, false);

    public final int[] stages;
    public final int totalHarvestTime;
    public final boolean oneTime;
    public final int regrowthTime;
    public final int baseSellPrice;
    public final boolean isEdible;
    public final int energy;
    public final int health;
    public final Seasons[] season;
    public final boolean canBecomeGiant;
    public final FoodType cropItem;
    public final String icon;
    CropType(FoodType cropItem, int[] stages, int totalHarvestTime, boolean oneTime, int regrowthTime,
             int baseSellPrice, boolean isEdible, int energy, int health, Seasons[] season, boolean canBecomeGiant) {
        this.cropItem = cropItem;
        this.stages = stages;
        this.totalHarvestTime = totalHarvestTime;
        this.oneTime = oneTime;
        this.regrowthTime = regrowthTime;
        this.baseSellPrice = baseSellPrice;
        this.isEdible = isEdible;
        this.energy = energy;
        this.health = health;
        this.season = season;
        this.canBecomeGiant = canBecomeGiant;
        this.icon = "\uD83C\uDF31";
    }
    public String getIcon(){
        return icon;
    }
}
