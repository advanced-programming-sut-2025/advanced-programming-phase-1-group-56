package controller.GameMenuController;

import controller.CommandController;
import model.App;
import model.Enums.Direction;
import model.MapModule.Tile;
import model.Player;
import model.Result;
import model.Slot;
import model.items.Tool;

public class ToolsController extends CommandController {
    public static Result equipTool(String toolName) {
        Player player = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        if (player.getInventory().findItemByName(toolName) == null) {
            return new Result(false, "you dont have such tool");
        }
        player.setCurrentItem(player.getInventory().findItemByName(toolName));
        return new Result(true, "now you equip the tool");
    }

    public static Result showCurrentTools() {
        Player player = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        if (player.getCurrentItem() instanceof Tool) {
            return new Result(true, player.getCurrentItem().getName());
        }
        return new Result(false, "your current item is not instance of tool");
    }

    public static Result showToolsAvailable() {
        Player player = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        StringBuilder result = new StringBuilder();
        for (Slot slot : player.getInventory().getSlots()) {
            if (slot.getItem() instanceof Tool) {
                result.append(slot.getItem().getName()).append("\n");
            }
        }
        return new Result(true, result.toString());
    }

    public static Result upgradeTools(String toolName) {
        Player player = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        Tool tool = (Tool) player.getInventory().findItemByName(toolName);
        if (tool == null) {
            return new Result(false, "you dont have such tool");
        }
        Tool.upgrade(tool);
        return new Result(true, "the tool is upgraded");
    }

    public static Result useTools(String direction) {
        Player player = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        Tile destTile = player.getCurrentGameLocation().getTileByPosition(player.getPosition().getX(), player.getPosition().getY());
        Direction dir;
        if ((dir = CraftingController.getDirectionFromString(direction)) == null) {
            return new Result(false, "this direction does not exist!");
        }
        switch (dir) {
            case Direction.UP: {
                destTile = player.getCurrentGameLocation().getTileByPosition(destTile.getPosition().getX(), destTile.getPosition().getY() - 1);
            }
            break;
            case Direction.DOWN: {
                destTile = player.getCurrentGameLocation().getTileByPosition(destTile.getPosition().getX(), destTile.getPosition().getY() + 1);
            }
            break;
            case Direction.LEFT: {
                destTile = player.getCurrentGameLocation().getTileByPosition(destTile.getPosition().getX() - 1, destTile.getPosition().getY());
            }
            break;
            case Direction.RIGHT: {
                destTile = player.getCurrentGameLocation().getTileByPosition(destTile.getPosition().getX() + 1, destTile.getPosition().getY());
            }
            break;
            case Direction.UPLEFT: {
                destTile = player.getCurrentGameLocation().getTileByPosition(destTile.getPosition().getX() - 1, destTile.getPosition().getY() - 1);
            }
            break;
            case Direction.DOWNRIGHT: {
                destTile = player.getCurrentGameLocation().getTileByPosition(destTile.getPosition().getX() + 1, destTile.getPosition().getY() + 1);
            }
            break;
            case Direction.UPRIGHT: {
                destTile = player.getCurrentGameLocation().getTileByPosition(destTile.getPosition().getX() + 1, destTile.getPosition().getY() - 1);
            }
            break;
            case Direction.DOWNLEFT: {
                destTile = player.getCurrentGameLocation().getTileByPosition(destTile.getPosition().getX() - 1, destTile.getPosition().getY() + 1);
            }
            break;
            default: {
                return new Result(false, "this direction does not exist!");
            }
        }
        if (player.getCurrentItem() instanceof Tool) {
            ((Tool) player.getCurrentItem()).use(destTile);
            return new Result(true, "you have used the tool");
        }
        return new Result(false, "your current item is not instance of tool");
    }

}
