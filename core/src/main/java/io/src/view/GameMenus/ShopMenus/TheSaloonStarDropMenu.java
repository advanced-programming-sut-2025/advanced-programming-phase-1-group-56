package io.src.view.GameMenus.ShopMenus;

import io.src.controller.GameMenuController.ShopMenuControllers.StardropSaloonController;
import io.src.model.Enums.commands.GameCommands.StoreCommands;
import io.src.model.Result;
import io.src.view.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class TheSaloonStarDropMenu implements AppMenu {
    @Override
    public Result check(Scanner scanner, String cmd) {
        Matcher matcher;
        if ((StoreCommands.ShowAllProduct.getMatcher(cmd)) != null) {
            return StardropSaloonController.showAllProducts();
        } else if ((StoreCommands.ShowAllAvailableProducts.getMatcher(cmd)) != null) {
            return StardropSaloonController.showAllAvailableProducts();
        } else if ((matcher = StoreCommands.PurchaseProducts.getMatcher(cmd)) != null) {
            return StardropSaloonController.PurchaseProduct(matcher);
        } else if (cmd.equalsIgnoreCase("exit")) {
            return StardropSaloonController.ExitShop();
        } else {
            return new Result(false, "invalid command");
        }
    }
}
