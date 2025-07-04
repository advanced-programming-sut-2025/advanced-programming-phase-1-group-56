package io.src.model.Enums.NpcDialogs;

import io.src.model.Enums.WeatherAndTime.WeatherType;

import java.util.*;

public enum AbigailPrompt implements NpcPrompt {
    Sunny_MUSEUM(
            "On a Sunny day, Abigail eagerly talks about her latest museum find.",
            WeatherType.Sunny, true, 1),
    Sunny_COAL_ART(
            "Shy Abigail, on this Sunny afternoon, sketches coal sculptures in her notebook.",
            WeatherType.Sunny, true, 2),
    Sunny_COFFEE_BREAK(
            "Abigail smiles over a steaming cup of coffee under the bright sun.",
            WeatherType.Sunny, true, 3),
    Rainy_DREAMS(
            "On a Rainy morning, Abigail confides her desire to open an art studio.",
            WeatherType.Rainy, true, 1),
    Rainy_SKETCH(
            "Abigail, rain pattering outside, timidly shows you her latest sketch.",
            WeatherType.Rainy, true, 2),
    Rainy_GEEK_OUT(
            "Shy Abigail, listening to rain, nerds out about geology facts.",
            WeatherType.Rainy, true, 3),
    SNOWY_NOSTALGIA(
            "On a snowy evening, Abigail softly recalls childhood winters in the museum.",
            WeatherType.Snow, false, 1),
    SnowY_CAMPSITE(
            "Abigail dreams aloud of setting up camp amid the falling Snow.",
            WeatherType.Snow, false, 2),
    StormY_REPAIR(
            "During a Stormy night, Abigail nervously wonders if the museum roof leaks.",
            WeatherType.Storm, false, 1),
    StormY_INVENTION(
            "Abigail, sheltering from a storm, excitedly sketches a new gadget design.",
            WeatherType.Storm, false, 3);

    private final String prompt;
    private final WeatherType weather;
    private final boolean day;
    private final int minFriendship;

    AbigailPrompt(String prompt,
                  WeatherType weather,
                  boolean day,
                  int minFriendship) {
        this.prompt = prompt;
        this.weather = weather;
        this.day = day;
        this.minFriendship = minFriendship;
    }

    public String getPrompt()       { return prompt; }
    public WeatherType getWeather(){ return weather; }
    public boolean isDay()          { return day; }
    public int getMinFriendship()   { return minFriendship; }

    public static String randomFor(WeatherType w, boolean isDay, int friendshipLevel) {
        List<AbigailPrompt> filtered = Arrays.stream(values())
                .filter(p -> p.weather == w)
                .filter(p -> p.day == isDay)
                .filter(p -> p.minFriendship == friendshipLevel)
                .toList();
        if (filtered.isEmpty()) {
            return "Abigail gives you a shy nod and stays silent.";
        }
        return filtered.get(new Random().nextInt(filtered.size())).getPrompt();
    }
}

