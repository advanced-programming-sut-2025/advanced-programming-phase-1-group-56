package model.skills;

import model.App;
import model.Enums.Recepies.CraftingRecipesList;
import model.Enums.Recepies.FoodRecipesList;
import model.Enums.Skills;

public class ForagingSkill extends Skill {
    public ForagingSkill(Skills name, int xp) {
        super(name,xp);
    }

    @Override
    public void setXp(int xp) {
        super.setXp(xp);
        if (getLevel() == 1) {
            App.getMe().addToolRecipes(CraftingRecipesList.CharcoalKlin);
        } else if (getLevel() == 2) {
            App.getMe().addFoodRecipes(FoodRecipesList.VEGETABLE_MEDLEY);
        } else if (getLevel() == 3) {
            App.getMe().addFoodRecipes(FoodRecipesList.SURVIVAL_BURGER);
        } else if (getLevel() == 4) {
            App.getMe().addToolRecipes(CraftingRecipesList.MysticTreeSeed);
        }
    }
}
