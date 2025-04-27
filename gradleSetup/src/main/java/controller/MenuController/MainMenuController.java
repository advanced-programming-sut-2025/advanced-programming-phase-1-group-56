package controller.MenuController;

import controller.CommandController;
import model.App;
import model.Enums.Menu;
import model.Result;

public class MainMenuController extends CommandController {
    public static Result manageUserLogout() {
        App.setCurrentMenu(Menu.loginMenu);
        App.setCurrentUser(null);
        return new Result(true, "You have been logged out!");
    }
}
