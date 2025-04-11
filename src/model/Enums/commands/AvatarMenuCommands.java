package model.Enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum AvatarMenuCommands implements Commands {

    private final String regex;

    AvatarMenuCommands(String regex) {
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
