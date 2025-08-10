package io.src.model.GameObject;

import io.src.model.Enums.GameObjects.EtcObjectType;
import io.src.model.MapModule.Position;

public class Sprinkle extends GameObject {
    private EtcObjectType type;

    public Sprinkle(Position position, EtcObjectType type) {
        super(true, position);
        this.type = type;
    }

    public EtcObjectType getType() {
        return type;
    }

    public void setType(EtcObjectType type) {
        this.type = type;
    }


    @Override
    public String getAssetName() {
        return type.getAssetName();
    }
}
