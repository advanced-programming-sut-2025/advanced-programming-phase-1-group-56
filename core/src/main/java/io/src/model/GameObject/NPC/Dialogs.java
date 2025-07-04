package io.src.model.GameObject.NPC;

import io.src.model.Enums.WeatherAndTime.WeatherType;

public class Dialogs {
    private String text;
    private WeatherType weatherState;
    private boolean day;
    private int FriendshipLevel;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public WeatherType getWeatherState() {
        return weatherState;
    }

    public void setWeatherState(WeatherType weatherState) {
        this.weatherState = weatherState;
    }

    public boolean isDay() {
        return day;
    }

    public void setDay(boolean day) {
        this.day = day;
    }

    public int getFriendshipLevel() {
        return FriendshipLevel;
    }

    public void setFriendshipLevel(int friendshipLevel) {
        FriendshipLevel = friendshipLevel;
    }
}
