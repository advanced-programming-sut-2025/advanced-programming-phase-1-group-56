package io.src.model.Enums.Items;

import org.jetbrains.annotations.Nullable;

public enum ToolMaterial implements ItemType {
    Training(null, "wood"),
    Bamboo(null, "wood"),
    FiberGlass(null, "wood"),
    Wooden(EtcType.WOOD, "wood"),
    Cupric(EtcType.COPPER_BAR, "wood"),
    Ironic(EtcType.IRON_BAR, "wood"),
    Golden(EtcType.GOLD_BAR, "wood"),
    Iridium(EtcType.IRIDIUM_BAR, "wood");

    private final EtcType ore;

    ToolMaterial(EtcType ore, String assetName) {
        this.ore = ore;
        this.assetName = assetName;
    }

    private final String assetName;

    @Override
    public String getName() {
        return this.toString();
    }

    @Nullable
    public String getAssetName() {
        return switch (assetName) {
            case "null" -> null;
            case "" -> this.toString();
            default -> assetName;
        };
    }

    public EtcType getOre() {
        return ore;
    }
}
