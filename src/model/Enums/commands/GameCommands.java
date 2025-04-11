package model.Enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameCommands implements Commands {

    //Execute Commands
    exitGame(""),
    nextTurn(""),
    buildGreenHouse(""),
    // Show Commands
    showTime(""),
    showDate(""),
    showDateTime(""),
    showDayOfWeek(""),
    showSeason(""),
    showWeather(""),
    showTomorrowWeather(""),
    //Cheat Commands
    cheatThor(""),
    cheatAdvancedDate(""),
    cheatAdvancedTime(""),
    cheatWeather(""),
    toolsEquip(""),
    toolsShowCurrent(""),
    toolsShowAvailable(""),
    toolsUpgrade(""),
    toolsUse("")
    ;

    private final String pattern;

    GameCommands(String pattern) {
        this.pattern = pattern;
    }

    public boolean isValid(String input) {
        return java.util.regex.Pattern.matches(this.pattern, input);
    }

    public Matcher getMatcher(String input) {
        Pattern compiledPattern = java.util.regex.Pattern.compile(this.pattern);
        return compiledPattern.matcher(input);
    }
}
