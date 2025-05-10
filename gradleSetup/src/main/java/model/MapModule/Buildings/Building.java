package model.MapModule.Buildings;

import model.GameObject.GameObject;
import model.MapModule.GameLocations.GameLocation;
import model.MapModule.Position;

public abstract class Building extends GameObject {
     protected GameLocation indoor;
     protected String name;
     protected Position doorPosition;
     protected Position startPosition;
     protected final int height;
     protected final int width;

//    public boolean[][] getWalkable() {
//        return walkable;
//    }
//
//    public void setWalkable(boolean[][] walkable) {
//        this.walkable = walkable;
//    }

    public GameLocation getIndoor() {
        return indoor;
    }

    public void setIndoor(GameLocation indoor) {
        this.indoor = indoor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Position getDoorPosition() {
        return doorPosition;
    }

    public void setDoorPosition(Position doorPosition) {
        this.doorPosition = doorPosition;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }

    public int getWidth() {
        return width;
    }

//    protected boolean [][] walkable;

    public Building(boolean walkable,String name/*,GameLocation indoor*/,Position doorPosition,Position startPosition,int height,int width) {
        super(walkable);
        this.name = name;
        this.indoor = null;
        this.doorPosition = doorPosition;
        this.startPosition = startPosition;
        this.height = height;
        this.width = width;
    }

    public abstract void interact();
}
