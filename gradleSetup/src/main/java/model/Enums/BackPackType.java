package model.Enums;

public enum BackPackType {
    InitialBackpack(12,0),//TODO
    BigBackpack(24,0),//TODO
    DeluxeBackpack(Integer.MAX_VALUE,0);//TODO

    private final int capacity;
    private final int price;

    BackPackType(int capacity,int price) {
        this.capacity = capacity;
        this.price = price;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getPrice() {
        return price;
    }
}