package model.GameObject;

import model.Enums.GameObjects.ForagingCropType;
import model.MapModule.Position;

public class ForagingCrop extends  GameObject {
    public ForagingCropType foragingCropType;
    public ForagingCrop(boolean walkable, Position position, ForagingCropType foragingCropType) {
        super(walkable, position);
        this.foragingCropType = foragingCropType;
    }
    public ForagingCropType getForagingCropType() {
        return foragingCropType;
    }

}
