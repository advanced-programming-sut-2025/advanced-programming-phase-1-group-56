package io.src.model.Enums.Items;

import io.src.model.Enums.GameObjects.CropType;
import io.src.model.Enums.GameObjects.TreeType;
import io.src.model.Enums.GameObjects.initialPlant;
import io.src.model.Enums.WeatherAndTime.Seasons;
import org.jetbrains.annotations.Nullable;

public enum SeedType implements ItemType {
    JAZZ("Jazz Seeds", new Seasons[]{Seasons.Spring}, CropType.BLUE_JAZZ, ""),
    CARROT("Carrot Seeds", new Seasons[]{Seasons.Spring}, CropType.CARROT, ""),
    CAULIFLOWER("Cauliflower Seeds", new Seasons[]{Seasons.Spring}, CropType.CAULIFLOWER, ""),
    COFFEE_BEAN("Coffee Bean", new Seasons[]{Seasons.Spring}, CropType.COFFEE_BEAN, ""),
    GARLIC("Garlic Seeds", new Seasons[]{Seasons.Spring}, CropType.GARLIC, ""),
    BEAN_STARTER("Bean Starter", new Seasons[]{Seasons.Spring}, CropType.GREEN_BEAN, ""),
    KALE("Kale Seeds", new Seasons[]{Seasons.Spring}, CropType.KALE, ""),
    PARSNIP("Parsnip Seeds", new Seasons[]{Seasons.Spring}, CropType.PARSNIP, ""),
    POTATO("Potato Seeds", new Seasons[]{Seasons.Spring}, CropType.POTATO, ""),
    RHUBARB("Rhubarb Seeds", new Seasons[]{Seasons.Spring}, CropType.RHUBARB, ""),
    STRAWBERRY("Strawberry Seeds", new Seasons[]{Seasons.Spring}, CropType.STRAWBERRY, ""),
    TULIP("Tulip Bulb", new Seasons[]{Seasons.Spring}, CropType.TULIP, ""),
    RICE_SHOOT("Rice Shoot", new Seasons[]{Seasons.Spring}, CropType.UNMILLED_RICE, ""),
    BLUEBERRY("Blueberry Seeds", new Seasons[]{Seasons.Summer}, CropType.BLUEBERRY, ""),
    CORN("Corn Seeds", new Seasons[]{Seasons.Summer}, CropType.CORN, ""),
    HOPS("Hops Starter", new Seasons[]{Seasons.Summer}, CropType.HOPS, ""),
    PEPPER("Pepper Seeds", new Seasons[]{Seasons.Summer}, CropType.HOT_PEPPER, ""),
    MELON("Melon Seeds", new Seasons[]{Seasons.Summer}, CropType.MELON, ""),
    POPPY("Poppy Seeds", new Seasons[]{Seasons.Summer}, CropType.POPPY, ""),
    RADISH("Radish Seeds", new Seasons[]{Seasons.Summer}, CropType.RADISH, ""),
    RED_CABBAGE("Red Cabbage Seeds", new Seasons[]{Seasons.Summer}, CropType.RED_CABBAGE, ""),
    STARFRUIT("Starfruit Seeds", new Seasons[]{Seasons.Summer}, CropType.STARFRUIT, ""),
    SPANGLE("Spangle Seeds", new Seasons[]{Seasons.Summer}, CropType.SUMMER_SPANGLE, ""),
    SUMMER_SQUASH("Summer Squash Seeds", new Seasons[]{Seasons.Summer}, CropType.SUMMER_SQUASH, ""),
    SUNFLOWER("Sunflower Seeds", new Seasons[]{Seasons.Summer}, CropType.SUNFLOWER, ""),
    TOMATO("Tomato Seeds", new Seasons[]{Seasons.Summer}, CropType.TOMATO, ""),
    WHEAT("Wheat Seeds", new Seasons[]{Seasons.Summer}, CropType.WHEAT, ""),
    AMARANTH("Amaranth Seeds", new Seasons[]{Seasons.Fall}, CropType.AMARANTH, ""),
    ARTICHOKE("Artichoke Seeds", new Seasons[]{Seasons.Fall}, CropType.ARTICHOKE, ""),
    BEET("Beet Seeds", new Seasons[]{Seasons.Fall}, CropType.BEET, ""),
    BOK_CHOY("Bok Choy Seeds", new Seasons[]{Seasons.Fall}, CropType.BOK_CHOY, ""),
    BROCCOLI("Broccoli Seeds", new Seasons[]{Seasons.Fall}, CropType.BROCCOLI, ""),
    CRANBERRY("Cranberry Seeds", new Seasons[]{Seasons.Fall}, CropType.CRANBERRIES, ""),
    EGGPLANT("Eggplant Seeds", new Seasons[]{Seasons.Fall}, CropType.EGGPLANT, ""),
    FAIRY("Fairy Seeds", new Seasons[]{Seasons.Fall}, CropType.FAIRY_ROSE, ""),
    GRAPE("Grape Starter", new Seasons[]{Seasons.Fall}, CropType.GRAPE, ""),
    PUMPKIN("Pumpkin Seeds", new Seasons[]{Seasons.Fall}, CropType.PUMPKIN, ""),
    YAM("Yam Seeds", new Seasons[]{Seasons.Fall}, CropType.YAM, ""),
    RARE_SEED("Rare Seed", new Seasons[]{Seasons.Fall}, CropType.SWEET_GEM_BERRY, ""),
    POWDERMELON("Powdermelon Seeds", new Seasons[]{Seasons.Winter}, CropType.POWDERMELON, ""),
    ANCIENT("Ancient Seeds", new Seasons[]{Seasons.Winter, Seasons.Summer, Seasons.Spring, Seasons.Fall}, CropType.ANCIENT_FRUIT, ""),
    MIXED("Mixed Seeds", new Seasons[]{Seasons.Winter, Seasons.Summer, Seasons.Spring, Seasons.Fall}, null, ""),
    APRICOT_SAPLING("Apricot Sapling", new Seasons[]{Seasons.Spring}, TreeType.APRICOT_TREE, ""),
    CHERRY_SAPLING("Cherry Sapling", new Seasons[]{Seasons.Spring}, TreeType.CHERRY_TREE, ""),
    BANANA_SAPLING("Banana Sapling", new Seasons[]{Seasons.Summer}, TreeType.BANANA_TREE, ""),
    MANGO_SAPLING("Mango Sapling", new Seasons[]{Seasons.Summer}, TreeType.MANGO_TREE, ""),
    ORANGE_SAPLING("Orange Sapling", new Seasons[]{Seasons.Summer}, TreeType.ORANGE_TREE, ""),
    PEACH_SAPLING("Peach Sapling", new Seasons[]{Seasons.Summer}, TreeType.PEACH_TREE, ""),
    APPLE_SAPLING("Apple Sapling", new Seasons[]{Seasons.Fall}, TreeType.APPLE_TREE, ""),
    POMEGRANATE_SAPLING("Pomegranate Sapling", new Seasons[]{Seasons.Fall}, TreeType.POMEGRANATE_TREE, ""),
    OAK_SEED("Acorns", Seasons.values(), TreeType.OAK_TREE, ""),
    MAPLE_SEED("Maple Seeds", Seasons.values(), TreeType.MAPLE_TREE, ""),
    PINE_SEED("Pine Cone", Seasons.values(), TreeType.PINE_TREE, ""),
    MAHOGANY_SEED("Mahogany Seed", Seasons.values(), TreeType.MAHOGANY_TREE, ""),
    MUSHROOM_SEED("Mushroom Tree Seed", Seasons.values(), TreeType.MUSHROOM_TREE, ""),
    MYSTIC_SEED("Mystic Tree Seeds", Seasons.values(), TreeType.MYSTIC_TREE, "");

    public final String name;
    public final Seasons[] season;
    public final initialPlant cropType;
    private final String assetName;

    SeedType(String name, Seasons[] season, initialPlant cropType, String assetName) {
        this.name = name;
        this.season = season;
        this.cropType = cropType;
        this.assetName = assetName;
    }


    @Override
    public String getName() {
        return name;
    }

    public static SeedType fromName(String name) {
        for (SeedType type : SeedType.values()) {
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

    @Nullable
    public String getAssetName() {
        return switch (assetName) {
            case "null" -> null;
            case "" -> name.replace(" ", "_");
            default -> assetName;
        };
    }
}
