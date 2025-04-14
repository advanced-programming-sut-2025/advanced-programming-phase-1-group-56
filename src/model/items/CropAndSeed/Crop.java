package model.items.CropAndSeed;

import model.GameObject.GameObject;
import model.Locations.Tile;
import model.items.Item;

public class Crop extends Item implements Plantable {
    private final Seed resource;
    private final int baseSellPrice;
    private final boolean Eatable;
    private final int Energy;
    private final GameObject plantingObject;
    private final boolean canPlant;



    public Crop(String name, Seed resource,int baseSellPrice, boolean eatable, int energy
            ,GameObject plantingObject, boolean canPlant) {
        super(name,99,true);
        this.resource = resource;
        this.baseSellPrice = baseSellPrice;
        Eatable = eatable;
        Energy = energy;
        this.plantingObject = plantingObject;
        this.canPlant = canPlant;
    }

    public String getName() {
        return name;
    }

    public Seed getResource() {
        return resource;
    }

    public int getBaseSellPrice() {
        return baseSellPrice;
    }

    public boolean isEatable() {
        return Eatable;
    }

    public int getEnergy() {
        return Energy;
    }

    @Override
    public void plant(Tile tile) {

    }

    public GameObject getPlantingObject() {
        return plantingObject;
    }

    public boolean isCanPlant() {
        return canPlant;
    }
}
