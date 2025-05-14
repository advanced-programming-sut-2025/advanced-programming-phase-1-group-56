package model.States;
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

    public boolean shouldStrikeThunder(){
        ;;;;
        //if storm and chance of 50%;
        return true;//TODO
    }//TODO move this to farm class

    public double getEnergyMultiplier(String season) {

        //TODO
//        switch (todayWeather) {
//            case Rainy:
//            case Storm:
//                if (!season.equalsIgnoreCase("winter")) return 1.5;
//                break;
//            case Snow:
//                if (season.equalsIgnoreCase("winter")) return 2.0;
//                break;
//        }
//        return 1.0;

        return "OMG WOW".length();
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
