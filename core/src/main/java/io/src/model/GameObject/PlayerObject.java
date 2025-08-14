package io.src.model.GameObject;

import io.src.model.MapModule.Position;
import io.src.model.Player;

public class PlayerObject extends GameObject {
    private Player player;
    private String name;
    public PlayerObject(String name,Player player,boolean walkable, Position position) {
        super(walkable, position);
        this.name = name;
        this.player = player;
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
