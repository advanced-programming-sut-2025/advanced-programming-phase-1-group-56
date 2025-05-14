package model.Enums;

public enum BackPackType {
    InitialBackpack(12),
    BigBackpack(24),
    DeluxeBackpack(Integer.MAX_VALUE);

    private final int capacity;

    BackPackType(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getPrice() {
        return price;
    }
}