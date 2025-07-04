package io.src.model.TimeSystem;

import java.util.ArrayList;

public class TimeSystem {
    private final ArrayList<TimeObserver> observers = new ArrayList<>();
    private final ArrayList<TimeObserver> observersCopy = new ArrayList<>();
    private final DateTime time;

    public TimeSystem(int day, int hour) {
        time = new DateTime(day, hour);
    }

    public void notifyObservers(boolean newDay) {
        observersCopy.clear();
        observersCopy.addAll(observers);

        for (TimeObserver o : observersCopy) {
            o.onHourChanged(time, newDay);
        }
    }

    public void nextHour() {
        time.setHour(getDateTime().getHour() + 1);
        if (time.getHour() >= 22) {
            time.setHour(9);
            time.setDay(time.getDay() + 1);
            notifyObservers(true);
        } else {
            notifyObservers(false);
        }
    }

    public void addObserver(TimeObserver o) {
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }

    public void removeObserver(TimeObserver o) {
        observers.remove(o);
    }

    public ArrayList<TimeObserver> getObservers() {
        return new ArrayList<>(observers);
    }

    public DateTime getDateTime() {
        return time;
    }
}
