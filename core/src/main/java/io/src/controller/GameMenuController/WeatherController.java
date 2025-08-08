package io.src.controller.GameMenuController;

import io.src.controller.CommandController;
import io.src.model.App;
import io.src.model.Enums.GameObjects.TreeType;
import io.src.model.Enums.WeatherAndTime.WeatherType;
import io.src.model.GameObject.Crop;
import io.src.model.GameObject.GameObject;
import io.src.model.GameObject.Tree;
import io.src.model.MapModule.GameLocations.Farm;
import io.src.model.MapModule.GameLocations.GameLocation;
import io.src.model.MapModule.Tile;
import io.src.model.Result;

public class WeatherController extends CommandController {
    public static Result cheatThor(GameLocation gameLocation, String Xs, String Ys) {
        if (gameLocation instanceof Farm farm) {
            int x, y;
            try {
                x = Integer.parseInt(Xs);
                y = Integer.parseInt(Ys);
            } catch (NumberFormatException e) {
                return new Result(false, "Invalid X/Y coordinates");
            }

            if (x < 0 || x > farm.getTiles()[0].length || y < 0 || y > farm.getTiles().length) {
                return new Result(false, "X/Y out of bounds");
            }

            Tile tile = farm.getTiles()[y][x];
            GameObject object = tile.getFixedObject();
            if (object instanceof Crop) {
                tile.setFixedObject(null);
                farm.getAllGameObjects().remove(object);
                App.getCurrentUser().getCurrentGame().getTimeSystem().removeObserver((Crop) object);
                return new Result(true, "Thunder Struck successfully");
            } else if (object instanceof Tree) {
                ((Tree) object).setCurrentStage(-1);
                farm.getAllGameObjects().remove(object);
                App.getCurrentUser().getCurrentGame().getTimeSystem().removeObserver((Tree) object);
                return new Result(true, "Thunder Struck successfully");

            } else {
                return new Result(true, "Thunder Struck successfully");
            }

        } else {
            return new Result(false, "You should be in a farm to use this cheat");
        }
    }

    public static Result showTodayWeather() {
        WeatherType weatherType = App.getCurrentUser().getCurrentGame().getWeatherState().getTodayWeather();
        return new Result(true, "weather type: " + weatherType);
    }

    public static Result showTomorrowWeather() {
        WeatherType weatherType = App.getCurrentUser().getCurrentGame().getWeatherState().getTomorrowWeather();
        return new Result(true, "weather type: " + weatherType);
    }

    public static Result cheatWeather(String weather) {
        weather = weather.trim();
        try {
            WeatherType weatherToSet = WeatherType.valueOf(weather);
            App.getCurrentUser().getCurrentGame().getWeatherState().setTomorrowWeather(weatherToSet);
            return new Result(false, "tomorrow weather will be " + weatherToSet);
        } catch (Exception e) {
            return new Result(false, "Invalid weather type");
        }
    }
}
