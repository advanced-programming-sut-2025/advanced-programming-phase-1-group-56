package io.src.model.GameObject;

import io.src.model.Clickable;
import io.src.model.MapModule.Position;
import io.src.model.items.Item;

public class DroppedItem extends GameObject {
    private Item item;

    /// /

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

    @Override
    public String getAssetName() {
        return item.getAssetName();
    }
}
