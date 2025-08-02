package io.src.model.items;

import io.src.model.Enums.GameObjects.initialPlant;
import io.src.model.Enums.Items.SeedType;
import io.src.model.Enums.WeatherAndTime.Seasons;

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

    @Override
    public String getAssetName() {
        return seedType.getAssetName();
    }

    public initialPlant getCrop(){
        return seedType.cropType;
    }

}
