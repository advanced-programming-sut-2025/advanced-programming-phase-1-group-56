package io.src.model.GameObject;

import io.src.model.MapModule.Position;
import io.src.model.Player;

public class PlayerObject extends GameObject {
    private Player player;

    public PlayerObject(Player player,  Position position) {
        super(false, position);
        this.player = player;
    }


    @Override
    public String getAssetName() {
        return "";
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void setPosition(Position position) {
        super.setPosition(position);
    }
}
