package model.Enums.commands.GameCommands;

import model.Enums.commands.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameCommands implements Commands {

    //Execute Commands
    exitGame("\\s*exit\\s+\\s*"),//TODO think about the algorithms
    nextTurn("\\s*next\\s+turn\\s*"),
    buildGreenHouse("")
    ;

    private final String pattern;

    GameCommands(String pattern) {
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
