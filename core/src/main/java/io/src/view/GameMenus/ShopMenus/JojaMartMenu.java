package io.src.view.GameMenus.ShopMenus;

import io.src.controller.GameMenuController.ShopMenuControllers.JojaMartShopMenuController;
import io.src.model.Enums.commands.GameCommands.StoreCommands;
import io.src.view.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class JojaMartMenu implements AppMenu {
    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;
        if((StoreCommands.ShowAllProduct.getMatcher(input))!=null) {
            System.out.println(JojaMartShopMenuController.showAllProducts().message());
        } else if((StoreCommands.ShowAllAvailableProducts.getMatcher(input))!=null) {
            System.out.println(JojaMartShopMenuController.showAllAvailableProducts().message());
        } else if((matcher = StoreCommands.PurchaseProducts.getMatcher(input))!=null) {
            System.out.println(JojaMartShopMenuController.PurchaseProduct(matcher).message());
        } else if(input.equalsIgnoreCase("exit")) {
            System.out.println(JojaMartShopMenuController.ExitShop().message());
        } else {
            System.out.println("invalid command");
        }
    }
}
