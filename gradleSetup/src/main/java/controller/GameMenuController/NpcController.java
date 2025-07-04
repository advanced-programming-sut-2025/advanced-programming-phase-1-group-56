package controller.GameMenuController;

import controller.CommandController;
import model.App;
import model.Enums.Items.EtcType;
import model.Enums.Items.SeedType;
import model.Enums.Recepies.FoodRecipesList;
import model.Enums.NpcDialogs.NpcPrompt;
import model.Enums.NpcDialogs.RobinPrompt;
import model.Enums.WeatherAndTime.WeatherType;
import model.GameObject.ArtesianMachine;
import model.GameObject.NPC.*;
import model.GameObject.NPC.DeepSeekApiChat;
import model.GameObject.NPC.NpcFriendship;
import model.MapModule.GameMap;
import model.Player;
import model.Result;
import model.items.Etc;
import model.items.Item;
import model.items.Saleable;
import model.items.Seed;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class NpcController extends CommandController {

    public static Result meetNPC(String npcName) throws IOException {
        Player player = App.getMe();
        GameMap map = App.getCurrentUser().getCurrentGame().getGameMap();
        NPC npc = map.getPelikanTown().getNpcByName(npcName);
        if (npc == null) {
            return new Result(false, "NPC '" + npcName + "' not found.");
        }
        if (!player.getPosition().isNear(npc.getPosition(), 1)) {
            return new Result(false, "You aren't close enough to " + npcName + ".");
        }


        NpcFriendship friendship = npc.findFriendshipByPlayer(player);
        if (!friendship.isHasMetToday()) {
            friendship.addXp(20);
            friendship.setHasMetToday(true);
        }

        Class<? extends NpcPrompt> cls = npc.getType().getPromptClass();
        if (cls == null) {
            return new Result(false, "No dialogue class configured for " + npcName + ".");
        }

        Method method;
        try {
            method = cls.getMethod("randomFor",
                    WeatherType.class, boolean.class, int.class
            );
        } catch (NoSuchMethodException e) {
            return new Result(false, "Dialogue method not found for " + cls.getSimpleName());
        }

        String outPrompt;
        try {
            outPrompt = (String) method.invoke(
                    null,
                    App.getCurrentUser().getCurrentGame().getWeatherState().getTodayWeather(),
                    App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour() < 19,
                    friendship.getLevel()
            );
        } catch (IllegalAccessException | InvocationTargetException e) {
            return new Result(false, "Error generating dialogue: " + e.getCause().getMessage());
        }

        //DeepSeekApiChat deepSeek = new DeepSeekApiChat();
        //String meet = deepSeek.generateDialogue(outPrompt);
        player.setLastMeetedNpc(npc);
        //return new Result(true, meet);
        //TODO DEBUG
        return new Result(true, outPrompt);
    }


    public static Result giftNPC(String npcName, String itemName) {
        NPC npc = App.getCurrentUser().getCurrentGame().getGameMap().getPelikanTown().getNpcByName(npcName.trim());
        if (npc == null) {
            return new Result(false, "NPC doesn't exist in town");
        }

        Item item = App.getMe().getInventory().findItemByName(itemName);
        if (item == null) {
            return new Result(false, "You don't have such item");
        }

        if (!App.getMe().getPosition().isNear(npc.getPosition(), 1)) {
            return new Result(false, "You have to be near npc");
        }
        NpcFriendship friendship = npc.findFriendshipByPlayer(App.getMe());
        boolean isFavorite = npc.getType().getFavoriteItems().stream().anyMatch(i -> i.getName().equalsIgnoreCase(itemName));
        App.getMe().getInventory().remove(item, 1);

        if (!friendship.isHasGiftedToday()) {
            if (isFavorite) {
                friendship.addXp(+200);
                friendship.setHasGiftedToday(true);
                return new Result(true, "tnx for gifting " + npc.getType().getName() +
                        " it's favorite item");
            } else {
                friendship.addXp(+50);
                friendship.setHasGiftedToday(true);
                return new Result(true, "tnx for gifting " + npc.getType().getName());
            }
        }
        return new Result(true, "gift sent successfully...");
    }

    public static Result manageFriendshipNPCList() {
        StringBuilder builder = new StringBuilder();
        builder.append("your friendships:\n");
        for (NpcFriendship f : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getNpcFriendShips()) {
            builder.append("with Npc:").append(f.getNpc().getType().getName()).append("\n")
                    .append("\txp:").append(f.getXp()).append("\n")
                    .append("\tlevel:").append(f.getLevel()).append("\n")
                    .append("----------------\n");
        }
        return new Result(true, builder.toString());
    }

    public static Result manageShowAllQuests() {
        StringBuilder builder = new StringBuilder();
        builder.append("your quests:\n");
        for (NpcFriendship f : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getNpcFriendShips()) {
            int IndexOfActiveReq = f.getLastActiveRequest();
            NpcRequest req = f.getNpc().getType().getRequests().get(IndexOfActiveReq);
            if (f.getLastActiveRequest() != -1) {
                builder.append("--your Active request with: ").append(f.getNpc().getType().getName())
                        .append("\n\tindex of request: ").append(IndexOfActiveReq)
                        .append(req.toString()).append("\n");
            } else if (f.getLastActiveRequest() != -1 && f.getDaysToSecondQ() > 0) {
                builder.append("--your second request With: ").append(f.getNpc().getType().getName()).append("\n")
                        .append("will be activated on ").append(f.getDaysToSecondQ()).append(" day(s)\n");
            } else if (f.getLastActiveRequest() != -1 && f.getLevel() == 0) {
                builder.append("--your main requests with: ").append(f.getNpc().getType().getName()).append("\n")
                        .append("is done. to get the extra request you have to increase your freindShip with this NPC ");
            } else {
                builder.append("you dont have any Active request with: ").append(f.getNpc().getType().getName())
                        .append("and you never will. wish a good friendship for you and it");
            }
            builder.append("\n-------------------");
        }
        return new Result(true, builder.toString());
    }


    public static Result manageShowActiveQuest() {
        StringBuilder builder = new StringBuilder();
        NPC npc = App.getMe().getLastMeetedNpc();

        if (npc == null) {
            return new Result(false, "You haven't met a npc recently");
        } else if (!App.getMe().getPosition().isNear(npc.getPosition(), 1)) {
            return new Result(false, "You have to be near npc:" + npc.getPosition().toString());
        }

        builder.append("your active Quest with npc:").append(npc.getType().getName()).append("\n");
        NpcFriendship f = npc.findFriendshipByPlayer(App.getMe());
        int IndexOfActiveReq = f.getLastActiveRequest() ;
        NpcRequest req = f.getNpc().getType().getRequests().get(IndexOfActiveReq);
        if (f.getLastActiveRequest() != -1) {
            builder.append("--your Active request with: ").append(f.getNpc().getType().getName())
                    .append("\n\tindex of request: ").append(IndexOfActiveReq)
                    .append(req.toString()).append("\n");
        } else if (f.getLastActiveRequest() != -1 && f.getDaysToSecondQ() > 0) {
            builder.append("--your second request With: ").append(f.getNpc().getType().getName()).append("\n")
                    .append("will be activated on ").append(f.getDaysToSecondQ()).append(" day(s)\n");
        } else if (f.getLastActiveRequest() != -1 && f.getLevel() == 0) {
            builder.append("--your main requests with: ").append(f.getNpc().getType().getName()).append("\n")
                    .append("is done. to get the extra request you have to increase your freindShip with this NPC ");
        } else {
            builder.append("you dont have any Active request with: ").append(f.getNpc().getType().getName())
                    .append("and you never will. wish a good friendship for you and it");
        }
        builder.append("\n-------------------");
        return new Result(true, builder.toString());
    }

    public static Result finishingQuest(String indexStr) {
        NPC npc = App.getMe().getLastMeetedNpc();
        int index;
        try {
            index = Integer.parseInt(indexStr.trim());
        } catch (NumberFormatException e) {
            return new Result(false, "Invalid index format");
        }
        if (npc == null) {
            return new Result(false, "You haven't met a npc recently");
        } else if (!App.getMe().getPosition().isNear(npc.getPosition(), 1)) {
            return new Result(false, "You have to be near npc:" + npc.getPosition().toString());
        }
        NpcFriendship f = npc.findFriendshipByPlayer(App.getMe());
        if (f.getLastActiveRequest() == -1) {
            return new Result(false, "You dont have any Active request with: " +
                    f.getNpc().getType().getName());
        }
        if (f.getLastActiveRequest() != index) {
            return new Result(false, "index you entered is not your active quest");
        }
        NpcRequest req = f.getNpc().getType().getRequests().get(index);
        Item itemToPay = req.getRequestedItem();
        int payAmount = req.getRequestedQuantity();
        if (itemToPay instanceof Etc etc && etc.getEtcType() == EtcType.ANY_PLANT) {
            boolean found = false;
            for (SeedType type : SeedType.values()) {
                Seed newSeed = new Seed(type);
                if (App.getMe().getInventory().countItem(newSeed) >= payAmount) {
                    found = true;
                    itemToPay = newSeed;
                }
            }
            if (!found) {
                return new Result(false, "You dont have" + payAmount + " of any plant");
            }
        } else if (App.getMe().getInventory().countItem(itemToPay) < payAmount) {
            return new Result(false, "you dont have enough item to finish the quest with:"
                    + npc.getType().getName() + " item to pay : " + itemToPay.getName() + "*" + payAmount);
        }
        App.getMe().getInventory().remove(itemToPay, payAmount);//Temp remove

        Saleable reward = req.getRewardItem();
        int rewardAmount = req.getRewardQuantity();

        if (reward instanceof EtcType etcType) {
            switch (etcType) {
                case NPC_FRIENDSHIP_XP: {
                    f.addXp(rewardAmount);
                }
                break;
                case Money: {
                    App.getMe().addGold(rewardAmount);
                }
                default: {
                    App.getMe().addGold(-1);
                }
                break;
            }
        } else if (reward instanceof FoodRecipesList recipe) {
            App.getMe().addFoodRecipes(recipe);
        } else if (reward instanceof Item item) {
            if (!App.getMe().getInventory().canAddItem(item, rewardAmount)) {
                App.getMe().getInventory().add(itemToPay, payAmount);//cancel Temp remove
                return new Result(false, "your inventory doesn't have space for " +
                        "reward failed to finish");
            }
            App.getMe().getInventory().add(item, rewardAmount);
            f.setLastDoneRequest(index);
            return new Result(true, "quest number: " + indexStr + " with npc: " +
                    npc.getType().getName() + " is done successfully");
        }
        return new Result(false, "bug happened in finish request type is incorrect");

    }
}
