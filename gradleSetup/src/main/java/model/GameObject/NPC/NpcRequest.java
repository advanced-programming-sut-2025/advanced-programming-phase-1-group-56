package model.GameObject.NPC;

import model.items.Item;

public class NpcRequest {
    private Item requestedItem;
    private int RequestedQuantity;
    private Item rewardItem;
    private int rewardQuantity;

    public NpcRequest(Item item, int quantity, Item rewardItem, int rewardQuantity) {
        this.requestedItem = item;
        this.RequestedQuantity = quantity;
        this.rewardItem = rewardItem;
        this.rewardQuantity = rewardQuantity;
    }


    public int getRequestedQuantity() {
        return RequestedQuantity;
    }

    public void setRequestedQuantity(int requestedQuantity) {
        this.RequestedQuantity = requestedQuantity;
    }

    public Item getRequestedItem() {
        return requestedItem;
    }

    public void setRequestedItem(Item requestedItem) {
        this.requestedItem = requestedItem;
    }

    public int getRewardQuantity() {
        return rewardQuantity;
    }

    public void setRewardQuantity(int rewardQuantity) {
        this.rewardQuantity = rewardQuantity;
    }

    public Item getRewardItem() {
        return rewardItem;
    }

    public void setRewardItem(Item rewardItem) {
        this.rewardItem = rewardItem;
    }
}
