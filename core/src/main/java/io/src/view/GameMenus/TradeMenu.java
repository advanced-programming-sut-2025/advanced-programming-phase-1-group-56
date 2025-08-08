package io.src.view.GameMenus;

import io.src.controller.GameMenuController.TradeController;
import io.src.model.App;
import io.src.model.Enums.Menu;
import io.src.model.Enums.commands.GameCommands.TradeCommands;
import io.src.model.Result;
import io.src.view.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenu implements AppMenu {
    @Override
    public Result check(Scanner scanner, String cmd) {
        Matcher matcher;
        if ((matcher = TradeCommands.tradeWithPlayer.getMatcher(cmd)) != null) {
            return TradeController.makeNewTrade(matcher);
        } else if ((matcher = TradeCommands.tradeResponse.getMatcher(cmd)) != null) {
            return TradeController.tradeResponse(matcher);
        } else if ((TradeCommands.tradeList.getMatcher(cmd)) != null) {
            return TradeController.showTradeList();
        } else if ((TradeCommands.tradeHistory.getMatcher(cmd)) != null) {
            return TradeController.showTradeHistory();
        } else if (cmd.equalsIgnoreCase("exit")) {
            App.setCurrentMenu(Menu.gameMenu);
            return new Result(true, "exiting tradeMenu redirecting to farm...");
        } else {
            return new Result(false, "invalid command");
        }
    }

}
