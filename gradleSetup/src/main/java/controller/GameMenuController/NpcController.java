package controller.GameMenuController;

import controller.CommandController;
import model.App;
import model.GameObject.NPC.NPC;
import model.Result;
import model.items.Item;

public class NpcController extends CommandController {
    public static Result meetNPC() {
        return null;
    }

    public static Result giftNPC(String npcName,String itemName) {
        NPC npc = App.getCurrentUser().getCurrentGame().getGameMap().getPelikanTown().getNpcByName(npcName.trim());
        if(npc == null) {
            return new Result(false, "NPC doesn't exist in town");
        }

        Item item = App.getMe().getInventory().findItemByName(itemName);
        if(item == null) {
            return new Result(false, "You don't have such item");
        }

    }

    public static Result manageFriendshipNPCList() {

        return null;
    }

    public static Result manageQuests() {

        return null;
    }

    public static Result finishingQuest() {

        return null;
    }
}
