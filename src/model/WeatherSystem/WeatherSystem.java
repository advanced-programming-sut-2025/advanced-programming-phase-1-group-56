package model.WeatherSystem;

public class WeatherSystem {
    private WeatherStrategy strategy;

    public void setWeatherStrategy(WeatherStrategy strategy) {
        this.strategy = strategy;
    }

    public void applyWeather() {
        strategy.applyWeatherEffect();
    }
}