package model.MapModule.Buildings;

import model.Enums.Buildings.BuildingType;
import model.GameObject.Animal;

import java.util.ArrayList;

public interface AnimalHouse {

    public ArrayList<Animal> getAnimals();
    public BuildingType getType();
}
