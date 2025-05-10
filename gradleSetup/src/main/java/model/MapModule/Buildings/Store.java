package model.MapModule.Buildings;

import model.GameObject.NPC.NPC;
import model.GameObject.NPC.NpcGift;
import model.GameObject.NPC.NpcProduct;
import model.MapModule.GameLocations.GameLocation;
import model.MapModule.Position;
import model.Slot;
import model.items.Item;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Store extends Building {
    private NPC owner;
    private int openingHour;
    private int closingHour;
    private ArrayList<NpcProduct> products;


    public void setOwner(NPC owner) {
        this.owner = owner;
    }

    public void setClosingHour(int closingHour) {
        this.closingHour = closingHour;
    }

    public void setOpeningHour(int openingHour) {
        this.openingHour = openingHour;
    }

    public Store(boolean walkable, String name, Position doorPosition, Position startingPosition, int height, int width) {
        super(walkable,name,doorPosition,startingPosition,height,width);
    }
    public Store(String name, Position position,Position doorPosition, int width, int height, boolean walkable, NPC owner, int openingHour, int closingHour, ArrayList<NpcProduct> products) {
        super(walkable,name, doorPosition,position, height,width);
        this.products = products;
        this.name = name;
        this.owner = owner;
        this.openingHour = openingHour;
        this.closingHour = closingHour;
    }

    public int getClosingHour() {
        return closingHour;
    }

    public int getOpeningHour() {
        return openingHour;
    }

    public NPC getOwner() {
        return owner;
    }

    public ArrayList<NpcProduct> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<NpcProduct> products) {
        this.products = products;
    }
}
