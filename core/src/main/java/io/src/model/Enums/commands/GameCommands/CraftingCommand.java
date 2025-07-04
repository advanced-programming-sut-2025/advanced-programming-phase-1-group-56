package io.src.model.Enums.commands.GameCommands;


import io.src.model.Enums.commands.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CraftingCommand implements Commands {
    ShowRecipe("crafting show recipes"),
    craftItem("crafting craft (.+)"),
    dropItem("place item -n (.+) -d (.+)"),
    cheatCode("cheat add item -n (.+) -c (.+)"),
    cheatAddRecipe("add recipe -n (.+) -c (.+)");//TODO

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
