package io.src.controller.MenuController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.src.StardewValley;
import io.src.controller.CommandController;
import io.src.model.App;
import io.src.model.Enums.Menu;
import io.src.model.GameAudioManager;
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

    public void init() {
        menu = new MainMenu();
    }

    public void run() {
        game.setScreen(menu);
        App.init();
        initialize();
    }

    private void initialize() {

        menu.getCoopButton().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                GameAudioManager.getInstance().playSound("SFXs/click2.mp3", false, 1f);
            }
        });

        menu.getLoadButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameAudioManager.getInstance().playSound("SFXs/click1.mp3", false, 1f);
            }
        });

        menu.getExitButton().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        menu.getLogoutButton().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                GameAudioManager.getInstance().playSound("SFXs/click1.mp3", false, 1f);
                Result result = manageUserLogout();
                if (result.isSuccess()) {
                    LoginMenuController controller = new LoginMenuController(game);
                    controller.init();
                    controller.run();
                }
            }
        });

        menu.getAboutButton().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                GameAudioManager.getInstance().playSound("SFXs/click1.mp3", false, 1f);
                setAboutMenu(true);
            }
        });

        menu.getBack_about_Button().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                GameAudioManager.getInstance().playSound("SFXs/click1.mp3", false, 1f);
                if (menu.getNewButton().isVisible()) {
                    setAboutMenu(false);
                    menu.getScrollPane().setScrollPercentY(0.0f);
                    System.out.println("back about button clicked");
                } else {
                    setNewMenu(true);
                    System.out.println("new button clicked");
                }
            }
        });

        menu.getNewButton().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                GameAudioManager.getInstance().playSound("SFXs/click1.mp3", false, 1f);
                setNewMenu(false);
            }
        });
    }

    private void setNewMenu(boolean state) {
        menu.getNewButton().setVisible(state);
        menu.getLoadButton().setVisible(state);
        menu.getCoopButton().setVisible(state);
        menu.getLogoutButton().setVisible(state);
        menu.getAboutButton().setVisible(state);
        menu.getExitButton().setVisible(state);

        menu.getBack_about_Button().setVisible(!state);
        menu.getAvatarMenu().setVisible(!state);
    }

    private void setAboutMenu(boolean state) {
        if (state)
            menu.getStage().setScrollFocus(menu.getScrollPane());

        // state :
        menu.getAboutWindow().setVisible(state);
        menu.getBack_about_Button().setVisible(state);

        // not state :
        menu.getAboutButton().setVisible(!state);
        menu.getExitButton().setVisible(!state);

        // disable
        menu.getLoadButton().setDisabled(state);
        menu.getLogoutButton().setDisabled(state);
        menu.getNewButton().setDisabled(state);
        menu.getCoopButton().setDisabled(state);
    }

    public static Result manageUserLogout() {
        App.setCurrentMenu(Menu.loginMenu);
        App.setCurrentUser(null);
        File file = new File(FILE_PATH_FOR_STAY_LOGGED);
        if (!file.delete())
            return new Result(false, "");
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
