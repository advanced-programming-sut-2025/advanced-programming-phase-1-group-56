package io.src.model.GameObject;

import io.src.model.Enums.Items.GrassType;
import io.src.model.MapModule.Position;

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
