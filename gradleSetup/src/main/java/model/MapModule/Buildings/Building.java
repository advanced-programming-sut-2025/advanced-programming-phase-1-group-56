package model.MapModule.Buildings;

import model.Enums.Buildings.BuildingType;
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
     protected final BuildingType buildingType;

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

    public int getHeight() {
        return height;
    }

//    protected boolean [][] walkable;

    public Building(Position position ,boolean walkable,String name,Position doorPosition,int height,int width,BuildingType buildingType) {
        super(walkable,position);
        this.name = name;
        this.indoor = null;
        this.doorPosition = doorPosition;
        this.height = height;
        this.width = width;
        this.buildingType = buildingType;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public abstract void interact();
}
