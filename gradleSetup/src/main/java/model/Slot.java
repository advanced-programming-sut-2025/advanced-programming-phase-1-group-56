package model;

import model.items.Item;

public class Slot {
    private Item item;
    private int quantity;

    public Slot(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }


    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void increaseQuantity(int quantity){
        this.quantity += quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}