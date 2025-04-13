package model.locations;

import model.timeSystem.DateTime;
import model.timeSystem.TimeObserver;

import java.util.ArrayList;

public class Farm implements TimeObserver {
    Tile[][] tiles;
    ArrayList<Building> buildings;



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
    //TODO
}
