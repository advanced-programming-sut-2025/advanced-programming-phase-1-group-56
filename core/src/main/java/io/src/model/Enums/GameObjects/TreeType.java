package io.src.model.Enums.GameObjects;

import io.src.model.App;
import io.src.model.Enums.Items.*;
import io.src.model.Enums.WeatherAndTime.Seasons;
import io.src.model.items.Saleable;
import org.jetbrains.annotations.Nullable;

public enum TreeType implements Saleable, initialPlant {
    TREE_BARK("Tree Bark", null, -1, EtcType.WOOD, -1, -1, -1, false, -1, null, "Wood"),
    BURNT_TREE("Burnt Tree", null, -1, MineralItemType.COAL_ORE, -1, -1, -1, false, -1, null, ""),
    //TODO tree_stage_0 == burnt tree handle this fucking shit
    NORMAL_TREE("Normal Tree", null, -1, EtcType.WOOD, -1, 0, -1, false, -1, null, "Apple"),
    //TODO idk wtf is exactly the normal tree
    //source
    APRICOT_TREE("Apricot Tree", SeedType.APRICOT_SAPLING, 7, FruitType.APRICOT, 28, 1, 59, true, 38, new Seasons[]{Seasons.Spring}, "Apricot"),
    CHERRY_TREE("Cherry Tree", SeedType.CHERRY_SAPLING, 7, FruitType.CHERRY, 28, 1, 80, true, 38, new Seasons[]{Seasons.Spring}, "Cherry"),
    BANANA_TREE("Banana Tree", SeedType.BANANA_SAPLING, 7, FruitType.BANANA, 28, 1, 150, true, 75, new Seasons[]{Seasons.Summer}, "Banana"),
    MANGO_TREE("Mango Tree", SeedType.MANGO_SAPLING, 7, FruitType.MANGO, 28, 1, 130, true, 100, new Seasons[]{Seasons.Summer}, "Mango"),
    ORANGE_TREE("Orange Tree", SeedType.ORANGE_SAPLING, 7, FruitType.ORANGE, 28, 1, 100, true, 38, new Seasons[]{Seasons.Summer}, "Orange"),
    PEACH_TREE("Peach Tree", SeedType.PEACH_SAPLING, 7, FruitType.PEACH, 28, 1, 140, true, 38, new Seasons[]{Seasons.Summer}, "Peach"),
    APPLE_TREE("Apple Tree", SeedType.APPLE_SAPLING, 7, FruitType.APPLE, 28, 1, 100, true, 38, new Seasons[]{Seasons.Fall}, "Apple"),
    POMEGRANATE_TREE("Pomegranate Tree", SeedType.POMEGRANATE_SAPLING, 7, FruitType.POMEGRANATE, 28, 1, 100, true, 38, new Seasons[]{Seasons.Fall}, "Pomegranate"),
    OAK_TREE("Oak Tree", SeedType.OAK_SEED, 7, FruitType.OAK_RESIN, 28, 7, 150, false, 0, Seasons.values(), "Oak"),
    MAPLE_TREE("Maple Tree", SeedType.MAPLE_SEED, 7, FruitType.MAPLE_SYRUP, 28, 9, 200, false, 0, Seasons.values(), "Maple"),
    PINE_TREE("Pine Tree", SeedType.PINE_SEED, 7, FruitType.PINE_TAR, 28, 5, 100, false, 0, Seasons.values(), "Pine"),
    MAHOGANY_TREE("Mahogany Tree", SeedType.MAHOGANY_SEED, 7, FruitType.SAP, 28, 1, 2, true, -2, Seasons.values(), "Mahogany"),
    MUSHROOM_TREE("Mushroom Tree", SeedType.MUSHROOM_SEED, 7, FruitType.COMMON_MUSHROOM, 28, 1, 40, true, 38, Seasons.values(), "MushroomTree"),
    MYSTIC_TREE("Mystic Tree", SeedType.MYSTIC_SEED, 7, FruitType.MYSTIC_SYRUP, 28, 7, 1000, true, 500, Seasons.values(), "Mystic_Tree");

    //TODO assets should be handled in code it should be gotten by
    public final String name;
    public final SeedType source;
    public final int stages;
    public final int totalHarvestTime;
    public final ItemType fruit;
    public final int fruitHarvestCycle;
    public final int fruitBaseSellPrice;
    public final boolean isFruitEdible;
    public final int fruitEnergy;
    public final Seasons[] season;
    private final String assetName;

    TreeType(String name, SeedType source, int stages, ItemType fruit, int totalHarvestTime, int fruitHarvestCycle,
             int fruitBaseSellPrice, boolean isFruitEdible, int fruitEnergy, Seasons[] season, String assetName) {
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
        this.assetName = assetName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Nullable
    public String getAssetName() {
        return switch (assetName) {
            case "null" -> null;
            case "" -> name.replace(" ", "_");
            default -> assetName;
        };
    }

    public String getAssetNameByStageAndSeason(int stage) {
        stage++;
        Seasons s = App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getSeason();
        if (this == TreeType.TREE_BARK) {
            return "Wood";
        }
        if (stage < 0 || stage > 5) {
            return null;
        } else if (stage == 5)
            return getAssetName() + "_Stage_5_0" + (s.ordinal()+1);
        else
            return getAssetName() + "_Stage_" + stage;
    }


    public static TreeType fromName(String name) {
        for (TreeType type : TreeType.values()) {
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}
