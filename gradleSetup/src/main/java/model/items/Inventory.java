package model.items;

import java.util.HashMap;

public class Inventory {
    private final HashMap<Item,Integer> itemList = new HashMap<>();
    private int capacity;
    public Inventory(int capacity) {
        this.capacity = capacity;
    }

    public HashMap<Item,Integer> getItemList() {
        return itemList;
    }

    public void updateCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void add(Item item) {
        //TODO
    }
}