package model.Locations;

import model.GameObject.NPC.NPC;
import model.items.Item;

import java.util.HashMap;

public class Store extends Building {

    private final NPC owner;
    private int openingHour;
    private int closingHour;
    private HashMap<Item,Integer> products;
    public Store(String name, Position position, int width, int height, int insideWidth, int insideHeight
                 ,boolean[][] walkable,NPC owner,int openingHour,int closingHour) {
        super(name, position, width, height, insideWidth, insideHeight, walkable);
        this.name = name;
        this.owner = owner;
        this.openingHour = openingHour;
        this.closingHour = closingHour;
    }

    @Override
    public void interact() {

    }

    public int getClosingHour() {
        return closingHour;
    }

    public void setClosingHour(int closingHour) {
        this.closingHour = closingHour;
    }

    public int getOpeningHour() {
        return openingHour;
    }

    public void setOpeningHour(int openingHour) {
        this.openingHour = openingHour;
    }

    public NPC getOwner() {
        return owner;
    }
}
