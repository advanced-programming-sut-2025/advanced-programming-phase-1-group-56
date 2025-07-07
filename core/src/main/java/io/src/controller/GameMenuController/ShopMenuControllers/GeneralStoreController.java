package io.src.controller.GameMenuController.ShopMenuControllers;

import io.src.model.App;
import io.src.model.GameObject.NPC.NpcProduct;
import io.src.model.MapModule.Buildings.PierresGeneralStore;
import io.src.model.Result;

import java.util.regex.Matcher;

public class GeneralStoreController implements ShopController {
    public static Result showAllProducts() {
        return ShopController.showAllProducts(
                App.getCurrentUser().getCurrentGame().
                        findStoreByClass(PierresGeneralStore.class).getDailyProductList());
    }

    public static Result showAllAvailableProducts() {
        return ShopController.showAllAvailableProducts(
                App.getCurrentUser().getCurrentGame().
                        findStoreByClass(PierresGeneralStore.class).getDailyProductList());
    }

    public static Result PurchaseProduct(Matcher matcher) {
        return ShopController.purchaseProductFromList(matcher,
                App.getCurrentUser().getCurrentGame().
                        findStoreByClass(PierresGeneralStore.class).getDailyProductList());
    }

    public static Result ExitShop(){
        return ShopController.exitShopMenu(PierresGeneralStore.class);
    }
}
