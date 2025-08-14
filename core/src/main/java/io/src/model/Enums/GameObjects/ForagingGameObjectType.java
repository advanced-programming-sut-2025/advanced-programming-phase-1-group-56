package io.src.model.Enums.GameObjects;
import io.src.model.Enums.Items.MineralItemType;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public enum ForagingGameObjectType {
    Stone_Boulder(MineralItemType.STONE, 1, 100, "small_rock2"),
    Copper_Boulder(MineralItemType.COPPER_ORE, 1, 80, "Copper_Node"),
    Iron_Boulder(MineralItemType.IRON_ORE, 1, 40, "Iron_Node"),
    Coal_Boulder(MineralItemType.COAL_ORE, 1, 30, "Coal_Node_Quarry_01"),
    Gold_Boulder(MineralItemType.GOLD_ORE, 1, 18, "Gold_Node"),
    Quartz_Boulder(MineralItemType.QUARTZ, 1, 18, "Quartz"),
    Earth_Crystal_Boulder(MineralItemType.EARTH_CRYSTAL, 1, 9, "Earth_Crystal"),
    Frozen_Tear_Boulder(MineralItemType.FROZEN_TEAR,1,6,"Frozen_Geode_Node"),
    Topaz_Boulder(MineralItemType.TOPAZ, 1, 6, "Topaz_Node"),
    Iridium_Boulder(MineralItemType.IRIDIUM_ORE, 1, 5, "Iridium_Node"),
    Amethyst_Boulder(MineralItemType.AMETHYST,1,5,"Amethyst_Node"),
    Fire_Quartz_Boulder(MineralItemType.FIRE_QUARTZ,1,5,"Fire_Quartz"),
    Aquamarine_Boulder(MineralItemType.AQUAMARINE, 1, 3.33, "Aquamarine_Node"),
    Jade_Boulder(MineralItemType.JADE, 1, 3, "Jade_Node"),
    Ruby_Boulder(MineralItemType.RUBY, 1, 2.5, "Ruby_Node"),
    Emerald_Boulder(MineralItemType.EMERALD, 1, 2.5, "Emerald_Node"),
    Diamond_Boulder(MineralItemType.DIAMOND, 1, 1, "Diamond_Node"),
    Prismatic_Shard_Boulder(MineralItemType.PRISMATIC_SHARD, 1, 0.4, "Prismatic_Shard"),
    ;

    private final MineralItemType relatedItem;
    private final int value;
    private final double spawnChance;
    private final String assetName;

    ForagingGameObjectType(MineralItemType relatedItem, int value, double spawnChance, String assetName) {
        this.relatedItem = relatedItem;
        this.value = value;
        this.spawnChance = spawnChance;
        this.assetName = assetName;
    }

    @Nullable
    public String getAssetName() {
        return switch (assetName) {
            case "null" -> null;
            case "" -> relatedItem.getAssetName();
            default -> assetName;
        };
    }

    public double getSpawnChance() {
        return spawnChance;
    }

    public int getValue() {
        return value;
    }

    public MineralItemType getRelatedItem() {
        return relatedItem;
    }

    private static final Random RANDOM = new Random();
    public static ForagingGameObjectType getRandomMineralGameObject() {
        double totalChance = 0;
        for (ForagingGameObjectType type : values()) {
            totalChance += type.spawnChance;
        }

        double randomValue = RANDOM.nextDouble() * totalChance;

        double cumulative = 0;
        for (ForagingGameObjectType type : values()) {
            cumulative += type.spawnChance;
            if (randomValue <= cumulative) {
                return type;
            }
        }
        return values()[0];
    }
}
