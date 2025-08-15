package io.src.controller.GameMenuController;

import io.src.controller.CommandController;
import io.src.model.*;
import io.src.model.Activities.Trade;
import io.src.model.Enums.Activity.TradeStatus;
import io.src.model.Enums.Activity.TradeType;
import io.src.model.Enums.Menu;
import io.src.model.GameObject.ShippingBar;
import io.src.model.MapModule.GameLocations.Farm;
import io.src.model.items.Item;

import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;

public class TradeController extends CommandController {

    public static Result startTrade() {
        App.setCurrentMenu(Menu.TradeMenu);
        return new Result(true, "you are now in trade menu");
    }

    public static Result makeNewTrade(String itemName1, String itemCount1, String itemName2, String itemCount2, TradeType tradeType, Player counterParty) {
        itemName1 = itemName1.isEmpty() ? "INVALID" : itemName1;
        itemName2 = itemName2.isEmpty() ? "INVALID" : itemName2;
        itemCount1 = itemCount1.isEmpty() ? "-1" : itemCount1;
        itemCount2 = itemCount2.isEmpty() ? "-1" : itemCount2;
        Trade trade = null;
        if (counterParty == null) {
            return new Result(false, "player == null");
        }
        switch (tradeType) {
            case PRODUCT_TO_PRODUCT_OFFER: {
                Item item1 = App.getMe().getInventory().findItemByName(itemName1);
                if (item1 == null) {
                    return new Result(false, "item not found in your inventory");
                }
                int count1 = 0;
                try {
                    count1 = Integer.parseInt(itemCount1);
                    if (count1 <= 0) {
                        return new Result(false, "invalid count1");
                    }
                } catch (Exception e) {
                    return new Result(false, "invalid count1");
                }

                Item item2 = counterParty.getInventory().findItemByName(itemName2);
                if (item2 == null) {
                    return new Result(false, "item not found in counterParty inventory");
                }
                int count2 = 0;
                try {
                    count2 = Integer.parseInt(itemCount2);
                    if (count2 <= 0) {
                        return new Result(false, "invalid count2");
                    }
                } catch (Exception e) {
                    return new Result(false, "invalid count2");
                }
                trade = new Trade(App.getMe(), counterParty, new Slot(item1, count1), new Slot(item2, count2));
            }
            break;
            case PRODUCT_TO_MONEY_OFFER: {
                Item item1 = App.getMe().getInventory().findItemByName(itemName1);
                if (item1 == null) {
                    return new Result(false, "item not found in your inventory");
                }
                int count1 = 0;
                try {
                    count1 = Integer.parseInt(itemCount1);
                    if (count1 <= 0) {
                        return new Result(false, "invalid count1");
                    }
                } catch (Exception e) {
                    return new Result(false, "invalid count1");
                }

                int count2 = 0;
                try {
                    count2 = Integer.parseInt(itemCount2);
                    if (count2 <= 0) {
                        return new Result(false, "invalid count2");
                    }
                } catch (Exception e) {
                    return new Result(false, "invalid count2");
                }
                trade = new Trade(App.getMe(), counterParty, new Slot(item1, count1), count2);
            }
            break;
            case PRODUCT_REQUEST: {
                Item item2 = counterParty.getInventory().findItemByName(itemName2);
                if (item2 == null) {
                    return new Result(false, "item not found in your inventory");
                }
                int count2 = 0;
                try {
                    count2 = Integer.parseInt(itemCount2);
                    if (count2 <= 0) {
                        return new Result(false, "invalid count2");
                    }
                } catch (Exception e) {
                    return new Result(false, "invalid count1");
                }

                trade = new Trade(App.getMe(), counterParty, new Slot(item2, count2));
            }
            break;
            case MONEY_REQUEST: {
                int count2 = 0;
                try {
                    count2 = Integer.parseInt(itemCount2);
                    if (count2 <= 0) {
                        return new Result(false, "invalid count2");
                    }
                } catch (Exception e) {
                    return new Result(false, "invalid count2");
                }

                trade = new Trade(App.getMe(), counterParty, count2);
            }
            break;
            default:
                return new Result(false, "Trade type not supported");
        }

        App.getMe().getMyTrades().add(trade);
        counterParty.getReceivedTrades().add(trade);
        System.out.println(trade.toString());
        return new Result(true, "trade added successfully");
    }

    public static Result showTradeList() {
        StringBuilder builder = new StringBuilder();
        builder.append("My Pending Trades:\n");
        builder.append("\n-------------------------------\n");
        Player me = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        ArrayList<Trade> myTrades = me.getMyTrades();
        Game thisGame = App.getCurrentUser().getCurrentGame();
        for (Trade trade : myTrades) {
            if (trade == null) {
                continue;
            }
            builder.append(trade);
            builder.append("\n-------------------------------\n");
        }
        ArrayList<Trade> receivedTradesID = me.getReceivedTrades();
        for (Trade trade : receivedTradesID) {
            if (trade == null) {
                continue;
            }
            builder.append(trade);
            builder.append("\n-------------------------------\n");
        }
        return new Result(true, builder.toString());
    }

    public static Result tradeResponse(boolean accept, Trade trade) {

        Game thisGame = App.getCurrentUser().getCurrentGame();

        if (trade == null) {
            return new Result(false, "there is no such trade");
        }


        if (accept) {
            switch (trade.getType()) {
                case TradeType.MONEY_REQUEST: {
                    Player me = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
                    Player counterParty = trade.getPlayerID();
                    //counterParty gets the money so I loose money
                    if (me.getGold() > trade.getMoneyGets()) {//I have such money
                        me.addGold(-trade.getMoneyGets());
                        counterParty.addGold(trade.getMoneyGets());
                    } else {
                        return new Result(false, "you dont have enough money to give!");
                    }
                }
                break;
                case TradeType.PRODUCT_REQUEST: {
                    Player me = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
                    Player counterParty = trade.getPlayerID();
                    //counterParty gets the item so I loose item
                    Slot tradeSlot = trade.getItemsGets();
                    if (me.getInventory().countItem(tradeSlot.getItem()) > tradeSlot.getQuantity()) {//I have such item
                        if (counterParty.getInventory().canAddItem(tradeSlot.getItem(), tradeSlot.getQuantity())) {
                            me.getInventory().remove(tradeSlot.getItem(), tradeSlot.getQuantity());
                            counterParty.getInventory().add(tradeSlot.getItem(), tradeSlot.getQuantity());
                        } else {
                            return new Result(false, "your counterParty Inventory is full!\n" +
                                "you can reject request or either wait...");
                        }
                    } else {
                        return new Result(false, "you dont have such item to give!");
                    }
                }
                break;
                case TradeType.PRODUCT_TO_PRODUCT_OFFER: {
                    Player me = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
                    Player counterParty = trade.getPlayerID();
                    Slot givingSlot = trade.getItemsGets();
                    Slot gettingSlot = trade.getItemsToGive();
                    // why its reverse? because trade is made from the counterParties POV
                    if (me.getInventory().countItem(givingSlot.getItem()) > givingSlot.getQuantity()) {//I have such item
                        if (counterParty.getInventory().countItem(gettingSlot.getItem()) > gettingSlot.getQuantity()) {//cp also has such item

                            //Temp remove
                            me.getInventory().remove(givingSlot.getItem(), givingSlot.getQuantity());
                            counterParty.getInventory().remove(gettingSlot.getItem(), gettingSlot.getQuantity());

                            if (!me.getInventory().canAddItem(gettingSlot.getItem(), gettingSlot.getQuantity())) {
                                //Temp remove cancellation
                                me.getInventory().add(givingSlot.getItem(), givingSlot.getQuantity());
                                counterParty.getInventory().add(gettingSlot.getItem(), gettingSlot.getQuantity());
                                return new Result(false, "your inventory doesn't have enough space RN!" +
                                    "you can reject the trade or respond later...");
                            } else if (!counterParty.getInventory().canAddItem(
                                givingSlot.getItem(), givingSlot.getQuantity())) {
                                //Temp remove cancellation
                                me.getInventory().add(givingSlot.getItem(), givingSlot.getQuantity());
                                counterParty.getInventory().add(gettingSlot.getItem(), gettingSlot.getQuantity());
                                return new Result(false, "your counterParty inventory is full RN!!" +
                                    "you can reject the trade or respond later...");
                            } else {
                                counterParty.getInventory().add(givingSlot.getItem(), givingSlot.getQuantity());
                                me.getInventory().add(gettingSlot.getItem(), gettingSlot.getQuantity());
                            }
                        } else {
                            return new Result(false, "your counterParty doesn't have such" +
                                " item in his inventory RN!\n you can reject trade or wait for your counterParty");
                        }
                    } else {
                        return new Result(false, "you dont have such item to give!");
                    }
                }
                break;
                case TradeType.PRODUCT_TO_MONEY_OFFER: {
                    Player me = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
                    Player counterParty = trade.getPlayerID();
                    int moneyToPay = trade.getMoneyGets();
                    Slot gettingSlot = trade.getItemsToGive();
                    // why its reverse? because trade is made from the counterParties POV
                    if (me.getGold() > moneyToPay) {//I have such money
                        if (counterParty.getInventory().countItem(gettingSlot.getItem()) > gettingSlot.getQuantity()) {//CP has such item
                            //Temp remove
                            me.addGold(-moneyToPay);
                            counterParty.getInventory().remove(gettingSlot.getItem(), gettingSlot.getQuantity());

                            if (!me.getInventory().canAddItem(gettingSlot.getItem(), gettingSlot.getQuantity())) {//inventory of me is full
                                //Temp remove cancellation
                                me.addGold(+moneyToPay);
                                counterParty.getInventory().add(gettingSlot.getItem(), gettingSlot.getQuantity());
                                return new Result(false, "your inventory doesn't have enough space RN!" +
                                    "you can reject the trade or respond later...");
                            } else {//trade is done
                                me.getInventory().add(gettingSlot.getItem(), gettingSlot.getQuantity());
                            }
                        } else {
                            return new Result(false, "your counterParty doesn't have such" +
                                " item in his inventory RN!\n you can reject trade or wait for your counterParty");
                        }
                    } else {
                        return new Result(false, "you dont have such money to give!");
                    }
                }
                break;
                default: {
                    return new Result(false, "wtf invalid trade how pussyble");
                }
            }


            //IF WE GET HERE MEANS SUCCESSFUL = TRUE
            trade.setStatus(TradeStatus.ACCEPTED);
            Player player2 = trade.getCounterPartyId();
            Player player1 = trade.getPlayerID();
            player1.getEndedTradesHistory().add(trade);
            player1.getMyTrades().remove(trade);
            player2.getEndedTradesHistory().add(trade);
            player2.getReceivedTrades().remove(trade);
            //friendship
            //player1.findFriendshipByPlayer(player2).changeTwoWayXp(+50);
            return new Result(true, "trade done successfully...");


        } else {
            trade.setStatus(TradeStatus.REJECTED);
            Player player1 = trade.getPlayerID();
            Player player2 = trade.getCounterPartyId();
            player1.getMyTrades().remove(trade);
            player1.getEndedTradesHistory().add(trade);
            player2.getReceivedTrades().remove(trade);
            player2.getEndedTradesHistory().add(trade);
            //friendship
            //player1.findFriendshipByPlayer(player2).changeTwoWayXp(-30);
            return new Result(true, "trade rejected successfully...");
        }
    }

    public static Result showTradeHistory() {
        StringBuilder builder = new StringBuilder();
        builder.append("History Trades:\n");
        builder.append("\n-------------------------------\n");
        Player me = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        ArrayList<Trade> myTrades = me.getEndedTradesHistory();
        Game thisGame = App.getCurrentUser().getCurrentGame();
        for (Trade trade : myTrades) {
            if (trade == null) {
                continue;
            }
            builder.append(trade);
            builder.append("\n-------------------------------\n");
        }
        return new Result(true, builder.toString());
    }

    public static Result sellProducts(String item, String quantity) {

        int amount;
        ShippingBar shippingBar;
        if (quantity == null) {
            amount = 1;
        } else {
            try {
                amount = Integer.parseInt(quantity);
            } catch (NumberFormatException e) {
                return new Result(false, e.getMessage());
            }
        }
        Player me = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        Item itemToSell = findItemInPlayerInventoryByName(me, item);
        if (itemToSell == null) {
            return new Result(false, "you dont have such item");
        } else if (amount <= 0) {
            return new Result(false, "wtf brother amount should be greater than 0");
        } else if (amount > me.getInventory().countItem(itemToSell)) {
            return new Result(false, "you dont have as much you want to sell");
        }

        shippingBar = App.getMe().getPlayerFarm().getShippingBar();
        me.getInventory().remove(itemToSell, amount);
        shippingBar.getInventory().add(itemToSell, amount);
        return new Result(true, "sold the product successfully...");
    }

    private static ShippingBar getShippingBarNearby(Player me) {
        ShippingBar shippingBar = null;
        if (me.getCurrentGameLocation() instanceof Farm farm) {
            for (int i = (int) me.getPosition().getX() - 1; i <= me.getPosition().getX() + 1; i++) {
                for (int j = (int) me.getPosition().getY() - 1; j <= me.getPosition().getY() + 1; j++) {
                    if (farm.getTiles()[i][j].getFixedObject() instanceof ShippingBar ship) {
                        shippingBar = ship;
                    }
                }

            }
        }
        return shippingBar;
    }


    public static Result cheatAddMoney(String amountStr) {
        try {
            int amount = Integer.parseInt(amountStr.trim());
            App.getCurrentUser().getCurrentGame().getCurrentPlayer().addGold(amount);
        } catch (NumberFormatException e) {
            return new Result(false, "invalid amount");
        }
        return new Result(true, "money added successfully...");
    }

    public static Item findItemInPlayerInventoryByName(Player player, String itemName) {
        for (Slot slot : player.getInventory().getSlots()) {
            if (slot.getItem().getName().equals(itemName)) {
                return slot.getItem();
            }
        }
        return null;
    }
}
