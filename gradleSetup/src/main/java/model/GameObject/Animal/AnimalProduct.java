package model.GameObject.Animal;

import model.Enums.Animals.AnimalProductQuality;

public class AnimalProduct {
    private final String name;
    private final int price;
    private final int productionInterval;
    private final int chance;
    private final AnimalProductQuality quality;

    public AnimalProduct(String name, int price, int productionInterval, int chance, AnimalProductQuality quality) {
        this.name = name;
        this.price = price;
        this.productionInterval = productionInterval;
        this.chance = chance;
        this.quality = quality;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getProductionInterval() {
        return productionInterval;
    }

    public int getChance() {
        return chance;
    }

    public AnimalProductQuality getQuality() {
        return quality;
    }
}
