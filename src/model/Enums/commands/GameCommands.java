package model.Enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameCommands implements Commands {
    exitGame(""),
    nextTurn(""),
    showTime(""),
    showDate(""),
    showDateTime(""),
    showDayOfWeek(""),
    cheatAdvancedTime(""),
    cheatAdvancedDate(""),
    showSeason("");

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
