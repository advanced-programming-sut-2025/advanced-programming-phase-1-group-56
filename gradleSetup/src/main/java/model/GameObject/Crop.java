package model.GameObject;

import model.Enums.GameObjects.CropType;

public class Crop {
    private CropType cropType;

    public Crop(CropType cropType) {
        this.cropType = cropType;
    }

    public CropType getCropType() {
        return cropType;
    }
}
