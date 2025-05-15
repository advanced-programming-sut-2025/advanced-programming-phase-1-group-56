package model.Enums.Items;

public enum TrashcanType implements  ItemType {
    iridiumTrashcan(60,ToolMaterial.Iridium,null),
    goldTrashcan(45,ToolMaterial.Golden,iridiumTrashcan),
    ironTrashcan(30,ToolMaterial.Ironic,iridiumTrashcan),
    copperTrashcan(15,ToolMaterial.Cupric,ironTrashcan),
    initialTrashcan(0,null,copperTrashcan);

    private final int ReturnPercent;
    private final TrashcanType nextTrashcanType;
    private final ToolMaterial material;

    TrashcanType(int ReturnPercent,ToolMaterial material, TrashcanType nextTrashcanType) {
        this.ReturnPercent = ReturnPercent;
        this.nextTrashcanType = nextTrashcanType;
        this.material = material;
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
}
