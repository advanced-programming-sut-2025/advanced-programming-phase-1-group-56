package io.src.view.GameMenus.ShopMenus;

import io.src.controller.GameMenuController.ShopMenuControllers.BlacksmithMenuController;
import io.src.controller.GameMenuController.ToolsController;
import io.src.model.Enums.commands.GameCommands.StoreCommands;
import io.src.model.Enums.commands.GameCommands.ToolCommands;
import io.src.view.AppMenu;

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

