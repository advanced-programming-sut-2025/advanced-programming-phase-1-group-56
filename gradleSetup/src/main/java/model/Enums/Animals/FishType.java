package model.Enums.Animals;

import model.Enums.WeatherAndTime.Seasons;

public enum FishType {
    Salmon("Salmon", 75, Seasons.Fall, false),
    Sardine("Sardine", 40, Seasons.Fall, false),
    Shad("Shad", 60, Seasons.Fall, false),
    BlueDiscus("Blue Discus", 120, Seasons.Fall, false),
    MidnightCarp("Midnight Carp", 150, Seasons.Winter, false),
    Squid("Squid", 80, Seasons.Winter, false),
    Tuna("Tuna", 100, Seasons.Winter, false),
    Perch("Perch", 55, Seasons.Winter, false),
    Flounder("Flounder", 100, Seasons.Spring, false),
    Lionfish("Lionfish", 100, Seasons.Spring, false),
    Herring("Herring", 30, Seasons.Spring, false),
    Ghostfish("Ghostfish", 45, Seasons.Spring, false),
    Tilapia("Tilapia", 75, Seasons.Summer, false),
    Dorado("Dorado", 100, Seasons.Summer, false),
    Sunfish("Sunfish", 30, Seasons.Summer, false),
    RainbowTrout("Rainbow Trout", 65, Seasons.Summer, false),
    // 🐟 Legendary Fish
    Legend("Legend", 5000, Seasons.Spring, true),
    Glacierfish("Glacierfish", 1000, Seasons.Winter, true),
    Angler("Angler", 900, Seasons.Fall, true),
    Crimsonfish("Crimsonfish", 1500, Seasons.Summer, true);


    private final String name;
    private final int price;
    private final Seasons season;
    private final boolean legendary;

    FishType(String name, int price, Seasons season, boolean legendary) {
        this.name = name;
        this.price = price;
        this.season = season;
        this.legendary = legendary;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public Seasons getSeason() {
        return season;
    }

    public boolean isLegendary() {
        return legendary;
    }
}

