package io.src.model.items;

import io.src.model.Enums.Items.FruitType;

public class Fruit extends Item {
    private final FruitType fruitType;

    public Fruit( FruitType fruitType) {
        super(fruitType.getName(), 9999, true, fruitType.getPrice());
        this.fruitType = fruitType;
    }
    public FruitType getFruitType() {
        return fruitType;
    }

    public int getEnergy(){
        return fruitType.getEnergy();
    }

    @Override
    public String getAssetName() {
        return fruitType.getAssetName();
    }
}
