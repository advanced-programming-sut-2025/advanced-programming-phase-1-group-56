package model.Enums.commands.GameCommands;

import model.Enums.commands.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum EnergyAndSkillsCommands implements Commands {
    showEnergy("show energy"),
    cheatEnergy("energy set -v ([0-9]+)"),
    unlimitedEnergy("energy unlimited"),
    showInventory("inventory show"),
    trashInventory("inventory trash -i (.+) -n ([0-9]+)");




    private final String pattern;

    EnergyAndSkillsCommands(String pattern) {
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
