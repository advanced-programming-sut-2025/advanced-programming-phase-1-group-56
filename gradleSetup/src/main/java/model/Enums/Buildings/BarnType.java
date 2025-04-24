package model.Enums.Buildings;

public enum BarnType {
    normalBarn(4, 0),//TODO
    bigBarn(8, 0),//TODO
    deluxeBarn(12, 0);//TODO
    private final int capacity;
    private final int price;

    BarnType(int capacity, int price) {
        this.capacity = capacity;
        this.price = price;
    }

    public int getCapacity() {
        return capacity;
    }
}
