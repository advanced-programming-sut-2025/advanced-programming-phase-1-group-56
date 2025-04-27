package model.Enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum InfoRegexes {
    email(""),
    nickname(""),
    password(""),
    usersName(""),
    gender(""),
    answer("");

    private final String pattern;

    InfoRegexes(String pattern) {
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
