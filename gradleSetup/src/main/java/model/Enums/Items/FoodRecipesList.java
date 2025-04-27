
package model.Enums.Items;

import java.util.regex.Pattern;

public enum FoodRecipesList {
    FriedEgg("Fried egg"),
    BakedFish("Baked Fish"),
    Salad("Salad"),
    Olmelet("Olmelet"),
    PumpkinPie("pumpkin pie"),
    Spaghetti("spaghetti"),
    Pizza("pizza"),
    Tortilla("Tortilla"),
    MakiRoll("Maki Roll"),
    TripleShotEspresso("Triple Shot Espresso"),
    Cookie("Cookie"),
    HashBrowns("hash browns"),
    Pancakes("pancakes"),
    FruitSalad("fruit salad"),
    RedPlate("red plate"),
    Bread("bread"),
    SalmonDinner("salmon dinner"),
    VegetableMedley("vegetable medley"),
    FarmersLunch("farmer's lunch"),
    SurvivalBurger("survival burger"),
    DishOTheSea("dish O' the Sea"),
    SeafoamPudding("seaform Pudding"),
    MinersTreat("miner's treat");

    private final String input;

    FoodRecipesList(String input) {
        this.input = input;
    }

    public boolean isValid(String input) {
        return Pattern.matches(this.input, input);
    }

    @Override
    public String toString() {
        return input;
    }
}
