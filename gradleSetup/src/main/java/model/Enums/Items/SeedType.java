package model.Enums.Items;

import model.Enums.GameObjects.CropType;
import model.Enums.WeatherAndTime.Seasons;

public enum SeedType {
    JAZZ("Jazz Seeds", new Seasons[]{Seasons.Spring} , CropType.BLUE_JAZZ),
    CARROT("Carrot Seeds", new Seasons[]{Seasons.Spring} , CropType.CARROT),
    CAULIFLOWER("Cauliflower Seeds", new Seasons[]{Seasons.Spring} , CropType.CAULIFLOWER),
    COFFEE_BEAN("Coffee Bean", new Seasons[]{Seasons.Spring} , CropType.COFFEE_BEAN),
    GARLIC("Garlic Seeds", new Seasons[]{Seasons.Spring} , CropType.GARLIC),
    BEAN_STARTER("Bean Starter", new Seasons[]{Seasons.Spring} , CropType.GREEN_BEAN),
    KALE("Kale Seeds", new Seasons[]{Seasons.Spring} , CropType.KALE),
    PARSNIP("Parsnip Seeds", new Seasons[]{Seasons.Spring} , CropType.PARSNIP),
    POTATO("Potato Seeds", new Seasons[]{Seasons.Spring} , CropType.POTATO),
    RHUBARB("Rhubarb Seeds", new Seasons[]{Seasons.Spring} , CropType.RHUBARB),
    STRAWBERRY("Strawberry Seeds", new Seasons[]{Seasons.Spring} , CropType.STRAWBERRY),
    TULIP("Tulip Bulb", new Seasons[]{Seasons.Spring} , CropType.TULIP),
    RICE_SHOOT("Rice Shoot", new Seasons[]{Seasons.Spring} , CropType.UNMILLED_RICE),

    BLUEBERRY("Blueberry Seeds", new Seasons[]{Seasons.Summer} , CropType.BLUEBERRY),
    CORN("Corn Seeds", new Seasons[]{Seasons.Summer} , CropType.CORN),
    HOPS("Hops Starter", new Seasons[]{Seasons.Summer} , CropType.HOPS),
    PEPPER("Pepper Seeds", new Seasons[]{Seasons.Summer} , CropType.HOT_PEPPER),
    MELON("Melon Seeds", new Seasons[]{Seasons.Summer} , CropType.MELON),
    POPPY("Poppy Seeds", new Seasons[]{Seasons.Summer} , CropType.POPPY),
    RADISH("Radish Seeds", new Seasons[]{Seasons.Summer} , CropType.RADISH),
    RED_CABBAGE("Red Cabbage Seeds", new Seasons[]{Seasons.Summer} , CropType.RED_CABBAGE),
    STARFRUIT("Starfruit Seeds", new Seasons[]{Seasons.Summer} , CropType.STARFRUIT),
    SPANGLE("Spangle Seeds", new Seasons[]{Seasons.Summer} , CropType.SUMMER_SPANGLE),
    SUMMER_SQUASH("Summer Squash Seeds", new Seasons[]{Seasons.Summer} , CropType.SUMMER_SQUASH),
    SUNFLOWER("Sunflower Seeds", new Seasons[]{Seasons.Summer} , CropType.SUNFLOWER),
    TOMATO("Tomato Seeds", new Seasons[]{Seasons.Summer} , CropType.TOMATO),
    WHEAT("Wheat Seeds", new Seasons[]{Seasons.Summer} , CropType.WHEAT),

    AMARANTH("Amaranth Seeds", new Seasons[]{Seasons.Fall} , CropType.AMARANTH),
    ARTICHOKE("Artichoke Seeds", new Seasons[]{Seasons.Fall} , CropType.ARTICHOKE),
    BEET("Beet Seeds", new Seasons[]{Seasons.Fall} , CropType.BEET),
    BOK_CHOY("Bok Choy Seeds", new Seasons[]{Seasons.Fall} , CropType.BOK_CHOY),
    BROCCOLI("Broccoli Seeds", new Seasons[]{Seasons.Fall} , CropType.BROCCOLI),
    CRANBERRY("Cranberry Seeds", new Seasons[]{Seasons.Fall} , CropType.CRANBERRIES),
    EGGPLANT("Eggplant Seeds", new Seasons[]{Seasons.Fall} , CropType.EGGPLANT),
    FAIRY("Fairy Seeds", new Seasons[]{Seasons.Fall} , CropType.FAIRY_ROSE),
    GRAPE("Grape Starter", new Seasons[]{Seasons.Fall} , CropType.GRAPE),
    PUMPKIN("Pumpkin Seeds", new Seasons[]{Seasons.Fall} , CropType.PUMPKIN),
    YAM("Yam Seeds", new Seasons[]{Seasons.Fall} , CropType.YAM),
    RARE_SEED("Rare Seed", new Seasons[]{Seasons.Fall} , CropType.SWEET_GEM_BERRY),

    POWDERMELON("Powdermelon Seeds", new Seasons[]{Seasons.Winter} , CropType.POWDERMELON),

    ANCIENT("Ancient Seeds", new Seasons[]{Seasons.Winter,Seasons.Summer,Seasons.Spring,Seasons.Fall} , CropType.ANCIENT_FRUIT),
    MIXED("Mixed Seeds", new Seasons[]{Seasons.Winter,Seasons.Summer,Seasons.Spring,Seasons.Fall} , null);

    public final String name;
    public final Seasons[] season;
    public final CropType cropType;

    SeedType(String name, Seasons[] season, CropType cropType) {
        this.name = name;
        this.season = season;
        this.cropType = cropType;
    }






}
