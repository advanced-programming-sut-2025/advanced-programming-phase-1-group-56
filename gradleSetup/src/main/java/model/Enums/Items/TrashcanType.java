package model.Enums.Items;

public enum TrashcanType {
    initialTrashcan(0),
    copperTrashcan(15),
    ironTrashcan(30),
    goldTrashcan(45),
    iridiumTrashcan(60);

    private final int ReturnPercent;

    TrashcanType(int ReturnPercent) {
        this.ReturnPercent = ReturnPercent;
    }

    public int getReturnPercent() {
        return ReturnPercent;
    }

}
