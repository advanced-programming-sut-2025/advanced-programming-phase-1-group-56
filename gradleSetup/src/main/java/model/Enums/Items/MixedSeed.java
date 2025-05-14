package model.Enums.Items;

import model.Enums.WeatherAndTime.Seasons;

public enum MixedSeed implements  ItemType {
    SPRING(Seasons.Spring, new SeedType[]{SeedType.CAULIFLOWER,SeedType.PARSNIP,SeedType.POTATO, SeedType.JAZZ, SeedType.TULIP}),
    SUMMER(Seasons.Summer, new SeedType[]{SeedType.CORN,SeedType.PEPPER, SeedType.RADISH, SeedType.WHEAT, SeedType.POPPY, SeedType.SUNFLOWER, SeedType.SPANGLE}),
    FALL(Seasons.Fall, new SeedType[]{SeedType.ARTICHOKE,SeedType.CORN,SeedType.EGGPLANT,SeedType.PUMPKIN, SeedType.SUNFLOWER, SeedType.FAIRY}),
    WINTER(Seasons.Winter, new SeedType[]{SeedType.POWDERMELON});

    public final Seasons season;
    public final SeedType[] possibleCrops;

    MixedSeed(Seasons season, SeedType[] possibleCrops) {
        this.season = season;
        this.possibleCrops = possibleCrops;
    }

    public static MixedSeed fromSeason(Seasons season) {
        for (MixedSeed seed : values()) {
            if (seed.season.equals(season)) {
                return seed;
            }
        }
        return null;
    }
}
