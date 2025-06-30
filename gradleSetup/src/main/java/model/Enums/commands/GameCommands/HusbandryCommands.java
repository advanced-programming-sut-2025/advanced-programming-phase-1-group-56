package model.Enums.commands.GameCommands;

import model.Enums.commands.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum HusbandryCommands implements Commands {
    cheatADdAnimal("add animal <(.+), (.+)> -n (.+)"),
    buildBarnOrCoop("build\\s+-a\\s+(.+)\\s+-l\\s+(\\S+) , (\\S+)"),
    buyAnimal("buy\\s+animal\\s+-a\\s+(.+)\\s+-n\\s+(.+)"),
    pettingAnimal("pet -n (\\S+)"),
    showInfoAnimals("animals"),
    shepherdAnimals("shepherd animals -n (.+) -l (\\S+), (\\S+)"),
    feedHay("feed hay -n (\\S+)"),
    produces("produces"),
    collectProduce("collect produce -n (\\S+)"),
    sellAnimal("sell animal -n (\\S+)"),
    fishing("fishing -p (.+)"),
    //cheatCode
    cheatSetFriendship("cheat set friendship -n (.+) -c (.+)"),
    BuildABuilding("build -a (.+) -l  (\\d+,\\d+)")
    ;

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
