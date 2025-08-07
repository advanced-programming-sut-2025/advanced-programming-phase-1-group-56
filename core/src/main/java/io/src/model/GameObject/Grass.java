package io.src.model.GameObject;

import io.src.model.Enums.Items.GrassType;
import io.src.model.MapModule.Position;

public class Grass extends GameObject implements SensitiveToPlayer {
    private final GrassType grassType;
    private String assetName;

    public Grass(boolean walkable, Position position, GrassType grassType) {
        super(walkable, position);
        this.grassType = grassType;
        assetName = grassType.getAssetName();
    }


    public GrassType getGrassType() {
        return grassType;
    }

    @Override
    public String getAssetName() {
        return assetName;
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
        return 1;
    }
}
