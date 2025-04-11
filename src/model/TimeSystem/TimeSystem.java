package model.TimeSystem;

import java.util.ArrayList;

public class TimeSystem {
    private final ArrayList<Observer> observers = new ArrayList<>();
    private DateTime time;


    public TimeSystem(int day, int hour) {
        time = new DateTime(day,hour);
    }
    public void notifyObservers(boolean newDay) {
        for (Observer o : observers) {
            if(newDay) {
                o.onDayChanged(time);
            }
            else {
                o.onHourChanged(time);
            }
        }
    }

    public void nextHour() {
        time.addHour(1);
        if (time.getHour() >= 22) {
            time.setHour(0);time.setDay(time.getDay() + 1);
            notifyObservers(true);
        }
        else {
            notifyObservers(false);
        }
    }

    public void addObserver(Observer o) { observers.add(o); }

    public ArrayList<Observer> getObservers() { return observers; }

    public DateTime getDateTime() { return time; }


}
