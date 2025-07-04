package io.src.view.GameMenus.ShopMenus;

import io.src.controller.GameMenuController.ShopMenuControllers.FishShopMenuController;
import io.src.model.Enums.commands.GameCommands.StoreCommands;
import io.src.view.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class FishShopMenu implements AppMenu {
    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;
        if((StoreCommands.ShowAllProduct.getMatcher(input))!=null) {
            System.out.println(FishShopMenuController.showAllProducts().message());
        } else if((StoreCommands.ShowAllAvailableProducts.getMatcher(input))!=null) {
            System.out.println(FishShopMenuController.showAllAvailableProducts().message());
        } else if((matcher = StoreCommands.PurchaseProducts.getMatcher(input))!=null) {
            System.out.println(FishShopMenuController.PurchaseProduct(matcher).message());
        } else if(input.equalsIgnoreCase("exit")) {
            System.out.println(FishShopMenuController.ExitShop().message());
        } else {
            System.out.println("invalid command");
        }
    }
}
