package controller.GameMenuController;

import controller.CommandController;
import model.App;
import model.Enums.Direction;
import model.Enums.Items.*;
import model.Enums.Recepies.CraftingRecipesList;
import model.GameObject.DroppedItem;
import model.GameObject.*;
import model.Ingredient;
import model.MapModule.Position;
import model.Result;
import model.Slot;
import model.items.*;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class CraftingController extends CommandController {
    public static Result showCraftingRecipes() {
        String tmpString = RecipeList();
        return new Result(true, tmpString);
    }

    public static Result craftingItem(Matcher matcher) {
        String ItemName = matcher.group(1).trim();
        CraftingRecipesList recipe = returnCraftingRecipe(ItemName);
        if (recipe == null) {
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
                for (Slot i : cr.ingredients) {
                    count += 1;
                    tmpString.append(count).append(".").append("  name : ").append(i.getItem().getName());
                    tmpString.append("   quantity : ").append(i.getQuantity()).append("\n");
                }
                tmpString.append("-------------------------------");
            }
        }
        CraftingRecipesList craftingRecipesList = null;
        for (CraftingRecipesList cr : craftingRecipesLists) {
            if (cr.name.equals(recipe.name)) {
                craftingRecipesList = cr;
            }
        }
        if(craftingRecipesList == null){
            return new Result(false,"craft recipe list in null");
        }
        Item craftingTool = new CraftingTool(craftingRecipesList);
        if (App.getCurrentUser().getCurrentGame().getCurrentPlayer().getInventory().canAddItem(craftingTool, 1)) {
            App.getCurrentUser().getCurrentGame().getCurrentPlayer().getInventory().add(craftingTool, 1);
        } else {
            return new Result(false, "you don't have enough space!");
        }
        for (Slot i : recipe.ingredients) {
            App.getCurrentUser().getCurrentGame().getCurrentPlayer().getInventory().remove(returnInventoryItemByName(i.getItem().getName()), i.getQuantity());
        }
        App.getCurrentUser().getCurrentGame().getCurrentPlayer().subtractEnergy(2);
        return new Result(true, tmpString.toString());
    }

    public static Result placeItem(Matcher matcher) {
        String itemName = matcher.group(1).trim();
        String direction = matcher.group(2).trim();
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
        if (item instanceof Artesian) {
            App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x, y).setFixedObject(new ArtesianMachine(false, new Position(x, y), ((Artesian) item).getArtisanMachineItemType().getArtisanMachineType()));
        } else if (item instanceof Etc) {
            if (((Etc) item).getEtcType().etcObjectType != null) {
                if (((Etc) item).getEtcType().getName().equals(EtcType.SCARE_CROW.name) || ((Etc) item).getEtcType().getName().equals(EtcType.DELUXE_SCARE_CROW.name)) {
                    for (int i = -1; i <= 1; i++) {
                        for (int j = -1; j <= 1; j++) {
                            if (App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x + i, y + j).getFixedObject() instanceof Crop)
                                ((Crop) App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x + i, y + j).getFixedObject()).setProtected(true);
                            else if (App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x + i, y + j).getFixedObject() instanceof Tree)
                                ((Tree) App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x + i, y + j).getFixedObject()).setProtected(true);
                        }
                    }
                }
            } else if (((Etc) item).getEtcType().getName().equals(EtcType.SPRINKLER.name) || ((Etc) item).getEtcType().getName().equals(EtcType.IRIDIUM_SPRINKLER.name) || ((Etc) item).getEtcType().getName().equals(EtcType.QUALITY_SPRINKLER.name)) {
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x + i, y + j).getFixedObject() instanceof Crop)
                            ((Crop) App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x + i, y + j).getFixedObject()).setWateredToday(true);
                        else if (App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x + i, y + j).getFixedObject() instanceof Tree)
                            ((Tree) App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x + i, y + j).getFixedObject()).setWateredToday(true);
                    }
                }
            }
            App.getCurrentUser().getCurrentGame().getCurrentPlayer().getCurrentGameLocation().getTileByPosition(x, y).setFixedObject(new EtcObject(false, new Position(x, y), ((Etc) item).getEtcType().etcObjectType));
        }


        App.getCurrentUser().getCurrentGame().getCurrentPlayer().getInventory().remove(item, 1);
        return new Result(true, "you placed a item!");
    }


    public static Result cheatAddItem(Matcher matcher) {
        String itemName = matcher.group(1).trim();
        int count = Integer.parseInt(matcher.group(2).trim());
        EtcType type = (EtcType) ItemType.FindItemTypeByName(EtcType.values(), itemName);
        if (type != null) {
            App.getMe().getInventory().add(new Etc(type), 1);
            return new Result(true, "add Etc To Your Inventory!");
        }
        ArtisanMachineItemType type1 = (ArtisanMachineItemType) ItemType.FindItemTypeByName(ArtisanMachineItemType.values(), itemName);
        if (type1 != null) {
            App.getMe().getInventory().add(new Artesian(type1), 1);
            return new Result(true, "add Artisan Machine To Your Inventory!");
        }
        ArtisanGoodType type2 = (ArtisanGoodType) ItemType.FindItemTypeByName(ArtisanGoodType.values(), itemName);
        if (type2 != null) {
            App.getMe().getInventory().add(new ArtisanGood(type2), 1);
            return new Result(true, "add Artisan Good To Your Inventory!");
        }
        CraftingRecipesList type3 = (CraftingRecipesList) CraftingRecipesList.fromName(itemName);
        if (type3 != null) {
            App.getMe().getInventory().add(new CraftingTool(type3), 1);
            return new Result(true, "add CraftingTool To Your Inventory!");
        }

        FishType type4 = (FishType) ItemType.FindItemTypeByName(FishType.values(), itemName);
        if (type4 != null) {
            App.getMe().getInventory().add(new Fish(type4), 1);
            return new Result(true, "add Fish To Your Inventory!");
        }

        FoodType type5 = (FoodType) ItemType.FindItemTypeByName(FoodType.values(), itemName);
        if (type5 != null) {
            App.getMe().getInventory().add(new Food(type5), 1);
            return new Result(true, "add Food To Your Inventory!");
        }
        FruitType type6 = (FruitType) ItemType.FindItemTypeByName(FruitType.values(), itemName);
        if (type6 != null) {
            App.getMe().getInventory().add(new Fruit(type6), 1);
            return new Result(true, "add Fruit To Your Inventory!");
        }

        MineralItemType type7 = (MineralItemType)ItemType.FindItemTypeByName(MineralItemType.values(), itemName);
        if (type7 != null) {
            App.getMe().getInventory().add(new Mineral(type7), 1);
            return new Result(true, "add CraftingTool To Your Inventory!");
        }

        SeedType type8 = (SeedType) ItemType.FindItemTypeByName(SeedType.values(), itemName);
        if (type8 != null) {
            App.getMe().getInventory().add(new Seed(type8), 1);
            return new Result(true, "add Seed To Your Inventory!");
        }

        ToolType type9 = (ToolType) ToolType.fromName(itemName);
        if (type9 != null) {
            App.getMe().getInventory().add(new Tool(type9), 1);
            return new Result(true, "add Tool To Your Inventory!");
        }


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
            for (Slot ingredient : craftTool.ingredients) {
                tmpString.append("Name : ").append(ingredient.getItem().getName()).append("\n");
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
        for (Slot ingredient : craftTool.ingredients) {
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
        for (Slot ingredient : craftTool.ingredients) {
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

    public static Direction getDirectionFromString(String input) {
        try {
            return Direction.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
