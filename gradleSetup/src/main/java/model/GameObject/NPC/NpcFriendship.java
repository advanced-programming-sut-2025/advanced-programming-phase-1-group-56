package model.GameObject.NPC;

import model.Player;

public class NpcFriendship {
    private final Player player;
    private final NPC npc;
    private int level;
    private int xp;

    public NpcFriendship(Player player, NPC npc, int level, int xp) {
        this.player = player;
        this.npc = npc;
        this.level = level;
        this.xp = xp;
    }


    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public NPC getNpc() {
        return npc;
    }

    public Player getPlayer() {
        return player;
    }
}
