package model.Enums.GameObjects;

import model.Enums.Items.EtcType;
import model.Enums.Items.FruitType;
import model.Enums.Items.ItemType;
import model.Enums.Items.MineralItemType;
import model.Enums.WeatherAndTime.Seasons;
import model.items.Saleable;

public enum TreeType implements Saleable , initialPlant {
    TREE_BARK("Tree Bark", "nothing", -1, EtcType.WOOD, -1, -1, -1, false, -1, null , "\uD83E\uDEB5"),
    BURNT_TREE("Burnt Tree", "nothing", -1, MineralItemType.COAL, -1, -1, -1, false, -1, null  , "\uD83D\uDD25"),
    NORMAL_TREE("Normal Tree", "nothing", -1, EtcType.WOOD, -1, 0, -1, false, -1, null , "\uD83C\uDF33"),
    // source
    APRICOT_TREE("Apricot Tree", "Apricot Sapling", 7, FruitType.APRICOT, 28, 1, 59, true, 38, new Seasons[]{Seasons.Spring} , "\uD83C\uDF33"),
    CHERRY_TREE("Cherry Tree", "Cherry Sapling", 7, FruitType.CHERRY, 28, 1, 80, true, 38, new Seasons[]{Seasons.Spring} , "\uD83C\uDF33"),
    BANANA_TREE("Banana Tree", "Banana Sapling", 7, FruitType.BANANA, 28, 1, 150, true, 75, new Seasons[]{Seasons.Summer} , "\uD83C\uDF33"),
    MANGO_TREE("Mango Tree", "Mango Sapling", 7, FruitType.MANGO, 28, 1, 130, true, 100, new Seasons[]{Seasons.Summer} , "\uD83C\uDF33"),
    ORANGE_TREE("Orange Tree", "Orange Sapling", 7, FruitType.ORANGE, 28, 1, 100, true, 38, new Seasons[]{Seasons.Summer} , "\uD83C\uDF33"),
    PEACH_TREE("Peach Tree", "Peach Sapling", 7, FruitType.PEACH, 28, 1, 140, true, 38, new Seasons[]{Seasons.Summer} , "\uD83C\uDF33"),
    APPLE_TREE("Apple Tree", "Apple Sapling", 7, FruitType.APPLE, 28, 1, 100, true, 38, new Seasons[]{Seasons.Fall} , "\uD83C\uDF33"),
    POMEGRANATE_TREE("Pomegranate Tree", "Pomegranate Sapling", 7, FruitType.POMEGRANATE, 28, 1, 100, true, 38, new Seasons[]{Seasons.Fall} , "\uD83C\uDF33"),
    OAK_TREE("Oak Tree", "Acorns", 7, FruitType.OAK_RESIN, 28, 7, 150, false, 0, Seasons.values() , "\uD83C\uDF33"),
    MAPLE_TREE("Maple Tree", "Maple Seeds", 7, FruitType.MAPLE_SYRUP, 28, 9, 200, false, 0, Seasons.values() , "\uD83C\uDF33"),
    PINE_TREE("Pine Tree", "Pine Cones", 7, FruitType.PINE_TAR, 28, 5, 100, false, 0, Seasons.values() , "\uD83C\uDF33"),
    MAHOGANY_TREE("Mahogany Tree", "Mahogany Seeds", 7, FruitType.SAP, 28, 1, 2, true, -2, Seasons.values() , "\uD83C\uDF33"),
    MUSHROOM_TREE("Mushroom Tree", "Mushroom Tree Seeds", 7, FruitType.COMMON_MUSHROOM, 28, 1, 40, true, 38, Seasons.values() , "\uD83C\uDF33"),
    MYSTIC_TREE("Mystic Tree", "Mystic Tree Seeds", 7, FruitType.MYSTIC_SYRUP, 28, 7, 1000, true, 500, Seasons.values() , "\uD83C\uDF33");


    public final String name;
    public final String source;
    public final int stages;
    public final int totalHarvestTime;
    public final ItemType fruit;
    public final int fruitHarvestCycle;
    public final int fruitBaseSellPrice;
    public final boolean isFruitEdible;
    public final int fruitEnergy;
    public final Seasons[] season;
    public final String icon;

    TreeType(String name, String source, int stages, ItemType fruit, int totalHarvestTime, int fruitHarvestCycle,
             int fruitBaseSellPrice, boolean isFruitEdible, int fruitEnergy, Seasons[] season , String icon) {
        this.name = name;
        this.source = source;
        this.stages = stages;
        this.fruit = fruit;
        this.totalHarvestTime = totalHarvestTime;
        this.fruitHarvestCycle = fruitHarvestCycle;
        this.fruitBaseSellPrice = fruitBaseSellPrice;
        this.isFruitEdible = isFruitEdible;
        this.fruitEnergy = fruitEnergy;
        this.season = season;
        this.icon = icon;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getIcon(){
        return icon;
    }
}
