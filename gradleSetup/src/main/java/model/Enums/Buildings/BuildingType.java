package model.Enums.Buildings;

public enum BuildingType {
    COOP(4),
    BIG_COOP(8),
    DELUXE_COOP(12),
    BARN(4),
    BIG_BARN(8),
    DELUXE_BARN(12);


    private final int capacity;

    BuildingType(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

}
