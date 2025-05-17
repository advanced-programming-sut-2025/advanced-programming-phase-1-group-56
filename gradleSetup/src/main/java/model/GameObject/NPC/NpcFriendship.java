

package model.GameObject.NPC;

import model.App;
import model.Player;
import model.TimeSystem.DateTime;
import model.TimeSystem.TimeObserver;

public class NpcFriendship implements TimeObserver {
    private final Player player;
    private final NPC npc;
    private int xp;
    private boolean hasGiftedToday;
    private boolean hasMetToday;
    private int lastDoneRequest;
    private int daysToSecondQ;

    public NpcFriendship(Player player, NPC npc) {
        this.player = player;
        this.npc = npc;
        xp = 0;
        lastDoneRequest = 0;
        hasMetToday = false;
        hasGiftedToday = false;
        daysToSecondQ = npc.getType().getDaysForSecond();
        App.getCurrentUser().getCurrentGame().getTimeSystem().addObserver(this);
    }


    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getLevel() {
        return xp / 200;
    }

    public NPC getNpc() {
        return npc;
    }

    public Player getPlayer() {
        return player;
    }

    public int getLastActiveRequest() {
        if (lastDoneRequest == 0) {
            return 1;
        }
        if (lastDoneRequest == 1) {
            if (daysToSecondQ <= 0) {
                return 2;
            }
            return -1;//second request is locked because of days
        } else if (lastDoneRequest == 2) {
            if (getLevel() >= 1) {
                return 3;
            }
            return -1;//third request is locked because of friendShip
        } else return -1;//all request are done
    }

    public boolean isHasMetToday() {
        return hasMetToday;
    }

    public void setHasMetToday(boolean hasMetToday) {
        this.hasMetToday = hasMetToday;
    }

    public boolean isHasGiftedToday() {
        return hasGiftedToday;
    }

    public void addXp(int amount) {
        xp += amount;
        xp = Math.max(xp, 799);
    }

    public void setHasGiftedToday(boolean hasGiftedToday) {
        this.hasGiftedToday = hasGiftedToday;
    }

    @Override
    public void onHourChanged(DateTime time, boolean newDay) {
        if (newDay) {
            hasGiftedToday = false;
            hasMetToday = false;
            daysToSecondQ = Math.max(daysToSecondQ - 1, 0);
        }
    }

    public void setLastDoneRequest(int lastDoneRequest) {
        this.lastDoneRequest = lastDoneRequest;
    }

    public void setDaysToSecondQ(int daysToSecondQ) {
        this.daysToSecondQ = daysToSecondQ;
    }

    public int getDaysToSecondQ() {
        return daysToSecondQ;
    }

    public int getLastDoneRequest() {
        return lastDoneRequest;
    }

}
