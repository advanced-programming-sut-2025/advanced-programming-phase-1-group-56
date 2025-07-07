package model.Activities;

import com.google.gson.annotations.Expose;
import model.App;
import model.Player;

import java.util.UUID;

public class Friendship {
    private final UUID playerID;
    @Expose(serialize = false, deserialize = false)
    private final Player player;
    private int xp;
    private boolean isDeliveredFlower;
    private boolean isMarried;

    public Friendship(Player player) {
        this.playerID = player.getPlayerID();
        this.player = player;
        this.xp = 0;
        isDeliveredFlower = false;
        isMarried = false;
    }



    public int getXp() {
        return xp;
    }

    public void changeTwoWayXp(int amount) {
        this.xp = Math.max(xp + amount, 0);
        Player me = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        player.findFriendshipByPlayer(me).changeXp(amount);
    }

    public void resetFriendshipBothWay(){
        setDeliveredFlowerBothWay(false);
        setMarriedBothWay(false);
        changeTwoWayXp(-xp);
    }

    public void changeXp(int amount) {
        this.xp = Math.max(xp + amount, 0);
    }

    public int getLevel() {
        int lvl = xp/200 + 1;
        if(!isDeliveredFlower)
            lvl = Math.min(lvl, 2);
        if(!isMarried)
            lvl = Math.min(lvl, 3);
        return lvl;
    }

    public Player getPlayer() {
        return player;
    }


    public boolean isDeliveredFlower() {
        return isDeliveredFlower;
    }

    public void setDeliveredFlowerBothWay(boolean deliveredFlower) {
        isDeliveredFlower = deliveredFlower;
        Player me = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        player.findFriendshipByPlayer(me).setDeliveredFlower(deliveredFlower);
    }

    public void setDeliveredFlower(boolean deliveredFlower) {
        isDeliveredFlower = deliveredFlower;
    }

    public boolean isMarried() {
        return isMarried;
    }

    public void setMarriedBothWay(boolean married) {
        isMarried = married;
        Player me = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        player.findFriendshipByPlayer(me).setMarried(married);
    }

    public void setMarried(boolean married) {
        isMarried = married;
    }

    public UUID getPlayerID() {
        return playerID;
    }
}
