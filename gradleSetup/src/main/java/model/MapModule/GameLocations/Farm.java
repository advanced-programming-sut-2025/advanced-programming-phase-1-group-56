package model.MapModule.GameLocations;

import com.google.gson.annotations.Expose;
import controller.GameMenuController.FarmingController;
import model.App;
import model.Enums.FarmPosition;
import model.GameObject.Crop;
import model.GameObject.GameObject;
import model.GameObject.Tree;
import model.MapModule.Buildings.Building;
import model.MapModule.Buildings.GreenHouse;
import model.MapModule.Buildings.Home;
import model.MapModule.Tile;
import model.Player;
import model.TimeSystem.DateTime;
import model.TimeSystem.TimeObserver;

import java.util.ArrayList;

public class Farm extends GameLocation implements TimeObserver {
    private FarmPosition position;
    private final ArrayList<Building> buildings = new ArrayList<>();
    private final ArrayList<GameObject> allGameObjects = new ArrayList<>();
    @Expose(serialize = false, deserialize = false)
    private Player player;

    public Farm() {
        App.getCurrentUser().getCurrentGame().getTimeSystem().addObserver(this);
    }


    @Override
    public void onHourChanged(DateTime time, boolean newDay) {
        if (newDay) {
            FarmingController.manageStrikeThunder(this);
            FarmingController.manageCrows(this);
            FarmingController.managePlaceMineral(this);


            FarmingController.managePlaceRandomCropOrSeed(this);
            for (GameObject gameObject : readAllGameObjectsFromTiles()) {
                if (gameObject instanceof Tree tree) {
                    if (tree.getDaysWithNoWater() >= 2) {
                        int x = tree.getPosition().getX();
                        int y = tree.getPosition().getY();
                        this.getTileByPosition(x,y).setFixedObject(null);
//                        allGameObjects.remove(tree);
                    }
                } else if (gameObject instanceof Crop crop) {
                    if ((crop.getDaysWithNoWater() >= 2 || crop.isIs1time())) {
                        int x = crop.getPosition().getX();
                        int y = crop.getPosition().getY();
                        this.getTileByPosition(x,y).setFixedObject(null);
//                        allGameObjects.remove(crop);
                    }
                }
            }
        }
    }


    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public FarmPosition getPosition() {
        return position;
    }

    public void setPosition(FarmPosition position) {
        this.position = position;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    //TODO

    public Home getDefaultHome() {
        for (Building building : buildings) {
            if (building instanceof Home home) {
                return home;
            }
        }
        return null;
    }

    public ArrayList<GameObject> getAllGameObjects() {
        return allGameObjects;
    }

    public ArrayList<GameObject> readAllGameObjectsFromTiles() {
        allGameObjects.clear();
        for (Tile[] row : tiles) {
            for (Tile tile : row) {
                if (tile.getFixedObject() != null) {
//                    boolean found = false;
//                    for (GameObject go : allGameObjects) {
//                        if (go.equals(tile.getFixedObject())) {
//                            found = true;
//                            break;
//                        }
//                    }
                    //if (!found) {
                        allGameObjects.add(tile.getFixedObject());
                    //}
                }
            }
        }
        return allGameObjects;
        //DONE!
    }

    public void PlaceAllGameObjectsFromArrayList() {
        //TODO add this if needed
    }

    public GreenHouse getGreenHouse() {
        for (Building building : buildings) {
            if (building instanceof GreenHouse) {
                return (GreenHouse) building;
            }
        }
        return null;
    }

}
