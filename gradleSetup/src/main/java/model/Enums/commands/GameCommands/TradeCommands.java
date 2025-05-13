package model.Enums.commands.GameCommands;
import model.Enums.commands.Commands;

import java.util.regex.Matcher;

public enum TradeCommands implements Commands {
    startTrade("\\s*start\\s+trade\\s*"),
    tradeWithPlayer("trade\\s+-u\\s+(?<username>.+)\\s+-t\\s+(?<type>.+)\\s+-i\\s+(?<item>.+)"+
                           "\\s+-a\\s+(?<amount>.+)(\\s+-p\\s+(?<price>\\S+))?(\\s+-ti\\s+"+
                            "(?<targetItem>\\S+)\\s+-ta\\s+(?<targetAmount>\\S+))?\\s*"),
    tradeList("\\s*trade\\s+list\\s*"),
    tradeHistory("\\s*trade\\s+history\\s*"),
    tradeResponse("\\s*trade\\s+response\\s+(.+)-i\\s+(.+)\\s*")
    ;
    final String regex;
    TradeCommands(String regex){
        this.regex = regex;
    }
    @Override
    public Matcher getMatcher(String input) {
        return null;
    }
}
