package io.src.model.MapModule.Buildings;

import io.src.model.Enums.Buildings.BuildingType;
//import io.src.model.Enums.Buildings.CoopType;
import io.src.model.Enums.TileType;
import io.src.model.GameObject.Animal;
import io.src.model.MapModule.GameLocations.GameLocation;
import io.src.model.MapModule.Position;
import io.src.model.MapModule.Tile;

import java.util.ArrayList;

import static io.src.model.MapModule.newFarmLoader.loadTheLocation;

public class Coop extends Building implements AnimalHouse {
    private ArrayList<Animal> animals = new ArrayList<>();
    private final BuildingType type;


    public Coop(Position position, BuildingType type) {
        super(
                position,
                false,
                type.getName(),
                new Position(
                    position.getX() + 1,
                    position.getY()
                ),
                type.getHeight(),
                type.getWidth(),
                type
        );
        this.type = type;
        GameLocation indoor = loadTheLocation("assets\\gameLocations\\Coop_Indoor");
        setIndoor(indoor);
        setInitialPosition(new Position(2 , 2));
        indoor.getTiles()[2][1].setTileType(TileType.Wrapper);
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


    @Override
    public String getAssetName() {
        return buildingType.getAssetName()+ "_Closed";
    }
}
