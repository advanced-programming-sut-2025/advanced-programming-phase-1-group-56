package io.src.view.GameMenus.ShopMenus;

import io.src.controller.GameMenuController.ShopMenuControllers.GeneralStoreController;
import io.src.model.Enums.commands.GameCommands.StoreCommands;
import io.src.model.Result;
import io.src.view.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class PierresGeneralStoreMenu implements AppMenu {

    @Override
    public Result check(Scanner scanner, String cmd) {
        Matcher matcher;
        if((StoreCommands.ShowAllProduct.getMatcher(cmd))!=null) {
            return GeneralStoreController.showAllProducts();
        } else if((StoreCommands.ShowAllAvailableProducts.getMatcher(cmd))!=null) {
            return GeneralStoreController.showAllAvailableProducts();
        } else if((matcher = StoreCommands.PurchaseProducts.getMatcher(cmd))!=null) {
            return GeneralStoreController.PurchaseProduct(matcher);
        } else if(cmd.equalsIgnoreCase("exit")) {
            return GeneralStoreController.ExitShop();
        } else {
            return new Result(false,"invalid command");
        }
    }
}
