package model.MapModule.Buildings;

import model.GameObject.Animal;
import model.MapModule.Position;

import java.util.ArrayList;

public class Coop extends Building {
    private int capacity;
    private int price;
    private ArrayList<Animal> animals = new ArrayList<>();


    public Coop(String name, Position position,Position doorPosition, int width, int height, boolean walkable, int capacity, int price) {
        super(position,walkable,name, doorPosition, height,width);
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
