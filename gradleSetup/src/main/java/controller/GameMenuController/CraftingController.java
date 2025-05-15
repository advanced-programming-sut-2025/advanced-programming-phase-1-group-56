package controller.GameMenuController;

import controller.CommandController;
import model.App;
import model.Enums.Direction;
import model.Enums.Recepies.CraftingRecipesList;
import model.GameObject.DroppedItem;
import model.Ingredient;
import model.MapModule.Position;
import model.MapModule.Tile;
import model.Result;
import model.Slot;
import model.items.CraftingTool;
import model.items.Item;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class CraftingController extends CommandController {
    public static Result showCraftingRecipes() {
        String tmpString = RecipeList();
        return new Result(true, tmpString);
    }

    public static Result craftingItem(Matcher matcher) {
        String ItemName = matcher.group(1);
        CraftingRecipesList recipe = returnCraftingRecipe(ItemName);
        if (recipe != null) {
            return new Result(true, "you can't Craft this Recipe!");
        } else if (!havaIngredient(recipe)) {
            return new Result(false, "you have not enough ingredients!");
        }
        StringBuilder tmpString = new StringBuilder();
        tmpString.append("you can Craft this Recipes:").append("\n").append(RecipeList());
        CraftingRecipesList[] craftingRecipesLists = CraftingRecipesList.values();
        tmpString.append("You don't know these recipes yet:\n");
        for (CraftingRecipesList cr : craftingRecipesLists) {
            boolean known = false;
            for (CraftingRecipesList c : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getToolRecipes()) {
                if (cr.name.equals(c.name)) {
                    known = true;
                    break;
                }
            }
            if (!known) {
                tmpString.append("- Recipe Name:  ").append(cr.name).append("\n");
                tmpString.append("- Description : ").append(cr.description).append("\n");
                tmpString.append("- Ingredients : \n");
                int count = 0;
                for (Ingredient i : cr.ingredients) {
                    count += 1;
                    tmpString.append(count + ".").append("  name : ").append(i.name);
                    tmpString.append("   quantity : ").append(i.quantity).append("\n");
                }
                tmpString.append("-------------------------------");
            }
        }
        CraftingRecipesList craftingRecipesList = null;
        for(CraftingRecipesList cr : craftingRecipesLists) {
            if(cr.name.equals(recipe.name)) {
                craftingRecipesList = cr;
            }
        }
        Item craftingTool = new CraftingTool(craftingRecipesList);
        if (App.getCurrentUser().getCurrentGame().getCurrentPlayer().getInventory().canAddItem(craftingTool, 1)) {
            App.getCurrentUser().getCurrentGame().getCurrentPlayer().getInventory().add(craftingTool, 1);
        } else {
            return new Result(false, "you don't have enough space!");
        }
        for (Ingredient i : recipe.ingredients) {
            App.getCurrentUser().getCurrentGame().getCurrentPlayer().getInventory().remove(returnInventoryItemByName(i.name), i.getQuantity());
        }
        App.getCurrentUser().getCurrentGame().getCurrentPlayer().subtractEnergy(2);

        return new Result(true, tmpString.toString());
    }

    public static Result placeItem(Matcher matcher) {
        String itemName = matcher.group(1);
        String direction = matcher.group(2);
        Direction dir;
        Item item = returnInventoryItemByName(itemName);
        if (item == null) {
            return new Result(false, "this item does not exist!");
        } else if ((dir = getDirectionFromString(direction)) == null) {
            return new Result(false, "this direction does not exist!");
        }
        Position position = App.getCurrentUser().getCurrentGame().getCurrentPlayer().getPosition();
        int x = position.getX();
        int y = position.getY();
        switch (dir) {
            case UP:
                y -= 1;
                break;
            case DOWN:
                y += 1;
                break;
            case LEFT:
                x -= 1;
                break;
            case RIGHT:
                x += 1;
                break;
            case UPLEFT:
                x -= 1;
                y -= 1;
                break;
            case UPRIGHT:
                x += 1;
                y -= 1;
                break;
            case DOWNLEFT:
                x -= 1;
                y += 1;
                break;
            case DOWNRIGHT:
                x += 1;
                y += 1;
                break;
            default:
                break;
        }

        DroppedItem droppedItem = new DroppedItem(item,new Position(x,y));
        App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x, y).setFixedObject(droppedItem);
        App.getCurrentUser().getCurrentGame().getCurrentPlayer().getInventory().remove(item, 1);
        return new Result(true, "you placed a item!");
    }


    //CHEAT

    public static Result cheatAddItem(Matcher matcher) {
        String itemName = matcher.group(1);
        int count = Integer.parseInt(matcher.group(2));
        //TODO kol item hai bazi

        return null;
    }

    private static String RecipeList() {
        ArrayList<CraftingRecipesList> craftToolsRecipe = App.getCurrentUser()
                .getCurrentGame()
                .getCurrentPlayer()
                .getToolRecipes();
        StringBuilder tmpString = new StringBuilder();
        for (CraftingRecipesList craftTool : craftToolsRecipe) {
            tmpString.append("Tools Name : ").append(craftTool.name).append("\n");
            tmpString.append("Description : ").append(craftTool.description).append("\n");
            tmpString.append("Ingredients : \n");
            for (Ingredient ingredient : craftTool.ingredients) {
                tmpString.append("Name : ").append(ingredient.getName()).append("\n");
                tmpString.append("Quantity : ").append(ingredient.getQuantity()).append("\n----\n");
            }
            tmpString.append("Sell Price : ").append(craftTool.sellPrice).append("\n");
            tmpString.append("----------------------------------");

        }
        return tmpString.toString();
    }

    private static CraftingRecipesList returnCraftingRecipe(String ItemName) {
        for (CraftingRecipesList craftTool : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getToolRecipes()) {
            if (craftTool.name.equals(ItemName)) {
                return craftTool;
            }
        }
        return null;
    }

    private static boolean havaIngredient(CraftingRecipesList craftTool) {
        for (Ingredient ingredient : craftTool.ingredients) {
            boolean isExist = false;
            for (Slot slot : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getInventory().getSlots()) {
                if (slot.getItem().getName().equals(ingredient.getName())) {
                    isExist = true;
                }
            }
            if (!isExist) {
                return false;
            }
        }
        for (Ingredient ingredient : craftTool.ingredients) {
            for (Slot slot : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getInventory().getSlots()) {
                if (ingredient.getName().equals(slot.getItem().getName())) {
                    int sum = 0;
                    for (Slot slot1 : App.getCurrentUser()
                            .getCurrentGame()
                            .getCurrentPlayer()
                            .getInventory()
                            .getSlots()) {
                        if (slot1.getItem().getName().equals(ingredient.getName())) {
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

    private static Direction getDirectionFromString(String input) {
        try {
            return Direction.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
