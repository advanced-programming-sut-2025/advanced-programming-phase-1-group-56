package model.Enums.Buildings;

import model.items.Saleable;

public enum BuildingType implements Saleable {
    COOP(4),
    BIG_COOP(8),
    DELUXE_COOP(12),
    BARN(4),
    BIG_BARN(8),
    DELUXE_BARN(12),
    WELL(Integer.MAX_VALUE),
    SHIPPING_BIN(100);


    private final int capacity;

    BuildingType(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }


    @Override
    public String getName() {
        return this.toString();
    }
}
