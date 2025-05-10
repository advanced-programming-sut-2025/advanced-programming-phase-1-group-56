package model.Enums.Registery;

public enum MixedSeed {
    SPRING("Spring", new String[]{"Cauliflower", "Parsnip", "Potato", "Blue Jazz", "Tulip"}),
    SUMMER("Summer", new String[]{"Corn", "Hot Pepper", "Radish", "Wheat", "Poppy", "Sunflower", "Summer Spangle"}),
    FALL("Fall", new String[]{"Artichoke", "Corn", "Eggplant", "Pumpkin", "Sunflower", "Fairy Rose"}),
    WINTER("Winter", new String[]{"Powdermelon"});

    public final String season;
    public final String[] possibleCrops;

    MixedSeed(String season, String[] possibleCrops) {
        this.season = season;
        this.possibleCrops = possibleCrops;
    }

    public static MixedSeed fromSeason(String season) {
        for (MixedSeed seed : values()) {
            if (seed.season.equalsIgnoreCase(season)) {
                return seed;
            }
        }
        return null;
    }
}
