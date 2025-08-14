package io.src.model.GameObject;

import com.badlogic.gdx.Input;
import io.src.model.Clickable;
import io.src.model.MapModule.Position;
import io.src.model.items.Inventory;

public class Refrigerator extends GameObject implements Clickable, SensitiveToPlayer {
    private Inventory inventory;

    public Refrigerator(boolean walkable, Position position, Inventory inventory) {
        super(walkable, position);
        this.inventory = inventory;
    }


    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public String getAssetName() {
        return "Refrigerator_State1";
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean onPlayerGoesNearby(float distance) {
        return false;
    }

    @Override
    public boolean onPlayerGetsFar(float distance) {
        return false;
    }

    @Override
    public boolean onPlayerFocus() {
        return false;
    }

    @Override
    public boolean onPlayerDefocus() {
        return false;
    }

    @Override
    public float getSensitivityDistance() {
        return 2;
    }
}
