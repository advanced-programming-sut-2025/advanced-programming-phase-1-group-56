package model.GameObject.Animal;

import model.Enums.Animals.AnimalType;

import java.util.*;

public class AnimalRegistry {
    private static final Map<AnimalType, AnimalInfo> animalInfoMap = new HashMap<>();

    static {
        animalInfoMap.put(AnimalType.CHICKEN, new AnimalInfo(AnimalType.CHICKEN, 800, 1, "Coop",null));//TODO
        animalInfoMap.put(AnimalType.DUCK, new AnimalInfo(AnimalType.DUCK, 1200, 1, "BigCoop",null));
        animalInfoMap.put(AnimalType.RABBIT, new AnimalInfo(AnimalType.RABBIT, 8000, 1, "DeluxeCoop",null));
        animalInfoMap.put(AnimalType.DINOSAUR, new AnimalInfo(AnimalType.DINOSAUR, 14000, 1, "BigCoop",null));

        animalInfoMap.put(AnimalType.COW, new AnimalInfo(AnimalType.COW, 1500, 1, "Barn",null));
        animalInfoMap.put(AnimalType.GOAT, new AnimalInfo(AnimalType.GOAT, 4000, 1, "BigBarn",null));
        animalInfoMap.put(AnimalType.SHEEP, new AnimalInfo(AnimalType.SHEEP, 8000, 1, "DeluxeBarn",null));
        animalInfoMap.put(AnimalType.PIG, new AnimalInfo(AnimalType.PIG, 16000, 1, "DeluxeBarn",null));
    }

    public static AnimalInfo getAnimalInfo(AnimalType type) {
        return animalInfoMap.get(type);
    }

    public static boolean isValidAnimal(String name) {
        try {
            AnimalType.valueOf(name.toUpperCase());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
