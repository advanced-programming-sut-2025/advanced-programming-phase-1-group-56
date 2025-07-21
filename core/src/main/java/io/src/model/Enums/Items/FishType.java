package io.src.model.Enums.Items;

import io.src.model.Enums.WeatherAndTime.Seasons;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public enum FishType implements ItemType {
    Salmon("Salmon", 75, Seasons.Fall, false, ""),
    Sardine("Sardine", 40, Seasons.Fall, false, ""),
    Shad("Shad", 60, Seasons.Fall, false, ""),
    BlueDiscus("Blue Discus", 120, Seasons.Fall, false, "Blue_Discus"),
    MidnightCarp("Midnight Carp", 150, Seasons.Winter, false, ""),
    Squid("Squid", 80, Seasons.Winter, false, ""),
    Tuna("Tuna", 100, Seasons.Winter, false, ""),
    Perch("Perch", 55, Seasons.Winter, false, ""),
    Flounder("Flounder", 100, Seasons.Spring, false, ""),
    Lionfish("Lionfish", 100, Seasons.Spring, false, ""),
    Herring("Herring", 30, Seasons.Spring, false, ""),
    Ghostfish("Ghostfish", 45, Seasons.Spring, false, ""),
    Tilapia("Tilapia", 75, Seasons.Summer, false, ""),
    Dorado("Dorado", 100, Seasons.Summer, false, ""),
    Sunfish("Sunfish", 30, Seasons.Summer, false, ""),
    RainbowTrout("Rainbow Trout", 65, Seasons.Summer, false, ""),
    Legend("Legend", 5000, Seasons.Spring, true, ""),
    Glacierfish("Glacierfish", 1000, Seasons.Winter, true, ""),
    Angler("Angler", 900, Seasons.Fall, true, ""),
    Crimsonfish("Crimsonfish", 1500, Seasons.Summer, true, ""),
    ANY_FISH("Any Fish", 1500, Seasons.Summer, true, "");


    private final String name;
    private final int price;
    private final Seasons season;
    private final boolean legendary;
    private final String assetName;

    FishType(String name, int price, Seasons season, boolean legendary, String assetName) {
        this.name = name;
        this.price = price;
        this.season = season;
        this.legendary = legendary;
        this.assetName = assetName;
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

    public static ArrayList<FishType> getSeasonFishes(Seasons season) {
        ArrayList<FishType> seasonFishes = new ArrayList<>();
        for (FishType fishType : FishType.values()) {
            if (fishType.getSeason() == season) {
                seasonFishes.add(fishType);
            }
        }
        return seasonFishes;
    }

    public static FishType getCheapestFishOfSeason(Seasons season) {
        FishType cheapestFish = null;
        for (FishType fishType : FishType.values()) {
            if (fishType.getSeason() == season && !fishType.isLegendary()) { // حذف ماهی افسانه‌ای
                if (cheapestFish == null || fishType.getPrice() < cheapestFish.getPrice()) {
                    cheapestFish = fishType;
                }
            }
        }
        return cheapestFish;
    }

    public static FishType fromName(String name) {
        for (FishType type : FishType.values()) {
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

    @Nullable
    public String getAssetName() {
        return switch (assetName) {
            case "null" -> null;
            case "" -> name.replace(" ", "_");
            default -> assetName;
        };
    }
}
