package view.GameMenus;

import controller.GameMenuController.TradeController;
import model.App;
import model.Enums.Menu;
import model.Enums.commands.GameCommands.TradeCommands;
import view.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenu implements AppMenu {
    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;
        if ((matcher = TradeCommands.tradeWithPlayer.getMatcher(input)) != null) {
            System.out.println(TradeController.makeNewTrade(matcher).message());
        } else if ((matcher = TradeCommands.tradeResponse.getMatcher(input)) != null) {
            System.out.println(TradeController.tradeResponse(matcher).message());
        } else if ( (TradeCommands.tradeList.getMatcher(input)) != null) {
            System.out.println(TradeController.showTradeList());
        } else if ((TradeCommands.tradeHistory.getMatcher(input)) != null) {
            System.out.println(TradeController.showTradeHistory());
        } else if (input.equalsIgnoreCase("exit")) {
            App.setCurrentMenu(Menu.gameMenu);
            System.out.println("exiting tradeMenu redirecting to farm...");
        } else {
            System.out.println("Invalid command!");
        }
    }

}
