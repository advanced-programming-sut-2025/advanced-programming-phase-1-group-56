package model.GameObject.NPC;

import model.items.Item;

public class NpcProduct {
    private Item item;
    private int price;
    private int dailyStock;

    public NpcProduct(Item item, int price, int dailyStock) {
        this.item = item;
        this.price = price;
        this.dailyStock = dailyStock;
    }

    public int getDailyStock() {
        return dailyStock;
    }

    public void setDailyStock(int dailyStock) {
        this.dailyStock = dailyStock;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
