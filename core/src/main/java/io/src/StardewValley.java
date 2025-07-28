package io.src;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.gson.Gson;
import io.src.controller.GameMenuController.GameController;
import io.src.controller.MenuController.LoginMenuController;
import io.src.controller.MenuController.MainMenuController;
import io.src.model.App;
import io.src.model.Game;
import io.src.model.User;
import io.src.view.AppView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class StardewValley extends com.badlogic.gdx.Game {
    private Game game;
    private static SpriteBatch batch;

    public StardewValley() {
//        this.game = game;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        Gson gson = new Gson();
        User user;
        try (Reader reader = new FileReader("assets\\StayLoggedIn.json")) {
            user = gson.fromJson(reader, User.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (user == null) {
            LoginMenuController loginMenuController = new LoginMenuController(this);
            loginMenuController.init();
            loginMenuController.run();
        } else {
            MainMenuController mainMenuController = new MainMenuController(this);
            mainMenuController.init();
            mainMenuController.run();
        }
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public static SpriteBatch getBatch() {
        return batch;
    }
}
