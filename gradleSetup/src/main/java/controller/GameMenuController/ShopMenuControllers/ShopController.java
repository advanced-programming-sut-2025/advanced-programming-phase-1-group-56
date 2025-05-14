package controller.GameMenuController.ShopMenuControllers;

import model.GameObject.NPC.NpcProduct;
import model.Result;

import java.util.List;
import java.util.regex.Matcher;

public interface ShopController {
    Result showAllProducts();
    Result ShowAllAvailableProducts();
    Result PurchaseProduct(Matcher matcher);

    public static NpcProduct findProductByName(List<NpcProduct> products, String productName) {
        for (NpcProduct product : products) {
            if(product.getSaleable().getName().equals(productName)){
                return product;
            }
        }
        return null;
    }
}
