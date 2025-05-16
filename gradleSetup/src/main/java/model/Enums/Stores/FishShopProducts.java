package model.Enums.Stores;

import model.Enums.Items.EtcType;
import model.Enums.Items.FoodType;
import model.Enums.Items.ToolType;
import model.Enums.Recepies.CraftingRecipesList;
import model.Enums.Recepies.FoodRecipesList;
import model.Enums.WeatherAndTime.Seasons;
import model.GameObject.NPC.NpcProduct;
import model.items.Food;
import model.items.Tool;

import java.util.ArrayList;

public enum FishShopProducts implements Store{
    FISH_SMOKER_RECIPE(
            new NpcProduct(
                    "FISH_SMOKER_RECIPE",
                    CraftingRecipesList.FishSmoker,
                    "A recipe to make Fish Smoker",
                    10000,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    1
            )
    ),
    TROUT_SOUP(
            new NpcProduct(
                    "TROUT_SOUP",
                    new Food(FoodType.TROUT_SOUP),
                    "Pretty salty.",
                    250,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    1
            )
    ),
    BAMBOO_POLE(
            new NpcProduct(
                    "BAMBOO_POLE",
                    new Tool(ToolType.POLE_BAMBOO),
                    "Use in the water to catch fish.",
                    500,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    1
            )
    ),
    TRAINING_ROD(
            new NpcProduct(
                    "TRAINING_ROD",
                    new Tool(ToolType.POLE_TRAINING),
                    "It's a lot easier to use than other rods, but catches lower-quality fish.",
                    25,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    1
            )
    ),
    FIBERGLASS_ROD(
            new NpcProduct(
                    "FIBERGLASS_ROD",
                    new Tool(ToolType.POLE_FIBERGLASS),
                    "Use in the water to catch fish.",
                    1800,
                    2,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    1
            )
    ),
    IRIDIUM_ROD(
            new NpcProduct(
                    "IRIDIUM_ROD",
                    new Tool(ToolType.POLE_IRIDIUM),
                    "Use in the water to catch fish.",
                    7500,
                    4,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    1
            )
        );

    public final NpcProduct npcProduct;

    FishShopProducts(NpcProduct npcProduct) {
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
