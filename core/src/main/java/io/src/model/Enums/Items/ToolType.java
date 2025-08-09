package io.src.model.Enums.Items;

import io.src.model.Enums.Recepies.CraftingRecipesList;
import org.jetbrains.annotations.Nullable;

public enum ToolType {
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
    CAN_WOODEN("WateringCan", ToolMaterial.Wooden, 5, 40, CAN_CUPRIC, ""),
    // Fishing pole variants
    //POLE_IRIDIUM_Advanced("Advanced Iridium Rod", ToolMaterial.Iridium, 2, 0, null,"),
    POLE_IRIDIUM("Iridium Rod", ToolMaterial.Iridium, 4, 0, null, ""),
    POLE_FIBERGLASS("Fiberglass Rod", ToolMaterial.FiberGlass, 6, 0, POLE_IRIDIUM, ""),
    POLE_BAMBOO("Bamboo Rod", ToolMaterial.Bamboo, 8, 0, POLE_FIBERGLASS, "Bamboo_Pole"),
    POLE_TRAINING("Training Rod", ToolMaterial.Training, 8, 0, POLE_BAMBOO, "Training_Rod"),
    // Scythe
    SCYTHE_IRIDIUM("Scythe", ToolMaterial.Wooden, 2, 0, null, "Iridium_Scythe"),
    SCYTHE_GOLDEN("Scythe", ToolMaterial.Wooden, 2, 0, null, "Golden_Scythe"),
    SCYTHE_CUPRIC("Scythe", ToolMaterial.Wooden, 2, 0, null, "Scythe1"),
    SCYTHE_BASIC("Scythe", ToolMaterial.Wooden, 2, 0, null, "Scythe"),
    // Milk pail
    MILK_PAIL("Milk Pail", ToolMaterial.Wooden, 4, 0, null, "Milk_Pail"),
    // Shears
    SHEAR("Shears", ToolMaterial.Wooden, 4, 0, null, "Shearss");


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

}
