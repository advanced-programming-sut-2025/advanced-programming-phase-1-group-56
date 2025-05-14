package model.MapModule.Buildings;

import model.GameObject.NPC.NPC;
import model.GameObject.NPC.NpcProduct;
import model.MapModule.Position;
import model.TimeSystem.DateTime;
import model.TimeSystem.TimeObserver;

import java.util.ArrayList;

public abstract class Store extends Building implements TimeObserver {
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

    public Store(Position position ,boolean walkable, String name, Position doorPosition, int height, int width) {
        super(position,walkable,name,doorPosition,height,width);
    }
    public Store(Position position, String name,Position doorPosition, int width, int height, boolean walkable, NPC owner, int openingHour, int closingHour, ArrayList<NpcProduct> products) {
        super(position, walkable,name, doorPosition, height,width);
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

    @Override
    public void onHourChanged(DateTime time, boolean newDay) {
        //TODO
    }
}
