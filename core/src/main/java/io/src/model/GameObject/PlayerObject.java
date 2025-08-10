package io.src.model.GameObject;

import io.src.model.MapModule.Position;

public class PlayerObject extends GameObject {
    private String name;
    public PlayerObject(String name,boolean walkable, Position position) {
        super(walkable, position);
        this.name = name;
    }


    @Override
    public String getAssetName() {
        return "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
