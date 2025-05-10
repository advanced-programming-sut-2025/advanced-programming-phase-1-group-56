package model.GameObject;

import model.Slot;

public class DroppedItem extends GameObject{
    private Slot slot;

    public DroppedItem(Slot slot) {
        super(true);
        this.slot = slot;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }
}
