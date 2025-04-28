package model.Enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands implements Commands {
    changeUserName("\\s*change\\s+username\\s+-u\\s+(?<username>.+)\\s*"),
    changeNickName("\\s*change\\s+nickname\\s+-u\\s+(?<name>.+)\\s*"),
    changeEmail("\\s*change\\s+email\\s+-e\\s+<email>.+)\\s*"),
    changePassword("\\s*change password\\s*-p\\s+(?<newPassword>.+)\\s+-o\\s+(?<old_password>.+)\\s*"),
    showUserInformation("\\s*user\\s+info\\s*"),
    ShowCurrentMenu("\\s*show\\s+current\\s+menu\\s*"),
    BackToMainMenu("\\s*back\\s*");

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