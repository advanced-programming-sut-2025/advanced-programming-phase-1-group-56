package model.Enums.Registery;

import model.Ingredient;

public enum FoodRecipesList {
    FRIED_EGG(new Ingredient[]{new Ingredient("Egg", 1)}, 50, null, "Starter", 35),
    BAKED_FISH(new Ingredient[]{new Ingredient("Sardine", 1), new Ingredient("Salmon", 1), new Ingredient("Wheat", 1)}, 75, null, "Starter", 100),
    SALAD(new Ingredient[]{new Ingredient("Leek", 1), new Ingredient("Dandelion", 1)}, 113, null, "Starter", 110),
    OLMELET(new Ingredient[]{new Ingredient("Egg", 1), new Ingredient("Milk", 1)}, 100, null, "Stardrop Saloon", 125),
    PUMPKIN_PIE(new Ingredient[]{new Ingredient("Pumpkin", 1), new Ingredient("Wheat Flour", 1), new Ingredient("Milk", 1), new Ingredient("Sugar", 1)}, 225, null, "Stardrop Saloon", 385),
    SPAGHETTI(new Ingredient[]{new Ingredient("Wheat Flour", 1), new Ingredient("Tomato", 1)}, 75, null, "Stardrop Saloon", 120),
    PIZZA(new Ingredient[]{new Ingredient("Wheat Flour", 1), new Ingredient("Tomato", 1), new Ingredient("Cheese", 1)}, 150, null, "Stardrop Saloon", 300),
    TORTILLA(new Ingredient[]{new Ingredient("Corn", 1)}, 50, null, "Stardrop Saloon", 50),
    MAKI_ROLL(new Ingredient[]{new Ingredient("Any Fish", 1), new Ingredient("Rice", 1), new Ingredient("Fiber", 1)}, 100, null, "Stardrop Saloon", 220),
    TRIPLE_SHOT_ESPRESSO(new Ingredient[]{new Ingredient("Coffee", 3)}, 200, "Max Energy +100 (5 hours)", "Stardrop Saloon", 450),
    COOKIE(new Ingredient[]{new Ingredient("Wheat Flour", 1), new Ingredient("Sugar", 1), new Ingredient("Egg", 1)}, 90, null, "Stardrop Saloon", 140),
    HASH_BROWNS(new Ingredient[]{new Ingredient("Potato", 1), new Ingredient("Oil", 1)}, 90, "Farming (5 hours)", "Stardrop Saloon", 120),
    PANCAKES(new Ingredient[]{new Ingredient("Wheat Flour", 1), new Ingredient("Egg", 1)}, 90, "Foraging (11 hours)", "Stardrop Saloon", 80),
    FRUIT_SALAD(new Ingredient[]{new Ingredient("Blueberry", 1), new Ingredient("Melon", 1), new Ingredient("Apricot", 1)}, 263, null, "Stardrop Saloon", 450),
    RED_PLATE(new Ingredient[]{new Ingredient("Red Cabbage", 1), new Ingredient("Radish", 1)}, 240, "Max Energy +50 (3 hours)", "Stardrop Saloon", 400),
    BREAD(new Ingredient[]{new Ingredient("Wheat Flour", 1)}, 50, null, "Stardrop Saloon", 60),
    SALMON_DINNER(new Ingredient[]{new Ingredient("Salmon", 1), new Ingredient("Amaranth", 1), new Ingredient("Kale", 1)}, 125, null, "Leah reward", 300),
    VEGETABLE_MEDLEY(new Ingredient[]{new Ingredient("Tomato", 1), new Ingredient("Beet", 1)}, 165, null, "Foraging Level 2", 120),
    FARMERS_LUNCH(new Ingredient[]{new Ingredient("Omelet", 1), new Ingredient("Parsnip", 1)}, 200, "Farming (5 hours)", "Farming Level 1", 150),
    SURVIVAL_BURGER(new Ingredient[]{new Ingredient("Bread", 1), new Ingredient("Carrot", 1), new Ingredient("Eggplant", 1)}, 125, "Foraging (5 hours)", "Foraging Level 3", 180),
    DISH_O_THE_SEA(new Ingredient[]{new Ingredient("Sardine", 2), new Ingredient("Hash Browns", 1)}, 150, "Fishing (5 hours)", "Fishing Level 2", 220),
    SEAFOAM_PUDDING(new Ingredient[]{new Ingredient("Flounder", 1), new Ingredient("Midnight Carp", 1)}, 175, "Fishing (10 hours)", "Fishing Level 3", 300),
    MINERS_TREAT(new Ingredient[]{new Ingredient("Carrot", 2), new Ingredient("Sugar", 1), new Ingredient("Milk", 1)}, 125, "Mining (5 hours)", "Mining Level 1", 200);

    public final Ingredient[] ingredients;
    public final int energy;
    public final String effect;
    public final String source;
    public final int sellPrice;

    FoodRecipesList(Ingredient[] ingredients, int energy, String effect, String source, int sellPrice) {
        this.ingredients = ingredients;
        this.energy = energy;
        this.effect = effect;
        this.source = source;
        this.sellPrice = sellPrice;
    }
}
