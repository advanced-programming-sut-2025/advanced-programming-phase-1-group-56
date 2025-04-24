package model.GameObject;

import model.Locations.Position;
import model.items.Item;

import java.util.HashMap;

public class Foraging extends GameObject {
    private final String name;
    private final String description;
    private int sellPrice;
    HashMap<Item,Integer> itemsGiven;

    public Foraging(Position position,int width,int height,String name,String description,int sellPrice,HashMap<Item,Integer> itemsGiven)
    {
        super(position,width,height);
        this.sellPrice=sellPrice;
        this.name=name;
        this.description=description;
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

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }
}
