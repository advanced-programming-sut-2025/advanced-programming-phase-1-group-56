package io.src.model.GameObject;

import io.src.model.MapModule.Position;
import io.src.model.items.Inventory;

public class Refrigerator extends GameObject {
    private Inventory inventory;

    public Refrigerator(boolean walkable, Position position,Inventory inventory) {
        super(walkable, position);
        this.inventory = inventory;
    }


    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public String getAssetName() {
        return "Refrigerator_State1";
    }
}
