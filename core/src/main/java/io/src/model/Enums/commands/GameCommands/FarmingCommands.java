package io.src.model.Enums.commands.GameCommands;

import io.src.model.Enums.commands.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum FarmingCommands implements Commands {
    CRAFT_INFO("craftinfo -n (.+)"),
    PLANT("plant -s (.+) -d (.+)"),
    showPlant("showplant -l <(\\d+), (\\d+)>"),
    feritilize("fertilize -f (.+) -d (.+)"),
    showWater("howmuch water");

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
