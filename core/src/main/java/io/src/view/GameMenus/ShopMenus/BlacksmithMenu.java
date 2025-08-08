package io.src.view.GameMenus.ShopMenus;

import io.src.controller.GameMenuController.ShopMenuControllers.BlacksmithMenuController;
import io.src.model.Enums.commands.GameCommands.StoreCommands;
import io.src.model.Enums.commands.GameCommands.ToolCommands;
import io.src.model.Result;
import io.src.view.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class BlacksmithMenu implements AppMenu {
    @Override
    public Result check(Scanner scanner, String cmd) {
        Matcher matcher;
        if ((StoreCommands.ShowAllProduct.getMatcher(cmd)) != null) {
            return BlacksmithMenuController.showAllProducts();
        } else if ((StoreCommands.ShowAllAvailableProducts.getMatcher(cmd)) != null) {
            return BlacksmithMenuController.showAllAvailableProducts();
        } else if ((matcher = ToolCommands.toolsUpgrade.getMatcher(cmd)) != null) {
            return BlacksmithMenuController.upgradeTools(matcher.group(1).trim());
        } else if ((matcher = StoreCommands.PurchaseProducts.getMatcher(cmd)) != null) {
            return BlacksmithMenuController.PurchaseProduct(matcher);
        } else if (cmd.equalsIgnoreCase("exit")) {
            return BlacksmithMenuController.ExitShop();
        } else {
            return new Result(false, "invalid command");
        }
    }
}

