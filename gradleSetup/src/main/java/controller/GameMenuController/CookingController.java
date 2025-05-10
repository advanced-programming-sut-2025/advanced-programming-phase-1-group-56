package controller.GameMenuController;

import controller.CommandController;
import model.Activities.CookFood;
import model.App;
import model.Result;
import model.Slot;
import model.States.Energy;
import model.items.Food;
import model.items.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

public class CookingController extends CommandController {
    public static Result refrigeratorPick(Matcher matcher) {
        String input = matcher.group(1).trim();
        Item item = returnRefrigeratorItemByName(input);
        if (item == null) {
            return new Result(false, "there is no " + item.getName() + " in refrigerator");
        }
        App.getCurrentUser()
                .getCurrentGame()
                .getCurrentPlayer()
                .getPlayerFarm().getHome()
                .getMyRefrigerator()
                .getInventory()
                .remove(item, 1);
        App.getCurrentUser()
                .getCurrentGame()
                .getCurrentPlayer()
                .getInventory()
                .add(item, 1);
        return new Result(true, "you get Food!");
    }

    public static Result refrigeratorPut(Matcher matcher) {
        String input = matcher.group(1).trim();
        Item item = returnInventoryItemByName(input);
        if (item == null) {
            return new Result(false, "there is no " + item.getName() + " in refrigerator");
        } else if (!(item instanceof Food)) {
            return new Result(false, "this item is not Food!");
        }
        App.getCurrentUser()
                .getCurrentGame()
                .getCurrentPlayer()
                .getPlayerFarm()
                .getHome()
                .getMyRefrigerator()
                .getInventory()
                .add(item, 1);
        App.getCurrentUser()
                .getCurrentGame()
                .getCurrentPlayer()
                .getInventory()
                .remove(item, 1);
        return new Result(true, "you put Food!");
    }

    public static Result showRecipes() {
        StringBuilder tmpString = new StringBuilder();
        for (CookFood cookFood : App.
                getCurrentUser()
                .getCurrentGame()
                .getCurrentPlayer()
                .getFoodRecipes()) {
            tmpString.append("foodName : ").append(cookFood.getName()).append("\n").append("Ingredients : ").append(cookFood.getIngredients());
            tmpString.append("-----------------------------------------");
        }
        return new Result(true, tmpString.toString());
    }

    public static Result prepareCooking(Matcher matcher) {
        String recipeName = matcher.group(1).trim();
        CookFood cookFood = returnCookFoodByName(recipeName);
        if (cookFood == null) {
            return new Result(false, "you are not allowed to use this recipe");
        }
        for (Slot slot : App.getCurrentUser()
                .getCurrentGame()
                .getCurrentPlayer()
                .getInventory()
                .getSlots()) {
            Item item = slot.getItem();
            int amount = slot.getQuantity();
            boolean exist = false;
            for (Item item1 : cookFood.getIngredients().keySet()) {
                int amount1 = cookFood.getIngredients().get(item1);
                if (amount1 > amount) {
                    return new Result(false, "you dont have enough ingredients ->>> " + item1.getName() + " about " + (amount1 - amount));
                }
                if (item1.getName().equals(item.getName())) {
                    exist = true;
                }
            }
            if (!exist) {
                return new Result(false, "you don't hava all the materials.");
            }
        }
        App.getCurrentUser().
                getCurrentGame()
                .getCurrentPlayer()
                .setEnergy(new Energy(App.getCurrentUser()
                .getCurrentGame()
                .getCurrentPlayer()
                .getEnergy()
                .getEnergy() - 3));
        for (Item item : cookFood.getIngredients().keySet()) {
            int quantity = cookFood.getIngredients().get(item);
            App.getCurrentUser()
                    .getCurrentGame()
                    .getCurrentPlayer()
                    .getInventory()
                    .remove(item, quantity);
        }
        Food food = new Food(cookFood.getName(), 20, cookFood.getEnergy(), cookFood.getPrice(),cookFood.getBuff());
        App.getCurrentUser()
                .getCurrentGame()
                .getCurrentPlayer()
                .getInventory()
                .add(food, 1);
        App.getCurrentUser().getCurrentGame().getCurrentPlayer().subtractGold(cookFood.getPrice());
        return new Result(true, "your food is ready!");
    }

    public static Result eatFood(Matcher matcher) {
        String foodName = matcher.group(1).trim();
        Item item = returnInventoryItemByName(foodName);
        if (item == null) {
            return new Result(false, "there is no `" + item.getName() + " in your inventory!");
        }else if (!(item instanceof Food)) {
            return new Result(false, "this item is not Food!");
        }
        Food food = (Food) item;
        App.getCurrentUser().getCurrentGame().getCurrentPlayer().getInventory().remove(item, 1);
        App.getCurrentUser()
                .getCurrentGame()
                .getCurrentPlayer()
                .setEnergy(new Energy(App.getCurrentUser()
                        .getCurrentGame()
                        .getCurrentPlayer()
                        .getEnergy()
                        .getEnergy()+food.getEnergy()));
        if(food.getBuff().equals("1")){}
        //TODO
        return new Result(true, "you eat "+item.getName());
    }

    private static Item returnRefrigeratorItemByName(String itemName) {

        for (Slot slot : App.getCurrentUser()
                .getCurrentGame()
                .getCurrentPlayer()
                .getPlayerFarm()
                .getHome()
                .getMyRefrigerator()
                .getInventory()
                .getSlots()) {
            Item item = slot.getItem();
            if (item.getName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }

    private static Item returnInventoryItemByName(String itemName) {
        for (Slot slot : App.getCurrentUser()
                .getCurrentGame()
                .getCurrentPlayer()
                .getInventory()
                .getSlots()) {
            Item item = slot.getItem();
            if (item.getName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }

    private static CookFood returnCookFoodByName(String itemName) {
        for (CookFood cookFood : App.getCurrentUser()
                .getCurrentGame()
                .getCurrentPlayer()
                .getFoodRecipes()) {
            if (cookFood.getName().equals(itemName)) {
                return cookFood;
            }
        }
        return null;
    }
}
