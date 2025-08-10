package io.src.model.Enums.Recepies;

import io.src.model.Enums.Items.*;
import io.src.model.Slot;
import io.src.model.items.Etc;
import io.src.model.items.Mineral;
import io.src.model.items.Seed;
import io.src.model.items.Saleable;
import org.jetbrains.annotations.Nullable;

public enum CraftingRecipesList implements Saleable {
    CherryBomb("Cherry_Bomb", "Destroys everything in a 3-tile radius",
        new Slot[]{new Slot(new Mineral(MineralItemType.COPPER_ORE), 4), new Slot(new Mineral(MineralItemType.COAL_ORE), 1)},
        "Mining Level 1", 50, "Cherry_Bomb"),

    Bomb("Bomb", "Destroys everything in a 5-tile radius",
        new Slot[]{new Slot(new Mineral(MineralItemType.IRON_ORE), 4), new Slot(new Mineral(MineralItemType.COAL_ORE), 1)},
        "Mining Level 2", 50, ""),

    MegaBomb("Mega_Bomb", "Destroys everything in a 7-tile radius",
        new Slot[]{new Slot(new Mineral(MineralItemType.GOLD_ORE), 4), new Slot(new Mineral(MineralItemType.COAL_ORE), 1)},
        "Mining Level 3", 50, "Mega_Bomb"),

    Sprinkler("Sprinkler", "Waters 4 adjacent tiles",
        new Slot[]{new Slot(new Etc(EtcType.COPPER_BAR), 1), new Slot(new Etc(EtcType.IRON_BAR), 1)},
        "Farming Level 1", 0, ""),

    QualitySprinkler("Quality_Sprinkler", "Waters 8 adjacent tiles",
        new Slot[]{new Slot(new Etc(EtcType.IRON_BAR), 1), new Slot(new Etc(EtcType.GOLD_BAR), 1)},
        "Farming Level 2", 0, "Quality_Sprinkler"),

    IridiumSprinkler("Iridium_Sprinkler", "Waters 24 adjacent tiles",
        new Slot[]{new Slot(new Etc(EtcType.GOLD_BAR), 1), new Slot(new Etc(EtcType.IRIDIUM_BAR), 1)},
        "Farming Level 3", 0, "Iridium_Sprinkler"),

    CharcoalKlin("Charcoal Klin", "Turns 10 wood into 1 coal",
        new Slot[]{new Slot(new Etc(EtcType.WOOD), 20), new Slot(new Etc(EtcType.COPPER_BAR), 2)},
        "Foraging Level 1", 0, "Charcoal_Klin"),

    Furnace("Furnace", "Turns ores and coal into bars",
        new Slot[]{new Slot(new Mineral(MineralItemType.COPPER_ORE), 20), new Slot(new Mineral(MineralItemType.STONE), 25)},
        "-", 0, "Furnace"),

    Scarecrow("Scarecrow", "Prevents crow attacks within an 8-tile radius",
        new Slot[]{new Slot(new Etc(EtcType.WOOD), 50), new Slot(new Mineral(MineralItemType.COAL_ORE), 1), new Slot(new Etc(EtcType.FIBER), 20)},
        "-", 0, "Scarecrow"),

    DeluxeScarecrow("Deluxe_Scarecrow", "Prevents crow attacks within a 12-tile radius",
        new Slot[]{new Slot(new Etc(EtcType.WOOD), 50), new Slot(new Mineral(MineralItemType.COAL_ORE), 1), new Slot(new Etc(EtcType.FIBER), 20), new Slot(new Mineral(MineralItemType.IRON_ORE), 1)},
        "Farming Level 2", 0, "Deluxe_Scarecrow"),

    BeeHouse("Bee_House", "Produces honey when placed on the farm",
        new Slot[]{new Slot(new Etc(EtcType.WOOD), 40), new Slot(new Mineral(MineralItemType.COAL_ORE), 8), new Slot(new Etc(EtcType.IRON_BAR), 1)},
        "Farming Level 1", 0, "Bee_House"),

    CheesePress("Cheese_Press", "Turns milk into cheese",
        new Slot[]{new Slot(new Etc(EtcType.WOOD), 45), new Slot(new Mineral(MineralItemType.STONE), 45), new Slot(new Etc(EtcType.COPPER_BAR), 1)},
        "Farming Level 2", 0, "Cheese_Press"),

    Keg("Keg", "Turns fruits and vegetables into beverages",
        new Slot[]{new Slot(new Etc(EtcType.WOOD), 30), new Slot(new Etc(EtcType.COPPER_BAR), 1), new Slot(new Etc(EtcType.IRON_BAR), 1)},
        "Farming Level 3", 0, "Keg"),

    Loom("Loom", "Turns wool into cloth",
        new Slot[]{new Slot(new Etc(EtcType.WOOD), 60), new Slot(new Etc(EtcType.FIBER), 30)},
        "Farming Level 3", 0, "Loom"),

    MayonnaiseMachine("Mayonnaise_Machine", "Turns eggs into mayonnaise",
        new Slot[]{new Slot(new Etc(EtcType.WOOD), 15), new Slot(new Mineral(MineralItemType.STONE), 15), new Slot(new Etc(EtcType.COPPER_BAR), 1)},
        "-", 0, "Mayonnaise_Machine"),

    OilMaker("Oil_Maker", "Turns truffle into oil",
        new Slot[]{new Slot(new Etc(EtcType.WOOD), 100), new Slot(new Etc(EtcType.GOLD_BAR), 1), new Slot(new Etc(EtcType.IRON_BAR), 1)},
        "Farming Level 3", 0, "Oil_Maker"),

    PreservesJar("Preserves_Jar", "Turns vegetables into pickles and fruits into jam",
        new Slot[]{new Slot(new Etc(EtcType.WOOD), 50), new Slot(new Mineral(MineralItemType.STONE), 40), new Slot(new Mineral(MineralItemType.COAL_ORE), 8)},
        "Farming Level 2", 0, "Preserves_Jar"),

    Dehydrator("Dehydrator", "Dries fruits or mushrooms",
        new Slot[]{new Slot(new Etc(EtcType.WOOD), 30), new Slot(new Mineral(MineralItemType.STONE), 20), new Slot(new Etc(EtcType.FIBER), 30)},
        "Pierre's General Store", 0, "Dehydrator"),

    GrassStarter("Grass_Starter", "Plants grass on the tile it is used",
        new Slot[]{new Slot(new Etc(EtcType.WOOD), 1), new Slot(new Etc(EtcType.FIBER), 1)},
        "Pierre's General Store", 0, "Grass_Starter"),

    FishSmoker("Fish_Smoker", "Turns any fish into smoked fish with preserved quality",
        new Slot[]{new Slot(new Etc(EtcType.WOOD), 50), new Slot(new Etc(EtcType.IRON_BAR), 3), new Slot(new Mineral(MineralItemType.COAL_ORE), 10)},
        "Fish Shop", 0, "Fish_Smoker"),

    MysticTreeSeed("Mystic_Tree_Seed", "Can be planted to grow a mystic tree",
        new Slot[]{new Slot(new Seed(SeedType.MAPLE_SEED), 5), new Slot(new Seed(SeedType.MAPLE_SEED), 5), new Slot(new Seed(SeedType.PINE_SEED), 5), new Slot(new Seed(SeedType.MAHOGANY_SEED), 5)},
        "Foraging Level 4", 100, "Mystic_Tree_Seed");

    public final String name;
    public final String description;
    public final Slot[] ingredients;
    public final String source;
    public final int sellPrice;
    private final String assetName;

    CraftingRecipesList(String name, String description, Slot[] ingredients, String source, int sellPrice, String assetName) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.source = source;
        this.sellPrice = sellPrice;
        this.assetName = assetName;
    }

    @Override
    public String getName() {
        return name;
    }


    public static CraftingRecipesList fromName(String name) {
        for (CraftingRecipesList type : CraftingRecipesList.values()) {
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
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
