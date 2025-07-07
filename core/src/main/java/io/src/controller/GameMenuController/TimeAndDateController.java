package io.src.controller.GameMenuController;

import io.src.controller.CommandController;
import io.src.model.App;
import io.src.model.Enums.WeatherAndTime.DayOfWeek;
import io.src.model.Enums.WeatherAndTime.Seasons;
import io.src.model.Result;

public class TimeAndDateController extends CommandController {
    public static Result manageShowTime() {
        int hour = App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour();
        String hour24 = (hour > 12) ? String.format("%02d", hour)+"PM":  String.format("%02d", hour)+"AM";
        return new Result(true,"Current Hour:" + hour24);
    }

    public static Result manageShowDate() {
        int day = App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getDay();
        return new Result(true,"Emshow showhe " + day + " om mibashad");
    }

    public static Result manageShowDateAndTime() {
        int hour = App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getHour();
        String hour24 = (hour > 12) ? String.format("%02d", hour)+"PM":  String.format("%02d", hour)+"AM";
        int day = App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getDay();
        return new Result(true, "Day: " + day + "\nHour: " + hour24 );
    }

    public static Result dayOfTheWeek() {
        DayOfWeek dow = App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getDayOfWeek();
        return new Result(true,"Day Of Week = " + dow);
    }
    public static Result showCurrentSeason() {
        Seasons season = App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().getSeason();
        return new Result(true,"Current Season = " + season);
    }

    //CHEAT

    public static Result cheatAdvanceTime(String newTime) {
        newTime = newTime.trim();
        try{
            int newHour = Integer.parseInt(newTime);
            App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().addHour(newHour);
            return new Result(true,"Time successfully changed to " + newTime);
        }catch(NumberFormatException e){
            return new Result(true,"Invalid time format");
        }
    }

    public static Result cheatAdvanceDate(String newDate) {
        newDate = newDate.trim();
        try{
            int newDay = Integer.parseInt(newDate);
            App.getCurrentUser().getCurrentGame().getTimeSystem().getDateTime().addDay(newDay);
            return new Result(true,"Date successfully changed to " + newDate);
        }catch(NumberFormatException e){
            return new Result(true,"Invalid time format");
        }
    }


}
