package io.src.model.TimeSystem;

import io.src.model.App;
import io.src.model.Enums.WeatherAndTime.DayOfWeek;
import io.src.model.Enums.WeatherAndTime.Seasons;

public class DateTime {
    private int hour;
    private int day;

    public DateTime(int day, int hour) {
        this.day = day;
        this.hour = hour;
    }

    public DateTime(DateTime other) {
        this.day = other.day;
        this.hour = other.hour;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void addHour(int hour) {
        for (int i = 0; i < hour; i++) {
            App.getCurrentUser().getCurrentGame().getTimeSystem().nextHour();
        }
    }

    public void addDay(int day) {
        int destDay = App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getDay() + day;
        while (App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getDay() < destDay) {
            App.getCurrentUser().getCurrentGame().getTimeSystem().nextHour();
        }
    }

    public Seasons getSeason() {
        return Seasons.values()[((day-1)/28)%4];
    }

    public DayOfWeek getDayOfWeek() {
        return DayOfWeek.values()[(day - 1) % 7];
    }

}
