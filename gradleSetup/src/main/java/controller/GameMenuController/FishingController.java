package controller.GameMenuController;

import controller.CommandController;
import model.App;
import model.Enums.TileType;
import model.GameObject.ArtesianMachine;
import model.Player;
import model.Result;
import model.items.Item;
import model.items.Tool;

import java.util.regex.Matcher;

public class FishingController extends CommandController {
    public static Result fishing(Matcher matcher) {
        String fishingPoleName = matcher.group(1).trim();
        Item item = App.getMe().getInventory().findItemByName(fishingPoleName);
        if (item == null) {
            return new Result(false , "you don't have a this fishing pole");
        }
        if (App.getMe().getCurrentItem() != item){
            return new Result(false , "you do");
        }
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) {
                if (App.getMe().getCurrentGameLocation().getTileByPosition(App.getMe().getPosition().getX() - 1 + i, App.getMe().getPosition().getY() - 1 + j).getTileType() == TileType.Water) {
                    ((Tool) App.getMe().getCurrentItem()).use(App.getMe().getCurrentGameLocation().getTileByPosition(App.getMe().getPosition().getX() - 1 + i, App.getMe().getPosition().getY() - 1 + j));
                    return new Result(true , "you have use your fishing pole");
                }
            }
        }

        return new Result(false , "you aren't near any water tile");
    }

}
