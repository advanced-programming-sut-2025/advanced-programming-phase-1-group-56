package model.GameObject;

import model.Enums.Items.GrassType;

public class Grass extends GameObject{
    private final GrassType grassType;
    public Grass(boolean walkable , GrassType grassType) {
        super(walkable);
        this.grassType = grassType;
    }

    public GrassType getGrassType() {
        return grassType;
    }
}
