package io.src.model.Network.DTO;

import io.src.model.Enums.WeatherAndTime.WeatherType;
import io.src.model.States.WeatherState;

public class WeatherStateDTO {


    private WeatherType currentWeather;


    public WeatherType getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(WeatherType currentWeather) {
        this.currentWeather = currentWeather;
    }
    // getters & setters
}
