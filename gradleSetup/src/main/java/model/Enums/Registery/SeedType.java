package model.Enums.Registery;

import model.Enums.WeatherAndTime.Seasons;

public enum SeedType {
    JAZZ("Jazz Seeds", new Seasons[]{Seasons.Spring}),
    CARROT("Carrot Seeds", new Seasons[]{Seasons.Spring}),
    CAULIFLOWER("Cauliflower Seeds", new Seasons[]{Seasons.Spring}),
    COFFEE_BEAN("Coffee Bean", new Seasons[]{Seasons.Spring}),
    GARLIC("Garlic Seeds", new Seasons[]{Seasons.Spring}),
    BEAN_STARTER("Bean Starter", new Seasons[]{Seasons.Spring}),
    KALE("Kale Seeds", new Seasons[]{Seasons.Spring}),
    PARSNIP("Parsnip Seeds", new Seasons[]{Seasons.Spring}),
    POTATO("Potato Seeds", new Seasons[]{Seasons.Spring}),
    RHUBARB("Rhubarb Seeds", new Seasons[]{Seasons.Spring}),
    STRAWBERRY("Strawberry Seeds", new Seasons[]{Seasons.Spring}),
    TULIP("Tulip Bulb", new Seasons[]{Seasons.Spring}),
    RICE_SHOOT("Rice Shoot", new Seasons[]{Seasons.Spring}),

    BLUEBERRY("Blueberry Seeds", new Seasons[]{Seasons.Summer}),
    CORN("Corn Seeds", new Seasons[]{Seasons.Summer}),
    HOPS("Hops Starter", new Seasons[]{Seasons.Summer}),
    PEPPER("Pepper Seeds", new Seasons[]{Seasons.Summer}),
    MELON("Melon Seeds", new Seasons[]{Seasons.Summer}),
    POPPY("Poppy Seeds", new Seasons[]{Seasons.Summer}),
    RADISH("Radish Seeds", new Seasons[]{Seasons.Summer}),
    RED_CABBAGE("Red Cabbage Seeds", new Seasons[]{Seasons.Summer}),
    STARFRUIT("Starfruit Seeds", new Seasons[]{Seasons.Summer}),
    SPANGLE("Spangle Seeds", new Seasons[]{Seasons.Summer}),
    SUMMER_SQUASH("Summer Squash Seeds", new Seasons[]{Seasons.Summer}),
    SUNFLOWER("Sunflower Seeds", new Seasons[]{Seasons.Summer}),
    TOMATO("Tomato Seeds", new Seasons[]{Seasons.Summer}),
    WHEAT("Wheat Seeds", new Seasons[]{Seasons.Summer}),

    AMARANTH("Amaranth Seeds", new Seasons[]{Seasons.Fall}),
    ARTICHOKE("Artichoke Seeds", new Seasons[]{Seasons.Fall}),
    BEET("Beet Seeds", new Seasons[]{Seasons.Fall}),
    BOK_CHOY("Bok Choy Seeds", new Seasons[]{Seasons.Fall}),
    BROCCOLI("Broccoli Seeds", new Seasons[]{Seasons.Fall}),
    CRANBERRY("Cranberry Seeds", new Seasons[]{Seasons.Fall}),
    EGGPLANT("Eggplant Seeds", new Seasons[]{Seasons.Fall}),
    FAIRY("Fairy Seeds", new Seasons[]{Seasons.Fall}),
    GRAPE("Grape Starter", new Seasons[]{Seasons.Fall}),
    PUMPKIN("Pumpkin Seeds", new Seasons[]{Seasons.Fall}),
    YAM("Yam Seeds", new Seasons[]{Seasons.Fall}),
    RARE_SEED("Rare Seed", new Seasons[]{Seasons.Fall}),

    POWDERMELON("Powdermelon Seeds", new Seasons[]{Seasons.Winter}),

    ANCIENT("Ancient Seeds", new Seasons[]{Seasons.Winter,Seasons.Summer,Seasons.Spring,Seasons.Fall}),
    MIXED("Mixed Seeds", new Seasons[]{Seasons.Winter,Seasons.Summer,Seasons.Spring,Seasons.Fall} );

    public final String name;
    public final Seasons[] season;

    SeedType(String name, Seasons[] season) {
        this.name = name;
        this.season = season;
    }


}
