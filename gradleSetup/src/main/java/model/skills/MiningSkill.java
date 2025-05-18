package model.skills;

import model.App;
import model.Enums.Recepies.CraftingRecipesList;
import model.Enums.Recepies.FoodRecipesList;
import model.Enums.Skills;

public class MiningSkill extends Skill {

    public MiningSkill(Skills name , int xp) {
        super(name, xp);
    }

    @Override
    public void setXp(int xp) {
        super.setXp(xp);
        if (getLevel() == 1) {
            App.getMe().addToolRecipes(CraftingRecipesList.CherryBomb);
            App.getMe().addFoodRecipes(FoodRecipesList.MINERS_TREAT);
        } else if (getLevel() == 2) {
            App.getMe().addToolRecipes(CraftingRecipesList.Bomb);
            App.getMe().addFoodRecipes(FoodRecipesList.SURVIVAL_BURGER);
        } else if (getLevel() == 3) {
            App.getMe().addToolRecipes(CraftingRecipesList.MegaBomb);
            App.getMe().addFoodRecipes(FoodRecipesList.FRIED_EGG);
        }
    }
}
