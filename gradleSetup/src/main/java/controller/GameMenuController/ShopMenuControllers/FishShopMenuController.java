package controller.GameMenuController.ShopMenuControllers;

import model.App;
import model.MapModule.Buildings.FishShop;
import model.MapModule.Buildings.PierresGeneralStore;
import model.Result;
import model.items.Fish;

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
        return ShopController.purchaseProductFromList(matcher,
                App.getCurrentUser().getCurrentGame().
                        findStoreByClass(FishShop.class).getDailyProductList());
    }
}
