package model.GameObject;


import model.Enums.GameObjects.TreeType;
import model.MapModule.Position;

public class Tree extends GameObject {
    //    private final ArrayList<Integer> stages;
//    private final boolean recollectable;
//    private final int recollectDuration;
//    private final ArrayList<Seasons> seasons;
    private TreeType treeType;

    private int currentStage;
    //    private final HashMap<Item,Integer> itemsGiven;
    public boolean isWateredToday;

    public Tree (boolean walkable , TreeType treeType, Position position)
    {
        super(walkable,position);
        this.treeType = treeType;
        this.isWateredToday = false;
        this.currentStage = 0;
    }

//    public ArrayList<Seasons> getSeasons() {
//        return treeType.;
//    }

//    public int getRecollectDuration() {
//        return recollectDuration;
//    }
//
//    public boolean isRecollectable() {
//        return recollectable;
//    }


    public TreeType getTreeType() {
        return treeType;
    }

    public boolean isWateredToday() {
        return isWateredToday;
    }

//    public ArrayList<Integer> getStages() {
//        return stages;
//    }

    public int getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(int currentStage) {
        this.currentStage = currentStage;
    }

//    public HashMap<Item, Integer> getItemsGiven() {
//        return itemsGiven;
//    }
}
