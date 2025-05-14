package model.Enums.commands.GameCommands;

import model.Enums.commands.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum FarmingCommands implements Commands {
    CRAFT_INFO("craftinfo -n (.+)");

    private final String pattern;
    FarmingCommands(String pattern) {
        this.pattern = pattern;
    }
    @Override
    public Matcher getMatcher(String input) {
        Pattern compiledPattern = java.util.regex.Pattern.compile(this.pattern);
        return compiledPattern.matcher(input);
    }
}
