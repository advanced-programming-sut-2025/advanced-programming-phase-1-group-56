package model.Enums.Registery;

import model.Enums.Animals.AnimalProductQuality;
import model.Enums.Animals.AnimalType;
import model.Enums.Animals.BuildingType;
import model.GameObject.Animal.AnimalProduct;

public enum AnimalInfo {
    CHICKEN(AnimalType.CHICKEN,800, BuildingType.COOP, 4,
            new AnimalProduct[]{
                    new AnimalProduct("Egg", 50, 1, 90, AnimalProductQuality.normal),
                    new AnimalProduct("Large Egg", 95, 1, 10,AnimalProductQuality.normal)
            }),
    DUCK(AnimalType.DUCK,1200, BuildingType.BIG_COOP, 8,
            new AnimalProduct[]{
                    new AnimalProduct("Duck Egg", 95, 2, 80,AnimalProductQuality.normal),
                    new AnimalProduct("Duck Feather", 250, 2, 20,AnimalProductQuality.normal)
            }),
    RABBIT(AnimalType.RABBIT,8000, BuildingType.DELUXE_COOP, 12,
            new AnimalProduct[]{
                    new AnimalProduct("Wool", 340, 4, 80,AnimalProductQuality.normal),
                    new AnimalProduct("Rabbit's Foot", 565, 4, 20,AnimalProductQuality.normal)
            }),
    DINOSAUR(AnimalType.DINOSAUR, 14000,BuildingType.BIG_COOP, 8,
            new AnimalProduct[]{
                    new AnimalProduct("Dinosaur Egg", 350, 7, 100,AnimalProductQuality.normal)
            }),

    COW(AnimalType.COW,1500 ,BuildingType.BARN ,4,
            new AnimalProduct[]{
                    new AnimalProduct("Milk", 125, 1, 90,AnimalProductQuality.normal),
                    new AnimalProduct("Large Milk", 190, 1, 10,AnimalProductQuality.normal)
            }),
    GOAT(AnimalType.GOAT,4000, BuildingType.BIG_BARN, 8,
            new AnimalProduct[]{
                    new AnimalProduct("Goat Milk", 225, 2, 90,AnimalProductQuality.normal),
                    new AnimalProduct("Large Goat Milk", 345, 2, 10,AnimalProductQuality.normal)
            }),
    SHEEP(AnimalType.SHEEP,8000, BuildingType.DELUXE_BARN, 12,
            new AnimalProduct[]{
                    new AnimalProduct("Wool", 340, 3, 100,AnimalProductQuality.normal)
            }),
    PIG(AnimalType.PIG, 16000,BuildingType.DELUXE_BARN, 12,
            new AnimalProduct[]{
                    new AnimalProduct("Truffle", 625, 1, 100,AnimalProductQuality.normal)
            });

    private final AnimalType type;
    private final int price;
    private final BuildingType requiredBuilding;
    private final int capacity;
    private final AnimalProduct[] products;

    AnimalInfo(AnimalType type,int price, BuildingType requiredBuilding, int capacity, AnimalProduct[] products) {
        this.type = type;
        this.price = price;
        this.requiredBuilding = requiredBuilding;
        this.capacity = capacity;
        this.products = products;
    }

    public AnimalType getType() {
        return type;
    }

    public BuildingType getRequiredBuilding() {
        return requiredBuilding;
    }

    public int getCapacity() {
        return capacity;
    }

    public AnimalProduct[] getProducts() {
        return products;
    }

    public int getPrice() {
        return price;
    }
}
