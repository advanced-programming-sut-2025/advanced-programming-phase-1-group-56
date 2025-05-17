package controller.MenuController;

import controller.CommandController;
import model.App;
import model.Enums.Menu;
import model.Result;

import java.io.File;

public class MainMenuController extends CommandController {
    private static final String FILE_PATH_FOR_STAY_LOGGED = "StayLoggedIn.json";
    public static Result manageUserLogout() {
        App.setCurrentMenu(Menu.loginMenu);
        App.setCurrentUser(null);
        File file = new File(FILE_PATH_FOR_STAY_LOGGED);
        file.delete()
        return new Result(true, "You have been logged out!");
    }
}
