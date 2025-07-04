package io.src.model.Enums.NpcDialogs;

import io.src.model.Enums.WeatherAndTime.WeatherType;

import java.util.*;

public enum LeahPrompt implements NpcPrompt {
    Sunny_ART(
            "On a Sunny day, Leah excitedly shows you her latest clay sculpture.",
            WeatherType.Sunny, true, 1),
    Sunny_GARDENING(
            "Leah hums happily as she tends her Sunny garden of spring flowers.",
            WeatherType.Sunny, true, 2),
    Sunny_SCULPT(
            "Leah, under bright sun, passionately describes her artistic vision.",
            WeatherType.Sunny, true, 3),
    Rainy_COMFORT(
            "On a Rainy afternoon, Leah offers you warm vegetable soup.",
            WeatherType.Rainy, true, 1),
    Rainy_PAINT(
            "Leah, listening to rain, mixes new colors on her palette.",
            WeatherType.Rainy, true, 2),
    Rainy_CRAFT(
            "Shy Leah, rain splashing, shares a cozy crafting idea.",
            WeatherType.Rainy, true, 3),
    SNOWY_COOKING(
            "On a snowy evening, Leah reminisces about baking seasonal treats.",
            WeatherType.Snow, false, 1),
    SnowY_INSPIRATION(
            "Leah, in the snow’s glow, describes a painting idea it inspired.",
            WeatherType.Snow, false, 2),
    StormY_MUSIC(
            "During a Stormy night, Leah softly plays a tune on her lute.",
            WeatherType.Storm, false, 3),
    StormY_MEMORIES(
            "Leah, sheltering from the storm, shares fond memories of home.",
            WeatherType.Storm, false, 2);

    private final String prompt;
    private final WeatherType weather;
    private final boolean day;
    private final int minFriendship;

    LeahPrompt(String prompt,
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
        List<LeahPrompt> filtered = Arrays.stream(values())
                .filter(p -> p.weather == w)
                .filter(p -> p.day == isDay)
                .filter(p -> p.minFriendship == friendshipLevel)
                .toList();
        if (filtered.isEmpty()) {
            return "Leah smiles gently, choosing to stay quiet.";
        }
        return filtered.get(new Random().nextInt(filtered.size())).getPrompt();
    }
}
