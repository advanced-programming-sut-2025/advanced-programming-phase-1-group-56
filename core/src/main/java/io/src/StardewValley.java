package io.src;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.gson.Gson;
import io.src.controller.GameMenuController.GameController;
import io.src.controller.MenuController.LoginMenuController;
import io.src.controller.MenuController.MainMenuController;
import io.src.model.App;
import io.src.model.Game;
import io.src.model.Network.Client.LobbyClient;
import io.src.model.User;
import io.src.view.AppView;
import io.src.view.GameMenus.GameView;

import java.io.*;

public class StardewValley extends com.badlogic.gdx.Game {
    private static Game game;

    private static GameView gameView;

    private static SpriteBatch batch;

    public StardewValley() {
//        this.game = game;
    }

    @Override
    public void create() {
        new AppView().run();
        setScreen(new LobbyClient());
        App.setStardewValley(this);
//        GameController gameController = new GameController(this);
//        gameController.init();
//        gameController.run();


//        batch = new SpriteBatch();
//        Gson gson = new Gson();
//        User user = null;
//
//        if (new File("assets\\StayLoggedIn.json").exists()) {
//            try (Reader reader = new FileReader("assets\\StayLoggedIn.json")) {
//                user = gson.fromJson(reader, User.class);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        if (user == null) {
//            LoginMenuController loginMenuController = new LoginMenuController(this);
//            loginMenuController.init();
//            loginMenuController.run();
//        } else {
//            MainMenuController mainMenuController = new MainMenuController(this);
//            mainMenuController.init();
//            mainMenuController.run();
//        }
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

    public GameView getGameView() {
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
