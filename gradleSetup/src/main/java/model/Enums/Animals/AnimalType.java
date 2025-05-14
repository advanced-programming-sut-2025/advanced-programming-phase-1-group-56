package model.Enums.Animals;

import model.Enums.Buildings.BuildingType;
import model.Enums.Items.EtcType;
import model.GameObject.Animal.Animal;
import model.MapModule.Position;

public enum AnimalType {
    CHICKEN("Chicken",800, BuildingType.COOP,
            new EtcType[]{
                    EtcType.EGG,EtcType.BIG_EGG
            },1),
    DUCK("Duck",1200, BuildingType.BIG_COOP,
            new  EtcType[]{
                    EtcType.DUCK_EGG,EtcType.DUCK_FEATHER
            },2),
    RABBIT("Rabbit",8000, BuildingType.DELUXE_COOP,
            new EtcType[]{
                    EtcType.WOOL,EtcType.RABBITS_FOOT
            },4),
    DINOSAUR("Dinosaur", 14000,BuildingType.BIG_COOP,
            new EtcType[]{EtcType.DINOSAUR_EGG},7),

    COW("Cow",1500 ,BuildingType.BARN,
            new EtcType[]{
                    EtcType.MILK,EtcType.BIG_MILK
            },1),
    GOAT("Goat",4000, BuildingType.BIG_BARN,
            new EtcType[]{
                    EtcType.GOAT_MILK,EtcType.BIG_GOAT_MILK
            },2),
    SHEEP("Sheep",8000, BuildingType.DELUXE_BARN,
            new EtcType[]{EtcType.WOOL},3),
    PIG("Pig", 16000,BuildingType.DELUXE_BARN,
            new EtcType[]{
                    EtcType.TRUFFLE
            },1);

    private final String name;
    private final int price;
    private final BuildingType requiredBuilding;
    private final EtcType[] products;
    private final int productionInterval;

    AnimalType(String name, int price, BuildingType requiredBuilding, EtcType[] products, int productionInterval) {
        this.name = name;
        this.price = price;
        this.requiredBuilding = requiredBuilding;
        this.products = products;
        this.productionInterval = productionInterval;
    }

    public String getType() {
        return name;
    }

    public BuildingType getRequiredBuilding() {
        return requiredBuilding;
    }


    public EtcType[] getProducts() {
        return products;
    }

    public int getPrice() {
        return price;
    }

    public int getProductionInterval() {
        return productionInterval;
    }

    public Animal createAnimal(Position position, String name) {
        return new Animal(position, false, name, this);
    }
}
