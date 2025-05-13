package model.Enums.Items;

import model.Enums.WeatherAndTime.Seasons;

public enum SpecialTreeSeed {
    ACORN("Acorns", new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Winter, Seasons.Fall}),
    MAPLE_SEED("Maple Seeds", new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Winter, Seasons.Fall}),
    PINE_CONE("Pine Cones", new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Winter, Seasons.Fall}),
    MAHOGANY_SEED("Mahogany Seeds", new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Winter, Seasons.Fall}),
    MUSHROOM_TREE_SEED("Mushroom Tree Seeds", new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Winter, Seasons.Fall});

    public final String name;
    public final Seasons[] season;

    SpecialTreeSeed(String name, Seasons[] season) {
        this.name = name;
        this.season = season;
    }
}
