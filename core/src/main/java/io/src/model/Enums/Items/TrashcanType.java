package io.src.model.Enums.Items;

import org.jetbrains.annotations.Nullable;

public enum TrashcanType implements ItemType {
    iridiumTrashcan(60, ToolMaterial.Iridium, null, "Trash_Can_Iridium"),
    goldTrashcan(45, ToolMaterial.Golden, iridiumTrashcan, "Trash_Can_Gold"),
    ironTrashcan(30, ToolMaterial.Ironic, iridiumTrashcan, "Trash_Can_Steel"),
    copperTrashcan(15, ToolMaterial.Cupric, ironTrashcan, "Trash_Can_Copper"),
    initialTrashcan(0, null, copperTrashcan, "Trash_Can_Steel"),
    ;

    private final int ReturnPercent;
    private final TrashcanType nextTrashcanType;
    private final ToolMaterial material;
    private final String assetName;

    TrashcanType(int ReturnPercent, ToolMaterial material, TrashcanType nextTrashcanType, String assetName) {
        this.ReturnPercent = ReturnPercent;
        this.nextTrashcanType = nextTrashcanType;
        this.material = material;
        this.assetName = assetName;
    }


    public int getReturnPercent() {
        return ReturnPercent;
    }

    @Override
    public String getName() {
        return this.toString().toLowerCase();
    }

    public TrashcanType getNextTrashcanType() {
        return nextTrashcanType;
    }

    public ToolMaterial getMaterial() {
        return material;
    }

    @Nullable
    public String getAssetName() {
        return switch (assetName) {
            case "null" -> null;
            case "" -> assetName.replace(" ", "_");
            default -> assetName;
        };
    }
}
