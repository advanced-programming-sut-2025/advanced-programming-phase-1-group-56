package io.src.model.Activities;

import io.src.model.App;
import io.src.model.Enums.Activity.TradeStatus;
import io.src.model.Enums.Activity.TradeType;
import io.src.model.Slot;
import io.src.model.TimeSystem.DateTime;
import java.util.UUID;

public class Trade {
    private final UUID tradeID;
    private final DateTime timeStamp;
    private final UUID playerID;
    private final UUID counterPartyId;
    private final Slot itemsToGive;
    private final TradeType type; // REQUEST or OFFER  *  MONEY or PRODUCT (4 case)
    private final Slot itemsGets;
    private final int moneyGets;

    private TradeStatus status;   // PENDING, ACCEPTED, REJECTED
    private boolean seenByReceiver;

    //1.....Requests PRODUCT
    public Trade(UUID playerID, UUID counterPartyID,Slot itemsGets)
    {
        this.tradeID = UUID.randomUUID();
        this.timeStamp = App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime();
        this.playerID = playerID;
        this.counterPartyId = counterPartyID;
        this.type = TradeType.PRODUCT_REQUEST;
        this.itemsGets = itemsGets;
        this.moneyGets = 0;
        this.itemsToGive = null;
        this.status = TradeStatus.PENDING;
        this.seenByReceiver = false;
    }

    //2....Requests Money
    public Trade(UUID playerID, UUID counterPartyID,int moneyGets)
    {
        this.tradeID = UUID.randomUUID();
        this.timeStamp = App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime();
        this.playerID = playerID;
        this.counterPartyId = counterPartyID;
        this.type = TradeType.MONEY_REQUEST;
        this.itemsGets = null;
        this.moneyGets = moneyGets;
        this.itemsToGive = null;
        this.status = TradeStatus.PENDING;
        this.seenByReceiver = false;
    }

    //3...Trade Product To Product
    public Trade(UUID playerID, UUID counterPartyID,Slot itemsToGive,Slot itemsGets)
    {
        this.tradeID = UUID.randomUUID();
        this.timeStamp = App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime();
        this.playerID = playerID;
        this.counterPartyId = counterPartyID;
        this.type = TradeType.PRODUCT_TO_PRODUCT_OFFER;
        this.itemsGets = itemsGets;
        this.moneyGets = 0;
        this.itemsToGive = itemsToGive;
        this.status = TradeStatus.PENDING;
        this.seenByReceiver = false;
    }

    //4...Trade Product To Money
    public Trade(UUID playerID, UUID counterPartyID,Slot itemsToGive,int moneyGets)
    {
        this.tradeID = UUID.randomUUID();
        this.timeStamp = App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime();
        this.playerID = playerID;
        this.counterPartyId = counterPartyID;
        this.type = TradeType.PRODUCT_TO_MONEY_OFFER;
        this.itemsGets = null;
        this.moneyGets = moneyGets;
        this.itemsToGive = itemsToGive;
        this.status = TradeStatus.PENDING;
        this.seenByReceiver = false;
    }


    public int getMoneyGets() {
        return moneyGets;
    }

    public Slot getItemsGets() {
        return itemsGets;
    }


    public Slot getItemsToGive() {
        return itemsToGive;
    }

    public UUID getTradeID() {
        return tradeID;
    }

    public TradeStatus getStatus() {
        return status;
    }

    public void setStatus(TradeStatus status) {
        this.status = status;
    }

    public TradeType getType() {
        return type;
    }

    public boolean isSeenByReceiver() {
        return seenByReceiver;
    }

    public void setSeenByReceiver(boolean seenByReceiver) {
        this.seenByReceiver = seenByReceiver;
    }

    public UUID getCounterPartyId() {
        return counterPartyId;
    }

    public UUID getPlayerID() {
        return playerID;
    }

    @Override
    public String toString() {
        return "\nTrade [tradeID=" + tradeID + ", timeStamp=" + timeStamp
                + "]\n[ playerID=" + playerID + ", counterPartyId=" + counterPartyId + "]" +
                "\nTrade Type=" + type + "\n--------------" +
                "\nitemsToGive=" + ((itemsToGive==null)?"null":itemsToGive.getItem().getName()) +
                "\ncount : " + ((itemsToGive==null)?"null":itemsToGive.getQuantity()) +
                "\nitemsToGet=" + ((itemsGets==null)?"null":itemsGets.getItem().getName()) +
                "\ncount : " + ((itemsGets==null)?"null":itemsGets.getQuantity()) +
                "\nmoneyGets : " + moneyGets +
                "status" + status +
                "\nseenByReceiver : " + seenByReceiver + "\n";
    }
}
