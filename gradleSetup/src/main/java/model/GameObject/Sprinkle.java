package model.GameObject;

import model.Enums.GameObjects.EtcObjectType;
import model.MapModule.Position;

public class Sprinkle extends GameObject {
    private EtcObjectType type;
    public Sprinkle( Position position,EtcObjectType type) {
        super(true,position);
        this.type = type;
    }

    public EtcObjectType getType() {
        return type;
    }

    public void setType(EtcObjectType type) {
        this.type = type;
    }
}
