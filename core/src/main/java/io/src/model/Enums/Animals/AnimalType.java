package io.src.model.Enums.Animals;

import io.src.model.Enums.Buildings.BuildingType;
import io.src.model.Enums.Items.EtcType;
import io.src.model.GameObject.Animal;
import io.src.model.MapModule.Position;
import io.src.model.items.Saleable;
import org.jetbrains.annotations.Nullable;

public enum AnimalType implements Saleable {
    CHICKEN("Chicken", 800, BuildingType.COOP,
        new EtcType[]{
            EtcType.EGG, EtcType.BIG_EGG
        }, 1, "Chicken_Atlas"),
    DUCK("Duck", 1200, BuildingType.BIG_COOP,
        new EtcType[]{
            EtcType.DUCK_EGG, EtcType.DUCK_FEATHER
        }, 2, "Duck_Atlas"),
    RABBIT("Rabbit", 8000, BuildingType.DELUXE_COOP,
        new EtcType[]{
            EtcType.WOOL, EtcType.RABBITS_FOOT
        }, 4, "Rabbit_Atlas"),
    DINOSAUR("Dinosaur", 14000, BuildingType.BIG_COOP,
        new EtcType[]{EtcType.DINOSAUR_EGG}, 7, "Dinosaur_Atlas"),

    COW("Cow", 1500, BuildingType.BARN,
        new EtcType[]{
            EtcType.MILK, EtcType.BIG_MILK
        }, 1, "Cow_Atlas"),
    GOAT("Goat", 4000, BuildingType.BIG_BARN,
        new EtcType[]{
            EtcType.GOAT_MILK, EtcType.BIG_GOAT_MILK
        }, 2, "Goat_Atlas"),
    SHEEP("Sheep", 8000, BuildingType.DELUXE_BARN,
        new EtcType[]{EtcType.WOOL}, 3, "Sheep_Atlas"),
    PIG("Pig", 16000, BuildingType.DELUXE_BARN,
        new EtcType[]{
            EtcType.TRUFFLE
        }, 1, "Pig_Atlas"),
    ;
    //TODO handle atlases

    private final String name;
    private final int price;
    private final BuildingType requiredBuilding;
    private final EtcType[] products;
    private final int productionInterval;
    private final String assetName;

    AnimalType(String name, int price, BuildingType requiredBuilding, EtcType[] products, int productionInterval, String assetName) {
        this.name = name;
        this.price = price;
        this.requiredBuilding = requiredBuilding;
        this.products = products;
        this.productionInterval = productionInterval;
        this.assetName = assetName;
    }

    public String getType() {
        return name;
    }

    public BuildingType getRequiredBuilding() {
        return requiredBuilding;
    }

    public boolean isAtlas() {
        return this.assetName.toLowerCase().contains("atlas");
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
        return new Animal(position, name, this);
    }

    @Override
    public String getName() {
        return name;
    }

    public BuildingType getRequiredBuildingType() {
        return requiredBuilding;
    }

    public static AnimalType findAnimalTypeByName(String name) {
        for (AnimalType animalType : AnimalType.values()) {
            if (animalType.name.equalsIgnoreCase(name)) {
                return animalType;
            }
        }
        return null;
    }

    @Nullable
    public String getAssetName() {
        return switch (assetName) {
            case "null" -> null;
            case "" -> name.replace(" ", "_");
            default -> assetName;
        };
    }


}
