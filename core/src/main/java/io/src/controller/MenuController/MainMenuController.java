package io.src.controller.MenuController;

import io.src.controller.CommandController;
import io.src.model.App;
import io.src.model.Enums.Menu;
import io.src.model.Result;

import java.io.File;

public class MainMenuController extends CommandController {
    private static final String FILE_PATH_FOR_STAY_LOGGED = "assets\\StayLoggedIn.json";
    public static Result manageUserLogout() {
        App.setCurrentMenu(Menu.loginMenu);
        App.setCurrentUser(null);
        File file = new File(FILE_PATH_FOR_STAY_LOGGED);
        file.delete();
        return new Result(true, "You have been logged out!");
    }

    public static void Login(){



    }
    public static void exitFromGame(){

    }

    public static void newGame(){


    }

    public static void co_op(){



    }
}
