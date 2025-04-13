package model.GameObject.Animal;

import model.Enums.Animals.AnimalType;
import model.GameObject.LivingEntity;
import model.locations.Position;

import java.util.ArrayList;

public class Animal extends LivingEntity {
    private String name;
    private AnimalType type;
    private int friendship = 0;
    private Position position;
    private int width;
    private int height;
    private int health;//TODO
    private int hungaryBar;//TODO
    private ArrayList<AnimalProduct> products;

    public Animal(Position position, int width, int height, boolean[][] walkable, String name, AnimalType type) {
        super(position, width, height, walkable);
        this.name = name;
        this.type = type;
    }

    public void pet() {
        friendship += 15;
    }

    public int getFriendship() {
        return friendship;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AnimalType getType() {
        return type;
    }

    public void setType(AnimalType type) {
        this.type = type;
    }

    public ArrayList<AnimalProduct> getProducts() {
        return products;
    }

    public void addProducts(AnimalProduct products) {
        this.products.add(products);
    }
}
