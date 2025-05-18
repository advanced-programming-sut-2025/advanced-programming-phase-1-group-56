package model.Enums.commands.GameCommands;

import model.Enums.commands.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum WeatherCommands implements Commands {
    //show commands
    showTodayWeather("\\s*weather\\s*"),
    showTomorrowWeather("\\s*weather\\s+forecast\\s*"),
    buildGreenHouse("\\s*greenhouse\\s+build\\s*"),
    //cheat
    cheatThor("\\s*cheat\\s+Thor\\s+-l\\s+([0-9]+)\\s+([0-9]+)\\s*"),
    cheatWeather("\\s*cheat\\s+weather\\s+set\\s+(.+)\\s*");

    private final String regex;

    WeatherCommands(String regex) {
        this.regex = regex;
    }

    @Override
    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(regex).matcher(input);
        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}
