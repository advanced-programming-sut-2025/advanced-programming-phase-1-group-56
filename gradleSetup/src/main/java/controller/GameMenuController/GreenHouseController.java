package controller.GameMenuController;

import controller.CommandController;
import model.App;
import model.Enums.Items.EtcType;
import model.Enums.Items.MineralItemType;
import model.MapModule.Buildings.GreenHouse;
import model.MapModule.GameLocations.Farm;
import model.Player;
import model.Result;
import model.items.Etc;

public class GreenHouseController extends CommandController {
    public static Result greenHouseBuild() {
        Player me = App.getMe();
        Farm farm = me.getPlayerFarm();
        GreenHouse greenHouse = farm.getGreenHouse();

        if (!greenHouse.isBroken()) {
            return new Result(false, "Greenhouse is already repaired.");
        }

        boolean hasEnoughWood = me.getInventory().countItem(new Etc(EtcType.WOOD)) >= 500;
        boolean hasEnoughMoney = me.getGold() >= 1000;

        if (!hasEnoughWood || !hasEnoughMoney) {
            return new Result(false, "You need 500 wood and 1000 gold to repair the greenhouse.");
        }

        me.getInventory().remove(new Etc(EtcType.WOOD), 500);
        me.addGold(-1000);

        greenHouse.setBroken(false);

        return new Result(true, "Greenhouse built successfully.");
    }

}
