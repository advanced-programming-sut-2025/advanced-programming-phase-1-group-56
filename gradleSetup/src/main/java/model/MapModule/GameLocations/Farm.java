package model.MapModule.GameLocations;

import model.MapModule.Buildings.Home;
import model.MapModule.Tile;
import model.TimeSystem.DateTime;
import model.TimeSystem.TimeObserver;

public class Farm extends GameLocation implements TimeObserver {

    private Home home;

    public Farm(Tile[][] tiles) {
        this.tiles = tiles;
    }



    @Override
    public void onHourChanged(DateTime time, boolean newDay) {
        if(newDay) {
            //TODO
            System.out.println("good Morning");
        }
        else {
            System.out.println("Welcome to your turn");
            //TODO
        }
    }

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }
    //TODO
}
