package model.TimeSystem;

import model.App;
import model.Enums.WeatherAndTime.DayOfWeek;
import model.Enums.WeatherAndTime.Seasons;

public class DateTime {
    private int hour;
    private int day;

    DateTime(int day, int hour) {
        this.day = day;
        this.hour = hour;
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
