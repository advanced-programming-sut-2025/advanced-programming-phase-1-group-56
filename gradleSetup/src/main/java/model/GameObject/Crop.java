package model.GameObject;

import model.Enums.GameObjects.CropType;
import model.MapModule.Position;

public class Crop extends GameObject {
    private CropType cropType;

    public Crop(boolean walkable, Position position,  CropType cropType) {
        super(walkable, position);
        this.cropType = cropType;
    }


    public CropType getCropType() {
        return cropType;
    }
}
