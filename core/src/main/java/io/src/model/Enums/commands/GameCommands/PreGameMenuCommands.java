package io.src.model.Enums.commands.GameCommands;

import io.src.model.Enums.commands.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum PreGameMenuCommands implements Commands {
    newGameRegex("\\s*game\\s+new\\s+-u\\s*(.+)"),
    chooseMap("\\s*game\\s+map\\s+(-?\\d+)\\s*"),
    loadGame("\\s*load\\s+game\\s*")

    ;

    private final String regex;

    PreGameMenuCommands(String regex) {
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
