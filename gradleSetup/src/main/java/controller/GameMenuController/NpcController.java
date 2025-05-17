package controller.GameMenuController;

import controller.CommandController;
import model.App;
import model.Enums.NpcDialogs.NpcPrompt;
import model.Enums.NpcDialogs.RobinPrompt;
import model.Enums.WeatherAndTime.WeatherType;
import model.GameObject.ArtesianMachine;
import model.GameObject.NPC.DeepSeekApiChat;
import model.GameObject.NPC.NPC;
import model.GameObject.NPC.NpcFriendship;
import model.MapModule.GameMap;
import model.Player;
import model.Result;
import model.items.Item;

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

        DeepSeekApiChat deepSeek = new DeepSeekApiChat();
        String meet = deepSeek.generateDialogue(outPrompt);
        player.setLastMeetedNpc(npc);
        return new Result(true, meet);
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
