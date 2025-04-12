package model.GameObject;

import model.Locations.Position;

public abstract class GameObject {
    protected Position position;
    protected boolean [][] walkable;
    protected int width;
    protected int height;

    public GameObject(Position position, int width, int height, boolean[][] walkable) {
        this.position = position;
        this.width = width;
        this.height = height;
        this.walkable = walkable;
    }

    public Position getPosition() {
        return position;
    }
}
