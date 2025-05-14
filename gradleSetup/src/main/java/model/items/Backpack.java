package model.items;

import model.Enums.BackPackType;

public class Backpack extends Item {
    private int capacity;
    private BackPackType BackPackType;

    public Backpack(BackPackType BackPackType) {
        super(BackPackType.name(), 0, false,-1);
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

    public BackPackType getBackPackType() {
        return BackPackType;
    }

    public void setBackPackType(BackPackType backPackType) {
        BackPackType = backPackType;
    }
}
