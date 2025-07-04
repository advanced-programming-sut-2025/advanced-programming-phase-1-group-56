package io.src.model.MapModule.Buildings;


import io.src.model.Enums.Buildings.BuildingType;
import io.src.model.GameObject.Animal;
import io.src.model.MapModule.Position;

import java.util.ArrayList;

public class Barn extends Building implements AnimalHouse {
    private final BuildingType type;
    private final ArrayList<Animal> animals = new ArrayList<>();


    public Barn(Position position, BuildingType type) {
        super(
                position,
                false,
                type.getName(),
                new Position(
                        (2 * position.getX() + type.getWidth()) / 2,
                        (2 * position.getY() + type.getHeight()) / 2
                ),
                type.getHeight(),
                type.getWidth(),
                type
        );
        this.type = type;
    }

    @Override
    public void interact() {
        //TODO
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public boolean hasFreeCapacity() {
        return animals.size() < this.getRemainingCapacity();
    }

    public int getCurrentAnimalCount() {
        return animals.size();
    }

    public int getRemainingCapacity() {
        return this.type.getCapacity() - animals.size();
    }

    public BuildingType getType() {
        return type;
    }
}
