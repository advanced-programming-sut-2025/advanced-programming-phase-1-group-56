package model.TimeSystem;

import java.util.ArrayList;

public class TimeSystem {
    private final ArrayList<TimeObserver> observers = new ArrayList<>();
    private DateTime time;


    public TimeSystem(int day, int hour) {
        time = new DateTime(day,hour);
    }
    public void notifyObservers(boolean newDay) {
        for (TimeObserver o : observers) {
            o.onHourChanged(time, newDay);
        }
    }

    public void nextHour() {
        time.setHour(time.getHour() + 1);
        if (time.getHour() >= 22) {
            time.setHour(9);time.setDay(time.getDay() + 1);
            notifyObservers(true);
        }
        else {
            notifyObservers(false);
        }
    }

    public void addObserver(TimeObserver o) { observers.add(o); }

    public ArrayList<TimeObserver> getObservers() { return observers; }

    public DateTime getDateTime() { return time; }


}
