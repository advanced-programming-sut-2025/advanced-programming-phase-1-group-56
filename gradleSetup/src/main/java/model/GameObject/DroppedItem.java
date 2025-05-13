package model.GameObject;

import model.Slot;
import model.items.Item;

public class DroppedItem extends GameObject {
    private Item item;

    public DroppedItem(Item item) {
        super(true);
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
