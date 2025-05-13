package model.Activities;

import com.google.gson.annotations.Expose;
import model.Ingredient;
import model.items.Item;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class CookFood {
    private String name;
    private int energy;
    private String source;
    private String buff;
    private int price;
    private Ingredient[] ingredients ;


    protected CookFood(String name, Ingredient[] ingridient , int energy, String source , String buff, int price) {
        this.name = name;
        this.energy = energy;
        this.source =  source;
        this.buff = buff;
        this.price = price;
        this.ingredients = ingridient;
    }

    public Ingredient[] getIngredients() {
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

    public String getSource() {
        return source;
    }

    public String getBuff() {
        return buff;
    }
}
