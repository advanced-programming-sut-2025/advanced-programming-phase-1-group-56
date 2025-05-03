package controller.GameMenuController;

import controller.CommandController;
import model.*;
import model.Activities.Trade;
import model.Enums.Menu;
import model.items.Item;

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
        if(username == null || username.isEmpty()) {
            return new Result(false, "Username not found");

        }
        User user = App.getUserByUsername(username);
        if(user == null) {
            return new Result(false, "Username not found");
        }
        counterParty = App.getCurrentUser().getCurrentGame().getPlayerByUser(user);
        if(counterParty == null) {
            return new Result(false, "there is no player with such username in this game");
        }

        if (type.equals("offer")) {
            if (price == null && targetItem != null) {
                //ITEM TO ITEM TRADE
                try{
                    int amountToGive = Integer.parseInt(itemAmount);
                    int amountToGet = Integer.parseInt(targetAmount);
                    Item itemToGive = ItemRegistry.findItemByName(item);
                    Item itemToGet = ItemRegistry.findItemByName(targetItem);
                    if(itemToGive == null) {
                        return new Result(false, "there is no such item to offer");
                    }
                    if(itemToGet == null) {
                        return new Result(false, "there is no such target item");
                    }
                    int amountThatPlayerHas = App.getCurrentUser().getCurrentGame().getCurrentPlayer()
                            .getInventory().countItem(itemToGive);
                    if(amountThatPlayerHas < amountToGive) {
                        return new Result(false, "You do not have much item in you inventory");
                    }
                    Slot slotToGive = new Slot(itemToGive, amountToGive);
                    Slot slotToGet = new Slot(itemToGet, amountToGet);
                    Trade trade = new Trade(
                            App.getCurrentUser().getCurrentGame().getCurrentPlayer().getPlayerID(),
                            counterParty.getPlayerID(),
                            slotToGive,slotToGet
                            );
                }
                catch(NumberFormatException e) {
                    return new Result(false, "Invalid target amount or item amount");
                }
            } else if (targetItem == null && price != null) {
                //ITEM TO MONEY TRADE
                    try{
                        int amountToGive = Integer.parseInt(itemAmount);
                        int moneyToGet = Integer.parseInt(price);
                        Item itemToGive = ItemRegistry.findItemByName(item);
                        if(itemToGive == null) {
                            return new Result(false, "there is no such item to offer");
                        }
                        int amountThatPlayerHas = App.getCurrentUser().getCurrentGame().getCurrentPlayer()
                                .getInventory().countItem(itemToGive);
                        if(amountThatPlayerHas < amountToGive) {
                            return new Result(false, "You do not have much item in you inventory");
                        }
                        Slot slotToGive = new Slot(itemToGive, amountToGive);
                        Trade trade = new Trade(
                                App.getCurrentUser().getCurrentGame().getCurrentPlayer().getPlayerID(),
                                counterParty.getPlayerID(),
                                slotToGive,moneyToGet
                        );
                    }
                    catch(NumberFormatException e) {
                        return new Result(false, "Invalid target amount or item amount");
                    }
            } else if (targetItem == null) {
                return new Result(false,"you cannot leave both (targetItem) and price (price) blank");
            } else {
                return new Result(false,"you cannot get money and item at same time");
            }

        } else if (type.equals("request")) {
            if (price != null || targetItem != null) {
                return new Result(false, "invalid request format....to request money"+
                        " type money after the flag '-i'");
            } else {
                if (item.equals("money")) {
                    // MONEY_REQUEST
                    try{
                        int moneyToGet = Integer.parseInt(targetAmount);
                        Trade trade = new Trade(
                                App.getCurrentUser().getCurrentGame().getCurrentPlayer().getPlayerID(),
                                counterParty.getPlayerID(),
                                moneyToGet
                        );
                    }
                    catch(NumberFormatException e) {
                        return new Result(false, "Invalid target amount or item amount");
                    }
                    //
                } else {
                    //PRODUCT_REQUEST
                    try{
                        int amountToGet = Integer.parseInt(targetAmount);
                        Item itemToGet = ItemRegistry.findItemByName(item);
                        if(itemToGet == null) {
                            return new Result(false, "there is no such target item");
                        }
                        Slot slotToGet = new Slot(itemToGet, amountToGet);
                        Trade trade = new Trade(
                                App.getCurrentUser().getCurrentGame().getCurrentPlayer().getPlayerID(),
                                counterParty.getPlayerID(),
                                slotToGet
                        );
                    }
                    catch(NumberFormatException e) {
                        return new Result(false, "Invalid target amount or item amount");
                    }
                    //
                }
            }
        }
        return new Result(true,"trade added successfully");
    }

    public Result showTradeList(){
        String Builder
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
}
