package model.Enums.commands.GameCommands;

import model.Enums.commands.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TimeAndDateCommands implements Commands {
    showTime(""),
    showDate(""),
    showDateTime(""),
    showDayOfWeek(""),
    showSeason("");

    private final String regex;

    TimeAndDateCommands(String regex) {
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
