package model.items;

public abstract class Item {
    protected boolean Stackable;
    protected int maxStackSize;

    protected String name;

    public Item(String name, int maxStackSize, boolean Stackable) {
        this.name = name;
        this.maxStackSize = maxStackSize;
        this.Stackable = Stackable;
    }

    public void deleteItem() {
    }

    public void dropItem() {
    }
}
