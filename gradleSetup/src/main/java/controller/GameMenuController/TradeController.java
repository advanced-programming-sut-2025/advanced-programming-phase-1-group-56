package controller.GameMenuController;

import controller.CommandController;
import model.*;
import model.Activities.Trade;
import model.Enums.Activity.TradeStatus;
import model.Enums.Activity.TradeType;
import model.Enums.Menu;
import model.items.Item;

import java.util.ArrayList;
import java.util.UUID;
import java.util.regex.Matcher;

public class TradeController extends CommandController {

    public static Result startTrade(Player player) {
        App.setCurrentMenu(Menu.TradeMenu);
        return new Result(true, "you are now in trade menu");
    }

    public static Result makeNewTrade(Matcher matcher) {
        String username = matcher.group("username");
        String type = matcher.group("type");
        String item = matcher.group("item");
        String itemAmount = matcher.group("amount");
        String price = matcher.group("price");
        String targetItem = matcher.group("targetItem");
        String targetAmount = matcher.group("targetAmount");
        Player counterParty;
        if (username == null || username.isEmpty()) {
            return new Result(false, "Username not found");

        }
        User user = App.getUserByUsername(username);
        if (user == null) {
            return new Result(false, "Username not found");
        }
        counterParty = App.getCurrentUser().getCurrentGame().getPlayerByUser(user);
        if (counterParty == null) {
            return new Result(false, "there is no player with such username in this game");
        }

        if (type.equals("offer")) {
            if (price == null && targetItem != null) {
                //ITEM TO ITEM TRADE
                try {
                    int amountToGive = Integer.parseInt(itemAmount);
                    int amountToGet = Integer.parseInt(targetAmount);
                    //Item itemToGive = ItemRegistry.findItemByName(item);
                    Player me = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
                    Item itemToGive = findItemInPlayerInventoryByName(me, targetItem);
                    //Item itemToGet = ItemRegistry.findItemByName(targetItem);
                    Item itemToGet = findItemInPlayerInventoryByName(counterParty, item);
                    //TODO
                    if (itemToGive == null) {
                        return new Result(false, "there is no such item to offer");
                    }
                    if (itemToGet == null) {
                        return new Result(false, "there is no such target item");
                    }
                    int amountThatPlayerHas = App.getCurrentUser().getCurrentGame().getCurrentPlayer()
                            .getInventory().countItem(itemToGive);
                    if (amountThatPlayerHas < amountToGive) {
                        return new Result(false, "You do not have much item in you inventory");
                    }
                    Slot slotToGive = new Slot(itemToGive, amountToGive);
                    Slot slotToGet = new Slot(itemToGet, amountToGet);
                    Trade trade = new Trade(
                            App.getCurrentUser().getCurrentGame().getCurrentPlayer().getPlayerID(),
                            counterParty.getPlayerID(),
                            slotToGive, slotToGet
                    );
                } catch (NumberFormatException e) {
                    return new Result(false, "Invalid target amount or item amount");
                }
            } else if (targetItem == null && price != null) {
                //ITEM TO MONEY TRADE
                try {
                    int amountToGive = Integer.parseInt(itemAmount);
                    int moneyToGet = Integer.parseInt(price);
                    //Item itemToGive = ItemRegistry.findItemByName(item);
                    //TODO
                    Player me = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
                    Item itemToGive = findItemInPlayerInventoryByName(me, item);
                    if (itemToGive == null) {
                        return new Result(false, "there is no such item to offer");
                    }
                    int amountThatPlayerHas = App.getCurrentUser().getCurrentGame().getCurrentPlayer()
                            .getInventory().countItem(itemToGive);
                    if (amountThatPlayerHas < amountToGive) {
                        return new Result(false, "You do not have much item in you inventory");
                    }
                    Slot slotToGive = new Slot(itemToGive, amountToGive);
                    Trade trade = new Trade(
                            App.getCurrentUser().getCurrentGame().getCurrentPlayer().getPlayerID(),
                            counterParty.getPlayerID(),
                            slotToGive, moneyToGet
                    );
                } catch (NumberFormatException e) {
                    return new Result(false, "Invalid target amount or item amount");
                }
            } else if (targetItem == null) {
                return new Result(false, "you cannot leave both (targetItem) and price (price) blank");
            } else {
                return new Result(false, "you cannot get money and item at same time");
            }

        } else if (type.equals("request")) {
            if (price != null || targetItem != null) {
                return new Result(false, "invalid request format....to request money" +
                        " type money after the flag '-i'");
            } else {
                if (item.equals("money")) {
                    // MONEY_REQUEST
                    try {
                        int moneyToGet = Integer.parseInt(targetAmount);
                        Trade trade = new Trade(
                                App.getCurrentUser().getCurrentGame().getCurrentPlayer().getPlayerID(),
                                counterParty.getPlayerID(),
                                moneyToGet
                        );
                    } catch (NumberFormatException e) {
                        return new Result(false, "Invalid target amount or item amount");
                    }
                    //
                } else {
                    //PRODUCT_REQUEST
                    try {
                        int amountToGet = Integer.parseInt(targetAmount);
                        //Item itemToGet = ItemRegistry.findItemByName(item);
                        //TODO
                        Item itemToGet = findItemInPlayerInventoryByName(counterParty, item);
                        if (itemToGet == null) {
                            return new Result(false, "there is no such target item");
                        }
                        Slot slotToGet = new Slot(itemToGet, amountToGet);
                        Trade trade = new Trade(
                                App.getCurrentUser().getCurrentGame().getCurrentPlayer().getPlayerID(),
                                counterParty.getPlayerID(),
                                slotToGet
                        );
                    } catch (NumberFormatException e) {
                        return new Result(false, "Invalid target amount or item amount");
                    }
                    //
                }
            }
        }
        return new Result(true, "trade added successfully");
    }

    public Result showTradeList() {
        StringBuilder builder = new StringBuilder();
        builder.append("My Pending Trades:\n");
        builder.append("\n-------------------------------\n");
        Player me = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        ArrayList<UUID> myTrades = me.getMyTrades();
        Game thisGame = App.getCurrentUser().getCurrentGame();
        for (UUID uuid : myTrades) {
            Trade trade = thisGame.findTradeById(uuid);
            if (trade == null) {
                continue;
            }
            builder.append(trade.toString());
            builder.append("\n-------------------------------\n");
        }
        ArrayList<UUID> receivedTradesID = me.getReceivedTrades();
        for (UUID uuid : receivedTradesID) {
            Trade trade = thisGame.findTradeById(uuid);
            if (trade == null) {
                continue;
            }
            builder.append(trade.toString());
            builder.append("\n-------------------------------\n");
        }
        return new Result(true, builder.toString());
    }

    public Result tradeResponse(Matcher matcher) {
        String resp = matcher.group(1);
        String tradeID = matcher.group(2);
        if (resp == null) {
            return new Result(false, "response is null");
        }
        if (tradeID == null) {
            return new Result(false, "tradeID is null");
        }

        Game thisGame = App.getCurrentUser().getCurrentGame();
        Trade tradeToDo = thisGame.findTradeById(UUID.fromString(tradeID));

        if (tradeToDo == null) {
            return new Result(false, "there is no such trade");
        }


        if (resp.equals("-accept")) {
            switch (tradeToDo.getType()) {
                case TradeType.MONEY_REQUEST: {
                    Player me = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
                    Player counterParty = thisGame.findPlayerById(tradeToDo.getPlayerID());
                    //counterParty gets the money so I loose money
                    if(me.getGold()> tradeToDo.getMoneyGets()){//I have such money
                        me.addGold(-tradeToDo.getMoneyGets());
                        counterParty.addGold(tradeToDo.getMoneyGets());
                    }
                    else{
                        return new  Result(false, "you dont have enough money to give!");
                    }
                }
                break;
                case TradeType.PRODUCT_REQUEST: {
                    Player me = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
                    Player counterParty = thisGame.findPlayerById(tradeToDo.getPlayerID());
                    //counterParty gets the item so I loose item
                    Slot tradeSlot = tradeToDo.getItemsGets();
                    if(me.getInventory().countItem(tradeSlot.getItem())>tradeSlot.getQuantity()){//I have such item
                        if(counterParty.getInventory().canAddItem(tradeSlot.getItem(), tradeSlot.getQuantity())){
                            me.getInventory().remove(tradeSlot.getItem(),tradeSlot.getQuantity());
                            counterParty.getInventory().add(tradeSlot.getItem(),tradeSlot.getQuantity());
                        }
                        else {
                            return new Result(false,"your counterParty Inventory is full!\n"+
                                    "you can reject request or either wait...");
                        }
                    }
                    else{
                        return new  Result(false, "you dont have such item to give!");
                    }
                }
                break;
                case TradeType.PRODUCT_TO_PRODUCT_OFFER: {
                    Player me = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
                    Player counterParty = thisGame.findPlayerById(tradeToDo.getPlayerID());
                    Slot givingSlot = tradeToDo.getItemsGets();
                    Slot gettingSlot = tradeToDo.getItemsToGive();
                    // why its reverse? because trade is made from the counterParties POV
                    if(me.getInventory().countItem(givingSlot.getItem())>givingSlot.getQuantity()){//I have such item
                        if(counterParty.getInventory().countItem(gettingSlot.getItem())>gettingSlot.getQuantity()){//cp also has such item

                            //Temp remove
                            me.getInventory().remove(givingSlot.getItem(),givingSlot.getQuantity());
                            counterParty.getInventory().remove(gettingSlot.getItem(),gettingSlot.getQuantity());

                            if(!me.getInventory().canAddItem(gettingSlot.getItem(), gettingSlot.getQuantity())) {
                                //Temp remove cancellation
                                me.getInventory().add(givingSlot.getItem(),givingSlot.getQuantity());
                                counterParty.getInventory().add(gettingSlot.getItem(),gettingSlot.getQuantity());
                                return new Result(false,"your inventory doesn't have enough space RN!" +
                                        "you can reject the trade or respond later..." );
                            }
                            else if(!counterParty.getInventory().canAddItem(
                                    givingSlot.getItem(), givingSlot.getQuantity())) {
                                //Temp remove cancellation
                                me.getInventory().add(givingSlot.getItem(),givingSlot.getQuantity());
                                counterParty.getInventory().add(gettingSlot.getItem(),gettingSlot.getQuantity());
                                return new Result(false,"your counterParty inventory is full RN!!" +
                                        "you can reject the trade or respond later..." );
                            }else {
                                counterParty.getInventory().add(givingSlot.getItem(),givingSlot.getQuantity());
                                me.getInventory().add(gettingSlot.getItem(),gettingSlot.getQuantity());
                            }
                        }
                        else{
                            return new  Result(false, "your counterParty doesn't have such"+
                                    " item in his inventory RN!\n you can reject trade or wait for your counterParty");
                        }
                    }
                    else{
                        return new  Result(false, "you dont have such item to give!");
                    }
                }
                break;
                case TradeType.PRODUCT_TO_MONEY_OFFER: {
                    Player me = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
                    Player counterParty = thisGame.findPlayerById(tradeToDo.getPlayerID());
                    int moneyToPay = tradeToDo.getMoneyGets();
                    Slot gettingSlot = tradeToDo.getItemsToGive();
                    // why its reverse? because trade is made from the counterParties POV
                    if(me.getGold()>moneyToPay){//I have such money
                        if(counterParty.getInventory().countItem(gettingSlot.getItem())>gettingSlot.getQuantity()){//CP has such item
                            //Temp remove
                            me.addGold(-moneyToPay);
                            counterParty.getInventory().remove(gettingSlot.getItem(),gettingSlot.getQuantity());

                            if(!me.getInventory().canAddItem(gettingSlot.getItem(), gettingSlot.getQuantity())) {//inventory of me is full
                                //Temp remove cancellation
                                me.addGold(+moneyToPay);
                                counterParty.getInventory().add(gettingSlot.getItem(),gettingSlot.getQuantity());
                                return new Result(false,"your inventory doesn't have enough space RN!" +
                                        "you can reject the trade or respond later..." );
                            }else {//trade is done
                                me.getInventory().add(gettingSlot.getItem(), gettingSlot.getQuantity());
                            }
                        }
                        else{
                            return new  Result(false, "your counterParty doesn't have such"+
                                    " item in his inventory RN!\n you can reject trade or wait for your counterParty");
                        }
                    }
                    else{
                        return new  Result(false, "you dont have such money to give!");
                    }
                }
                break;
                default: {
                    return new Result(false, "wtf invalid trade how pussyble");
                }
            }


            //IF WE GET HERE MEANS SUCCESSFUL = TRUE
            tradeToDo.setStatus(TradeStatus.ACCEPTED);
            Player player2 = thisGame.findPlayerById(tradeToDo.getCounterPartyId());
            Player player1 = thisGame.findPlayerById(tradeToDo.getPlayerID());
            player1.getEndedTradesHistory().add(tradeToDo.getTradeID());
            player1.getMyTrades().remove(tradeToDo.getTradeID());
            player2.getEndedTradesHistory().add(tradeToDo.getTradeID());
            player2.getReceivedTrades().remove(tradeToDo.getTradeID());
            return new Result(true, "trade done successfully...");


        } else if (resp.equals("-reject")) {
            tradeToDo.setStatus(TradeStatus.REJECTED);
            Player player1 = thisGame.findPlayerById(tradeToDo.getPlayerID());
            Player player2 = thisGame.findPlayerById(tradeToDo.getCounterPartyId());
            player1.getMyTrades().remove(tradeToDo.getTradeID());
            player1.getEndedTradesHistory().add(tradeToDo.getTradeID());
            player2.getReceivedTrades().remove(tradeToDo.getTradeID());
            player2.getEndedTradesHistory().add(tradeToDo.getTradeID());
            return new Result(true, "trade rejected successfully...");
        } else {
            return new Result(false, "taklifet chie da?");
        }
    }


    public static Result showAllProducts() {

        return null;
    }

    public static Result showAllAvailableProducts() {

        return null;
    }

    public static Result purchase() {

        return null;
    }

    public static Result sellProducts() {

        return null;
    }
    //CHEAT

    public static Result cheatBuyProducts() {

        return null;
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
