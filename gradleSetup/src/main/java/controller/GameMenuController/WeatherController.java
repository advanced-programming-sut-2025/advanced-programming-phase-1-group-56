package controller.GameMenuController;

import controller.CommandController;
import model.App;
import model.Enums.GameObjects.TreeType;
import model.Enums.WeatherAndTime.WeatherType;
import model.GameObject.Crop;
import model.GameObject.GameObject;
import model.GameObject.Tree;
import model.MapModule.GameLocations.Farm;
import model.MapModule.GameLocations.GameLocation;
import model.MapModule.Tile;
import model.Result;

public class WeatherController extends CommandController {
    public static Result cheatThor(GameLocation gameLocation, String Xs, String Ys) {
            if(gameLocation instanceof Farm farm){
                int x,y;
                try{
                    x = Integer.parseInt(Xs);
                    y = Integer.parseInt(Ys);
                }catch(NumberFormatException e){
                    return new Result(false, "Invalid X/Y coordinates");
                }

                if(x<0 || x> farm.getTiles()[0].length || y<0 || y> farm.getTiles().length){
                    return new Result(false, "X/Y out of bounds");
                }

                Tile tile = farm.getTiles()[x][y];
                GameObject object = tile.getFixedObject();
                if(object instanceof Crop){
                    tile.setFixedObject(null);
                    farm.getAllGameObjects().remove(object);
                    App.getCurrentUser().getCurrentGame().getTimeSystem().getObservers().remove(object);
                    return new Result(true,"Thunder Struck successfully");
                }
                else if(object instanceof Tree){
                    Tree burnedTree = new Tree(TreeType.BURNT_TREE,object.getPosition());
                    tile.setFixedObject(burnedTree);
                    farm.getAllGameObjects().remove(object);
                    App.getCurrentUser().getCurrentGame().getTimeSystem().getObservers().remove(object);
                    return new Result(true,"Thunder Struck successfully");

                } else {
                    return new Result(true,"Thunder Struck successfully");
                }

            }
            else{
                return new Result(false,"You should be in a farm to use this cheat");
            }
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
