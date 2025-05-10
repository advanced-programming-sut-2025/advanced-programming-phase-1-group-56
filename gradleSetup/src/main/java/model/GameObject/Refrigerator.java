package model.GameObject;

import model.items.Inventory;
import model.items.Item;

import java.util.ArrayList;
import java.util.HashMap;

public class Refrigerator extends GameObject {
    private Inventory inventory;

    public Refrigerator(boolean walkable,Inventory inventory) {
        super(walkable);
        this.inventory = inventory;
    }


    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
