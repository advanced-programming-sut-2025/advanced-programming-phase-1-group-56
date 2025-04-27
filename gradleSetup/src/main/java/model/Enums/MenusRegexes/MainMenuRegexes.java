package model.Enums.MenusRegexes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainMenuRegexes {
    ShowCurrentMenu("show current menu"),
    goMenu("menu Enter (.+)"),
    logout("user logout");

    private final String regex;

    MainMenuRegexes(String regex) {
        this.regex = regex;
    }

    public Matcher getMatcher(String input) {
        Pattern compiledPattern = java.util.regex.Pattern.compile(this.regex);
        return compiledPattern.matcher(input);
    }
}
