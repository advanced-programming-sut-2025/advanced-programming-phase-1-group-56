package model.Activities;

import model.Player;
import model.items.Item;

public class Gift {
    private final int giftID;
    private final Player sender;
    private final Player receiver;
    private final Item gift;
    private final int amount;
    private int rate;

    public Gift(int giftID, Player sender, Player receiver, Item gift,int amount) {
        this.giftID = giftID;
        this.sender = sender;
        this.receiver = receiver;
        this.gift = gift;
        this.amount = amount;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getAmount() {
        return amount;
    }

    public Item getGift() {
        return gift;
    }

    public Player getReceiver() {
        return receiver;
    }

    public Player getSender() {
        return sender;
    }

    public int getGiftID() {
        return giftID;
    }
}
