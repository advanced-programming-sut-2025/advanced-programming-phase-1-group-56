package model.Enums.commands.GameCommands;

import model.Enums.commands.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TradeCommands implements Commands {
    tradeWithPlayer(
            "\\s*trade\\s+" +
                    "-u\\s+(?<username>\\S+)\\s+" +
                    "-t\\s+(?<type>\\S+)\\s+" +
                    "-i\\s+(?<item>\\S+)\\s+" +
                    "-a\\s+(?<amount>\\S+)" +
                    "(?:\\s+-p\\s+(?<price>\\S+))?" +
                    "(?:\\s+-ti\\s+(?<targetItem>\\S+)\\s+-ta\\s+(?<targetAmount>\\S+))?" +
                    "\\s*"
    ),
    tradeList("\\s*trade\\s+list\\s*"),
    startTrade("\\s*start\\s+trade\\s*"),
    tradeHistory("\\s*trade\\s+history\\s*"),
    tradeResponse("\\s*trade\\s+response\\s+\\((.+)\\)\\s+-i\\s+(.+)\\s*"),
    cheatAddMoney("\\s*cheat\\s+add\\s+(\\d+)\\s+dollars\\s*"),
    sell("\\s*sell\\s+(.+)\\s*(?:\\s+-n\\s+(\\d+))?\\s*"),
    ;
    final String regex;

    TradeCommands(String regex) {
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
