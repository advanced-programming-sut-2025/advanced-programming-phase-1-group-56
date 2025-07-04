package io.src.model.Enums.commands.GameCommands;

import io.src.model.Enums.commands.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum HouseMenuCommands implements Commands {
    refrigeratorPick("cooking\\s+refrigerator\\s+pick\\s+(.+)"),
    refrigeratorPut("cooking\\s+refrigerator\\s+put\\s+(.+)"),
    showRecipes("cooking show recipes"),
    prepareRecipe("cooking prepare (.+)"),
    addRecipe("cooking add recipe (.+)");//TODO

    private final String pattern;
    HouseMenuCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Matcher getMatcher(String input) {
        Pattern compiledPattern = Pattern.compile(this.pattern);
        return compiledPattern.matcher(input);
    }
}
