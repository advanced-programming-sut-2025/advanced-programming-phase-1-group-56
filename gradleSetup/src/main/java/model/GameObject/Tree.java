package model.GameObject;

import model.Enums.WeatherAndTime.Seasons;
import model.Locations.Position;
import model.items.Item;

import java.util.ArrayList;
import java.util.HashMap;

public class Tree extends GameObject {
    private final ArrayList<Integer> stages;
    private final boolean recollectable;
    private final int recollectDuration;
    private final ArrayList<Seasons> seasons;
    private int currentStage;
    private final HashMap<Item,Integer> itemsGiven;

    public Tree (Position position, int width, int height, ArrayList<Integer> stages, boolean recollectable,
                 int recollectDuration, ArrayList<Seasons> seasons,HashMap<Item,Integer> itemsGiven, boolean walkable[][])
    {
        super(position,width,height,walkable);
        this.stages = stages;
        this.recollectable = recollectable;
        this.recollectDuration = recollectDuration;
        this.seasons = seasons;
        this.itemsGiven = itemsGiven;
    }

    public ArrayList<Seasons> getSeasons() {
        return seasons;
    }

    public int getRecollectDuration() {
        return recollectDuration;
    }

    public boolean isRecollectable() {
        return recollectable;
    }

    public ArrayList<Integer> getStages() {
        return stages;
    }

    public int getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(int currentStage) {
        this.currentStage = currentStage;
    }

    public HashMap<Item, Integer> getItemsGiven() {
        return itemsGiven;
    }
}
