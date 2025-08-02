package io.src.model.GameObject;

import io.src.model.Enums.GameObjects.EtcObjectType;
import io.src.model.MapModule.Position;

public class EtcObject extends GameObject{
    private EtcObjectType etcObjectType;
    public EtcObject(boolean walkable, Position position , EtcObjectType etcObjectType) {
        super(walkable, position);
        this.etcObjectType = etcObjectType;
    }
    public EtcObjectType getEtcObjectType() {
        return etcObjectType;
    }


    @Override
    public String getAssetName() {
        return etcObjectType.getAssetName();
    }
}
