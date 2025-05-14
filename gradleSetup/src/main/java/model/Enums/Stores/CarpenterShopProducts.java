import model.Enums.Items.EtcType;
import model.Enums.Items.MineralItemType;
import model.Enums.WeatherAndTime.Seasons;
import model.GameObject.NPC.NpcProduct;
import model.items.Etc;
import model.items.Mineral;

public enum CarpenterShopProducts {
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
                    null,
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
                    null,
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
                    null,
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
                    null,
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
                    null,
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
                    null,
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
                    null,
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
                    null,
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

    public NpcProduct getProduct() {
        return product;
    }
}