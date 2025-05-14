package model.GameObject;

import model.Enums.Items.GrassType;
import model.MapModule.Position;

public class Grass extends GameObject{
    private final GrassType grassType;

    public Grass(boolean walkable, Position position, GrassType grassType ) {
        super(walkable, position);
        this.grassType = grassType;
    }


    public GrassType getGrassType() {
        return grassType;
    }
}
