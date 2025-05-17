package model.GameObject.NPC;

import model.App;
import model.Player;

public class NpcFriendship {
    private final Player player;
    private final NPC npc;
    private int xp;
    private int lastActiveRequest ;

    public NpcFriendship(Player player, NPC npc) {
        this.player = player;
        this.npc = npc;
        xp = 0;
        lastActiveRequest = 1;
        NPC seb = App.getCurrentUser().getCurrentGame().getGameMap().getPelikanTown().getNpcByName("Sebastian");
        seb.getType().getPromptClass().
    }


    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getLevel() {
        return xp/200;
    }

    public NPC getNpc() {
        return npc;
    }

    public Player getPlayer() {
        return player;
    }

    public int getLastActiveRequest() {
        return lastActiveRequest;
    }

    public void setLastActiveRequest(int lastActiveRequest) {
        this.lastActiveRequest = lastActiveRequest;
    }
}
