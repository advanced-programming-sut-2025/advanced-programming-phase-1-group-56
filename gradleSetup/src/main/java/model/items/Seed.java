package model.items;

import model.Enums.GameObjects.CropType;
import model.Enums.Items.SeedType;
import model.Enums.WeatherAndTime.Seasons;
import model.GameObject.Crop;

public class Seed extends Item {
    private SeedType seedType;

    public Seed( SeedType seedType) {
        super(seedType.name, 100,true,-1);
        this.seedType = seedType;
    }

    public SeedType getSeedType() {
        return seedType;
    }

    public Seasons[] getSeason(){
        return seedType.season;
    }
    public int getPrice() {
        return -1;
    }
    public CropType getCrop(){
        return seedType.cropType;
    }

}
