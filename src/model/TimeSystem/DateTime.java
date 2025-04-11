package model.TimeSystem;

import model.Enums.DayOfWeek;
import model.Enums.Seasons;

public class DateTime {
    private int hour;
    private int day;

    DateTime( int day,int hour) {
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
        this.hour += hour;
    }
    //TODO
    public Seasons getSeason(){

    }
    //TODO
    public DayOfWeek getDayOfWeek(){

    }
}
