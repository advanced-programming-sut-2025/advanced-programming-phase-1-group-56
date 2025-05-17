package model.Enums.NpcDialogs;

import model.Enums.WeatherAndTime.WeatherType;

import java.util.*;
import java.util.stream.*;

public enum RobinPrompt implements NpcPrompt {
    Sunny_BUILDING(
            "On a Sunny morning, Robin proudly describes the new coop she’s built.",
            WeatherType.Sunny, true, 1),
    Sunny_PLANS(
            "Robin, under bright sun, happily discusses her next carpentry project.",
            WeatherType.Sunny, true, 2),
    Sunny_SAW(
            "Robin demonstrates proper saw technique on a Sunny workbench.",
            WeatherType.Sunny, true, 3),
    Rainy_SHELTER(
            "On a Rainy day, Robin reassures you the barn will stay dry.",
            WeatherType.Rainy, true, 1),
    Rainy_MATERIALS(
            "Robin, rainy weather aside, inspects her wood and iron stock.",
            WeatherType.Rainy, true, 2),
    Rainy_INNOVATE(
            "Shy Robin, rain outside, sketches a deluxe coop design.",
            WeatherType.Rainy, true, 3),
    SNOWY_FIREPLACE(
            "On a snowy evening, Robin invites you to warm up by her workshop’s fireplace.",
            WeatherType.Snow, false, 1),
    SnowY_TOOL(
            "Robin, snow piling up, carefully oils her tools for winter work.",
            WeatherType.Snow, false, 2),
    StormY_REPAIR(
            "During a Stormy night, Robin repairs broken fence planks by lantern light.",
            WeatherType.Storm, false, 3),
    StormY_MEMORY(
            "Robin, amid the storm, fondly recalls building her first house.",
            WeatherType.Storm, false, 2);

    private final String prompt;
    private final WeatherType weather;
    private final boolean day;
    private final int minFriendship;

    RobinPrompt(String prompt,
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
        List<RobinPrompt> filtered = Arrays.stream(values())
                .filter(p -> p.weather == w)
                .filter(p -> p.day == isDay)
                .filter(p -> p.minFriendship == friendshipLevel)
                .toList();
        if (filtered.isEmpty()) {
            return "Robin gives you a confident nod but remains silent.";
        }
        return filtered.get(new Random().nextInt(filtered.size())).getPrompt();
    }
}
