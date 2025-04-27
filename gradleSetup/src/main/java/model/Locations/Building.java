package model.Locations;

import model.GameObject.GameObject;

public abstract class Building extends GameObject {
    protected String name;
    protected final int InsideWidth;
    protected final int InsideHeight;

    public Building(String name, Position position, int width, int height, int insideWidth, int insideHeight, boolean[][] walkable) {
        super(position, width, height, walkable);
        this.name = name;
        this.width = width;
        this.height = height;
        this.InsideWidth = insideWidth;
        this.InsideHeight = insideHeight;
    }

    public String getName() {
        return name;
    }

    public abstract void interact();
}
