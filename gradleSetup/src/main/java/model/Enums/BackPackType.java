package model.Enums;

import model.items.Saleable;

public enum BackPackType implements Saleable {
    DeluxeBackpack(Integer.MAX_VALUE,null),
    BigBackpack(24,DeluxeBackpack),
    InitialBackpack(12,BigBackpack);

    private final int capacity;
    private final BackPackType next;

    BackPackType(int capacity, BackPackType next) {
        this.capacity = capacity;
        this.next = next;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String getName() {
        return this.toString();
    }

    public BackPackType getNext() {
        return next;
    }


}