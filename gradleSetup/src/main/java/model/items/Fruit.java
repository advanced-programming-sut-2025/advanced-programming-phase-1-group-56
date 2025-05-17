package model.items;

import model.Enums.Items.EtcType;
import model.Enums.Items.FoodType;
import model.Enums.Items.FruitType;

public class Fruit extends Item {
    private final FruitType fruitType;

    public Fruit( FruitType fruitType) {
        super(fruitType.getName(), 100, true, fruitType.getPrice());
        this.fruitType = fruitType;
    }
    public FruitType getFruitType() {
        return fruitType;
    }

    public int getEnergy(){
        return fruitType.getEnergy();
    }
}
