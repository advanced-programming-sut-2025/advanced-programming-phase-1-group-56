package controller.GameMenuController;

import controller.CommandController;
import model.Result;

import java.util.regex.Matcher;

public class FishingController extends CommandController {
    public static Result fishing(Matcher matcher) {
        String fishingPoleName = matcher.group(1);
        //TODO
        return null;
    }

}
