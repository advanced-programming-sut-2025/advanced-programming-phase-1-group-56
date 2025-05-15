package controller.GameMenuController;

import controller.CommandController;
import model.App;
import model.MapModule.Tile;
import model.Player;
import model.Result;
import model.Slot;
import model.items.Tool;

public class ToolsController extends CommandController {
    public static Result equipTool(String toolName) {
        Player player = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        if (player.getInventory().findItemByName(toolName) == null){
            return new Result(false , "you dont have such tool");
        }
        player.setCurrentItem(player.getInventory().findItemByName(toolName));
        return new Result(true , "now you equip the tool");
    }

    public static Result showCurrentTools() {
        Player player = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        if (player.getCurrentItem() instanceof Tool){
            return new Result(true , player.getCurrentItem().getName());
        }
        return new Result(false , "your current item is not instance of tool");
    }

    public static Result showToolsAvailable() {
        Player player = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        StringBuilder result = new StringBuilder();
        for (Slot slot : player.getInventory().getSlots()) {
            if (slot.getItem() instanceof Tool){
                result.append(slot.getItem().getName()).append("\n");
            }
        }
        return new Result(true , result.toString());
    }

    public static Result upgradeTools(String toolName) {
        Player player = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        Tool tool = (Tool)player.getInventory().findItemByName(toolName);
        if (tool == null){
            return new Result(false , "you dont have such tool");
        }
        Tool.upgrade(tool);
        return new Result(true , "the tool is upgraded");
    }

    public static Result useTools(String direction) {
        Player player = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        Tile destTile = player.getCurrentGameLocation().getTileByPosition(player.getPosition().getX() , player.getPosition().getY());
        switch (direction){
            case "north": destTile = player.getCurrentGameLocation().getTileByPosition(destTile.getPosition().getX() , destTile.getPosition().getY()-1); break;
            case "south": destTile = player.getCurrentGameLocation().getTileByPosition(destTile.getPosition().getX() , destTile.getPosition().getY()+1); break;
            case "west": destTile = player.getCurrentGameLocation().getTileByPosition(destTile.getPosition().getX()-1 , destTile.getPosition().getY()); break;
            case "east": destTile = player.getCurrentGameLocation().getTileByPosition(destTile.getPosition().getX()+1 , destTile.getPosition().getY()); break;
            case "northwest": destTile = player.getCurrentGameLocation().getTileByPosition(destTile.getPosition().getX()-1 , destTile.getPosition().getY()-1); break;
            case "southeast": destTile = player.getCurrentGameLocation().getTileByPosition(destTile.getPosition().getX()+1 , destTile.getPosition().getY()+1); break;
            case "northeast": destTile = player.getCurrentGameLocation().getTileByPosition(destTile.getPosition().getX()+1 , destTile.getPosition().getY()-1); break;
            case "southwest":   destTile = player.getCurrentGameLocation().getTileByPosition(destTile.getPosition().getX()-1 , destTile.getPosition().getY()+1); break;
        }
        if (player.getCurrentItem() instanceof Tool){
            ((Tool) player.getCurrentItem()).use(destTile);
            return new Result(true , "you have used the tool");
        }
        return new Result(false , "your current item is not instance of tool");
    }

    public static Result manageUsingHoe() {

        return null;
    }

    public static Result manageUsingPickaxe() {

        return null;
    }

    public static Result manageUsingAxe() {

        return null;
    }

    public static Result manageUsingWateringCan() {

        return null;
    }

    public static Result manageUsingFishingPole() {

        return null;
    }

    public static Result manageUsingScythe() {

        return null;
    }

    public static Result manageUsingMilkPail() {

        return null;
    }

    public static Result manageUsingShear() {

        return null;
    }

    public static Result manageUsingBackPack() {

        return null;
    }

    public static Result manageUsingTrashCan() {

        return null;
    }


}
