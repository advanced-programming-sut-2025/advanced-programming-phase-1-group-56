package io.src.model.GameObject.NPC;

import io.src.model.Player;
import io.src.model.items.Item;

public class NpcGift {
    private final int giftID;
    private final Player sender;
    private final NPC receiverNPC;
    private final Item gift;

    public NpcGift(int giftID, Player sender, NPC receiver, Item gift, int amount) {
        this.giftID = giftID;
        this.sender = sender;
        this.receiverNPC = receiver;
        this.gift = gift;
    }

    public Item getGift() {
        return gift;
    }

    public Player getSender() {
        return sender;
    }

    public int getGiftID() {
        return giftID;
    }

    public NPC getReceiverNPC() {
        return receiverNPC;
    }
}
