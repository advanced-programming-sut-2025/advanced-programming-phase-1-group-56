package io.src.model.Enums.Items;

import io.src.model.Enums.Recepies.CraftingRecipesList;
import io.src.model.items.Saleable;
import org.jetbrains.annotations.Nullable;

public enum ToolType implements Saleable {
    // Hoe variants (capacity = 0)
    HOE_IRIDIUM("Hoe", ToolMaterial.Iridium, 1, 0, null, "Iridium_Hoe"),
    HOE_GOLDEN("Hoe", ToolMaterial.Golden, 2, 0, HOE_IRIDIUM, "Gold_Hoe"),
    HOE_IRONIC("Hoe", ToolMaterial.Ironic, 3, 0, HOE_GOLDEN, "Steel_Hoe"),
    HOE_CUPRIC("Hoe", ToolMaterial.Cupric, 4, 0, HOE_IRONIC, "Copper_Hoe"),
    HOE_WOODEN("Hoe", ToolMaterial.Wooden, 5, 0, HOE_CUPRIC, "Hoe"),
    // Pickaxe var
    PICK_IRIDIUM("Pickaxe", ToolMaterial.Iridium, 1, 0, null, "Iridium_Pickaxe"),
    PICK_GOLDEN("Pickaxe", ToolMaterial.Golden, 2, 0, PICK_IRIDIUM, "Gold_Pickaxe"),
    PICK_IRONIC("Pickaxe", ToolMaterial.Ironic, 3, 0, PICK_GOLDEN, "Steel_Pickaxe"),
    PICK_CUPRIC("Pickaxe", ToolMaterial.Cupric, 4, 0, PICK_IRONIC, "Copper_Pickaxe"),
    PICK_WOODEN("Pickaxe", ToolMaterial.Wooden, 5, 0, PICK_CUPRIC, "Pickaxe"),
    // Axe variant
    AXE_IRIDIUM("Axe", ToolMaterial.Iridium, 1, 0, null, "Iridium_Axe"),
    AXE_GOLDEN("Axe", ToolMaterial.Golden, 2, 0, AXE_IRIDIUM, "Gold_Axe"),
    AXE_IRONIC("Axe", ToolMaterial.Ironic, 3, 0, AXE_GOLDEN, "Steel_Axe"),
    AXE_CUPRIC("Axe", ToolMaterial.Cupric, 4, 0, AXE_IRONIC, "Copper_Axe"),
    AXE_WOODEN("Axe", ToolMaterial.Wooden, 5, 0, AXE_CUPRIC, "Axe"),
    // Watering can
    CAN_IRIDIUM("WateringCan", ToolMaterial.Iridium, 1, 100, null, "Iridium_Watering_Can"),
    CAN_GOLDEN("WateringCan", ToolMaterial.Golden, 2, 85, CAN_IRIDIUM, "Gold_Watering_Can"),
    CAN_IRONIC("WateringCan", ToolMaterial.Ironic, 3, 70, CAN_GOLDEN, "Steel_Watering_Can"),
    CAN_CUPRIC("WateringCan", ToolMaterial.Cupric, 4, 55, CAN_IRONIC, "Copper_Watering_Can"),
    CAN_WOODEN("WateringCan", ToolMaterial.Wooden, 5, 40, CAN_CUPRIC, "Wooden_Watering_Can"),
    // Fishing pole variants
    //POLE_IRIDIUM_Advanced("Advanced Iridium Rod", ToolMaterial.Iridium, 2, 0, null,"),
    POLE_IRIDIUM("FishingPole", ToolMaterial.Iridium, 4, 0, null, "FishingPole_Iridium"),
    POLE_FIBERGLASS("FishingPole", ToolMaterial.FiberGlass, 6, 0, POLE_IRIDIUM, "FishingPole_FiberGlass"),
    POLE_BAMBOO("FishingPole", ToolMaterial.Bamboo, 8, 0, POLE_FIBERGLASS, "FishingPole_Bamboo"),
    POLE_TRAINING("FishingPole", ToolMaterial.Training, 8, 0, POLE_BAMBOO, "FishingPole_Training"),
    // Scythe
    SCYTHE_IRIDIUM("Scythe", ToolMaterial.Iridium, 0, 0, null, "Iridium_Scythe"),
    SCYTHE_GOLDEN("Scythe", ToolMaterial.Golden, 1, 0, SCYTHE_IRIDIUM, "Golden_Scythe"),
    SCYTHE_IRONIC("Scythe", ToolMaterial.Ironic, 1, 0, SCYTHE_GOLDEN, "Scythe"),
    SCYTHE_CUPRIC("Scythe", ToolMaterial.Cupric, 2, 0, SCYTHE_IRONIC, "Scythe1"),
    SCYTHE_BASIC("Scythe", ToolMaterial.Wooden, 2, 0, SCYTHE_CUPRIC, "Scythe"),

    // Milk pail
    MILK_PAIL("Milk Pail", ToolMaterial.Wooden, 4, 0, null, "Milk_Pail"),
    // Shears
    SHEAR("Shears", ToolMaterial.Wooden, 4, 0, null, "Shears"),

    Iridium_Trashcan("Trash_Can_Iridium", ToolMaterial.Iridium, 0, 0, null, "Trash_Can_Iridium"),
    Gold_Trashcan("Trash_Can_Iridium", ToolMaterial.Golden, 0, 0, Iridium_Trashcan, "Trash_Can_Gold"),
    Iron_Trashcan("Trash_Can_Iridium", ToolMaterial.Ironic, 0, 0, Gold_Trashcan, "Trash_Can_Steel"),
    Copper_Trashcan("Trash_Can_Iridium", ToolMaterial.Cupric, 0, 0, Iron_Trashcan, "Trash_Can_Copper"),
    InitialTrashcan("Trash_Can_Iridium", null, 0, 0, Copper_Trashcan, "Trash_Can_Steel"),
    ;


    private final String name;
    private final ToolMaterial toolMaterial;
    private final int usedEnergy;
    private final int capacity;
    private final ToolType nextToolType;
    private final String assetName;

    ToolType(String name, ToolMaterial toolMaterial, int usedEnergy, int capacity, ToolType nextToolType, String assetName) {
        this.name = name;
        this.toolMaterial = toolMaterial;
        this.usedEnergy = usedEnergy;
        this.capacity = capacity;
        this.nextToolType = nextToolType;
        this.assetName = assetName;
    }

    public ToolMaterial getToolMaterial() {
        return toolMaterial;
    }

    public int getUsedEnergy() {
        return usedEnergy;
    }


    public int getCapacity() {
        return capacity;
    }

    public String getName() {
        return name;
    }

    public ToolType getNextToolType() {
        return nextToolType;
    }

    public static ToolType fromName(String name) {
        for (ToolType type : ToolType.values()) {
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

    @Nullable
    public String getAssetName() {
        return switch (assetName) {
            case "null" -> null;
            case "" -> name.replace(" ", "_");
            default -> assetName;
        };
    }

    public ToolType findBeforeToolType() {
        for (ToolType type : ToolType.values()) {
            if (type.getNextToolType()!=null&&type.getNextToolType().equals(this)) {
                return type;
            }
        }
        return null;
    }

}
