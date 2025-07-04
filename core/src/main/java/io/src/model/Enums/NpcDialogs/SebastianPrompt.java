package io.src.model.Enums.NpcDialogs;

import io.src.model.Enums.WeatherAndTime.WeatherType;

import java.util.*;

public enum SebastianPrompt implements NpcPrompt {
    // (promptText, weather, day, minFriendship)
    Sunny_DAY_WEATHER(
            "On a Sunny day, Sebastian shyly comments on the bright sunshine.",
            WeatherType.Sunny, true, 1),
    Sunny_DAY_DREAMS(
            "Shy Sebastian, on this Sunny afternoon, shares his dreams.",
            WeatherType.Sunny, true, 2),
    Sunny_DAY_MUSIC(
            "During a Sunny noon, Sebastian opens up about his favorite music.",
            WeatherType.Sunny, true, 3),
    Rainy_READING(
            "On a Rainy day, Sebastian shyly discusses his love of reading.",
            WeatherType.Rainy, true, 1),
    Rainy_SCHOOL(
            "On a Rainy morning, Sebastian timidly recalls school memories.",
            WeatherType.Rainy, true, 2),
    Rainy_POETRY(
            "Shy Sebastian, rain tapping outside, shares a short poem.",
            WeatherType.Rainy, true, 3),
    SNOWY_MEMORY(
            "On a snowy day, Sebastian at friendship level 2 shyly shares a childhood memory.",
            WeatherType.Snow, true, 2),
    Snow_LONELY(
            "On a snowy night, shy Sebastian talks about feeling lonely.",
            WeatherType.Snow, false, 1),
    StormY_FEAR(
            "During a Stormy night, shy Sebastian confesses his fears.",
            WeatherType.Storm, false, 1),
    StormY_MUSIC(
            "In a Stormy afternoon, Sebastian shyly chats about favorite tunes.",
            WeatherType.Storm, true, 3);

    private final String prompt;
    private final WeatherType weather;
    private final boolean day;
    private final int minFriendship;

    SebastianPrompt(String prompt,
                    WeatherType weather,
                    boolean day,
                    int minFriendship) {
        this.prompt = prompt;
        this.weather = weather;
        this.day = day;
        this.minFriendship = minFriendship;
    }

    public String getPrompt()      { return prompt; }
    public WeatherType getWeather(){ return weather; }
    public boolean isDay()         { return day; }
    public int getMinFriendship() { return minFriendship; }

    /**
     * برگرداندن یک پرامپت تصادفی که با شرایط منطبق باشد.
     */
    public static String randomFor(WeatherType w, boolean isDay, int friendshipLevel) {
        List<SebastianPrompt> filtered = Arrays.stream(values())
                .filter(p -> p.weather == w)
                .filter(p -> p.day == isDay)
                .filter(p -> p.minFriendship == friendshipLevel)
                .toList();

        if (filtered.isEmpty()) {
            // اگر هیچ پرامپتی منطبق نبود می‌توانیم یکی عمومی برگردانیم
            return "Sebastian quietly nods, says nothing right now.";
        }
        return filtered.get(new Random().nextInt(filtered.size())).getPrompt();
    }
}
