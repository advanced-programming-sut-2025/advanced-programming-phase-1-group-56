package model.Locations;

import model.TimeSystem.DateTime;
import model.TimeSystem.TimeObserver;

public class Farm implements TimeObserver {
    int hello;

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
