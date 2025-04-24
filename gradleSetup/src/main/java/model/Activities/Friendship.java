package model.Activities;

import model.Player;

public class Friendship {
    private final Player player;
    private int level;
    private int xp;

    Friendship(Player player, int level, int xp) {
        this.player = player;
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

    public Player getPlayer() {
        return player;
    }
}
