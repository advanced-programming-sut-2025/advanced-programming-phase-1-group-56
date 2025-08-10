package io.src.model.Enums.Items;

import io.src.model.Enums.WeatherAndTime.Seasons;
import org.jetbrains.annotations.Nullable;

public enum MixedSeed implements ItemType {
    SPRING(Seasons.Spring, new SeedType[]{SeedType.CAULIFLOWER, SeedType.PARSNIP, SeedType.POTATO, SeedType.JAZZ, SeedType.TULIP},"Spring_Seeds"),
    SUMMER(Seasons.Summer, new SeedType[]{SeedType.CORN, SeedType.PEPPER, SeedType.RADISH, SeedType.WHEAT, SeedType.POPPY, SeedType.SUNFLOWER, SeedType.SPANGLE},"Summer_Seeds"),
    FALL(Seasons.Fall, new SeedType[]{SeedType.ARTICHOKE, SeedType.CORN, SeedType.EGGPLANT, SeedType.PUMPKIN, SeedType.SUNFLOWER, SeedType.FAIRY},"Fall_Seeds"),
    WINTER(Seasons.Winter, new SeedType[]{SeedType.POWDERMELON},"Winter_Seeds"),;

    public final Seasons season;
    public final SeedType[] possibleCrops;
    private final String assetName;

    MixedSeed(Seasons season, SeedType[] possibleCrops, String assetName) {
        this.season = season;
        this.possibleCrops = possibleCrops;
        this.assetName = assetName;
    }

    public static MixedSeed fromSeason(Seasons season) {
        for (MixedSeed seed : values()) {
            if (seed.season.equals(season)) {
                return seed;
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return this.getAssetName();
    }

    @Nullable
    public String getAssetName() {
        return switch (assetName) {
            case "null" -> null;
            case "" -> getName().replace(" ", "_");
            default -> assetName;
        };
    }
}
