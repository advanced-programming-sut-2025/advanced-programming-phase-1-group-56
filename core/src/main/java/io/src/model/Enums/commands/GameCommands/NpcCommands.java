package io.src.model.Enums.commands.GameCommands;

import io.src.model.Enums.commands.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum NpcCommands implements Commands {
    MeetNPC("\\s*meet\\s+NPC\\s+(.+)\\s*"),
    GiftNPC("\\s*gift\\s+NPC\\s+(.+)\\s+-i\\s+(.+)\\s*"),
    NpcFriendship("\\s*friendship\\s+NPC\\s+list\\s*"),
    ListQuest("\\s*quests\\s+list\\s*"),
    FinishQuest("\\s*quests\\s+finish\\s+-i\\s+(.+)\\s*")
    ;
    private final String regex;
    NpcCommands(String regex) {
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
