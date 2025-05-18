package controller.GameMenuController;

import controller.CommandController;
import model.*;

import model.Enums.Items.FoodType;
import model.Enums.Recepies.FoodRecipesList;
import model.items.Food;
import model.items.Item;

import java.util.regex.Matcher;

public class CookingController extends CommandController {

    public static Result addRecipe(String name) {
        FoodRecipesList foodRecipesList = FoodRecipesList.fromName(name);
        App.getMe().addFoodRecipes(foodRecipesList);
        return new Result(true, "recipe added successfully");
    }
    //TODO
    public static Result refrigeratorPick(Matcher matcher) {
        String input = matcher.group(1).trim().trim();
        Item item = returnRefrigeratorItemByName(input);
        if (item == null) {
            return new Result(false, "there is no " + input + " in refrigerator");
        }
        App.getCurrentUser()
                .getCurrentGame()
                .getCurrentPlayer()
                .getPlayerFarm().getDefaultHome()
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
        String input = matcher.group(1).trim().trim();
        Item item = returnInventoryItemByName(input);
        if (item == null) {
            return new Result(false, "there is no " + input + " in refrigerator");
        } else if (!(item instanceof Food)) {
            return new Result(false, "this item is not Food!");
        }
        App.getCurrentUser()
                .getCurrentGame()
                .getCurrentPlayer()
                .getPlayerFarm()
                .getDefaultHome()
                .getMyRefrigerator()
                .getInventory()
                .add(item, 1);
        App.getCurrentUser()
                .getCurrentGame()
                .getCurrentPlayer()
                .getInventory()
                .remove(item, 1);
        return new Result(true, "you put Food in refrigerator!");
    }

    public static Result showRecipes() {
        String tmpString = FoodList();
        return new Result(true, tmpString);
    }

    public static Result prepareCooking(Matcher matcher) {
        String recipeName = matcher.group(1).trim().trim();
        FoodRecipesList cookFood = returnCookFoodByName(recipeName);
        if (cookFood == null) {
            return new Result(false, "you are not allowed to use this recipe");
        } else if (!havaIngredient(cookFood)) {
            return new Result(false, "you don't have enough ingredients!");
        }
        StringBuilder tmpString = new StringBuilder();
        tmpString.append("you can cook these foods :\n").append(FoodList());
        FoodRecipesList[] foodRecipesList = FoodRecipesList.values();
        tmpString.append("You don't know these recipes yet:\n");
        for (FoodRecipesList cr : foodRecipesList) {
            boolean known = false;
            for (FoodRecipesList c : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getFoodRecipes()) {
                if (cr.name().equals(c.name)) {
                    known = true;

                    break;
                }
            }
            if (!known) {
                tmpString.append("- Recipe Name :  ").append(cr.name()).append("\n");
                tmpString.append("- Add Energy : ").append(cr.energy).append("\n");
                tmpString.append("- Ingredients : \n");
                int count = 0;
                for (Slot i : cr.ingredients) {
                    count += 1;
                    tmpString.append(count).append(".").append("  name : ").append(i.getItem().getName());
                    tmpString.append("   quantity : ").append(i.getQuantity()).append("\n");
                }
                tmpString.append("-------------------------------");
            }
        }
        App.getCurrentUser().
                getCurrentGame()
                .getCurrentPlayer()
                .subtractEnergy(3);
        for (Slot ingredient : cookFood.ingredients) {
            Item item = returnInventoryItemByName(ingredient.getItem().getName());
            App.getCurrentUser().getCurrentGame().getCurrentPlayer().getInventory().remove(item, ingredient.getQuantity());
        }
        Food food = null;
        for(FoodType foodType : FoodType.values()){
            if(foodType.getName().equals(cookFood.name)){
                food = new Food(foodType);
            }
        }

        App.getCurrentUser()
                .getCurrentGame()
                .getCurrentPlayer()
                .getInventory()
                .add(food, 1);
        return new Result(true, tmpString.append("your food is ready!").toString());
    }

    public static Result eatFood(Matcher matcher) {
        String foodName = matcher.group(1).trim().trim();
        Item item = returnInventoryItemByName(foodName);
        if (item == null) {
            return new Result(false, "there is no `" + foodName + " in your inventory!");
        } else if (!(item instanceof Food)) {
            return new Result(false, "this item is not Food!");
        }
        Food food = (Food) item;
        App.getCurrentUser().getCurrentGame().getCurrentPlayer().getInventory().remove(item, 1);
        App.getCurrentUser().getCurrentGame().getCurrentPlayer().addEnergy(food.getEnergy());
        App.getCurrentUser().getCurrentGame().getCurrentPlayer().setCurrentBuff(new Buff(food.getBuff()));
        return new Result(true, "you eat " + item.getName());
    }

    private static Item returnRefrigeratorItemByName(String itemName) {
        for (Slot slot : App.getCurrentUser()
                .getCurrentGame()
                .getCurrentPlayer()
                .getPlayerFarm()
                .getDefaultHome()
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

    private static FoodRecipesList returnCookFoodByName(String itemName) {
        for (FoodRecipesList cookFood : App.getCurrentUser()
                .getCurrentGame()
                .getCurrentPlayer()
                .getFoodRecipes()) {
            if (cookFood.name.equals(itemName)) {
                return cookFood;
            }
        }
        return null;
    }

    private static String FoodsList() {
        StringBuilder tmpString = new StringBuilder();
        int count = 0;
        for (FoodRecipesList cookFood : App.
                getCurrentUser()
                .getCurrentGame()
                .getCurrentPlayer()
                .getFoodRecipes()) {
            count++;
            tmpString.append(count).append(". \n");
            tmpString.append("FoodName : ").append(cookFood.name).append("\n");
            tmpString.append("Ingredients : \n");
            for (Slot ingredient : cookFood.ingredients) {
                tmpString.append("  ");
                tmpString.append("Ingridient Name :").append(ingredient.getItem().getName()).append("\n");
                tmpString.append("Quantity : ").append(ingredient.getQuantity()).append("\n").append("----\n");
            }
            tmpString.append("Sell Price :").append(cookFood.sellPrice).append("g\n");
            tmpString.append("-----------------------------------------");
        }
        return tmpString.toString();
    }

    private static boolean havaIngredient(FoodRecipesList cookFood) {
        for (Slot ingredient : cookFood.ingredients) {
            boolean isExist = false;
            for (Slot slot : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getInventory().getSlots()) {
                if (slot.getItem().getName().equals(ingredient.getItem().getName())) {
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                return false;
            }
        }
        for (Slot ingredient : cookFood.ingredients) {
            for (Slot slot : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getInventory().getSlots()) {
                if (ingredient.getItem().getName().equals(slot.getItem().getName())) {
                    int sum = 0;
                    for (Slot slot1 : App.getCurrentUser()
                            .getCurrentGame()
                            .getCurrentPlayer()
                            .getInventory()
                            .getSlots()) {
                        if (slot1.getItem().getName().equals(ingredient.getItem().getName())) {
                            sum += slot1.getQuantity();
                        }
                    }
                    if (sum < ingredient.getQuantity()) {
                        return false;
                    }

                }
            }
        }
        return true;
    }

    private static String FoodList() {
        StringBuilder tmpString = new StringBuilder();
        int count = 0;
        for (FoodRecipesList cookFood : App.
                getCurrentUser()
                .getCurrentGame()
                .getCurrentPlayer()
                .getFoodRecipes()) {
            count++;
            tmpString.append(count).append(". \n");
            tmpString.append("FoodName : ").append(cookFood.name).append("\n");
            tmpString.append("Ingredients : \n");
            for (Slot ingredient : cookFood.ingredients) {
                tmpString.append("  ");
                tmpString.append("Ingridient Name :").append(ingredient.getItem().getName()).append("\n");
                tmpString.append("Quantity : ").append(ingredient.getQuantity()).append("\n").append("----\n");
            }
            tmpString.append("Sell Price :").append(cookFood.sellPrice).append("g\n");
            tmpString.append("-----------------------------------------");
        }
        return tmpString.toString();
    }
}
