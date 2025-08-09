package io.src.controller.GameMenuController.ShopMenuControllers;

import io.src.model.App;
import io.src.model.Enums.BackPackType;
import io.src.model.Enums.Menu;
import io.src.model.Enums.Recepies.CraftingRecipesList;
import io.src.model.Enums.Recepies.FoodRecipesList;
import io.src.model.Enums.WeatherAndTime.Seasons;
import io.src.model.GameObject.NPC.NpcProduct;
import io.src.model.MapModule.Buildings.Store;
import io.src.model.MapModule.Position;
import io.src.model.Player;
import io.src.model.Result;
import io.src.model.items.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

public interface ShopController {

    static NpcProduct findProductByName(List<NpcProduct> products, String productName) {
        if (products == null || products.isEmpty()) {
            System.out.println("mf store doesn't have any products");
        }
        for (NpcProduct product : products) {
            if (product.getSaleable().getName().trim().equalsIgnoreCase(productName.trim())) {
                return product;
            }
        }
        return null;
    }

    static Result purchaseProductFromList(String productName, String amountStr, ArrayList<NpcProduct> list) {
        productName = productName.trim();
        amountStr = amountStr.trim();
        int amount;
        try {
            amount = Integer.parseInt(amountStr.trim());
        } catch (NumberFormatException e) {
            return new Result(false, e.getMessage());
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

        if (productToBuy.getRemainingStock() <= 0) {
            return new Result(false, productName + " is ran out of stock,comeback tomorrow...");
        } else if (productToBuy.getRemainingStock() < amount) {
            return new Result(false, " only " + productToBuy.getRemainingStock() + "of " + productName
                + " has remained..there is not enough amount you want..");
        } else if (sumPrice > me.getGold()) {
            return new Result(false, "you can't afford buy this\n" +
                "you have :" + me.getGold() + " gold,but you need: " + sumPrice);
        }

        if (productToBuy.getSaleable() instanceof Item item) {
            //item
            me.addGold(-sumPrice);
            productToBuy.changeRemainingStock(-amount);
            me.getInventory().add(item, amount);
            return new Result(true, "purchase item successful..");
        } else if (productToBuy.getSaleable() instanceof CraftingRecipesList recipe ||
            productToBuy.getSaleable() instanceof FoodRecipesList food) {
            //recipe
            if (amount > 1) {
                return new Result(false, "you dont need to buy more than one crafting recipe.");
            }
            me.addGold(-sumPrice);
            productToBuy.changeRemainingStock(-amount);
            if (productToBuy.getSaleable() instanceof CraftingRecipesList recipe) {
                me.addToolRecipes(recipe);
            } else if (productToBuy.getSaleable() instanceof FoodRecipesList food) {
                me.addFoodRecipes(food);
            }
            return new Result(true, "purchase recipe successful..");
        } else if (productToBuy.getSaleable() instanceof BackPackType backPackType) {
            if (me.getCurrentBackpack().getNext() == null) {
                return new Result(false, "your backpack is already max");
            } else if (amount > 1) {
                return new Result(false, "you can't buy more than one back pack");
            } else if (me.getCurrentBackpack().getNext() != backPackType) {
                return new Result(false, "you can't buy worse back pack or jump from initial backpack" +
                    "to deluxe backpack");
            }
            me.addGold(-sumPrice);
            productToBuy.changeRemainingStock(-amount);
            me.setCurrentBackpack(backPackType);
            return new Result(true, "upgrade your backpack to :" + backPackType.getName() +
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
            if (!Arrays.stream(product.getSeasons()).toList().contains(currSeason) && product.getOutOfSeasonPrice() == -1) {
                continue;
            }
            if (product.getSaleable() instanceof Item) {
                String remainM = (product.getDailyStock() > 100) ? "unlimited" : Integer.toString(product.getDailyStock());
                String remain = (product.getDailyStock() > 100) ? "unlimited" : Integer.toString(product.getRemainingStock());
                builder.append("name :\t'").append(product.getName())
                    .append("'\n\tprice: ").append(product.getPrice())
                    .append("\n\tstock: ").append(remain)
                    .append("out of ").append(remainM).append("remained.")
                    .append("\n----------------------\n");


            }
        }
        return new Result(true, builder.toString());
    }

    static Result exitShopMenu(Class<? extends Store> store) {
        App.setCurrentMenu(Menu.gameMenu);
        Player me = App.getMe();
        Store shop = App.getCurrentUser().getCurrentGame().findStoreByClass(store);
        Position posToSet = new Position(shop.getDoorPosition().getX(), shop.getDoorPosition().getY() + 1);
        me.setCurrentGameLocation(App.getCurrentUser().getCurrentGame().getGameMap().getPelikanTown());
        me.setPosition(posToSet);
        return new Result(true, "exiting " + shop.getName() + "shop...\n" +
            "redirecting to the town...");
    }

}
