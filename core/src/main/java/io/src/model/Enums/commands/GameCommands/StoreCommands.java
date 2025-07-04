package io.src.model.Enums.commands.GameCommands;

import io.src.model.Enums.commands.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum StoreCommands implements Commands {
    ShowAllProduct("\\s*show\\s+all\\s+products\\s*"),
    ShowAllAvailableProducts("\\s*show\\s+all\\s+available\\s+products\\s*"),
    PurchaseProducts("\\s*purchase\\s+(.+)\\s+-n\\s+(.+)\\s*"),
    ;
    private final String regex;

    StoreCommands(String regex) {
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

    public String getRegex() {
        return regex;
    }
}
