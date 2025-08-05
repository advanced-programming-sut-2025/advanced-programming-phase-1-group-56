package io.src.model.items;

import io.src.model.Enums.Items.FishType;
import io.src.model.Enums.WeatherAndTime.Seasons;

public class Fish extends Item {
    private final FishType fishType;

    public Fish(FishType fishType) {
        super(fishType.getName(), 9999, true, fishType.getPrice());
        this.fishType = fishType;
    }


    public int getBasePrice() {
        return fishType.getPrice();
    }

    public Seasons getSeason() {
        return fishType.getSeason();
    }

    public boolean isLegendary() {
        return fishType.isLegendary();
    }

    @Override
    public String getAssetName() {
        return fishType.getAssetName();
    }
}
