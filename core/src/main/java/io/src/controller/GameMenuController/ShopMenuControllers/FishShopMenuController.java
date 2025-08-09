package io.src.controller.GameMenuController.ShopMenuControllers;

import io.src.model.App;
import io.src.model.MapModule.Buildings.FishShop;
import io.src.model.Result;

import java.util.regex.Matcher;

public class FishShopMenuController implements ShopController {
    public static Result showAllProducts() {
        return ShopController.showAllProducts(
                App.getCurrentUser().getCurrentGame().
                        findStoreByClass(FishShop.class).getDailyProductList());
    }

    public static Result showAllAvailableProducts() {
        return ShopController.showAllAvailableProducts(
                App.getCurrentUser().getCurrentGame().
                        findStoreByClass(FishShop.class).getDailyProductList());
    }

    public static Result PurchaseProduct(Matcher matcher) {
        return ShopController.purchaseProductFromList(matcher.group(1),matcher.group(2),
                App.getCurrentUser().getCurrentGame().
                        findStoreByClass(FishShop.class).getDailyProductList());
    }

    public static Result ExitShop(){
        return ShopController.exitShopMenu(FishShop.class);
    }
}
