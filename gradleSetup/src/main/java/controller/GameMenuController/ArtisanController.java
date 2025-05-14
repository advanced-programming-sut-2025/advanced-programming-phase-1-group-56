package controller.GameMenuController;

import controller.CommandController;
import model.Result;

import java.util.regex.Matcher;

public class ArtisanController extends CommandController {
    public static Result useArtisan(Matcher matcher) {
        String machineName = matcher.group(1);
        String productName = matcher.group(2);

        return null;
    }
    public static Result getArtisan(Matcher matcher){

        return null;
    }


}
