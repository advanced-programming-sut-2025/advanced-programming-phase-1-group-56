package model.Enums.Registery;


import model.Enums.WeatherAndTime.Seasons;

public enum CropType {
    BLUE_JAZZ("Jazz Seeds", "1-2-2-2", 7, true, -1, 50, true, 45, 20, new Seasons[]{Seasons.Spring}, false),
    CARROT("Carrot Seeds", "1-1-1", 3, true, -1, 35, true, 75, 33, new Seasons[]{Seasons.Spring}, false),
    CAULIFLOWER("Cauliflower Seeds", "1-2-4-4-1", 12, true, -1, 175, true, 75, 33, new Seasons[]{Seasons.Spring}, true),
    COFFEE_BEAN("Coffee Bean", "1-2-2-3-2", 10, false, 2, 15, false, -1, -1, new Seasons[]{Seasons.Spring, Seasons.Summer}, false),
    GARLIC("Garlic Seeds", "1-1-1-1", 4, true, -1, 60, true, 20, 9, new Seasons[]{Seasons.Spring}, false),
    GREEN_BEAN("Bean Starter", "1-1-1-3-4", 10, false, 3, 40, true, 25, 11, new Seasons[]{Seasons.Spring}, false),
    KALE("Kale Seeds", "1-2-2-1", 6, true, -1, 110, true, 50, 22, new Seasons[]{Seasons.Spring}, false),
    PARSNIP("Parsnip Seeds", "1-1-1-1", 4, true, -1, 35, true, 25, 11, new Seasons[]{Seasons.Spring}, false),
    POTATO("Potato Seeds", "1-1-1-2-1", 6, true, -1, 80, true, 25, 11, new Seasons[]{Seasons.Spring}, false),
    RHUBARB("Rhubarb Seeds", "2-2-2-3-4", 13, true, -1, 220, false, -1, -1, new Seasons[]{Seasons.Spring}, false),
    STRAWBERRY("Strawberry Seeds", "1-1-2-2-2", 8, false, 4, 120, true, 50, 22, new Seasons[]{Seasons.Spring}, false),
    TULIP("Tulip Bulb", "1-1-2-2", 6, true, -1, 30, true, 45, 20, new Seasons[]{Seasons.Spring}, false),
    UNMILLED_RICE("Rice Shoot", "1-2-2-3", 8, true, -1, 30, true, 3, 1, new Seasons[]{Seasons.Spring}, false),
    BLUEBERRY("Blueberry Seeds", "1-3-3-4-2", 13, false, 4, 50, true, 25, 11, new Seasons[]{Seasons.Summer}, false),
    CORN("Corn Seeds", "2-3-3-3-3", 14, false, 4, 50, true, 25, 11, new Seasons[]{Seasons.Summer,Seasons.Fall}, false),
    HOPS("Hops Starter", "1-1-2-3-4", 11, false, 1, 25, true, 45, 20, new Seasons[]{Seasons.Summer}, false),
    HOT_PEPPER("Pepper Seeds", "1-1-1-1-1", 5, false, 3, 40, true, 13, 5, new Seasons[]{Seasons.Summer}, false),
    MELON("Melon Seeds", "1-2-3-3-3", 12, true, -1, 250, true, 113, 50, new Seasons[]{Seasons.Summer}, true),
    POPPY("Poppy Seeds", "1-2-2-2", 7, true, -1, 140, true, 45, 20, new Seasons[]{Seasons.Summer}, false),
    RADISH("Radish Seeds", "2-1-2-1", 6, true, -1, 90, true, 45, 20, new Seasons[]{Seasons.Summer}, false),
    RED_CABBAGE("Red Cabbage Seeds", "2-1-2-2-2", 9, true, -1, 260, true, 75, 33, new Seasons[]{Seasons.Summer}, false),
    STARFRUIT("Starfruit Seeds", "2-3-2-3-3", 13, true, -1, 750, true, 125, 56, new Seasons[]{Seasons.Summer}, false),
    SUMMER_SPANGLE("Spangle Seeds", "1-2-3-1", 8, true, -1, 90, true, 45, 20, new Seasons[]{Seasons.Summer}, false),
    SUMMER_SQUASH("Summer Squash Seeds", "1-1-1-2-1", 6, false, 3, 45, true, 63, 28, new Seasons[]{Seasons.Summer}, false),
    SUNFLOWER("Sunflower Seeds", "1-2-3-2", 8, true, -1, 80, true, 45, 20,new Seasons[]{Seasons.Summer,Seasons.Fall} , false),
    TOMATO("Tomato Seeds", "2-2-2-2-3", 11, false, 4, 60, true, 20, 9, new Seasons[]{Seasons.Summer}, false),
    WHEAT("Wheat Seeds", "1-1-1-1", 4, true, -1, 25, false, -1, -1, new Seasons[]{Seasons.Summer,Seasons.Fall}, false),
    AMARANTH("Amaranth Seeds", "1-2-2-2", 7, true, -1, 150, true, 50, 22, new Seasons[]{Seasons.Fall}, false),
    ARTICHOKE("Artichoke Seeds", "2-2-1-2-1", 8, true, -1, 160, true, 30, 13, new Seasons[]{Seasons.Fall}, false),
    BEET("Beet Seeds", "1-1-2-2", 6, true, -1, 100, true, 30, 13, new Seasons[]{Seasons.Fall}, false),
    BOK_CHOY("Bok Choy Seeds", "1-1-1-1", 4, true, -1, 80, true, 25, 11, new Seasons[]{Seasons.Fall}, false),
    BROCCOLI("Broccoli Seeds", "2-2-2-2", 8, false, 4, 70, true, 63, 28, new Seasons[]{Seasons.Fall}, false),
    CRANBERRIES("Cranberry Seeds", "1-2-1-1-2", 7, false, 5, 75, true, 38, 17, new Seasons[]{Seasons.Fall}, false),
    EGGPLANT("Eggplant Seeds", "1-1-1-1", 5, false, 5, 60, true, 20, 9, new Seasons[]{Seasons.Fall}, false),
    FAIRY_ROSE("Fairy Seeds", "1-4-4-3", 12, true, -1, 290, true, 45, 20, new Seasons[]{Seasons.Fall}, false),
    GRAPE("Grape Starter", "1-1-2-3-3", 10, false, 3, 80, true, 38, 17, new Seasons[]{Seasons.Fall}, false),
    PUMPKIN("Pumpkin Seeds", "1-2-3-4-3", 13, true, -1, 320, false, -1, -1, new Seasons[]{Seasons.Fall}, true),
    YAM("Yam Seeds", "1-3-3-3", 10, true, -1, 160, true, 45, 20, new Seasons[]{Seasons.Fall}, false),
    SWEET_GEM_BERRY("Rare Seed", "2-4-6-6-6", 24, true, -1, 3000, false, -1, -1, new Seasons[]{Seasons.Fall}, false),
    POWDERMELON("Powdermelon Seeds", "1-2-1-2-1", 7, true, -1, 60, true, 63, 28, new Seasons[]{Seasons.Winter}, true),
    ANCIENT_FRUIT("Ancient Seeds", "2-7-7-7-5", 28, false, 7, 550, false, -1, -1, new Seasons[]{Seasons.Summer,Seasons.Fall,Seasons.Spring}, false);

    public final String source;
    public final String stages;
    public final int totalHarvestTime;
    public final boolean oneTime;
    public final int regrowthTime;
    public final int baseSellPrice;
    public final boolean isEdible;
    public final int energy;
    public final int health;
    public final Seasons[] season;
    public final boolean canBecomeGiant;

    CropType(String source, String stages, int totalHarvestTime, boolean oneTime, int regrowthTime,
         int baseSellPrice, boolean isEdible, int energy, int health, Seasons[] season, boolean canBecomeGiant) {
        this.source = source;
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
    }
}
