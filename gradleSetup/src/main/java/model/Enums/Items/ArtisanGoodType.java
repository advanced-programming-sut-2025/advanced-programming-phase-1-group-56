package model.Enums.Items;

import model.Slot;
import model.items.*;

public enum ArtisanGoodType implements ItemType {
    HONEY               ("Honey",            "It's a sweet syrup produced by bees.",             75,   96,  null,                                               350),
    CHEESE              ("Cheese",           "It's your basic cheese.",                          100,   3,  new Slot[]{new Slot(new Etc(EtcType.MILK), 1)},      230),
    BIG_CHEESE          ("Cheese",           "It's your basic cheese.",                          100,   3,  new Slot[]{new Slot(new Etc(EtcType.BIG_MILK), 1)},  345),
    GOAT_CHEESE         ("GoatCheese",       "Soft cheese made from goat's milk.",               100,   3,  new Slot[]{new Slot(new Etc(EtcType.GOAT_MILK), 1)}, 345),
    BIG_GOAT_CHEESE     ("GoatCheese",       "Soft cheese made from goat's milk.",               100,   3,  new Slot[]{new Slot(new Etc(EtcType.BIG_GOAT_MILK), 1)},600),
    BEER                ("Beer",             "Drink in moderation.",                             50,    24, new Slot[]{new Slot(new Food(FoodType.WHEAT), 1)},   200),
    VINEGAR             ("Vinegar",          "An aged fermented liquid used in many cooking recipes.",13,10, new Slot[]{new Slot(new Food(FoodType.RICE), 1)},    100),
    COFFEE              ("Coffee",           "It smells delicious. This is sure to give you a boost.",75, 2,  new Slot[]{new Slot(new Food(FoodType.COFFEE_BEAN), 5)},150),
    JUICE               ("Juice",            "A sweet, nutritious beverage.",                    50,    96, new Slot[]{new Slot(new Food(FoodType.ANY_VEGETABLE),1)}, 170),
    MEAD                ("Mead",             "A fermented beverage made from honey. Drink in moderation.",100,10,new Slot[]{new Slot(new Food(FoodType.HONEY), 1)},300),
    PALE_ALE            ("PaleAle",          "Drink in moderation.",                             50,    72, new Slot[]{new Slot(new Food(FoodType.HOPS), 1)},      300),
    WINE                ("Wine",             "Drink in moderation.",                             100,    168,new Slot[]{new Slot(new Fruit(FruitType.ANY_FRUIT), 1)}, 250),
    DRIED_MUSHROOMS     ("DriedMushrooms",   "A package of gourmet mushrooms.",                  50,    -1, new Slot[]{new Slot(new Food(FoodType.ANY_MUSHROOMS),5)}, -1),
    DRIED_FRUIT         ("DriedFruit",       "Chewy pieces of dried fruit.",                     75,    -1, new Slot[]{new Slot(new Fruit(FruitType.ANY_FRUIT), 5)}, -1),
    RAISINS             ("Raisins",          "It's said to be the Junimos' favorite food.",      125,   -1, new Slot[]{new Slot(new Food(FoodType.GRAPE), 5)},     600),
    COAL                ("Coal",             "Turns 10 pieces of wood into one piece of coal.",   100,   1,  new Slot[]{new Slot(new Etc(EtcType.WOOD), 10)},      50),
    CLOTH               ("Cloth",            "A bolt of fine wool cloth.",                       150,    4,  new Slot[]{new Slot(new Etc(EtcType.WOOL), 1)},      470),
    MAYONNAISE          ("Mayonnaise",       "It looks spreadable.",                             50,    3,  new Slot[]{new Slot(new Etc(EtcType.EGG), 1)},       190),
    BIG_MAYONNAISE      ("Mayonnaise",       "It looks spreadable.",                             50,    3,  new Slot[]{new Slot(new Etc(EtcType.BIG_EGG), 1)},  237),
    DUCK_MAYONNAISE     ("Duck Mayonnaise",  "It's a rich, yellow mayonnaise.",                  75,    3,  new Slot[]{new Slot(new Etc(EtcType.DUCK_EGG), 1)},  37),
    DINOSAUR_MAYONNAISE ("Dinosaur Mayonnaise","Thick and creamy, vivid green, smells like grass and leather.",125,3,new Slot[]{new Slot(new Etc(EtcType.DINOSAUR_EGG),1)},800),
    TRUFFLE_OIL         ("Truffle Oil",      "A gourmet cooking ingredient.",                    38,    6,  new Slot[]{new Slot(new Etc(EtcType.TRUFFLE), 1)},   1065),
    CORN_OIL            ("Oil",              "All purpose cooking oil.",                         13,    6,  new Slot[]{new Slot(new Food(FoodType.CORN), 1)},        100),
    SUNFLOWER_SEED_OIL  ("Oil",              "All purpose cooking oil.",                         13,    48, new Slot[]{new Slot(new Seed(SeedType.SUNFLOWER),1)},100),
    SUNFLOWER_OIL       ("Oil",              "All purpose cooking oil.",                         13,    1,  new Slot[]{new Slot(new Food(FoodType.SUNFLOWER),1)},100),
    PICKLES             ("Pickles",          "A jar of your home-made pickles.",                 20,    6,  new Slot[]{new Slot(new Food(FoodType.ANY_VEGETABLE),1)}, 40),
    JELLY               ("Jelly",            "Gooey.",                                           30,    72, new Slot[]{new Slot(new Fruit(FruitType.ANY_FRUIT),1)}, 50),
    SMOKED_FISH         ("Smoked Fish",      "A whole fish, smoked to perfection.",               60,   1,  new Slot[]{new Slot(new Fish(FishType.ANY_FISH),1),
            new Slot(new Mineral(MineralItemType.COAL_ORE), 1)}, 120),
    METAL_BAR           ("Any metal bar",     "Turns ore and coal into metal bars.",               150,   4,  new Slot[]{new Slot(new Mineral(MineralItemType.ANY_ORE), 5),
            new Slot(new Mineral(MineralItemType.COAL_ORE),1)}, 200);

    private final String name;
    private final String description;
    private final int energy;
    private final int processingTime;
    private final Slot[] ingredients;
    private final int sellPrice;

    ArtisanGoodType(String name,
                    String description,
                    int energy,
                    int processingTime,
                    Slot[] ingredients,
                    int sellPrice)
    {
        this.name           = name;
        this.description    = description;
        this.energy         = energy;
        this.processingTime = processingTime;
        this.ingredients    = ingredients;
        this.sellPrice      = sellPrice;
    }

    public String getName()            { return name; }
    public String getDescription()     { return description; }
    public int    getEnergy()          { return energy; }
    public int    getProcessingTime()  { return processingTime; }
    public Slot[] getIngredients()     { return ingredients; }
    public int    getSellPrice()       { return sellPrice; }
}








//package model.Enums.Items;
//
//
//import model.Enums.GameObjects.CropType;
//import model.GameObject.Crop;
//import model.Ingredient;
//import model.Slot;
//import model.items.*;
//
//public enum ArtisanGoodType implements  ItemType {
//    HONEY("Honey", "It's a sweet syrup produced by bees.", "75", 96, null, "350"),
//    CHEESE("Cheese", "It's your basic cheese.", "100", 3, new Slot[]{new Slot(new Etc(EtcType.MILK), 1)}, "230"),
//    BIG_CHEESE("Cheese", "It's your basic cheese.", "100", 3, new Slot[]{new Slot(new Etc(EtcType.BIG_MILK), 1)}, "345"),
//    GOAT_CHEESE("GoatCheese", "Soft cheese made from goat's milk.", "100", 3, new Slot[]{new Slot(new Etc(EtcType.GOAT_MILK), 1)}, "400"),
//    BIG_GOAT_CHEESE("GoatCheese", "Soft cheese made from goat's milk.", "100", 3, new Slot[]{new Slot(new Etc(EtcType.BIG_GOAT_MILK), 1)}, "600"),
//    BEER("Beer", "Drink in moderation.", "50", 24, new Slot[]{new Slot(new Food(FoodType.WHEAT), 1)}, "200"),
//    VINEGAR("Vinegar", "An aged fermented liquid used in many cooking recipes.", "13", 10, new Slot[]{new Slot(new Food(FoodType.RICE), 1)}, "100"),
//    COFFEE("Coffee", "It smells delicious. This is sure to give you a boost.", "75", 2, new Slot[]{new Slot(new Food(FoodType.COFFEE_BEAN), 5)}, "150"),
//    JUICE("Juice", "A sweet, nutritious beverage.", "2 × Base Ingredient Energy", 96, new Slot[]{new Slot(new Food(FoodType.VEGETABLE_MEDLEY), 1)}, "2.25 × Ingredient Base Price"),
//    MEAD("Mead", "A fermented beverage made from honey. Drink in moderation.", "100", 10, new Slot[]{new Slot(new Food(FoodType.HONEY), 1)}, "300"),
//    PALE_ALE("PaleAle", "Drink in moderation.", "50", 72, new Slot[]{new Slot(new Food(FoodType.HOPS), 1)}, "300"),
//    WINE("Wine", "Drink in moderation.", "1.75 × Base Fruit Energy", 168, new Slot[]{new Slot(new Fruit(FruitType.ANY_FRUIT), 1)}, "3 × Fruit Base Price"),
//    DRIED_MUSHROOMS("DriedMushrooms", "A package of gourmet mushrooms.", "50", -1, new Slot[]{new Slot(new Food(FoodType.ANY_MUSHROOMS), 5)}, "7.5 × Mushroom Base Price + 25g"),
//    DRIED_FRUIT("DriedFruit", "Chewy pieces of dried fruit.", "75", -1, new Slot[]{new Slot(new Fruit(FruitType.ANY_FRUIT)/*be gozesh bayad dorost beshe*/, 5)}, "7.5 × Fruit Base Price + 25g"),
//    RAISINS("Raisins", "It's said to be the Junimos' favorite food.", "125", -1, new Slot[]{new Slot(new Food(FoodType.GRAPE), 5)}, "600"),
//    COAL("Coal", "Turns 10 pieces of wood into one piece of coal.", "Inedible", 1, new Slot[]{new Slot(new Etc(EtcType.WOOD), 10)}, "50"),
//    CLOTH("Cloth", "A bolt of fine wool cloth.", "Inedible", 4, new Slot[]{new Slot(new Etc(EtcType.WOOL), 1)}, "470"),
//    MAYONNAISE("Mayonnaise", "It looks spreadable.", "50", 3, new Slot[]{new Slot(new Etc(EtcType.EGG), 1)}, "190"),
//    BIG_MAYONNAISE("Mayonnaise", "It looks spreadable.", "50", 3, new Slot[]{new Slot(new Etc(EtcType.BIG_EGG), 1)}, "237"),
//    DUCK_MAYONNAISE("Duck Mayonnaise", "It's a rich, yellow mayonnaise.", "75", 3, new Slot[]{new Slot(new Etc(EtcType.DUCK_EGG), 1)}, "37"),
//    DINOSAUR_MAYONNAISE("Dinosaur Mayonnaise", "It's thick and creamy, with a vivid green hue. It smells like grass and leather.", "125", 3, new Slot[]{new Slot(new Etc(EtcType.DINOSAUR_EGG), 1)}, "800"),
//    TRUFFLE_OIL("Truffle Oil", "A gourmet cooking ingredient.", "38", 6, new Slot[]{new Slot(new Etc(EtcType.TRUFFLE), 1)}, "1065"),
//    CORN_OIL("Oil", "All purpose cooking oil.", "13", 6, new Slot[]{new Slot(new Food(FoodType.CORN), 1)}, "100"),
//    SUNFLOWER_SEED_OIL("Oil", "All purpose cooking oil.", "13", 48, new Slot[]{new Slot(new Seed(SeedType.SUNFLOWER) ,1)}, "100"),
//    SUNFLOWER_OIL("Oil", "All purpose cooking oil.", "13", 1, new Slot[]{new Slot(new Food(FoodType.SUNFLOWER), 1)}, "100"),
//    PICKLES("Pickles", "A jar of your home-made pickles.", "1.75 × Base Ingredient Energy", 6, new Slot[]{new Slot(new Food(FoodType.ANY_VEGETABLE), 1)}, "2 × Base Price + 50"),
//    JELLY("Jelly", "Gooey.", "2 × Base Fruit Energy", 72, new Slot[]{new Slot(new Fruit(FruitType.ANY_FRUIT), 1)}, "2 × Base Fruit Price + 50"),
//    SMOKED_FISH("Smoked Fish", "A whole fish, smoked to perfection.", "1.5 × Fish Energy", 1, new Slot[]{new Slot(new Fish(FishType.ANY_FISH), 1), new Slot(new Mineral(MineralItemType.COAL), 1)}, "2 × Fish Price"),
//    METAL_BAR("Any metal bar", "Turns ore and coal into metal bars.", "Inedible", 4, new Slot[]{new Slot(new OreItem(Ore.ANY_ORE), 5), new Slot(new Mineral(MineralItemType.COAL), 1)}, "10 × Ore Price");
//
//    private final String name;
//    private final String description;
//    private final String energy;
//    private final int processingTime;
//    private final Slot[] ingredients;
//    private final String sellPrice;
//
//    ArtisanGoodType(String name, String description, String energy, int processingTime, Slot[] ingredients, String sellPrice) {
//        this.name = name;
//        this.description = description;
//        this.energy = energy;
//        this.processingTime = processingTime;
//        this.ingredients = ingredients;
//        this.sellPrice = sellPrice;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public String getEnergy() {
//        return energy;
//    }
//
//    public int getProcessingTime() {
//        return processingTime;
//    }
//
//    public Slot[] getIngredients() {
//        return ingredients;
//    }
//
//    public String getSellPrice() {
//        return sellPrice;
//    }
//}
