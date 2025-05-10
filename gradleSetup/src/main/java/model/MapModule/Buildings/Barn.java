package model.MapModule.Buildings;

import model.GameObject.Animal.Animal;
import model.MapModule.Position;

import java.util.ArrayList;

public class Barn extends Building {
    private int capacity;
    private int price;
    private ArrayList<Animal> animals = new ArrayList<>();



    public Barn(boolean walkable,String name, Position position,Position doorPosition, int width, int height, int insideWidth, int insideHeight, int capacity, int price) {
        super(walkable,name,doorPosition, position, height,width);
        this.capacity = capacity;
        this.price = price;
    }

    @Override
    public void interact() {
        //TODO
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public boolean hasFreeCapacity() {
        return animals.size() < this.capacity; // فرض کن فیلد capacity رو داری
    }

    public int getCurrentAnimalCount() {
        return animals.size();
    }

    public int getRemainingCapacity() {
        return this.capacity - animals.size();
    }
}
