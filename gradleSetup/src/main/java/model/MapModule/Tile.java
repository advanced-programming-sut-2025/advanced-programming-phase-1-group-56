package model.MapModule;

import com.google.gson.annotations.Expose;
import model.Enums.TileType;
import model.GameObject.GameObject;
import model.MapModule.GameLocations.GameLocation;

import java.util.ArrayList;

public class Tile extends Node{
    private Position position;
    private boolean isWalkable; // این به‌صورت دستی باید مقداردهی بشه یا از objectها گرفته بشه
    @Expose(deserialize = false, serialize = false)
    private GameObject fixedObject;
    private TileType tileType;

    public Tile(Position position,boolean isWalkable ,TileType tileType) {
        this.position = position;
        this.fixedObject = null;
        this.isWalkable = isWalkable;
        this.tileType = tileType;
    }

    public boolean isWalkable() {
        return isWalkable;
    }

    public Position getPosition() {
        return position;
    }

    public GameObject getFixedObject() {
        return fixedObject;
    }

//    public LivingEntity getLivingEntity() {
//        return livingEntity;
//    }
//
//    public void setLivingEntity(LivingEntity entity) {
//        this.livingEntity = entity;
//        this.isWalkable = calculateWalkable(); // بروزرسانی وضعیت راه‌پذیری
//    }

    public void setFixedObject(GameObject fixedObject) {
        this.fixedObject = fixedObject;
        this.isWalkable = fixedObject.isWalkable();
    }

    public TileType getTileType() {
        return tileType;
    }

    @Override
    public void calculateNeighbours(Network network) {
        GameLocation grid = (GameLocation) network;

        ArrayList<Node> nodes = new ArrayList<>();

        int minX = 0;
        int minY = 0;
        int maxX = grid.getTiles().length-1;
        int maxY = grid.getTiles()[0].length - 1;

        if (position.getX() > minX) {
            nodes.add(grid.getTileByPosition(position.getX() - 1 , position.getY())); //west
        }

        if (position.getX() < maxX) {
            nodes.add(grid.getTileByPosition(position.getX()+1, position.getY())); //east
        }

        if (position.getY() > minY) {
            nodes.add(grid.getTileByPosition(position.getX(), position.getY() -1)); //north
        }

        if (position.getY() < maxY) {
            nodes.add(grid.getTileByPosition(position.getX(), position.getY() + 1)); //south
        }

        if (position.getX() > minX && position.getY() > minY) {
            nodes.add(grid.getTileByPosition(position.getX() - 1 , position.getY()- 1)); //northwest
        }

        if (position.getX() < maxX && position.getY() < maxY) {
            nodes.add(grid.getTileByPosition(position.getX() + 1, position.getY() + 1)); //southeast
        }

        if(position.getX() < maxX && position.getY() > minY){
            nodes.add(grid.getTileByPosition(position.getX() + 1, position.getY() - 1)); //northeast
        }

        if(position.getX() > minY && position.getY() < maxY){
            nodes.add(grid.getTileByPosition(position.getX() - 1, position.getY() + 1)); //southwest
        }

        setNeighbours(nodes);
    }

    @Override
    public double heuristic(Node dest) {
        return distanceTo(dest);
    }

    @Override
    public double distanceTo(Node dest) {
        Tile d = (Tile) dest;
        return Math.sqrt(Math.pow(d.position.getX() - position.getX() , 2) + Math.pow(d.position.getY() - position.getY() , 2));
    }
}
