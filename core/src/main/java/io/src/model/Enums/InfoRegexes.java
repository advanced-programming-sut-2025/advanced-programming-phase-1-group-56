package io.src.model.Enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum InfoRegexes {
    email("(?!.*\\.\\.)[A-Za-z0-9._-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}"),
    password("[A-Za-z0-9?><,\"';:\\\\/|\\]\\[}{+=)(*&^%$#!]*"),
    strongPassword("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[?><,\"';:\\\\/|\\]\\[\\}\\{\\+=\\)\\(\\*&\\^%\\$#!])[a-zA-Z\\d?><,\"';:\\\\/|\\]\\[\\}\\{\\+=\\)\\(\\*&\\^%\\$#!]{1,}"),
    usersName("[a-zA-Z0-9-]+");

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
