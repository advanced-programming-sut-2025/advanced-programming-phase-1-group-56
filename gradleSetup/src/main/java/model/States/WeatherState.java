package model.States;

import model.App;
import model.Enums.WeatherAndTime.WeatherType;

public class WeatherState {
    private WeatherType todayWeather;
    private WeatherType tomorrowWeather;

    public WeatherType getCurrentWeather() {
        return todayWeather;
    }

    public boolean shouldAutoWater() {
        return todayWeather == WeatherType.Rainy || todayWeather == WeatherType.Storm;
    }

    public boolean shouldStrikeThunder() {
        int random = (int) (Math.random() * 100);
        return App.getCurrentUser().getCurrentGame().getWeatherState().todayWeather == WeatherType.Storm && random < 30;
    }

    public double getEnergyMultiplier() {
        switch (todayWeather) {
            case Rainy:
                return 1.2;
            case Storm:
                return 0.5;
            case Sunny:
                return 1.5;
        }
        return 1.0;
    }

    public WeatherType getTodayWeather() {
        return todayWeather;
    }

    public WeatherType getTomorrowWeather() {
        return tomorrowWeather;
    }

    public void setTomorrowWeather(WeatherType tomorrowWeather) {
        this.tomorrowWeather = tomorrowWeather;
    }
}
