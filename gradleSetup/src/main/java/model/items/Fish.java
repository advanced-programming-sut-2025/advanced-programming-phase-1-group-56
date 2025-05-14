package model.items;

import model.Enums.Items.FishType;
import model.Enums.WeatherAndTime.Seasons;

public class Fish extends Item {
    private final FishType fishType;

    public Fish(FishType fishType) {
        super(fishType.getName(),100, false, fishType.getPrice());
        this.fishType = fishType;
    }


    public String getName() {
        return fishType.getName();
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
}
