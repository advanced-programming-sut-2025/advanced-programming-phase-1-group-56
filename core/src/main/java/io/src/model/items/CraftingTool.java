package io.src.model.items;

//import io.src.model.Activities.ArtesianRecepie;
import io.src.model.Enums.Recepies.CraftingRecipesList;

public class CraftingTool extends Item {
    private final CraftingRecipesList recipesList;

    public CraftingTool(CraftingRecipesList recipesList) {
        super(recipesList.name,9999,false, recipesList.sellPrice);
        this.recipesList = recipesList;
    }


    public CraftingRecipesList getRecipesList() {
        return recipesList;
    }

    @Override
    public String getAssetName() {
        return recipesList.getAssetName();
    }
}
