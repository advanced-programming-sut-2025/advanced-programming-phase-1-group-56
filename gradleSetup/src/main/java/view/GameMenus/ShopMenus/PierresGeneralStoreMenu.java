package view.GameMenus.ShopMenus;

import controller.GameMenuController.ShopMenuControllers.BlacksmithMenuController;
import controller.GameMenuController.ShopMenuControllers.GeneralStoreController;
import model.Enums.commands.GameCommands.StoreCommands;
import view.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class PierresGeneralStoreMenu implements AppMenu {

    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;
        if((StoreCommands.ShowAllProduct.getMatcher(input))!=null) {
            System.out.println(GeneralStoreController.showAllProducts().message());
        } else if((StoreCommands.ShowAllAvailableProducts.getMatcher(input))!=null) {
            System.out.println(GeneralStoreController.showAllAvailableProducts().message());
        } else if((matcher = StoreCommands.PurchaseProducts.getMatcher(input))!=null) {
            System.out.println(GeneralStoreController.PurchaseProduct(matcher).message());
        } else if(input.equalsIgnoreCase("exit")) {
            System.out.println(GeneralStoreController.ExitShop().message());
        } else {
            System.out.println("invalid command");
        }
    }
}
