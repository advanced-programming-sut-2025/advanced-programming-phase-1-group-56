package model.items;

import model.Enums.Items.ItemQuality;

public abstract class Item implements Saleable {
    protected boolean Stackable;
    protected int maxStackSize;
    protected String name;
    protected int price;
    protected ItemQuality itemQuality;

    public Item(String name, int maxStackSize, boolean Stackable,int price) {
        this.name = name;
        this.maxStackSize = maxStackSize;
        this.Stackable = Stackable;
        this.price = price;
        this.itemQuality = ItemQuality.Normal;
    }

    public void deleteItem() {
    }

    public void dropItem() {
    }

    public String getName() {
        return name;
    }
    public int getMaxStackSize() {
        return maxStackSize;
    }

    public int getPrice() {
        return price;
    }

    public void setItemQuality (ItemQuality itemQuality) {
        this.itemQuality = itemQuality;
    }

    public ItemQuality getItemQuality() {
        return itemQuality;
    }

}
