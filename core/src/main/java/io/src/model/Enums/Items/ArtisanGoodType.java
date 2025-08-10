package io.src.model.Enums.Items;

import io.src.model.Slot;
import io.src.model.items.*;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;

public enum ArtisanGoodType implements ItemType {
    HONEY("Honey", "It's a sweet syrup produced by bees.", 7, 96, null, 350, "Honey"),
    CHEESE("Cheese", "It's your basic cheese.", 10, 3, new Slot[]{new Slot(new Etc(EtcType.MILK), 1)}, 230, "Cheese"),
    BIG_CHEESE("Cheese", "It's your basic cheese.", 10, 3, new Slot[]{new Slot(new Etc(EtcType.BIG_MILK), 1)}, 345, "Cheese"),
    GOAT_CHEESE("GoatCheese", "Soft cheese made from goat's milk.", 10, 3, new Slot[]{new Slot(new Etc(EtcType.GOAT_MILK), 1)}, 345, "Goat_Cheese"),
    BIG_GOAT_CHEESE("GoatCheese", "Soft cheese made from goat's milk.", 10, 3, new Slot[]{new Slot(new Etc(EtcType.BIG_GOAT_MILK), 1)}, 600, "Goat_Cheese"),
    BEER("Beer", "Drink in moderation.", 5, 24, new Slot[]{new Slot(new Food(FoodType.WHEAT), 1)}, 200, "Beer"),
    VINEGAR("Vinegar", "An aged fermented liquid used in many cooking recipes.", 13, 10, new Slot[]{new Slot(new Food(FoodType.RICE), 1)}, 100, "Vinegar"),
    COFFEE("Coffee", "It smells delicious. This is sure to give you a boost.", 7, 2, new Slot[]{new Slot(new Food(FoodType.COFFEE_BEAN), 5)}, 150, ""),
    JUICE("Juice", "A sweet, nutritious beverage.", 5, 96, new Slot[]{new Slot(new Food(FoodType.ANY_VEGETABLE), 1)}, 170, ""),
    MEAD("Mead", "A fermented beverage made from honey. Drink in moderation.", 100, 10, new Slot[]{new Slot(new Food(FoodType.HONEY), 1)}, 300, ""),
    PALE_ALE("PaleAle", "Drink in moderation.", 5, 72, new Slot[]{new Slot(new Food(FoodType.HOPS), 1)}, 300, "Pale_Ale"),
    WINE("Wine", "Drink in moderation.", 10, 168, new Slot[]{new Slot(new Fruit(FruitType.ANY_FRUIT), 1)}, 250, ""),
    DRIED_MUSHROOMS("DriedMushrooms", "A package of gourmet mushrooms.", 5, -1, new Slot[]{new Slot(new Food(FoodType.ANY_MUSHROOMS), 5)}, -1, ""),
    DRIED_FRUIT("DriedFruit", "Chewy pieces of dried fruit.", 7, -1, new Slot[]{new Slot(new Fruit(FruitType.ANY_FRUIT), 5)}, -1, "Dried_Fruit"),
    RAISINS("Raisins", "It's said to be the Junimos' favorite food.", 12, -1, new Slot[]{new Slot(new Food(FoodType.GRAPE), 5)}, 600, ""),
    COAL("Coal", "Turns 10 pieces of wood into one piece of coal.", 10, 1, new Slot[]{new Slot(new Etc(EtcType.WOOD), 10)}, 50, ""),
    CLOTH("Cloth", "A bolt of fine wool cloth.", 15, 4, new Slot[]{new Slot(new Etc(EtcType.WOOL), 1)}, 470, ""),
    MAYONNAISE("Mayonnaise", "It looks spreadable.", 5, 3, new Slot[]{new Slot(new Etc(EtcType.EGG), 1)}, 190, ""),
    BIG_MAYONNAISE("Mayonnaise", "It looks spreadable.", 5, 3, new Slot[]{new Slot(new Etc(EtcType.BIG_EGG), 1)}, 237, ""),
    DUCK_MAYONNAISE("Duck Mayonnaise", "It's a rich, yellow mayonnaise.", 7, 3, new Slot[]{new Slot(new Etc(EtcType.DUCK_EGG), 1)}, 37, "Duck_Mayonnaise"),
    DINOSAUR_MAYONNAISE("Dinosaur Mayonnaise", "Thick and creamy, vivid green, smells like grass and leather.", 12, 3, new Slot[]{new Slot(new Etc(EtcType.DINOSAUR_EGG), 1)}, 800, "Dinosaur_Mayonnaise"),
    TRUFFLE_OIL("Truffle Oil", "A gourmet cooking ingredient.", 3, 6, new Slot[]{new Slot(new Etc(EtcType.TRUFFLE), 1)}, 1065, "Truffle_Oil"),
    CORN_OIL("Oil", "All purpose cooking oil.", 13, 6, new Slot[]{new Slot(new Food(FoodType.CORN), 1)}, 100, ""),
    SUNFLOWER_SEED_OIL("Oil", "All purpose cooking oil.", 13, 48, new Slot[]{new Slot(new Seed(SeedType.SUNFLOWER), 1)}, 100, ""),
    SUNFLOWER_OIL("Oil", "All purpose cooking oil.", 13, 1, new Slot[]{new Slot(new Food(FoodType.SUNFLOWER), 1)}, 100, ""),
    PICKLES("Pickles", "A jar of your home-made pickles.", 20, 6, new Slot[]{new Slot(new Food(FoodType.ANY_VEGETABLE), 1)}, 40, ""),
    JELLY("Jelly", "Gooey.", 30, 72, new Slot[]{new Slot(new Fruit(FruitType.ANY_FRUIT), 1)}, 50, ""),
    SMOKED_FISH("Smoked Fish", "A whole fish, smoked to perfection.", 6, 1, new Slot[]{new Slot(new Fish(FishType.ANY_FISH), 1),
        new Slot(new Mineral(MineralItemType.COAL_ORE), 1)}, 120, "Smoked_Fish"),
    METAL_BAR("Any metal bar", "Turns ore and coal into metal bars.", 15, 4, new Slot[]{new Slot(new Mineral(MineralItemType.ANY_ORE), 5),
        new Slot(new Mineral(MineralItemType.COAL_ORE), 1)}, 200, "null");

    private final String name;
    private final String description;
    private final int energy;
    private final int processingTime;
    private final Slot[] ingredients;
    private final int sellPrice;
    private final String assetName;

    ArtisanGoodType(String name,
                    String description,
                    int energy,
                    int processingTime,
                    Slot[] ingredients,
                    int sellPrice
                    , String assetName
    ) {
        this.name = name;
        this.description = description;
        this.energy = energy;
        this.processingTime = processingTime;
        this.ingredients = ingredients;
        this.sellPrice = sellPrice;
        this.assetName = assetName;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getEnergy() {
        return energy;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public Slot[] getIngredients() {
        return ingredients;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    @Nullable
    public String getAssetName() {
        return switch (assetName) {
            case "null" -> null;
            case "" -> name;
            default -> assetName;
        };
    }
}

