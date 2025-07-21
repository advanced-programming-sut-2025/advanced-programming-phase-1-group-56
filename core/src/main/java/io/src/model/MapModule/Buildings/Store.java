package io.src.model.MapModule.Buildings;

import io.src.model.Enums.Buildings.BuildingType;
import io.src.model.GameObject.NPC.NPC;
import io.src.model.GameObject.NPC.NpcProduct;
import io.src.model.MapModule.Position;
import io.src.model.TimeSystem.TimeObserver;

import java.util.ArrayList;

public abstract class Store extends Building implements TimeObserver {
    private NPC owner;
    private int openingHour;
    private int closingHour;
    protected ArrayList<NpcProduct> dailyProductList = new ArrayList<>();

    public void setOwner(NPC owner) {
        this.owner = owner;
    }

    public void setClosingHour(int closingHour) {
        this.closingHour = closingHour;
    }

    public void setOpeningHour(int openingHour) {
        this.openingHour = openingHour;
    }

    public Store(Position position, boolean walkable, String name, Position doorPosition, int height, int width) {
        super(position, walkable, name, doorPosition, height, width , BuildingType.STORE);
    }

    public Store(Position position, String name, Position doorPosition, int width, int height, boolean walkable, NPC owner, int openingHour, int closingHour, ArrayList<NpcProduct> products) {
        super(position, walkable, name, doorPosition, height, width , BuildingType.STORE);
        this.name = name;
        this.owner = owner;
        this.openingHour = openingHour;
        this.closingHour = closingHour;
        this.dailyProductList = products;
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

    public ArrayList<NpcProduct> getDailyProductList() {
        return dailyProductList;
    }

    public void setDailyProductList(ArrayList<NpcProduct> dailyProductList) {
        this.dailyProductList = dailyProductList;
    }

    public NpcProduct getProductByName(String name) {
        for (NpcProduct product : dailyProductList) {
            if (product.getName().equalsIgnoreCase(name) || product.getSaleable().getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }
}
