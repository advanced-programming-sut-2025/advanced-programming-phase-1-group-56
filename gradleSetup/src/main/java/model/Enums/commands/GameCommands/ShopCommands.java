package model.Enums.commands.GameCommands;
import model.Enums.commands.Commands;

import java.util.regex.Matcher;

public enum ShopCommands implements Commands {
    ShowAllProducts("\\s*show\\s+all\\s+products\\s*"),
    ShowAllAvailableProducts("\\s*show\\s+all\\s+available\\s+products\\s*"),
    PurchaseProduct("^\\s*purchase\\s+([a-zA-Z0-9_]+)\\s*(?:\\s+-n\\s+(?P<count>\\d+))?\\s*$"),
    cheat("cheat add ([0,9]+) dollars"),
    sell("sell (.+) -n ([0,9]+)"),
    ;
    final String regex;
    ShopCommands(String regex){
        this.regex = regex;
    }
    @Override
    public Matcher getMatcher(String input) {
        return null;
    }
}
