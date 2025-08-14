package io.src.model.MapModule.Buildings;


import io.src.model.Enums.Buildings.BuildingType;
import io.src.model.Enums.TileType;
import io.src.model.GameObject.Animal;
import io.src.model.MapModule.GameLocations.GameLocation;
import io.src.model.MapModule.Position;

import java.util.ArrayList;

import static io.src.model.MapModule.newFarmLoader.loadTheLocation;

public class Barn extends Building implements AnimalHouse {
    private final BuildingType type;
    private final ArrayList<Animal> animals = new ArrayList<>();
    private boolean open = false;


    public Barn(Position position, BuildingType type) {
        super(
            position,
            false,
            type.getName(),
            new Position(
                position.getX()+1,
                position.getY()
            ),
            type.getHeight(),
            type.getWidth(),
            type
        );
        this.type = type;
        GameLocation indoor = loadTheLocation("assets\\gameLocations\\Barn");
        setIndoor(indoor);
        setInitialPosition(new Position(11 , 3));
        indoor.getTiles()[11][2].setTileType(TileType.Wrapper);////
    }

    public ArrayList<Position> getListOfPathNodes() {
        ArrayList<Position> pathNodes = new ArrayList<>();
//        for (Tile[] tileLine : getIndoor().getTiles()) {
//            for (Tile tile : tileLine) {
//
//            }
//        }
        for (int i = 0; i < 5; i++) {
            int randomX;
            int randomY;
            boolean flag = false;
            while (!flag){
                randomX = (int) (Math.random() * (getIndoor().getWidth()-3));
                randomY = (int) (Math.random() * (getIndoor().getHeight()-3));
                if (getIndoor().getTiles()[randomX][randomY].getTileType() != TileType.Wrapper && getIndoor().getTiles()[randomX][randomY].isWalkable()) {
                    flag = true;
                    pathNodes.add(new Position(randomX, randomY));
                }
            }
        }
        return pathNodes;
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


    @Override
    public String getAssetName() {
        return type.getAssetName() + (open ? "_Open" : "_Closed");
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
