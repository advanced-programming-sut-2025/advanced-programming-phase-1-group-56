package model.Activities;

import model.items.Item;

import java.util.HashMap;

public abstract class CookFood {
    private String name;
    private HashMap<Item, Integer> ingredients = new HashMap<>();
    private int energy;
    private int price;

    protected CookFood(String name, HashMap<Item, Integer> ingredients, int energy, int price) {
        this.name = name;
        this.ingredients = ingredients;
        this.energy = energy;
        this.price = price;
    }


    public HashMap<Item, Integer> getIngredients() {
        return ingredients;
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
