package model.Enums.commands.GameCommands;


import model.Enums.commands.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CraftingCommand implements Commands {
    ShowRecipe(""),
    craftItem(""),
    dropItem(""),
    cheatCode("");

    private final String command;

    CraftingCommand(String command) {
        this.command = command;
    }

    @Override
    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(command).matcher(input);
        if (matcher.find()) {
            return matcher;
        }
        return null;
    }
}
