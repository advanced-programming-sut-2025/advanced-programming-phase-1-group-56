package model.Enums.Registery;


import model.Ingredient;

public enum ArtisanGoods {
    HONEY("Honey", "It's a sweet syrup produced by bees.", 75, "4 Days", null, 350),
    CHEESE("Cheese", "It's your basic cheese.", 100, "3 Hours", new Ingredient[]{new Ingredient("Milk",1),new Ingredient("LargeMilk",1)}, 230),
    GOAT_CHEESE("GoatCheese", "Soft cheese made from goat's milk.", 100, "3 Hours", new Ingredient[]{new Ingredient("Goat Milk",1),new Ingredient("Large Goat Milk",1)}, 400),
    BEER("Beer", "Drink in moderation.", 50, "1 Day", new Ingredient[]{new Ingredient("Wheat",1)}, 200),
    VINEGAR("Vinegar", "An aged fermented liquid used in many cooking recipes.", 13, "10 Hours", new Ingredient[]{new Ingredient("Rice",1)}, 100),
    COFFEE("Coffee", "It smells delicious. This is sure to give you a boost.", 75, "2 Hours", new Ingredient[]{new Ingredient("CoffeeBean" ,5)}, 150),
    JUICE("Juice", "A sweet, nutritious beverage.", 2, "4 Days", new Ingredient[]{new Ingredient("any vegetable",1)}, 2.25),
    MEAD("Mead", "A fermented beverage made from honey. Drink in moderation.", 100, "10 hours", new Ingredient[]{new Ingredient("Honey",1)}, 300),
    PALE_ALE("PaleAle", "Drink in moderation.", 50, "3 Days", new Ingredient[]{new Ingredient("Hops",1)}, 300),
    WINE("Wine", "Drink in moderation.", 1.75, "7 Days", new Ingredient[]{new Ingredient("Any Fruit",1)}, 3),
    DRIED_MUSHROOMS("DriedMushrooms", "A package of gourmet mushrooms.", 50, "Ready the next morning", new Ingredient[]{new Ingredient("Any Mushroom",5)}, 7.5),
    DRIED_FRUIT("DriedFruit", "Chewy pieces of dried fruit.", 75, "Ready the next morning", new Ingredient[]{new Ingredient("Any Fruit (except Grapes)",5)}, 7.5),
    RAISINS("Raisins", "It's said to be the Junimos' favorite food.", 125, "Ready the next morning", new Ingredient[]{new Ingredient("Grapes",5)}, 600),
    COAL("Coal", "Turns 10 pieces of wood into one piece of coal.", 0, "1 Hour", new Ingredient[]{new Ingredient("Wood",10)}, 50);

    private final String name;
    private final String description;
    private final double energy;
    private final String processingTime;
    private final Ingredient[] ingredients;
    private final double sellPrice;

    ArtisanGoods(String name, String description, double energy, String processingTime, Ingredient[] ingredients, double sellPrice) {
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

    public double getEnergy() {
        return energy;
    }

    public String getProcessingTime() {
        return processingTime;
    }

    public Ingredient[] getIngredients() {
        return ingredients;
    }

    public double getSellPrice() {
        return sellPrice;
    }
}
