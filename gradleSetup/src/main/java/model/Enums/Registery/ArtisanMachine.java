package model.Enums.Registery;

import java.util.List;

public enum ArtisanMachine {
    BEE_HOUSE(List.of(ArtisanGoods.HONEY)),
    CHEESE_PRESS(List.of(ArtisanGoods.CHEESE, ArtisanGoods.GOAT_CHEESE)),
    KEG(List.of(
            ArtisanGoods.BEER,
            ArtisanGoods.COFFEE,
            ArtisanGoods.JUICE,
            ArtisanGoods.MEAD,
            ArtisanGoods.PALE_ALE,
            ArtisanGoods.VINEGAR,
            ArtisanGoods.WINE
    )),
    PRESERVES_JAR(List.of(
            ArtisanGoods.DRIED_MUSHROOMS,
            ArtisanGoods.DRIED_FRUIT,
            ArtisanGoods.RAISINS
    )),
    CHARCOAL_KILN(List.of(ArtisanGoods.COAL));

    private final List<ArtisanGoods> products;

    ArtisanMachine(List<ArtisanGoods> products) {
        this.products = products;
    }

    public List<ArtisanGoods> getProducts() {
        return products;
    }
}
