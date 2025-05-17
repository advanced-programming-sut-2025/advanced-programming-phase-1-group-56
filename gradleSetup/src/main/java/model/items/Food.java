package model.items;

import model.Enums.BuffType;
import model.Enums.Items.FoodType;

public class Food extends Item {
    private final FoodType foodType;

    public Food(FoodType foodType) {
        super(foodType.getName(), 100, true,foodType.getPrice());
        this.foodType = foodType;
    }


    public String getName() {
        return foodType.getName();
    }

    public int getEnergy() {
        return foodType.getEnergy();
    }

    public BuffType getBuff() {
        return foodType.getBuffType();
    }


}
