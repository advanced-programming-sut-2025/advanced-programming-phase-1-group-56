package controller.GameMenuController;

import controller.CommandController;
import model.App;
import model.Enums.Items.EtcType;
import model.Enums.Items.SeedType;
import model.Enums.Recepies.FoodRecipesList;
import model.GameObject.NPC.NPC;
import model.GameObject.NPC.NpcFriendship;
import model.GameObject.NPC.NpcRequest;
import model.Result;
import model.items.Etc;
import model.items.Item;
import model.items.Saleable;
import model.items.Seed;

public class NpcController extends CommandController {
    public static Result meetNPC() {
        return null;
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
        boolean isFavorite = npc.getType().getFavoriteItems().contains(item);
        App.getMe().getInventory().remove(item, 1);
        friendship.setHasGiftedToday(true);

        if (!friendship.isHasGiftedToday()) {
            if (isFavorite) {
                friendship.addXp(+200);
                return new Result(true, "tnx for gifting " + npc.getType().getName() +
                        " it's favorite item");
            } else {
                friendship.addXp(+50);
                return new Result(true, "tnx for gifting " + npc.getType().getName());
            }
        }
        return new Result(true, "gift sent successfully...");
    }

    public static Result manageFriendshipNPCList() {
        StringBuilder builder = new StringBuilder();
        builder.append("your friendships:\n");
        for (NpcFriendship f : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getNpcFriendShips()) {
            builder.append("with Npc:").append(f.getPlayer().getUser().getName()).append("\n")
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
            int IndexOfActiveReq = f.getLastActiveRequest() - 1;
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
        NPC npc = App.getMe().getLastMeetedNPC();

        if (npc == null) {
            return new Result(false, "You haven't met a npc recently");
        } else if (!App.getMe().getPosition().isNear(npc.getPosition(), 1)) {
            return new Result(false, "You have to be near npc:" + npc.getPosition().toString());
        }

        builder.append("your active Quest with npc:").append(npc.getType().getName()).append("\n");
        NpcFriendship f = npc.findFriendshipByPlayer(App.getMe());
        int IndexOfActiveReq = f.getLastActiveRequest() - 1;
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
        StringBuilder builder = new StringBuilder();
        NPC npc = App.getMe().getLastMeetedNPC();
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
            return new Result(false, "you dont have enough item to finish the quest");
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
