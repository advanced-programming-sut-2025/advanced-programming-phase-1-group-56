package model.GameObject;

import model.MapModule.Position;
import model.items.Inventory;
import model.items.Item;

import java.util.ArrayList;
import java.util.HashMap;

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
}
