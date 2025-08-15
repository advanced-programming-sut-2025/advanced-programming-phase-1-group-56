package io.src.controller.MenuController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.bullet.collision._btMprSimplex_t;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.google.gson.Gson;
import io.src.StardewValley;
import io.src.controller.CommandController;
import io.src.controller.GameMenuController.PreGameMenuController;
import io.src.model.*;
import io.src.model.Enums.Menu;
import io.src.model.Enums.SfxEnum;
import io.src.view.InnerMenus.SettingMenu;
import io.src.view.MainMenu;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

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
        menu.getProfileMenu().setController(this);
        menu.getProfileMenu().adStage(menu.getStage());
    }

    public void run() {
        game.setScreen(menu);
        App.init();
        initialize();
    }

    private void initialize() {

        menu.getCoopButton().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                GameAudioManager.getInstance().playSound(SfxEnum.RANDOM_CLICK.getPath(), false, GameAudioManager.sfxVolume);
            }
        });

        menu.getExitButton().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                GameAudioManager.getInstance().playSound(SfxEnum.RANDOM_CLICK.getPath(), false, GameAudioManager.sfxVolume);
                Gdx.app.exit();
            }
        });

        menu.getLogoutButton().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                GameAudioManager.getInstance().playSound(SfxEnum.RANDOM_CLICK.getPath(), false, GameAudioManager.sfxVolume);
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
                GameAudioManager.getInstance().playSound(SfxEnum.RANDOM_CLICK.getPath(), false, GameAudioManager.sfxVolume);
                setAboutMenu(true);
            }
        });

        menu.getBack_about_Button().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                GameAudioManager.getInstance().playSound(SfxEnum.RANDOM_CLICK.getPath(), false, GameAudioManager.sfxVolume);
                if (menu.getNewButton().isVisible()) {
                    setAboutMenu(false);
                    menu.getScrollPane().setScrollPercentY(0.0f);
                } else {
                    setNewMenu(true);
                    App.getCurrentUser().setAvatarIndex(menu.getAvatarMenu().getAvatarIndex());
                    App.getCurrentUser().setAvatarStyleIndex(menu.getAvatarMenu().getAvatarStyleIndex());
                    App.saveUsers();
                }
            }
        });

        menu.getNewButton().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                GameAudioManager.getInstance().playSound(SfxEnum.RANDOM_CLICK.getPath(), false, GameAudioManager.sfxVolume);
                menu.getAvatarMenu().setAvatarIndex(App.getCurrentUser().getAvatarIndex());
                menu.getAvatarMenu().setAvatarStyleIndex(App.getCurrentUser().getAvatarStyleIndex());
                menu.getAvatarMenu().updateAvatarTextures();
                setNewMenu(false);
            }
        });

        menu.getLoadButton().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                GameAudioManager.getInstance().playSound(SfxEnum.RANDOM_CLICK.getPath(), false, GameAudioManager.sfxVolume);
                PreGameMenuController.manageFourPlayerGame();
            }
        });

        menu.getProfileButton().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                GameAudioManager.getInstance().playSound(SfxEnum.RANDOM_CLICK.getPath(), false, GameAudioManager.sfxVolume);
                menu.getProfileMenu().updateAvatar();
                hideMainMenu(false);
                menu.getProfileMenu().setVisible(true);
            }
        });

        menu.getSettingButton().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                GameAudioManager.getInstance().playSound(SfxEnum.RANDOM_CLICK.getPath(), false, GameAudioManager.sfxVolume);
                SettingMenu settingMenu = new SettingMenu(SkinManager.getInstance().getSkin(SkinManager.MAIN_SKIN));
                settingMenu.show(menu.getStage());
            }
        });
    }

    public void hideMainMenu(boolean state) {
        menu.getNewButton().setVisible(state);
        menu.getLoadButton().setVisible(state);
        menu.getCoopButton().setVisible(state);
        menu.getExitButton().setVisible(state);
        menu.getLogoutButton().setVisible(state);
        menu.getButtonTable().setVisible(state);
    }

    private void setNewMenu(boolean state) {
        hideMainMenu(state);
        menu.getBack_about_Button().setVisible(!state);
        menu.getAvatarMenu().setVisible(!state);
    }

    private void setAboutMenu(boolean state) {
        if (state)
            menu.getStage().setScrollFocus(menu.getScrollPane());

        // state :
        menu.getAboutWindow().setVisible(state);
        menu.getBack_about_Button().setVisible(state);

        // disable
        menu.getLoadButton().setDisabled(state);
        menu.getLogoutButton().setDisabled(state);
        menu.getNewButton().setDisabled(state);
        menu.getCoopButton().setDisabled(state);


        // not state :
        menu.getButtonTable().setVisible(!state);
        menu.getExitButton().setVisible(!state);
    }

    public static Result manageUserLogout() {
        App.setCurrentMenu(Menu.loginMenu);
        App.setCurrentUser(null);
        File file = new File(FILE_PATH_FOR_STAY_LOGGED);
        if (!file.delete())
            return new Result(true, "");
        return new Result(true, "You have been logged out!");
    }

    public static void Login() {


    }

    public static void exitFromGame() {

    }

    public static void newGame() {


    }

    public static void co_op() {


    }
}
