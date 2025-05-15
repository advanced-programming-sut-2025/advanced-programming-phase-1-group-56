package model.Enums.Stores;

import model.Enums.Items.EtcType;
import model.Enums.Items.MineralItemType;
import model.Enums.Items.ToolType;
import model.Enums.Items.TrashcanType;
import model.Enums.WeatherAndTime.Seasons;
import model.GameObject.NPC.NpcProduct;
import model.items.Item;
import model.items.Saleable;
import model.items.Mineral;
import model.items.Tool;

import java.util.ArrayList;

public enum BlackSmithProducts implements Store {
    // --- Ores ---
    COPPER_ORE(
            new NpcProduct(
                    "COPPER_ORE",
                    new Mineral(MineralItemType.COPPER),
                    "A common ore that can be smelted into bars.",
                    75,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    Integer.MAX_VALUE
            )
    ),
    IRON_ORE(
            new NpcProduct(
                    "IRON_ORE",
                    new Mineral(MineralItemType.IRON),
                    "A fairly common ore that can be smelted into bars.",
                    150,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    Integer.MAX_VALUE
            )
    ),
    COAL(
            new NpcProduct(
                    "COAL",
                    new Mineral(MineralItemType.COAL),
                    "A combustible rock that is useful for crafting and smelting.",
                    150,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    Integer.MAX_VALUE
            )
    ),
    GOLD_ORE(
            new NpcProduct(
                    "GOLD_ORE",
                    new Mineral(MineralItemType.GOLD),
                    "A precious ore that can be smelted into bars.",
                    400,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    Integer.MAX_VALUE
            )
    ),

    // --- Upgrade Tools ---
    COPPER_TOOL(
            new NpcProduct(
                    "COPPER_TOOL",
                    EtcType.COPPER_BAR,
                    "",
                    2000,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    1
            )
    ),
    STEEL_TOOL(
            new NpcProduct(
                    "STEEL_TOOL",
                    EtcType.IRON_BAR,
                    "",
                    5000,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    1
            )
    ),
    GOLD_TOOL(
            new NpcProduct(
                    "GOLD_TOOL",
                    EtcType.GOLD_BAR,
                    "",
                    10000,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    1
            )
    ),
    IRIDIUM_TOOL(
            new NpcProduct(
                    "IRIDIUM_TOOL",
                    EtcType.IRIDIUM_BAR,
                    "",
                    25000,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    1
            )
    ),

    // --- Trash Can Upgrades ---
    COPPER_TRASH_CAN(
            new NpcProduct(
                    "COPPER_TRASH_CAN",
                    EtcType.COPPER_BAR,
                        "",
                    1000,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    1
            )
    ),
    STEEL_TRASH_CAN(
            new NpcProduct(
                    "STEEL_TRASH_CAN",
                    EtcType.IRON_BAR,
                    "",
                    2500,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    1
            )
    ),
    GOLD_TRASH_CAN(
            new NpcProduct(
                    "GOLD_TRASH_CAN",
                    EtcType.GOLD_BAR,
                    "",
                    5000,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    1
            )
    ),
    IRIDIUM_TRASH_CAN(
            new NpcProduct(
                    "IRIDIUM_TRASH_CAN",
                    EtcType.IRIDIUM_BAR,
                    "",
                    12500,
                    -1,
                    new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
                    1
            )
    );

    private final NpcProduct product;

    BlackSmithProducts(NpcProduct product) {
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