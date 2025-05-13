package model.Activities;

import model.Player;
import model.items.Item;

import java.util.UUID;

public class Gift {
    private final UUID giftID;
    private final Player sender;
    private final Player receiver;
    private final Item gift;
    private final int amount;
    private int rate;

    public Gift (Player sender, Player receiver, Item gift,int amount) {
        this.giftID = UUID.randomUUID();
        this.sender = sender;
        this.receiver = receiver;
        this.gift = gift;
        this.amount = amount;
        this.rate = -1;
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

    public UUID getGiftID() {
        return giftID;
    }
}
