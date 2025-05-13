package model.Enums.GameObjects;

import model.Enums.Items.ArtisanGoodType;

import java.util.List;

public enum ArtisanMachineType {
    BEE_HOUSE("Bee House",List.of(ArtisanGoodType.HONEY)),
    CHEESE_PRESS("Cheese Press",List.of(ArtisanGoodType.CHEESE, ArtisanGoodType.GOAT_CHEESE, ArtisanGoodType.BIG_CHEESE, ArtisanGoodType.BIG_GOAT_CHEESE)),
    KEG("Keg",List.of(
            ArtisanGoodType.BEER,
            ArtisanGoodType.COFFEE,
            ArtisanGoodType.JUICE,
            ArtisanGoodType.MEAD,
            ArtisanGoodType.PALE_ALE,
            ArtisanGoodType.VINEGAR,
            ArtisanGoodType.WINE
    )),
    Dehydrator("Dehydrator",List.of(
            ArtisanGoodType.DRIED_MUSHROOMS,
            ArtisanGoodType.DRIED_FRUIT,
            ArtisanGoodType.RAISINS
    )),
    CHARCOAL_KILN("Charcoal Kiln",List.of(ArtisanGoodType.COAL)),
    LOOM("Loom",List.of(ArtisanGoodType.CLOTH)),
    MAYONNAISE_MACHINE("Mayonnaise Machine",List.of(ArtisanGoodType.MAYONNAISE, ArtisanGoodType.BIG_MAYONNAISE, ArtisanGoodType.DINOSAUR_MAYONNAISE, ArtisanGoodType.DUCK_MAYONNAISE)),
    OIL_MAKER("Oil Maker",List.of(ArtisanGoodType.CORN_OIL, ArtisanGoodType.SUNFLOWER_OIL, ArtisanGoodType.SUNFLOWER_SEED_OIL, ArtisanGoodType.TRUFFLE_OIL)),
    PRESERVES_JAR("Preserves Jar",List.of(ArtisanGoodType.PICKLES, ArtisanGoodType.JELLY)),
    FISH_SMOKER("Fish Smoker",List.of(ArtisanGoodType.SMOKED_FISH)),
    FURNACE("Furnace",List.of(ArtisanGoodType.METAL_BAR));
    private final String name;
    private final List<ArtisanGoodType> products;

    ArtisanMachineType(String name,List<ArtisanGoodType> products) {
        this.name = name;
        this.products = products;
    }

    public List<ArtisanGoodType> getProducts() {
        return products;
    }

    public String getName() {
        return name;
    }
}
