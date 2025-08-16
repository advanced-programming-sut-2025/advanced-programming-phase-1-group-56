package io.src.model.Enums.Stores;

import io.src.model.Enums.Items.FoodType;
import io.src.model.Enums.Items.QualityPole;
import io.src.model.Enums.Items.ToolType;
import io.src.model.Enums.Recepies.CraftingRecipesList;
import io.src.model.Enums.WeatherAndTime.Seasons;
import io.src.model.GameObject.NPC.NpcProduct;
import io.src.model.items.Food;
import io.src.model.items.Tool;

import java.util.ArrayList;

public enum FishShopProducts implements Store {
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
            "Bamboo Rod",
            QualityPole.BambooPole,
            "Use in the water to catch fish.",
            500,
            -1,
            new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
            1
        )
    ),
    TRAINING_ROD(
        new NpcProduct(
            "Training Rod",
            QualityPole.TrainingPole,
            "It's a lot easier to use than other rods, but catches lower-quality fish.",
            25,
            -1,
            new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
            1
        )
    ),
    IRIDIUM_ROD(
        new NpcProduct(
            "Iridium Rod",
            QualityPole.IridiumRod,
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
