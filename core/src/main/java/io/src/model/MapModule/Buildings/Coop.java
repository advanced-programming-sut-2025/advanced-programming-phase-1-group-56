package io.src.model.MapModule.Buildings;

import io.src.model.Enums.Buildings.BuildingType;
//import io.src.model.Enums.Buildings.CoopType;
import io.src.model.GameObject.Animal;
import io.src.model.MapModule.Position;

import java.util.ArrayList;

public class Coop extends Building implements AnimalHouse {
    private ArrayList<Animal> animals = new ArrayList<>();
    private final BuildingType type;


    public Coop(Position position, BuildingType type) {
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

    public int getCapacity() {
        return type.getCapacity();
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    @Override
    public BuildingType getType() {
        return type;
    }

    public void setAnimals(ArrayList<Animal> animals) {
        this.animals = animals;
    }

    public int getRemainingCapacity() {
        return this.type.getCapacity() - animals.size();
    }
}
