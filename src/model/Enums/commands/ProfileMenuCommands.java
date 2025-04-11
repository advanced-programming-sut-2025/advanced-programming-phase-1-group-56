package model.Enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands implements Commands {
    changeUserName(""),
    changeNickName(""),
    changeEmail(""),
    changePassword(""),
    showUserInformation("");
    private final String regex;

    ProfileMenuCommands(String regex) {
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
