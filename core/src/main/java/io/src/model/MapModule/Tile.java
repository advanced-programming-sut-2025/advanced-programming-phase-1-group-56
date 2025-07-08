package io.src.model.MapModule;

import com.google.gson.annotations.Expose;
import kotlin.jvm.Transient;
import io.src.model.Enums.TileType;
import io.src.model.GameObject.GameObject;
import io.src.model.MapModule.Buildings.Building;
import io.src.model.MapModule.GameLocations.GameLocation;

import java.util.ArrayList;

public class Tile extends Node {
    private Position position;
    private boolean isWalkable;
    @Expose(deserialize = false, serialize = false)
    private GameObject fixedObject;
    private TileType tileType;

    public Tile(Position position, boolean isWalkable, TileType tileType) {
        this.position = position;
        this.fixedObject = null;
        this.isWalkable = isWalkable;
        this.tileType = tileType;
    }

    public boolean isWalkable() {
        boolean objectWalk = fixedObject == null || fixedObject.isWalkable();
        if (
                fixedObject != null &&
                        fixedObject instanceof Building b &&
                        b.getDoorPosition().getX() == this.getPosition().getX() &&
                        b.getDoorPosition().getY() == this.getPosition().getY()
        ) return true;
        return isWalkable && objectWalk;
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
        int maxY = grid.getTiles().length - 1;
        int maxX = grid.getTiles()[0].length - 1;

        if (position.getX() > minX) {
            nodes.add(grid.getTileByPosition((int)position.getX() - 1, (int)position.getY())); //west
        }

        if (position.getX() < maxX) {
            nodes.add(grid.getTileByPosition((int)position.getX() + 1, (int)position.getY())); //east
        }

        if (position.getY() > minY) {
            nodes.add(grid.getTileByPosition((int)position.getX(), (int)position.getY() - 1)); //north
        }

        if (position.getY() < maxY) {
            nodes.add(grid.getTileByPosition((int)position.getX(), (int)position.getY() + 1)); //south
        }

        if (position.getX() > minX && position.getY() > minY) {
            nodes.add(grid.getTileByPosition((int)position.getX() - 1, (int)position.getY() - 1)); //northwest
        }

        if (position.getX() < maxX && position.getY() < maxY) {
            nodes.add(grid.getTileByPosition((int)position.getX() + 1, (int)position.getY() + 1)); //southeast
        }

        if (position.getX() < maxX && position.getY() > minY) {
            nodes.add(grid.getTileByPosition((int)position.getX() + 1, (int)position.getY() - 1)); //northeast
        }

        if (position.getX() > minY && position.getY() < maxY) {
            nodes.add(grid.getTileByPosition((int)position.getX() - 1, (int)position.getY() + 1)); //southwest
        }

        setNeighbours(nodes);
    }

    @Override
    public boolean isValid() {
        return this.isWalkable();
    }


    @Override
    public double heuristic(Node dest) {
        return distanceTo(dest);
    }


    @Override
    public double distanceTo(Node dest) {
        Tile d = (Tile) dest;
        return Math.sqrt(Math.pow(d.position.getX() - position.getX(), 2) + Math.pow(d.position.getY() - position.getY(), 2));
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

    public void setWalkable(boolean walkable) {
        isWalkable = walkable;
    }
}
