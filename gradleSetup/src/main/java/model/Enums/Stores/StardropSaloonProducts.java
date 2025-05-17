package model.Enums.Stores;

import model.Enums.Items.FoodType;
import model.Enums.Recepies.FoodRecipesList;
import model.Enums.WeatherAndTime.Seasons;
import model.GameObject.NPC.NpcProduct;
import model.items.Food;

import java.util.ArrayList;

public enum StardropSaloonProducts implements Store {
    BEER(
            new NpcProduct(
                    "BEER",
                    new Food(FoodType.BEER),
                    "Drink in moderation.",
                    400,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    Integer.MAX_VALUE
            )
    ),
    SALAD(
            new NpcProduct(
                    "SALAD",
                    new Food(FoodType.SALAD),
                    "A healthy garden salad.",
                    220,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    Integer.MAX_VALUE
            )
    ),
    BREAD(
            new NpcProduct(
                    "BREAD",
                    new Food(FoodType.BREAD),
                    "A crusty baguette.",
                    120,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    Integer.MAX_VALUE
            )
    ),
    SPAGHETTI(
            new NpcProduct(
                    "SPAGHETTI",
                    new Food(FoodType.SPAGHETTI),
                    "An old favorite.",
                    240,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    Integer.MAX_VALUE
            )
    ),
    PIZZA(
            new NpcProduct(
                    "PIZZA",
                    new Food(FoodType.PIZZA),
                    "It's popular for all the right reasons.",
                    600,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    Integer.MAX_VALUE
            )
    ),
    COFFEE(
            new NpcProduct(
                    "COFFEE",
                    new Food(FoodType.COFFEE),
                    "It smells delicious. This is sure to give you enough energy to get through the day.",
                    300,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    Integer.MAX_VALUE
            )
    ),

    HASHBROWNS_RECIPE(
            new NpcProduct(
                    "HASHBROWNS_RECIPE",
                    FoodRecipesList.HASH_BROWNS,
                    "A recipe to make Hashbrowns.",
                    50,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    1
            )
    ),
    OMELET_RECIPE(
            new NpcProduct(
                    "OMELET_RECIPE",
                    FoodRecipesList.OLMELET,
                    "A recipe to make Omelet.",
                    100,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    1
            )
    ),
    PANCAKES_RECIPE(
            new NpcProduct(
                    "PANCAKES_RECIPE",
                    FoodRecipesList.PANCAKES,
                    "A recipe to make Pancakes.",
                    100,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    1
            )
    ),
    BREAD_RECIPE(
            new NpcProduct(
                    "BREAD_RECIPE",
                    FoodRecipesList.BREAD,
                    "A recipe to make Bread.",
                    100,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    1
            )
    ),
    TORTILLA_RECIPE(
            new NpcProduct(
                    "TORTILLA_RECIPE",
                    FoodRecipesList.TORTILLA,
                    "A recipe to make Tortilla.",
                    100,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    1
            )
    ),
    PIZZA_RECIPE(
            new NpcProduct(
                    "PIZZA_RECIPE",
                    FoodRecipesList.PIZZA,
                    "A recipe to make Pizza.",
                    150,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    1
            )
    ),
    MAKI_ROLL_RECIPE(
            new NpcProduct(
                    "MAKI_ROLL_RECIPE",
                    FoodRecipesList.MAKI_ROLL,
                    "A recipe to make Maki Roll.",
                    300,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    1
            )
    ),
    TRIPLE_SHOT_ESPRESSO_RECIPE(
            new NpcProduct(
                    "TRIPLE_SHOT_ESPRESSO_RECIPE",
                    FoodRecipesList.TRIPLE_SHOT_ESPRESSO,
                    "A recipe to make Triple Shot Espresso.",
                    5000,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    1
            )
    ),
    COOKIE_RECIPE(
            new NpcProduct(
                    "COOKIE_RECIPE",
                    FoodRecipesList.COOKIE,
                    "A recipe to make Cookie.",
                    300,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    1
            )
    );

    private final NpcProduct product;

    StardropSaloonProducts(NpcProduct product) {
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