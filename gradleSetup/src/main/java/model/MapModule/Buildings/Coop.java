package model.MapModule.Buildings;

import model.MapModule.Position;

public class Coop extends Building {
    private int capacity;
    private int price;

    public Coop(String name, Position position,Position doorPosition, int width, int height, boolean walkable, int capacity, int price) {
        super(walkable,name, doorPosition,position, height,width);
        this.capacity = capacity;
        this.price = price;
    }

    @Override
    public void interact() {
        //TODO
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
