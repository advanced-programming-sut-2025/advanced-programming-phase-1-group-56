package model.items;

import model.Enums.BuffType;
import model.Enums.Items.FoodType;

import java.util.HashMap;

public class Food extends Item {
    private FoodType foodType;

    public Food( FoodType foodType) {
        super(foodType.getName(), 100, true, foodType.getPrice());
        this.foodType = foodType;
    }


    public String getName() {
        return foodType.getName();
    }

    public int getEnergy() {
        return foodType.getEnergy();
    }
    @Override
    public int getPrice() {
        return foodType.getPrice();
    }

    public BuffType getBuff() {
        return foodType.getBuffType();
    }


}
