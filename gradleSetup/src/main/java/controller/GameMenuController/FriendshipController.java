package controller.GameMenuController;

import controller.CommandController;
import model.*;
import model.Activities.Friendship;
import model.Activities.Gift;
import model.Activities.Message;

import model.Enums.BuffType;

import model.items.Item;

public class FriendshipController extends CommandController {
    public static Result showFriendships() {
        StringBuilder builder = new StringBuilder();
        builder.append("your friendships:\n");
        for (Friendship f : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getFriendShips()) {
            builder.append("\twith player:").append(f.getPlayer().getUser().getName()).append("\n")
                    .append("\txp:").append(f.getXp()).append("\n")
                    .append("\tlevel:").append(f.getLevel()).append("\n")
                    .append("----------------\n");
        }
        return new Result(true, builder.toString());
    }

    public static Result TalkWithPlayer(String userName, String message) {
        userName = userName.trim();
        message = message.trim();
        User user = App.getUserByUsername(userName);
        if (user == null) {
            return new Result(false, "User not found");
        }
        Player playerToTalk = App.getCurrentUser().getCurrentGame().getPlayerByUser(user);
        if (playerToTalk == null) {
            return new Result(false, "Player not found..");
        }
        Player me = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        if (playerToTalk.equals(me)) {
            return new Result(false, "You can't talk with yourself");
        }
        Message msgToAdd = new Message(message, me, playerToTalk);
        me.getMessages().add(msgToAdd);
        playerToTalk.getMessages().add(msgToAdd);


        try {
            me.findFriendshipByPlayer(playerToTalk).changeTwoWayXp(20);
        } catch (NullPointerException e) {
            return new Result(false, "one of Players not found..for adding friendship xp");
        }

        if (playerToTalk.equals(me.getPartner()) && !me.isInteractWithPartnerToday()) {
            me.setInteractWithPartnerToday(true);
            playerToTalk.setInteractWithPartnerToday(true);
            me.addEnergy(50);
            playerToTalk.addEnergy(50);
        }

        return new Result(true, "your message has delivered successfully");
    }

    public static Result historyOfTalking(String userName) {
        userName = userName.trim();
        User user = App.getUserByUsername(userName);
        if (user == null) {
            return new Result(false, "User not found");
        }
        Player playerToTalk = App.getCurrentUser().getCurrentGame().getPlayerByUser(user);
        if (playerToTalk == null) {
            return new Result(false, "Player not found..");
        }
        Player me = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        if (playerToTalk.equals(me)) {
            return new Result(true, "you dont have history to talking to yourself");
        }
        StringBuilder builder = new StringBuilder();
        builder.append("chat Between You And: ").append(playerToTalk.getUser().getName()).append("\n");
        for (Message msg : playerToTalk.getMessages()) {
            if (
                    (msg.getSender().equals(me) && msg.getReciever().equals(playerToTalk)) ||
                            (msg.getSender().equals(playerToTalk) && msg.getReciever().equals(me))
            ) {
                builder.append(msg.getMessage()).append("\n");
                builder.append("---");
            }
        }
        return new Result(true, builder.toString());
    }

    public static Result sendGift(String userName, String item, String amountStr) {
        User user = App.getUserByUsername(userName.trim());
        Player me = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        Item itemToGift = me.getInventory().findItemByName(item.trim());
        int amount;
        if (user == null) {
            return new Result(false, "User not found");
        }
        Player playerToGift = App.getCurrentUser().getCurrentGame().getPlayerByUser(user);
        if (playerToGift == null) {
            return new Result(false, "Player not found..");
        }
        if (itemToGift == null) {
            return new Result(false, "Item not found in inventory");
        }
        try {
            amount = Integer.parseInt(amountStr);
        } catch (NumberFormatException e) {
            return new Result(false, "Amount must be an integer");
        }
        if (amount > me.getInventory().countItem(itemToGift)) {
            return new Result(false, "You have not enough amount of item");
        } else if (!me.getPosition().isNear(playerToGift.getPosition(), 3)) {
            return new Result(false, "You are not nearby the player to send gift");
        } else if (playerToGift.equals(me)) {
            return new Result(false, "you have narcissism");
        } else if (!playerToGift.getInventory().canAddItem(itemToGift, amount)) {
            return new Result(false, "Your friend inventory is full...");
        } else if (me.findFriendshipByPlayer(playerToGift).getLevel() < 1) {
            return new Result(false, "Your friendship level should be at least 1 to send gift");
        }
        me.getInventory().remove(itemToGift, amount);
        playerToGift.getInventory().add(itemToGift, amount);
        playerToGift.getGifts().add(new Gift(me, playerToGift, itemToGift, amount));

        if (playerToGift.equals(me.getPartner()) && !me.isInteractWithPartnerToday()) {
            me.setInteractWithPartnerToday(true);
            playerToGift.setInteractWithPartnerToday(true);
            me.addEnergy(50);
            playerToGift.addEnergy(50);
        }


        return new Result(true, "You have successfully sent gift...");
    }

    public static Result giftList() {
        StringBuilder builder = new StringBuilder();
        builder.append("your gifts:\n----------------\n");
        for (Gift f : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getGifts()) {
            if (!f.getReceiver().equals(App.getMe()))
                continue;
            builder.append("gift id: ").append(f.getGiftID()).append("\n")
                    .append("\tfrom player:").append(f.getSender().getUser().getName()).append("\n")
                    .append("\titem: '").append(f.getGift().getName())
                    .append("' * ").append(f.getAmount()).append("\n")
                    .append("\trate: ").append((f.getRate() == -1) ? "unrated" : f.getRate()).append("\n")
                    .append("----------------\n");
        }
        return new Result(true, builder.toString());
    }

    public static Result manageRatingGift(String giftId, String rateStr) {
        giftId = giftId.trim();
        rateStr = rateStr.trim();
        Player me = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        Gift gift = me.findGiftById(giftId);
        if (gift == null) {
            return new Result(false, "gift doesn't find with such id..");
        }
        int rate;
        try {
            rate = Integer.parseInt(rateStr);
        } catch (NumberFormatException e) {
            return new Result(false, "Rate must be an integer");
        }
        gift.setRate(rate);
        me.findFriendshipByPlayer(gift.getSender()).changeTwoWayXp(((rate - 3) * 30) + 15);
        return new Result(true, "You have successfully rated gift...");
    }

    public static Result giftHistory() {
        StringBuilder builder = new StringBuilder();
        builder.append(giftList().message());
        builder.append("your sent gifts:\n----------------\n");
        for (Gift f : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getGifts()) {
            if (!f.getSender().equals(App.getMe()))
                continue;
            builder.append("gift id: ").append(f.getGiftID()).append("\n")
                    .append("\tfrom player:").append(f.getSender().getUser().getName()).append("\n")
                    .append("\titem: '").append(f.getGift().getName())
                    .append("' * ").append(f.getAmount()).append("\n")
                    .append("\trate: ").append((f.getRate() == -1) ? "unrated" : f.getRate()).append("\n")
                    .append("----------------\n");
        }
        return new Result(true, builder.toString());
    }

    public static Result hugPlayer(String username) {
        User user = App.getUserByUsername(username.trim());
        if (user == null) {
            return new Result(false, "User not found");
        }
        Player player = App.getCurrentUser().getCurrentGame().getPlayerByUser(user);
        if (player == null) {
            return new Result(false, "Player not found");
        }
        Player me = App.getMe();
        if (!me.getPosition().isNear(player.getPosition(), 1)) {
            return new Result(false, "You are out of bounds");
        }
        if (me.findFriendshipByPlayer(player).getLevel() < 2) {
            return new Result(false, "atsafghorilah.Hanuz mahram nistid..");
        }

        if (player.equals(me.getPartner()) && !me.isInteractWithPartnerToday()) {
            me.setInteractWithPartnerToday(true);
            player.setInteractWithPartnerToday(true);
            me.addEnergy(50);
            player.addEnergy(50);
        }

        me.findFriendshipByPlayer(player).changeTwoWayXp(60);
        return new Result(true, "Hugging " + player.getUser().getName() + " now...<3<3<3");

    }

    public static Result buyFlower(String username) {
        User user = App.getUserByUsername(username.trim());
        if (user == null) {
            return new Result(false, "User does not found");
        }
        Player player = App.getCurrentUser().getCurrentGame().getPlayerByUser(user);
        if (player == null) {
            return new Result(false, "Player does not found");
        }
        Player me = App.getMe();
        if (!me.getPosition().isNear(player.getPosition(), 1)) {
            return new Result(false, "You are out of bounds");
        }
        if (me.findFriendshipByPlayer(player).getLevel() < 2) {
            return new Result(false, "kesafat ashghal saad bar bet goftam esm babaye joloye man niar..");
        }

        Item gol = me.getInventory().findItemByName("Bouquet");
        if (gol == null) {
            return new Result(false, "gol nemikeshi dasdash?");
        } else if (!player.getInventory().canAddItem(gol, 1)) {
            return new Result(false, "refighet pake..");
        }
        me.getInventory().remove(gol, 1);
        player.getInventory().add(gol, 1);
        player.findFriendshipByPlayer(player).setDeliveredFlowerBothWay(true);

        if (player.equals(me.getPartner()) && !me.isInteractWithPartnerToday()) {
            me.setInteractWithPartnerToday(true);
            player.setInteractWithPartnerToday(true);
            me.addEnergy(50);
            player.addEnergy(50);
        }

        return new Result(true, "gol keshide budan halat tabii nabudan(mission passed,respect...)");
    }

    public static Result askMarriage(String username, String ring) {
        User user = App.getUserByUsername(username.trim());
        if (user == null) {
            return new Result(false, "You can not marry your illusion");
        }

        Player player = App.getCurrentUser().getCurrentGame().getPlayerByUser(user);
        if (player == null) {
            return new Result(false, "This player only exist in your dream.. find a real one");
        }

        Player me = App.getMe();
        if (!me.isGender()) {
            return new Result(false, "a boy can send request. you cannot LOL");
        }
        if (player.isGender()) {
            return new Result(false, "why are you gay?? why are you gay????!");
        }
        if (me.getPosition().isNear(player.getPosition(), 1)) {
            return new Result(false, "out of bounds...");
        }
        if (me.findFriendshipByPlayer(player).getLevel() < 3) {
            return new Result(false, "namzad nistid hanuz...");
        }
        Item ringToMarry = me.getInventory().findItemByName(ring);
        if (ringToMarry == null) {
            return new Result(false, "you dont have ringe mored nazar");
        } else if (!ringToMarry.getName().toLowerCase().contains("ring")) {
            return new Result(false, "bedun halghe nemishe");
        } else if (!player.getInventory().canAddItem(ringToMarry, 1)) {
            return new Result(false, "her inventory is full. she maybe...");
        } else if (player.getPartner() != null) {
            return new Result(false, "astaghfurillah brother,HARAMM!!#@#@#$@##,she is married ");
        }

        for (Gift request : player.getMarryRequests()) {
            if (request.getSender().equals(me)) {
                return new Result(false, "don't spam marry request pls,we will peygiri it");
            }
        }

        player.getMarryRequests().add(new Gift(me, player, ringToMarry, 1));
        me.getInventory().remove(ringToMarry, 1);//TEMP REMOVE maybe comebacks
        return new Result(true, "KhasteGari sabt shod..ishala ke kheyre...");
    }

    public static Result respondMarriage(String respond, String username) {
        User user = App.getUserByUsername(username.trim());
        if (user == null) {
            return new Result(false, "User not found");
        }
        Player player = App.getCurrentUser().getCurrentGame().getPlayerByUser(user);
        if (player == null) {
            return new Result(false, "Player not found");
        }
        Player me = App.getMe();
        Gift request = null;
        for (Gift req : player.getMarryRequests()) {
            if (req.getSender().equals(player)) {
                request = req;
            }
        }
        if (request == null) {
            return new Result(false, "this guy doesn't send you marry request");
        }

        switch (respond) {
            case "-accept": {
                me.getInventory().add(request.getGift(), 1);//girl gets the ring
                me.setPartner(player);
                me.findFriendshipByPlayer(player).setMarriedBothWay(true);
                me.getMarryRequests().clear();
                return new Result(true, "Mobarake...You are now married to " + player.getUser().getName());
            }
            case "-reject": {
                player.getInventory().add(request.getGift(), 1);//boy get back his ring
                me.findFriendshipByPlayer(player).setMarriedBothWay(false);
                me.getMarryRequests().remove(request);
                me.findFriendshipByPlayer(player).resetFriendshipBothWay();
                player.setCurrentBuff(new Buff(BuffType.Depression));
                return new Result(true, "you rejected " + player.getUser().getName() + "'s request");
            }
            default:
                return new Result(false, "barye bar N on porsidam ..invalid response.");
        }
    }
}