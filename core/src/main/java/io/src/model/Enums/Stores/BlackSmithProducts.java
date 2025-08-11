package io.src.model.Enums.Stores;

import io.src.model.Enums.Items.*;
import io.src.model.Enums.WeatherAndTime.Seasons;
import io.src.model.GameObject.NPC.NpcProduct;
import io.src.model.items.*;

import java.util.ArrayList;

public enum BlackSmithProducts implements Store {
    // --- Ores ---
    COPPER_ORE(
        new NpcProduct(
            "COPPER_ORE",
            new Mineral(MineralItemType.COPPER_ORE),
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
            new Mineral(MineralItemType.IRON_ORE),
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
            new Mineral(MineralItemType.COAL_ORE),
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
            new Mineral(MineralItemType.GOLD_ORE),
            "A precious ore that can be smelted into bars.",
            400,
            -1,
            new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
            Integer.MAX_VALUE
        )
    ),

    // --- Upgrade Tools ---
    COPPER_HOE(
        new NpcProduct(
            "Copper_Hoe",
            ToolType.HOE_CUPRIC,
            "Upgrade your Hoe from Wooden to Copper Hoe",
            2000,
            -1,
            new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
            1
        )
    ),
    IRON_HOE(
        new NpcProduct(
            "Iron_Hoe",
            ToolType.HOE_IRONIC,
            "Upgrade your Hoe from Copper to Iron Hoe",
            5000,
            -1,
            new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
            1
        )
    ),
    GOLD_HOE(
        new NpcProduct(
            "Gold_Hoe",
            ToolType.HOE_GOLDEN,
            "Upgrade your Hoe from Iron to Gold Hoe",
            10000,
            -1,
            new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
            1
        )
    ),
    IRIDIUM_HOE(
        new NpcProduct(
            "Iridium_Hoe",
            ToolType.HOE_IRIDIUM,
            "Upgrade your Hoe from Gold to Iridium Hoe",
            25000,
            -1,
            new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
            1
        )
    ),
    COPPER_PICKAXE(
        new NpcProduct(
            "Copper_Pickaxe",
            ToolType.PICK_CUPRIC,
            "Upgrade your Pickaxe from Wooden to Copper Pickaxe",
            2000,
            -1,
            new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
            1
        )
    ),
    IRON_PICKAXE(
        new NpcProduct(
            "Steel_Pickaxe",
            ToolType.PICK_IRONIC,
            "Upgrade your Pickaxe from Copper to Steel Pickaxe",
            5000,
            -1,
            new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
            1
        )
    ),
    GOLD_PICKAXE(
        new NpcProduct(
            "Gold_Pickaxe",
            ToolType.PICK_GOLDEN,
            "Upgrade your Pickaxe from Steel to Gold Pickaxe",
            10000,
            -1,
            new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
            1
        )
    ),
    IRIDIUM_PICKAXE(
        new NpcProduct(
            "Iridium_Pickaxe",
            ToolType.PICK_IRIDIUM,
            "Upgrade your Pickaxe from Gold to Iridium Pickaxe",
            25000,
            -1,
            new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
            1
        )
    ),
    COPPER_AXE(
        new NpcProduct(
            "Copper_Axe",
            ToolType.AXE_CUPRIC,
            "Upgrade your Axe from Wooden to Copper Axe",
            2000,
            -1,
            new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
            1
        )
    ),
    IRON_AXE(
        new NpcProduct(
            "Steel_Axe",
            ToolType.AXE_IRONIC,
            "Upgrade your Axe from Copper to Steel Axe",
            5000,
            -1,
            new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
            1
        )
    ),
    GOLD_AXE(
        new NpcProduct(
            "Gold_Axe",
            ToolType.AXE_GOLDEN,
            "Upgrade your Axe from Steel to Gold Axe",
            10000,
            -1,
            new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
            1
        )
    ),
    IRIDIUM_AXE(
        new NpcProduct(
            "Iridium_Axe",
            ToolType.AXE_IRIDIUM,
            "Upgrade your Axe from Gold to Iridium Axe",
            25000,
            -1,
            new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
            1
        )
    ),
    COPPER_CAN(
        new NpcProduct(
            "Copper_Watering_Can",
            ToolType.CAN_CUPRIC,
            "Upgrade your Watering Can from Wooden to Copper Watering Can",
            2000,
            -1,
            new Seasons[]{Seasons.Spring, Seasons.Summer},
            1
        )
    ),
    IRON_CAN(
        new NpcProduct(
            "Steel_Watering_Can",
            ToolType.CAN_IRONIC,
            "Upgrade your Watering Can from Copper to Steel Watering Can",
            5000,
            -1,
            new Seasons[]{Seasons.Spring, Seasons.Summer},
            1
        )
    ),
    GOLD_CAN(
        new NpcProduct(
            "Gold_Watering_Can",
            ToolType.CAN_GOLDEN,
            "Upgrade your Watering Can from Steel to Gold Watering Can",
            10000,
            -1,
            new Seasons[]{Seasons.Spring, Seasons.Summer},
            1
        )
    ),
    IRIDIUM_CAN(
        new NpcProduct(
            "Iridium_Watering_Can",
            ToolType.CAN_IRIDIUM,
            "Upgrade your Watering Can from Gold to Iridium Watering Can",
            25000,
            -1,
            new Seasons[]{Seasons.Spring, Seasons.Summer},
            1
        )
    ),
    COPPER_SCYTHE(
        new NpcProduct(
            "Copper_Scythe",
            ToolType.SCYTHE_CUPRIC,
            "Upgrade your Scythe from Basic to Copper Scythe",
            2000,
            -1,
            new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
            1
        )
    ),
    IRON_SCYTHE(
        new NpcProduct(
            "Steel_Scythe",
            ToolType.SCYTHE_IRONIC,
            "Upgrade your Scythe from Copper to Steel Scythe",
            5000,
            -1,
            new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
            1
        )
    ),
    GOLD_SCYTHE(
        new NpcProduct(
            "Golden_Scythe",
            ToolType.SCYTHE_GOLDEN,
            "Upgrade your Scythe from Steel to Golden Scythe",
            10000,
            -1,
            new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
            1
        )
    ),
    IRIDIUM_SCYTHE(
        new NpcProduct(
            "Iridium_Scythe",
            ToolType.SCYTHE_IRIDIUM,
            "Upgrade your Scythe from Golden to Iridium Scythe",
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
            TrashcanType.copperTrashcan,
            "Upgrade your Trashcan from Basic to Copper Trashcan",
            1000,
            -1,
            new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
            1
        )
    ),
    STEEL_TRASH_CAN(
        new NpcProduct(
            "STEEL_TRASH_CAN",
            TrashcanType.ironTrashcan,
            "Upgrade your Trashcan from Copper to Steel Trashcan",
            2500,
            -1,
            new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
            1
        )
    ),
    GOLD_TRASH_CAN(
        new NpcProduct(
            "GOLD_TRASH_CAN",
            TrashcanType.goldTrashcan,
            "Upgrade your Trashcan from Steel to Gold Trashcan",
            5000,
            -1,
            new Seasons[]{Seasons.Spring, Seasons.Summer, Seasons.Fall, Seasons.Winter},
            1
        )
    ),
    IRIDIUM_TRASH_CAN(
        new NpcProduct(
            "IRIDIUM_TRASH_CAN",
            TrashcanType.iridiumTrashcan,
            "Upgrade your Trashcan from Gold to Iridium Trashcan",
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
