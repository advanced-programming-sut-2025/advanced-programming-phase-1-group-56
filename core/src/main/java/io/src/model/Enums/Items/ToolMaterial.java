package io.src.model.Enums.Items;

public enum ToolMaterial implements ItemType {
    Training(null),
    Bamboo(null),
    FiberGlass(null),
    Wooden(EtcType.WOOD),
    Cupric(EtcType.COPPER_BAR),
    Ironic(EtcType.IRON_BAR),
    Golden(EtcType.GOLD_BAR),
    Iridium(EtcType.IRIDIUM_BAR);
    private final EtcType ore;
    ToolMaterial(EtcType ore) {
        this.ore = ore;
    }


    @Override
    public String getName() {
        return this.toString();
    }

    public EtcType getOre() {
        return ore;
    }
}
