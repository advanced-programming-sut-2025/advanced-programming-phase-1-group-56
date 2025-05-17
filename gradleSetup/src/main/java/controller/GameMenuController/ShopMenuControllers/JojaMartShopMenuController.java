package controller.GameMenuController.ShopMenuControllers;

import model.App;
import model.MapModule.Buildings.JojaMart;
import model.Result;

import java.util.regex.Matcher;

public class JojaMartShopMenuController implements ShopController {
    public static Result showAllProducts() {
        return ShopController.showAllProducts(
                App.getCurrentUser().getCurrentGame().
                        findStoreByClass(JojaMart.class).getDailyProductList());
    }

    public static Result showAllAvailableProducts() {
        return ShopController.showAllAvailableProducts(
                App.getCurrentUser().getCurrentGame().
                        findStoreByClass(JojaMart.class).getDailyProductList());
    }

    public static Result PurchaseProduct(Matcher matcher) {
        return ShopController.purchaseProductFromList(matcher,
                App.getCurrentUser().getCurrentGame().
                        findStoreByClass(JojaMart.class).getDailyProductList());
    }

    public static Result ExitShop(){
        return ShopController.exitShopMenu(JojaMart.class);
    }

}
