package model.Activities;

import model.items.Item;

import java.util.HashMap;

public abstract class CraftTool {
    private String name;
    private String description;
    private HashMap<Item, Integer> ingredients = new HashMap<>();
    private String sellPrice;

    public CraftTool(String name, String description, HashMap<Item, Integer> ingredients, String sellPrice) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.sellPrice = sellPrice;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public HashMap<Item, Integer> getIngredients() {
        return ingredients;
    }

    public String getSellPrice() {
        return sellPrice;
    }

}
