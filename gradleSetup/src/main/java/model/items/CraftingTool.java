package model.items;

//import model.Activities.ArtesianRecepie;
import model.Enums.Recepies.CraftingRecipesList;

public class CraftingTool extends Item {
    private final CraftingRecipesList recipesList;

    public CraftingTool(CraftingRecipesList recipesList) {
        super(recipesList.name,100,false, recipesList.sellPrice);
        this.recipesList = recipesList;
    }


    public CraftingRecipesList getRecipesList() {
        return recipesList;
    }
}
