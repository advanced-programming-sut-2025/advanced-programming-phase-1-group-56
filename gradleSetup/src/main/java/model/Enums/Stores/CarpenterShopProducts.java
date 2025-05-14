package model.Enums.Stores;

import model.Enums.Buildings.BuildingType;
import model.Enums.Items.EtcType;
import model.Enums.Items.MineralItemType;
import model.Enums.WeatherAndTime.Seasons;
import model.GameObject.NPC.NpcProduct;
import model.items.Etc;
import model.items.Mineral;

import java.util.ArrayList;

public enum CarpenterShopProducts implements Store {
    // --- Permanent Stock ---
    WOOD(
            new NpcProduct(
                    "WOOD",
                    new Etc(EtcType.WOOD),
                    "A sturdy, yet flexible plant material with a wide variety of uses.",
                    10,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    Integer.MAX_VALUE
            )
    ),
    STONE(
            new NpcProduct(
                    "STONE",
                    new Mineral(MineralItemType.STONE),
                    "A common material with many uses in crafting and building.",
                    20,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    Integer.MAX_VALUE
            )
    ),

    // --- Farm Buildings ---
    BARN(
            new NpcProduct(
                    "BARN",
                    BuildingType.BARN,
                    "Houses 4 barn-dwelling animals.",
                    6000,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    1
            )
    ),
    BIG_BARN(
            new NpcProduct(
                    "BIG_BARN",
                    BuildingType.BIG_BARN,
                    "Houses 8 barn-dwelling animals. Unlocks goats.",
                    12000,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    1
            )
    ),
    DELUXE_BARN(
            new NpcProduct(
                    "DELUXE_BARN",
                    BuildingType.DELUXE_BARN,
                    "Houses 12 barn-dwelling animals. Unlocks sheep and pigs.",
                    25000,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    1
            )
    ),
    COOP(
            new NpcProduct(
                    "COOP",
                    BuildingType.COOP,
                    "Houses 4 coop-dwelling animals.",
                    4000,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    1
            )
    ),
    BIG_COOP(
            new NpcProduct(
                    "BIG_COOP",
                    BuildingType.BIG_COOP,
                    "Houses 8 coop-dwelling animals. Unlocks ducks.",
                    10000,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    1
            )
    ),
    DELUXE_COOP(
            new NpcProduct(
                    "DELUXE_COOP",
                    BuildingType.DELUXE_COOP,
                    "Houses 12 coop-dwelling animals. Unlocks rabbits.",
                    20000,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    1
            )
    ),
    WELL(
            new NpcProduct(
                    "WELL",
                    BuildingType.WELL,
                    "Provides a place for you to refill your watering can.",
                    1000,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    1
            )
    ),
    SHIPPING_BIN(
            new NpcProduct(
                    "SHIPPING_BIN",
                    BuildingType.SHIPPING_BIN,
                    "Items placed in it will be included in the nightly shipment.",
                    250,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    Integer.MAX_VALUE
            )
    );

    private final NpcProduct product;


    CarpenterShopProducts(NpcProduct product) {
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