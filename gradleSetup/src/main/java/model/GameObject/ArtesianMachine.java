package model.GameObject;

import model.Enums.GameObjects.ArtisanMachineType;
import model.Enums.GameObjects.CropType;
import model.Enums.Items.ArtisanGoodType;
import model.MapModule.Position;
import model.items.ArtisanGood;

public class ArtesianMachine extends GameObject {
    private ArtisanMachineType artesianMachineType;
    private ArtisanGoodType artisanGoodType;
    private ArtisanGood artisanGood;

    private int processTime;

    public ArtesianMachine(boolean walkable, Position position, ArtisanGoodType artisanGoodType) {
        super(walkable, position);
        this.artisanGoodType = artisanGoodType;
    }


    public void makeArtisanGood(ArtisanGoodType artisanGoodType) {
        this.artisanGoodType = artisanGoodType;
        int processTime = artisanGoodType.getProcessingTime();

    }

    public ArtisanGood getArtisanGood() {
        return artisanGood;
    }

    public void setArtisanGood(ArtisanGood artisanGood) {
        this.artisanGood = artisanGood;
    }
}
