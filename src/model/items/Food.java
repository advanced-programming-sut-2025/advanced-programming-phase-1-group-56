package model.items;

import java.util.HashMap;

public class Food extends Item {
    private String name;
    private int energy;
    private int price;

    public Food(String name, int maxStackSize,HashMap<Item, Integer> ingredients,int energy, int price) {
        super(name,maxStackSize,true);
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
