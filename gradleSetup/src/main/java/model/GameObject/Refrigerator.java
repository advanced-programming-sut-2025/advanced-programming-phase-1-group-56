package model.GameObject;

import model.Locations.Position;
import model.items.Inventory;
import model.items.Item;

import java.util.ArrayList;
import java.util.HashMap;

public class Refrigerator extends GameObject {
    private Inventory inventory;

    public Refrigerator(Position position, int width, int height, boolean[][] walkable,Inventory inventory) {
        super(position, width, height, walkable);
        this.inventory = inventory;
    }


    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
