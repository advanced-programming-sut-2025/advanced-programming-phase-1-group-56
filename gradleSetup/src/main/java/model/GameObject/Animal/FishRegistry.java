package model.GameObject.Animal;

import model.Enums.WeatherAndTime.Seasons;

import java.util.*;

public class FishRegistry {
    private static final List<Fish> allFish = new ArrayList<>();

    static {
        allFish.add(new Fish("Salmon", 75, Seasons.Fall, false));
        allFish.add(new Fish("Sardine", 40, Seasons.Fall, false));
        allFish.add(new Fish("Shad", 60, Seasons.Fall, false));
        allFish.add(new Fish("Blue Discus", 120, Seasons.Fall, false));

        allFish.add(new Fish("Midnight Carp", 150, Seasons.Winter, false));
        allFish.add(new Fish("Squid", 80, Seasons.Winter, false));
        allFish.add(new Fish("Tuna", 100, Seasons.Winter, false));
        allFish.add(new Fish("Perch", 55, Seasons.Winter, false));

        allFish.add(new Fish("Flounder", 100, Seasons.Spring, false));
        allFish.add(new Fish("Lionfish", 100, Seasons.Spring, false));
        allFish.add(new Fish("Herring", 30, Seasons.Spring, false));
        allFish.add(new Fish("Ghostfish", 45, Seasons.Spring, false));

        allFish.add(new Fish("Tilapia", 75, Seasons.Summer, false));
        allFish.add(new Fish("Dorado", 100, Seasons.Summer, false));
        allFish.add(new Fish("Sunfish", 30, Seasons.Summer, false));
        allFish.add(new Fish("Rainbow Trout", 65, Seasons.Summer, false));

        allFish.add(new Fish("Legend", 5000, Seasons.Spring, true));
        allFish.add(new Fish("Glacierfish", 1000, Seasons.Winter, true));
        allFish.add(new Fish("Angler", 900, Seasons.Fall, true));
        allFish.add(new Fish("Crimsonfish", 1500, Seasons.Summer, true));
    }

    public static List<Fish> getAllFish() {
        return allFish;
    }

    public static List<Fish> getFishBySeason(Seasons seasons) {
        //TODO
        return null;
    }

    public static List<Fish> getLegendaryFish() {
        //TODO
        return null;
    }
}
