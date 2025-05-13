package model.Activities;

import model.Ingredient;
import model.items.Item;

import java.util.HashMap;

public abstract class CraftTool {
    private String name;
    private String description;
    private Ingredient[] ingredients;
    private int sellPrice;
    private String source;

    public CraftTool(String name, String description, Ingredient[] ingredients, int sellPrice, String source) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.sellPrice = sellPrice;
        this.source = source;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Ingredient[] getIngredients() {
        return ingredients;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public String getSource() {
        return source;
    }
}
