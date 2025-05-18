package model.Enums.Items;

import model.Enums.Recepies.CraftingRecipesList;

public enum ToolType {
    // Hoe variants (capacity = 0)
    HOE_IRIDIUM  ("Hoe" ,ToolMaterial.Iridium, 1, 0 , null),
    HOE_GOLDEN   ("Hoe" ,ToolMaterial.Golden, 2, 0 , HOE_IRIDIUM),
    HOE_IRONIC   ("Hoe" ,ToolMaterial.Ironic, 3, 0 , HOE_GOLDEN),
    HOE_CUPRIC   ("Hoe" ,ToolMaterial.Cupric, 4, 0 , HOE_IRONIC),
    HOE_WOODEN   ("Hoe" ,ToolMaterial.Wooden, 5, 0 , HOE_CUPRIC),

    // Pickaxe var
    PICK_IRIDIUM ("Pickaxe" ,ToolMaterial.Iridium, 1, 0 , null),
    PICK_GOLDEN  ("Pickaxe" ,ToolMaterial.Golden, 2, 0 , PICK_IRIDIUM),
    PICK_IRONIC  ("Pickaxe" ,ToolMaterial.Ironic, 3, 0 , PICK_GOLDEN),
    PICK_CUPRIC  ("Pickaxe" ,ToolMaterial.Cupric, 4, 0 , PICK_IRONIC),
    PICK_WOODEN  ("Pickaxe" ,ToolMaterial.Wooden, 5, 0 , PICK_CUPRIC),


    // Axe variant
    AXE_IRIDIUM  ("Axe" ,ToolMaterial.Iridium, 1, 0 , null),
    AXE_GOLDEN   ("Axe" ,ToolMaterial.Golden, 2, 0 , AXE_IRIDIUM),
    AXE_IRONIC   ("Axe" ,ToolMaterial.Ironic, 3, 0 , AXE_GOLDEN),
    AXE_CUPRIC   ("Axe" ,ToolMaterial.Cupric, 4, 0 , AXE_IRONIC),
    AXE_WOODEN   ("Axe" ,ToolMaterial.Wooden, 5, 0 , AXE_CUPRIC),

    // Watering can
    CAN_IRIDIUM  ("Watering Can" ,ToolMaterial.Iridium, 1, 100 , null),
    CAN_GOLDEN   ("Watering Can" ,ToolMaterial.Golden, 2, 85 , CAN_IRIDIUM),
    CAN_IRONIC   ("Watering Can" ,ToolMaterial.Ironic, 3, 70 , CAN_GOLDEN),
    CAN_CUPRIC   ("Watering Can" ,ToolMaterial.Cupric, 4, 55 , CAN_IRONIC),
    CAN_WOODEN   ("Watering Can" ,ToolMaterial.Wooden, 5, 40 , CAN_CUPRIC),

    // Fishing pole variants
    POLE_IRIDIUM  ("Fishing Pole" ,ToolMaterial.Iridium, 4, 0 , null),
    POLE_FIBERGLASS("Fishing Pole" , ToolMaterial.FiberGlass, 6, 0 , POLE_IRIDIUM),
    POLE_BAMBOO   ("Fishing Pole" ,ToolMaterial.Bamboo, 8, 0 , POLE_FIBERGLASS),
    POLE_TRAINING ("Fishing Pole" ,ToolMaterial.Training, 8, 0 , POLE_BAMBOO),////

    // Scythe
    SCYTHE_BASIC  ("Scythe" ,ToolMaterial.Wooden, 2, 0 , null),

    // Milk pail
    MILK_PAIL     ("Milk Pail" ,ToolMaterial.Wooden, 4, 0 , null),

    // Shears
    SHEAR         ("Shears" ,ToolMaterial.Wooden, 4, 0 , null);


    private final String name;
    private final ToolMaterial toolMaterial;
    private final int usedEnergy;
    private final int capacity;
    private final ToolType nextToolType;

    ToolType(String name ,ToolMaterial toolMaterial, int usedEnergy, int capacity , ToolType nextToolType) {
        this.name = name;
        this.toolMaterial = toolMaterial;
        this.usedEnergy = usedEnergy;
        this.capacity = capacity;
        this.nextToolType = nextToolType;
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

}
