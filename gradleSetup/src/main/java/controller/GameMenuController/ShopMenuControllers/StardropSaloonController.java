package controller.GameMenuController.ShopMenuControllers;

import model.App;
import model.MapModule.Buildings.Store;
import model.MapModule.Buildings.TheSaloonStardrop;
import model.MapModule.Position;
import model.Player;
import model.Result;

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
        return ShopController.purchaseProductFromList(matcher,
                App.getCurrentUser().getCurrentGame().findStoreByClass(TheSaloonStardrop.class).getDailyProductList());
    }

    public static Result ExitShop(){
        return ShopController.exitShopMenu(TheSaloonStardrop.class);
    }
}
