package model.MapModule;

import model.MapModule.GameLocations.Farm;
import model.MapModule.GameLocations.GameLocation;

import java.util.ArrayList;

public class GameMap {
    private ArrayList<GameLocation> gameLocations;



//    public GameMap(ArrayList<Farm> farms) {
//        this.farms = farms;
//    }

    public ArrayList<GameLocation> getGameLocations() {
        return gameLocations;
    }

    public void setGameLocations(ArrayList<GameLocation> gameLocations) {
        this.gameLocations = gameLocations;
    }
}
