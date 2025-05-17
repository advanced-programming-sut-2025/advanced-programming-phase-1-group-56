package model.GameObject.NPC;

import model.items.Item;
import model.items.Saleable;

public class NpcRequest {
    private final Item requestedItem;
    private final int RequestedQuantity;
    private final Saleable rewardItem;

    public int getRewardQuantity() {
        return rewardQuantity;
    }

    public Saleable getRewardItem() {
        return rewardItem;
    }

    public int getRequestedQuantity() {
        return RequestedQuantity;
    }

    public Item getRequestedItem() {
        return requestedItem;
    }

    private final int rewardQuantity;

    public NpcRequest(Item item, int quantity, Saleable rewardItem, int rewardQuantity) {
        this.requestedItem = item;
        this.RequestedQuantity = quantity;
        this.rewardItem = rewardItem;
        this.rewardQuantity = rewardQuantity;
    }

    @Override
    public String toString() {
        return  "\trequested Item: " + this.getRequestedItem().getName() + "*" +
                this.getRequestedQuantity() + "\n" + "\treward Item:" +
                this.getRewardItem().getName() + "*" +
                this.getRewardQuantity();
    }
}
