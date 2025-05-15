package model.GameObject;

import model.Enums.GameObjects.EtcObjectType;
import model.MapModule.Position;

public class EtcObject extends GameObject{
    private EtcObjectType etcObjectType;
    public EtcObject(boolean walkable, Position position , EtcObjectType etcObjectType) {
        super(walkable, position);
        this.etcObjectType = etcObjectType;
    }
    public EtcObjectType getEtcObjectType() {
        return etcObjectType;
    }
}
