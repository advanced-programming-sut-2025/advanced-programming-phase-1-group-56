package model.Locations;

import model.TimeSystem.DateTime;
import model.TimeSystem.TimeObserver;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Farm implements TimeObserver {
    Tile [][] tiles;
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
