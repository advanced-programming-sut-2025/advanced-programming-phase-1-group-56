package model.Activities;

import com.google.gson.annotations.Expose;
import model.items.Item;

import java.util.HashMap;

public abstract class CookFood {
    private String name;
    private String ingridient;
    private int energy;
    private String source;
    private String buff;
    private int price;
    @Expose(serialize = false, deserialize = false)
    private HashMap<Item, Integer> ingredients = new HashMap<>();
    @Expose(serialize = false, deserialize = false)
    private int buyPrice;

    protected CookFood(String name, String ingridient, int energy, String source , String buff, int price) {
        this.name = name;
        this.ingridient = ingridient;
        this.energy = energy;
        this.source =  source;
        this.buff = buff;
        this.price = price;
    }

    public void setIngredients(HashMap<Item, Integer> ingredients) {
        this.ingredients = ingredients;
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

    public String getIngridient() {
        return ingridient;
    }

    public void setIngridient(String ingridient) {
        this.ingridient = ingridient;
    }

    public String getSource() {
        return source;
    }

    public String getBuff() {
        return buff;
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(int buyPrice) {
        this.buyPrice = buyPrice;
    }
}
