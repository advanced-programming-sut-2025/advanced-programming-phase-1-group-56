package model.Enums.commands.GameCommands;

import model.Enums.commands.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum HusbandryCommands implements Commands {
    buildBarnOrCoop(""),
    buyAnimal(""),
    pettingAnimal(""),
    showInfoAnimals(""),
    shepherdAnimals(""),
    feedHay(""),
    dontCollectProducts(""),
    collectProduce(""),
    sellAnimal(""),
    fishing(""),
    //cheatCode
    cheatSetFriendship("");

    private final String regex;

    HusbandryCommands(String regex) {
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
