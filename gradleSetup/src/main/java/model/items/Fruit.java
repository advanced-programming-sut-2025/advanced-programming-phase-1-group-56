package model.items;

import model.Enums.Items.EtcType;
import model.Enums.Items.FoodType;
import model.Enums.Items.FruitType;

public class Fruit extends Item {
    private FruitType fruitType;

    public Fruit(String name, int maxStackSize, boolean Stackable, FruitType fruitType) {
        super(name, maxStackSize, Stackable);
        this.fruitType = fruitType;
    }
    private FruitType getFruitType() {
        return fruitType;
    }
}
