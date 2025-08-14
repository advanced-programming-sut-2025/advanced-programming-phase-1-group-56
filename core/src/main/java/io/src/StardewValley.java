package io.src;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.src.controller.MenuController.LoginMenuController;
import io.src.controller.MenuController.MainMenuController;
import io.src.model.App;
import io.src.model.Game;
import io.src.model.TimeSystem.LocalDateTimeAdapter;
import io.src.model.Network.Client.LobbyClient;
import io.src.model.User;
import io.src.view.GameMenus.GameView;
import io.src.view.GameMenus.ShippingBarWindow;

import java.io.*;
import java.time.LocalDateTime;

public class StardewValley extends com.badlogic.gdx.Game {
    private static StardewValley stardewValley;
    private static Game game;
    private static GameView gameView;


    private static SpriteBatch batch;

    @Override
    public void create() {
        stardewValley = this;
        batch = new SpriteBatch();
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
        User user = null;

        if (new File("assets\\StayLoggedIn.json").exists()) {
            try (Reader reader = new FileReader("assets\\StayLoggedIn.json")) {
                user = gson.fromJson(reader, User.class);
                App.setCurrentUser(user);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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

    public static StardewValley getStardewValley() {
        return stardewValley;
    }

    public static Game getGame() {
        return game;
    }

    public static void setGame(Game game) {
        StardewValley.game = game;
    }

    public static SpriteBatch getBatch() {
        return batch;
    }

    public static GameView getGameView() {
        return gameView;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    public static void setBatch(SpriteBatch batch) {
        StardewValley.batch = batch;
    }
    public static void startGame(Game newGame) {
        game = newGame;
        gameView = new GameView(newGame);
        App.getStardewValley().setScreen(gameView);
    }

}
