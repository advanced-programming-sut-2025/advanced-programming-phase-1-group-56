package io.src.model.items;

import io.src.model.Enums.BuffType;
import io.src.model.Enums.Items.FoodType;

public class Food extends Item {
    private final FoodType foodType;

    public Food(FoodType foodType) {
        super(foodType.getName(), 9999, true, foodType.getPrice());
        this.foodType = foodType;
    }


    public String getName() {
        return foodType.getName();
    }

    @Override
    public String getAssetName() {
        return foodType.getAssetName();
    }

    public int getEnergy() {
        return foodType.getEnergy();
    }

    public BuffType getBuff() {
        return foodType.getBuffType();
    }


}
