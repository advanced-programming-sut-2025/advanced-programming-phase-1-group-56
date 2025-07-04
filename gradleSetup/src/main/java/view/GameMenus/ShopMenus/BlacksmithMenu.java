package view.GameMenus.ShopMenus;

import controller.GameMenuController.ShopMenuControllers.BlacksmithMenuController;
import controller.GameMenuController.ToolsController;
import model.Enums.commands.GameCommands.StoreCommands;
import model.Enums.commands.GameCommands.ToolCommands;
import view.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class BlacksmithMenu implements AppMenu {
    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;
        if ((StoreCommands.ShowAllProduct.getMatcher(input)) != null) {
            System.out.println(BlacksmithMenuController.showAllProducts().message());
        } else if ((StoreCommands.ShowAllAvailableProducts.getMatcher(input)) != null) {
            System.out.println(BlacksmithMenuController.showAllAvailableProducts().message());
        } else if ((matcher = ToolCommands.toolsUpgrade.getMatcher(input)) != null) {
            System.out.println(BlacksmithMenuController.upgradeTools(matcher.group(1).trim()));
        } else if ((matcher = StoreCommands.PurchaseProducts.getMatcher(input)) != null) {
            System.out.println(BlacksmithMenuController.PurchaseProduct(matcher).message());
        } else if (input.equalsIgnoreCase("exit")) {
            System.out.println(BlacksmithMenuController.ExitShop().message());
        } else {
            System.out.println("invalid command");
        }
    }
}

