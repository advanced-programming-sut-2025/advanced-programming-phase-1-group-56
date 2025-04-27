package model.Enums.commands.GameCommands;

import model.Enums.commands.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum WeatherCommands implements Commands {
    //show commands
    showTime(""),
    showDate(""),
    showDateTime(""),
    showDayOfWeek(""),
    showSeason(""),
    //cheat
    cheatThor(""),
    cheatAdvancedDate(""),
    cheatAdvancedTime(""),
    cheatWeather("");

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
