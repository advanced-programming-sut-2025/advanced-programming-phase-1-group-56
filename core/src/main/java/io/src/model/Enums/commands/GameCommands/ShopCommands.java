package io.src.model.Enums.commands.GameCommands;

import io.src.model.Enums.commands.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ShopCommands implements Commands {
    ShowAllProducts("show all products"),
    ShowAllAvailableProducts("show all available products"),
    PurchaseProduct("purchase ([a-zA-Z0-9_]+) (?: -n (\\d+))?"),
    cheatMoney("cheat add ([0-9]+) dollars"),
    sell("sell (.+) -n ([0-9]+)"),
    ;
    private final String regex;

    ShopCommands(String regex) {
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
