package io.src.model.Enums.Items;

import io.src.model.Enums.WeatherAndTime.Seasons;
import org.jetbrains.annotations.Nullable;

public enum SpecialTreeSeed implements ItemType {
    ACORN("Acorn", new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Winter, Seasons.Fall}, ""),
    MAPLE_SEED("Maple Seed", new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Winter, Seasons.Fall}, ""),
    PINE_CONE("Pine Cone", new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Winter, Seasons.Fall}, ""),
    MAHOGANY_SEED("Mahogany Seed", new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Winter, Seasons.Fall}, ""),
    MUSHROOM_TREE_SEED("Mushroom Tree Seed", new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Winter, Seasons.Fall}, "");

    public final String name;
    public final Seasons[] season;
    private final String assetName;

    SpecialTreeSeed(String name, Seasons[] season, String assetName) {
        this.name = name;
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
}
