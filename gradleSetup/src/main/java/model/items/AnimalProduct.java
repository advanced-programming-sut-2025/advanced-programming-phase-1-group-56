package model.items;

import model.Enums.Animals.AnimalProductQuality;

public class AnimalProduct extends Item{
    private final int price;
    private final AnimalProductQuality quality;

    public AnimalProduct(String name, int maxStackSize, boolean Stackable, int price, AnimalProductQuality quality) {
        super(name, maxStackSize, Stackable);
        this.price = price;
        this.quality = quality;
    }


    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public AnimalProductQuality getQuality() {
        return quality;
    }
}
