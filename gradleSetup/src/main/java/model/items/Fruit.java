package model.items;

import model.Enums.Items.EtcType;
import model.Enums.Items.FoodType;
import model.Enums.Items.FruitType;

public class Fruit extends Item {
    private FruitType fruitType;

    public Fruit(FruitType fruitType) {
        super(fruitType.getName(), 100, true , fruitType.getPrice());
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
