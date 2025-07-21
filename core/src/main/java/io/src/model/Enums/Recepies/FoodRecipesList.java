package io.src.model.Enums.Recepies;

import io.src.model.Enums.Items.EtcType;
import io.src.model.Enums.Items.FishType;
import io.src.model.Enums.Items.FoodType;
import io.src.model.Enums.Items.FruitType;
import io.src.model.items.Saleable;
import io.src.model.Slot;
import io.src.model.items.Etc;
import io.src.model.items.Fish;
import io.src.model.items.Food;
import io.src.model.items.Fruit;

public enum FoodRecipesList implements Saleable {
    FRIED_EGG("Fried Egg", FoodType.FRIED_EGG ,new Slot[]{new Slot(new Etc(EtcType.EGG), 1)}, 50, null, "Starter", 35),
    BAKED_FISH("Baked Fish", FoodType.BAKED_FISH ,new Slot[]{new Slot(new Fish(FishType.Sardine), 1), new Slot(new Fish(FishType.Salmon), 1), new Slot(new Food(FoodType.WHEAT), 1)}, 75, null, "Starter", 100),
    SALAD("Salad", FoodType.SALAD ,new Slot[]{new Slot(new Food(FoodType.LEEK), 1), new Slot(new Food(FoodType.DANDELION), 1)}, 113, null, "Starter", 110),
    OLMELET("Omelet", FoodType.OMELETTE ,new Slot[]{new Slot(new Etc(EtcType.EGG), 1), new Slot(new Etc(EtcType.MILK), 1)}, 100, null, "Stardrop Saloon", 125),
    PUMPKIN_PIE("Pumpkin Pie", FoodType.PUMPKIN_PIE ,new Slot[]{new Slot(new Food(FoodType.PUMPKIN), 1), new Slot(new Food(FoodType.WHEAT_FLOUR), 1), new Slot(new Etc(EtcType.MILK), 1), new Slot(new Food(FoodType.SUGAR), 1)}, 225, null, "Stardrop Saloon", 385),
    SPAGHETTI("Spaghetti", FoodType.SPAGHETTI ,new Slot[]{new Slot(new Food(FoodType.WHEAT_FLOUR), 1), new Slot(new Food(FoodType.TOMATO), 1)}, 75, null, "Stardrop Saloon", 120),
    PIZZA("Pizza", FoodType.PIZZA ,new Slot[]{new Slot(new Food(FoodType.WHEAT_FLOUR), 1), new Slot(new Food(FoodType.TOMATO), 1), new Slot(new Food(FoodType.CHEESE), 1)}, 150, null, "Stardrop Saloon", 300),
    TORTILLA("Tortilla", FoodType.TORTILLA ,new Slot[]{new Slot(new Food(FoodType.CORN), 1)}, 50, null, "Stardrop Saloon", 50),
    MAKI_ROLL("Maki Roll", FoodType.MAKI_ROLL ,new Slot[]{new Slot(new Fish(FishType.ANY_FISH), 1), new Slot(new Food(FoodType.RICE), 1), new Slot(new Etc(EtcType.FIBER), 1)}, 100, null, "Stardrop Saloon", 220),
    TRIPLE_SHOT_ESPRESSO("Triple shot Espresso", FoodType.TRIPLE_SHOT_ESPRESSO ,new Slot[]{new Slot(new Food(FoodType.COFFEE), 3)}, 200, "Max Energy +100 (5 hours)", "Stardrop Saloon", 450),
    COOKIE("Cookie", FoodType.COOKIE ,new Slot[]{new Slot(new Food(FoodType.WHEAT_FLOUR), 1), new Slot(new Food(FoodType.SUGAR), 1), new Slot(new Etc(EtcType.EGG), 1)}, 90, null, "Stardrop Saloon", 140),
    HASH_BROWNS("Hash Browns", FoodType.HASH_BROWNS ,new Slot[]{new Slot(new Food(FoodType.POTATO), 1), new Slot(new Food(FoodType.OIL), 1)}, 90, "Farming (5 hours)", "Stardrop Saloon", 120),
    PANCAKES("Pancakes", FoodType.PANCAKES ,new Slot[]{new Slot(new Food(FoodType.WHEAT_FLOUR), 1), new Slot(new Etc(EtcType.EGG), 1)}, 90, "Foraging (11 hours)", "Stardrop Saloon", 80),
    FRUIT_SALAD("Fruit Salad", FoodType.FRUIT_SALAD ,new Slot[]{new Slot(new Food(FoodType.BLUEBERRY), 1), new Slot(new Food(FoodType.MELON), 1), new Slot(new Fruit(FruitType.APRICOT), 1)}, 263, null, "Stardrop Saloon", 450),
    RED_PLATE("Red Plate", FoodType.RED_PLATE ,new Slot[]{new Slot(new Food(FoodType.RED_CABBAGE), 1), new Slot(new Food(FoodType.RADISH), 1)}, 240, "Max Energy +50 (3 hours)", "Stardrop Saloon", 400),
    BREAD("Bread", FoodType.BREAD ,new Slot[]{new Slot(new Food(FoodType.WHEAT_FLOUR), 1)}, 50, null, "Stardrop Saloon", 60),
    SALMON_DINNER("Salmon Dinner", FoodType.SALMON_DINNER ,new Slot[]{new Slot(new Fish(FishType.Salmon), 1), new Slot(new Food(FoodType.AMARANTH), 1), new Slot(new Food(FoodType.KALE), 1)}, 125, null, "Leah reward", 300),
    VEGETABLE_MEDLEY("Vegetable Medley", FoodType.VEGETABLE_MEDLEY ,new Slot[]{new Slot(new Food(FoodType.TOMATO), 1), new Slot(new Food(FoodType.BEET), 1)}, 165, null, "Foraging Level 2", 120),
    FARMERS_LUNCH("Farmers Lunch", FoodType.FARMERS_LUNCH ,new Slot[]{new Slot(new Food(FoodType.OMELETTE), 1), new Slot(new Food(FoodType.PARSNIP), 1)}, 200, "Farming (5 hours)", "Farming Level 1", 150),
    SURVIVAL_BURGER("Survival Burger", FoodType.SURVIVAL_BURGER ,new Slot[]{new Slot(new Food(FoodType.BREAD), 1), new Slot(new Food(FoodType.CARROT), 1), new Slot(new Food(FoodType.EGGPLANT), 1)}, 125, "Foraging (5 hours)", "Foraging Level 3", 180),
    DISH_O_THE_SEA("Dish O The Sea", FoodType.DISH_OF_THE_SEA ,new Slot[]{new Slot(new Fish(FishType.Sardine), 2), new Slot(new Food(FoodType.HASH_BROWNS), 1)}, 150, "Fishing (5 hours)", "Fishing Level 2", 220),
    SEAFOAM_PUDDING("Seafoam Pudding", FoodType.SEAFORM_PUDDING ,new Slot[]{new Slot(new Fish(FishType.Flounder), 1), new Slot(new Fish(FishType.MidnightCarp), 1)}, 175, "Fishing (10 hours)", "Fishing Level 3", 300),
    MINERS_TREAT("Miners Treat", FoodType.MINERS_TREAT ,new Slot[]{new Slot(new Food(FoodType.CARROT), 2), new Slot(new Food(FoodType.SUGAR), 1), new Slot(new Etc(EtcType.MILK), 1)}, 125, "Mining (5 hours)", "Mining Level 1", 200);


    public final String name;
    public final FoodType foodType;
    public final Slot[] ingredients;
    public final int energy;
    public final String effect;
    public final String source;
    public final int sellPrice;

    FoodRecipesList(String name, FoodType foodType ,Slot[] ingredients, int energy, String effect, String source, int sellPrice) {
        this.name = name;
        this.foodType = foodType;
        this.ingredients = ingredients;
        this.energy = energy;
        this.effect = effect;
        this.source = source;
        this.sellPrice = sellPrice;
    }

    @Override
    public String getName() {
        return name;
    }

    public static FoodRecipesList fromName(String name) {
        for (FoodRecipesList type : FoodRecipesList.values()) {
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}
