package model.Activities;

import model.items.CraftingTool;
import model.items.Item;

import java.util.HashMap;

public class ArtesianRecepie {
    private CraftingTool station;
    private int minLevel;
    private Item resultItem;
    private HashMap<Item, Integer> ingredients = new HashMap<>();
    private int energy;
    private int price;
    private int basePrice;
    private int processingTime;
    private int SellPrice;

    public ArtesianRecepie(CraftingTool station, int minLevel, Item resultItem, int energy, int price) {
        this.station = station;
        this.minLevel = minLevel;
        this.resultItem = resultItem;
        this.energy = energy;
        this.price = price;
    }

    public CraftingTool getStation() {
        return station;
    }

    public void setStation(CraftingTool station) {
        this.station = station;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(int minLevel) {
        this.minLevel = minLevel;
    }

    public Item getResultItem() {
        return resultItem;
    }

    public void setResultItem(Item resultItem) {
        this.resultItem = resultItem;
    }

    public HashMap<Item, Integer> getIngredients() {
        return ingredients;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(int processingTime) {
        this.processingTime = processingTime;
    }

    public int getSellPrice() {
        return SellPrice;
    }

    public void setSellPrice(int sellPrice) {
        SellPrice = sellPrice;
    }
}
