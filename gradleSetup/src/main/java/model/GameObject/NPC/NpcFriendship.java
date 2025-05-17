

package model.GameObject.NPC;

import model.App;
import model.Player;
import model.TimeSystem.DateTime;
import model.TimeSystem.TimeObserver;

public class NpcFriendship implements TimeObserver {
    private final Player player;
    private final NPC npc;
    private int xp;
    private int lastActiveRequest ;
    private boolean hasGiftedToday;
    private boolean hasMetToday;

    public NpcFriendship(Player player, NPC npc) {
        this.player = player;
        this.npc = npc;
        xp = 0;
        lastActiveRequest = 1;
        hasMetToday = false;
        hasGiftedToday = false;
        App.getCurrentUser().getCurrentGame().getTimeSystem().addObserver(this);
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

    public boolean isHasMetToday() {
        return hasMetToday;
    }

    public void addXp(int amount    ) {
        xp += amount;
        xp = Math.max(xp,799);
    }


    public void setHasMetToday(boolean hasMetToday) {
        this.hasMetToday = hasMetToday;
    }

    public boolean isHasGiftedToday() {
        return hasGiftedToday;
    }

    public void setHasGiftedToday(boolean hasGiftedToday) {
        this.hasGiftedToday = hasGiftedToday;
    }

    @Override
    public void onHourChanged(DateTime time, boolean newDay) {
        if(newDay) {
            hasGiftedToday = false;
            hasMetToday = false;
        }
    }
}
