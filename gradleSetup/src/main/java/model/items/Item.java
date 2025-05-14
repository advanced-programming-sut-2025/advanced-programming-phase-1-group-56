package model.items;

public abstract class Item implements Saleable {
    protected boolean Stackable;
    protected int maxStackSize;
    protected String name;
    protected int price;

    public Item(String name, int maxStackSize, boolean Stackable,int price) {
        this.name = name;
        this.maxStackSize = maxStackSize;
        this.Stackable = Stackable;
        this.price = price;
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
}
