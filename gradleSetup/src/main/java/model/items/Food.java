package model.items;

import model.Enums.BuffType;
import model.Enums.Items.FoodType;

import java.util.HashMap;

public class Food extends Item {
    private FoodType foodType;

    public Food(String name, int maxStackSize, boolean Stackable, FoodType foodType) {
        super(name, maxStackSize, Stackable);
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
