package model.Locations;

import model.TimeSystem.DateTime;
import model.TimeSystem.TimeObserver;

import java.util.ArrayList;

public class Farm implements TimeObserver {
    private Tile[][] tiles;
    private Home myHome;



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

    public Home getMyHome() {
        return myHome;
    }

    public void setMyHome(Home myHome) {
        this.myHome = myHome;
    }

}
