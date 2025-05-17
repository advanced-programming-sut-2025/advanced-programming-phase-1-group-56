package model.GameObject;

import model.MapModule.Position;
import model.items.Item;

public class DroppedItem extends GameObject {
    private Item item;////

    public DroppedItem(Item item, Position position) {
        super(true, position);
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
