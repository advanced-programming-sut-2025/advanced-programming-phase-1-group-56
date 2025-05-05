package model.Enums.Registery;

import model.Enums.WeatherAndTime.Seasons;

public enum TreeType {
    APRICOT("Apricot Tree", "Apricot Sapling", 28, "Apricot", 1, 59, true, 38, 17, new Seasons[]{Seasons.Spring}),
    CHERRY("Cherry Tree", "Cherry Sapling", 28, "Cherry", 1, 80, true, 38, 17, new Seasons[]{Seasons.Spring}),
    BANANA("Banana Tree", "Banana Sapling", 28, "Banana", 1, 150, true, 75, 33, new Seasons[]{Seasons.Summer}),
    MANGO("Mango Tree", "Mango Sapling", 28, "Mango", 1, 130, true, 100, 45, new Seasons[]{Seasons.Summer}),
    ORANGE("Orange Tree", "Orange Sapling", 28, "Orange", 1, 100, true, 38, 17, new Seasons[]{Seasons.Summer}),
    PEACH("Peach Tree", "Peach Sapling", 28, "Peach", 1, 140, true, 38, 17, new Seasons[]{Seasons.Summer}),
    APPLE("Apple Tree", "Apple Sapling", 28, "Apple", 1, 100, true, 38, 17, new Seasons[]{Seasons.Fall}),
    POMEGRANATE("Pomegranate Tree", "Pomegranate Sapling", 28, "Pomegranate", 1, 140, true, 38, 17, new Seasons[]{Seasons.Fall}),
    OAK("Oak Tree", "Acorns", 28, "Oak Resin", 7, 150, false, 0, 0, new Seasons[]{Seasons.Fall,Seasons.Spring,Seasons.Winter,Seasons.Summer}),
    MAPLE("Maple Tree", "Maple Seeds", 28, "Maple Syrup", 9, 200, false, 0, 0, new Seasons[]{Seasons.Fall,Seasons.Spring,Seasons.Winter,Seasons.Summer}),
    PINE("Pine Tree", "Pine Cones", 28, "Pine Tar", 5, 100, false, 0, 0, new Seasons[]{Seasons.Fall,Seasons.Spring,Seasons.Winter,Seasons.Summer}),
    MAHOGANY("Mahogany Tree", "Mahogany Seeds", 28, "Sap", 1, 2, true, -2, 0, new Seasons[]{Seasons.Fall,Seasons.Spring,Seasons.Winter,Seasons.Summer}),
    MUSHROOM("Mushroom Tree", "Mushroom Tree Seeds", 28, "Common Mushroom", 1, 40, true, 38, 17, new Seasons[]{Seasons.Fall,Seasons.Spring,Seasons.Winter,Seasons.Summer}),
    MYSTIC("Mystic Tree", "Mystic Tree Seeds", 28, "Mystic Syrup", 7, 1000, true, 500, 225, new Seasons[]{Seasons.Fall,Seasons.Spring,Seasons.Winter,Seasons.Summer});

    public final String name;
    public final String source;
    public final int totalHarvestTime;
    public final String fruit;
    public final int fruitHarvestCycle;
    public final int fruitBaseSellPrice;
    public final boolean isFruitEdible;
    public final int fruitEnergy;
    public final int fruitHealth;
    public final Seasons[] season;

    TreeType(String name, String source, int totalHarvestTime, String fruit, int fruitHarvestCycle,
             int fruitBaseSellPrice, boolean isFruitEdible, int fruitEnergy, int fruitHealth, Seasons[] season) {
        this.name = name;
        this.source = source;
        this.totalHarvestTime = totalHarvestTime;
        this.fruit = fruit;
        this.fruitHarvestCycle = fruitHarvestCycle;
        this.fruitBaseSellPrice = fruitBaseSellPrice;
        this.isFruitEdible = isFruitEdible;
        this.fruitEnergy = fruitEnergy;
        this.fruitHealth = fruitHealth;
        this.season = season;
    }
}
