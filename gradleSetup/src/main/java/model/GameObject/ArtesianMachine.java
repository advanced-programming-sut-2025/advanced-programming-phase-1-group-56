package model.GameObject;

import model.Enums.GameObjects.ArtisanMachineType;
import model.Enums.Items.ArtisanGoodType;

public class ArtesianMachine extends GameObject {
    private ArtisanMachineType artesianMachineType;
    private ArtisanGoodType artisanGoodType;
    private ArtisanGood artisanGood;

    private int processTime;


    public ArtesianMachine(boolean walkable) {
        super(walkable);
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
