package model.Enums.Items;

public enum ToolType {
    // Hoe variants (capacity = 0)
    HOE_WOODEN   (ToolMaterial.Wooden, 5, 0),
    HOE_CUPRIC   (ToolMaterial.Cupric, 4, 0),
    HOE_IRONIC   (ToolMaterial.Ironic, 3, 0),
    HOE_GOLDEN   (ToolMaterial.Golden, 2, 0),
    HOE_IRIDIUM  (ToolMaterial.Iridium, 1, 0),

    // Pickaxe variants
    PICK_WOODEN  (ToolMaterial.Wooden, 5, 0),
    PICK_CUPRIC  (ToolMaterial.Cupric, 4, 0),
    PICK_IRONIC  (ToolMaterial.Ironic, 3, 0),
    PICK_GOLDEN  (ToolMaterial.Golden, 2, 0),
    PICK_IRIDIUM (ToolMaterial.Iridium, 1, 0),

    // Axe variants
    AXE_WOODEN   (ToolMaterial.Wooden, 5, 0),
    AXE_CUPRIC   (ToolMaterial.Cupric, 4, 0),
    AXE_IRONIC   (ToolMaterial.Ironic, 3, 0),
    AXE_GOLDEN   (ToolMaterial.Golden, 2, 0),
    AXE_IRIDIUM  (ToolMaterial.Iridium, 1, 0),

    // Watering can variants (capacity > 0)
    CAN_WOODEN   (ToolMaterial.Wooden, 5, 40),
    CAN_CUPRIC   (ToolMaterial.Cupric, 4, 55),
    CAN_IRONIC   (ToolMaterial.Ironic, 3, 70),
    CAN_GOLDEN   (ToolMaterial.Golden, 2, 85),
    CAN_IRIDIUM  (ToolMaterial.Iridium, 1, 100),

    // Fishing pole variants
    POLE_TRAINING (ToolMaterial.Wooden, 8, 0),
    POLE_BAMBOO   (ToolMaterial.Wooden, 8, 0),
    POLE_FIBERGLASS (ToolMaterial.Cupric, 6, 0),
    POLE_IRIDIUM  (ToolMaterial.Iridium, 4, 0),

    // Scythe
    SCYTHE_BASIC  (ToolMaterial.Wooden, 2, 0),

    // Milk pail
    MILK_PAIL     (ToolMaterial.Wooden, 4, 0),

    // Shears
    SHEAR         (ToolMaterial.Wooden, 4, 0);

    private final ToolMaterial toolMaterial;
    private final int usedEnergy;
    private final int capacity;

    ToolType(ToolMaterial toolMaterial, int usedEnergy, int capacity) {
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
}