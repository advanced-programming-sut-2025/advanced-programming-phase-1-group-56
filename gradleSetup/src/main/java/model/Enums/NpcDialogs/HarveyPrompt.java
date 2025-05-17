package model.Enums.NpcDialogs;

import model.Enums.WeatherAndTime.WeatherType;

import java.util.*;

public enum HarveyPrompt implements NpcPrompt {
    Sunny_HEALTHY(
            "On a Sunny afternoon, Harvey discusses the benefits of a healthy heart.",
            WeatherType.Sunny, true, 1),
    Sunny_SURGERY(
            "Harvey shyly explains a minor surgery he performed this morning.",
            WeatherType.Sunny, true, 2),
    Sunny_GARDEN(
            "Harvey admires the Sunny day as he tends to his hospital garden.",
            WeatherType.Sunny, true, 3),
    Rainy_MEDICINE(
            "On a Rainy day, Harvey patiently reviews new medicine samples.",
            WeatherType.Rainy, true, 1),
    Rainy_STORY(
            "Harvey, candle flicker and rain outside, shares an old medical story.",
            WeatherType.Rainy, true, 2),
    Rainy_ADVICE(
            "Shy Harvey, listening to raindrops, gives heartfelt health advice.",
            WeatherType.Rainy, true, 3),
    SNOWY_RECOVERY(
            "On a snowy evening, Harvey reflects on a patient's miraculous recovery.",
            WeatherType.Snow, false, 1),
    SnowY_CHILDHOOD(
            "Harvey wistfully recalls his snowy childhood hometown memories.",
            WeatherType.Snow, false, 2),
    StormY_CONCERN(
            "During a Stormy night, Harvey worries someone might need emergency care.",
            WeatherType.Storm, false, 2),
    StormY_HOPE(
            "Amid the storm, Harvey offers hopeful words about tomorrow’s weather and health.",
            WeatherType.Storm, false, 3);

    private final String prompt;
    private final WeatherType weather;
    private final boolean day;
    private final int minFriendship;

    HarveyPrompt(String prompt,
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
        List<HarveyPrompt> filtered = Arrays.stream(values())
                .filter(p -> p.weather == w)
                .filter(p -> p.day == isDay)
                .filter(p -> p.minFriendship == friendshipLevel)
                .toList();
        if (filtered.isEmpty()) {
            return "Harvey gives a reassuring smile, but says nothing more.";
        }
        return filtered.get(new Random().nextInt(filtered.size())).getPrompt();
    }
}
