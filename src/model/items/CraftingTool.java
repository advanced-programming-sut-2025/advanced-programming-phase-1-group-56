package model.items;

import java.util.HashMap;

public class CraftingTool extends Item {
    private String name;
    private String description;
    private String sellPrice;

    public CraftingTool(String name, String description, HashMap<Item, Integer> ingredients, String sellPrice) {
        this.name = name;
        this.description = description;
        this.sellPrice = sellPrice;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getSellPrice() {
        return sellPrice;
    }
}
