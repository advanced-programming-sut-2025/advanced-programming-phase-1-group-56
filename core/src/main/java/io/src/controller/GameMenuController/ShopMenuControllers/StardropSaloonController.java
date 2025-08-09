package io.src.controller.GameMenuController.ShopMenuControllers;

import io.src.model.App;
import io.src.model.MapModule.Buildings.TheSaloonStardrop;
import io.src.model.Result;

import java.util.regex.Matcher;

public class StardropSaloonController implements ShopController {
    public static Result showAllProducts(){
        return ShopController.showAllProducts(
                App.getCurrentUser().getCurrentGame().findStoreByClass(TheSaloonStardrop.class).getDailyProductList());
    }

    public static Result showAllAvailableProducts() {
        return ShopController.showAllAvailableProducts(
                App.getCurrentUser().getCurrentGame().findStoreByClass(TheSaloonStardrop.class).getDailyProductList());
    }

    public static Result PurchaseProduct(Matcher matcher) {
        return ShopController.purchaseProductFromList(matcher.group(1),matcher.group(2),
                App.getCurrentUser().getCurrentGame().findStoreByClass(TheSaloonStardrop.class).getDailyProductList());
    }

    public static Result ExitShop(){
        return ShopController.exitShopMenu(TheSaloonStardrop.class);
    }
}
