package io.src.view.GameMenus.ShopMenus;

import io.src.controller.GameMenuController.ShopMenuControllers.CarpenterMenuController;
import io.src.model.Enums.Buildings.BuildingType;
import io.src.model.Enums.commands.GameCommands.HusbandryCommands;
import io.src.model.Enums.commands.GameCommands.StoreCommands;
import io.src.model.Result;
import io.src.view.AppMenu;

import java.util.Scanner;
import java.util.regex.Matcher;

public class CarpentersShopMenu implements AppMenu {
    @Override
    public Result check(Scanner scanner, String cmd) {
        Matcher matcher;
        if ((StoreCommands.ShowAllProduct.getMatcher(cmd)) != null) {
            return CarpenterMenuController.showAllProducts();
        } else if ((StoreCommands.ShowAllAvailableProducts.getMatcher(cmd)) != null) {
            return CarpenterMenuController.showAllAvailableProducts();
        } else if ((matcher = StoreCommands.PurchaseProducts.getMatcher(cmd)) != null) {
            return CarpenterMenuController.PurchaseProduct(matcher);
        } else if (cmd.equalsIgnoreCase("exit")) {
            return CarpenterMenuController.ExitShop();
        } else if ((matcher = HusbandryCommands.BuildABuilding.getMatcher(cmd)) != null) {
            //return CarpenterMenuController.BuildABuilding(((BuildingType)selectedProduct.));
            return new Result(false,"this command has been deleted from terminal logic!");
        }else
        {
            return new Result(false,"invalid command");
        }
    }
}
