package io.src.model.MapModule.Buildings;

import io.src.model.Enums.Buildings.BuildingType;
import io.src.model.GameObject.Animal;
import io.src.model.MapModule.Position;

import java.util.ArrayList;

public interface AnimalHouse{

    public ArrayList<Animal> getAnimals();
    public BuildingType getType();
    public ArrayList<Position> getListOfPathNodes();
}
