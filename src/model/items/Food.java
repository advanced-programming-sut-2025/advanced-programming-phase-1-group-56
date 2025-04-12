package model.items;

import java.util.HashMap;

public class Food extends Item {
    private String name;
    private int energy;
    private int price;

    protected Food(String name, HashMap<Item, Integer> ingredients,int energy, int price) {
        this.name = name;
        this.energy = energy;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getEnergy() {
        return energy;
    }

    public int getPrice() {
        return price;
    }
}
