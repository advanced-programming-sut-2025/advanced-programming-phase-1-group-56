package io.src.controller.MenuController;

import io.src.StardewValley;
import io.src.controller.CommandController;
import io.src.model.App;
import io.src.model.Enums.Menu;
import io.src.model.Result;
import io.src.view.MainMenu;

import java.io.File;

public class MainMenuController extends CommandController {
    private static final String FILE_PATH_FOR_STAY_LOGGED = "assets\\StayLoggedIn.json";

    // fields :

    private final StardewValley game;
    private MainMenu menu;

    // init :

    public MainMenuController(StardewValley game) {
        this.game = game;
    }

    public void init(){
        menu = new MainMenu();
    }

    public void run(){
        game.setScreen(menu);
        initialize();
    }

    private void initialize() {

    }

    public static Result manageUserLogout() {
        App.setCurrentMenu(Menu.loginMenu);
        App.setCurrentUser(null);
        File file = new File(FILE_PATH_FOR_STAY_LOGGED);
        file.delete();

        return new Result(true, "You have been logged out!");
    }
}
