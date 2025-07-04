package io.src.view.GameMenus.ShopMenus;

import io.src.controller.GameMenuController.ShopMenuControllers.StardropSaloonController;
import io.src.model.Enums.commands.GameCommands.StoreCommands;
import io.src.view.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class TheSaloonStarDropMenu implements AppMenu {
    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;
        if ((StoreCommands.ShowAllProduct.getMatcher(input)) != null) {
            System.out.println(StardropSaloonController.showAllProducts().message());
        } else if ((StoreCommands.ShowAllAvailableProducts.getMatcher(input)) != null) {
            System.out.println(StardropSaloonController.showAllAvailableProducts().message());
        } else if ((matcher = StoreCommands.PurchaseProducts.getMatcher(input)) != null) {
            System.out.println(StardropSaloonController.PurchaseProduct(matcher).message());
        } else if (input.equalsIgnoreCase("exit")) {
            System.out.println(StardropSaloonController.ExitShop().message());
        } else {
            System.out.println("invalid command");
        }
    }
}
