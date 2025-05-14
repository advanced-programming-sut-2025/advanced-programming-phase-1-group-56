
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
        StringBuilder builder = new StringBuilder();
        for (BlackSmithProducts products : BlackSmithProducts.values()) {
            NpcProduct product = products.getProduct();
            builder.append("Name :\t'").append(product.getName())
                    .append((product.getSaleable() instanceof Item)?"' (item)":"' upgrade")
                    .append("\n\tDescription : '").append(product.getDescription())
                    .append("'\n\tPrice: ").append(product.getPrice())
                    .append("\n----------------------\n");
        }
        return new Result(true, builder.toString());
    }

    @Override
    public Result ShowAllAvailableProducts() {
        StringBuilder builder = new StringBuilder();
        for (BlackSmithProducts products : BlackSmithProducts.values()) {
            NpcProduct product = products.getProduct();
            if (product.getRemainingStock() > 0) {
                if(product.getSaleable() instanceof Item) {

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
        String productName = matcher.group(1);
        String amountStr = matcher.group(2);
        int amount;
        if (amountStr == null) {
            amount = 1;
        } else {
            try {
                amount = Integer.parseInt(amountStr.trim());
            } catch (NumberFormatException e) {
                return new Result(false, e.getMessage());
            }
        }
        ArrayList<NpcProduct> list = BlackSmithProducts.getProducts(BlackSmithProducts.class);
        NpcProduct productToBuy = ShopController.findProductByName(list, productName);
        Player me = App.getCurrentUser().getCurrentGame().getCurrentPlayer();
        if (productToBuy == null) {
            return new Result(false, productName + " does not exist in the store");
        }
        if (productToBuy.getSaleable() instanceof Item item) {
            if (productToBuy.getRemainingStock() == 0) {
                return new Result(false, productName + " is ran out of stock,comeback tomorrow...");
            } else if (productToBuy.getRemainingStock() < amount) {
                return new Result(false, " only " + productToBuy.getRemainingStock() + "of " + productName
                        + " has remained..there is not enough amount you want..");
            } else if (productToBuy.getPrice() * amount > me.getGold()) {
                return new Result(false, "you can't afford buy this\n" +
                        "you have :" + me.getGold() + " gold,but you need: " + productToBuy.getPrice() * amount);
            }
            productToBuy.setRemainingStock(-amount);
            me.getInventory().add(item, amount);
            me.addGold(-amount * productToBuy.getPrice());
            return new Result(true, "purchase successful..");
        }
        //upgrading tool
        else{
            return new Result(false,"you can only buy items with this command for upgrading tools"+
                    " you should try Command 'tools upgrade <tool_name>'\n");
        }
    }

    public Result UpgradeTools(String toolName) {
        return null;
    }
}
