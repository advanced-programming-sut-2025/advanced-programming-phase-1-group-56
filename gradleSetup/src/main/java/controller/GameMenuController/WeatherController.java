package controller.GameMenuController;

import controller.CommandController;
import model.App;
import model.Enums.WeatherAndTime.WeatherType;
import model.Result;

public class WeatherController extends CommandController {
    public static Result cheatThor(String x,String y) {

        return null;
    }

    public static Result showTodayWeather() {
        WeatherType weatherType = App.getCurrentUser().getCurrentGame().getWeatherState().getTodayWeather();
        return new Result(true,"weather type: " + weatherType);
    }

    public static Result showTomorrowWeather() {
        WeatherType weatherType = App.getCurrentUser().getCurrentGame().getWeatherState().getTomorrowWeather();
        return new Result(true,"weather type: " + weatherType);
    }

    public static Result cheatWeather(String weather) {
        weather = weather.trim();
        try{
            WeatherType weatherToSet = WeatherType.valueOf(weather);
            App.getCurrentUser().getCurrentGame().getWeatherState().setTomorrowWeather(weatherToSet);
            return new Result(false,"tomorrow weather will be " + weatherToSet);
        }
        catch (Exception e){
            return new Result(false,"Invalid weather type");
        }
    }
}
