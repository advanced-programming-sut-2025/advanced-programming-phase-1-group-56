package model.Enums.commands.GameCommands;

import model.Enums.commands.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameCommands implements Commands {

    //Execute Commands
    exitGame("\\s*exit\\s+\\s*"),//TODO think about the algorithms
    deleteGame("\\s*force\\s+terminate\\s*"),
    nextTurn("\\s*next\\s+turn\\s*"),
    Walk("\\s*walk\\s+-l\\s+<([0,9]+), ([0,9]+)>\\s*"),
    printMap("\\s*print\\s+map\\s+"),
    //printMap("\\s*print\\s+map\\s+-l\s+<([0,9]+),\s+([0,9]+)>\s+-s\s+([0-9]+)"),
    helpReadingMap("help\\s+reading\\s+map");




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
