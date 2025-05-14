package controller.GameMenuController.ShopMenuControllers;

import model.Enums.Registery.StoreType;
import model.GameObject.NPC.NpcProduct;
import model.Result;

public class StardropSaloonController implements ShopController {
    @Override
    public Result showAllProducts(){
        StringBuilder builder = new StringBuilder();
        for (NpcProduct product : StoreType.SALOON.getProducts()) {
            builder.append("name :\t'").append(product.getItem().getName()).append("'\tprice:")
                    .append(product.getPrice())
                    .append("\n----------------------\n");
        }
        return new Result(true, builder.toString());
    }

    @Override
    public Result ShowAllAvailableProducts() {
        StringBuilder builder = new StringBuilder();
        for (NpcProduct product : StoreType.SALOON.getProducts()) {
            if(product.getRemainingStock() > 0){
                builder.append("name :\t'").append(product.getItem().getName()).append("'\tprice:")
                        .append(product.getPrice()).append("\nstock: ")
                        .append(product.getRemainingStock()).append("out of ")
                        .append(product.getDailyStock()).append("remained.")
                        .append("\n----------------------\n");
            }
        }
        return new Result(true, builder.toString());
    }
}
