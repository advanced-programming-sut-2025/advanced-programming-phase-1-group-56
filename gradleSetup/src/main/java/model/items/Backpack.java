package model.items;

public class Backpack extends Item {
    private int capacity;

    public Backpack(String name, int maxStackSize, boolean Stackable) {
        super(name, maxStackSize, Stackable);
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getPrice(){
        return -1 ;
    }
}
