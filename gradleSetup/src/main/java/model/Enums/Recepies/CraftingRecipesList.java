package model.Enums.Recepies;

import model.Ingredient;
import model.items.CraftingTool;
import model.items.Saleable;

public enum CraftingRecipesList implements Saleable {
    CherryBomb("Cherry Bomb", "Destroys everything in a 3-tile radius",
            new Ingredient[]{new Ingredient("Copper Ore", 4), new Ingredient("Coal", 1)},
            "Mining Level 1", 50),

    Bomb("Bomb", "Destroys everything in a 5-tile radius",
            new Ingredient[]{new Ingredient("Iron Ore", 4), new Ingredient("Coal", 1)},
            "Mining Level 2", 50),

    MegaBomb("Mega Bomb", "Destroys everything in a 7-tile radius",
            new Ingredient[]{new Ingredient("Gold Ore", 4), new Ingredient("Coal", 1)},
            "Mining Level 3", 50),

    Sprinkler("Sprinkler", "Waters 4 adjacent tiles",
            new Ingredient[]{new Ingredient("Copper Bar", 1), new Ingredient("Iron Bar", 1)},
            "Farming Level 1", 0),

    QualitySprinkler("Quality Sprinkler", "Waters 8 adjacent tiles",
            new Ingredient[]{new Ingredient("Iron Bar", 1), new Ingredient("Gold Bar", 1)},
            "Farming Level 2", 0),

    IridiumSprinkler("Iridium Sprinkler", "Waters 24 adjacent tiles",
            new Ingredient[]{new Ingredient("Gold Bar", 1), new Ingredient("Iridium Bar", 1)},
            "Farming Level 3", 0),

    CharcoalKlin("Charcoal Klin", "Turns 10 wood into 1 coal",
            new Ingredient[]{new Ingredient("Wood", 20), new Ingredient("Copper Bar", 2)},
            "Foraging Level 1", 0),

    Furnace("Furnace", "Turns ores and coal into bars",
            new Ingredient[]{new Ingredient("Copper Ore", 20), new Ingredient("Stone", 25)},
            "-", 0),

    Scarecrow("Scarecrow", "Prevents crow attacks within an 8-tile radius",
            new Ingredient[]{new Ingredient("Wood", 50), new Ingredient("Coal", 1), new Ingredient("Fiber", 20)},
            "-", 0),

    DeluxeScarecrow("Deluxe Scarecrow", "Prevents crow attacks within a 12-tile radius",
            new Ingredient[]{new Ingredient("Wood", 50), new Ingredient("Coal", 1), new Ingredient("Fiber", 20), new Ingredient("Iridium Ore", 1)},
            "Farming Level 2", 0),

    BeeHouse("Bee House", "Produces honey when placed on the farm",
            new Ingredient[]{new Ingredient("Wood", 40), new Ingredient("Coal", 8), new Ingredient("Iron Bar", 1)},
            "Farming Level 1", 0),

    CheesePress("Cheese Press", "Turns milk into cheese",
            new Ingredient[]{new Ingredient("Wood", 45), new Ingredient("Stone", 45), new Ingredient("Copper Bar", 1)},
            "Farming Level 2", 0),

    Keg("Keg", "Turns fruits and vegetables into beverages",
            new Ingredient[]{new Ingredient("Wood", 30), new Ingredient("Copper Bar", 1), new Ingredient("Iron Bar", 1)},
            "Farming Level 3", 0),

    Loom("Loom", "Turns wool into cloth",
            new Ingredient[]{new Ingredient("Wood", 60), new Ingredient("Fiber", 30)},
            "Farming Level 3", 0),

    MayonnaiseMachine("Mayonnaise Machine", "Turns eggs into mayonnaise",
            new Ingredient[]{new Ingredient("Wood", 15), new Ingredient("Stone", 15), new Ingredient("Copper Bar", 1)},
            "-", 0),

    OilMaker("Oil Maker", "Turns truffle into oil",
            new Ingredient[]{new Ingredient("Wood", 100), new Ingredient("Gold Bar", 1), new Ingredient("Iron Bar", 1)},
            "Farming Level 3", 0),

    PreservesJar("Preserves Jar", "Turns vegetables into pickles and fruits into jam",
            new Ingredient[]{new Ingredient("Wood", 50), new Ingredient("Stone", 40), new Ingredient("Coal", 8)},
            "Farming Level 2", 0),

    Dehydrator("Dehydrator", "Dries fruits or mushrooms",
            new Ingredient[]{new Ingredient("Wood", 30), new Ingredient("Stone", 20), new Ingredient("Fiber", 30)},
            "Pierre's General Store", 0),

    GrassStarter("Grass Starter", "Plants grass on the tile it is used",
            new Ingredient[]{new Ingredient("Wood", 1), new Ingredient("Fiber", 1)},
            "Pierre's General Store", 0),

    FishSmoker("Fish Smoker", "Turns any fish into smoked fish with preserved quality",
            new Ingredient[]{new Ingredient("Wood", 50), new Ingredient("Iron Bar", 3), new Ingredient("Coal", 10)},
            "Fish Shop", 0),

    MysticTreeSeed("Mystic Tree Seed", "Can be planted to grow a mystic tree",
            new Ingredient[]{new Ingredient("Acorn", 5), new Ingredient("Maple Seed", 5), new Ingredient("Pine Cone", 5), new Ingredient("Mahogany Seed", 5)},
            "Foraging Level 4", 100);

    public final String name;
    public final String description;
    public final Ingredient[] ingredients;
    public final String source;
    public final int sellPrice;

    CraftingRecipesList(String name, String description, Ingredient[] ingredients, String source, int sellPrice) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.source = source;
        this.sellPrice = sellPrice;
    }

    @Override
    public String getName() {
        return name;
    }
}
