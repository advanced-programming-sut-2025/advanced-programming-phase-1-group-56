
package controller.GameMenuController.ShopMenuControllers;

import model.App;
import model.Enums.Stores.BlackSmithProducts;
import model.Enums.commands.GameCommands.ShopCommands;
import model.GameObject.NPC.NpcProduct;
import model.Player;
import model.Result;
import model.items.Item;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class BlacksmithMenuController implements ShopController {
    @Override
    public Result showAllProducts() {
        return ShopController.showAllProducts(BlackSmithProducts.getProducts(BlackSmithProducts.class));
    }

    @Override
    public Result ShowAllAvailableProducts() {
        StringBuilder builder = new StringBuilder();
        for (BlackSmithProducts products : BlackSmithProducts.values()) {
            NpcProduct product = products.getProduct();
            if (product.getRemainingStock() > 0) {
                if(product.getSaleable() instanceof Item ) {
                    builder.append("name :\t'").append(product.getName())
                            .append("'\n\tprice: ").append(product.getPrice())
                            .append("\n\tstock: ").append(product.getRemainingStock())
                            .append("out of ").append(product.getDailyStock()).append("remained.")
                            .append("\n----------------------\n");
                }
            }
        }
        return new Result(true, builder.toString());
    }
    @Override
    public Result PurchaseProduct(Matcher matcher) {
        return ShopController.PurchaseProductFromList(matcher,BlackSmithProducts.getProducts(BlackSmithProducts.class));
    }

    public Result UpgradeTools(String toolName) {
        return null;
    }
}
