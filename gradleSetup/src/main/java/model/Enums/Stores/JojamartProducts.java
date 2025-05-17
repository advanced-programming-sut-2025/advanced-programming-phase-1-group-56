package model.Enums.Stores;

import model.Enums.Items.EtcType;
import model.Enums.Items.FoodType;
import model.Enums.Items.SeedType;
import model.Enums.WeatherAndTime.Seasons;
import model.GameObject.NPC.NpcProduct;
import model.items.Etc;
import model.items.Food;
import model.items.Seed;

import java.util.ArrayList;

public enum JojamartProducts implements Store {
    // --- Permanent Stock ---
    JOJA_COLA(
            new NpcProduct(
                    "JOJA_COLA",
                    new Food(FoodType.JOJA_COLA),
                    "The flagship product of Joja corporation.",
                    75,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    Integer.MAX_VALUE
            )
    ),
    ANCIENT_SEED(
            new NpcProduct(
                    "ANCIENT_SEED",
                    new Seed(SeedType.ANCIENT),
                    "Could these still grow?",
                    500,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    1
            )
    ),
    GRASS_STARTER(
            new NpcProduct(
                    "GRASS_STARTER",
                    new Etc(EtcType.GRASS_STARTER),
                    "Place this on your farm to start a new patch of grass.",
                    125,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    Integer.MAX_VALUE
            )
    ),
    SUGAR(
            new NpcProduct(
                    "SUGAR",
                    new Food(FoodType.SUGAR),
                    "Adds sweetness to pastries and candies. Too much can be unhealthy.",
                    125,
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
                    125,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    Integer.MAX_VALUE
            )
    ),
    RICE(
            new NpcProduct(
                    "RICE",
                    new Food(FoodType.RICE),
                    "A basic grain often served under vegetables.",
                    250,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    Integer.MAX_VALUE
            )
    ),

    // --- Spring Stock ---
    PARSNIP_SEEDS(
            new NpcProduct(
                    "PARSNIP_SEEDS",
                    new Seed(SeedType.PARSNIP),
                    "Plant these in the spring. Takes 4 days to mature.",
                    25,
                    -1,
                    new Seasons[]{Seasons.Spring},
                    5
            )
    ),
    BEAN_STARTER(
            new NpcProduct(
                    "BEAN_STARTER",
                    new Seed(SeedType.BEAN_STARTER),
                    "Plant these in the spring. Takes 10 days to mature, but keeps producing after that. Grows on a trellis.",
                    75,
                    -1,
                    new Seasons[]{Seasons.Spring},
                    5
            )
    ),
    CAULIFLOWER_SEEDS(
            new NpcProduct(
                    "CAULIFLOWER_SEEDS",
                    new Seed(SeedType.CAULIFLOWER),
                    "Plant these in the spring. Takes 12 days to produce a large cauliflower.",
                    100,
                    -1,
                    new Seasons[]{Seasons.Spring},
                    5
            )
    ),
    POTATO_SEEDS(
            new NpcProduct(
                    "POTATO_SEEDS",
                    new Seed(SeedType.POTATO),
                    "Plant these in the spring. Takes 6 days to mature, and has a chance of yielding multiple potatoes at harvest.",
                    62,
                    -1,
                    new Seasons[]{Seasons.Spring},
                    5
            )
    ),
    STRAWBERRY_SEEDS(
            new NpcProduct(
                    "STRAWBERRY_SEEDS",
                    new Seed(SeedType.STRAWBERRY),
                    "Plant these in spring. Takes 8 days to mature, and keeps producing strawberries after that.",
                    100,
                    -1,
                    new Seasons[]{Seasons.Spring},
                    5
            )
    ),
    TULIP_BULB(
            new NpcProduct(
                    "TULIP_BULB",
                    new Seed(SeedType.TULIP),
                    "Plant in spring. Takes 6 days to produce a colorful flower. Assorted colors.",
                    25,
                    -1,
                    new Seasons[]{Seasons.Spring},
                    5
            )
    ),
    KALE_SEEDS(
            new NpcProduct(
                    "KALE_SEEDS",
                    new Seed(SeedType.KALE),
                    "Plant these in the spring. Takes 6 days to mature. Harvest with the scythe.",
                    87,
                    -1,
                    new Seasons[]{Seasons.Spring},
                    5
            )
    ),
    COFFEE_BEANS_SPRING(
            new NpcProduct(
                    "COFFEE_BEANS_SPRING",
                    new Seed(SeedType.COFFEE_BEAN),
                    "Plant in summer or spring. Takes 10 days to grow, Then produces coffee Beans every other day.",
                    200,
                    -1,
                    new Seasons[]{Seasons.Spring},
                    1
            )
    ),
    CARROT_SEEDS(
            new NpcProduct(
                    "CARROT_SEEDS",
                    new Seed(SeedType.CARROT),
                    "Plant in the spring. Takes 3 days to grow.",
                    5,
                    -1,
                    new Seasons[]{Seasons.Spring},
                    10
            )
    ),
    RHUBARB_SEEDS(
            new NpcProduct(
                    "RHUBARB_SEEDS",
                    new Seed(SeedType.JAZZ),
                    "Plant these in the spring. Takes 13 days to mature.",
                    100,
                    -1,
                    new Seasons[]{Seasons.Spring},
                    5
            )
    ),
    JAZZ_SEEDS(
            new NpcProduct(
                    "JAZZ_SEEDS",
                    new Seed(SeedType.JAZZ),
                    "Plant in spring. Takes 7 days to produce a blue puffball flower.",
                    37,
                    -1,
                    new Seasons[]{Seasons.Spring},
                    5
            )
    ),
    //WINTER
    POWDERMELON_SEEDS(
            new NpcProduct(
                    "POWDERMELON_SEEDS",
                    new Seed(SeedType.POWDERMELON),
                    "This special melon grows in the winter. Takes 7 days to grow.",
                    20,
                    -1,
                    new Seasons[]{Seasons.Winter},
                    10
            )
    );

    private final NpcProduct product;

    JojamartProducts(NpcProduct product) {
        this.product = product;
    }

    @Override
    public NpcProduct getProduct() {
        return product;
    }

    public static <T extends Enum<T> & Store> ArrayList<NpcProduct> getProducts(Class<T> enumClass) {
        ArrayList<NpcProduct> products = new ArrayList<>();
        for (T product : enumClass.getEnumConstants()) {
            products.add(product.getProduct());
        }
        return products;
    }
}