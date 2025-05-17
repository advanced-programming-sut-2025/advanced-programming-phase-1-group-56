package model.Enums.commands.GameCommands;

import model.Enums.commands.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum RelationshipCommands implements Commands {
    showAllFriendships("\\s*friendships\\s*"),
    talk("\\s*talk\\s+-u\\s+(.+)\\s+-m\\s+(.+)\\s*"),
    talkHistory("\\s*talk\\s+history\\s+-u\\s+(.+)\\s*"),
    gift("\\s*gift\\s+-u\\s+(.+)\\s+-i\\s+(.+)\\s+-a\\s+(.+)\\s*"),
    giftList("\\s*gift\\s+list\\s*"),
    giftRate("\\s*gift\\s+rate\\s+-i\\s(.+)\\s+-r\\s+(.+)\\s*"),
    giftHistory("\\s*gift\\s+history\\s+-u\\s+(.+)\\s*"),
    hug("\\s*hug\\s+-u\\s+(.+)\\s*"),
    flower("\\s*flower\\s+-u\\s+(.+)\\s*"),
    marryRequest("\\s*ask\\s+marriage\\s+-u\\s+(.+)\\s+-r\\s+(.+)\\s*"),
    marryRespond("\\s*respond\\s+(.+)\\s+-u\\s+(.+)\\s*")
    ;
    private final String regex;
    RelationshipCommands(String regex) {
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
