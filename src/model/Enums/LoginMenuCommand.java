package model.Enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommand implements Commands {
    goToMenu(""),
    menuExit(""),
    showCurrentMenu(""),
    register(""),
    safetyQuestion(""),
    login(""),
    forgotPassword(""),
    answerRegex(""),
    

    private final String regex;

    LoginMenuCommand(String regex) {
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
