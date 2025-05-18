package model.skills;

import model.App;
import model.Enums.Recepies.CraftingRecipesList;
import model.Enums.Recepies.FoodRecipesList;
import model.Enums.Skills;

public class FarmingSkill extends Skill {
    public FarmingSkill(Skills name, int xp) {
        super(name, xp);
    }

    @Override
    public void setXp(int xp) {
        super.setXp(xp);

        if (getLevel() == 1) {
            App.getMe().addToolRecipes(CraftingRecipesList.Sprinkler);
            App.getMe().addToolRecipes(CraftingRecipesList.BeeHouse);
            App.getMe().addFoodRecipes(FoodRecipesList.FARMERS_LUNCH);
        } else if (getLevel() == 2) {
            App.getMe().addToolRecipes(CraftingRecipesList.QualitySprinkler);
            App.getMe().addToolRecipes(CraftingRecipesList.DeluxeScarecrow);
            App.getMe().addToolRecipes(CraftingRecipesList.CheesePress);
            App.getMe().addToolRecipes(CraftingRecipesList.PreservesJar);
            App.getMe().addFoodRecipes(FoodRecipesList.VEGETABLE_MEDLEY);
        } else if (getLevel() == 3) {
            App.getMe().addToolRecipes(CraftingRecipesList.IridiumSprinkler);
            App.getMe().addToolRecipes(CraftingRecipesList.Keg);
            App.getMe().addToolRecipes(CraftingRecipesList.Loom);
            App.getMe().addToolRecipes(CraftingRecipesList.OilMaker);
        }
    }
}
