package model.Enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainMenuCommands implements Commands {
    ShowCurrentMenu("show current menu"),
    goMenu("menu Enter (.+)"),
    logout("user logout");

    private final String regex;

    MainMenuCommands(String regex) {
        this.regex = regex;
    }
    @Override
    public Matcher getMatcher(String input) {
        Pattern compiledPattern = Pattern.compile(this.regex);
        return compiledPattern.matcher(input);
    }
}
