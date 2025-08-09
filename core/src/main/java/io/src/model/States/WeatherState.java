package io.src.model.States;

import io.src.model.App;
import io.src.model.Enums.WeatherAndTime.Seasons;
import io.src.model.Enums.WeatherAndTime.WeatherType;
import io.src.model.TimeSystem.DateTime;
import io.src.model.TimeSystem.TimeObserver;

public class WeatherState  implements TimeObserver {
    private WeatherType todayWeather;
    private WeatherType tomorrowWeather;

    public WeatherState(){
        Seasons season = App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getSeason();
        todayWeather = getRandomWeather(season);
        tomorrowWeather = getRandomWeather(season);
        App.getCurrentUser().getCurrentGame().getTimeSystem().addObserver(this);
    }

    private WeatherType getRandomWeather(Seasons season) {
        WeatherType weather;
        int rand = (int)(Math.random()*100);
        switch(season){
            case Winter:{
                weather = (rand<7)?WeatherType.Snow:(rand<11)?WeatherType.Storm:(rand<30)?WeatherType.Rainy:WeatherType.Sunny;
            }break;
            case Fall:{
                weather = (rand<2)?WeatherType.Snow:(rand<6)?WeatherType.Storm:(rand<15)?WeatherType.Rainy:WeatherType.Sunny;
            }break;
            case Spring:{
                weather = (rand<2)?WeatherType.Snow:(rand<5)?WeatherType.Storm:(rand<20)?WeatherType.Rainy:WeatherType.Sunny;
            }break;
            case Summer:
            default:{
                weather = (rand<1)?WeatherType.Snow:(rand<2)?WeatherType.Storm:(rand<10)?WeatherType.Rainy:WeatherType.Sunny;
            }break;
        }
        return weather;
    }

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
        return switch (todayWeather) {
            case Rainy -> 1.2;
            case Storm -> 0.5;
            case Sunny -> 1.5;
            default -> 1.0;
        };
    }


    public double getEnergyMultiplierTool() {
        return switch (todayWeather){
            case Rainy -> 1.5;
            case Sunny -> 2;
            default -> 1.0;
        };
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

    @Override
    public void onHourChanged(DateTime time, boolean newDay) {
        if(newDay){
            todayWeather = tomorrowWeather;
            tomorrowWeather = getRandomWeather(App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getSeason());
        }
    }
}
