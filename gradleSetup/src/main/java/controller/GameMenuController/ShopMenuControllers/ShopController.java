package controller.GameMenuController.ShopMenuControllers;

import model.App;
import model.Enums.BackPackType;
import model.Enums.Menu;
import model.Enums.Recepies.CraftingRecipesList;
import model.Enums.Recepies.FoodRecipesList;
import model.Enums.WeatherAndTime.Seasons;
import model.GameObject.NPC.NpcProduct;
import model.MapModule.Buildings.Store;
import model.MapModule.Buildings.TheSaloonStardrop;
import model.MapModule.Position;
import model.Player;
import model.Result;
import model.items.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

public interface ShopController {

    static NpcProduct findProductByName(List<NpcProduct> products, String productName) {
        for (NpcProduct product : products) {
            if (product.getSaleable().getName().equals(productName)) {
                return product;
            }
        }
        return null;
    }

    static Result purchaseProductFromList(Matcher matcher, ArrayList<NpcProduct> list) {
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
        NpcProduct productToBuy = ShopController.findProductByName(list, productName);
        Player me = App.getCurrentUser().getCurrentGame().getCurrentPlayer();

        if (productToBuy == null) {
            return new Result(false, productName + " does not exist in the store");
        }
        if (amount <= 0) {
            return new Result(false, "Invalid amount: must be greater than zero.");
        }

        int sumPrice;
        if (productToBuy.getOutOfSeasonPrice() == -1 ||
                Arrays.stream(productToBuy.getSeasons()).toList().contains(
                        App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getSeason())) {
            sumPrice = amount * productToBuy.getPrice();
        } else {
            sumPrice = amount * productToBuy.getOutOfSeasonPrice();
        }

        if (productToBuy.getRemainingStock() == 0) {
            return new Result(false, productName + " is ran out of stock,comeback tomorrow...");
        } else if (productToBuy.getRemainingStock() < amount) {
            return new Result(false, " only " + productToBuy.getRemainingStock() + "of " + productName
                    + " has remained..there is not enough amount you want..");
        } else if (productToBuy.getPrice() * amount > me.getGold()) {
            return new Result(false, "you can't afford buy this\n" +
                    "you have :" + me.getGold() + " gold,but you need: " + productToBuy.getPrice() * amount);
        }

        if (productToBuy.getSaleable() instanceof Item item) {
            //item
            me.addGold(-sumPrice);
            productToBuy.setRemainingStock(-amount);
            me.getInventory().add(item, amount);
            return new Result(true, "purchase item successful..");
        } else if (productToBuy.getSaleable() instanceof CraftingRecipesList recipe ||
                productToBuy.getSaleable() instanceof FoodRecipesList food) {
            //recipe
            if (amount > 1) {
                return new Result(false, "you dont need to buy more than one crafting recipe.");
            }
            me.addGold(-sumPrice);
            productToBuy.setRemainingStock(-amount);
            if (productToBuy.getSaleable() instanceof CraftingRecipesList recipe) {
                me.addToolRecipes(recipe);
            } else if (productToBuy.getSaleable() instanceof FoodRecipesList food) {
                me.addFoodRecipes(food);
            }
            return new Result(true, "purchase recipe successful..");
        }else if(productToBuy.getSaleable() instanceof BackPackType backPackType) {
            if(me.getCurrentBackpack().getNext()==null){
                return new Result(false, "your backpack is already max");
            }
            else if(amount>1){
                return new Result(false, "you can't buy more than one back pack");
            }
            else if(me.getCurrentBackpack().getNext()!=backPackType){
                return new Result(false, "you can't buy worse back pack or jump from initial backpack" +
                        "to deluxe backpack");
            }
            me.addGold(-sumPrice);
            productToBuy.setRemainingStock(-amount);
            me.setCurrentBackpack(backPackType);
            return new Result(true,"upgrade your backpack to :" + backPackType.getName() +
                    " successfully..");
        } else {
            return new Result(false, "you can only buy items with this command");
        }
    }

    static Result showAllProducts(ArrayList<NpcProduct> list) {
        StringBuilder builder = new StringBuilder();
        for (NpcProduct product : list) {
            builder.append("Name :\t'").append(product.getName())
                    .append((product.getSaleable() instanceof Item) ? "' (item)" : "' upgrade")
                    .append("\n\tDescription : '").append(product.getDescription())
                    .append("'\n\tPrice: ").append(product.getPrice())
                    .append("\n----------------------\n");
        }
        return new Result(true, builder.toString());
    }

    static Result showAllAvailableProducts(ArrayList<NpcProduct> list) {
        StringBuilder builder = new StringBuilder();
        for (NpcProduct product : list) {
            if (product.getRemainingStock() <= 0)
                continue;
            Seasons currSeason = App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getSeason();
            if (!Arrays.stream(product.getSeasons()).toList().contains(currSeason)) {
                continue;
            }
            if (product.getSaleable() instanceof Item) {
                builder.append("name :\t'").append(product.getName())
                        .append("'\n\tprice: ").append(product.getPrice())
                        .append("\n\tstock: ").append(product.getRemainingStock())
                        .append("out of ").append(product.getDailyStock()).append("remained.")
                        .append("\n----------------------\n");


            }
        }
        return new Result(true, builder.toString());
    }

    static Result exitShopMenu(Class <? extends Store> store) {
        App.setCurrentMenu(Menu.gameMenu);
        Player me = App.getMe();
        Store shop = App.getCurrentUser().getCurrentGame().findStoreByClass(store);
        Position posToSet = new Position(shop.getDoorPosition().getX(),shop.getDoorPosition().getY()+2);
        me.setCurrentGameLocation(App.getCurrentUser().getCurrentGame().getGameMap().getPelikanTown());
        me.setPosition(posToSet);
        return new Result(true,"exiting " + shop.getName() + "shop...\n" +
                "redirecting to the town...");
    }

}
