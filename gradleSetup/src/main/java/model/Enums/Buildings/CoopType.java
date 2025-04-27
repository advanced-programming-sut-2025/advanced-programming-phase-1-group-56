package model.Enums.Buildings;

public enum CoopType {
    normalCoop(4, 0),//TODO
    bigCoop(8, 0),//TODO
    deluxeCoop(12, 0);//TODO
    private final int capacity;
    private final int price;

    CoopType(int capacity, int price) {
        this.capacity = capacity;
        this.price = price;
    }

    public int getCapacity() {
        return capacity;
    }
}