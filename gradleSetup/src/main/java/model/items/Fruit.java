package model.items;

import model.Enums.Items.EtcType;
import model.Enums.Items.FoodType;
import model.Enums.Items.FruitType;

public class Fruit extends Item {
    private FruitType fruitType;

    public Fruit( FruitType fruitType,int maxStackSize, boolean Stackable) {
        super(fruitType.getName(), maxStackSize, Stackable);
        this.fruitType = fruitType;
    }
    private FruitType getFruitType() {
        return fruitType;
    }

    @Override
    public int getPrice() {
        return fruitType.getPrice();
    }
}
