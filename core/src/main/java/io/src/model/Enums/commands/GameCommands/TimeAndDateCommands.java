package io.src.model.Enums.commands.GameCommands;
import io.src.model.Enums.commands.Commands;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TimeAndDateCommands implements Commands {
    showTime("\\s*time\\s*"),
    showDate("\\s*date\\s*"),
    showDateTime("\\s*date\\s+time\\s*"),
    showDayOfWeek("\\s*day\\s+of\\s+the\\s+week\\s*"),
    showSeason("\\s*season\\s*"),
    cheatAdvancedDate("\\s*cheat\\s+advance\\s+date\\s+([0-9]+)d\\s*"),
    cheatAdvancedTime("\\s*cheat\\s+advance\\s+time\\s+([0-9]+)h\\s*"),
    ;

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
