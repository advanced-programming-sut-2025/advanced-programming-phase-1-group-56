package io.src.view.GameMenus.ShopMenus;

import io.src.controller.GameMenuController.HusbandryController;
import io.src.controller.GameMenuController.ShopMenuControllers.CarpenterMenuController;
import io.src.model.Enums.commands.GameCommands.HusbandryCommands;
import io.src.model.Enums.commands.GameCommands.StoreCommands;
import io.src.view.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class CarpentersShopMenu implements AppMenu {
    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;
        if ((StoreCommands.ShowAllProduct.getMatcher(input)) != null) {
            System.out.println(CarpenterMenuController.showAllProducts().message());
        } else if ((StoreCommands.ShowAllAvailableProducts.getMatcher(input)) != null) {
            System.out.println(CarpenterMenuController.showAllAvailableProducts().message());
        } else if ((matcher = StoreCommands.PurchaseProducts.getMatcher(input)) != null) {
            System.out.println(CarpenterMenuController.PurchaseProduct(matcher).message());
        } else if (input.equalsIgnoreCase("exit")) {
            System.out.println(CarpenterMenuController.ExitShop().message());
        } else if ((matcher = HusbandryCommands.BuildABuilding.getMatcher(input)) != null) {
            System.out.println(CarpenterMenuController.BuildABuilding(matcher).message());
        }
        {
            System.out.println("invalid command");
        }
    }
}
