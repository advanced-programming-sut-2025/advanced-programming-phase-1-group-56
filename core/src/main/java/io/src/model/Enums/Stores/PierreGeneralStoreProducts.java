package io.src.model.Enums.Stores;

import io.src.model.Enums.BackPackType;
import io.src.model.Enums.GameObjects.TreeType;
import io.src.model.Enums.Items.EtcType;
import io.src.model.Enums.Items.FoodType;
import io.src.model.Enums.Items.SeedType;
import io.src.model.Enums.Recepies.CraftingRecipesList;
import io.src.model.Enums.WeatherAndTime.Seasons;
import io.src.model.GameObject.NPC.NpcProduct;
import io.src.model.items.Etc;
import io.src.model.items.Food;
import io.src.model.items.Seed;

import java.util.ArrayList;

public enum PierreGeneralStoreProducts implements Store {
    // --- Year-Round Stock (from provided list) ---
    RICE(
            new NpcProduct(
                    "RICE",
                    new Food(FoodType.RICE),
                    "A basic grain often served under vegetables.",
                    200,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    Integer.MAX_VALUE
            )
    ),
    WHEAT_FLOUR(
            new NpcProduct(
                    "WHEAT_FLOUR",
                    new Food(FoodType.WHEAT_FLOUR),
                    "A common cooking ingredient made from crushed wheat seeds.",
                    100,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    Integer.MAX_VALUE
            )
    ),
    BOUQUET(
            new NpcProduct(
                    "BOUQUET",
                    new Etc(EtcType.BOUQUET),
                    "A gift that shows your romantic interest.\n(Unlocked after reaching level 2 friendship with a player)",
                    1000,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    2
            )
    ),
    WEDDING_RING(
            new NpcProduct(
                    "WEDDING_RING",
                    new Etc(EtcType.WEDDING_RING),
                    "It's used to ask for another farmer's hand in marriage.\n(Unlocked after reaching level 3 friendship with a player)",
                    10000,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    2
            )
    ),
    DEHYDRATOR_RECIPE(
            new NpcProduct(
                    "DEHYDRATOR_RECIPE",
                    CraftingRecipesList.Dehydrator,
                    "A recipe to make Dehydrator",
                    10000,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    1
            )
    ),
    GRASS_STARTER_RECIPE(
            new NpcProduct(
                    "GRASS_STARTER_RECIPE",
                    CraftingRecipesList.GrassStarter,
                    "A recipe to make Grass Starter",
                    1000,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    1
            )
    ),
    SUGAR(
            new NpcProduct(
                    "SUGAR",
                    new Food(FoodType.SUGAR),
                    "Adds sweetness to pastries and candies. Too much can be unhealthy.",
                    100,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    Integer.MAX_VALUE
            )
    ),
    OIL(
            new NpcProduct(
                    "OIL",
                    new Food(FoodType.OIL),
                    "All purpose cooking oil.",
                    200,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    Integer.MAX_VALUE
            )
    ),
    VINEGAR(
            new NpcProduct(
                    "VINEGAR",
                    new Food(FoodType.VINEGAR),
                    "An aged fermented liquid used in many cooking recipes.",
                    200,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    Integer.MAX_VALUE
            )
    ),
    DELUXE_RETAINING_SOIL(
            new NpcProduct(
                    "DELUXE_RETAINING_SOIL",
                    new Etc(EtcType.DELUXE_RETAINING_SOIL),
                    "This soil has a 100% chance of staying watered overnight. Mix into tilled soil.",
                    150,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    Integer.MAX_VALUE
            )
    ),
    GRASS_STARTER(
            new NpcProduct(
                    "GRASS_STARTER",
                    new Etc(EtcType.GRASS_STARTER),
                    "Place this on your farm to start a new patch of grass.",
                    100,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    Integer.MAX_VALUE
            )
    ),
    SPEED_GRO(
            new NpcProduct(
                    "SPEED_GRO",
                    new Etc(EtcType.SPEED_GRO),
                    "Makes the plants grow 1 day earlier.",
                    100,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    Integer.MAX_VALUE
            )
    ),
    APPLE_SAPLING(
            new NpcProduct(
                    "APPLE_SAPLING",
                    TreeType.APPLE_TREE,
                    "Takes 28 days to produce a mature Apple tree. Bears fruit in the fall. Only grows if the 8 surrounding \"tiles\" are empty.",
                    4000,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    Integer.MAX_VALUE
            )
    ),
    APRICOT_SAPLING(
            new NpcProduct(
                    "APRICOT_SAPLING",
                    TreeType.APRICOT_TREE,
                    "Takes 28 days to produce a mature Apricot tree. Bears fruit in the spring. Only grows if the 8 surrounding \"tiles\" are empty.",
                    2000,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    Integer.MAX_VALUE
            )
    ),
    CHERRY_SAPLING(
            new NpcProduct(
                    "CHERRY_SAPLING",
                    TreeType.CHERRY_TREE,//TODO
                    "Takes 28 days to produce a mature Cherry tree. Bears fruit in the spring. Only grows if the 8 surrounding \"tiles\" are empty.",
                    3400,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    Integer.MAX_VALUE
            )
    ),
    ORANGE_SAPLING(
            new NpcProduct(
                    "ORANGE_SAPLING",
                    TreeType.ORANGE_TREE,
                    "Takes 28 days to produce a mature Orange tree. Bears fruit in the summer. Only grows if the 8 surrounding \"tiles\" are empty.",
                    4000,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    Integer.MAX_VALUE
            )
    ),
    PEACH_SAPLING(
            new NpcProduct(
                    "PEACH_SAPLING",
                    TreeType.PEACH_TREE,
                    "Takes 28 days to produce a mature Peach tree. Bears fruit in the summer. Only grows if the 8 surrounding \"tiles\" are empty.",
                    6000,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    Integer.MAX_VALUE
            )
    ),
    POMEGRANATE_SAPLING(
            new NpcProduct(
                    "POMEGRANATE_SAPLING",
                    TreeType.POMEGRANATE_TREE,
                    "Takes 28 days to produce a mature Pomegranate tree. Bears fruit in the fall. Only grows if the 8 surrounding \"tiles\" are empty.",
                    6000,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    Integer.MAX_VALUE
            )
    ),
    BASIC_RETAINING_SOIL(
            new NpcProduct(
                    "BASIC_RETAINING_SOIL",
                    new Etc(EtcType.BASIC_RETAINING_SOIL),
                    "This soil has a chance of staying watered overnight. Mix into tilled soil.",
                    100,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    Integer.MAX_VALUE
            )
    ),
    QUALITY_RETAINING_SOIL(
            new NpcProduct(
                    "QUALITY_RETAINING_SOIL",
                    new Etc(EtcType.QUALITY_RETAINING_SOIL),
                    "This soil has a good chance of staying watered overnight. Mix into tilled soil.",
                    150,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    Integer.MAX_VALUE
            )
    ),

    // --- Backpack Stock ---
    LARGE_PACK(
            new NpcProduct(
                    "LARGE_PACK",
                    BackPackType.BigBackpack,
                    "Unlocks the 2nd row of inventory (12 more slots, total 24).",
                    2000,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    1
            )
    ),
    DELUXE_PACK(
            new NpcProduct(
                    "DELUXE_PACK",
                    BackPackType.DeluxeBackpack,
                    "Unlocks the 3rd row of inventory (infinite slots).",
                    10000,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    1
            )
    ),
    PARSNIP_SEEDS(
            new NpcProduct(
                    "PARSNIP_SEEDS",
                    new Seed(SeedType.PARSNIP),
                    "Plant these in the spring. Takes 4 days to mature.",
                    20,
                    30,
                    new Seasons[]{Seasons.Spring},
                    5
            )
    ),
    BEAN_STARTER(
            new NpcProduct(
                    "BEAN_STARTER",
                    new Seed(SeedType.BEAN_STARTER),
                    "Plant these in the spring. Takes 10 days to mature, but keeps producing after that. Grows on a trellis.",
                    60,
                    90,
                    new Seasons[]{Seasons.Spring},
                    5
            )
    ),
    CAULIFLOWER_SEEDS(
            new NpcProduct(
                    "CAULIFLOWER_SEEDS",
                    new Seed(SeedType.CAULIFLOWER),
                    "Plant these in the spring. Takes 12 days to produce a large cauliflower.",
                    80,
                    120,
                    new Seasons[]{Seasons.Spring},
                    5
            )
    ),
    POTATO_SEEDS(
            new NpcProduct(
                    "POTATO_SEEDS",
                    new Seed(SeedType.POTATO),
                    "Plant these in the spring. Takes 6 days to mature, and has a chance of yielding multiple potatoes at harvest.",
                    50,
                    75,
                    new Seasons[]{Seasons.Spring},
                    5
            )
    ),
    TULIP_BULB(
            new NpcProduct(
                    "TULIP_BULB",
                    new Seed(SeedType.TULIP),
                    "Plant in spring. Takes 6 days to produce a colorful flower. Assorted colors.",
                    20,
                    30,
                    new Seasons[]{Seasons.Spring},
                    5
            )
    ),
    KALE_SEEDS(
            new NpcProduct(
                    "KALE_SEEDS",
                    new Seed(SeedType.KALE),
                    "Plant these in the spring. Takes 6 days to mature. Harvest with the scythe.",
                    70,
                    105,
                    new Seasons[]{Seasons.Spring},
                    5
            )
    ),
    JAZZ_SEEDS(
            new NpcProduct(
                    "JAZZ_SEEDS",
                    new Seed(SeedType.JAZZ),
                    "Plant in spring. Takes 7 days to produce a blue puffball flower.",
                    30,
                    45,
                    new Seasons[]{Seasons.Spring},
                    5
            )
    ),
    GARLIC_SEEDS(
            new NpcProduct(
                    "GARLIC_SEEDS",
                    new Seed(SeedType.GARLIC),
                    "Plant these in the spring. Takes 4 days to mature.",
                    40,
                    60,
                    new Seasons[]{Seasons.Spring},
                    5
            )
    ),
    RICE_SHOOT(
            new NpcProduct(
                    "RICE_SHOOT",
                    new Seed(SeedType.RICE_SHOOT),
                    "Plant these in the spring. Takes 8 days to mature. Grows faster if planted near a body of water. Harvest with the scythe.",
                    40,
                    60,
                    new Seasons[]{Seasons.Spring},
                    5
            )
    ),
    // --- Summer Stock ---
    MELON_SEEDS(
            new NpcProduct(
                    "MELON_SEEDS",
                    new Seed(SeedType.MELON),
                    "Plant these in the summer. Takes 12 days to mature.",
                    80,
                    120,
                    new Seasons[]{Seasons.Summer},
                    5
            )
    ),
    TOMATO_SEEDS(
            new NpcProduct(
                    "TOMATO_SEEDS",
                    new Seed(SeedType.TOMATO),
                    "Plant these in the summer. Takes 11 days to mature, and continues to produce after first harvest.",
                    50,
                    75,
                    new Seasons[]{Seasons.Summer},
                    5
            )
    ),
    BLUEBERRY_SEEDS(
            new NpcProduct(
                    "BLUEBERRY_SEEDS",
                    new Seed(SeedType.BLUEBERRY),
                    "Plant these in the summer. Takes 13 days to mature, and continues to produce after first harvest.",
                    80,
                    120,
                    new Seasons[]{Seasons.Summer},
                    5
            )
    ),
    PEPPER_SEEDS(
            new NpcProduct(
                    "PEPPER_SEEDS",
                    new Seed(SeedType.PEPPER),
                    "Plant these in the summer. Takes 5 days to mature, and continues to produce after first harvest.",
                    40,
                    60,
                    new Seasons[]{Seasons.Summer},
                    5
            )
    ),
    WHEAT_SEEDS(
            new NpcProduct(
                    "WHEAT_SEEDS",
                    new Seed(SeedType.WHEAT),
                    "Plant these in the summer or fall. Takes 4 days to mature. Harvest with the scythe.",
                    10,
                    15,
                    new Seasons[]{Seasons.Summer, Seasons.Fall},
                    5
            )
    ),
    RADISH_SEEDS(
            new NpcProduct(
                    "RADISH_SEEDS",
                    new Seed(SeedType.RADISH),
                    "Plant these in the summer. Takes 6 days to mature.",
                    40,
                    60,
                    new Seasons[]{Seasons.Summer},
                    5
            )
    ),
    POPPY_SEEDS(
            new NpcProduct(
                    "POPPY_SEEDS",
                    new Seed(SeedType.POPPY),
                    "Plant in summer. Produces a bright red flower in 7 days.",
                    100,
                    150,
                    new Seasons[]{Seasons.Summer},
                    5
            )
    ),
    SPANGLE_SEEDS(
            new NpcProduct(
                    "SPANGLE",
                    new Seed(SeedType.SPANGLE),
                    "Plant in summer. Takes 8 days to produce a vibrant tropical flower. Assorted colors.",
                    50,
                    75,
                    new Seasons[]{Seasons.Summer},
                    5
            )
    ),
    HOPS_STARTER(
            new NpcProduct(
                    "HOPS_STARTER",
                    new Seed(SeedType.HOPS),
                    "Plant these in the summer. Takes 11 days to grow, but keeps producing after that. Grows on a trellis.",
                    60,
                    90,
                    new Seasons[]{Seasons.Summer},
                    5
            )
    ),
    CORN_SEEDS(
            new NpcProduct(
                    "CORN_SEEDS",
                    new Seed(SeedType.CORN),
                    "Plant these in the summer or fall. Takes 14 days to mature, and continues to produce after first harvest.",
                    150,
                    225,
                    new Seasons[]{Seasons.Summer, Seasons.Fall},
                    5
            )
    ),
    SUNFLOWER_SEEDS(
            new NpcProduct(
                    "SUNFLOWER_SEEDS",
                    new Seed(SeedType.SUNFLOWER),
                    "Plant in summer or fall. Takes 8 days to produce a large sunflower. Yields more seeds at harvest.",
                    200,
                    300,
                    new Seasons[]{Seasons.Summer, Seasons.Fall},
                    5
            )
    ),
    RED_CABBAGE_SEEDS(
            new NpcProduct(
                    "RED_CABBAGE_SEEDS",
                    new Seed(SeedType.RED_CABBAGE),
                    "Plant these in the summer. Takes 9 days to mature.",
                    100,
                    150,
                    new Seasons[]{Seasons.Summer},
                    5
            )
    ),
    // --- Fall Seasonal Stock ---
    EGGPLANT_SEEDS(
            new NpcProduct(
                    "EGGPLANT_SEEDS",
                    new Seed(SeedType.EGGPLANT),
                    "Plant these in the fall. Takes 5 days to mature, and continues to produce after first harvest.",
                    20,
                    30,
                    new Seasons[]{Seasons.Fall},
                    5
            )
    ),
//    CORN_SEEDS(
//            new NpcProduct(
//                    "CORN_SEEDS",
//                    new Seed(SeedType.CORN),
//                    "Plant these in the summer or fall. Takes 14 days to mature, and continues to produce after first harvest.",
//                    150,
//                    225,
//                    new Seasons[]{Seasons.Fall},
//                    5
//            )
//    ),
    PUMPKIN_SEEDS(
            new NpcProduct(
                    "PUMPKIN_SEEDS",
                    new Seed(SeedType.PUMPKIN),
                    "Plant these in the fall. Takes 13 days to mature.",
                    100,
                    150,
                    new Seasons[]{Seasons.Fall},
                    5
            )
    ),
    BOK_CHOY_SEEDS(
            new NpcProduct(
                    "BOK_CHOY_SEEDS",
                    new Seed(SeedType.BOK_CHOY),
                    "Plant these in the fall. Takes 4 days to mature.",
                    50,
                    75,
                    new Seasons[]{Seasons.Fall},
                    5
            )
    ),
    YAM_SEEDS(
            new NpcProduct(
                    "YAM_SEEDS",
                    new Seed(SeedType.YAM),
                    "Plant these in the fall. Takes 10 days to mature.",
                    60,
                    90,
                    new Seasons[]{Seasons.Fall},
                    5
            )
    ),
    CRANBERRY_SEEDS(
            new NpcProduct(
                    "CRANBERRY_SEEDS",
                    new Seed(SeedType.CRANBERRY),
                    "Plant these in the fall. Takes 7 days to mature, and continues to produce after first harvest.",
                    240,
                    360,
                    new Seasons[]{Seasons.Fall},
                    5
            )
    ),
//    SUNFLOWER_SEEDS(
//            new NpcProduct(
//                    "SUNFLOWER_SEEDS",
//                    new Seed(SeedType.SUNFLOWER),
//                    "Plant in summer or fall. Takes 8 days to produce a large sunflower. Yields more seeds at harvest.",
//                    200,
//                    300,
//                    new Seasons[]{Seasons.Fall},
//                    5
//            )
//    ),
    FAIRY_SEEDS(
            new NpcProduct(
                    "FAIRY_SEEDS",
                    new Seed(SeedType.FAIRY),
                    "Plant in fall. Takes 12 days to produce a mysterious flower. Assorted Colors.",
                    200,
                    300,
                    new Seasons[]{Seasons.Fall},
                    5
            )
    ),
    AMARANTH_SEEDS(
            new NpcProduct(
                    "AMARANTH_SEEDS",
                    new Seed(SeedType.AMARANTH),
                    "Plant these in the fall. Takes 7 days to grow. Harvest with the scythe.",
                    70,
                    105,
                    new Seasons[]{Seasons.Fall},
                    5
            )
    ),
    GRAPE_STARTER(
            new NpcProduct(
                    "GRAPE_STARTER",
                    new Seed(SeedType.GRAPE),
                    "Plant these in the fall. Takes 10 days to grow, but keeps producing after that. Grows on a trellis.",
                    60,
                    90,
                    new Seasons[]{Seasons.Fall},
                    5
            )
    ),
//    WHEAT_SEEDS(
//            new NpcProduct(
//                    "WHEAT_SEEDS",
//                    new Seed(SeedType.WHEAT),
//                    "Plant these in the summer or fall. Takes 4 days to mature. Harvest with the scythe.",
//                    10,
//                    15,
//                    new Seasons[]{Seasons.Fall},
//                    5
//            )
//    ),
    ARTICHOKE_SEEDS(
            new NpcProduct(
                    "ARTICHOKE_SEEDS",
                    new Seed(SeedType.ARTICHOKE),
                    "Plant these in the fall. Takes 8 days to mature.",
                    30,
                    45,
                    new Seasons[]{Seasons.Fall},
                    5
            )
    );
    public final NpcProduct npcProduct;

    PierreGeneralStoreProducts(NpcProduct npcProduct) {
        this.npcProduct = npcProduct;
    }

    @Override
    public NpcProduct getProduct() {
        return npcProduct;
    }

    public static <T extends Enum<T> & Store> ArrayList<NpcProduct> getProducts(Class<T> enumClass) {
        ArrayList<NpcProduct> products = new ArrayList<>();
        for (T product : enumClass.getEnumConstants()) {
            products.add(product.getProduct());
        }
        return products;
    }

}
