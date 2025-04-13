package model.Locations;

import model.GameObject.Animal.Animal;

import java.util.ArrayList;

public class Barn extends Building {
    private int capacity;
    private int price;
    private ArrayList<Animal> animals = new ArrayList<>();



    public Barn(String name, Position position, int width, int height, int insideWidth, int insideHeight, boolean[][] walkable,  int capacity, int price) {
        super(name, position, width, height, insideWidth, insideHeight, walkable);
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
    public List<Animal> getAnimals() {
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
