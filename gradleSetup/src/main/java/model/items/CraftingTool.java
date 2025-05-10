package model.items;

import model.Activities.ArtesianRecepie;
import model.Ingredient;

import java.util.ArrayList;
import java.util.HashMap;

public class CraftingTool extends Item {
    private final String description;
    private final int sellPrice;
    private ArrayList<ArtesianRecepie> craftingRecepie = new ArrayList<>();

    public CraftingTool(String name, String description, int sellPrice) {
        super(name,1,false);
        this.description = description;
        this.sellPrice = sellPrice;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public ArrayList<ArtesianRecepie> getCraftingRecepie() {
        return craftingRecepie;
    }

    public void setCraftingRecepie(ArrayList<ArtesianRecepie> craftingRecepie) {
        this.craftingRecepie = craftingRecepie;
    }
}
