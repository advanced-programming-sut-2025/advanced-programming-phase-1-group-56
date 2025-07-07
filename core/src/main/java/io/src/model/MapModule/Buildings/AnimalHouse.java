package io.src.model.MapModule.Buildings;

import io.src.model.Enums.Buildings.BuildingType;
import io.src.model.GameObject.Animal;

import java.util.ArrayList;

public interface AnimalHouse {

    public ArrayList<Animal> getAnimals();
    public BuildingType getType();
}
