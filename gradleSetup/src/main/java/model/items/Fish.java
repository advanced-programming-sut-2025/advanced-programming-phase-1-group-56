package model.items;

import model.Enums.Items.FishType;
import model.Enums.WeatherAndTime.Seasons;

public class Fish extends Item {
    private FishType fishType;

    public Fish(int maxStackSize, boolean Stackable, FishType fishType) {
        super(fishType.getName(), maxStackSize, Stackable);
        fishType = fishType;
    }


    public String getName() {
        return name;
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
