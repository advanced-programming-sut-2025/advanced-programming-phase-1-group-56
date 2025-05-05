package controller.GameMenuController;

import controller.CommandController;
import model.Activities.CraftTool;
import model.App;
import model.Enums.Registery.CraftingRecipesList;
import model.Enums.commands.GameCommands.CraftingCommand;
import model.Ingredient;
import model.Result;
import model.items.CraftingTool;
import model.items.Item;

import javax.net.ssl.CertPathTrustManagerParameters;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;

public class CraftingController extends CommandController {
    public static Result showCraftingRecipes() {
        String tmpString = RecipeList();
        return new Result(true, tmpString);
    }

    public static Result craftingCraft(Matcher matcher) {
        StringBuilder tmpString = new StringBuilder();
        tmpString.append("you can Craft this Recipes:").append("\n").append(RecipeList());
        CraftingRecipesList[] craftingRecipesLists  = CraftingRecipesList.values();
        for(CraftTool c : App.getCurrentUser().getCurrentGame().getCurrentPlayer().getToolRecipes()) {
            boolean correct = false;
             for(CraftingRecipesList cr : craftingRecipesLists) {
                 if(cr.name.equals(c.getName())) {
                     correct = true;
                     break;
                 }
             }
             if(!correct) {

             }
        }

        String ItemName = matcher.group(1);
        CraftingRecipesList recipesList = returnCraftingRecipe(ItemName);


        return null;
    }

    public static Result placeItem() {

        return null;
    }


    //CHEAT

    public static Result cheatAddItem() {

        return null;
    }

    private static String RecipeList(){
        ArrayList<CraftTool> craftToolsRecipe = App.getCurrentUser()
                .getCurrentGame()
                .getCurrentPlayer()
                .getToolRecipes();
        StringBuilder tmpString = new StringBuilder();
        for(CraftTool craftTool : craftToolsRecipe) {
            tmpString.append("Tools Name : ").append(craftTool.getName()).append("\n");
            tmpString.append("Description : ").append(craftTool.getDescription()).append("\n");
            tmpString.append("Ingredients : \n");
            for(Map.Entry<Item,Integer> map : craftTool.getIngredients().entrySet()){
                tmpString.append(map.getKey().getName()).append("(").append(map.getValue()).append(")\n");
            }
            tmpString.append("Sell Price : ").append(craftTool.getSellPrice()).append("\n");
        }
        return tmpString.toString();
    }




    public static CraftingRecipesList returnCraftingRecipe(String ItemName) {
        for(CraftingRecipesList recipesList : CraftingRecipesList.values()) {
            if(recipesList.name.equals(ItemName)) {
                return recipesList;
            }
        }
        return null;
    }
}
