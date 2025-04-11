package model.Enums;

import model.WeatherSystem.WeatherStrategy;

public enum Weather {
    Sunny(new ),
    Snow(),
    Rainy()
    Storm();

    WeatherStrategy strategy;

    public WeatherStrategy getStrategy() {
        return strategy;
    }
}
