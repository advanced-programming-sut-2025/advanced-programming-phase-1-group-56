package io.src.controller.GameMenuController;

import io.src.controller.CommandController;
import io.src.model.App;
import io.src.model.Enums.Items.EtcType;
import io.src.model.MapModule.Buildings.GreenHouse;
import io.src.model.MapModule.GameLocations.Farm;
import io.src.model.Player;
import io.src.model.Result;
import io.src.model.items.Etc;

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
