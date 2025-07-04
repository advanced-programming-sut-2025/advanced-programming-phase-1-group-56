package io.src.view.GameMenus.ShopMenus;

import io.src.controller.GameMenuController.ShopMenuControllers.MarniesRanchController;
import io.src.model.Enums.commands.GameCommands.HusbandryCommands;
import io.src.model.Enums.commands.GameCommands.StoreCommands;
import io.src.view.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MarniesRanchMenu implements AppMenu {
    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;
        if((StoreCommands.ShowAllProduct.getMatcher(input))!=null) {
            System.out.println(MarniesRanchController.showAllProducts().message());
        } else if((StoreCommands.ShowAllAvailableProducts.getMatcher(input))!=null) {
            System.out.println(MarniesRanchController.showAllAvailableProducts().message());
        } else if((matcher = StoreCommands.PurchaseProducts.getMatcher(input))!=null) {
            System.out.println(MarniesRanchController.PurchaseProduct(matcher).message());
        } else if((matcher = HusbandryCommands.buyAnimal.getMatcher(input))!=null) {
            System.out.println(MarniesRanchController.buyAnimal(matcher).message());
        } else if(input.equalsIgnoreCase("exit")) {
            System.out.println(MarniesRanchController.ExitShop().message());
        } else {
            System.out.println("invalid command");
        }
    }
}
