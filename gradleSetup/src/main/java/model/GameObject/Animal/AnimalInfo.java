package model.GameObject.Animal;

import model.Enums.Animals.AnimalType;

import java.util.List;

public class AnimalInfo {
    private AnimalType type;
    private int price;
    private int requiredCapacity;
    private String requiredBuildingName;// مثلاً "Coop" یا "Barn"
    private List<AnimalProduct> products;

    public AnimalInfo(AnimalType type, int price, int requiredCapacity, String requiredBuildingName, List<AnimalProduct> products) {
        this.type = type;
        this.price = price;
        this.requiredCapacity = requiredCapacity;
        this.requiredBuildingName = requiredBuildingName;
        this.products = products;
    }

    public AnimalType getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public int getRequiredCapacity() {
        return requiredCapacity;
    }

    public String getRequiredBuildingName() {
        return requiredBuildingName;
    }


}