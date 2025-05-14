package model.Enums.Items;

public enum ToolType {
    // Hoe variants (capacity = 0)
    HOE_WOODEN   ("Hoe" ,ToolMaterial.Wooden, 5, 0),
    HOE_CUPRIC   ("Hoe" ,ToolMaterial.Cupric, 4, 0),
    HOE_IRONIC   ("Hoe" ,ToolMaterial.Ironic, 3, 0),
    HOE_GOLDEN   ("Hoe" ,ToolMaterial.Golden, 2, 0),
    HOE_IRIDIUM  ("Hoe" ,ToolMaterial.Iridium, 1, 0),

    // Pickaxe var
    PICK_WOODEN  ("Pickaxe" ,ToolMaterial.Wooden, 5, 0),
    PICK_CUPRIC  ("Pickaxe" ,ToolMaterial.Cupric, 4, 0),
    PICK_IRONIC  ("Pickaxe" ,ToolMaterial.Ironic, 3, 0),
    PICK_GOLDEN  ("Pickaxe" ,ToolMaterial.Golden, 2, 0),
    PICK_IRIDIUM ("Pickaxe" ,ToolMaterial.Iridium, 1, 0),

    // Axe variant
    AXE_WOODEN   ("Axe" ,ToolMaterial.Wooden, 5, 0),
    AXE_CUPRIC   ("Axe" ,ToolMaterial.Cupric, 4, 0),
    AXE_IRONIC   ("Axe" ,ToolMaterial.Ironic, 3, 0),
    AXE_GOLDEN   ("Axe" ,ToolMaterial.Golden, 2, 0),
    AXE_IRIDIUM  ("Axe" ,ToolMaterial.Iridium, 1, 0),

    // Watering can
    CAN_WOODEN   ("Watering Can" ,ToolMaterial.Wooden, 5, 40),
    CAN_CUPRIC   ("Watering Can" ,ToolMaterial.Cupric, 4, 55),
    CAN_IRONIC   ("Watering Can" ,ToolMaterial.Ironic, 3, 70),
    CAN_GOLDEN   ("Watering Can" ,ToolMaterial.Golden, 2, 85),
    CAN_IRIDIUM  ("Watering Can" ,ToolMaterial.Iridium, 1, 100),

    // Fishing pole variants
    POLE_TRAINING ("Fishing Pole" ,ToolMaterial.Training, 8, 0),
    POLE_BAMBOO   ("Fishing Pole" ,ToolMaterial.Bamboo, 8, 0),
    POLE_FIBERGLASS("Fishing Pole" , ToolMaterial.FiberGlass, 6, 0),
    POLE_IRIDIUM  ("Fishing Pole" ,ToolMaterial.Iridium, 4, 0),

    // Scythe
    SCYTHE_BASIC  ("Scythe" ,ToolMaterial.Wooden, 2, 0),

    // Milk pail
    MILK_PAIL     ("Milk Pail" ,ToolMaterial.Wooden, 4, 0),

    // Shears
    SHEAR         ("Shears" ,ToolMaterial.Wooden, 4, 0);


    private final String name;
    private final ToolMaterial toolMaterial;
    private final int usedEnergy;
    private final int capacity;

    ToolType(String name ,ToolMaterial toolMaterial, int usedEnergy, int capacity) {
        this.name = name;
        this.toolMaterial = toolMaterial;
        this.usedEnergy = usedEnergy;
        this.capacity = capacity;
    }

    public ToolMaterial getToolMaterial() {
        return toolMaterial;
    }

    public int getUsedEnergy() {
        return usedEnergy;
    }

    /**
     * ظرفیت ابزار (برای ابزارهایی مثل ظرف آبیاری)
     * برای ابزارهای بدون ظرفیت، مقدار ۰ است
     */
    public int getCapacity() {
        return capacity;
    }
    public String getName() {
        return name;
    }
}
