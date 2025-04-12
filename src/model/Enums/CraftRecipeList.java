package model.Enums;

import java.util.regex.Pattern;

public enum CraftRecipeList {
    CherryBomb("Cherry Bomb"),
    Bomb("Bomb"),
    megaBomb("Mega Bomb"),
    Sprinkler("Sprinkler"),
    QualitySprinkler("Quality Sprinkler"),
    iridiumSprinkler("Iridium Sprinkler"),
    charcoalKlin("Charcoal Klin"),
    furnace("Furnace"),
    scarecrow("Scarecrow"),
    deluxeScarecrow("Deluxe Scarecrow"),
    beeHouse("Bee House"),
    cheesePress("Cheese Press"),
    keg("Keg"),
    loom("Loom"),
    mayonnaiseMachine("Mayonnaise Machine"),
    oilMaker("Oil Maker"),
    preservesJar("Preserves Jar"),
    dehydrator("dehydrator"),
    fishSmoker("Fish Smoker"),
    mysticTreeSeed("Mystic Tree Seed");

    private final String input;

    CraftRecipeList(String input) {
        this.input = input;
    }

    public boolean isValid(String input) {
        return Pattern.matches(this.input, input);
    }

}
