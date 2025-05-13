package model.Enums.Items;


import model.Ingredient;

public enum ArtisanGoodType {
    HONEY("Honey", "It's a sweet syrup produced by bees.", "75", 96, null, "350"),
    CHEESE("Cheese", "It's your basic cheese.", "100", 3, new Ingredient[]{new Ingredient("Milk", 1)}, "230"),
    BIG_CHEESE("Cheese", "It's your basic cheese.", "100", 3, new Ingredient[]{new Ingredient("LargeMilk", 1)}, "345"),
    GOAT_CHEESE("GoatCheese", "Soft cheese made from goat's milk.", "100", 3, new Ingredient[]{new Ingredient("Goat Milk", 1)}, "400"),
    BIG_GOAT_CHEESE("GoatCheese", "Soft cheese made from goat's milk.", "100", 3, new Ingredient[]{new Ingredient("Large Goat Milk", 1)}, "600"),
    BEER("Beer", "Drink in moderation.", "50", 24, new Ingredient[]{new Ingredient("Wheat", 1)}, "200"),
    VINEGAR("Vinegar", "An aged fermented liquid used in many cooking recipes.", "13", 10, new Ingredient[]{new Ingredient("Rice", 1)}, "100"),
    COFFEE("Coffee", "It smells delicious. This is sure to give you a boost.", "75", 2, new Ingredient[]{new Ingredient("CoffeeBean", 5)}, "150"),
    JUICE("Juice", "A sweet, nutritious beverage.", "2 × Base Ingredient Energy", 96, new Ingredient[]{new Ingredient("any vegetable", 1)}, "2.25 × Ingredient Base Price"),
    MEAD("Mead", "A fermented beverage made from honey. Drink in moderation.", "100", 10, new Ingredient[]{new Ingredient("Honey", 1)}, "300"),
    PALE_ALE("PaleAle", "Drink in moderation.", "50", 72, new Ingredient[]{new Ingredient("Hops", 1)}, "300"),
    WINE("Wine", "Drink in moderation.", "1.75 × Base Fruit Energy", 168, new Ingredient[]{new Ingredient("Any Fruit", 1)}, "3 × Fruit Base Price"),
    DRIED_MUSHROOMS("DriedMushrooms", "A package of gourmet mushrooms.", "50", -1, new Ingredient[]{new Ingredient("Any Mushroom", 5)}, "7.5 × Mushroom Base Price + 25g"),
    DRIED_FRUIT("DriedFruit", "Chewy pieces of dried fruit.", "75", -1, new Ingredient[]{new Ingredient("Any Fruit (except Grapes)", 5)}, "7.5 × Fruit Base Price + 25g"),
    RAISINS("Raisins", "It's said to be the Junimos' favorite food.", "125", -1, new Ingredient[]{new Ingredient("Grapes", 5)}, "600"),
    COAL("Coal", "Turns 10 pieces of wood into one piece of coal.", "Inedible", 1, new Ingredient[]{new Ingredient("Wood", 10)}, "50"),
    CLOTH("Cloth", "A bolt of fine wool cloth.", "Inedible", 4, new Ingredient[]{new Ingredient("Wool", 1)}, "470"),
    MAYONNAISE("Mayonnaise", "It looks spreadable.", "50", 3, new Ingredient[]{new Ingredient("Egg", 1)}, "190"),
    BIG_MAYONNAISE("Mayonnaise", "It looks spreadable.", "50", 3, new Ingredient[]{new Ingredient("Large Egg", 1)}, "237"),
    DUCK_MAYONNAISE("Duck Mayonnaise", "It's a rich, yellow mayonnaise.", "75", 3, new Ingredient[]{new Ingredient("Duck Egg", 1)}, "37"),
    DINOSAUR_MAYONNAISE("Dinosaur Mayonnaise", "It's thick and creamy, with a vivid green hue. It smells like grass and leather.", "125", 3, new Ingredient[]{new Ingredient("Dinosaur Egg", 1)}, "800"),
    TRUFFLE_OIL("Truffle Oil", "A gourmet cooking ingredient.", "38", 6, new Ingredient[]{new Ingredient("Truffle", 1)}, "1065"),
    CORN_OIL("Oil", "All purpose cooking oil.", "13", 6, new Ingredient[]{new Ingredient("Corn", 1)}, "100"),
    SUNFLOWER_SEED_OIL("Oil", "All purpose cooking oil.", "13", 48, new Ingredient[]{new Ingredient("Sunflower Seeds", 1)}, "100"),
    SUNFLOWER_OIL("Oil", "All purpose cooking oil.", "13", 1, new Ingredient[]{new Ingredient("Sunflower", 1)}, "100"),
    PICKLES("Pickles", "A jar of your home-made pickles.", "1.75 × Base Ingredient Energy", 6, new Ingredient[]{new Ingredient("Any Vegetable", 1)}, "2 × Base Price + 50"),
    JELLY("Jelly", "Gooey.", "2 × Base Fruit Energy", 72, new Ingredient[]{new Ingredient("Any Fruit", 1)}, "2 × Base Fruit Price + 50"),
    SMOKED_FISH("Smoked Fish", "A whole fish, smoked to perfection.", "1.5 × Fish Energy", 1, new Ingredient[]{new Ingredient("Any Fish", 1), new Ingredient("Coal", 1)}, "2 × Fish Price"),
    METAL_BAR("Any metal bar", "Turns ore and coal into metal bars.", "Inedible", 4, new Ingredient[]{new Ingredient("Any Ore", 5), new Ingredient("Coal", 1)}, "10 × Ore Price");

    private final String name;
    private final String description;
    private final String energy;
    private final int processingTime;
    private final Ingredient[] ingredients;
    private final String sellPrice;

    ArtisanGoodType(String name, String description, String energy, int processingTime, Ingredient[] ingredients, String sellPrice) {
        this.name = name;
        this.description = description;
        this.energy = energy;
        this.processingTime = processingTime;
        this.ingredients = ingredients;
        this.sellPrice = sellPrice;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getEnergy() {
        return energy;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public Ingredient[] getIngredients() {
        return ingredients;
    }

    public String getSellPrice() {
        return sellPrice;
    }
}
