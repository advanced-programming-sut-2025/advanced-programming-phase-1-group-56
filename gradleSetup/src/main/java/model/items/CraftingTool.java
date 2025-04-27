package model.items;

import model.Activities.ArtesianRecepie;

import java.util.ArrayList;
import java.util.HashMap;

public class CraftingTool extends Item {
    private final String name;
    private final String description;
    private final String sellPrice;
    private ArrayList<ArtesianRecepie> craftingRecepie = new ArrayList<>();

    public CraftingTool(String name, String description, HashMap<Item, Integer> ingredients, String sellPrice) {
        super(name,1,false);
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

    public ArrayList<ArtesianRecepie> getCraftingRecepie() {
        return craftingRecepie;
    }

    public void setCraftingRecepie(ArrayList<ArtesianRecepie> craftingRecepie) {
        this.craftingRecepie = craftingRecepie;
    }
}
