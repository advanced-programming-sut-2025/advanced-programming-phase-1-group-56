package model.Enums.commands.GameCommands;
import model.Enums.commands.Commands;

import java.util.regex.Matcher;

public enum TradeCommands implements Commands {
    startTrade("\\s*start\\s+trade\\s*"),
    tradeWithPlayer("\\s*trade\\s+-u\\s+(.+)\\s+-t\\s+(.+)\\s+-i\\s+(.+)\\s+-a\\s+(.+)\\s+"+
            "\\[-p\\s+(.+)\\]\\s+\\[-ti\\s+(.+)\\s+-ta\\s+(.+)\\]\\s*"),
    tradeList("\\s*trade\\s+list\\s*"),
    tradeHistory("\\s*trade\\s+history\\s*"),
    tradeResponse("\\s*trade\\s+response\\s+\\((.+)\\)\\s+-i\\s+(.+)\\s*")
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
