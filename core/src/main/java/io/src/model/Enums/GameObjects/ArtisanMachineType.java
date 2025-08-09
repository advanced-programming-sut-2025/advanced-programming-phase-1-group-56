package io.src.model.Enums.GameObjects;

import io.src.model.Enums.Items.ArtisanGoodType;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public enum ArtisanMachineType {
    BEE_HOUSE("Bee_House", List.of(ArtisanGoodType.HONEY), "Bee_House"),
    CHEESE_PRESS("Cheese_Press", List.of(ArtisanGoodType.CHEESE, ArtisanGoodType.GOAT_CHEESE, ArtisanGoodType.BIG_CHEESE, ArtisanGoodType.BIG_GOAT_CHEESE), ""),
    KEG("Keg", List.of(
        ArtisanGoodType.BEER,
        ArtisanGoodType.COFFEE,
        ArtisanGoodType.JUICE,
        ArtisanGoodType.MEAD,
        ArtisanGoodType.PALE_ALE,
        ArtisanGoodType.VINEGAR,
        ArtisanGoodType.WINE
    ), ""),
    Dehydrator("Dehydrator", List.of(
        ArtisanGoodType.DRIED_MUSHROOMS,
        ArtisanGoodType.DRIED_FRUIT,
        ArtisanGoodType.RAISINS
    ), ""),
    CHARCOAL_KILN("Charcoal_Kiln", List.of(ArtisanGoodType.COAL), ""),
    LOOM("Loom", List.of(ArtisanGoodType.CLOTH), ""),
    MAYONNAISE_MACHINE("Mayonnaise_Machine", List.of(ArtisanGoodType.MAYONNAISE, ArtisanGoodType.BIG_MAYONNAISE, ArtisanGoodType.DINOSAUR_MAYONNAISE, ArtisanGoodType.DUCK_MAYONNAISE), ""),
    OIL_MAKER("Oil_Maker", List.of(ArtisanGoodType.CORN_OIL, ArtisanGoodType.SUNFLOWER_OIL, ArtisanGoodType.SUNFLOWER_SEED_OIL, ArtisanGoodType.TRUFFLE_OIL), ""),
    PRESERVES_JAR("Preserves_Jar", List.of(ArtisanGoodType.PICKLES, ArtisanGoodType.JELLY), ""),
    FISH_SMOKER("Fish_Smoker", List.of(ArtisanGoodType.SMOKED_FISH), ""),
    FURNACE("Furnace", List.of(ArtisanGoodType.METAL_BAR), "");
    private final String name;
    private final List<ArtisanGoodType> products;
    private final String assetName;

    ArtisanMachineType(String name, List<ArtisanGoodType> products, String assetName) {
        this.name = name;
        this.products = products;
        this.assetName = assetName;
    }

    public List<ArtisanGoodType> getProducts() {
        return products;
    }


    public String getName() {
        return name;
    }

    @Nullable
    public String getAssetName() {
        return switch (assetName) {
            case "null" -> null;
            case "" -> name.replace(" ", "_");
            default -> assetName;
        };
    }
}
