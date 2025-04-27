package model.GameObject.Animal;

import model.Enums.WeatherAndTime.Seasons;

public class Fish {
    private String name;
    private int basePrice;
    private Seasons season;
    private boolean isLegendary;

    public Fish(String name, int basePrice, Seasons season, boolean isLegendary) {
        this.name = name;
        this.basePrice = basePrice;
        this.season = season;
        this.isLegendary = isLegendary;
    }

    public String getName() {
        return name;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public Seasons getSeason() {
        return season;
    }

    public boolean isLegendary() {
        return isLegendary;
    }
}
