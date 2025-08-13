package io.src.model.GameObject.NPC;

import io.src.model.items.Item;
import io.src.model.items.Saleable;

public class NpcRequest {
    private final Item requestedItem;
    private final int RequestedQuantity;
    private final Saleable rewardItem;
    private final int rewardAmount;

    public Saleable getRewardItem() {
        return rewardItem;
    }

    public int getRequestedQuantity() {
        return RequestedQuantity;
    }

    public Item getRequestedItem() {
        return requestedItem;
    }


    public NpcRequest(Item item, int quantity, Saleable rewardItem, int rewardAmount) {
        this.requestedItem = item;
        this.RequestedQuantity = quantity;
        this.rewardItem = rewardItem;
        this.rewardAmount = rewardAmount;
    }

    @Override
    public String toString() {
        return "\trequested Item: " + this.getRequestedItem().getName() + "*" +
            this.getRequestedQuantity() + "\n" + "\treward Item:" +
            this.getRewardItem().getName() + "*" +
            this.getRewardAmount();
    }

    public int getRewardAmount() {
        return rewardAmount;
    }

    public int getRewardQuantity() {
        return rewardAmount;
    }
}
