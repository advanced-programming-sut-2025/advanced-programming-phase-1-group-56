package model.skills;

import model.App;
import model.Enums.Recepies.FoodRecipesList;
import model.Enums.Skills;

public class FishingSkill extends Skill {
    public FishingSkill(Skills name, int xp) {
        super(name, xp);
    }

    @Override
    public void setXp(int xp) {
        super.setXp(xp);
        if (getLevel() == 2) {
            App.getMe().addFoodRecipes(FoodRecipesList.DISH_O_THE_SEA);
        } else if (getLevel() == 3) {
            App.getMe().addFoodRecipes(FoodRecipesList.SEAFOAM_PUDDING);
        }
    }
}
