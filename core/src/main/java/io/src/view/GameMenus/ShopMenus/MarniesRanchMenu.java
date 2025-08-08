package io.src.view.GameMenus.ShopMenus;

import io.src.controller.GameMenuController.ShopMenuControllers.MarniesRanchController;
import io.src.model.Enums.commands.GameCommands.HusbandryCommands;
import io.src.model.Enums.commands.GameCommands.StoreCommands;
import io.src.model.Result;
import io.src.view.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MarniesRanchMenu implements AppMenu {
    @Override
    public Result check(Scanner scanner, String cmd) {
        Matcher matcher;
        if ((StoreCommands.ShowAllProduct.getMatcher(cmd)) != null) {
            return MarniesRanchController.showAllProducts();
        } else if ((StoreCommands.ShowAllAvailableProducts.getMatcher(cmd)) != null) {
            return MarniesRanchController.showAllAvailableProducts();
        } else if ((matcher = StoreCommands.PurchaseProducts.getMatcher(cmd)) != null) {
            return MarniesRanchController.PurchaseProduct(matcher);
        } else if ((matcher = HusbandryCommands.buyAnimal.getMatcher(cmd)) != null) {
            return MarniesRanchController.buyAnimal(matcher);
        } else if (cmd.equalsIgnoreCase("exit")) {
            return MarniesRanchController.ExitShop();
        } else {
            return new Result(false, "invalid command");
        }
    }
}
