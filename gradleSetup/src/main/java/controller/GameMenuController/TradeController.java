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
        App.setCurrentMenu(Menu.tradeMenu);
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
        ArrayList<UUID> myTrades = me.getMyTradesID();
        Game thisGame = App.getCurrentUser().getCurrentGame();
        for (UUID uuid : myTrades) {
            Trade trade = thisGame.findTradeById(uuid);
            if (trade == null) {
                continue;
            }
            builder.append(trade.toString());
            builder.append("\n-------------------------------\n");
        }
        ArrayList<UUID> receivedTradesID = me.getReceivedTradesID();
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
            boolean successful = false;
            switch (tradeToDo.getType()) {
                case TradeType.MONEY_REQUEST: {

                }
                break;
                case TradeType.PRODUCT_REQUEST: {

                }
                break;
                case TradeType.PRODUCT_TO_PRODUCT_OFFER: {

                }
                break;
                case TradeType.PRODUCT_TO_MONEY_OFFER: {

                }
                break;
                default: {
                    return new Result(false, "wtf invalid trade how pussyble");
                }
            }


            if (successful) {
                tradeToDo.setStatus(TradeStatus.ACCEPTED);
                Player player2 = thisGame.findPlayerById(tradeToDo.getCounterPartyId());
                Player player1 = thisGame.findPlayerById(tradeToDo.getPlayerID());
                player1.getEndedTradesHistoryID().add(tradeToDo.getTradeID());
                player1.getMyTradesID().remove(tradeToDo.getTradeID());
                player2.getEndedTradesHistoryID().add(tradeToDo.getTradeID());
                player2.getReceivedTradesID().remove(tradeToDo.getTradeID());
                return new Result(true, "trade done successfully...");
            }
        } else if (resp.equals("-reject")) {
            tradeToDo.setStatus(TradeStatus.REJECTED);
            Player player1 = thisGame.findPlayerById(tradeToDo.getPlayerID());
            Player player2 = thisGame.findPlayerById(tradeToDo.getCounterPartyId());
            player1.getMyTradesID().remove(tradeToDo.getTradeID());
            player1.getEndedTradesHistoryID().add(tradeToDo.getTradeID());
            player2.getReceivedTradesID().remove(tradeToDo.getTradeID());
            player2.getEndedTradesHistoryID().add(tradeToDo.getTradeID());
            return new Result(true, "trade rejected successfully...");
        } else {
            return new Result(false, "taklifet chie da?");
        }
        return new Result(true, "trade added successfully...");
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
