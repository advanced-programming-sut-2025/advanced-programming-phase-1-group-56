package model.Enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands implements Commands {
    exit("menu exit"),
    ShowCurrentMenu("show current menu"),
    register("register.+-u\\s+(.+)\\s+-p\\s+(.+)\\s+(.+)\\s+-n\\s+(.+)\\s+-e\\s+(.+)\\s+-g\\s+(.+)"),
    pickSecurityQuestion("pick\\s+question\\s+-q\\s+(.+)\\s+-a\\s+(.+)\\s+-c\\s+(.+)"),
    goMenu("menu Enter (.+)"),
    login("login -u (.+) -p (.+)"),
    loginWithStayLoggedOut("login\\s+-u\\s+(.+)\\s+-p\\s+(.+)\\s+–stay-logged-in"),
    forgetPassword("forget\\s+password\\s+-u\\s+(.+)"),
    answerSecurityQuestion("answer\\s+-a\\s+(.+)");

    private final String regex;
    LoginMenuCommands(String regex) {
        this.regex = regex;
    }
    @Override
    public Matcher getMatcher(String input) {
        Pattern compiledPattern = Pattern.compile(this.regex);
        return compiledPattern.matcher(input);
    }
}
