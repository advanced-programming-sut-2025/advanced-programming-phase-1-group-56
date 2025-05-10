package model.Enums.Registery;

import model.Ingredient;

public enum ItemRecipesList {
    CHERRY_BOMB(new Ingredient[]{new Ingredient("Copper Ore", 4), new Ingredient("Coal", 1)}, "Mining Level 1", "Destroys everything in a 3-tile radius", 50),
    BOMB(new Ingredient[]{new Ingredient("Iron Ore", 4), new Ingredient("Coal", 1)}, "Mining Level 2", "Destroys everything in a 5-tile radius", 50),
    MEGA_BOMB(new Ingredient[]{new Ingredient("Gold Ore", 4), new Ingredient("Coal", 1)}, "Mining Level 3", "Destroys everything in a 7-tile radius", 50),
    SPRINKLER(new Ingredient[]{new Ingredient("Copper Bar", 1), new Ingredient("Iron Bar", 1)}, "Farming Level 1", "Waters 4 adjacent tiles", 0),
    QUALITY_SPRINKLER(new Ingredient[]{new Ingredient("Iron Bar", 1), new Ingredient("Gold Bar", 1)}, "Farming Level 2", "Waters 8 adjacent tiles", 0),
    IRIDIUM_SPRINKLER(new Ingredient[]{new Ingredient("Gold Bar", 1), new Ingredient("Iridium Bar", 1)}, "Farming Level 3", "Waters 24 adjacent tiles", 0),
    CHARCOAL_KILN(new Ingredient[]{new Ingredient("Wood", 20), new Ingredient("Copper Bar", 2)}, "Foraging Level 1", "Turns 10 wood into 1 coal", 0),
    FURNACE(new Ingredient[]{new Ingredient("Copper Ore", 20), new Ingredient("Stone", 25)}, "-", "Turns ores and coal into bars", 0),
    SCARECROW(new Ingredient[]{new Ingredient("Wood", 50), new Ingredient("Coal", 1), new Ingredient("Fiber", 20)}, "-", "Prevents crow attacks within an 8-tile radius", 0),
    DELUXE_SCARECROW(new Ingredient[]{new Ingredient("Wood", 50), new Ingredient("Coal", 1), new Ingredient("Fiber", 20), new Ingredient("Iridium Ore", 1)}, "Farming Level 2", "Prevents crow attacks within a 12-tile radius", 0),
    BEE_HOUSE(new Ingredient[]{new Ingredient("Wood", 40), new Ingredient("Coal", 8), new Ingredient("Iron Bar", 1)}, "Farming Level 1", "Produces honey when placed on the farm", 0),
    CHEESE_PRESS(new Ingredient[]{new Ingredient("Wood", 45), new Ingredient("Stone", 45), new Ingredient("Copper Bar", 1)}, "Farming Level 2", "Turns milk into cheese", 0),
    KEG(new Ingredient[]{new Ingredient("Wood", 30), new Ingredient("Copper Bar", 1), new Ingredient("Iron Bar", 1)}, "Farming Level 3", "Turns fruits and vegetables into beverages", 0),
    LOOM(new Ingredient[]{new Ingredient("Wood", 60), new Ingredient("Fiber", 30)}, "Farming Level 3", "Turns wool into cloth", 0),
    MAYONNAISE_MACHINE(new Ingredient[]{new Ingredient("Wood", 15), new Ingredient("Stone", 15), new Ingredient("Copper Bar", 1)}, "-", "Turns eggs into mayonnaise", 0),
    OIL_MAKER(new Ingredient[]{new Ingredient("Wood", 100), new Ingredient("Gold Bar", 1), new Ingredient("Iron Bar", 1)}, "Farming Level 3", "Turns truffle into oil", 0),
    PRESERVES_JAR(new Ingredient[]{new Ingredient("Wood", 50), new Ingredient("Stone", 40), new Ingredient("Coal", 8)}, "Farming Level 2", "Turns vegetables into pickles and fruits into jam", 0),
    DEHYDRATOR(new Ingredient[]{new Ingredient("Wood", 30), new Ingredient("Stone", 20), new Ingredient("Fiber", 30)}, "Pierre's General Store", "Dries fruits or mushrooms", 0),
    GRASS_STARTER(new Ingredient[]{new Ingredient("Wood", 1), new Ingredient("Fiber", 1)}, "Pierre's General Store", "Plants grass on the tile it is used", 0),
    FISH_SMOKER(new Ingredient[]{new Ingredient("Wood", 50), new Ingredient("Iron Bar", 3), new Ingredient("Coal", 10)}, "Fish Shop", "Turns any fish into smoked fish with preserved quality", 0),
    MYSTIC_TREE_SEED(new Ingredient[]{new Ingredient("Acorn", 5), new Ingredient("Maple Seed", 5), new Ingredient("Pine Cone", 5), new Ingredient("Mahogany Seed", 5)}, "Foraging Level 4", "Can be planted to grow a mystic tree", 100);

    public final Ingredient[] ingredients;
    public final String source;
    public final String description;
    public final int sellPrice;

    ItemRecipesList(Ingredient[] ingredients, String source, String description, int sellPrice) {
        this.ingredients = ingredients;
        this.source = source;
        this.description = description;
        this.sellPrice = sellPrice;
    }
}
